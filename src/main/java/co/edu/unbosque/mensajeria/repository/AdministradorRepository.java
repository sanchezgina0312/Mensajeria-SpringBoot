package co.edu.unbosque.mensajeria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.mensajeria.entity.Administrador;

/**
 * Interfaz de repositorio para la entidad Administrador.
 * <p>
 * Proporciona los métodos necesarios para realizar operaciones CRUD (Crear, Leer, 
 * Actualizar, Eliminar) y consultas personalizadas sobre la tabla "administrador" 
 * mediante Spring Data JPA.
 *
 * @version 1.0
 */
public interface AdministradorRepository extends CrudRepository<Administrador, Long> {
	
	/**
	 * Busca administradores por su nombre completo.
	 * * @param nombre El nombre del administrador a buscar.
	 * @return Un Optional que contiene una lista de administradores encontrados.
	 */
	public Optional<List<Administrador>> findByNombre(String nombre);

	/**
	 * Busca administradores por su número de cédula.
	 * * @param cedula La cédula del administrador a buscar.
	 * @return Un Optional que contiene una lista de administradores encontrados.
	 */
	public Optional<List<Administrador>> findByCedula(String cedula);

	/**
	 * Busca administradores asociados a un correo electrónico específico.
	 * * @param correo El correo electrónico a consultar.
	 * @return Un Optional que contiene una lista de administradores encontrados.
	 */
	public Optional<List<Administrador>> findByCorreo(String correo);	

	/**
	 * Busca administradores por su número de teléfono.
	 * * @param telefono El teléfono a consultar.
	 * @return Un  Optional que contiene una lista de administradores encontrados.
	 */
	public Optional<List<Administrador>> findByTelefono(String telefono);		

	/**
	 * Busca administradores por su nombre de usuario de acceso.
	 * * @param usuario El nombre de usuario a consultar.
	 * @return Un  Optional que contiene una lista de administradores encontrados.
	 */
	public Optional<List<Administrador>> findByUsuario(String usuario);		
	

	/**
	 * Busca administradores que coincidan simultáneamente con un nombre y una cédula.
	 * * @param nombre El nombre del administrador.
	 * @param cedula La cédula del administrador.
	 * @return Un  Optional que contiene una lista de administradores que cumplen ambos criterios.
	 */
	public Optional<List<Administrador>> findByNombreAndCedula(String nombre, String cedula);	
	
	/**
	 * Verifica si existe un administrador registrado con una cédula específica.
	 * * @param cedula La cédula a comprobar en la base de datos.
	 * @return true si la cédula ya existe, false en caso contrario.
	 */
	public boolean existsByCedula(String cedula);
}