package ieci.tdw.ispac.api.rule.procedures.secretaria;

import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.rule.IRuleContext;

public class GenerateActaMesaConDebatesRule extends GenerateActaBaseRule {

    public boolean init(IRuleContext rulectx) throws ISPACRuleException {

        strPrefijo                 = "Borrador de Acta de Mesa";
        String strComun                = "Borrador de Acta de Pleno";
        strNombreTramite           = strPrefijo + " con debates";
        strNombreCabecera          = strPrefijo + " - Cabecera";
        strNombrePie               = strPrefijo + " - Pie";
        strNombreCabeceraPropuesta = strComun + " - Propuesta - Cabecera";
        strNombrePiePropuesta      = strComun + " - Propuesta - Pie con debate";
        strNombreRuegos            = strComun + " - Ruegos y preguntas";
        return true;
    }
}

