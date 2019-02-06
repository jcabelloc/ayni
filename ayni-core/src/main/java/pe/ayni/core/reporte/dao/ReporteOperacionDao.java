package pe.ayni.core.reporte.dao;

import java.util.List;

public interface ReporteOperacionDao {

	List<Object[]> getOperaciones(String desde, String hasta);

}
