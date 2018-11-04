package pe.ayni.core.operacion.caja.dto;

import java.io.Serializable;

import pe.ayni.core.banco.dto.DetalleBancoDto;
import pe.ayni.core.operacion.dto.OperacionDto;

public class TraspasoEfectivoDto extends OperacionDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer idCuentaCaja;
	
	private Integer idCuentaBanco;
    
    private DetalleBancoDto detalleBanco;
	
    public TraspasoEfectivoDto() {
    	
    }

	public TraspasoEfectivoDto(OperacionDto operacion, DetalleBancoDto detalleBanco) {
		super(operacion);
		this.detalleBanco = detalleBanco;
		
	}

	public Integer getIdCuentaCaja() {
		return idCuentaCaja;
	}

	public void setIdCuentaCaja(Integer idCuentaCaja) {
		this.idCuentaCaja = idCuentaCaja;
	}

	public Integer getIdCuentaBanco() {
		return idCuentaBanco;
	}

	public void setIdCuentaBanco(Integer idCuentaBanco) {
		this.idCuentaBanco = idCuentaBanco;
	}

	public DetalleBancoDto getDetalleBanco() {
		return detalleBanco;
	}

	public void setDetalleBanco(DetalleBancoDto detalleBanco) {
		this.detalleBanco = detalleBanco;
	}

	@Override
	public String toString() {
		return "TraspasoEfectivoDto [idCuentaCaja=" + idCuentaCaja + ", idCuentaBanco=" + idCuentaBanco
				+ ", detalleBanco=" + detalleBanco + "]";
	}
    
}
