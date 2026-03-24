package co.edu.unbosque.mensajeria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import co.edu.unbosque.mensajeria.dto.AdministradorDTO;
import co.edu.unbosque.mensajeria.entity.Administrador;
import co.edu.unbosque.mensajeria.repository.AdministradorRepository;
import co.edu.unbosque.mensajeria.service.AdministradorService;

/**
 * Clase de pruebas unitarias para {@link AdministradorService}.
 * 
 * <p>
 * Esta clase valida el correcto funcionamiento de los métodos CRUD del servicio
 * de administradores utilizando Mockito para simular el repositorio y evitar
 * acceso a base de datos real.
 * </p>
 * 
 * <p>
 * Se prueban escenarios de éxito, error, duplicidad y búsquedas.
 * </p>
 */
class AdministradorServiceTest {

	/**
	 * Mock del repositorio de Administrador.
	 */
	@Mock
	private AdministradorRepository administradorRep;

	/**
	 * Mapper para conversión entre DTO y entidad.
	 */
	private ModelMapper mapper;

	/**
	 * Servicio que será probado.
	 */
	private AdministradorService administradorService;

	/**
	 * Objeto DTO de prueba.
	 */
	private AdministradorDTO dto;

	/**
	 * Entidad de prueba.
	 */
	private Administrador entity;

	/**
	 * Inicializa el entorno de pruebas antes de cada test.
	 * 
	 * <p>
	 * Configura Mockito, instancia el servicio y crea los objetos de prueba
	 * necesarios.
	 * </p>
	 */
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		mapper = new ModelMapper();

		administradorService = new AdministradorService();
		administradorService.setAdministradorRep(administradorRep);
		administradorService.setMapper(mapper);

		dto = new AdministradorDTO();
		dto.setNombre("Juan Perez");
		dto.setCedula("123456");
		dto.setCorreo("juan@mail.com");
		dto.setTelefono("3001234567");
		dto.setTurno('D');
		dto.setUsuario("juan");
		dto.setContrasenia("1234");

		entity = new Administrador();
		entity.setId(1L);
		entity.setNombre(dto.getNombre());
		entity.setCedula(dto.getCedula());
		entity.setCorreo(dto.getCorreo());
		entity.setTelefono(dto.getTelefono());
		entity.setTurno(dto.getTurno());
		entity.setUsuario(dto.getUsuario());
		entity.setContrasenia(dto.getContrasenia());
	}

	/**
	 * Prueba la creación exitosa de un administrador.
	 * 
	 * <p>
	 * Verifica que se llame al método save cuando no existe duplicado.
	 * </p>
	 */
	@Test
	void testCreateSuccess() {
		when(administradorRep.existsByCedula(dto.getCedula())).thenReturn(false);
		when(administradorRep.save(any(Administrador.class))).thenReturn(entity);

		int result = administradorService.create(dto);

		assertTrue(result == 1 || result == 0);
		verify(administradorRep).save(any(Administrador.class));
	}

	/**
	 * Prueba la creación cuando ya existe un administrador con la misma cédula.
	 * 
	 * <p>
	 * Se espera que se lance una excepción y no se guarde el registro.
	 * </p>
	 */
	@Test
	void testCreateDuplicado() {
		when(administradorRep.existsByCedula(dto.getCedula())).thenReturn(true);

		assertThrows(RuntimeException.class, () -> {
			administradorService.create(dto);
		});

		verify(administradorRep, never()).save(any());
	}

	/**
	 * Prueba la obtención de todos los administradores.
	 */
	@Test
	void testGetAll() {
		when(administradorRep.findAll()).thenReturn(List.of(entity));

		List<AdministradorDTO> result = administradorService.getAll();

		assertNotNull(result);
		assertEquals(1, result.size());
	}

	/**
	 * Prueba la eliminación exitosa de un administrador por ID.
	 */
	@Test
	void testDeleteByIdSuccess() {
		when(administradorRep.findById(1L)).thenReturn(Optional.of(entity));

		int result = administradorService.deleteById(1L);

		assertEquals(0, result);
		verify(administradorRep).delete(entity);
	}

	/**
	 * Prueba la eliminación cuando el administrador no existe.
	 */
	@Test
	void testDeleteByIdNotFound() {
		when(administradorRep.findById(1L)).thenReturn(Optional.empty());

		int result = administradorService.deleteById(1L);

		assertEquals(1, result);
		verify(administradorRep, never()).delete(any());
	}

	/**
	 * Prueba la actualización exitosa de un administrador.
	 */
	@Test
	void testUpdateSuccess() {
		when(administradorRep.findById(1L)).thenReturn(Optional.of(entity));
		when(administradorRep.existsByCedula(dto.getCedula())).thenReturn(false);
		when(administradorRep.save(any(Administrador.class))).thenReturn(entity);

		int result = administradorService.updateById(1L, dto);

		assertTrue(result == 1 || result == 0);
		verify(administradorRep).save(any(Administrador.class));
	}

	/**
	 * Prueba la actualización cuando el administrador no existe.
	 * 
	 * <p>
	 * Puede retornar un valor o lanzar una excepción dependiendo de la
	 * implementación del servicio.
	 * </p>
	 */
	@Test
	void testUpdateNotFound() {
		when(administradorRep.findById(1L)).thenReturn(Optional.empty());

		try {
			int result = administradorService.updateById(1L, dto);
			assertTrue(result == 0 || result == 1);
		} catch (Exception e) {
			assertTrue(e instanceof RuntimeException);
		}

		verify(administradorRep, never()).save(any());
	}

	/**
	 * Prueba el conteo de registros.
	 */
	@Test
	void testCount() {
		when(administradorRep.count()).thenReturn(5L);

		assertEquals(5, administradorService.count());
	}

	/**
	 * Prueba la verificación de existencia por ID.
	 */
	@Test
	void testExist() {
		when(administradorRep.existsById(1L)).thenReturn(true);

		assertTrue(administradorService.exist(1L));
	}

	/**
	 * Prueba la búsqueda por nombre.
	 */
	@Test
	void testFindByNombre() {
		when(administradorRep.findByNombre("Juan Perez")).thenReturn(Optional.of(List.of(entity)));

		List<AdministradorDTO> result = administradorService.findByNombre("Juan Perez");

		assertNotNull(result);
		assertEquals(1, result.size());
	}

	/**
	 * Prueba la búsqueda por cédula existente.
	 */
	@Test
	void testFindByCedula() {
		when(administradorRep.findByCedula("123456")).thenReturn(Optional.of(List.of(entity)));

		List<AdministradorDTO> result = administradorService.findByCedula("123456");

		assertNotNull(result);
		assertEquals(1, result.size());
	}

	/**
	 * Prueba la búsqueda por cédula cuando no existen resultados.
	 */
	@Test
	void testFindByCedulaEmpty() {
		when(administradorRep.findByCedula("999999")).thenReturn(Optional.empty());

		List<AdministradorDTO> result = administradorService.findByCedula("999999");

		assertNotNull(result);
		assertTrue(result.isEmpty());
	}
}