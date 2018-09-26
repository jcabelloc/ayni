package pe.ayni.core.credito.dao;

import java.util.List;

import pe.ayni.core.credito.entity.CuentaCredito;

public interface CreditoDao {

	void create(CuentaCredito credito);

	CuentaCredito findById(Integer idCuenta);

	List<CuentaCredito> findByIdCliente(Integer id);

}
