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
import co.edu.unbosque.mensajeria.exception.CedulaInvalidaException;
import co.edu.unbosque.mensajeria.exception.CorreoInvalidoException;
import co.edu.unbosque.mensajeria.exception.DireccionDestinoInvalidaException;
import co.edu.unbosque.mensajeria.exception.NombreInvalidoException;
import co.edu.unbosque.mensajeria.exception.TamanioInvalidoException;
import co.edu.unbosque.mensajeria.exception.TelefonoInvalidoException;
import co.edu.unbosque.mensajeria.exception.TipoDeAlimentoInvalidoException;
import co.edu.unbosque.mensajeria.exception.TurnoInvalidoException;
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
	public ResponseEntity<String> crearPaqueteAlimenticio(@RequestParam boolean seEnviaHoy,
			@RequestParam String tipoDeAlimento, @RequestParam int precioEnvio, @RequestParam String direccionDestino,
			@RequestParam String tamanio, @RequestParam LocalDateTime fechaCreacionPedido,
			@RequestParam LocalDateTime fechaEstimadaEntrega) {

		
		try {
			
		PaqueteAlimenticioDTO nuevo = new PaqueteAlimenticioDTO();
		
		 nuevo.setPrecioEnvio(precioEnvio);
         nuevo.setTamanio(tamanio);
         nuevo.setTipoDeAlimento(tipoDeAlimento);
         nuevo.setSeEnviaHoy(seEnviaHoy);
         nuevo.setDireccionDestino(direccionDestino);
         nuevo.setFechaCreacionPedido(fechaCreacionPedido);
         nuevo.setFechaEstimadaEntrega(fechaEstimadaEntrega);
         
         paqueteAlimenticioSer.create(nuevo);
		
         return new ResponseEntity<>("Paquete alimenticio creado con éxito", HttpStatus.CREATED);
         
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
		List<PaqueteAlimenticioDTO> paquetesalimenticios = paqueteAlimenticioSer.getAll();
		if (paquetesalimenticios.isEmpty()) {
			return new ResponseEntity<List<PaqueteAlimenticioDTO>>(paquetesalimenticios, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<PaqueteAlimenticioDTO>>(paquetesalimenticios, HttpStatus.ACCEPTED);
		}
	}
	
	// http://localhost:8080/paquetealimenticio/actualizar?id=1&seEnviaHoy=false&tipoDeAlimento=Seco&precioEnvio=4000&direccionDestino=Carrera5&tamanio=Pequeno&fechaCreacionPedido=2024-03-20T10:00:00&fechaEstimadaEntrega=2024-03-20T16:00:00
	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizar(@RequestParam Long id, @RequestParam boolean seEnviaHoy,
			@RequestParam String tipoDeAlimento, @RequestParam int precioEnvio, @RequestParam String direccionDestino,
			@RequestParam String tamanio, @RequestParam LocalDateTime fechaCreacionPedido,
			@RequestParam LocalDateTime fechaEstimadaEntrega) {
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
	}
	
	// http://localhost:8080/paquetealimenticio/eliminar?id=1
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> delete(@RequestParam Long id) {
		int status = paqueteAlimenticioSer.deleteById(id);
		if (status == 0) {
			return new ResponseEntity<>("Dato eliminado con éxito", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>("Error: No se encontró el registro con ID " + id, HttpStatus.BAD_REQUEST);
		}
	}

}