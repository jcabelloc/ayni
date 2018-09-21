package pe.ayni.core.persona.rest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.ayni.core.empleado.constraint.EmpleadoConstraint.EstadoEmpleado;
import pe.ayni.core.empleado.entity.Empleado;
import pe.ayni.core.empleado.service.EmpleadoService;
import pe.ayni.core.persona.dto.ConfiguracionUbigeoDto;
import pe.ayni.core.persona.entity.PersonaNatural;
import pe.ayni.core.persona.service.PersonaNaturalService;
import pe.ayni.core.persona.service.UbigeoService;
import pe.ayni.core.seguridad.constraint.UsuarioConstraint.EstadoUsuario;
import pe.ayni.core.seguridad.entity.Usuario;
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
	
	@GetMapping("/demo")
	public ConfiguracionUbigeoDto demo() {
		return ubigeoService.getConfiguracionUbigeo();
		
	}
	
	@GetMapping("/empleado")
	public void empleado() {
		PersonaNatural personaNatural = new PersonaNatural();
		personaNatural.setId(10000001);
		Empleado empleado = new Empleado();
		empleado.setFechaIngreso(LocalDate.now());
		empleado.setEstado(EstadoEmpleado.ACTIVO);
		empleado.setFechaHoraInsercion(LocalDateTime.now());
		empleado.setPersonaNatural(personaNatural);
		empleadoService.createEmpleado(empleado);
	}
	
	@GetMapping("/usuario")
	public void usuario() {
		Usuario usuario = new Usuario();
		usuario.setClave("{bcrypt}$2a$04$opSxNXcLQoXjwz8cFQyFHetBGk6fYDM3e2sQHpKRX344jN1oEAUuS");
		usuario.setUsuario("orfita");
		usuario.setEstado(EstadoUsuario.ACTIVO);
		usuario.setFechaAlta(LocalDate.now());
		usuario.setFechaHoraInsercion(LocalDateTime.now());
		Empleado empleado = new Empleado();
		empleado.setId(1001);
		usuario.setEmpleado(empleado);
		usuarioService.createUsuario(usuario);
	}
	
	
	@CrossOrigin
	@GetMapping("/login")
	public User getUsuario() {
		return new User("SuperUsuario");
		
	}
	
	public class User {
		private String usuario;
		
		public User(String usuario) {
			this.usuario = usuario;
		}
		
		public String getUsuario() {
			return usuario;
		}

		public void setUsuario(String usuario) {
			this.usuario = usuario;
		}
		
	}
}
