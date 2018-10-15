package pe.ayni.core.reporte.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.ayni.core.reporte.dao.ReporteCreditoDao;

@Service
public class ReporteCreditoServiceImpl implements ReporteCreditoService {
	
	@Autowired
	ReporteCreditoDao reporteCreditoDao;

	@Override
	@Transactional
	public List<List<Object>> getCarteraCreditos() {
		
		List<List<Object>> carteraOut = new ArrayList<>();
		List<Object[]> carteraIn = reporteCreditoDao.getCarteraCreditos();
		for (Object[] row: carteraIn ) {
			List<Object> rowAsList= Arrays.asList(row);
			carteraOut.add(rowAsList);
		}
		return carteraOut;
	}

	@Override
	@Transactional
	public List<List<Object>> getAmortizaciones() {
		List<List<Object>> amortizacionesOut = new ArrayList<>();
		List<Object[]> amortizacionesIn = reporteCreditoDao.getAmortizaciones();
		for (Object[] row: amortizacionesIn ) {
			List<Object> rowAsList= Arrays.asList(row);
			amortizacionesOut.add(rowAsList);
		}
		return amortizacionesOut;

	}
	
	
	
}
