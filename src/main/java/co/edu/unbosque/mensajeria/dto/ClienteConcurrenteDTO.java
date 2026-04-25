package co.edu.unbosque.mensajeria.dto;

import java.util.Objects;

import co.edu.unbosque.mensajeria.entity.Cliente;

/**
 * DTO (Data Transfer Object) para ClienteConcurrente.
 * <p>
 * Hereda de la clase base Cliente y añade una tarifa específica para los
 * clientes concurrentes o recurrentes. Se utiliza para transferir datos de
 * clientes concurrentes de forma segura.
 *
 * @version 1.0
 */
public class ClienteConcurrenteDTO extends Cliente {

	/** Tarifa aplicada al cliente concurrente. Por defecto inicializada en 0.5. */
	private double tarifaConcurrente = 0.5;

	/**
	 * Constructor vacío. <br>
	 * <b>post</b>: Se crea un DTO ClienteConcurrente con atributos heredados
	 * nulos o por defecto y una tarifa concurrente predeterminada de 0.5.
	 */
	public ClienteConcurrenteDTO() {

	}

	/**
	 * Constructor con tarifa concurrente. <br>
	 * <b>post</b>: Se crea un DTO ClienteConcurrente con la tarifa establecida y
	 * los atributos heredados nulos o por defecto.
	 * 
	 *  @param tarifaConcurrente La tarifa aplicada al cliente concurrente.
	 */
	public ClienteConcurrenteDTO(double tarifaConcurrente) {
		super();
		this.tarifaConcurrente = tarifaConcurrente;
	}

	/**
	 * Constructor con método de pago y tipo de pedido. <br>
	 * <b>post</b>: Se crea un DTO ClienteConcurrente inicializando datos del pedido
	 * y manteniendo la tarifa predeterminada.
	 * 
	 *  @param metodoPago El método de pago preferido del cliente.
	 * @param tipoPedido El tipo de pedido realizado por el cliente.
	 */
	public ClienteConcurrenteDTO(String metodoPago, String tipoPedido) {
		super(metodoPago, tipoPedido);
	}

	/**
	 * Constructor con método de pago, tipo de pedido y tarifa. <br>
	 * <b>post</b>: Se crea un DTO ClienteConcurrente con los datos del pedido y
	 * la tarifa concurrente inicializados.
	 * 
	 *  @param metodoPago        El método de pago preferido del cliente.
	 * @param tipoPedido        El tipo de pedido realizado por el cliente.
	 * @param tarifaConcurrente La tarifa aplicada al cliente concurrente.
	 */
	public ClienteConcurrenteDTO(String metodoPago, String tipoPedido, double tarifaConcurrente) {
		super(metodoPago, tipoPedido);
		this.tarifaConcurrente = tarifaConcurrente;
	}

	/**
	 * Constructor con datos personales básicos. <br>
	 * <b>post</b>: Se crea un DTO ClienteConcurrente inicializando sus datos
	 * básicos y manteniendo la tarifa predeterminada.
	 * 
	 * @param nombre   El nombre del cliente.
	 * @param cedula   La cédula del cliente.
	 * @param correo   El correo del cliente.
	 * @param telefono El teléfono del cliente.
	 */
	public ClienteConcurrenteDTO(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);
	}

	/**
	 * Constructor con datos personales básicos y tarifa. <br>
	 * <b>post</b>: Se crea un DTO ClienteConcurrente con sus datos personales y
	 * tarifa inicializados.
	 * 
	 *  @param nombre            El nombre del cliente.
	 * @param cedula            La cédula del cliente.
	 * @param correo            El correo del cliente.
	 * @param telefono          El teléfono del cliente.
	 * @param tarifaConcurrente La tarifa aplicada al cliente concurrente.
	 */
	public ClienteConcurrenteDTO(String nombre, String cedula, String correo, String telefono,
			double tarifaConcurrente) {
		super(nombre, cedula, correo, telefono);
		this.tarifaConcurrente = tarifaConcurrente;
	}

	/**
	 * Constructor con datos personales, método de pago y tipo de pedido. <br>
	 * <b>post</b>: Se crea un DTO ClienteConcurrente completo respecto a los datos
	 * del cliente y pedido, manteniendo la tarifa por defecto.
	 * 
	 * @param nombre     El nombre del cliente.
	 * @param cedula     La cédula del cliente.
	 * @param correo     El correo del cliente.
	 * @param telefono   El teléfono del cliente.
	 * @param metodoPago El método de pago preferido del cliente.
	 * @param tipoPedido El tipo de pedido realizado por el cliente.
	 */
	public ClienteConcurrenteDTO(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String tipoPedido) {
		super(nombre, cedula, correo, telefono, metodoPago, tipoPedido);
	}

	/**
	 * Constructor completo. <br>
	 * <b>post</b>: Se crea un DTO ClienteConcurrente con todos los atributos
	 * (heredados y propios) inicializados.
	 * 
	 * @param nombre            El nombre del cliente.
	 * @param cedula            La cédula del cliente.
	 * @param correo            El correo del cliente.
	 * @param telefono          El teléfono del cliente.
	 * @param metodoPago        El método de pago preferido del cliente.
	 * @param tipoPedido        El tipo de pedido realizado por el cliente.
	 * @param tarifaConcurrente La tarifa aplicada al cliente concurrente.
	 */
	public ClienteConcurrenteDTO(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String tipoPedido, double tarifaConcurrente) {
		super(nombre, cedula, correo, telefono, metodoPago, tipoPedido);
		this.tarifaConcurrente = tarifaConcurrente;
	}

	/**
	 * Obtiene la tarifa concurrente del cliente.
	 * 
	 * @return La tarifa asignada al cliente concurrente.
	 */
	public double getTarifaConcurrente() {
		return tarifaConcurrente;
	}

	/**
	 * Establece la tarifa concurrente del cliente.
	 * 
	 *  @param tarifaConcurrente La nueva tarifa a asignar.
	 */
	public void setTarifaConcurrente(double tarifaConcurrente) {
		this.tarifaConcurrente = tarifaConcurrente;
	}

	/**
	 * Devuelve una representación en String del DTO del cliente concurrente.
	 * 
	 * @return Una cadena que incluye los datos del cliente general junto con la tarifa de concurrencia.
	 */
	@Override
	public String toString() {
		return super.toString() + "\n Cliente concurrente:\n Tarifa concurrente:" + tarifaConcurrente + ".";
	}

	/**
	 * Genera un código hash para el objeto DTO.
	 * 
	 * @return El código hash basado en los atributos del padre y la tarifa concurrente.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(tarifaConcurrente);
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
		ClienteConcurrenteDTO other = (ClienteConcurrenteDTO) obj;
		return Double.doubleToLongBits(tarifaConcurrente) == Double.doubleToLongBits(other.tarifaConcurrente);
	}

}