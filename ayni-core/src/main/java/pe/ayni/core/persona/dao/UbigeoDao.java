package pe.ayni.core.persona.dao;

import java.util.List;

import pe.ayni.core.persona.dto.ConfiguracionUbigeoDto.Departamento;
import pe.ayni.core.persona.dto.ConfiguracionUbigeoDto.Distrito;
import pe.ayni.core.persona.dto.ConfiguracionUbigeoDto.Provincia;

public interface UbigeoDao {
	
	List<Departamento> findAllDepartamentos();
	List<Provincia> findAllProvinciasByCodDpto(String codDpto);
	List<Distrito> findAllDistritosByCodDptoAndCodProvincia(String codDpto, String codProvincia);
}
