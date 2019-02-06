package pe.ayni.core.reporte.service;

import java.util.List;

public interface ReporteOperacionService {

	List<List<Object>> getOperaciones(String desde, String hasta);

}
