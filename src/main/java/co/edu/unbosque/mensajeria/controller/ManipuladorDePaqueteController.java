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

import co.edu.unbosque.mensajeria.dto.ManipuladorDePaqueteDTO;
import co.edu.unbosque.mensajeria.exception.CedulaInvalidaException;
import co.edu.unbosque.mensajeria.exception.CorreoInvalidoException;
import co.edu.unbosque.mensajeria.exception.IdInvalidoException;
import co.edu.unbosque.mensajeria.exception.NombreInvalidoException;
import co.edu.unbosque.mensajeria.exception.TelefonoInvalidoException;
import co.edu.unbosque.mensajeria.exception.TipoManipuladorInvalidoException;
import co.edu.unbosque.mensajeria.exception.TurnoInvalidoException;
import co.edu.unbosque.mensajeria.service.ManipuladorDePaqueteService;

/**
 * Controlador REST para la gestión de manipuladores de paquete.
 * <p>
 * Expone endpoints HTTP para realizar operaciones CRUD sobre los
 * manipuladores de paquete registrados en el sistema de mensajería,
 * así como búsquedas por diferentes criterios.
 * </p>
 *
 * <p>Base URL: {@code /manipuladordepaquete}</p>
 *
 * @author Angie Villarreal
 * @version 1.0
 */
@RestController
@RequestMapping("/manipuladordepaquete")
@CrossOrigin(origins = { "http://localhost:8080", "*" })
public class ManipuladorDePaqueteController {

	/**
	 * Servicio que contiene la lógica de negocio para los manipuladores de paquete.
	 */
	@Autowired
	private ManipuladorDePaqueteService manipuladorSer;

	/**
	 * Constructor por defecto de {@code ManipuladorDePaqueteController}.
	 */
	public ManipuladorDePaqueteController() {
	}

	/**
	 * Crea un nuevo manipulador de paquete en el sistema.
	 * <p>
	 * Endpoint: {@code POST /manipuladordepaquete/crear}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/manipuladordepaquete/crear?nombre=Roberto&cedula=4455&correo=roberto@mail.com&telefono=318777&turno=N&tipoManipulador=Carga}
	 * </p>
	 *
	 * @param nombre          nombre del manipulador de paquete
	 * @param cedula          número de cédula del manipulador
	 * @param correo          correo electrónico del manipulador
	 * @param telefono        número de teléfono del manipulador
	 * @param turno           turno asignado al manipulador (p. ej. {@code 'M'} para mañana, {@code 'N'} para noche)
	 * @param tipoManipulador tipo de manipulador (p. ej. {@code "Carga"}, {@code "Logistica"})
	 * @return {@link ResponseEntity} con un mensaje de resultado y el código HTTP correspondiente:
	 *         <ul>
	 *           <li>{@code 201 CREATED} – manipulador creado exitosamente</li>
	 *           <li>{@code 409 CONFLICT} – la cédula ya se encuentra registrada</li>
	 *           <li>{@code 400 BAD_REQUEST} – datos inválidos o error de validación</li>
	 *           <li>{@code 500 INTERNAL_SERVER_ERROR} – error interno del servidor</li>
	 *         </ul>
	 */
	// http://localhost:8080/manipuladordepaquete/crear?nombre=Roberto&cedula=4455&correo=roberto@mail.com&telefono=318777&turno=N&tipoManipulador=Carga
	@PostMapping("/crear")
	public ResponseEntity<String> crearManipulador(@RequestParam String nombre, @RequestParam String cedula,
			@RequestParam String correo, @RequestParam String telefono, @RequestParam char turno,
			@RequestParam String tipoManipulador) {

		try {
			ManipuladorDePaqueteDTO nuevo = new ManipuladorDePaqueteDTO(nombre, cedula, correo, telefono, turno,
					tipoManipulador);
			int status = manipuladorSer.create(nuevo);
			if (status == 0) {
				return new ResponseEntity<>("Dato creado con éxito", HttpStatus.CREATED);
			} else if (status == 1) {
				return new ResponseEntity<>("La cédula ya se encuentra registrada", HttpStatus.CONFLICT);
			} else {
				return new ResponseEntity<>("Error al crear el manipulador de paquete", HttpStatus.BAD_REQUEST);
			}

		} catch (NombreInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (CedulaInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (CorreoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TelefonoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TurnoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TipoManipuladorInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Retorna la lista completa de manipuladores de paquete registrados en el sistema.
	 * <p>
	 * Endpoint: {@code GET /manipuladordepaquete/mostrartodo}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/manipuladordepaquete/mostrartodo}
	 * </p>
	 *
	 * @return {@link ResponseEntity} con la lista de {@link ManipuladorDePaqueteDTO} y el código HTTP correspondiente:
	 *         <ul>
	 *           <li>{@code 202 ACCEPTED} – lista retornada exitosamente</li>
	 *           <li>{@code 204 NO_CONTENT} – no hay manipuladores registrados</li>
	 *         </ul>
	 */
	// http://localhost:8080/manipuladordepaquete/mostrartodo
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<ManipuladorDePaqueteDTO>> mostrarTodo() {
		List<ManipuladorDePaqueteDTO> lista = manipuladorSer.getAll();
		if (lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		}
	}

	/**
	 * Actualiza los datos de un manipulador de paquete existente identificado por su ID.
	 * <p>
	 * Endpoint: {@code PUT /manipuladordepaquete/actualizar}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/manipuladordepaquete/actualizar?id=1&nombre=Roberto+Carlos&cedula=4455&correo=rcarlos@mail.com&telefono=318888&turno=M&tipoManipulador=Logistica}
	 * </p>
	 *
	 * @param id              identificador único del manipulador a actualizar
	 * @param nombre          nuevo nombre del manipulador
	 * @param cedula          nueva cédula del manipulador
	 * @param correo          nuevo correo electrónico del manipulador
	 * @param telefono        nuevo número de teléfono del manipulador
	 * @param turno           nuevo turno del manipulador
	 * @param tipoManipulador nuevo tipo de manipulador
	 * @return {@link ResponseEntity} con un mensaje de resultado y el código HTTP correspondiente:
	 *         <ul>
	 *           <li>{@code 202 ACCEPTED} – registro actualizado exitosamente</li>
	 *           <li>{@code 400 BAD_REQUEST} – ID no encontrado o datos inválidos</li>
	 *           <li>{@code 500 INTERNAL_SERVER_ERROR} – error inesperado en el servidor</li>
	 *         </ul>
	 */
	// http://localhost:8080/manipuladordepaquete/actualizar?id=1&nombre=Roberto+Carlos&cedula=4455&correo=rcarlos@mail.com&telefono=318888&turno=M&tipoManipulador=Logistica
	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizar(@RequestParam Long id, @RequestParam String nombre,
			@RequestParam String cedula, @RequestParam String correo, @RequestParam String telefono,
			@RequestParam char turno, @RequestParam String tipoManipulador) {
		try {
			ManipuladorDePaqueteDTO nuevo = new ManipuladorDePaqueteDTO(nombre, cedula, correo, telefono, turno,
					tipoManipulador);
			int status = manipuladorSer.updateById(id, nuevo);
			if (status == 0) {
				return new ResponseEntity<>("Dato actualizado con éxito", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>("Error: El ID " + id + " no existe en la base de datos",
						HttpStatus.BAD_REQUEST);
			}
		} catch (NombreInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (CedulaInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (CorreoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TelefonoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TurnoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TipoManipuladorInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Error inesperado en el servidor", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Elimina un manipulador de paquete del sistema según su ID.
	 * <p>
	 * Endpoint: {@code DELETE /manipuladordepaquete/eliminar}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/manipuladordepaquete/eliminar?id=1}
	 * </p>
	 *
	 * @param id identificador único del manipulador a eliminar
	 * @return {@link ResponseEntity} con un mensaje de resultado y el código HTTP correspondiente:
	 *         <ul>
	 *           <li>{@code 202 ACCEPTED} – registro eliminado exitosamente</li>
	 *           <li>{@code 400 BAD_REQUEST} – ID no encontrado o inválido</li>
	 *           <li>{@code 500 INTERNAL_SERVER_ERROR} – error al procesar la solicitud</li>
	 *         </ul>
	 */
	// http://localhost:8080/manipuladordepaquete/eliminar?id=1
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> delete(@RequestParam Long id) {
		try {
			int status = manipuladorSer.deleteById(id);
			if (status == 0) {
				return new ResponseEntity<>("Dato eliminado con éxito", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>("No se encontró el manipulador de paquete con el ID ingresado: " + id, HttpStatus.BAD_REQUEST);
			}
		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Error al procesar la solicitud", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Busca manipuladores de paquete por nombre.
	 * <p>
	 * Endpoint: {@code GET /manipuladordepaquete/buscarpornombre}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/manipuladordepaquete/buscarpornombre?nombre=Roberto}
	 * </p>
	 *
	 * @param nombre nombre del manipulador a buscar
	 * @return {@link ResponseEntity} con la lista de {@link ManipuladorDePaqueteDTO} encontrados y el código HTTP:
	 *         <ul>
	 *           <li>{@code 202 ACCEPTED} – se encontraron resultados</li>
	 *           <li>{@code 204 NO_CONTENT} – no se encontraron coincidencias</li>
	 *         </ul>
	 */
	// http://localhost:8080/manipuladordepaquete/buscarpornombre?nombre=Roberto
	@GetMapping("/buscarpornombre")
	public ResponseEntity<List<ManipuladorDePaqueteDTO>> buscarPorNombre(@RequestParam String nombre) {
		List<ManipuladorDePaqueteDTO> lista = manipuladorSer.findByNombre(nombre);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Busca manipuladores de paquete por número de cédula.
	 * <p>
	 * Endpoint: {@code GET /manipuladordepaquete/buscarporcedula}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/manipuladordepaquete/buscarporcedula?cedula=4455}
	 * </p>
	 *
	 * @param cedula número de cédula del manipulador a buscar
	 * @return {@link ResponseEntity} con la lista de {@link ManipuladorDePaqueteDTO} encontrados y el código HTTP:
	 *         <ul>
	 *           <li>{@code 202 ACCEPTED} – se encontraron resultados</li>
	 *           <li>{@code 204 NO_CONTENT} – no se encontraron coincidencias</li>
	 *         </ul>
	 */
	// http://localhost:8080/manipuladordepaquete/buscarporcedula?cedula=4455
	@GetMapping("/buscarporcedula")
	public ResponseEntity<List<ManipuladorDePaqueteDTO>> buscarPorCedula(@RequestParam String cedula) {
		List<ManipuladorDePaqueteDTO> lista = manipuladorSer.findByCedula(cedula);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Busca manipuladores de paquete por correo electrónico.
	 * <p>
	 * Endpoint: {@code GET /manipuladordepaquete/buscarporcorreo}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/manipuladordepaquete/buscarporcorreo?correo=roberto@mail.com}
	 * </p>
	 *
	 * @param correo correo electrónico del manipulador a buscar
	 * @return {@link ResponseEntity} con la lista de {@link ManipuladorDePaqueteDTO} encontrados y el código HTTP:
	 *         <ul>
	 *           <li>{@code 202 ACCEPTED} – se encontraron resultados</li>
	 *           <li>{@code 204 NO_CONTENT} – no se encontraron coincidencias</li>
	 *         </ul>
	 */
	// http://localhost:8080/manipuladordepaquete/buscarporcorreo?correo=roberto@mail.com
	@GetMapping("/buscarporcorreo")
	public ResponseEntity<List<ManipuladorDePaqueteDTO>> buscarPorCorreo(@RequestParam String correo) {
		List<ManipuladorDePaqueteDTO> lista = manipuladorSer.findByCorreo(correo);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Busca manipuladores de paquete por número de teléfono.
	 * <p>
	 * Endpoint: {@code GET /manipuladordepaquete/buscarportelefono}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/manipuladordepaquete/buscarportelefono?telefono=318777}
	 * </p>
	 *
	 * @param telefono número de teléfono del manipulador a buscar
	 * @return {@link ResponseEntity} con la lista de {@link ManipuladorDePaqueteDTO} encontrados y el código HTTP:
	 *         <ul>
	 *           <li>{@code 202 ACCEPTED} – se encontraron resultados</li>
	 *           <li>{@code 204 NO_CONTENT} – no se encontraron coincidencias</li>
	 *         </ul>
	 */
	// http://localhost:8080/manipuladordepaquete/buscarportelefono?telefono=318777
	@GetMapping("/buscarportelefono")
	public ResponseEntity<List<ManipuladorDePaqueteDTO>> buscarPorTelefono(@RequestParam String telefono) {
		List<ManipuladorDePaqueteDTO> lista = manipuladorSer.findByTelefono(telefono);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Busca manipuladores de paquete según su tipo (p. ej. Carga, Logística).
	 * <p>
	 * Endpoint: {@code GET /manipuladordepaquete/buscarportipomanipulador}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/manipuladordepaquete/buscarportipomanipulador?tipoManipulador=Carga}
	 * </p>
	 *
	 * @param tipoManipulador tipo del manipulador a buscar
	 * @return {@link ResponseEntity} con la lista de {@link ManipuladorDePaqueteDTO} encontrados y el código HTTP:
	 *         <ul>
	 *           <li>{@code 202 ACCEPTED} – se encontraron resultados</li>
	 *           <li>{@code 204 NO_CONTENT} – no se encontraron coincidencias</li>
	 *         </ul>
	 */
	// http://localhost:8080/manipuladordepaquete/buscarportipomanipulador?tipoManipulador=Carga
	@GetMapping("/buscarportipomanipulador")
	public ResponseEntity<List<ManipuladorDePaqueteDTO>> buscarPorTipoManipulador(
			@RequestParam String tipoManipulador) {
		List<ManipuladorDePaqueteDTO> lista = manipuladorSer.findByTipoManipulador(tipoManipulador);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Busca manipuladores de paquete filtrando simultáneamente por nombre y cédula.
	 * <p>
	 * Endpoint: {@code GET /manipuladordepaquete/buscarpornombreycedula}
	 * </p>
	 * <p>
	 * Ejemplo de uso:
	 * {@code http://localhost:8080/manipuladordepaquete/buscarpornombreycedula?nombre=Roberto&cedula=4455}
	 * </p>
	 *
	 * @param nombre nombre del manipulador a buscar
	 * @param cedula cédula del manipulador a buscar
	 * @return {@link ResponseEntity} con la lista de {@link ManipuladorDePaqueteDTO} encontrados y el código HTTP:
	 *         <ul>
	 *           <li>{@code 202 ACCEPTED} – se encontraron resultados</li>
	 *           <li>{@code 204 NO_CONTENT} – no se encontraron coincidencias</li>
	 *         </ul>
	 */
	// http://localhost:8080/manipuladordepaquete/buscarpornombreycedula?nombre=Roberto&cedula=4455
	@GetMapping("/buscarpornombreycedula")
	public ResponseEntity<List<ManipuladorDePaqueteDTO>> buscarPorNombreAndCedula(@RequestParam String nombre,
			@RequestParam String cedula) {
		List<ManipuladorDePaqueteDTO> lista = manipuladorSer.findByNombreAndCedula(nombre, cedula);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}
}