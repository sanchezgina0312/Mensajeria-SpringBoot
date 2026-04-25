package co.edu.unbosque.mensajeria.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entidad que representa a un Paquete Alimenticio en el sistema.
 * <p>
 * Hereda de la clase base Paquete y añade atributos específicos para los
 * paquetes que contienen alimentos, como la urgencia de envío en el mismo día y
 * la clasificación del alimento. Esta clase está mapeada a la tabla
 * "paquetealimenticio" en la base de datos mediante JPA.
 *
 * @version 1.0
 */
@Entity
@Table(name = "paquetealimenticio")
public class PaqueteAlimenticio extends Paquete {

	/**
	 * Indica si el paquete alimenticio debe enviarse el mismo día de su solicitud
	 * por ser perecedero.
	 */
	private boolean seEnviaHoy;

	/** Especifica el tipo o clasificación del alimento que contiene el paquete. */
	private String tipoDeAlimento;

	/**
	 * Constructor vacío. <br>
	 * <b>post</b>: Se crea una entidad PaqueteAlimenticio con atributos heredados
	 * nulos o por defecto, y los atributos específicos sin inicializar. Requerido
	 * por JPA.
	 */
	public PaqueteAlimenticio() {

	}

	/**
	 * Constructor con atributos específicos del paquete alimenticio. <br>
	 * <b>post</b>: Se crea una entidad PaqueteAlimenticio inicializando si se envía
	 * hoy y el tipo de alimento, manteniendo los atributos del padre por defecto.
	 * * @param seEnviaHoy Indica si el paquete debe ser enviado hoy mismo.
	 * 
	 * @param tipoDeAlimento El tipo de alimento contenido en el paquete.
	 */
	public PaqueteAlimenticio(boolean seEnviaHoy, String tipoDeAlimento) {
		super();
		this.seEnviaHoy = seEnviaHoy;
		this.tipoDeAlimento = tipoDeAlimento;
	}

	/**
	 * Constructor con todos los parámetros, incluyendo atributos específicos de
	 * alimentos. <br>
	 * <b>post</b>: Se crea una instancia de PaqueteAlimenticio con la información
	 * de envío, la vinculación al cliente y las especificaciones sobre el tipo de
	 * alimento y urgencia de despacho. * @param precioEnvio Precio inicial de
	 * envío.
	 * 
	 * @param direccionDestino     Dirección del destinatario.
	 * @param tamanio              Dimensiones del paquete.
	 * @param fechaCreacionPedido  Fecha de registro.
	 * @param fechaEstimadaEntrega Fecha tentativa de entrega.
	 * @param ciudadDestino        Ciudad del destinatario.
	 * @param estadoPedido         Estado del envío.
	 * @param esPrioritario        Nivel de prioridad.
	 * @param precioFinal          Costo total calculado.
	 * @param idCliente            Identificador único del cliente.
	 * @param seEnviaHoy           Indicador de si el despacho debe realizarse en el
	 *                             día actual.
	 * @param tipoDeAlimento       Categoría o descripción del contenido alimenticio
	 *                             (ej: Perecedero).
	 */
	public PaqueteAlimenticio(int precioEnvio, String direccionDestino, String tamanio,
			LocalDateTime fechaCreacionPedido, LocalDateTime fechaEstimadaEntrega, String ciudadDestino,
			String estadoPedido, boolean esPrioritario, double precioFinal, long idCliente, boolean seEnviaHoy,
			String tipoDeAlimento) {
		super(precioEnvio, direccionDestino, tamanio, fechaCreacionPedido, fechaEstimadaEntrega, ciudadDestino,
				estadoPedido, esPrioritario, precioFinal, idCliente);
		this.seEnviaHoy = seEnviaHoy;
		this.tipoDeAlimento = tipoDeAlimento;
	}

	/**
	 * Constructor que inicializa los atributos base del paquete alimenticio. <br>
	 * <b>post</b>: Se crea una instancia de PaqueteAlimenticio delegando los
	 * atributos generales a la clase base y dejando los atributos específicos de
	 * alimento en sus valores por defecto. * @param precioEnvio Precio inicial de
	 * envío.
	 * 
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
	public PaqueteAlimenticio(int precioEnvio, String direccionDestino, String tamanio,
			LocalDateTime fechaCreacionPedido, LocalDateTime fechaEstimadaEntrega, String ciudadDestino,
			String estadoPedido, boolean esPrioritario, double precioFinal, long idCliente) {
		super(precioEnvio, direccionDestino, tamanio, fechaCreacionPedido, fechaEstimadaEntrega, ciudadDestino,
				estadoPedido, esPrioritario, precioFinal, idCliente);
	}

	/**
	 * Verifica si el paquete alimenticio debe enviarse el mismo día. * @return true
	 * si el paquete se envía hoy, false en caso contrario.
	 */
	public boolean isSeEnviaHoy() {
		return seEnviaHoy;
	}

	/**
	 * Establece si el paquete alimenticio debe enviarse el mismo día. * @param
	 * seEnviaHoy true para indicar envío inmediato, false en caso contrario.
	 */
	public void setSeEnviaHoy(boolean seEnviaHoy) {
		this.seEnviaHoy = seEnviaHoy;
	}

	/**
	 * Obtiene el tipo de alimento que contiene el paquete. * @return El tipo de
	 * alimento.
	 */
	public String getTipoDeAlimento() {
		return tipoDeAlimento;
	}

	/**
	 * Establece el tipo de alimento que contiene el paquete. * @param
	 * tipoDeAlimento El nuevo tipo de alimento.
	 */
	public void setTipoDeAlimento(String tipoDeAlimento) {
		this.tipoDeAlimento = tipoDeAlimento;
	}

	/**
	 * Devuelve una representación en String de la entidad paquete alimenticio.
	 * * @return Una cadena que incluye los datos generales del paquete junto con la
	 * confirmación de envío para hoy y el tipo de alimento.
	 */
	@Override
	public String toString() {
		return super.toString() + "\n Paquete Alimenticio:\n ¿Se Envía Hoy?:" + seEnviaHoy + "\n Tipo De Alimento:"
				+ tipoDeAlimento + ".";
	}

	/**
	 * Genera un código hash para la entidad PaqueteAlimenticio. * @return El código
	 * hash basado en los atributos heredados, si se envía hoy y el tipo de
	 * alimento.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(seEnviaHoy, tipoDeAlimento);
		return result;
	}

	/**
	 * Compara esta entidad con otro objeto para verificar si son iguales. * @param
	 * obj El objeto con el cual se va a comparar.
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
		PaqueteAlimenticio other = (PaqueteAlimenticio) obj;
		return seEnviaHoy == other.seEnviaHoy && Objects.equals(tipoDeAlimento, other.tipoDeAlimento);
	}

}