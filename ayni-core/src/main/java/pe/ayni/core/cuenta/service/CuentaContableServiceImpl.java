package pe.ayni.core.cuenta.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.ayni.core.cuenta.dao.CuentaContableDao;
import pe.ayni.core.cuenta.entity.CuentaContable;

@Service
public class CuentaContableServiceImpl implements CuentaContableService {
	
	@Autowired
	CuentaContableDao cuentaContableDao;
	
	@Override
	@Transactional
	public CuentaContable findCuentaContableByCta(String ctaContable) {
		return cuentaContableDao.findByCta(ctaContable);
	}
}
