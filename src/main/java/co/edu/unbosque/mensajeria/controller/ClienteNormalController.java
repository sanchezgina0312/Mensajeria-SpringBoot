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

import co.edu.unbosque.mensajeria.dto.ClienteConcurrenteDTO;
import co.edu.unbosque.mensajeria.dto.ClienteNormalDTO;
import co.edu.unbosque.mensajeria.exception.CedulaInvalidaException;
import co.edu.unbosque.mensajeria.exception.ContraseniaInvalidaException;
import co.edu.unbosque.mensajeria.exception.CorreoInvalidoException;
import co.edu.unbosque.mensajeria.exception.IdInvalidoException;
import co.edu.unbosque.mensajeria.exception.MetodoDePagoInvalidoException;
import co.edu.unbosque.mensajeria.exception.NombreInvalidoException;
import co.edu.unbosque.mensajeria.exception.TelefonoInvalidoException;
import co.edu.unbosque.mensajeria.exception.TipoPedidoInvalidoException;
import co.edu.unbosque.mensajeria.service.ClienteNormalService;

/**
 * Controlador REST para la gestión de clientes normales dentro del sistema.
 * * @version 1.0
 */
@RestController
@RequestMapping("/clientenormal")
@CrossOrigin(origins = { "http://localhost:8080", "*" })
public class ClienteNormalController {

	/**
	 * Servicio de lógica de negocio para la gestión de clientes normales.
	 */
	@Autowired
	private ClienteNormalService clienteNormalService;

	/**
	 * Constructor por defecto.
	 */
	public ClienteNormalController() {
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
        List<ClienteNormalDTO> lista = clienteNormalService.findByCedula(cedula);
        
        if (lista.isEmpty()) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
        ClienteNormalDTO clienteNormal= lista.get(0);
        if (clienteNormal.getContrasenia().equals(contrasenia)) {
            return new ResponseEntity<>("Bienvenido " + clienteNormal.getNombre(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Contraseña incorrecta", HttpStatus.UNAUTHORIZED);
        }
    }

	/**
	 * Crea un nuevo cliente normal en el sistema.
	 *
	 * @param nombre      Nombre completo del cliente.
	 * @param cedula      Número de cédula o identificación.
	 * @param correo      Dirección de correo electrónico.
	 * @param telefono    Número de teléfono de contacto.
	 * @param contrasenia Contraseña para el acceso al sistema.
	 * @return Respuesta con el estado de la creación.
	 */
	@PostMapping("/crear")
	public ResponseEntity<String> crearClienteNormal(@RequestParam String nombre, @RequestParam String cedula,
			@RequestParam String correo, @RequestParam String telefono, @RequestParam String contrasenia) {

		try {
			ClienteNormalDTO nuevoClienteNormal = new ClienteNormalDTO();
			nuevoClienteNormal.setNombre(nombre);
			nuevoClienteNormal.setCedula(cedula);
			nuevoClienteNormal.setCorreo(correo);
			nuevoClienteNormal.setTelefono(telefono);
			nuevoClienteNormal.setContrasenia(contrasenia);

			int status = clienteNormalService.create(nuevoClienteNormal);

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
	 * Retorna la lista de todos los clientes normales registrados.
	 *
	 * @return ResponseEntity con la lista de clientes.
	 */
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<ClienteNormalDTO>> mostrarTodo() {
		List<ClienteNormalDTO> clientes = clienteNormalService.getAll();
		if (!clientes.isEmpty()) {
			return new ResponseEntity<>(clientes, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(clientes, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Elimina un cliente normal del sistema por su ID.
	 *
	 * @param id Identificador único del cliente.
	 * @return Respuesta con el estado de la eliminación.
	 */
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> eliminarClienteNormal(@RequestParam Long id) {
		try {
			int status = clienteNormalService.deleteById(id);
			if (status == 0) {
				return new ResponseEntity<>("Cliente eliminado correctamente.", HttpStatus.ACCEPTED);
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
	 * Actualiza los datos de un cliente normal existente.
	 *
	 * @param id          Identificador del cliente a actualizar.
	 * @param nombre      Nuevo nombre del cliente.
	 * @param cedula      Nueva cédula del cliente.
	 * @param correo      Nuevo correo electrónico.
	 * @param telefono    Nuevo número telefónico.
	 * @param metodoPago  Nuevo método de pago asignado.
	 * @param contrasenia Nueva contraseña del cliente.
	 * @return Respuesta con el estado de la actualización.
	 */
	@PutMapping("/actualizarclientenormal")
	public ResponseEntity<String> actualizarClienteNormal(@RequestParam Long id, @RequestParam String nombre,
			@RequestParam String cedula, @RequestParam String correo, @RequestParam String telefono,
			@RequestParam String metodoPago, @RequestParam String contrasenia) {
		try {
			ClienteNormalDTO clienteNormalNuevo = new ClienteNormalDTO();
			clienteNormalNuevo.setNombre(nombre);
			clienteNormalNuevo.setCedula(cedula);
			clienteNormalNuevo.setCorreo(correo);
			clienteNormalNuevo.setTelefono(telefono);
			clienteNormalNuevo.setMetodoPago(metodoPago);
			clienteNormalNuevo.setContrasenia(contrasenia);

			int status = clienteNormalService.updateById(id, clienteNormalNuevo);

			if (status == 0) {
				return new ResponseEntity<>("Cliente actualizado correctamente.", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>("Error al actualizar cliente. ID no encontrado.", HttpStatus.BAD_REQUEST);
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
		} catch (TipoPedidoInvalidoException e) {
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
	 * Busca clientes normales por su nombre.
	 *
	 * @param nombre Nombre a buscar.
	 * @return Lista de clientes que coinciden con el nombre.
	 */
	@GetMapping("/buscarpornombre")
	public ResponseEntity<List<ClienteNormalDTO>> buscarPorNombre(@RequestParam String nombre) {
		List<ClienteNormalDTO> lista = clienteNormalService.findByNombre(nombre);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Busca clientes normales por su número de cédula.
	 *
	 * @param cedula Cédula a buscar.
	 * @return Lista de clientes encontrados.
	 */
	@GetMapping("/buscarporcedula")
	public ResponseEntity<List<ClienteNormalDTO>> buscarPorCedula(@RequestParam String cedula) {
		List<ClienteNormalDTO> lista = clienteNormalService.findByCedula(cedula);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Busca clientes normales por correo electrónico.
	 *
	 * @param correo Correo electrónico a buscar.
	 * @return Lista de coincidencias encontradas.
	 */
	@GetMapping("/buscarporcorreo")
	public ResponseEntity<List<ClienteNormalDTO>> buscarPorCorreo(@RequestParam String correo) {
		List<ClienteNormalDTO> lista = clienteNormalService.findByCorreo(correo);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Busca clientes normales por número telefónico.
	 *
	 * @param telefono Teléfono a buscar.
	 * @return Lista de clientes encontrados.
	 */
	@GetMapping("/buscarportelefono")
	public ResponseEntity<List<ClienteNormalDTO>> buscarPorTelefono(@RequestParam String telefono) {
		List<ClienteNormalDTO> lista = clienteNormalService.findByTelefono(telefono);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Busca clientes normales por el método de pago registrado.
	 *
	 * @param metodoPago Método de pago a buscar.
	 * @return Lista de clientes que coinciden con el método.
	 */
	@GetMapping("/buscarpormetodopago")
	public ResponseEntity<List<ClienteNormalDTO>> buscarPorMetodoPago(@RequestParam String metodoPago) {
		List<ClienteNormalDTO> lista = clienteNormalService.findByMetodoPago(metodoPago);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Busca clientes normales filtrando por nombre y cédula simultáneamente.
	 *
	 * @param nombre Nombre del cliente.
	 * @param cedula Cédula del cliente.
	 * @return Lista de coincidencias encontradas.
	 */
	@GetMapping("/buscarpornombreycedula")
	public ResponseEntity<List<ClienteNormalDTO>> buscarPorNombreAndCedula(@RequestParam String nombre,
			@RequestParam String cedula) {
		List<ClienteNormalDTO> lista = clienteNormalService.findByNombreAndCedula(nombre, cedula);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

}