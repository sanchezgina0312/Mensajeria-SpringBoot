package co.edu.unbosque.mensajeria.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entidad que representa a un Paquete No Alimenticio en el sistema.
 * <p>
 * Hereda de la clase base Paquete y añade un atributo específico para indicar
 * si el paquete requiere un manejo cuidadoso debido a su fragilidad. Esta clase
 * está mapeada a la tabla "paquetenoalimenticio" en la base de datos mediante
 * JPA.
 *
 * @version 1.0
 */
@Entity
@Table(name = "paquetenoalimenticio")
public class PaqueteNoAlimenticio extends Paquete {

	/**
	 * Indica si el paquete no alimenticio es frágil y requiere manejo cuidadoso.
	 */
	private boolean esFragil;

	/**
	 * Constructor vacío. <br>
	 * <b>post</b>: Se crea una entidad PaqueteNoAlimenticio con atributos heredados
	 * nulos o por defecto y el indicador de fragilidad en false. Requerido por JPA.
	 */
	public PaqueteNoAlimenticio() {

	}

	/**
	 * Constructor con indicador de fragilidad. <br>
	 * <b>post</b>: Se crea una entidad PaqueteNoAlimenticio estableciendo si es
	 * frágil y manteniendo los atributos heredados nulos o por defecto. * @param
	 * esFragil Indica si el paquete es frágil.
	 */
	public PaqueteNoAlimenticio(boolean esFragil) {
		super();
		this.esFragil = esFragil;
	}

	/**
	 * Constructor con todos los parámetros, incluyendo la especificación de
	 * fragilidad. <br>
	 * <b>post</b>: Se crea una instancia de PaqueteNoAlimenticio con la información
	 * de envío, la vinculación al cliente y la indicación de si el contenido
	 * requiere cuidados especiales. * @param precioEnvio Precio inicial de envío.
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
	 * @param esFragil             Indica si el paquete contiene materiales
	 *                             delicados o rompibles.
	 */
	public PaqueteNoAlimenticio(int precioEnvio, String direccionDestino, String tamanio,
			LocalDateTime fechaCreacionPedido, LocalDateTime fechaEstimadaEntrega, String ciudadDestino,
			String estadoPedido, boolean esPrioritario, double precioFinal, long idCliente, boolean esFragil) {
		super(precioEnvio, direccionDestino, tamanio, fechaCreacionPedido, fechaEstimadaEntrega, ciudadDestino,
				estadoPedido, esPrioritario, precioFinal, idCliente);
		this.esFragil = esFragil;
	}

	/**
	 * Constructor que inicializa los atributos generales para un paquete de tipo no
	 * alimenticio. <br>
	 * <b>post</b>: Se crea una instancia de PaqueteNoAlimenticio delegando la
	 * gestión de datos comunes a la superclase y permitiendo la definición
	 * posterior de atributos específicos. * @param precioEnvio Precio inicial de
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
	public PaqueteNoAlimenticio(int precioEnvio, String direccionDestino, String tamanio,
			LocalDateTime fechaCreacionPedido, LocalDateTime fechaEstimadaEntrega, String ciudadDestino,
			String estadoPedido, boolean esPrioritario, double precioFinal, long idCliente) {
		super(precioEnvio, direccionDestino, tamanio, fechaCreacionPedido, fechaEstimadaEntrega, ciudadDestino,
				estadoPedido, esPrioritario, precioFinal, idCliente);
	}

	/**
	 * Verifica si el paquete es frágil. * @return true si el paquete es frágil,
	 * false en caso contrario.
	 */
	public boolean isEsFragil() {
		return esFragil;
	}

	/**
	 * Establece si el paquete es frágil. * @param esFragil true para indicar que es
	 * frágil, false en caso contrario.
	 */
	public void setEsFragil(boolean esFragil) {
		this.esFragil = esFragil;
	}

	/**
	 * Devuelve una representación en String de la entidad paquete no alimenticio.
	 * * @return Una cadena que incluye los datos generales del paquete junto con la
	 * confirmación de su fragilidad.
	 */
	@Override
	public String toString() {
		return super.toString() + "\n PaqueteNoAlimenticio: \n ¿Es Frágil?:" + esFragil + ".";
	}

	/**
	 * Genera un código hash para la entidad PaqueteNoAlimenticio. * @return El
	 * código hash basado en los atributos heredados y si es frágil o no.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(esFragil);
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
		PaqueteNoAlimenticio other = (PaqueteNoAlimenticio) obj;
		return esFragil == other.esFragil;
	}

}