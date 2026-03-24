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
class PaqueteCartaControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    
    @Test
    void crearCarta() throws Exception {

        mockMvc.perform(post("/paquetecarta/crear")
                .param("direccionDestino", "Calle 10")
                .param("tamanio", "Pequeño")
                .param("ciudadDestino", "Bogota")
                .param("tipoCarta", "Certificada"))
                .andExpect(status().is2xxSuccessful());
    }

    
    @Test
    void mostrarTodo() throws Exception {

        mockMvc.perform(get("/paquetecarta/mostrartodo"))
                .andExpect(status().is2xxSuccessful());
    }

    
    @Test
    void eliminarCarta() throws Exception {

      
        mockMvc.perform(post("/paquetecarta/crear")
                .param("direccionDestino", "Calle 10")
                .param("tamanio", "Pequeño")
                .param("ciudadDestino", "Bogota")
                .param("tipoCarta", "Certificada"))
                .andExpect(status().is2xxSuccessful());

       
        mockMvc.perform(delete("/paquetecarta/eliminar")
                .param("id", "1"))
                .andExpect(status().is2xxSuccessful());
    }

  
    @Test
    void actualizarCarta() throws Exception {

       
        mockMvc.perform(post("/paquetecarta/crear")
                .param("direccionDestino", "Calle 10")
                .param("tamanio", "Pequeño")
                .param("ciudadDestino", "Bogota")
                .param("tipoCarta", "Certificada"))
                .andExpect(status().is2xxSuccessful());

       
        mockMvc.perform(put("/paquetecarta/actualizar")
                .param("id", "1")
                .param("precioEnvio", "1500")
                .param("direccionDestino", "Carrera 15")
                .param("tamanio", "Pequeño")
                .param("fechaCreacionPedido", "2024-05-20T10:00:00")
                .param("fechaEstimadaEntrega", "2024-05-21T10:00:00")
                .param("tipoCarta", "Normal"))
                .andExpect(status().is2xxSuccessful());
    }

    
    @Test
    void buscarPorTamanio() throws Exception {

        mockMvc.perform(get("/paquetecarta/buscarportamanio")
                .param("tamanio", "Pequeño"))
                .andExpect(status().is2xxSuccessful());
    }

    
    @Test
    void buscarPorTipoCarta() throws Exception {

        mockMvc.perform(get("/paquetecarta/buscarportipocarta")
                .param("tipoCarta", "Certificada"))
                .andExpect(status().is2xxSuccessful());
    }

   
    @Test
    void buscarPorTamanioYTipoCarta() throws Exception {

        mockMvc.perform(get("/paquetecarta/buscarportamanioytipocarta")
                .param("tamanio", "Pequeño")
                .param("tipoCarta", "Documento"))
                .andExpect(status().is2xxSuccessful());
    }

   
    @Test
    void seguimientoId() throws Exception {

        mockMvc.perform(get("/paquetecarta/seguimientoid")
                .param("id", "1"))
                .andExpect(status().is2xxSuccessful());
    }

    
    @Test
    void buscarDireccionCiudad() throws Exception {

        mockMvc.perform(get("/paquetecarta/buscardireccionyciudad")
                .param("dir", "Calle 123")
                .param("ciudad", "Bogota"))
                .andExpect(status().is2xxSuccessful());
    }
}