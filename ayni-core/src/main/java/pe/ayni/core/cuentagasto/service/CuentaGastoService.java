package pe.ayni.core.cuentagasto.service;

import java.util.List;

import pe.ayni.core.cuentagasto.dto.CuentaGastoDto;

public interface CuentaGastoService {

	List<CuentaGastoDto> findAllCuentasGasto();

}
