package es.dipucr.sigem.api.rule.procedures.intervencion.planesprovinciales.convocatorias;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;

import java.util.Calendar;
import java.util.Iterator;

import org.apache.log4j.Logger;

import es.dipucr.sigem.api.rule.common.documento.DipucrAutoGeneraDocIniTramiteRule;
import es.dipucr.sigem.api.rule.common.utils.ParticipantesUtil;
import es.dipucr.sigem.api.rule.procedures.ConstantesString;
import es.dipucr.sigem.api.rule.procedures.ConstantesSubvenciones;

    public class DipucrGeneraJustificacionParcialPlanObras  extends
            DipucrAutoGeneraDocIniTramiteRule {

        private static final Logger LOGGER = Logger
                .getLogger(DipucrGeneraJustificacionParcialPlanObras .class);

        public boolean init(IRuleContext rulectx) throws ISPACRuleException {
            LOGGER.info(ConstantesString.INICIO +this.getClass().getName());

            tipoDocumento = "Informe de Justificación";
            plantilla = "Informe de Justificación Parcial";
 
            LOGGER.info(ConstantesString.FIN +this.getClass().getName());
            return true;
        }

        
        public void setSsVariables(IClientContext cct, IRuleContext rulectx) {
            String numexp = "";
            try {
                cct.setSsVariable(ConstantesSubvenciones.VariablesSesion.ANIO, "" +Calendar.getInstance().get(Calendar.YEAR));
                
                numexp = rulectx.getNumExp();
                
                IItemCollection partCol = ParticipantesUtil.getParticipantes( cct, numexp, "", "");
                Iterator<?> partIt = partCol.iterator();
                
                if(partIt.hasNext()){
                    IItem part = (IItem)partIt.next();
                
                    cct.setSsVariable("NOMBRE", part.getString("NOMBRE"));
                    cct.setSsVariable("DIRNOT", part.getString("DIRNOT"));
                    cct.setSsVariable("C_POSTAL", part.getString("C_POSTAL"));
                    cct.setSsVariable("LOCALIDAD", part.getString("LOCALIDAD"));
                    cct.setSsVariable("CAUT", part.getString("CAUT"));
                }
            } catch (ISPACException e) {
                LOGGER.error(ConstantesString.LOGGER_ERROR + " en el expediente: " + numexp + ". " + e.getMessage(), e);
            }
        }

        public void deleteSsVariables(IClientContext cct) {
            try {
                cct.deleteSsVariable(ConstantesSubvenciones.VariablesSesion.ANIO);
                cct.deleteSsVariable("NOMBRE");
                cct.deleteSsVariable("DIRNOT");
                cct.deleteSsVariable("C_POSTAL");
                cct.deleteSsVariable("LOCALIDAD");
                cct.deleteSsVariable("CAUT");
            } catch (ISPACException e) {
                LOGGER.error( e.getMessage(), e);
            }
        }
    }
