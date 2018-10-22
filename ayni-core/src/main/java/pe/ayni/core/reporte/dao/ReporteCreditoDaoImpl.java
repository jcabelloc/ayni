package pe.ayni.core.reporte.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public class ReporteCreditoDaoImpl implements ReporteCreditoDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public List<Object[]> getCarteraCreditos() {
		
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Object[]> creditos = session.createNativeQuery(
				" SELECT cl.id, pe.nombre, pe.nroIdentificacion, " 
						+ "   IFNULL((SELECT CONCAT(dr.tipoVia, ' ' ,dr.nombreVia, ' ', dr.numeroVia, ' Mza ', dr.manzana, ' lote ', " 
						+ "	  dr.lote, ' ', dr.tipoLocalidad, ' ', dr.nombreLocalidad, ' ', dr.distrito, '-', dr.provincia, '-', dr.departamento ) " 
						+ "	FROM Direccion dr WHERE dr.idPersona = pe.id and dr.estado = 'ACTIVO' and tipo = 'CASA' LIMIT 1), 'N/D') direccion0, " 
						+ "   IFNULL((SELECT CONCAT(dr.tipoVia, ' ' , dr.nombreVia, ' ', dr.numeroVia, ' Mza ', dr.manzana, ' lote ', " 
						+ "	  dr.lote, ' ', dr.tipoLocalidad, ' ', dr.nombreLocalidad, ' ', dr.distrito, '-', dr.provincia, '-', dr.departamento ) " 
						+ "	FROM Direccion dr WHERE dr.idPersona = pe.id and dr.estado = 'ACTIVO' and tipo = 'NEGOCIO' LIMIT 1),'N/D') direccion1, "
						+ "   IFNULL((SELECT CONCAT(tl.codTelefonicoDpto, '-', tl.numero) " 
						+ "	FROM Telefono tl WHERE tl.idPersona = pe.id and tl.estado = 'ACTIVO' LIMIT 1), 'N/D') telefono, " 
						+ "   cr.idCuenta, (CASE WHEN  ct.moneda = '1' THEN 'SOLES' ELSE 'NA' END) moneda, " 
						+ "   cr.montoDesembolso, DATE_FORMAT(cr.fechaDesembolso, '%d/%m/%Y') fechaDesembolso, cr.nroCuotas nroCuotasDesembolso,  " 
						+ "   ct.usuarioResponsable asesor, cr.nroCondicion, cr.estado, " 
						+ "   cr.tem, cr.frecuencia, cr.montoCuota, DATE_FORMAT(cr.fechaPrimeraCuota, '%d/%m/%Y') fechaPrimeraCuota, " 
						+ "   (SELECT SUM(dc.montoProgramado - dc.montoPagado) FROM DetalleCredito dc " 
						+ "		WHERE dc.idCuenta = cr.idCuenta " 
						+ "		AND dc.nroConcepto = 0 " 
						+ "		AND dc.nroCondicion = cr.nroCondicion " 
						+ "	) saldoCapital, " 
						+ "    IFNULL((SELECT DATEDIFF(DATE_FORMAT(SYSDATE(),'%Y-%m-%d'), MIN(dc.fechaVencimiento)) FROM DetalleCredito dc " 
						+ "		WHERE dc.idCuenta = cr.idCuenta " 
						+ "		AND dc.nroConcepto = 0 " 
						+ "		AND dc.nroCondicion = cr.nroCondicion " 
						+ "		AND dc.montoProgramado > dc.montoPagado " 
						+ "	),'N/D') diasAtraso, " 
						+ "    IFNULL((SELECT DATE_FORMAT(MAX(op.fechaOperacion), '%d/%m/%Y') FROM DetalleOperacion do, Operacion op " 
						+ "		WHERE do.idCuenta = cr.idCuenta "  
						+ "		AND do.idOperacion = op.id " 
						+ "		AND op.tipoOperacion = 'AMORTIZACION_CRED' " 
						+ "	), 'N/D') ultimaFechaPago, " 
						+ "   cr.nroCuotas, " 
						+ "    ( SELECT COUNT(dc.id) FROM DetalleCredito dc " 
						+ "		WHERE dc.idCuenta = cr.idCuenta " 
						+ "		AND dc.nroConcepto = 0 " 
						+ "		AND dc.nroCondicion = cr.nroCondicion " 
						+ "		AND dc.montoProgramado = dc.montoPagado " 
						+ "        AND dc.nroCuota > 0 " 
						+ "    ) cuotasPagadas, " 
						+ "     (SELECT COUNT(dc.id) FROM DetalleCredito dc " 
						+ "		WHERE dc.idCuenta = cr.idCuenta " 
						+ "		AND dc.nroConcepto = 0 "  
						+ "		AND dc.nroCondicion = cr.nroCondicion " 
						+ "		AND dc.montoProgramado > dc.montoPagado" 
						+ "	) cuotasPendientes " 
						+ "  FROM CuentaCredito cr, Cliente cl, Persona pe, PersonaNatural pn, Cuenta ct " 
						+ "  WHERE cr.idCliente = cl.id " 
						+ "   AND cl.idPersonaNatural = pn.id " 
						+ "   AND pn.id = pe.id " 
						+ "   AND cr.idCuenta = ct.idCuenta "
			).list();
		
		return creditos;
		 
	}

	@Override
	public List<Object[]> getAmortizaciones() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Object[]> amortizaciones = session.createNativeQuery(
				 " SELECT  " 
						+ "   T.idCuenta, T.nroCondicion, T.nroCuota, DATE_FORMAT(T.fechaVencimiento,'%d/%m/%Y') fechaVencimiento, T.capProgramado, " 
						+ "   T.intProgramado, T.idOperacion, DATE_FORMAT(op.fechaOperacion, '%d/%m/%Y') fechaOperacion,  " 
						+ "   (CASE WHEN  op.moneda = '1' THEN 'SOLES' ELSE 'NA' END) moneda, op.monto, T.capAmortizado, T.intAmortizado " 
						+ " FROM Operacion op,  " 
						+ " 	  (SELECT do.idOperacion, dc.idCuenta, dc.nroCondicion, dc.nroCuota,  " 
						+ " 			SUM(CASE WHEN dc.nroConcepto = 0 THEN do.credito ELSE 0 END) capAmortizado, " 
						+ " 			SUM(CASE WHEN dc.nroConcepto = 1 THEN do.credito ELSE 0 END) intAmortizado, " 
						+ " 			MAX(CASE WHEN dc.nroConcepto = 0 THEN dc.montoProgramado ELSE 0 END) capProgramado, " 
						+ " 			MAX(CASE WHEN dc.nroConcepto = 1 THEN dc.montoProgramado ELSE 0 END) intProgramado, " 
						+ " 			MAX(dc.fechaVencimiento) fechaVencimiento " 
						+ " 		 FROM Operacion op, DetalleOperacion do, DetalleCredito dc " 
						+ " 		WHERE op.tipoOperacion = 'AMORTIZACION_CRED' " 
						+ " 		  AND op.id = do.idOperacion " 
						+ " 		  AND do.idDetalleCredito = dc.id " 
						+ " 		  GROUP BY dc.idCuenta, dc.nroCondicion, dc.nroCuota, do.idOperacion) T " 
						+ "   WHERE op.id = T.idOperacion " 
						+ "   ORDER BY T.idCuenta, T.nroCondicion, T.nroCuota, T.idOperacion "
			).list();
		
		return amortizaciones;
	}
	
}
