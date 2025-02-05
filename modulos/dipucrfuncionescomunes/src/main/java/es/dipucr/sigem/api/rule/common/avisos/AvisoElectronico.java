package es.dipucr.sigem.api.rule.common.avisos;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import org.apache.log4j.Logger;

import es.dipucr.sigem.api.rule.common.utils.AvisosUtil;

public class AvisoElectronico implements IRule 
{
	private Logger logger = Logger.getLogger(AvisoElectronico.class);
	public String sResponsable = "";
	public String mensaje = "";
	
	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}
	
	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}
	
	public Object execute(IRuleContext rulectx) throws ISPACRuleException {		
		try{
			
			//Generamos el aviso
			generarAvisoAnuncio(rulectx, sResponsable);
			
		}
		catch (Exception e) {
			
			logger.error("Se produjo una excepción"+e.getMessage(), e);
			throw new ISPACRuleException("Error. "+e.getMessage(),e);
		}
		
		return new Boolean(true);
	}

	public void cancel(IRuleContext rulectx) throws ISPACRuleException {
	}
	
	
	private void generarAvisoAnuncio(IRuleContext rulectx, String sResponsable)
			throws ISPACException
	{
		
		IClientContext ctx = rulectx.getClientContext();
		IInvesflowAPI invesflowAPI = ctx.getAPI();
		IEntitiesAPI entitiesAPI = invesflowAPI.getEntitiesAPI();
		
		if (!StringUtils.isEmpty(sResponsable)){
			String numexp = rulectx.getNumExp();		
			int processId = invesflowAPI.getProcess(numexp).getInt("ID");
	//		ITask task = invesflowAPI.getTask(idTask);
	//		String nombreTramite = task.getString("NOMBRE");
			IItem itemExpediente = entitiesAPI.getExpedient(numexp);
			String asunto = itemExpediente.getString("ASUNTO");
			//Enlace que dirige directamente al tr�mite
			String message = "<a href=\"/SIGEM_TramitacionWeb/showTask.do?taskId=" + rulectx.getTaskId() +
				"\" class=\"displayLink\">" + mensaje + "</a><br/>Asunto: " + asunto;
			
			AvisosUtil.generarAviso(entitiesAPI, processId, numexp, message, sResponsable, ctx);
		}
	}
}

