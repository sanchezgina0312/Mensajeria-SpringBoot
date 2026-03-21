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

		ClienteConcurrenteDTO nuevoClienteConcurrente = new ClienteConcurrenteDTO(nombre, cedula, correo, telefono,
				metodoPago, tipoPedido, tarifaConcurrente);

		int status = clienteConcurrenteService.create(nuevoClienteConcurrente);

		if (status == 0) {

			return new ResponseEntity<>("Cliente creado con Ã©xito.", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error al crear cliente.", HttpStatus.BAD_REQUEST);
		}
	}

	// http://localhost:8080/clienteconcurrente/mostrartodo
	@GetMapping("/mostrar")
	public ResponseEntity<List<ClienteConcurrenteDTO>> mostrarTodo() {
		List<ClienteConcurrenteDTO> clientes = clienteConcurrenteService.getAll();

		if (clientes.isEmpty()) {
			return new ResponseEntity<List<ClienteConcurrenteDTO>>(clientes, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<ClienteConcurrenteDTO>>(clientes, HttpStatus.ACCEPTED);
		}
	}

	// http://localhost:8080/clienteconcurrente/eliminar?id=1
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> eliminarClienteConcurrente(@RequestParam Long id) {
		int status = clienteConcurrenteService.deleteById(id);
		if (status == 0) {
			return new ResponseEntity<>("Cliente eliminado correctamente. ", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Error al eliminar cliente. ", HttpStatus.BAD_REQUEST);
		}
	}

	// http://localhost:8080/clienteconcurrente/actualizarclienteconcurrente?id=1&nombre=Maria&cedula=789&correo=m@mail.com&telefono=500&metodoPago=Transferencia&tipoPedido=Recurrente&tarifaConcurrente=2000.0
	@PutMapping("/actualizarclienteconcurrente")
	public ResponseEntity<String> actualizarClienteConcurrente(@RequestParam Long id, @RequestParam String nombre,
			@RequestParam String cedula, @RequestParam String correo, @RequestParam String telefono,
			@RequestParam String metodoPago, @RequestParam String tipoPedido, @RequestParam double tarifaConcurrente) {

		ClienteConcurrenteDTO clienteConcurrenteNuevo = new ClienteConcurrenteDTO(nombre, cedula, correo, telefono,
				metodoPago, tipoPedido, tarifaConcurrente);

		int status = clienteConcurrenteService.updateById(id, clienteConcurrenteNuevo);

		if (status == 0) {
			return new ResponseEntity<>("Cliente actualizado correctamente. ", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Error al actualizar cliente. ", HttpStatus.BAD_REQUEST);
		}

	}

}