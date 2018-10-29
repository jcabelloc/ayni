package pe.ayni.core.proveedor.dao;

import java.util.List;

import pe.ayni.core.proveedor.entity.Proveedor;

public interface ProveedorDao {

	List<Proveedor> findBy(String by, String input);

}
