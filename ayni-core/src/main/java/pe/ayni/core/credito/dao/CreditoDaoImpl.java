package pe.ayni.core.credito.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.ayni.core.credito.constraint.CreditoConstraint.EstadoCredito;
import pe.ayni.core.credito.entity.CuentaCredito;

@Repository
public class CreditoDaoImpl implements CreditoDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void create(CuentaCredito credito) {
		
		Session session = sessionFactory.getCurrentSession();
		session.save(credito);
	}

	@Override
	public CuentaCredito findById(Integer idCuenta) {
		
		Session session = sessionFactory.getCurrentSession();
		return session.get(CuentaCredito.class, idCuenta);
	}

	@Override
	public List<CuentaCredito> findByIdCliente(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String query = "SELECT a FROM CuentaCredito a "
				+ " WHERE a.cliente.id = :id";
		
		return session.createQuery(query, CuentaCredito.class)
				.setParameter("id", id)
				.getResultList();
	}

	@Override
	public int updateEstado(Integer idCuenta, EstadoCredito estado) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("UPDATE CuentaCredito a "
				+ " SET a.estado = :estado "
				+ " WHERE a.idCuenta = :idCuenta ");
		query.setParameter("estado", estado);
		query.setParameter("idCuenta", idCuenta);
		return query.executeUpdate();
	}

}
