package pe.ayni.core.banco.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.ayni.core.banco.constraint.DetalleBancoConstraint.TipoOperacion;
import pe.ayni.core.banco.dto.DetalleBancoDto;

@Service
public class BancoServiceImpl implements BancoService {
	
	@Autowired
	DetalleBancoService detalleBancoService;
	
	@Override
	@Transactional
	public DetalleBancoDto createDeposito(DetalleBancoDto detalleBanco) {
		detalleBanco.setTipoOperacion(TipoOperacion.ABONO.toString());
		return detalleBancoService.createDeposito(detalleBanco);
	}
	
	@Override
	@Transactional
	public DetalleBancoDto createRetiro(DetalleBancoDto detalleBanco) {
		detalleBanco.setTipoOperacion(TipoOperacion.CARGO.toString());
		return detalleBancoService.createRetiro(detalleBanco);
	}

}
