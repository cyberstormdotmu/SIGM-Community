package es.dipucr.sigem.api.rule.procedures.cdj.convocatorias;


import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.Iterator;

import org.apache.log4j.Logger;

import es.dipucr.sigem.api.rule.common.utils.ExpedientesRelacionadosUtil;
import es.dipucr.sigem.api.rule.common.utils.ExpedientesUtil;
import es.dipucr.sigem.api.rule.procedures.ConstantesString;
import es.dipucr.sigem.api.rule.procedures.ConstantesSubvenciones;
import es.dipucr.sigem.api.rule.procedures.Constants;
import es.dipucr.sigem.api.rule.procedures.SubvencionesUtils;

public class DipucrCambiaEstadoJIaJF1PConvDosProyectos implements IRule{
    private static final Logger LOGGER = Logger.getLogger(DipucrCambiaEstadoJIaJF1PConvDosProyectos.class);
    
    protected String estadoIni;
    protected String estadoFin;

    public boolean init(IRuleContext rulectx) throws ISPACRuleException {
        estadoIni = "JI";
        estadoFin = "JF1P";
        return true;
    }

    public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
        return true;
    }

    
    public Object execute(IRuleContext rulectx) throws ISPACRuleException{
        String numexp = rulectx.getNumExp();

        try{
            //----------------------------------------------------------------------------------------------
            ClientContext cct = (ClientContext) rulectx.getClientContext();
            IInvesflowAPI invesFlowAPI = cct.getAPI();
            IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
            //----------------------------------------------------------------------------------------------
            LOGGER.info(ConstantesString.INICIO +this.getClass().getName());
            
            String strQuery = ConstantesString.WHERE + ExpedientesRelacionadosUtil.NUMEXP_PADRE + " = '" + numexp + "' ";
            IItemCollection expRelCol = entitiesAPI.queryEntities(Constants.TABLASBBDD.SPAC_EXP_RELACIONADOS, strQuery);
            Iterator<?> expRelIt = expRelCol.iterator();                  
            while (expRelIt.hasNext()){
                IItem expRel = (IItem)expRelIt.next();
                
                String numexpHijo = expRel.getString(ExpedientesRelacionadosUtil.NUMEXP_HIJO);

                IItemCollection resolucionesCollection = entitiesAPI.queryEntities(ConstantesSubvenciones.DatosResolucion.NOMBRE_TABLA, ConstantesString.WHERE + " NUMEXP = '" + numexpHijo + "'");
                   for(Object oResol : resolucionesCollection.toList()){
                       IItem resolucion = (IItem) oResol;
                       String proyecto1 = SubvencionesUtils.getString(resolucion, ConstantesSubvenciones.DatosResolucion.PROYECTO1);
                       String justificado1 = SubvencionesUtils.getString(resolucion, ConstantesSubvenciones.DatosResolucion.JUSTIF_PROY_1);
                       
                       String proyecto2 = SubvencionesUtils.getString(resolucion, ConstantesSubvenciones.DatosResolucion.PROYECTO2);
                       String justificado2 = SubvencionesUtils.getString(resolucion, ConstantesSubvenciones.DatosResolucion.JUSTIF_PROY_2);
                       
                    if(compruebaSiJustificado(proyecto1, justificado1) ||compruebaSiJustificado(proyecto2, justificado2)){
                        IItem expHijo = ExpedientesUtil.getExpediente(cct, numexpHijo); 
                        
                        if(estadoIni.equals(expHijo.get(ExpedientesUtil.ESTADOADM))){
                            
                            expHijo.set(ExpedientesUtil.ESTADOADM, estadoFin);
                            expHijo.store(cct);
                        }
                    }
                }
            }               
            LOGGER.info(ConstantesString.FIN +this.getClass().getName());
            return true;
            
        } catch(Exception e) {
            LOGGER.error("No se ha podido obtener la lista de solicitudes relacionadas del expediente: " + numexp + ". " + e.getMessage(), e);
            throw new ISPACRuleException("No se ha podido obtener la lista de solicitudes relacionadas del expediente: " + numexp + ". " + e.getMessage(), e);
        }
    }
    
    private boolean compruebaSiJustificado(String proyecto, String justificado){
        return StringUtils.isNotEmpty(proyecto) && StringUtils.isNotEmpty(justificado) && "NO".equals(justificado);
    }

    public void cancel(IRuleContext rulectx) throws ISPACRuleException {
        //No se da nunca este caso
    }

}