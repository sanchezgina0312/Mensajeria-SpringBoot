package co.edu.unbosque.mensajeria.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entidad que representa a un Paquete Alimenticio en el sistema.
 * <p>
 * Hereda de la clase base  Paquete y añade atributos específicos para los
 * paquetes que contienen alimentos, como la urgencia de envío en el mismo día
 * y la clasificación del alimento. Esta clase está mapeada a la tabla 
 * "paquetealimenticio" en la base de datos mediante JPA.
 *
 * @version 1.0
 */
@Entity
@Table(name = "paquetealimenticio")
public class PaqueteAlimenticio extends Paquete {

	/** Indica si el paquete alimenticio debe enviarse el mismo día de su solicitud por ser perecedero. */
	private boolean seEnviaHoy;
	
	/** Especifica el tipo o clasificación del alimento que contiene el paquete. */
	private String tipoDeAlimento;

	/**
	 * Constructor vacío. <br>
	 * <b>post</b>: Se crea una entidad PaqueteAlimenticio con atributos heredados
	 * nulos o por defecto, y los atributos específicos sin inicializar. Requerido por JPA.
	 */
	public PaqueteAlimenticio() {

	}

	/**
	 * Constructor con atributos específicos del paquete alimenticio. <br>
	 * <b>post</b>: Se crea una entidad PaqueteAlimenticio inicializando si se envía
	 * hoy y el tipo de alimento, manteniendo los atributos del padre por defecto.
	 * * @param seEnviaHoy     Indica si el paquete debe ser enviado hoy mismo.
	 * @param tipoDeAlimento El tipo de alimento contenido en el paquete.
	 */
	public PaqueteAlimenticio(boolean seEnviaHoy, String tipoDeAlimento) {
		super();
		this.seEnviaHoy = seEnviaHoy;
		this.tipoDeAlimento = tipoDeAlimento;
	}

	/**
	 * Constructor completo con atributos heredados y específicos. <br>
	 * <b>post</b>: Se crea una entidad PaqueteAlimenticio con todos los atributos
	 * (heredados de Paquete general y los propios del alimento) inicializados.
	 * * @param id                   El identificador único del paquete.
	 * @param precioEnvio          El precio base del envío.
	 * @param direccionDestino     La dirección a la que se envía el paquete.
	 * @param tamanio              El tamaño del paquete.
	 * @param fechaCreacionPedido  La fecha y hora en que se creó el pedido.
	 * @param fechaEstimadaEntrega La fecha y hora estimadas para la entrega.
	 * @param ciudadDestino        La ciudad de destino del paquete.
	 * @param estadoPedido         El estado actual del pedido.
	 * @param esPrioritario        Indica si el paquete tiene prioridad de entrega.
	 * @param precioFinal          El precio final calculado del envío.
	 * @param seEnviaHoy           Indica si el paquete debe ser enviado hoy mismo.
	 * @param tipoDeAlimento       El tipo de alimento contenido en el paquete.
	 */
	public PaqueteAlimenticio(long id, int precioEnvio, String direccionDestino, String tamanio,
			LocalDateTime fechaCreacionPedido, LocalDateTime fechaEstimadaEntrega, String ciudadDestino,
			String estadoPedido, boolean esPrioritario, double precioFinal, boolean seEnviaHoy, String tipoDeAlimento) {
		super(id, precioEnvio, direccionDestino, tamanio, fechaCreacionPedido, fechaEstimadaEntrega, ciudadDestino,
				estadoPedido, esPrioritario, precioFinal);
		this.seEnviaHoy = seEnviaHoy;
		this.tipoDeAlimento = tipoDeAlimento;
	}
	
	/**
	 * Constructor con atributos heredados de Paquete. <br>
	 * <b>post</b>: Se crea una entidad PaqueteAlimenticio inicializando todos los
	 * atributos generales de un paquete, manteniendo los específicos por defecto.
	 * * @param id                   El identificador único del paquete.
	 * @param precioEnvio          El precio base del envío.
	 * @param direccionDestino     La dirección a la que se envía el paquete.
	 * @param tamanio              El tamaño del paquete.
	 * @param fechaCreacionPedido  La fecha y hora en que se creó el pedido.
	 * @param fechaEstimadaEntrega La fecha y hora estimadas para la entrega.
	 * @param ciudadDestino        La ciudad de destino del paquete.
	 * @param estadoPedido         El estado actual del pedido.
	 * @param esPrioritario        Indica si el paquete tiene prioridad de entrega.
	 * @param precioFinal          El precio final calculado del envío.
	 */
	public PaqueteAlimenticio(long id, int precioEnvio, String direccionDestino, String tamanio,
			LocalDateTime fechaCreacionPedido, LocalDateTime fechaEstimadaEntrega, String ciudadDestino,
			String estadoPedido, boolean esPrioritario, double precioFinal) {
		super(id, precioEnvio, direccionDestino, tamanio, fechaCreacionPedido, fechaEstimadaEntrega, ciudadDestino,
				estadoPedido, esPrioritario, precioFinal);
	}

	/**
	 * Verifica si el paquete alimenticio debe enviarse el mismo día.
	 * * @return true si el paquete se envía hoy, false en caso contrario.
	 */
	public boolean isSeEnviaHoy() {
		return seEnviaHoy;
	}

	/**
	 * Establece si el paquete alimenticio debe enviarse el mismo día.
	 * * @param seEnviaHoy true para indicar envío inmediato, false en caso contrario.
	 */
	public void setSeEnviaHoy(boolean seEnviaHoy) {
		this.seEnviaHoy = seEnviaHoy;
	}

	/**
	 * Obtiene el tipo de alimento que contiene el paquete.
	 * * @return El tipo de alimento.
	 */
	public String getTipoDeAlimento() {
		return tipoDeAlimento;
	}

	/**
	 * Establece el tipo de alimento que contiene el paquete.
	 * * @param tipoDeAlimento El nuevo tipo de alimento.
	 */
	public void setTipoDeAlimento(String tipoDeAlimento) {
		this.tipoDeAlimento = tipoDeAlimento;
	}

	/**
	 * Devuelve una representación en String de la entidad paquete alimenticio.
	 * * @return Una cadena que incluye los datos generales del paquete junto con 
	 * la confirmación de envío para hoy y el tipo de alimento.
	 */
	@Override
	public String toString() {
		return super.toString() + "\n Paquete Alimenticio:\n ¿Se Envía Hoy?:" + seEnviaHoy + "\n Tipo De Alimento:"
				+ tipoDeAlimento + ".";
	}

	/**
	 * Genera un código hash para la entidad PaqueteAlimenticio.
	 * * @return El código hash basado en los atributos heredados, si se envía hoy 
	 * y el tipo de alimento.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(seEnviaHoy, tipoDeAlimento);
		return result;
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
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaqueteAlimenticio other = (PaqueteAlimenticio) obj;
		return seEnviaHoy == other.seEnviaHoy && Objects.equals(tipoDeAlimento, other.tipoDeAlimento);
	}

}