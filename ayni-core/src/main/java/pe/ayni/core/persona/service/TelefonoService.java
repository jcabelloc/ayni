package pe.ayni.core.persona.service;

import java.util.List;

import pe.ayni.core.persona.constraint.TelefonoConstraint.EstadoTelefono;
import pe.ayni.core.persona.dto.TelefonoDto;
import pe.ayni.core.persona.dto.TelefonoFormDto;
import pe.ayni.core.persona.entity.Persona;

public interface TelefonoService {
	TelefonoFormDto getTelefonoForm();

	void createTelefono(Integer idPersona, TelefonoDto telefono);

	void deleteTelefono(Persona persona, Integer idTelefono);

	List<TelefonoDto> findAllTelefonosByEstadoAndIdPersona(EstadoTelefono estado, Integer idPersona);

}
