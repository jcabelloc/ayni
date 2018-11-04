package pe.ayni.core.operacion.caja.rest;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.ayni.core.operacion.caja.dto.TraspasoEfectivoDto;
import pe.ayni.core.operacion.caja.service.OperacionCajaService;

@RestController
@RequestMapping("/api/operaciones/traspasos")
public class CajaRest {
	
	@Autowired
	OperacionCajaService operacionCajaService;
	
	@CrossOrigin
	@PostMapping
	public TraspasoEfectivoDto createGasto(@RequestBody TraspasoEfectivoDto traspaso, Principal principal) {
		traspaso.setUsuario(principal.getName().toUpperCase()); 
		// traspaso.setUsuario("OAJON"); 

		System.out.println(traspaso);
		TraspasoEfectivoDto traspasoResponse = operacionCajaService.createTraspasoEfectivo(traspaso);
		return traspasoResponse;
	}
	
	@CrossOrigin
	@GetMapping("/{id}")
	public TraspasoEfectivoDto findTraspasoEfectivoById(@PathVariable Integer id) {
		return operacionCajaService.findTraspasoEfectivoById(id);
	}
}
