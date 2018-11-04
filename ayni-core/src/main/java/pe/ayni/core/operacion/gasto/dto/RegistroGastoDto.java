package pe.ayni.core.operacion.gasto.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import pe.ayni.core.banco.dto.DetalleBancoDto;
import pe.ayni.core.gasto.dto.GastoDto;
import pe.ayni.core.operacion.dto.OperacionDto;
import pe.ayni.core.proveedor.dto.ProveedorDto;

public class RegistroGastoDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String fecha;
	
	private String tipoComprobante;
	
	private String nroComprobante;
	
	private String autorizador;
	
	private Proveedor proveedor;

	private Operacion operacion;
	
	private DetalleBanco detalleBanco;
	
	public RegistroGastoDto() {
		
	}
	
	public RegistroGastoDto(GastoDto gastoDto, OperacionDto operacion, ProveedorDto proveedor,
			DetalleBancoDto detalleBanco) {
		this.id = gastoDto.getId();
		this.fecha = gastoDto.getFecha();
		this.tipoComprobante = gastoDto.getTipoComprobante();
		this.nroComprobante = gastoDto.getNroComprobante();
		this.autorizador = gastoDto.getAutorizador();
		this.proveedor = new Proveedor(proveedor);
		this.operacion = new Operacion(operacion);
		this.detalleBanco = detalleBanco == null? null: new DetalleBanco(detalleBanco);
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getTipoComprobante() {
		return tipoComprobante;
	}

	public void setTipoComprobante(String tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}

	public String getNroComprobante() {
		return nroComprobante;
	}

	public void setNroComprobante(String nroComprobante) {
		this.nroComprobante = nroComprobante;
	}

	public String getAutorizador() {
		return autorizador;
	}

	public void setAutorizador(String autorizador) {
		this.autorizador = autorizador;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Operacion getOperacion() {
		return operacion;
	}

	public void setOperacion(Operacion operacion) {
		this.operacion = operacion;
	}

	public DetalleBanco getDetalleBanco() {
		return detalleBanco;
	}

	public void setDetalleBanco(DetalleBanco detalleBanco) {
		this.detalleBanco = detalleBanco;
	}
	
	@Override
	public String toString() {
		return "GastoDto [id=" + id + ", fecha=" + fecha + ", tipoComprobante=" + tipoComprobante + ", nroComprobante="
				+ nroComprobante + ", autorizador=" + autorizador + ", proveedor=" + proveedor + ", operacion="
				+ operacion + ", detalleBanco=" + detalleBanco + "]";
	}

	public static class Proveedor {
		
	    private Integer id;
	    
	    private String nombre;
	    
	    private String tipoPersona;
	    
	    private String tipoIdentificacion;
	    
	    private String nroIdentificacion;
	    
	    public Proveedor() {
	    	
	    }

		public Proveedor(ProveedorDto proveedor) {
			this.id = proveedor.getId();
			this.nombre = proveedor.getNombre();
			this.tipoPersona = proveedor.getTipoPersona();
			this.tipoIdentificacion = proveedor.getTipoIdentificacion();
			this.nroIdentificacion = proveedor.getNroIdentificacion();
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getTipoPersona() {
			return tipoPersona;
		}

		public void setTipoPersona(String tipoPersona) {
			this.tipoPersona = tipoPersona;
		}

		public String getTipoIdentificacion() {
			return tipoIdentificacion;
		}

		public void setTipoIdentificacion(String tipoIdentificacion) {
			this.tipoIdentificacion = tipoIdentificacion;
		}

		public String getNroIdentificacion() {
			return nroIdentificacion;
		}

		public void setNroIdentificacion(String nroIdentificacion) {
			this.nroIdentificacion = nroIdentificacion;
		}

		@Override
		public String toString() {
			return "Proveedor [id=" + id + ", nombre=" + nombre + ", tipoPersona=" + tipoPersona
					+ ", tipoIdentificacion=" + tipoIdentificacion + ", nroIdentificacion=" + nroIdentificacion + "]";
		}
	    
	}
	
	
	public static class Operacion {
		
		private Integer id;
		
		private BigDecimal monto;
		
		private String moneda; // "0", "1": Soles, "2": Dolares
		
		private String fechaOperacion;
		
		private String horaOperacion;

		private String usuario;
		
		private String tipoOperacion;
		
		private Integer idCuentaGasto;
		
		private String tipoCuentaEgreso;
		
		private Integer idCuentaEgreso;
	    
	    private String nota;
	    
		public Operacion() {
		
		}

		public Operacion(OperacionDto operacion) {
			this.id = operacion.getId();
			this.monto = operacion.getMonto();
			this.moneda = operacion.getMoneda();
			this.fechaOperacion = operacion.getFechaOperacion();
			this.horaOperacion = operacion.getHoraOperacion();
			this.usuario = operacion.getUsuario();
			this.tipoOperacion = operacion.getTipoOperacion();
			this.idCuentaGasto = operacion.getDetallesOperacion().stream().filter(e -> e.getCredito().compareTo(BigDecimal.ZERO) > 0).findFirst().get().getIdCuenta();
			this.tipoCuentaEgreso = operacion.getDetallesOperacion().stream().filter(e -> e.getDebito().compareTo(BigDecimal.ZERO) > 0).findFirst().get().getTipoCuenta();
			this.idCuentaEgreso = operacion.getDetallesOperacion().stream().filter(e -> e.getDebito().compareTo(BigDecimal.ZERO) > 0).findFirst().get().getIdCuenta();
			this.nota = operacion.getNota();
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public BigDecimal getMonto() {
			return monto;
		}

		public void setMonto(BigDecimal monto) {
			this.monto = monto;
		}

		public String getMoneda() {
			return moneda;
		}

		public void setMoneda(String moneda) {
			this.moneda = moneda;
		}

		public String getFechaOperacion() {
			return fechaOperacion;
		}

		public void setFechaOperacion(String fechaOperacion) {
			this.fechaOperacion = fechaOperacion;
		}

		public String getHoraOperacion() {
			return horaOperacion;
		}

		public void setHoraOperacion(String horaOperacion) {
			this.horaOperacion = horaOperacion;
		}

		public String getUsuario() {
			return usuario;
		}

		public void setUsuario(String usuario) {
			this.usuario = usuario;
		}

		public String getTipoOperacion() {
			return tipoOperacion;
		}

		public void setTipoOperacion(String tipoOperacion) {
			this.tipoOperacion = tipoOperacion;
		}

		public Integer getIdCuentaGasto() {
			return idCuentaGasto;
		}

		public void setIdCuentaGasto(Integer idCuentaGasto) {
			this.idCuentaGasto = idCuentaGasto;
		}

		public String getTipoCuentaEgreso() {
			return tipoCuentaEgreso;
		}

		public void setTipoCuentaEgreso(String tipoCuentaEgreso) {
			this.tipoCuentaEgreso = tipoCuentaEgreso;
		}

		public Integer getIdCuentaEgreso() {
			return idCuentaEgreso;
		}

		public void setIdCuentaEgreso(Integer idCuentaEgreso) {
			this.idCuentaEgreso = idCuentaEgreso;
		}

		public String getNota() {
			return nota;
		}

		public void setNota(String nota) {
			this.nota = nota;
		}

		@Override
		public String toString() {
			return "Operacion [id=" + id + ", monto=" + monto + ", moneda=" + moneda + ", fechaOperacion="
					+ fechaOperacion + ", horaOperacion=" + horaOperacion + ", usuario=" + usuario + ", tipoOperacion="
					+ tipoOperacion + ", idCuentaGasto=" + idCuentaGasto + ", tipoCuentaEgreso=" + tipoCuentaEgreso
					+ ", idCuentaEgreso=" + idCuentaEgreso + ", nota=" + nota + "]";
		}
	    
	    
	}
	
	public static class DetalleBanco {
		private Integer id;
		
		private String nroOperacion;
		
		private String fechaOperacion;
		
		private BigDecimal montoOperacion;
		
		public DetalleBanco() {
			
		}

		public DetalleBanco(DetalleBancoDto detalleBanco) {
			this.id = detalleBanco.getId();
			this.nroOperacion = detalleBanco.getNroOperacion();
			this.fechaOperacion = detalleBanco.getFechaOperacion();
			this.montoOperacion = detalleBanco.getMontoOperacion();		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getNroOperacion() {
			return nroOperacion;
		}

		public void setNroOperacion(String nroOperacion) {
			this.nroOperacion = nroOperacion;
		}

		public String getFechaOperacion() {
			return fechaOperacion;
		}

		public void setFechaOperacion(String fechaOperacion) {
			this.fechaOperacion = fechaOperacion;
		}

		public BigDecimal getMontoOperacion() {
			return montoOperacion;
		}

		public void setMontoOperacion(BigDecimal montoOperacion) {
			this.montoOperacion = montoOperacion;
		}

		@Override
		public String toString() {
			return "DetalleBanco [id=" + id + ", nroOperacion=" + nroOperacion + ", fechaOperacion=" + fechaOperacion
					+ ", montoOperacion=" + montoOperacion + "]";
		}
		
	}
	
	
}
