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

	// http://localhost:8080/clientepremium/crear?nombre=VIP&cedula=111&correo=vip@mail.com&telefono=900&metodoPago=Tarjeta&tipoPedido=Gold&tarifaPremium=0.8
	@PostMapping("/crear")
	public ResponseEntity<String> crearClientePremium(@RequestParam String nombre, @RequestParam String cedula,
			@RequestParam String correo, @RequestParam String telefono, @RequestParam String metodoPago,
			@RequestParam String tipoPedido, @RequestParam double tarifaPremium) {

		try {
		ClientePremiumDTO nuevoClientePremium = new ClientePremiumDTO();
		
		nuevoClientePremium.setNombre(nombre);
		nuevoClientePremium.setCedula(cedula);
		nuevoClientePremium.setCorreo(correo);
		nuevoClientePremium.setTelefono(telefono);
		nuevoClientePremium.setMetodoPago(metodoPago);
		nuevoClientePremium.setTipoPedido(tipoPedido);
		nuevoClientePremium.setTarifaPremium(tarifaPremium);

		clientePremiumService.create(nuevoClientePremium);
		
		return new ResponseEntity<>("Cliente premium creado con éxito", HttpStatus.CREATED);

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
		}catch (TipoPedidoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
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
		try {
		int status = clientePremiumService.deleteById(id);
		if (status == 0) {
			return new ResponseEntity<>("Cliente eliminado correctamente. ", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Error al eliminar cliente. ", HttpStatus.BAD_REQUEST);
		}
		}catch(IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			
		}
	}

	// http://localhost:8080/clientepremium/actualizar?id=1&nombre=Carlos&cedula=456&correo=c@mail.com&telefono=400&metodoPago=Tarjeta&tipoPedido=Prioritario&tarifaPremium=2500.0
	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizarClientePremium(@RequestParam Long id, @RequestParam String nombre,
			@RequestParam String cedula, @RequestParam String correo, @RequestParam String telefono,
			@RequestParam String metodoPago, @RequestParam String tipoPedido, @RequestParam double tarifaPremium) {

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
			return new ResponseEntity<>("Cliente actualizado correctamente. ", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Error al actualizar dcliente. ", HttpStatus.BAD_REQUEST);
		}

	}
	
	@GetMapping("/buscarpornombre")
	public ResponseEntity<List<ClientePremiumDTO>> findByNombre(@RequestParam String nombre) {

		List<ClientePremiumDTO> lista = clientePremiumService.findByNombre(nombre);

		if (lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.OK);
		}
	}
	
	@GetMapping("/buscarporcedula")
	public ResponseEntity<List<ClientePremiumDTO>> findByCedula(@RequestParam String cedula) {

		List<ClientePremiumDTO> lista = clientePremiumService.findByCedula(cedula);

		if (lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.OK);
		}
	}
	
	@GetMapping("/buscarporcorreo")
	public ResponseEntity<List<ClientePremiumDTO>> findByCorreo(@RequestParam String correo) {

		List<ClientePremiumDTO> lista = clientePremiumService.findByCorreo(correo);

		if (lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.OK);
		}
	}
	
	@GetMapping("/buscarportelefono")
	public ResponseEntity<List<ClientePremiumDTO>> findByTelefono(@RequestParam String telefono) {

		List<ClientePremiumDTO> lista = clientePremiumService.findByTelefono(telefono);

		if (lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.OK);
		}
	}
	
	@GetMapping("/buscarpormetodopago")
	public ResponseEntity<List<ClientePremiumDTO>> findByMetodoPago(@RequestParam String metodoPago) {

		List<ClientePremiumDTO> lista = clientePremiumService.findByMetodoPago(metodoPago);

		if (lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.OK);
		}
	}
	
	@GetMapping("/buscarportipopedido")
	public ResponseEntity<List<ClientePremiumDTO>> findByTipoPedido(@RequestParam String tipoPedido) {

		List<ClientePremiumDTO> lista = clientePremiumService.findByTipoPedido(tipoPedido);

		if (lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.OK);
		}
	}
	
	@GetMapping("/buscarpornombreycedula")
	public ResponseEntity<List<ClientePremiumDTO>> findByNombreYCedula(
			@RequestParam String nombre,
			@RequestParam String cedula) {

		List<ClientePremiumDTO> lista = clientePremiumService.findByNombreAndCedula(nombre, cedula);

		if (lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.OK);
		}
	}
	
	@GetMapping("/buscarportipopedidoymetodopago")
	public ResponseEntity<List<ClientePremiumDTO>> findByTipoPedidoYMetodoPago(
			@RequestParam String tipoPedido,
			@RequestParam String metodoPago) {

		List<ClientePremiumDTO> lista = clientePremiumService.findByTipoPedidoAndMetodoPago(tipoPedido, metodoPago);

		if (lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.OK);
		}
	}
}