package co.edu.unbosque.mensajeria.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import co.edu.unbosque.mensajeria.entity.Paquete;

/**
 * DTO (Data Transfer Object) para PaqueteCarta.
 * <p>
 * Hereda de la clase base Paquete y añade un atributo específico para los
 * paquetes que son de tipo carta, especificando su clasificación. Se utiliza 
 * para transferir datos de cartas o documentos de forma segura entre capas.
 *
 * @version 1.0
 */
public class PaqueteCartaDTO extends Paquete {

	/** Especifica la clasificación o el tipo de carta que se está enviando. */
	private String tipoCarta;

	/**
	 * Constructor vacío. <br>
	 * <b>post</b>: Se crea un DTO PaqueteCarta con atributos heredados
	 * nulos o por defecto y el tipo de carta nulo.
	 */
	public PaqueteCartaDTO() {

	}

	/**
	 * Constructor con el tipo de carta. <br>
	 * <b>post</b>: Se crea un DTO PaqueteCarta con el tipo de carta establecido y
	 * los atributos heredados nulos o por defecto.
	 * * @param tipoCarta El tipo o clasificación de la carta.
	 */
	public PaqueteCartaDTO(String tipoCarta) {
		super();
		this.tipoCarta = tipoCarta;
	}

	/**
	 * Constructor completo con atributos heredados y el tipo de carta. <br>
	 * <b>post</b>: Se crea un DTO PaqueteCarta con todos los atributos
	 * (heredados del Paquete general y el propio de la carta) inicializados.
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
	 * @param tipoCarta            El tipo o clasificación de la carta.
	 */
	public PaqueteCartaDTO(long id, int precioEnvio, String direccionDestino, String tamanio,
			LocalDateTime fechaCreacionPedido, LocalDateTime fechaEstimadaEntrega, String ciudadDestino,
			String estadoPedido, boolean esPrioritario, double precioFinal, String tipoCarta) {
		super(id, precioEnvio, direccionDestino, tamanio, fechaCreacionPedido, fechaEstimadaEntrega, ciudadDestino,
				estadoPedido, esPrioritario, precioFinal);
		this.tipoCarta = tipoCarta;
	}
	
	/**
	 * Constructor con atributos heredados de Paquete. <br>
	 * <b>post</b>: Se crea un DTO PaqueteCarta inicializando todos los
	 * atributos generales de un paquete, manteniendo el tipo de carta por defecto.
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
	public PaqueteCartaDTO(long id, int precioEnvio, String direccionDestino, String tamanio,
			LocalDateTime fechaCreacionPedido, LocalDateTime fechaEstimadaEntrega, String ciudadDestino,
			String estadoPedido, boolean esPrioritario, double precioFinal) {
		super(id, precioEnvio, direccionDestino, tamanio, fechaCreacionPedido, fechaEstimadaEntrega, ciudadDestino,
				estadoPedido, esPrioritario, precioFinal);
	}

	/**
	 * Obtiene el tipo o clasificación de la carta.
	 * * @return El tipo de carta.
	 */
	public String getTipoCarta() {
		return tipoCarta;
	}

	/**
	 * Establece el tipo o clasificación de la carta.
	 * * @param tipoCarta El nuevo tipo de carta.
	 */
	public void setTipoCarta(String tipoCarta) {
		this.tipoCarta = tipoCarta;
	}

	/**
	 * Devuelve una representación en String del DTO del paquete carta.
	 * * @return Una cadena que incluye los datos generales del paquete junto con el tipo de carta.
	 */
	@Override
	public String toString() {
		return super.toString() + "\n PaqueteCarta:\n Tipo Carta:" + tipoCarta + ".";
	}

	/**
	 * Genera un código hash para el objeto DTO.
	 * * @return El código hash basado en los atributos heredados y el tipo de carta.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(tipoCarta);
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
		PaqueteCartaDTO other = (PaqueteCartaDTO) obj;
		return Objects.equals(tipoCarta, other.tipoCarta);
	}

}