package pe.ayni.core.operacion.dao;

import pe.ayni.core.cuenta.entity.Cuenta;
import pe.ayni.core.cuenta.entity.CuentaContable;
import pe.ayni.core.operacion.entity.Saldo;
import pe.ayni.core.operacion.entity.Saldo.SaldoPk;

public interface SaldoDao {

	Saldo findByCuentaContableAndCuenta(CuentaContable cuentaContable, Cuenta cuenta);

	SaldoPk save(Saldo saldo);

}
