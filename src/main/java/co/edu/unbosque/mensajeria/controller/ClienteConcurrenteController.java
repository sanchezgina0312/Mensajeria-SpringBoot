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

import co.edu.unbosque.mensajeria.dto.AdministradorDTO;
import co.edu.unbosque.mensajeria.dto.ClienteConcurrenteDTO;
import co.edu.unbosque.mensajeria.dto.ClienteNormalDTO;
import co.edu.unbosque.mensajeria.exception.CedulaInvalidaException;
import co.edu.unbosque.mensajeria.exception.ContraseniaInvalidaException;
import co.edu.unbosque.mensajeria.exception.CorreoInvalidoException;
import co.edu.unbosque.mensajeria.exception.IdInvalidoException;
import co.edu.unbosque.mensajeria.exception.MetodoDePagoInvalidoException;
import co.edu.unbosque.mensajeria.exception.NombreInvalidoException;
import co.edu.unbosque.mensajeria.exception.TelefonoInvalidoException;
import co.edu.unbosque.mensajeria.service.ClienteConcurrenteService;

/**
 * Controlador REST que expone los endpoints para la gestión de clientes
 * concurrentes dentro del sistema de mensajería.
 *
 * <p>
 * Provee operaciones CRUD completas y búsquedas por diferentes criterios. Las
 * respuestas se retornan en formato JSON y los errores son gestionados mediante
 * excepciones personalizadas.
 * </p>
 *
 * @version 1.0
 */
@RestController
@RequestMapping("/clienteconcurrente")
@CrossOrigin(origins = { "http://localhost:8080", "*" })
public class ClienteConcurrenteController {

	/**
	 * Servicio de lógica de negocio para la gestión de clientes concurrentes.
	 */
	@Autowired
	private ClienteConcurrenteService clienteConcurrenteService;

	/**
	 * Constructor por defecto.
	 */
	public ClienteConcurrenteController() {
	}

	
	/**
	 * MÉTODO AGREGADO: Valida las credenciales del cliente para iniciar sesión.
	 *
	 * @param cedula      Cédula del cliente.
	 * @param contrasenia Contraseña del cliente.
	 * @return El objeto ClienteNormalDTO si es válido, o un error de autorización.
	 */
	@PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String cedula, @RequestParam String contrasenia) {
        List<ClienteConcurrenteDTO> lista = clienteConcurrenteService.findByCedula(cedula);
        
        if (lista.isEmpty()) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
        ClienteConcurrenteDTO clienteConcurrente = lista.get(0);
        if (clienteConcurrente.getContrasenia().equals(contrasenia)) {
            return new ResponseEntity<>("Bienvenido " + clienteConcurrente.getNombre(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Contraseña incorrecta", HttpStatus.UNAUTHORIZED);
        }
    }
	
	/**
	 * Crea un nuevo cliente concurrente en el sistema.
	 *
	 * <p>
	 * Endpoint: {@code POST /clienteconcurrente/crear}
	 * </p>
	 *
	 * @param nombre      nombre completo del cliente.
	 * @param cedula      número de identificación único.
	 * @param correo      dirección de correo electrónico.
	 * @param telefono    número de contacto telefónico.
	 * @param contrasenia clave de seguridad para el acceso.
	 * @return {@link ResponseEntity} con el estado de la operación.
	 * @throws NombreInvalidoException      si el nombre no cumple el formato.
	 * @throws CedulaInvalidaException      si la cédula no cumple el formato.
	 * @throws CorreoInvalidoException      si el correo no es válido.
	 * @throws TelefonoInvalidoException    si el teléfono no es válido.
	 * @throws ContraseniaInvalidaException si la contraseña no cumple requisitos.
	 */
	@PostMapping("/crear")
	public ResponseEntity<String> crearClienteConcurrente(@RequestParam String nombre, @RequestParam String cedula,
			@RequestParam String correo, @RequestParam String telefono, @RequestParam String contrasenia) {

		try {
			ClienteConcurrenteDTO nuevoClienteConcurrente = new ClienteConcurrenteDTO();
			nuevoClienteConcurrente.setNombre(nombre);
			nuevoClienteConcurrente.setCedula(cedula);
			nuevoClienteConcurrente.setCorreo(correo);
			nuevoClienteConcurrente.setTelefono(telefono);
			nuevoClienteConcurrente.setContrasenia(contrasenia);

			int status = clienteConcurrenteService.create(nuevoClienteConcurrente);

			if (status == 0) {
				return new ResponseEntity<>("Cliente creado con éxito", HttpStatus.CREATED);
			} else if (status == 1) {
				return new ResponseEntity<>("La cédula ya se encuentra registrada", HttpStatus.CONFLICT);
			} else {
				return new ResponseEntity<>("Error al crear Cliente", HttpStatus.BAD_REQUEST);
			}
		} catch (NombreInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (CedulaInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (CorreoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TelefonoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (ContraseniaInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Retorna la lista completa de clientes concurrentes registrados.
	 *
	 * @return Lista de {@link ClienteConcurrenteDTO} con estado HTTP.
	 */
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<ClienteConcurrenteDTO>> mostrarTodo() {
		List<ClienteConcurrenteDTO> clientes = clienteConcurrenteService.getAll();
		if (!clientes.isEmpty()) {
			return new ResponseEntity<List<ClienteConcurrenteDTO>>(clientes, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<List<ClienteConcurrenteDTO>>(clientes, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Elimina un cliente concurrente según su ID.
	 *
	 * @param id identificador único del cliente.
	 * @return Mensaje descriptivo con el estado de la eliminación.
	 * @throws IdInvalidoException si el ID no es válido.
	 */
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> eliminarClienteConcurrente(@RequestParam Long id) {
		try {
			int status = clienteConcurrenteService.deleteById(id);
			if (status == 0) {
				return new ResponseEntity<>("Cliente eliminado correctamente. ", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>("No se encontró el cliente con el ID ingresado: " + id,
						HttpStatus.BAD_REQUEST);
			}
		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Error al procesar la solicitud", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Actualiza los datos de un cliente concurrente identificado por su ID.
	 *
	 * @param id          identificador del cliente a actualizar.
	 * @param nombre      nuevo nombre del cliente.
	 * @param cedula      nueva cédula del cliente.
	 * @param correo      nuevo correo electrónico.
	 * @param telefono    nuevo número de teléfono.
	 * @param metodoPago  nuevo método de pago.
	 * @param contrasenia nueva contraseña de acceso.
	 * @return Respuesta con el estado de la actualización.
	 * @throws IdInvalidoException           si el ID no es válido.
	 * @throws MetodoDePagoInvalidoException si el método de pago es inválido.
	 */
	@PutMapping("/actualizarclienteconcurrente")
	public ResponseEntity<String> actualizarClienteConcurrente(@RequestParam Long id, @RequestParam String nombre,
			@RequestParam String cedula, @RequestParam String correo, @RequestParam String telefono,
			@RequestParam String metodoPago, @RequestParam String contrasenia) {
		try {
			ClienteConcurrenteDTO clienteConcurrenteNuevo = new ClienteConcurrenteDTO();
			clienteConcurrenteNuevo.setNombre(nombre);
			clienteConcurrenteNuevo.setCedula(cedula);
			clienteConcurrenteNuevo.setCorreo(correo);
			clienteConcurrenteNuevo.setTelefono(telefono);
			clienteConcurrenteNuevo.setMetodoPago(metodoPago);
			clienteConcurrenteNuevo.setContrasenia(contrasenia);

			int status = clienteConcurrenteService.updateById(id, clienteConcurrenteNuevo);

			if (status == 0) {
				return new ResponseEntity<>("Cliente actualizado correctamente. ", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>("Error al actualizar cliente. ", HttpStatus.BAD_REQUEST);
			}
		} catch (NombreInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (CedulaInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (CorreoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TelefonoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (MetodoDePagoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (ContraseniaInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Error inesperado", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Busca clientes concurrentes por nombre.
	 *
	 * @param nombre nombre a buscar.
	 * @return Lista de coincidencias encontradas.
	 */
	@GetMapping("/buscarpornombre")
	public ResponseEntity<List<ClienteConcurrenteDTO>> buscarPorNombre(@RequestParam String nombre) {
		List<ClienteConcurrenteDTO> lista = clienteConcurrenteService.findByNombre(nombre);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Busca clientes concurrentes por número de cédula.
	 *
	 * @param cedula número de cédula a buscar.
	 * @return Lista con el cliente encontrado o vacío.
	 */
	@GetMapping("/buscarporcedula")
	public ResponseEntity<List<ClienteConcurrenteDTO>> buscarPorCedula(@RequestParam String cedula) {
		List<ClienteConcurrenteDTO> lista = clienteConcurrenteService.findByCedula(cedula);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Busca clientes concurrentes por correo electrónico.
	 *
	 * @param correo correo a buscar.
	 * @return Lista de coincidencias.
	 */
	@GetMapping("/buscarporcorreo")
	public ResponseEntity<List<ClienteConcurrenteDTO>> buscarPorCorreo(@RequestParam String correo) {
		List<ClienteConcurrenteDTO> lista = clienteConcurrenteService.findByCorreo(correo);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Busca clientes concurrentes por número de teléfono.
	 *
	 * @param telefono teléfono a buscar.
	 * @return Lista de coincidencias.
	 */
	@GetMapping("/buscarportelefono")
	public ResponseEntity<List<ClienteConcurrenteDTO>> buscarPorTelefono(@RequestParam String telefono) {
		List<ClienteConcurrenteDTO> lista = clienteConcurrenteService.findByTelefono(telefono);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Busca clientes concurrentes según el método de pago registrado.
	 *
	 * @param metodoPago nombre del método de pago.
	 * @return Lista de clientes que usan dicho método.
	 */
	@GetMapping("/buscarpormetodopago")
	public ResponseEntity<List<ClienteConcurrenteDTO>> buscarPorMetodoPago(@RequestParam String metodoPago) {
		List<ClienteConcurrenteDTO> lista = clienteConcurrenteService.findByMetodoPago(metodoPago);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Busca clientes por coincidencia de nombre y cédula.
	 *
	 * @param nombre nombre del cliente.
	 * @param cedula cédula del cliente.
	 * @return Lista de coincidencias encontradas.
	 */
	@GetMapping("/buscarpornombreycedula")
	public ResponseEntity<List<ClienteConcurrenteDTO>> buscarPorNombreAndCedula(@RequestParam String nombre,
			@RequestParam String cedula) {
		List<ClienteConcurrenteDTO> lista = clienteConcurrenteService.findByNombreAndCedula(nombre, cedula);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

}