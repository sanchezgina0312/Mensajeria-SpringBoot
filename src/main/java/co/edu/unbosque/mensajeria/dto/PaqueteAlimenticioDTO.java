package co.edu.unbosque.mensajeria.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import co.edu.unbosque.mensajeria.entity.Paquete;

/**
 * DTO (Data Transfer Object) para PaqueteAlimenticio.
 * <p>
 * Hereda de la clase base Paquete y añade atributos específicos para los
 * paquetes que contienen alimentos, como la urgencia de envío en el mismo día y
 * la clasificación del alimento. Se utiliza para transferir datos de estos
 * paquetes de forma segura entre capas.
 *
 * @version 1.0
 */
public class PaqueteAlimenticioDTO extends Paquete {

	/**
	 * Indica si el paquete alimenticio debe enviarse el mismo día de su solicitud.
	 */
	private boolean seEnviaHoy;

	/** Especifica el tipo o clasificación del alimento que contiene el paquete. */
	private String tipoDeAlimento;

	/**
	 * Constructor vacío. <br>
	 * <b>post</b>: Se crea un DTO PaqueteAlimenticio con atributos heredados nulos
	 * o por defecto, y los atributos específicos sin inicializar.
	 */
	public PaqueteAlimenticioDTO() {

	}

	/**
	 * Constructor con atributos específicos del paquete alimenticio. <br>
	 * <b>post</b>: Se crea un DTO PaqueteAlimenticio inicializando si se envía hoy
	 * y el tipo de alimento, manteniendo los atributos del padre por defecto.
	 * * @param seEnviaHoy Indica si el paquete debe ser enviado hoy mismo.
	 * 
	 * @param tipoDeAlimento El tipo de alimento contenido en el paquete.
	 */
	public PaqueteAlimenticioDTO(boolean seEnviaHoy, String tipoDeAlimento) {
		super();
		this.seEnviaHoy = seEnviaHoy;
		this.tipoDeAlimento = tipoDeAlimento;
	}

	/**
	 * Constructor completo para la transferencia de datos de un paquete
	 * alimenticio. <br>
	 * <b>post</b>: Se crea un objeto DTO que encapsula toda la información de un
	 * envío alimenticio, incluyendo datos de transporte, cliente y detalles
	 * específicos del producto para su transferencia entre capas del sistema.
	 * * @param precioEnvio Precio inicial de envío.
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
	 * @param seEnviaHoy           Indicador de despacho inmediato.
	 * @param tipoDeAlimento       Categoría del contenido alimenticio.
	 */
	public PaqueteAlimenticioDTO(int precioEnvio, String direccionDestino, String tamanio,
			LocalDateTime fechaCreacionPedido, LocalDateTime fechaEstimadaEntrega, String ciudadDestino,
			String estadoPedido, boolean esPrioritario, double precioFinal, long idCliente, boolean seEnviaHoy,
			String tipoDeAlimento) {
		super(precioEnvio, direccionDestino, tamanio, fechaCreacionPedido, fechaEstimadaEntrega, ciudadDestino,
				estadoPedido, esPrioritario, precioFinal, idCliente);
		this.seEnviaHoy = seEnviaHoy;
		this.tipoDeAlimento = tipoDeAlimento;
	}

	/**
	 * Constructor de transferencia para datos base de un paquete alimenticio. <br>
	 * <b>post</b>: Se crea una instancia de PaqueteAlimenticioDTO que contiene la
	 * información general de envío y cliente, omitiendo inicialmente los detalles
	 * específicos del tipo de alimento. * @param precioEnvio Precio inicial de
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
	public PaqueteAlimenticioDTO(int precioEnvio, String direccionDestino, String tamanio,
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
	 * Devuelve una representación en String del DTO del paquete alimenticio.
	 * * @return Una cadena que incluye los datos generales del paquete junto con la
	 * confirmación de envío para hoy y el tipo de alimento.
	 */
	@Override
	public String toString() {
		return super.toString() + "\n Paquete Alimenticio:\n ¿Se Envía Hoy?:" + seEnviaHoy + "\n Tipo De Alimento:"
				+ tipoDeAlimento + ".";
	}

	/**
	 * Genera un código hash para el objeto DTO. * @return El código hash basado en
	 * los atributos heredados, si se envía hoy y el tipo de alimento.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(seEnviaHoy, tipoDeAlimento);
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
		PaqueteAlimenticioDTO other = (PaqueteAlimenticioDTO) obj;
		return seEnviaHoy == other.seEnviaHoy && Objects.equals(tipoDeAlimento, other.tipoDeAlimento);
	}

}