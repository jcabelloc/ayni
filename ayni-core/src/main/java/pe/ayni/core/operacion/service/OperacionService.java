package pe.ayni.core.operacion.service;

import pe.ayni.core.operacion.dto.OperacionDto;

public interface OperacionService {

	OperacionDto createOperacion(OperacionDto operacionDto);

	OperacionDto findOperacionById(Integer id);

}
