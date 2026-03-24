package co.edu.unbosque.mensajeria;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Clase de pruebas para el controlador ClienteConcurrenteController.
 * 
 * Esta clase valida el correcto funcionamiento de los endpoints REST
 * relacionados con la gestión de clientes concurrentes, incluyendo
 * operaciones CRUD y consultas específicas.
 */
@SpringBootTest
class ClienteConcurrenteControllerTest {

	/**
	 * Contexto web de la aplicación cargado por Spring Boot.
	 * 
	 * Permite acceder a todos los beans configurados en la aplicación
	 * durante la ejecución de las pruebas de integración.
	 */
    @Autowired
    private WebApplicationContext context;

    /**
     * Objeto MockMvc utilizado para simular peticiones HTTP
     * hacia los endpoints del controlador sin necesidad de
     * desplegar un servidor real.
     * 
     * Permite validar respuestas, estados HTTP y comportamiento
     * de los controladores REST.
     */
    private MockMvc mockMvc;

    /**
     * Configura el entorno de pruebas antes de cada test,
     * inicializando MockMvc con el contexto de la aplicación.
     */
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    /**
     * Verifica la creación de un cliente concurrente.
     * 
     * Se genera una cédula única para evitar conflictos
     * y se espera un estado HTTP 201 (Created).
     * 
     * @throws Exception si ocurre un error en la petición
     */
    @Test
    void crearClienteConcurrente() throws Exception {
        
        String cedulaUnica = String.valueOf(System.currentTimeMillis()).substring(5);

        mockMvc.perform(post("/clienteconcurrente/crear")
                .param("nombre", "Mariana")
                .param("cedula", cedulaUnica)
                .param("correo", "mariana@mail.com")
                .param("telefono", "322000")
                .param("metodoPago", "Efectivo")
                .param("tipoPedido", "Alimenticio")
                .param("tarifaConcurrente", "5000.0"))
                .andExpect(status().isCreated()); 
    }

    /**
     * Verifica que el endpoint de consulta de todos los clientes
     * retorne una respuesta exitosa.
     * 
     * @throws Exception si ocurre un error en la petición
     */
    @Test
    void mostrarTodo() throws Exception {
        mockMvc.perform(get("/clienteconcurrente/mostrartodo"))
                .andExpect(status().is2xxSuccessful()); 
    }

    /**
     * Verifica la actualización de un cliente concurrente existente.
     * 
     * Se espera un estado HTTP 202 (Accepted).
     * 
     * @throws Exception si ocurre un error en la petición
     */
    @Test
    void actualizarClienteConcurrente() throws Exception {
        
        mockMvc.perform(put("/clienteconcurrente/actualizarclienteconcurrente")
                .param("id", "1")
                .param("nombre", "Mariana Lopez")
                .param("cedula", "7890")
                .param("correo", "marianal@mail.com")
                .param("telefono", "322999")
                .param("metodoPago", "Tarjeta")
                .param("tipoPedido", "No Alimenticio")
                .param("tarifaConcurrente", "6000.0"))
                .andExpect(status().isAccepted()); 
    }

    /**
     * Verifica la eliminación de un cliente concurrente.
     * 
     * Primero se crea un cliente de prueba y luego se elimina
     * utilizando su ID. Se espera un estado HTTP 202 (Accepted).
     * 
     * @throws Exception si ocurre un error en la petición
     */
    @Test
    void eliminarClienteConcurrente() throws Exception {
        
        mockMvc.perform(post("/clienteconcurrente/crear")
                .param("nombre", "Test")
                .param("cedula", "111222")
                .param("correo", "test@mail.com")
                .param("telefono", "123")
                .param("metodoPago", "Efectivo")
                .param("tipoPedido", "Alimenticio")
                .param("tarifaConcurrente", "1000.0"));

        mockMvc.perform(delete("/clienteconcurrente/eliminar")
                .param("id", "1"))
                .andExpect(status().isAccepted()); 
    }

    /**
     * Verifica la búsqueda de clientes por nombre.
     * 
     * @throws Exception si ocurre un error en la petición
     */
    @Test
    void buscarPorNombre() throws Exception {
        mockMvc.perform(get("/clienteconcurrente/buscarpornombre")
                .param("nombre", "Mariana"))
                .andExpect(status().is2xxSuccessful());
    }

    /**
     * Verifica la búsqueda de clientes por método de pago.
     * 
     * @throws Exception si ocurre un error en la petición
     */
    @Test
    void buscarPorMetodoPago() throws Exception {
        mockMvc.perform(get("/clienteconcurrente/buscarpormetodopago")
                .param("metodoPago", "Efectivo"))
                .andExpect(status().is2xxSuccessful());
    }

    /**
     * Verifica la búsqueda de clientes por tipo de pedido.
     * 
     * @throws Exception si ocurre un error en la petición
     */
    @Test
    void buscarPorTipoPedido() throws Exception {
        mockMvc.perform(get("/clienteconcurrente/buscarportipopedido")
                .param("tipoPedido", "Alimenticio"))
                .andExpect(status().is2xxSuccessful());
    }

    /**
     * Verifica la búsqueda de clientes por nombre y cédula.
     * 
     * @throws Exception si ocurre un error en la petición
     */
    @Test
    void buscarPorNombreAndCedula() throws Exception {
        mockMvc.perform(get("/clienteconcurrente/buscarpornombreycedula")
                .param("nombre", "Mariana")
                .param("cedula", "7890"))
                .andExpect(status().is2xxSuccessful());
    }
}