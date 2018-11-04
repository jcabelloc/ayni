package pe.ayni.core.operacion.caja.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.ayni.core.banco.dto.DetalleBancoDto;
import pe.ayni.core.banco.service.BancoService;
import pe.ayni.core.banco.service.DetalleBancoService;
import pe.ayni.core.operacion.caja.constraint.CajaConstraint.TipoTraspaso;
import pe.ayni.core.operacion.caja.dto.TraspasoEfectivoDto;
import pe.ayni.core.operacion.constraint.DetalleOperacionConstraint.DebitoCredito;
import pe.ayni.core.operacion.dto.DetalleOperacionDto;
import pe.ayni.core.operacion.dto.OperacionDto;
import pe.ayni.core.operacion.service.DetalleOperacionService;
import pe.ayni.core.operacion.service.OperacionService;

@Service
public class OperacionCajaServiceImpl implements OperacionCajaService {
	
	@Autowired
	BancoService bancoService;
	
	@Autowired
	DetalleBancoService detalleBancoService;
	
	
	@Autowired
	DetalleOperacionService detalleOperacionService;
	
	@Autowired
	OperacionService operacionService;
	
	@Override
	@Transactional
	public TraspasoEfectivoDto createTraspasoEfectivo(TraspasoEfectivoDto traspaso) {

		DetalleBancoDto detalleBanco = null;
		List<DetalleOperacionDto> detallesOperacion = new ArrayList<>();
		Integer nroDetalle = 0;
		DetalleOperacionDto detalleOperacionAbono = null; 
		DetalleOperacionDto detalleOperacionCargo = null;
		if (traspaso.getTipoOperacion().equals(TipoTraspaso.REMESA_BANCO.toString())) {
			detalleBanco = bancoService.createDeposito(new DetalleBancoDto(traspaso.getIdCuentaBanco(), 
					traspaso.getDetalleBanco().getFechaOperacion(), traspaso.getDetalleBanco().getNroOperacion(), 
					traspaso.getDetalleBanco().getMontoOperacion()));
			detalleOperacionAbono = detalleOperacionService.buildDetalleOperacion(
					traspaso.getIdCuentaBanco(), nroDetalle, DebitoCredito.DEBITO, traspaso.getMonto(), null, detalleBanco.getId());
			detalleOperacionCargo = detalleOperacionService.buildDetalleOperacion(traspaso.getIdCuentaCaja(),
					++nroDetalle, DebitoCredito.CREDITO, traspaso.getMonto(), null, null);

			
		} else if (traspaso.getTipoOperacion().equals(TipoTraspaso.HABILITACION_CAJA.toString())) {
			detalleBanco = bancoService.createRetiro(new DetalleBancoDto(traspaso.getIdCuentaBanco(), 
					traspaso.getDetalleBanco().getFechaOperacion(), traspaso.getDetalleBanco().getNroOperacion(), 
					traspaso.getDetalleBanco().getMontoOperacion()));
			detalleOperacionAbono = detalleOperacionService.buildDetalleOperacion(
					traspaso.getIdCuentaCaja(), nroDetalle, DebitoCredito.DEBITO, traspaso.getMonto(), null, null);
			detalleOperacionCargo = detalleOperacionService.buildDetalleOperacion(traspaso.getIdCuentaBanco(), 
					++nroDetalle, DebitoCredito.CREDITO, traspaso.getMonto(), null, detalleBanco.getId());
		}
		detallesOperacion.add(detalleOperacionAbono);
		detallesOperacion.add(detalleOperacionCargo);
		
		traspaso.getDetalleBanco().setId(detalleBanco.getId());
		Integer idOperacionRelacionada = null;
		OperacionDto operacionDto = new OperacionDto(traspaso.getMonto(), traspaso.getMoneda(), traspaso.getUsuario(),
				traspaso.getTipoOperacion(), traspaso.getNota(), idOperacionRelacionada);	
		operacionDto.setDetallesOperacion(detallesOperacion);
		OperacionDto operacion = operacionService.createOperacion(operacionDto);

		traspaso = buildTraspasoEfectivo(operacion, detalleBanco);
		return traspaso;
	}

	private TraspasoEfectivoDto buildTraspasoEfectivo(OperacionDto operacion, DetalleBancoDto detalleBanco) {
		TraspasoEfectivoDto traspaso = new TraspasoEfectivoDto(operacion, detalleBanco);
		return traspaso;
	}

	@Override
	@Transactional
	public TraspasoEfectivoDto findTraspasoEfectivoById(Integer id) {
		OperacionDto operacion = operacionService.findOperacionById(id);
		DetalleBancoDto detalleBanco = null; 
		DetalleOperacionDto detalle = operacion.getDetallesOperacion().stream().filter(e -> e.getIdDetalleBanco()!= null).findFirst().orElse(null);
		if (detalle != null) {
			detalleBanco = detalleBancoService.findDetalleBancoById(detalle.getIdDetalleBanco());
		}
		return buildTraspasoEfectivo(operacion, detalleBanco);
	}


}
