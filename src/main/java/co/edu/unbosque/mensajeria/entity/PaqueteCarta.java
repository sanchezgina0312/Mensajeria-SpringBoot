package co.edu.unbosque.mensajeria.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entidad que representa a un Paquete de tipo Carta en el sistema.
 * <p>
 * Hereda de la clase base Paquete y añade un atributo específico para los
 * envíos que son cartas o documentos, especificando su clasificación. Esta
 * clase está mapeada a la tabla "paquetecarta" en la base de datos mediante
 * JPA.
 *
 * @version 1.0
 */
@Entity
@Table(name = "paquetecarta")
public class PaqueteCarta extends Paquete {

	/** Especifica la clasificación o el tipo de carta que se está enviando. */
	private String tipoCarta;

	/**
	 * Constructor vacío. <br>
	 * <b>post</b>: Se crea una entidad PaqueteCarta con atributos heredados nulos o
	 * por defecto y el tipo de carta sin inicializar. Requerido por JPA.
	 */
	public PaqueteCarta() {

	}

	/**
	 * Constructor con el tipo de carta. <br>
	 * <b>post</b>: Se crea una entidad PaqueteCarta con el tipo de carta
	 * establecido y los atributos heredados nulos o por defecto. * @param tipoCarta
	 * El tipo o clasificación de la carta.
	 */
	public PaqueteCarta(String tipoCarta) {
		super();
		this.tipoCarta = tipoCarta;
	}

	/**
	 * Constructor con todos los parámetros, incluyendo la especialización de
	 * correspondencia. <br>
	 * <b>post</b>: Se crea una instancia de PaqueteCarta inicializando los datos de
	 * envío, la relación con el cliente y el tipo específico de documento o carta a
	 * enviar. * @param precioEnvio Precio inicial de envío.
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
	 *                             Ordinaria, Notificación).
	 */
	public PaqueteCarta(int precioEnvio, String direccionDestino, String tamanio, LocalDateTime fechaCreacionPedido,
			LocalDateTime fechaEstimadaEntrega, String ciudadDestino, String estadoPedido, boolean esPrioritario,
			double precioFinal, long idCliente, String tipoCarta) {
		super(precioEnvio, direccionDestino, tamanio, fechaCreacionPedido, fechaEstimadaEntrega, ciudadDestino,
				estadoPedido, esPrioritario, precioFinal, idCliente);
		this.tipoCarta = tipoCarta;
	}

	/**
	 * Constructor que inicializa únicamente los atributos generales de un paquete
	 * de correspondencia. <br>
	 * <b>post</b>: Se crea una instancia de PaqueteCarta vinculada a un cliente,
	 * delegando los atributos comunes a la clase superior y dejando el tipo de
	 * carta para ser definido posteriormente. * @param precioEnvio Precio inicial
	 * de envío.
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
	public PaqueteCarta(int precioEnvio, String direccionDestino, String tamanio, LocalDateTime fechaCreacionPedido,
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
	 * Devuelve una representación en String de la entidad paquete carta. * @return
	 * Una cadena que incluye los datos generales del paquete junto con el tipo de
	 * carta.
	 */
	@Override
	public String toString() {
		return super.toString() + "\n PaqueteCarta:\n Tipo Carta:" + tipoCarta + ".";
	}

	/**
	 * Genera un código hash para la entidad PaqueteCarta. * @return El código hash
	 * basado en los atributos heredados y el tipo de carta.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(tipoCarta);
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
		PaqueteCarta other = (PaqueteCarta) obj;
		return Objects.equals(tipoCarta, other.tipoCarta);
	}

}