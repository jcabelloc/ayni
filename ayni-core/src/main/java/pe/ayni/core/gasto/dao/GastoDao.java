package pe.ayni.core.gasto.dao;

import pe.ayni.core.gasto.entity.Gasto;

public interface GastoDao {

	Integer save(Gasto gasto);

	Gasto findById(Integer id);

}
