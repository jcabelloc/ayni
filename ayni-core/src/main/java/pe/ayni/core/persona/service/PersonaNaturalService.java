package pe.ayni.core.persona.service;

import java.util.List;

import pe.ayni.core.persona.dto.PersonaNaturalDto;
import pe.ayni.core.persona.entity.PersonaNatural;

public interface PersonaNaturalService {
	
	void createPersonaNatural(PersonaNaturalDto personaNatural);
	
	PersonaNaturalDto findPersonaNaturalById(Integer id);
	
	void updatePersonaNatural(PersonaNaturalDto personaNatural);
	
	List<PersonaNaturalDto> findFirstNumberOfPersonasNaturales(int max);
	
	List<PersonaNaturalDto> findPersonasNaturalesBy(String by, String input);
	
	List<PersonaNaturalDto> findFirstNumberOfExtensionPersonasNaturales(int max);

	List<PersonaNaturalDto> findExtensionPersonasNaturalesBy(String by, String input);
	
	PersonaNaturalDto buildDtoFrom (PersonaNatural personaNatural);

}
