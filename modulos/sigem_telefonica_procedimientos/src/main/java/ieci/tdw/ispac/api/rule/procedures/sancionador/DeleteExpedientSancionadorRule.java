package ieci.tdw.ispac.api.rule.procedures.sancionador;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;

/**
 * 
 * @author teresa
 * @date 30/03/2010
 * @prop�sito Eliminar las transiciones del expediente cuando �ste es cerrado
 */
public class DeleteExpedientSancionadorRule implements IRule {
    
    public boolean init(IRuleContext rulectx) throws ISPACRuleException {
        return true;
    }

    public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
        
        try{
            //----------------------------------------------------------------------------------------------
            IClientContext cct =  rulectx.getClientContext();
            IInvesflowAPI invesFlowAPI = cct.getAPI();
            IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
            //----------------------------------------------------------------------------------------------

            String numExp = rulectx.getNumExp();
            String strQuery = "WHERE NUMEXP='" + numExp + "'";
               //int nTransEliminar = entitiesAPI.deleteEntities("TSOL_TRANSICIONES", strQuery);
            entitiesAPI.deleteEntities("TSOL_TRANSICIONES", strQuery);

            
        }catch(ISPACException e){
            throw new ISPACRuleException(e);
        }
        
        return true;
    }

    public Object execute(IRuleContext rulectx) throws ISPACRuleException {
        return null;
    }

    public void cancel(IRuleContext rulectx) throws ISPACRuleException {
        // No se da nunca este caso
    }
    
}
