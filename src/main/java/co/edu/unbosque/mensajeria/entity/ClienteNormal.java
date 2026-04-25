package co.edu.unbosque.mensajeria.entity;

import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entidad que representa a un Cliente Normal en el sistema.
 * <p>
 * Hereda de la clase base Cliente y añade una tarifa específica aplicada a los
 * clientes estándar (usualmente 0). Esta clase está mapeada a la tabla
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
	 * <b>post</b>: Se crea una instancia de ClienteNormal con atributos en sus valores 
	 * por defecto. Requerido para el funcionamiento de JPA y frameworks de mapeo.
	 */
	public ClienteNormal() {
		
	}

	/**
	 * Constructor con tarifa base. <br>
	 * <b>post</b>: Se crea una instancia de ClienteNormal inicializando únicamente 
	 * el valor de la tarifa estándar aplicada a sus envíos.
	 * @param tarifaNormal Valor de la tarifa base para el cliente.
	 */
	public ClienteNormal(double tarifaNormal) {
		super();
		this.tarifaNormal = tarifaNormal;
	}

	/**
	 * Constructor con credenciales y tarifa. <br>
	 * <b>post</b>: Se crea una instancia de ClienteNormal con método de pago, 
	 * contraseña y tarifa asignada, delegando la seguridad a la clase base.
	 * @param metodoPago   Sistema de pago elegido.
	 * @param contrasenia  Clave de acceso.
	 * @param tarifaNormal Valor de la tarifa base.
	 */
	public ClienteNormal(String metodoPago, String contrasenia, double tarifaNormal) {
		super(metodoPago, contrasenia);
		this.tarifaNormal = tarifaNormal;
	}

	/**
	 * Constructor con datos personales y tarifa. <br>
	 * <b>post</b>: Se crea una instancia de ClienteNormal vinculando la información 
	 * básica de contacto con su tarifa correspondiente.
	 * @param nombre       Nombre del cliente.
	 * @param cedula       Documento de identidad.
	 * @param correo       Email de contacto.
	 * @param telefono     Número telefónico.
	 * @param tarifaNormal Valor de la tarifa base.
	 */
	public ClienteNormal(String nombre, String cedula, String correo, String telefono, double tarifaNormal) {
		super(nombre, cedula, correo, telefono);
		this.tarifaNormal = tarifaNormal;
	}

	/**
	 * Constructor completo con todos los parámetros de negocio. <br>
	 * <b>post</b>: Se crea una instancia de ClienteNormal con la totalidad de datos 
	 * personales, credenciales de cuenta y configuración de tarifa.
	 * @param nombre       Nombre completo.
	 * @param cedula       Cédula de ciudadanía.
	 * @param correo       Correo electrónico.
	 * @param telefono     Teléfono de contacto.
	 * @param metodoPago   Medio de pago.
	 * @param contrasenia  Contraseña del sistema.
	 * @param tarifaNormal Valor de la tarifa base.
	 */
	public ClienteNormal(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String contrasenia, double tarifaNormal) {
		super(nombre, cedula, correo, telefono, metodoPago, contrasenia);
		this.tarifaNormal = tarifaNormal;
	}

	/**
	 * Constructor con datos personales y credenciales de acceso. <br>
	 * <b>post</b>: Se crea una instancia de ClienteNormal delegando toda la información 
	 * a la clase Cliente, dejando la tarifa en su valor por defecto.
	 * @param nombre      Nombre completo.
	 * @param cedula      Documento de identidad.
	 * @param correo      Correo electrónico.
	 * @param telefono    Teléfono de contacto.
	 * @param metodoPago  Medio de pago.
	 * @param contrasenia Contraseña del sistema.
	 */
	public ClienteNormal(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String contrasenia) {
		super(nombre, cedula, correo, telefono, metodoPago, contrasenia);
	}

	/**
	 * Constructor orientado únicamente a datos personales básicos. <br>
	 * <b>post</b>: Se crea una instancia de ClienteNormal inicializando la información 
	 * heredada de la jerarquía de Persona.
	 * @param nombre   Nombre completo.
	 * @param cedula   Documento de identidad.
	 * @param correo   Correo electrónico.
	 * @param telefono Teléfono de contacto.
	 */
	public ClienteNormal(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);
	}

	/**
	 * Constructor orientado únicamente a credenciales de cuenta. <br>
	 * <b>post</b>: Se crea una instancia de ClienteNormal con los datos de acceso 
	 * definidos en la clase Cliente.
	 * @param metodoPago  Medio de pago.
	 * @param contrasenia Contraseña del sistema.
	 */
	public ClienteNormal(String metodoPago, String contrasenia) {
		super(metodoPago, contrasenia);
	}

	/**
	 * Obtiene la tarifa normal del cliente.
	 * 
	 * @return La tarifa asignada al cliente normal.
	 */
	public double getTarifaNormal() {
		return tarifaNormal;
	}

	/**
	 * Establece la tarifa normal del cliente.
	 * 
	 * @param tarifaNormal La nueva tarifa a asignar.
	 */
	public void setTarifaNormal(double tarifaNormal) {
		this.tarifaNormal = tarifaNormal;
	}

	/**
	 * Devuelve una representación en String de la entidad ClienteNormal.
	 * 
	 * @return Una cadena que incluye los datos del cliente general junto con la
	 *         tarifa normal.
	 */
	@Override
	public String toString() {
		return super.toString() + "Cliente normal \n Tarifa normal: " + tarifaNormal + ".";
	}

	/**
	 * Genera un código hash para la entidad ClienteNormal.
	 * 
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
	 * 
	 * @param obj El objeto con el cual se va a comparar.
	 * @return true si los objetos coinciden en atributos e identidad, false en caso
	 *         contrario.
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