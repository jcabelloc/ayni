package pe.ayni.core.persona.service;

import java.util.List;

import pe.ayni.core.persona.constraint.DireccionConstraint.EstadoDireccion;
import pe.ayni.core.persona.dto.DireccionDto;
import pe.ayni.core.persona.entity.Persona;

public interface DireccionService {
	void createDireccion(Integer idPersona, DireccionDto direccion);

	void deleteDireccion(Persona persona, Integer idDireccion);

	List<DireccionDto> findAllDireccionesByEstadoAndIdPersona(EstadoDireccion estado, Integer idPersona);


}
