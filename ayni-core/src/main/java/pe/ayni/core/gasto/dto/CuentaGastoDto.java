package pe.ayni.core.gasto.dto;

import java.io.Serializable;

import pe.ayni.core.gasto.entity.CuentaGasto;

public class CuentaGastoDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer idCuenta;
	
	private String moneda;
	
	private String ctaContable;
	
	private String tipoCuenta;
	
	public CuentaGastoDto() {
		
	}
	
	public CuentaGastoDto(CuentaGasto cuentaGasto) {
		this.idCuenta = cuentaGasto.getIdCuenta();
		this.moneda = cuentaGasto.getMoneda();
		this.ctaContable = cuentaGasto.getCuentaContable().getCtaContable();
		this.tipoCuenta = cuentaGasto.getCuentaContable().getTipoCuenta().toString();
	}

	public Integer getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(Integer idCuenta) {
		this.idCuenta = idCuenta;
	}
	
	public String getMoneda() {
		return moneda;
	}


	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}


	public String getCtaContable() {
		return ctaContable;
	}


	public void setCtaContable(String ctaContable) {
		this.ctaContable = ctaContable;
	}


	public String getTipoCuenta() {
		return tipoCuenta;
	}


	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	@Override
	public String toString() {
		return "CuentaGastoDto [idCuenta=" + idCuenta + ", moneda=" + moneda + ", ctaContable=" + ctaContable
				+ ", tipoCuenta=" + tipoCuenta + "]";
	}

}
