package es.dipucr.sigem.api.rule.procedures.planesProvinciales;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
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
import ieci.tdw.ispac.ispaclib.gendoc.openoffice.OpenOfficeHelper;
import ieci.tdw.ispac.ispaclib.util.FileTemporaryManager;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.sun.star.awt.FontWeight;
import com.sun.star.beans.PropertyVetoException;
import com.sun.star.beans.UnknownPropertyException;
import com.sun.star.beans.XPropertySet;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.lang.WrappedTargetException;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiServiceFactory;
import com.sun.star.style.ParagraphAdjust;
import com.sun.star.table.XCell;
import com.sun.star.text.TableColumnSeparator;
import com.sun.star.text.VertOrientation;
import com.sun.star.text.XText;
import com.sun.star.text.XTextContent;
import com.sun.star.text.XTextCursor;
import com.sun.star.text.XTextDocument;
import com.sun.star.text.XTextRange;
import com.sun.star.text.XTextTable;
import com.sun.star.text.XTextTableCursor;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XInterface;
import com.sun.star.util.XSearchDescriptor;
import com.sun.star.util.XSearchable;

import es.dipucr.domain.planesProvinciales.DatosCuadroMinisterio;
import es.dipucr.services.server.planesProvinciales.PlanProvincialServicioProxy;
import es.dipucr.sigem.api.rule.common.utils.DocumentosUtil;

public class DipucrGeneraAnunciosPlanes implements IRule {

	private static final Logger logger = Logger.getLogger(DipucrGeneraAnunciosPlanes.class);
	
	public void cancel(IRuleContext rulectx) throws ISPACRuleException {
	}

	@SuppressWarnings("rawtypes")
	public Object execute(IRuleContext rulectx) throws ISPACRuleException {		
		try{
			logger.info("INICIO - DipucrGeneraAnunciosPlanes");
			// APIs
			IClientContext cct = rulectx.getClientContext();
			IEntitiesAPI entitiesAPI = cct.getAPI().getEntitiesAPI();
			
			String anio = "";
			
			String consultaAnio = "WHERE NUMEXP = '"+rulectx.getNumExp()+"'";
			IItemCollection planesProvinciales = entitiesAPI.queryEntities("DIPUCRPLANESPROVINCIALES", consultaAnio);
			Iterator it1 = (Iterator) planesProvinciales.iterator();
			if(it1.hasNext()){
				anio = ((IItem)it1.next()).getString("ANIO");
			}
			if(anio == null){
				anio = "";
			}
			
			generaAnuncioPlanCooperacion(rulectx, anio);
			generaAnuncioPlanComplementario(rulectx, anio);
			logger.info("FIN - DipucrGeneraAnunciosPlanes");
		} catch (ISPACException e) {		
			logger.error("ERROR al generar el anuncio. " + e.getMessage(), e);
			throw new ISPACRuleException("ERROR al generar el anuncio. " + e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	private void generaAnuncioPlanCooperacion(IRuleContext rulectx, String anio) throws ISPACRuleException {
		try{
			// APIs
			IClientContext cct = rulectx.getClientContext();
			IEntitiesAPI entitiesAPI = cct.getAPI().getEntitiesAPI();
			IGenDocAPI gendocAPI = cct.getAPI().getGenDocAPI();
			IProcedureAPI procedureAPI = cct.getAPI().getProcedureAPI();
			cct.endTX(true);					
			
			// Variables
			IItem entityDocument = null;
			int documentTypeId = 0;
			int templateId = 0;
			int taskId = rulectx.getTaskId();
			
			IItem processTask =  entitiesAPI.getTask(rulectx.getTaskId());
			int idTramCtl = processTask.getInt("ID_TRAM_CTL");
			
	    	int documentId = 0;
	    	Object connectorSession = null;
	    	OpenOfficeHelper ooHelper = null;
	    	
			// Comprobar que el tr�mite tenga un tipo de documento asociado y obtenerlo
        	IItemCollection taskTpDocCollection = (IItemCollection)procedureAPI.getTaskTpDoc(idTramCtl);     		
    		Iterator it = taskTpDocCollection.iterator();
    		while (it.hasNext()){
    			IItem taskTpDoc = (IItem)it.next();
    			if ((((String)taskTpDoc.get("CT_TPDOC:NOMBRE")).trim().toUpperCase()).equals(("Propuesta Anuncio").trim().toUpperCase())){
    				
    				documentTypeId = taskTpDoc.getInt("TASKTPDOC:ID_TPDOC");
    			}
    		}
    		//Asignamos el nombre del tr�time ya que si no no lo muestra
    		setSsVariables(cct, rulectx);
    		cct.setSsVariable("NOMBRE_TRAMITE", processTask.getString("NOMBRE"));
    		
    		//Comprobamos que haya encontrado el Tipo de documento
    		if (documentTypeId != 0){
        		// Comprobar que el tipo de documento tiene asociado una plantilla
	        	IItemCollection tpDocsTemplatesCollection = (IItemCollection)procedureAPI.getTpDocsTemplates(documentTypeId);
	        	if(tpDocsTemplatesCollection==null || tpDocsTemplatesCollection.toList().isEmpty()){
	        		throw new ISPACInfo(Messages.getString("error.decretos.acuses.tpDocsTemplates"));
	        	}else{
	        		Iterator docs = tpDocsTemplatesCollection.iterator();
	        		boolean encontrado= false;
	        		while (docs.hasNext() && !encontrado){
	        			IItem tpDocsTemplate = (IItem)docs.next();
	        			if(((String)tpDocsTemplate.get("NOMBRE")).trim().toUpperCase().equals("BOP - Anuncio Plan Cooperaci�n".trim().toUpperCase())){
	        				templateId = tpDocsTemplate.getInt("ID");
	        				encontrado= true;
	        			}
	        		}
	        		IItem entityDocumentT  = gendocAPI.createTaskDocument(taskId, documentTypeId);
					int documentIdT = entityDocumentT.getKeyInt();						
					
					
					IItem entityTemplateT = gendocAPI.attachTaskTemplate(connectorSession, taskId, documentIdT, templateId);
					String infoPagT = entityTemplateT.getString("INFOPAG");
					entityTemplateT.store(cct);
					
	        		
	        		entityDocument  = gendocAPI.createTaskDocument(taskId, documentTypeId);
					documentId = entityDocument.getKeyInt();

					String sFileTemplate = DocumentosUtil.getFile(cct, infoPagT, null, null).getName();
														
					// Generar el documento a partir la plantilla 
					IItem entityTemplate = gendocAPI.attachTaskTemplate(connectorSession, taskId, documentId, templateId, sFileTemplate);
											
					String docref = entityTemplate.getString("INFOPAG");
					String sMimetype = gendocAPI.getMimeType(connectorSession, docref);
					entityTemplate.set("EXTENSION", MimetypeMapping.getExtension(sMimetype));
					String templateDescripcion = entityTemplate.getString("DESCRIPCION");
					entityTemplate.set("DESCRIPCION", templateDescripcion);
					entityTemplate.set("DESTINO", cct.getSsVariable("NOMBRE"));
					
					entityTemplate.store(cct);
					
					deleteSsVariables(cct);
					cct.deleteSsVariable("NOMBRE_TRAMITE");
					
					//Abre el documento
					String extension = "odt";
					String fileName = FileTemporaryManager.getInstance().newFileName("."+extension);
					fileName = FileTemporaryManager.getInstance().getFileTemporaryPath() + "/" + fileName;
					OutputStream out = new FileOutputStream(fileName);
		    		connectorSession = gendocAPI.createConnectorSession();
					gendocAPI.getDocument(connectorSession, docref, out);
					File file = new File(fileName);
		    		ooHelper = OpenOfficeHelper.getInstance();
		    		XComponent xComponent = ooHelper.loadDocument("file://" + fileName);
		    		
		    		//Realiza los cambios
		    		insertaTabla1(rulectx, xComponent, anio);
				    
				    //Guarda el documento
					String fileNameOut = FileTemporaryManager.getInstance().newFileName(".odt");
					fileNameOut = FileTemporaryManager.getInstance().getFileTemporaryPath() + "/" + fileNameOut;
		    		OpenOfficeHelper.saveDocument(xComponent,"file://" + fileNameOut,"");
		    		File fileOut = new File(fileNameOut);
		    		InputStream in = new FileInputStream(fileOut);
		    		String mime = "application/vnd.oasis.opendocument.text";
		    		gendocAPI.setDocument(connectorSession, documentId, docref, in, (int)(fileOut.length()), mime);
		    		
		    		//Borra archivos temporales
		    		if(null != file) {
		    			file.delete();
		    		}
		    		if( null != fileOut){
		    			fileOut.delete();
		    		}
		    		
		    		if(null != entityTemplateT) {
		    			entityTemplateT.delete(cct);
		    		}
					if( null != entityDocumentT) {
						entityDocumentT.delete(cct);	
					}
					if(null != ooHelper){
			        	ooHelper.dispose();
					}
				}
    		}	
			cct.endTX(true);
		} catch(Exception e) {
	    	if (e instanceof ISPACRuleException)
			    throw new ISPACRuleException(e);
	    	throw new ISPACRuleException(e);
	    }
	}
	
	@SuppressWarnings("rawtypes")
	private void generaAnuncioPlanComplementario(IRuleContext rulectx, String anio) throws ISPACRuleException {
		try{
			// APIs
			IClientContext cct = rulectx.getClientContext();
			IEntitiesAPI entitiesAPI = cct.getAPI().getEntitiesAPI();
			IGenDocAPI gendocAPI = cct.getAPI().getGenDocAPI();
			IProcedureAPI procedureAPI = cct.getAPI().getProcedureAPI();
			cct.endTX(true);
			
			// Variables
			IItem entityDocument = null;
			int documentTypeId = 0;
			int templateId = 0;
			int taskId = rulectx.getTaskId();
			
			IItem processTask =  entitiesAPI.getTask(rulectx.getTaskId());
			int idTramCtl = processTask.getInt("ID_TRAM_CTL");
			
	    	int documentId = 0;
	    	Object connectorSession = null;
	    	OpenOfficeHelper ooHelper = null;
	    	
			// Comprobar que el tr�mite tenga un tipo de documento asociado y obtenerlo
        	IItemCollection taskTpDocCollection = (IItemCollection)procedureAPI.getTaskTpDoc(idTramCtl);     		
    		Iterator it = taskTpDocCollection.iterator();
    		while (it.hasNext()){
    			IItem taskTpDoc = (IItem)it.next();
    			if ((((String)taskTpDoc.get("CT_TPDOC:NOMBRE")).trim().toUpperCase()).equals(("Propuesta Anuncio").trim().toUpperCase())){
    				
    				documentTypeId = taskTpDoc.getInt("TASKTPDOC:ID_TPDOC");
    			}
    		}
    		//Asignamos el nombre del tr�time ya que si no no lo muestra
    		setSsVariables(cct, rulectx);
    		cct.setSsVariable("NOMBRE_TRAMITE", processTask.getString("NOMBRE"));
    		
    		//Comprobamos que haya encontrado el Tipo de documento
    		if (documentTypeId != 0){
        		// Comprobar que el tipo de documento tiene asociado una plantilla
	        	IItemCollection tpDocsTemplatesCollection = (IItemCollection)procedureAPI.getTpDocsTemplates(documentTypeId);
	        	if(tpDocsTemplatesCollection==null || tpDocsTemplatesCollection.toList().isEmpty()){
	        		throw new ISPACInfo(Messages.getString("error.decretos.acuses.tpDocsTemplates"));
	        	}else{
	        		Iterator docs = tpDocsTemplatesCollection.iterator();
	        		boolean encontrado= false;
	        		while (docs.hasNext() && !encontrado){
	        			IItem tpDocsTemplate = (IItem)docs.next();
	        			if(((String)tpDocsTemplate.get("NOMBRE")).trim().toUpperCase().equals("BOP - Anuncio Plan Complementario".trim().toUpperCase())){
	        				templateId = tpDocsTemplate.getInt("ID");
	        				encontrado= true;
	        			}
	        		}
	        		IItem entityDocumentT  = gendocAPI.createTaskDocument(taskId, documentTypeId);
					int documentIdT = entityDocumentT.getKeyInt();						
					
					
					IItem entityTemplateT = gendocAPI.attachTaskTemplate(connectorSession, taskId, documentIdT, templateId);
					String infoPagT = entityTemplateT.getString("INFOPAG");
					entityTemplateT.store(cct);
					
	        		
	        		entityDocument  = gendocAPI.createTaskDocument(taskId, documentTypeId);
					documentId = entityDocument.getKeyInt();

					String sFileTemplate = DocumentosUtil.getFile(cct, infoPagT, null, null).getName();
														
					// Generar el documento a partir la plantilla 
					IItem entityTemplate = gendocAPI.attachTaskTemplate(connectorSession, taskId, documentId, templateId, sFileTemplate);
											
					String docref = entityTemplate.getString("INFOPAG");
					String sMimetype = gendocAPI.getMimeType(connectorSession, docref);
					entityTemplate.set("EXTENSION", MimetypeMapping.getExtension(sMimetype));
					String templateDescripcion = entityTemplate.getString("DESCRIPCION");
					entityTemplate.set("DESCRIPCION", templateDescripcion);
					entityTemplate.set("DESTINO", cct.getSsVariable("NOMBRE"));
					
					entityTemplate.store(cct);
					
					deleteSsVariables(cct);
					cct.deleteSsVariable("NOMBRE_TRAMITE");
					
					//Abre el documento
					String extension = "odt";
					String fileName = FileTemporaryManager.getInstance().newFileName("."+extension);
					fileName = FileTemporaryManager.getInstance().getFileTemporaryPath() + "/" + fileName;
					OutputStream out = new FileOutputStream(fileName);
		    		connectorSession = gendocAPI.createConnectorSession();
					gendocAPI.getDocument(connectorSession, docref, out);
					File file = new File(fileName);
		    		ooHelper = OpenOfficeHelper.getInstance();
		    		XComponent xComponent = ooHelper.loadDocument("file://" + fileName);
		    		
		    		//Realiza los cambios
		    		insertaTabla2(rulectx, xComponent, anio);
				    
				    //Guarda el documento
					String fileNameOut = FileTemporaryManager.getInstance().newFileName(".odt");
					fileNameOut = FileTemporaryManager.getInstance().getFileTemporaryPath() + "/" + fileNameOut;
		    		OpenOfficeHelper.saveDocument(xComponent,"file://" + fileNameOut,"");
		    		File fileOut = new File(fileNameOut);
		    		InputStream in = new FileInputStream(fileOut);
		    		String mime = "application/vnd.oasis.opendocument.text";
		    		gendocAPI.setDocument(connectorSession, documentId, docref, in, (int)(fileOut.length()), mime);
		    		
		    		//Borra archivos temporales
		    		if(null != file) {
		    			file.delete();
		    		}
		    		if(null != fileOut) {
		    			fileOut.delete();
		    		}
		    		
		    		if(null != entityTemplateT) {
		    			entityTemplateT.delete(cct);
		    		}
					if(null != entityDocumentT) {
						entityDocumentT.delete(cct);	
					}

					if(null != ooHelper){
			        	ooHelper.dispose();
					}
	        	}
    		}	
			cct.endTX(true);
		} catch(Exception e) {
	    	if (e instanceof ISPACRuleException)
			    throw new ISPACRuleException(e);
	    	throw new ISPACRuleException(e);
	    }
	}

	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}
	
	private void insertaTabla1(IRuleContext rulectx , XComponent xComponent, String anio) throws ISPACException, Exception
	{
        PlanProvincialServicioProxy planService;
        planService = new PlanProvincialServicioProxy();
        DatosCuadroMinisterio[] datosCuadroMinisterio = planService.getCuadroA(Integer.parseInt(anio));
        DatosCuadroMinisterio[] totalDatosCuadroMinisterio = planService.getTotalesCuadroA(Integer.parseInt(anio));        
        
		if (datosCuadroMinisterio.length>0){
		    //Busca la posici�n de la tabla y coloca el cursor ah�
			//Usaremos el localizador %TABLA4%
			XTextDocument xTextDocument = (XTextDocument)UnoRuntime.queryInterface(XTextDocument.class, xComponent);
		    XText xText = xTextDocument.getText();
	        XSearchable xSearchable = (XSearchable) UnoRuntime.queryInterface( XSearchable.class, xComponent);
	        XSearchDescriptor xSearchDescriptor = xSearchable.createSearchDescriptor();
	        xSearchDescriptor.setSearchString("%TABLA1%");
	        XInterface xSearchInterface = null;
	        XTextRange xSearchTextRange = null;
//		        XTextTableCursor xtc = null;
	        xSearchInterface = (XInterface)xSearchable.findFirst(xSearchDescriptor);
	        if (xSearchInterface != null) 
	        {
	        	//Cadena encontrada, la borro antes de insertar la tabla
		        xSearchTextRange = (XTextRange) UnoRuntime.queryInterface(XTextRange.class, xSearchInterface);
		        xSearchTextRange.setString("");
		        
				//Inserta una tabla de 9 columnas y tantas filas
			    //como nuevas liquidaciones haya mas una de cabecera
				XMultiServiceFactory xDocMSF = (XMultiServiceFactory) UnoRuntime.queryInterface(XMultiServiceFactory.class, xTextDocument);
				Object xObject = xDocMSF.createInstance("com.sun.star.text.TextTable");
				XTextTable xTable = (XTextTable) UnoRuntime.queryInterface(XTextTable.class, xObject);
				
				
				//Calculamos las filas, son 3 seguras de la cabecera, m�s a partir de 8*2 3 m�s por cada 14*2 m�s una de los totales
				//A�adimos 4 filas m�s para las 3 de la cabecera de la tabla y uno para la celda final
				int filas = datosCuadroMinisterio.length*2;
				int filasTotal = filas;
				//primera cabecera
				filasTotal += 3;
//				//Si hay m�s de 8 municipios otra cabecera
//				if(filas>=16){
//					filasTotal += 3;
//					filas -= 16;
//				}
//				//Vemos cuantos grupos de 14 hay, una cabecera por cada uno
//				int grupos = filas / 28;
//				filasTotal += grupos * 3;
				
				//A�adimos los totales
				filasTotal++;
				
				xTable.initialize(filasTotal, 11);
				
//					xTable.initialize((datosCuadroMinisterio.length*2)+4, 11);
				
				XTextContent xTextContent = (XTextContent) UnoRuntime.queryInterface(XTextContent.class, xTable);
				xText.insertTextContent(xSearchTextRange, xTextContent, false);
	
				colocaColumnas(xTable);
				
				Float tamanioFuente = new Float(7.0);
				
//					xtc = aniadirCabecera(xTable, tamanioFuente, indexFila, xtc);
				
				setHeaderCellText(xTable, "A1", "OBRA", tamanioFuente, ParagraphAdjust.CENTER);				
				setHeaderCellText(xTable, "E1", "PROGRAMA DE FINANCIACI�N", tamanioFuente, ParagraphAdjust.CENTER);
				
				XTextTableCursor xtc = xTable.createCursorByCellName("A1");
				xtc.goRight((short) 3, true);
				xtc.mergeRange();				
				xtc = xTable.createCursorByCellName("B1");
				xtc.goRight((short) 6, true);
				xtc.mergeRange();
				
				setHeaderCellText(xTable, "A2", "N�m.", tamanioFuente, ParagraphAdjust.CENTER);
				setHeaderCellText(xTable, "B2", "a) C�digo y denominaci�n de la obra", tamanioFuente, ParagraphAdjust.LEFT);
				setHeaderCellText(xTable, "C2", "Puntos\nkilom�tricos", tamanioFuente, ParagraphAdjust.CENTER);
				setHeaderCellText(xTable, "E2", "Presupuesto", tamanioFuente, ParagraphAdjust.CENTER);
				setHeaderCellText(xTable, "F2", "PART�CIPES", tamanioFuente, ParagraphAdjust.CENTER);		
		
				setHeaderCellText(xTable, "B3", "b) C�digo y denominaci�n del municipio", tamanioFuente, ParagraphAdjust.LEFT);				
				setHeaderCellText(xTable, "C3", "Inicial", tamanioFuente, ParagraphAdjust.CENTER);
				setHeaderCellText(xTable, "D3", "Final", tamanioFuente, ParagraphAdjust.CENTER);
				setHeaderCellText(xTable, "F3", "Administraci�n\nGeneral del\nEstado (MPT)", tamanioFuente, ParagraphAdjust.CENTER);
				setHeaderCellText(xTable, "G3", "Comunidad\nAut�noma", tamanioFuente, ParagraphAdjust.CENTER);
				setHeaderCellText(xTable, "H3", "Diputaci�n\nProvincial", tamanioFuente, ParagraphAdjust.CENTER);
				setHeaderCellText(xTable, "I3", "Ayuntamiento", tamanioFuente, ParagraphAdjust.CENTER);
				setHeaderCellText(xTable, "J3", "Uni�n Europea", tamanioFuente, ParagraphAdjust.CENTER);
				setHeaderCellText(xTable, "K3", "Otros", tamanioFuente, ParagraphAdjust.CENTER);
		
				xtc = xTable.createCursorByCellName("A2");
				xtc.goRight((short) 11, true);
				xtc.mergeRange();
				xtc = xTable.createCursorByCellName("E2");
				xtc.goRight((short) 11, true);
				xtc.mergeRange();
				xtc = xTable.createCursorByCellName("C2");
				xtc.goRight((short) 1, true);
				xtc.mergeRange();
				xtc = xTable.createCursorByCellName("E2");
				xtc.goRight((short) 5, true);
				xtc.mergeRange();
				
				//Rellena la tabla con los datos de las liquidaciones
				NumberFormat df = NumberFormat.getCurrencyInstance();
				
				int i=4;
				for(int contadorMunicipio = 0; contadorMunicipio<datosCuadroMinisterio.length;contadorMunicipio++){	
					DatosCuadroMinisterio cuadro = datosCuadroMinisterio[contadorMunicipio];						
					String nombre = cuadro.getMunicipio();
					if(nombre.toUpperCase().contains("�")){						        		
		        		nombre = nombre.replace("�", "�");						        		
		        	}
					
					setCellText(xTable, "A"+String.valueOf(i), "" +(contadorMunicipio+1), tamanioFuente, ParagraphAdjust.CENTER);
					setCellText(xTable, "B"+String.valueOf(i), "a) " + cuadro.getDenoobra(), tamanioFuente, ParagraphAdjust.LEFT);
					xtc = xTable.createCursorByCellName("A"+i);
					xtc.goRight((short) 11, true);
					xtc.mergeRange();
					xtc = xTable.createCursorByCellName("B"+String.valueOf(i));
					xtc.goRight((short) 9, true);
					xtc.mergeRange();
					i++;
					setCellText(xTable, "B"+String.valueOf(i), "b) " + nombre, tamanioFuente, ParagraphAdjust.LEFT);
					setCellText(xTable, "E"+String.valueOf(i), "" + df.format(cuadro.getPresupuesto()), tamanioFuente, ParagraphAdjust.RIGHT);
					setCellText(xTable, "F"+String.valueOf(i), "" + df.format(cuadro.getEstado()), tamanioFuente, ParagraphAdjust.RIGHT);					
					setCellText(xTable, "H"+String.valueOf(i), "" + df.format(cuadro.getDiputacion()), tamanioFuente, ParagraphAdjust.RIGHT);
					setCellText(xTable, "I"+String.valueOf(i), "" + df.format(cuadro.getAyuntamiento()), tamanioFuente, ParagraphAdjust.RIGHT);
					i++;	
					/**if(contadorMunicipio+1 == 8 && datosCuadroMinisterio.length>contadorMunicipio+1){
						//xtc = aniadirCabecera(xTable, tamanioFuente, i, xtc);
//							i +=3;
						setHeaderCellText(xTable, "A"+i, "OBRA", tamanioFuente, ParagraphAdjust.CENTER);				
						setHeaderCellText(xTable, "E"+i, "PROGRAMA DE FINANCIACI�N", tamanioFuente, ParagraphAdjust.CENTER);
						
						xtc = xTable.createCursorByCellName("A"+i);
						xtc.goRight((short) 3, true);
						xtc.mergeRange();				
						xtc = xTable.createCursorByCellName("B"+i);
						xtc.goRight((short) 6, true);
						xtc.mergeRange();
						
						i++;
						
						setHeaderCellText(xTable, "A"+i, "N�m.", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "B"+i, "a) C�digo y denominaci�n de la obra", tamanioFuente, ParagraphAdjust.LEFT);
						setHeaderCellText(xTable, "C"+i, "Puntos\nkilom�tricos", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "E"+i, "Presupuesto", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "F"+i, "PART�CIPES", tamanioFuente, ParagraphAdjust.CENTER);		
				
						i++;
						
						setHeaderCellText(xTable, "B"+i, "b) C�digo y denominaci�n del municipio", tamanioFuente, ParagraphAdjust.LEFT);				
						setHeaderCellText(xTable, "C"+i, "Inicial", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "D"+i, "Final", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "F"+i, "Administraci�n\nGeneral del\nEstado (MPT)", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "G"+i, "Comunidad\nAut�noma", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "H"+i, "Diputaci�n\nProvincial", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "I"+i, "Ayuntamiento", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "J"+i, "Uni�n Europea", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "K"+i, "Otros", tamanioFuente, ParagraphAdjust.CENTER);
				
						xtc = xTable.createCursorByCellName("A"+(i-1));
						xtc.goRight((short) 11, true);
						xtc.mergeRange();
						xtc = xTable.createCursorByCellName("E"+(i-1));
						xtc.goRight((short) 11, true);
						xtc.mergeRange();
						xtc = xTable.createCursorByCellName("C"+(i-1));
						xtc.goRight((short) 1, true);
						xtc.mergeRange();
						xtc = xTable.createCursorByCellName("E"+(i-1));
						xtc.goRight((short) 5, true);
						xtc.mergeRange();
						i++;
					}
					else if(contadorMunicipio+1-8>=14 && (contadorMunicipio+1-8!=7 && (contadorMunicipio+1-8)%14 == 0)){
						//xtc = aniadirCabecera(xTable, tamanioFuente, i, xtc);
//							i +=3;
						setHeaderCellText(xTable, "A"+i, "OBRA", tamanioFuente, ParagraphAdjust.CENTER);				
						setHeaderCellText(xTable, "E"+i, "PROGRAMA DE FINANCIACI�N", tamanioFuente, ParagraphAdjust.CENTER);
						
						xtc = xTable.createCursorByCellName("A"+i);
						xtc.goRight((short) 3, true);
						xtc.mergeRange();				
						xtc = xTable.createCursorByCellName("B"+i);
						xtc.goRight((short) 6, true);
						xtc.mergeRange();
						
						i++;
						
						setHeaderCellText(xTable, "A"+i, "N�m.", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "B"+i, "a) C�digo y denominaci�n de la obra", tamanioFuente, ParagraphAdjust.LEFT);
						setHeaderCellText(xTable, "C"+i, "Puntos\nkilom�tricos", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "E"+i, "Presupuesto", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "F"+i, "PART�CIPES", tamanioFuente, ParagraphAdjust.CENTER);		
				
						i++;
						
						setHeaderCellText(xTable, "B"+i, "b) C�digo y denominaci�n del municipio", tamanioFuente, ParagraphAdjust.LEFT);				
						setHeaderCellText(xTable, "C"+i, "Inicial", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "D"+i, "Final", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "F"+i, "Administraci�n\nGeneral del\nEstado (MPT)", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "G"+i, "Comunidad\nAut�noma", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "H"+i, "Diputaci�n\nProvincial", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "I"+i, "Ayuntamiento", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "J"+i, "Uni�n Europea", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "K"+i, "Otros", tamanioFuente, ParagraphAdjust.CENTER);
				
						xtc = xTable.createCursorByCellName("A"+(i-1));
						xtc.goRight((short) 11, true);
						xtc.mergeRange();
						xtc = xTable.createCursorByCellName("E"+(i-1));
						xtc.goRight((short) 11, true);
						xtc.mergeRange();
						xtc = xTable.createCursorByCellName("C"+(i-1));
						xtc.goRight((short) 1, true);
						xtc.mergeRange();
						xtc = xTable.createCursorByCellName("E"+(i-1));
						xtc.goRight((short) 5, true);
						xtc.mergeRange();
						i++;
					}**/
				}
				
				//A�adimos la celda final
				DatosCuadroMinisterio total = totalDatosCuadroMinisterio[0];

				setCellTextLasRow(xTable, "A"+String.valueOf(i), total.getMunicipio(), tamanioFuente, ParagraphAdjust.RIGHT);				
				setCellTextLasRow(xTable, "E"+String.valueOf(i), ""+df.format(total.getPresupuesto()), tamanioFuente, ParagraphAdjust.RIGHT);
				setCellTextLasRow(xTable, "F"+String.valueOf(i), ""+df.format(total.getEstado()), tamanioFuente, ParagraphAdjust.RIGHT);
				setCellTextLasRow(xTable, "H"+String.valueOf(i), ""+df.format(total.getDiputacion()), tamanioFuente, ParagraphAdjust.RIGHT);
				setCellTextLasRow(xTable, "I"+String.valueOf(i), ""+df.format(total.getAyuntamiento()), tamanioFuente, ParagraphAdjust.RIGHT);
				
				xtc = xTable.createCursorByCellName("A"+String.valueOf(i));
				xtc.goRight((short) 3, true);
				xtc.mergeRange();				
	        }
		}
	}
	
	private void insertaTabla2(IRuleContext rulectx , XComponent xComponent, String anio) throws ISPACException, Exception
	{
        PlanProvincialServicioProxy planService;
        planService = new PlanProvincialServicioProxy();
        DatosCuadroMinisterio[] datosCuadroMinisterio = planService.getCuadroC(Integer.parseInt(anio));
        DatosCuadroMinisterio[] totalDatosCuadroMinisterio = planService.getTotalesCuadroC(Integer.parseInt(anio));        
        
		if (datosCuadroMinisterio.length>0){
		    //Busca la posici�n de la tabla y coloca el cursor ah�
			//Usaremos el localizador %TABLA5%
			XTextDocument xTextDocument = (XTextDocument)UnoRuntime.queryInterface(XTextDocument.class, xComponent);
		    XText xText = xTextDocument.getText();
	        XSearchable xSearchable = (XSearchable) UnoRuntime.queryInterface( XSearchable.class, xComponent);
	        XSearchDescriptor xSearchDescriptor = xSearchable.createSearchDescriptor();
	        xSearchDescriptor.setSearchString("%TABLA1%");
	        XInterface xSearchInterface = null;
	        XTextRange xSearchTextRange = null;	 
//		        XTextTableCursor xtc = null;
	        xSearchInterface = (XInterface)xSearchable.findFirst(xSearchDescriptor);
	        if (xSearchInterface != null) 
	        {
	        	//Cadena encontrada, la borro antes de insertar la tabla
		        xSearchTextRange = (XTextRange) UnoRuntime.queryInterface(XTextRange.class, xSearchInterface);
		        xSearchTextRange.setString("");
		        
				//Inserta una tabla de 9 columnas y tantas filas
			    //como nuevas liquidaciones haya mas una de cabecera
				XMultiServiceFactory xDocMSF = (XMultiServiceFactory) UnoRuntime.queryInterface(XMultiServiceFactory.class, xTextDocument);
				Object xObject = xDocMSF.createInstance("com.sun.star.text.TextTable");
				XTextTable xTable = (XTextTable) UnoRuntime.queryInterface(XTextTable.class, xObject);
				
				
				//Calculamos las filas, son 3 seguras de la cabecera, m�s a partir de 8*2 3 m�s por cada 14*2 m�s una de los totales
				//A�adimos 4 filas m�s para las 3 de la cabecera de la tabla y uno para la celda final
				int filas = datosCuadroMinisterio.length*2;
				int filasTotal = filas;
				//primera cabecera
				filasTotal += 3;
//				//Si hay m�s de 8 municipios otra cabecera
//				if(filas>=16){
//					filasTotal += 3;
//					filas -= 16;
//				}
//				//Vemos cuantos grupos de 14 hay, una cabecera por cada uno
//				int grupos = filas / 28;
//				filasTotal += grupos * 3;
				
				//A�adimos los totales
				filasTotal++;
				
				xTable.initialize(filasTotal, 11);
				
				XTextContent xTextContent = (XTextContent) UnoRuntime.queryInterface(XTextContent.class, xTable);
				xText.insertTextContent(xSearchTextRange, xTextContent, false);
	
				colocaColumnas(xTable);
				
				Float tamanioFuente = new Float(7.0);
				
//					xtc = aniadirCabecera(xTable, tamanioFuente, indexFila, xtc);
				
				setHeaderCellText(xTable, "A1", "OBRA", tamanioFuente, ParagraphAdjust.CENTER);				
				setHeaderCellText(xTable, "E1", "PROGRAMA DE FINANCIACI�N", tamanioFuente, ParagraphAdjust.CENTER);
				
				XTextTableCursor xtc = xTable.createCursorByCellName("A1");
				xtc.goRight((short) 3, true);
				xtc.mergeRange();				
				xtc = xTable.createCursorByCellName("B1");
				xtc.goRight((short) 6, true);
				xtc.mergeRange();
				
				setHeaderCellText(xTable, "A2", "N�m.", tamanioFuente, ParagraphAdjust.CENTER);
				setHeaderCellText(xTable, "B2", "a) C�digo y denominaci�n de la obra", tamanioFuente, ParagraphAdjust.LEFT);
				setHeaderCellText(xTable, "C2", "Puntos\nkilom�tricos", tamanioFuente, ParagraphAdjust.CENTER);
				setHeaderCellText(xTable, "E2", "Presupuesto", tamanioFuente, ParagraphAdjust.CENTER);
				setHeaderCellText(xTable, "F2", "PART�CIPES", tamanioFuente, ParagraphAdjust.CENTER);		
		
				setHeaderCellText(xTable, "B3", "b) C�digo y denominaci�n del municipio", tamanioFuente, ParagraphAdjust.LEFT);				
				setHeaderCellText(xTable, "C3", "Inicial", tamanioFuente, ParagraphAdjust.CENTER);
				setHeaderCellText(xTable, "D3", "Final", tamanioFuente, ParagraphAdjust.CENTER);
				setHeaderCellText(xTable, "F3", "Administraci�n\nGeneral del\nEstado (MPT)", tamanioFuente, ParagraphAdjust.CENTER);
				setHeaderCellText(xTable, "G3", "Comunidad\nAut�noma", tamanioFuente, ParagraphAdjust.CENTER);
				setHeaderCellText(xTable, "H3", "Diputaci�n\nProvincial", tamanioFuente, ParagraphAdjust.CENTER);
				setHeaderCellText(xTable, "I3", "Ayuntamiento", tamanioFuente, ParagraphAdjust.CENTER);
				setHeaderCellText(xTable, "J3", "Uni�n Europea", tamanioFuente, ParagraphAdjust.CENTER);
				setHeaderCellText(xTable, "K3", "Otros", tamanioFuente, ParagraphAdjust.CENTER);
		
				xtc = xTable.createCursorByCellName("A2");
				xtc.goRight((short) 11, true);
				xtc.mergeRange();
				xtc = xTable.createCursorByCellName("E2");
				xtc.goRight((short) 11, true);
				xtc.mergeRange();
				xtc = xTable.createCursorByCellName("C2");
				xtc.goRight((short) 1, true);
				xtc.mergeRange();
				xtc = xTable.createCursorByCellName("E2");
				xtc.goRight((short) 5, true);
				xtc.mergeRange();
				
				//Rellena la tabla con los datos de las liquidaciones
				NumberFormat df = NumberFormat.getCurrencyInstance();
				
				int i=4;
				for(int contadorMunicipio = 0; contadorMunicipio<datosCuadroMinisterio.length;contadorMunicipio++){		
					DatosCuadroMinisterio cuadro = datosCuadroMinisterio[contadorMunicipio];						
					String nombre = cuadro.getMunicipio();
					if(nombre.toUpperCase().contains("�")){						        		
		        		nombre = nombre.replace("�", "�");						        		
		        	}
					
					setCellText(xTable, "A"+String.valueOf(i), "" +(contadorMunicipio+1), tamanioFuente, ParagraphAdjust.CENTER);
					setCellText(xTable, "B"+String.valueOf(i), "a) " + cuadro.getDenoobra(), tamanioFuente, ParagraphAdjust.LEFT);
					xtc = xTable.createCursorByCellName("A"+i);
					xtc.goRight((short) 11, true);
					xtc.mergeRange();
					xtc = xTable.createCursorByCellName("B"+String.valueOf(i));
					xtc.goRight((short) 9, true);
					xtc.mergeRange();
					i++;
					setCellText(xTable, "B"+String.valueOf(i), "b) " + nombre, tamanioFuente, ParagraphAdjust.LEFT);
					setCellText(xTable, "E"+String.valueOf(i), "" + df.format(cuadro.getPresupuesto()), tamanioFuente, ParagraphAdjust.RIGHT);
					setCellText(xTable, "F"+String.valueOf(i), "" + df.format(cuadro.getEstado()), tamanioFuente, ParagraphAdjust.RIGHT);					
					setCellText(xTable, "H"+String.valueOf(i), "" + df.format(cuadro.getDiputacion()), tamanioFuente, ParagraphAdjust.RIGHT);
					setCellText(xTable, "I"+String.valueOf(i), "" + df.format(cuadro.getAyuntamiento()), tamanioFuente, ParagraphAdjust.RIGHT);
					i++;	
					/**if(contadorMunicipio+1 == 8 && datosCuadroMinisterio.length>contadorMunicipio+1){
						//xtc = aniadirCabecera(xTable, tamanioFuente, i, xtc);
//							i +=3;
						setHeaderCellText(xTable, "A"+i, "OBRA", tamanioFuente, ParagraphAdjust.CENTER);				
						setHeaderCellText(xTable, "E"+i, "PROGRAMA DE FINANCIACI�N", tamanioFuente, ParagraphAdjust.CENTER);
						
						xtc = xTable.createCursorByCellName("A"+i);
						xtc.goRight((short) 3, true);
						xtc.mergeRange();				
						xtc = xTable.createCursorByCellName("B"+i);
						xtc.goRight((short) 6, true);
						xtc.mergeRange();
						
						i++;
						
						setHeaderCellText(xTable, "A"+i, "N�m.", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "B"+i, "a) C�digo y denominaci�n de la obra", tamanioFuente, ParagraphAdjust.LEFT);
						setHeaderCellText(xTable, "C"+i, "Puntos\nkilom�tricos", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "E"+i, "Presupuesto", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "F"+i, "PART�CIPES", tamanioFuente, ParagraphAdjust.CENTER);		
				
						i++;
						
						setHeaderCellText(xTable, "B"+i, "b) C�digo y denominaci�n del municipio", tamanioFuente, ParagraphAdjust.LEFT);				
						setHeaderCellText(xTable, "C"+i, "Inicial", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "D"+i, "Final", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "F"+i, "Administraci�n\nGeneral del\nEstado (MPT)", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "G"+i, "Comunidad\nAut�noma", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "H"+i, "Diputaci�n\nProvincial", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "I"+i, "Ayuntamiento", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "J"+i, "Uni�n Europea", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "K"+i, "Otros", tamanioFuente, ParagraphAdjust.CENTER);
				
						xtc = xTable.createCursorByCellName("A"+(i-1));
						xtc.goRight((short) 11, true);
						xtc.mergeRange();
						xtc = xTable.createCursorByCellName("E"+(i-1));
						xtc.goRight((short) 11, true);
						xtc.mergeRange();
						xtc = xTable.createCursorByCellName("C"+(i-1));
						xtc.goRight((short) 1, true);
						xtc.mergeRange();
						xtc = xTable.createCursorByCellName("E"+(i-1));
						xtc.goRight((short) 5, true);
						xtc.mergeRange();
						i++;
					}
					else if(contadorMunicipio+1-8>=14 && (contadorMunicipio+1-8!=7 && (contadorMunicipio+1-8)%14 == 0)){
						//xtc = aniadirCabecera(xTable, tamanioFuente, i, xtc);
//							i +=3;
						setHeaderCellText(xTable, "A"+i, "OBRA", tamanioFuente, ParagraphAdjust.CENTER);				
						setHeaderCellText(xTable, "E"+i, "PROGRAMA DE FINANCIACI�N", tamanioFuente, ParagraphAdjust.CENTER);
						
						xtc = xTable.createCursorByCellName("A"+i);
						xtc.goRight((short) 3, true);
						xtc.mergeRange();				
						xtc = xTable.createCursorByCellName("B"+i);
						xtc.goRight((short) 6, true);
						xtc.mergeRange();
						
						i++;
						
						setHeaderCellText(xTable, "A"+i, "N�m.", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "B"+i, "a) C�digo y denominaci�n de la obra", tamanioFuente, ParagraphAdjust.LEFT);
						setHeaderCellText(xTable, "C"+i, "Puntos\nkilom�tricos", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "E"+i, "Presupuesto", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "F"+i, "PART�CIPES", tamanioFuente, ParagraphAdjust.CENTER);		
				
						i++;
						
						setHeaderCellText(xTable, "B"+i, "b) C�digo y denominaci�n del municipio", tamanioFuente, ParagraphAdjust.LEFT);				
						setHeaderCellText(xTable, "C"+i, "Inicial", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "D"+i, "Final", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "F"+i, "Administraci�n\nGeneral del\nEstado (MPT)", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "G"+i, "Comunidad\nAut�noma", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "H"+i, "Diputaci�n\nProvincial", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "I"+i, "Ayuntamiento", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "J"+i, "Uni�n Europea", tamanioFuente, ParagraphAdjust.CENTER);
						setHeaderCellText(xTable, "K"+i, "Otros", tamanioFuente, ParagraphAdjust.CENTER);
				
						xtc = xTable.createCursorByCellName("A"+(i-1));
						xtc.goRight((short) 11, true);
						xtc.mergeRange();
						xtc = xTable.createCursorByCellName("E"+(i-1));
						xtc.goRight((short) 11, true);
						xtc.mergeRange();
						xtc = xTable.createCursorByCellName("C"+(i-1));
						xtc.goRight((short) 1, true);
						xtc.mergeRange();
						xtc = xTable.createCursorByCellName("E"+(i-1));
						xtc.goRight((short) 5, true);
						xtc.mergeRange();
						i++;
					}**/
				}
				
				//A�adimos la celda final
				DatosCuadroMinisterio total = totalDatosCuadroMinisterio[0];

				setCellTextLasRow(xTable, "A"+String.valueOf(i), total.getMunicipio(), tamanioFuente, ParagraphAdjust.RIGHT);				
				setCellTextLasRow(xTable, "E"+String.valueOf(i), ""+df.format(total.getPresupuesto()), tamanioFuente, ParagraphAdjust.RIGHT);
				setCellTextLasRow(xTable, "F"+String.valueOf(i), ""+df.format(total.getEstado()), tamanioFuente, ParagraphAdjust.RIGHT);
				setCellTextLasRow(xTable, "H"+String.valueOf(i), ""+df.format(total.getDiputacion()), tamanioFuente, ParagraphAdjust.RIGHT);
				setCellTextLasRow(xTable, "I"+String.valueOf(i), ""+df.format(total.getAyuntamiento()), tamanioFuente, ParagraphAdjust.RIGHT);
				
				xtc = xTable.createCursorByCellName("A"+String.valueOf(i));
				xtc.goRight((short) 3, true);
				xtc.mergeRange();				
	        }
		}
	}
	
    
    private void setHeaderCellText(XTextTable xTextTable, String CellName, String strText, Float tamanioFuente, ParagraphAdjust alineacion) throws Exception 
    {
    	XCell xCell = xTextTable.getCellByName(CellName);
		XText xCellText = (XText) UnoRuntime.queryInterface(XText.class, xTextTable.getCellByName(CellName));

		//Propiedades		
		XTextCursor xTC = xCellText.createTextCursor();
		XPropertySet xTPS = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, xTC);
		xTPS.setPropertyValue("CharFontName", new String("Arial"));
		xTPS.setPropertyValue("CharHeight", tamanioFuente);	
		xTPS.setPropertyValue("CharWeight", new Float(FontWeight.BOLD));
		xTPS.setPropertyValue("ParaAdjust", alineacion);
//			xTPS.setPropertyValue("ParaVertAlignment", alineacionVertical);
		xTPS.setPropertyValue("ParaTopMargin", new Short((short)60));
		xTPS.setPropertyValue("ParaBottomMargin", new Short((short)60));
		XPropertySet xCPS = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, xCell);
		xCPS.setPropertyValue("VertOrient", new Short(VertOrientation.CENTER));
		xCPS.setPropertyValue("BackColor", new Integer(0xC0C0C0));
		
		//Texto de la celda
		xCellText.setString(strText);
	}	

    private void setCellText(XTextTable xTextTable, String CellName, String strText, Float tamanioFuente, ParagraphAdjust alineacion) throws Exception 
    {
    	XCell xCell = xTextTable.getCellByName(CellName);    	
		XText xCellText = (XText) UnoRuntime.queryInterface(XText.class, xCell);

		//Propiedades
		XTextCursor xTC = xCellText.createTextCursor();
		XPropertySet xTPS = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, xTC);
		xTPS.setPropertyValue("CharFontName", new String("Arial"));
		xTPS.setPropertyValue("CharHeight", tamanioFuente);	
		xTPS.setPropertyValue("ParaAdjust", alineacion);
//			xTPS.setPropertyValue("ParaVertAlignment", alineacionVertical);
		xTPS.setPropertyValue("ParaTopMargin", new Short((short)0));
		xTPS.setPropertyValue("ParaBottomMargin", new Short((short)0));
		XPropertySet xCPS = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, xCell);
		xCPS.setPropertyValue("VertOrient", new Short(VertOrientation.CENTER));

		//Texto de la celda
		xCellText.setString(strText);
	}
    
    private void setCellTextLasRow(XTextTable xTextTable, String CellName, String strText, Float tamanioFuente, ParagraphAdjust alineacion) throws Exception 
    {
    	XCell xCell = xTextTable.getCellByName(CellName);
		XText xCellText = (XText) UnoRuntime.queryInterface(XText.class, xCell);

		//Propiedades
		XTextCursor xTC = xCellText.createTextCursor();
		XPropertySet xTPS = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, xTC);
		xTPS.setPropertyValue("CharFontName", new String("Arial"));
		xTPS.setPropertyValue("CharHeight", tamanioFuente);
		xTPS.setPropertyValue("CharWeight", new Float(FontWeight.BOLD));
		xTPS.setPropertyValue("ParaAdjust", alineacion);
//			xTPS.setPropertyValue("ParaVertAlignment", alineacionVertical);
		xTPS.setPropertyValue("ParaTopMargin", new Short((short)0));
		xTPS.setPropertyValue("ParaBottomMargin", new Short((short)0));
		XPropertySet xCPS = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, xCell);
		xCPS.setPropertyValue("VertOrient", new Short(VertOrientation.CENTER));

		//Texto de la celda
		xCellText.setString(strText);
	}
    private void colocaColumnas(XTextTable xTextTable){
    	
		XPropertySet xPS = ( XPropertySet ) UnoRuntime.queryInterface(XPropertySet.class, xTextTable);
		 
		// Get table Width and TableColumnRelativeSum properties values
		int iWidth;
		try {
			iWidth = ( Integer ) xPS.getPropertyValue( "Width" );
			
    		short sTableColumnRelativeSum = ( Short ) xPS.getPropertyValue( "TableColumnRelativeSum" );
    		 
    		// Get table column separators
    		Object xObj = xPS.getPropertyValue( "TableColumnSeparators" );
    		 
    		TableColumnSeparator[] xSeparators = ( TableColumnSeparator[] )UnoRuntime.queryInterface(
    		    TableColumnSeparator[].class, xObj );

    		
    		//Calculamos el tama�o que le queremos dar a la celda
    		//Se empieza colocando de la �ltima a la primera, por eso las primeras 7 son iguales 
    		double dRatio = ( double ) sTableColumnRelativeSum / ( double ) iWidth;
    		double dRelativeWidth = ( double ) 9000 * dRatio;
    		
    		// Last table column separator position
    		double dPosition = sTableColumnRelativeSum - dRelativeWidth;
    		 
    		// Set set new position for all column separators        
    		//N�mero de separadores
    		int i = xSeparators.length - 1;
    		//Las 8 �litmas columnas miden los mismo
    		xSeparators[i].Position = (short) Math.ceil( dPosition );
    		dPosition -= dRelativeWidth;
    		i--;
    		xSeparators[i].Position = (short) Math.ceil( dPosition );
    		dPosition -= dRelativeWidth;
    		i--;
    		xSeparators[i].Position = (short) Math.ceil( dPosition );
    		dPosition -= dRelativeWidth;
    		i--;
    		xSeparators[i].Position = (short) Math.ceil( dPosition );
    		dPosition -= dRelativeWidth;
    		i--;
    		xSeparators[i].Position = (short) Math.ceil( dPosition );
    		dPosition -= dRelativeWidth;
    		i--;
    		xSeparators[i].Position = (short) Math.ceil( dPosition );
    		dPosition -= dRelativeWidth;
    		i--;
    		xSeparators[i].Position = (short) Math.ceil( dPosition );
    		i--;
    		
    		//Las columnas de los puntos kilom�tricosvan a medir menos 7 a cada una
    		dRelativeWidth = ( double ) 7000 * dRatio;
    		dPosition -= dRelativeWidth;
    		xSeparators[i].Position = (short) Math.ceil( dPosition );
    		dPosition -= dRelativeWidth;
    		i--;
    		xSeparators[i].Position = (short) Math.ceil( dPosition );
    		i--;
    		
//		    		el Nombre del municipio = 32
    		dRelativeWidth = ( double ) 32000 * dRatio;
    		dPosition -= dRelativeWidth;
    		xSeparators[i].Position = (short) Math.ceil( dPosition );	    		
    		i--;
    		
    		// Do not forget to set TableColumnSeparators back! Otherwise, it doesn't work.
    		xPS.setPropertyValue( "TableColumnSeparators", xSeparators );	
		} catch (UnknownPropertyException e) {
			logger.error(e.getMessage(),e);
		} catch (WrappedTargetException e) {
			logger.error(e.getMessage(),e);
		} catch (PropertyVetoException e) {
			logger.error(e.getMessage(),e);
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(),e);
		}
	}
    
	@SuppressWarnings("rawtypes")
	public void setSsVariables(IClientContext cct, IRuleContext rulectx) {
		try {
			String anio = "", anioAnterior = "", fechaSegundoPleno = "", fechaPrimerBop = "";
			String fechaPrimeraNotificacion = ""; 
			StringBuffer nregistros = new StringBuffer();
			IInvesflowAPI invesflowAPI = cct.getAPI();
			IEntitiesAPI entitiesAPI = invesflowAPI.getEntitiesAPI();
			
			String sqlQueryPart = "WHERE NUMEXP = '"+rulectx.getNumExp()+"'";
			IItemCollection participantes = entitiesAPI.queryEntities("DIPUCRPLANESPROVINCIALES", sqlQueryPart);
			Iterator it = (Iterator) participantes.iterator();
			if(it.hasNext()){
				IItem item = (IItem)it.next();
				anio = item.getString("ANIO");
				fechaSegundoPleno = item.getString("SEGUNDOPLENO");
				fechaPrimerBop = item.getString("PRIMERBOP");
			}
			if(anio == null){
				anio = "";
			}
			else{
				try{
					anioAnterior = ""+(Integer.parseInt(anio)-1);
				}
				catch(Exception e){anioAnterior = "";}
			}
			if(fechaSegundoPleno == null) fechaSegundoPleno = "";
			if(fechaPrimerBop == null) fechaPrimerBop = "";
			
			IItemCollection docs = entitiesAPI.getDocuments(rulectx.getNumExp(), "UPPER(NOMBRE) LIKE '%COMUNICACI�N ADMINISTRATIVA%' AND COD_COTEJO IS NOT NULL AND NREG != NULL", "");
			Iterator it2 = (Iterator) docs.iterator();
			while(it2.hasNext()){
				IItem item = (IItem)it2.next();
				fechaPrimeraNotificacion = item.getString("FREG");
				nregistros.append(item.getString("NREG"));
				nregistros.append(",");
			}
			if(nregistros!= null && !nregistros.equals(""))
				nregistros.substring(0, nregistros.length());
			
			if(fechaPrimeraNotificacion == null) fechaPrimeraNotificacion = "";
			
			cct.setSsVariable("ANIO", anio);			
			cct.setSsVariable("FECHASEGUNDOPLENO", fechaSegundoPleno);
			cct.setSsVariable("FECHAPRIMERBOP", fechaPrimerBop);
			cct.setSsVariable("ANIOANTERIOR", anioAnterior);
			cct.setSsVariable("FECHAPRIMERANOTIFICACION", fechaPrimeraNotificacion);
			cct.setSsVariable("NREGISTROSALIDAPRIMERANOTIF", nregistros.toString());			
		} catch (ISPACException e) {				
			logger.error(e.getMessage(),e);
		}
	}

	public void deleteSsVariables(IClientContext cct) {	
		try {
			cct.deleteSsVariable("ANIO");
			cct.deleteSsVariable("FECHAPRIMERPLENO");
			cct.deleteSsVariable("FECHAPRIMERBOP");
			cct.deleteSsVariable("ANIOANTERIOR");
			cct.deleteSsVariable("FECHAPRIMERANOTIFICACION");
			cct.deleteSsVariable("NREGISTROSALIDAPRIMERANOTIF");			
		} catch (ISPACException e) {
			logger.error(e.getMessage(),e);
		}
	}	
}
