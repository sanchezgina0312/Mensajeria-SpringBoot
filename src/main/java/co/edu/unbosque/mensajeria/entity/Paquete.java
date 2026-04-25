package co.edu.unbosque.mensajeria.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

/**
 * Entidad abstracta que representa la base de un Paquete en el sistema.
 * <p>
 * Define los atributos comunes para todos los tipos de envíos, como costos, 
 * destinos, dimensiones y tiempos de entrega. Utiliza la estrategia de herencia 
 *  para organizar las subclases en tablas 
 * relacionadas dentro de la base de datos.
 *
 * @version 1.0
 */
@Entity
@Table(name = "paquete")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Paquete {

	/** Identificador único del paquete. Se genera automáticamente mediante una estrategia de identidad. */
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;
	
	/** Valor base asignado al envío del paquete. */
	private int precioEnvio;
	
	/** Ubicación física de destino donde debe entregarse el paquete. */
	private String direccionDestino;
	
	/** Clasificación de las dimensiones o volumen del paquete. */
	private String tamanio;
	
	/** Registro de la fecha y hora exacta en la que se generó la solicitud de envío. */
	private LocalDateTime fechaCreacionPedido;
	
	/** Fecha y hora proyectada para la entrega final del paquete. */
	private LocalDateTime fechaEstimadaEntrega;
	
	/** Nombre de la ciudad a la cual pertenece la dirección de destino. */
	private String ciudadDestino;
	
	/** Describe la etapa actual del envío (ej: Pendiente, En Camino, Entregado). */
	private String estadoPedido;
	
	/** Indica si el paquete requiere un manejo urgente o preferencial. Por defecto es false. */
	private boolean esPrioritario = false;
	
	/** Monto total a pagar, calculado tras aplicar reglas de negocio sobre el precio base. */
	private double precioFinal;
	
	/** Identificador único del cliente asociado al paquete. */
	private long idCliente;

	/**
	 * Constructor vacío. <br>
	 * <b>post</b>: Se crea una instancia de Paquete con atributos en sus valores 
	 * por defecto. Requerido para el funcionamiento de JPA.
	 */
	public Paquete() {

	}
	
	/**
	 * Constructor con parámetros de negocio e identificador de cliente. <br>
	 * <b>post</b>: Se crea una instancia de Paquete inicializando todos los campos base 
	 * y vinculándola a un cliente específico. No incluye el ID propio del paquete 
	 * para permitir la generación automática por JPA.
	 * * @param precioEnvio          Precio inicial de envío.
	 * @param direccionDestino     Dirección del destinatario.
	 * @param tamanio              Dimensiones del paquete.
	 * @param fechaCreacionPedido  Fecha de registro.
	 * @param fechaEstimadaEntrega Fecha tentativa de entrega.
	 * @param ciudadDestino        Ciudad del destinatario.
	 * @param estadoPedido         Estado del envío.
	 * @param esPrioritario        Nivel de prioridad.
	 * @param precioFinal          Costo total calculado.
	 * @param idCliente            Identificador único del cliente.
	 */
	public Paquete(int precioEnvio, String direccionDestino, String tamanio, LocalDateTime fechaCreacionPedido,
			LocalDateTime fechaEstimadaEntrega, String ciudadDestino, String estadoPedido, boolean esPrioritario,
			double precioFinal, long idCliente) {
		super();
		this.precioEnvio = precioEnvio;
		this.direccionDestino = direccionDestino;
		this.tamanio = tamanio;
		this.fechaCreacionPedido = fechaCreacionPedido;
		this.fechaEstimadaEntrega = fechaEstimadaEntrega;
		this.ciudadDestino = ciudadDestino;
		this.estadoPedido = estadoPedido;
		this.esPrioritario = esPrioritario;
		this.precioFinal = precioFinal;
		this.idCliente = idCliente;
	}

	/**
	 * Obtiene el ID del paquete.
	 * @return El identificador único.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Establece el ID del paquete.
	 * @param id El nuevo identificador único.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Obtiene el precio de envío base.
	 * @return El valor del envío.
	 */
	public int getPrecioEnvio() {
		return precioEnvio;
	}

	/**
	 * Establece el precio de envío base.
	 * @param precioEnvio El nuevo valor de envío.
	 */
	public void setPrecioEnvio(int precioEnvio) {
		this.precioEnvio = precioEnvio;
	}

	/**
	 * Obtiene la dirección de destino.
	 * @return La cadena con la dirección.
	 */
	public String getDireccionDestino() {
		return direccionDestino;
	}

	/**
	 * Establece la dirección de destino.
	 * @param direccionDestino La nueva dirección.
	 */
	public void setDireccionDestino(String direccionDestino) {
		this.direccionDestino = direccionDestino;
	}

	/**
	 * Obtiene el tamaño del paquete.
	 * @return La clasificación del tamaño.
	 */
	public String getTamanio() {
		return tamanio;
	}

	/**
	 * Establece el tamaño del paquete.
	 * @param tamanio El nuevo tamaño.
	 */
	public void setTamanio(String tamanio) {
		this.tamanio = tamanio;
	}

	/**
	 * Obtiene la fecha de creación del pedido.
	 * @return Objeto LocalDateTime con la fecha.
	 */
	public LocalDateTime getFechaCreacionPedido() {
		return fechaCreacionPedido;
	}

	/**
	 * Establece la fecha de creación del pedido.
	 * @param fechaCreacionPedido La nueva fecha de registro.
	 */
	public void setFechaCreacionPedido(LocalDateTime fechaCreacionPedido) {
		this.fechaCreacionPedido = fechaCreacionPedido;
	}

	/**
	 * Obtiene la fecha estimada de entrega.
	 * @return Objeto LocalDateTime con la fecha estimada.
	 */
	public LocalDateTime getFechaEstimadaEntrega() {
		return fechaEstimadaEntrega;
	}

	/**
	 * Establece la fecha estimada de entrega.
	 * @param fechaEstimadaEntrega La nueva fecha estimada.
	 */
	public void setFechaEstimadaEntrega(LocalDateTime fechaEstimadaEntrega) {
		this.fechaEstimadaEntrega = fechaEstimadaEntrega;
	}

	/**
	 * Obtiene la ciudad de destino.
	 * @return El nombre de la ciudad.
	 */
	public String getCiudadDestino() {
		return ciudadDestino;
	}

	/**
	 * Establece la ciudad de destino.
	 * @param ciudadDestino El nuevo nombre de la ciudad.
	 */
	public void setCiudadDestino(String ciudadDestino) {
		this.ciudadDestino = ciudadDestino;
	}

	/**
	 * Obtiene el estado actual del pedido.
	 * @return La descripción del estado.
	 */
	public String getEstadoPedido() {
		return estadoPedido;
	}

	/**
	 * Establece el estado actual del pedido.
	 * @param estadoPedido La nueva descripción del estado.
	 */
	public void setEstadoPedido(String estadoPedido) {
		this.estadoPedido = estadoPedido;
	}

	/**
	 * Verifica si el paquete es prioritario.
	 * @return true si es prioritario, false en caso contrario.
	 */
	public boolean isEsPrioritario() {
		return esPrioritario;
	}

	/**
	 * Establece el nivel de prioridad del paquete.
	 * @param esPrioritario Nuevo estado de prioridad.
	 */
	public void setEsPrioritario(boolean esPrioritario) {
		this.esPrioritario = esPrioritario;
	}

	/**
	 * Obtiene el precio final calculado.
	 * @return El valor total del envío.
	 */
	public double getPrecioFinal() {
		return precioFinal;
	}

	/**
	 * Establece el precio final calculado.
	 * @param precioFinal El nuevo costo total.
	 */
	public void setPrecioFinal(double precioFinal) {
		this.precioFinal = precioFinal;
	}

	/**
	 * Obtiene el ID del cliente asociado al paquete.
	 * @return El identificador único del cliente.
	 */
	public long getIdCliente() {
		return idCliente;
	}


	/**
	 * Establece el ID del cliente asociado al paquete.
	 * @param idCliente El nuevo identificador del cliente.
	 */
	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}


	/**
	 * Genera una representación textual detallada del paquete.
	 * @return Cadena con toda la información del paquete.
	 */
	@Override
	public String toString() {
		return "Paquete:\n Id:" + id + "\n Precio Envío:" + precioEnvio + "\n Dirección Destino:" + direccionDestino
				+ "\n Tamaño:" + tamanio + "\n Fecha Creación Pedido:" + fechaCreacionPedido
				+ "\n Fecha Estimada Entrega:" + fechaEstimadaEntrega + "\n Ciudad Destino:" + ciudadDestino
				+ "\n Estado Pedido:" + estadoPedido + "\n Prioridad:" + esPrioritario + "\n Precio Final:" + precioFinal
				+ "\n idCliente:" + idCliente + ".";
	}

	/**
	 * Calcula el código hash basado en los atributos del paquete.
	 * @return El valor del hash.
	 */
	
	@Override
	public int hashCode() {
		return Objects.hash(ciudadDestino, direccionDestino, esPrioritario, estadoPedido, fechaCreacionPedido,
				fechaEstimadaEntrega, id, idCliente, precioEnvio, precioFinal, tamanio);
	}

	/**
	 * Compara este paquete con otro objeto para determinar su igualdad.
	 * @param obj Objeto a comparar.
	 * @return true si coinciden todos los atributos, false de lo contrario.
	 */
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Paquete other = (Paquete) obj;
		return Objects.equals(ciudadDestino, other.ciudadDestino)
				&& Objects.equals(direccionDestino, other.direccionDestino) && esPrioritario == other.esPrioritario
				&& Objects.equals(estadoPedido, other.estadoPedido)
				&& Objects.equals(fechaCreacionPedido, other.fechaCreacionPedido)
				&& Objects.equals(fechaEstimadaEntrega, other.fechaEstimadaEntrega) && id == other.id
				&& idCliente == other.idCliente && precioEnvio == other.precioEnvio
				&& Double.doubleToLongBits(precioFinal) == Double.doubleToLongBits(other.precioFinal)
				&& Objects.equals(tamanio, other.tamanio);
	}
	

}