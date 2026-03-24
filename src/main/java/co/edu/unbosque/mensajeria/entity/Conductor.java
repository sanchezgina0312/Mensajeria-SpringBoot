package co.edu.unbosque.mensajeria.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entidad que representa a un Conductor en el sistema.
 * <p>
 * Hereda de la clase base Trabajador y añade el atributo específico
 * correspondiente a la placa del vehículo asignado para sus labores. Esta clase 
 * está mapeada a la tabla "conductor" en la base de datos mediante JPA.
 *
 * @version 1.0
 */
@Entity
@Table(name = "conductor")
public class Conductor extends Trabajador {

	/** Placa del vehículo asignado al conductor. */
	private String placaVehiculo;

	/**
	 * Constructor vacío. <br>
	 * <b>post</b>: Se crea una entidad Conductor con atributos heredados
	 * nulos o por defecto y la placa del vehículo sin inicializar. Requerido por JPA.
	 */
	public Conductor() {

	}

	/**
	 * Constructor con la placa del vehículo. <br>
	 * <b>post</b>: Se crea una entidad Conductor con la placa establecida y
	 * los atributos heredados nulos o por defecto.
	 * * @param placaVehiculo La placa del vehículo asignado al conductor.
	 */
	public Conductor(String placaVehiculo) {
		super();
		this.placaVehiculo = placaVehiculo;
	}

	/**
	 * Constructor con turno y placa del vehículo. <br>
	 * <b>post</b>: Se crea una entidad Conductor inicializando el turno de trabajo
	 * y la placa del vehículo asignado.
	 * * @param turno         El turno asignado al conductor.
	 * @param placaVehiculo La placa del vehículo asignado.
	 */
	public Conductor(char turno, String placaVehiculo) {
		super(turno);
		this.placaVehiculo = placaVehiculo;
	}

	/**
	 * Constructor completo con datos personales, turno y placa del vehículo. <br>
	 * <b>post</b>: Se crea una entidad Conductor con todos los atributos
	 * (heredados y propios) inicializados.
	 * * @param nombre        El nombre del conductor.
	 * @param cedula        La cédula del conductor.
	 * @param correo        El correo del conductor.
	 * @param telefono      El teléfono del conductor.
	 * @param turno         El turno asignado al conductor.
	 * @param placaVehiculo La placa del vehículo asignado.
	 */
	public Conductor(String nombre, String cedula, String correo, String telefono, char turno, String placaVehiculo) {
		super(nombre, cedula, correo, telefono, turno);
		this.placaVehiculo = placaVehiculo;
	}

	/**
	 * Constructor con datos personales y placa del vehículo (sin turno). <br>
	 * <b>post</b>: Se crea una entidad Conductor inicializando sus datos personales
	 * básicos y la placa del vehículo, manteniendo el turno por defecto.
	 * * @param nombre        El nombre del conductor.
	 * @param cedula        La cédula del conductor.
	 * @param correo        El correo del conductor.
	 * @param telefono      El teléfono del conductor.
	 * @param placaVehiculo La placa del vehículo asignado.
	 */
	public Conductor(String nombre, String cedula, String correo, String telefono, String placaVehiculo) {
		super(nombre, cedula, correo, telefono);
		this.placaVehiculo = placaVehiculo;
	}

	/**
	 * Constructor solo con el turno. <br>
	 * <b>post</b>: Se crea una entidad Conductor inicializando únicamente el turno
	 * y manteniendo la placa y demás atributos por defecto.
	 * * @param turno El turno asignado al conductor.
	 */
	public Conductor(char turno) {
		super(turno);
	}

	/**
	 * Constructor con datos personales y turno. <br>
	 * <b>post</b>: Se crea una entidad Conductor con sus datos personales y
	 * turno inicializados, manteniendo la placa por defecto.
	 * * @param nombre   El nombre del conductor.
	 * @param cedula   La cédula del conductor.
	 * @param correo   El correo del conductor.
	 * @param telefono El teléfono del conductor.
	 * @param turno    El turno asignado al conductor.
	 */
	public Conductor(String nombre, String cedula, String correo, String telefono, char turno) {
		super(nombre, cedula, correo, telefono, turno);
	}

	/**
	 * Constructor con datos personales básicos. <br>
	 * <b>post</b>: Se crea una entidad Conductor inicializando sus datos
	 * básicos, manteniendo el turno y la placa por defecto.
	 * * @param nombre   El nombre del conductor.
	 * @param cedula   La cédula del conductor.
	 * @param correo   El correo del conductor.
	 * @param telefono El teléfono del conductor.
	 */
	public Conductor(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);
	}

	/**
	 * Obtiene la placa del vehículo asignado al conductor.
	 * * @return La placa del vehículo.
	 */
	public String getPlacaVehiculo() {
		return placaVehiculo;
	}

	/**
	 * Establece la placa del vehículo asignado al conductor.
	 * * @param placaVehiculo La nueva placa del vehículo.
	 */
	public void setPlacaVehiculo(String placaVehiculo) {
		this.placaVehiculo = placaVehiculo;
	}

	/**
	 * Devuelve una representación en String de la entidad Conductor.
	 * * @return Una cadena que incluye los datos del trabajador junto con la placa del vehículo.
	 */
	@Override
	public String toString() {
		return super.toString() + "\n Conductor:\n Placa Vehículo:" + placaVehiculo + ".";
	}

	/**
	 * Genera un código hash para la entidad Conductor.
	 * * @return El código hash basado en los atributos del padre y la placa del vehículo.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(placaVehiculo);
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
		Conductor other = (Conductor) obj;
		return Objects.equals(placaVehiculo, other.placaVehiculo);
	}

}