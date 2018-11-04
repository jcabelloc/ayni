package pe.ayni.core.operacion.caja.service;

import pe.ayni.core.operacion.caja.dto.TraspasoEfectivoDto;

public interface OperacionCajaService {

	TraspasoEfectivoDto createTraspasoEfectivo(TraspasoEfectivoDto traspaso);

	TraspasoEfectivoDto findTraspasoEfectivoById(Integer id);

}
