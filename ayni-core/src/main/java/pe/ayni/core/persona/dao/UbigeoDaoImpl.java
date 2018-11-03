package pe.ayni.core.persona.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.ayni.core.persona.dto.ConfiguracionUbigeoDto.Departamento;
import pe.ayni.core.persona.dto.ConfiguracionUbigeoDto.Distrito;
import pe.ayni.core.persona.dto.ConfiguracionUbigeoDto.Provincia;
import pe.ayni.core.persona.entity.Ubigeo;

@Repository
public class UbigeoDaoImpl implements UbigeoDao {
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Deprecated
	@Override
	public List<Departamento> findAllDepartamentos() {
		List<Departamento> departamentos = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("SELECT  "
				+ " id, codDpto, nombre FROM Ubigeo WHERE codProvincia = '00' AND codDistrito = '00'");
		@SuppressWarnings("unchecked")
		List<Object[]> list = query.getResultList();
		for (Object[] line: list ) {
			departamentos.add(new Departamento((Integer)line[0], (String)line[1], (String)line[2]));
		}
		return departamentos;
	}
	
	@Deprecated
	@Override
	public List<Provincia> findAllProvinciasByCodDpto(String codDpto) {
		List<Provincia> provincias = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("SELECT  "
				+ " id, codProvincia, nombre FROM Ubigeo WHERE codDpto = :codDpto AND codDistrito = '00' and codProvincia <> '00'");
		query.setParameter("codDpto", codDpto);
		@SuppressWarnings("unchecked")
		List<Object[]> list = query.getResultList();
		for (Object[] line: list ) {
			provincias.add(new Provincia((Integer)line[0], (String)line[1], (String)line[2]));
		}
		return provincias;
	}
	
	@Deprecated
	@Override
	public List<Distrito> findAllDistritosByCodDptoAndCodProvincia(String codDpto, String codProvincia) {
		List<Distrito> distritos = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("SELECT  "
				+ " id, nombre FROM Ubigeo WHERE codDpto = :codDpto AND codProvincia = :codProvincia AND codDistrito <> '00'");
		query.setParameter("codDpto", codDpto);
		query.setParameter("codProvincia", codProvincia);
		@SuppressWarnings("unchecked")
		List<Object[]> list = query.getResultList();
		for (Object[] line: list ) {
			distritos.add(new Distrito((Integer)line[0], (String)line[1]));
		}
		return distritos;
	}

	@Override
	public List<Ubigeo> findAll() {
		Session session = sessionFactory.getCurrentSession();
		List<Ubigeo> ubigeos = session.createQuery(" SELECT a FROM Ubigeo a ", Ubigeo.class).getResultList();
		
		return ubigeos;
	}

}
