package es.dipucr.sigem.ws.common.firma;

import org.apache.axis.AxisFault;
import org.apache.axis.client.Call;
import org.apache.axis.handlers.soap.SOAPService;
import org.apache.log4j.Logger;

import es.dipucr.sigem.api.rule.common.serviciosWeb.ServiciosWebSecurityConfiguration;

public class SSLUtilities {


	protected static final Logger LOGGER = Logger.getLogger(SSLUtilities.class);
	
	
	public static void firmarPeticion(Call _call){
		try {
			ServiciosWebSecurityConfiguration wsConfig = ServiciosWebSecurityConfiguration.getInstance();
            // Creacion del manejador que securizara la peticion
            ClientHandler sender = new ClientHandler(wsConfig);
			
            SOAPService soapService=new SOAPService(sender, null, null);
            soapService.setUse(org.apache.axis.constants.Use.LITERAL);
            soapService.getServiceDescription().setTypeMapping(_call.getTypeMapping());
            soapService.getTypeMappingRegistry().register("", _call.getTypeMapping());
            _call.setSOAPService(soapService);
        } catch (Exception e) {

        	LOGGER.error("Error al firmar la petici�n - "+e.getMessage(), e);
        }
	}

	/**
	 * @param _call
	 * @throws AxisFault
	 */
	public static void imprimirErrorEnvio(org.apache.axis.client.Call _call) throws AxisFault {
		String requestXML = _call.getMessageContext().getRequestMessage().getSOAPPartAsString();
		String responseXML = _call.getMessageContext().getResponseMessage().getSOAPPartAsString();
		LOGGER.error("REQUEST");
		LOGGER.error(requestXML);
		LOGGER.error("RESPONSE");
		LOGGER.error(responseXML);
	}
	
	
	/**
	 * @param _operationClient
	 * @throws AxisFault
	 */
	public static void imprimirErrorEnvioAxis2(org.apache.axis2.client.OperationClient _operationClient) throws AxisFault {
		
		try {
			String requestXML = "";
			if(_operationClient!=null && _operationClient.getMessageContext("Out")!=null && _operationClient.getMessageContext("Out").getEnvelope()!=null){
				requestXML = _operationClient.getMessageContext("Out").getEnvelope().toString();
			}
			String responseXML = "";
			if(_operationClient!=null && _operationClient.getMessageContext("In")!=null && _operationClient.getMessageContext("In").getEnvelope()!=null){
				responseXML = _operationClient.getMessageContext("In").getEnvelope().toString(); 
			}
			
			LOGGER.error("REQUEST");
			LOGGER.error(requestXML);
			LOGGER.error("RESPONSE");
			LOGGER.error(responseXML);
		} catch (org.apache.axis2.AxisFault e) {
			LOGGER.error("Error al firmar la petici�n - "+e.getMessage(), e);
		} 
		
	}
}
