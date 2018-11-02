package pe.ayni.core.proveedor.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import pe.ayni.core.persona.entity.Persona;

@Entity
@Table(name="Proveedor")
public class Proveedor {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="fechaRegistro")
	private LocalDate fechaRegistro;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idPersona", nullable=false, unique=true)
	private Persona persona;

	public Proveedor() {

	}

	public Proveedor(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDate fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	@Override
	public String toString() {
		return "Proveedor [id=" + id + ", fechaRegistro=" + fechaRegistro + ", persona=" + persona + "]";
	}
	
	
}
