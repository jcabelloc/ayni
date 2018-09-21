package pe.ayni.core.seguridad.dao;

import pe.ayni.core.seguridad.entity.Usuario;

public interface UsuarioDao {
	
	void create(Usuario usuario);

	Usuario findById(String usuario);
}
