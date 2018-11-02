package pe.ayni.core.gasto.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.ayni.core.gasto.dto.CuentaGastoDto;
import pe.ayni.core.gasto.service.CuentaGastoService;

@RestController
@RequestMapping("/api/cuentas-gasto")
public class CuentaGastoRest {
	
	@Autowired
	CuentaGastoService cuentaGastoService;
	
	@CrossOrigin
	@GetMapping()
	public List<CuentaGastoDto> findAllCuentasGasto() {
		return cuentaGastoService.findAllCuentasGasto();
	}
}
