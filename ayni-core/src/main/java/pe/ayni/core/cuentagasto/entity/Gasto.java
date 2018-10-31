package pe.ayni.core.cuentagasto.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import pe.ayni.core.cuentagasto.constraint.GastoContraint.TipoComprobante;
import pe.ayni.core.operacion.entity.Operacion;
import pe.ayni.core.proveedor.entity.Proveedor;
import pe.ayni.core.seguridad.entity.Usuario;

@Entity
@Table(name="Gasto")
public class Gasto {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idOperacion", nullable=false, unique=true)
	private Operacion operacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idProveedor", nullable=false)
	private Proveedor proveedor;
	
	@Column(name="fecha", nullable=false)
	private LocalDate fecha;
	
	@Enumerated(value=EnumType.STRING)
	@Column(name="tipoComprobante", nullable=false, length=10)
	private TipoComprobante tipoComprobante;
	
	@Column(name="nroComprobante", nullable=false, length=25)
	private String nroComprobante;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="aprobador", nullable=false)
	private Usuario aprobador;
	
	public Gasto() {
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Operacion getOperacion() {
		return operacion;
	}

	public void setOperacion(Operacion operacion) {
		this.operacion = operacion;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public TipoComprobante getTipoComprobante() {
		return tipoComprobante;
	}

	public void setTipoComprobante(TipoComprobante tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}

	public String getNroComprobante() {
		return nroComprobante;
	}

	public void setNroComprobante(String nroComprobante) {
		this.nroComprobante = nroComprobante;
	}

	public Usuario getAprobador() {
		return aprobador;
	}

	public void setAprobador(Usuario aprobador) {
		this.aprobador = aprobador;
	}

	@Override
	public String toString() {
		return "Gasto [id=" + id + ", operacion=" + operacion + ", proveedor=" + proveedor + ", fecha=" + fecha
				+ ", tipoComprobante=" + tipoComprobante + ", nroComprobante=" + nroComprobante + ", aprobador="
				+ aprobador + "]";
	}
	
	
}
