package pe.ayni.core.tablero.service;

import pe.ayni.core.tablero.dto.XYSerieDto;

public interface CarteraService {

	XYSerieDto queryCarteraSaldo(String desde, String hasta, String groupBy);
	XYSerieDto queryCarteraAtrasada(Integer diasAtrasoMayorA, String desde, String hasta, String groupBy);
	XYSerieDto queryDesembolsos(String valor, String desde, String hasta, String groupBy);

}
