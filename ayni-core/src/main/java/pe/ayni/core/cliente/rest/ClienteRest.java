package pe.ayni.core.cliente.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.ayni.core.cliente.dto.ClienteDto;
import pe.ayni.core.cliente.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRest {
	
	@Autowired
	ClienteService clienteService;
	
	@CrossOrigin
	@PostMapping("")
	public ClienteDto createCliente(@RequestBody ClienteDto cliente) {
		
		clienteService.createCliente(cliente);
		return cliente;
	}
	
	@CrossOrigin
	@GetMapping(path="", params= {"by", "input"})
	public List<ClienteDto> findClientesBy(@RequestParam("by") String by, @RequestParam("input") String input) {
		return clienteService.findClientesBy(by, input);
	}
	
	@CrossOrigin
	@GetMapping("/{id}")
	public ClienteDto findClienteById(@PathVariable Integer id) {
		return clienteService.findClienteById(id);
	}
	
	@CrossOrigin
	@PutMapping("/{id}")
	public ClienteDto updateCliente(@RequestBody ClienteDto cliente) {
		System.out.println(cliente);
		ClienteDto clienteResponse = clienteService.updateCliente(cliente);
		return clienteResponse;
	}
	
}
