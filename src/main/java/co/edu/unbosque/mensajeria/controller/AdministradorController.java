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
import co.edu.unbosque.mensajeria.dto.AdministradorDTO;
import co.edu.unbosque.mensajeria.exception.CedulaInvalidaException;
import co.edu.unbosque.mensajeria.exception.CorreoInvalidoException;
import co.edu.unbosque.mensajeria.exception.IdInvalidoException;
import co.edu.unbosque.mensajeria.exception.NombreInvalidoException;
import co.edu.unbosque.mensajeria.exception.TelefonoInvalidoException;
import co.edu.unbosque.mensajeria.exception.TurnoInvalidoException;
import co.edu.unbosque.mensajeria.service.AdministradorService;

@RestController
@RequestMapping("/administrador")
@CrossOrigin(origins = { "http://localhost:8080", "*" })
public class AdministradorController {

	@Autowired
	private AdministradorService administradorSer;

	public AdministradorController() {

	}

	// http://localhost:8080/administrador/crear?nombre=Carlos&cedula=1010&correo=carlos@mail.com&telefono=315000&turno=M&usuario=admin1&contrasenia=pass123
	@PostMapping("/crear")
	public ResponseEntity<String> crearAdministrador(@RequestParam String nombre, @RequestParam String cedula,
			@RequestParam String correo, @RequestParam String telefono, @RequestParam char turno) {

		try {
			AdministradorDTO nuevo = new AdministradorDTO();
			nuevo.setNombre(nombre);
			nuevo.setCedula(cedula);
			nuevo.setCorreo(correo);
			nuevo.setTelefono(telefono);
			nuevo.setTurno(turno);

			int status = administradorSer.create(nuevo);

			if (status == 0) {
				return new ResponseEntity<>("Administrador creado con éxito", HttpStatus.CREATED);
			} else if (status == 1) {
				return new ResponseEntity<>("La cédula ya se encuentra registrada", HttpStatus.CONFLICT);
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

		} catch (Exception e) {
			return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// http://localhost:8080/administrador/mostrartodo
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<AdministradorDTO>> mostrarTodo() {
		List<AdministradorDTO> administradores = administradorSer.getAll();
		if (administradores.isEmpty()) {
			return new ResponseEntity<>(administradores, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(administradores, HttpStatus.ACCEPTED);
		}
	}

	// http://localhost:8080/administrador/actualizar?id=1&nombre=Carlos+Editado&cedula=1010&correo=carlos_nuevo@mail.com&telefono=320000&turno=T&usuario=admin_actualizado&contrasenia=newpass
	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizar(@RequestParam Long id, @RequestParam String nombre,
			@RequestParam String cedula, @RequestParam String correo, @RequestParam String telefono,
			@RequestParam char turno, @RequestParam String usuario, @RequestParam String contrasenia) {

		try {
			AdministradorDTO nuevo = new AdministradorDTO();
			nuevo.setNombre(nombre);
			nuevo.setCedula(cedula);
			nuevo.setCorreo(correo);
			nuevo.setTelefono(telefono);
			nuevo.setTurno(turno);
			nuevo.setUsuario(usuario);
			nuevo.setContrasenia(contrasenia);

			int status = administradorSer.updateById(id, nuevo);
			if (status == 0) {
				return new ResponseEntity<>("Dato actualizado con éxito", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>("Error: El ID " + id + " no existe en la base de datos",
						HttpStatus.BAD_REQUEST);
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

		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

		} catch (Exception e) {
			return new ResponseEntity<>("Error inesperado", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// http://localhost:8080/administrador/eliminar?id=1
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> delete(@RequestParam Long id) {
		try {
			int status = administradorSer.deleteById(id);
			if (status == 0) {
				return new ResponseEntity<>("Administrador eliminado con éxito", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("No se encontró ningún administrador con el ID ingresado",
						HttpStatus.NOT_FOUND);
			}
		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Error al procesar la solicitud", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// http://localhost:8080/administrador/buscarpornombre?nombre=Carlos
	@GetMapping("/buscarpornombre")
	public ResponseEntity<List<AdministradorDTO>> buscarPorNombre(@RequestParam String nombre) {
		List<AdministradorDTO> lista = administradorSer.findByNombre(nombre);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	// http://localhost:8080/administrador/buscarporcedula?cedula=1010
	@GetMapping("/buscarporcedula")
	public ResponseEntity<List<AdministradorDTO>> buscarPorCedula(@RequestParam String cedula) {
		List<AdministradorDTO> lista = administradorSer.findByCedula(cedula);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	// http://localhost:8080/administrador/buscarporcorreo?correo=carlos@mail.com
	@GetMapping("/buscarporcorreo")
	public ResponseEntity<List<AdministradorDTO>> buscarPorCorreo(@RequestParam String correo) {
		List<AdministradorDTO> lista = administradorSer.findByCorreo(correo);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	// http://localhost:8080/administrador/buscarportelefono?telefono=315000
	@GetMapping("/buscarportelefono")
	public ResponseEntity<List<AdministradorDTO>> buscarPorTelefono(@RequestParam String telefono) {
		List<AdministradorDTO> lista = administradorSer.findByTelefono(telefono);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	// http://localhost:8080/administrador/buscarporusuario?usuario=admin1
	@GetMapping("/buscarporusuario")
	public ResponseEntity<List<AdministradorDTO>> buscarPorUsuario(@RequestParam String usuario) {
		List<AdministradorDTO> lista = administradorSer.findByUsuario(usuario);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	// http://http://localhost:8080/administrador/buscarporcontrasenia?contrasenia=pass123
	@GetMapping("/buscarporcontrasenia")
	public ResponseEntity<List<AdministradorDTO>> buscarPorContrasenia(@RequestParam String contrasenia) {
		List<AdministradorDTO> lista = administradorSer.findByContrasenia(contrasenia);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	// http://localhost:8080/administrador/buscarpornombreycedula?nombre=Carlos&cedula=1010
	@GetMapping("/buscarpornombreycedula")
	public ResponseEntity<List<AdministradorDTO>> buscarPorNombreAndCedula(@RequestParam String nombre,
			@RequestParam String cedula) {
		List<AdministradorDTO> lista = administradorSer.findByNombreAndCedula(nombre, cedula);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	// http://localhost:8080/administrador/buscarporusuarioycontrasenia?usuario=admin1&contrasenia=pass123
	@GetMapping("/buscarporusuarioycontrasenia")
	public ResponseEntity<List<AdministradorDTO>> buscarPorUsuarioAndContrasenia(@RequestParam String usuario,
			@RequestParam String contrasenia) {
		List<AdministradorDTO> lista = administradorSer.findByUsuarioAndContrasenia(usuario, contrasenia);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}
}