package co.edu.unbosque.mensajeria.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "manipuladordepaquete")
public class ManipuladorDePaquete extends Trabajador {

	private String tipoManipulador;

	public ManipuladorDePaquete() {
	}

	public ManipuladorDePaquete(String tipoManipulador) {
		super();
		this.tipoManipulador = tipoManipulador;
	}

	public ManipuladorDePaquete(char turno, String tipoManipulador) {
		super(turno);
		this.tipoManipulador = tipoManipulador;
	}

	public ManipuladorDePaquete(String nombre, String cedula, String correo, String telefono, char turno,
			String tipoManipulador) {
		super(nombre, cedula, correo, telefono, turno);
		this.tipoManipulador = tipoManipulador;
	}

	public ManipuladorDePaquete(String nombre, String cedula, String correo, String telefono, String tipoManipulador) {
		super(nombre, cedula, correo, telefono);
		this.tipoManipulador = tipoManipulador;
	}

	public ManipuladorDePaquete(char turno) {
		super(turno);
	}

	public ManipuladorDePaquete(String nombre, String cedula, String correo, String telefono, char turno) {
		super(nombre, cedula, correo, telefono, turno);
	}

	public ManipuladorDePaquete(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);
	}

	public String getTipoManipulador() {
		return tipoManipulador;
	}

	public void setTipoManipulador(String tipoManipulador) {
		this.tipoManipulador = tipoManipulador;
	}

	@Override
	public String toString() {
		return super.toString() + "\n ManipuladorDePaquete:\n Tipo Manipulador:" + tipoManipulador + ".";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(tipoManipulador);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ManipuladorDePaquete other = (ManipuladorDePaquete) obj;
		return Objects.equals(tipoManipulador, other.tipoManipulador);
	}

}