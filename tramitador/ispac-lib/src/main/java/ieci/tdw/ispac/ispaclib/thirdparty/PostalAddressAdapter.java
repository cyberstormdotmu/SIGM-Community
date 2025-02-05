package ieci.tdw.ispac.ispaclib.thirdparty;

/**
 * Adaptador para contener la información de la dirección postal
 * de un tercero.
 *
 */
public class PostalAddressAdapter implements IPostalAddressAdapter {

	String id;
	String direccionPostal;
	String tipoVia;
	String via;
	String bloque;
	String piso;
	String puerta;
	String codigoPostal;
	String poblacion;
	String municipio;
	String provincia;
	String comunidadAutonoma;
	String pais;
	String telefono;
	
	/** INICIO [dipucr-Felipe 3#333] **/
	String codMunicipio;
	String codMunicipioDir3;
	String codProvincia;
	String codProvinciaDir3;
	/** FIN [dipucr-Felipe 3#333] **/
	
	
	/**
	 * Constructor.
	 */
	public PostalAddressAdapter() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDireccionPostal() {
		return direccionPostal;
	}

	public void setDireccionPostal(String direccionPostal) {
		this.direccionPostal = direccionPostal;
	}

	public String getTipoVia() {
		return tipoVia;
	}

	public void setTipoVia(String tipoVia) {
		this.tipoVia = tipoVia;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getBloque() {
		return bloque;
	}

	public void setBloque(String bloque) {
		this.bloque = bloque;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getPuerta() {
		return puerta;
	}

	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getComunidadAutonoma() {
		return comunidadAutonoma;
	}

	public void setComunidadAutonoma(String comunidadAutonoma) {
		this.comunidadAutonoma = comunidadAutonoma;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCodMunicipio() {
		return codMunicipio;
	}

	public void setCodMunicipio(String codMunicipio) {
		this.codMunicipio = codMunicipio;
	}
	
	public String getCodMunicipioDir3() {
		return codMunicipioDir3;
	}

	public void setCodMunicipioDir3(String codMunicipioDir3) {
		this.codMunicipioDir3 = codMunicipioDir3;
	}

	public String getCodProvincia() {
		return codProvincia;
	}

	public void setCodProvincia(String codProvincia) {
		this.codProvincia = codProvincia;
	}

	public String getCodProvinciaDir3() {
		return codProvinciaDir3;
	}

	public void setCodProvinciaDir3(String codProvinciaDir3) {
		this.codProvinciaDir3 = codProvinciaDir3;
	}

	
}