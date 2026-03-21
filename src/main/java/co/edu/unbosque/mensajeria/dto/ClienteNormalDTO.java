package co.edu.unbosque.mensajeria.dto;

import java.util.Objects;

import co.edu.unbosque.mensajeria.entity.Cliente;

public class ClienteNormalDTO extends Cliente {

	private double tarifaNormal; // 0%

	public ClienteNormalDTO() {
		
	}

	public ClienteNormalDTO(double tarifaNormal) {
		super();
		this.tarifaNormal = tarifaNormal;
	}

	public ClienteNormalDTO(String metodoPago, String tipoPedido) {
		super(metodoPago, tipoPedido);
	}

	public ClienteNormalDTO(String metodoPago, String tipoPedido, double tarifaNormal) {
		super(metodoPago, tipoPedido);
		this.tarifaNormal = tarifaNormal;
	}

	public ClienteNormalDTO(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);
	}

	public ClienteNormalDTO(String nombre, String cedula, String correo, String telefono, double tarifaNormal) {
		super(nombre, cedula, correo, telefono);
		this.tarifaNormal = tarifaNormal;
	}

	public ClienteNormalDTO(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String tipoPedido) {
		super(nombre, cedula, correo, telefono, metodoPago, tipoPedido);
	}

	public ClienteNormalDTO(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String tipoPedido, double tarifaNormal) {
		super(nombre, cedula, correo, telefono, metodoPago, tipoPedido);
		this.tarifaNormal = tarifaNormal;
	}

	public double getTarifaNormal() {
		return tarifaNormal;
	}

	public void setTarifaNormal(double tarifaNormal) {
		this.tarifaNormal = tarifaNormal;
	}

	@Override
	public String toString() {
		return super.toString() + "\n Cliente normal:\n Tarifa normal:" + tarifaNormal + ".";
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
		ClienteNormalDTO other = (ClienteNormalDTO) obj;
		return Double.doubleToLongBits(tarifaNormal) == Double.doubleToLongBits(other.tarifaNormal);
	}

}