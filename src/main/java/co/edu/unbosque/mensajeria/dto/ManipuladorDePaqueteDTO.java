package co.edu.unbosque.mensajeria.dto;

import java.util.Objects;

import co.edu.unbosque.mensajeria.entity.Trabajador;

/**
 * DTO (Data Transfer Object) para ManipuladorDePaquete.
 * <p>
 * Hereda de la clase base Trabajador y añade el tipo de manipulador. Se
 * utiliza para transferir de forma segura los datos de los trabajadores 
 * encargados de manipular los paquetes entre las distintas capas de la aplicación.
 *
 * @version 1.0
 */
public class ManipuladorDePaqueteDTO extends Trabajador {

	/** Especifica el rol o tipo de manipulador de paquete. */
	private String tipoManipulador;

	/**
	 * Constructor vacío. <br>
	 * <b>post</b>: Se crea un DTO ManipuladorDePaquete con atributos heredados
	 * nulos o por defecto y el tipo de manipulador nulo.
	 */
	public ManipuladorDePaqueteDTO() {

	}

	/**
	 * Constructor con el tipo de manipulador. <br>
	 * <b>post</b>: Se crea un DTO ManipuladorDePaquete con el tipo establecido y
	 * los atributos heredados nulos o por defecto.
	 * * @param tipoManipulador El tipo o rol del manipulador de paquetes.
	 */
	public ManipuladorDePaqueteDTO(String tipoManipulador) {
		super();
		this.tipoManipulador = tipoManipulador;
	}

	/**
	 * Constructor solo con el turno. <br>
	 * <b>post</b>: Se crea un DTO ManipuladorDePaquete inicializando únicamente
	 * el turno y manteniendo el tipo de manipulador por defecto.
	 * * @param turno El turno asignado al manipulador.
	 */
	public ManipuladorDePaqueteDTO(char turno) {
		super(turno);

	}

	/**
	 * Constructor con datos personales y turno. <br>
	 * <b>post</b>: Se crea un DTO ManipuladorDePaquete con sus datos personales y
	 * turno inicializados, manteniendo el tipo de manipulador por defecto.
	 * * @param nombre   El nombre del manipulador.
	 * @param cedula   La cédula del manipulador.
	 * @param correo   El correo del manipulador.
	 * @param telefono El teléfono del manipulador.
	 * @param turno    El turno asignado al manipulador.
	 */
	public ManipuladorDePaqueteDTO(String nombre, String cedula, String correo, String telefono, char turno) {
		super(nombre, cedula, correo, telefono, turno);

	}

	/**
	 * Constructor con datos personales básicos. <br>
	 * <b>post</b>: Se crea un DTO ManipuladorDePaquete inicializando sus datos
	 * básicos, manteniendo el turno y el tipo de manipulador por defecto.
	 * * @param nombre   El nombre del manipulador.
	 * @param cedula   La cédula del manipulador.
	 * @param correo   El correo del manipulador.
	 * @param telefono El teléfono del manipulador.
	 */
	public ManipuladorDePaqueteDTO(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);

	}

	/**
	 * Constructor con turno y tipo de manipulador. <br>
	 * <b>post</b>: Se crea un DTO ManipuladorDePaquete inicializando el turno de
	 * trabajo y su tipo de rol.
	 * * @param turno           El turno asignado al manipulador.
	 * @param tipoManipulador El tipo o rol del manipulador de paquetes.
	 */
	public ManipuladorDePaqueteDTO(char turno, String tipoManipulador) {
		super(turno);
		this.tipoManipulador = tipoManipulador;
	}

	/**
	 * Constructor completo con datos personales, turno y tipo de manipulador. <br>
	 * <b>post</b>: Se crea un DTO ManipuladorDePaquete con todos los atributos
	 * (heredados y propios) inicializados.
	 * * @param nombre          El nombre del manipulador.
	 * @param cedula          La cédula del manipulador.
	 * @param correo          El correo del manipulador.
	 * @param telefono        El teléfono del manipulador.
	 * @param turno           El turno asignado al manipulador.
	 * @param tipoManipulador El tipo o rol del manipulador de paquetes.
	 */
	public ManipuladorDePaqueteDTO(String nombre, String cedula, String correo, String telefono, char turno,
			String tipoManipulador) {
		super(nombre, cedula, correo, telefono, turno);
		this.tipoManipulador = tipoManipulador;
	}

	/**
	 * Constructor con datos personales y tipo de manipulador (sin turno). <br>
	 * <b>post</b>: Se crea un DTO ManipuladorDePaquete inicializando sus datos
	 * personales básicos y su tipo, manteniendo el turno por defecto.
	 * * @param nombre          El nombre del manipulador.
	 * @param cedula          La cédula del manipulador.
	 * @param correo          El correo del manipulador.
	 * @param telefono        El teléfono del manipulador.
	 * @param tipoManipulador El tipo o rol del manipulador de paquetes.
	 */
	public ManipuladorDePaqueteDTO(String nombre, String cedula, String correo, String telefono,
			String tipoManipulador) {
		super(nombre, cedula, correo, telefono);
		this.tipoManipulador = tipoManipulador;
	}

	/**
	 * Obtiene el tipo o rol del manipulador de paquetes.
	 * * @return El tipo de manipulador.
	 */
	public String getTipoManipulador() {
		return tipoManipulador;
	}

	/**
	 * Establece el tipo o rol del manipulador de paquetes.
	 * * @param tipoManipulador El nuevo tipo de manipulador a asignar.
	 */
	public void setTipoManipulador(String tipoManipulador) {
		this.tipoManipulador = tipoManipulador;
	}

	/**
	 * Devuelve una representación en String del DTO del manipulador.
	 * * @return Una cadena que incluye los datos del trabajador junto con su tipo de manipulador.
	 */
	@Override
	public String toString() {
		return super.toString() + "\n ManipuladorDePaquete:\n Tipo Manipulador:" + tipoManipulador + ".";
	}

	/**
	 * Genera un código hash para el objeto DTO.
	 * * @return El código hash basado en los atributos del padre y el tipo de manipulador.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(tipoManipulador);
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
		ManipuladorDePaqueteDTO other = (ManipuladorDePaqueteDTO) obj;
		return Objects.equals(tipoManipulador, other.tipoManipulador);
	}

}