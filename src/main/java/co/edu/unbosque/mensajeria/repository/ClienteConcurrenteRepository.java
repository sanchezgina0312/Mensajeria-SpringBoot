package co.edu.unbosque.mensajeria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.mensajeria.entity.ClienteConcurrente;

/**
 * Interfaz de repositorio para la entidad ClienteConcurrente.
 * <p>
 * Define las operaciones de persistencia para gestionar los datos de los
 * clientes concurrentes en la base de datos. Extiende de CrudRepository para
 * heredar funcionalidades básicas de CRUD y añade métodos de consulta
 * personalizados basados en los atributos específicos de la entidad.
 *
 * @version 1.0
 */
public interface ClienteConcurrenteRepository extends CrudRepository<ClienteConcurrente, Long> {

	/**
	 * Busca clientes concurrentes por su nombre completo. * @param nombre El nombre
	 * del cliente a buscar.
	 * 
	 * @return Un Optional con la lista de clientes encontrados.
	 */
	public Optional<List<ClienteConcurrente>> findByNombre(String nombre);

	/**
	 * Busca clientes concurrentes por su número de cédula. * @param cedula El
	 * número de identificación a consultar.
	 * 
	 * @return Un Optional con la lista de clientes encontrados.
	 */
	public Optional<List<ClienteConcurrente>> findByCedula(String cedula);

	/**
	 * Busca clientes concurrentes asociados a un correo electrónico específico.
	 * * @param correo El correo electrónico a buscar.
	 * 
	 * @return Un Optional con la lista de clientes encontrados.
	 */
	public Optional<List<ClienteConcurrente>> findByCorreo(String correo);

	/**
	 * Busca clientes concurrentes por su número de teléfono. * @param telefono El
	 * teléfono de contacto a consultar.
	 * 
	 * @return Un Optional con la lista de clientes encontrados.
	 */
	public Optional<List<ClienteConcurrente>> findByTelefono(String telefono);

	/**
	 * Busca clientes concurrentes que utilicen un método de pago específico.
	 * * @param metodoPago El método de pago a consultar (ej: Efectivo, Tarjeta).
	 * 
	 * @return Un Optional con la lista de clientes encontrados.
	 */
	public Optional<List<ClienteConcurrente>> findByMetodoPago(String metodoPago);

	/**
	 * Busca clientes concurrentes que coincidan con un nombre y una cédula
	 * específicos. * @param nombre El nombre del cliente.
	 * 
	 * @param cedula El número de identificación del cliente.
	 * @return Un Optional con la lista de clientes que cumplen ambos criterios.
	 */
	public Optional<List<ClienteConcurrente>> findByNombreAndCedula(String nombre, String cedula);

	/**
	 * Verifica la existencia de un cliente concurrente en la base de datos mediante
	 * su cédula. * @param cedula La cédula a comprobar.
	 * 
	 * @return true si el registro existe, false en caso contrario.
	 */
	public boolean existsByCedula(String cedula);
}