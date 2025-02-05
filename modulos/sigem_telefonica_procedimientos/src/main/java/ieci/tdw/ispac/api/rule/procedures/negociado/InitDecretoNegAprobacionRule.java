package ieci.tdw.ispac.api.rule.procedures.negociado;

import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.rule.IRuleContext;

/**
 * 
 * @author teresa
 * @date 17/11/2009
 * @propósito Inicializa el expediente de decreto asociado al expediente de Convocatoria Negociado de contratación actual.
 */
public class InitDecretoNegAprobacionRule extends InitDecretoNegRule {

    public boolean init(IRuleContext rulectx) throws ISPACRuleException {
        strEntidad = "SGN_NEGOCIADO";
        strExtracto = "Aprobación de convocatoria de contratación";
        return true;
    }
    
}
