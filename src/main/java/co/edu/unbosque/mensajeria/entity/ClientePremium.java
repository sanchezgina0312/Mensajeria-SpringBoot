package co.edu.unbosque.mensajeria.entity;

import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entidad que representa a un Cliente Premium en el sistema.
 * <p>
 * Hereda de la clase base Cliente y añade una tarifa específica aplicada a los
 * clientes que cuentan con una membresía o estatus premium. Esta clase está
 * mapeada a la tabla "clientepremium" en la base de datos mediante JPA.
 * </p>
 *
 * @version 1.0
 */
@Entity
@Table(name = "clientepremium")
public class ClientePremium extends Cliente {

	/**
	 * Tarifa especial aplicada al cliente premium. Inicializada por defecto en
	 * 0.15.
	 */
	private double tarifaPremium = 0.15;

	/**
	 * Constructor vacío. <br>
	 * <b>post</b>: Se crea una instancia de ClientePremium con atributos en sus
	 * valores por defecto. Requerido para el funcionamiento de JPA y procesos de
	 * persistencia.
	 */
	public ClientePremium() {

	}

	/**
	 * Constructor con tarifa preferencial. <br>
	 * <b>post</b>: Se crea una instancia de ClientePremium inicializando el valor
	 * de la tarifa especial aplicada a los miembros del programa premium.
	 * 
	 * @param tarifaPremium Valor de la tarifa preferencial para el cliente.
	 */
	public ClientePremium(double tarifaPremium) {
		super();
		this.tarifaPremium = tarifaPremium;
	}

	/**
	 * Constructor con credenciales y tarifa preferencial. <br>
	 * <b>post</b>: Se crea una instancia de ClientePremium con los datos de cuenta
	 * y el beneficio de tarifa premium asignado.
	 * 
	 * @param metodoPago    Sistema de pago elegido.
	 * @param contrasenia   Clave de acceso.
	 * @param tarifaPremium Valor de la tarifa preferencial.
	 */
	public ClientePremium(String metodoPago, String contrasenia, double tarifaPremium) {
		super(metodoPago, contrasenia);
		this.tarifaPremium = tarifaPremium;
	}

	/**
	 * Constructor con datos personales y tarifa preferencial. <br>
	 * <b>post</b>: Se crea una instancia de ClientePremium vinculando la
	 * información de contacto personal con su respectiva tarifa de beneficios.
	 * 
	 * @param nombre        Nombre del cliente.
	 * @param cedula        Documento de identidad.
	 * @param correo        Email de contacto.
	 * @param telefono      Número telefónico.
	 * @param tarifaPremium Valor de la tarifa preferencial.
	 */
	public ClientePremium(String nombre, String cedula, String correo, String telefono, double tarifaPremium) {
		super(nombre, cedula, correo, telefono);
		this.tarifaPremium = tarifaPremium;
	}

	/**
	 * Constructor completo con todos los parámetros de negocio. <br>
	 * <b>post</b>: Se crea una instancia de ClientePremium con la totalidad de
	 * datos personales, credenciales de acceso y configuración de tarifa
	 * preferencial.
	 * 
	 * @param nombre        Nombre completo.
	 * @param cedula        Cédula de ciudadanía.
	 * @param correo        Correo electrónico.
	 * @param telefono      Teléfono de contacto.
	 * @param metodoPago    Medio de pago.
	 * @param contrasenia   Contraseña del sistema.
	 * @param tarifaPremium Valor de la tarifa preferencial.
	 */
	public ClientePremium(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String contrasenia, double tarifaPremium) {
		super(nombre, cedula, correo, telefono, metodoPago, contrasenia);
		this.tarifaPremium = tarifaPremium;
	}

	/**
	 * Constructor con datos personales y credenciales de acceso. <br>
	 * <b>post</b>: Se crea una instancia de ClientePremium delegando la información
	 * a la clase base y dejando la tarifa premium para ser definida posteriormente.
	 * 
	 * @param nombre      Nombre completo.
	 * @param cedula      Documento de identidad.
	 * @param correo      Correo electrónico.
	 * @param telefono    Teléfono de contacto.
	 * @param metodoPago  Medio de pago.
	 * @param contrasenia Contraseña del sistema.
	 */
	public ClientePremium(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String contrasenia) {
		super(nombre, cedula, correo, telefono, metodoPago, contrasenia);
	}

	/**
	 * Constructor orientado únicamente a datos personales. <br>
	 * <b>post</b>: Se crea una instancia de ClientePremium inicializando la
	 * información de contacto heredada de la jerarquía de Persona.
	 * 
	 * @param nombre   Nombre completo.
	 * @param cedula   Documento de identidad.
	 * @param correo   Correo electrónico.
	 * @param telefono Teléfono de contacto.
	 */
	public ClientePremium(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);
	}

	/**
	 * Constructor orientado únicamente a credenciales de cuenta. <br>
	 * <b>post</b>: Se crea una instancia de ClientePremium con los datos de acceso
	 * requeridos para la plataforma.
	 * 
	 * @param metodoPago  Medio de pago.
	 * @param contrasenia Contraseña del sistema.
	 */
	public ClientePremium(String metodoPago, String contrasenia) {
		super(metodoPago, contrasenia);
	}

	/**
	 * Obtiene la tarifa premium del cliente.
	 * 
	 * @return La tarifa asignada al cliente premium.
	 */
	public double getTarifaPremium() {
		return tarifaPremium;
	}

	/**
	 * Establece la tarifa premium del cliente.
	 * 
	 * @param tarifaPremium La nueva tarifa a asignar.
	 */
	public void setTarifaPremium(double tarifaPremium) {
		this.tarifaPremium = tarifaPremium;
	}

	/**
	 * Devuelve una representación en String de la entidad ClientePremium.
	 * 
	 * @return Una cadena que incluye los datos del cliente general junto con la
	 *         tarifa premium.
	 */
	@Override
	public String toString() {
		return super.toString() + "Cliente premium \n Tarifa premium: " + tarifaPremium + ".";
	}

	/**
	 * Genera un código hash para la entidad ClientePremium.
	 * 
	 * @return El código hash basado en los atributos del padre y la tarifa premium.
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
	 * 
	 * @param obj El objeto con el cual se va a comparar.
	 * @return true si los objetos son iguales en atributos e identidad, false en
	 *         caso contrario.
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