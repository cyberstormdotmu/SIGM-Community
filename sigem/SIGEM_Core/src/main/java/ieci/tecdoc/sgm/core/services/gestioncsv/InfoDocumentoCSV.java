/**
 * 
 */
package ieci.tecdoc.sgm.core.services.gestioncsv;

import java.util.Date;

/**
 * @author IECISA
 * 
 * Informaci�n de un documento con su CSV generado
 *
 */
public class InfoDocumentoCSV extends InfoDocumentoCSVForm{
	
	/**
	 * Identificador del documento
	 */
	private String id;
	
	/**
	 * C�digo CSV asignado al documento
	 */
	private String csv;
	
	/**
	 * Fecha de generaci�n del CSV del documento
	 */
	private Date fechaCSV;
	
	/**
	 * Nombre de la aplicaci�n a la que pertenece el documento
	 */
	private String nombreAplicacion;
	
	/**
	 *  [Manu Ticket #625] CVE Consulta de documentos - A�adir campos para registros de salida 
	 */
	/**
	 * N�mero de Registro del documento 
	 */
	private String numeroRegistro = null;
	/**
	 * Fecha de Registro del documento
	 */
	private Date fechaRegistro = null;
	/**
	 * Origne del registro
	 */
	private String origenRegistro = null;
	/**
	 * Destino del registro
	 */
	private String destinoRegistro = null;
	/**
	 *  [Manu Ticket #625] CVE Consulta de documentos - A�adir campos para registros de salida 
	 */
	
	/**
	 *  [Manu Ticket #625] CVE Consulta de documentos - A�adir campos para registros de salida 
	 */
	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getOrigenRegistro() {
		return origenRegistro;
	}

	public void setOrigenRegistro(String origenRegistro) {
		this.origenRegistro = origenRegistro;
	}

	public String getDestinoRegistro() {
		return destinoRegistro;
	}

	public void setDestinoRegistro(String destinoRegistro) {
		this.destinoRegistro = destinoRegistro;
	}
	/**
	 *  [Manu Ticket #625] CVE Consulta de documentos - A�adir campos para registros de salida 
	 */

	/**
	 * @return el id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id el id a fijar
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return el csv
	 */
	public String getCsv() {
		return csv;
	}

	/**
	 * @param csv el csv a fijar
	 */
	public void setCsv(String csv) {
		this.csv = csv;
	}

	/**
	 * @return el fechaCSV
	 */
	public Date getFechaCSV() {
		return fechaCSV;
	}

	/**
	 * @param fechaCSV el fechaCSV a fijar
	 */
	public void setFechaCSV(Date fechaCSV) {
		this.fechaCSV = fechaCSV;
	}

	/**
	 * @return el nombreAplicacion
	 */
	public String getNombreAplicacion() {
		return nombreAplicacion;
	}

	/**
	 * @param nombreAplicacion el nombreAplicacion a fijar
	 */
	public void setNombreAplicacion(String nombreAplicacion) {
		this.nombreAplicacion = nombreAplicacion;
	}

	
	

}
