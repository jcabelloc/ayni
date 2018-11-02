package pe.ayni.core.gasto.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import pe.ayni.core.cuenta.entity.Cuenta;
import pe.ayni.core.gasto.constraint.CuentaGastoConstraint.EstadoCuentaGasto;

@Entity
@Table(name="CuentaGasto")
@PrimaryKeyJoinColumn(name = "idCuenta")
public class CuentaGasto extends Cuenta {
	
	@Enumerated(EnumType.STRING)
	@Column(name="estado", nullable=false, length=10)
	private EstadoCuentaGasto estado;
	
	public CuentaGasto() {
		
	}

	public EstadoCuentaGasto getEstado() {
		return estado;
	}

	public void setEstado(EstadoCuentaGasto estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "CuentaGasto [estado=" + estado + "]";
	}
	
	
}
