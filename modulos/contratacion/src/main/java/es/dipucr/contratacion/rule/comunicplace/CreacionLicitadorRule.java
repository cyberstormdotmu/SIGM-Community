package es.dipucr.contratacion.rule.comunicplace;

import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.rule.IRuleContext;
import es.dipucr.contratacion.common.MandarPublicacionPLACSPRule;

public class CreacionLicitadorRule extends MandarPublicacionPLACSPRule{	

	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		OPERACIONPLACE = "CREACION_LICITADOR";
		return true;
	}
}
