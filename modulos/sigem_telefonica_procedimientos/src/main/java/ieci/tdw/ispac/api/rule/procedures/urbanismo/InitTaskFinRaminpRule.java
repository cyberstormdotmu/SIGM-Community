package ieci.tdw.ispac.api.rule.procedures.urbanismo;

import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.rule.IRuleContext;

public class InitTaskFinRaminpRule extends InitTaskResolucionRaminpRule {

    public boolean init(IRuleContext rulectx) throws ISPACRuleException {
        strEntidad = "URB_LIC_RAMINP";
        strQueryDocumentos = 
            "NOMBRE = 'Providencia' OR " +
            "NOMBRE = 'Informe jur�dico' OR " +        
            "NOMBRE = 'Informe t�cnico' OR " +
            "NOMBRE = 'Informe Jefe Local de Sanidad' OR " +        
            "NOMBRE = 'Informe comisi�n prov. saneamiento'";        
        return true;
    }
}
