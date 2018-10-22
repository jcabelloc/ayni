package pe.ayni.core.credito.service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.ayni.core.cliente.dto.ClienteDto;
import pe.ayni.core.cliente.entity.Cliente;
import pe.ayni.core.cliente.service.ClienteService;
import pe.ayni.core.credito.constraint.CreditoConstraint.EstadoCredito;
import pe.ayni.core.credito.constraint.CreditoConstraint.FrecuenciaCredito;
import pe.ayni.core.credito.constraint.CuotaCreditoConstraint.EstadoCuota;
import pe.ayni.core.credito.dao.CreditoDao;
import pe.ayni.core.credito.dto.CreditoDto;
import pe.ayni.core.credito.dto.SimulacionCreditoDto;
import pe.ayni.core.credito.dto.CuotaCreditoDto;
import pe.ayni.core.credito.entity.CuentaCredito;
import pe.ayni.core.credito.entity.DetalleCredito;
import pe.ayni.core.cuenta.entity.CuentaContable;
import pe.ayni.core.seguridad.entity.Usuario;

@Service
public class CreditoServiceImpl implements CreditoService {
	
	@Autowired
	CreditoDao creditoDao;
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	DetalleCreditoService detalleCreditoService;
	
	@Resource(name = "feriados")
	private Map<String, int[]> feriados;
	
	@Override
	@Transactional
	public CreditoDto createCredito(CreditoDto creditoDto) {
		
		creditoDto.setNroCondicion(0);
		
		List<DetalleCredito> detallesCredito = buildDetallesCredito(creditoDto);
		
		CuentaCredito credito = buildEntityFrom(creditoDto);
		credito.setMontoCuota(getMontoCuota(detallesCredito));
		credito.setDetallesCredito(detallesCredito);

		creditoDao.create(credito);
		creditoDto = buildDtoFrom(credito);
		return creditoDto;
	}
	
	@Override
	@Transactional
	public CreditoDto findCreditoById(Integer idCuenta) {
		CuentaCredito credito = creditoDao.findById(idCuenta);
		CreditoDto creditoDto = buildDtoFrom(credito);
		return creditoDto;
	}

	private CreditoDto buildDtoFrom(CuentaCredito credito) {
		CreditoDto creditoDto = null;
		if (credito != null) {
			ModelMapper modelMapper = new ModelMapper();
			creditoDto = modelMapper.map(credito, CreditoDto.class);
			ClienteDto cliente = clienteService.findClienteById(credito.getCliente().getId());
			creditoDto.getCliente().setNombre(cliente.getPersonaNatural().getNombre());
			creditoDto.getCliente().setTipoIdentificacion(cliente.getPersonaNatural().getTipoIdentificacion().toString());
			creditoDto.getCliente().setNroIdentificacion(cliente.getPersonaNatural().getNroIdentificacion());
			creditoDto.setSaldoCapital(getSaldoCapital(credito));
		}
		return creditoDto;
	}
	
	private BigDecimal getSaldoCapital(CuentaCredito credito) {
		return credito.getDetallesCredito()
				.stream()
				.filter(e -> ( e.getNroCuota().intValue() > 0 && e.getNroConcepto().intValue() == 0))
				.map(e -> e.getMontoProgramado().subtract(e.getMontoPagado()))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private BigDecimal getMontoCuota(List<DetalleCredito> detallesCredito) {
		
		BigDecimal montoCuota = detallesCredito.stream().filter(e -> (e.getNroCuota().intValue() == 1))
			.map(e -> e.getMontoProgramado()).reduce(BigDecimal.ZERO, BigDecimal::add);
		
		return montoCuota;
	}
	
	private List<DetalleCredito> buildDetallesCredito(CreditoDto credito) {
		
		List<DetalleCredito> detallesCredito = new ArrayList<>();
		
		List<CuotaCreditoDto> cuotasCredito = calculateCuotas(credito);
		
		int nroConceptoCapital = 0;
		int nroConceptoInteres = 1;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		for (int nroCuota=0; nroCuota < cuotasCredito.size(); nroCuota++) {
			// {nroCuota : 0}, representa al desembolso
			if (nroCuota == 0) {
				DetalleCredito detalleDesembolso = new DetalleCredito(
						credito.getNroCondicion(), nroCuota, nroConceptoCapital, new CuentaContable("14110206"), // TODO 
						LocalDate.parse(cuotasCredito.get(nroCuota).getFechaVencimiento(), formatter),
						cuotasCredito.get(nroCuota).getCapitalCredito(),
						new BigDecimal(0), new BigDecimal(0));
				detallesCredito.add(detalleDesembolso);
				
			} else {
				DetalleCredito detalleCreditoCapital = new DetalleCredito(
						credito.getNroCondicion(), nroCuota, nroConceptoCapital, new CuentaContable("14110206"), // TODO 
						LocalDate.parse(cuotasCredito.get(nroCuota).getFechaVencimiento(), formatter),
						cuotasCredito.get(nroCuota).getCapitalCredito(), cuotasCredito.get(nroCuota).getCapitalProgramado(),
						new BigDecimal(0));
				detallesCredito.add(detalleCreditoCapital);
				
				DetalleCredito detalleCreditoInteres = new DetalleCredito(
						credito.getNroCondicion(), nroCuota, nroConceptoInteres, new CuentaContable("5114010206"), // TODO 
						LocalDate.parse(cuotasCredito.get(nroCuota).getFechaVencimiento(), formatter),
						cuotasCredito.get(nroCuota).getCapitalCredito(), cuotasCredito.get(nroCuota).getInteresProgramado(),
						new BigDecimal(0) );			
				detallesCredito.add(detalleCreditoInteres);
			}
		}
		return detallesCredito;
	}

	
	private CuentaCredito buildEntityFrom(CreditoDto creditoDto) {
		CuentaCredito credito = new CuentaCredito();
		
		
		credito.setCapitalInicial(creditoDto.getMontoDesembolso());
		credito.setCliente(new Cliente(creditoDto.getCliente().getId()));
		credito.setCuentaContable(new CuentaContable("14110206"));
		credito.setEstado(EstadoCredito.ACTIVO);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate fechaDesembolso = LocalDate.parse(creditoDto.getFechaDesembolso(), formatter);
		credito.setFechaDesembolso(fechaDesembolso);
		LocalDate fechaPrimeraCuota = LocalDate.parse(creditoDto.getFechaPrimeraCuota(), formatter);
		credito.setFechaPrimeraCuota(fechaPrimeraCuota);
		credito.setFrecuencia(FrecuenciaCredito.valueOf(creditoDto.getFrecuencia()));
		credito.setMoneda(creditoDto.getMoneda());
		//credito.setMontoCuota(montoCuota);
		credito.setMontoDesembolso(creditoDto.getMontoDesembolso());
		credito.setNroCondicion(creditoDto.getNroCondicion());
		credito.setNroCuotas(creditoDto.getNroCuotas());
		credito.setUsuarioResponsable(new Usuario(creditoDto.getUsuarioResponsable()));
		credito.setTem(creditoDto.getTem());
		credito.setUsuarioAprobador(new Usuario(creditoDto.getUsuarioAprobador()));
		
		return credito;
	}
	
	@Override
	public List<CuotaCreditoDto> calculateCuotas(SimulacionCreditoDto simulacionCredito) {
		CreditoDto creditoDto = new CreditoDto(simulacionCredito.getMontoDesembolso(), 
				simulacionCredito.getFrecuencia(), simulacionCredito.getTem(), 
				simulacionCredito.getNroCuotas(), simulacionCredito.getFechaDesembolso(), 
				simulacionCredito.getFechaPrimeraCuota());
		
		return calculateCuotas(creditoDto);
	}
	
	@Override
	public List<CuotaCreditoDto> calculateCuotas(CreditoDto credito) {
		
		LocalDate[] fechasVencimiento = getFechasVencimiento(LocalDate.parse(credito.getFechaPrimeraCuota()), 
				FrecuenciaCredito.valueOf(credito.getFrecuencia()), credito.getNroCuotas().intValue());
		
		int[] diferenciaEnDias = getDiferenciaEnDias(LocalDate.parse(credito.getFechaDesembolso()), fechasVencimiento);
		
		double ted = getTEDFromTEM(credito.getTem().doubleValue() / 100);
		
		BigDecimal montoCuota = calculateMontoCuota(credito.getMontoDesembolso(), diferenciaEnDias, ted);
		
		List<CuotaCreditoDto> cuotasCalculadas = new ArrayList<>();
		
		// 1era Cuota es Desembolso
		CuotaCreditoDto cuotaDesembolso = new CuotaCreditoDto(0, credito.getFechaDesembolso(),
				credito.getMontoDesembolso(), new BigDecimal(0), new BigDecimal(0), montoCuota);
		cuotasCalculadas.add(cuotaDesembolso);
		
		for (int i = 0; i < credito.getNroCuotas().intValue(); i++) {
			
			CuotaCreditoDto cuotaCredito = new CuotaCreditoDto();
			int nroCuota = i + 1;
			cuotaCredito.setNroCuota(nroCuota);
			cuotaCredito.setFechaVencimiento(fechasVencimiento[i].toString());
			cuotaCredito.setCapitalCredito(cuotasCalculadas.get(i).getCapitalCredito().subtract(cuotasCalculadas.get(i).getCapitalProgramado()));
			cuotaCredito.setMontoCuota(montoCuota);

			cuotaCredito.setInteresProgramado(calculateInteres(ted, diferenciaEnDias[i], cuotaCredito.getCapitalCredito()));
			cuotaCredito.setCapitalProgramado(cuotaCredito.getMontoCuota().subtract(cuotaCredito.getInteresProgramado()));
			
			// ultima cuota
			if (nroCuota == credito.getNroCuotas().intValue() ) {
				cuotaCredito.setCapitalProgramado(cuotaCredito.getCapitalCredito());
				cuotaCredito.setMontoCuota(cuotaCredito.getCapitalProgramado().add(cuotaCredito.getInteresProgramado()));
			}
			
			cuotasCalculadas.add(cuotaCredito);
		}
		return cuotasCalculadas;
	}

	private BigDecimal calculateInteres(double ted, int nroDias, BigDecimal saldoCapital) {

		return BigDecimal.valueOf((Math.pow(1 + ted, nroDias) - 1) * saldoCapital.doubleValue()).setScale(1, BigDecimal.ROUND_UP);
	}

	private double getTEDFromTEM(double tem) {
	
		return Math.pow(1 + tem, 1.0 / 30) - 1;
	}

	private BigDecimal calculateMontoCuota(BigDecimal montoDesembolso, int[] diferenciaEnDias, double ted) {

		double sumaFactores = 0.0;
		int diasAcumulados = 0;
		for (int i = 0; i < diferenciaEnDias.length; i++) {
			diasAcumulados += diferenciaEnDias[i];
			sumaFactores += 1 / Math.pow(1 + ted, diasAcumulados); 
		}
		return BigDecimal.valueOf(montoDesembolso.doubleValue()/sumaFactores).setScale(1, BigDecimal.ROUND_UP);
	}

	private int[] getDiferenciaEnDias(LocalDate fechaDesembolso, LocalDate[] fechasVencimiento) {
		
		int[] diferenciasEnDias = new int[fechasVencimiento.length];
		diferenciasEnDias[0] = (int)Math.abs(ChronoUnit.DAYS.between(fechaDesembolso, fechasVencimiento[0]));
		for (int i = 1; i < diferenciasEnDias.length; i++) {
			diferenciasEnDias[i] = (int)Math.abs(ChronoUnit.DAYS.between(fechasVencimiento[i - 1], fechasVencimiento[i]));
		}
		return diferenciasEnDias;
	}

	private LocalDate[] getFechasVencimiento(LocalDate fechaPrimerVencimiento, FrecuenciaCredito frecuencia, int nroCuotas) {
		
		LocalDate[] fechasVencimiento = new LocalDate[nroCuotas];
		fechasVencimiento[0] = fechaPrimerVencimiento;
		for (int i = 1; i < fechasVencimiento.length; i++) {
			switch (frecuencia.toString()) {
				case "DIARIA": {
					fechasVencimiento[i] = fechasVencimiento[i - 1].plusDays(1);
					while (isDiaNoHabil(fechasVencimiento[i])) {
						fechasVencimiento[i] = fechasVencimiento[i].plusDays(1);
					}
					break;
				}
				case "SEMANAL": {
					fechasVencimiento[i] = fechasVencimiento[i - 1].plusDays(7);
					while (isDiaNoHabil(fechasVencimiento[i])) {
						fechasVencimiento[i] = fechasVencimiento[i].plusDays(1);
					}
					break;
				}
				case "MENSUAL": {
					fechasVencimiento[i] = fechaPrimerVencimiento.plusMonths(i);
					while (isDiaNoHabil(fechasVencimiento[i])) {
						fechasVencimiento[i] = fechasVencimiento[i].plusDays(1);
					}
					break;
				}
				default: {
					break;
				}
			}
			
		}
		return fechasVencimiento;
	}

	private boolean isDiaNoHabil(LocalDate fecha) {
		if (fecha.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
			return true;
		}
		if (isFeriadoRegular(fecha.getDayOfYear())) {
			return true;
		}
		if (isFeriadoNoRegular(fecha)) {
			return true;
		}
		return false;
	}

	private boolean isFeriadoNoRegular(LocalDate fecha) {
		int [] diasFeriados = feriados.get(String.valueOf(fecha.getYear()));
		if ( diasFeriados == null) {
			return false;
		}
		for (int diaFeriado: diasFeriados ) {
			if (diaFeriado == fecha.getDayOfYear()) {
				return true;
			}
		}
		return false;
	}

	private boolean isFeriadoRegular(int dayOfYear) {
		for (int diaFeriado: feriados.get("REGULAR")) {
			if (diaFeriado == dayOfYear) {
				return true;
			}
		}
		return false;
	}

	@Override
	@Transactional
	public List<CuotaCreditoDto> findCuotasByIdCuentaAndEstado(Integer idCuenta, String estado) {
		Integer nroCondicion= getNroCondicionCredito(idCuenta);
		return detalleCreditoService.findCuotasByIdCuentaAndEstado(idCuenta, nroCondicion, EstadoCuota.valueOf(estado));
	}

	@Override
	@Transactional
	public Integer getNroCondicionCredito(Integer idCuenta) {
		return creditoDao.findById(idCuenta).getNroCondicion();
	}

	@Override
	@Transactional
	public void amortizarCredito(Integer idCuenta, BigDecimal monto) {
		Integer nroCondicion = getNroCondicionCredito(idCuenta);
		CuentaCredito credito = creditoDao.findById(idCuenta);
		BigDecimal saldoCancelacion = getSaldoCancelacion(credito);
		
		detalleCreditoService.amortizarDetallesCredito(idCuenta, nroCondicion, monto);

		if (saldoCancelacion.equals(monto)) {
			creditoDao.updateEstado(idCuenta, EstadoCredito.CANCELADO);
		}
	}

	@Override
	@Transactional
	public List<CreditoDto> findCreditosBy(String by, String input) {
		List<CreditoDto> creditos = new ArrayList<>();
		List<ClienteDto> clientes = clienteService.findClientesBy(by, input);
		if (clientes != null && clientes.size() > 0) {
			creditos = findCreditosByIdCliente(clientes.get(0).getId());
		}
		return creditos;
	}
	
	@Override
	@Transactional
	public List<CreditoDto> findCreditosByIdCliente(Integer id) {
		List<CreditoDto> creditosDto = new ArrayList<>();
		List<CuentaCredito> creditos = creditoDao.findByIdCliente(id);
		
		for(CuentaCredito credito: creditos) {
			CreditoDto creditoDto = buildDtoFrom(credito);
			creditosDto.add(creditoDto);
		}
		return creditosDto;
	}
	
	private BigDecimal getSaldoCancelacion(CuentaCredito credito) {
		return credito.getDetallesCredito()
				.stream()
				.filter(e -> ( e.getNroCuota().intValue() > 0))
				.map(e -> e.getMontoProgramado().subtract(e.getMontoPagado()))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@Override
	@Transactional
	public List<CuotaCreditoDto> findAllCuotasByIdCuenta(Integer idCuenta) {
		Integer nroCondicion= getNroCondicionCredito(idCuenta);
		return detalleCreditoService.findCuotasByIdCuentaAndEstado(idCuenta, nroCondicion, null);
	}

}
