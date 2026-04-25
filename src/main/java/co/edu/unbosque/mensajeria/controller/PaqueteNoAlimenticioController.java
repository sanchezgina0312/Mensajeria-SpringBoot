package co.edu.unbosque.mensajeria.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import co.edu.unbosque.mensajeria.dto.PaqueteNoAlimenticioDTO;
import co.edu.unbosque.mensajeria.exception.CiudadInvalidaException;
import co.edu.unbosque.mensajeria.exception.DireccionInvalidaException;
import co.edu.unbosque.mensajeria.exception.IdInvalidoException;
import co.edu.unbosque.mensajeria.exception.TamanioInvalidoException;
import co.edu.unbosque.mensajeria.service.PaqueteNoAlimenticioService;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador REST para la gestión de paquetes no alimenticios.
 * <p>
 * Expone endpoints HTTP para realizar operaciones CRUD sobre los paquetes no
 * alimenticios registrados en el sistema de mensajería, así como búsquedas por
 * criterios como tamaño, fragilidad, dirección y ciudad de destino.
 * </p>
 *
 * <p>
 * Base URL: {@code /paquetenoalimenticio}
 * </p>
 *
 * @version 1.0
 */
@RestController
@RequestMapping("/paquetenoalimenticio")
@CrossOrigin(origins = { "http://localhost:8080", "*" })
public class PaqueteNoAlimenticioController {

	/**
	 * Servicio que contiene la lógica de negocio para los paquetes no alimenticios.
	 */
	@Autowired
	private PaqueteNoAlimenticioService paqueteNoAlimenticioSer;

	/**
	 * Constructor por defecto de {@code PaqueteNoAlimenticioController}.
	 */
	public PaqueteNoAlimenticioController() {
	}

	/**
	 * Crea un nuevo paquete no alimenticio en el sistema.
	 * <p>
	 * Endpoint: {@code POST /paquetenoalimenticio/crear}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/paquetenoalimenticio/crear?remitente=Luis&destinatario=Carlos&direccionDestino=Calle+20&ciudadDestino=Cali&fechaEntrega=2024-08-15T10:00:00&tamanio=Grande&esFragil=true}
	 * </p>
	 *
	 * @param direccionDestino dirección de destino del paquete
	 * @param tamanio          tamaño del paquete (p. ej. {@code "Grande"},
	 *                         {@code "Mediano"}, {@code "Pequeño"})
	 * @param ciudadDestino    ciudad de destino del paquete
	 * @param esFragil         indica si el paquete es frágil ({@code true} o
	 *                         {@code false})
	 * @return {@link ResponseEntity} con un mensaje de resultado y el código HTTP
	 *         correspondiente:
	 *         <ul>
	 *         <li>{@code 201 CREATED} – paquete registrado exitosamente</li>
	 *         <li>{@code 400 BAD_REQUEST} – datos inválidos o formato
	 *         incorrecto</li>
	 *         </ul>
	 */
	// http://localhost:8080/paquetenoalimenticio/crear?remitente=Luis&destinatario=Carlos&direccionDestino=Calle+20&ciudadDestino=Cali&fechaEntrega=2024-08-15T10:00:00&tamanio=Grande&esFragil=true
	@PostMapping("/crear")
	public ResponseEntity<String> crear(@RequestParam String direccionDestino, @RequestParam String tamanio,
			@RequestParam String ciudadDestino, @RequestParam boolean esFragil) {

		try {
			PaqueteNoAlimenticioDTO dto = new PaqueteNoAlimenticioDTO();
			dto.setDireccionDestino(direccionDestino);
			dto.setTamanio(tamanio);
			dto.setCiudadDestino(ciudadDestino);
			dto.setEsFragil(esFragil);

			int resultado = paqueteNoAlimenticioSer.create(dto);

			if (resultado != 0) {
				return new ResponseEntity<>("Paquete no alimenticio registrado con éxito.", HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("No se pudo registrar el paquete.", HttpStatus.BAD_REQUEST);
			}

		} catch (DireccionInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TamanioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (CiudadInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (MethodArgumentTypeMismatchException e) {
			return new ResponseEntity<>("El valor de debe ser true o false", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Retorna la lista completa de paquetes no alimenticios registrados en el
	 * sistema.
	 * <p>
	 * Endpoint: {@code GET /paquetenoalimenticio/mostrartodo}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/paquetenoalimenticio/mostrartodo}
	 * </p>
	 *
	 * @return {@link ResponseEntity} con la lista de
	 *         {@link PaqueteNoAlimenticioDTO} y el código HTTP correspondiente:
	 *         <ul>
	 *         <li>{@code 202 ACCEPTED} – lista retornada exitosamente</li>
	 *         <li>{@code 204 NO_CONTENT} – no hay paquetes registrados</li>
	 *         </ul>
	 */
	// http://localhost:8080/paquetenoalimenticio/mostrartodo
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<PaqueteNoAlimenticioDTO>> mostrarTodo() {
		List<PaqueteNoAlimenticioDTO> lista = paqueteNoAlimenticioSer.getAll();
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Actualiza los datos de un paquete no alimenticio existente identificado por
	 * su ID.
	 * <p>
	 * Endpoint: {@code PUT /paquetenoalimenticio/actualizar}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/paquetenoalimenticio/actualizar?id=1&remitente=Luis+M&destinatario=Carlos+P&direccionDestino=Carrera+50&ciudadDestino=Buga&fechaEntrega=2024-08-16T14:00:00&tamanio=Mediano&esFragil=false}
	 * </p>
	 *
	 * @param id                   identificador único del paquete a actualizar
	 * @param esFragil             indica si el paquete es frágil
	 * @param precioEnvio          nuevo precio de envío del paquete
	 * @param direccionDestino     nueva dirección de destino
	 * @param tamanio              nuevo tamaño del paquete
	 * @param fechaCreacionPedido  nueva fecha de creación del pedido
	 * @param fechaEstimadaEntrega nueva fecha estimada de entrega
	 * @return {@link ResponseEntity} con un mensaje de resultado y el código HTTP
	 *         correspondiente:
	 *         <ul>
	 *         <li>{@code 202 ACCEPTED} – paquete actualizado exitosamente</li>
	 *         <li>{@code 400 BAD_REQUEST} – ID no encontrado, datos inválidos o
	 *         formato incorrecto</li>
	 *         </ul>
	 */
	// http://localhost:8080/paquetenoalimenticio/actualizar?id=1&remitente=Luis+M&destinatario=Carlos+P&direccionDestino=Carrera+50&ciudadDestino=Buga&fechaEntrega=2024-08-16T14:00:00&tamanio=Mediano&esFragil=false
	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizar(@RequestParam Long id, @RequestParam String direccionDestino,
			@RequestParam String ciudadDestino, @RequestParam String tamanio) {
		try {
			PaqueteNoAlimenticioDTO nuevo = new PaqueteNoAlimenticioDTO();
			nuevo.setDireccionDestino(direccionDestino);
			nuevo.setCiudadDestino(ciudadDestino);
			nuevo.setTamanio(tamanio);

			int status = paqueteNoAlimenticioSer.updateById(id, nuevo);
			if (status == 1) {
				return new ResponseEntity<>("Paquete no alimenticio actualizado con éxito", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("No se encontró el paquete con el ID ingresado", HttpStatus.NOT_FOUND);
			}
		} catch (DireccionInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TamanioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (CiudadInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (MethodArgumentTypeMismatchException e) {
			return new ResponseEntity<>("El formato del ID no es válido", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Elimina un paquete no alimenticio del sistema según su ID.
	 * <p>
	 * Endpoint: {@code DELETE /paquetenoalimenticio/eliminar}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/paquetenoalimenticio/eliminar?id=1}
	 * </p>
	 *
	 * @param id identificador único del paquete a eliminar
	 * @return {@link ResponseEntity} con un mensaje de resultado y el código HTTP
	 *         correspondiente:
	 *         <ul>
	 *         <li>{@code 202 ACCEPTED} – paquete eliminado exitosamente</li>
	 *         <li>{@code 400 BAD_REQUEST} – ID no encontrado, inválido o con
	 *         formato incorrecto</li>
	 *         </ul>
	 */
	// http://localhost:8080/paquetenoalimenticio/eliminar?id=1
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> delete(@RequestParam Long id) {
		try {
			int status = paqueteNoAlimenticioSer.deleteById(id);
			if (status == 0) {
				return new ResponseEntity<>("Paquete no alimenticio eliminado con éxito", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>("No se encontró el paquete no alimenticio con el ID ingresado: " + id,
						HttpStatus.BAD_REQUEST);
			}
		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (MethodArgumentTypeMismatchException e) {
			return new ResponseEntity<>("El valor de debe ser un número entero", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Busca paquetes no alimenticios según su tamaño.
	 * <p>
	 * Endpoint: {@code GET /paquetenoalimenticio/buscarportamanio}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/paquetenoalimenticio/buscarportamanio?tamanio=Grande}
	 * </p>
	 *
	 * @param tamanio tamaño del paquete a buscar (p. ej. {@code "Grande"},
	 *                {@code "Mediano"}, {@code "Pequeño"})
	 * @return {@link ResponseEntity} con la lista de
	 *         {@link PaqueteNoAlimenticioDTO} encontrados y el código HTTP:
	 *         <ul>
	 *         <li>{@code 202 ACCEPTED} – se encontraron resultados</li>
	 *         <li>{@code 204 NO_CONTENT} – no se encontraron coincidencias</li>
	 *         <li>{@code 400 BAD_REQUEST} – tamaño inválido</li>
	 *         </ul>
	 */
	// http://localhost:8080/paquetenoalimenticio/buscarportamanio?tamanio=Grande
	@GetMapping("/buscarportamanio")
	public ResponseEntity<Object> buscarPorTamanio(@RequestParam String tamanio) {

		try {
			List<PaqueteNoAlimenticioDTO> lista = paqueteNoAlimenticioSer.findByTamanio(tamanio);
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
	 * Busca paquetes no alimenticios según si son frágiles o no.
	 * <p>
	 * Endpoint: {@code GET /paquetenoalimenticio/buscarporesfragil}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/paquetenoalimenticio/buscarporesfragil?esFragil=true}
	 * </p>
	 *
	 * @param esFragil valor booleano que indica si el paquete es frágil
	 *                 ({@code true} o {@code false})
	 * @return {@link ResponseEntity} con la lista de
	 *         {@link PaqueteNoAlimenticioDTO} encontrados y el código HTTP:
	 *         <ul>
	 *         <li>{@code 202 ACCEPTED} – se encontraron resultados</li>
	 *         <li>{@code 204 NO_CONTENT} – no se encontraron coincidencias</li>
	 *         <li>{@code 400 BAD_REQUEST} – el valor no es un booleano válido</li>
	 *         </ul>
	 */
	// http://localhost:8080/paquetenoalimenticio/buscarporesfragil?esFragil=true
	@GetMapping("/buscarporesfragil")
	public ResponseEntity<Object> buscarPorEsFragil(@RequestParam boolean esFragil) {
		try {
			List<PaqueteNoAlimenticioDTO> lista = paqueteNoAlimenticioSer.findByEsFragil(esFragil);
			if (!lista.isEmpty()) {
				return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
			}

		} catch (MethodArgumentTypeMismatchException e) {
			return new ResponseEntity<>("El valor debe ser true o false", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Busca paquetes no alimenticios filtrando simultáneamente por tamaño y
	 * fragilidad.
	 * <p>
	 * Endpoint: {@code GET /paquetenoalimenticio/buscarportamanioyfragil}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/paquetenoalimenticio/buscarportamanioyfragil?tamanio=Grande&esFragil=true}
	 * </p>
	 *
	 * @param tamanio  tamaño del paquete a buscar
	 * @param esFragil indica si el paquete es frágil ({@code true} o {@code false})
	 * @return {@link ResponseEntity} con la lista de
	 *         {@link PaqueteNoAlimenticioDTO} encontrados y el código HTTP:
	 *         <ul>
	 *         <li>{@code 202 ACCEPTED} – se encontraron resultados</li>
	 *         <li>{@code 204 NO_CONTENT} – no se encontraron coincidencias</li>
	 *         <li>{@code 400 BAD_REQUEST} – tamaño inválido o valor booleano
	 *         incorrecto</li>
	 *         </ul>
	 */
	// http://localhost:8080/paquetenoalimenticio/buscarportamanioyfragil?tamanio=Grande&esFragil=true
	@GetMapping("/buscarportamanioyfragil")
	public ResponseEntity<Object> buscarPorTamanioAndFragil(@RequestParam String tamanio,
			@RequestParam boolean esFragil) {

		try {
			List<PaqueteNoAlimenticioDTO> lista = paqueteNoAlimenticioSer.findByTamanioAndEsFragil(tamanio, esFragil);
			if (!lista.isEmpty()) {
				return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
			}

		} catch (TamanioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (MethodArgumentTypeMismatchException e) {
			return new ResponseEntity<>("El valor de debe ser true o false", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Busca y retorna un paquete no alimenticio específico por su ID para realizar
	 * seguimiento.
	 * <p>
	 * Endpoint: {@code GET /paquetenoalimenticio/seguimientoid}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/paquetenoalimenticio/seguimientoid?id=1}
	 * </p>
	 *
	 * @param id identificador único del paquete a consultar
	 * @return {@link ResponseEntity} con el {@link PaqueteNoAlimenticioDTO}
	 *         encontrado y el código HTTP:
	 *         <ul>
	 *         <li>{@code 200 OK} – paquete encontrado exitosamente</li>
	 *         <li>{@code 404 NOT_FOUND} – no se encontró un paquete con el ID
	 *         ingresado</li>
	 *         <li>{@code 400 BAD_REQUEST} – ID con formato incorrecto o
	 *         inválido</li>
	 *         </ul>
	 */
	// http://localhost:8080/paquetenoalimenticio/buscarporidid?id=1
	@GetMapping("/buscarporid")
	public ResponseEntity<Object> buscarPorId(@RequestParam Long id) {
		try {
			PaqueteNoAlimenticioDTO p = paqueteNoAlimenticioSer.findById(id);

			if (p != null) {
				return new ResponseEntity<>(p, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("No se encontró el paquete no alimenticio con el ID ingresado",
						HttpStatus.NOT_FOUND);
			}

		} catch (MethodArgumentTypeMismatchException e) {
			return new ResponseEntity<>("El valor de debe ser un número entero", HttpStatus.BAD_REQUEST);
		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Busca paquetes no alimenticios filtrando simultáneamente por dirección y
	 * ciudad de destino.
	 * <p>
	 * Endpoint: {@code GET /paquetenoalimenticio/buscardireccionyciudad}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/paquetenoalimenticio/buscardireccionyciudad?dir=Calle+20&ciudad=Cali}
	 * </p>
	 *
	 * @param dir    dirección de destino del paquete a buscar
	 * @param ciudad ciudad de destino del paquete a buscar
	 * @return {@link ResponseEntity} con la lista de
	 *         {@link PaqueteNoAlimenticioDTO} encontrados y el código HTTP:
	 *         <ul>
	 *         <li>{@code 200 OK} – se encontraron resultados</li>
	 *         <li>{@code 204 NO_CONTENT} – no se encontraron coincidencias</li>
	 *         <li>{@code 400 BAD_REQUEST} – dirección inválida</li>
	 *         </ul>
	 */
	// http://localhost:8080/paquetenoalimenticio/buscardireccionyciudad?dir=Calle+20&ciudad=Cali
	@GetMapping("/buscardireccionyciudad")
	public ResponseEntity<Object> buscarDireccionYCiudad(@RequestParam String dir, @RequestParam String ciudad) {
		try {
			List<PaqueteNoAlimenticioDTO> lista = paqueteNoAlimenticioSer.findByDireccionDestinoAndCiudadDestino(dir,
					ciudad);

			if (lista.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(lista, HttpStatus.OK);
			}

		} catch (DireccionInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/historialporid")
    public ResponseEntity<List<PaqueteNoAlimenticioDTO>> verHistorial(@RequestParam long idCliente) {
        List<PaqueteNoAlimenticioDTO> lista = paqueteNoAlimenticioSer.findByIdCLiente(idCliente);
        
        if(lista.isEmpty()) {
        	return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        } else {
        	return new ResponseEntity<>(lista, HttpStatus.OK);
        }
    }

}