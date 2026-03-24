package co.edu.unbosque.mensajeria.controller;

import java.time.LocalDateTime;
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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import co.edu.unbosque.mensajeria.dto.PaqueteCartaDTO;
import co.edu.unbosque.mensajeria.exception.CiudadInvalidaException;
import co.edu.unbosque.mensajeria.exception.DireccionInvalidaException;
import co.edu.unbosque.mensajeria.exception.IdInvalidoException;
import co.edu.unbosque.mensajeria.exception.TamanioInvalidoException;
import co.edu.unbosque.mensajeria.exception.TipoDeCartaInvalidaException;
import co.edu.unbosque.mensajeria.service.PaqueteCartaService;

@RestController
@RequestMapping("/paquetecarta")
@CrossOrigin(origins = { "http://localhost:8080", "*" })
public class PaqueteCartaController {

	@Autowired
	private PaqueteCartaService paqueteCartaSer;

	public PaqueteCartaController() {

	}

	// http://localhost:8080/paquetecarta/crear?remitente=Juan&destinatario=Maria&direccionDestino=Calle+10&ciudadDestino=Bogota&fechaEntrega=2024-12-31T23:59:59&tamanio=Pequeño&tipoCarta=Certificada
	@PostMapping("/crear")
	public ResponseEntity<String> crear(@RequestParam String direccionDestino, @RequestParam String tamanio,
			@RequestParam String ciudadDestino, @RequestParam String tipoCarta) {

		try {
			PaqueteCartaDTO dto = new PaqueteCartaDTO();
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

		} catch (DireccionInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TamanioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TipoDeCartaInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (CiudadInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// http://localhost:8080/paquetecarta/mostrartodo
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<PaqueteCartaDTO>> mostrarTodo() {
		List<PaqueteCartaDTO> lista = paqueteCartaSer.getAll();
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	// http://localhost:8080/paquetecarta/actualizar?id=1&remitente=Juan+Actualizado&destinatario=Maria+Lopez&direccionDestino=Carrera+15&ciudadDestino=Medellin&fechaEntrega=2025-01-01T10:00:00&tamanio=Pequeño&tipoCarta=Normal
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
		} catch (DireccionInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TamanioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (CiudadInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TipoDeCartaInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (MethodArgumentTypeMismatchException e) {
			return new ResponseEntity<>("El formato del ID no es válido", HttpStatus.BAD_REQUEST);
		}
	}

	// http://localhost:8080/paquetecarta/eliminar?id=1
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> delete(@RequestParam Long id) {
		try {
			int status = paqueteCartaSer.deleteById(id);
			if (status == 0) {
				return new ResponseEntity<>("Carta eliminada con éxito", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>("No se encontró la carta con el ID ingresado" + id, HttpStatus.BAD_REQUEST);
			}
		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (MethodArgumentTypeMismatchException e) {
			return new ResponseEntity<>("El valor debe ser un número entero", HttpStatus.BAD_REQUEST);
		}
	}

	// http://localhost:8080/paquetecarta/buscarportamanio?tamanio=Pequeño
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

	// http://localhost:8080/paquetecarta/buscarportipocarta?tipoCarta=Certificada
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

	// http://localhost:8080/paquetecarta/buscarportamanioytipocarta?tamanio=Pequeño&tipoCarta=Documento
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

	// http://localhost:8080/paquetecarta/seguimientoid?id=1
	@GetMapping("/seguimientoid")
	public ResponseEntity<Object> seguimientoId(@RequestParam Long id) {

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

	// http://localhost:8080/paquetecarta/buscardireccionyciudad?dir=Calle+123&ciudad=Bogota
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
}
