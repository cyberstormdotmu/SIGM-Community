package ieci.tdw.ispac.api.rule.procedures.decretos;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;

/**
 * 
 * @author diezp
 * @proposito Calcula y almacena la informaci�n del Decreto (a�o, fecha de creci�n, presidente que lo ha firmado), 
 *               al terminar el tr�mite Preparaci�n de las firmas
 * 
 */
public class CalculateDataDecretoRule implements IRule {
    
    private static final Logger LOGGER = Logger.getLogger(CalculateDataDecretoRule.class);

    public boolean init(IRuleContext rulectx) throws ISPACRuleException {
        return true;
    }

    public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
        return true;
    }

    public Object execute(IRuleContext rulectx) throws ISPACRuleException {
        try{
            try{
                
                //Comprobar si el campo motivo rechazo tiene asignado un valor
                
                //----------------------------------------------------------------------------------------------
                IClientContext cct =  rulectx.getClientContext();
                IInvesflowAPI invesFlowAPI = cct.getAPI();
                IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
                //----------------------------------------------------------------------------------------------
        
                IItem exp = null;
                String motivoRechazo = null;
                String numExp = rulectx.getNumExp();
                IItemCollection collExps = entitiesAPI.getEntities("SGD_RECHAZO_DECRETO", numExp);
                Iterator<?> itExps = collExps.iterator();

                if (itExps.hasNext()) {
                    exp = (IItem)itExps.next();
                    motivoRechazo = exp.getString("RECHAZO_DECRETO");
                    
                    if (StringUtils.isNotEmpty(motivoRechazo)){
                        return null;
                    }
                }
                
            }catch (Exception e){
                LOGGER.info("No mostraba nada, solo muesta el mensaje. El error es: " + e.getMessage(), e);

                try {
                    throw new ISPACInfo("Se ha producido un error al ejecutar la regla de obtener datos del decreto.");
                } catch (ISPACInfo e1) {
                    LOGGER.error("ERROR. " + e1.getMessage(), e1);
                }
            }
            
            //APIs
            IEntitiesAPI entitiesAPI = rulectx.getClientContext().getAPI().getEntitiesAPI();
            
            // Consulta para obtener los documentos del tr�mite actual
            String sqlDocQuery = "ID_TRAMITE = "+rulectx.getTaskId();
            IItemCollection tasksDocuments = entitiesAPI.getDocuments(rulectx.getNumExp(), sqlDocQuery, "");
            
            // S�lo si hay un documento (decreto) adjunto
            if (tasksDocuments!=null && tasksDocuments.toList().size()==1){
                IItem doc = (IItem)tasksDocuments.iterator().next();
                int docId = doc.getInt("ID");

                String sqlQuery = "WHERE ID_DOCUMENTO = " + docId + " ORDER BY ID_PASO ASC";
                
                IItemCollection ctosFirmaCollection = entitiesAPI.queryEntities("SPAC_CTOS_FIRMA", sqlQuery);
                
                // Comprobar que hay circuito de firmas para el documento, y que son dos pasos (presidente y fedatario)
                if(ctosFirmaCollection.next() && ctosFirmaCollection.toList().size()==2){
                    
                    // Datos del decreto
                    String presidente = null;
                    Date fechaFirmaPresidente = null;
                    
                    IItem ctoFirmaPpresidente = (IItem)ctosFirmaCollection.toList().get(0);
                    
                    if(ctoFirmaPpresidente!=null && ctoFirmaPpresidente.getInt("ESTADO")==2){
                        // Entidad Decreto
                        IItemCollection decretos = entitiesAPI.getEntities("SGD_DECRETO", rulectx.getNumExp());
                        IItem decreto = (IItem)decretos.iterator().next();

                        String strNombrePresidente = decreto.getString("NOMBRE_PRESIDENTE");
                        
                        if (StringUtils.isBlank(strNombrePresidente)){
                            presidente = ctoFirmaPpresidente.getString("NOMBRE_FIRMANTE");
                            
                            if (presidente!=null){
                                decreto.set("NOMBRE_PRESIDENTE", presidente);
                            }
                            fechaFirmaPresidente = decreto.getDate("FECHA_DECRETO");
                            
                            if (fechaFirmaPresidente!=null){
                                decreto.set("FECHA_PRESIDENTE", fechaFirmaPresidente);
                            }
                            decreto.store(rulectx.getClientContext());
                        }
                    }
                }
            }
            
            return null;
            
        } catch(ISPACRuleException e) {
            LOGGER.info("No mostraba nada, solo muesta el mensaje. El error es: " + e.getMessage(), e);
            throw new ISPACRuleException(e);

        } catch(Exception e) {
            throw new ISPACRuleException(e);
        }
    }

    public void cancel(IRuleContext rulectx) throws ISPACRuleException {
        // No se da nunca este caso
    }
}
