package pe.ayni.core.credito.service;

import java.math.BigDecimal;
import java.util.List;

import pe.ayni.core.credito.dto.CuotaCreditoDto;
import pe.ayni.core.credito.dto.DetalleCreditoDto;
import pe.ayni.core.operacion.credito.dto.AmortizacionCuotaDto;
import pe.ayni.core.operacion.credito.dto.AmortizacionDetalleDto;

public interface DetalleCreditoService {

	DetalleCreditoDto findDetalleDesembolso(Integer idCuenta);

	List<CuotaCreditoDto> findCuotasByIdCuentaAndEstado(Integer idCuenta, Integer nroCondicion, String estado);

	List<DetalleCreditoDto> findDetallesCreditoWithSaldo(Integer idCuenta, Integer nroCondicion);

	void amortizarDetallesCredito(Integer idCuenta, Integer nroCondicion, BigDecimal monto);

	DetalleCreditoDto findDetalleCreditoById(Integer id);

	List<AmortizacionCuotaDto> calculateAmortizacionCuotas(Integer idCuenta, Integer nroCondicion,
	BigDecimal monto);

	List<AmortizacionDetalleDto> calculateAmortizacionDetalleCredito(Integer idCuenta,
	Integer nroCondicion, BigDecimal monto);
	
	

}
