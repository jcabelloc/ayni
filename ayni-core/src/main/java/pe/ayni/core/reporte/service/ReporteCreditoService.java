package pe.ayni.core.reporte.service;

import java.util.List;

import pe.ayni.core.credito.dto.CreditoDto;

public interface ReporteCreditoService {
	
	List<List<Object>> getCarteraCreditos();

	List<List<Object>> getAmortizaciones(int month, int year);

	List<List<Object>> calculateCuotas(CreditoDto creditoDto);

	public String formatStringDate(String fecha);

}
