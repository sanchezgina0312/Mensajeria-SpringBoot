package co.edu.unbosque.mensajeria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.mensajeria.entity.ManipuladorDePaquete;

/**
 * Interfaz de repositorio para la entidad  ManipuladorDePaquete.
 * <p>
 * Proporciona los métodos necesarios para gestionar la persistencia de los 
 * manipuladores de paquetes en la base de datos. Al extender de CrudRepository, 
 * hereda las operaciones CRUD básicas y permite realizar búsquedas personalizadas 
 *  de Spring Data JPA.
 *
 * @version 1.0
 */
public interface ManipuladorDePaqueteRepository extends CrudRepository<ManipuladorDePaquete, Long> {

	/**
	 * Busca manipuladores de paquetes filtrando por su nombre completo.
	 * * @param nombre El nombre del manipulador a buscar.
	 * @return Un  Optional que contiene la lista de los manipuladores encontrados.
	 */
	public Optional<List<ManipuladorDePaquete>> findByNombre(String nombre);

	/**
	 * Busca manipuladores de paquetes según su número de identificación.
	 * * @param cedula El número de cédula a consultar.
	 * @return Un  Optional que contiene la lista de los manipuladores encontrados.
	 */
	public Optional<List<ManipuladorDePaquete>> findByCedula(String cedula);

	/**
	 * Busca manipuladores de paquetes asociados a una dirección de correo electrónico.
	 * * @param correo El correo electrónico a consultar.
	 * @return Un  Optional que contiene la lista de los manipuladores encontrados.
	 */
	public Optional<List<ManipuladorDePaquete>> findByCorreo(String correo);	

	/**
	 * Busca manipuladores de paquetes por su número de teléfono.
	 * * @param telefono El número de teléfono de contacto.
	 * @return Un  Optional que contiene la lista de los manipuladores encontrados.
	 */
	public Optional<List<ManipuladorDePaquete>> findByTelefono(String telefono);		

	/**
	 * Busca manipuladores de paquetes según su tipo o rol específico.
	 * * @param tipoManipulador El tipo de manipulador a consultar (ej. clasificador, empacador).
	 * @return Un  Optional que contiene la lista de los manipuladores encontrados.
	 */
	public Optional<List<ManipuladorDePaquete>> findByTipoManipulador(String tipoManipulador);
	
	/**
	 * Busca manipuladores de paquetes que coincidan exactamente con un nombre y una cédula.
	 * * @param nombre El nombre del manipulador.
	 * @param cedula El número de identificación del manipulador.
	 * @return Un  Optional con la lista de manipuladores que cumplen ambos criterios.
	 */
	public Optional<List<ManipuladorDePaquete>> findByNombreAndCedula(String nombre, String cedula);	

	/**
	 * Verifica si existe un registro de manipulador de paquete en la base de datos con una cédula dada.
	 * * @param cedula La cédula a verificar.
	 * @return true si el manipulador ya se encuentra registrado, false en caso contrario.
	 */
	public boolean existsByCedula(String cedula);
}