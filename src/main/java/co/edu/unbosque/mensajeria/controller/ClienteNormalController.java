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
import co.edu.unbosque.mensajeria.exception.CedulaInvalidaException;
import co.edu.unbosque.mensajeria.exception.CorreoInvalidoException;
import co.edu.unbosque.mensajeria.exception.IdInvalidoException;
import co.edu.unbosque.mensajeria.exception.MetodoDePagoInvalidoException;
import co.edu.unbosque.mensajeria.exception.NombreInvalidoException;
import co.edu.unbosque.mensajeria.exception.TelefonoInvalidoException;
import co.edu.unbosque.mensajeria.exception.TipoPedidoInvalidoException;
import co.edu.unbosque.mensajeria.service.ClienteNormalService;

@RestController
@RequestMapping("/clientenormal")
@CrossOrigin(origins = { "http://localhost:8080", "*" })
public class ClienteNormalController {

	@Autowired
	private ClienteNormalService clienteNormalService;

	public ClienteNormalController() {
	}

	// http://localhost:8080/clientenormal/crear?nombre=Pedro&cedula=5566&correo=pedro@mail.com&telefono=310111&tipoPedido=Salud&metodoPago=Efectivo
	@PostMapping("/crear")
	public ResponseEntity<String> crearClienteNormal(@RequestParam String nombre, @RequestParam String cedula,
			@RequestParam String correo, @RequestParam String telefono, @RequestParam String metodoPago,
			@RequestParam String tipoPedido) {

		try {
			ClienteNormalDTO nuevoClienteNormal = new ClienteNormalDTO();
			nuevoClienteNormal.setNombre(nombre);
			nuevoClienteNormal.setCedula(cedula);
			nuevoClienteNormal.setCorreo(correo);
			nuevoClienteNormal.setTelefono(telefono);
			nuevoClienteNormal.setMetodoPago(metodoPago);
			nuevoClienteNormal.setTipoPedido(tipoPedido);

			int status = clienteNormalService.create(nuevoClienteNormal);

			if (status == 0) {
				return new ResponseEntity<>("Dato creado con éxito", HttpStatus.CREATED);
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
		} catch (MetodoDePagoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TipoPedidoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// http://localhost:8080/clientenormal/mostrartodo
	@GetMapping("/mostrar")
	public ResponseEntity<List<ClienteNormalDTO>> mostrarTodo() {
		List<ClienteNormalDTO> clientes = clienteNormalService.getAll();
		if (!clientes.isEmpty()) {
			return new ResponseEntity<>(clientes, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(clientes, HttpStatus.NO_CONTENT);
		}
	}

	// http://localhost:8080/clientenormal/eliminar?id=1
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> eliminarClienteNormal(@RequestParam Long id) {
		try {
			int status = clienteNormalService.deleteById(id);
			if (status == 0) {
				return new ResponseEntity<>("Cliente eliminado correctamente.", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>("Error al eliminar cliente.", HttpStatus.BAD_REQUEST);
			}
		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Error al procesar la solicitud", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// http://localhost:8080/clientenormal/actualizar?id=1&nombre=Pedro+Gomez&cedula=5566&correo=pgomez@mail.com&telefono=310999&tipoPedido=Hogar&metodoPago=Transferencia
	@PutMapping("/actualizarclientenormal")
	public ResponseEntity<String> actualizarClienteNormal(@RequestParam Long id, @RequestParam String nombre,
			@RequestParam String cedula, @RequestParam String correo, @RequestParam String telefono,
			@RequestParam String metodoPago, @RequestParam String tipoPedido) {
		try {
			ClienteNormalDTO clienteNormalNuevo = new ClienteNormalDTO();
			clienteNormalNuevo.setNombre(nombre);
			clienteNormalNuevo.setCedula(cedula);
			clienteNormalNuevo.setCorreo(correo);
			clienteNormalNuevo.setTelefono(telefono);
			clienteNormalNuevo.setMetodoPago(metodoPago);
			clienteNormalNuevo.setTipoPedido(tipoPedido);

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
		} catch (Exception e) {
			return new ResponseEntity<>("Error inesperado", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// http://localhost:8080/clientenormal/buscarpornombre?nombre=Pedro
	@GetMapping("/buscarpornombre")
	public ResponseEntity<List<ClienteNormalDTO>> buscarPorNombre(@RequestParam String nombre) {
		List<ClienteNormalDTO> lista = clienteNormalService.findByNombre(nombre);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}
	
	// http://localhost:8080/clientenormal/buscarporcedula?cedula=5566
	@GetMapping("/buscarporcedula")
	public ResponseEntity<List<ClienteNormalDTO>> buscarPorCedula(@RequestParam String cedula) {
		List<ClienteNormalDTO> lista = clienteNormalService.findByCedula(cedula);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}
	
	// http://localhost:8080/clientenormal/buscarporcorreo?correo=pedro@mail.com
	@GetMapping("/buscarporcorreo")
	public ResponseEntity<List<ClienteNormalDTO>> buscarPorCorreo(@RequestParam String correo) {
		List<ClienteNormalDTO> lista = clienteNormalService.findByCorreo(correo);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}
	
	// http://localhost:8080/clientenormal/buscarportelefono?telefono=310111
	@GetMapping("/buscarportelefono")
	public ResponseEntity<List<ClienteNormalDTO>> buscarPorTelefono(@RequestParam String telefono) {
		List<ClienteNormalDTO> lista = clienteNormalService.findByTelefono(telefono);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}
	
	// http://localhost:8080/clientenormal/buscarpormetodopago?metodoPago=Efectivo
	@GetMapping("/buscarpormetodopago")
	public ResponseEntity<List<ClienteNormalDTO>> buscarPorMetodoPago(@RequestParam String metodoPago) {
		List<ClienteNormalDTO> lista = clienteNormalService.findByMetodoPago(metodoPago);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}
	
	// http://localhost:8080/clientenormal/buscarportipopedido?tipoPedido=Salud
	@GetMapping("/buscarportipopedido")
	public ResponseEntity<List<ClienteNormalDTO>> buscarPorTipoPedido(@RequestParam String tipoPedido) {
		List<ClienteNormalDTO> lista = clienteNormalService.findByTipoPedido(tipoPedido);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	// http://localhost:8080/clientenormal/buscarpornombreycedula?nombre=Pedro&cedula=5566
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
	
	// http://localhost:8080/clientenormal/buscarportipopedidoymetodopago?tipoPedido=Salud&metodoPago=Efectivo
	@GetMapping("/buscarportipopedidoymetodopago")
	public ResponseEntity<List<ClienteNormalDTO>> buscarPorTipoPedidoAndMetodoPago(@RequestParam String tipoPedido,
			@RequestParam String metodoPago) {
		List<ClienteNormalDTO> lista = clienteNormalService.findByTipoPedidoAndMetodoPago(tipoPedido, metodoPago);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}
}