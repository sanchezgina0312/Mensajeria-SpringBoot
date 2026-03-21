package co.edu.unbosque.mensajeria.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "clienteconcurrente")
public class ClienteConcurrente extends Cliente {

	private double tarifaConcurrente = 0.5;

	public ClienteConcurrente() {
	}

	public ClienteConcurrente(double tarifaConcurrente) {
		super();
		this.tarifaConcurrente = tarifaConcurrente;
	}

	public ClienteConcurrente(String metodoPago, String tipoPedido) {
		super(metodoPago, tipoPedido);
	}

	public ClienteConcurrente(String metodoPago, String tipoPedido, double tarifaConcurrente) {
		super(metodoPago, tipoPedido);
		this.tarifaConcurrente = tarifaConcurrente;
	}

	public ClienteConcurrente(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);
	}

	public ClienteConcurrente(String nombre, String cedula, String correo, String telefono, double tarifaConcurrente) {
		super(nombre, cedula, correo, telefono);
		this.tarifaConcurrente = tarifaConcurrente;
	}

	public ClienteConcurrente(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String tipoPedido) {
		super(nombre, cedula, correo, telefono, metodoPago, tipoPedido);
	}

	public ClienteConcurrente(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String tipoPedido, double tarifaConcurrente) {
		super(nombre, cedula, correo, telefono, metodoPago, tipoPedido);
		this.tarifaConcurrente = tarifaConcurrente;
	}

	public double getTarifaConcurrente() {
		return tarifaConcurrente;
	}

	public void setTarifaConcurrente(double tarifaConcurrente) {
		this.tarifaConcurrente = tarifaConcurrente;
	}

	@Override
	public String toString() {
		return super.toString() + "Cliente concurrente \n Tarifa concurrente:" + tarifaConcurrente + ".";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(tarifaConcurrente);
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
		ClienteConcurrente other = (ClienteConcurrente) obj;
		return Double.doubleToLongBits(tarifaConcurrente) == Double.doubleToLongBits(other.tarifaConcurrente);
	}

}