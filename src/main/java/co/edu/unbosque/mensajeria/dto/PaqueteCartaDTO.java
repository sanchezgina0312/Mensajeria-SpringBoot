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
	 * <b>post</b>: Se crea un DTO PaqueteCarta con atributos heredados nulos o por
	 * defecto y el tipo de carta nulo.
	 */
	public PaqueteCartaDTO() {

	}

	/**
	 * Constructor con el tipo de carta. <br>
	 * <b>post</b>: Se crea un DTO PaqueteCarta con el tipo de carta establecido y
	 * los atributos heredados nulos o por defecto. * @param tipoCarta El tipo o
	 * clasificación de la carta.
	 */
	public PaqueteCartaDTO(String tipoCarta) {
		super();
		this.tipoCarta = tipoCarta;
	}

	/**
	 * Constructor completo para la transferencia de datos de una carta. <br>
	 * <b>post</b>: Se crea un objeto DTO que encapsula la información de envío, los
	 * datos del cliente y la categorización específica de la correspondencia para
	 * su procesamiento entre las distintas capas de la aplicación. * @param
	 * precioEnvio Precio inicial de envío.
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
	 * @param tipoCarta            Categoría de la carta (ej: Certificada,
	 *                             Ordinaria).
	 */
	public PaqueteCartaDTO(int precioEnvio, String direccionDestino, String tamanio, LocalDateTime fechaCreacionPedido,
			LocalDateTime fechaEstimadaEntrega, String ciudadDestino, String estadoPedido, boolean esPrioritario,
			double precioFinal, long idCliente, String tipoCarta) {
		super(precioEnvio, direccionDestino, tamanio, fechaCreacionPedido, fechaEstimadaEntrega, ciudadDestino,
				estadoPedido, esPrioritario, precioFinal, idCliente);
		this.tipoCarta = tipoCarta;
	}

	/**
	 * Constructor de transferencia para datos base de una carta. <br>
	 * <b>post</b>: Se crea una instancia de PaqueteCartaDTO con la información
	 * general de logística y cliente, permitiendo la asignación posterior del tipo
	 * de correspondencia. * @param precioEnvio Precio inicial de envío.
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
	public PaqueteCartaDTO(int precioEnvio, String direccionDestino, String tamanio, LocalDateTime fechaCreacionPedido,
			LocalDateTime fechaEstimadaEntrega, String ciudadDestino, String estadoPedido, boolean esPrioritario,
			double precioFinal, long idCliente) {
		super(precioEnvio, direccionDestino, tamanio, fechaCreacionPedido, fechaEstimadaEntrega, ciudadDestino,
				estadoPedido, esPrioritario, precioFinal, idCliente);
	}

	/**
	 * Obtiene el tipo o clasificación de la carta. * @return El tipo de carta.
	 */
	public String getTipoCarta() {
		return tipoCarta;
	}

	/**
	 * Establece el tipo o clasificación de la carta. * @param tipoCarta El nuevo
	 * tipo de carta.
	 */
	public void setTipoCarta(String tipoCarta) {
		this.tipoCarta = tipoCarta;
	}

	/**
	 * Devuelve una representación en String del DTO del paquete carta. * @return
	 * Una cadena que incluye los datos generales del paquete junto con el tipo de
	 * carta.
	 */
	@Override
	public String toString() {
		return super.toString() + "\n PaqueteCarta:\n Tipo Carta:" + tipoCarta + ".";
	}

	/**
	 * Genera un código hash para el objeto DTO. * @return El código hash basado en
	 * los atributos heredados y el tipo de carta.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(tipoCarta);
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
		PaqueteCartaDTO other = (PaqueteCartaDTO) obj;
		return Objects.equals(tipoCarta, other.tipoCarta);
	}

}