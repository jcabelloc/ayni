package pe.ayni.core.operacion.gasto.rest;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.ayni.core.operacion.gasto.dto.RegistroGastoDto;
import pe.ayni.core.operacion.gasto.service.OperacionGastoService;

@RestController
@RequestMapping("/api/operaciones/gastos")
public class OperacionGastoRest {
	
	@Autowired
	OperacionGastoService operacionGastoService;
	
	@CrossOrigin
	@PostMapping
	public RegistroGastoDto createGasto(@RequestBody RegistroGastoDto registroGasto, Principal principal) {
		registroGasto.getOperacion().setUsuario(principal.getName().toUpperCase()); 
		//registroGasto.getOperacion().setUsuario("OAJON"); 

		RegistroGastoDto gastoResponse = operacionGastoService.createRegistroGasto(registroGasto);
		return gastoResponse;
	}
	@CrossOrigin
	@GetMapping("/{id}")
	public RegistroGastoDto findGastoById(@PathVariable Integer id) {
		return operacionGastoService.findGastoById(id);
	}
	

}
