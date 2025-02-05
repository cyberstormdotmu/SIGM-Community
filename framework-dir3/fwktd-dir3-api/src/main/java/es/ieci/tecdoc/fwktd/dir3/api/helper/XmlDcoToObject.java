package es.ieci.tecdoc.fwktd.dir3.api.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.thoughtworks.xstream.XStream;

import es.ieci.tecdoc.fwktd.dir3.api.vo.oficina.OficinaDatosDependenciaJerarquicaVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.oficina.OficinaDatosIdentificativosVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.oficina.OficinaDatosLocalizacionVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.oficina.OficinaDatosOperativosVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.oficina.OficinaDatosVigenciaVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.oficina.OficinaVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.oficina.OficinasVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.unidad.OrganismoDatosDependenciaVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.unidad.OrganismoDatosIdentificativosVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.unidad.OrganismoDatosVigenciaVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.unidad.OrganismoVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.unidad.OrganismosVO;

public class XmlDcoToObject {

	private static XmlDcoToObject _instance = new XmlDcoToObject();
	
	/**
	 * Constructor protegido para evitar creaci�n de instancias desde otras clases
	 */	
	protected XmlDcoToObject() {}
	
	/**
	 * Obtiene la instanc�a �nica de la clase
	 * @return La instancia �nica de la clase
	 */
	public static XmlDcoToObject getInstance()
	{
		return _instance;
	}
	
	/**
	 * Devuelve un objeto "Oficinas" a partir de un Xml de Oficinas de DCO
	 * @param xmlFilePath Path al fichero XML de Oficinas de DCO
	 * @return Un objeto "Oficinas" a partir de un Xml de Oficinas de DCO
	 */
	public OficinasVO getOficinasFromXmlFile(String xmlFilePath) 
	{
		XStream xstream = new XStream();
		File file = new File(xmlFilePath);
		InputStream fileIS = null;
		try {
			 fileIS = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//Anotaciones para el mapeo de las clases con los nodos del XML
		xstream.processAnnotations(OficinasVO.class);
		xstream.processAnnotations(OficinaVO.class);
		xstream.processAnnotations(OficinaDatosIdentificativosVO.class);
		xstream.processAnnotations(OficinaDatosOperativosVO.class);
		xstream.processAnnotations(OficinaDatosVigenciaVO.class);
		xstream.processAnnotations(OficinaDatosDependenciaJerarquicaVO.class);
		xstream.processAnnotations(OficinaDatosLocalizacionVO.class);
		
		return (OficinasVO)xstream.fromXML(fileIS);
	}
	
	/**
	 * Devuelve un objeto "Organismos" a partir de un Xml de Organismos de DCO
	 * @param xmlFilePath Path al fichero XML de Organismos de DCO
	 * @return Un objeto "Organismos" a partir de un Xml de Organismos de DCO
	 */   
	public OrganismosVO getOrganismosFromXmlFile(String xmlFilePath)
	{
		XStream xstream = new XStream();
		File file = new File(xmlFilePath);
		InputStream fileIS = null;
		try {
			 fileIS = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//Anotaciones para el mapeo de las clases con los nodos del XML
		xstream.processAnnotations(OrganismosVO.class);
		xstream.processAnnotations(OrganismoVO.class);
		xstream.processAnnotations(OrganismoDatosIdentificativosVO.class);
		xstream.processAnnotations(OrganismoDatosDependenciaVO.class);
		xstream.processAnnotations(OrganismoDatosVigenciaVO.class);
		
		return (OrganismosVO)xstream.fromXML(fileIS);
	}
}
