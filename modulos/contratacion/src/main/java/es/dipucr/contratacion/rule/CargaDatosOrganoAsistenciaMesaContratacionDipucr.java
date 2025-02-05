package es.dipucr.contratacion.rule;

import java.util.Iterator;

import org.apache.log4j.Logger;

import es.dipucr.sigem.api.rule.common.utils.ConsultasGenericasUtil;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

public class CargaDatosOrganoAsistenciaMesaContratacionDipucr implements IRule{
	
	private static final Logger logger = Logger.getLogger(CargaDatosOrganoAsistenciaMesaContratacionDipucr.class);

	public void cancel(IRuleContext rulectx) throws ISPACRuleException {
		
	}

	public Object execute(IRuleContext rulectx) throws ISPACRuleException {
		try {
			/*************************************************************/
			ClientContext cct = (ClientContext) rulectx.getClientContext();
			IInvesflowAPI invesflowAPI = cct.getAPI();
			IEntitiesAPI entitiesAPI = invesflowAPI.getEntitiesAPI();
			 /***********************************************************/
			
			Iterator<IItem> itdatosLic = ConsultasGenericasUtil.queryEntities(rulectx, "CONTRATACION_DATOS_LIC", "NUMEXP='"+rulectx.getNumExp()+"'");
			IItem itemDatosLic = null;
			if(itdatosLic.hasNext()){
				itemDatosLic = itdatosLic.next();
			}
			else{
				itemDatosLic = entitiesAPI.createEntity("CONTRATACION_DATOS_LIC",rulectx.getNumExp());
			}			
			String identOC = ConfigurationMgr.getVarGlobal(cct, "IDENT_CM_OA");
			if(StringUtils.isNotEmpty(identOC)){
				itemDatosLic.set("IDENT_CM_OA", identOC);
			}
			String nombreOC = ConfigurationMgr.getVarGlobal(cct, "NOMBRE_CM_OA");
			if(StringUtils.isNotEmpty(nombreOC)){
				itemDatosLic.set("NOMBRE_CM_OA", nombreOC);
			}
			itemDatosLic.store(cct);
			
			Iterator<IItem> itPersCont = ConsultasGenericasUtil.queryEntities(rulectx, "CONTRATACION_PERS_CONTACTO", "NUMEXP='"+rulectx.getNumExp()+"'");
			IItem itemPersCont = null;
			if(itPersCont.hasNext()){
				itemPersCont = itPersCont.next();
			}
			else{
				itemPersCont = entitiesAPI.createEntity("CONTRATACION_PERS_CONTACTO",rulectx.getNumExp());
			}
			String emailOC = ConfigurationMgr.getVarGlobal(cct, "EMAIL_CM_OA");
			if(StringUtils.isNotEmpty(emailOC)){
				itemPersCont.set("EMAIL_CM_OA", emailOC); 
			}
			String calleOC = ConfigurationMgr.getVarGlobal(cct, "CALLE_CM_OA");
			if(StringUtils.isNotEmpty(calleOC)){
				itemPersCont.set("CALLE_CM_OA", calleOC);
			}
			String numeroOA = ConfigurationMgr.getVarGlobal(cct, "NUMERO_CM_OA");
			if(StringUtils.isNotEmpty(numeroOA)){
				itemPersCont.set("NUMERO_CM_OA", numeroOA);
			}
			String cpOC = ConfigurationMgr.getVarGlobal(cct, "CP_CM_OA");
			if(StringUtils.isNotEmpty(cpOC)){
				itemPersCont.set("CP_CM_OA", cpOC); 
			}
			String localidadOC = ConfigurationMgr.getVarGlobal(cct, "LOCALIDAD_CM_OA");
			if(StringUtils.isNotEmpty(localidadOC)){
				itemPersCont.set("LOCALIDAD_CM_OA", localidadOC); 
			}
			String provinciaOC = ConfigurationMgr.getVarGlobal(cct, "PROVINCIA_CM_OA");
			if(StringUtils.isNotEmpty(provinciaOC)){
				itemPersCont.set("PROVINCIA_CM_OA", provinciaOC);
			}
			String locgeograficaOC = ConfigurationMgr.getVarGlobal(cct, "LOCALIZACIONGEOGRAFICA_CM_OA");
			if(StringUtils.isNotEmpty(locgeograficaOC)){
				itemPersCont.set("LOCALIZACIONGEOGRAFICA_CM_OA", locgeograficaOC);
			}
			String movilOC = ConfigurationMgr.getVarGlobal(cct, "MOVIL_CM_OA");
			if(StringUtils.isNotEmpty(movilOC)){
				itemPersCont.set("MOVIL_CM_OA", movilOC);
			}
			
			itemPersCont.store(cct);
	        
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ISPACRuleException("Error a la hora de insertar el personal de contacto. ",e);
		}
		
		return new Boolean(true);
	}

	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

}
