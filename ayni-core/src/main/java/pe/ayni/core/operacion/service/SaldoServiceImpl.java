package pe.ayni.core.operacion.service;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.ayni.core.cuenta.constraint.CuentaContableConstraint.Naturaleza;
import pe.ayni.core.cuenta.entity.CuentaContable;
import pe.ayni.core.cuenta.service.CuentaContableService;
import pe.ayni.core.operacion.dao.SaldoDao;
import pe.ayni.core.operacion.entity.DetalleOperacion;
import pe.ayni.core.operacion.entity.Operacion;
import pe.ayni.core.operacion.entity.Saldo;

@Service
public class SaldoServiceImpl implements SaldoService {
	
	@Autowired
	SaldoDao saldoDao;
	
	@Autowired
	CuentaContableService cuentaContableService;
	
	@Override
	@Transactional
	public void updateSaldo(Operacion operacion) {
		
		for(DetalleOperacion detalle: operacion.getDetallesOperacion()) {
			
			Saldo saldo = saldoDao.findByCuentaContableAndCuenta(detalle.getCuentaContable(), detalle.getCuenta());
			if (saldo == null) {
				saldo = new Saldo(detalle.getCuentaContable(), detalle.getCuenta(), BigDecimal.ZERO);
				saldoDao.save(saldo);
			}
			CuentaContable cuentaContable = cuentaContableService.findCuentaContableByCta(detalle.getCuentaContable().getCtaContable());
			if (cuentaContable.getNaturaleza().equals(Naturaleza.DEUDORA)) {
				saldo.setSaldo(saldo.getSaldo().add(detalle.getDebito()).subtract(detalle.getCredito()));
			} else if (cuentaContable.getNaturaleza().equals(Naturaleza.ACREEDORA)) {
				saldo.setSaldo(saldo.getSaldo().add(detalle.getCredito()).subtract(detalle.getDebito()));
			}
			saldoDao.save(saldo);
		}
		
	}

}
