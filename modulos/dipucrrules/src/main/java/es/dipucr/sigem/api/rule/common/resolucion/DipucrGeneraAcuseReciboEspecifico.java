package es.dipucr.sigem.api.rule.common.resolucion;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.messages.Messages;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.Iterator;

import com.sun.star.connection.NoConnectException;
import com.sun.star.lang.DisposedException;

import es.dipucr.sigem.api.rule.common.utils.DocumentosUtil;
import es.dipucr.sigem.api.rule.common.utils.ParticipantesUtil;

public class DipucrGeneraAcuseReciboEspecifico implements IRule {

	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	@SuppressWarnings("rawtypes")
	public Object execute(IRuleContext rulectx) throws ISPACRuleException {
		try{
			// APIs
			IClientContext cct = rulectx.getClientContext();
			IEntitiesAPI entitiesAPI = cct.getAPI().getEntitiesAPI();
			IGenDocAPI gendocAPI = cct.getAPI().getGenDocAPI();
			IProcedureAPI procedureAPI = cct.getAPI().getProcedureAPI();
			
			// Variables
			IItem entityDocument = null;
			int documentTypeId = 0;
			int templateId = 0;
			int taskId = rulectx.getTaskId();
			
			IItem processTask =  entitiesAPI.getTask(rulectx.getTaskId());
			int idTramCtl = processTask.getInt("ID_TRAM_CTL");
			String numExp = rulectx.getNumExp();
	    	int documentId = 0;
	    	Object connectorSession = null;
	    	
	    	String plantilla = DocumentosUtil.getPlantillaDefecto(cct, rulectx.getTaskProcedureId());
	    	String tipoDocumento = ""; 
			
			if(StringUtils.isNotEmpty(plantilla)){
				tipoDocumento = DocumentosUtil.getTipoDocumentoByPlantilla(cct, plantilla);			
			}
			
			IItemCollection taskTpDocCollection = (IItemCollection) procedureAPI.getTaskTpDoc(idTramCtl);
			Iterator it = taskTpDocCollection.iterator();
			while (it.hasNext()) {
				IItem taskTpDoc = (IItem) it.next();
				if ((((String) taskTpDoc.get("CT_TPDOC:NOMBRE")).trim()).equalsIgnoreCase((tipoDocumento.trim()))) {
					documentTypeId = taskTpDoc.getInt("TASKTPDOC:ID_TPDOC");
				}
			}
			if (documentTypeId != 0){
				IItemCollection tpDocsTemplatesCollection = (IItemCollection) procedureAPI.getTpDocsTemplates(documentTypeId);
				if (tpDocsTemplatesCollection == null || tpDocsTemplatesCollection.toList().isEmpty()) {
					throw new ISPACInfo(Messages.getString("error.decretos.acuses.tpDocsTemplates"));
				} else {
					Iterator docs = tpDocsTemplatesCollection.iterator();
					boolean encontrado = false;
					while (docs.hasNext() && !encontrado) {
						IItem tpDocsTemplate = (IItem) docs.next();
						if (((String) tpDocsTemplate.get("NOMBRE")).trim().equalsIgnoreCase(plantilla.trim())) {
							templateId = tpDocsTemplate.getInt("ID");
							encontrado = true;
						}
					}
					if(encontrado){			

						IItemCollection participantes = ParticipantesUtil.getParticipantes(cct, numExp, "(ROL != 'TRAS' OR ROL IS NULL)", "ID");
						if (participantes!=null) {
							Iterator participantesIterator = participantes.iterator();

							while (participantesIterator.hasNext()){
								try {
									connectorSession = gendocAPI.createConnectorSession();
									IItem participante = (IItem) participantesIterator.next();
							        cct.beginTX();
								
									if (participante!=null){
										DocumentosUtil.setParticipanteAsSsVariable(cct, participante);
		
							        	entityDocument = gendocAPI.createTaskDocument(taskId, documentTypeId);
										documentId = entityDocument.getKeyInt();
		
										IItem entityTemplate = gendocAPI.attachTaskTemplate(connectorSession, taskId, documentId, templateId);
		
										String docref = entityTemplate.getString("INFOPAG");
										String sMimetype = gendocAPI.getMimeType(connectorSession, docref);
										entityTemplate.set("EXTENSION", MimetypeMapping.getExtension(sMimetype));
										String templateDescripcion = entityTemplate.getString("DESCRIPCION");
										templateDescripcion = templateDescripcion + " - " + cct.getSsVariable("NOMBRE");
										entityTemplate.set("DESCRIPCION", templateDescripcion);
										entityTemplate.store(cct);
										
										IItem participanteAActualizar = entitiesAPI.getParticipant(participante.getInt("ID"));
										participanteAActualizar.set("ACUSE_GENERADO", "Y");
										participanteAActualizar.store(cct);
								        
										DocumentosUtil.borraParticipanteSsVariable(cct);							        
								    }
								}catch (Exception e) {
									cct.endTX(false);
									
									String message = "exception.documents.generate";
									String extraInfo = null;
									Throwable eCause = e.getCause();
									
									if (eCause instanceof ISPACException) {
										
										if (eCause.getCause() instanceof NoConnectException) {
											extraInfo = "exception.extrainfo.documents.openoffice.off"; 
										}
										else {
											extraInfo = eCause.getCause().getMessage();
										}
									}
									else if (eCause instanceof DisposedException) {
										extraInfo = "exception.extrainfo.documents.openoffice.stop";
									}
									else {
										extraInfo = e.getMessage();
									}			
									throw new ISPACInfo(message, extraInfo);
									
								}finally{
									
									if (connectorSession != null) {
										gendocAPI.closeConnectorSession(connectorSession);
									}
								}
							}//catch
			        	}						
		        	}
		      	}
			}
			cct.endTX(true);
		} catch(Exception e) {
        	throw new ISPACRuleException("No se ha podido generar el acuse de recibo específico", e);
        }
		return true;
	}

	public void cancel(IRuleContext rulectx) throws ISPACRuleException {
		// Empty method
	}

}
