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
import co.edu.unbosque.mensajeria.service.ClientePremiumService;

@RestController
@RequestMapping("/clientepremium")
@CrossOrigin(origins = { "http://localhost:8080", "*" })

public class ClientePremiumController {

	@Autowired
	private ClientePremiumService clientePremiumService;

	public ClientePremiumController() {
	}

	// http://localhost:8080/clientepremium/crear?nombre=VIP&cedula=111&correo=vip@mail.com&telefono=900&metodoPago=Tarjeta&tipoPedido=Gold&tarifaPremium=0.8
	@PostMapping("/crear")
	public ResponseEntity<String> crearClientePremium(@RequestParam String nombre, @RequestParam String cedula,
			@RequestParam String correo, @RequestParam String telefono, @RequestParam String metodoPago,
			@RequestParam String tipoPedido, @RequestParam double tarifaPremium) {

		ClientePremiumDTO nuevoClientePremium = new ClientePremiumDTO(nombre, cedula, correo, telefono, metodoPago,
				tipoPedido, tarifaPremium);
		int status = clientePremiumService.create(nuevoClientePremium);

		if (status == 0) {

			return new ResponseEntity<>("Cliente creado con Ã©xito.", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error al crear cliente.", HttpStatus.BAD_REQUEST);
		}
	}

	// http://localhost:8080/clientepremium/mostrartodo
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<ClientePremiumDTO>> mostrarTodo() {
		List<ClientePremiumDTO> clientes = clientePremiumService.getAll();

		if (clientes.isEmpty()) {
			return new ResponseEntity<List<ClientePremiumDTO>>(clientes, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<ClientePremiumDTO>>(clientes, HttpStatus.ACCEPTED);
		}
	}

	// http://localhost:8080/clientepremium/eliminar?id=1
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> eliminarClientePremium(@RequestParam Long id) {
		int status = clientePremiumService.deleteById(id);
		if (status == 0) {
			return new ResponseEntity<>("Cliente eliminado correctamente. ", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Error al eliminar cliente. ", HttpStatus.BAD_REQUEST);
		}
	}

	// http://localhost:8080/clientepremium/actualizar?id=1&nombre=Carlos&cedula=456&correo=c@mail.com&telefono=400&metodoPago=Tarjeta&tipoPedido=Prioritario&tarifaPremium=2500.0
	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizarClientePremium(@RequestParam Long id, @RequestParam String nombre,
			@RequestParam String cedula, @RequestParam String correo, @RequestParam String telefono,
			@RequestParam String metodoPago, @RequestParam String tipoPedido, @RequestParam double tarifaPremium) {

		ClientePremiumDTO clientePremiumNuevo = new ClientePremiumDTO(nombre, cedula, correo, telefono, metodoPago,
				tipoPedido, tarifaPremium);

		int status = clientePremiumService.updateById(id, clientePremiumNuevo);

		if (status == 0) {
			return new ResponseEntity<>("Cliente actualizado correctamente. ", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Error al actualizar dcliente. ", HttpStatus.BAD_REQUEST);
		}

	}
}