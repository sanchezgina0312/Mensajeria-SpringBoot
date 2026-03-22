package co.edu.unbosque.mensajeria.controller;
// HOLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
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
	
	// http://localhost:8080/administrador/crear?nombre=Admin1&cedula=999&correo=a@mail.com&telefono=000&turno=N
	@PostMapping("/crear")
    public ResponseEntity<String> crearAdministrador(@RequestParam String nombre, @RequestParam String cedula, @RequestParam String correo, @RequestParam String telefono, @RequestParam char turno) {

        try {
            AdministradorDTO nuevo = new AdministradorDTO();
            nuevo.setNombre(nombre);
            nuevo.setCedula(cedula);
            nuevo.setCorreo(correo);
            nuevo.setTelefono(telefono);
            nuevo.setTurno(turno);

            administradorSer.create(nuevo);

            return new ResponseEntity<>("Administrador creado con éxito", HttpStatus.CREATED);

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

	// http://localhost:8080/administrador/actualizar?id=1&nombre=Jefe&cedula=001&correo=admin@mail.com&telefono=100&turno=T&usuario=admin2&contrasenia=nueva
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
	        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
	    }
	}
	
	// http://localhost:8080/administrador/eliminar?id=1
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> delete(@RequestParam Long id) {
	    try {
	        int status = administradorSer.deleteById(id);
	        if (status == 0) {
	            return new ResponseEntity<>("Administrador eliminado con éxito",HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("No se encontró ningún administrador con el ID ingresado", HttpStatus.NOT_FOUND);
	        }
	    } catch (IdInvalidoException e) {
	        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
	    }
	}
}