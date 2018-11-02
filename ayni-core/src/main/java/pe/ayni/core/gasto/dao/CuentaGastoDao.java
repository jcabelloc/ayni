package pe.ayni.core.gasto.dao;

import java.util.List;

import pe.ayni.core.gasto.entity.CuentaGasto;

public interface CuentaGastoDao {

	List<CuentaGasto> findAll();

}
