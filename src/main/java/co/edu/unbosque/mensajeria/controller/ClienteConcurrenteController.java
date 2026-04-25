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

import co.edu.unbosque.mensajeria.dto.ClienteConcurrenteDTO;
import co.edu.unbosque.mensajeria.exception.CedulaInvalidaException;
import co.edu.unbosque.mensajeria.exception.CorreoInvalidoException;
import co.edu.unbosque.mensajeria.exception.IdInvalidoException;
import co.edu.unbosque.mensajeria.exception.MetodoDePagoInvalidoException;
import co.edu.unbosque.mensajeria.exception.NombreInvalidoException;
import co.edu.unbosque.mensajeria.exception.TelefonoInvalidoException;
import co.edu.unbosque.mensajeria.exception.TipoPedidoInvalidoException;
import co.edu.unbosque.mensajeria.service.ClienteConcurrenteService;

/**
 * Controlador REST que expone los endpoints para la gestión de clientes
 * concurrentes dentro del sistema de mensajería.
 *
 * <p>Provee operaciones CRUD completas así como búsquedas por diferentes
 * criterios. Todas las respuestas se retornan en formato JSON y los errores
 * de validación son gestionados mediante excepciones personalizadas.</p>
 *
 * <p>Base URL: {@code /clienteconcurrente}</p>
 *
 * @version 1.0
 * @since 1.0
 * @see ClienteConcurrenteService
 * @see ClienteConcurrenteDTO
 */
@RestController
@RequestMapping("/clienteconcurrente")
@CrossOrigin(origins = { "http://localhost:8080", "*" })
public class ClienteConcurrenteController {

    /**
     * Servicio de lógica de negocio para la gestión de clientes concurrentes.
     */
    @Autowired
    private ClienteConcurrenteService clienteConcurrenteService;

    /**
     * Constructor por defecto de {@code ClienteConcurrenteController}.
     */
    public ClienteConcurrenteController() {
    }

    /**
     * Crea un nuevo cliente concurrente en el sistema.
     *
     * <p>Endpoint: {@code POST /clienteconcurrente/crear}</p>
     *
     * @param nombre             nombre del cliente; no debe ser {@code null} ni vacío.
     * @param cedula             número de cédula del cliente; no debe ser {@code null} ni vacío.
     * @param correo             correo electrónico del cliente; debe tener formato válido.
     * @param telefono           número de teléfono del cliente; no debe ser {@code null} ni vacío.
     * @param metodoPago         método de pago del cliente (por ejemplo, Efectivo, Tarjeta).
     * @param tipoPedido         tipo de pedido del cliente (por ejemplo, Alimenticio, No Alimenticio).
     * @param tarifaConcurrente  tarifa especial asignada al cliente concurrente.
     * @return {@link ResponseEntity} con un mensaje descriptivo y uno de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 201 Created} — cliente concurrente creado exitosamente.</li>
     *           <li>{@code 409 Conflict} — la cédula ya se encuentra registrada.</li>
     *           <li>{@code 400 Bad Request} — datos inválidos o error de validación.</li>
     *           <li>{@code 500 Internal Server Error} — error inesperado del servidor.</li>
     *         </ul>
     * @throws NombreInvalidoException          si el nombre no cumple el formato esperado.
     * @throws CedulaInvalidaException          si la cédula no cumple el formato esperado.
     * @throws CorreoInvalidoException          si el correo no cumple el formato esperado.
     * @throws TelefonoInvalidoException        si el teléfono no cumple el formato esperado.
     * @throws MetodoDePagoInvalidoException    si el método de pago no corresponde a un valor permitido.
     * @throws TipoPedidoInvalidoException      si el tipo de pedido no corresponde a un valor permitido.
     */
    @PostMapping("/crear")
    public ResponseEntity<String> crearClienteConcurrente(@RequestParam String nombre, @RequestParam String cedula,
            @RequestParam String correo, @RequestParam String telefono, @RequestParam String metodoPago,
            @RequestParam String tipoPedido, @RequestParam double tarifaConcurrente) {

        try {
            ClienteConcurrenteDTO nuevoClienteConcurrente = new ClienteConcurrenteDTO();
            nuevoClienteConcurrente.setNombre(nombre);
            nuevoClienteConcurrente.setCedula(cedula);
            nuevoClienteConcurrente.setCorreo(correo);
            nuevoClienteConcurrente.setTelefono(telefono);
            nuevoClienteConcurrente.setMetodoPago(metodoPago);
            nuevoClienteConcurrente.setTipoPedido(tipoPedido);
            nuevoClienteConcurrente.setTarifaConcurrente(tarifaConcurrente);

            int status = clienteConcurrenteService.create(nuevoClienteConcurrente);

            if (status == 0) {
                return new ResponseEntity<>("Dato creado con éxito", HttpStatus.CREATED);
            } else if (status == 1) {
                return new ResponseEntity<>("La cédula ya se encuentra registrada", HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>("Error al crear Cliente", HttpStatus.BAD_REQUEST);
            }
        } catch (NombreInvalidoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (CedulaInvalidaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (CorreoInvalidoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (TelefonoInvalidoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (MetodoDePagoInvalidoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (TipoPedidoInvalidoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retorna la lista completa de clientes concurrentes registrados en el sistema.
     *
     * <p>Endpoint: {@code GET /clienteconcurrente/mostrartodo}</p>
     *
     * @return {@link ResponseEntity} con la lista de {@link ClienteConcurrenteDTO} y uno de los
     *         siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — lista retornada exitosamente.</li>
     *           <li>{@code 400 Bad Request} — no existen clientes concurrentes registrados.</li>
     *         </ul>
     */
    @GetMapping("/mostrartodo")
    public ResponseEntity<List<ClienteConcurrenteDTO>> mostrarTodo() {
        List<ClienteConcurrenteDTO> clientes = clienteConcurrenteService.getAll();
        if (!clientes.isEmpty()) {
            return new ResponseEntity<List<ClienteConcurrenteDTO>>(clientes, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<List<ClienteConcurrenteDTO>>(clientes, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Elimina un cliente concurrente del sistema según su ID.
     *
     * <p>Endpoint: {@code DELETE /clienteconcurrente/eliminar}</p>
     *
     * @param id identificador único del cliente concurrente a eliminar.
     * @return {@link ResponseEntity} con un mensaje descriptivo y uno de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — cliente eliminado exitosamente.</li>
     *           <li>{@code 400 Bad Request} — error al eliminar el cliente.</li>
     *           <li>{@code 500 Internal Server Error} — error al procesar la solicitud.</li>
     *         </ul>
     * @throws IdInvalidoException si el ID proporcionado no es válido.
     */
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarClienteConcurrente(@RequestParam Long id) {
        try {
            int status = clienteConcurrenteService.deleteById(id);
            if (status == 0) {
                return new ResponseEntity<>("Cliente eliminado correctamente. ", HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>("No se encontró el cliente con el ID ingresado: " + id, HttpStatus.BAD_REQUEST);
            }
        } catch (IdInvalidoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al procesar la solicitud", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Actualiza los datos de un cliente concurrente existente identificado por su ID.
     *
     * <p>Endpoint: {@code PUT /clienteconcurrente/actualizarclienteconcurrente}</p>
     *
     * @param id                 identificador único del cliente concurrente a actualizar.
     * @param nombre             nuevo nombre del cliente.
     * @param cedula             nueva cédula del cliente.
     * @param correo             nuevo correo electrónico del cliente.
     * @param telefono           nuevo número de teléfono del cliente.
     * @param metodoPago         nuevo método de pago del cliente.
     * @param tipoPedido         nuevo tipo de pedido del cliente.
     * @param tarifaConcurrente  nueva tarifa concurrente del cliente.
     * @return {@link ResponseEntity} con un mensaje descriptivo y uno de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — cliente actualizado exitosamente.</li>
     *           <li>{@code 400 Bad Request} — datos inválidos o error de validación.</li>
     *           <li>{@code 500 Internal Server Error} — error inesperado del servidor.</li>
     *         </ul>
     * @throws NombreInvalidoException          si el nombre no cumple el formato esperado.
     * @throws CedulaInvalidaException          si la cédula no cumple el formato esperado.
     * @throws CorreoInvalidoException          si el correo no cumple el formato esperado.
     * @throws TelefonoInvalidoException        si el teléfono no cumple el formato esperado.
     * @throws MetodoDePagoInvalidoException    si el método de pago no corresponde a un valor permitido.
     * @throws TipoPedidoInvalidoException      si el tipo de pedido no corresponde a un valor permitido.
     * @throws IdInvalidoException              si el ID proporcionado no es válido.
     */
    @PutMapping("/actualizarclienteconcurrente")
    public ResponseEntity<String> actualizarClienteConcurrente(@RequestParam Long id, @RequestParam String nombre,
            @RequestParam String cedula, @RequestParam String correo, @RequestParam String telefono,
            @RequestParam String metodoPago, @RequestParam String tipoPedido, @RequestParam double tarifaConcurrente) {
        try {
            ClienteConcurrenteDTO clienteConcurrenteNuevo = new ClienteConcurrenteDTO();
            clienteConcurrenteNuevo.setNombre(nombre);
            clienteConcurrenteNuevo.setCedula(cedula);
            clienteConcurrenteNuevo.setCorreo(correo);
            clienteConcurrenteNuevo.setTelefono(telefono);
            clienteConcurrenteNuevo.setMetodoPago(metodoPago);
            clienteConcurrenteNuevo.setTipoPedido(tipoPedido);
            clienteConcurrenteNuevo.setTarifaConcurrente(tarifaConcurrente);

            int status = clienteConcurrenteService.updateById(id, clienteConcurrenteNuevo);

            if (status == 0) {
                return new ResponseEntity<>("Cliente actualizado correctamente. ", HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>("Error al actualizar cliente. ", HttpStatus.BAD_REQUEST);
            }
        } catch (NombreInvalidoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (CedulaInvalidaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (CorreoInvalidoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (TelefonoInvalidoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (MetodoDePagoInvalidoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (TipoPedidoInvalidoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IdInvalidoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error inesperado", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Busca clientes concurrentes por nombre.
     *
     * <p>Endpoint: {@code GET /clienteconcurrente/buscarpornombre}</p>
     *
     * @param nombre nombre del cliente a buscar.
     * @return {@link ResponseEntity} con la lista de {@link ClienteConcurrenteDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarpornombre")
    public ResponseEntity<List<ClienteConcurrenteDTO>> buscarPorNombre(@RequestParam String nombre) {
        List<ClienteConcurrenteDTO> lista = clienteConcurrenteService.findByNombre(nombre);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Busca clientes concurrentes por número de cédula.
     *
     * <p>Endpoint: {@code GET /clienteconcurrente/buscarporcedula}</p>
     *
     * @param cedula número de cédula del cliente a buscar.
     * @return {@link ResponseEntity} con la lista de {@link ClienteConcurrenteDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarporcedula")
    public ResponseEntity<List<ClienteConcurrenteDTO>> buscarPorCedula(@RequestParam String cedula) {
        List<ClienteConcurrenteDTO> lista = clienteConcurrenteService.findByCedula(cedula);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Busca clientes concurrentes por correo electrónico.
     *
     * <p>Endpoint: {@code GET /clienteconcurrente/buscarporcorreo}</p>
     *
     * @param correo correo electrónico del cliente a buscar.
     * @return {@link ResponseEntity} con la lista de {@link ClienteConcurrenteDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarporcorreo")
    public ResponseEntity<List<ClienteConcurrenteDTO>> buscarPorCorreo(@RequestParam String correo) {
        List<ClienteConcurrenteDTO> lista = clienteConcurrenteService.findByCorreo(correo);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Busca clientes concurrentes por número de teléfono.
     *
     * <p>Endpoint: {@code GET /clienteconcurrente/buscarportelefono}</p>
     *
     * @param telefono número de teléfono del cliente a buscar.
     * @return {@link ResponseEntity} con la lista de {@link ClienteConcurrenteDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarportelefono")
    public ResponseEntity<List<ClienteConcurrenteDTO>> buscarPorTelefono(@RequestParam String telefono) {
        List<ClienteConcurrenteDTO> lista = clienteConcurrenteService.findByTelefono(telefono);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Busca clientes concurrentes por método de pago.
     *
     * <p>Endpoint: {@code GET /clienteconcurrente/buscarpormetodopago}</p>
     *
     * @param metodoPago método de pago del cliente a buscar (por ejemplo, Efectivo, Tarjeta).
     * @return {@link ResponseEntity} con la lista de {@link ClienteConcurrenteDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarpormetodopago")
    public ResponseEntity<List<ClienteConcurrenteDTO>> buscarPorMetodoPago(@RequestParam String metodoPago) {
        List<ClienteConcurrenteDTO> lista = clienteConcurrenteService.findByMetodoPago(metodoPago);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Busca clientes concurrentes por tipo de pedido.
     *
     * <p>Endpoint: {@code GET /clienteconcurrente/buscarportipopedido}</p>
     *
     * @param tipoPedido tipo de pedido del cliente a buscar (por ejemplo, Alimenticio, No Alimenticio).
     * @return {@link ResponseEntity} con la lista de {@link ClienteConcurrenteDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarportipopedido")
    public ResponseEntity<List<ClienteConcurrenteDTO>> buscarPorTipoPedido(@RequestParam String tipoPedido) {
        List<ClienteConcurrenteDTO> lista = clienteConcurrenteService.findByTipoPedido(tipoPedido);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Busca clientes concurrentes por nombre y número de cédula de forma combinada.
     *
     * <p>Endpoint: {@code GET /clienteconcurrente/buscarpornombreycedula}</p>
     *
     * @param nombre nombre del cliente a buscar.
     * @param cedula número de cédula del cliente a buscar.
     * @return {@link ResponseEntity} con la lista de {@link ClienteConcurrenteDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarpornombreycedula")
    public ResponseEntity<List<ClienteConcurrenteDTO>> buscarPorNombreAndCedula(@RequestParam String nombre,
            @RequestParam String cedula) {
        List<ClienteConcurrenteDTO> lista = clienteConcurrenteService.findByNombreAndCedula(nombre, cedula);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Busca clientes concurrentes por tipo de pedido y método de pago de forma combinada.
     *
     * <p>Endpoint: {@code GET /clienteconcurrente/buscarportipopedidoymetodopago}</p>
     *
     * @param tipoPedido tipo de pedido del cliente a buscar.
     * @param metodoPago método de pago del cliente a buscar.
     * @return {@link ResponseEntity} con la lista de {@link ClienteConcurrenteDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarportipopedidoymetodopago")
    public ResponseEntity<List<ClienteConcurrenteDTO>> buscarPorTipoPedidoAndMetodoPago(
            @RequestParam String tipoPedido, @RequestParam String metodoPago) {
        List<ClienteConcurrenteDTO> lista = clienteConcurrenteService.findByTipoPedidoAndMetodoPago(tipoPedido,
                metodoPago);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }
}
