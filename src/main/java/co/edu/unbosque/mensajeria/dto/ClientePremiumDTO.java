package co.edu.unbosque.mensajeria.dto;

import java.util.Objects;

import co.edu.unbosque.mensajeria.entity.Cliente;

/**
 * DTO (Data Transfer Object) para ClientePremium.
 * <p>
 * Hereda de la clase base Cliente y añade una tarifa específica para los
 * clientes premium. Se utiliza para transferir datos de clientes con 
 * membresía premium de forma segura entre capas.
 *
 * @version 1.0
 */
public class ClientePremiumDTO extends Cliente {
	
	/** Tarifa aplicada al cliente premium. Por defecto inicializada en 0.15. */
	private double tarifaPremium = 0.15;

	/**
	 * Constructor vacío. <br>
	 * <b>post</b>: Se crea un DTO ClientePremium con atributos heredados
	 * nulos o por defecto y una tarifa premium predeterminada de 0.15.
	 */
	public ClientePremiumDTO() {

	}

	/**
	 * Constructor con tarifa premium. <br>
	 * <b>post</b>: Se crea un DTO ClientePremium con la tarifa establecida y
	 * los atributos heredados nulos o por defecto.
	 * 
	 * @param tarifaPremium La tarifa aplicada al cliente premium.
	 */
	public ClientePremiumDTO(double tarifaPremium) {
		super();
		this.tarifaPremium = tarifaPremium;
	}

	/**
	 * Constructor con método de pago y tipo de pedido. <br>
	 * <b>post</b>: Se crea un DTO ClientePremium inicializando datos del pedido
	 * y manteniendo la tarifa por defecto.
	 * 
	 * @param metodoPago El método de pago preferido del cliente.
	 * @param tipoPedido El tipo de pedido realizado por el cliente.
	 */
	public ClientePremiumDTO(String metodoPago, String tipoPedido) {
		super(metodoPago, tipoPedido);
	}

	/**
	 * Constructor con método de pago, tipo de pedido y tarifa. <br>
	 * <b>post</b>: Se crea un DTO ClientePremium con los datos del pedido y
	 * la tarifa premium inicializados.
	 * 
	 * @param metodoPago    El método de pago preferido del cliente.
	 * @param tipoPedido    El tipo de pedido realizado por el cliente.
	 * @param tarifaPremium La tarifa aplicada al cliente premium.
	 */
	public ClientePremiumDTO(String metodoPago, String tipoPedido, double tarifaPremium) {
		super(metodoPago, tipoPedido);
		this.tarifaPremium = tarifaPremium;
	}

	/**
	 * Constructor con datos personales básicos. <br>
	 * <b>post</b>: Se crea un DTO ClientePremium inicializando sus datos
	 * básicos y manteniendo la tarifa por defecto.
	 * 
	 * @param nombre   El nombre del cliente.
	 * @param cedula   La cédula del cliente.
	 * @param correo   El correo del cliente.
	 * @param telefono El teléfono del cliente.
	 */
	public ClientePremiumDTO(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);
	}

	/**
	 * Constructor con datos personales básicos y tarifa. <br>
	 * <b>post</b>: Se crea un DTO ClientePremium con sus datos personales y
	 * tarifa inicializados.
	 * 
	 *  @param nombre        El nombre del cliente.
	 * @param cedula        La cédula del cliente.
	 * @param correo        El correo del cliente.
	 * @param telefono      El teléfono del cliente.
	 * @param tarifaPremium La tarifa aplicada al cliente premium.
	 */
	public ClientePremiumDTO(String nombre, String cedula, String correo, String telefono, double tarifaPremium) {
		super(nombre, cedula, correo, telefono);
		this.tarifaPremium = tarifaPremium;
	}

	/**
	 * Constructor con datos personales, método de pago y tipo de pedido. <br>
	 * <b>post</b>: Se crea un DTO ClientePremium completo respecto a los datos
	 * del cliente y pedido, manteniendo la tarifa por defecto.
	 * 
	 * @param nombre     El nombre del cliente.
	 * @param cedula     La cédula del cliente.
	 * @param correo     El correo del cliente.
	 * @param telefono   El teléfono del cliente.
	 * @param metodoPago El método de pago preferido del cliente.
	 * @param tipoPedido El tipo de pedido realizado por el cliente.
	 */
	public ClientePremiumDTO(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String tipoPedido) {
		super(nombre, cedula, correo, telefono, metodoPago, tipoPedido);
	}

	/**
	 * Constructor completo. <br>
	 * <b>post</b>: Se crea un DTO ClientePremium con todos los atributos
	 * (heredados y propios) inicializados.
	 * 
	 * @param nombre        El nombre del cliente.
	 * @param cedula        La cédula del cliente.
	 * @param correo        El correo del cliente.
	 * @param telefono      El teléfono del cliente.
	 * @param metodoPago    El método de pago preferido del cliente.
	 * @param tipoPedido    El tipo de pedido realizado por el cliente.
	 * @param tarifaPremium La tarifa aplicada al cliente premium.
	 */
	public ClientePremiumDTO(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String tipoPedido, double tarifaPremium) {
		super(nombre, cedula, correo, telefono, metodoPago, tipoPedido);
		this.tarifaPremium = tarifaPremium;
	}

	/**
	 * Obtiene la tarifa del cliente premium.
	 * 
	 *  @return La tarifa asignada al cliente premium.
	 */
	public double getTarifaPremium() {
		return tarifaPremium;
	}

	/**
	 * Establece la tarifa del cliente premium.
	 * 
	 *  @param tarifaPremium La nueva tarifa a asignar.
	 */
	public void setTarifaPremium(double tarifaPremium) {
		this.tarifaPremium = tarifaPremium;
	}

	/**
	 * Devuelve una representación en String del DTO del cliente premium.
	 * 
	 *  @return Una cadena que incluye los datos del cliente general junto con la tarifa premium.
	 */
	@Override
	public String toString() {
		return super.toString() + "\n Cliente Premium:\n Tarifa premium:" + tarifaPremium + ".";
	}

	/**
	 * Genera un código hash para el objeto DTO.
	 * 
	 *  @return El código hash basado en los atributos del padre y la tarifa premium.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(tarifaPremium);
		return result;
	}

	/**
	 * Compara este DTO con otro objeto para verificar si son iguales.
	 * 
	 * @param obj El objeto con el cual se va a comparar.
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
		ClientePremiumDTO other = (ClientePremiumDTO) obj;
		return Double.doubleToLongBits(tarifaPremium) == Double.doubleToLongBits(other.tarifaPremium);
	}

}