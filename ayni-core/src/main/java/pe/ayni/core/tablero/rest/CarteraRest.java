package pe.ayni.core.tablero.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.ayni.core.tablero.dto.XYSerieDto;
import pe.ayni.core.tablero.service.CarteraService;

@RestController
@RequestMapping("/api/tableros/cartera")
public class CarteraRest {
	
	@Autowired
	CarteraService carteraService; 
	
	
	@CrossOrigin
	@GetMapping(path="/saldo", params= {"mes", "groupBy"})
	public XYSerieDto queryCarteraSaldo(@RequestParam("mes") String mes, @RequestParam("groupBy") String groupBy){
		
		return carteraService.queryCarteraSaldo(mes, groupBy);
	}
	
	@CrossOrigin
	@GetMapping(path="/atrasada", params= {"diasAtrasoMayorA", "mes", "groupBy"})
	public XYSerieDto queryCarteraAtrasada(@RequestParam("diasAtrasoMayorA") Integer diasAtrasoMayorA, 
			@RequestParam("mes") String mes, @RequestParam("groupBy") String groupBy){
		
		return carteraService.queryCarteraAtrasada(diasAtrasoMayorA, mes, groupBy);
	}
}
