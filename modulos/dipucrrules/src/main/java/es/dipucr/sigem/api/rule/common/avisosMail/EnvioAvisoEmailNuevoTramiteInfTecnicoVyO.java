package es.dipucr.sigem.api.rule.common.avisosMail;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.rule.IRuleContext;

import org.apache.log4j.Logger;

import es.dipucr.sigem.api.rule.common.convocatorias.DipucrEnviaDocEmailConAcuse;

public class EnvioAvisoEmailNuevoTramiteInfTecnicoVyO extends DipucrEnviaDocEmailConAcuse{

	/**
	 * [Ticket #486 TCG](SIGEM creaci�n m�todo gen�rico para el env�o de avisos por mail)
	 * [Manu Ticket #1181] Procedimiento de Recursos Administrativos de Servicios Sociales
	 */
	protected static final Logger logger = Logger.getLogger(EnvioAvisoEmailNuevoTramiteInfTecnicoVyO.class);
	
	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		String numexp = "";
		try{
			numexp = rulectx.getNumExp();
				
			VAR_EMAILS = "marcelo_navarro@dipucr.es";
			nombreNotif = "Marcelo Navarro Casas";
			contenido = "Tiene un nuevo Tr�mite T�cnico en SIGEM a visualizar con el n�mero de expediente " + numexp;
			asunto = "[AL-SIGM] Tiene un nuevo Tr�mite T�cnico en SIGEM a visualizar con el n�mero de expediente " + numexp;
			
			conDocumento = false;
		} catch (ISPACException e) {
			 logger.error("Error al enviar email a: " + nombreNotif + ", en el expediente: " + numexp + ". "+ e.getMessage(), e);
		}
		
		return true;
	}	 
}
