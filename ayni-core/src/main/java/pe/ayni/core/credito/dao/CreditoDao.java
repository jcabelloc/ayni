package pe.ayni.core.credito.dao;

import pe.ayni.core.credito.entity.CuentaCredito;

public interface CreditoDao {

	void create(CuentaCredito credito);

	CuentaCredito findById(Integer idCuenta);

}
