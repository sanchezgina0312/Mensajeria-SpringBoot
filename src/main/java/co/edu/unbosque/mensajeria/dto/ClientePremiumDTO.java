package co.edu.unbosque.mensajeria.dto;

import java.util.Objects;

import co.edu.unbosque.mensajeria.entity.Cliente;

public class ClientePremiumDTO extends Cliente {
	
	private double tarifaPremium = 0.15;

	public ClientePremiumDTO() {

	}

	public ClientePremiumDTO(double tarifaPremium) {
		super();
		this.tarifaPremium = tarifaPremium;
	}

	public ClientePremiumDTO(String metodoPago, String tipoPedido) {
		super(metodoPago, tipoPedido);
	}

	public ClientePremiumDTO(String metodoPago, String tipoPedido, double tarifaPremium) {
		super(metodoPago, tipoPedido);
		this.tarifaPremium = tarifaPremium;
	}

	public ClientePremiumDTO(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);
	}

	public ClientePremiumDTO(String nombre, String cedula, String correo, String telefono, double tarifaPremium) {
		super(nombre, cedula, correo, telefono);
		this.tarifaPremium = tarifaPremium;
	}

	public ClientePremiumDTO(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String tipoPedido) {
		super(nombre, cedula, correo, telefono, metodoPago, tipoPedido);
	}

	public ClientePremiumDTO(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String tipoPedido, double tarifaPremium) {
		super(nombre, cedula, correo, telefono, metodoPago, tipoPedido);
		this.tarifaPremium = tarifaPremium;
	}

	public double getTarifaPremium() {
		return tarifaPremium;
	}

	public void setTarifaPremium(double tarifaPremium) {
		this.tarifaPremium = tarifaPremium;
	}

	@Override
	public String toString() {
		return super.toString() + "\n Cliente Premium:\n Tarifa premium:" + tarifaPremium + ".";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(tarifaPremium);
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
		ClientePremiumDTO other = (ClientePremiumDTO) obj;
		return Double.doubleToLongBits(tarifaPremium) == Double.doubleToLongBits(other.tarifaPremium);
	}

}