package pe.ayni.core.operacion.service;

import java.math.BigDecimal;
import java.util.List;

import pe.ayni.core.credito.dto.DetalleCreditoDto;
import pe.ayni.core.operacion.constraint.DetalleOperacionConstraint.DebitoCredito;
import pe.ayni.core.operacion.dto.DetalleOperacionDto;
import pe.ayni.core.operacion.entity.Operacion;

public interface DetalleOperacionService {

	DetalleOperacionDto buildDetalleOperacionDesembolso(DetalleCreditoDto detalleDesembolso);

	DetalleOperacionDto buildDetalleOperacion2(Integer idCuenta, int nroDetalle, DebitoCredito debitoCredito);

	void setDetallesOperacion(Operacion operacion, List<DetalleOperacionDto> detallesOperacion);

	DetalleOperacionDto buildDetalleOperacion(Integer idCuenta, Integer nroDetalle, DebitoCredito debitoCredito,
			BigDecimal monto, Integer idDetalleCredito, Integer idDetalleBanco);
}
