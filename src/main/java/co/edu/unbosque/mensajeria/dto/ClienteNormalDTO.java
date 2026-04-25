package co.edu.unbosque.mensajeria.dto;

import java.util.Objects;

import co.edu.unbosque.mensajeria.entity.Cliente;

/**
 * DTO (Data Transfer Object) para ClienteNormal.
 * <p>
 * Hereda de la clase base Cliente y añade una tarifa específica para los
 * clientes normales. Se utiliza para transferir datos de clientes estándar de 
 * forma segura entre capas.
 *
 * @version 1.0
 */
public class ClienteNormalDTO extends Cliente {

	/** Tarifa aplicada al cliente normal (usualmente 0%). */
	private double tarifaNormal; // 0%

	/**
	 * Constructor vacío. <br>
	 * <b>post</b>: Se crea un DTO ClienteNormal con atributos heredados
	 * nulos o por defecto y una tarifa normal inicializada en 0.
	 */
	public ClienteNormalDTO() {
		
	}

	/**
	 * Constructor con tarifa normal. <br>
	 * <b>post</b>: Se crea un DTO ClienteNormal con la tarifa establecida y
	 * los atributos heredados nulos o por defecto.
	 * 
	 * @param tarifaNormal La tarifa aplicada al cliente normal.
	 */
	public ClienteNormalDTO(double tarifaNormal) {
		super();
		this.tarifaNormal = tarifaNormal;
	}

	/**
	 * Constructor con método de pago y tipo de pedido. <br>
	 * <b>post</b>: Se crea un DTO ClienteNormal inicializando datos del pedido
	 * y manteniendo la tarifa por defecto.
	 * 
	 * @param metodoPago El método de pago preferido del cliente.
	 * @param tipoPedido El tipo de pedido realizado por el cliente.
	 */
	public ClienteNormalDTO(String metodoPago, String tipoPedido) {
		super(metodoPago, tipoPedido);
	}

	/**
	 * Constructor con método de pago, tipo de pedido y tarifa. <br>
	 * <b>post</b>: Se crea un DTO ClienteNormal con los datos del pedido y
	 * la tarifa normal inicializados.
	 * 
	 * @param metodoPago   El método de pago preferido del cliente.
	 * @param tipoPedido   El tipo de pedido realizado por el cliente.
	 * @param tarifaNormal La tarifa aplicada al cliente normal.
	 */
	public ClienteNormalDTO(String metodoPago, String tipoPedido, double tarifaNormal) {
		super(metodoPago, tipoPedido);
		this.tarifaNormal = tarifaNormal;
	}

	/**
	 * Constructor con datos personales básicos. <br>
	 * <b>post</b>: Se crea un DTO ClienteNormal inicializando sus datos
	 * básicos y manteniendo la tarifa por defecto.
	 * 
	 * @param nombre   El nombre del cliente.
	 * @param cedula   La cédula del cliente.
	 * @param correo   El correo del cliente.
	 * @param telefono El teléfono del cliente.
	 */
	public ClienteNormalDTO(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);
	}

	/**
	 * Constructor con datos personales básicos y tarifa. <br>
	 * <b>post</b>: Se crea un DTO ClienteNormal con sus datos personales y
	 * tarifa inicializados.
	 * 
	 * @param nombre       El nombre del cliente.
	 * @param cedula       La cédula del cliente.
	 * @param correo       El correo del cliente.
	 * @param telefono     El teléfono del cliente.
	 * @param tarifaNormal La tarifa aplicada al cliente normal.
	 */
	public ClienteNormalDTO(String nombre, String cedula, String correo, String telefono, double tarifaNormal) {
		super(nombre, cedula, correo, telefono);
		this.tarifaNormal = tarifaNormal;
	}

	/**
	 * Constructor con datos personales, método de pago y tipo de pedido. <br>
	 * <b>post</b>: Se crea un DTO ClienteNormal completo respecto a los datos
	 * del cliente y pedido, manteniendo la tarifa por defecto.
	 *
	 * @param nombre     El nombre del cliente.
	 * @param cedula     La cédula del cliente.
	 * @param correo     El correo del cliente.
	 * @param telefono   El teléfono del cliente.
	 * @param metodoPago El método de pago preferido del cliente.
	 * @param tipoPedido El tipo de pedido realizado por el cliente.
	 */
	public ClienteNormalDTO(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String tipoPedido) {
		super(nombre, cedula, correo, telefono, metodoPago, tipoPedido);
	}

	/**
	 * Constructor completo. <br>
	 * <b>post</b>: Se crea un DTO ClienteNormal con todos los atributos
	 * (heredados y propios) inicializados.
	 * 
	 *  @param nombre       El nombre del cliente.
	 * @param cedula       La cédula del cliente.
	 * @param correo       El correo del cliente.
	 * @param telefono     El teléfono del cliente.
	 * @param metodoPago   El método de pago preferido del cliente.
	 * @param tipoPedido   El tipo de pedido realizado por el cliente.
	 * @param tarifaNormal La tarifa aplicada al cliente normal.
	 */
	public ClienteNormalDTO(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String tipoPedido, double tarifaNormal) {
		super(nombre, cedula, correo, telefono, metodoPago, tipoPedido);
		this.tarifaNormal = tarifaNormal;
	}

	/**
	 * Obtiene la tarifa del cliente normal.
	 * 
	 * @return La tarifa asignada al cliente normal.
	 */
	public double getTarifaNormal() {
		return tarifaNormal;
	}

	/**
	 * Establece la tarifa del cliente normal.
	 * 
	 *  @param tarifaNormal La nueva tarifa a asignar.
	 */
	public void setTarifaNormal(double tarifaNormal) {
		this.tarifaNormal = tarifaNormal;
	}

	/**
	 * Devuelve una representación en String del DTO del cliente normal.
	 * 
	 *  @return Una cadena que incluye los datos del cliente general junto con la tarifa normal.
	 */
	@Override
	public String toString() {
		return super.toString() + "\n Cliente normal:\n Tarifa normal:" + tarifaNormal + ".";
	}

	/**
	 * Genera un código hash para el objeto DTO.
	 * 
	 *  @return El código hash basado en los atributos del padre y la tarifa normal.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(tarifaNormal);
		return result;
	}

	/**
	 * Compara este DTO con otro objeto para verificar si son iguales.
	 * 
	 *  @param obj El objeto con el cual se va a comparar.
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
		ClienteNormalDTO other = (ClienteNormalDTO) obj;
		return Double.doubleToLongBits(tarifaNormal) == Double.doubleToLongBits(other.tarifaNormal);
	}

}