package pe.ayni.core.persona.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.ayni.core.persona.constraint.DireccionConstraint.EstadoDireccion;
import pe.ayni.core.persona.constraint.TelefonoConstraint.EstadoTelefono;
import pe.ayni.core.persona.dao.PersonaDao;
import pe.ayni.core.persona.dto.DireccionDto;
import pe.ayni.core.persona.dto.TelefonoDto;
import pe.ayni.core.persona.entity.Persona;

@Service
public class PersonaServiceImpl implements PersonaService {
	

	@Autowired
	PersonaDao personaDao;
	
	@Autowired
	DireccionService direccionService;
	
	@Autowired
	TelefonoService telefonoService;
	
	@Transactional
	@Override
	public Persona findPersonaById(Integer id) {
		return personaDao.findById(id) ;
	}
	
	@Transactional
	@Override
	public void addDireccion(Integer idPersona, DireccionDto direccion) {
		direccionService.createDireccion(idPersona, direccion);
	}
	
	@Transactional
	@Override
	public void deleteDireccion(Integer idPersona, Integer idDireccion) {
		Persona persona = findPersonaById(idPersona);
		direccionService.deleteDireccion(persona, idDireccion);
	}
	
	
	@Transactional
	@Override
	public List<DireccionDto> findAllDireccionesByIdPersona(Integer idPersona) {
		return direccionService.findAllDireccionesByEstadoAndIdPersona(EstadoDireccion.ACTIVO, idPersona);

	}
	
	@Transactional
	@Override
	public void addTelefono(Integer idPersona, TelefonoDto telefono) {
		telefonoService.createTelefono(idPersona, telefono);
	}
	
	@Transactional
	@Override
	public void deleteTelefono(Integer idPersona, Integer idTelefono) {
		Persona persona = findPersonaById(idPersona);
		telefonoService.deleteTelefono(persona, idTelefono);
	}
	
	
	@Transactional
	@Override
	public List<TelefonoDto> findAllTelefonosByIdPersona(Integer idPersona) {
		return telefonoService.findAllTelefonosByEstadoAndIdPersona(EstadoTelefono.ACTIVO, idPersona);

	}


}
