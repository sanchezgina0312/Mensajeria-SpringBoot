package co.edu.unbosque.mensajeria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.mensajeria.entity.ClientePremium;

/**
 * Interfaz de repositorio para la entidad ClientePremium.
 * <p>
 * Define las operaciones de persistencia para gestionar los datos de los 
 * clientes premium en la base de datos. Al extender de  CrudRepository, 
 * proporciona funcionalidades automáticas de CRUD y métodos de búsqueda 
 * personalizados de Spring Data JPA.
 *
 * @version 1.0
 */
public interface ClientePremiumRepository extends CrudRepository<ClientePremium, Long> {

	/**
	 * Busca clientes premium filtrando por su nombre completo.
	 * * @param nombre El nombre del cliente a buscar.
	 * @return Un  Optional que contiene la lista de clientes encontrados.
	 */
	public Optional<List<ClientePremium>> findByNombre(String nombre);

	/**
	 * Busca clientes premium según su número de cédula.
	 * * @param cedula El número de identificación a consultar.
	 * @return Un  Optional que contiene la lista de clientes encontrados.
	 */
	public Optional<List<ClientePremium>> findByCedula(String cedula);

	/**
	 * Busca clientes premium asociados a una dirección de correo electrónico.
	 * * @param correo El correo electrónico a consultar.
	 * @return Un  Optional que contiene la lista de clientes encontrados.
	 */
	public Optional<List<ClientePremium>> findByCorreo(String correo);	

	/**
	 * Busca clientes premium por su número de teléfono.
	 * * @param telefono El número de teléfono de contacto.
	 * @return Un  Optional que contiene la lista de clientes encontrados.
	 */
	public Optional<List<ClientePremium>> findByTelefono(String telefono);		

	/**
	 * Busca clientes premium según el método de pago registrado.
	 * * @param metodoPago El método de pago utilizado (ej. Efectivo, Tarjeta).
	 * @return Un  Optional que contiene la lista de clientes encontrados.
	 */
	public Optional<List<ClientePremium>> findByMetodoPago(String metodoPago);	

	/**
	 * Busca clientes premium filtrando por el tipo de pedido realizado.
	 * * @param tipoPedido La categoría o tipo del pedido.
	 * @return Un  Optional que contiene la lista de clientes encontrados.
	 */
	public Optional<List<ClientePremium>> findByTipoPedido(String tipoPedido);	
	
	/**
	 * Busca clientes premium que coincidan exactamente con un nombre y una cédula.
	 * * @param nombre El nombre del cliente.
	 * @param cedula El número de identificación del cliente.
	 * @return Un  Optional con la lista de clientes que cumplen ambos criterios.
	 */
	public Optional<List<ClientePremium>> findByNombreAndCedula(String nombre, String cedula);	

	/**
	 * Busca clientes premium filtrando por la combinación de tipo de pedido y método de pago.
	 * * @param tipoPedido El tipo de pedido registrado.
	 * @param metodoPago El método de pago utilizado.
	 * @return Un  Optional con la lista de clientes que cumplen ambos criterios.
	 */
	public Optional<List<ClientePremium>> findByTipoPedidoAndMetodoPago(String tipoPedido, String metodoPago);
	
	/**
	 * Verifica si existe un registro de cliente premium en la base de datos con una cédula dada.
	 * * @param cedula La cédula a verificar.
	 * @return true si el cliente ya está registrado, false en caso contrario.
	 */
	public boolean existsByCedula(String cedula);
}