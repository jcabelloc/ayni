package pe.ayni.core.cuenta.dao;

import pe.ayni.core.cuenta.entity.CuentaContable;

public interface CuentaContableDao {

	CuentaContable findByCta(String ctaContable);

}
