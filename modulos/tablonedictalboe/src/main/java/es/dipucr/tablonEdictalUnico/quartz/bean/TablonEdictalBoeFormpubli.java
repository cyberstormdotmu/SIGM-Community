package es.dipucr.tablonEdictalUnico.quartz.bean;

// Generated 26-may-2015 10:44:34 by Hibernate Tools 3.4.0.CR1

/**
 * TablonEdictalBoeFormpubli generated by hbm2java
 */
public class TablonEdictalBoeFormpubli implements java.io.Serializable {

	private int id;
	private String valor;
	private String sustituto;
	private Boolean vigente;
	private Long orden;

	public TablonEdictalBoeFormpubli() {
	}

	public TablonEdictalBoeFormpubli(int id) {
		this.id = id;
	}

	public TablonEdictalBoeFormpubli(int id, String valor, String sustituto,
			Boolean vigente, Long orden) {
		this.id = id;
		this.valor = valor;
		this.sustituto = sustituto;
		this.vigente = vigente;
		this.orden = orden;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getSustituto() {
		return this.sustituto;
	}

	public void setSustituto(String sustituto) {
		this.sustituto = sustituto;
	}

	public Boolean getVigente() {
		return this.vigente;
	}

	public void setVigente(Boolean vigente) {
		this.vigente = vigente;
	}

	public Long getOrden() {
		return this.orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

}
