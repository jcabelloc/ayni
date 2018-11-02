package pe.ayni.core.operacion.gasto.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.ayni.core.banco.dto.DetalleBancoDto;
import pe.ayni.core.banco.service.BancoService;
import pe.ayni.core.gasto.dto.GastoDto;
import pe.ayni.core.gasto.service.GastoService;
import pe.ayni.core.operacion.constraint.DetalleOperacionConstraint.DebitoCredito;
import pe.ayni.core.operacion.constraint.OperacionConstraint.TipoOperacion;
import pe.ayni.core.operacion.dto.DetalleOperacionDto;
import pe.ayni.core.operacion.dto.OperacionDto;
import pe.ayni.core.operacion.gasto.constraint.GastoContraint.TipoCuentaEgreso;
import pe.ayni.core.operacion.gasto.dto.RegistroGastoDto;
import pe.ayni.core.operacion.service.DetalleOperacionService;
import pe.ayni.core.operacion.service.OperacionService;
import pe.ayni.core.proveedor.dto.ProveedorDto;

@Service
public class OperacionGastoServiceImpl implements OperacionGastoService {
	
	@Autowired
	BancoService bancoService;
	
	@Autowired
	DetalleOperacionService detalleOperacionService;
	
	@Autowired
	OperacionService operacionService;
	
	@Autowired
	GastoService gastoService;
	
	@Override
	@Transactional
	public RegistroGastoDto createRegistroGasto(RegistroGastoDto registroGasto) {
		
		DetalleBancoDto detalleBanco = null;
		if(registroGasto.getOperacion().getTipoCuentaEgreso().equals(TipoCuentaEgreso.BANCOS.toString())) {
			detalleBanco = bancoService.createRetiro(new DetalleBancoDto(registroGasto.getOperacion().getIdCuentaEgreso(), 
					registroGasto.getDetalleBanco().getFechaOperacion(), registroGasto.getDetalleBanco().getNroOperacion(), 
					registroGasto.getDetalleBanco().getMontoOperacion()));
			registroGasto.getDetalleBanco().setId(detalleBanco.getId());
		}
		Integer idOperacionRelacionada = null;
		OperacionDto operacionDto = new OperacionDto(registroGasto.getOperacion().getMonto(), registroGasto.getOperacion().getMoneda(),
				registroGasto.getOperacion().getUsuario(), TipoOperacion.GASTO_ADM.toString(), 
				registroGasto.getOperacion().getNota(), idOperacionRelacionada);	
		
		List<DetalleOperacionDto> detallesOperacion = buildDetallesOperacionGasto(registroGasto);
		operacionDto.setDetallesOperacion(detallesOperacion);
		OperacionDto operacion = operacionService.createOperacion(operacionDto);

		GastoDto gastoDto = gastoService.createGasto(new GastoDto(operacion.getId(), registroGasto.getProveedor().getId(), 
				registroGasto.getFecha(), registroGasto.getTipoComprobante(), registroGasto.getNroComprobante(), 
				registroGasto.getAutorizador()));
		
		ProveedorDto proveedor = new ProveedorDto(registroGasto.getProveedor().getId(), registroGasto.getProveedor().getNombre(),
				registroGasto.getProveedor().getTipoPersona(), registroGasto.getProveedor().getTipoIdentificacion(),
				registroGasto.getProveedor().getNroIdentificacion());
		registroGasto = buildRegistroGasto(gastoDto, operacion, proveedor, detalleBanco);
		
		return registroGasto;
	}

	private RegistroGastoDto buildRegistroGasto(GastoDto gastoDto, OperacionDto operacion, ProveedorDto proveedor,
			DetalleBancoDto detalleBanco) {
		
		return new RegistroGastoDto(gastoDto, operacion, proveedor, detalleBanco);
	}

	private List<DetalleOperacionDto> buildDetallesOperacionGasto(RegistroGastoDto registroGasto) {
		List<DetalleOperacionDto> detallesOperacion = new ArrayList<>();
		// Detalle del Gasto
		Integer nroDetalle = 0;
		DetalleOperacionDto detalleOperacionGasto = detalleOperacionService.buildDetalleOperacion(
				registroGasto.getOperacion().getIdCuentaGasto(), nroDetalle, DebitoCredito.DEBITO, 
				registroGasto.getOperacion().getMonto(), null, null);
		detallesOperacion.add(detalleOperacionGasto);
		nroDetalle++;
		// Detalle del Egreso
		DetalleOperacionDto detalleOperacionEgreso= detalleOperacionService.buildDetalleOperacion(
				registroGasto.getOperacion().getIdCuentaEgreso(), nroDetalle, DebitoCredito.CREDITO, 
				registroGasto.getOperacion().getMonto(), null, registroGasto.getDetalleBanco().getId());
		detallesOperacion.add(detalleOperacionEgreso);
		
		return detallesOperacion;
	}

}
