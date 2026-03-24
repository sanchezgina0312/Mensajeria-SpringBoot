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

	/**
	 * Constructor con tarifa concurrente. <br>
	 * <b>post</b>: Se crea una entidad ClienteConcurrente con la tarifa establecida y los 
	 * atributos heredados nulos o por defecto.
	 * * @param tarifaConcurrente La tarifa aplicada al cliente concurrente.
	 */
	public ClienteConcurrente(double tarifaConcurrente) {
		super();
		this.tarifaConcurrente = tarifaConcurrente;
	}

	/**
	 * Constructor con método de pago y tipo de pedido. <br>
	 * <b>post</b>: Se crea una entidad ClienteConcurrente inicializando datos del pedido
	 * y manteniendo la tarifa predeterminada.
	 * * @param metodoPago El método de pago preferido del cliente.
	 * @param tipoPedido El tipo de pedido realizado por el cliente.
	 */
	public ClienteConcurrente(String metodoPago, String tipoPedido) {
		super(metodoPago, tipoPedido);
	}

	/**
	 * Constructor con método de pago, tipo de pedido y tarifa. <br>
	 * <b>post</b>: Se crea una entidad ClienteConcurrente con los datos del pedido y
	 * la tarifa concurrente inicializados.
	 * * @param metodoPago        El método de pago preferido del cliente.
	 * @param tipoPedido        El tipo de pedido realizado por el cliente.
	 * @param tarifaConcurrente La tarifa aplicada al cliente concurrente.
	 */
	public ClienteConcurrente(String metodoPago, String tipoPedido, double tarifaConcurrente) {
		super(metodoPago, tipoPedido);
		this.tarifaConcurrente = tarifaConcurrente;
	}

	/**
	 * Constructor con datos personales básicos. <br>
	 * <b>post</b>: Se crea una entidad ClienteConcurrente inicializando sus datos
	 * básicos y manteniendo la tarifa predeterminada.
	 * * @param nombre   El nombre del cliente.
	 * @param cedula   La cédula del cliente.
	 * @param correo   El correo del cliente.
	 * @param telefono El teléfono del cliente.
	 */
	public ClienteConcurrente(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);
	}

	/**
	 * Constructor con datos personales básicos y tarifa. <br>
	 * <b>post</b>: Se crea una entidad ClienteConcurrente con sus datos personales y
	 * tarifa inicializados.
	 * * @param nombre            El nombre del cliente.
	 * @param cedula            La cédula del cliente.
	 * @param correo            El correo del cliente.
	 * @param telefono          El teléfono del cliente.
	 * @param tarifaConcurrente La tarifa aplicada al cliente concurrente.
	 */
	public ClienteConcurrente(String nombre, String cedula, String correo, String telefono, double tarifaConcurrente) {
		super(nombre, cedula, correo, telefono);
		this.tarifaConcurrente = tarifaConcurrente;
	}

	/**
	 * Constructor con datos personales, método de pago y tipo de pedido. <br>
	 * <b>post</b>: Se crea una entidad ClienteConcurrente completa respecto a los datos
	 * del cliente y pedido, manteniendo la tarifa por defecto.
	 * * @param nombre     El nombre del cliente.
	 * @param cedula     La cédula del cliente.
	 * @param correo     El correo del cliente.
	 * @param telefono   El teléfono del cliente.
	 * @param metodoPago El método de pago preferido del cliente.
	 * @param tipoPedido El tipo de pedido realizado por el cliente.
	 */
	public ClienteConcurrente(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String tipoPedido) {
		super(nombre, cedula, correo, telefono, metodoPago, tipoPedido);
	}

	/**
	 * Constructor completo. <br>
	 * <b>post</b>: Se crea una entidad ClienteConcurrente con todos los atributos
	 * (heredados y propios) inicializados.
	 * * @param nombre            El nombre del cliente.
	 * @param cedula            La cédula del cliente.
	 * @param correo            El correo del cliente.
	 * @param telefono          El teléfono del cliente.
	 * @param metodoPago        El método de pago preferido del cliente.
	 * @param tipoPedido        El tipo de pedido realizado por el cliente.
	 * @param tarifaConcurrente La tarifa aplicada al cliente concurrente.
	 */
	public ClienteConcurrente(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String tipoPedido, double tarifaConcurrente) {
		super(nombre, cedula, correo, telefono, metodoPago, tipoPedido);
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

	/**
	 * Genera un código hash para la entidad ClienteConcurrente.
	 * * @return El código hash basado en los atributos del padre y la tarifa concurrente.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(tarifaConcurrente);
		return result;
	}

	/**
	 * Compara esta entidad con otro objeto para verificar si son iguales.
	 * * @param obj El objeto con el cual se va a comparar.
	 * @return true si los objetos son iguales en atributos e identidad, false en caso contrario.
	 */
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