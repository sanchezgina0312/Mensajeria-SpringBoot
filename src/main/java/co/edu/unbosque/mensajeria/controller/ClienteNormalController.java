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
import co.edu.unbosque.mensajeria.service.ClienteNormalService;

@RestController
@RequestMapping("/clientenormal")
@CrossOrigin(origins = { "http://localhost:8080", "*" })

public class ClienteNormalController {

	@Autowired
	private ClienteNormalService clienteNormalService;

	public ClienteNormalController() {
	}
	
	// http://localhost:8080/clientenormal/crear?nombre=Ana&cedula=789&correo=ana@mail.com&telefono=500&metodoPago=Efectivo&tipoPedido=Express&tarifaNormal=1.5
	@PostMapping("/crear")
	public ResponseEntity<String> crearClienteNormal(@RequestParam String nombre, @RequestParam String cedula,
			@RequestParam String correo, @RequestParam String telefono, @RequestParam String metodoPago,
			@RequestParam String tipoPedido, @RequestParam double tarifaNormal) {

		ClienteNormalDTO nuevoclienteNormal = new ClienteNormalDTO(nombre, cedula, correo, telefono, metodoPago,
				tipoPedido, tarifaNormal);
		int status = clienteNormalService.create(nuevoclienteNormal);

		if (status == 0) {

			return new ResponseEntity<>("Cliente creado con Ã©xito.", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error al crear cliente.", HttpStatus.BAD_REQUEST);
		}
	}

	// http://localhost:8080/clientenormal/mostrartodo
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<ClienteNormalDTO>> mostrarTodo() {
		List<ClienteNormalDTO> clientes = clienteNormalService.getAll();

		if (clientes.isEmpty()) {
			return new ResponseEntity<List<ClienteNormalDTO>>(clientes, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<ClienteNormalDTO>>(clientes, HttpStatus.ACCEPTED);
		}
	}

	// http://localhost:8080/clientenormal/eliminar?id=1
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> eliminarClienteNormal(@RequestParam Long id) {
		int status = clienteNormalService.deleteById(id);
		if (status == 0) {
			return new ResponseEntity<>("Cliente eliminado correctamente. ", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Error al eliminar cliente. ", HttpStatus.BAD_REQUEST);
		}
	}

	// http://localhost:8080/clientenormal/actualizar?id=1&nombre=Juan&cedula=123&correo=j@mail.com&telefono=300&metodoPago=Efectivo&tipoPedido=Estandar&tarifaNormal=1500.0
	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizarClienteNormal(@RequestParam Long id, @RequestParam String nombre,
			@RequestParam String cedula, @RequestParam String correo, @RequestParam String telefono,
			@RequestParam String metodoPago, @RequestParam String tipoPedido, @RequestParam double tarifaNormal) {

		ClienteNormalDTO clienteNormalNuevo = new ClienteNormalDTO(nombre, cedula, correo, telefono, metodoPago,
				tipoPedido, tarifaNormal);

		int status = clienteNormalService.updateById(id, clienteNormalNuevo);

		if (status == 0) {
			return new ResponseEntity<>("Cliente actualizado correctamente. ", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Error al actualizar cliente. ", HttpStatus.BAD_REQUEST);
		}

	}

}