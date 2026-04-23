package co.edu.unbosque.mensajeria.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entidad que representa a un Cliente Concurrente (frecuente) en el sistema.
 * <p>
 * Hereda de la clase base  Cliente y añade una tarifa específica aplicada a 
 * los clientes que realizan pedidos de forma concurrente o recurrente. Esta clase 
 * está mapeada a la tabla "clienteconcurrente" en la base de datos mediante JPA.
 *
 * @version 1.0
 */
@Entity
@Table(name = "clienteconcurrente")
public class ClienteConcurrente extends Cliente {

	/** Tarifa especial aplicada al cliente concurrente. Inicializada por defecto en 0.5. */
	private double tarifaConcurrente = 0.5;

	/**
	 * Constructor vacío. <br>
	 * <b>post</b>: Se crea una entidad ClienteConcurrente con atributos heredados nulos 
	 * o por defecto y una tarifa concurrente predeterminada de 0.5. Requerido por JPA.
	 */
	public ClienteConcurrente() {
	}

	public ClienteConcurrente(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String tipoPedido) {
		super(nombre, cedula, correo, telefono, metodoPago, tipoPedido);
	}
	
	public ClienteConcurrente(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);
	}
	
	public ClienteConcurrente(String metodoPago, String tipoPedido, String contrasenia) {
		super(metodoPago, tipoPedido, contrasenia);
	}

	public ClienteConcurrente(String metodoPago, String tipoPedido, String contrasenia, double tarifaConcurrente) {
		super(metodoPago, tipoPedido, contrasenia);
		this.tarifaConcurrente = tarifaConcurrente;
	}

	public ClienteConcurrente(String nombre, String cedula, String correo, String telefono, double tarifaConcurrente) {
		super(nombre, cedula, correo, telefono);
		this.tarifaConcurrente = tarifaConcurrente;
	}

	public ClienteConcurrente(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String tipoPedido, double tarifaConcurrente) {
		super(nombre, cedula, correo, telefono, metodoPago, tipoPedido);
		this.tarifaConcurrente = tarifaConcurrente;
	}

	public ClienteConcurrente(double tarifaConcurrente) {
		super();
		this.tarifaConcurrente = tarifaConcurrente;
	}

	/**
	 * Obtiene la tarifa concurrente del cliente.
	 * * @return La tarifa asignada al cliente concurrente.
	 */
	public double getTarifaConcurrente() {
		return tarifaConcurrente;
	}

	/**
	 * Establece la tarifa concurrente del cliente.
	 * * @param tarifaConcurrente La nueva tarifa a asignar.
	 */
	public void setTarifaConcurrente(double tarifaConcurrente) {
		this.tarifaConcurrente = tarifaConcurrente;
	}

	/**
	 * Devuelve una representación en String de la entidad ClienteConcurrente.
	 * * @return Una cadena que incluye los datos del cliente general junto con la tarifa de concurrencia.
	 */
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