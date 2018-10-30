package pe.ayni.core.cuentagasto.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.ayni.core.cuentagasto.dto.CuentaGastoDto;
import pe.ayni.core.cuentagasto.service.CuentaGastoService;

@RestController
@RequestMapping("/api/cuentas-gasto")
public class CuentaGastoRest {
	
	@Autowired
	CuentaGastoService cuentaGastoService;
	
	@CrossOrigin
	@GetMapping(path="", params= {"idProveedor"})
	public List<CuentaGastoDto> findProveedoresBy(@RequestParam("idProveedor") Integer idProveedor) {
		return cuentaGastoService.findCuentasGastoByIdProveedor(idProveedor);
	}
}
