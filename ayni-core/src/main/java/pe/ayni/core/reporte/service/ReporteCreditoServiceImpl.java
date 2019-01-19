package pe.ayni.core.reporte.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.ayni.core.credito.constraint.CreditoConstraint.EstadoCredito;
import pe.ayni.core.credito.dto.CreditoDto;
import pe.ayni.core.credito.service.CreditoService;
import pe.ayni.core.reporte.dao.ReporteCreditoDao;

@Service
public class ReporteCreditoServiceImpl implements ReporteCreditoService {
	
	@Autowired
	ReporteCreditoDao reporteCreditoDao;
	
	@Autowired
	CreditoService creditoService;

	@Override
	@Transactional
	public List<List<Object>> getCarteraCreditos(EstadoCredito estado) {
		
		List<List<Object>> carteraOut = new ArrayList<>();
		List<Object[]> carteraIn = reporteCreditoDao.getCarteraCreditos(estado);
		for (Object[] row: carteraIn ) {
			List<Object> rowAsList= Arrays.asList(row);
			carteraOut.add(rowAsList);
		}
		return carteraOut;
	}

	@Override
	@Transactional
	public List<List<Object>> getAmortizaciones(int month, int year) {
		List<List<Object>> amortizacionesOut = new ArrayList<>();
		List<Object[]> amortizacionesIn = reporteCreditoDao.getAmortizaciones(month, year);
		for (Object[] row: amortizacionesIn ) {
			List<Object> rowAsList= Arrays.asList(row);
			amortizacionesOut.add(rowAsList);
		}
		return amortizacionesOut;

	}

	@Override
	public List<List<Object>> calculateCuotas(CreditoDto creditoDto) {
		
		List<List<Object>> cuotas = creditoService.calculateCuotas(creditoDto)
										.stream()
										.filter(e -> (e.getNroCuota().intValue() > 0))
										.map(e -> {
											List<Object> row = new ArrayList<>();
											row.add(e.getNroCuota());
											row.add(formatStringDate(e.getFechaVencimiento()));
											row.add(e.getCapitalCredito());
											row.add(e.getCapitalProgramado());
											row.add(e.getInteresProgramado());
											row.add(e.getMontoCuota());
											return row;
										})
										.collect(Collectors.toList()); 

		return cuotas;
	}
	@Override
	public String formatStringDate(String fecha) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate ld = LocalDate.parse(fecha, formatter);
		formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return ld.format(formatter);
	}
	
	
}
