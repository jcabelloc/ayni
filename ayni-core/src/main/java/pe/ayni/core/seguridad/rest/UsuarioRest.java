package pe.ayni.core.seguridad.rest;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.ayni.core.seguridad.dto.UsuarioDto;
import pe.ayni.core.seguridad.service.UsuarioService;

@RestController
@RequestMapping("/api")
public class UsuarioRest {

	@Autowired
	UsuarioService usuarioService;
	
	@CrossOrigin
	@GetMapping("/login")
	public UsuarioDto getUsuario(Principal principal) {
		return new UsuarioDto(principal.getName().toUpperCase());
		
	}
}
