package co.edu.unbosque.mensajeria.dto;

import java.util.Objects;
import co.edu.unbosque.mensajeria.entity.Cliente;

/**
 * DTO (Data Transfer Object) para ClienteConcurrente.
 * <p>
 * Hereda de la clase base Cliente y añade una tarifa específica para los
 * clientes concurrentes o recurrentes. Se utiliza para transferir datos de
 * clientes concurrentes de forma segura.
 * </p>
 *
 * @version 1.0
 */
public class ClienteConcurrenteDTO extends Cliente {

	/** Tarifa aplicada al cliente concurrente. Por defecto inicializada en 0.5. */
	private double tarifaConcurrente = 0.5;

	/**
	 * Constructor vacío. <br>
	 * <b>post</b>: Se crea un objeto DTO de ClienteConcurrente con sus atributos 
	 * inicializados por defecto. Útil para procesos de serialización y frameworks de mapeo.
	 */
	public ClienteConcurrenteDTO() {

	}

	/**
	 * Constructor con tarifa de recurrencia. <br>
	 * <b>post</b>: Se crea un DTO de ClienteConcurrente inicializando el valor 
	 * de la tarifa específica para la transferencia de este dato.
	 * @param tarifaConcurrente Valor de la tarifa por frecuencia.
	 */
	public ClienteConcurrenteDTO(double tarifaConcurrente) {
		super();
		this.tarifaConcurrente = tarifaConcurrente;
	}

	/**
	 * Constructor con credenciales y tarifa de recurrencia. <br>
	 * <b>post</b>: Se crea un DTO que encapsula los datos de acceso y la 
	 * tarifa asignada para su transporte entre capas.
	 * @param metodoPago        Sistema de pago registrado.
	 * @param contrasenia       Clave de seguridad.
	 * @param tarifaConcurrente Valor de la tarifa por frecuencia.
	 */
	public ClienteConcurrenteDTO(String metodoPago, String contrasenia, double tarifaConcurrente) {
		super(metodoPago, contrasenia);
		this.tarifaConcurrente = tarifaConcurrente;
	}

	/**
	 * Constructor con datos personales y tarifa de recurrencia. <br>
	 * <b>post</b>: Se crea un DTO vinculando la información de contacto personal 
	 * con el valor de la tarifa de cliente frecuente.
	 * @param nombre            Nombre completo del cliente.
	 * @param cedula            Documento de identidad.
	 * @param correo            Dirección de correo electrónico.
	 * @param telefono          Número de contacto.
	 * @param tarifaConcurrente Valor de la tarifa por frecuencia.
	 */
	public ClienteConcurrenteDTO(String nombre, String cedula, String correo, String telefono,
			double tarifaConcurrente) {
		super(nombre, cedula, correo, telefono);
		this.tarifaConcurrente = tarifaConcurrente;
	}

	/**
	 * Constructor completo de transferencia. <br>
	 * <b>post</b>: Se crea una instancia DTO con la totalidad de la información 
	 * personal, credenciales y tarifa para una transferencia de datos exhaustiva.
	 * @param nombre            Nombre completo.
	 * @param cedula            Cédula de ciudadanía.
	 * @param correo            Correo electrónico.
	 * @param telefono          Teléfono de contacto.
	 * @param metodoPago        Medio de pago registrado.
	 * @param contrasenia       Contraseña de la cuenta.
	 * @param tarifaConcurrente Valor de la tarifa por frecuencia.
	 */
	public ClienteConcurrenteDTO(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String contrasenia, double tarifaConcurrente) {
		super(nombre, cedula, correo, telefono, metodoPago, contrasenia);
		this.tarifaConcurrente = tarifaConcurrente;
	}

	/**
	 * Constructor con datos personales y credenciales. <br>
	 * <b>post</b>: Se crea un DTO que agrupa la información básica y de acceso, 
	 * delegando la estructura a la jerarquía superior de DTOs.
	 * @param nombre      Nombre completo.
	 * @param cedula      Documento de identidad.
	 * @param correo      Correo electrónico.
	 * @param telefono    Teléfono de contacto.
	 * @param metodoPago  Medio de pago.
	 * @param contrasenia Contraseña del sistema.
	 */
	public ClienteConcurrenteDTO(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String contrasenia) {
		super(nombre, cedula, correo, telefono, metodoPago, contrasenia);
	}

	/**
	 * Constructor orientado a transferencia de datos personales. <br>
	 * <b>post</b>: Se crea un DTO inicializando únicamente la información de 
	 * contacto heredada para su envío entre servicios.
	 * @param nombre   Nombre completo.
	 * @param cedula   Documento de identidad.
	 * @param correo   Correo electrónico.
	 * @param telefono Teléfono de contacto.
	 */
	public ClienteConcurrenteDTO(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);
	}

	/**
	 * Constructor orientado a transferencia de credenciales. <br>
	 * <b>post</b>: Se crea un DTO con los datos de acceso requeridos para 
	 * validaciones o procesos de autenticación.
	 * @param metodoPago  Medio de pago preferido.
	 * @param contrasenia Contraseña del sistema.
	 */
	public ClienteConcurrenteDTO(String metodoPago, String contrasenia) {
		super(metodoPago, contrasenia);
	}

	/**
	 * Obtiene la tarifa concurrente del cliente. * @return La tarifa asignada al
	 * cliente concurrente.
	 */
	public double getTarifaConcurrente() {
		return tarifaConcurrente;
	}

	/**
	 * Establece la tarifa concurrente del cliente. * @param tarifaConcurrente La
	 * nueva tarifa a asignar.
	 */
	public void setTarifaConcurrente(double tarifaConcurrente) {
		this.tarifaConcurrente = tarifaConcurrente;
	}

	/**
	 * Devuelve una representación en String del DTO del cliente concurrente.
	 * * @return Una cadena que incluye los datos del cliente general junto con la
	 * tarifa de concurrencia.
	 */
	@Override
	public String toString() {
		return super.toString() + "\n Cliente concurrente:\n Tarifa concurrente:" + tarifaConcurrente + ".";
	}

	/**
	 * Genera un código hash para el objeto DTO. * @return El código hash basado en
	 * los atributos del padre y la tarifa concurrente.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(tarifaConcurrente);
		return result;
	}

	/**
	 * Compara este DTO con otro objeto para verificar si son iguales. * @param obj
	 * El objeto con el cual se va a comparar.
	 * 
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
		ClienteConcurrenteDTO other = (ClienteConcurrenteDTO) obj;
		return Double.doubleToLongBits(tarifaConcurrente) == Double.doubleToLongBits(other.tarifaConcurrente);
	}

}