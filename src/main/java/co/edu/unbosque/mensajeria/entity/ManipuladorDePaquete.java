package co.edu.unbosque.mensajeria.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entidad que representa a un Manipulador de Paquete en el sistema.
 * <p>
 * Hereda de la clase base Trabajador y añade un atributo específico
 * que define el tipo o rol del manipulador (por ejemplo, empacador, clasificador, etc.).
 * Esta clase está mapeada a la tabla "manipuladordepaquete" en la base de datos 
 * mediante JPA.
 *
 * @version 1.0
 */
@Entity
@Table(name = "manipuladordepaquete")
public class ManipuladorDePaquete extends Trabajador {

	/** Especifica el rol o tipo de manipulador de paquete. */
	private String tipoManipulador;

	/**
	 * Constructor vacío. <br>
	 * <b>post</b>: Se crea una entidad ManipuladorDePaquete con atributos heredados
	 * nulos o por defecto y el tipo de manipulador sin inicializar. Requerido por JPA.
	 */
	public ManipuladorDePaquete() {
	}

	/**
	 * Constructor con el tipo de manipulador. <br>
	 * <b>post</b>: Se crea una entidad ManipuladorDePaquete con el tipo establecido y
	 * los atributos heredados nulos o por defecto.
	 * * @param tipoManipulador El tipo o rol del manipulador de paquetes.
	 */
	public ManipuladorDePaquete(String tipoManipulador) {
		super();
		this.tipoManipulador = tipoManipulador;
	}

	/**
	 * Constructor con turno y tipo de manipulador. <br>
	 * <b>post</b>: Se crea una entidad ManipuladorDePaquete inicializando el turno de
	 * trabajo y su tipo de rol.
	 * * @param turno           El turno asignado al manipulador.
	 * @param tipoManipulador El tipo o rol del manipulador de paquetes.
	 */
	public ManipuladorDePaquete(char turno, String tipoManipulador) {
		super(turno);
		this.tipoManipulador = tipoManipulador;
	}

	/**
	 * Constructor completo con datos personales, turno y tipo de manipulador. <br>
	 * <b>post</b>: Se crea una entidad ManipuladorDePaquete con todos los atributos
	 * (heredados y propios) inicializados.
	 * * @param nombre          El nombre del manipulador.
	 * @param cedula          La cédula del manipulador.
	 * @param correo          El correo del manipulador.
	 * @param telefono        El teléfono del manipulador.
	 * @param turno           El turno asignado al manipulador.
	 * @param tipoManipulador El tipo o rol del manipulador de paquetes.
	 */
	public ManipuladorDePaquete(String nombre, String cedula, String correo, String telefono, char turno,
			String tipoManipulador) {
		super(nombre, cedula, correo, telefono, turno);
		this.tipoManipulador = tipoManipulador;
	}

	/**
	 * Constructor con datos personales y tipo de manipulador (sin turno). <br>
	 * <b>post</b>: Se crea una entidad ManipuladorDePaquete inicializando sus datos
	 * personales básicos y su tipo, manteniendo el turno por defecto.
	 * * @param nombre          El nombre del manipulador.
	 * @param cedula          La cédula del manipulador.
	 * @param correo          El correo del manipulador.
	 * @param telefono        El teléfono del manipulador.
	 * @param tipoManipulador El tipo o rol del manipulador de paquetes.
	 */
	public ManipuladorDePaquete(String nombre, String cedula, String correo, String telefono, String tipoManipulador) {
		super(nombre, cedula, correo, telefono);
		this.tipoManipulador = tipoManipulador;
	}

	/**
	 * Constructor solo con el turno. <br>
	 * <b>post</b>: Se crea una entidad ManipuladorDePaquete inicializando únicamente
	 * el turno y manteniendo el tipo de manipulador y demás atributos por defecto.
	 * * @param turno El turno asignado al manipulador.
	 */
	public ManipuladorDePaquete(char turno) {
		super(turno);
	}

	/**
	 * Constructor con datos personales y turno. <br>
	 * <b>post</b>: Se crea una entidad ManipuladorDePaquete con sus datos personales y
	 * turno inicializados, manteniendo el tipo de manipulador por defecto.
	 * * @param nombre   El nombre del manipulador.
	 * @param cedula   La cédula del manipulador.
	 * @param correo   El correo del manipulador.
	 * @param telefono El teléfono del manipulador.
	 * @param turno    El turno asignado al manipulador.
	 */
	public ManipuladorDePaquete(String nombre, String cedula, String correo, String telefono, char turno) {
		super(nombre, cedula, correo, telefono, turno);
	}

	/**
	 * Constructor con datos personales básicos. <br>
	 * <b>post</b>: Se crea una entidad ManipuladorDePaquete inicializando sus datos
	 * básicos, manteniendo el turno y el tipo de manipulador por defecto.
	 * * @param nombre   El nombre del manipulador.
	 * @param cedula   La cédula del manipulador.
	 * @param correo   El correo del manipulador.
	 * @param telefono El teléfono del manipulador.
	 */
	public ManipuladorDePaquete(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);
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
	 * Devuelve una representación en String de la entidad ManipuladorDePaquete.
	 * * @return Una cadena que incluye los datos del trabajador junto con su tipo de manipulador.
	 */
	@Override
	public String toString() {
		return super.toString() + "\n ManipuladorDePaquete:\n Tipo Manipulador:" + tipoManipulador + ".";
	}

	/**
	 * Genera un código hash para la entidad ManipuladorDePaquete.
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
		ManipuladorDePaquete other = (ManipuladorDePaquete) obj;
		return Objects.equals(tipoManipulador, other.tipoManipulador);
	}

}