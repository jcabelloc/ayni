package pe.ayni.core.cuenta.service;

import pe.ayni.core.cuenta.entity.CuentaContable;

public interface CuentaContableService {
	
	CuentaContable findCuentaContableByCta(String ctaContable);
}
