package pe.ayni.core.operacion.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.ayni.core.banco.entity.DetalleBanco;
import pe.ayni.core.credito.dto.DetalleCreditoDto;
import pe.ayni.core.credito.entity.DetalleCredito;
import pe.ayni.core.credito.service.DetalleCreditoService;
import pe.ayni.core.cuenta.entity.Cuenta;
import pe.ayni.core.cuenta.entity.CuentaContable;
import pe.ayni.core.cuenta.service.CuentaService;
import pe.ayni.core.operacion.constraint.DetalleOperacionConstraint.DebitoCredito;
import pe.ayni.core.operacion.dto.DetalleOperacionDto;
import pe.ayni.core.operacion.entity.DetalleOperacion;
import pe.ayni.core.operacion.entity.Operacion;

@Service
public class DetalleOperacionServiceImpl implements DetalleOperacionService {
	
	public static final String DEBITO = "DEBITO";
	public static final String CREDITO = "CREDITO";
	
	@Autowired
	CuentaService cuentaService;
	
	@Autowired
	DetalleCreditoService detalleCreditoService;
	
	@Transactional
	private DetalleOperacion buildEntityFrom(DetalleOperacionDto detalleOperacionDto) {
		
		DetalleOperacion detalleOperacion = new DetalleOperacion();
		
		detalleOperacion.setNroDetalle(detalleOperacionDto.getNroDetalle());
		
		Cuenta cuenta = cuentaService.findCuentaById(detalleOperacionDto.getIdCuenta());
		detalleOperacion.setCuenta(cuenta);
		
		detalleOperacion.setCuentaContable(new CuentaContable(detalleOperacionDto.getCtaContable()));
		detalleOperacion.setDebito(detalleOperacionDto.getDebito());
		detalleOperacion.setCredito(detalleOperacionDto.getCredito());
		
		if (detalleOperacionDto.getIdDetalleCredito() != null)
			detalleOperacion.setDetalleCredito(new DetalleCredito(detalleOperacionDto.getIdDetalleCredito()));
		
		if (detalleOperacionDto.getIdDetalleBanco() != null) 
			detalleOperacion.setDetalleBanco(new DetalleBanco(detalleOperacionDto.getIdDetalleBanco()));
		
		return detalleOperacion;
	}

	@Override
	public DetalleOperacionDto buildDetalleOperacionDesembolso(DetalleCreditoDto detalleDesembolso) {
		DetalleOperacionDto detalleOperacion = new DetalleOperacionDto();
		detalleOperacion.setIdCuenta(detalleDesembolso.getIdCuenta());
		detalleOperacion.setCredito(BigDecimal.ZERO);
		detalleOperacion.setCtaContable(detalleDesembolso.getCtaContable());
		detalleOperacion.setIdDetalleCredito(detalleDesembolso.getId());
		return detalleOperacion;
	}

	@Override
	@Transactional
	public DetalleOperacionDto buildDetalleOperacion2(Integer idCuenta, int nroDetalle, DebitoCredito debitoCredito) {
		Cuenta cuenta = cuentaService.findCuentaById(idCuenta);
		
		DetalleOperacionDto detalleOperacion = new DetalleOperacionDto();
		
		detalleOperacion.setIdCuenta(idCuenta);
		detalleOperacion.setCtaContable(cuenta.getCuentaContable().getCtaContable());
		detalleOperacion.setNroDetalle(nroDetalle);
		
		if (debitoCredito.equals(DebitoCredito.DEBITO)) {
			detalleOperacion.setCredito(BigDecimal.ZERO);
		} else if (debitoCredito.equals(DebitoCredito.CREDITO)) {
			detalleOperacion.setDebito(BigDecimal.ZERO);
		}
		
		return detalleOperacion;
	}

	@Override
	public void setDetallesOperacion(Operacion operacion, List<DetalleOperacionDto> detallesOperacionDto) {
		List<DetalleOperacion> detallesOperacion = new ArrayList<>();
		for (DetalleOperacionDto detalleOperacionDto: detallesOperacionDto) {
			DetalleOperacion detalleOperacion = buildEntityFrom(detalleOperacionDto);
			detallesOperacion.add(detalleOperacion);
		}
		operacion.setDetallesOperacion(detallesOperacion);		
	}

	@Override
	@Transactional
	public DetalleOperacionDto buildDetalleOperacion(Integer idCuenta, Integer nroDetalle, DebitoCredito debitoCredito,
			BigDecimal monto, Integer idDetalleCredito, Integer idDetalleBanco) {
		
		Cuenta cuenta = cuentaService.findCuentaById(idCuenta);
		String ctaContable = cuenta.getCuentaContable().getCtaContable();
		if (idDetalleCredito != null) {
			DetalleCreditoDto detalleCredito = detalleCreditoService.findDetalleCreditoById(idDetalleCredito);
			ctaContable = detalleCredito.getCtaContable();
		}
		
		DetalleOperacionDto detalleOperacion = new DetalleOperacionDto();
		
		detalleOperacion.setIdCuenta(idCuenta);
		detalleOperacion.setCtaContable(ctaContable);
		detalleOperacion.setNroDetalle(nroDetalle);
		detalleOperacion.setIdDetalleCredito(idDetalleCredito);
		detalleOperacion.setIdDetalleBanco(idDetalleBanco);
		
		if (debitoCredito.equals(DebitoCredito.DEBITO)) {
			detalleOperacion.setCredito(BigDecimal.ZERO);
			detalleOperacion.setDebito(monto);
		} else if (debitoCredito.equals(DebitoCredito.CREDITO)) {
			detalleOperacion.setDebito(BigDecimal.ZERO);
			detalleOperacion.setCredito(monto);
		}
		
		return detalleOperacion;
	}

}
