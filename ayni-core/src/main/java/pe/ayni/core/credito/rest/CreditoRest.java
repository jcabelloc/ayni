package pe.ayni.core.credito.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.ayni.core.credito.dto.CreditoDto;
import pe.ayni.core.credito.dto.SimulacionCreditoDto;
import pe.ayni.core.credito.dto.CuotaCreditoDto;
import pe.ayni.core.credito.service.CreditoService;

@RestController
@RequestMapping("/api/creditos")
public class CreditoRest {
	
	@Autowired
	CreditoService creditoService;
	
	
	@CrossOrigin
	@GetMapping("/simular-cuotas")
	public List<CuotaCreditoDto> calculateCuotas(SimulacionCreditoDto simulacionCredito) {
		System.out.println(simulacionCredito.toString());
		return creditoService.calculateCuotas(simulacionCredito);
	}
	
	@CrossOrigin
	@GetMapping("/{idCuenta}")
	public CreditoDto findCreditoById(@PathVariable Integer idCuenta) {
		return creditoService.findCreditoById(idCuenta);
	}
	
	@CrossOrigin
	@GetMapping(path="/{idCuenta}/cuotas-credito", params="estado")
	public List<CuotaCreditoDto> findCuotasByIdCuentaAndEstado(@PathVariable Integer idCuenta, 
			@RequestParam("estado") String estado) {
		return creditoService.findCuotasByIdCuentaAndEstado(idCuenta, estado);
	}
	
	@CrossOrigin
	@GetMapping(path="/{idCuenta}/cuotas-credito")
	public List<CuotaCreditoDto> findAllCuotasByIdCuenta(@PathVariable Integer idCuenta) {
		return creditoService.findAllCuotasByIdCuenta(idCuenta);
	}
	
	@CrossOrigin
	@GetMapping(path="", params= {"by", "input"})
	public List<CreditoDto> findCreditosBy(@RequestParam("by") String by, @RequestParam("input") String input) {
		return creditoService.findCreditosBy(by, input);
	}
}
