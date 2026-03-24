package co.edu.unbosque.mensajeria.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entidad abstracta que representa a un Trabajador en el sistema.
 * <p>
 * Hereda de la clase base  Usuario y define los atributos comunes 
 * para el personal de la empresa, como el turno de trabajo asignado. 
 * Sirve como clase base para tipos específicos de trabajadores (Administrador, 
 * Conductor, ManipuladorDePaquete). Esta clase está mapeada a la tabla 
 * "trabajador" en la base de datos mediante JPA.
 *
 * @version 1.0
 */
@Entity
@Table(name = "trabajador")
public abstract class Trabajador extends Usuario {

	/** Turno de trabajo asignado al empleado (ej. 'M' para mañana, 'T' para tarde). */
	private char turno;

	/**
	 * Constructor vacío. <br>
	 * <b>post</b>: Se crea una entidad Trabajador con atributos heredados nulos
	 * o por defecto y sin inicializar el turno. Requerido por el marco de trabajo JPA.
	 */
	public Trabajador() {
	}

	/**
	 * Constructor con el turno asignado. <br>
	 * <b>post</b>: Se crea una entidad Trabajador estableciendo su turno y 
	 * manteniendo los atributos heredados de Usuario por defecto.
	 * * @param turno El turno asignado al trabajador.
	 */
	public Trabajador(char turno) {
		super();
		this.turno = turno;
	}

	/**
	 * Constructor completo con datos personales y turno. <br>
	 * <b>post</b>: Se crea una entidad Trabajador inicializando sus datos 
	 * de usuario y el turno de trabajo correspondiente.
	 * * @param nombre   El nombre del trabajador.
	 * @param cedula   La cédula del trabajador.
	 * @param correo   El correo electrónico del trabajador.
	 * @param telefono El teléfono de contacto del trabajador.
	 * @param turno    El turno asignado al trabajador.
	 */
	public Trabajador(String nombre, String cedula, String correo, String telefono, char turno) {
		super(nombre, cedula, correo, telefono);
		this.turno = turno;
	}

	/**
	 * Constructor con datos personales básicos. <br>
	 * <b>post</b>: Se crea una entidad Trabajador inicializando los atributos 
	 * heredados de Usuario, manteniendo el turno por defecto.
	 * * @param nombre   El nombre del trabajador.
	 * @param cedula   La cédula del trabajador.
	 * @param correo   El correo electrónico del trabajador.
	 * @param telefono El teléfono de contacto del trabajador.
	 */
	public Trabajador(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);
	}

	/**
	 * Obtiene el turno asignado al trabajador.
	 * * @return El carácter que representa el turno.
	 */
	public char getTurno() {
		return turno;
	}

	/**
	 * Establece el turno del trabajador.
	 * * @param turno El nuevo turno a asignar.
	 */
	public void setTurno(char turno) {
		this.turno = turno;
	}

	/**
	 * Devuelve una representación en String de la entidad Trabajador.
	 * * @return Una cadena que incluye los datos básicos del usuario junto con su turno.
	 */
	@Override
	public String toString() {
		return super.toString() + "\n Trabajador:\n Turno:" + turno + ".";
	}

	/**
	 * Genera un código hash para la entidad Trabajador.
	 * * @return El código hash basado en los atributos heredados y el turno.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(turno);
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
		Trabajador other = (Trabajador) obj;
		return turno == other.turno;
	}

}