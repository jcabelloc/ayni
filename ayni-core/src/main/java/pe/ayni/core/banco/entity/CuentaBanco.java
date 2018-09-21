package pe.ayni.core.banco.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import pe.ayni.core.banco.constraint.CuentaBancoConstraint.EstadoCuentaBanco;
import pe.ayni.core.banco.constraint.CuentaBancoConstraint.NombreBanco;
import pe.ayni.core.cuenta.entity.Cuenta;

@Entity
@Table(name="CuentaBanco")
@PrimaryKeyJoinColumn(name="idCuenta")
public class CuentaBanco extends Cuenta {
	
	@Enumerated(value=EnumType.STRING)
	@Column(name="nombreBanco", nullable=false, length=10)
	private NombreBanco nombreBanco;
	
	@Column(name="nroCuentaBanco", nullable=false, length=45)
	private String nroCuentaBanco;
	
	@Enumerated(value=EnumType.STRING)
	@Column(name="estado", nullable=false, length=10)
	private EstadoCuentaBanco estado;
	
	public CuentaBanco() {
		
	}

	public CuentaBanco(Integer idCuenta) {
		super(idCuenta);
	}

	public NombreBanco getNombreBanco() {
		return nombreBanco;
	}

	public void setNombreBanco(NombreBanco nombreBanco) {
		this.nombreBanco = nombreBanco;
	}

	public String getNroCuentaBanco() {
		return nroCuentaBanco;
	}

	public void setNroCuentaBanco(String nroCuentaBanco) {
		this.nroCuentaBanco = nroCuentaBanco;
	}

	public EstadoCuentaBanco getEstado() {
		return estado;
	}

	public void setEstado(EstadoCuentaBanco estado) {
		this.estado = estado;
	}

}
