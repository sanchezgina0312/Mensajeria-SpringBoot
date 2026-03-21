package co.edu.unbosque.mensajeria.entity;

import java.util.Objects;

import jakarta.persistence.Entity;

import jakarta.persistence.Table;

@Entity
@Table(name = "cliente")

public abstract class Cliente extends Usuario {

	private String metodoPago;
	private String tipoPedido;

	public Cliente() {
		
	}

	public Cliente(String metodoPago, String tipoPedido) {
		super();
		this.metodoPago = metodoPago;
		this.tipoPedido = tipoPedido;
	}

	public Cliente(String nombre, String cedula, String correo, String telefono, String metodoPago, String tipoPedido) {
		super(nombre, cedula, correo, telefono);
		this.metodoPago = metodoPago;
		this.tipoPedido = tipoPedido;
	}

	public Cliente(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);

	}

	public String getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

	public String getTipoPedido() {
		return tipoPedido;
	}

	public void setTipoPedido(String tipoPedido) {
		this.tipoPedido = tipoPedido;
	}

	@Override
	public String toString() {
		return super.toString() + "Cliente \n MÃ©todo de pago:" + metodoPago + "\n" + "Tipo de pedido:" + tipoPedido
				+ "\n";
	}

	@Override
	public int hashCode() {
		return Objects.hash(metodoPago, tipoPedido);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(metodoPago, other.metodoPago) && Objects.equals(tipoPedido, other.tipoPedido);
	}

}