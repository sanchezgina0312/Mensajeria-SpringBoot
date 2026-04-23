package co.edu.unbosque.mensajeria.dto;

import java.util.Objects;
import co.edu.unbosque.mensajeria.entity.Cliente;

/**
 * DTO (Data Transfer Object) para ClientePremium.
 * <p>
 * Hereda de la clase base Cliente y añade una tarifa específica para los
 * clientes premium. Se utiliza para transferir datos de clientes con 
 * membresía premium de forma segura entre capas.
 * </p>
 *
 * @version 1.0
 */
public class ClientePremiumDTO extends Cliente {
	
	/** Tarifa aplicada al cliente premium. Por defecto inicializada en 0.15. */
	private double tarifaPremium = 0.15;

	/**
	 * Constructor vacío. <br>
	 * <b>post</b>: Se crea un DTO ClientePremium con atributos heredados
	 * nulos o por defecto y una tarifa premium predeterminada de 0.15.
	 */
	public ClientePremiumDTO() {
		super();
	}

	/**
	 * Constructor completo con datos de identificación, contacto, logística y tarifa.
	 * * @param nombre            Nombre completo del cliente.
	 * @param cedula            Documento de identidad.
	 * @param correo            Dirección de correo electrónico.
	 * @param telefono          Número de contacto.
	 * @param metodoPago        Medio de pago preferido.
	 * @param tipoPedido        Categoría del servicio solicitado.
	 * @param tarifaPremium     Valor de la tarifa premium personalizada.
	 */
	public ClientePremiumDTO(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String tipoPedido, double tarifaPremium) {
		super(nombre, cedula, correo, telefono, metodoPago, tipoPedido);
		this.tarifaPremium = tarifaPremium;
	}

	/**
	 * Constructor que inicializa únicamente la tarifa premium del cliente.
	 * * @param tarifaPremium Valor decimal de la tarifa a asignar.
	 */
	public ClientePremiumDTO(double tarifaPremium) {
		super();
		this.tarifaPremium = tarifaPremium;
	}

	/**
	 * Constructor para datos básicos de contacto e identificación junto con la tarifa.
	 * * @param nombre            Nombre del cliente.
	 * @param cedula            Cédula de ciudadanía.
	 * @param correo            E-mail de contacto.
	 * @param telefono          Número telefónico.
	 * @param tarifaPremium     Valor de la tarifa premium.
	 */
	public ClientePremiumDTO(String nombre, String cedula, String correo, String telefono, double tarifaPremium) {
		super(nombre, cedula, correo, telefono);
		this.tarifaPremium = tarifaPremium;
	}

	/**
	 * Constructor enfocado en seguridad y preferencias de pago con tarifa diferencial.
	 * * @param metodoPago        Medio de pago.
	 * @param tipoPedido        Tipo de servicio.
	 * @param contrasenia       Clave de acceso al sistema.
	 * @param tarifaPremium     Tarifa especial asignada.
	 */
	public ClientePremiumDTO(String metodoPago, String tipoPedido, String contrasenia, double tarifaPremium) {
		super(metodoPago, tipoPedido, contrasenia);
		this.tarifaPremium = tarifaPremium;
	}

	/**
	 * Constructor para datos logísticos y personales sin tarifa personalizada.
	 * * @param nombre     Nombre completo.
	 * @param cedula     Identificación.
	 * @param correo     E-mail.
	 * @param telefono   Teléfono.
	 * @param metodoPago Método de pago.
	 * @param tipoPedido Tipo de pedido.
	 */
	public ClientePremiumDTO(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String tipoPedido) {
		super(nombre, cedula, correo, telefono, metodoPago, tipoPedido);
	}

	/**
	 * Constructor simplificado para registro rápido de identidad y contacto.
	 * * @param nombre   Nombre del cliente.
	 * @param cedula   Identificación oficial.
	 * @param correo   Correo electrónico.
	 * @param telefono Número de contacto.
	 */
	public ClientePremiumDTO(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);
	}

	/**
	 * Constructor para gestión de credenciales y preferencias de servicio.
	 * * @param metodoPago  Medio de pago preferido.
	 * @param tipoPedido  Tipo de pedido habitual.
	 * @param contrasenia Contraseña de seguridad.
	 */
	public ClientePremiumDTO(String metodoPago, String tipoPedido, String contrasenia) {
		super(metodoPago, tipoPedido, contrasenia);
	}

	/**
	 * Obtiene la tarifa del cliente premium.
	 * * @return La tarifa asignada al cliente premium.
	 */
	public double getTarifaPremium() {
		return tarifaPremium;
	}

	/**
	 * Establece la tarifa del cliente premium.
	 * * @param tarifaPremium La nueva tarifa a asignar.
	 */
	public void setTarifaPremium(double tarifaPremium) {
		this.tarifaPremium = tarifaPremium;
	}

	/**
	 * Devuelve una representación en String del DTO del cliente premium.
	 * * @return Una cadena que incluye los datos del cliente general junto con la tarifa premium.
	 */
	@Override
	public String toString() {
		return super.toString() + "\n Cliente Premium:\n Tarifa premium:" + tarifaPremium + ".";
	}

	/**
	 * Genera un código hash para el objeto DTO.
	 * * @return El código hash basado en los atributos del padre y la tarifa premium.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(tarifaPremium);
		return result;
	}

	/**
	 * Compara este DTO con otro objeto para verificar si son iguales.
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
		ClientePremiumDTO other = (ClientePremiumDTO) obj;
		return Double.doubleToLongBits(tarifaPremium) == Double.doubleToLongBits(other.tarifaPremium);
	}

}