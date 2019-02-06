package pe.ayni.core.reporte.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReporteOperacionDaoImpl implements ReporteOperacionDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public List<Object[]> getOperaciones(String desde, String hasta) {
		Session session = sessionFactory.getCurrentSession();
		String query = 				 
				" SELECT	" 
					+ " op.id Nro_Operacion, op.usuario usuario, op.tipoOperacion tipoOperacion, "
					+ " DATE_FORMAT(op.fechaOperacion, '%d/%m/%Y') fechaOperacion, "
					+ " (CASE WHEN  op.moneda = '1' THEN 'SOLES' ELSE 'NA' END) moneda, op.monto, "
					+ " do.nroDetalle nroDetalle, "
					+ " do.idCuenta idCuenta, "
					+ " do.ctaContable ctaContable, "
					+ " (SELECT cc.tipoCuenta FROM CuentaContable cc WHERE cc.ctaContable = do.ctaContable) tipoCuenta, "
					+ " do.debito debito, "
					+ " do.credito credito "
				+ " FROM  Operacion op, DetalleOperacion do " 
				+ " WHERE op.id = do.idOperacion "
				+ " AND op.fechaOperacion BETWEEN ?1 AND ?2 "
				+ " ORDER BY op.id, do.nroDetalle ";
		@SuppressWarnings("unchecked")
		List<Object[]> amortizaciones = session.createNativeQuery(query)
			.setParameter(1,desde).setParameter(2,hasta).list();
		
		return amortizaciones;
	}

	
}
