package co.edu.unbosque.mensajeria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.mensajeria.entity.Conductor;

/**
 * Interfaz de repositorio para la entidad  Conductor.
 * <p>
 * Define los métodos necesarios para gestionar la persistencia de los conductores 
 * en la base de datos. Al extender de CrudRepository, proporciona acceso 
 * a operaciones CRUD estándar y permite la ejecución de consultas personalizadas 
 * mediante Query Methods.
 *
 * @version 1.0
 */
public interface ConductorRepository extends CrudRepository<Conductor, Long> {
	
	/**
	 * Busca conductores filtrando por su nombre completo.
	 * * @param nombre El nombre del conductor a buscar.
	 * @return Un  Optiona} que contiene una lista de los conductores encontrados.
	 */
	public Optional<List<Conductor>> findByNombre(String nombre);

	/**
	 * Busca conductores según su número de cédula.
	 * * @param cedula El número de identificación a consultar.
	 * @return Un  Optional que contiene una lista de los conductores encontrados.
	 */
	public Optional<List<Conductor>> findByCedula(String cedula);

	/**
	 * Busca conductores asociados a una dirección de correo electrónico.
	 * * @param correo El correo electrónico a buscar.
	 * @return Un  Optional que contiene una lista de los conductores encontrados.
	 */
	public Optional<List<Conductor>> findByCorreo(String correo);	

	/**
	 * Busca conductores por su número de teléfono.
	 * * @param telefono El número de teléfono de contacto.
	 * @return Un  Optional que contiene una lista de los conductores encontrados.
	 */
	public Optional<List<Conductor>> findByTelefono(String telefono);		

	/**
	 * Busca conductores registrados con una placa de vehículo específica.
	 * * @param placaVehiculo La placa del vehículo a consultar.
	 * @return Un  Optional que contiene una lista de los conductores encontrados.
	 */
	public Optional<List<Conductor>> findByPlacaVehiculo(String placaVehiculo);
	
	/**
	 * Busca conductores que coincidan exactamente con un nombre y una cédula dados.
	 * * @param nombre El nombre del conductor.
	 * @param cedula El número de identificación del conductor.
	 * @return Un  Optional con la lista de conductores que cumplen ambos criterios.
	 */
	public Optional<List<Conductor>> findByNombreAndCedula(String nombre, String cedula);	
	
	/**
	 * Verifica si existe un registro de conductor en la base de datos con una cédula específica.
	 * * @param cedula La cédula a verificar.
	 * @return true si el conductor ya se encuentra registrado, false en caso contrario.
	 */
	public boolean existsByCedula(String cedula);
}