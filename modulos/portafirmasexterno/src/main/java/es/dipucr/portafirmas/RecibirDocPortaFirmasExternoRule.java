package es.dipucr.portafirmas;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.activation.DataHandler;

import org.apache.axis2.AxisFault;
import org.apache.log4j.Logger;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfCopy;

import _0.v2.query.pfirma.cice.juntadeandalucia.PfirmaException;
import _0.v2.query.pfirma.cice.juntadeandalucia.QueryServiceStub.Authentication;
import _0.v2.query.pfirma.cice.juntadeandalucia.QueryServiceStub.Comment;
import _0.v2.query.pfirma.cice.juntadeandalucia.QueryServiceStub.CommentList;
import _0.v2.query.pfirma.cice.juntadeandalucia.QueryServiceStub.Document;
import _0.v2.query.pfirma.cice.juntadeandalucia.QueryServiceStub.DocumentList;
import _0.v2.query.pfirma.cice.juntadeandalucia.QueryServiceStub.DownloadSignResponse;
import _0.v2.query.pfirma.cice.juntadeandalucia.QueryServiceStub.GetCVSResponse;
import _0.v2.query.pfirma.cice.juntadeandalucia.QueryServiceStub.ImportanceLevel;
import _0.v2.query.pfirma.cice.juntadeandalucia.QueryServiceStub.Parameter;
import _0.v2.query.pfirma.cice.juntadeandalucia.QueryServiceStub.ParameterList;
import _0.v2.query.pfirma.cice.juntadeandalucia.QueryServiceStub.QueryRequestResponse;
import _0.v2.query.pfirma.cice.juntadeandalucia.QueryServiceStub.RemitterList;
import _0.v2.query.pfirma.cice.juntadeandalucia.QueryServiceStub.Request;
import _0.v2.query.pfirma.cice.juntadeandalucia.QueryServiceStub.RequestStatus;
import _0.v2.query.pfirma.cice.juntadeandalucia.QueryServiceStub.SignLine;
import _0.v2.query.pfirma.cice.juntadeandalucia.QueryServiceStub.SignLineList;
import _0.v2.query.pfirma.cice.juntadeandalucia.QueryServiceStub.SignType;
import _0.v2.query.pfirma.cice.juntadeandalucia.QueryServiceStub.Signature;
import _0.v2.query.pfirma.cice.juntadeandalucia.QueryServiceStub.Signer;
import _0.v2.query.pfirma.cice.juntadeandalucia.QueryServiceStub.SignerList;
import _0.v2.query.pfirma.cice.juntadeandalucia.QueryServiceStub.State;
import _0.v2.query.pfirma.cice.juntadeandalucia.QueryServiceStub.User;
import _0.v2.query.pfirma.cice.juntadeandalucia.QueryServiceStub.UserJob;
import es.dipucr.portafirmas.client.PortaFirmasConsultaClient;
import es.dipucr.portafirmas.common.Configuracion;
import es.dipucr.portafirmas.common.ServiciosWebPortaFirmasFunciones;
import es.dipucr.portafirmas.dao.procedure.FirmaDocExternoInformSDAO;
import es.dipucr.sigem.api.rule.common.comparece.CompareceConfiguration;
import es.dipucr.sigem.api.rule.common.utils.ConsultasGenericasUtil;
import es.dipucr.sigem.api.rule.common.utils.DocumentosUtil;
import es.dipucr.sigem.api.rule.common.utils.EntidadesAdmUtil;
import es.dipucr.sigem.api.rule.common.utils.FechasUtil;
import es.dipucr.sigem.api.rule.common.utils.GestorMetadatos;
import es.dipucr.sigem.api.rule.common.utils.ParticipantesUtil;
import es.dipucr.sigem.api.rule.common.utils.TramitesUtil;
import es.dipucr.sigem.api.rule.procedures.Constants;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.ISignAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.common.constants.DocumentLockStates;
import ieci.tdw.ispac.ispaclib.common.constants.SignStatesConstants;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.sign.SignDocument;
import ieci.tdw.ispac.ispaclib.util.FileTemporaryManager;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tecdoc.sgm.core.config.impl.spring.SigemConfigFilePathResolver;

public class RecibirDocPortaFirmasExternoRule implements IRule{
	
	private static final Logger logger = Logger.getLogger(RecibirDocPortaFirmasExternoRule.class);
	
	/**
	 * Ruta por defecto de la imagen del logo dipu
	 * /home/sigem/SIGEM/conf/SIGEM_Tramitacion
	 */
    private static Font fuenteNegra12 = new Font(Font.TIMES_ROMAN, 12, Font.BOLD, Color.BLACK);
    private static Font fuenteNegraNormal12 = new Font(Font.TIMES_ROMAN, 12, Font.NORMAL, Color.BLACK);
    private static Font fuenteNegra25 = new Font(Font.TIMES_ROMAN, 15, Font.BOLD, Color.BLUE);

 


	public boolean init(IRuleContext rulectx) throws ISPACRuleException {

		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
		boolean cerrar = false;
		try{
			/*************************************************************************************/
			ClientContext cct = (ClientContext)rulectx.getClientContext();
			IEntitiesAPI entitiesAPI =  cct.getAPI().getEntitiesAPI();
			/**************************************************************************************/
			
			IItem itTramite = TramitesUtil.getTramiteByCode(rulectx, "gen-doc-firmar");
			
			IItemCollection itColTramites = TramitesUtil.queryTramites(cct, "WHERE ID_TRAM_CTL="+itTramite.getInt("ID")+"AND NUMEXP='"+rulectx.getNumExp()+"'");
			Iterator<IItem> itTramites = itColTramites.iterator();
			StringBuffer queryTramite = new StringBuffer("");
			queryTramite.append("(ID_TRAMITE='"+ itTramites.next().getInt("ID_TRAM_EXP")+"'");
			while(itTramites.hasNext()){
				IItem tramite = itTramites.next();
				queryTramite.append(" OR ID_TRAMITE='"+tramite.getInt("ID_TRAM_EXP")+"'");
			}
			queryTramite.append(")");
			
			//Obtengo el identificador de la peticion
			String query = "WHERE NUMEXP='"+rulectx.getNumExp()+"' AND ID_FASE='"+rulectx.getStageId()+"' AND ESTADOFIRMA=0 AND "+queryTramite.toString();
			IItemCollection itColPeticion = entitiesAPI.queryEntities("FIRMA_DOC_EXTERNO_IDDOC", query);
			Iterator<IItem> itPeticion = itColPeticion.iterator();
			if(!itPeticion.hasNext()){
				rulectx.setInfoMessage("No ha sido firmado el convenio por el interesado");
			}
			else{
				while(itPeticion.hasNext()){

					IItem peticion = itPeticion.next();		
					String direccionPortaFirmaExternoConsulta = ServiciosWebPortaFirmasFunciones.getDireccionSWConsulta();
					PortaFirmasConsultaClient consulta = new PortaFirmasConsultaClient(direccionPortaFirmaExternoConsulta);					
					Authentication authentication = Configuracion.getAuthenticationConsultaPADES(cct);
					
					QueryRequestResponse informacion = consulta.recuperaDetallePeticion(authentication, peticion.getString("ID_PETICION"));
					String valorPet = informacion.getRequest().getRequestStatus().getValue();
					if (!valorPet.equals("EN PROCESO")) {
						cerrar = true;
					}
				}	
			}
			
		}catch (ISPACRuleException e){
			logger.error("Expediente. "+rulectx.getNumExp() +" - "+e.getMessage(), e);
			throw new ISPACRuleException("Expediente. "+rulectx.getNumExp() +" - "+e.getMessage(), e);
		} catch (AxisFault e) {
			logger.error("Expediente. "+rulectx.getNumExp() +" - "+e.getMessage(), e);
			throw new ISPACRuleException("Expediente. "+rulectx.getNumExp() +" - "+e.getMessage(), e);
		} catch (RemoteException e) {
			logger.error("Expediente. "+rulectx.getNumExp() +" - "+e.getMessage(), e);
			throw new ISPACRuleException("Expediente. "+rulectx.getNumExp() +" - "+e.getMessage(), e);
		} catch (PfirmaException e) {
			logger.error("Expediente. "+rulectx.getNumExp() +" - "+e.getMessage(), e);
			throw new ISPACRuleException("Expediente. "+rulectx.getNumExp() +" - "+e.getMessage(), e);
		} catch (ISPACException e) {
			logger.error("Expediente. "+rulectx.getNumExp() +" - "+e.getMessage(), e);
			throw new ISPACRuleException("Expediente. "+rulectx.getNumExp() +" - "+e.getMessage(), e);
		}
		return cerrar;
	}

	@SuppressWarnings("unchecked")
	public Object execute(IRuleContext rulectx) throws ISPACRuleException {
		try{
			/*************************************************************************************/
			ClientContext cct = (ClientContext)rulectx.getClientContext();
			IEntitiesAPI entitiesAPI =  cct.getAPI().getEntitiesAPI();
			ISignAPI signAPI = cct.getAPI().getSignAPI();
			/**************************************************************************************/
			IItem itTramite = TramitesUtil.getTramiteByCode(rulectx, "gen-doc-firmar");
			
			IItemCollection itColTramites = TramitesUtil.queryTramites(cct, "WHERE ID_TRAM_CTL="+itTramite.getInt("ID")+" AND NUMEXP='"+rulectx.getNumExp()+"'");
			Iterator<IItem> itTramites = itColTramites.iterator();
			StringBuffer queryTramite = new StringBuffer("");
			queryTramite.append("(ID_TRAMITE='"+ itTramites.next().getInt("ID_TRAM_EXP")+"'");
			while(itTramites.hasNext()){
				IItem tramite = itTramites.next();
				queryTramite.append(" OR ID_TRAMITE='"+tramite.getInt("ID_TRAM_EXP")+"'");
			}
			queryTramite.append(")");
			
			//Obtengo el identificador de la peticion
			String query = "WHERE NUMEXP='"+rulectx.getNumExp()+"' AND ID_FASE='"+rulectx.getStageId()+"' AND "+queryTramite.toString();
			IItemCollection itColPeticion = entitiesAPI.queryEntities("FIRMA_DOC_EXTERNO_IDDOC", query);
			Iterator<IItem> itPeticion = itColPeticion.iterator();
			while(itPeticion.hasNext()){
				IItem peticion = itPeticion.next();		
				String direccionPortaFirmaExternoConsulta = ServiciosWebPortaFirmasFunciones.getDireccionSWConsulta();				
				PortaFirmasConsultaClient consulta = new PortaFirmasConsultaClient(direccionPortaFirmaExternoConsulta);
				Authentication authentication = Configuracion.getAuthenticationConsultaPADES(cct);
				
				DownloadSignResponse downloadSign = consulta.recuperaDocumentosBySolicitud(authentication, peticion.getString("ID_DOCUMENTO"));
				
				if(downloadSign!=null){
					Signature signature = downloadSign.getSignature();
					if(signature.getSign()==true){
						DataHandler dh = null;
						if(signature.getContent()!=null) dh = signature.getContent();
						if(dh!=null){
							
							String ruta = FileTemporaryManager.getInstance().getFileTemporaryPath()+ "/"+ FileTemporaryManager.getInstance().newFileName("."+Constants._EXTENSION_PDF);
							InputStream ie = dh.getInputStream();
							int documentId = DocumentosUtil.getIdTipoDocByCodigo(cct, "doc-Firmado");
							File documento = new File(ruta);
							if (documento.createNewFile()) {
								OutputStream os1 = new FileOutputStream(documento);
								byte[] buffer = new byte[1024];
								int numRead;
								while ( (numRead = ie.read(buffer) ) != -1) {
							          os1.write(buffer, 0, numRead);
							      }

								os1.close();
							}
							
							//C�digo de verificaci�n electr�nica de la firma digital.							
							GetCVSResponse responseCVS = consulta.devolverCVS(authentication, signature);							
							IItem docDocFirmadoPortafirmas = DocumentosUtil.generaYAnexaDocumento(rulectx, documentId, "", documento, Constants._EXTENSION_PDF);
							
							ie.close();
							
							
							IItemCollection itDocExpPorFirmas = DocumentosUtil.getDocumentos(cct, rulectx.getNumExp(), "INFOPAG_RDE IS NOT NULL", "FDOC DESC");
							if(itDocExpPorFirmas.toList().size()==0){
								String queryDocFirmadoExpRelacionado = "where id_tramite in (select id_tram_exp from spac_dt_tramites where id_tramite in (select id_tramite_padre from spac_exp_relacionados_info where numexp_hijo='"+rulectx.getNumExp()+"') and INFOPAG_RDE IS NOT NULL)";
								itDocExpPorFirmas = DocumentosUtil.queryDocumentos(cct, queryDocFirmadoExpRelacionado);
							}


							Iterator<IItem> itConv = itDocExpPorFirmas.iterator();
							IItem docFirmaPreviamenteExpeRelacionado = null;
							if(itConv.hasNext()){
								docFirmaPreviamenteExpeRelacionado = itConv.next();
								
								IItemCollection collectionPasosFirma = signAPI.getStepsByDocument(docFirmaPreviamenteExpeRelacionado.getInt("ID"));
								Iterator <IItem> iterPasosFirmar = collectionPasosFirma.iterator();
								while(iterPasosFirmar.hasNext()){
									IItem pasoFirma = iterPasosFirmar.next();
									int idCircuitoFirma = signAPI.addFirmanteCtosFirma(pasoFirma.getInt("ID_CIRCUITO"), docDocFirmadoPortafirmas.getInt("ID"), pasoFirma.getInt("ID_PASO"), pasoFirma.getString("ID_FIRMANTE"), pasoFirma.getString("NOMBRE_FIRMANTE"), pasoFirma.getDate("FECHA"));
								}								
							}
							if(null == docFirmaPreviamenteExpeRelacionado){
								docFirmaPreviamenteExpeRelacionado = docDocFirmadoPortafirmas;
							}

								
							SignDocument signDocBefore = new SignDocument(docFirmaPreviamenteExpeRelacionado);
							SignDocument signDocAfter = new SignDocument(docDocFirmadoPortafirmas);
							signDocAfter.setDocument(dh.getInputStream());
							signDocAfter.setFechaCreacion(docFirmaPreviamenteExpeRelacionado.getDate("FDOC"));
							signDocAfter.setLength((int) documento.length());
							String sMimeType = MimetypeMapping.getMimeType(Constants._EXTENSION_PDF);
							signDocAfter.setMimetype(sMimeType);
							String infoPagRDE = store(rulectx, signDocAfter);
							logger.warn("infoPagRDE---"+infoPagRDE);
							//updateDataDoc(rulectx, infoPagRDE, Constants._EXTENSION_PDF, false, signDocAfter);
							
							String hash = signAPI.generateHashCode(signDocAfter);
							signDocAfter.setHash(hash);
							String nombreFirmante = obtenerFirmante(rulectx);
							if(nombreFirmante.length()>=31){
								nombreFirmante = nombreFirmante.substring(0, 31);
							}
							
							Date fechaFirma = obtenerInformacionPeticion(authentication, signature, consulta, rulectx, peticion.getString("ID_PETICION"));
							
							
							int idCircuitoFirma = signAPI.addFirmanteCtosFirma(0, docDocFirmadoPortafirmas.getInt("ID"), 0, "", nombreFirmante, fechaFirma);
							
							GestorMetadatos.storeMetadaDocBeforeDocAfter(rulectx, signDocBefore, signDocAfter, infoPagRDE, nombreFirmante);
							docDocFirmadoPortafirmas.set(DocumentosUtil.COD_VERIFICACION, responseCVS.getCvs());
							docDocFirmadoPortafirmas.set(DocumentosUtil.COD_COTEJO, responseCVS.getCvs());
							//Obtener el nombre del documento
							docDocFirmadoPortafirmas.set(DocumentosUtil.DESCRIPCION, docDocFirmadoPortafirmas.getString(DocumentosUtil.NOMBRE));
							docDocFirmadoPortafirmas.set(DocumentosUtil.INFOPAG_RDE, infoPagRDE);
							Iterator<IItem> rellenadoFirmantesContinuacion = ConsultasGenericasUtil.queryEntities(rulectx, "FIRMA_DOC_MASFIRMANTES", "NUMEXP='"+rulectx.getNumExp()+"'");
							if(rellenadoFirmantesContinuacion.hasNext()){
								IItem firmanteDespues = rellenadoFirmantesContinuacion.next();
								String firmaDespues = "";
								if(StringUtils.isNotEmpty(firmanteDespues.getString("FIRMA_FINAL"))) firmaDespues = firmanteDespues.getString("FIRMA_FINAL");
								if(firmaDespues.equals("NO")){
									docDocFirmadoPortafirmas.set(DocumentosUtil.ESTADOFIRMA, SignStatesConstants.FIRMADO);
								}
							}							
							//docTramite.set(DocumentosUtil.INFOPAG_RDE, docTramite.getString(DocumentosUtil.INFOPAG));
							docDocFirmadoPortafirmas.set(DocumentosUtil.EXTENSION_RDE, DocumentosUtil.Extensiones.PDF);
							//docTramite.set(DocumentosUtil.FAPROBACION, responseCVS.get);
							docDocFirmadoPortafirmas.store(cct);
							
							if(documento != null && documento.exists())documento.delete();
							
							peticion.set("ESTADOFIRMA", 1);
							peticion.store(cct);
						}
					}
				}
			}
			
			
			
		}catch (ISPACRuleException e){
			logger.error("Error en el numExp. "+rulectx.getNumExp()+" - "+e.getMessage(), e);
			throw new ISPACRuleException("Error en el numExp. "+rulectx.getNumExp()+" - "+e.getMessage(), e);
		} catch (AxisFault e) {
			logger.error("Error en el numExp. "+rulectx.getNumExp()+" - "+e.getMessage(), e);
			throw new ISPACRuleException("Error en el numExp. "+rulectx.getNumExp()+" - "+e.getMessage(), e);
		} catch (RemoteException e) {
			logger.error("Error en el numExp. "+rulectx.getNumExp()+" - "+e.getMessage(), e);
			throw new ISPACRuleException("Error en el numExp. "+rulectx.getNumExp()+" - "+e.getMessage(), e);
		} catch (PfirmaException e) {
			logger.error("Error en el numExp. "+rulectx.getNumExp()+" - "+e.getMessage(), e);
			throw new ISPACRuleException("Error en el numExp. "+rulectx.getNumExp()+" - "+e.getMessage(), e);
		} catch (ISPACException e) {
			logger.error("Error en el numExp. "+rulectx.getNumExp()+" - "+e.getMessage(), e);
			throw new ISPACRuleException("Error en el numExp. "+rulectx.getNumExp()+" - "+e.getMessage(), e);
		} catch (IOException e) {
			logger.error("Error en el numExp. "+rulectx.getNumExp()+" - "+e.getMessage(), e);
			throw new ISPACRuleException("Error en el numExp. "+rulectx.getNumExp()+" - "+e.getMessage(), e);
		} catch (Exception e) {
			logger.error("Error en el numExp. "+rulectx.getNumExp()+" - "+e.getMessage(), e);
			throw new ISPACRuleException("Error en el numExp. "+rulectx.getNumExp()+" - "+e.getMessage(), e);
		}

		return new Boolean(true);
	}
	
	private String obtenerFirmante(IRuleContext rulectx) throws ISPACRuleException {
		StringBuffer firmante = new StringBuffer("");
		try {
			Iterator<IItem> iteFirma = ConsultasGenericasUtil.queryEntities(rulectx, "FIRMA_DOC_EXTERNO", "NUMEXP='"+rulectx.getNumExp()+"'");
			while(iteFirma.hasNext()){
				IItem itFirmante = iteFirma.next();
				String firma = itFirmante.getString("DNI"); 
				String [] vFirma = firma.split(" - ");
				if(vFirma.length>=2){
					firmante.append(vFirma[1]);
				}
			}
		} catch (ISPACRuleException e) {
			logger.error("Error en el numExp. "+rulectx.getNumExp()+" - "+e.getMessage(), e);
			throw new ISPACRuleException("Error en el numExp. "+rulectx.getNumExp()+" - "+e.getMessage(), e);
		} catch (ISPACException e) {
			logger.error("Error en el numExp. "+rulectx.getNumExp()+" - "+e.getMessage(), e);
			throw new ISPACRuleException("Error en el numExp. "+rulectx.getNumExp()+" - "+e.getMessage(), e);
		}
		return firmante.toString();
	}

	public String store(IRuleContext rulectx, SignDocument signDocAfter) throws ISPACException {
		String docref = null;

		//Cuando son documentos firmados en el infopadRDE el valor es 4
		// si es sin firma el infopag 3 
		// y como no tengo manera de obtener estos valores sin pasar por las clases de firma
		// pongo el valor directamente.
		Integer rdeArchiveId = 4;
		IGenDocAPI genDocAPI = rulectx.getClientContext().getAPI().getGenDocAPI();
		Object connectorSession = null;

		try {
			connectorSession = genDocAPI.createConnectorSession();
			docref = genDocAPI.newDocument(connectorSession, rdeArchiveId, signDocAfter.getDocument(), signDocAfter.getLength(), signDocAfter.getProperties());

			// Cerramos el fichero
			signDocAfter.getDocument().close();
			
		} catch (ISPACException e) {
			logger.error("Error al guardar el documento firmado", e);
			throw e;
		} catch (Exception e) {
			logger.error("Error al guardar el documento firmado", e);
			throw new ISPACException(e);
		} finally {
			if (connectorSession != null) {
				genDocAPI.closeConnectorSession(connectorSession);
			}
		}
		return docref;
	}
	
	protected void updateDataDoc(IRuleContext rulectx, String infoPagRDE, String extension,
			boolean changeState, SignDocument signDocAfter) throws ISPACException {
		// Actualizar la referencia en el campo INFOPAG_RDE en la tabla de
		// documentos, que es la referencia para el gestor documental
		// Se Establece el repositorio y la extension del documento (El
		// repositorio es una constante a extraer de fichero de configuracion)
		// Obtenemos el repositorio
		ISPACConfiguration config = ISPACConfiguration.getInstance();
		String repositorio = config.getProperty(ISPACConfiguration.REPOSITORY);
		IItem itemDoc = signDocAfter.getItemDoc();
		//itemDoc.set("INFOPAG_RDE", infoPagRDE);
		//itemDoc.set("EXTENSION_RDE", extension);
		//itemDoc.set("REPOSITORIO", repositorio);

		// Se cambia el estado del documento a FIRMADO si asi esta indicado,
		// se establece la fecha de firma y se asigna tambien esta fecha al
		// campo que indica la fecha de aprobacion
		if (changeState)
			itemDoc.set("ESTADOFIRMA", SignStatesConstants.FIRMADO);
		itemDoc.set("FFIRMA", new Date());
		//itemDoc.set("FAPROBACION", new Date());
		// Bloqueamos el documento para la edicion
		
		//[Manu Ticket #584] INICIO - SIGEM regla decretos evitar borrado tramite y documento.
		String bloqueo = itemDoc.getString("BLOQUEO");
		if(StringUtils.isEmpty(bloqueo) || (StringUtils.isNotEmpty(bloqueo) && !bloqueo.trim().equals(DocumentLockStates.TOTAL_LOCK)))
			itemDoc.set("BLOQUEO", DocumentLockStates.EDIT_LOCK);
		//[Manu Ticket #584] FIN - SIGEM regla decretos evitar borrado tramite y documento.
				
		itemDoc.store(rulectx.getClientContext());
	}


	@SuppressWarnings("unchecked")
	private Date obtenerInformacionPeticion(Authentication authentication, Signature signature, PortaFirmasConsultaClient consulta, IRuleContext rulectx, String idPeticion) throws ISPACRuleException {
		Calendar fechaFirma = null;
		try {
			
			/********************************************************************/
			ClientContext cct = (ClientContext)rulectx.getClientContext();
			IEntitiesAPI entitiesAPI = cct.getAPI().getEntitiesAPI();
			/********************************************************************/
			
			//Montamos el documento
			File fileDoc = new File(FileTemporaryManager.getInstance().getFileTemporaryPath()+ "/"+ FileTemporaryManager.getInstance().newFileName("."+Constants._EXTENSION_PDF));
			com.lowagie.text.Document documentJustificante = new com.lowagie.text.Document();

			PdfCopy.getInstance(documentJustificante, new FileOutputStream(fileDoc));
			
			documentJustificante.open();
			String entidad = EntidadesAdmUtil.obtenerEntidad(cct);
			
			String imageLogoPath = SigemConfigFilePathResolver.getInstance().resolveFullPath("skinEntidad_" + entidad + File.separator, "/SIGEM_TramitacionWeb") + CompareceConfiguration.getInstanceNoSingleton(entidad).getProperty(CompareceConfiguration.IMAGE_LOGO_PATH_DIPUCR);
			String imageFondoPath = SigemConfigFilePathResolver.getInstance().resolveFullPath("skinEntidad_" + entidad + File.separator, "/SIGEM_TramitacionWeb") + CompareceConfiguration.getInstanceNoSingleton(entidad).getProperty(CompareceConfiguration.IMAGE_FONDO_PATH_DIPUCR);
			String imagePiePath = SigemConfigFilePathResolver.getInstance().resolveFullPath("skinEntidad_" + entidad + File.separator, "/SIGEM_TramitacionWeb") + CompareceConfiguration.getInstanceNoSingleton(entidad).getProperty(CompareceConfiguration.IMAGE_PIE_PATH_DIPUCR);


			File logoURL = new File(imageLogoPath);
			if (logoURL != null) {
				Image logo = Image.getInstance(imageLogoPath);
				logo.scalePercent(50);
				documentJustificante.add(logo);
			}
			File fondoURL = new File(imageFondoPath);
			if(fondoURL != null){
				Image fondo = Image.getInstance(imageFondoPath);
				fondo.setAbsolutePosition(250, 50);
				fondo.scalePercent(70);
				documentJustificante.add(fondo);
				
			}

			File pieURL = new File(imagePiePath);
			if(pieURL != null){
				Image pie = Image.getInstance(imagePiePath);
				pie.setAbsolutePosition(documentJustificante.getPageSize().getWidth() - 550, 15);
				pie.scalePercent(80);
				documentJustificante.add(pie);
				
			}

			documentJustificante.add(new Phrase("\n\n"));
			
			Paragraph parrafoHoja = new Paragraph();
			 
	        // Agregamos una linea en blanco
	        agregarLineasEnBlanco(parrafoHoja, 1);
			
			parrafoHoja.add(new Paragraph("JUSTIFICANTE DE FIRMA", fuenteNegra25) );
			parrafoHoja.add(new Phrase("\n\n\n"));

			documentJustificante.add(parrafoHoja);
			
			IItem informacionEntidad = entitiesAPI.createEntity("FIRMA_DOC_EXTERNO_INFORM", rulectx.getNumExp());
			informacionEntidad.set("TRAMITE", rulectx.getTaskId());
			informacionEntidad.set("FASE", rulectx.getStageId());
			//C�digo de verificaci�n electr�nica de la firma digital.
			
			GetCVSResponse responseCsv = consulta.devolverCVS(authentication, signature);
			if(responseCsv!=null){
				String sCvs = responseCsv.getCvs();
				informacionEntidad.set("CVS", sCvs);
				documentJustificante.add(new Phrase("C�digo de Verificaci�n Electr�nica: ", fuenteNegra12));
				documentJustificante.add(new Phrase(sCvs, fuenteNegraNormal12));
				documentJustificante.add(new Phrase("\n"));
			}		
			
			/**
			 * El elemento de tipo request, representa las peticiones que se mandan a los usuarios para su firma.
			 * **/
			QueryRequestResponse informacion = consulta.recuperaDetallePeticion(authentication, idPeticion);
			Request request = informacion.getRequest();
			if(request!=null){
				
				
				//Identificador de la aplicaci�n que env�a la solicitud
				String aplicacion = request.getApplication();
				informacionEntidad.set("APPLICATION", aplicacion);
				documentJustificante.add(new Phrase("Identificador de la aplicaci�n que env�a la solicitud: ", fuenteNegra12));
				documentJustificante.add(new Phrase(aplicacion, fuenteNegraNormal12));
				documentJustificante.add(new Phrase("\n"));
				
				//Fecha sin hora en la que la petici�n entra en el portafirmas
				Calendar fEntrada = request.getFentry();
				if(fEntrada!=null){
					informacionEntidad.set("FENTRY", fEntrada.getTime());
					documentJustificante.add(new Phrase("Fecha en la que la petici�n entra en el portafirmas: ", fuenteNegra12));
					documentJustificante.add(new Phrase(FechasUtil.getFormattedDateTimeForQuery(fEntrada.getTime()), fuenteNegraNormal12));
					documentJustificante.add(new Phrase("\n"));
				}				
				
				//Fecha a partir de la cual la petici�n deja de ser v�lida. La escribe el remitente
				Calendar fExp = request.getFexpiration();
				if(fExp!=null){
					informacionEntidad.set("FEXPIRATION", fExp.getTime());
					documentJustificante.add(new Phrase("Fecha a partir de la cual la petici�n deja de ser v�lida: ", fuenteNegra12));
					documentJustificante.add(new Phrase(FechasUtil.getFormattedDateTimeForQuery(fExp.getTime()), fuenteNegraNormal12));
					documentJustificante.add(new Phrase("\n"));
				}
				
				//Fecha a partir de la cual la petici�n es v�lida. La escribe el remitente
				Calendar fStart = request.getFstart();
				if(fStart!=null){
					informacionEntidad.set("FSTART", fStart.getTime());
					documentJustificante.add(new Phrase("Fecha a partir de la cual la petici�n es v�lida: ", fuenteNegra12));
					documentJustificante.add(new Phrase(FechasUtil.getFormattedDateTimeForQuery(fStart.getTime()), fuenteNegraNormal12));
					documentJustificante.add(new Phrase("\n"));
				}				
				
				//Identificador de la solicitud. Lo genera el portafirmas cuando se crea la
				//solicitud mendiante createRequest
				String identificador = request.getIdentifier();
				informacionEntidad.set("IDENTIFIER", identificador);
				documentJustificante.add(new Phrase("Identificador de la solicitud: ", fuenteNegra12));
				documentJustificante.add(new Phrase(identificador, fuenteNegraNormal12));
				documentJustificante.add(new Phrase("\n"));
				
				//Campo que contiene una referencia que podr� ser vista por el firmante de la
				//aplicaci�n. Esta referencia no se puede usar para identificar la solicitud a trav�s de los
				//webservices. Hay que usar el campo identifier
				//String numexp = request.getReference();
				
				//Campo de conveniencia devuelto por el portafirmas indicando el estado
				//de la solicitud.
				RequestStatus requestStatus = request.getRequestStatus();
				if(requestStatus!=null){
					String estado = requestStatus.getValue();
					informacionEntidad.set("REQUESTSTATUS", estado);
					documentJustificante.add(new Phrase("Estado de la solicitud: ", fuenteNegra12));
					documentJustificante.add(new Phrase(estado, fuenteNegraNormal12));
					documentJustificante.add(new Phrase("\n"));
				}
				
				//Indica si la firma se realiza en paralelo o cascada
				SignType signType = request.getSignType();
				if(signType!=null){
					String formaFirma = signType.getValue();
					informacionEntidad.set("SIGNTYPE", formaFirma);
					documentJustificante.add(new Phrase("Forma de realizaci�n de la firma: ", fuenteNegra12));
					documentJustificante.add(new Phrase(formaFirma, fuenteNegraNormal12));
					documentJustificante.add(new Phrase("\n"));
				}				
				
				//Asunto de la solicitud.
				String subject = request.getSubject();
				informacionEntidad.set("SUBJECT", subject);
				documentJustificante.add(new Phrase("Asunto de la solicitud: ", fuenteNegra12));
				documentJustificante.add(new Phrase(subject, fuenteNegraNormal12));
				documentJustificante.add(new Phrase("\n"));
				
				//Contenido del cuerpo del mensaje
				String text = request.getText();
				informacionEntidad.set("TEXT", text);
				documentJustificante.add(new Phrase("Contenido del cuerpo del mensaje: ", fuenteNegra12));
				documentJustificante.add(new Phrase(text, fuenteNegraNormal12));
				documentJustificante.add(new Phrase("\n"));
				
				//Nivel de importancia de la petici�n
				ImportanceLevel imporLevel = request.getImportanceLevel();
				if(imporLevel!=null){
					String nivel = imporLevel.getLevelCode();
					String descripcion = imporLevel.getDescription();
					informacionEntidad.set("IMPORTANCELEVEL", nivel+" - "+descripcion);
					documentJustificante.add(new Phrase("Nivel de importancia de la petici�n: ", fuenteNegra12));
					documentJustificante.add(new Phrase(nivel, fuenteNegraNormal12));
					documentJustificante.add(new Phrase("\n"));
					documentJustificante.add(new Phrase(descripcion, fuenteNegraNormal12));
					documentJustificante.add(new Phrase("\n"));
				}
				
				//Informaci�n sobre si se quiere que la petici�n tenga sello de tiempo
				//TimestampInfo timestampInfo = request.getTimestampInfo();
				//informacionEntidad.set("TIMESTAMPINFO", timestampInfo.getAddTimestamp()+");
				
				informacionEntidad.store(cct);

				int id = informacionEntidad.getInt("ID");

				DbCnt cnt = cct.getConnection();
				
				//Lista de comentarios que los usuarios han ido a�adiendo a la petici�n
				CommentList commentList = request.getCommentList();
				if(commentList!=null){
					Comment[] vComentarios = commentList.getComment();
					if(vComentarios!=null && vComentarios.length>0){
						documentJustificante.add(new Phrase("Lista de comentarios que los usuarios han ido a�adiendo a la petici�n: ", fuenteNegra12));
					}					
					for (int i = 0; vComentarios!=null && i < vComentarios.length; i++) {
						
						Comment comment = vComentarios[i];
						//Fecha de �ltima modificaci�n
						Calendar fUltimaMod = comment.getFmodify();
						//Asunto del comentario
						String asunto = comment.getSubject();
						//Texto del comentario
						String textoCOm = comment.getTextComment();
						//Usuario que escribi� el comentario.
						UserJob usuario = comment.getUser();
						//Usuario que escribi� el comentario.
						String dniUsuairo = usuario.getIdentifier();
						
						IItemCollection itCollect = ParticipantesUtil.getParticipantes(cct, rulectx.getNumExp(), "(NDOC = '"+dniUsuairo+"')", "");
						Iterator<IItem> iterPar = itCollect.iterator();
						String nombre = "";
						while(iterPar.hasNext()){
							IItem partic = iterPar.next();
							if(partic.getString("NOMBRE")!=null) nombre = partic.getString("NOMBRE");
						}
						
						documentJustificante.add(new Phrase(dniUsuairo+" - "+nombre+": "+asunto+", "+textoCOm+", "+FechasUtil.getFormattedDateTimeForQuery(fUltimaMod.getTime()), fuenteNegraNormal12));
						documentJustificante.add(new Phrase("\n"));
						
						FirmaDocExternoInformSDAO pcftdao = new FirmaDocExternoInformSDAO(cnt);
						pcftdao.createNew(cnt);
						pcftdao.set("FIELD", "COMMENTLIST");
						pcftdao.set("REG_ID", id);
						pcftdao.set("VALUE", dniUsuairo+" - "+asunto+" - "+textoCOm+" - "+fUltimaMod);
						pcftdao.store(cnt);
					}
				}
				
				
				//Lista de documentos. El tipo documentList contiene varios elementos de
				//tipo document, que representan los datos de un documento. Cuando se recupera una petici�n, los elementos de tipo document no
				//incluyen el contenido del mismo
				DocumentList documentoList = request.getDocumentList();
				if(documentoList!=null){
					Document [] vdoc = documentoList.getDocument();
					if(vdoc!=null && vdoc.length>0){
						documentJustificante.add(new Phrase("Lista de documentos: ", fuenteNegra12));
					}
					for (int i = 0; vdoc!=null && i < vdoc.length; i++) {
						Document document = vdoc[i];
						String nombre = document.getName();
						boolean firmado = document.getSign();
						String strinFirm = "";
						if(firmado){
							strinFirm="Documento firmado";
						}
						else{
							strinFirm="Documento sin firma";
						}
						
						documentJustificante.add(new Phrase(nombre+" - "+strinFirm, fuenteNegraNormal12));
						documentJustificante.add(new Phrase("\n"));
						
						
						FirmaDocExternoInformSDAO pcftdao = new FirmaDocExternoInformSDAO(cnt);
						pcftdao.createNew(cnt);
						pcftdao.set("FIELD", "DOCUMENTLIST");
						pcftdao.set("REG_ID", id);
						pcftdao.set("VALUE", nombre+" - "+firmado);
						pcftdao.store(cnt);
					}
				}

				
				//Indica los cambios de estado en los que se ha de enviar una notificaci�n al
				//remitente de la petici�n
				//NoticeList noticeList = request.getNoticeList();
				
				//Lista de par�metros de la solicitud. Este campo existe en previsi�n de
				//usos futuros, pero actualmente no se utiliza
				ParameterList parameterList = request.getParameterList();
				if(parameterList!=null){
					Parameter [] vParam = parameterList.getParameter();
					if(vParam!=null && vParam.length>0){
						documentJustificante.add(new Phrase("Lista de par�metros de la solicitud: ", fuenteNegra12));
					}
					for (int i = 0; vParam!=null && i < vParam.length; i++) {
						Parameter parameter = vParam[i];
						String ident = parameter.getIdentifier();
						String value = parameter.getValue();
						
						documentJustificante.add(new Phrase(ident+" - "+value, fuenteNegraNormal12));
						documentJustificante.add(new Phrase("\n"));
						
						FirmaDocExternoInformSDAO pcftdao = new FirmaDocExternoInformSDAO(cnt);
						pcftdao.createNew(cnt);
						pcftdao.set("FIELD", "PARAMETERLIST");
						pcftdao.set("REG_ID", id);
						pcftdao.set("VALUE", ident+" - "+value);
						pcftdao.store(cnt);
					}
				}
				
				//Lista de remitentes de la solicitud
				/**
				 * El objeto remitterList nos permite indicar quienes son las personas que remiten una petici�n. Los
				 * remitentes deben ser usuarios v�lidos de la aplicaci�n. Aunque el modelo de datos permite poner varios
				 * remitentes, la aplicaci�n actualmente limita el n�mero a 1 remitente, que adem�s debe ser la misma
				 * aplicaci�n que se pone como origen de la petici�n
				 * **/
				RemitterList remitterList = request.getRemitterList();
				if(remitterList!=null){
					User[] vUser = remitterList.getUser();
					if(vUser!=null && vUser.length>0){
						documentJustificante.add(new Phrase("Lista de remitentes de la solicitud: ", fuenteNegra12));
					}
					for (int i = 0; vUser!=null && i < vUser.length; i++) {
						User user = vUser[i];
						String ident = user.getIdentifier();
						String nombre = user.getName();
						String apell1 = user.getSurname1();
						String apell2 = user.getSurname2();
						
						documentJustificante.add(new Phrase(ident+" - "+nombre, fuenteNegraNormal12));
						documentJustificante.add(new Phrase("\n"));
						
						FirmaDocExternoInformSDAO pcftdao = new FirmaDocExternoInformSDAO(cnt);
						pcftdao.createNew(cnt);
						pcftdao.set("FIELD", "REMITTERLIST");
						pcftdao.set("REG_ID", id);
						pcftdao.set("VALUE", ident+" - "+nombre+" "+apell1+" "+apell2);
						pcftdao.store(cnt);
					}
				}
				
				//Lista de l�neas de firma de la solicitud
				SignLineList signLineList = request.getSignLineList();
				if(signLineList!=null){
					SignLine [] vSignLine = signLineList.getSignLine();
					if(vSignLine!=null && vSignLine.length>0){
						documentJustificante.add(new Phrase("Lista de l�neas de firma de la solicitud: ", fuenteNegra12));
					}
					for (int i = 0; vSignLine!=null && i < vSignLine.length; i++) {
						SignLine signLine = vSignLine[i];
						//Lista de personas y cargos que componen la linea de firma
						SignerList signerList = signLine.getSignerList();
						if(signerList!=null){
							Signer [] vSigner = signerList.getSigner();
							
							for (int j = 0; vSigner!=null && j < vSigner.length; j++) {
								Signer signer = vSigner[j];
								//Indica la fecha del �ltimo cambio de estado. Este campo s�lo est� relleno cuando se recibe
								//informaci�n desde el servidor de portafirma, y ser� ignorado si se env�a para mdoficiar una
								//petici�n
								fechaFirma = signer.getFstate();
								//Indica el estado de la linea de firma a la que pertenece el firmante. Este campo s�lo est�
								//relleno cuando se recibe informaci�n desde el servidor y ser� ignorado si se env�a para mdoficiar
								//una petici�n.
								State state = signer.getState();
								String ident = "";
								if(state!=null){
									ident = state.getIdentifier();
								}
								//userJob es un elemento que puede representar tanto a un usuario de la aplicaci�n como a
								//un cargo.
								UserJob userJob = signer.getUserJob();
								String identFirmante = "";
								if(userJob!=null){
									identFirmante = userJob.getIdentifier();
								}
								
								String nombreFirmante = "";
								IItemCollection itParti = ParticipantesUtil.getParticipantes(cct, rulectx.getNumExp(), "(NDOC = '"+identFirmante+"')", "");
								Iterator<IItem> iteratorParti = itParti.iterator();
								while(iteratorParti.hasNext()){
									IItem partic = iteratorParti.next();
									nombreFirmante = partic.getString("NOMBRE");
								}
								
								documentJustificante.add(new Phrase("\n"));
								documentJustificante.add(new Phrase("* "+identFirmante +": "+nombreFirmante+" - "+FechasUtil.getFormattedDateTimeForQuery(fechaFirma.getTime())+" - "+ident, fuenteNegraNormal12));
								documentJustificante.add(new Phrase("\n"));
								
								FirmaDocExternoInformSDAO pcftdao = new FirmaDocExternoInformSDAO(cnt);
								pcftdao.createNew(cnt);
								pcftdao.set("FIELD", "SIGNLINELIST");
								pcftdao.set("REG_ID", id);
								pcftdao.set("VALUE", identFirmante+" - "+FechasUtil.getFormattedDateTimeForQuery(fechaFirma.getTime())+" - "+ident);
								pcftdao.store(cnt);
							}
						}
						//signLineType: Tipo de linea de firma. Acepta 2 valores:
						//	FIRMA: La linea debe ser aceptada y firmada.
						//	VISTOBUENO: La linea debe ser aceptada.
						/*SignLineType lineType = signLine.getType();
						if(lineType!=null){
							String valorFirma = lineType.getValue();
						}*/
					}
				}
				documentJustificante.close();
				
				if (fileDoc != null) {
					int documentId = DocumentosUtil.getIdTipoDocByCodigo(cct, "justif-petic");
					IItem docTramite = DocumentosUtil.generaYAnexaDocumento(rulectx, documentId, "Informe", fileDoc, "pdf");
					docTramite.store(cct);
				}
				
			}
			
		} catch (ISPACException e) {
			logger.error(e.getMessage(), e);
			throw new ISPACRuleException("Error. ",e);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			throw new ISPACRuleException("Error. ",e);
		} catch (PfirmaException e) {
			logger.error(e.getMessage(), e);
			throw new ISPACRuleException("Error. ",e);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new ISPACRuleException("Error. ",e);
		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
			throw new ISPACRuleException("Error. ",e);
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
			throw new ISPACRuleException("Error. ",e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new ISPACRuleException("Error. ",e);
		}
		return fechaFirma.getTime();
	}

	public void cancel(IRuleContext rulectx) throws ISPACRuleException {

	}
	
	 private static void agregarLineasEnBlanco(Paragraph parrafo, int nLineas) 
	    {
	        for (int i = 0; i < nLineas; i++) 
	            parrafo.add(new Paragraph(" "));
	    }

}












