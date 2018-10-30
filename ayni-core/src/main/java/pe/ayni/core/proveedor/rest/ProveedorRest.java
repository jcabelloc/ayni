package pe.ayni.core.proveedor.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.ayni.core.proveedor.dto.ProveedorDto;
import pe.ayni.core.proveedor.service.ProveedorService;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorRest {

	@Autowired
	ProveedorService proveedorService;
	
	@CrossOrigin
	@GetMapping(path="", params= {"by", "input"})
	public List<ProveedorDto> findProveedoresBy(@RequestParam("by") String by, @RequestParam("input") String input) {
		return proveedorService.findProveedoresBy(by, input);
	}
	
	@CrossOrigin
	@GetMapping("/{id}")
	public ProveedorDto findProveedorById(@PathVariable Integer id) {
		return proveedorService.findProveedorById(id);
	}
	
}
