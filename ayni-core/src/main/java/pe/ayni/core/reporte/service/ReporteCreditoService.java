package pe.ayni.core.reporte.service;

import java.util.List;

public interface ReporteCreditoService {
	
	List<List<Object>> getCarteraCreditos();

	List<List<Object>> getAmortizaciones();
}
