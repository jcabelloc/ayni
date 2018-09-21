package pe.ayni.core.operacion.credito.service;

import java.io.OutputStream;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import pe.ayni.core.operacion.credito.dto.AmortizacionCreditoDto;
import pe.ayni.core.operacion.credito.dto.AmortizacionCuotaDto;
import pe.ayni.core.operacion.credito.dto.SimulacionAmortizacionDto;
import pe.ayni.core.operacion.credito.dto.DesembolsoCreditoDto;

public interface OperacionCreditoService {

	DesembolsoCreditoDto createDesembolso(DesembolsoCreditoDto desembolsoCredito);

	void buildReporteSolicitud(DesembolsoCreditoDto desembolsoCreditoDto, OutputStream outStream) throws JRException;

	List<AmortizacionCuotaDto> calculateAmortizacion(SimulacionAmortizacionDto simulacionAmortizacion);

	AmortizacionCreditoDto createAmortizacion(AmortizacionCreditoDto amortizacionCredito);

	DesembolsoCreditoDto findDesembolsoById(Integer id);

	AmortizacionCreditoDto findAmortizacionById(Integer id);
	
	
	
}
