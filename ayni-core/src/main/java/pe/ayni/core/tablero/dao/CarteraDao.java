package pe.ayni.core.tablero.dao;

import java.util.List;

public interface CarteraDao {

	List<Object[]> queryCarteraSaldo(String mes, String groupBy);

	List<Object[]> queryCarteraAtrasada(Integer diasAtrasoMayorA, String mes, String groupBy);

}
