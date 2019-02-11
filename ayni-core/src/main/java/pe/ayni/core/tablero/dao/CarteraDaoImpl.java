package pe.ayni.core.tablero.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CarteraDaoImpl implements CarteraDao {
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public List<Object[]> queryCartera(String valor, String mes, String groupBy) {

		Session session = sessionFactory.getCurrentSession();
		String query = 
				" SELECT df." + groupBy + ", " + " SUM(" + valor + ") " + 
						" FROM FactCarteraCreditos fc, DimFecha df " + 
						" WHERE fc.idFecha = df.idFecha " + 
						"   AND df.mes = '" + mes + "' " + 
						" GROUP BY " + groupBy +  
						" ORDER BY " + groupBy;
		
		@SuppressWarnings("unchecked")
		List<Object[]> data = session.createNativeQuery(query).list();
		return data;
	}
	
}
