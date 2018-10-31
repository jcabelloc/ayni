package pe.ayni.core.cuentagasto.dao;

import java.util.List;

import pe.ayni.core.cuentagasto.entity.CuentaGasto;

public interface CuentaGastoDao {

	List<CuentaGasto> findAll();

}
