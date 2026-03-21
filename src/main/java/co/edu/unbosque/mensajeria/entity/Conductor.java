package co.edu.unbosque.mensajeria.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "conductor")
public class Conductor extends Trabajador {

	private String placaVehiculo;

	public Conductor() {

	}

	public Conductor(String placaVehiculo) {
		super();
		this.placaVehiculo = placaVehiculo;
	}

	public Conductor(char turno, String placaVehiculo) {
		super(turno);
		this.placaVehiculo = placaVehiculo;
	}

	public Conductor(String nombre, String cedula, String correo, String telefono, char turno, String placaVehiculo) {
		super(nombre, cedula, correo, telefono, turno);
		this.placaVehiculo = placaVehiculo;
	}

	public Conductor(String nombre, String cedula, String correo, String telefono, String placaVehiculo) {
		super(nombre, cedula, correo, telefono);
		this.placaVehiculo = placaVehiculo;
	}

	public Conductor(char turno) {
		super(turno);
	}

	public Conductor(String nombre, String cedula, String correo, String telefono, char turno) {
		super(nombre, cedula, correo, telefono, turno);
	}

	public Conductor(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);
	}

	public String getPlacaVehiculo() {
		return placaVehiculo;
	}

	public void setPlacaVehiculo(String placaVehiculo) {
		this.placaVehiculo = placaVehiculo;
	}

	@Override
	public String toString() {
		return super.toString() + "\n Conductor:\n Placa Vehículo:" + placaVehiculo + ".";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(placaVehiculo);
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
		Conductor other = (Conductor) obj;
		return Objects.equals(placaVehiculo, other.placaVehiculo);
	}

}