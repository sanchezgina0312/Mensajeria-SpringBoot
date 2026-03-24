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

import co.edu.unbosque.mensajeria.dto.ConductorDTO;
import co.edu.unbosque.mensajeria.exception.CedulaInvalidaException;
import co.edu.unbosque.mensajeria.exception.CorreoInvalidoException;
import co.edu.unbosque.mensajeria.exception.IdInvalidoException;
import co.edu.unbosque.mensajeria.exception.NombreInvalidoException;
import co.edu.unbosque.mensajeria.exception.PlacaInvalidaException;
import co.edu.unbosque.mensajeria.exception.TelefonoInvalidoException;
import co.edu.unbosque.mensajeria.exception.TurnoInvalidoException;
import co.edu.unbosque.mensajeria.service.ConductorService;

/**
 * Controlador REST que expone los endpoints para la gestión de conductores
 * dentro del sistema de mensajería.
 *
 * <p>Provee operaciones CRUD completas así como búsquedas por diferentes
 * criterios. Todas las respuestas se retornan en formato JSON y los errores
 * de validación son gestionados mediante excepciones personalizadas.</p>
 *
 * <p>Base URL: {@code /conductor}</p>
 *
 * @author Angie Villarreal
 * @version 1.0
 * @since 1.0
 * @see ConductorService
 * @see ConductorDTO
 */
@RestController
@RequestMapping("/conductor")
@CrossOrigin(origins = { "http://localhost:8080", "*" })
public class ConductorController {

    /**
     * Servicio de lógica de negocio para la gestión de conductores.
     */
    @Autowired
    private ConductorService conductorSer;

    /**
     * Constructor por defecto de {@code ConductorController}.
     */
    public ConductorController() {
    }

    /**
     * Crea un nuevo conductor en el sistema.
     *
     * <p>Endpoint: {@code POST /conductor/crear}</p>
     *
     * @param nombre        nombre del conductor; no debe ser {@code null} ni vacío.
     * @param cedula        número de cédula del conductor; no debe ser {@code null} ni vacío.
     * @param correo        correo electrónico del conductor; debe tener formato válido.
     * @param telefono      número de teléfono del conductor; no debe ser {@code null} ni vacío.
     * @param turno         turno asignado al conductor ({@code 'M'} para mañana, {@code 'T'} para tarde).
     * @param placaVehiculo placa del vehículo asignado al conductor; debe tener formato válido.
     * @return {@link ResponseEntity} con un mensaje descriptivo y uno de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 201 Created} — conductor creado exitosamente.</li>
     *           <li>{@code 409 Conflict} — la cédula ya se encuentra registrada.</li>
     *           <li>{@code 400 Bad Request} — datos inválidos o error de validación.</li>
     *           <li>{@code 500 Internal Server Error} — error inesperado del servidor.</li>
     *         </ul>
     * @throws NombreInvalidoException   si el nombre no cumple el formato esperado.
     * @throws CedulaInvalidaException   si la cédula no cumple el formato esperado.
     * @throws CorreoInvalidoException   si el correo no cumple el formato esperado.
     * @throws TelefonoInvalidoException si el teléfono no cumple el formato esperado.
     * @throws TurnoInvalidoException    si el turno no corresponde a un valor permitido.
     * @throws PlacaInvalidaException    si la placa del vehículo no cumple el formato esperado.
     */
    @PostMapping("/crear")
    public ResponseEntity<String> crearConductor(@RequestParam String nombre, @RequestParam String cedula,
            @RequestParam String correo, @RequestParam String telefono, @RequestParam char turno,
            @RequestParam String placaVehiculo) {

        try {
            ConductorDTO nuevo = new ConductorDTO(nombre, cedula, correo, telefono, turno, placaVehiculo);
            int status = conductorSer.create(nuevo);
            if (status == 0) {
                return new ResponseEntity<>("Dato creado con éxito", HttpStatus.CREATED);
            } else if (status == 1) {
                return new ResponseEntity<>("La cédula ya se encuentra registrada", HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>("Error al crear conductor", HttpStatus.BAD_REQUEST);
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
        } catch (PlacaInvalidaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retorna la lista completa de conductores registrados en el sistema.
     *
     * <p>Endpoint: {@code GET /conductor/mostrartodo}</p>
     *
     * @return {@link ResponseEntity} con la lista de {@link ConductorDTO} y uno de los
     *         siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — lista retornada exitosamente.</li>
     *           <li>{@code 204 No Content} — no existen conductores registrados.</li>
     *         </ul>
     */
    @GetMapping("/mostrartodo")
    public ResponseEntity<List<ConductorDTO>> mostrarTodo() {
        List<ConductorDTO> conductores = conductorSer.getAll();
        if (conductores.isEmpty()) {
            return new ResponseEntity<>(conductores, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(conductores, HttpStatus.ACCEPTED);
        }
    }

    /**
     * Actualiza los datos de un conductor existente identificado por su ID.
     *
     * <p>Endpoint: {@code PUT /conductor/actualizar}</p>
     *
     * @param id            identificador único del conductor a actualizar.
     * @param nombre        nuevo nombre del conductor.
     * @param cedula        nueva cédula del conductor.
     * @param correo        nuevo correo electrónico del conductor.
     * @param telefono      nuevo número de teléfono del conductor.
     * @param turno         nuevo turno del conductor.
     * @param placaVehiculo nueva placa del vehículo del conductor.
     * @return {@link ResponseEntity} con un mensaje descriptivo y uno de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — conductor actualizado exitosamente.</li>
     *           <li>{@code 400 Bad Request} — el ID no existe o datos inválidos.</li>
     *           <li>{@code 500 Internal Server Error} — error inesperado del servidor.</li>
     *         </ul>
     * @throws NombreInvalidoException   si el nombre no cumple el formato esperado.
     * @throws CedulaInvalidaException   si la cédula no cumple el formato esperado.
     * @throws CorreoInvalidoException   si el correo no cumple el formato esperado.
     * @throws TelefonoInvalidoException si el teléfono no cumple el formato esperado.
     * @throws TurnoInvalidoException    si el turno no corresponde a un valor permitido.
     * @throws PlacaInvalidaException    si la placa del vehículo no cumple el formato esperado.
     * @throws IdInvalidoException       si el ID proporcionado no es válido.
     */
    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizar(@RequestParam Long id, @RequestParam String nombre,
            @RequestParam String cedula, @RequestParam String correo, @RequestParam String telefono,
            @RequestParam char turno, @RequestParam String placaVehiculo) {
        try {
            ConductorDTO nuevo = new ConductorDTO(nombre, cedula, correo, telefono, turno, placaVehiculo);
            int status = conductorSer.updateById(id, nuevo);
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
        } catch (PlacaInvalidaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IdInvalidoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Elimina un conductor del sistema según su ID.
     *
     * <p>Endpoint: {@code DELETE /conductor/eliminar}</p>
     *
     * @param id identificador único del conductor a eliminar.
     * @return {@link ResponseEntity} con un mensaje descriptivo y uno de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — conductor eliminado exitosamente.</li>
     *           <li>{@code 400 Bad Request} — no se encontró el registro con el ID proporcionado.</li>
     *           <li>{@code 500 Internal Server Error} — error al procesar la solicitud.</li>
     *         </ul>
     * @throws IdInvalidoException si el ID proporcionado no es válido.
     */
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> delete(@RequestParam Long id) {
        try {
            int status = conductorSer.deleteById(id);
            if (status == 0) {
                return new ResponseEntity<>("Dato eliminado con éxito", HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>("Error: No se encontró el registro con ID: " + id, HttpStatus.BAD_REQUEST);
            }
        } catch (IdInvalidoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al procesar la solicitud", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Busca conductores por nombre.
     *
     * <p>Endpoint: {@code GET /conductor/buscarpornombre}</p>
     *
     * @param nombre nombre del conductor a buscar.
     * @return {@link ResponseEntity} con la lista de {@link ConductorDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarpornombre")
    public ResponseEntity<List<ConductorDTO>> buscarPorNombre(@RequestParam String nombre) {
        List<ConductorDTO> lista = conductorSer.findByNombre(nombre);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Busca conductores por número de cédula.
     *
     * <p>Endpoint: {@code GET /conductor/buscarporcedula}</p>
     *
     * @param cedula número de cédula del conductor a buscar.
     * @return {@link ResponseEntity} con la lista de {@link ConductorDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarporcedula")
    public ResponseEntity<List<ConductorDTO>> buscarPorCedula(@RequestParam String cedula) {
        List<ConductorDTO> lista = conductorSer.findByCedula(cedula);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Busca conductores por correo electrónico.
     *
     * <p>Endpoint: {@code GET /conductor/buscarporcorreo}</p>
     *
     * @param correo correo electrónico del conductor a buscar.
     * @return {@link ResponseEntity} con la lista de {@link ConductorDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarporcorreo")
    public ResponseEntity<List<ConductorDTO>> buscarPorCorreo(@RequestParam String correo) {
        List<ConductorDTO> lista = conductorSer.findByCorreo(correo);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Busca conductores por número de teléfono.
     *
     * <p>Endpoint: {@code GET /conductor/buscarportelefono}</p>
     *
     * @param telefono número de teléfono del conductor a buscar.
     * @return {@link ResponseEntity} con la lista de {@link ConductorDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarportelefono")
    public ResponseEntity<List<ConductorDTO>> buscarPorTelefono(@RequestParam String telefono) {
        List<ConductorDTO> lista = conductorSer.findByTelefono(telefono);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Busca conductores por placa del vehículo.
     *
     * <p>Endpoint: {@code GET /conductor/buscarporplacavehiculo}</p>
     *
     * @param placaVehiculo placa del vehículo del conductor a buscar.
     * @return {@link ResponseEntity} con la lista de {@link ConductorDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarporplacavehiculo")
    public ResponseEntity<List<ConductorDTO>> buscarPorPlacaVehiculo(@RequestParam String placaVehiculo) {
        List<ConductorDTO> lista = conductorSer.findByPlacaVehiculo(placaVehiculo);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Busca conductores por nombre y número de cédula de forma combinada.
     *
     * <p>Endpoint: {@code GET /conductor/buscarpornombreycedula}</p>
     *
     * @param nombre nombre del conductor a buscar.
     * @param cedula número de cédula del conductor a buscar.
     * @return {@link ResponseEntity} con la lista de {@link ConductorDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarpornombreycedula")
    public ResponseEntity<List<ConductorDTO>> buscarPorNombreAndCedula(@RequestParam String nombre,
            @RequestParam String cedula) {
        List<ConductorDTO> lista = conductorSer.findByNombreAndCedula(nombre, cedula);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }
}
