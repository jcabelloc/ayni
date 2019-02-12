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
	@GetMapping(path="/saldo", params= {"desde", "hasta", "groupBy"})
	public XYSerieDto queryCarteraSaldo(@RequestParam("desde") String desde, @RequestParam("hasta") String hasta, 
			@RequestParam("groupBy") String groupBy){
		
		return carteraService.queryCarteraSaldo(desde, hasta, groupBy);
	}
	
	@CrossOrigin
	@GetMapping(path="/atrasada", params= {"diasAtrasoMayorA", "desde", "hasta", "groupBy"})
	public XYSerieDto queryCarteraAtrasada(@RequestParam("diasAtrasoMayorA") Integer diasAtrasoMayorA, 
			@RequestParam("desde") String desde, @RequestParam("hasta") String hasta, @RequestParam("groupBy") String groupBy){
		
		return carteraService.queryCarteraAtrasada(diasAtrasoMayorA, desde, hasta, groupBy);
	}
	
	@CrossOrigin
	@GetMapping(path="/desembolsos", params= {"valor", "desde", "hasta", "groupBy"})
	public XYSerieDto queryDesembolsos(@RequestParam("valor") String valor, @RequestParam("desde") String desde, 
			@RequestParam("hasta") String hasta, @RequestParam("groupBy") String groupBy){
		
		return carteraService.queryDesembolsos(valor, desde, hasta,groupBy);
	}
}
