package pe.ayni.core.operacion.dao;

import pe.ayni.core.operacion.entity.Operacion;

public interface OperacionDao {

	Integer save(Operacion operacion);

	Operacion findById(Integer id);

}
