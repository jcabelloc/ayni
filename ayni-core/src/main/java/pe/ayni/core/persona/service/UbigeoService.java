package pe.ayni.core.persona.service;


import pe.ayni.core.persona.dto.ConfiguracionUbigeoDto;
import pe.ayni.core.persona.dto.ConfiguracionUbigeoDto.Departamento;
import pe.ayni.core.persona.dto.ConfiguracionUbigeoDto.Distrito;
import pe.ayni.core.persona.dto.ConfiguracionUbigeoDto.Provincia;


public interface UbigeoService {
	
	ConfiguracionUbigeoDto getConfiguracionUbigeo();
	ConfiguracionUbigeoDto getConfiguracionUbigeo2();

	Departamento findDptoByIdUbigeo(Integer idUbigeoDpto);

	Provincia findProvinciaByIdUbigeo(Departamento departamento, Integer idUbigeoProvincia);

	Distrito findDistritoByIdUbigeo(Provincia provincia, Integer idUbigeoDistrito);
	
}
