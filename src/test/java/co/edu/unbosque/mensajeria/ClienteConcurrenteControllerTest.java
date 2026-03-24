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

@SpringBootTest
class ClienteConcurrenteControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void crearClienteConcurrente() throws Exception {
        // Usamos una cédula basada en el tiempo para que nunca se repita y no de error 409
        String cedulaUnica = String.valueOf(System.currentTimeMillis()).substring(5);

        mockMvc.perform(post("/clienteconcurrente/crear")
                .param("nombre", "Mariana")
                .param("cedula", cedulaUnica)
                .param("correo", "mariana@mail.com")
                .param("telefono", "322000")
                .param("metodoPago", "Efectivo")
                .param("tipoPedido", "Alimenticio")
                .param("tarifaConcurrente", "5000.0"))
                .andExpect(status().isCreated()); // Espera 201
    }

    @Test
    void mostrarTodo() throws Exception {
        mockMvc.perform(get("/clienteconcurrente/mostrartodo"))
                .andExpect(status().is2xxSuccessful()); 
    }

    @Test
    void actualizarClienteConcurrente() throws Exception {
        // IMPORTANTE: En tu Controller la ruta es /actualizarclienteconcurrente
        mockMvc.perform(put("/clienteconcurrente/actualizarclienteconcurrente")
                .param("id", "1")
                .param("nombre", "Mariana Lopez")
                .param("cedula", "7890")
                .param("correo", "marianal@mail.com")
                .param("telefono", "322999")
                .param("metodoPago", "Tarjeta")
                .param("tipoPedido", "No Alimenticio")
                .param("tarifaConcurrente", "6000.0"))
                .andExpect(status().isAccepted()); // Tu controller devuelve ACCEPTED (202)
    }

    @Test
    void eliminarClienteConcurrente() throws Exception {
        // Primero creamos uno para asegurar que el ID 1 exista en la base de datos H2
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
                .andExpect(status().isAccepted()); // Tu controller devuelve ACCEPTED
    }

    @Test
    void buscarPorNombre() throws Exception {
        mockMvc.perform(get("/clienteconcurrente/buscarpornombre")
                .param("nombre", "Mariana"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void buscarPorMetodoPago() throws Exception {
        mockMvc.perform(get("/clienteconcurrente/buscarpormetodopago")
                .param("metodoPago", "Efectivo"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void buscarPorTipoPedido() throws Exception {
        mockMvc.perform(get("/clienteconcurrente/buscarportipopedido")
                .param("tipoPedido", "Alimenticio"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void buscarPorNombreAndCedula() throws Exception {
        mockMvc.perform(get("/clienteconcurrente/buscarpornombreycedula")
                .param("nombre", "Mariana")
                .param("cedula", "7890"))
                .andExpect(status().is2xxSuccessful());
    }
}