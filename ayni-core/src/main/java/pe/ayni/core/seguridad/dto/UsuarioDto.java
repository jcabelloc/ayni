package pe.ayni.core.seguridad.dto;

import java.io.Serializable;

public class UsuarioDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String usuario;
	
	public UsuarioDto(String usuario) {
		this.usuario = usuario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	
	
}
