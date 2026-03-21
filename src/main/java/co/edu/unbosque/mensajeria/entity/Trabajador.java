package co.edu.unbosque.mensajeria.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "trabajador")
public abstract class Trabajador extends Usuario {

	private char turno;

	public Trabajador() {
	}

	public Trabajador(char turno) {
		super();
		this.turno = turno;
	}

	public Trabajador(String nombre, String cedula, String correo, String telefono, char turno) {
		super(nombre, cedula, correo, telefono);
		this.turno = turno;
	}

	public Trabajador(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);
	}

	public char getTurno() {
		return turno;
	}

	public void setTurno(char turno) {
		this.turno = turno;
	}

	@Override
	public String toString() {
		return super.toString() + "\n Trabajador:\n Turno:" + turno + ".";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(turno);
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
		Trabajador other = (Trabajador) obj;
		return turno == other.turno;
	}

}