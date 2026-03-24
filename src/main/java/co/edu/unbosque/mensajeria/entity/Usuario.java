package co.edu.unbosque.mensajeria.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

/**
 * Entidad abstracta que representa a un Usuario general en el sistema.
 * <p>
 * Define los atributos fundamentales y comunes para cualquier persona registrada 
 * en la aplicación (como nombre, identificación y datos de contacto). Utiliza la 
 * estrategia de herencia JOINED para que sus subclases (como Cliente y  
 *  Trabajador) se mapeen de forma relacional en la base de datos mediante JPA.
 *
 * @version 1.0
 */
@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Usuario {

	/** Identificador único del usuario, generado automáticamente por la base de datos. */
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;
	
	/** Nombre completo del usuario. */
	private String nombre;
	
	/** Número de identificación (Cédula) único del usuario. */
	private String cedula;
	
	/** Dirección de correo electrónico del usuario. */
	private String correo;
	
	/** Número de teléfono o celular de contacto del usuario. */
	private String telefono;

	/**
	 * Constructor vacío. <br>
	 * <b>post</b>: Se crea una entidad Usuario con sus atributos nulos o por defecto. 
	 * Requerido por el marco de trabajo JPA para la persistencia.
	 */
	public Usuario() {

	}

	/**
	 * Constructor con atributos básicos de información personal. <br>
	 * <b>post</b>: Se crea una entidad Usuario con nombre, cédula, correo y 
	 * teléfono inicializados.
	 * * @param nombre   El nombre completo del usuario.
	 * @param cedula   La cédula de ciudadanía o identificación del usuario.
	 * @param correo   El correo electrónico del usuario.
	 * @param telefono El número de teléfono del usuario.
	 */
	public Usuario(String nombre, String cedula, String correo, String telefono) {
		super();
		this.nombre = nombre;
		this.cedula = cedula;
		this.correo = correo;
		this.telefono = telefono;
	}

	/**
	 * Obtiene el nombre del usuario.
	 * * @return El nombre completo.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del usuario.
	 * * @param nombre El nuevo nombre a asignar.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene la cédula del usuario.
	 * * @return El número de cédula.
	 */
	public String getCedula() {
		return cedula;
	}

	/**
	 * Establece la cédula del usuario.
	 * * @param cedula El nuevo número de cédula.
	 */
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	/**
	 * Obtiene el correo electrónico del usuario.
	 * * @return El correo electrónico.
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Establece el correo electrónico del usuario.
	 * * @param correo El nuevo correo electrónico.
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * Obtiene el número de teléfono del usuario.
	 * * @return El teléfono de contacto.
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Establece el número de teléfono del usuario.
	 * * @param telefono El nuevo teléfono de contacto.
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	/**
	 * Obtiene el identificador único del usuario.
	 * * @return El ID autogenerado.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Establece el identificador único del usuario.
	 * * @param id El nuevo ID a asignar.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Devuelve una representación en String de la entidad Usuario.
	 * * @return Una cadena que incluye el ID, nombre, cédula, correo y teléfono del usuario.
	 */
	@Override
	public String toString() {
		return "Usuario \n Id: " + id + "\n" + "Nombre: " + nombre + "\n" + "Cédula: " + cedula + "\n" + "Correo: "
				+ correo + "\n" + "Teléfono: " + telefono + "\n";
	}

	/**
	 * Genera un código hash para la entidad Usuario.
	 * * @return El código hash basado en la cédula, correo, id, nombre y teléfono.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(cedula, correo, id, nombre, telefono);
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
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(cedula, other.cedula) && Objects.equals(correo, other.correo) && id == other.id
				&& Objects.equals(nombre, other.nombre) && Objects.equals(telefono, other.telefono);
	}

}