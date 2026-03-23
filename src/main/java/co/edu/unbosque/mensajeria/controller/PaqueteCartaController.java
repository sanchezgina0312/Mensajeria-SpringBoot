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

	// http://localhost:8080/paquetecarta/crear?precioEnvio=2000&direccionDestino=AvSuba&tamanio=Sobre&fechaCreacionPedido=2024-03-20T08:00:00&fechaEstimadaEntrega=2024-03-23T08:00:00&tipoCarta=Documento
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
		}   catch (CiudadInvalidaException e) {
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

	// http://localhost:8080/paquetecarta/actualizar?id=1&precioEnvio=2500&direccionDestino=Calle80&tamanio=Sobre&fechaCreacionPedido=2024-03-20T08:00:00&fechaEstimadaEntrega=2024-03-23T08:00:00&tipoCarta=Certificada
	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizar(@RequestParam Long id, @RequestParam int precioEnvio,
			@RequestParam String direccionDestino, @RequestParam String tamanio,
			@RequestParam LocalDateTime fechaCreacionPedido, @RequestParam LocalDateTime fechaEstimadaEntrega,
			@RequestParam String tipoCarta) {
		try {
			PaqueteCartaDTO nuevo = new PaqueteCartaDTO();

			nuevo.setDireccionDestino(direccionDestino);
			nuevo.setFechaCreacionPedido(fechaCreacionPedido);
			nuevo.setFechaEstimadaEntrega(fechaEstimadaEntrega);
			nuevo.setPrecioEnvio(precioEnvio);
			nuevo.setTamanio(tamanio);
			nuevo.setTipoCarta(tipoCarta);

			int status = paqueteCartaSer.updateById(id, nuevo);
			if (status == 0) {
				return new ResponseEntity<>("Carta actualizada con éxito.", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>("No se encontró la carta con el ID ingresado",
						HttpStatus.BAD_REQUEST);
			}
		} catch (DireccionInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TamanioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TipoDeCartaInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}  catch (CiudadInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (MethodArgumentTypeMismatchException e) {
	        return new ResponseEntity<>("El valor debe ser un número entero", HttpStatus.BAD_REQUEST);
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
		}  catch (MethodArgumentTypeMismatchException e) {
	        return new ResponseEntity<>("El valor debe ser un número entero", HttpStatus.BAD_REQUEST);
	    }
	}

	@GetMapping("/buscartamanio")
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

	@GetMapping("/buscartipocarta")
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
		}  catch (MethodArgumentTypeMismatchException e) {
	        return new ResponseEntity<>("El valor debe ser un número entero", HttpStatus.BAD_REQUEST);
	    }
	}
	
	// http://localhost:8080/paquetecarta/buscar-direccion-ciudad?dir=Calle123&ciudad=Bogota
	@GetMapping("/buscardireccionyciudad")
	public ResponseEntity<Object> buscarDireccionYCiudad(@RequestParam String dir,
			@RequestParam String ciudad) {

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
