package pe.ayni.core.gasto.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.ayni.core.gasto.dao.CuentaGastoDao;
import pe.ayni.core.gasto.dto.CuentaGastoDto;
import pe.ayni.core.gasto.entity.CuentaGasto;

@Service
public class CuentaGastoServiceImpl implements CuentaGastoService {
	
	@Autowired
	CuentaGastoDao cuentaGastoDao;
	
	@Override
	@Transactional
	public List<CuentaGastoDto> findAllCuentasGasto() {
		List<CuentaGastoDto> cuentasGastoDto = new ArrayList<>();
		List<CuentaGasto> cuentasGasto = cuentaGastoDao.findAll();
		for (CuentaGasto cuentaGasto: cuentasGasto) {
			CuentaGastoDto cuentaGastoDto = buildDtoFrom(cuentaGasto);
			cuentasGastoDto.add(cuentaGastoDto);
		}
		return cuentasGastoDto;
	}

	private CuentaGastoDto buildDtoFrom(CuentaGasto cuentaGasto) {
		return new CuentaGastoDto(cuentaGasto);
	}

}
