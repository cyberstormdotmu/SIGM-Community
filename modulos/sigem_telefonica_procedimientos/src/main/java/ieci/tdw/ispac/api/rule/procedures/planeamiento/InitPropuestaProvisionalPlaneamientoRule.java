package ieci.tdw.ispac.api.rule.procedures.planeamiento;

import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.rule.IRuleContext;

/**
 * 
 * @author teresa
 * Inicia propuesta aprobación provisional Procedimiento Aprobación y Modificación del Plan de Ordenación Municipal
 *
 */

public class InitPropuestaProvisionalPlaneamientoRule extends InitPropuestaPlaneamientoRule {

    public boolean init(IRuleContext rulectx) throws ISPACRuleException {
        strEntidad = "PLAN_POM";
        strExtracto = "Resolución de aprobación provisional del procedimiento de Aprobación y Modificación del Plan de Ordenación Municipal";
        return true;
    }
}
