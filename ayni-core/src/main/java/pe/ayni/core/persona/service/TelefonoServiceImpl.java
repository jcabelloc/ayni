package pe.ayni.core.persona.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.ayni.core.persona.constraint.TelefonoConstraint;
import pe.ayni.core.persona.constraint.TelefonoConstraint.EstadoTelefono;
import pe.ayni.core.persona.constraint.TelefonoConstraint.TipoTelefono;
import pe.ayni.core.persona.dao.TelefonoDao;
import pe.ayni.core.persona.dto.TelefonoDto;
import pe.ayni.core.persona.dto.TelefonoFormDto;
import pe.ayni.core.persona.dto.TelefonoFormDto.Option;
import pe.ayni.core.persona.entity.Persona;
import pe.ayni.core.persona.entity.Telefono;

@Service
public class TelefonoServiceImpl implements TelefonoService {
	
	@Autowired
	PersonaService personaService;
	
	@Autowired
	TelefonoDao telefonoDao;
	
	@Transactional
	@Override
	public TelefonoFormDto getTelefonoForm() {
		TelefonoFormDto telefonoForm = new TelefonoFormDto();
		
		List<Option> optionsTipoTelefono = new ArrayList<>();
		for(TipoTelefono tipoTelefono: TipoTelefono.values()) {
			optionsTipoTelefono.add(telefonoForm.new Option(tipoTelefono.toString(), tipoTelefono.toString()));
		}
		telefonoForm.setOptionsTipoTelefono(optionsTipoTelefono);;
		
		List<Option> optionsCodTelefonicoDpto = new ArrayList<>();
		TelefonoConstraint.CODIGOS_TELEFONICO_DPTO.forEach((k,v)-> {
			optionsCodTelefonicoDpto.add(telefonoForm.new Option(k , v));
		});
		
		telefonoForm.setOptionsCodTelefonicoDpto(optionsCodTelefonicoDpto);
		
		return telefonoForm;
	}
	
	@Transactional
	@Override
	public void createTelefono(Integer idPersona, TelefonoDto telefonoDto) {
		Telefono telefono = new Telefono();
		
		telefono.setCodTelefonicoDpto(telefonoDto.getCodTelefonicoDpto());
		telefono.setId(null);
		telefono.setEstado(EstadoTelefono.ACTIVO);
		telefono.setFechaHoraInsercion(LocalDateTime.now());
		telefono.setNumero(telefonoDto.getNumero());
		telefono.setTipo(TipoTelefono.valueOf(telefonoDto.getTipo()));
		Persona persona = personaService.findPersonaById(idPersona);
		telefono.setPersona(persona);
		
		telefonoDao.create(telefono);
		
		telefonoDto.setId(telefono.getId());
		
	}
	
	@Transactional
	@Override
	public void deleteTelefono(Persona persona, Integer idTelefono) {
		Telefono telefono = telefonoDao.findById(idTelefono);
		if (!telefono.getPersona().equals(persona)) {
			// do something
		}
		telefono.setEstado(EstadoTelefono.INACTIVO);
		telefono.setFechaHoraModificacion(LocalDateTime.now());
		telefonoDao.update(telefono);
	}
	
	@Transactional
	@Override
	public List<TelefonoDto> findAllTelefonosByEstadoAndIdPersona(EstadoTelefono estado, Integer idPersona) {
		List<Telefono> telefonos = telefonoDao.findAllByEstadoAndIdPersona(estado, idPersona);
		
		List<TelefonoDto> telefonosDto = new ArrayList<>();
		ModelMapper modelMapper = new ModelMapper();
		for (Telefono telefono: telefonos) {
			TelefonoDto telefonoDto = modelMapper.map(telefono, TelefonoDto.class);
			telefonosDto.add(telefonoDto);
		}
		return telefonosDto;
	}
	
	

}
