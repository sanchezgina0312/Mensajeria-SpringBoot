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

import co.edu.unbosque.mensajeria.dto.ConductorDTO;
import co.edu.unbosque.mensajeria.exception.CedulaInvalidaException;
import co.edu.unbosque.mensajeria.exception.CorreoInvalidoException;
import co.edu.unbosque.mensajeria.exception.NombreInvalidoException;
import co.edu.unbosque.mensajeria.exception.PlacaInvalidaException;
import co.edu.unbosque.mensajeria.exception.TelefonoInvalidoException;
import co.edu.unbosque.mensajeria.exception.TurnoInvalidoException;
import co.edu.unbosque.mensajeria.service.ConductorService;

@RestController
@RequestMapping("/conductor")
@CrossOrigin(origins = { "http://localhost:8080", "*" })
public class ConductorController {

	@Autowired
	private ConductorService conductorSer;

	public ConductorController() {

	}

	// http://localhost:8080/conductor/crear?nombre=Juan&cedula=123&correo=juan@mail.com&telefono=3001&turno=M&placaVehiculo=ABC123
	@PostMapping("/crear")
	public ResponseEntity<String> crearPaqueteAlimenticio(@RequestParam String nombre, @RequestParam String cedula,
			@RequestParam String correo, @RequestParam String telefono, @RequestParam char turno,
			@RequestParam String placaVehiculo) {

		try {
			ConductorDTO nuevo = new ConductorDTO(nombre, cedula, correo, telefono, turno, placaVehiculo);
			int status = conductorSer.create(nuevo);
			if (status == 0) {
				return new ResponseEntity<>("Dato creado con exito", HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Error al crear el paquete no alimenticio", HttpStatus.BAD_REQUEST);
			}

		} catch (NombreInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

		} catch (CedulaInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

		} catch (CorreoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

		} catch (TelefonoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

		} catch (TurnoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

		} catch (PlacaInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	// http://localhost:8080/conductor/mostrartodo
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<ConductorDTO>> mostrarTodo() {
		List<ConductorDTO> conductores = conductorSer.getAll();
		if (conductores.isEmpty()) {
			return new ResponseEntity<List<ConductorDTO>>(conductores, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<ConductorDTO>>(conductores, HttpStatus.ACCEPTED);
		}
	}

	// http://localhost:8080/conductor/actualizar?id=1&nombre=Juan&cedula=123&correo=j@mail.com&telefono=300&turno=T&placaVehiculo=XYZ789
	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizar(@RequestParam Long id, @RequestParam String nombre,
			@RequestParam String cedula, @RequestParam String correo, @RequestParam String telefono,
			@RequestParam char turno, @RequestParam String placaVehiculo) {
		ConductorDTO nuevo = new ConductorDTO(nombre, cedula, correo, telefono, turno, placaVehiculo);
		int status = conductorSer.updateById(id, nuevo);
		if (status == 0) {
			return new ResponseEntity<>("Dato actualizado con éxito", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>("Error: El ID " + id + " no existe en la base de datos",
					HttpStatus.BAD_REQUEST);
		}
	}

	// http://localhost:8080/conductor/eliminar?id=1
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> delete(@RequestParam Long id) {
		int status = conductorSer.deleteById(id);
		if (status == 0) {
			return new ResponseEntity<>("Dato eliminado con éxito", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>("Error: No se encontró el registro con ID " + id, HttpStatus.BAD_REQUEST);
		}
	}

	// http://localhost:8080/conductor/buscarpornombre?nombre=Juan Perez
	@GetMapping("/buscarpornombre")
	public ResponseEntity<List<ConductorDTO>> buscarPorNombre(@RequestParam String nombre) {

		List<ConductorDTO> lista = conductorSer.findByNombre(nombre);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	// http://localhost:8080/conductor/buscarporcedula?cedula=123456789
	@GetMapping("/buscarporcedula")
	public ResponseEntity<List<ConductorDTO>> buscarPorCedula(@RequestParam String cedula) {

		List<ConductorDTO> lista = conductorSer.findByCedula(cedula);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	// http://localhost:8080/conductor/buscarporcorreo?correo=juan@mail.com
	@GetMapping("/buscarporcorreo")
	public ResponseEntity<List<ConductorDTO>> buscarPorCorreo(@RequestParam String correo) {

		List<ConductorDTO> lista = conductorSer.findByCorreo(correo);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	// http://localhost:8080/conductor/buscarportelefono?telefono=3001234567
	@GetMapping("/buscarportelefono")
	public ResponseEntity<List<ConductorDTO>> buscarPorTelefono(@RequestParam String telefono) {

		List<ConductorDTO> lista = conductorSer.findByTelefono(telefono);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	// http://localhost:8080/conductor/buscarporplacavehiculo?placaVehiculo=ABC123
	@GetMapping("/buscarporplacavehiculo")
	public ResponseEntity<List<ConductorDTO>> buscarPorPlacaVehiculo(@RequestParam String placaVehiculo) {

		List<ConductorDTO> lista = conductorSer.findByPlacaVehiculo(placaVehiculo);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	// http://localhost:8080/conductor/buscarpornombreycedula?nombre=Juan
	// Perez&cedula=123456789
	@GetMapping("/buscarpornombreycedula")
	public ResponseEntity<List<ConductorDTO>> buscarPorNombreAndCedula(@RequestParam String nombre,
			@RequestParam String cedula) {

		List<ConductorDTO> lista = conductorSer.findByNombreAndCedula(nombre, cedula);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	// http://localhost:8080/conductor/buscarporplacavehiculoynombre?placaVehiculo=ABC123&nombre=Juan
	// Perez
	@GetMapping("/buscarporplacavehiculoynombre")
	public ResponseEntity<List<ConductorDTO>> buscarPorPlacaVehiculoAndNombre(@RequestParam String placaVehiculo,
			@RequestParam String nombre) {

		List<ConductorDTO> lista = conductorSer.findByPlacaVehiculoAndNombre(placaVehiculo, nombre);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}
}