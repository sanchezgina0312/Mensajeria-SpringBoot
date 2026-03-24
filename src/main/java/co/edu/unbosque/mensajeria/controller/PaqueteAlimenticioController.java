package co.edu.unbosque.mensajeria.controller;

import java.time.LocalDateTime;
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

import co.edu.unbosque.mensajeria.dto.PaqueteAlimenticioDTO;
import co.edu.unbosque.mensajeria.exception.CiudadInvalidaException;
import co.edu.unbosque.mensajeria.exception.DireccionInvalidaException;
import co.edu.unbosque.mensajeria.exception.IdInvalidoException;
import co.edu.unbosque.mensajeria.exception.TamanioInvalidoException;
import co.edu.unbosque.mensajeria.exception.TipoDeAlimentoInvalidoException;
import co.edu.unbosque.mensajeria.service.PaqueteAlimenticioService;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador REST para la gestión de paquetes alimenticios.
 * <p>
 * Expone endpoints HTTP para realizar operaciones CRUD sobre los paquetes
 * alimenticios registrados en el sistema de mensajería, así como búsquedas
 * por diferentes criterios como tamaño, tipo de alimento, dirección y ciudad.
 * </p>
 *
 * <p>Base URL: {@code /paquetealimenticio}</p>
 *
 * @author Gina Buitrago
 * @version 1.0
 */
@RestController
@RequestMapping("/paquetealimenticio")
@CrossOrigin(origins = { "http://localhost:8080", "*" })
public class PaqueteAlimenticioController {

	/**
	 * Servicio que contiene la lógica de negocio para los paquetes alimenticios.
	 */
	@Autowired
	private PaqueteAlimenticioService paqueteAlimenticioSer;

	/**
	 * Constructor por defecto de {@code PaqueteAlimenticioController}.
	 */
	public PaqueteAlimenticioController() {
	}

	/**
	 * Crea un nuevo paquete alimenticio en el sistema.
	 * <p>
	 * Endpoint: {@code POST /paquetealimenticio/crear}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/paquetealimenticio/crear?remitente=Juan&destinatario=Ana&direccionDestino=Calle+10&ciudadDestino=Bogota&fechaEntrega=2024-05-20T14:30:00&tamanio=Grande&tipoAlimento=Perecedero}
	 * </p>
	 *
	 * @param direccionDestino dirección de destino del paquete alimenticio
	 * @param tamanio          tamaño del paquete (p. ej. {@code "Grande"}, {@code "Mediano"}, {@code "Pequeño"})
	 * @param ciudadDestino    ciudad de destino del paquete
	 * @param seEnviaHoy       indica si el paquete se envía el mismo día ({@code true} o {@code false})
	 * @param tipoDeAlimento   tipo de alimento del paquete (p. ej. {@code "Perecedero"}, {@code "No Perecedero"})
	 * @return {@link ResponseEntity} con un mensaje de resultado y el código HTTP correspondiente:
	 *         <ul>
	 *           <li>{@code 201 CREATED} – paquete creado exitosamente</li>
	 *           <li>{@code 400 BAD_REQUEST} – datos inválidos o error de validación</li>
	 *         </ul>
	 */
	// http://localhost:8080/paquetealimenticio/crear?remitente=Juan&destinatario=Ana&direccionDestino=Calle+10&ciudadDestino=Bogota&fechaEntrega=2024-05-20T14:30:00&tamanio=Grande&tipoAlimento=Perecedero
	@PostMapping("/crear")
	public ResponseEntity<String> crear(@RequestParam String direccionDestino, @RequestParam String tamanio,
			@RequestParam String ciudadDestino, @RequestParam boolean seEnviaHoy, @RequestParam String tipoDeAlimento) {

		try {
			PaqueteAlimenticioDTO dto = new PaqueteAlimenticioDTO();
			dto.setDireccionDestino(direccionDestino);
			dto.setTamanio(tamanio);
			dto.setCiudadDestino(ciudadDestino);
			dto.setSeEnviaHoy(seEnviaHoy);
			dto.setTipoDeAlimento(tipoDeAlimento);

			int resultado = paqueteAlimenticioSer.create(dto);

			if (resultado != 0) {
				return new ResponseEntity<>("Paquete alimenticio creado con éxito", HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Error al guardar el paquete alimenticio", HttpStatus.BAD_REQUEST);
			}

		} catch (DireccionInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TamanioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TipoDeAlimentoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (CiudadInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (MethodArgumentTypeMismatchException e) {
			return new ResponseEntity<>("El valor de debe ser true o false", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Retorna la lista completa de paquetes alimenticios registrados en el sistema.
	 * <p>
	 * Endpoint: {@code GET /paquetealimenticio/mostrartodo}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/paquetealimenticio/mostrartodo}
	 * </p>
	 *
	 * @return {@link ResponseEntity} con la lista de {@link PaqueteAlimenticioDTO} y el código HTTP correspondiente:
	 *         <ul>
	 *           <li>{@code 202 ACCEPTED} – lista retornada exitosamente</li>
	 *           <li>{@code 204 NO_CONTENT} – no hay paquetes registrados</li>
	 *         </ul>
	 */
	// http://localhost:8080/paquetealimenticio/mostrartodo
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<PaqueteAlimenticioDTO>> mostrarTodo() {
		List<PaqueteAlimenticioDTO> lista = paqueteAlimenticioSer.getAll();
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Actualiza los datos de un paquete alimenticio existente identificado por su ID.
	 * <p>
	 * Endpoint: {@code PUT /paquetealimenticio/actualizar}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/paquetealimenticio/actualizar?id=1&remitente=Juan+Perez&destinatario=Ana+Gomez&direccionDestino=Carrera+15&ciudadDestino=Medellin&fechaEntrega=2024-05-21T10:00:00&tamanio=Mediano&tipoAlimento=No+Perecedero}
	 * </p>
	 *
	 * @param id                    identificador único del paquete a actualizar
	 * @param seEnviaHoy            indica si el paquete se envía el mismo día
	 * @param tipoDeAlimento        nuevo tipo de alimento del paquete
	 * @param precioEnvio           nuevo precio de envío del paquete
	 * @param direccionDestino      nueva dirección de destino
	 * @param tamanio               nuevo tamaño del paquete
	 * @param fechaCreacionPedido   nueva fecha de creación del pedido
	 * @param fechaEstimadaEntrega  nueva fecha estimada de entrega
	 * @return {@link ResponseEntity} con un mensaje de resultado y el código HTTP correspondiente:
	 *         <ul>
	 *           <li>{@code 202 ACCEPTED} – paquete actualizado exitosamente</li>
	 *           <li>{@code 400 BAD_REQUEST} – ID no encontrado, datos inválidos o formato incorrecto</li>
	 *         </ul>
	 */
	// http://localhost:8080/paquetealimenticio/actualizar?id=1&remitente=Juan+Perez&destinatario=Ana+Gomez&direccionDestino=Carrera+15&ciudadDestino=Medellin&fechaEntrega=2024-05-21T10:00:00&tamanio=Mediano&tipoAlimento=No+Perecedero
	@PutMapping("/actualizar")
	public ResponseEntity<Object> actualizar(@RequestParam Long id, @RequestParam String direccionDestino,
			@RequestParam String ciudadDestino, @RequestParam String tamanio, @RequestParam String tipoDeAlimento) {
		try {
			PaqueteAlimenticioDTO nuevo = new PaqueteAlimenticioDTO();
			nuevo.setDireccionDestino(direccionDestino);
			nuevo.setCiudadDestino(ciudadDestino);
			nuevo.setTamanio(tamanio);
			nuevo.setTipoDeAlimento(tipoDeAlimento);

			int status = paqueteAlimenticioSer.updateById(id, nuevo);
			if (status == 1) {
				return new ResponseEntity<>("Paquete alimenticio actualizado con éxito", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Error: Paquete no encontrado o ya fue enviado hoy",
						HttpStatus.BAD_REQUEST);
			}
		} catch (DireccionInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TamanioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (CiudadInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TipoDeAlimentoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (MethodArgumentTypeMismatchException e) {
			return new ResponseEntity<>("El formato del ID no es válido", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Elimina un paquete alimenticio del sistema según su ID.
	 * <p>
	 * Endpoint: {@code DELETE /paquetealimenticio/eliminar}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/paquetealimenticio/eliminar?id=1}
	 * </p>
	 *
	 * @param id identificador único del paquete a eliminar
	 * @return {@link ResponseEntity} con un mensaje de resultado y el código HTTP correspondiente:
	 *         <ul>
	 *           <li>{@code 202 ACCEPTED} – paquete eliminado exitosamente</li>
	 *           <li>{@code 400 BAD_REQUEST} – ID no encontrado, inválido o con formato incorrecto</li>
	 *         </ul>
	 */
	// http://localhost:8080/paquetealimenticio/eliminar?id=1
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> delete(@RequestParam Long id) {

		try {
			int status = paqueteAlimenticioSer.deleteById(id);
			if (status == 0) {
				return new ResponseEntity<>("Paquete alimenticio eliminado con éxito", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>("No se encontró el paquete alimenticio con el ID ingresado" + id,
						HttpStatus.BAD_REQUEST);
			}
		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (MethodArgumentTypeMismatchException e) {
			return new ResponseEntity<>("El valor debe ser un número entero", HttpStatus.BAD_REQUEST);
		}
	}

<<<<<<< HEAD
=======
	/**
	 * Busca paquetes alimenticios según su tamaño.
	 * <p>
	 * Endpoint: {@code GET /paquetealimenticio/buscartamanio}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/paquetealimenticio/buscarportamanio?tamanio=Grande}
	 * </p>
	 *
	 * @param tamanio tamaño del paquete a buscar (p. ej. {@code "Grande"}, {@code "Mediano"}, {@code "Pequeño"})
	 * @return {@link ResponseEntity} con la lista de {@link PaqueteAlimenticioDTO} encontrados y el código HTTP:
	 *         <ul>
	 *           <li>{@code 202 ACCEPTED} – se encontraron resultados</li>
	 *           <li>{@code 204 NO_CONTENT} – no se encontraron coincidencias</li>
	 *           <li>{@code 400 BAD_REQUEST} – tamaño inválido</li>
	 *         </ul>
	 */
>>>>>>> 3ded234e7254e19399a0395bd14043a5139d4b9d
	// http://localhost:8080/paquetealimenticio/buscarportamanio?tamanio=Grande
	@GetMapping("/buscartamanio")
	public ResponseEntity<Object> buscarPorTamanio(@RequestParam String tamanio) {
		try {
			List<PaqueteAlimenticioDTO> lista = paqueteAlimenticioSer.findByTamanio(tamanio);
			if (!lista.isEmpty()) {
				return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
			}
		} catch (TamanioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

<<<<<<< HEAD
=======
	/**
	 * Busca paquetes alimenticios según si se envían el mismo día o no.
	 * <p>
	 * Endpoint: {@code GET /paquetealimenticio/buscarporenviahoy}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/paquetealimenticio/buscarporenviahoy?enviadhoy=true}
	 * </p>
	 *
	 * @param seEnviaHoy valor booleano que indica si el paquete se envía hoy ({@code true} o {@code false})
	 * @return {@link ResponseEntity} con la lista de {@link PaqueteAlimenticioDTO} encontrados y el código HTTP:
	 *         <ul>
	 *           <li>{@code 202 ACCEPTED} – se encontraron resultados</li>
	 *           <li>{@code 204 NO_CONTENT} – no se encontraron coincidencias</li>
	 *           <li>{@code 400 BAD_REQUEST} – el valor no es un booleano válido</li>
	 *         </ul>
	 */
>>>>>>> 3ded234e7254e19399a0395bd14043a5139d4b9d
	// http://localhost:8080/paquetealimenticio/buscarporenviahoy?enviadhoy=true
	@GetMapping("/buscarporenviahoy")
	public ResponseEntity<Object> buscarPorSeEnviaHoy(@RequestParam boolean seEnviaHoy) {
		try {
			List<PaqueteAlimenticioDTO> lista = paqueteAlimenticioSer.findBySeEnviaHoy(seEnviaHoy);
			if (!lista.isEmpty()) {
				return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
			}
		} catch (MethodArgumentTypeMismatchException e) {
			return new ResponseEntity<>("El valor debe ser true o false", HttpStatus.BAD_REQUEST);
		}
	}

<<<<<<< HEAD
=======
	/**
	 * Busca paquetes alimenticios según el tipo de alimento que contienen.
	 * <p>
	 * Endpoint: {@code GET /paquetealimenticio/buscarportipoalimento}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/paquetealimenticio/buscarportipoalimento?tipoAlimento=Perecedero}
	 * </p>
	 *
	 * @param tipoDeAlimento tipo de alimento a buscar (p. ej. {@code "Perecedero"}, {@code "No Perecedero"})
	 * @return {@link ResponseEntity} con la lista de {@link PaqueteAlimenticioDTO} encontrados y el código HTTP:
	 *         <ul>
	 *           <li>{@code 202 ACCEPTED} – se encontraron resultados</li>
	 *           <li>{@code 204 NO_CONTENT} – no se encontraron coincidencias</li>
	 *           <li>{@code 400 BAD_REQUEST} – tipo de alimento inválido</li>
	 *         </ul>
	 */
>>>>>>> 3ded234e7254e19399a0395bd14043a5139d4b9d
	// http://localhost:8080/paquetealimenticio/buscarportipoalimento?tipoAlimento=Perecedero
	@GetMapping("/buscarportipoalimento")
	public ResponseEntity<Object> buscarPorTipoDeAlimento(@RequestParam String tipoDeAlimento) {
		try {
			List<PaqueteAlimenticioDTO> lista = paqueteAlimenticioSer.findByTipoDeAlimento(tipoDeAlimento);
			if (!lista.isEmpty()) {
				return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
			}
		} catch (TipoDeAlimentoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

<<<<<<< HEAD
=======
	/**
	 * Busca paquetes alimenticios filtrando simultáneamente por tamaño y tipo de alimento.
	 * <p>
	 * Endpoint: {@code GET /paquetealimenticio/buscarportamanioytipo}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/paquetealimenticio/buscarportamanioytipo?tamanio=Pequeño&tipoAlimento=No+Perecedero}
	 * </p>
	 *
	 * @param tamanio        tamaño del paquete a buscar
	 * @param tipoDeAlimento tipo de alimento del paquete a buscar
	 * @return {@link ResponseEntity} con la lista de {@link PaqueteAlimenticioDTO} encontrados y el código HTTP:
	 *         <ul>
	 *           <li>{@code 202 ACCEPTED} – se encontraron resultados</li>
	 *           <li>{@code 204 NO_CONTENT} – no se encontraron coincidencias</li>
	 *           <li>{@code 400 BAD_REQUEST} – tamaño o tipo de alimento inválido</li>
	 *         </ul>
	 */
>>>>>>> 3ded234e7254e19399a0395bd14043a5139d4b9d
	// http://localhost:8080/paquetealimenticio/buscarportamanioytipo?tamanio=Pequeño&tipoAlimento=No+Perecedero
	@GetMapping("/buscarportamanioytipo")
	public ResponseEntity<Object> buscarPorTamanioAndTipo(@RequestParam String tamanio,
			@RequestParam String tipoDeAlimento) {
		try {
			List<PaqueteAlimenticioDTO> lista = paqueteAlimenticioSer.findByTamanioAndTipoDeAlimento(tamanio,
					tipoDeAlimento);
			if (!lista.isEmpty()) {
				return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
			}
		} catch (TamanioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TipoDeAlimentoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

<<<<<<< HEAD
=======
	/**
	 * Busca y retorna un paquete alimenticio específico por su ID para realizar seguimiento.
	 * <p>
	 * Endpoint: {@code GET /paquetealimenticio/seguimientoid}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/paquetealimenticio/seguimientoid?id=10}
	 * </p>
	 *
	 * @param id identificador único del paquete a consultar
	 * @return {@link ResponseEntity} con el {@link PaqueteAlimenticioDTO} encontrado y el código HTTP:
	 *         <ul>
	 *           <li>{@code 200 OK} – paquete encontrado exitosamente</li>
	 *           <li>{@code 404 NOT_FOUND} – no se encontró un paquete con el ID ingresado</li>
	 *           <li>{@code 400 BAD_REQUEST} – ID inválido o con formato incorrecto</li>
	 *         </ul>
	 */
>>>>>>> 3ded234e7254e19399a0395bd14043a5139d4b9d
	// http://localhost:8080/paquetealimenticio/seguimientoid?id=10
	@GetMapping("/seguimientoid")
	public ResponseEntity<Object> seguimientoId(@RequestParam Long id) {
		try {
			PaqueteAlimenticioDTO p = paqueteAlimenticioSer.findById(id);

			if (p != null) {
				return new ResponseEntity<>(p, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("No se encontró el paquete alimenticio con el ID ingresado",
						HttpStatus.NOT_FOUND);
			}
		} catch (MethodArgumentTypeMismatchException e) {
			return new ResponseEntity<>("El valor debe ser un número entero", HttpStatus.BAD_REQUEST);
		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Busca paquetes alimenticios filtrando simultáneamente por dirección y ciudad de destino.
	 * <p>
	 * Endpoint: {@code GET /paquetealimenticio/buscardireccionyciudad}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/paquetealimenticio/buscardireccionyciudad?dir=Calle+123&ciudad=Bogota}
	 * </p>
	 *
	 * @param dir    dirección de destino del paquete a buscar
	 * @param ciudad ciudad de destino del paquete a buscar
	 * @return {@link ResponseEntity} con la lista de {@link PaqueteAlimenticioDTO} encontrados y el código HTTP:
	 *         <ul>
	 *           <li>{@code 200 OK} – se encontraron resultados</li>
	 *           <li>{@code 204 NO_CONTENT} – no se encontraron coincidencias</li>
	 *           <li>{@code 400 BAD_REQUEST} – dirección inválida</li>
	 *         </ul>
	 */
	// http://localhost:8080/paquetealimenticio/buscardireccionyciudad?dir=Calle+123&ciudad=Bogota
	@GetMapping("/buscardireccionyciudad")
	public ResponseEntity<Object> buscarDireccionYCiudad(@RequestParam String dir, @RequestParam String ciudad) {
		try {
			List<PaqueteAlimenticioDTO> lista = paqueteAlimenticioSer.findByDireccionDestinoAndCiudadDestino(dir,
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

}