package es.dipucr.tecdoc.sgm.ct.struts.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IProcedure;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.api.item.ITask;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowDocumentAction extends Action {
	
	private static final Logger logger = Logger.getLogger(ShowDocumentAction.class);

    public ActionForward executeAction(ActionMapping mapping, 
    								   ActionForm form,
    								   HttpServletRequest request, 
    								   HttpServletResponse response,
    								   SessionAPI session) throws Exception {
    	
        String sDocument = request.getParameter("document");
        if (sDocument == null) {
            return null;
        }

        IInvesflowAPI invesflowAPI = session.getAPI();
        IEntitiesAPI entityAPI = invesflowAPI.getEntitiesAPI();

        IItem entity = entityAPI.getDocument(Integer.parseInt(sDocument));
        if (entity == null) {
            return null;
        }
        
        // Comprobar si el usuario tiene responsabilidad para ver el documento
        isResponsible(invesflowAPI, entity, session.getClientContext().getRespId());

        String docref = getDocRef(entity);
        if (docref != null) {

        	IGenDocAPI genDocAPI = invesflowAPI.getGenDocAPI();
        	
			Object connectorSession = null;
			try {
				connectorSession = genDocAPI.createConnectorSession();

	        	String mimetype = genDocAPI.getMimeType(connectorSession, docref);
	            ServletOutputStream out = response.getOutputStream();
		    	response.setHeader("Pragma", "public");
		    	response.setHeader("Cache-Control", "max-age=0");
	            response.setContentType(mimetype);
	            response.setHeader("Content-Transfer-Encoding", "binary");
	            
	            String extension = getDocExtension(entity);
	            if (StringUtils.isBlank(extension)) {
	            	response.setHeader("Content-Disposition", "attachment; filename=\"" + sDocument + "\"");
	            } else {
	            	response.setHeader("Content-Disposition", "inline; filename=\"" + sDocument + "." + extension + "\"");
	            }
	            
	            response.setContentLength(genDocAPI.getDocumentSize(connectorSession, docref));
	            try {
	            	genDocAPI.getDocument(connectorSession, docref, out);
	            }
	            catch(Exception e){
	            	//Se saca el mensaje de error en la propia ventana, que habra sido lanzada con un popup
	            	response.setContentType("text/html");
	            	out.write(e.getCause().getMessage().getBytes());
	            }
	            finally{
	            	out.close();
	            }
			}
			finally {
				if (connectorSession != null) {
					genDocAPI.closeConnectorSession(connectorSession);
				}
			}
        }
        else {
        	logger.error("No existe el documento");
        }

        return null;
    }
    
    protected String getDocRef(IItem docItem) throws Exception {
    	
    	// Obtener el documento del repositorio de documentos electronicos (documento firmado)
    	String docrefRDE = docItem.getString("INFOPAG_RDE");
    	if (StringUtils.isNotEmpty(docrefRDE)) {
    		return docrefRDE;
    	}
    	
    	return docItem.getString("INFOPAG");
    }

    protected String getDocExtension(IItem docItem) throws Exception {
    	
    	// Obtener la extensi�n del documento del repositorio de documentos electronicos
    	String rdeRef = docItem.getString("INFOPAG_RDE");
    	if (StringUtils.isNotBlank(rdeRef)) {
    		return docItem.getString("EXTENSION_RDE");
    	} else {
    		return docItem.getString("EXTENSION");
    	}
    }
    
    protected void isResponsible(IInvesflowAPI invesflowAPI,
    							 IItem entity,
    							 String uid) throws Exception {

    	IItem pcdStage = invesflowAPI.getProcedureStage(entity.getInt("ID_FASE_PCD"));
    	IProcedure procedure = invesflowAPI.getProcedure(pcdStage.getInt("ID_PCD"));
    	
    	if (procedure.getInt("TIPO") == IProcedure.PROCEDURE_TYPE) { // Procedimiento
    		
        	// Documento a nivel de tr�mite
            int taskId = entity.getInt("ID_TRAMITE");
            if (taskId > 0) {

    	    	// Comprobar responsabilidad del tr�mite
            	checkTaskReponsible(invesflowAPI, entity, uid);
            	
            } else {
            	
	        	// Comprobar responsabilidad de la fase
	        	checkStageReponsible(invesflowAPI, entity, uid);
            }
            
    	} else { // Subproceso

        	// Comprobar responsabilidad de la actividad
        	checkStageReponsible(invesflowAPI, entity, uid);
    	}
    }
    
    private void checkTaskReponsible(IInvesflowAPI invesflowAPI, IItem entity, String uid) throws Exception {
    	
        // Comprobamos la responsabilidad para saber si el usuario tiene permisos para consultar el documento, 
    	// siempre y cuando el tramite este abierto.
    	// Si el tramite esta cerrado ya no se puede saber si el usuario conectado puede consultar el documento o no,
    	// porque en la tabla de documentos no se almacena datos de la responsabilidad, unicamente del autor, 
    	// con lo cual no se puede determinar permisos de consulta.
    	ITask task = null;
    	try {
    		task = invesflowAPI.getTask(entity.getInt("ID_TRAMITE"));
		} catch(Exception e) {}
		
    	if (task != null) {
        	String sUID = task.getString("ID_RESP");
		  	if (!((invesflowAPI.getWorkListAPI().isInResponsibleList(sUID, ISecurityAPI.SUPERV_ANY)) ||
		  		  (invesflowAPI.getSignAPI().isResponsible(entity.getKeyInt(), uid)))) {
		  		logger.warn("exception.documents.noResponsability");
		  	}
		}
    }
    
    private void checkStageReponsible(IInvesflowAPI invesflowAPI, IItem entity, String uid) throws Exception {
    	
        // Documento a nivel de fase/actividad
        int stageId = entity.getInt("ID_FASE");
        if (stageId > 0) {
	
	        // Comprobamos la responsabilidad para saber si el usuario tiene permisos para consultar el documento, 
	    	// siempre y cuando la fase este abierta.
	    	// Si la fase esta cerrada ya no se puede saber si el usuario conectado puede consultar el documento o no,
	    	// porque en la tabla de documentos no se almacena datos de la responsabilidad, unicamente del autor, 
	    	// con lo cual no se puede determinar permisos de consulta.
	    	IStage stage = null;
	    	try {
	    		stage = invesflowAPI.getStage(entity.getInt("ID_FASE"));
			} catch(Exception e) {}
			
	    	if (stage != null) {
	        	String sUID = stage.getString("ID_RESP");
			  	if (!((invesflowAPI.getWorkListAPI().isInResponsibleList(sUID, ISecurityAPI.SUPERV_ANY)) ||
			  		  (invesflowAPI.getSignAPI().isResponsible(entity.getKeyInt(), uid)))) {
			  		logger.warn("exception.documents.noResponsability");
			  	}
			}
        }
    }
    

}
