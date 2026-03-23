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

import co.edu.unbosque.mensajeria.dto.ClientePremiumDTO;
import co.edu.unbosque.mensajeria.exception.CedulaInvalidaException;
import co.edu.unbosque.mensajeria.exception.CorreoInvalidoException;
import co.edu.unbosque.mensajeria.exception.IdInvalidoException;
import co.edu.unbosque.mensajeria.exception.MetodoDePagoInvalidoException;
import co.edu.unbosque.mensajeria.exception.NombreInvalidoException;
import co.edu.unbosque.mensajeria.exception.TelefonoInvalidoException;
import co.edu.unbosque.mensajeria.exception.TipoPedidoInvalidoException;
import co.edu.unbosque.mensajeria.service.ClientePremiumService;

@RestController
@RequestMapping("/clientepremium")
@CrossOrigin(origins = { "http://localhost:8080", "*" })
public class ClientePremiumController {

	@Autowired
	private ClientePremiumService clientePremiumService;

	public ClientePremiumController() {
	}

	// http://localhost:8080/clientepremium/crear?nombre=Sebastian&cedula=9988&correo=sebas@mail.com&telefono=315222&tipoPedido=Alimenticio&metodoPago=Tarjeta
	@PostMapping("/crear")
	public ResponseEntity<String> crearClientePremium(@RequestParam String nombre, @RequestParam String cedula,
			@RequestParam String correo, @RequestParam String telefono, @RequestParam String metodoPago,
			@RequestParam String tipoPedido) {

		try {
			ClientePremiumDTO nuevoClientePremium = new ClientePremiumDTO();
			nuevoClientePremium.setNombre(nombre);
			nuevoClientePremium.setCedula(cedula);
			nuevoClientePremium.setCorreo(correo);
			nuevoClientePremium.setTelefono(telefono);
			nuevoClientePremium.setMetodoPago(metodoPago);
			nuevoClientePremium.setTipoPedido(tipoPedido);

			int status = clientePremiumService.create(nuevoClientePremium);

			if (status == 0) {
				return new ResponseEntity<>("Dato creado con éxito", HttpStatus.CREATED);
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
		} catch (MetodoDePagoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TipoPedidoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// http://localhost:8080/clientepremium/mostrartodo
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<ClientePremiumDTO>> mostrarTodo() {
		List<ClientePremiumDTO> clientes = clientePremiumService.getAll();
		if (!clientes.isEmpty()) {
			return new ResponseEntity<>(clientes, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(clientes, HttpStatus.NO_CONTENT);
		}
	}

	// http://localhost:8080/clientepremium/eliminar?id=1
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> eliminarClientePremium(@RequestParam Long id) {
		try {
			int status = clientePremiumService.deleteById(id);
			if (status == 0) {
				return new ResponseEntity<>("Cliente eliminado correctamente.", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Error al eliminar cliente.", HttpStatus.BAD_REQUEST);
			}
		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Error al procesar la solicitud", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// http://localhost:8080/clientepremium/actualizar?id=1&nombre=Sebastian+Vip&cedula=9988&correo=sebas_nuevo@mail.com&telefono=315333&tipoPedido=Importados&metodoPago=Puntos
	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizarClientePremium(@RequestParam Long id, @RequestParam String nombre,
			@RequestParam String cedula, @RequestParam String correo, @RequestParam String telefono,
			@RequestParam String metodoPago, @RequestParam String tipoPedido, @RequestParam double tarifaPremium) {

		try {
			ClientePremiumDTO clientePremiumNuevo = new ClientePremiumDTO();
			clientePremiumNuevo.setNombre(nombre);
			clientePremiumNuevo.setCedula(cedula);
			clientePremiumNuevo.setCorreo(correo);
			clientePremiumNuevo.setTelefono(telefono);
			clientePremiumNuevo.setMetodoPago(metodoPago);
			clientePremiumNuevo.setTipoPedido(tipoPedido);
			clientePremiumNuevo.setTarifaPremium(tarifaPremium);

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
		} catch (TipoPedidoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Error inesperado", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// http://localhost:8080/clientepremium/buscarpornombre?nombre=Sebastian
	@GetMapping("/buscarpornombre")
	public ResponseEntity<List<ClientePremiumDTO>> buscarPorNombre(@RequestParam String nombre) {
		List<ClientePremiumDTO> lista = clientePremiumService.findByNombre(nombre);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}
	
	// http://localhost:8080/clientepremium/buscarporcedula?cedula=9988
	@GetMapping("/buscarporcedula")
	public ResponseEntity<List<ClientePremiumDTO>> buscarPorCedula(@RequestParam String cedula) {
		List<ClientePremiumDTO> lista = clientePremiumService.findByCedula(cedula);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}
	
	// http://localhost:8080/clientepremium/buscarporcorreo?correo=sebas@mail.com
	@GetMapping("/buscarporcorreo")
	public ResponseEntity<List<ClientePremiumDTO>> buscarPorCorreo(@RequestParam String correo) {
		List<ClientePremiumDTO> lista = clientePremiumService.findByCorreo(correo);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}
	
	// http://localhost:8080/clientepremium/buscarportelefono?telefono=315222
	@GetMapping("/buscarportelefono")
	public ResponseEntity<List<ClientePremiumDTO>> buscarPorTelefono(@RequestParam String telefono) {
		List<ClientePremiumDTO> lista = clientePremiumService.findByTelefono(telefono);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}
	
	// http://localhost:8080/clientepremium/buscarpormetodopago?metodoPago=Tarjeta
	@GetMapping("/buscarpormetodopago")
	public ResponseEntity<List<ClientePremiumDTO>> buscarPorMetodoPago(@RequestParam String metodoPago) {
		List<ClientePremiumDTO> lista = clientePremiumService.findByMetodoPago(metodoPago);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}
	
	// http://localhost:8080/clientepremium/buscarportipopedido?tipoPedido=Alimenticio
	@GetMapping("/buscarportipopedido")
	public ResponseEntity<List<ClientePremiumDTO>> buscarPorTipoPedido(@RequestParam String tipoPedido) {
		List<ClientePremiumDTO> lista = clientePremiumService.findByTipoPedido(tipoPedido);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}
	
	// http://localhost:8080/clientepremium/buscarpornombreycedula?nombre=Sebastian&cedula=9988
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
	
	// http://localhost:8080/clientepremium/buscarportipopedidoymetodopago?tipoPedido=Alimenticio&metodoPago=Tarjeta
	@GetMapping("/buscarportipopedidoymetodopago")
	public ResponseEntity<List<ClientePremiumDTO>> buscarPorTipoPedidoAndMetodoPago(@RequestParam String tipoPedido,
			@RequestParam String metodoPago) {
		List<ClientePremiumDTO> lista = clientePremiumService.findByTipoPedidoAndMetodoPago(tipoPedido, metodoPago);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}
}