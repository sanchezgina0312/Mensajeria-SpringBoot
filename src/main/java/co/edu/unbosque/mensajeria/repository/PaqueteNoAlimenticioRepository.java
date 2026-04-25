package co.edu.unbosque.mensajeria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.mensajeria.entity.PaqueteNoAlimenticio;

/**
 * Interfaz de repositorio para la entidad  PaqueteNoAlimenticio.
 * <p>
 * Define las operaciones de persistencia para gestionar los datos de los 
 * paquetes que no contienen alimentos en la base de datos. Al extender de 
 *  CrudRepository, proporciona acceso a funcionalidades CRUD estándar 
 * y permite realizar búsquedas personalizadas basadas en la fragilidad del 
 * contenido y su logística de envío.
 *
 * @version 1.0
 */
public interface PaqueteNoAlimenticioRepository extends CrudRepository<PaqueteNoAlimenticio, Long> {

	/**
	 * Busca paquetes no alimenticios filtrando por sus dimensiones o tamaño.
	 * * @param tamanio El tamaño del paquete a consultar (ej. Grande, Mediano).
	 * @return Un  Optional que contiene la lista de paquetes no alimenticios encontrados.
	 */
	public Optional<List<PaqueteNoAlimenticio>> findByTamanio(String tamanio);

	/**
	 * Busca paquetes no alimenticios según su nivel de fragilidad.
	 * * @param esFragil true si el paquete es frágil y requiere manejo especial, false de lo contrario.
	 * @return Un  Optional que contiene la lista de paquetes no alimenticios encontrados.
	 */
	public Optional<List<PaqueteNoAlimenticio>> findByEsFragil(boolean esFragil);

	/**
	 * Busca paquetes no alimenticios que coincidan simultáneamente con un tamaño y un estado de fragilidad específicos.
	 * * @param tamanio  El tamaño del paquete.
	 * @param esFragil El estado de fragilidad del contenido.
	 * @return Un  Optional con la lista de paquetes que cumplen ambos criterios.
	 */
	public Optional<List<PaqueteNoAlimenticio>> findByTamanioAndEsFragil(String tamanio, boolean esFragil);

	/**
	 * Busca paquetes no alimenticios filtrando por la ubicación exacta de entrega (dirección y ciudad).
	 * * @param direccionDestino La dirección física del destinatario.
	 * @param ciudadDestino    La ciudad de destino del paquete.
	 * @return Un  Optional con la lista de paquetes que coinciden con la ubicación proporcionada.
	 */
	public Optional<List<PaqueteNoAlimenticio>> findByDireccionDestinoAndCiudadDestino(String direccionDestino, String ciudadDestino);

	public Optional<List<PaqueteNoAlimenticio>> findByIdCliente(long idCliente);
}