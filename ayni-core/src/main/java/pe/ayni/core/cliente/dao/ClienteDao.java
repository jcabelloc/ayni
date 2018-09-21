package pe.ayni.core.cliente.dao;

import java.util.List;

import pe.ayni.core.cliente.entity.Cliente;

public interface ClienteDao {
	
	void create(Cliente cliente);

	List<Cliente> findBy(String by, String input);

	Cliente findById(Integer id);

	void update(Cliente cliente);

	Cliente findByIdCuentaCredito(Integer idCuenta);
}
