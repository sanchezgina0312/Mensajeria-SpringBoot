package co.edu.unbosque.mensajeria.entity;

import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entidad que representa a un Cliente Concurrente (frecuente) en el sistema.
 * <p>
 * Hereda de la clase base Cliente y añade una tarifa específica aplicada a los
 * clientes que realizan pedidos de forma concurrente o recurrente. Esta clase
 * está mapeada a la tabla "clienteconcurrente" en la base de datos mediante
 * JPA.
 * </p>
 *
 * @version 1.0
 */
@Entity
@Table(name = "clienteconcurrente")
public class ClienteConcurrente extends Cliente {

	/**
	 * Tarifa especial aplicada al cliente concurrente. Inicializada por defecto en
	 * 0.5.
	 */
	private double tarifaConcurrente = 0.5;

	/**
	 * Constructor vacío. <br>
	 * <b>post</b>: Se crea una instancia de ClienteConcurrente con atributos en sus
	 * valores por defecto. Requerido para la persistencia de datos mediante JPA.
	 */
	public ClienteConcurrente() {

	}

	/**
	 * Constructor con tarifa de recurrencia. <br>
	 * <b>post</b>: Se crea una instancia de ClienteConcurrente inicializando el
	 * valor de la tarifa aplicada a clientes con alta frecuencia de envíos.
	 * 
	 * @param tarifaConcurrente Valor de la tarifa especial por volumen de uso.
	 */
	public ClienteConcurrente(double tarifaConcurrente) {
		super();
		this.tarifaConcurrente = tarifaConcurrente;
	}

	/**
	 * Constructor con credenciales y tarifa de recurrencia. <br>
	 * <b>post</b>: Se crea una instancia de ClienteConcurrente con los datos de
	 * acceso y la tarifa configurada según su perfil de frecuencia.
	 * 
	 * @param metodoPago        Sistema de pago preferido.
	 * @param contrasenia       Clave de seguridad para el sistema.
	 * @param tarifaConcurrente Valor de la tarifa por frecuencia.
	 */
	public ClienteConcurrente(String metodoPago, String contrasenia, double tarifaConcurrente) {
		super(metodoPago, contrasenia);
		this.tarifaConcurrente = tarifaConcurrente;
	}

	/**
	 * Constructor con datos personales y tarifa de recurrencia. <br>
	 * <b>post</b>: Se crea una instancia de ClienteConcurrente vinculando la
	 * información de contacto con su tarifa de cliente frecuente.
	 * 
	 * @param nombre            Nombre completo del cliente.
	 * @param cedula            Documento de identidad.
	 * @param correo            Dirección de correo electrónico.
	 * @param telefono          Número de contacto.
	 * @param tarifaConcurrente Valor de la tarifa por frecuencia.
	 */
	public ClienteConcurrente(String nombre, String cedula, String correo, String telefono, double tarifaConcurrente) {
		super(nombre, cedula, correo, telefono);
		this.tarifaConcurrente = tarifaConcurrente;
	}

	/**
	 * Constructor completo con todos los parámetros de negocio. <br>
	 * <b>post</b>: Se crea una instancia de ClienteConcurrente con la totalidad de
	 * la información personal, credenciales de cuenta y configuración de tarifa.
	 * 
	 * @param nombre            Nombre completo.
	 * @param cedula            Cédula de ciudadanía.
	 * @param correo            Correo electrónico de contacto.
	 * @param telefono          Teléfono de contacto.
	 * @param metodoPago        Medio de pago registrado.
	 * @param contrasenia       Contraseña de la cuenta.
	 * @param tarifaConcurrente Valor de la tarifa por frecuencia.
	 */
	public ClienteConcurrente(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String contrasenia, double tarifaConcurrente) {
		super(nombre, cedula, correo, telefono, metodoPago, contrasenia);
		this.tarifaConcurrente = tarifaConcurrente;
	}

	/**
	 * Constructor con datos personales y credenciales de acceso. <br>
	 * <b>post</b>: Se crea una instancia de ClienteConcurrente delegando la
	 * información básica y de cuenta a la clase superior, omitiendo la tarifa
	 * específica.
	 * 
	 * @param nombre      Nombre completo.
	 * @param cedula      Documento de identidad.
	 * @param correo      Correo electrónico.
	 * @param telefono    Teléfono de contacto.
	 * @param metodoPago  Medio de pago.
	 * @param contrasenia Contraseña del sistema.
	 */
	public ClienteConcurrente(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String contrasenia) {
		super(nombre, cedula, correo, telefono, metodoPago, contrasenia);
	}

	/**
	 * Constructor enfocado en datos personales básicos. <br>
	 * <b>post</b>: Se crea una instancia de ClienteConcurrente inicializando
	 * únicamente la información de contacto heredada de la clase Persona.
	 * 
	 * @param nombre   Nombre completo.
	 * @param cedula   Documento de identidad.
	 * @param correo   Correo electrónico.
	 * @param telefono Teléfono de contacto.
	 */
	public ClienteConcurrente(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);
	}

	/**
	 * Constructor enfocado en credenciales de cuenta de usuario. <br>
	 * <b>post</b>: Se crea una instancia de ClienteConcurrente con la información
	 * de acceso requerida, sin datos personales adicionales.
	 * 
	 * @param metodoPago  Medio de pago preferido.
	 * @param contrasenia Contraseña del sistema.
	 */
	public ClienteConcurrente(String metodoPago, String contrasenia) {
		super(metodoPago, contrasenia);
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
	 * @param tarifaConcurrente La nueva tarifa a asignar.
	 */
	public void setTarifaConcurrente(double tarifaConcurrente) {
		this.tarifaConcurrente = tarifaConcurrente;
	}

	/**
	 * Devuelve una representación en String de la entidad ClienteConcurrente.
	 * 
	 * @return Una cadena que incluye los datos del cliente general junto con la
	 *         tarifa de concurrencia.
	 */
	@Override
	public String toString() {
		return super.toString() + "Cliente concurrente \n Tarifa concurrente:" + tarifaConcurrente + ".";
	}

	/**
	 * Genera un código hash para la entidad.
	 * 
	 * @return El código hash basado en los atributos heredados y la tarifa
	 *         concurrente.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(tarifaConcurrente);
		return result;
	}

	/**
	 * Compara esta entidad con otro objeto para verificar igualdad funcional.
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
		ClienteConcurrente other = (ClienteConcurrente) obj;
		return Double.doubleToLongBits(tarifaConcurrente) == Double.doubleToLongBits(other.tarifaConcurrente);
	}
}