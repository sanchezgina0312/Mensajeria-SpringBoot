package co.edu.unbosque.mensajeria.dto;

import java.time.LocalDateTime;
import java.util.Objects;
import co.edu.unbosque.mensajeria.entity.Paquete;

/**
 * DTO (Data Transfer Object) para PaqueteCarta.
 * <p>
 * Hereda de la clase base Paquete y añade atributos específicos para el envío de 
 * correspondencia y documentos, incluyendo la vinculación directa con el cliente.
 * </p>
 *
 * @version 1.1
 */
public class PaqueteCartaDTO extends Paquete {

	/** Especifica la clasificación o el tipo de carta que se está enviando. */
	private String tipoCarta;

	/** Identificador único del cliente asociado al paquete. */
	private long idCliente; // Agregado para consistencia con los otros DTOs

	/**
	 * Constructor vacío.
	 */
	public PaqueteCartaDTO() {
		super();
	}

	/**
	 * Constructor con el tipo de carta.
	 * @param tipoCarta El tipo o clasificación de la carta.
	 */
	public PaqueteCartaDTO(String tipoCarta) {
		super();
		this.tipoCarta = tipoCarta;
	}

	/**
	 * Constructor completo para la transferencia de datos.
	 */
	public PaqueteCartaDTO(int precioEnvio, String direccionDestino, String tamanio, LocalDateTime fechaCreacionPedido,
			LocalDateTime fechaEstimadaEntrega, String ciudadDestino, String estadoPedido, boolean esPrioritario,
			double precioFinal, long idCliente, String tipoCarta) {
		super(precioEnvio, direccionDestino, tamanio, fechaCreacionPedido, fechaEstimadaEntrega, ciudadDestino,
				estadoPedido, esPrioritario, precioFinal, idCliente);
		this.idCliente = idCliente; // Seteo local
		this.tipoCarta = tipoCarta;
	}

	/**
	 * Constructor de transferencia para datos base.
	 */
	public PaqueteCartaDTO(int precioEnvio, String direccionDestino, String tamanio, LocalDateTime fechaCreacionPedido,
			LocalDateTime fechaEstimadaEntrega, String ciudadDestino, String estadoPedido, boolean esPrioritario,
			double precioFinal, long idCliente) {
		super(precioEnvio, direccionDestino, tamanio, fechaCreacionPedido, fechaEstimadaEntrega, ciudadDestino,
				estadoPedido, esPrioritario, precioFinal, idCliente);
		this.idCliente = idCliente; // Seteo local
	}

	// --- Getters y Setters ---

	public long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}

	public String getTipoCarta() {
		return tipoCarta;
	}

	public void setTipoCarta(String tipoCarta) {
		this.tipoCarta = tipoCarta;
	}

	@Override
	public String toString() {
		return super.toString() + "\n PaqueteCarta:\n Tipo Carta:" + tipoCarta + "\n ID Cliente:" + idCliente + ".";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(tipoCarta, idCliente);
		return result;
	}

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
		return idCliente == other.idCliente && Objects.equals(tipoCarta, other.tipoCarta);
	}
}