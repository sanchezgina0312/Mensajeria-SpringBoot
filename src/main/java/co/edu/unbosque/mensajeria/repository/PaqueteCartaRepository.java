package co.edu.unbosque.mensajeria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.mensajeria.entity.PaqueteCarta;

/**
 * Interfaz de repositorio para la entidad  PaqueteCarta.
 * <p>
 * Define las operaciones de persistencia para gestionar los datos de los 
 * paquetes de tipo carta o documentos en la base de datos. Al extender de 
 *  CrudRepository, proporciona acceso a funcionalidades CRUD estándar 
 * y permite realizar búsquedas personalizadas basadas en la clasificación del 
 * documento y su logística de envío.
 *
 * @version 1.0
 */
public interface PaqueteCartaRepository extends CrudRepository<PaqueteCarta, Long> {

	/**
	 * Busca paquetes de carta filtrando por sus dimensiones o tamaño.
	 * * @param tamanio El tamaño del paquete a consultar (ej. Sobre pequeño, Mediano).
	 * @return Un  Optional que contiene la lista de paquetes de carta encontrados.
	 */
	public Optional<List<PaqueteCarta>> findByTamanio(String tamanio);

	/**
	 * Busca paquetes de carta según la clasificación o el tipo de documento que contienen.
	 * * @param tipoCarta El tipo de carta a consultar (ej. Certificada, Ordinaria).
	 * @return Un  Optional que contiene la lista de paquetes de carta encontrados.
	 */
	public Optional<List<PaqueteCarta>> findByTipoCarta(String tipoCarta);

	/**
	 * Busca paquetes de carta que coincidan simultáneamente con un tamaño y un tipo de carta específicos.
	 * * @param tamanio   El tamaño del sobre o paquete.
	 * @param tipoCarta El tipo de carta contenido.
	 * @return Un  Optional con la lista de paquetes que cumplen ambos criterios.
	 */
	public Optional<List<PaqueteCarta>> findByTamanioAndTipoCarta(String tamanio, String tipoCarta);

	/**
	 * Busca paquetes de carta filtrando por la ubicación exacta de entrega (dirección y ciudad).
	 * * @param direccionDestino La dirección física del destinatario.
	 * @param ciudadDestino    La ciudad de destino del documento.
	 * @return Un  Optional con la lista de paquetes que coinciden con la ubicación proporcionada.
	 */
	public Optional<List<PaqueteCarta>> findByDireccionDestinoAndCiudadDestino(String direccionDestino, String ciudadDestino);
	
	public Optional<List<PaqueteCarta>> findByIdCliente(long idCliente);
}