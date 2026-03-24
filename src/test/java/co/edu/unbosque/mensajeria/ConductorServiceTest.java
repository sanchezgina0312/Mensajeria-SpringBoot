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

class ConductorServiceTest {

	@Mock
	private ConductorRepository repo;

	private ModelMapper mapper;

	private ConductorService service;

	private ConductorDTO dto;
	private Conductor entity;

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

	@Test
	void testCreateSuccess() {
		when(repo.existsByCedula(dto.getCedula())).thenReturn(false);

		int result = service.create(dto);

		assertEquals(0, result);
		verify(repo).save(any(Conductor.class));
	}

	@Test
	void testCreateDuplicado() {
		when(repo.existsByCedula(dto.getCedula())).thenReturn(true);

		assertThrows(Exception.class, () -> service.create(dto));
	}

	@Test
	void testGetAll() {
		List<Conductor> lista = Arrays.asList(entity);

		when(repo.findAll()).thenReturn(lista);

		List<ConductorDTO> result = service.getAll();

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

	@Test
	void testDeleteSuccess() {
		when(repo.findById(1L)).thenReturn(Optional.of(entity));

		int result = service.deleteById(1L);

		assertEquals(0, result);
		verify(repo).delete(entity);
	}

	@Test
	void testDeleteNotFound() {
		when(repo.findById(1L)).thenReturn(Optional.empty());

		int result = service.deleteById(1L);

		assertEquals(1, result);
	}

	@Test
	void testUpdateSuccess() {
		when(repo.findById(1L)).thenReturn(Optional.of(entity));
		when(repo.existsByCedula(dto.getCedula())).thenReturn(false);

		int result = service.updateById(1L, dto);

		assertEquals(0, result);
		verify(repo).save(any(Conductor.class));
	}

	@Test
	void testUpdateNotFound() {
		when(repo.findById(1L)).thenReturn(Optional.empty());

		int result = service.updateById(1L, dto);

		assertEquals(1, result);
	}

	@Test
	void testCount() {
		when(repo.count()).thenReturn(3L);

		long result = service.count();

		assertEquals(3, result);
	}

	@Test
	void testExist() {
		when(repo.existsById(1L)).thenReturn(true);

		boolean result = service.exist(1L);

		assertTrue(result);
	}

	@Test
	void testFindByNombre() {
		List<Conductor> lista = Arrays.asList(entity);

		when(repo.findByNombre(dto.getNombre())).thenReturn(Optional.of(lista));

		List<ConductorDTO> result = service.findByNombre(dto.getNombre());

		assertFalse(result.isEmpty());
	}

	@Test
	void testFindByCedula() {
		List<Conductor> lista = Arrays.asList(entity);

		when(repo.findByCedula(dto.getCedula())).thenReturn(Optional.of(lista));

		List<ConductorDTO> result = service.findByCedula(dto.getCedula());

		assertFalse(result.isEmpty());
	}

	@Test
	void testFindByCorreo() {
		List<Conductor> lista = Arrays.asList(entity);

		when(repo.findByCorreo(dto.getCorreo())).thenReturn(Optional.of(lista));

		List<ConductorDTO> result = service.findByCorreo(dto.getCorreo());

		assertFalse(result.isEmpty());
	}

	@Test
	void testFindByTelefono() {
		List<Conductor> lista = Arrays.asList(entity);

		when(repo.findByTelefono(dto.getTelefono())).thenReturn(Optional.of(lista));

		List<ConductorDTO> result = service.findByTelefono(dto.getTelefono());

		assertFalse(result.isEmpty());
	}

	@Test
	void testFindByPlacaVehiculo() {
		List<Conductor> lista = Arrays.asList(entity);

		when(repo.findByPlacaVehiculo(dto.getPlacaVehiculo())).thenReturn(Optional.of(lista));

		List<ConductorDTO> result = service.findByPlacaVehiculo(dto.getPlacaVehiculo());

		assertFalse(result.isEmpty());
	}

	@Test
	void testFindByNombreAndCedula() {
		List<Conductor> lista = Arrays.asList(entity);

		when(repo.findByNombreAndCedula(dto.getNombre(), dto.getCedula())).thenReturn(Optional.of(lista));

		List<ConductorDTO> result = service.findByNombreAndCedula(dto.getNombre(), dto.getCedula());

		assertFalse(result.isEmpty());
	}
}