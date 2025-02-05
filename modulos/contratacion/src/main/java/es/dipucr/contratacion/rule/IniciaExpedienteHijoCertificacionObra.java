package es.dipucr.contratacion.rule;

import java.util.Date;

import org.apache.log4j.Logger;

import es.dipucr.sigem.api.rule.common.utils.ExpedientesRelacionadosUtil;
import es.dipucr.sigem.api.rule.common.utils.FechasUtil;
import es.dipucr.sigem.api.rule.common.utils.ProcedimientosUtil;
import es.dipucr.sigem.api.rule.common.utils.SecretariaUtil;
import es.dipucr.sigem.api.rule.common.utils.TramitesUtil;
import es.dipucr.sigem.api.rule.procedures.Constants;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.context.ClientContext;

public class IniciaExpedienteHijoCertificacionObra implements IRule{
	
	public static final Logger logger = Logger.getLogger(IniciaExpedienteHijoCertificacionObra.class);

	public void cancel(IRuleContext rulectx) throws ISPACRuleException {
	}

	public Object execute(IRuleContext rulectx) throws ISPACRuleException {
		try{
			/************************************************************************/
			ClientContext cct = (ClientContext) rulectx.getClientContext();
			IInvesflowAPI invesFlowAPI = cct.getAPI();
			IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
			//IGenDocAPI genDocAPI = cct.getAPI().getGenDocAPI();
			 /***********************************************************************/
			/**Inicio de expediente de 'Certificación de obra'**/
			
			
			/**Obtengo el organo resolutor del expediente**/
			int idCtProcedimientoNuevo = 0;
			IItemCollection itColExp = SecretariaUtil.buscaTodosExpDelDepartamento(cct, rulectx.getNumExp(),"WHERE COD_PCD LIKE 'CERTIF-OBRA%'");
			if(itColExp.iterator()!=null){
				IItem it_ct_procedimiento = (IItem) itColExp.iterator().next();
				if(it_ct_procedimiento!=null){
					String nombreCertifObra = it_ct_procedimiento.getString("NOMBRE");	
					logger.warn("nombreCertifObra "+nombreCertifObra);
				    IItem procArqObra = ProcedimientosUtil.getProcedimientoByCodNombre(rulectx, nombreCertifObra);  
				    if(procArqObra!=null){
				    	idCtProcedimientoNuevo = procArqObra.getInt("ID");
				    }
				}
			    
			}
			
		    
		    
		    
			
			/**Creación del expediente de 'Certificación de obra'**/
			//calculo el número de certificación de obra por el número de expedientes relacionados que son Certificación de obra
			String strQuery = "WHERE NUMEXP_PADRE='" + rulectx.getNumExp() + "' AND RELACION LIKE 'Certificación Obra - %'";
			IItemCollection collectExpRel = entitiesAPI.queryEntities(Constants.TABLASBBDD.SPAC_EXP_RELACIONADOS, strQuery);
					
			//Obtenemos el id del procedimiento 'Certificación obra'
			/*IItemCollection procedimientosDelDepartamento = entitiesAPI.queryEntities(Constants.TABLASBBDD.SPAC_CT_PROCEDIMIENTOS, "WHERE COD_PCD = 'CERTIF-OBRA' ");
			Iterator <IItem> procsIterator = procedimientosDelDepartamento.iterator();
			int idCtProcedimientoNuevo = 0;
			while(procsIterator.hasNext()){
				IItem procs = (IItem) procsIterator.next();
				idCtProcedimientoNuevo = procs.getInt("ID");
			}*/
			
			//Compruebo que el trámite de certificación de obra no sea el final
			IItem itemTram = entitiesAPI.getTask(rulectx.getTaskId());	
			int idTaskTramCtl = (Integer) itemTram.get("ID_TRAM_CTL");
			String codigTramite = TramitesUtil.getCodTram(idTaskTramCtl, cct);
			String relacion = "";
			if(codigTramite.equals("Cert-Final-Obra")) {
				relacion = "Certificación Obra FINAL";
			}
			else{
				relacion = "Certificación Obra - "+(collectExpRel.toList().size()+1)+ " - "+ FechasUtil.getFormattedDate(new Date());
			}
			
			IItem numexpHijo = ExpedientesRelacionadosUtil.iniciaExpedienteRelacionadoHijo(cct, idCtProcedimientoNuevo, rulectx.getNumExp(), relacion, true, null);		
			numexpHijo.set("ASUNTO", relacion);
			numexpHijo.store(cct);
			/**Fin del expediente de 'Certificación de obra'**/
			
			TramitesUtil.cargarObservacionesTramite(cct, true,rulectx.getNumExp(), rulectx.getTaskId(), " Exp.Relacionado: "+numexpHijo.getString("NUMEXP"));
			/**FIN incidencia del contrato**/
			
		}catch(ISPACRuleException e){
			logger.error(e.getMessage(), e);
			throw new ISPACRuleException("Error. ",e);
		} catch (ISPACException e) {
			logger.error(e.getMessage(), e);
			throw new ISPACRuleException("Error. ",e);
		}
		
		return new Boolean(true);
	}

	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

}
