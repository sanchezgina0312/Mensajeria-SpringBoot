package co.edu.unbosque.mensajeria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import co.edu.unbosque.mensajeria.dto.ManipuladorDePaqueteDTO;
import co.edu.unbosque.mensajeria.entity.ManipuladorDePaquete;
import co.edu.unbosque.mensajeria.repository.ManipuladorDePaqueteRepository;
import co.edu.unbosque.mensajeria.service.ManipuladorDePaqueteService;

/**
 * Clase de pruebas unitarias para {@link ManipuladorDePaqueteService}.
 * 
 * Se encarga de validar las operaciones CRUD y métodos de búsqueda del servicio
 * de manipuladores de paquetes.
 * 
 * Se utiliza Mockito para simular el repositorio y evitar acceso a base de
 * datos real.
 */
class ManipuladorDePaqueteServiceTest {

	/**
	 * Mock del repositorio de ManipuladorDePaquete.
	 */
	@Mock
	private ManipuladorDePaqueteRepository repository;

	/**
	 * Mapper para conversión entre entidad y DTO.
	 */
	private ModelMapper mapper;

	/**
	 * Servicio a probar.
	 */
	private ManipuladorDePaqueteService service;

	/**
	 * DTO de prueba.
	 */
	private ManipuladorDePaqueteDTO dto;

	/**
	 * Entidad de prueba.
	 */
	private ManipuladorDePaquete entity;

	/**
	 * Inicializa mocks, servicio y objetos de prueba antes de cada test.
	 */
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		mapper = new ModelMapper();

		service = new ManipuladorDePaqueteService();
		service.setManipuladorDePaqueteRep(repository);
		service.setMapper(mapper);

		dto = new ManipuladorDePaqueteDTO();
		dto.setNombre("Juan Perez");
		dto.setCedula("12345678");
		dto.setCorreo("juan@gmail.com");
		dto.setTelefono("3001234567");
		dto.setTipoManipulador("CARTAS");

		entity = new ManipuladorDePaquete();
		entity.setNombre(dto.getNombre());
		entity.setCedula(dto.getCedula());
		entity.setCorreo(dto.getCorreo());
		entity.setTelefono(dto.getTelefono());
		entity.setTipoManipulador(dto.getTipoManipulador());
	}

	/**
	 * Verifica creación exitosa cuando no existe duplicado.
	 */
	@Test
	void testCreateSuccess() {
		when(repository.existsByCedula(dto.getCedula())).thenReturn(false);

		int result = service.create(dto);

		assertEquals(0, result);
		verify(repository).save(any(ManipuladorDePaquete.class));
	}

	/**
	 * Verifica que se lance excepción cuando existe duplicado.
	 */
	@Test
	void testCreateDuplicado() {
		when(repository.existsByCedula(dto.getCedula())).thenReturn(true);

		assertThrows(Exception.class, () -> service.create(dto));
	}

	/**
	 * Verifica que se obtenga la lista de manipuladores correctamente.
	 */
	@Test
	void testGetAll() {
		when(repository.findAll()).thenReturn(Arrays.asList(entity));

		List<ManipuladorDePaqueteDTO> result = service.getAll();

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

	/**
	 * Verifica eliminación exitosa por ID.
	 */
	@Test
	void testDeleteByIdSuccess() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		int result = service.deleteById(1L);

		assertEquals(0, result);
		verify(repository).delete(entity);
	}

	/**
	 * Verifica comportamiento cuando el ID no existe al eliminar.
	 */
	@Test
	void testDeleteByIdNotFound() {
		when(repository.findById(1L)).thenReturn(Optional.empty());

		int result = service.deleteById(1L);

		assertEquals(1, result);
	}

	/**
	 * Verifica actualización exitosa de un registro existente.
	 */
	@Test
	void testUpdateSuccess() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.existsByCedula(dto.getCedula())).thenReturn(false);

		int result = service.updateById(1L, dto);

		assertEquals(0, result);
		verify(repository).save(any(ManipuladorDePaquete.class));
	}

	/**
	 * Verifica comportamiento cuando el registro a actualizar no existe.
	 */
	@Test
	void testUpdateNotFound() {
		when(repository.findById(1L)).thenReturn(Optional.empty());

		int result = service.updateById(1L, dto);

		assertEquals(1, result);
	}

	/**
	 * Verifica el conteo de registros.
	 */
	@Test
	void testCount() {
		when(repository.count()).thenReturn(5L);

		long result = service.count();

		assertEquals(5, result);
	}

	/**
	 * Verifica existencia de un registro por ID.
	 */
	@Test
	void testExist() {
		when(repository.existsById(1L)).thenReturn(true);

		boolean result = service.exist(1L);

		assertTrue(result);
	}

	/**
	 * Verifica búsqueda por nombre.
	 */
	@Test
	void testFindByNombre() {
		when(repository.findByNombre("Juan Perez")).thenReturn(Optional.of(Arrays.asList(entity)));

		List<ManipuladorDePaqueteDTO> result = service.findByNombre("Juan Perez");

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

	/**
	 * Verifica búsqueda por cédula.
	 */
	@Test
	void testFindByCedula() {
		when(repository.findByCedula("12345678")).thenReturn(Optional.of(Arrays.asList(entity)));

		List<ManipuladorDePaqueteDTO> result = service.findByCedula("12345678");

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

	/**
	 * Verifica búsqueda por tipo de manipulador.
	 */
	@Test
	void testFindByTipoManipulador() {
		when(repository.findByTipoManipulador("CARTAS")).thenReturn(Optional.of(Arrays.asList(entity)));

		List<ManipuladorDePaqueteDTO> result = service.findByTipoManipulador("CARTAS");

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

	/**
	 * Verifica búsqueda combinada por nombre y cédula.
	 */
	@Test
	void testFindByNombreAndCedula() {
		when(repository.findByNombreAndCedula("Juan Perez", "12345678")).thenReturn(Optional.of(Arrays.asList(entity)));

		List<ManipuladorDePaqueteDTO> result = service.findByNombreAndCedula("Juan Perez", "12345678");

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}
}