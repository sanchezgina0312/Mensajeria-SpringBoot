package co.edu.unbosque.mensajeria.entity;

import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entidad abstracta que representa a un Cliente en el sistema.
 * <p>
 * Hereda de la clase base Usuario y define los atributos específicos de los
 * clientes, como su método de pago y tipo de pedido. Sirve como clase base para
 * tipos más específicos de clientes (Normal, Premium, Concurrente). Esta clase
 * está mapeada a la tabla "cliente" en la base de datos mediante JPA.
 * </p>
 *
 * @version 1.0
 */
@Entity
@Table(name = "cliente")
public abstract class Cliente extends Usuario {

	/** Método de pago preferido o utilizado por el cliente. */
	private String metodoPago;

	/** Contraseña de acceso al sistema para el cliente. */
	private String contrasenia;

	/**
	 * Constructor vacío. <br>
	 * <b>post</b>: Se crea una entidad Cliente con atributos heredados nulos o por
	 * defecto y los atributos propios sin inicializar. Requerido por JPA.
	 */
	public Cliente() {
		super();
	}

	/**
	 * Constructor con credenciales de acceso y pago. <br>
	 * <b>post</b>: Se crea una instancia de Cliente con el método de pago preferido
	 * y su contraseña de seguridad, dejando los datos personales para ser
	 * inicializados posteriormente. * @param metodoPago Sistema o medio de pago
	 * elegido por el cliente.
	 * 
	 * @param contrasenia Clave de seguridad para el acceso al sistema.
	 */
	public Cliente(String metodoPago, String contrasenia) {
		super();
		this.metodoPago = metodoPago;
		this.contrasenia = contrasenia;
	}

	/**
	 * Constructor con atributos heredados de Usuario. <br>
	 * <b>post</b>: Se crea una entidad Cliente inicializando únicamente sus datos
	 * personales y de contacto básicos.
	 * 
	 * @param nombre   El nombre del cliente.
	 * @param cedula   La cédula del cliente.
	 * @param correo   El correo electrónico del cliente.
	 * @param telefono El teléfono de contacto del cliente.
	 */
	public Cliente(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);
	}

	/**
	 * Constructor completo con datos personales y de cuenta. <br>
	 * <b>post</b>: Se crea una instancia de Cliente inicializando tanto la
	 * información básica heredada (nombre, cédula, correo, teléfono) como los
	 * atributos específicos de su cuenta de usuario. * @param nombre Nombre
	 * completo del cliente.
	 * 
	 * @param cedula      Documento de identidad único.
	 * @param correo      Dirección de correo electrónico.
	 * @param telefono    Número de contacto telefónico.
	 * @param metodoPago  Sistema o medio de pago elegido por el cliente.
	 * @param contrasenia Clave de seguridad para el acceso al sistema.
	 */
	public Cliente(String nombre, String cedula, String correo, String telefono, String metodoPago,
			String contrasenia) {
		super(nombre, cedula, correo, telefono);
		this.metodoPago = metodoPago;
		this.contrasenia = contrasenia;
	}

	/**
	 * Obtiene el método de pago del cliente.
	 * 
	 * @return El método de pago actual.
	 */
	public String getMetodoPago() {
		return metodoPago;
	}

	/**
	 * Establece el método de pago del cliente.
	 * 
	 * @param metodoPago El nuevo método de pago a asignar.
	 */
	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

	/**
	 * Obtiene la contraseña de acceso del cliente.
	 * 
	 * @return La contraseña almacenada.
	 */
	public String getContrasenia() {
		return contrasenia;
	}

	/**
	 * Establece la contraseña de acceso del cliente.
	 * 
	 * @param contrasenia La nueva contraseña a asignar.
	 */
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	/**
	 * Genera una representación textual detallada del cliente.
	 * 
	 * @return Cadena con la información del método de pago y credenciales.
	 */
	@Override
	public String toString() {
		return "Cliente:\n Método de Pago:" + metodoPago + "\n Contraseña:" + contrasenia + ".";
	}

	/**
	 * Calcula el código hash basado en los atributos del cliente y su clase base.
	 * 
	 * @return El valor del hash calculado.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(contrasenia, metodoPago);
		return result;
	}

	/**
	 * Compara este cliente con otro objeto para determinar su igualdad.
	 * 
	 * @param obj Objeto a comparar.
	 * @return true si los atributos coinciden con los de la clase base y los
	 *         propios, false de lo contrario.
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
		Cliente other = (Cliente) obj;
		return Objects.equals(contrasenia, other.contrasenia) && Objects.equals(metodoPago, other.metodoPago);
	}

}