package ieci.tdw.ispac.api.rule.procedures.secretaria;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import es.dipucr.sigem.api.rule.common.utils.SecretariaUtil;

public class GenerateExpedienteParaUrgencia implements IRule {

    public boolean init(IRuleContext rulectx) throws ISPACRuleException{
        return true;
    }

    public boolean validate(IRuleContext rulectx) throws ISPACRuleException{
        return true;
    }

    public Object execute(IRuleContext rulectx) throws ISPACRuleException{
        try{
            //----------------------------------------------------------------------------------------------
            IClientContext cct =  rulectx.getClientContext();
            IInvesflowAPI invesFlowAPI = cct.getAPI();
            IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
            ITXTransaction tx = invesFlowAPI.getTransactionAPI();
            //----------------------------------------------------------------------------------------------
            
            //Creación del expediente
            String strQuery = "WHERE NOMBRE='Propuesta'";
            IItemCollection coll = entitiesAPI.queryEntities("SPAC_CT_PROCEDIMIENTOS", strQuery);
            Iterator<?> it = coll.iterator();
            int nProcedure = 0;
            IItem proc = null;
            int n;
            String codPcd = "";
            
            while (it.hasNext()) {
                proc = (IItem)it.next();
                n = proc.getInt("ID");
                
                if ( n > nProcedure ) {
                    nProcedure = n;
                    codPcd = proc.getString("COD_PCD");
                }
            }
            
            Map<String, String> params = new HashMap<String, String>();
            params.put("COD_PCD", codPcd);
            int idExp = tx.createProcess(nProcedure, params);
            IProcess process = invesFlowAPI.getProcess(idExp);
            String numexp = process.getString("NUMEXP");            
            IItem iProp = entitiesAPI.createEntity("SECR_PROPUESTA", numexp);

            //Inicialización de datos de la propuesta
            IItem urgencia = rulectx.getItem();
            iProp.set("ORIGEN", urgencia.getString("ORIGEN"));
            iProp.set("DESTINO", urgencia.getString("DESTINO"));
            iProp.set("PRIORIDAD", urgencia.getString("PRIORIDAD"));
            iProp.set("PRIORIDAD_MOTIVO", urgencia.getString("PRIORIDAD_MOTIVO"));
            iProp.set("ORDEN", urgencia.getString("ORDEN"));
            iProp.set("EXTRACTO", urgencia.getString("EXTRACTO"));
            iProp.set("NOTAS", urgencia.getString("NOTAS"));
            iProp.set("DEBATE", urgencia.getString("DEBATE"));
            iProp.set("ACUERDOS", urgencia.getString("ACUERDOS"));
            iProp.set("DICTAMEN", urgencia.getString("DICTAMEN"));
            iProp.set("N_SI", urgencia.getString("N_SI"));
            iProp.set("N_NO", urgencia.getString("N_NO"));
            iProp.set("N_ABS", urgencia.getString("N_ABS"));
            iProp.store(cct);
            
            //Campo Asunto del expediente de la propuesta
            coll = entitiesAPI.getEntities("SPAC_EXPEDIENTES", numexp);
            it = coll.iterator();
            
            if (it.hasNext()) {
                IItem iExp = (IItem)it.next();
                iExp.set("ASUNTO", "Urgencia");
                String strEstado = SecretariaUtil.getEstadoAdmPropuesta(rulectx);
                iExp.set("ESTADOADM", strEstado);
                iExp.store(cct);
            }
            
            //Relación de ambos expediente mediante numexp_origen en urgencia
            urgencia.set("NUMEXP_ORIGEN", iProp.getString("NUMEXP"));
            
            return Boolean.TRUE;
            
        } catch(ISPACRuleException e) {
            throw new ISPACRuleException(e);
        
        } catch(Exception e) {
            throw new ISPACRuleException("No se ha podido crear la propuesta",e);
        }
    }

    public void cancel(IRuleContext rulectx) throws ISPACRuleException{
        //No se da nunca este caso
    }
}