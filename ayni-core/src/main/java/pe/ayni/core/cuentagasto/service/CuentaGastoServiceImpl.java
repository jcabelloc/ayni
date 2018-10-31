package pe.ayni.core.cuentagasto.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.ayni.core.cuentagasto.dao.CuentaGastoDao;
import pe.ayni.core.cuentagasto.dto.CuentaGastoDto;
import pe.ayni.core.cuentagasto.entity.CuentaGasto;

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
