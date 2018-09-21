package pe.ayni.core.persona.dao;

import java.util.List;

import pe.ayni.core.persona.constraint.TelefonoConstraint.EstadoTelefono;
import pe.ayni.core.persona.entity.Telefono;

public interface TelefonoDao {
	
	void create(Telefono telefono);

	void update(Telefono telefono);

	List<Telefono> findAllByEstadoAndIdPersona(EstadoTelefono estado, Integer idPersona);

	Telefono findById(Integer id);
}
