package pe.ayni.core.tablero.dao;

import java.util.List;

public interface CarteraDao {

	List<Object[]> queryCarteraSaldo(String desde, String hasta, String groupBy);

	List<Object[]> queryCarteraAtrasada(Integer diasAtrasoMayorA, String desde, String hasta, String groupBy);

	List<Object[]> queryDesembolsos(String valor, String desde, String hasta, String groupBy);

}
