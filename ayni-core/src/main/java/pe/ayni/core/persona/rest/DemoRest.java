package pe.ayni.core.persona.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.ayni.core.empleado.service.EmpleadoService;
import pe.ayni.core.persona.service.PersonaNaturalService;
import pe.ayni.core.persona.service.UbigeoService;
import pe.ayni.core.seguridad.service.UsuarioService;

@RestController
@RequestMapping("/api")
public class DemoRest {
	
	@Autowired
	UbigeoService ubigeoService;
	
	@Autowired
	PersonaNaturalService personaNaturalService;
	
	@Autowired
	EmpleadoService empleadoService;
	
	@Autowired
	UsuarioService usuarioService;
	
	
	
}
