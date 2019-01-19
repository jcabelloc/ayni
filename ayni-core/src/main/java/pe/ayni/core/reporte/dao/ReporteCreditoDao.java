package pe.ayni.core.reporte.dao;

import java.util.List;

import pe.ayni.core.credito.constraint.CreditoConstraint.EstadoCredito;

public interface ReporteCreditoDao {

	List<Object[]> getCarteraCreditos(EstadoCredito estado);

	List<Object[]> getAmortizaciones(int month, int year);

}
