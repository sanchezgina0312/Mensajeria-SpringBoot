package co.edu.unbosque.mensajeria.controller;

//:)))) jijiji
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
import co.edu.unbosque.mensajeria.exception.CedulaInvalidaException;
import co.edu.unbosque.mensajeria.exception.CorreoInvalidoException;
import co.edu.unbosque.mensajeria.exception.IdInvalidoException;
import co.edu.unbosque.mensajeria.exception.MetodoDePagoInvalidoException;
import co.edu.unbosque.mensajeria.exception.NombreInvalidoException;
import co.edu.unbosque.mensajeria.exception.TelefonoInvalidoException;
import co.edu.unbosque.mensajeria.exception.TipoPedidoInvalidoException;
import co.edu.unbosque.mensajeria.exception.TurnoInvalidoException;
import co.edu.unbosque.mensajeria.service.ClienteConcurrenteService;

@RestController
@RequestMapping("/clienteconcurrente")
@CrossOrigin(origins = { "http://localhost:8080", "*" })
public class ClienteConcurrenteController {

	@Autowired
	private ClienteConcurrenteService clienteConcurrenteService;

	public ClienteConcurrenteController() {
	}

	// http://localhost:8080/clienteconcurrente/crear?nombre=Frecuente&cedula=222&correo=f@mail.com&telefono=800&metodoPago=Debito&tipoPedido=Diario&tarifaConcurrente=1.2
	@PostMapping("/crear")
	public ResponseEntity<String> crearClienteConcurrente(@RequestParam String nombre, @RequestParam String cedula,
			@RequestParam String correo, @RequestParam String telefono, @RequestParam String metodoPago,
			@RequestParam String tipoPedido, @RequestParam double tarifaConcurrente) {

		try {

			ClienteConcurrenteDTO nuevoClienteConcurrente = new ClienteConcurrenteDTO();

			nuevoClienteConcurrente.setNombre(nombre);
			nuevoClienteConcurrente.setCedula(cedula);
			nuevoClienteConcurrente.setCorreo(correo);
			nuevoClienteConcurrente.setTelefono(telefono);
			nuevoClienteConcurrente.setMetodoPago(metodoPago);
			nuevoClienteConcurrente.setTipoPedido(tipoPedido);
			nuevoClienteConcurrente.setTarifaConcurrente(tarifaConcurrente);

			int status = clienteConcurrenteService.create(nuevoClienteConcurrente);

			if (status == 0) {
				return new ResponseEntity<>("Dato creado con éxito", HttpStatus.CREATED);
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
		}

	}

	// http://localhost:8080/clienteconcurrente/mostrartodo
	@GetMapping("/mostrar")
	public ResponseEntity<List<ClienteConcurrenteDTO>> mostrarTodo() {
		List<ClienteConcurrenteDTO> clientes = clienteConcurrenteService.getAll();

		if (!clientes.isEmpty()) {
			return new ResponseEntity<List<ClienteConcurrenteDTO>>(clientes, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<List<ClienteConcurrenteDTO>>(clientes, HttpStatus.BAD_REQUEST);
		}
	}

	// http://localhost:8080/clienteconcurrente/eliminar?id=1
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> eliminarClienteConcurrente(@RequestParam Long id) {
		try {
			int status = clienteConcurrenteService.deleteById(id);
			if (status == 0) {
				return new ResponseEntity<>("Cliente eliminado correctamente. ", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>("Error al eliminar cliente. ", HttpStatus.BAD_REQUEST);
			}
		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// http://localhost:8080/clienteconcurrente/actualizarclienteconcurrente?id=1&nombre=Maria&cedula=789&correo=m@mail.com&telefono=500&metodoPago=Transferencia&tipoPedido=Recurrente&tarifaConcurrente=2000.0
	@PutMapping("/actualizarclienteconcurrente")
	public ResponseEntity<String> actualizarClienteConcurrente(@RequestParam Long id, @RequestParam String nombre,
			@RequestParam String cedula, @RequestParam String correo, @RequestParam String telefono,
			@RequestParam String metodoPago, @RequestParam String tipoPedido, @RequestParam double tarifaConcurrente) {
		try {
			ClienteConcurrenteDTO clienteConcurrenteNuevo = new ClienteConcurrenteDTO();

			clienteConcurrenteNuevo.setNombre(nombre);
			clienteConcurrenteNuevo.setCedula(cedula);
			clienteConcurrenteNuevo.setCorreo(correo);
			clienteConcurrenteNuevo.setTelefono(telefono);
			clienteConcurrenteNuevo.setMetodoPago(metodoPago);
			clienteConcurrenteNuevo.setTipoPedido(tipoPedido);
			clienteConcurrenteNuevo.setTarifaConcurrente(tarifaConcurrente);

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
		} catch (TipoPedidoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/buscarpornombre")
	public ResponseEntity<List<ClienteConcurrenteDTO>> findByNombre(@RequestParam String nombre) {

		List<ClienteConcurrenteDTO> lista = clienteConcurrenteService.findByNombre(nombre);

		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/buscarporcedula")
	public ResponseEntity<List<ClienteConcurrenteDTO>> findByCedula(@RequestParam String cedula) {

		List<ClienteConcurrenteDTO> lista = clienteConcurrenteService.findByCedula(cedula);

		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/buscarporcorreo")
	public ResponseEntity<List<ClienteConcurrenteDTO>> findByCorreo(@RequestParam String correo) {

		List<ClienteConcurrenteDTO> lista = clienteConcurrenteService.findByCorreo(correo);

		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/buscarportelefono")
	public ResponseEntity<List<ClienteConcurrenteDTO>> findByTelefono(@RequestParam String telefono) {

		List<ClienteConcurrenteDTO> lista = clienteConcurrenteService.findByTelefono(telefono);

		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/buscarpormetodopago")
	public ResponseEntity<List<ClienteConcurrenteDTO>> findByMetodoPago(@RequestParam String metodoPago) {

		List<ClienteConcurrenteDTO> lista = clienteConcurrenteService.findByMetodoPago(metodoPago);

		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/buscarportipopedido")
	public ResponseEntity<List<ClienteConcurrenteDTO>> findByTipoPedido(@RequestParam String tipoPedido) {

		List<ClienteConcurrenteDTO> lista = clienteConcurrenteService.findByTipoPedido(tipoPedido);

		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/buscarpornombreycedula")
	public ResponseEntity<List<ClienteConcurrenteDTO>> findByNombreAndCedula(@RequestParam String nombre,
			@RequestParam String cedula) {

		List<ClienteConcurrenteDTO> lista = clienteConcurrenteService.findByNombreAndCedula(nombre, cedula);

		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/buscarportipopedidoymetodopago")
	public ResponseEntity<List<ClienteConcurrenteDTO>> findByTipoPedidoAndMetodoPago(@RequestParam String tipoPedido,
			@RequestParam String metodoPago) {

		List<ClienteConcurrenteDTO> lista = clienteConcurrenteService.findByTipoPedidoAndMetodoPago(tipoPedido,
				metodoPago);

		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.BAD_REQUEST);
		}
	}
}