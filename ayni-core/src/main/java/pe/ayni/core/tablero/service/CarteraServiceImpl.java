package pe.ayni.core.tablero.service;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.ayni.core.tablero.dao.CarteraDao;
import pe.ayni.core.tablero.dto.XYSerieDto;

@Service
public class CarteraServiceImpl implements CarteraService {
	
	@Autowired
	CarteraDao carteraDao;
	
	@Override
	@Transactional
	public XYSerieDto queryCarteraSaldo(String desde, String hasta, String groupBy) {
		XYSerieDto xySerie = new XYSerieDto();
		
		List<Object[]> data = carteraDao.queryCarteraSaldo(desde, hasta, groupBy);
		String[] xSerie = new String[data.size()];
		BigDecimal[] ySerie = new BigDecimal[data.size()];
		int i = 0; 
		for (Object[] row: data) {
			xSerie[i] = String.valueOf(row[0]);
			ySerie[i] = new BigDecimal(String.valueOf(row[1]));
			i++;
		}
		xySerie.setxSerie(xSerie);
		xySerie.setySerie(ySerie);
		return xySerie; 
	}

	@Override
	@Transactional
	public XYSerieDto queryCarteraAtrasada(Integer diasAtrasoMayorA, String desde, String hasta, String groupBy) {
		XYSerieDto xySerie = new XYSerieDto();
		
		List<Object[]> data = carteraDao.queryCarteraAtrasada(diasAtrasoMayorA, desde, hasta, groupBy);
		String[] xSerie = new String[data.size()];
		BigDecimal[] ySerie = new BigDecimal[data.size()];
		int i = 0; 
		for (Object[] row: data) {
			xSerie[i] = String.valueOf(row[0]);
			ySerie[i] = new BigDecimal(String.valueOf(row[1]));
			i++;
		}
		xySerie.setxSerie(xSerie);
		xySerie.setySerie(ySerie);
		return xySerie; 
	}
	
	@Override
	@Transactional
	public XYSerieDto queryDesembolsos(String valor, String desde, String hasta, String groupBy) {
		XYSerieDto xySerie = new XYSerieDto();
		
		List<Object[]> data = carteraDao.queryDesembolsos(valor, desde, hasta, groupBy);
		String[] xSerie = new String[data.size()];
		BigDecimal[] ySerie = new BigDecimal[data.size()];
		int i = 0; 
		for (Object[] row: data) {
			xSerie[i] = String.valueOf(row[0]);
			ySerie[i] = new BigDecimal(String.valueOf(row[1]));
			i++;
		}
		xySerie.setxSerie(xSerie);
		xySerie.setySerie(ySerie);
		return xySerie; 
	}

}
