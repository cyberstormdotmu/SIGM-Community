package es.dipucr.contratacion.rule.entidad;

import java.util.Iterator;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.context.ClientContext;

import org.apache.log4j.Logger;

public class VisualizarContratacionDocAdicionalTramite implements IRule{
	
	public static final Logger logger = Logger.getLogger(VisualizarContratacionDocAdicionalTramite.class);

	public void cancel(IRuleContext rulectx) throws ISPACRuleException {

	}

	@SuppressWarnings("unchecked")
	public Object execute(IRuleContext rulectx) throws ISPACRuleException {
		
		try{
			// --------------------------------------------------------------------
			ClientContext cct = (ClientContext) rulectx.getClientContext();
			IInvesflowAPI invesFlowAPI = cct.getAPI();
			IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
			// --------------------------------------------------------------------		
			
			String sQuery = "WHERE NUMEXP='"+rulectx.getNumExp()+"' AND TRAMITE="+rulectx.getTaskId()+"";
			logger.warn("sQuery "+sQuery);
			IItemCollection coleccion = entitiesAPI.queryEntities("CONTRATACION_DOC_ADIC_LISTAD", sQuery);
			Iterator<IItem> itCollection = coleccion.iterator();
			if(itCollection.hasNext()){
				IItem nuevoDoc = itCollection.next();
				logger.warn(nuevoDoc.get("TIPO_DOC"));
				IItem contratacionDocAdicional = rulectx.getItem();
				contratacionDocAdicional.set("TIPO_DOC", nuevoDoc.get("TIPO_DOC"));
			}
			else{
				IItem contratacionDocAdicional = rulectx.getItem();
				contratacionDocAdicional.set("TIPO_DOC", "");
			}
			
			
			
		}catch(ISPACRuleException e){
			logger.error(e.getMessage(), e);
			throw new ISPACRuleException("Error. ",e);
		} catch (ISPACException e1) {
			logger.error(e1.getMessage(), e1);
			throw new ISPACRuleException("Error. ",e1);
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
