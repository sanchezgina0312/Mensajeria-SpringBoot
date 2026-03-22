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
import co.edu.unbosque.mensajeria.dto.ConductorDTO;
import co.edu.unbosque.mensajeria.dto.ManipuladorDePaqueteDTO;
import co.edu.unbosque.mensajeria.exception.CedulaInvalidaException;
import co.edu.unbosque.mensajeria.exception.CorreoInvalidoException;
import co.edu.unbosque.mensajeria.exception.IdInvalidoException;
import co.edu.unbosque.mensajeria.exception.NombreInvalidoException;
import co.edu.unbosque.mensajeria.exception.TelefonoInvalidoException;
import co.edu.unbosque.mensajeria.exception.TipoManipuladorInvalidoException;
import co.edu.unbosque.mensajeria.exception.TurnoInvalidoException;
import co.edu.unbosque.mensajeria.service.ManipuladorDePaqueteService;

@RestController
@RequestMapping("/manipuladordepaquete")
@CrossOrigin(origins = { "http://localhost:8080", "*" })
public class ManipuladorDePaqueteController {

	@Autowired
	private ManipuladorDePaqueteService manipuladorSer;

	public ManipuladorDePaqueteController() {

	}

	// http://localhost:8080/manipuladordepaquete/crear?nombre=Pedro&cedula=456&correo=p@mail.com&telefono=3002&turno=T&tipoManipulador=Bodega
	@PostMapping("/crear")
	public ResponseEntity<String> crearManipulador(@RequestParam String nombre, @RequestParam String cedula,
			@RequestParam String correo, @RequestParam String telefono, @RequestParam char turno,
			@RequestParam String tipoManipulador) {

		try {
			ManipuladorDePaqueteDTO nuevo = new ManipuladorDePaqueteDTO();
			nuevo.setNombre(nombre);
			nuevo.setCedula(cedula);
			nuevo.setCorreo(correo);
			nuevo.setTelefono(telefono);
			nuevo.setTurno(turno);
			nuevo.setTipoManipulador(tipoManipulador);

			int status = manipuladorSer.create(nuevo);
			if (status == 0) {
				return new ResponseEntity<>("Dato creado con exito", HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Error al crear el manipulador de paquete", HttpStatus.BAD_REQUEST);
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
	        
	    } catch (TipoManipuladorInvalidoException e) {
	        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
	        
	    } 
	}

	// http://localhost:8080/manipuladordepaquete/mostrartodo
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<ManipuladorDePaqueteDTO>> mostrarTodo() {
		List<ManipuladorDePaqueteDTO> manipuladores = manipuladorSer.getAll();
		if (manipuladores.isEmpty()) {
			return new ResponseEntity<>(manipuladores, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(manipuladores, HttpStatus.ACCEPTED);
		}
	}
	
	// http://localhost:8080/manipuladordepaquete/actualizar?id=1&nombre=Leo&cedula=456&correo=l@mail.com&telefono=400&turno=N&tipoManipulador=Bodega
	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizar(@RequestParam Long id, @RequestParam String nombre,
			@RequestParam String cedula, @RequestParam String correo, @RequestParam String telefono,
			@RequestParam char turno, @RequestParam String tipoManipulador) {
		
		
		try {
			ManipuladorDePaqueteDTO nuevo = new ManipuladorDePaqueteDTO();
			nuevo.setNombre(nombre);
			nuevo.setCedula(cedula);
			nuevo.setCorreo(correo);
			nuevo.setTelefono(telefono);
			nuevo.setTurno(turno);
			nuevo.setTipoManipulador(tipoManipulador);

			int status = manipuladorSer.updateById(id, nuevo);
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
	        
	    }  catch (TipoManipuladorInvalidoException e) {
	        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
	        
	    } 
	}
	
	// http://localhost:8080/manipuladordepaquete/eliminar?id=1
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> delete(@RequestParam Long id) {
		try {
			int status = manipuladorSer.deleteById(id);
			if (status == 0) {
				return new ResponseEntity<>("Dato eliminado con éxito", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>("Error: No se encontró el registro con ID " + id, HttpStatus.BAD_REQUEST);
			}
	    } catch (IdInvalidoException e) {
	        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
	    }
	}
}