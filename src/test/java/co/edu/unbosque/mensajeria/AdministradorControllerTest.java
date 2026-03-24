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

import org.springframework.transaction.annotation.Transactional;

@SpringBootTest 
@Transactional
class AdministradorControllerTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	void crearAdministrador() throws Exception {

		String json = """
		{
		    "nombre": "Admin Uno",
		    "cedula": "123456",
		    "correo": "admin@mail.com",
		    "telefono": "3001234567",
		    "turno": "N",
		    "usuario": "admin",
		    "contrasenia": "1234"
		}
		""";

		mockMvc.perform(post("/administrador/crear")
		        .contentType("application/json")
		        .content(json))
		        .andExpect(status().isOk());
	}
	
	
	@Test
	void mostrarTodo() throws Exception {

		mockMvc.perform(get("/administrador/mostrartodo")).andExpect(status().is2xxSuccessful());
	}

	void actualizarAdministrador() throws Exception {

		
		mockMvc.perform(post("/administrador/crear").param("nombre", "Admin Uno").param("cedula", "123456")
				.param("correo", "admin@mail.com").param("telefono", "3001234567").param("turno", "N")
				.param("usuario", "admin").param("contrasenia", "1234")).andExpect(status().isCreated());

	
		mockMvc.perform(put("/administrador/actualizar").param("id", "1").param("nombre", "Admin Dos")
				.param("cedula", "123456").param("correo", "nuevo@mail.com").param("telefono", "3001234567")
				.param("turno", "T").param("usuario", "admin").param("contrasenia", "1234"))
				.andExpect(status().isAccepted());
	}

	@Test
	void eliminarAdministrador() throws Exception {

		String json = """
		{
		    "nombre": "Admin Uno",
		    "cedula": "123456",
		    "correo": "admin@mail.com",
		    "telefono": "3001234567",
		    "turno": "N",
		    "usuario": "admin",
		    "contrasenia": "1234"
		}
		""";

		
		mockMvc.perform(post("/administrador/crear")
		        .contentType("application/json")
		        .content(json))
		        .andExpect(status().isOk());

		
		mockMvc.perform(delete("/administrador/eliminar")
		        .param("id", "1"))
		        .andExpect(status().isOk());
	}

	@Test
	void buscarPorNombre() throws Exception {

		mockMvc.perform(get("/administrador/buscarpornombre").param("nombre", "Admin Uno"))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void buscarPorCedula() throws Exception {

		mockMvc.perform(get("/administrador/buscarporcedula").param("cedula", "123456"))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void buscarPorCorreo() throws Exception {

		mockMvc.perform(get("/administrador/buscarporcorreo").param("correo", "admin@mail.com"))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void buscarPorTelefono() throws Exception {

		mockMvc.perform(get("/administrador/buscarportelefono").param("telefono", "3001234567"))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void buscarPorUsuario() throws Exception {

		mockMvc.perform(get("/administrador/buscarporusuario").param("usuario", "admin"))
				.andExpect(status().is2xxSuccessful());
	}

}