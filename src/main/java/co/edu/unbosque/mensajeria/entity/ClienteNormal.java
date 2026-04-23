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
 * </p>
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
		super();
	}

	/**
	 * Constructor que inicializa únicamente la tarifa normal.
	 * @param tarifaNormal Valor decimal de la tarifa base.
	 */
	public ClienteNormal(double tarifaNormal) {
		super();
		this.tarifaNormal = tarifaNormal;
	}

	/**
	 * Constructor completo con datos personales, logísticos y tarifa.
	 * @param nombre     Nombre completo del cliente.
	 * @param cedula     Documento de identidad.
	 * @param correo     Dirección de correo electrónico.
	 * @param telefono   Número telefónico de contacto.
	 * @param metodoPago Método de pago preferido.
	 * @param tipoPedido Categoría del pedido realizado.
	 * @param tarifaNormal Valor de la tarifa diferencial.
	 */
	public ClienteNormal(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String tipoPedido, double tarifaNormal) {
		super(nombre, cedula, correo, telefono, metodoPago, tipoPedido);
		this.tarifaNormal = tarifaNormal;
	}

	/**
	 * Constructor para datos personales e identificación con tarifa.
	 * @param nombre       Nombre completo del cliente.
	 * @param cedula       Identificación oficial.
	 * @param correo       E-mail de contacto.
	 * @param telefono     Número de teléfono.
	 * @param tarifaNormal Valor de la tarifa normal.
	 */
	public ClienteNormal(String nombre, String cedula, String correo, String telefono, double tarifaNormal) {
		super(nombre, cedula, correo, telefono);
		this.tarifaNormal = tarifaNormal;
	}

	/**
	 * Constructor para gestión de cuenta, logística y tarifa diferencial.
	 * @param metodoPago   Medio de pago.
	 * @param tipoPedido   Tipo de servicio.
	 * @param contrasenia  Clave de acceso al sistema.
	 * @param tarifaNormal Tarifa asignada.
	 */
	public ClienteNormal(String metodoPago, String tipoPedido, String contrasenia, double tarifaNormal) {
		super(metodoPago, tipoPedido, contrasenia);
		this.tarifaNormal = tarifaNormal;
	}

	/**
	 * Obtiene la tarifa normal del cliente.
	 * @return La tarifa asignada al cliente normal.
	 */
	public double getTarifaNormal() {
		return tarifaNormal;
	}

	/**
	 * Establece la tarifa normal del cliente.
	 * @param tarifaNormal La nueva tarifa a asignar.
	 */
	public void setTarifaNormal(double tarifaNormal) {
		this.tarifaNormal = tarifaNormal;
	}

	/**
	 * Devuelve una representación en String de la entidad ClienteNormal.
	 * @return Una cadena que incluye los datos del cliente general junto con la tarifa normal.
	 */
	@Override
	public String toString() {
		return super.toString() + "Cliente normal \n Tarifa normal: " + tarifaNormal + ".";
	}

	/**
	 * Genera un código hash para la entidad ClienteNormal.
	 * @return El código hash basado en los atributos del padre y la tarifa normal.
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
	 * @param obj El objeto con el cual se va a comparar.
	 * @return true si los objetos coinciden en atributos e identidad, false en caso contrario.
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