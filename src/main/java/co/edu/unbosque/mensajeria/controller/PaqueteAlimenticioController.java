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

import co.edu.unbosque.mensajeria.dto.PaqueteAlimenticioDTO;
import co.edu.unbosque.mensajeria.exception.DireccionDestinoInvalidaException;
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

	// http://localhost:8080/paquetealimenticio/crear?seEnviaHoy=true&tipoDeAlimento=Perecedero&precioEnvio=5000&direccionDestino=Calle10&tamanio=Mediano
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
				return new ResponseEntity<>("Error al guardar el paquete.", HttpStatus.BAD_REQUEST);
			}

		} catch (DireccionDestinoInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TamanioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TipoDeAlimentoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
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

	// http://localhost:8080/paquetealimenticio/actualizar?id=1&seEnviaHoy=false&tipoDeAlimento=Seco&precioEnvio=4000&direccionDestino=Carrera5&tamanio=Pequeno&fechaCreacionPedido=2024-03-20T10:00:00&fechaEstimadaEntrega=2024-03-20T16:00:00
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
				return new ResponseEntity<>("Dato actualizado con éxito", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>("Error: El ID " + id + " no existe en la base de datos",
						HttpStatus.BAD_REQUEST);
			}
		} catch (DireccionDestinoInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TamanioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TipoDeAlimentoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

		}
	}

	// http://localhost:8080/paquetealimenticio/eliminar?id=1
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> delete(@RequestParam Long id) {

		try {
			int status = paqueteAlimenticioSer.deleteById(id);
			if (status == 0) {
				return new ResponseEntity<>("Dato eliminado con éxito", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>("Error: No se encontró el registro con ID " + id, HttpStatus.BAD_REQUEST);
			}
		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

		}
	}

	@GetMapping("/buscartamanio")
	public ResponseEntity<List<PaqueteAlimenticioDTO>> buscarPorTamanio(@RequestParam String tamanio) {
		List<PaqueteAlimenticioDTO> lista = paqueteAlimenticioSer.findByTamanio(tamanio);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/buscarenviahoy")
	public ResponseEntity<List<PaqueteAlimenticioDTO>> buscarPorSeEnviaHoy(@RequestParam boolean seEnviaHoy) {
		List<PaqueteAlimenticioDTO> lista = paqueteAlimenticioSer.findBySeEnviaHoy(seEnviaHoy);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/buscartipoalimento")
	public ResponseEntity<List<PaqueteAlimenticioDTO>> buscarPorTipoDeAlimento(@RequestParam String tipoDeAlimento) {
		List<PaqueteAlimenticioDTO> lista = paqueteAlimenticioSer.findByTipoDeAlimento(tipoDeAlimento);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/buscarportamanioytipo")
	public ResponseEntity<List<PaqueteAlimenticioDTO>> buscarPorTamanioAndTipo(@RequestParam String tamanio,
			@RequestParam String tipoDeAlimento) {
		List<PaqueteAlimenticioDTO> lista = paqueteAlimenticioSer.findByTamanioAndTipoDeAlimento(tamanio,
				tipoDeAlimento);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/seguimiento-id")
	public ResponseEntity<Object> seguimientoId(@RequestParam Long id) {
		PaqueteAlimenticioDTO p = paqueteAlimenticioSer.findById(id);

		if (p != null) {
			return new ResponseEntity<>(p, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Guía no encontrada en el sistema", HttpStatus.NOT_FOUND);
		}
	}
	
	// http://localhost:8080/paquetealimenticio/buscar-direccion-ciudad?dir=Calle123&ciudad=Bogota
	@GetMapping("/buscar-direccion-ciudad")
	public ResponseEntity<List<PaqueteAlimenticioDTO>> buscarDireccionYCiudad(@RequestParam String dir,
			@RequestParam String ciudad) {

		List<PaqueteAlimenticioDTO> lista = paqueteAlimenticioSer.findByDireccionDestinoAndCiudadDestino(dir, ciudad);

		if (lista.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.OK);
		}
	}

}