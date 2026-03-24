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

        mockMvc.perform(post("/clienteconcurrente/crear")
                .param("nombre", "Mariana")
                .param("cedula", "7890")
                .param("correo", "mariana@mail.com")
                .param("telefono", "322000")
                .param("metodoPago", "Efectivo")
                .param("tipoPedido", "Alimenticio")
                .param("tarifaConcurrente", "10.5"))
                .andExpect(status().is2xxSuccessful());
    }

    
    @Test
    void mostrarTodo() throws Exception {

        mockMvc.perform(get("/clienteconcurrente/mostrartodo"))
                .andExpect(status().is2xxSuccessful());
    }

    
    @Test
    void eliminarClienteConcurrente() throws Exception {

        
        mockMvc.perform(post("/clienteconcurrente/crear")
                .param("nombre", "Mariana")
                .param("cedula", "7890")
                .param("correo", "mariana@mail.com")
                .param("telefono", "322000")
                .param("metodoPago", "Efectivo")
                .param("tipoPedido", "Alimenticio")
                .param("tarifaConcurrente", "10.5"))
                .andExpect(status().is2xxSuccessful());

        
        mockMvc.perform(delete("/clienteconcurrente/eliminar")
                .param("id", "1"))
                .andExpect(status().is2xxSuccessful());
    }

  
    @Test
    void actualizarClienteConcurrente() throws Exception {

        
        mockMvc.perform(post("/clienteconcurrente/crear")
                .param("nombre", "Mariana")
                .param("cedula", "7890")
                .param("correo", "mariana@mail.com")
                .param("telefono", "322000")
                .param("metodoPago", "Efectivo")
                .param("tipoPedido", "Alimenticio")
                .param("tarifaConcurrente", "10.5"))
                .andExpect(status().is2xxSuccessful());

        
        mockMvc.perform(put("/clienteconcurrente/actualizarclienteconcurrente")
                .param("id", "1")
                .param("nombre", "Mariana Lopez")
                .param("cedula", "7890")
                .param("correo", "marianal@mail.com")
                .param("telefono", "322999")
                .param("metodoPago", "Tarjeta")
                .param("tipoPedido", "No Alimenticio")
                .param("tarifaConcurrente", "15.0"))
                .andExpect(status().is2xxSuccessful());
    }

    
    @Test
    void buscarPorNombre() throws Exception {

        mockMvc.perform(get("/clienteconcurrente/buscarpornombre")
                .param("nombre", "Mariana"))
                .andExpect(status().is2xxSuccessful());
    }

    
    @Test
    void buscarPorCedula() throws Exception {

        mockMvc.perform(get("/clienteconcurrente/buscarporcedula")
                .param("cedula", "7890"))
                .andExpect(status().is2xxSuccessful());
    }

    
    @Test
    void buscarPorCorreo() throws Exception {

        mockMvc.perform(get("/clienteconcurrente/buscarporcorreo")
                .param("correo", "mariana@mail.com"))
                .andExpect(status().is2xxSuccessful());
    }

   
    @Test
    void buscarPorTelefono() throws Exception {

        mockMvc.perform(get("/clienteconcurrente/buscarportelefono")
                .param("telefono", "322000"))
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
}