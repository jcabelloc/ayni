package pe.ayni.core.persona.service;

import java.util.List;

import pe.ayni.core.persona.dto.DireccionDto;
import pe.ayni.core.persona.dto.TelefonoDto;
import pe.ayni.core.persona.entity.Persona;

public interface PersonaService {
	
	Persona findPersonaById(Integer id);

	void addDireccion(Integer idPersona, DireccionDto direccion);

	List<DireccionDto> findAllDireccionesByIdPersona(Integer idPersona);
	
	void addTelefono(Integer idPersona, TelefonoDto telefono);
	
	List<TelefonoDto> findAllTelefonosByIdPersona(Integer idPersona);

	void deleteTelefono(Integer idPersona, Integer idTelefono);

	void deleteDireccion(Integer idPersona, Integer idDireccion);
	
}
