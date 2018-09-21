package pe.ayni.core.cliente.entity;

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

import pe.ayni.core.persona.entity.PersonaNatural;

@Entity
@Table(name="Cliente")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="fechaRegistro")
	private LocalDate fechaRegistro;
	
	@Column(name="fechaAfiliacion")
	private LocalDate fechaAfiliacion;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idPersonaNatural", nullable=false, unique=true)
	private PersonaNatural personaNatural;
	
	public Cliente() {
		
	}

	public Cliente(Integer id) {
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

	public LocalDate getFechaAfiliacion() {
		return fechaAfiliacion;
	}

	public void setFechaAfiliacion(LocalDate fechaAfiliacion) {
		this.fechaAfiliacion = fechaAfiliacion;
	}

	public PersonaNatural getPersonaNatural() {
		return personaNatural;
	}

	public void setPersonaNatural(PersonaNatural personaNatural) {
		this.personaNatural = personaNatural;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", fechaRegistro=" + fechaRegistro + ", fechaAfiliacion=" + fechaAfiliacion
				+ ", personaNatural=" + personaNatural + "]";
	}
	
	
}
