package es.dipucr.sigem.api.rule.procedures.cdj.convocatorias;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.sun.star.lang.XComponent;
import com.sun.star.text.XTextTable;

import es.dipucr.sigem.api.rule.common.documento.DipucrAutoGeneraDocIniTramiteRule;
import es.dipucr.sigem.api.rule.common.utils.DecretosUtil;
import es.dipucr.sigem.api.rule.common.utils.DocumentosUtil;
import es.dipucr.sigem.api.rule.common.utils.ExpedientesRelacionadosUtil;
import es.dipucr.sigem.api.rule.common.utils.ExpedientesUtil;
import es.dipucr.sigem.api.rule.common.utils.LibreOfficeUtil;
import es.dipucr.sigem.api.rule.common.utils.TramitesUtil;
import es.dipucr.sigem.api.rule.procedures.ConstantesString;
import es.dipucr.sigem.api.rule.procedures.ConstantesSubvenciones;
import es.dipucr.sigem.api.rule.procedures.SubvencionesUtils;
import es.dipucr.sigem.api.rule.procedures.bop.BopUtils;

public class DipucrGeneraJustificacionConvocatoriasSubAsociaciones extends DipucrAutoGeneraDocIniTramiteRule{

    public static final Logger LOGGER = Logger.getLogger(DipucrGeneraJustificacionConvocatoriasSubAsociaciones.class);
    
    public static final double[] DISTRIBUCION_5_COLUMNAS = {35, 15, 30, 10, 10};
    public static final double[] DISTRIBUCION_4_COLUMNAS = {35, 15, 25, 25};
    public static final double[] DISTRIBUCION_3_COLUMNAS = {40, 20, 40}; 
    
    public boolean init(IRuleContext rulectx) throws ISPACRuleException {
        LOGGER.info(ConstantesString.INICIO + this.getClass().getName());
        
        try{
            IClientContext cct = rulectx.getClientContext();
            
            plantilla = DocumentosUtil.getPlantillaDefecto(cct, rulectx.getTaskProcedureId());
            
            if(StringUtils.isNotEmpty(plantilla)){
                tipoDocumento = DocumentosUtil.getTipoDocumentoByPlantilla(cct,  plantilla);
            }
            
            refTablas = LibreOfficeUtil.ReferenciasTablas.TABLA1 + "," + LibreOfficeUtil.ReferenciasTablas.TABLA2 + "," + LibreOfficeUtil.ReferenciasTablas.TABLA3;
        } catch(ISPACException e){
            LOGGER.error(ConstantesString.LOGGER_ERROR + " al recuperar la plantilla espec�fica del expediente: " + rulectx.getNumExp() + ". " + e.getMessage(), e);
            throw new ISPACRuleException(ConstantesString.LOGGER_ERROR + " al recuperar la plantilla espec�fica del expediente: " + rulectx.getNumExp() + ". " + e.getMessage(), e);
        }

        LOGGER.info(ConstantesString.FIN + this.getClass().getName());
        return true;
    }
    
    
    public void setSsVariables(IClientContext cct, IRuleContext rulectx) {
        String numexpConvocatoria = "";
        try {
            cct.setSsVariable(ConstantesSubvenciones.VariablesSesion.ANIO, "" + Calendar.getInstance().get(Calendar.YEAR));
            
            numexpConvocatoria = rulectx.getNumExp();
                         
            //Obtenemos el asunto de la convocatoria
            String convocatoria = ExpedientesUtil.getAsunto(cct, numexpConvocatoria);

            String numexpDecreto = DecretosUtil.getUltimoNumexpDecreto(cct, numexpConvocatoria);
            String numDecreto = DecretosUtil.getNumeroDecretoCompleto(cct, numexpDecreto);
            Date fechaDecreto = DecretosUtil.getFechaDecreto(cct, numexpDecreto);
            
            //Obtenemos el n�mero de bolet�n y la fecha
            String numexpBoletin = SubvencionesUtils.getPrimerNumexpBOP(cct, numexpConvocatoria);
            Date fechaBoletin = BopUtils.getFechaPublicacion(cct, numexpBoletin);
            int numBoletin = BopUtils.getNumBoletin(cct, fechaBoletin);
            
            //Recuperamos el n�mero de informes generados ya
            cct.setSsVariable(ConstantesSubvenciones.VariablesSesion.NUM_DECRETO, numDecreto);
            cct.setSsVariable(ConstantesSubvenciones.VariablesSesion.FECHA_DECRETO, SubvencionesUtils.formateaFecha(fechaDecreto));
            
            cct.setSsVariable(ConstantesSubvenciones.VariablesSesion.NUM_BOLETIN, "" + numBoletin);
            cct.setSsVariable(ConstantesSubvenciones.VariablesSesion.FECHA_BOLETIN, SubvencionesUtils.formateaFecha(fechaBoletin));
            
            cct.setSsVariable(ConstantesSubvenciones.VariablesSesion.NUM_INFORME, "" + TramitesUtil.cuentaTramites(cct, numexpConvocatoria, rulectx.getTaskProcedureId()));
            cct.setSsVariable(ConstantesSubvenciones.VariablesSesion.CONVOCATORIA, convocatoria);
        } catch (ISPACException e) {
            LOGGER.error(ConstantesString.LOGGER_ERROR + " en el expediente: " + numexpConvocatoria + ". " + e.getMessage(), e);
        }
    }

    public void deleteSsVariables(IClientContext cct) {
        try {
            cct.deleteSsVariable(ConstantesSubvenciones.VariablesSesion.ANIO);            
            cct.deleteSsVariable(ConstantesSubvenciones.VariablesSesion.NUM_DECRETO);
            cct.deleteSsVariable(ConstantesSubvenciones.VariablesSesion.FECHA_DECRETO);            
            cct.deleteSsVariable(ConstantesSubvenciones.VariablesSesion.NUM_BOLETIN);
            cct.deleteSsVariable(ConstantesSubvenciones.VariablesSesion.FECHA_BOLETIN);
            cct.deleteSsVariable(ConstantesSubvenciones.VariablesSesion.NUM_INFORME);
            cct.deleteSsVariable(ConstantesSubvenciones.VariablesSesion.CONVOCATORIA);
        } catch (ISPACException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
    
    
    
    public void insertaTabla(IRuleContext rulectx, XComponent component, String refTabla, IEntitiesAPI entitiesAPI, String numexp) {
        
        String estadoAdm = ExpedientesUtil.EstadoADM.JF;
        int numeroColumnas = 5;
        double [] distribucionColumnas = DISTRIBUCION_5_COLUMNAS;
        String textoCabecera3 = ConstantesString.CabeceraTabla.PROYECTO_ACTIVIDAD;
        
        try{
            IClientContext cct = rulectx.getClientContext();
            if (LibreOfficeUtil.ReferenciasTablas.TABLA1.equals(refTabla)){
                estadoAdm = ExpedientesUtil.EstadoADM.JF;
                numeroColumnas = 5;
                distribucionColumnas = DISTRIBUCION_5_COLUMNAS;
                textoCabecera3 = ConstantesString.CabeceraTabla.PROYECTO_ACTIVIDAD;
                
            } else if (LibreOfficeUtil.ReferenciasTablas.TABLA2.equals(refTabla)){
                estadoAdm = ExpedientesUtil.EstadoADM.RC;
                numeroColumnas = 4;
                distribucionColumnas = DISTRIBUCION_4_COLUMNAS;
                textoCabecera3 = ConstantesString.CabeceraTabla.PROYECTO_ACTIVIDAD;
                
            } else if (LibreOfficeUtil.ReferenciasTablas.TABLA3.equals(refTabla)){
                estadoAdm = ExpedientesUtil.EstadoADM.RN;
                numeroColumnas = 3;
                distribucionColumnas = DISTRIBUCION_3_COLUMNAS;
                textoCabecera3 = ConstantesString.CabeceraTabla.MOTIVO_RECHAZO;
            }

            List<String> expedientesList = ExpedientesRelacionadosUtil.getExpedientesRelacionadosHijosByEstadoAdm(rulectx, estadoAdm, ExpedientesUtil.IDENTIDADTITULAR);
            
            int numFilas = expedientesList.size();
            
            XTextTable tabla = LibreOfficeUtil.insertaTablaEnPosicion(component, refTabla, numFilas + 1, numeroColumnas);
            if(null != tabla){
                LibreOfficeUtil.colocaColumnas(tabla, distribucionColumnas);
    
                LibreOfficeUtil.setTextoCeldaCabecera(tabla, 1, ConstantesString.CabeceraTabla.ASOCIACION_CLUB);
                LibreOfficeUtil.setTextoCeldaCabecera(tabla, 2, ConstantesString.CabeceraTabla.CIF);
                LibreOfficeUtil.setTextoCeldaCabecera(tabla, 3, textoCabecera3);
                
                if(5 == numeroColumnas){
                    LibreOfficeUtil.setTextoCeldaCabecera(tabla, 4, ConstantesString.CabeceraTabla.SUBVENCION);
                    LibreOfficeUtil.setTextoCeldaCabecera(tabla, 5, ConstantesString.CabeceraTabla.MINORACION_DEVOLUCION);
                } else if (4 == numeroColumnas){
                    LibreOfficeUtil.setTextoCeldaCabecera(tabla, 4, ConstantesString.CabeceraTabla.MOTIVO_RECHAZO);
                }
                
                int i = 1;
                for(String numexpHijo: expedientesList){
                    i++;                    
                    insertaFila(cct, tabla, numexpHijo, numeroColumnas, i);
                }
            }
        } catch (ISPACException e) {
            LOGGER.error(ConstantesString.LOGGER_ERROR + " en el expediente: " + numexp + ". " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(ConstantesString.LOGGER_ERROR + " en el expediente: " + numexp + ". " + e.getMessage(), e);
        }
    }


    private void insertaFila(IClientContext cct, XTextTable tabla, String numexpHijo, int numeroColumnas, int numColumna) {
        
        String ayuntamiento = "";
        String cif = "";
        String proyecto = "";
        String importe = "";
        String devolucion = "";
        String motivoRechazo = "";

        try{
            IEntitiesAPI entitiesAPI = cct.getAPI().getEntitiesAPI();
            
            IItem expediente = ExpedientesUtil.getExpediente(cct, numexpHijo);
            
            ayuntamiento = SubvencionesUtils.getString(expediente, ExpedientesUtil.IDENTIDADTITULAR);
            cif = SubvencionesUtils.getString(expediente, ExpedientesUtil.NIFCIFTITULAR);                        
    
            IItem solicitud = (IItem) entitiesAPI.getEntities(ConstantesSubvenciones.DatosSolicitud.NOMBRE_TABLA, numexpHijo).iterator().next();
            proyecto = SubvencionesUtils.getString(solicitud, ConstantesSubvenciones.DatosSolicitud.FINALIDAD);
    
            Iterator<?> expResolucion = entitiesAPI.getEntities(ConstantesSubvenciones.DatosResolucion.NOMBRE_TABLA, numexpHijo).iterator();
            
            if(expResolucion.hasNext()){                        
                IItem resolucion = (IItem) expResolucion.next();
                importe = SubvencionesUtils.getFormattedDouble(resolucion, ConstantesSubvenciones.DatosResolucion.IMPORTE);
                devolucion = SubvencionesUtils.getFormattedDoubleVacioSiMenorIgualCero(resolucion, ConstantesSubvenciones.DatosResolucion.DEVOLUCION);
    
                motivoRechazo = SubvencionesUtils.getString(resolucion, ConstantesSubvenciones.DatosResolucion.MOTIVO_RECHAZO);
            }
            
            LibreOfficeUtil.setTextoCelda(tabla,  1,  numColumna, ayuntamiento);
            LibreOfficeUtil.setTextoCelda(tabla,  2,  numColumna, cif);
            LibreOfficeUtil.setTextoCelda(tabla,  3,  numColumna, proyecto);
            
            if(5 == numeroColumnas){
                
                LibreOfficeUtil.setTextoCelda(tabla,  4,  numColumna, importe);
                LibreOfficeUtil.setTextoCelda(tabla,  5,  numColumna, devolucion);
                
            } else if (4 == numeroColumnas){
                LibreOfficeUtil.setTextoCelda(tabla,  3,  numColumna, proyecto);
                LibreOfficeUtil.setTextoCelda(tabla,  4,  numColumna, motivoRechazo);
                
            } else if (3 == numeroColumnas){
                LibreOfficeUtil.setTextoCelda(tabla,  3,  numColumna, motivoRechazo);
            }
            
        } catch (ISPACException e){
            LOGGER.error("ERROR al inserta la fila: " + numColumna + " quese corresponde con la solcitud: " + numexpHijo + ". " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("ERROR al inserta la fila: " + numColumna + " quese corresponde con la solcitud: " + numexpHijo + ". " + e.getMessage(), e);
        }
    }
}