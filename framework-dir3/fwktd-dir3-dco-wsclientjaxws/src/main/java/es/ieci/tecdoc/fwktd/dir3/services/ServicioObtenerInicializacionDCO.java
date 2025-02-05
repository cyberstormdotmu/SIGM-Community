package es.ieci.tecdoc.fwktd.dir3.services;

/**
 * Interfaz de los servicios de inicialización de dco.
 * 
 */
public interface ServicioObtenerInicializacionDCO {

	/**
	 * Obtiene del DCO el fichero XML para inicializar el modelo de datos de oficinas
	 * @return String path completo al fichero xml generado
	 */
	public String getFicheroInicializarOficinasDCO();

	/**
	 * Obtiene del DCO el fichero XML para inicializar el modelo de datos de unidades
	 * @return String path completo al fichero xml generado
	 */
	public String getFicheroInicializarUnidadesDCO();



}
