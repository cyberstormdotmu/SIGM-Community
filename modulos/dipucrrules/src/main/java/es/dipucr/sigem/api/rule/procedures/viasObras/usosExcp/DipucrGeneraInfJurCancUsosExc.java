package es.dipucr.sigem.api.rule.procedures.viasObras.usosExcp;

import java.util.Calendar;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;

import org.apache.log4j.Logger;



import es.dipucr.sigem.api.rule.common.documento.DipucrAutoGeneraDocIniTramiteRule;

public class DipucrGeneraInfJurCancUsosExc extends DipucrAutoGeneraDocIniTramiteRule {

	private static final Logger logger = Logger.getLogger(DipucrGeneraInfJurCancUsosExc .class);

	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		logger.info("INICIO - " +this.getClass().getName());

		tipoDocumento = "Informe jurídico";
		plantilla = "Informe Jurídico Cancelación Fianza Autorización Usos Excepcionales";

		logger.info("FIN - "+ this.getClass().getName());
		return true;
	}

	public void setSsVariables(IClientContext cct, IRuleContext rulectx) {
		try {
			cct.setSsVariable("ANIO", ""+Calendar.getInstance().get(Calendar.YEAR));
		} catch (ISPACException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void deleteSsVariables(IClientContext cct) {
		try {
			cct.deleteSsVariable("ANIO");
		} catch (ISPACException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
