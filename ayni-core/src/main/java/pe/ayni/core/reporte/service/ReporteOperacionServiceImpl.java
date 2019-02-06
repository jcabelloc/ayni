package pe.ayni.core.reporte.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.ayni.core.reporte.dao.ReporteOperacionDao;

@Service
public class ReporteOperacionServiceImpl implements ReporteOperacionService {

	@Autowired
	ReporteOperacionDao reporteOperacionDao;

	@Override
	@Transactional
	public List<List<Object>> getOperaciones(String desde, String hasta) {
		List<List<Object>> operacionesOut = new ArrayList<>();
		List<Object[]> operacionesIn = reporteOperacionDao.getOperaciones(desde, hasta);
		for (Object[] row: operacionesIn ) {
			List<Object> rowAsList= Arrays.asList(row);
			operacionesOut.add(rowAsList);
		}
		return operacionesOut;
	}

}
