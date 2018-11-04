package pe.ayni.core.banco.service;

import pe.ayni.core.banco.dto.DetalleBancoDto;

public interface DetalleBancoService {

	DetalleBancoDto createDeposito(DetalleBancoDto detalleBanco);

	DetalleBancoDto createRetiro(DetalleBancoDto detalleBanco);

	DetalleBancoDto findDetalleBancoById(Integer id);

}
