package pe.ayni.core.cliente.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.ayni.core.cliente.dao.ClienteDao;
import pe.ayni.core.cliente.dto.ClienteDto;
import pe.ayni.core.cliente.entity.Cliente;
import pe.ayni.core.persona.dto.PersonaNaturalDto;
import pe.ayni.core.persona.entity.PersonaNatural;
import pe.ayni.core.persona.service.PersonaNaturalService;

@Service
public class ClienteServiceImpl implements ClienteService {
	
	@Autowired
	ClienteDao clienteDao;
	
	@Autowired
	PersonaNaturalService personaNaturalService;
	
	@Override
	@Transactional
	public void createCliente(ClienteDto clienteDto) {
		
		personaNaturalService.createPersonaNatural(clienteDto.getPersonaNatural());
		
		PersonaNatural personaNatural = new PersonaNatural();
		personaNatural.setId(clienteDto.getPersonaNatural().getId());
		Cliente cliente = new Cliente();
		cliente.setPersonaNatural(personaNatural);
		cliente.setFechaRegistro(LocalDate.now());
		clienteDao.create(cliente);
		clienteDto.setId(cliente.getId());
	}

	@Override
	@Transactional
	public List<ClienteDto> findClientesBy(String by, String input) {
		List<ClienteDto> clientesDto = new ArrayList<>();
		List<Cliente> clientes = clienteDao.findBy(by, input);
		for (Cliente cliente:clientes) {
			ClienteDto clienteDto = buildDtoFrom(cliente);
			clientesDto.add(clienteDto);
		}
		return clientesDto;
	}

	@Override
	@Transactional
	public ClienteDto findClienteById(Integer id) {
		Cliente cliente = clienteDao.findById(id);
		ClienteDto clienteDto = buildDtoFrom(cliente);
		return clienteDto;
	}

	@Override
	@Transactional
	public ClienteDto findClienteByIdCuentaCredito(Integer idCuenta) {
		Cliente cliente = clienteDao.findByIdCuentaCredito(idCuenta);
		ClienteDto clienteDto = buildDtoFrom(cliente);
		return clienteDto;
	}

	private ClienteDto buildDtoFrom (Cliente cliente) {
		ModelMapper modelMapper = new ModelMapper();

		ClienteDto  clienteDto = modelMapper.map(cliente, ClienteDto.class);
		PersonaNaturalDto personaNaturalDto = personaNaturalService.buildDtoFrom(cliente.getPersonaNatural());
		clienteDto.setPersonaNaturalDto(personaNaturalDto);
		return clienteDto;
	}

	@Override
	@Transactional
	public ClienteDto updateCliente(ClienteDto clienteDto) {
		Cliente cliente = clienteDao.findById(clienteDto.getId());
		personaNaturalService.updatePersonaNatural(clienteDto.getPersonaNatural());
		setEntityDetails(cliente, clienteDto);	
		clienteDao.update(cliente);
		clienteDto = buildDtoFrom(cliente);
		return clienteDto;
	}

	private void setEntityDetails(Cliente cliente, ClienteDto clienteDto) {
		// nothing to set so far
	}

}
