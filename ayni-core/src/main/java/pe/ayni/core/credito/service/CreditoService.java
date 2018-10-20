package pe.ayni.core.credito.service;

import java.math.BigDecimal;
import java.util.List;

import pe.ayni.core.credito.dto.CreditoDto;
import pe.ayni.core.credito.dto.SimulacionCreditoDto;
import pe.ayni.core.credito.dto.CuotaCreditoDto;

public interface CreditoService {

	CreditoDto createCredito(CreditoDto credito);
	
	List<CuotaCreditoDto> calculateCuotas(CreditoDto credito);

	List<CuotaCreditoDto> calculateCuotas(SimulacionCreditoDto simulacionCredito);

	CreditoDto findCreditoById(Integer idCuenta);

	List<CuotaCreditoDto> findCuotasByIdCuentaAndEstado(Integer idCuenta, String estado); 
	
	Integer getNroCondicionCredito(Integer idCuenta);

	void amortizarCredito(Integer idCuenta, BigDecimal monto);

	List<CreditoDto> findCreditosBy(String by, String input);
	
	List<CreditoDto> findCreditosByIdCliente(Integer id);

	List<CuotaCreditoDto> findAllCuotasByIdCuenta(Integer idCuenta);


}
