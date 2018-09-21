package pe.ayni.core.persona.dao;

import java.util.List;

import pe.ayni.core.persona.dto.PersonaNaturalDto;
import pe.ayni.core.persona.entity.PersonaNatural;

public interface PersonaNaturalDao {
	
	void create(PersonaNatural personaNatural);
	
	void update(PersonaNatural personaNatural);

	PersonaNatural findById(Integer id);
	
	List<PersonaNatural> findAll();
	
	PersonaNatural findByNroDocumento(String nroDocumento);
	
	List<PersonaNatural> findByNameLike(String name);

	List<PersonaNatural> findFirstNumberOf(int max);

	List<PersonaNatural> findBy(String by, String input);

	List<PersonaNaturalDto> findFirstNumberOfExtension(int max);

	List<PersonaNaturalDto> findExtensionBy(String by, String input);
}
