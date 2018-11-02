package pe.ayni.core.gasto.dto;

import java.io.Serializable;

public class GastoDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private Integer idOperacion;

	private Integer idProveedor;

	private String fecha;
	
	private String tipoComprobante;
	
	private String nroComprobante;
	
	private String autorizador;
	
	public GastoDto() {
		
	}
			
	public GastoDto(Integer idOperacion, Integer idProveedor, String fecha, String tipoComprobante,
			String nroComprobante, String autorizador) {
		super();
		this.idOperacion = idOperacion;
		this.idProveedor = idProveedor;
		this.fecha = fecha;
		this.tipoComprobante = tipoComprobante;
		this.nroComprobante = nroComprobante;
		this.autorizador = autorizador;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdOperacion() {
		return idOperacion;
	}

	public void setIdOperacion(Integer idOperacion) {
		this.idOperacion = idOperacion;
	}

	public Integer getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(Integer idProveedor) {
		this.idProveedor = idProveedor;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getTipoComprobante() {
		return tipoComprobante;
	}

	public void setTipoComprobante(String tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}

	public String getNroComprobante() {
		return nroComprobante;
	}

	public void setNroComprobante(String nroComprobante) {
		this.nroComprobante = nroComprobante;
	}

	public String getAutorizador() {
		return autorizador;
	}

	public void setAutorizador(String autorizador) {
		this.autorizador = autorizador;
	}

	@Override
	public String toString() {
		return "GastoDto [id=" + id + ", idOperacion=" + idOperacion + ", idProveedor=" + idProveedor + ", fecha="
				+ fecha + ", tipoComprobante=" + tipoComprobante + ", nroComprobante=" + nroComprobante
				+ ", autorizador=" + autorizador + "]";
	}
	

}
