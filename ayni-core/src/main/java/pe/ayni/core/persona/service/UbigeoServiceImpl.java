package pe.ayni.core.persona.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.ayni.core.persona.dao.UbigeoDao;
import pe.ayni.core.persona.dto.ConfiguracionUbigeoDto;
import pe.ayni.core.persona.dto.ConfiguracionUbigeoDto.Departamento;
import pe.ayni.core.persona.dto.ConfiguracionUbigeoDto.Distrito;
import pe.ayni.core.persona.dto.ConfiguracionUbigeoDto.Provincia;
import pe.ayni.core.persona.entity.Ubigeo;

@Service
public class UbigeoServiceImpl implements UbigeoService{
	
	@Autowired
	UbigeoDao ubigeoDao;
	
	@Deprecated
	@Transactional
	@Override
	public ConfiguracionUbigeoDto getConfiguracionUbigeo2() {
		ConfiguracionUbigeoDto ubigeo = ConfiguracionUbigeoDto.getInstance();
		if (ubigeo.getDepartamentos() == null) {
			List<Departamento> departamentos = ubigeoDao.findAllDepartamentos();
			for (Departamento dpto:departamentos) {
				List<Provincia> provincias = ubigeoDao.findAllProvinciasByCodDpto(dpto.getCodDpto());
				for (Provincia provincia:provincias) {
					List<Distrito> distritos = ubigeoDao.findAllDistritosByCodDptoAndCodProvincia(dpto.getCodDpto(), provincia.getCodProvincia());
					provincia.setDistritos(distritos);
				}
				dpto.setProvincias(provincias);
			}
			ubigeo.setDepartamentos(departamentos);
		}
		return ubigeo;
	}
	
	@Transactional
	@Override
	public ConfiguracionUbigeoDto getConfiguracionUbigeo() {
		ConfiguracionUbigeoDto ubigeo = ConfiguracionUbigeoDto.getInstance();
		
		if (ubigeo.getDepartamentos() == null) {
			
			List<Ubigeo> ubigeos =  ubigeoDao.findAll();
			List<Departamento> departamentos = findAllDepartamentos(ubigeos);
			ubigeo.setDepartamentos(departamentos);
		}
		return ubigeo;
	}
	
	private List<Departamento> findAllDepartamentos(List<Ubigeo> ubigeos){
		List<Departamento> departamentos = ubigeos.stream()
				.filter(e -> e.getCodProvincia().equals("00") && e.getCodDistrito().equals("00"))
				.map(e -> {
					Departamento dpto = new Departamento(e.getId(),e.getCodDpto(), e.getNombre());
					List<Provincia> provincias = findAllProvinciasByCodDpto(ubigeos, dpto.getCodDpto());
					dpto.setProvincias(provincias);
					return dpto;
				})
				.collect(Collectors.toList());
		return departamentos;
	}
	
	private List<Provincia> findAllProvinciasByCodDpto(List<Ubigeo> ubigeos, String codDpto) {
		List<Provincia> provincias = ubigeos.stream()
			.filter(e -> e.getCodDpto().equals(codDpto) && e.getCodDistrito().equals("00") && !e.getCodProvincia().equals("00"))
			.map( e -> {
				Provincia provincia =  new Provincia(e.getId(), e.getCodProvincia(), e.getNombre());
				List<Distrito> distritos =  findAllDistritosByCodDptoAndCodProvincia(ubigeos, codDpto, provincia.getCodProvincia());
				provincia.setDistritos(distritos);
				return provincia;
			})
			.collect(Collectors.toList());
		
		return provincias;
	}

	private List<Distrito> findAllDistritosByCodDptoAndCodProvincia(List<Ubigeo> ubigeos, String codDpto,
			String codProvincia) {
		List<Distrito> distritos = ubigeos.stream()
				.filter(e -> e.getCodDpto().equals(codDpto) && e.getCodProvincia().equals(codProvincia) && !e.getCodDistrito().equals("00"))
				.map ( e -> new Distrito(e.getId(), e.getNombre()))
				.collect(Collectors.toList());
		return distritos;
	}

	@Transactional
	@Override
	public Departamento findDptoByIdUbigeo(Integer idUbigeoDpto) {
		ConfiguracionUbigeoDto ubigeoDto = getConfiguracionUbigeo();
		Departamento departamento = ubigeoDto.getDepartamentos()
			.stream()
			.filter(dpto -> dpto.getId().equals(idUbigeoDpto))
			.findFirst()
			.get();
		
		return departamento;
	}
	
	@Transactional
	@Override
	public Provincia findProvinciaByIdUbigeo(Departamento departamento, Integer idUbigeoProvincia) {
		Provincia provincia = departamento.getProvincias()
				.stream()
				.filter(prov -> prov.getId().equals(idUbigeoProvincia))
				.findFirst()
				.get();

		return provincia;
	}
	
	@Transactional
	@Override
	public Distrito findDistritoByIdUbigeo(Provincia provincia, Integer idUbigeoDistrito) {
		Distrito distrito = provincia.getDistritos()
				.stream()
				.filter(dist -> dist.getId().equals(idUbigeoDistrito))
				.findFirst()
				.get();
		return distrito;
	}


}
