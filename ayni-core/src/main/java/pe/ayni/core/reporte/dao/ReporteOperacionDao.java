package pe.ayni.core.reporte.dao;

import java.util.List;

import pe.ayni.core.operacion.constraint.OperacionConstraint.TipoOperacion;

public interface ReporteOperacionDao {

	List<Object[]> getOperaciones(TipoOperacion tipoOperacion, String desde, String hasta);

}
