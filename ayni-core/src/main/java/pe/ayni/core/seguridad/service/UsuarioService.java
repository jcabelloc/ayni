package pe.ayni.core.seguridad.service;

import pe.ayni.core.seguridad.entity.Usuario;

public interface UsuarioService {
	
	void createUsuario(Usuario usuario);  // TODO replace it to DTO

	Usuario findUsuarioById(String usuario);
	
}
