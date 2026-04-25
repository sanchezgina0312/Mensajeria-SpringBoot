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

	/**
	 * Constructor vacío. <br>
	 * <b>post</b>: Se crea una entidad Cliente con atributos heredados
	 * nulos o por defecto y los atributos propios sin inicializar. Requerido por JPA.
	 */
	public Cliente() {
		
	}

	/**
	 * Constructor con método de pago y tipo de pedido. <br>
	 * <b>post</b>: Se crea una entidad Cliente con su método de pago y tipo de 
	 * pedido establecidos, manteniendo los atributos de Usuario nulos.
	 * * @param metodoPago El método de pago preferido por el cliente.
	 * @param tipoPedido El tipo de pedido asociado al cliente.
	 */
	public Cliente(String metodoPago, String tipoPedido) {
		super();
		this.metodoPago = metodoPago;
		this.tipoPedido = tipoPedido;
	}

	/**
	 * Constructor completo con atributos heredados y propios. <br>
	 * <b>post</b>: Se crea una entidad Cliente con todos los atributos
	 * (heredados de Usuario y específicos de Cliente) inicializados.
	 * * @param nombre     El nombre del cliente.
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
	 * * @param nombre   El nombre del cliente.
	 * @param cedula   La cédula del cliente.
	 * @param correo   El correo electrónico del cliente.
	 * @param telefono El teléfono de contacto del cliente.
	 */
	public Cliente(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);

	}

	/**
	 * Obtiene el método de pago del cliente.
	 * * @return El método de pago.
	 */
	public String getMetodoPago() {
		return metodoPago;
	}

	/**
	 * Establece el método de pago del cliente.
	 * * @param metodoPago El nuevo método de pago a asignar.
	 */
	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

	/**
	 * Obtiene el tipo de pedido asociado al cliente.
	 * * @return El tipo de pedido.
	 */
	public String getTipoPedido() {
		return tipoPedido;
	}

	/**
	 * Establece el tipo de pedido asociado al cliente.
	 * * @param tipoPedido El nuevo tipo de pedido a asignar.
	 */
	public void setTipoPedido(String tipoPedido) {
		this.tipoPedido = tipoPedido;
	}

	/**
	 * Devuelve una representación en String de la entidad Cliente.
	 * * @return Una cadena que incluye los datos del usuario junto con su 
	 * método de pago y tipo de pedido.
	 */
	@Override
	public String toString() {
		return super.toString() + "Cliente \n Método de pago:" + metodoPago + "\n" + "Tipo de pedido:" + tipoPedido
				+ "\n";
	}

	/**
	 * Genera un código hash para la entidad Cliente.
	 * * @return El código hash basado en el método de pago y el tipo de pedido.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(metodoPago, tipoPedido);
	}

	/**
	 * Compara esta entidad con otro objeto para verificar si son iguales.
	 * * @param obj El objeto con el cual se va a comparar.
	 * @return true si los objetos son iguales en atributos e identidad, false en caso contrario.
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