package co.edu.unbosque.mensajeria.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import co.edu.unbosque.mensajeria.entity.Paquete;

/**
 * DTO (Data Transfer Object) para PaqueteNoAlimenticio.
 * <p>
 * Hereda de la clase base Paquete y añade atributos específicos para los
 * paquetes que no contienen alimentos, como el indicador de fragilidad. 
 * Se utiliza para transferir datos de estos paquetes de forma segura entre capas.
 * </p>
 *
 * @version 1.0
 */
public class PaqueteNoAlimenticioDTO extends Paquete {

	/**
	 * Indica si el paquete no alimenticio es frágil y requiere manejo cuidadoso.
	 */
	private boolean esFragil;

	/** Identificador único del cliente asociado al paquete. */
	private long idCliente;

	/**
	 * Constructor vacío. <br>
	 * <b>post</b>: Se crea un DTO PaqueteNoAlimenticio con atributos heredados
	 * nulos o por defecto y el indicador de fragilidad en false.
	 */
	public PaqueteNoAlimenticioDTO() {
		super();
	}

	/**
	 * Constructor con indicador de fragilidad. <br>
	 * <b>post</b>: Se crea un DTO PaqueteNoAlimenticio estableciendo si es frágil y
	 * manteniendo los atributos heredados nulos o por defecto.
	 * * @param esFragil Indica si el paquete es frágil.
	 */
	public PaqueteNoAlimenticioDTO(boolean esFragil) {
		super();
		this.esFragil = esFragil;
	}

	/**
	 * Constructor completo para la transferencia de datos de un paquete no
	 * alimenticio. <br>
	 * <b>post</b>: Se crea un objeto DTO que encapsula toda la información de un
	 * envío no alimenticio, incluyendo datos de transporte, cliente y detalles
	 * de manejo especial.
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
	 * @param esFragil             Indica si el contenido requiere trato especial por fragilidad.
	 */
	public PaqueteNoAlimenticioDTO(int precioEnvio, String direccionDestino, String tamanio,
			LocalDateTime fechaCreacionPedido, LocalDateTime fechaEstimadaEntrega, String ciudadDestino,
			String estadoPedido, boolean esPrioritario, double precioFinal, long idCliente, boolean esFragil) {
		super(precioEnvio, direccionDestino, tamanio, fechaCreacionPedido, fechaEstimadaEntrega, ciudadDestino,
				estadoPedido, esPrioritario, precioFinal, idCliente);
		this.idCliente = idCliente;
		this.esFragil = esFragil;
	}

	/**
	 * Constructor de transferencia para datos base de un paquete no alimenticio.
	 * <br>
	 * <b>post</b>: Se crea una instancia de PaqueteNoAlimenticioDTO que contiene
	 * los datos generales de envío y cliente, dejando la fragilidad por defecto.
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
	public PaqueteNoAlimenticioDTO(int precioEnvio, String direccionDestino, String tamanio,
			LocalDateTime fechaCreacionPedido, LocalDateTime fechaEstimadaEntrega, String ciudadDestino,
			String estadoPedido, boolean esPrioritario, double precioFinal, long idCliente) {
		super(precioEnvio, direccionDestino, tamanio, fechaCreacionPedido, fechaEstimadaEntrega, ciudadDestino,
				estadoPedido, esPrioritario, precioFinal, idCliente);
		this.idCliente = idCliente;
	}

	// --- Getters y Setters ---

	/**
	 * Obtiene el identificador único del cliente.
	 * @return El id del cliente.
	 */
	public long getIdCliente() {
		return idCliente;
	}

	/**
	 * Establece el identificador único del cliente.
	 * @param idCliente El nuevo id del cliente.
	 */
	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * Verifica si el paquete es frágil.
	 * @return true si el paquete es frágil, false en caso contrario.
	 */
	public boolean isEsFragil() {
		return esFragil;
	}

	/**
	 * Establece si el paquete es frágil.
	 * @param esFragil true para indicar que es frágil, false en caso contrario.
	 */
	public void setEsFragil(boolean esFragil) {
		this.esFragil = esFragil;
	}

	/**
	 * Devuelve una representación en String del DTO del paquete no alimenticio.
	 */
	@Override
	public String toString() {
		return super.toString() + "\n Paquete No Alimenticio: \n ¿Es Frágil?:" + esFragil + "\n ID Cliente:" + idCliente + ".";
	}

	/**
	 * Genera un código hash para el objeto DTO.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(esFragil, idCliente);
		return result;
	}

	/**
	 * Compara este DTO con otro objeto para verificar si son iguales.
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
		PaqueteNoAlimenticioDTO other = (PaqueteNoAlimenticioDTO) obj;
		return esFragil == other.esFragil && idCliente == other.idCliente;
	}
}