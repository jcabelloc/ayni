package pe.ayni.core.gasto.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import pe.ayni.core.cuenta.entity.Cuenta;
import pe.ayni.core.gasto.constraint.GastoConstraint.EstadoCuentaGasto;
import pe.ayni.core.proveedor.entity.Proveedor;

@Entity
@Table(name="CuentaGasto")
@PrimaryKeyJoinColumn(name = "idCuenta")
public class CuentaGasto extends Cuenta {
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idProveedor", nullable=false)
	private Proveedor proveedor;
	
	@Enumerated(EnumType.STRING)
	@Column(name="estado", nullable=false, length=10)
	private EstadoCuentaGasto estado;
	
	public CuentaGasto() {
		
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public EstadoCuentaGasto getEstado() {
		return estado;
	}

	public void setEstado(EstadoCuentaGasto estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "CuentaGasto [proveedor=" + proveedor + ", estado=" + estado + "]";
	}
	
}
