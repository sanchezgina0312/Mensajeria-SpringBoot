package co.edu.unbosque.mensajeria.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import co.edu.unbosque.mensajeria.dto.PaqueteCartaDTO;
import co.edu.unbosque.mensajeria.exception.CiudadInvalidaException;
import co.edu.unbosque.mensajeria.exception.DireccionInvalidaException;
import co.edu.unbosque.mensajeria.exception.IdInvalidoException;
import co.edu.unbosque.mensajeria.exception.TamanioInvalidoException;
import co.edu.unbosque.mensajeria.exception.TipoDeCartaInvalidaException;
import co.edu.unbosque.mensajeria.service.PaqueteCartaService;

/**
 * Controlador REST para la gestión de paquetes de tipo carta.
 */
@RestController
@RequestMapping("/paquetecarta")
@CrossOrigin(origins = { "http://localhost:8080", "*" })
public class PaqueteCartaController {

	@Autowired
	private PaqueteCartaService paqueteCartaSer;

	public PaqueteCartaController() {
	}

	/**
	 * Crea un nuevo paquete de carta vinculado a un cliente.
	 * <p>
	 * Ejemplo: {@code .../crear?idCliente=123&direccionDestino=Calle 10&tamanio=Pequeño&ciudadDestino=Bogota&tipoCarta=Certificada}
	 * </p>
	 */
	@PostMapping("/crear")
	public ResponseEntity<String> crear(
			@RequestParam long idCliente, // Agregado para vinculación
			@RequestParam String direccionDestino, 
			@RequestParam String tamanio,
			@RequestParam String ciudadDestino, 
			@RequestParam String tipoCarta) {

		try {
			PaqueteCartaDTO dto = new PaqueteCartaDTO();
			dto.setIdCliente(idCliente); // Seteamos el dueño
			dto.setDireccionDestino(direccionDestino);
			dto.setTamanio(tamanio);
			dto.setCiudadDestino(ciudadDestino);
			dto.setTipoCarta(tipoCarta);

			int resultado = paqueteCartaSer.create(dto);

			if (resultado != 0) {
				return new ResponseEntity<>("Carta creada correctamente", HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Error al generar la carta", HttpStatus.BAD_REQUEST);
			}

		} catch (DireccionInvalidaException | TamanioInvalidoException | TipoDeCartaInvalidaException | CiudadInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/mostrartodo")
	public ResponseEntity<List<PaqueteCartaDTO>> mostrarTodo() {
		List<PaqueteCartaDTO> lista = paqueteCartaSer.getAll();
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	@PutMapping("/actualizar")
	public ResponseEntity<Object> actualizar(@RequestParam Long id, @RequestParam String direccionDestino,
			@RequestParam String ciudadDestino, @RequestParam String tamanio, @RequestParam String tipoCarta) {
		try {
			PaqueteCartaDTO nuevo = new PaqueteCartaDTO();
			nuevo.setDireccionDestino(direccionDestino);
			nuevo.setCiudadDestino(ciudadDestino);
			nuevo.setTamanio(tamanio);
			nuevo.setTipoCarta(tipoCarta);

			int status = paqueteCartaSer.updateById(id, nuevo);
			if (status == 1) {
				return new ResponseEntity<>("Carta actualizada con éxito", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("No se encontró la carta con el ID ingresado", HttpStatus.NOT_FOUND);
			}
		} catch (DireccionInvalidaException | TamanioInvalidoException | IdInvalidoException | CiudadInvalidaException | TipoDeCartaInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (MethodArgumentTypeMismatchException e) {
			return new ResponseEntity<>("El formato del ID no es válido", HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/eliminar")
	public ResponseEntity<String> delete(@RequestParam Long id) {
		try {
			int status = paqueteCartaSer.deleteById(id);
			if (status == 0) {
				return new ResponseEntity<>("Carta eliminada con éxito", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>("No se encontró la carta con el ID ingresado: " + id, HttpStatus.BAD_REQUEST);
			}
		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (MethodArgumentTypeMismatchException e) {
			return new ResponseEntity<>("El valor debe ser un número entero", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/buscarportamanio")
	public ResponseEntity<Object> buscarPorTamanio(@RequestParam String tamanio) {
		try {
			List<PaqueteCartaDTO> lista = paqueteCartaSer.findByTamanio(tamanio);
			if (!lista.isEmpty()) {
				return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
			}
		} catch (TamanioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/buscarportipocarta")
	public ResponseEntity<Object> buscarPorTipoCarta(@RequestParam String tipoCarta) {
		try {
			List<PaqueteCartaDTO> lista = paqueteCartaSer.findByTipoCarta(tipoCarta);
			if (!lista.isEmpty()) {
				return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
			}
		} catch (TipoDeCartaInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/buscarportamanioytipocarta")
	public ResponseEntity<Object> buscarPorTamanioAndTipoCarta(@RequestParam String tamanio,
			@RequestParam String tipoCarta) {
		try {
			List<PaqueteCartaDTO> lista = paqueteCartaSer.findByTamanioAndTipoCarta(tamanio, tipoCarta);
			if (!lista.isEmpty()) {
				return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
			}
		} catch (TamanioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/buscarporid")
	public ResponseEntity<Object> buscarPorId(@RequestParam Long id) {
		try {
			PaqueteCartaDTO p = paqueteCartaSer.findById(id);
			if (p != null) {
				return new ResponseEntity<>(p, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("No se encontró la carta con el ID ingresado", HttpStatus.NOT_FOUND);
			}
		} catch (TamanioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (MethodArgumentTypeMismatchException e) {
			return new ResponseEntity<>("El valor debe ser un número entero", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/buscardireccionyciudad")
	public ResponseEntity<Object> buscarDireccionYCiudad(@RequestParam String dir, @RequestParam String ciudad) {
		try {
			List<PaqueteCartaDTO> lista = paqueteCartaSer.findByDireccionDestinoAndCiudadDestino(dir, ciudad);
			if (lista.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(lista, HttpStatus.OK);
			}
		} catch (TamanioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Retorna el historial de cartas de un cliente.
	 */
	@GetMapping("/historialporid")
    public ResponseEntity<List<PaqueteCartaDTO>> verHistorial(@RequestParam long idCliente) {
        // Corregido a minúscula para coincidir con el estándar del Service
        List<PaqueteCartaDTO> lista = paqueteCartaSer.findByIdCliente(idCliente);
        
        if(lista.isEmpty()) {
        	return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        } else {
        	return new ResponseEntity<>(lista, HttpStatus.OK);
        }
    }
}