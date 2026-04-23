package co.edu.unbosque.mensajeria.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entidad que representa a un Cliente Normal en el sistema.
 * <p>
 * Hereda de la clase base Cliente y añade una tarifa específica aplicada a 
 * los clientes estándar (usualmente 0). Esta clase está mapeada a la tabla 
 * "clientenormal" en la base de datos mediante JPA.
 *
 * @version 1.0
 */
@Entity
@Table(name = "clientenormal")
public class ClienteNormal extends Cliente {

	/** Tarifa aplicada al cliente normal. Inicializada por defecto en 0. */
	private double tarifaNormal = 0;

	/**
	 * Constructor vacío. <br>
	 * <b>post</b>: Se crea una entidad ClienteNormal con atributos heredados nulos 
	 * o por defecto y una tarifa normal predeterminada de 0. Requerido por JPA.
	 */
	public ClienteNormal() {

	}

	public ClienteNormal(double tarifaNormal) {
		super();
		this.tarifaNormal = tarifaNormal;
	}

	public ClienteNormal(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String tipoPedido, double tarifaNormal) {
		super(nombre, cedula, correo, telefono, metodoPago, tipoPedido);
		this.tarifaNormal = tarifaNormal;
	}

	public ClienteNormal(String nombre, String cedula, String correo, String telefono, double tarifaNormal) {
		super(nombre, cedula, correo, telefono);
		this.tarifaNormal = tarifaNormal;
	}

	public ClienteNormal(String metodoPago, String tipoPedido, String contrasenia, double tarifaNormal) {
		super(metodoPago, tipoPedido, contrasenia);
		this.tarifaNormal = tarifaNormal;
	}

	/**
	 * Obtiene la tarifa normal del cliente.
	 * * @return La tarifa asignada al cliente normal.
	 */
	public double getTarifaNormal() {
		return tarifaNormal;
	}

	/**
	 * Establece la tarifa normal del cliente.
	 * * @param tarifaNormal La nueva tarifa a asignar.
	 */
	public void setTarifaNormal(double tarifaNormal) {
		this.tarifaNormal = tarifaNormal;
	}

	/**
	 * Devuelve una representación en String de la entidad ClienteNormal.
	 * * @return Una cadena que incluye los datos del cliente general junto con la tarifa normal.
	 */
	@Override
	public String toString() {
		return super.toString() + "Cliente normal \n Tarifa normal: " + tarifaNormal + ".";
	}

	/**
	 * Genera un código hash para la entidad ClienteNormal.
	 * * @return El código hash basado en los atributos del padre y la tarifa normal.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(tarifaNormal);
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
		ClienteNormal other = (ClienteNormal) obj;
		return Double.doubleToLongBits(tarifaNormal) == Double.doubleToLongBits(other.tarifaNormal);
	}

}