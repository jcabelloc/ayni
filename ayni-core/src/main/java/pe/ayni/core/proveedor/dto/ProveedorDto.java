package pe.ayni.core.proveedor.dto;

import java.io.Serializable;

import pe.ayni.core.proveedor.entity.Proveedor;

public class ProveedorDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private Integer id;
    
    private String nombre;
    
    private String tipoPersona;
    
    private String tipoIdentificacion;
    
    private String nroIdentificacion;
    
    public ProveedorDto() {
    	
    }

	public ProveedorDto(Proveedor proveedor) {
		this.id = proveedor.getId();
		this.nombre = proveedor.getPersona().getNombre();
		this.tipoPersona = proveedor.getPersona().getTipoPersona().toString();
		this.tipoIdentificacion = proveedor.getPersona().getTipoIdentificacion().toString();
		this.nroIdentificacion = proveedor.getPersona().getNroIdentificacion();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public String getNroIdentificacion() {
		return nroIdentificacion;
	}

	public void setNroIdentificacion(String nroIdentificacion) {
		this.nroIdentificacion = nroIdentificacion;
	}

	@Override
	public String toString() {
		return "ProveedorDto [id=" + id + ", nombre=" + nombre + ", tipoPersona=" + tipoPersona
				+ ", tipoIdentificacion=" + tipoIdentificacion + ", nroIdentificacion=" + nroIdentificacion + "]";
	}
    
    
    
}
