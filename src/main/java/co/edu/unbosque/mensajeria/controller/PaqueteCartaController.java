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

import co.edu.unbosque.mensajeria.dto.PaqueteCartaDTO;
import co.edu.unbosque.mensajeria.exception.DireccionDestinoInvalidaException;
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

	// http://localhost:8080/paquetecarta/crear?precioEnvio=2000&direccionDestino=AvSuba&tamanio=Sobre&fechaCreacionPedido=2024-03-20T08:00:00&fechaEstimadaEntrega=2024-03-23T08:00:00&tipoCarta=Documento
	@PostMapping("/crear")
	public ResponseEntity<String> crearPaqueteCarta(@RequestParam int precioEnvio,
			@RequestParam String direccionDestino, @RequestParam String tamanio,
			@RequestParam LocalDateTime fechaCreacionPedido, @RequestParam LocalDateTime fechaEstimadaEntrega,
			@RequestParam String tipoCarta) {
		try {
			PaqueteCartaDTO nuevo = new PaqueteCartaDTO();

			nuevo.setTipoCarta(tipoCarta);
			nuevo.setTamanio(tamanio);
			nuevo.setPrecioEnvio(precioEnvio);
			nuevo.setFechaEstimadaEntrega(fechaEstimadaEntrega);
			nuevo.setFechaCreacionPedido(fechaCreacionPedido);
			nuevo.setDireccionDestino(direccionDestino);

			int status = paqueteCartaSer.registrarPlazo72Horas(nuevo);

			if (status == 0) {
				paqueteCartaSer.create(nuevo);
				return new ResponseEntity<>("Paquete carta creado con éxito", HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Error al crear paquete carta", HttpStatus.BAD_REQUEST);
			}

		} catch (DireccionDestinoInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TipoDeCartaInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TamanioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// http://localhost:8080/paquetecarta/mostrartodo
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<PaqueteCartaDTO>> mostrarTodo() {
		List<PaqueteCartaDTO> parejas = paqueteCartaSer.getAll();
		if (parejas.isEmpty()) {
			return new ResponseEntity<>(parejas, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(parejas, HttpStatus.ACCEPTED);
		}
	}

	// http://localhost:8080/paquetecarta/actualizar?id=1&precioEnvio=2500&direccionDestino=Calle80&tamanio=Sobre&fechaCreacionPedido=2024-03-20T08:00:00&fechaEstimadaEntrega=2024-03-23T08:00:00&tipoCarta=Certificada
	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizar(@RequestParam Long id, @RequestParam int precioEnvio,
			@RequestParam String direccionDestino, @RequestParam String tamanio,
			@RequestParam LocalDateTime fechaCreacionPedido, @RequestParam LocalDateTime fechaEstimadaEntrega,
			@RequestParam String tipoCarta) {
		PaqueteCartaDTO nuevo = new PaqueteCartaDTO();

		nuevo.setDireccionDestino(direccionDestino);
		nuevo.setFechaCreacionPedido(fechaCreacionPedido);
		nuevo.setFechaEstimadaEntrega(fechaEstimadaEntrega);
		nuevo.setPrecioEnvio(precioEnvio);
		nuevo.setTamanio(tamanio);
		nuevo.setTipoCarta(tipoCarta);

		int status = paqueteCartaSer.updateById(id, nuevo);
		if (status == 0) {
			return new ResponseEntity<>("Dato actualizado con éxito", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>("Error: El ID " + id + " no existe en la base de datos",
					HttpStatus.BAD_REQUEST);
		}
	}

	// http://localhost:8080/paquetecarta/eliminar?id=1
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> delete(@RequestParam Long id) {
		try {
			int status = paqueteCartaSer.deleteById(id);
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
	public ResponseEntity<List<PaqueteCartaDTO>> buscarPorTamanio(@RequestParam String tamanio) {
		List<PaqueteCartaDTO> lista = paqueteCartaSer.findByTamanio(tamanio);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/buscartipocarta")
	public ResponseEntity<List<PaqueteCartaDTO>> buscarPorTipoCarta(@RequestParam String tipoCarta) {
		List<PaqueteCartaDTO> lista = paqueteCartaSer.findByTipoCarta(tipoCarta);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/buscarportamanioytipocarta")
	public ResponseEntity<List<PaqueteCartaDTO>> buscarPorTamanioYTipoCarta(@RequestParam String tamanio,
			@RequestParam String tipoCarta) {
		List<PaqueteCartaDTO> lista = paqueteCartaSer.findByTamanioAndTipoCarta(tamanio, tipoCarta);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}
}
