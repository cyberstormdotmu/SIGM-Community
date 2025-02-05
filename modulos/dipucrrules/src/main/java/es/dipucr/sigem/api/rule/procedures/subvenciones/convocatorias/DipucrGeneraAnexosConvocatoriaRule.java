package es.dipucr.sigem.api.rule.procedures.subvenciones.convocatorias;

import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;

import org.apache.log4j.Logger;

import es.dipucr.sigem.api.rule.common.documento.DipucrAutoGeneraDocIniTramiteRule;

public class DipucrGeneraAnexosConvocatoriaRule extends DipucrAutoGeneraDocIniTramiteRule{

	private static final Logger logger = Logger.getLogger(DipucrGeneraAnexosConvocatoriaRule.class);	

	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		logger.warn("INICIO - DipucrGeneraAnexosConvocatoriaRule");
		
		tipoDocumento = "Anuncio de Convocatoria - Anexos";
		plantilla = "Anexos Subvenci�n";
		
		logger.warn("FIN - DipucrGeneraAnexosConvocatoriaRule");
		return true;
	}
	
	public void setSsVariables(IClientContext cct, IRuleContext rulectx) {
	}

	public void deleteSsVariables(IClientContext cct) {	
	}
}