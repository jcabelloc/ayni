package pe.ayni.core.gasto.service;

import java.util.List;

import pe.ayni.core.gasto.dto.CuentaGastoDto;

public interface CuentaGastoService {

	List<CuentaGastoDto> findAllCuentasGasto();

}
