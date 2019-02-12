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
	public List<Object[]> queryCarteraSaldo(String mes, String groupBy) {

		Session session = sessionFactory.getCurrentSession();
		String query = 
				" SELECT df." + groupBy + ", " + " SUM(" + "saldoCapital" + ") " + 
						" FROM FactCarteraCreditos fc, DimFecha df " + 
						" WHERE fc.idFecha = df.idFecha " + 
						"   AND df.mes = '" + mes + "' " + 
						" GROUP BY " + groupBy +  
						" ORDER BY " + groupBy;
		
		@SuppressWarnings("unchecked")
		List<Object[]> data = session.createNativeQuery(query).list();
		return data;
	}

	@Override
	public List<Object[]> queryCarteraAtrasada(Integer diasAtrasoMayorA, String mes, String groupBy) {
		Session session = sessionFactory.getCurrentSession();
		String query = 
				 "SELECT df." + groupBy + ", " + 
						" SUM(CASE WHEN fc.diasAtraso > ?1 then fc.saldoCapital else 0 end) " + 
						" FROM FactCarteraCreditos fc, DimFecha df " + 
						" WHERE fc.idFecha = df.idFecha " + 
						"   AND df.mes = '" + mes + "' " + 
						" GROUP BY " + groupBy +  
						" ORDER BY " + groupBy;
		
		@SuppressWarnings("unchecked")
		List<Object[]> data = session.createNativeQuery(query)
			.setParameter(1, diasAtrasoMayorA).list();
		return data;
	}
	
}
