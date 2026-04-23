package co.edu.unbosque.mensajeria.entity;

import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entidad abstracta que representa a un Cliente en el sistema.
 * <p>
 * Hereda de la clase base Usuario y define los atributos específicos
 * de los clientes, como su método de pago y tipo de pedido. Sirve como clase 
 * base para tipos más específicos de clientes (Normal, Premium, Concurrente).
 * Esta clase está mapeada a la tabla "cliente" en la base de datos mediante JPA.
 * </p>
 *
 * @version 1.0
 */
@Entity
@Table(name = "cliente")
public abstract class Cliente extends Usuario {

	/** Método de pago preferido o utilizado por el cliente. */
	private String metodoPago;
	
	/** Tipo de pedido que realiza el cliente. */
	private String tipoPedido;
	
	/** Contraseña de acceso al sistema para el cliente. */
	private String contrasenia;

	/**
	 * Constructor vacío. <br>
	 * <b>post</b>: Se crea una entidad Cliente con atributos heredados
	 * nulos o por defecto y los atributos propios sin inicializar. Requerido por JPA.
	 */
	public Cliente() {
		super();
	}

	/**
	 * Constructor con información de contacto y atributos de negocio. <br>
	 * <b>post</b>: Se crea una entidad Cliente con todos sus datos personales 
	 * y preferencias de servicio inicializados.
	 * @param nombre     El nombre del cliente.
	 * @param cedula     La cédula del cliente.
	 * @param correo     El correo electrónico del cliente.
	 * @param telefono   El teléfono de contacto del cliente.
	 * @param metodoPago El método de pago preferido por el cliente.
	 * @param tipoPedido El tipo de pedido asociado al cliente.
	 */
	public Cliente(String nombre, String cedula, String correo, String telefono, String metodoPago, String tipoPedido) {
		super(nombre, cedula, correo, telefono);
		this.metodoPago = metodoPago;
		this.tipoPedido = tipoPedido;
	}

	/**
	 * Constructor con atributos heredados de Usuario. <br>
	 * <b>post</b>: Se crea una entidad Cliente inicializando únicamente sus 
	 * datos personales y de contacto básicos.
	 * @param nombre   El nombre del cliente.
	 * @param cedula   La cédula del cliente.
	 * @param correo   El correo electrónico del cliente.
	 * @param telefono El teléfono de contacto del cliente.
	 */
	public Cliente(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);
	}

	/**
	 * Constructor orientado a credenciales y logística. <br>
	 * <b>post</b>: Se inicializan las preferencias de pago, tipo de pedido y seguridad.
	 * @param metodoPago  El método de pago preferido por el cliente.
	 * @param tipoPedido  El tipo de pedido asociado al cliente.
	 * @param contrasenia La contraseña de acceso.
	 */
	public Cliente(String metodoPago, String tipoPedido, String contrasenia) {
		super();
		this.metodoPago = metodoPago;
		this.tipoPedido = tipoPedido;
		this.contrasenia = contrasenia;
	}

	/**
	 * Obtiene el método de pago del cliente.
	 * @return El método de pago actual.
	 */
	public String getMetodoPago() {
		return metodoPago;
	}

	/**
	 * Establece el método de pago del cliente.
	 * @param metodoPago El nuevo método de pago a asignar.
	 */
	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

	/**
	 * Obtiene el tipo de pedido asociado al cliente.
	 * @return El tipo de pedido actual.
	 */
	public String getTipoPedido() {
		return tipoPedido;
	}

	/**
	 * Establece el tipo de pedido asociado al cliente.
	 * @param tipoPedido El nuevo tipo de pedido a asignar.
	 */
	public void setTipoPedido(String tipoPedido) {
		this.tipoPedido = tipoPedido;
	}

	/**
	 * Obtiene la contraseña de acceso del cliente.
	 * @return La contraseña almacenada.
	 */
	public String getContrasenia() {
		return contrasenia;
	}

	/**
	 * Establece la contraseña de acceso del cliente.
	 * @param contrasenia La nueva contraseña a asignar.
	 */
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	/**
	 * Genera un código hash para la entidad Cliente.
	 * @return El código hash basado en el método de pago y el tipo de pedido.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(metodoPago, tipoPedido);
	}

	/**
	 * Devuelve una representación en String de la entidad Cliente.
	 * @return Una cadena con los datos de pago, pedido y contraseña.
	 */
	@Override
	public String toString() {
		return "Cliente [metodoPago=" + metodoPago + ", tipoPedido=" + tipoPedido + ", contrasenia=" + contrasenia
				+ "]";
	}

	/**
	 * Compara esta entidad con otro objeto para verificar si son iguales.
	 * @param obj El objeto con el cual se va a comparar.
	 * @return true si los objetos comparten el mismo método de pago y tipo de pedido.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(metodoPago, other.metodoPago) && Objects.equals(tipoPedido, other.tipoPedido);
	}
}