package pe.ayni.core.tablero.service;

import pe.ayni.core.tablero.dto.XYSerieDto;

public interface CarteraService {

	XYSerieDto queryCartera(String dato, String mes, String groupBy);

}
