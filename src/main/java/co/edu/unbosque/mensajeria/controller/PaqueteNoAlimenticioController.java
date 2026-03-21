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
import co.edu.unbosque.mensajeria.dto.PaqueteNoAlimenticioDTO;
import co.edu.unbosque.mensajeria.service.PaqueteNoAlimenticioService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/paquetenoalimenticio")
@CrossOrigin(origins = { "http://localhost:8080", "*" })
public class PaqueteNoAlimenticioController {

	@Autowired
	private PaqueteNoAlimenticioService paqueteNoAlimenticioSer;

	public PaqueteNoAlimenticioController() {
	}

	// http://localhost:8080/paquetenoalimenticio/crear?esFragil=true&precioEnvio=15000&direccionDestino=BarrioNorte&tamanio=Grande
	@PostMapping("/crear")
	public ResponseEntity<String> crearPaqueteNoAlimenticio(@RequestParam boolean esFragil,
			@RequestParam int precioEnvio, @RequestParam String direccionDestino, @RequestParam String tamanio,
			@RequestParam LocalDateTime fechaCreacionPedido, @RequestParam LocalDateTime fechaEstimadaEntrega) {

		PaqueteNoAlimenticioDTO nuevo = new PaqueteNoAlimenticioDTO(precioEnvio, direccionDestino, tamanio,
				fechaCreacionPedido, fechaEstimadaEntrega, esFragil);

		int statusA = paqueteNoAlimenticioSer.registrarPlazo24Horas(nuevo);
		int status = paqueteNoAlimenticioSer.create(nuevo);

		if (status == 0 && statusA == 0) {
			return new ResponseEntity<>("Paquete No Alimenticio creado con éxito (Entrega en 24h)", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error al crear el paquete", HttpStatus.BAD_REQUEST);
		}

	}

	// http://localhost:8080/paquetenoalimenticio/mostrartodo
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<PaqueteNoAlimenticioDTO>> mostrarTodo() {
		List<PaqueteNoAlimenticioDTO> paquetesnoalimenticios = paqueteNoAlimenticioSer.getAll();
		if (paquetesnoalimenticios.isEmpty()) {
			return new ResponseEntity<List<PaqueteNoAlimenticioDTO>>(paquetesnoalimenticios, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<PaqueteNoAlimenticioDTO>>(paquetesnoalimenticios, HttpStatus.ACCEPTED);
		}
	}

	// http://localhost:8080/paquetenoalimenticio/actualizar?id=1&esFragil=false&precioEnvio=10000&direccionDestino=BarrioSur&tamanio=Mediano&fechaCreacionPedido=2024-03-20T08:00:00&fechaEstimadaEntrega=2024-03-21T08:00:00
	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizar(@RequestParam Long id, @RequestParam boolean esFragil,
			@RequestParam int precioEnvio, @RequestParam String direccionDestino, @RequestParam String tamanio,
			@RequestParam LocalDateTime fechaCreacionPedido, @RequestParam LocalDateTime fechaEstimadaEntrega) {
		PaqueteNoAlimenticioDTO nuevo = new PaqueteNoAlimenticioDTO(precioEnvio, direccionDestino, tamanio,
				fechaCreacionPedido, fechaEstimadaEntrega, esFragil);
		int status = paqueteNoAlimenticioSer.updateById(id, nuevo);
		if (status == 0) {
			return new ResponseEntity<>("Dato actualizado con éxito", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>("Error: El ID " + id + " no existe en la base de datos",
					HttpStatus.BAD_REQUEST);
		}
	}
	
	// http://localhost:8080/paquetenoalimenticio/eliminar?id=1
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> delete(@RequestParam Long id) {
		int status = paqueteNoAlimenticioSer.deleteById(id);
		if (status == 0) {
			return new ResponseEntity<>("Dato eliminado con éxito", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>("Error: No se encontró el registro con ID " + id, HttpStatus.BAD_REQUEST);
		}
	}

}