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
	
	public ClienteConcurrenteDTO(double tarifaConcurrente) {
		super();
		this.tarifaConcurrente = tarifaConcurrente;
	}

	public ClienteConcurrenteDTO(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String tipoPedido, double tarifaConcurrente) {
		super(nombre, cedula, correo, telefono, metodoPago, tipoPedido);
		this.tarifaConcurrente = tarifaConcurrente;
	}

	public ClienteConcurrenteDTO(String nombre, String cedula, String correo, String telefono,
			double tarifaConcurrente) {
		super(nombre, cedula, correo, telefono);
		this.tarifaConcurrente = tarifaConcurrente;
	}

	public ClienteConcurrenteDTO(String metodoPago, String tipoPedido, String contrasenia, double tarifaConcurrente) {
		super(metodoPago, tipoPedido, contrasenia);
		this.tarifaConcurrente = tarifaConcurrente;
	}

	public ClienteConcurrenteDTO(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String tipoPedido) {
		super(nombre, cedula, correo, telefono, metodoPago, tipoPedido);
	}

	public ClienteConcurrenteDTO(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);
	}

	public ClienteConcurrenteDTO(String metodoPago, String tipoPedido, String contrasenia) {
		super(metodoPago, tipoPedido, contrasenia);
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