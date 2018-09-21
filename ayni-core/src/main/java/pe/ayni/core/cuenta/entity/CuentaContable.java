package pe.ayni.core.cuenta.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import pe.ayni.core.cuenta.constraint.CuentaContableConstraint.Naturaleza;
import pe.ayni.core.cuenta.constraint.CuentaContableConstraint.TipoCuenta;

@Entity
@Table(name="CuentaContable")
public class CuentaContable {
	
	@Id
	@Column(name="ctaContable", length=15)
	private String ctaContable;
	
	@Column(name="nombre", nullable=false, length=200)
	private String nombre;
	
	@Enumerated(EnumType.STRING)
	@Column(name="naturaleza", nullable=false, length=10)
	private Naturaleza naturaleza;
	
	@Enumerated(EnumType.STRING)
	@Column(name="tipoCuenta", nullable=false, length=45)
	private TipoCuenta tipoCuenta;
	
	@Column(name="moneda", nullable=false, length=1)
	private String moneda;
	
	public CuentaContable() {
		
	}

	public CuentaContable(String ctaContable) {
		this.ctaContable = ctaContable;
	}

	public String getCtaContable() {
		return ctaContable;
	}

	public void setCtaContable(String ctaContable) {
		this.ctaContable = ctaContable;
	}

	public String getNombre() {
		return nombre;
	}
	
	public Naturaleza getNaturaleza() {
		return naturaleza;
	}

	public void setNaturaleza(Naturaleza naturaleza) {
		this.naturaleza = naturaleza;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public TipoCuenta getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(TipoCuenta tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	@Override
	public String toString() {
		return "CuentaContable [ctaContable=" + ctaContable + ", nombre=" + nombre + ", tipoCuenta=" + tipoCuenta
				+ ", moneda=" + moneda + "]";
	}
	
}
