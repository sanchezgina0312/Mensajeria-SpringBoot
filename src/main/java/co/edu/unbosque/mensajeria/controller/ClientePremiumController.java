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

import co.edu.unbosque.mensajeria.dto.ClienteNormalDTO;
import co.edu.unbosque.mensajeria.dto.ClientePremiumDTO;
import co.edu.unbosque.mensajeria.exception.CedulaInvalidaException;
import co.edu.unbosque.mensajeria.exception.ContraseniaInvalidaException;
import co.edu.unbosque.mensajeria.exception.CorreoInvalidoException;
import co.edu.unbosque.mensajeria.exception.IdInvalidoException;
import co.edu.unbosque.mensajeria.exception.MetodoDePagoInvalidoException;
import co.edu.unbosque.mensajeria.exception.NombreInvalidoException;
import co.edu.unbosque.mensajeria.exception.TelefonoInvalidoException;
import co.edu.unbosque.mensajeria.service.ClientePremiumService;

/**
 * Controlador REST que expone los endpoints para la gestión de clientes premium
 * dentro del sistema de mensajería.
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
@RequestMapping("/clientepremium")
@CrossOrigin(origins = { "http://localhost:8080", "*" })
public class ClientePremiumController {

	/**
	 * Servicio de lógica de negocio para la gestión de clientes premium.
	 */
	@Autowired
	private ClientePremiumService clientePremiumService;

	/**
	 * Constructor por defecto.
	 */
	public ClientePremiumController() {
	}
	
	/**
	 * MÉTODO AGREGADO: Valida las credenciales del cliente para iniciar sesión.
	 *
	 * @param cedula      Cédula del cliente.
	 * @param contrasenia Contraseña del cliente.
	 * @return El objeto ClienteNormalDTO si es válido, o un error de autorización.
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String cedula, @RequestParam String contrasenia) {
	    List<ClientePremiumDTO> lista = clientePremiumService.findByCedula(cedula);
	    
	    if (lista.isEmpty()) {
	        return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
	    }
	    ClientePremiumDTO cliente = lista.get(0); 

	    if (cliente.getContrasenia().equals(contrasenia)) {
	        return new ResponseEntity<>(cliente, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>("Contraseña incorrecta", HttpStatus.UNAUTHORIZED);
	    }
	}

	/**
	 * Crea un nuevo cliente premium en el sistema.
	 *
	 * @param nombre      nombre completo del cliente.
	 * @param cedula      número de identificación único.
	 * @param correo      dirección de correo electrónico.
	 * @param telefono    número de contacto telefónico.
	 * @param contrasenia clave de seguridad para el acceso.
	 * @return {@link ResponseEntity} con el resultado de la operación.
	 * @throws NombreInvalidoException      si el nombre no cumple el formato.
	 * @throws CedulaInvalidaException      si la cédula no cumple el formato.
	 * @throws CorreoInvalidoException      si el correo no es válido.
	 * @throws TelefonoInvalidoException    si el teléfono no es válido.
	 * @throws ContraseniaInvalidaException si la contraseña no cumple requisitos.
	 */
	@PostMapping("/crear")
	public ResponseEntity<String> crearClientePremium(@RequestParam String nombre, @RequestParam String cedula,
			@RequestParam String correo, @RequestParam String telefono, @RequestParam String contrasenia) {

		try {
			ClientePremiumDTO nuevoClientePremium = new ClientePremiumDTO();
			nuevoClientePremium.setNombre(nombre);
			nuevoClientePremium.setCedula(cedula);
			nuevoClientePremium.setCorreo(correo);
			nuevoClientePremium.setTelefono(telefono);
			nuevoClientePremium.setContrasenia(contrasenia);

			int status = clientePremiumService.create(nuevoClientePremium);

			if (status == 0) {
				return new ResponseEntity<>("Cliente creado con éxito", HttpStatus.CREATED);
			} else if (status == 1) {
				return new ResponseEntity<>("La cédula ya se encuentra registrada", HttpStatus.CONFLICT);
			} else {
				return new ResponseEntity<>("Error al crear cliente", HttpStatus.BAD_REQUEST);
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
	 * Retorna la lista de todos los clientes premium registrados.
	 *
	 * @return Lista de {@link ClientePremiumDTO} con estado HTTP.
	 */
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<ClientePremiumDTO>> mostrarTodo() {
		List<ClientePremiumDTO> clientes = clientePremiumService.getAll();
		if (!clientes.isEmpty()) {
			return new ResponseEntity<>(clientes, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(clientes, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Elimina un cliente premium del sistema según su ID.
	 *
	 * @param id identificador único del cliente a eliminar.
	 * @return Mensaje descriptivo con el estado de la eliminación.
	 * @throws IdInvalidoException si el ID proporcionado no es válido.
	 */
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> eliminarClientePremium(@RequestParam Long id) {
		try {
			int status = clientePremiumService.deleteById(id);
			if (status == 0) {
				return new ResponseEntity<>("Cliente eliminado correctamente.", HttpStatus.OK);
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
	 * Actualiza los datos de un cliente premium existente identificado por su ID.
	 *
	 * @param id          identificador único del cliente a actualizar.
	 * @param nombre      nuevo nombre del cliente.
	 * @param cedula      nueva cédula del cliente.
	 * @param correo      nuevo correo electrónico.
	 * @param telefono    nuevo número de teléfono.
	 * @param metodoPago  nuevo método de pago.
	 * @param contrasenia nueva contraseña de acceso.
	 * @return Respuesta con el estado de la actualización.
	 */
	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizarClientePremium(@RequestParam Long id, @RequestParam String nombre,
			@RequestParam String cedula, @RequestParam String correo, @RequestParam String telefono,
			@RequestParam String metodoPago, @RequestParam String contrasenia) {

		try {
			ClientePremiumDTO clientePremiumNuevo = new ClientePremiumDTO();
			clientePremiumNuevo.setNombre(nombre);
			clientePremiumNuevo.setCedula(cedula);
			clientePremiumNuevo.setCorreo(correo);
			clientePremiumNuevo.setTelefono(telefono);
			clientePremiumNuevo.setMetodoPago(metodoPago);
			clientePremiumNuevo.setContrasenia(contrasenia);

			int status = clientePremiumService.updateById(id, clientePremiumNuevo);

			if (status == 0) {
				return new ResponseEntity<>("Cliente actualizado correctamente.", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Error al actualizar cliente.", HttpStatus.BAD_REQUEST);
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
	 * Busca clientes premium por nombre.
	 *
	 * @param nombre nombre a buscar.
	 * @return Lista de coincidencias encontradas.
	 */
	@GetMapping("/buscarpornombre")
	public ResponseEntity<List<ClientePremiumDTO>> buscarPorNombre(@RequestParam String nombre) {
		List<ClientePremiumDTO> lista = clientePremiumService.findByNombre(nombre);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Busca clientes premium por número de cédula.
	 *
	 * @param cedula número de cédula a buscar.
	 * @return Lista con el cliente encontrado o vacío.
	 */
	@GetMapping("/buscarporcedula")
	public ResponseEntity<List<ClientePremiumDTO>> buscarPorCedula(@RequestParam String cedula) {
		List<ClientePremiumDTO> lista = clientePremiumService.findByCedula(cedula);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Busca clientes premium por correo electrónico.
	 *
	 * @param correo correo a buscar.
	 * @return Lista de coincidencias.
	 */
	@GetMapping("/buscarporcorreo")
	public ResponseEntity<List<ClientePremiumDTO>> buscarPorCorreo(@RequestParam String correo) {
		List<ClientePremiumDTO> lista = clientePremiumService.findByCorreo(correo);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Busca clientes premium por número de teléfono.
	 *
	 * @param telefono teléfono a buscar.
	 * @return Lista de coincidencias.
	 */
	@GetMapping("/buscarportelefono")
	public ResponseEntity<List<ClientePremiumDTO>> buscarPorTelefono(@RequestParam String telefono) {
		List<ClientePremiumDTO> lista = clientePremiumService.findByTelefono(telefono);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Busca clientes premium según el método de pago registrado.
	 *
	 * @param metodoPago nombre del método de pago.
	 * @return Lista de clientes que usan dicho método.
	 */
	@GetMapping("/buscarpormetodopago")
	public ResponseEntity<List<ClientePremiumDTO>> buscarPorMetodoPago(@RequestParam String metodoPago) {
		List<ClientePremiumDTO> lista = clientePremiumService.findByMetodoPago(metodoPago);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Busca clientes premium por coincidencia de nombre y cédula.
	 *
	 * @param nombre nombre del cliente.
	 * @param cedula cédula del cliente.
	 * @return Lista de coincidencias encontradas.
	 */
	@GetMapping("/buscarpornombreycedula")
	public ResponseEntity<List<ClientePremiumDTO>> buscarPorNombreAndCedula(@RequestParam String nombre,
			@RequestParam String cedula) {
		List<ClientePremiumDTO> lista = clientePremiumService.findByNombreAndCedula(nombre, cedula);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

}