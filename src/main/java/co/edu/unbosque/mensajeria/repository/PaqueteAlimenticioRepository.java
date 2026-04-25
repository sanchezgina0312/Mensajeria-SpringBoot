package co.edu.unbosque.mensajeria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.mensajeria.entity.PaqueteAlimenticio;

/**
 * Interfaz de repositorio para la entidad  PaqueteAlimenticio.
 * <p>
 * Proporciona el mecanismo de persistencia para gestionar los datos de los 
 * paquetes alimenticios en la base de datos. Al extender de  CrudRepository, 
 * permite realizar operaciones CRUD fundamentales y define consultas especializadas 
 * para el filtrado de envíos según su contenido alimentario y logística de entrega.
 *
 * @version 1.0
 */
public interface PaqueteAlimenticioRepository extends CrudRepository<PaqueteAlimenticio, Long> {

	/**
	 * Busca paquetes alimenticios según sus dimensiones o tamaño.
	 * * @param tamanio El tamaño del paquete a consultar.
	 * @return Un  Optional que contiene la lista de paquetes alimenticios encontrados.
	 */
	public Optional<List<PaqueteAlimenticio>> findByTamanio(String tamanio);

	/**
	 * Busca paquetes alimenticios filtrando por su urgencia de envío para el mismo día.
	 * * @param seEnviaHoy true si el paquete debe enviarse hoy, false en caso contrario.
	 * @return Un  Optional que contiene la lista de paquetes alimenticios encontrados.
	 */
	public Optional<List<PaqueteAlimenticio>> findBySeEnviaHoy(boolean seEnviaHoy);

	/**
	 * Busca paquetes alimenticios según la categoría o tipo de alimento que contienen.
	 * * @param tipoDeAlimento El tipo de alimento a consultar.
	 * @return Un Optional que contiene la lista de paquetes alimenticios encontrados.
	 */
	public Optional<List<PaqueteAlimenticio>> findByTipoDeAlimento(String tipoDeAlimento);

	/**
	 * Busca paquetes alimenticios que coincidan con un tamaño y un tipo de alimento específicos.
	 * * @param tamanio        El tamaño del paquete.
	 * @param tipoDeAlimento El tipo de alimento contenido.
	 * @return Un  Optional con la lista de paquetes que cumplen ambos criterios.
	 */
	public Optional<List<PaqueteAlimenticio>> findByTamanioAndTipoDeAlimento(String tamanio, String tipoDeAlimento);

	/**
	 * Busca paquetes alimenticios filtrando por la ubicación exacta de destino (dirección y ciudad).
	 * * @param direccionDestino La dirección física de entrega.
	 * @param ciudadDestino    La ciudad de destino.
	 * @return Un  Optional con la lista de paquetes que coinciden con la ubicación proporcionada.
	 */
	public Optional<List<PaqueteAlimenticio>> findByDireccionDestinoAndCiudadDestino(String direccionDestino, String ciudadDestino);
	
	public Optional<List<PaqueteAlimenticio>> findByIdCliente(long idCliente);
}