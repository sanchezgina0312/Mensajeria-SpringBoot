package co.edu.unbosque.mensajeria.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientenormal")
public class ClienteNormal extends Cliente {

	private double tarifaNormal = 0;

	public ClienteNormal() {

	}

	public ClienteNormal(double tarifaNormal) {
		super();
		this.tarifaNormal = tarifaNormal;
	}

	public ClienteNormal(String metodoPago, String tipoPedido, double tarifaNormal) {
		super(metodoPago, tipoPedido);
		this.tarifaNormal = tarifaNormal;
	}

	public ClienteNormal(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String tipoPedido, double tarifaNormal) {
		super(nombre, cedula, correo, telefono, metodoPago, tipoPedido);
		this.tarifaNormal = tarifaNormal;
	}

	public ClienteNormal(String nombre, String cedula, String correo, String telefono, double tarifaNormal) {
		super(nombre, cedula, correo, telefono);
		this.tarifaNormal = tarifaNormal;
	}

	public ClienteNormal(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String tipoPedido) {
		super(nombre, cedula, correo, telefono, metodoPago, tipoPedido);
	}

	public ClienteNormal(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);
	}

	public ClienteNormal(String metodoPago, String tipoPedido) {
		super(metodoPago, tipoPedido);
	}

	public double getTarifaNormal() {
		return tarifaNormal;
	}

	public void setTarifaNormal(double tarifaNormal) {
		this.tarifaNormal = tarifaNormal;
	}

	@Override
	public String toString() {
		return super.toString() + "Cliente normal \n Tarifa normal: " + tarifaNormal + ".";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(tarifaNormal);
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
		ClienteNormal other = (ClienteNormal) obj;
		return Double.doubleToLongBits(tarifaNormal) == Double.doubleToLongBits(other.tarifaNormal);
	}

}