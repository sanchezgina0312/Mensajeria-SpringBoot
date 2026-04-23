package co.edu.unbosque.mensajeria.entity;

import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entidad que representa a un Cliente Premium en el sistema.
 * <p>
 * Hereda de la clase base Cliente y añade una tarifa específica aplicada a 
 * los clientes que cuentan con una membresía o estatus premium. Esta clase 
 * está mapeada a la tabla "clientepremium" en la base de datos mediante JPA.
 * </p>
 *
 * @version 1.0
 */
@Entity
@Table(name = "clientepremium")
public class ClientePremium extends Cliente {

	/** Tarifa especial aplicada al cliente premium. Inicializada por defecto en 0.15. */
	private double tarifaPremium = 0.15;

	/**
	 * Constructor vacío. <br>
	 * <b>post</b>: Se crea una entidad ClientePremium con atributos heredados nulos 
	 * o por defecto y una tarifa premium predeterminada de 0.15. Requerido por JPA.
	 */
	public ClientePremium() {
		super();
	}

	/**
	 * Constructor orientado a seguridad, logística y tarifa diferencial.
	 * @param metodoPago   Medio de pago preferido.
	 * @param tipoPedido   Tipo de servicio solicitado.
	 * @param contrasenia  Clave de acceso al sistema.
	 * @param tarifaPremium Valor de la tarifa premium asignada.
	 */
	public ClientePremium(String metodoPago, String tipoPedido, String contrasenia, double tarifaPremium) {
		super(metodoPago, tipoPedido, contrasenia);
		this.tarifaPremium = tarifaPremium;
	}

	/**
	 * Constructor para datos personales e identificación con tarifa premium.
	 * @param nombre        Nombre completo del cliente.
	 * @param cedula        Documento de identidad.
	 * @param correo        Dirección de correo electrónico.
	 * @param telefono      Número telefónico de contacto.
	 * @param tarifaPremium Valor de la tarifa premium.
	 */
	public ClientePremium(String nombre, String cedula, String correo, String telefono, double tarifaPremium) {
		super(nombre, cedula, correo, telefono);
		this.tarifaPremium = tarifaPremium;
	}

	/**
	 * Constructor completo con datos personales, logísticos y tarifa diferencial.
	 * @param nombre        Nombre del cliente.
	 * @param cedula        Cédula de ciudadanía.
	 * @param correo        E-mail de contacto.
	 * @param telefono      Teléfono.
	 * @param metodoPago    Forma de pago.
	 * @param tipoPedido    Categoría de pedido.
	 * @param tarifaPremium Tarifa especial aplicada.
	 */
	public ClientePremium(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String tipoPedido, double tarifaPremium) {
		super(nombre, cedula, correo, telefono, metodoPago, tipoPedido);
		this.tarifaPremium = tarifaPremium;
	}

	/**
	 * Constructor que inicializa únicamente la tarifa premium.
	 * @param tarifaPremium Valor decimal de la tarifa.
	 */
	public ClientePremium(double tarifaPremium) {
		super();
		this.tarifaPremium = tarifaPremium;
	}

	/**
	 * Constructor para datos personales y de logística sin tarifa personalizada.
	 * @param nombre     Nombre completo.
	 * @param cedula     Identificación.
	 * @param correo     E-mail.
	 * @param telefono   Teléfono de contacto.
	 * @param metodoPago Método de pago.
	 * @param tipoPedido Tipo de pedido.
	 */
	public ClientePremium(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String tipoPedido) {
		super(nombre, cedula, correo, telefono, metodoPago, tipoPedido);
	}

	/**
	 * Constructor simplificado para registro de identidad y contacto.
	 * @param nombre   Nombre del cliente.
	 * @param cedula   Cédula o ID.
	 * @param correo   Correo electrónico.
	 * @param telefono Número de contacto.
	 */
	public ClientePremium(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);
	}

	/**
	 * Constructor para gestión de credenciales y preferencias logísticas.
	 * @param metodoPago  Medio de pago.
	 * @param tipoPedido  Clasificación del pedido.
	 * @param contrasenia Contraseña del usuario.
	 */
	public ClientePremium(String metodoPago, String tipoPedido, String contrasenia) {
		super(metodoPago, tipoPedido, contrasenia);
	}

	/**
	 * Obtiene la tarifa premium del cliente.
	 * @return La tarifa asignada al cliente premium.
	 */
	public double getTarifaPremium() {
		return tarifaPremium;
	}

	/**
	 * Establece la tarifa premium del cliente.
	 * @param tarifaPremium La nueva tarifa a asignar.
	 */
	public void setTarifaPremium(double tarifaPremium) {
		this.tarifaPremium = tarifaPremium;
	}

	/**
	 * Devuelve una representación en String de la entidad ClientePremium.
	 * @return Una cadena que incluye los datos del cliente general junto con la tarifa premium.
	 */
	@Override
	public String toString() {
		return super.toString() + "Cliente premium \n Tarifa premium: " + tarifaPremium + ".";
	}

	/**
	 * Genera un código hash para la entidad ClientePremium.
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
		ClientePremium other = (ClientePremium) obj;
		return Double.doubleToLongBits(tarifaPremium) == Double.doubleToLongBits(other.tarifaPremium);
	}
}