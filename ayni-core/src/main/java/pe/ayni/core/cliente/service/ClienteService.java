package pe.ayni.core.cliente.service;

import java.util.List;

import pe.ayni.core.cliente.dto.ClienteDto;

public interface ClienteService {
	
	void createCliente(ClienteDto cliente);

	List<ClienteDto> findClientesBy(String by, String input);

	ClienteDto findClienteById(Integer id);

	ClienteDto updateCliente(ClienteDto cliente);

	ClienteDto findClienteByIdCuentaCredito(Integer idCuenta);
}
