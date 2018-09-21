package pe.ayni.core.cuenta.service;

import pe.ayni.core.cuenta.entity.Cuenta;

public interface CuentaService {
	
	Cuenta findCuentaById(Integer id);
}
