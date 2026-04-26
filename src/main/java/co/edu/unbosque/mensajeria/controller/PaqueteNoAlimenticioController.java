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
	 * Crea un nuevo paquete no alimenticio en el sistema vinculado a un cliente.
	 * <p>
	 * Endpoint: {@code POST /paquetenoalimenticio/crear}
	 * </p>
	 *
	 * @param idCliente        identificador único del cliente que solicita el envío
	 * @param direccionDestino dirección de destino del paquete
	 * @param tamanio          tamaño del paquete (p. ej. {@code "Grande"},
	 * {@code "Mediano"}, {@code "Pequeño"})
	 * @param ciudadDestino    ciudad de destino del paquete
	 * @param esFragil         indica si el paquete es frágil ({@code true} o
	 * {@code false})
	 * @return {@link ResponseEntity} con un mensaje de resultado y el código HTTP
	 * correspondiente.
	 */
	@PostMapping("/crear")
	public ResponseEntity<String> crear(
			@RequestParam long idCliente, 
			@RequestParam String direccionDestino, 
			@RequestParam String tamanio,
			@RequestParam String ciudadDestino, 
			@RequestParam boolean esFragil) {

		try {
			PaqueteNoAlimenticioDTO dto = new PaqueteNoAlimenticioDTO();
			dto.setIdCliente(idCliente);
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

		} catch (DireccionInvalidaException | TamanioInvalidoException | CiudadInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (MethodArgumentTypeMismatchException e) {
			return new ResponseEntity<>("El valor de debe ser true o false o el ID no es válido", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Retorna la lista completa de paquetes no alimenticios registrados.
	 */
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
	 * Actualiza los datos de un paquete no alimenticio existente identificado por su ID.
	 */
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
		} catch (DireccionInvalidaException | TamanioInvalidoException | IdInvalidoException | CiudadInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (MethodArgumentTypeMismatchException e) {
			return new ResponseEntity<>("El formato del ID no es válido", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Elimina un paquete no alimenticio del sistema según su ID.
	 */
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
	 */
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
	 */
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
	 * Busca paquetes no alimenticios filtrando simultáneamente por tamaño y fragilidad.
	 */
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
	 * Busca y retorna un paquete no alimenticio específico por su ID.
	 */
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
	 * Busca paquetes no alimenticios filtrando por dirección y ciudad de destino.
	 */
	@GetMapping("/buscardireccionyciudad")
	public ResponseEntity<Object> buscarDireccionYCiudad(@RequestParam String dir, @RequestParam String ciudad) {
		try {
			List<PaqueteNoAlimenticioDTO> lista = paqueteNoAlimenticioSer.findByDireccionDestinoAndCiudadDestino(dir, ciudad);
			if (lista.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(lista, HttpStatus.OK);
			}
		} catch (DireccionInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Retorna el historial de paquetes no alimenticios de un cliente específico.
	 * <p>
	 * Endpoint: {@code GET /paquetenoalimenticio/historialporid}
	 * </p>
	 */
	@GetMapping("/historialporid")
    public ResponseEntity<List<PaqueteNoAlimenticioDTO>> verHistorial(@RequestParam long idCliente) {
        List<PaqueteNoAlimenticioDTO> lista = paqueteNoAlimenticioSer.findByIdCliente(idCliente);
        
        if(lista.isEmpty()) {
        	return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        } else {
        	return new ResponseEntity<>(lista, HttpStatus.OK);
        }
    }

}