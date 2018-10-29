package pe.ayni.core.proveedor.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.ayni.core.proveedor.entity.Proveedor;

@Repository
public class ProveedorDaoImpl implements ProveedorDao {
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public List<Proveedor> findBy(String by, String input) {
		Session session = sessionFactory.getCurrentSession();
		String stringQuery = "SELECT a FROM Proveedor a JOIN FETCH a.persona ";
		
		if (by.toUpperCase().equals("DNI")) {
			stringQuery += "WHERE a.persona.tipoIdentificacion = 'DNI' and a.persona.nroIdentificacion =:input ";
		} else if (by.toUpperCase().equals("NOMBRE")) {
			input = input.toUpperCase() + "%";
			stringQuery += "WHERE a.persona.nombre LIKE :input ";
		}
		
		List<Proveedor> proveedores = session.createQuery(stringQuery, Proveedor.class)
				.setMaxResults(100)
				.setParameter("input", input)
				.getResultList();

		return proveedores;
	}

}
