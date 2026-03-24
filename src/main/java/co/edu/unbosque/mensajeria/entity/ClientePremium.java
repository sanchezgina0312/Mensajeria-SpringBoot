package co.edu.unbosque.mensajeria.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entidad que representa a un Cliente Premium en el sistema.
 * <p>
 * Hereda de la clase base Cliente y añade una tarifa específica aplicada a 
 * los clientes que cuentan con una membresía o estatus premium. Esta clase 
 * está mapeada a la tabla "clientepremium" en la base de datos mediante JPA.
 *
 * @version 1.0
 */
@Entity
@Table(name = "clientepremium")
public class ClientePremium extends Cliente {

	/** Tarifa especial aplicada al cliente premium. Inicializada por defecto en 0.15. */
	private double tarifaPremium = 0.15;

	/**
	 * Constructor vacío. <br>
	 * <b>post</b>: Se crea una entidad ClientePremium con atributos heredados nulos 
	 * o por defecto y una tarifa premium predeterminada de 0.15. Requerido por JPA.
	 */
	public ClientePremium() {

	}

	/**
	 * Constructor con tarifa premium. <br>
	 * <b>post</b>: Se crea una entidad ClientePremium con la tarifa establecida y los 
	 * atributos heredados nulos o por defecto.
	 * * @param tarifaPremium La tarifa aplicada al cliente premium.
	 */
	public ClientePremium(double tarifaPremium) {
		super();
		this.tarifaPremium = tarifaPremium;
	}

	/**
	 * Constructor con método de pago y tipo de pedido. <br>
	 * <b>post</b>: Se crea una entidad ClientePremium inicializando datos del pedido
	 * y manteniendo la tarifa predeterminada.
	 * * @param metodoPago El método de pago preferido del cliente.
	 * @param tipoPedido El tipo de pedido realizado por el cliente.
	 */
	public ClientePremium(String metodoPago, String tipoPedido) {
		super(metodoPago, tipoPedido);
	}

	/**
	 * Constructor con método de pago, tipo de pedido y tarifa. <br>
	 * <b>post</b>: Se crea una entidad ClientePremium con los datos del pedido y
	 * la tarifa premium inicializados.
	 * * @param metodoPago    El método de pago preferido del cliente.
	 * @param tipoPedido    El tipo de pedido realizado por el cliente.
	 * @param tarifaPremium La tarifa aplicada al cliente premium.
	 */
	public ClientePremium(String metodoPago, String tipoPedido, double tarifaPremium) {
		super(metodoPago, tipoPedido);
		this.tarifaPremium = tarifaPremium;
	}

	/**
	 * Constructor con datos personales básicos. <br>
	 * <b>post</b>: Se crea una entidad ClientePremium inicializando sus datos
	 * básicos y manteniendo la tarifa predeterminada.
	 * * @param nombre   El nombre del cliente.
	 * @param cedula   La cédula del cliente.
	 * @param correo   El correo del cliente.
	 * @param telefono El teléfono del cliente.
	 */
	public ClientePremium(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);
	}

	/**
	 * Constructor con datos personales básicos y tarifa. <br>
	 * <b>post</b>: Se crea una entidad ClientePremium con sus datos personales y
	 * tarifa inicializados.
	 * * @param nombre        El nombre del cliente.
	 * @param cedula        La cédula del cliente.
	 * @param correo        El correo del cliente.
	 * @param telefono      El teléfono del cliente.
	 * @param tarifaPremium La tarifa aplicada al cliente premium.
	 */
	public ClientePremium(String nombre, String cedula, String correo, String telefono, double tarifaPremium) {
		super(nombre, cedula, correo, telefono);
		this.tarifaPremium = tarifaPremium;
	}

	/**
	 * Constructor con datos personales, método de pago y tipo de pedido. <br>
	 * <b>post</b>: Se crea una entidad ClientePremium completa respecto a los datos
	 * del cliente y pedido, manteniendo la tarifa por defecto.
	 * * @param nombre     El nombre del cliente.
	 * @param cedula     La cédula del cliente.
	 * @param correo     El correo del cliente.
	 * @param telefono   El teléfono del cliente.
	 * @param metodoPago El método de pago preferido del cliente.
	 * @param tipoPedido El tipo de pedido realizado por el cliente.
	 */
	public ClientePremium(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String tipoPedido) {
		super(nombre, cedula, correo, telefono, metodoPago, tipoPedido);
	}

	/**
	 * Constructor completo. <br>
	 * <b>post</b>: Se crea una entidad ClientePremium con todos los atributos
	 * (heredados y propios) inicializados.
	 * * @param nombre        El nombre del cliente.
	 * @param cedula        La cédula del cliente.
	 * @param correo        El correo del cliente.
	 * @param telefono      El teléfono del cliente.
	 * @param metodoPago    El método de pago preferido del cliente.
	 * @param tipoPedido    El tipo de pedido realizado por el cliente.
	 * @param tarifaPremium La tarifa aplicada al cliente premium.
	 */
	public ClientePremium(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String tipoPedido, double tarifaPremium) {
		super(nombre, cedula, correo, telefono, metodoPago, tipoPedido);
		this.tarifaPremium = tarifaPremium;
	}

	/**
	 * Obtiene la tarifa premium del cliente.
	 * * @return La tarifa asignada al cliente premium.
	 */
	public double getTarifaPremium() {
		return tarifaPremium;
	}

	/**
	 * Establece la tarifa premium del cliente.
	 * * @param tarifaPremium La nueva tarifa a asignar.
	 */
	public void setTarifaPremium(double tarifaPremium) {
		this.tarifaPremium = tarifaPremium;
	}

	/**
	 * Devuelve una representación en String de la entidad ClientePremium.
	 * * @return Una cadena que incluye los datos del cliente general junto con la tarifa premium.
	 */
	@Override
	public String toString() {
		return super.toString() + "Cliente premium \n Tarifa premium: " + tarifaPremium + ".";
	}

	/**
	 * Genera un código hash para la entidad ClientePremium.
	 * * @return El código hash basado en los atributos del padre y la tarifa premium.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(tarifaPremium);
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
		ClientePremium other = (ClientePremium) obj;
		return Double.doubleToLongBits(tarifaPremium) == Double.doubleToLongBits(other.tarifaPremium);
	}
}