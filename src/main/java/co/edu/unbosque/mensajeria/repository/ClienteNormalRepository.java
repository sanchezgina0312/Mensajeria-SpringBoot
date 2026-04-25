package co.edu.unbosque.mensajeria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.mensajeria.entity.ClienteNormal;

/**
 * Interfaz de repositorio para la entidad  ClienteNormal.
 * <p>
 * Proporciona el mecanismo de persistencia para gestionar los datos de los 
 * clientes estándar en la base de datos. Al extender de  CrudRepository, 
 * permite realizar operaciones fundamentales de base de datos y define consultas 
 * derivadas para búsquedas específicas por atributos personales y de pedido.
 *
 * @version 1.0
 */
public interface ClienteNormalRepository extends CrudRepository<ClienteNormal, Long> {
	
	/**
	 * Busca clientes normales filtrando por su nombre completo.
	 * * @param nombre El nombre del cliente a buscar.
	 * @return Un  Optional que contiene una lista con los clientes encontrados.
	 */
	public Optional<List<ClienteNormal>> findByNombre(String nombre);

	/**
	 * Busca clientes normales según su número de cédula.
	 * * @param cedula El número de identificación a consultar.
	 * @return Un  Optional que contiene una lista con los clientes encontrados.
	 */
	public Optional<List<ClienteNormal>> findByCedula(String cedula);

	/**
	 * Busca clientes normales asociados a una dirección de correo electrónico.
	 * * @param correo El correo electrónico a consultar.
	 * @return Un  Optional que contiene una lista con los clientes encontrados.
	 */
	public Optional<List<ClienteNormal>> findByCorreo(String correo);	

	/**
	 * Busca clientes normales por su número de teléfono.
	 * * @param telefono El número de teléfono de contacto.
	 * @return Un  Optional que contiene una lista con los clientes encontrados.
	 */
	public Optional<List<ClienteNormal>> findByTelefono(String telefono);		

	/**
	 * Busca clientes normales según el método de pago que utilizan.
	 * * @param metodoPago El método de pago registrado (ej. Efectivo, Tarjeta).
	 * @return Un  Optional que contiene una lista con los clientes encontrados.
	 */
	public Optional<List<ClienteNormal>> findByMetodoPago(String metodoPago);	

	/**
	 * Busca clientes normales que coincidan exactamente con un nombre y una cédula.
	 * * @param nombre El nombre del cliente.
	 * @param cedula El número de identificación del cliente.
	 * @return Un  Optional con la lista de clientes que cumplen ambos criterios.
	 */
	public Optional<List<ClienteNormal>> findByNombreAndCedula(String nombre, String cedula);	

	/**
	 * Verifica si existe un registro de cliente normal en la base de datos con una cédula dada.
	 * * @param cedula La cédula a verificar.
	 * @return true si la cédula ya se encuentra registrada, false en caso contrario.
	 */
	public boolean existsByCedula(String cedula);

}