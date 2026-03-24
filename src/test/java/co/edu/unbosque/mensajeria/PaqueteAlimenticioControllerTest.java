
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
class PaqueteAlimenticioControllerTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	void crearPaquete() throws Exception {

		mockMvc.perform(post("/paquetealimenticio/crear").param("direccionDestino", "Calle 10")
				.param("tamanio", "Grande").param("ciudadDestino", "Bogota").param("seEnviaHoy", "true")
				.param("tipoDeAlimento", "Perecedero")).andExpect(status().is2xxSuccessful());
	}

	@Test
	void mostrarTodo() throws Exception {

		mockMvc.perform(get("/paquetealimenticio/mostrartodo")).andExpect(status().is2xxSuccessful());
	}

	@Test
	void eliminarPaquete() throws Exception {

		mockMvc.perform(post("/paquetealimenticio/crear").param("direccionDestino", "Calle 10")
				.param("tamanio", "Grande").param("ciudadDestino", "Bogota").param("seEnviaHoy", "true")
				.param("tipoDeAlimento", "Perecedero")).andExpect(status().is2xxSuccessful());

		mockMvc.perform(delete("/paquetealimenticio/eliminar").param("id", "1")).andExpect(status().is2xxSuccessful());
	}

	@Test
	void actualizarPaquete() throws Exception {

		mockMvc.perform(post("/paquetealimenticio/crear").param("direccionDestino", "Calle 10")
				.param("tamanio", "Grande").param("ciudadDestino", "Bogota").param("seEnviaHoy", "true")
				.param("tipoDeAlimento", "Perecedero")).andExpect(status().is2xxSuccessful());

		mockMvc.perform(put("/paquetealimenticio/actualizar").param("id", "1").param("seEnviaHoy", "false")
				.param("tipoDeAlimento", "No Perecedero").param("precioEnvio", "2000")
				.param("direccionDestino", "Carrera 15").param("tamanio", "Mediano")
				.param("fechaCreacionPedido", "2024-05-20T10:00:00")
				.param("fechaEstimadaEntrega", "2024-05-21T10:00:00")).andExpect(status().is2xxSuccessful());
	}

	@Test
	void buscarPorTamanio() throws Exception {

		mockMvc.perform(get("/paquetealimenticio/buscartamanio").param("tamanio", "Grande"))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void buscarPorSeEnviaHoy() throws Exception {

		mockMvc.perform(get("/paquetealimenticio/buscarporenviahoy").param("seEnviaHoy", "true"))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void buscarPorTipoAlimento() throws Exception {

		mockMvc.perform(get("/paquetealimenticio/buscarportipoalimento").param("tipoDeAlimento", "Perecedero"))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void buscarPorTamanioYTipo() throws Exception {

		mockMvc.perform(get("/paquetealimenticio/buscarportamanioytipo").param("tamanio", "Grande")
				.param("tipoDeAlimento", "Perecedero")).andExpect(status().is2xxSuccessful());
	}

	@Test
	void seguimientoId() throws Exception {

		mockMvc.perform(get("/paquetealimenticio/seguimientoid").param("id", "1"))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void buscarDireccionCiudad() throws Exception {

		mockMvc.perform(
				get("/paquetealimenticio/buscardireccionyciudad").param("dir", "Calle 10").param("ciudad", "Bogota"))
				.andExpect(status().is2xxSuccessful());
	}
}
