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
	public List<Object[]> queryCarteraSaldo(String desde, String hasta, String groupBy) {

		Session session = sessionFactory.getCurrentSession();
		String query = 
				" SELECT df." + groupBy + ", " + " SUM(" + "saldoCapital" + ") " + 
						" FROM FactCarteraCreditos fc, DimFecha df " + 
						" WHERE fc.idFecha = df.idFecha " + 
						"   AND df.fecha BETWEEN ?1 AND ?2 " + 
						" GROUP BY " + groupBy +  
						" ORDER BY " + groupBy;
		
		@SuppressWarnings("unchecked")
		List<Object[]> data = session.createNativeQuery(query)
			.setParameter(1, desde)
			.setParameter(2, hasta)
			.list();
		return data;
	}

	@Override
	public List<Object[]> queryCarteraAtrasada(Integer diasAtrasoMayorA, String desde, String hasta, String groupBy) {
		Session session = sessionFactory.getCurrentSession();
		String query = 
				 "SELECT df." + groupBy + ", " + 
						" SUM(CASE WHEN fc.diasAtraso > ?3 then fc.saldoCapital else 0 end) " + 
						" FROM FactCarteraCreditos fc, DimFecha df " + 
						" WHERE fc.idFecha = df.idFecha " + 
						"   AND df.fecha BETWEEN ?1 AND ?2 " + 
						" GROUP BY " + groupBy +  
						" ORDER BY " + groupBy;
		
		@SuppressWarnings("unchecked")
		List<Object[]> data = session.createNativeQuery(query)
			.setParameter(1, desde)
			.setParameter(2, hasta)
			.setParameter(3, diasAtrasoMayorA)
			.list();
		return data;
	}
	
	@Override
	public List<Object[]> queryDesembolsos(String valor, String desde, String hasta, String groupBy) {
		Session session = sessionFactory.getCurrentSession();
		String condicion = valor.equals("count")? " > 0" : "";
		String query = 
				 "SELECT df." + groupBy + ", " +  
						" SUM" + "(IFNULL(montoDesembolso,0) " + condicion + ") " +  
						" FROM DimFecha df " + 
						" LEFT JOIN FactOperacionCredito fo " + 
						" ON df.idFecha = fo.idFecha " +
						" AND fo.tipoOperacion = 'DESEMBOLSO_CRED' " +
						" WHERE  df.fecha BETWEEN ?1 AND ?2 " +  
						" GROUP BY " + groupBy +  
						" ORDER BY " + groupBy;
		
		@SuppressWarnings("unchecked")
		List<Object[]> data = session.createNativeQuery(query)
			.setParameter(1, desde).setParameter(2, hasta).list();
		return data;	
	}
	
}
