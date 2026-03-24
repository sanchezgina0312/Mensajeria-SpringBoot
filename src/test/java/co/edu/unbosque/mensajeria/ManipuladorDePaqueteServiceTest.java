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

class ManipuladorDePaqueteServiceTest {

	@Mock
	private ManipuladorDePaqueteRepository repository;

	private ModelMapper mapper;

	private ManipuladorDePaqueteService service;

	private ManipuladorDePaqueteDTO dto;
	private ManipuladorDePaquete entity;

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

	@Test
	void testCreateSuccess() {
		when(repository.existsByCedula(dto.getCedula())).thenReturn(false);

		int result = service.create(dto);

		assertEquals(0, result);
		verify(repository).save(any(ManipuladorDePaquete.class));
	}

	@Test
	void testCreateDuplicado() {
		when(repository.existsByCedula(dto.getCedula())).thenReturn(true);

		assertThrows(Exception.class, () -> service.create(dto));
	}

	@Test
	void testGetAll() {
		List<ManipuladorDePaquete> lista = Arrays.asList(entity);

		when(repository.findAll()).thenReturn(lista);

		List<ManipuladorDePaqueteDTO> result = service.getAll();

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

	@Test
	void testDeleteByIdSuccess() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		int result = service.deleteById(1L);

		assertEquals(0, result);
		verify(repository).delete(entity);
	}

	@Test
	void testDeleteByIdNotFound() {
		when(repository.findById(1L)).thenReturn(Optional.empty());

		int result = service.deleteById(1L);

		assertEquals(1, result);
	}

	@Test
	void testUpdateSuccess() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.existsByCedula(dto.getCedula())).thenReturn(false);

		int result = service.updateById(1L, dto);

		assertEquals(0, result);
		verify(repository).save(any(ManipuladorDePaquete.class));
	}

	@Test
	void testUpdateNotFound() {
		when(repository.findById(1L)).thenReturn(Optional.empty());

		int result = service.updateById(1L, dto);

		assertEquals(1, result);
	}

	@Test
	void testCount() {
		when(repository.count()).thenReturn(5L);

		long result = service.count();

		assertEquals(5, result);
	}

	@Test
	void testExist() {
		when(repository.existsById(1L)).thenReturn(true);

		boolean result = service.exist(1L);

		assertTrue(result);
	}

	@Test
	void testFindByNombre() {
		List<ManipuladorDePaquete> lista = Arrays.asList(entity);

		when(repository.findByNombre("Juan Perez")).thenReturn(Optional.of(lista));

		List<ManipuladorDePaqueteDTO> result = service.findByNombre("Juan Perez");

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

	@Test
	void testFindByCedula() {
		List<ManipuladorDePaquete> lista = Arrays.asList(entity);

		when(repository.findByCedula("12345678")).thenReturn(Optional.of(lista));

		List<ManipuladorDePaqueteDTO> result = service.findByCedula("12345678");

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

	@Test
	void testFindByTipoManipulador() {
		List<ManipuladorDePaquete> lista = Arrays.asList(entity);

		when(repository.findByTipoManipulador("CARTAS")).thenReturn(Optional.of(lista));

		List<ManipuladorDePaqueteDTO> result = service.findByTipoManipulador("CARTAS");

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

	@Test
	void testFindByNombreAndCedula() {
		List<ManipuladorDePaquete> lista = Arrays.asList(entity);

		when(repository.findByNombreAndCedula("Juan Perez", "12345678")).thenReturn(Optional.of(lista));

		List<ManipuladorDePaqueteDTO> result = service.findByNombreAndCedula("Juan Perez", "12345678");

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}
}