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

import co.edu.unbosque.mensajeria.dto.PaqueteNoAlimenticioDTO;
import co.edu.unbosque.mensajeria.exception.CiudadInvalidaException;
import co.edu.unbosque.mensajeria.exception.DireccionInvalidaException;
import co.edu.unbosque.mensajeria.exception.IdInvalidoException;
import co.edu.unbosque.mensajeria.exception.TamanioInvalidoException;
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
	public ResponseEntity<String> crear(@RequestParam String direccionDestino, @RequestParam String tamanio,
			@RequestParam String ciudadDestino, @RequestParam boolean esFragil) {

		try {
			PaqueteNoAlimenticioDTO dto = new PaqueteNoAlimenticioDTO();
			dto.setDireccionDestino(direccionDestino);
			dto.setTamanio(tamanio);
			dto.setCiudadDestino(ciudadDestino);
			dto.setEsFragil(esFragil);

			int resultado = paqueteNoAlimenticioSer.create(dto);

			if (resultado != 0) {
				return new ResponseEntity<>("Paquete no alimenticio registrado con éxito.", HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("No se pudo registrar el paquete.", HttpStatus.BAD_REQUEST);
			}

		} catch (DireccionInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TamanioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (CiudadInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}  catch (MethodArgumentTypeMismatchException e) {
	        return new ResponseEntity<>("El valor de debe ser true o false", HttpStatus.BAD_REQUEST);
	    }
	}

	// http://localhost:8080/paquetenoalimenticio/mostrartodo
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<PaqueteNoAlimenticioDTO>> mostrarTodo() {
		List<PaqueteNoAlimenticioDTO> lista = paqueteNoAlimenticioSer.getAll();
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	// http://localhost:8080/paquetenoalimenticio/actualizar?id=1&esFragil=false&precioEnvio=10000&direccionDestino=BarrioSur&tamanio=Mediano&fechaCreacionPedido=2024-03-20T08:00:00&fechaEstimadaEntrega=2024-03-21T08:00:00
	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizar(@RequestParam Long id, @RequestParam boolean esFragil,
			@RequestParam int precioEnvio, @RequestParam String direccionDestino, @RequestParam String tamanio,
			@RequestParam LocalDateTime fechaCreacionPedido, @RequestParam LocalDateTime fechaEstimadaEntrega) {
		try {
			PaqueteNoAlimenticioDTO nuevo = new PaqueteNoAlimenticioDTO();

			nuevo.setEsFragil(esFragil);
			nuevo.setTamanio(tamanio);
			nuevo.setDireccionDestino(direccionDestino);
			nuevo.setFechaCreacionPedido(fechaCreacionPedido);
			nuevo.setFechaEstimadaEntrega(fechaEstimadaEntrega);
			nuevo.setPrecioEnvio(precioEnvio);

			int status = paqueteNoAlimenticioSer.updateById(id, nuevo);
			if (status == 0) {
				return new ResponseEntity<>("Paquete no alimenticio actualizado con éxito", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>("No se encontró el paquete no alimenticio con el ID ingresado",
						HttpStatus.BAD_REQUEST);
			}
		} catch (DireccionInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TamanioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (CiudadInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}  catch (MethodArgumentTypeMismatchException e) {
	        return new ResponseEntity<>("El formato no corresponde con el requerido", HttpStatus.BAD_REQUEST);
	    }
	}

	// http://localhost:8080/paquetenoalimenticio/eliminar?id=1
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> delete(@RequestParam Long id) {
		try {
			int status = paqueteNoAlimenticioSer.deleteById(id);
			if (status == 0) {
				return new ResponseEntity<>("Paquete no alimenticio eliminado con éxito", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>("No se encontró el paquete no alimenticio con el ID ingresado" + id, HttpStatus.BAD_REQUEST);
			}
		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}  catch (MethodArgumentTypeMismatchException e) {
	        return new ResponseEntity<>("El valor de debe ser un número entero", HttpStatus.BAD_REQUEST);
	    }
	}

	@GetMapping("/buscartamanio")
	public ResponseEntity<Object> buscarPorTamanio(@RequestParam String tamanio) {
		
		try {
			List<PaqueteNoAlimenticioDTO> lista = paqueteNoAlimenticioSer.findByTamanio(tamanio);
			if (!lista.isEmpty()) {
				return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
			}

		} catch (TamanioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}

	@GetMapping("/buscaresfragil")
	public ResponseEntity<Object> buscarPorEsFragil(@RequestParam boolean esFragil) {
	    try {
	        List<PaqueteNoAlimenticioDTO> lista = paqueteNoAlimenticioSer.findByEsFragil(esFragil);
	        if (!lista.isEmpty()) {
	            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
	        } else {
	            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
	        }

	    } catch (MethodArgumentTypeMismatchException e) {
	        return new ResponseEntity<>("El valor debe ser true o false", HttpStatus.BAD_REQUEST);
	    }
	}
	
	@GetMapping("/buscarportamanioyfragil")
	public ResponseEntity<Object> buscarPorTamanioAndFragil(@RequestParam String tamanio,
			@RequestParam boolean esFragil) {
		
		try {
			List<PaqueteNoAlimenticioDTO> lista = paqueteNoAlimenticioSer.findByTamanioAndEsFragil(tamanio, esFragil);
			if (!lista.isEmpty()) {
				return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
			}

		} catch (TamanioInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (MethodArgumentTypeMismatchException e) {
	        return new ResponseEntity<>("El valor de debe ser true o false", HttpStatus.BAD_REQUEST);
	    }
	}

	@GetMapping("/seguimientoid")
	public ResponseEntity<Object> findById(@RequestParam Long id) {
		try {
			PaqueteNoAlimenticioDTO p = paqueteNoAlimenticioSer.findById(id);

			if (p != null) {
				return new ResponseEntity<>(p, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("No se encontró el paquete no alimenticio con el ID ingresado", HttpStatus.NOT_FOUND);
			}

	    } catch (MethodArgumentTypeMismatchException e) {
	        return new ResponseEntity<>("El valor de debe ser un número entero", HttpStatus.BAD_REQUEST);
	    } catch (IdInvalidoException e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	}
	
	// http://localhost:8080/paquetenoalimenticio/buscar-direccion-ciudad?dir=Calle123&ciudad=Bogota
	@GetMapping("/buscardireccionyciudad")
	public ResponseEntity<Object> buscarDireccionYCiudad(@RequestParam String dir,
			@RequestParam String ciudad) {
		
		try {
			List<PaqueteNoAlimenticioDTO> lista = paqueteNoAlimenticioSer.findByDireccionDestinoAndCiudadDestino(dir, ciudad);

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