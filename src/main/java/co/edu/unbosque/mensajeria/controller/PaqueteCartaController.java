package co.edu.unbosque.mensajeria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import co.edu.unbosque.mensajeria.dto.PaqueteCartaDTO;
import co.edu.unbosque.mensajeria.exception.CiudadInvalidaException;
import co.edu.unbosque.mensajeria.exception.DireccionInvalidaException;
import co.edu.unbosque.mensajeria.exception.IdInvalidoException;
import co.edu.unbosque.mensajeria.exception.TamanioInvalidoException;
import co.edu.unbosque.mensajeria.exception.TipoDeCartaInvalidaException;
import co.edu.unbosque.mensajeria.service.PaqueteCartaService;

/**
 * Controlador REST para la gestión de paquetes de tipo carta.
 * <p>
 * Expone endpoints HTTP para realizar operaciones CRUD sobre los paquetes de
 * carta registrados en el sistema de mensajería, así como búsquedas por
 * criterios como tamaño, tipo de carta, dirección y ciudad de destino.
 * </p>
 *
 * <p>
 * Base URL: {@code /paquetecarta}
 * </p>
 *
 * @author Gina Buitrago
 * @version 1.0
 */
@RestController
@RequestMapping("/paquetecarta")
@CrossOrigin(origins = { "http://localhost:8080", "*" })
public class PaqueteCartaController {

	/**
	 * Servicio que contiene la lógica de negocio para los paquetes de carta.
	 */
	@Autowired
	private PaqueteCartaService paqueteCartaSer;

	/**
	 * Constructor por defecto de {@code PaqueteCartaController}.
	 */
	public PaqueteCartaController() {

	}

	/**
	 * Crea un nuevo paquete de carta en el sistema.
	 * <p>
	 * Endpoint: {@code POST /paquetecarta/crear}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/paquetecarta/crear?remitente=Juan&destinatario=Maria&direccionDestino=Calle+10&ciudadDestino=Bogota&fechaEntrega=2024-12-31T23:59:59&tamanio=Pequeño&tipoCarta=Certificada}
	 * </p>
	 *
	 * @param direccionDestino dirección de destino del paquete carta
	 * @param tamanio          tamaño del paquete (p. ej. {@code "Pequeño"},
	 *                         {@code "Mediano"}, {@code "Grande"})
	 * @param ciudadDestino    ciudad de destino del paquete carta
	 * @param tipoCarta        tipo de carta (p. ej. {@code "Certificada"},
	 *                         {@code "Normal"}, {@code "Documento"})
	 * @return {@link ResponseEntity} con un mensaje de resultado y el código HTTP
	 *         correspondiente:
	 *         <ul>
	 *         <li>{@code 201 CREATED} – carta creada correctamente</li>
	 *         <li>{@code 400 BAD_REQUEST} – datos inválidos o error de
	 *         validación</li>
	 *         </ul>
	 */
	// http://localhost:8080/paquetecarta/crear?remitente=Juan&destinatario=Maria&direccionDestino=Calle+10&ciudadDestino=Bogota&fechaEntrega=2024-12-31T23:59:59&tamanio=Pequeño&tipoCarta=Certificada
	@PostMapping("/crear")
	public ResponseEntity<String> crear(@RequestParam String direccionDestino, @RequestParam String tamanio,
			@RequestParam String ciudadDestino, @RequestParam String tipoCarta) {

		try {
			PaqueteCartaDTO dto = new PaqueteCartaDTO();
			dto.setDireccionDestino(direccionDestino);
			dto.setTamanio(tamanio);
			dto.setCiudadDestino(ciudadDestino);
			dto.setTipoCarta(tipoCarta);

			int resultado = paqueteCartaSer.create(dto);

			if (resultado != 0) {
				return new ResponseEntity<>("Carta creada correctamente", HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Error al generar la carta", HttpStatus.BAD_REQUEST);
			}

		} catch (DireccionInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TamanioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TipoDeCartaInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (CiudadInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Retorna la lista completa de paquetes de carta registrados en el sistema.
	 * <p>
	 * Endpoint: {@code GET /paquetecarta/mostrartodo}
	 * </p>
	 * <p>
	 * Ejemplo de uso: {@code http://localhost:8080/paquetecarta/mostrartodo}
	 * </p>
	 *
	 * @return {@link ResponseEntity} con la lista de {@link PaqueteCartaDTO} y el
	 *         código HTTP correspondiente:
	 *         <ul>
	 *         <li>{@code 202 ACCEPTED} – lista retornada exitosamente</li>
	 *         <li>{@code 204 NO_CONTENT} – no hay cartas registradas</li>
	 *         </ul>
	 */
	// http://localhost:8080/paquetecarta/mostrartodo
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<PaqueteCartaDTO>> mostrarTodo() {
		List<PaqueteCartaDTO> lista = paqueteCartaSer.getAll();
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Actualiza los datos de un paquete de carta existente identificado por su ID.
	 * <p>
	 * Endpoint: {@code PUT /paquetecarta/actualizar}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/paquetecarta/actualizar?id=1&remitente=Juan+Actualizado&destinatario=Maria+Lopez&direccionDestino=Carrera+15&ciudadDestino=Medellin&fechaEntrega=2025-01-01T10:00:00&tamanio=Pequeño&tipoCarta=Normal}
	 * </p>
	 *
	 * @param id                   identificador único de la carta a actualizar
	 * @param precioEnvio          nuevo precio de envío de la carta
	 * @param direccionDestino     nueva dirección de destino
	 * @param tamanio              nuevo tamaño del paquete carta
	 * @param fechaCreacionPedido  nueva fecha de creación del pedido
	 * @param fechaEstimadaEntrega nueva fecha estimada de entrega
	 * @param tipoCarta            nuevo tipo de carta
	 * @return {@link ResponseEntity} con un mensaje de resultado y el código HTTP
	 *         correspondiente:
	 *         <ul>
	 *         <li>{@code 202 ACCEPTED} – carta actualizada exitosamente</li>
	 *         <li>{@code 400 BAD_REQUEST} – ID no encontrado, datos inválidos o
	 *         formato incorrecto</li>
	 *         </ul>
	 */
	// http://localhost:8080/paquetecarta/actualizar?id=1&remitente=Juan+Actualizado&destinatario=Maria+Lopez&direccionDestino=Carrera+15&ciudadDestino=Medellin&fechaEntrega=2025-01-01T10:00:00&tamanio=Pequeño&tipoCarta=Normal
	@PutMapping("/actualizar")
	public ResponseEntity<Object> actualizar(@RequestParam Long id, @RequestParam String direccionDestino,
			@RequestParam String ciudadDestino, @RequestParam String tamanio, @RequestParam String tipoCarta) {
		try {
			PaqueteCartaDTO nuevo = new PaqueteCartaDTO();
			nuevo.setDireccionDestino(direccionDestino);
			nuevo.setCiudadDestino(ciudadDestino);
			nuevo.setTamanio(tamanio);
			nuevo.setTipoCarta(tipoCarta);

			int status = paqueteCartaSer.updateById(id, nuevo);
			if (status == 1) {
				return new ResponseEntity<>("Carta actualizada con éxito", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("No se encontró la carta con el ID ingresado", HttpStatus.NOT_FOUND);
			}
		} catch (DireccionInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TamanioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (CiudadInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TipoDeCartaInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (MethodArgumentTypeMismatchException e) {
			return new ResponseEntity<>("El formato del ID no es válido", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Elimina un paquete de carta del sistema según su ID.
	 * <p>
	 * Endpoint: {@code DELETE /paquetecarta/eliminar}
	 * </p>
	 * <p>
	 * Ejemplo de uso: {@code http://localhost:8080/paquetecarta/eliminar?id=1}
	 * </p>
	 *
	 * @param id identificador único de la carta a eliminar
	 * @return {@link ResponseEntity} con un mensaje de resultado y el código HTTP
	 *         correspondiente:
	 *         <ul>
	 *         <li>{@code 202 ACCEPTED} – carta eliminada exitosamente</li>
	 *         <li>{@code 400 BAD_REQUEST} – ID no encontrado, inválido o con
	 *         formato incorrecto</li>
	 *         </ul>
	 */
	// http://localhost:8080/paquetecarta/eliminar?id=1
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> delete(@RequestParam Long id) {
		try {
			int status = paqueteCartaSer.deleteById(id);
			if (status == 0) {
				return new ResponseEntity<>("Carta eliminada con éxito", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>("No se encontró la carta con el ID ingresado" + id, HttpStatus.BAD_REQUEST);
			}
		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (MethodArgumentTypeMismatchException e) {
			return new ResponseEntity<>("El valor debe ser un número entero", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Busca paquetes de carta según su tamaño.
	 * <p>
	 * Endpoint: {@code GET /paquetecarta/buscarportamanio}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/paquetecarta/buscarportamanio?tamanio=Pequeño}
	 * </p>
	 *
	 * @param tamanio tamaño de la carta a buscar (p. ej. {@code "Pequeño"},
	 *                {@code "Mediano"}, {@code "Grande"})
	 * @return {@link ResponseEntity} con la lista de {@link PaqueteCartaDTO}
	 *         encontrados y el código HTTP:
	 *         <ul>
	 *         <li>{@code 202 ACCEPTED} – se encontraron resultados</li>
	 *         <li>{@code 204 NO_CONTENT} – no se encontraron coincidencias</li>
	 *         <li>{@code 400 BAD_REQUEST} – tamaño inválido</li>
	 *         </ul>
	 */
	// http://localhost:8080/paquetecarta/buscarportamanio?tamanio=Pequeño
	@GetMapping("/buscarportamanio")
	public ResponseEntity<Object> buscarPorTamanio(@RequestParam String tamanio) {

		try {
			List<PaqueteCartaDTO> lista = paqueteCartaSer.findByTamanio(tamanio);
			if (!lista.isEmpty()) {
				return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
			}
		} catch (TamanioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Busca paquetes de carta según el tipo de carta.
	 * <p>
	 * Endpoint: {@code GET /paquetecarta/buscarportipocarta}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/paquetecarta/buscarportipocarta?tipoCarta=Certificada}
	 * </p>
	 *
	 * @param tipoCarta tipo de carta a buscar (p. ej. {@code "Certificada"},
	 *                  {@code "Normal"}, {@code "Documento"})
	 * @return {@link ResponseEntity} con la lista de {@link PaqueteCartaDTO}
	 *         encontrados y el código HTTP:
	 *         <ul>
	 *         <li>{@code 202 ACCEPTED} – se encontraron resultados</li>
	 *         <li>{@code 204 NO_CONTENT} – no se encontraron coincidencias</li>
	 *         <li>{@code 400 BAD_REQUEST} – tipo de carta inválido</li>
	 *         </ul>
	 */
	// http://localhost:8080/paquetecarta/buscarportipocarta?tipoCarta=Certificada
	@GetMapping("/buscarportipocarta")
	public ResponseEntity<Object> buscarPorTipoCarta(@RequestParam String tipoCarta) {

		try {
			List<PaqueteCartaDTO> lista = paqueteCartaSer.findByTipoCarta(tipoCarta);
			if (!lista.isEmpty()) {
				return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
			}
		} catch (TipoDeCartaInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Busca paquetes de carta filtrando simultáneamente por tamaño y tipo de carta.
	 * <p>
	 * Endpoint: {@code GET /paquetecarta/buscarportamanioytipocarta}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/paquetecarta/buscarportamanioytipocarta?tamanio=Pequeño&tipoCarta=Documento}
	 * </p>
	 *
	 * @param tamanio   tamaño de la carta a buscar
	 * @param tipoCarta tipo de carta a buscar
	 * @return {@link ResponseEntity} con la lista de {@link PaqueteCartaDTO}
	 *         encontrados y el código HTTP:
	 *         <ul>
	 *         <li>{@code 202 ACCEPTED} – se encontraron resultados</li>
	 *         <li>{@code 204 NO_CONTENT} – no se encontraron coincidencias</li>
	 *         <li>{@code 400 BAD_REQUEST} – tamaño inválido</li>
	 *         </ul>
	 */
	// http://localhost:8080/paquetecarta/buscarportamanioytipocarta?tamanio=Pequeño&tipoCarta=Documento
	@GetMapping("/buscarportamanioytipocarta")
	public ResponseEntity<Object> buscarPorTamanioAndTipoCarta(@RequestParam String tamanio,
			@RequestParam String tipoCarta) {

		try {
			List<PaqueteCartaDTO> lista = paqueteCartaSer.findByTamanioAndTipoCarta(tamanio, tipoCarta);
			if (!lista.isEmpty()) {
				return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
			}
		} catch (TamanioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * Busca y retorna un paquete de carta específico por su ID para realizar
	 * seguimiento.
	 * <p>
	 * Endpoint: {@code GET /paquetecarta/seguimientoid}
	 * </p>
	 * <p>
	 * Ejemplo de uso: {@code http://localhost:8080/paquetecarta/seguimientoid?id=1}
	 * </p>
	 *
	 * @param id identificador único de la carta a consultar
	 * @return {@link ResponseEntity} con el {@link PaqueteCartaDTO} encontrado y el
	 *         código HTTP:
	 *         <ul>
	 *         <li>{@code 200 OK} – carta encontrada exitosamente</li>
	 *         <li>{@code 404 NOT_FOUND} – no se encontró una carta con el ID
	 *         ingresado</li>
	 *         <li>{@code 400 BAD_REQUEST} – ID con formato incorrecto o tamaño
	 *         inválido</li>
	 *         </ul>
	 */
	// http://localhost:8080/paquetecarta/seguimientoid?id=1
	@GetMapping("/seguimientoid")
	public ResponseEntity<Object> seguimientoId(@RequestParam Long id) {

		try {
			PaqueteCartaDTO p = paqueteCartaSer.findById(id);
			if (p != null) {
				return new ResponseEntity<>(p, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("No se encontró la carta con el ID ingresado", HttpStatus.NOT_FOUND);
			}
		} catch (TamanioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (MethodArgumentTypeMismatchException e) {
			return new ResponseEntity<>("El valor debe ser un número entero", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Busca paquetes de carta filtrando simultáneamente por dirección y ciudad de
	 * destino.
	 * <p>
	 * Endpoint: {@code GET /paquetecarta/buscardireccionyciudad}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/paquetecarta/buscardireccionyciudad?dir=Calle+123&ciudad=Bogota}
	 * </p>
	 *
	 * @param dir    dirección de destino de la carta a buscar
	 * @param ciudad ciudad de destino de la carta a buscar
	 * @return {@link ResponseEntity} con la lista de {@link PaqueteCartaDTO}
	 *         encontrados y el código HTTP:
	 *         <ul>
	 *         <li>{@code 200 OK} – se encontraron resultados</li>
	 *         <li>{@code 204 NO_CONTENT} – no se encontraron coincidencias</li>
	 *         <li>{@code 400 BAD_REQUEST} – tamaño inválido en la búsqueda</li>
	 *         </ul>
	 */
	// http://localhost:8080/paquetecarta/buscardireccionyciudad?dir=Calle+123&ciudad=Bogota
	@GetMapping("/buscardireccionyciudad")
	public ResponseEntity<Object> buscarDireccionYCiudad(@RequestParam String dir, @RequestParam String ciudad) {

		try {
			List<PaqueteCartaDTO> lista = paqueteCartaSer.findByDireccionDestinoAndCiudadDestino(dir, ciudad);

			if (lista.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(lista, HttpStatus.OK);
			}
		} catch (TamanioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}