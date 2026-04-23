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

	public ClienteNormalDTO(double tarifaNormal) {
		super();
		this.tarifaNormal = tarifaNormal;
	}

	public ClienteNormalDTO(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String tipoPedido, double tarifaNormal) {
		super(nombre, cedula, correo, telefono, metodoPago, tipoPedido);
		this.tarifaNormal = tarifaNormal;
	}

	public ClienteNormalDTO(String nombre, String cedula, String correo, String telefono, double tarifaNormal) {
		super(nombre, cedula, correo, telefono);
		this.tarifaNormal = tarifaNormal;
	}

	public ClienteNormalDTO(String metodoPago, String tipoPedido, String contrasenia, double tarifaNormal) {
		super(metodoPago, tipoPedido, contrasenia);
		this.tarifaNormal = tarifaNormal;
	}

	public ClienteNormalDTO(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String tipoPedido) {
		super(nombre, cedula, correo, telefono, metodoPago, tipoPedido);
	}

	public ClienteNormalDTO(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);
	}

	public ClienteNormalDTO(String metodoPago, String tipoPedido, String contrasenia) {
		super(metodoPago, tipoPedido, contrasenia);
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