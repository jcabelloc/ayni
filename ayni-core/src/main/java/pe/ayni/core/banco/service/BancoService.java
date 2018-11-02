package pe.ayni.core.banco.service;

import pe.ayni.core.banco.dto.DetalleBancoDto;

public interface BancoService {

	DetalleBancoDto createDeposito(DetalleBancoDto detalleBanco);

	DetalleBancoDto createRetiro(DetalleBancoDto detalleBancoDto);

}
