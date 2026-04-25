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

import co.edu.unbosque.mensajeria.dto.ClientePremiumDTO;
import co.edu.unbosque.mensajeria.exception.CedulaInvalidaException;
import co.edu.unbosque.mensajeria.exception.CorreoInvalidoException;
import co.edu.unbosque.mensajeria.exception.IdInvalidoException;
import co.edu.unbosque.mensajeria.exception.MetodoDePagoInvalidoException;
import co.edu.unbosque.mensajeria.exception.NombreInvalidoException;
import co.edu.unbosque.mensajeria.exception.TelefonoInvalidoException;
import co.edu.unbosque.mensajeria.exception.TipoPedidoInvalidoException;
import co.edu.unbosque.mensajeria.service.ClientePremiumService;

/**
 * Controlador REST que expone los endpoints para la gestión de clientes
 * premium dentro del sistema de mensajería.
 *
 * <p>Provee operaciones CRUD completas así como búsquedas por diferentes
 * criterios. Todas las respuestas se retornan en formato JSON y los errores
 * de validación son gestionados mediante excepciones personalizadas.</p>
 *
 * <p>Base URL: {@code /clientepremium}</p>
 *
 * @version 1.0
 * @since 1.0
 * @see ClientePremiumService
 * @see ClientePremiumDTO
 */
@RestController
@RequestMapping("/clientepremium")
@CrossOrigin(origins = { "http://localhost:8080", "*" })
public class ClientePremiumController {

    /**
     * Servicio de lógica de negocio para la gestión de clientes premium.
     */
    @Autowired
    private ClientePremiumService clientePremiumService;

    /**
     * Constructor por defecto de {@code ClientePremiumController}.
     */
    public ClientePremiumController() {
    }

    /**
     * Crea un nuevo cliente premium en el sistema.
     *
     * <p>Endpoint: {@code POST /clientepremium/crear}</p>
     *
     * @param nombre        nombre del cliente; no debe ser {@code null} ni vacío.
     * @param cedula        número de cédula del cliente; no debe ser {@code null} ni vacío.
     * @param correo        correo electrónico del cliente; debe tener formato válido.
     * @param telefono      número de teléfono del cliente; no debe ser {@code null} ni vacío.
     * @param metodoPago    método de pago del cliente (por ejemplo, Tarjeta, Puntos).
     * @param tipoPedido    tipo de pedido del cliente (por ejemplo, Alimenticio, Importados).
     * @param tarifaPremium tarifa especial asignada al cliente premium.
     * @return {@link ResponseEntity} con un mensaje descriptivo y uno de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 201 Created} — cliente premium creado exitosamente.</li>
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
    public ResponseEntity<String> crearClientePremium(@RequestParam String nombre, @RequestParam String cedula,
            @RequestParam String correo, @RequestParam String telefono, @RequestParam String metodoPago,
            @RequestParam String tipoPedido, @RequestParam double tarifaPremium) {

        try {
            ClientePremiumDTO nuevoClientePremium = new ClientePremiumDTO();
            nuevoClientePremium.setNombre(nombre);
            nuevoClientePremium.setCedula(cedula);
            nuevoClientePremium.setCorreo(correo);
            nuevoClientePremium.setTelefono(telefono);
            nuevoClientePremium.setMetodoPago(metodoPago);
            nuevoClientePremium.setTipoPedido(tipoPedido);
            nuevoClientePremium.setTarifaPremium(tarifaPremium);

            int status = clientePremiumService.create(nuevoClientePremium);

            if (status == 0) {
                return new ResponseEntity<>("Dato creado con éxito", HttpStatus.CREATED);
            } else if (status == 1) {
                return new ResponseEntity<>("La cédula ya se encuentra registrada", HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>("Error al crear cliente", HttpStatus.BAD_REQUEST);
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
     * Retorna la lista completa de clientes premium registrados en el sistema.
     *
     * <p>Endpoint: {@code GET /clientepremium/mostrartodo}</p>
     *
     * @return {@link ResponseEntity} con la lista de {@link ClientePremiumDTO} y uno de los
     *         siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — lista retornada exitosamente.</li>
     *           <li>{@code 204 No Content} — no existen clientes premium registrados.</li>
     *         </ul>
     */
    @GetMapping("/mostrartodo")
    public ResponseEntity<List<ClientePremiumDTO>> mostrarTodo() {
        List<ClientePremiumDTO> clientes = clientePremiumService.getAll();
        if (!clientes.isEmpty()) {
            return new ResponseEntity<>(clientes, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(clientes, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Elimina un cliente premium del sistema según su ID.
     *
     * <p>Endpoint: {@code DELETE /clientepremium/eliminar}</p>
     *
     * @param id identificador único del cliente premium a eliminar.
     * @return {@link ResponseEntity} con un mensaje descriptivo y uno de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 200 OK} — cliente eliminado exitosamente.</li>
     *           <li>{@code 400 Bad Request} — error al eliminar el cliente.</li>
     *           <li>{@code 500 Internal Server Error} — error al procesar la solicitud.</li>
     *         </ul>
     * @throws IdInvalidoException si el ID proporcionado no es válido.
     */
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarClientePremium(@RequestParam Long id) {
        try {
            int status = clientePremiumService.deleteById(id);
            if (status == 0) {
                return new ResponseEntity<>("Cliente eliminado correctamente.", HttpStatus.OK);
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
     * Actualiza los datos de un cliente premium existente identificado por su ID.
     *
     * <p>Endpoint: {@code PUT /clientepremium/actualizar}</p>
     *
     * @param id            identificador único del cliente premium a actualizar.
     * @param nombre        nuevo nombre del cliente.
     * @param cedula        nueva cédula del cliente.
     * @param correo        nuevo correo electrónico del cliente.
     * @param telefono      nuevo número de teléfono del cliente.
     * @param metodoPago    nuevo método de pago del cliente.
     * @param tipoPedido    nuevo tipo de pedido del cliente.
     * @param tarifaPremium nueva tarifa premium del cliente.
     * @return {@link ResponseEntity} con un mensaje descriptivo y uno de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 200 OK} — cliente actualizado exitosamente.</li>
     *           <li>{@code 400 Bad Request} — datos inválidos o error de validación.</li>
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
    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarClientePremium(@RequestParam Long id, @RequestParam String nombre,
            @RequestParam String cedula, @RequestParam String correo, @RequestParam String telefono,
            @RequestParam String metodoPago, @RequestParam String tipoPedido, @RequestParam double tarifaPremium) {

        try {
            ClientePremiumDTO clientePremiumNuevo = new ClientePremiumDTO();
            clientePremiumNuevo.setNombre(nombre);
            clientePremiumNuevo.setCedula(cedula);
            clientePremiumNuevo.setCorreo(correo);
            clientePremiumNuevo.setTelefono(telefono);
            clientePremiumNuevo.setMetodoPago(metodoPago);
            clientePremiumNuevo.setTipoPedido(tipoPedido);
            clientePremiumNuevo.setTarifaPremium(tarifaPremium);

            int status = clientePremiumService.updateById(id, clientePremiumNuevo);

            if (status == 0) {
                return new ResponseEntity<>("Cliente actualizado correctamente.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Error al actualizar cliente.", HttpStatus.BAD_REQUEST);
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
     * Busca clientes premium por nombre.
     *
     * <p>Endpoint: {@code GET /clientepremium/buscarpornombre}</p>
     *
     * @param nombre nombre del cliente a buscar.
     * @return {@link ResponseEntity} con la lista de {@link ClientePremiumDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarpornombre")
    public ResponseEntity<List<ClientePremiumDTO>> buscarPorNombre(@RequestParam String nombre) {
        List<ClientePremiumDTO> lista = clientePremiumService.findByNombre(nombre);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Busca clientes premium por número de cédula.
     *
     * <p>Endpoint: {@code GET /clientepremium/buscarporcedula}</p>
     *
     * @param cedula número de cédula del cliente a buscar.
     * @return {@link ResponseEntity} con la lista de {@link ClientePremiumDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarporcedula")
    public ResponseEntity<List<ClientePremiumDTO>> buscarPorCedula(@RequestParam String cedula) {
        List<ClientePremiumDTO> lista = clientePremiumService.findByCedula(cedula);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Busca clientes premium por correo electrónico.
     *
     * <p>Endpoint: {@code GET /clientepremium/buscarporcorreo}</p>
     *
     * @param correo correo electrónico del cliente a buscar.
     * @return {@link ResponseEntity} con la lista de {@link ClientePremiumDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarporcorreo")
    public ResponseEntity<List<ClientePremiumDTO>> buscarPorCorreo(@RequestParam String correo) {
        List<ClientePremiumDTO> lista = clientePremiumService.findByCorreo(correo);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Busca clientes premium por número de teléfono.
     *
     * <p>Endpoint: {@code GET /clientepremium/buscarportelefono}</p>
     *
     * @param telefono número de teléfono del cliente a buscar.
     * @return {@link ResponseEntity} con la lista de {@link ClientePremiumDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarportelefono")
    public ResponseEntity<List<ClientePremiumDTO>> buscarPorTelefono(@RequestParam String telefono) {
        List<ClientePremiumDTO> lista = clientePremiumService.findByTelefono(telefono);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Busca clientes premium por método de pago.
     *
     * <p>Endpoint: {@code GET /clientepremium/buscarpormetodopago}</p>
     *
     * @param metodoPago método de pago del cliente a buscar (por ejemplo, Tarjeta, Puntos).
     * @return {@link ResponseEntity} con la lista de {@link ClientePremiumDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarpormetodopago")
    public ResponseEntity<List<ClientePremiumDTO>> buscarPorMetodoPago(@RequestParam String metodoPago) {
        List<ClientePremiumDTO> lista = clientePremiumService.findByMetodoPago(metodoPago);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Busca clientes premium por tipo de pedido.
     *
     * <p>Endpoint: {@code GET /clientepremium/buscarportipopedido}</p>
     *
     * @param tipoPedido tipo de pedido del cliente a buscar (por ejemplo, Alimenticio, Importados).
     * @return {@link ResponseEntity} con la lista de {@link ClientePremiumDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarportipopedido")
    public ResponseEntity<List<ClientePremiumDTO>> buscarPorTipoPedido(@RequestParam String tipoPedido) {
        List<ClientePremiumDTO> lista = clientePremiumService.findByTipoPedido(tipoPedido);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Busca clientes premium por nombre y número de cédula de forma combinada.
     *
     * <p>Endpoint: {@code GET /clientepremium/buscarpornombreycedula}</p>
     *
     * @param nombre nombre del cliente a buscar.
     * @param cedula número de cédula del cliente a buscar.
     * @return {@link ResponseEntity} con la lista de {@link ClientePremiumDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarpornombreycedula")
    public ResponseEntity<List<ClientePremiumDTO>> buscarPorNombreAndCedula(@RequestParam String nombre,
            @RequestParam String cedula) {
        List<ClientePremiumDTO> lista = clientePremiumService.findByNombreAndCedula(nombre, cedula);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Busca clientes premium por tipo de pedido y método de pago de forma combinada.
     *
     * <p>Endpoint: {@code GET /clientepremium/buscarportipopedidoymetodopago}</p>
     *
     * @param tipoPedido tipo de pedido del cliente a buscar.
     * @param metodoPago método de pago del cliente a buscar.
     * @return {@link ResponseEntity} con la lista de {@link ClientePremiumDTO} coincidentes y uno
     *         de los siguientes códigos HTTP:
     *         <ul>
     *           <li>{@code 202 Accepted} — se encontraron resultados.</li>
     *           <li>{@code 204 No Content} — no se encontraron coincidencias.</li>
     *         </ul>
     */
    @GetMapping("/buscarportipopedidoymetodopago")
    public ResponseEntity<List<ClientePremiumDTO>> buscarPorTipoPedidoAndMetodoPago(@RequestParam String tipoPedido,
            @RequestParam String metodoPago) {
        List<ClientePremiumDTO> lista = clientePremiumService.findByTipoPedidoAndMetodoPago(tipoPedido, metodoPago);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }
}
