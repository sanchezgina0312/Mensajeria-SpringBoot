package co.edu.unbosque.mensajeria.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.JOINED)

public abstract class Usuario {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;
	private String nombre;
	private String cedula;
	private String correo;
	private String telefono;

	public Usuario() {

	}

	public Usuario(String nombre, String cedula, String correo, String telefono) {
		super();
		this.nombre = nombre;
		this.cedula = cedula;
		this.correo = correo;
		this.telefono = telefono;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Override
	public String toString() {
		return "Usuario \n Id: " + id + "\n" + "Nombre: " + nombre + "\n" + "Cédula: " + cedula + "\n" + "Correo: "
				+ correo + "\n" + "Teléfono: " + telefono + "\n";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cedula, correo, id, nombre, telefono);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(cedula, other.cedula) && Objects.equals(correo, other.correo) && id == other.id
				&& Objects.equals(nombre, other.nombre) && Objects.equals(telefono, other.telefono);
	}

}