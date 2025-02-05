package es.dipucr.sigem.arbolDocumental.actions;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.InvesflowAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.impl.SessionAPIFactory;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.util.ListCollection;
import ieci.tdw.ispac.audit.business.vo.AuditContext;
import ieci.tdw.ispac.audit.context.AuditContextHolder;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.TreeItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.directory.DirectoryConnectorFactory;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryConnector;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryEntry;
import ieci.tdw.ispac.ispaclib.messages.MessagesFormatter;
import ieci.tdw.ispac.ispaclib.session.OrganizationUserInfo;
import ieci.tdw.ispac.ispaclib.utils.LocaleHelper;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.manager.ISPACRewrite;
import ieci.tecdoc.sgm.core.user.web.ConstantesSesionUser;
import ieci.tecdoc.sgm.core.user.web.WebAuthenticationHelper;
import ieci.tecdoc.sgm.core.web.locale.LocaleFilterHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;


//Llamada desde las sedes: http://172.20.1.105:8080/SIGEM_AutenticacionWeb/?REDIRECCION=ArbolDocumental&ENTIDAD_ID=036

public class ArbolDocumentalAction extends Action{
	
	private static final Logger logger = Logger.getLogger(ArbolDocumentalAction.class);
	
	public ActionForward executeAction(
 			ActionMapping mapping,
 			ActionForm form,
 			HttpServletRequest request,
 			HttpServletResponse response,
 			SessionAPI session)
 			throws Exception{
	
 		//Par�metro con el id de procedimiento a desplegar en �rbol
 		String idProc = request.getParameter("idProc");
		
//		IClientContext cct = new ClientContext();
//		((ClientContext) cct).setAPI(new InvesflowAPI((ClientContext) cct));
//		IInvesflowAPI invesFlowAPI = cct.getAPI();
		
		IInvesflowAPI invesFlowAPI = session.getAPI();

		ClientContext cct = session.getClientContext();
		
		if(null == cct){
			DbCnt cnt = new DbCnt();
			cnt.getConnection();
			cct = new ClientContext(cnt);
		}
		if(null == invesFlowAPI) invesFlowAPI = new InvesflowAPI((ClientContext) cct);
		
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();
		IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();

		Map<String, String> tableentitymap = new HashMap<String, String>();
		tableentitymap.put("SPAC_CT_PROCEDIMIENTO","SPAC_CT_PROCEDIMIENTOS");
		tableentitymap.put("SPAC_P_PROCEDIMIENTO","SPAC_P_PROCEDIMIENTOS");

        //Seleccionamos la �ltima versi�n de cada procedimiento.
		String whereClause =  " WHERE SPAC_P_PROCEDIMIENTO.ID = SPAC_CT_PROCEDIMIENTO.ID AND SPAC_CT_PROCEDIMIENTO.ID IN ( "
							+ " SELECT MAX(ID) FROM SPAC_P_PROCEDIMIENTOS "
							+ " GROUP BY ID_GROUP "
							+ " ) ORDER BY SPAC_CT_PROCEDIMIENTO.NOMBRE";

        IItemCollection itemcol = catalogAPI.queryCTEntities(tableentitymap, whereClause);
        Map<?,?> itemcolmapId = itemcol.toMap("SPAC_CT_PROCEDIMIENTO:ID");
        List<IItem> items = new ArrayList<IItem>();
        
        Iterator<?> it = itemcolmapId.entrySet().iterator();
        while (it.hasNext()) {
        	
        	Entry<?, ?> e = (Entry<?, ?>) it.next();
        	IItem item = (IItem) e.getValue();
        	
        	if (item.getInt("SPAC_CT_PROCEDIMIENTO:ID_PADRE") != -1) {
        		
        		
        		IItem itemPadre = (IItem) itemcolmapId.get(item.get("SPAC_CT_PROCEDIMIENTO:ID_PADRE"));
        		if (itemPadre != null) {
        			
        			// Referenciar los elementos del �rbol mediante los identificadores de grupo
        			item.set("SPAC_CT_PROCEDIMIENTO:ID_PADRE", itemPadre.getInt("SPAC_P_PROCEDIMIENTO:ID_GROUP"));
        			
        		} else {
        			itemPadre = procedureAPI.getProcedureById(item.getInt("SPAC_CT_PROCEDIMIENTO:ID_PADRE"));
        			if (itemPadre != null) {

            			// Referenciar los elementos del �rbol mediante los identificadores de grupo
            			item.set("SPAC_CT_PROCEDIMIENTO:ID_PADRE", itemPadre.getInt("PPROCEDIMIENTOS:ID_GROUP"));

        			}
        		}
        	}
        	
        	items.add(item);
        }

        //Obtenemos el �rbol de ItemBeans
        TreeItemBean tree = CollectionBean.getBeanTree(new ListCollection(items), "SPAC_P_PROCEDIMIENTO:ID_GROUP", "SPAC_CT_PROCEDIMIENTO:ID_PADRE");

   	 	//Obtenemos el formateador
        CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/ctprocedurestreeformatter.xml"));

		request.setAttribute("CTProceduresListFormatter", formatter);
   	 	request.setAttribute("CTProceduresList",tree);
   	 	if(idProc != null){
   	 		request.setAttribute("idProc", idProc);
   	 	}

		//request.getSession().setAttribute(ConstantesSesionUser.ID_SESION, request.getParameter(ConstantesSesionUser.ID_SESION));
		
   	 	return mapping.findForward("success");
	}

	
	private static final String APLICACION = "AD";
	
	protected static final String LAST_URL_SESSION_KEY="ISPAC_LAST_URL";
	public static final String APPLICATION_ERRORS="appErrors";
	private static final String ERROR_FORWARD_KEY="appError";

	public static final String GLOBAL_FORWARD_ERROR = "error";
	public static final String GLOBAL_FORWARD_LOGIN = "errorAutenticacion";
	private static final String TIPO_APLICACION = "ArbolDocumental";


	// Content type
	private static final String CONTENT_TYPE = "text/html; charset=ISO-8859-15";
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ActionForward forward = null;
		SessionAPI sesion = null;

		try{
			
			String entityId = obtenerIdentificadorEntidad(request);
			boolean tieneSesion = false;
			
			if( !isNuloOVacio(request.getParameter(ConstantesSesionUser.ID_SESION)) || !isNuloOVacio(request.getAttribute(ConstantesSesionUser.ID_SESION))  || !isNuloOVacio(request.getSession().getAttribute(ConstantesSesionUser.ID_SESION))){
				// Ya existe una sesi�n de usuario iniciada.
				if(isNuloOVacio(entityId)){
					tieneSesion = false;
				}
				tieneSesion = true;
			}
			if(!tieneSesion){
				response.sendRedirect(WebAuthenticationHelper.getWebAuthURL(request,TIPO_APLICACION));
				return null;
			}
			
			Locale locale = LocaleFilterHelper.getCurrentLocale(request);
			
			String remoteHost = request.getRemoteHost();
			if (StringUtils.isBlank(remoteHost)) {
				remoteHost = request.getRemoteAddr();
			}
			
			// Almacenamos el idioma seleccionado en sesi�n
			LocaleHelper.setLocale(request, locale);
			
			String entityName = null;
			String userName = null;
			
			OrganizationUserInfo info = new OrganizationUserInfo();
			info.setOrganizationId(entityId);
			info.setOrganizationName(entityName);
			info.setUserName(userName);
			
			sesion = SessionAPIFactory.getSessionAPI(request, info);
//			sesion.init(locale);
			
			//Se eliminan las sesiones activas del usuario actual para esta aplicacion
			IItemCollection activeSessions = sesion.getActiveSessions(userName, APLICACION);
			while(activeSessions.next()){
				IItem activeSession = activeSessions.value();
				sesion.deleteSession(activeSession.getString("ID"));
			}

			IDirectoryConnector directory = DirectoryConnectorFactory.getConnector();
			IDirectoryEntry userEntry = directory.getEntryFromRoot();
//			IDirectoryEntry userEntry = directory.getEntryFromUserName(userName);
			info.setUserId(userEntry.getUID());

			sesion.login(remoteHost, userName, userEntry, APLICACION, locale);

			request.getSession().setAttribute(ConstantesSesionUser.ID_ENTIDAD, entityId);
			request.getSession().setAttribute(ConstantesSesionUser.ID_SESION, request.getParameter(ConstantesSesionUser.ID_SESION));
	    	forward = executeAction(mapping, form, request, response, sesion);

		}catch(Throwable e){
			logger.error("Error inesperado ejecutando acci�n.", e);
			return mapping.findForward(GLOBAL_FORWARD_ERROR);
		}

		return forward;
	}
	
//	public ActionForward execute(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		// Establecer el ContentType
//		response.setContentType(CONTENT_TYPE);
//		SessionAPI sesion = null;
//		
//		if (AutenticacionBackOffice.autenticar(request)) {
//
//			// Informaci�n de la sesi�n del usuario
//			Sesion sesionBO = AutenticacionBackOffice.obtenerDatos(request);
//			String entityId = sesionBO.getIdEntidad();
//
//			HttpSession session = request.getSession();
//
//			//Almacenamos la entidad en sesi�n, �til si queremos realizar personalizaciones de imagenes, css, etc en funci�n
//			//de la entidad contra la que se est� trabajando
//			session.setAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD, entityId);
//
//			// Obtener el locale seleccionado por el filtro
//			Locale locale = LocaleFilterHelper.getCurrentLocale(request);
//
//			// Almacenamos el idioma seleccionado en sesi�n
//			LocaleHelper.setLocale(request, locale);
//
//			String entityName = null;
//			String userName = sesionBO.getUsuario();
//
//			String remoteHost = request.getRemoteHost();
//			if (StringUtils.isBlank(remoteHost)) {
//				remoteHost = request.getRemoteAddr();
//			}
//			
//			OrganizationUserInfo info = new OrganizationUserInfo();
//			info.setOrganizationId(entityId);
//			info.setOrganizationName(entityName);
//			info.setUserName(userName);
//			
//			sesion = SessionAPIFactory.getSessionAPI(request, info);
//			
////			sesion.init(locale);
//			
//			//Se eliminan las sesiones activas del usuario actual para esta aplicacion
//			IItemCollection activeSessions = sesion.getActiveSessions(userName, APLICACION);
//			while(activeSessions.next()){
//				IItem activeSession = activeSessions.value();
//				sesion.deleteSession(activeSession.getString("ID"));
//			}
//
//			IDirectoryConnector directory = DirectoryConnectorFactory.getConnector();
//			IDirectoryEntry userEntry = directory.getEntryFromRoot();
////			IDirectoryEntry userEntry = directory.getEntryFromUserName(userName);
//			info.setUserId(userEntry.getUID());
//
//			sesion.login(remoteHost, userName, userEntry, APLICACION, locale);
//
//		}
//		
//		try {			
//			setAuditContext(request, sesion);
//			
//			ActionForward forward = executeAction(mapping, form, request, response, sesion);
//			
//			// Contexto para la vista
//			// utilizado en el Tag de Calendario para obtener el idioma de la aplicaci�n
//			request.setAttribute("ClientContext", sesion.getClientContext());
//			
//			// Guardamos la ultima URL accedida correctamente.
//			if ((forward != null) && (!forward.getRedirect())) {
//				setLastURL(request);
//			}
//			
//			return forward;
//		}
//		catch(ISPACInfo e) {
//			
//			// Obtener el recurso para el mensaje de error de la excepci�n
//			saveAppErrors(request, getActionMessages(request, e));
//			
//			// Si no se ha definido la gesti�n de error para la acci�n actual
//			// en el struts-config se vuelve a invocar la anterior.
//			ActionForward actionForward = mapping.findForward(ERROR_FORWARD_KEY);
//			if(actionForward != null)
//			    return actionForward;
//			
//			//Si la acci�n anterior coincide con la actual
//			//hemos entrado en un bucle: lanzamos una ISPACException
//			//que ser� capturada por el manejador definido en el struts-config
//			if(compareLastURL(request))
//				throw new ISPACException("No se encuentra un forward v�lido para la gesti�n del error", e);
//
//			return new ActionForward((String)request.getSession().getAttribute(LAST_URL_SESSION_KEY) );
//		}
//		finally {
//			if(null != sesion) sesion.release();
//		}
//		
//	}
	
	public String getISPACPath(String relativepath) {
	    ISPACRewrite rewrite=new ISPACRewrite(servlet.getServletContext());
		return rewrite.rewritePath(relativepath);
	}
	
	private void setLastURL(HttpServletRequest request){
		request.getSession().setAttribute(LAST_URL_SESSION_KEY, getUrl(request));
	}
	
	private boolean compareLastURL(HttpServletRequest request){
		String lastURL = (String)request.getSession().getAttribute(LAST_URL_SESSION_KEY);
		String actual = getUrl(request);
		if(actual.equals(lastURL)){
			return true;
		}
		return false;
	}
	
	public void saveAppErrors(HttpServletRequest request, ActionMessages errors){
		request.setAttribute(APPLICATION_ERRORS, errors);
	}
	
	private String getUrl(HttpServletRequest request){
		StringBuffer sUrl = new StringBuffer(request.getServletPath());
		String sQueryString = request.getQueryString();
		if((sQueryString != null) && (!sQueryString.equals(""))){
			sUrl.append("?");
			sUrl.append(sQueryString);
		}
		return sUrl.toString();
	}
	
	public ActionMessages getActionMessages(HttpServletRequest request, Exception e){
		ActionMessages errors = new ActionMessages();
		ActionMessage mensaje = null;
		
		if (e instanceof ISPACException) {
			
			ISPACException ie = (ISPACException) e;
			
			String msg = ie.getExtendedMessage(request.getLocale());
			if (msg != null) {
			
				msg = MessagesFormatter.format(msg, ie.getArgs());
			}
			
			mensaje = new ActionMessage("ispacexception", msg);
		}
		else {
			mensaje = new ActionMessage("ispacexception", e.getMessage());
		}
		
		errors.add(ActionMessages.GLOBAL_MESSAGE, mensaje);
		return errors;
	}
	
	/**
	 * @param request
	 */
	private void setAuditContext(HttpServletRequest request, SessionAPI session) {
		AuditContext auditContext = new AuditContext();
		auditContext.setUserHost(request.getRemoteHost());
		auditContext.setUserIP(request.getRemoteAddr());
		if (null != session){
			auditContext.setUser(session.getUserName());
			//auditContext.setUserId(session.getClientContext().getUser().getUID());
		}
		AuditContextHolder.setAuditContext(auditContext);
	}

	public static String obtenerIdentificadorEntidad(HttpServletRequest request){
		String idEntidad = (String)request.getSession().getAttribute(ConstantesSesionUser.ID_ENTIDAD);
		if(isNuloOVacio(idEntidad)){
			idEntidad = request.getParameter(ConstantesSesionUser.ID_ENTIDAD);
		}
		return idEntidad;
	}
	
	public static boolean isNuloOVacio(Object cadena)
	{
		if((cadena == null) || ("".equals(cadena)) || ("null".equals(cadena))) {
			return true;
		}
		return false;
	}
	
}
