
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
import co.edu.unbosque.mensajeria.dto.AdministradorDTO;
import co.edu.unbosque.mensajeria.exception.CedulaInvalidaException;
import co.edu.unbosque.mensajeria.exception.CorreoInvalidoException;
import co.edu.unbosque.mensajeria.exception.IdInvalidoException;
import co.edu.unbosque.mensajeria.exception.NombreInvalidoException;
import co.edu.unbosque.mensajeria.exception.TelefonoInvalidoException;
import co.edu.unbosque.mensajeria.exception.TurnoInvalidoException;
import co.edu.unbosque.mensajeria.service.AdministradorService;

/**
 * Controlador REST que expone los endpoints para la gestión de administradores
 * dentro del sistema de mensajería.
 *
 * <p>Provee operaciones CRUD completas así como búsquedas por diferentes
 * criterios. Todas las respuestas se retornan en formato JSON y los errores
 * de validación son gestionados mediante excepciones personalizadas.</p>
 *
 * <p>Base URL: {@code /administrador}</p>
 *
 * @author Angie Villarreal
 * @version 1.0
 * @since 1.0
 * @see AdministradorService
 * @see AdministradorDTO
 */
@RestController
@RequestMapping("/administrador")
@CrossOrigin(origins = { "http://localhost:8080", "*" })
public class AdministradorController {

    /**
     * Servicio de lógica de negocio para la gestión de administradores.
     */
    @Autowired
    private AdministradorService administradorSer;

    /**
     * Constructor por defecto de {@code AdministradorController}.
     */
    public AdministradorController() {
    }

    /**
     * Crea un nuevo administrador en el sistema.
     *
     * <p>Endpoint: {@code POST /administrador/crear}</p>
     *
     * @param nombre    nombre del administrador; no debe ser {@code null} ni vacío.
     * @param cedula    número de cédula del administrador; no debe ser {@code null} ni vacío.
     * @param correo    correo electrónico del administrador; debe tener formato válido.
     * @param telefono  número de teléfono del administrador; no debe ser {@code null} ni vacío.
     * @param turno     turno asignado al administrador ({@code 'M'} para mañana, {@code 'T'} para tarde).
     * @return {@link ResponseEntity} con un mensaje descriptivo y uno de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 201 Created} — administrador creado exitosamente.</li>
     *           <li>{@code 409 Conflict} — la cédula ya se encuentra registrada.</li>
     *           <li>{@code 400 Bad Request} — datos inválidos o error de validación.</li>
     *           <li>{@code 500 Internal Server Error} — error inesperado del servidor.</li>
     *         </ul>
     * @throws NombreInvalidoException   si el nombre no cumple el formato esperado.
     * @throws CedulaInvalidaException   si la cédula no cumple el formato esperado.
     * @throws CorreoInvalidoException   si el correo no cumple el formato esperado.
     * @throws TelefonoInvalidoException si el teléfono no cumple el formato esperado.
     * @throws TurnoInvalidoException    si el turno no corresponde a un valor permitido.
     */
    @PostMapping("/crear")
    public ResponseEntity<String> crearAdministrador(@RequestParam String nombre, @RequestParam String cedula,
            @RequestParam String correo, @RequestParam String telefono, @RequestParam char turno) {

        try {
            AdministradorDTO nuevo = new AdministradorDTO();
            nuevo.setNombre(nombre);
            nuevo.setCedula(cedula);
            nuevo.setCorreo(correo);
            nuevo.setTelefono(telefono);
            nuevo.setTurno(turno);

            int status = administradorSer.create(nuevo);

            if (status == 0) {
                return new ResponseEntity<>("Administrador creado con éxito", HttpStatus.CREATED);
            } else if (status == 1) {
                return new ResponseEntity<>("La cédula ya se encuentra registrada", HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>("Error al crear el paquete no alimenticio", HttpStatus.BAD_REQUEST);
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
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retorna la lista completa de administradores registrados en el sistema.
     *
     * <p>Endpoint: {@code GET /administrador/mostrartodo}</p>
     *
     * @return {@link ResponseEntity} con la lista de {@link AdministradorDTO} y uno de los
     *         siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — lista retornada exitosamente.</li>
     *           <li>{@code 204 No Content} — no existen administradores registrados.</li>
     *         </ul>
     */
    @GetMapping("/mostrartodo")
    public ResponseEntity<List<AdministradorDTO>> mostrarTodo() {
        List<AdministradorDTO> administradores = administradorSer.getAll();
        if (administradores.isEmpty()) {
            return new ResponseEntity<>(administradores, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(administradores, HttpStatus.ACCEPTED);
        }
    }

    /**
     * Actualiza los datos de un administrador existente identificado por su ID.
     *
     * <p>Endpoint: {@code PUT /administrador/actualizar}</p>
     *
     * @param id          identificador único del administrador a actualizar.
     * @param nombre      nuevo nombre del administrador.
     * @param cedula      nueva cédula del administrador.
     * @param correo      nuevo correo electrónico del administrador.
     * @param telefono    nuevo número de teléfono del administrador.
     * @param turno       nuevo turno del administrador.
     * @param usuario     nuevo nombre de usuario del administrador.
     * @param contrasenia nueva contraseña del administrador.
     * @return {@link ResponseEntity} con un mensaje descriptivo y uno de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — dato actualizado exitosamente.</li>
     *           <li>{@code 400 Bad Request} — el ID no existe o datos inválidos.</li>
     *           <li>{@code 500 Internal Server Error} — error inesperado del servidor.</li>
     *         </ul>
     * @throws NombreInvalidoException   si el nombre no cumple el formato esperado.
     * @throws CedulaInvalidaException   si la cédula no cumple el formato esperado.
     * @throws CorreoInvalidoException   si el correo no cumple el formato esperado.
     * @throws TelefonoInvalidoException si el teléfono no cumple el formato esperado.
     * @throws TurnoInvalidoException    si el turno no corresponde a un valor permitido.
     * @throws IdInvalidoException       si el ID proporcionado no es válido.
     */
    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizar(@RequestParam Long id, @RequestParam String nombre,
            @RequestParam String cedula, @RequestParam String correo, @RequestParam String telefono,
            @RequestParam char turno, @RequestParam String usuario, @RequestParam String contrasenia) {

        try {
            AdministradorDTO nuevo = new AdministradorDTO();
            nuevo.setNombre(nombre);
            nuevo.setCedula(cedula);
            nuevo.setCorreo(correo);
            nuevo.setTelefono(telefono);
            nuevo.setTurno(turno);
            nuevo.setUsuario(usuario);
            nuevo.setContrasenia(contrasenia);

            int status = administradorSer.updateById(id, nuevo);
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
        } catch (IdInvalidoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error inesperado", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Elimina un administrador del sistema según su ID.
     *
     * <p>Endpoint: {@code DELETE /administrador/eliminar}</p>
     *
     * @param id identificador único del administrador a eliminar.
     * @return {@link ResponseEntity} con un mensaje descriptivo y uno de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 200 OK} — administrador eliminado exitosamente.</li>
     *           <li>{@code 404 Not Found} — no se encontró ningún administrador con el ID proporcionado.</li>
     *           <li>{@code 400 Bad Request} — el ID proporcionado no es válido.</li>
     *           <li>{@code 500 Internal Server Error} — error al procesar la solicitud.</li>
     *         </ul>
     * @throws IdInvalidoException si el ID proporcionado no es válido.
     */
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> delete(@RequestParam Long id) {
        try {
            int status = administradorSer.deleteById(id);
            if (status == 0) {
                return new ResponseEntity<>("Administrador eliminado con éxito", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No se encontró ningún administrador con el ID ingresado",
                        HttpStatus.NOT_FOUND);
            }
        } catch (IdInvalidoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al procesar la solicitud", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Busca administradores por nombre.
     *
     * <p>Endpoint: {@code GET /administrador/buscarpornombre}</p>
     *
     * @param nombre nombre del administrador a buscar.
     * @return {@link ResponseEntity} con la lista de {@link AdministradorDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarpornombre")
    public ResponseEntity<List<AdministradorDTO>> buscarPorNombre(@RequestParam String nombre) {
        List<AdministradorDTO> lista = administradorSer.findByNombre(nombre);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Busca administradores por número de cédula.
     *
     * <p>Endpoint: {@code GET /administrador/buscarporcedula}</p>
     *
     * @param cedula número de cédula del administrador a buscar.
     * @return {@link ResponseEntity} con la lista de {@link AdministradorDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarporcedula")
    public ResponseEntity<List<AdministradorDTO>> buscarPorCedula(@RequestParam String cedula) {
        List<AdministradorDTO> lista = administradorSer.findByCedula(cedula);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Busca administradores por correo electrónico.
     *
     * <p>Endpoint: {@code GET /administrador/buscarporcorreo}</p>
     *
     * @param correo correo electrónico del administrador a buscar.
     * @return {@link ResponseEntity} con la lista de {@link AdministradorDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarporcorreo")
    public ResponseEntity<List<AdministradorDTO>> buscarPorCorreo(@RequestParam String correo) {
        List<AdministradorDTO> lista = administradorSer.findByCorreo(correo);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Busca administradores por número de teléfono.
     *
     * <p>Endpoint: {@code GET /administrador/buscarportelefono}</p>
     *
     * @param telefono número de teléfono del administrador a buscar.
     * @return {@link ResponseEntity} con la lista de {@link AdministradorDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarportelefono")
    public ResponseEntity<List<AdministradorDTO>> buscarPorTelefono(@RequestParam String telefono) {
        List<AdministradorDTO> lista = administradorSer.findByTelefono(telefono);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Busca administradores por nombre de usuario.
     *
     * <p>Endpoint: {@code GET /administrador/buscarporusuario}</p>
     *
     * @param usuario nombre de usuario del administrador a buscar.
     * @return {@link ResponseEntity} con la lista de {@link AdministradorDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarporusuario")
    public ResponseEntity<List<AdministradorDTO>> buscarPorUsuario(@RequestParam String usuario) {
        List<AdministradorDTO> lista = administradorSer.findByUsuario(usuario);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Busca administradores por nombre y número de cédula de forma combinada.
     *
     * <p>Endpoint: {@code GET /administrador/buscarpornombreycedula}</p>
     *
     * @param nombre nombre del administrador a buscar.
     * @param cedula número de cédula del administrador a buscar.
     * @return {@link ResponseEntity} con la lista de {@link AdministradorDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarpornombreycedula")
    public ResponseEntity<List<AdministradorDTO>> buscarPorNombreAndCedula(@RequestParam String nombre,
            @RequestParam String cedula) {
        List<AdministradorDTO> lista = administradorSer.findByNombreAndCedula(nombre, cedula);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }
}
