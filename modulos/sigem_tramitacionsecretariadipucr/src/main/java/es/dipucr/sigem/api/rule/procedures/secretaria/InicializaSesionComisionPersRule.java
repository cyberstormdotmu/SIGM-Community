package es.dipucr.sigem.api.rule.procedures.secretaria;

import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.rule.IRuleContext;

public class InicializaSesionComisionPersRule extends InicializaSesionRule {
	
	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		STR_Organo	= "COMI";
		STR_Area	= "0060";
		return true;
	}
	
}
