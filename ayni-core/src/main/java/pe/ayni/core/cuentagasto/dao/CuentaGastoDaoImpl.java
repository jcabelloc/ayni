package pe.ayni.core.cuentagasto.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.ayni.core.cuentagasto.constraint.CuentaGastoConstraint;
import pe.ayni.core.cuentagasto.entity.CuentaGasto;

@Repository
public class CuentaGastoDaoImpl implements CuentaGastoDao {
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public List<CuentaGasto> findByIdProveedor(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String query = "SELECT a FROM CuentaGasto a JOIN FETCH a.cuentaContable "
				+ " WHERE a.estado =: estado "
				+ " AND a.proveedor.id = :id ";
		
		return session.createQuery(query, CuentaGasto.class)
				.setParameter("estado", CuentaGastoConstraint.EstadoCuentaGasto.ACTIVO)
				.setParameter("id", id)
				.getResultList();

	}
	
	 
}
