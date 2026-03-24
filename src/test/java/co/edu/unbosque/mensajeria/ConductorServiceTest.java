package co.edu.unbosque.mensajeria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import co.edu.unbosque.mensajeria.dto.ConductorDTO;
import co.edu.unbosque.mensajeria.entity.Conductor;
import co.edu.unbosque.mensajeria.repository.ConductorRepository;
import co.edu.unbosque.mensajeria.service.ConductorService;

/**
 * Clase de pruebas unitarias para el servicio ConductorService.
 * 
 * Esta clase valida el correcto funcionamiento de las operaciones CRUD y de
 * búsqueda relacionadas con la entidad Conductor.
 * 
 * Se utiliza Mockito para simular el comportamiento del repositorio y
 * ModelMapper para la conversión entre entidades y DTOs.
 */
class ConductorServiceTest {

	/**
	 * Mock del repositorio de Conductores. Permite simular las operaciones de
	 * acceso a datos.
	 */
	@Mock
	private ConductorRepository repo;

	/**
	 * Mapper real utilizado para convertir entre entidad y DTO.
	 */
	private ModelMapper mapper;

	/**
	 * Servicio que será probado.
	 */
	private ConductorService service;

	/**
	 * Objeto DTO de prueba.
	 */
	private ConductorDTO dto;

	/**
	 * Entidad de prueba.
	 */
	private Conductor entity;

	/**
	 * Inicializa los mocks, el mapper real, el servicio y los datos de prueba antes
	 * de cada test.
	 */
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		mapper = new ModelMapper();

		service = new ConductorService();
		service.setConductorRep(repo);
		service.setMapper(mapper);

		dto = new ConductorDTO();
		dto.setCedula("123456789");
		dto.setNombre("Carlos Perez");
		dto.setCorreo("carlos@gmail.com");
		dto.setTelefono("3123456789");
		dto.setPlacaVehiculo("ABC123");

		entity = new Conductor();
		entity.setCedula(dto.getCedula());
		entity.setNombre(dto.getNombre());
		entity.setCorreo(dto.getCorreo());
		entity.setTelefono(dto.getTelefono());
		entity.setPlacaVehiculo(dto.getPlacaVehiculo());
	}

	/**
	 * Verifica la creación exitosa de un conductor.
	 * 
	 * Se espera que la cédula no exista previamente y que el método retorne 0
	 * indicando éxito.
	 */
	@Test
	void testCreateSuccess() {
		when(repo.existsByCedula(dto.getCedula())).thenReturn(false);

		int result = service.create(dto);

		assertEquals(0, result);
		verify(repo).save(any(Conductor.class));
	}

	/**
	 * Verifica que no se permita crear un conductor duplicado.
	 * 
	 * Se espera que se lance una excepción cuando la cédula ya existe.
	 */
	@Test
	void testCreateDuplicado() {
		when(repo.existsByCedula(dto.getCedula())).thenReturn(true);

		assertThrows(Exception.class, () -> service.create(dto));
	}

	/**
	 * Verifica la obtención de todos los conductores.
	 * 
	 * Se espera una lista no vacía con el tamaño correcto.
	 */
	@Test
	void testGetAll() {
		List<Conductor> lista = Arrays.asList(entity);

		when(repo.findAll()).thenReturn(lista);

		List<ConductorDTO> result = service.getAll();

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

	/**
	 * Verifica la eliminación exitosa de un conductor existente.
	 */
	@Test
	void testDeleteSuccess() {
		when(repo.findById(1L)).thenReturn(Optional.of(entity));

		int result = service.deleteById(1L);

		assertEquals(0, result);
		verify(repo).delete(entity);
	}

	/**
	 * Verifica el comportamiento cuando se intenta eliminar un conductor que no
	 * existe.
	 */
	@Test
	void testDeleteNotFound() {
		when(repo.findById(1L)).thenReturn(Optional.empty());

		int result = service.deleteById(1L);

		assertEquals(1, result);
	}

	/**
	 * Verifica la actualización exitosa de un conductor.
	 */
	@Test
	void testUpdateSuccess() {
		when(repo.findById(1L)).thenReturn(Optional.of(entity));
		when(repo.existsByCedula(dto.getCedula())).thenReturn(false);

		int result = service.updateById(1L, dto);

		assertEquals(0, result);
		verify(repo).save(any(Conductor.class));
	}

	/**
	 * Verifica el comportamiento cuando se intenta actualizar un conductor
	 * inexistente.
	 */
	@Test
	void testUpdateNotFound() {
		when(repo.findById(1L)).thenReturn(Optional.empty());

		int result = service.updateById(1L, dto);

		assertEquals(1, result);
	}

	/**
	 * Verifica el conteo total de conductores.
	 */
	@Test
	void testCount() {
		when(repo.count()).thenReturn(3L);

		long result = service.count();

		assertEquals(3, result);
	}

	/**
	 * Verifica si un conductor existe por su ID.
	 */
	@Test
	void testExist() {
		when(repo.existsById(1L)).thenReturn(true);

		boolean result = service.exist(1L);

		assertTrue(result);
	}

	/**
	 * Verifica la búsqueda de conductores por nombre.
	 */
	@Test
	void testFindByNombre() {
		List<Conductor> lista = Arrays.asList(entity);

		when(repo.findByNombre(dto.getNombre())).thenReturn(Optional.of(lista));

		List<ConductorDTO> result = service.findByNombre(dto.getNombre());

		assertFalse(result.isEmpty());
	}

	/**
	 * Verifica la búsqueda de conductores por cédula.
	 */
	@Test
	void testFindByCedula() {
		List<Conductor> lista = Arrays.asList(entity);

		when(repo.findByCedula(dto.getCedula())).thenReturn(Optional.of(lista));

		List<ConductorDTO> result = service.findByCedula(dto.getCedula());

		assertFalse(result.isEmpty());
	}

	/**
	 * Verifica la búsqueda de conductores por correo.
	 */
	@Test
	void testFindByCorreo() {
		List<Conductor> lista = Arrays.asList(entity);

		when(repo.findByCorreo(dto.getCorreo())).thenReturn(Optional.of(lista));

		List<ConductorDTO> result = service.findByCorreo(dto.getCorreo());

		assertFalse(result.isEmpty());
	}

	/**
	 * Verifica la búsqueda de conductores por teléfono.
	 */
	@Test
	void testFindByTelefono() {
		List<Conductor> lista = Arrays.asList(entity);

		when(repo.findByTelefono(dto.getTelefono())).thenReturn(Optional.of(lista));

		List<ConductorDTO> result = service.findByTelefono(dto.getTelefono());

		assertFalse(result.isEmpty());
	}

	/**
	 * Verifica la búsqueda de conductores por placa del vehículo.
	 */
	@Test
	void testFindByPlacaVehiculo() {
		List<Conductor> lista = Arrays.asList(entity);

		when(repo.findByPlacaVehiculo(dto.getPlacaVehiculo())).thenReturn(Optional.of(lista));

		List<ConductorDTO> result = service.findByPlacaVehiculo(dto.getPlacaVehiculo());

		assertFalse(result.isEmpty());
	}

	/**
	 * Verifica la búsqueda combinada por nombre y cédula.
	 */
	@Test
	void testFindByNombreAndCedula() {
		List<Conductor> lista = Arrays.asList(entity);

		when(repo.findByNombreAndCedula(dto.getNombre(), dto.getCedula())).thenReturn(Optional.of(lista));

		List<ConductorDTO> result = service.findByNombreAndCedula(dto.getNombre(), dto.getCedula());

		assertFalse(result.isEmpty());
	}
}