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
class PaqueteNoAlimenticioControllerTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	
	@Test
	void crear() throws Exception {

		mockMvc.perform(post("/paquetenoalimenticio/crear").param("direccionDestino", "Calle 20")
				.param("tamanio", "Grande").param("ciudadDestino", "Cali").param("esFragil", "true"))
				.andExpect(status().is2xxSuccessful());
	}

	
	@Test
	void mostrarTodo() throws Exception {

		mockMvc.perform(get("/paquetenoalimenticio/mostrartodo")).andExpect(status().is2xxSuccessful());
	}

	
	@Test
	void eliminar() throws Exception {

		// Crear primero
		mockMvc.perform(post("/paquetenoalimenticio/crear").param("direccionDestino", "Calle 20")
				.param("tamanio", "Grande").param("ciudadDestino", "Cali").param("esFragil", "true"))
				.andExpect(status().is2xxSuccessful());

		
		mockMvc.perform(delete("/paquetenoalimenticio/eliminar").param("id", "1"))
				.andExpect(status().is2xxSuccessful());
	}


	@Test
	void actualizar() throws Exception {

		
		mockMvc.perform(post("/paquetenoalimenticio/crear").param("direccionDestino", "Calle 20")
				.param("tamanio", "Grande").param("ciudadDestino", "Cali").param("esFragil", "true"))
				.andExpect(status().is2xxSuccessful());

		
		mockMvc.perform(put("/paquetenoalimenticio/actualizar").param("id", "1").param("esFragil", "false")
				.param("precioEnvio", "2000").param("direccionDestino", "Carrera 50").param("tamanio", "Mediano")
				.param("fechaCreacionPedido", "2024-05-20T10:00:00")
				.param("fechaEstimadaEntrega", "2024-05-21T10:00:00")).andExpect(status().is2xxSuccessful());
	}

	
	@Test
	void buscarPorTamanio() throws Exception {

		mockMvc.perform(get("/paquetenoalimenticio/buscarportamanio").param("tamanio", "Grande"))
				.andExpect(status().is2xxSuccessful());
	}

	
	@Test
	void buscarPorEsFragil() throws Exception {

		mockMvc.perform(get("/paquetenoalimenticio/buscarporesfragil").param("esFragil", "true"))
				.andExpect(status().is2xxSuccessful());
	}

	
	@Test
	void buscarPorTamanioYFragil() throws Exception {

		mockMvc.perform(get("/paquetenoalimenticio/buscarportamanioyfragil").param("tamanio", "Grande")
				.param("esFragil", "true")).andExpect(status().is2xxSuccessful());
	}

	
	@Test
	void seguimientoId() throws Exception {

		mockMvc.perform(get("/paquetenoalimenticio/seguimientoid").param("id", "1"))
				.andExpect(status().is2xxSuccessful());
	}

	
	@Test
	void buscarDireccionCiudad() throws Exception {

		mockMvc.perform(
				get("/paquetenoalimenticio/buscardireccionyciudad").param("dir", "Calle 20").param("ciudad", "Cali"))
				.andExpect(status().is2xxSuccessful());
	}
}