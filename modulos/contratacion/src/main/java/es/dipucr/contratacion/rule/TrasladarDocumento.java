package es.dipucr.contratacion.rule;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;

import java.io.File;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import es.dipucr.sigem.api.rule.common.utils.DocumentosUtil;
import es.dipucr.sigem.api.rule.common.utils.MailUtil;


public class TrasladarDocumento  implements IRule {
	
	
	private static final Logger logger = Logger.getLogger(TrasladarDocumento.class);
	public static String nombreDoc = "Subsanaci�n del Informe";
	public static String rol = "TRAS";

	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
		try{
			//----------------------------------------------------------------------------------------------
	        ClientContext cct = (ClientContext) rulectx.getClientContext();
	        IInvesflowAPI invesFlowAPI = cct.getAPI();
	        IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
	        //---------------------------------------------------------------------------------------------- 
	
			String sqlQueryDoc = "ID_TRAMITE="+rulectx.getTaskId()+" AND INFOPAG_RDE != ''";
			logger.warn("sqlQueryDoc "+sqlQueryDoc);
	
			IItemCollection documentos = entitiesAPI.getDocuments(rulectx.getNumExp(), sqlQueryDoc, "");
			
			logger.warn("documentos.toList().size()."+documentos.toList().size());
			
			if (documentos.toList().size() == 0){
				rulectx.setInfoMessage("No existe ning�n documento a trasladar");
				return false;
			}

			//[dipucr-Felipe #707]
			//Obtener Participantes de la propuesta actual, con relaci�n "Trasladado"
	 		String sqlQueryPart = "WHERE ROL= '"+rol+"' AND NUMEXP = '"+rulectx.getNumExp()+"' ORDER BY ID";
			IItemCollection participantes = entitiesAPI.queryEntities("SPAC_DT_INTERVINIENTES", sqlQueryPart);

			if (null == participantes || participantes.toList().size() == 0){
				rulectx.setInfoMessage("Debe rellenar al menos un participante con relaci�n 'TRASLADO' para trasladar el documento");
				return false;
			}
			
			return true;
				
		}catch(Exception e) 
			{
	        	if (e instanceof ISPACRuleException)
	        	{
				    throw new ISPACRuleException(e);
	        	}
	        	throw new ISPACRuleException(e);
	        }
			
		
	}

	public Object execute(IRuleContext rulectx) throws ISPACRuleException 
	{
		try
		{
			/**
			 * Variables que se utilizar�n para insertar en la bbdd los datos 
			 * sobre el env�o correcto o incorrecto del email.
			 * */

			//----------------------------------------------------------------------------------------------
	        IClientContext cct = rulectx.getClientContext();
	        IInvesflowAPI invesFlowAPI = cct.getAPI();
	        IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
	        //---------------------------------------------------------------------------------------------- 
	        logger.warn("INICIO TrasladarSubsanacionInforme. ");
        	
			//Obtener Participantes de la propuesta actual, con relaci�n "Trasladado"
 			String sqlQueryPart = "WHERE ROL= '"+rol+"' AND NUMEXP = '"+rulectx.getNumExp()+"' ORDER BY ID";
			IItemCollection participantes = entitiesAPI.queryEntities("SPAC_DT_INTERVINIENTES", sqlQueryPart);
			
			//Obtener el documento Certificado de acuerdos para anexarlo al email 

			String sqlQueryDoc = "ID_TRAMITE="+rulectx.getTaskId()+" AND INFOPAG_RDE != ''";

			IItemCollection documentos = entitiesAPI.getDocuments(rulectx.getNumExp(), sqlQueryDoc, "");
			
			logger.warn("documentos.toList().size()."+documentos.toList().size());
			
			if (documentos.toList().size() == 1) 
			{
				//Enviar email con el decreto adjunto
				// Fichero a adjuntar
				IItem doc = (IItem)documentos.iterator().next();

				//[eCenpri-Felipe Ticket #306] Algunos servidores interpretan este from como span
				//String cCorreoOrigen = "SIGEM-DCR";
				String cContenido = "<br/>"+doc.getString("NOMBRE")+" del n�mero de expediente: "+rulectx.getNumExp();
				String cAsunto= "[SIGEM] "+doc.getString("NOMBRE")+": "+rulectx.getNumExp();
				
				
				
				String infoPag = doc.getString("INFOPAG_RDE");
				logger.warn("infoPag."+infoPag);
				
				File file = DocumentosUtil.getFile(cct, infoPag, nombreDoc, doc.getString("EXTENSION_RDE"));
				// Para cada participante seleccionado --> enviar email y actualizar el campo ACUERDO_TRASLADADO en la BBDD
				if(participantes != null && participantes.toList()!= null && participantes.toList().size() != 0){
					for (int i=0; i<participantes.toList().size(); i++)
					{
						IItem participante = (IItem) participantes.toList().get(i);
						String emailNotif = participante.getString("DIRECCIONTELEMATICA");
						
						if (emailNotif != null)
						{
							StringTokenizer tokens = new StringTokenizer(emailNotif, ";");
							while (tokens.hasMoreTokens()) 
							{
								String cCorreoDestino = tokens.nextToken();	
								if (participante!=null)
								{
						        	if (!cCorreoDestino.equals("")) 
						        	{
						        		logger.warn("direccion telematica. "+cCorreoDestino);
							        	// Confeccionar el email
										MailUtil.enviarCorreo(rulectx, cCorreoDestino, cAsunto, cContenido, file);	
						        	}
								}
							}
						}
					}
				}
				file.delete();
			}
			logger.warn("FIN TrasladarSubsanacionInforme. ");
			
			return new Boolean(true);
		}
		catch(Exception e) 
		{
        	if (e instanceof ISPACRuleException)
        	{
			    throw new ISPACRuleException(e);
        	}
        	throw new ISPACRuleException(e);
        }
	}
		
	public void cancel(IRuleContext rulectx) throws ISPACRuleException {

	}
}
