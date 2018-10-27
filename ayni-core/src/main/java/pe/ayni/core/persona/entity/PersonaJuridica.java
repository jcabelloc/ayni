package pe.ayni.core.persona.entity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="PersonaJuridica")
@PrimaryKeyJoinColumn(name = "id")
public class PersonaJuridica extends Persona {
	
	public PersonaJuridica() {
		
	}
	
	

}
