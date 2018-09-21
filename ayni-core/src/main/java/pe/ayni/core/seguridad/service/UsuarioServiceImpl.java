package pe.ayni.core.seguridad.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.ayni.core.seguridad.dao.UsuarioDao;
import pe.ayni.core.seguridad.entity.Usuario;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	UsuarioDao usuarioDao;
	
	@Override
	@Transactional
	public void createUsuario(Usuario usuario) {
		usuarioDao.create(usuario);
	}

	@Override
	@Transactional
	public Usuario findUsuarioById(String usuario) {
		return usuarioDao.findById(usuario);
	}

}
