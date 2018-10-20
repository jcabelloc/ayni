package pe.ayni.core.credito.dao;

import java.math.BigDecimal;
import java.util.List;

import pe.ayni.core.credito.entity.DetalleCredito;

public interface DetalleCreditoDao {

	DetalleCredito findDesembolso(Integer idCuenta);

	List<DetalleCredito> findByIdCuentaInCuotasPendientes(Integer idCuenta, Integer nroCondicion);

	List<DetalleCredito> findWithSaldo(Integer idCuenta, Integer nroCondicion);

	int updateMontoPagado(Integer id, BigDecimal monto);

	DetalleCredito findById(Integer id);

	List<DetalleCredito> findByIdCuenta(Integer idCuenta, Integer nroCondicion);

}
