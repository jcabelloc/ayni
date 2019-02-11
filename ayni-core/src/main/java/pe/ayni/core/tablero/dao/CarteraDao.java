package pe.ayni.core.tablero.dao;

import java.util.List;

public interface CarteraDao {

	List<Object[]> queryCartera(String valor, String mes, String groupBy);

}
