package pe.ayni.core.gasto.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.ayni.core.gasto.entity.Gasto;

@Repository
public class GastoDaoImpl implements GastoDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public Integer save(Gasto gasto) {
		Session session = sessionFactory.getCurrentSession();
		return (Integer)session.save(gasto);
	}
}
