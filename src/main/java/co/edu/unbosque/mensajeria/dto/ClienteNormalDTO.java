package co.edu.unbosque.mensajeria.dto;

import java.util.Objects;
import co.edu.unbosque.mensajeria.entity.Cliente;

/**
 * DTO (Data Transfer Object) para ClienteNormal.
 * <p>
 * Hereda de la clase base Cliente y añade una tarifa específica para los
 * clientes normales. Se utiliza para transferir datos de clientes estándar de
 * forma segura entre capas.
 * </p>
 *
 * @version 1.0
 */
public class ClienteNormalDTO extends Cliente {

	/** Tarifa aplicada al cliente normal (usualmente 0%). */
	private double tarifaNormal; // 0%

	/**
	 * Constructor vacío.
	 */
	public ClienteNormalDTO() {

	}

	/**
	 * Constructor con tarifa premium.
	 * 
	 * @param tarifaPremium Valor de la tarifa premium.
	 */
	public ClienteNormalDTO(double tarifaNormal) {
		super();
		this.tarifaNormal = tarifaNormal;
	}

	/**
	 * Constructor con credenciales y tarifa premium.
	 * 
	 * @param metodoPago    Medio de pago preferido.
	 * @param contrasenia   Contraseña del sistema.
	 * @param tarifaPremium Valor de la tarifa premium.
	 */
	public ClienteNormalDTO(String metodoPago, String contrasenia, double tarifaNormal) {
		super(metodoPago, contrasenia);
		this.tarifaNormal = tarifaNormal;
	}

	/**
	 * Constructor con datos personales y tarifa premium.
	 * 
	 * @param nombre        Nombre completo del cliente.
	 * @param cedula        Documento de identidad.
	 * @param correo        Dirección de correo electrónico.
	 * @param telefono      Número de contacto.
	 * @param tarifaPremium Valor de la tarifa premium.
	 */
	public ClienteNormalDTO(String nombre, String cedula, String correo, String telefono, double tarifaNormal) {
		super(nombre, cedula, correo, telefono);
		this.tarifaNormal = tarifaNormal;
	}

	/**
	 * Constructor completo con datos de identificación, contacto y tarifa.
	 * 
	 * @param nombre        Nombre completo del cliente.
	 * @param cedula        Documento de identidad.
	 * @param correo        Dirección de correo electrónico.
	 * @param telefono      Número de contacto.
	 * @param metodoPago    Medio de pago preferido.
	 * @param contrasenia   Contraseña del sistema.
	 * @param tarifaPremium Valor de la tarifa premium.
	 */
	public ClienteNormalDTO(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String contrasenia, double tarifaNormal) {
		super(nombre, cedula, correo, telefono, metodoPago, contrasenia);
		this.tarifaNormal = tarifaNormal;
	}

	/**
	 * Constructor con datos personales y credenciales.
	 * 
	 * @param nombre      Nombre completo del cliente.
	 * @param cedula      Documento de identidad.
	 * @param correo      Dirección de correo electrónico.
	 * @param telefono    Número de contacto.
	 * @param metodoPago  Medio de pago preferido.
	 * @param contrasenia Contraseña del sistema.
	 */
	public ClienteNormalDTO(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String contrasenia) {
		super(nombre, cedula, correo, telefono, metodoPago, contrasenia);
	}

	/**
	 * Constructor con datos de identificación y contacto.
	 * 
	 * @param nombre   Nombre completo del cliente.
	 * @param cedula   Documento de identidad.
	 * @param correo   Dirección de correo electrónico.
	 * @param telefono Número de contacto.
	 */
	public ClienteNormalDTO(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);
	}

	/**
	 * Constructor con credenciales de acceso.
	 * 
	 * @param metodoPago  Medio de pago preferido.
	 * @param contrasenia Contraseña del sistema.
	 */
	public ClienteNormalDTO(String metodoPago, String contrasenia) {
		super(metodoPago, contrasenia);
	}

	/**
	 * Obtiene la tarifa del cliente normal. * @return La tarifa asignada al cliente
	 * normal.
	 */
	public double getTarifaNormal() {
		return tarifaNormal;
	}

	/**
	 * Establece la tarifa del cliente normal. * @param tarifaNormal La nueva tarifa
	 * a asignar.
	 */
	public void setTarifaNormal(double tarifaNormal) {
		this.tarifaNormal = tarifaNormal;
	}

	/**
	 * Devuelve una representación en String del DTO del cliente normal. * @return
	 * Una cadena que incluye los datos del cliente general junto con la tarifa
	 * normal.
	 */
	@Override
	public String toString() {
		return super.toString() + "\n Cliente normal:\n Tarifa normal:" + tarifaNormal + ".";
	}

	/**
	 * Genera un código hash para el objeto DTO. * @return El código hash basado en
	 * los atributos del padre y la tarifa normal.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(tarifaNormal);
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
		ClienteNormalDTO other = (ClienteNormalDTO) obj;
		return Double.doubleToLongBits(tarifaNormal) == Double.doubleToLongBits(other.tarifaNormal);
	}

}