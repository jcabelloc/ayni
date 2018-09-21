package pe.ayni.core.operacion.service;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.ayni.core.cuenta.entity.CuentaContable;
import pe.ayni.core.cuenta.service.CuentaContableService;
import pe.ayni.core.operacion.constraint.OperacionConstraint.TipoOperacion;
import pe.ayni.core.operacion.dao.OperacionDao;
import pe.ayni.core.operacion.dto.DetalleOperacionDto;
import pe.ayni.core.operacion.dto.OperacionDto;
import pe.ayni.core.operacion.entity.Operacion;
import pe.ayni.core.seguridad.entity.Usuario;

@Service
public class OperacionServiceImpl implements OperacionService {
	
	@Autowired
	DetalleOperacionService detalleOperacionService;
	
	@Autowired
	OperacionDao operacionDao;
	
	@Autowired
	CuentaContableService cuentaContableService;
	
	@Autowired
	SaldoService saldoService;

	@Override
	@Transactional
	public OperacionDto createOperacion(OperacionDto operacionDto) {
		
		Operacion operacion = buildEntityFrom(operacionDto);
		operacionDao.save(operacion);
		saldoService.updateSaldo(operacion);
		operacionDto = buildDtoFrom(operacion);
		return operacionDto;
	}
	
	private OperacionDto buildDtoFrom(Operacion operacion) {
		ModelMapper modelMapper = new ModelMapper();
		OperacionDto operacionDto = modelMapper.map(operacion, OperacionDto.class);
		
		for (DetalleOperacionDto detalleOperacion: operacionDto.getDetallesOperacion()) {
			CuentaContable cuentaContable = cuentaContableService.findCuentaContableByCta(detalleOperacion.getCtaContable());
			detalleOperacion.setTipoCuenta(cuentaContable.getTipoCuenta().toString());
		}
		return operacionDto;
	}

	@Transactional
	private Operacion buildEntityFrom(OperacionDto operacionDto) {
		Operacion operacion = new Operacion();

		operacion.setMonto(operacionDto.getMonto());
		operacion.setMoneda(operacionDto.getMoneda());
		operacion.setFechaOperacion(LocalDate.now());
		operacion.setHoraOperacion(LocalTime.now());
		operacion.setUsuario(new Usuario(operacionDto.getUsuario()));
		operacion.setTipoOperacion(TipoOperacion.valueOf(operacionDto.getTipoOperacion()));
		operacion.setNota(operacionDto.getNota());
		if (operacionDto.getIdOperacionRelacionada() != null)
			operacion.setOperacionRelacionada(new Operacion(operacionDto.getIdOperacionRelacionada()));	
		
		detalleOperacionService.setDetallesOperacion(operacion, operacionDto.getDetallesOperacion());

		return operacion;
	}

	@Override
	@Transactional
	public OperacionDto findOperacionById(Integer id) {
		Operacion operacion = operacionDao.findById(id);
		OperacionDto operacionDto = buildDtoFrom(operacion);
		return operacionDto;
	}

}
