package co.edu.unbosque.mensajeria.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import co.edu.unbosque.mensajeria.dto.PaqueteAlimenticioDTO;
import co.edu.unbosque.mensajeria.exception.CiudadInvalidaException;
import co.edu.unbosque.mensajeria.exception.DireccionInvalidaException;
import co.edu.unbosque.mensajeria.exception.IdInvalidoException;
import co.edu.unbosque.mensajeria.exception.TamanioInvalidoException;
import co.edu.unbosque.mensajeria.exception.TipoDeAlimentoInvalidoException;
import co.edu.unbosque.mensajeria.service.PaqueteAlimenticioService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/paquetealimenticio")
@CrossOrigin(origins = { "http://localhost:8080", "*" })
public class PaqueteAlimenticioController {

	@Autowired
	private PaqueteAlimenticioService paqueteAlimenticioSer;

	public PaqueteAlimenticioController() {
	}

	// http://localhost:8080/paquetealimenticio/crear?remitente=Juan&destinatario=Ana&direccionDestino=Calle+10&ciudadDestino=Bogota&fechaEntrega=2024-05-20T14:30:00&tamanio=Grande&tipoAlimento=Perecedero
	@PostMapping("/crear")
	public ResponseEntity<String> crear(@RequestParam String direccionDestino, @RequestParam String tamanio,
			@RequestParam String ciudadDestino, @RequestParam boolean seEnviaHoy, @RequestParam String tipoDeAlimento) {

		try {
			PaqueteAlimenticioDTO dto = new PaqueteAlimenticioDTO();
			dto.setDireccionDestino(direccionDestino);
			dto.setTamanio(tamanio);
			dto.setCiudadDestino(ciudadDestino);
			dto.setSeEnviaHoy(seEnviaHoy);
			dto.setTipoDeAlimento(tipoDeAlimento);

			int resultado = paqueteAlimenticioSer.create(dto);

			if (resultado != 0) {
				return new ResponseEntity<>("Paquete alimenticio creado con éxito", HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Error al guardar el paquete alimenticio", HttpStatus.BAD_REQUEST);
			}

		} catch (DireccionInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TamanioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TipoDeAlimentoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (CiudadInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (MethodArgumentTypeMismatchException e) {
			return new ResponseEntity<>("El valor de debe ser true o false", HttpStatus.BAD_REQUEST);
		}
	}

	// http://localhost:8080/paquetealimenticio/mostrartodo
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<PaqueteAlimenticioDTO>> mostrarTodo() {
		List<PaqueteAlimenticioDTO> lista = paqueteAlimenticioSer.getAll();
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	// http://localhost:8080/paquetealimenticio/actualizar?id=1&remitente=Juan+Perez&destinatario=Ana+Gomez&direccionDestino=Carrera+15&ciudadDestino=Medellin&fechaEntrega=2024-05-21T10:00:00&tamanio=Mediano&tipoAlimento=No+Perecedero
	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizar(@RequestParam Long id, @RequestParam boolean seEnviaHoy,
			@RequestParam String tipoDeAlimento, @RequestParam int precioEnvio, @RequestParam String direccionDestino,
			@RequestParam String tamanio, @RequestParam LocalDateTime fechaCreacionPedido,
			@RequestParam LocalDateTime fechaEstimadaEntrega) {
		try {
			PaqueteAlimenticioDTO nuevo = new PaqueteAlimenticioDTO();

			nuevo.setSeEnviaHoy(seEnviaHoy);
			nuevo.setTipoDeAlimento(tipoDeAlimento);
			nuevo.setTamanio(tamanio);
			nuevo.setDireccionDestino(direccionDestino);
			nuevo.setFechaCreacionPedido(fechaCreacionPedido);
			nuevo.setFechaEstimadaEntrega(fechaEstimadaEntrega);
			nuevo.setPrecioEnvio(precioEnvio);

			int status = paqueteAlimenticioSer.updateById(id, nuevo);
			if (status == 0) {
				return new ResponseEntity<>("Paquete alimenticio actualizado con éxito", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>("El ID no existe en la base de datos",
						HttpStatus.BAD_REQUEST);
			}
		} catch (DireccionInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TamanioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TipoDeAlimentoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (CiudadInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (MethodArgumentTypeMismatchException e) {
			return new ResponseEntity<>("El formato no corresponde con el requerido", HttpStatus.BAD_REQUEST);
		}
	}

	// http://localhost:8080/paquetealimenticio/eliminar?id=1
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> delete(@RequestParam Long id) {

		try {
			int status = paqueteAlimenticioSer.deleteById(id);
			if (status == 0) {
				return new ResponseEntity<>("Paquete alimenticio eliminado con éxito", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>("No se encontró el paquete alimenticio con el ID ingresado" + id, HttpStatus.BAD_REQUEST);
			}
		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (MethodArgumentTypeMismatchException e) {
			return new ResponseEntity<>("El valor debe ser un número entero", HttpStatus.BAD_REQUEST);
		}
	}
	
	// http://localhost:8080/paquetealimenticio/buscarportamanio?tamanio=Grande
	@GetMapping("/buscartamanio")
	public ResponseEntity<Object> buscarPorTamanio(@RequestParam String tamanio) {
		try {
			List<PaqueteAlimenticioDTO> lista = paqueteAlimenticioSer.findByTamanio(tamanio);
			if (!lista.isEmpty()) {
				return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
			}
		} catch (TamanioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	// http://localhost:8080/paquetealimenticio/buscarporenviahoy?enviadhoy=true
	@GetMapping("/buscarporenviahoy")
	public ResponseEntity<Object> buscarPorSeEnviaHoy(@RequestParam boolean seEnviaHoy) {
		try {
			List<PaqueteAlimenticioDTO> lista = paqueteAlimenticioSer.findBySeEnviaHoy(seEnviaHoy);
			if (!lista.isEmpty()) {
				return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
			}
		} catch (MethodArgumentTypeMismatchException e) {
			return new ResponseEntity<>("El valor debe ser true o false", HttpStatus.BAD_REQUEST);
		}
	}
	
	// http://localhost:8080/paquetealimenticio/buscarportipoalimento?tipoAlimento=Perecedero
	@GetMapping("/buscarportipoalimento")
	public ResponseEntity<Object> buscarPorTipoDeAlimento(@RequestParam String tipoDeAlimento) {
		try {
			List<PaqueteAlimenticioDTO> lista = paqueteAlimenticioSer.findByTipoDeAlimento(tipoDeAlimento);
			if (!lista.isEmpty()) {
				return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
			}
		} catch (TipoDeAlimentoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	// http://localhost:8080/paquetealimenticio/buscarportamanioytipo?tamanio=Pequeño&tipoAlimento=No+Perecedero
	@GetMapping("/buscarportamanioytipo")
	public ResponseEntity<Object> buscarPorTamanioAndTipo(@RequestParam String tamanio,
			@RequestParam String tipoDeAlimento) {
		try {
			List<PaqueteAlimenticioDTO> lista = paqueteAlimenticioSer.findByTamanioAndTipoDeAlimento(tamanio,
					tipoDeAlimento);
			if (!lista.isEmpty()) {
				return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
			}
		} catch (TamanioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TipoDeAlimentoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	// http://localhost:8080/paquetealimenticio/seguimientoid?id=10
	@GetMapping("/seguimientoid")
	public ResponseEntity<Object> seguimientoId(@RequestParam Long id) {
		try {
			PaqueteAlimenticioDTO p = paqueteAlimenticioSer.findById(id);

			if (p != null) {
				return new ResponseEntity<>(p, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("No se encontró el paquete alimenticio con el ID ingresado", HttpStatus.NOT_FOUND);
			}
		} catch (MethodArgumentTypeMismatchException e) {
			return new ResponseEntity<>("El valor debe ser un número entero", HttpStatus.BAD_REQUEST);
		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// http://localhost:8080/paquetealimenticio/buscardireccionyciudad?dir=Calle+123&ciudad=Bogota
	@GetMapping("/buscardireccionyciudad")
	public ResponseEntity<Object> buscarDireccionYCiudad(@RequestParam String dir,
			@RequestParam String ciudad) {
		try {
			List<PaqueteAlimenticioDTO> lista = paqueteAlimenticioSer.findByDireccionDestinoAndCiudadDestino(dir, ciudad);

			if (lista.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(lista, HttpStatus.OK);
			}
		} catch (DireccionInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
