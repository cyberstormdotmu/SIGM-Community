package ieci.tdw.ispac.api.rule.procedures.subvenciones;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.utils.FileUtils;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import es.dipucr.sigem.api.rule.common.utils.DocumentosUtil;

public class InitTaskAcuerdoDecretoRule implements IRule {
    
    private static final Logger LOGGER = Logger.getLogger(InitTaskAcuerdoDecretoRule.class);

    protected String strQueryDocumentos = "";
    protected String strEntidad = "";
    
    public boolean init(IRuleContext rulectx) throws ISPACRuleException{
        return true;
    }

    public boolean validate(IRuleContext rulectx) throws ISPACRuleException{
        return true;
    }

    public Object execute(IRuleContext rulectx) throws ISPACRuleException {
        
        try {
            //----------------------------------------------------------------------------------------------
            IClientContext cct =  rulectx.getClientContext();
            IInvesflowAPI invesFlowAPI = cct.getAPI();
            IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
            IGenDocAPI genDocAPI = invesFlowAPI.getGenDocAPI();
            //----------------------------------------------------------------------------------------------

            //Actualiza el campo estado de la entidad
            //de modo que permita mostrar los enlaces para crear Propuesta/Decreto
            String numexp = rulectx.getNumExp();
            IItemCollection col = entitiesAPI.getEntities(strEntidad, numexp);
            Iterator<?> it = col.iterator();
            
            if (it.hasNext()){
                IItem entidad = (IItem)it.next();
                entidad.set("ESTADO", "Inicio");
                entidad.store(cct);
            }
            
            //Crea un ZIP con la documentación para la propuesta
            //--------------------------------------------------
    
            //Obtenemos los documentos a partir de su nombre
            List<IItem> filesConcatenate = new ArrayList<IItem>();
            int i=0;
            IItemCollection documentsCollection = entitiesAPI.getDocuments(rulectx.getNumExp(), strQueryDocumentos, DocumentosUtil.FDOC + " DESC");
            it = documentsCollection.iterator();
            IItem itemDoc = null;
            
            while (it.hasNext() && i<3) {
                itemDoc = (IItem)it.next();
                filesConcatenate.add(itemDoc);
                i++;
            }
            
            //Obtenemos el id del tipo de documento de "Contenido de la propuesta"
            String strQuery = "WHERE NOMBRE = 'Contenido de la propuesta'";
            IItemCollection typesDocumentCollection = entitiesAPI.queryEntities(SpacEntities.SPAC_CT_TPDOC, strQuery);
            int idTypeDocument = 0;
            
            if (typesDocumentCollection!=null && documentsCollection.next()){
                idTypeDocument = ((IItem)typesDocumentCollection.iterator().next()).getInt("ID");
            }
            
            if (idTypeDocument == 0){
                throw new ISPACInfo("Error al obtener el tipo de documento.");
            }
    
            // Crear el zip con los documentos
            //    http://www.manual-java.com/codigos-java/compresion-decompresion-archivos-zip-java.html
            File zipFile = DocumentosUtil.createDocumentsZipFileInfoPag(genDocAPI, filesConcatenate);
            
            //Generamos el documento zip
            String sExtension = "zip";
            String sName = "Contenido de la propuesta";//Campo descripción del documento
            String sMimeType = MimetypeMapping.getMimeType(sExtension);
            
            if (sMimeType == null) {
                throw new ISPACInfo("Error al obtener el tipo Mime");
            }
            
            int keyId = 0;
            IItem entityDocument = null;
            int taskId = rulectx.getTaskId(); 
            int currentId = taskId;
                    
            // Ejecución en un contexto transaccional
            boolean bCommit = false;
            
            try {
                // Abrir transacción para que no se pueda generar un documento sin fichero
                cct.beginTX();
                entityDocument = genDocAPI.createTaskDocument(currentId, idTypeDocument);
                keyId = entityDocument.getKeyInt();
                // Establecer la extensión del documento para mostrar
                // un icono descriptivo del tipo de documento en la lista de documentos
                entityDocument.set(DocumentosUtil.EXTENSION, sExtension);
                entityDocument.store(cct);
                
                InputStream in = new FileInputStream(zipFile);
                int length = (int)zipFile.length();
                Object connectorSession = null;
            
                try {
                    
                    connectorSession = genDocAPI.createConnectorSession();
                    genDocAPI.attachTaskInputStream(connectorSession, currentId, keyId, in, length, sMimeType, sName);
                
                } finally {
                    if (connectorSession != null) {
                        genDocAPI.closeConnectorSession(connectorSession);
                    }
                }
                // Si ha sido correcto se hace commit de la transacción
                bCommit = true;
                
            } catch (Exception e) {
                LOGGER.info("No hace nada, solo captura el posible error: " + e.getMessage(), e);

                // Si se produce algún error
                throw new ISPACInfo("Error al generar el documento.");
                
            } finally {
                cct.endTX(bCommit);
            }
                
            // Eliminar el zip
            FileUtils.deleteFile(zipFile);
            
        } catch(ISPACRuleException e) {
            throw new ISPACRuleException(e);
        
        } catch(Exception e) {
            throw new ISPACRuleException("No se ha podido generar la propuesta de concesión.",e);
        }
        
        return Boolean.TRUE;
    }

    public void cancel(IRuleContext rulectx) throws ISPACRuleException{
        //No se da nunca este caso
    }
}
