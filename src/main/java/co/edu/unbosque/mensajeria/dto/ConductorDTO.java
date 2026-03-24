package co.edu.unbosque.mensajeria.dto;

import java.util.Objects;

import co.edu.unbosque.mensajeria.entity.Trabajador;

/**
 * DTO (Data Transfer Object) para Conductor.
 * <p>
 * Hereda de la clase base Trabajador y añade la placa del vehículo asignado.
 * Se utiliza para transferir datos de los conductores de forma segura entre capas.
 *
 * @version 1.0
 */
public class ConductorDTO extends Trabajador {

	/** Placa del vehículo asignado al conductor. */
	private String placaVehiculo;

	/**
	 * Constructor vacío. <br>
	 * <b>post</b>: Se crea un DTO Conductor con atributos heredados
	 * nulos o por defecto y la placa del vehículo nula.
	 */
	public ConductorDTO() {

	}

	/**
	 * Constructor con la placa del vehículo. <br>
	 * <b>post</b>: Se crea un DTO Conductor con la placa establecida y
	 * los atributos heredados nulos o por defecto.
	 * * @param placaVehiculo La placa del vehículo asignado al conductor.
	 */
	public ConductorDTO(String placaVehiculo) {
		super();
		this.placaVehiculo = placaVehiculo;
	}

	/**
	 * Constructor con turno y placa del vehículo. <br>
	 * <b>post</b>: Se crea un DTO Conductor inicializando el turno de trabajo
	 * y la placa del vehículo.
	 * * @param turno         El turno asignado al conductor.
	 * @param placaVehiculo La placa del vehículo asignado.
	 */
	public ConductorDTO(char turno, String placaVehiculo) {
		super(turno);
		this.placaVehiculo = placaVehiculo;
	}

	/**
	 * Constructor completo con datos personales, turno y placa del vehículo. <br>
	 * <b>post</b>: Se crea un DTO Conductor con todos los atributos
	 * (heredados y propios) inicializados.
	 * * @param nombre        El nombre del conductor.
	 * @param cedula        La cédula del conductor.
	 * @param correo        El correo del conductor.
	 * @param telefono      El teléfono del conductor.
	 * @param turno         El turno asignado al conductor.
	 * @param placaVehiculo La placa del vehículo asignado.
	 */
	public ConductorDTO(String nombre, String cedula, String correo, String telefono, char turno,
			String placaVehiculo) {
		super(nombre, cedula, correo, telefono, turno);
		this.placaVehiculo = placaVehiculo;
	}

	/**
	 * Constructor con datos personales y placa del vehículo (sin turno). <br>
	 * <b>post</b>: Se crea un DTO Conductor inicializando sus datos personales
	 * básicos y la placa del vehículo, manteniendo el turno por defecto.
	 * * @param nombre        El nombre del conductor.
	 * @param cedula        La cédula del conductor.
	 * @param correo        El correo del conductor.
	 * @param telefono      El teléfono del conductor.
	 * @param placaVehiculo La placa del vehículo asignado.
	 */
	public ConductorDTO(String nombre, String cedula, String correo, String telefono, String placaVehiculo) {
		super(nombre, cedula, correo, telefono);
		this.placaVehiculo = placaVehiculo;
	}

	/**
	 * Constructor solo con el turno. <br>
	 * <b>post</b>: Se crea un DTO Conductor inicializando únicamente el turno
	 * y manteniendo la placa y demás atributos por defecto.
	 * * @param turno El turno asignado al conductor.
	 */
	public ConductorDTO(char turno) {
		super(turno);

	}

	/**
	 * Constructor con datos personales y turno. <br>
	 * <b>post</b>: Se crea un DTO Conductor con sus datos personales y
	 * turno inicializados, manteniendo la placa por defecto.
	 * * @param nombre   El nombre del conductor.
	 * @param cedula   La cédula del conductor.
	 * @param correo   El correo del conductor.
	 * @param telefono El teléfono del conductor.
	 * @param turno    El turno asignado al conductor.
	 */
	public ConductorDTO(String nombre, String cedula, String correo, String telefono, char turno) {
		super(nombre, cedula, correo, telefono, turno);

	}

	/**
	 * Constructor con datos personales básicos. <br>
	 * <b>post</b>: Se crea un DTO Conductor inicializando sus datos
	 * básicos, manteniendo el turno y la placa por defecto.
	 * * @param nombre   El nombre del conductor.
	 * @param cedula   La cédula del conductor.
	 * @param correo   El correo del conductor.
	 * @param telefono El teléfono del conductor.
	 */
	public ConductorDTO(String nombre, String cedula, String correo, String telefono) {
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
	 * Devuelve una representación en String del DTO del conductor.
	 * * @return Una cadena que incluye los datos del trabajador junto con la placa del vehículo.
	 */
	@Override
	public String toString() {
		return super.toString() + "\n Conductor:\n Placa Vehículo:" + placaVehiculo + ".";
	}

	/**
	 * Genera un código hash para el objeto DTO.
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
		ConductorDTO other = (ConductorDTO) obj;
		return Objects.equals(placaVehiculo, other.placaVehiculo);
	}

}