package pe.ayni.core.operacion.credito.service;

import java.util.List;

import pe.ayni.core.operacion.credito.dto.AmortizacionCreditoDto;
import pe.ayni.core.operacion.credito.dto.AmortizacionDetalleDto;
import pe.ayni.core.operacion.dto.DetalleOperacionDto;

public interface DetalleOperacionCreditoService {
	
	List<DetalleOperacionDto> buildDetallesAmortizacion(AmortizacionCreditoDto amortizacionCredito,
			List<AmortizacionDetalleDto> amortizacionDetalles);

}
