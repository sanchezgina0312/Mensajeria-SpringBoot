package co.edu.unbosque.mensajeria.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entidad que representa a un Administrador en el sistema.
 * <p>
 * Hereda de la clase base  Trabajador y añade credenciales de acceso 
 * (usuario y contraseña). Esta clase está mapeada a la tabla "administrador" 
 * en la base de datos mediante JPA.
 *
 * @version 1.0
 */
@Entity
@Table(name = "administrador")
public class Administrador extends Trabajador {

	/** Nombre de usuario del administrador para el acceso al sistema. Inicializado por defecto en "admin". */
	private String usuario = "admin";
	
	/** Contraseña del administrador para el acceso al sistema. Inicializada por defecto en "1234". */
	private String contrasenia = "Administrador-1234*";

	/**
	 * Constructor vacío. <br>
	 * <b>post</b>: Se crea una entidad Administrador con atributos heredados
	 * nulos o por defecto y credenciales predeterminadas. Requerido por JPA.
	 */
	public Administrador() {

	}

	/**
	 * Constructor con credenciales. <br>
	 * <b>post</b>: Se crea una entidad Administrador con usuario y contraseña
	 * establecidos y atributos heredados nulos.
	 * * @param usuario     El nombre de usuario del administrador.
	 * @param contrasenia La contraseña del administrador.
	 */
	public Administrador(String usuario, String contrasenia) {
		super();
		this.usuario = usuario;
		this.contrasenia = contrasenia;
	}

	/**
	 * Constructor con turno y credenciales. <br>
	 * <b>post</b>: Se crea una entidad Administrador con el turno de trabajo y las
	 * credenciales inicializadas.
	 * * @param turno       El turno asignado al administrador.
	 * @param usuario     El nombre de usuario del administrador.
	 * @param contrasenia La contraseña del administrador.
	 */
	public Administrador(char turno, String usuario, String contrasenia) {
		super(turno);
		this.usuario = usuario;
		this.contrasenia = contrasenia;
	}

	/**
	 * Constructor completo con atributos de trabajador y credenciales. <br>
	 * <b>post</b>: Se crea una entidad Administrador con todos los atributos
	 * (heredados y propios) inicializados.
	 * * @param nombre      El nombre del administrador.
	 * @param cedula      La cédula del administrador.
	 * @param correo      El correo del administrador.
	 * @param telefono    El teléfono del administrador.
	 * @param turno       El turno asignado al administrador.
	 * @param usuario     El nombre de usuario del administrador.
	 * @param contrasenia La contraseña del administrador.
	 */
	public Administrador(String nombre, String cedula, String correo, String telefono, char turno, String usuario,
			String contrasenia) {
		super(nombre, cedula, correo, telefono, turno);
		this.usuario = usuario;
		this.contrasenia = contrasenia;
	}

	/**
	 * Constructor sin turno pero con credenciales. <br>
	 * <b>post</b>: Se crea una entidad Administrador con los datos personales y
	 * credenciales inicializadas, manteniendo el turno por defecto.
	 * * @param nombre      El nombre del administrador.
	 * @param cedula      La cédula del administrador.
	 * @param correo      El correo del administrador.
	 * @param telefono    El teléfono del administrador.
	 * @param usuario     El nombre de usuario del administrador.
	 * @param contrasenia La contraseña del administrador.
	 */
	public Administrador(String nombre, String cedula, String correo, String telefono, String usuario,
			String contrasenia) {
		super(nombre, cedula, correo, telefono);
		this.usuario = usuario;
		this.contrasenia = contrasenia;
	}

	/**
	 * Constructor solo con el turno. <br>
	 * <b>post</b>: Se crea una entidad Administrador inicializando el turno y
	 * manteniendo las credenciales por defecto.
	 * * @param turno El turno asignado al administrador.
	 */
	public Administrador(char turno) {
		super(turno);
	}

	/**
	 * Constructor con datos personales y turno (sin credenciales). <br>
	 * <b>post</b>: Se crea una entidad Administrador con datos personales y turno,
	 * conservando las credenciales por defecto.
	 * * @param nombre   El nombre del administrador.
	 * @param cedula   La cédula del administrador.
	 * @param correo   El correo del administrador.
	 * @param telefono El teléfono del administrador.
	 * @param turno    El turno asignado al administrador.
	 */
	public Administrador(String nombre, String cedula, String correo, String telefono, char turno) {
		super(nombre, cedula, correo, telefono, turno);
	}

	/**
	 * Constructor solo con datos personales básicos. <br>
	 * <b>post</b>: Se crea una entidad Administrador con sus datos básicos, sin turno
	 * especificado y con las credenciales por defecto.
	 * * @param nombre   El nombre del administrador.
	 * @param cedula   La cédula del administrador.
	 * @param correo   El correo del administrador.
	 * @param telefono El teléfono del administrador.
	 */
	public Administrador(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);
	}

	/**
	 * Obtiene el nombre de usuario del administrador.
	 * * @return El nombre de usuario.
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * Establece el nombre de usuario del administrador.
	 * * @param usuario El nuevo nombre de usuario.
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * Obtiene la contraseña del administrador.
	 * * @return La contraseña del usuario.
	 */
	public String getContrasenia() {
		return contrasenia;
	}

	/**
	 * Establece la contraseña del administrador.
	 * * @param contrasenia La nueva contraseña.
	 */
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	/**
	 * Devuelve una representación en String de la entidad administrador.
	 * * @return Una cadena que incluye los datos del trabajador junto con las
	 * credenciales del administrador.
	 */
	@Override
	public String toString() {
		return super.toString() + "\n Administrador:\n Usuario:" + usuario + "\n Contraseña:" + contrasenia + ".";
	}

	/**
	 * Genera un código hash para la entidad Administrador.
	 * * @return El código hash basado en los atributos heredados, el usuario y la contraseña.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(contrasenia, usuario);
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
		Administrador other = (Administrador) obj;
		return Objects.equals(contrasenia, other.contrasenia) && Objects.equals(usuario, other.usuario);
	}

}