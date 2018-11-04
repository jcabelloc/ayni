package pe.ayni.core.banco.dao;

import pe.ayni.core.banco.entity.DetalleBanco;

public interface DetalleBancoDao {

	Integer save(DetalleBanco detalleBanco);

	DetalleBanco findById(Integer id);

}
