package pe.ayni.core.banco.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.ayni.core.banco.entity.DetalleBanco;

@Repository
public class DetalleBancoDaoImpl implements DetalleBancoDao {
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public Integer save(DetalleBanco detalleBanco) {
		Session session = sessionFactory.getCurrentSession();
		return (Integer)session.save(detalleBanco);
	}

}
