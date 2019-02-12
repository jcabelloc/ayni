package pe.ayni.core.tablero.service;

import pe.ayni.core.tablero.dto.XYSerieDto;

public interface CarteraService {

	XYSerieDto queryCarteraSaldo(String mes, String groupBy);
	XYSerieDto queryCarteraAtrasada(Integer diasAtrasoMayorA, String mes, String groupBy);

}
