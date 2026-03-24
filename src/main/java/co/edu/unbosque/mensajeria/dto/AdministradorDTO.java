package co.edu.unbosque.mensajeria.dto;

import java.util.Objects;

import co.edu.unbosque.mensajeria.entity.Trabajador;

/**
 * DTO (Data Transfer Object) para Administrador.
 * <p>
 * Hereda de la clase base Trabajador y añade credenciales de acceso (usuario y
 * contraseña). Se utiliza para transferir datos de administradores de forma
 * segura.
 *
 * @version 1.0
 */
public class AdministradorDTO extends Trabajador {

	/** Nombre de usuario del administrador. Por defecto inicializado en "admin". */
	private String usuario = "admin";
	
	/** Contraseña del administrador. Por defecto inicializada en "1234". */
	private String contrasenia = "1234";

	/**
	 * Constructor vacío. <br>
	 * <b>post</b>: Se crea un DTO Administrador con atributos heredados
	 * nulos o por defecto y credenciales predeterminadas.
	 */
	public AdministradorDTO() {

	}

	/**
	 * Constructor con credenciales. <br>
	 * <b>post</b>: Se crea un DTO Administrador con usuario y contraseña
	 * establecidos y atributos heredados nulos.
	 * 
	 * @param usuario     El nombre de usuario del administrador.
	 * @param contrasenia La contraseña del administrador.
	 */
	public AdministradorDTO(String usuario, String contrasenia) {
		super();
		this.usuario = usuario;
		this.contrasenia = contrasenia;
	}

	/**
	 * Constructor con turno y credenciales. <br>
	 * <b>post</b>: Se crea un DTO Administrador con el turno y las credenciales
	 * inicializadas.
	 * 
	 * @param turno       El turno asignado al administrador.
	 * @param usuario     El nombre de usuario del administrador.
	 * @param contrasenia La contraseña del administrador.
	 */
	public AdministradorDTO(char turno, String usuario, String contrasenia) {
		super(turno);
		this.usuario = usuario;
		this.contrasenia = contrasenia;
	}

	/**
	 * Constructor completo con atributos de trabajador y credenciales. <br>
	 * <b>post</b>: Se crea un DTO Administrador con todos los atributos
	 * (heredados y propios) inicializados.
	 * * @param nombre    El nombre del administrador.
	 * @param cedula      La cédula del administrador.
	 * @param correo      El correo del administrador.
	 * @param telefono    El teléfono del administrador.
	 * @param turno       El turno asignado al administrador.
	 * @param usuario     El nombre de usuario del administrador.
	 * @param contrasenia La contraseña del administrador.
	 */
	public AdministradorDTO(String nombre, String cedula, String correo, String telefono, char turno, String usuario,
			String contrasenia) {
		super(nombre, cedula, correo, telefono, turno);
		this.usuario = usuario;
		this.contrasenia = contrasenia;
	}

	/**
	 * Constructor sin turno pero con credenciales. <br>
	 * <b>post</b>: Se crea un DTO Administrador con los datos personales y
	 * credenciales inicializadas.
	 *
	 * @param nombre      El nombre del administrador.
	 * @param cedula      La cédula del administrador.
	 * @param correo      El correo del administrador.
	 * @param telefono    El teléfono del administrador.
	 * @param usuario     El nombre de usuario del administrador.
	 * @param contrasenia La contraseña del administrador.
	 */
	public AdministradorDTO(String nombre, String cedula, String correo, String telefono, String usuario,
			String contrasenia) {
		super(nombre, cedula, correo, telefono);
		this.usuario = usuario;
		this.contrasenia = contrasenia;
	}

	/**
	 * Constructor solo con el turno. <br>
	 * <b>post</b>: Se crea un DTO Administrador inicializando el turno y
	 * manteniendo las credenciales por defecto.
	 * 
	 *  @param turno El turno asignado al administrador.
	 */
	public AdministradorDTO(char turno) {
		super(turno);

	}

	/**
	 * Constructor con datos personales y turno (sin credenciales). <br>
	 * <b>post</b>: Se crea un DTO Administrador con datos personales y turno,
	 * conservando las credenciales por defecto.
	 * 
	 *  @param nombre   El nombre del administrador.
	 * @param cedula   La cédula del administrador.
	 * @param correo   El correo del administrador.
	 * @param telefono El teléfono del administrador.
	 * @param turno    El turno asignado al administrador.
	 */
	public AdministradorDTO(String nombre, String cedula, String correo, String telefono, char turno) {
		super(nombre, cedula, correo, telefono, turno);

	}

	/**
	 * Constructor solo con datos personales básicos. <br>
	 * <b>post</b>: Se crea un DTO Administrador con sus datos básicos, sin turno
	 * especificado y con las credenciales por defecto.
	 * 
	 * @param nombre   El nombre del administrador.
	 * @param cedula   La cédula del administrador.
	 * @param correo   El correo del administrador.
	 * @param telefono El teléfono del administrador.
	 */
	public AdministradorDTO(String nombre, String cedula, String correo, String telefono) {
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
	 * Devuelve una representación en String del DTO del administrador.
	 * 
	 *  @return Una cadena que incluye los datos del trabajador junto con las
	 * credenciales del administrador.
	 */
	@Override
	public String toString() {
		return super.toString() + "\n Administrador:\n Usuario:" + usuario + "\n Contraseña:" + contrasenia + ".";
	}

	/**
	 * Genera un código hash para el objeto DTO.
	 * 
	 *  @return El código hash basado en los atributos del padre, el usuario y la contraseña.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(contrasenia, usuario);
		return result;
	}

	/**
	 * Compara este DTO con otro objeto para verificar si son iguales.
	 * 
	 * @param obj El objeto con el cual se va a comparar.
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
		AdministradorDTO other = (AdministradorDTO) obj;
		return Objects.equals(contrasenia, other.contrasenia) && Objects.equals(usuario, other.usuario);
	}

}