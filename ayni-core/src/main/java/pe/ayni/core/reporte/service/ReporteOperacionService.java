package pe.ayni.core.reporte.service;

import java.util.List;

import pe.ayni.core.operacion.constraint.OperacionConstraint.TipoOperacion;

public interface ReporteOperacionService {

	List<List<Object>> getOperaciones(TipoOperacion tipoOperacion, String desde, String hasta);

}
