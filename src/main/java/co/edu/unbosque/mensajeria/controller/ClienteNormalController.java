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

import co.edu.unbosque.mensajeria.dto.ClienteNormalDTO;
import co.edu.unbosque.mensajeria.exception.CedulaInvalidaException;
import co.edu.unbosque.mensajeria.exception.CorreoInvalidoException;
import co.edu.unbosque.mensajeria.exception.IdInvalidoException;
import co.edu.unbosque.mensajeria.exception.MetodoDePagoInvalidoException;
import co.edu.unbosque.mensajeria.exception.NombreInvalidoException;
import co.edu.unbosque.mensajeria.exception.TelefonoInvalidoException;
import co.edu.unbosque.mensajeria.exception.TipoPedidoInvalidoException;
import co.edu.unbosque.mensajeria.service.ClienteNormalService;

/**
 * Controlador REST que expone los endpoints para la gestión de clientes
 * normales dentro del sistema de mensajería.
 *
 * <p>Provee operaciones CRUD completas así como búsquedas por diferentes
 * criterios. Todas las respuestas se retornan en formato JSON y los errores
 * de validación son gestionados mediante excepciones personalizadas.</p>
 *
 * <p>Base URL: {@code /clientenormal}</p>
 *
 * @version 1.0
 * @since 1.0
 * @see ClienteNormalService
 * @see ClienteNormalDTO
 */
@RestController
@RequestMapping("/clientenormal")
@CrossOrigin(origins = { "http://localhost:8080", "*" })
public class ClienteNormalController {

    /**
     * Servicio de lógica de negocio para la gestión de clientes normales.
     */
    @Autowired
    private ClienteNormalService clienteNormalService;

    /**
     * Constructor por defecto de {@code ClienteNormalController}.
     */
    public ClienteNormalController() {
    }

    /**
     * Crea un nuevo cliente normal en el sistema.
     *
     * <p>Endpoint: {@code POST /clientenormal/crear}</p>
     *
     * @param nombre     nombre del cliente; no debe ser {@code null} ni vacío.
     * @param cedula     número de cédula del cliente; no debe ser {@code null} ni vacío.
     * @param correo     correo electrónico del cliente; debe tener formato válido.
     * @param telefono   número de teléfono del cliente; no debe ser {@code null} ni vacío.
     * @param metodoPago método de pago del cliente (por ejemplo, Efectivo, Transferencia).
     * @param tipoPedido tipo de pedido del cliente (por ejemplo, Salud, Hogar).
     * @return {@link ResponseEntity} con un mensaje descriptivo y uno de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 201 Created} — cliente normal creado exitosamente.</li>
     *           <li>{@code 409 Conflict} — la cédula ya se encuentra registrada.</li>
     *           <li>{@code 400 Bad Request} — datos inválidos o error de validación.</li>
     *           <li>{@code 500 Internal Server Error} — error inesperado del servidor.</li>
     *         </ul>
     * @throws NombreInvalidoException       si el nombre no cumple el formato esperado.
     * @throws CedulaInvalidaException       si la cédula no cumple el formato esperado.
     * @throws CorreoInvalidoException       si el correo no cumple el formato esperado.
     * @throws TelefonoInvalidoException     si el teléfono no cumple el formato esperado.
     * @throws MetodoDePagoInvalidoException si el método de pago no corresponde a un valor permitido.
     * @throws TipoPedidoInvalidoException   si el tipo de pedido no corresponde a un valor permitido.
     */
    @PostMapping("/crear")
    public ResponseEntity<String> crearClienteNormal(@RequestParam String nombre, @RequestParam String cedula,
            @RequestParam String correo, @RequestParam String telefono, @RequestParam String metodoPago,
            @RequestParam String tipoPedido) {

        try {
            ClienteNormalDTO nuevoClienteNormal = new ClienteNormalDTO();
            nuevoClienteNormal.setNombre(nombre);
            nuevoClienteNormal.setCedula(cedula);
            nuevoClienteNormal.setCorreo(correo);
            nuevoClienteNormal.setTelefono(telefono);
            nuevoClienteNormal.setMetodoPago(metodoPago);
            nuevoClienteNormal.setTipoPedido(tipoPedido);

            int status = clienteNormalService.create(nuevoClienteNormal);

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
     * Retorna la lista completa de clientes normales registrados en el sistema.
     *
     * <p>Endpoint: {@code GET /clientenormal/mostrar}</p>
     *
     * @return {@link ResponseEntity} con la lista de {@link ClienteNormalDTO} y uno de los
     *         siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — lista retornada exitosamente.</li>
     *           <li>{@code 204 No Content} — no existen clientes normales registrados.</li>
     *         </ul>
     */
    @GetMapping("/mostrar")
    public ResponseEntity<List<ClienteNormalDTO>> mostrarTodo() {
        List<ClienteNormalDTO> clientes = clienteNormalService.getAll();
        if (!clientes.isEmpty()) {
            return new ResponseEntity<>(clientes, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(clientes, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Elimina un cliente normal del sistema según su ID.
     *
     * <p>Endpoint: {@code DELETE /clientenormal/eliminar}</p>
     *
     * @param id identificador único del cliente normal a eliminar.
     * @return {@link ResponseEntity} con un mensaje descriptivo y uno de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — cliente eliminado exitosamente.</li>
     *           <li>{@code 400 Bad Request} — error al eliminar el cliente.</li>
     *           <li>{@code 500 Internal Server Error} — error al procesar la solicitud.</li>
     *         </ul>
     * @throws IdInvalidoException si el ID proporcionado no es válido.
     */
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarClienteNormal(@RequestParam Long id) {
        try {
            int status = clienteNormalService.deleteById(id);
            if (status == 0) {
                return new ResponseEntity<>("Cliente eliminado correctamente.", HttpStatus.ACCEPTED);
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
     * Actualiza los datos de un cliente normal existente identificado por su ID.
     *
     * <p>Endpoint: {@code PUT /clientenormal/actualizarclientenormal}</p>
     *
     * @param id         identificador único del cliente normal a actualizar.
     * @param nombre     nuevo nombre del cliente.
     * @param cedula     nueva cédula del cliente.
     * @param correo     nuevo correo electrónico del cliente.
     * @param telefono   nuevo número de teléfono del cliente.
     * @param metodoPago nuevo método de pago del cliente.
     * @param tipoPedido nuevo tipo de pedido del cliente.
     * @return {@link ResponseEntity} con un mensaje descriptivo y uno de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — cliente actualizado exitosamente.</li>
     *           <li>{@code 400 Bad Request} — datos inválidos o ID no encontrado.</li>
     *           <li>{@code 500 Internal Server Error} — error inesperado del servidor.</li>
     *         </ul>
     * @throws NombreInvalidoException       si el nombre no cumple el formato esperado.
     * @throws CedulaInvalidaException       si la cédula no cumple el formato esperado.
     * @throws CorreoInvalidoException       si el correo no cumple el formato esperado.
     * @throws TelefonoInvalidoException     si el teléfono no cumple el formato esperado.
     * @throws MetodoDePagoInvalidoException si el método de pago no corresponde a un valor permitido.
     * @throws TipoPedidoInvalidoException   si el tipo de pedido no corresponde a un valor permitido.
     * @throws IdInvalidoException           si el ID proporcionado no es válido.
     */
    @PutMapping("/actualizarclientenormal")
    public ResponseEntity<String> actualizarClienteNormal(@RequestParam Long id, @RequestParam String nombre,
            @RequestParam String cedula, @RequestParam String correo, @RequestParam String telefono,
            @RequestParam String metodoPago, @RequestParam String tipoPedido) {
        try {
            ClienteNormalDTO clienteNormalNuevo = new ClienteNormalDTO();
            clienteNormalNuevo.setNombre(nombre);
            clienteNormalNuevo.setCedula(cedula);
            clienteNormalNuevo.setCorreo(correo);
            clienteNormalNuevo.setTelefono(telefono);
            clienteNormalNuevo.setMetodoPago(metodoPago);
            clienteNormalNuevo.setTipoPedido(tipoPedido);

            int status = clienteNormalService.updateById(id, clienteNormalNuevo);

            if (status == 0) {
                return new ResponseEntity<>("Cliente actualizado correctamente.", HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>("Error al actualizar cliente. ID no encontrado.", HttpStatus.BAD_REQUEST);
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
     * Busca clientes normales por nombre.
     *
     * <p>Endpoint: {@code GET /clientenormal/buscarpornombre}</p>
     *
     * @param nombre nombre del cliente a buscar.
     * @return {@link ResponseEntity} con la lista de {@link ClienteNormalDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarpornombre")
    public ResponseEntity<List<ClienteNormalDTO>> buscarPorNombre(@RequestParam String nombre) {
        List<ClienteNormalDTO> lista = clienteNormalService.findByNombre(nombre);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Busca clientes normales por número de cédula.
     *
     * <p>Endpoint: {@code GET /clientenormal/buscarporcedula}</p>
     *
     * @param cedula número de cédula del cliente a buscar.
     * @return {@link ResponseEntity} con la lista de {@link ClienteNormalDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarporcedula")
    public ResponseEntity<List<ClienteNormalDTO>> buscarPorCedula(@RequestParam String cedula) {
        List<ClienteNormalDTO> lista = clienteNormalService.findByCedula(cedula);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Busca clientes normales por correo electrónico.
     *
     * <p>Endpoint: {@code GET /clientenormal/buscarporcorreo}</p>
     *
     * @param correo correo electrónico del cliente a buscar.
     * @return {@link ResponseEntity} con la lista de {@link ClienteNormalDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarporcorreo")
    public ResponseEntity<List<ClienteNormalDTO>> buscarPorCorreo(@RequestParam String correo) {
        List<ClienteNormalDTO> lista = clienteNormalService.findByCorreo(correo);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Busca clientes normales por número de teléfono.
     *
     * <p>Endpoint: {@code GET /clientenormal/buscarportelefono}</p>
     *
     * @param telefono número de teléfono del cliente a buscar.
     * @return {@link ResponseEntity} con la lista de {@link ClienteNormalDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarportelefono")
    public ResponseEntity<List<ClienteNormalDTO>> buscarPorTelefono(@RequestParam String telefono) {
        List<ClienteNormalDTO> lista = clienteNormalService.findByTelefono(telefono);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Busca clientes normales por método de pago.
     *
     * <p>Endpoint: {@code GET /clientenormal/buscarpormetodopago}</p>
     *
     * @param metodoPago método de pago del cliente a buscar (por ejemplo, Efectivo, Transferencia).
     * @return {@link ResponseEntity} con la lista de {@link ClienteNormalDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarpormetodopago")
    public ResponseEntity<List<ClienteNormalDTO>> buscarPorMetodoPago(@RequestParam String metodoPago) {
        List<ClienteNormalDTO> lista = clienteNormalService.findByMetodoPago(metodoPago);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Busca clientes normales por tipo de pedido.
     *
     * <p>Endpoint: {@code GET /clientenormal/buscarportipopedido}</p>
     *
     * @param tipoPedido tipo de pedido del cliente a buscar (por ejemplo, Salud, Hogar).
     * @return {@link ResponseEntity} con la lista de {@link ClienteNormalDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarportipopedido")
    public ResponseEntity<List<ClienteNormalDTO>> buscarPorTipoPedido(@RequestParam String tipoPedido) {
        List<ClienteNormalDTO> lista = clienteNormalService.findByTipoPedido(tipoPedido);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Busca clientes normales por nombre y número de cédula de forma combinada.
     *
     * <p>Endpoint: {@code GET /clientenormal/buscarpornombreycedula}</p>
     *
     * @param nombre nombre del cliente a buscar.
     * @param cedula número de cédula del cliente a buscar.
     * @return {@link ResponseEntity} con la lista de {@link ClienteNormalDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarpornombreycedula")
    public ResponseEntity<List<ClienteNormalDTO>> buscarPorNombreAndCedula(@RequestParam String nombre,
            @RequestParam String cedula) {
        List<ClienteNormalDTO> lista = clienteNormalService.findByNombreAndCedula(nombre, cedula);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Busca clientes normales por tipo de pedido y método de pago de forma combinada.
     *
     * <p>Endpoint: {@code GET /clientenormal/buscarportipopedidoymetodopago}</p>
     *
     * @param tipoPedido tipo de pedido del cliente a buscar.
     * @param metodoPago método de pago del cliente a buscar.
     * @return {@link ResponseEntity} con la lista de {@link ClienteNormalDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarportipopedidoymetodopago")
    public ResponseEntity<List<ClienteNormalDTO>> buscarPorTipoPedidoAndMetodoPago(@RequestParam String tipoPedido,
            @RequestParam String metodoPago) {
        List<ClienteNormalDTO> lista = clienteNormalService.findByTipoPedidoAndMetodoPago(tipoPedido, metodoPago);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }
}
