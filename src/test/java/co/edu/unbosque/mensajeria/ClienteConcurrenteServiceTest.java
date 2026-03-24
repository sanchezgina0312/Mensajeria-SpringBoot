package co.edu.unbosque.mensajeria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import co.edu.unbosque.mensajeria.dto.ClienteConcurrenteDTO;
import co.edu.unbosque.mensajeria.entity.ClienteConcurrente;
import co.edu.unbosque.mensajeria.repository.ClienteConcurrenteRepository;
import co.edu.unbosque.mensajeria.service.ClienteConcurrenteService;

class ClienteConcurrenteServiceTest {

	@Mock
	private ClienteConcurrenteRepository clienteConcurrenteRep;

	
	private ModelMapper mapper;

	private ClienteConcurrenteService service;

	private ClienteConcurrenteDTO dto;
	private ClienteConcurrente entity;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		
		mapper = new ModelMapper();

		service = new ClienteConcurrenteService();
		
		service.setClienteConcurrenteRep(clienteConcurrenteRep);
		service.setMapper(mapper);
		
		
		dto = new ClienteConcurrenteDTO();
		dto.setNombre("Juan Perez");
		dto.setCedula("123456");
		dto.setCorreo("juan@mail.com");
		dto.setTelefono("3001234567");
		dto.setMetodoPago("EFECTIVO");
		dto.setTipoPedido("ALIMENTICIO");

		entity = new ClienteConcurrente();
		entity.setNombre(dto.getNombre());
		entity.setCedula(dto.getCedula());
		entity.setCorreo(dto.getCorreo());
		entity.setTelefono(dto.getTelefono());
		entity.setMetodoPago(dto.getMetodoPago());
		entity.setTipoPedido(dto.getTipoPedido());
	}

	@Test
	void testCreateSuccess() {
		when(clienteConcurrenteRep.existsByCedula(dto.getCedula())).thenReturn(false);

		int result = service.create(dto);

		assertEquals(0, result);
		verify(clienteConcurrenteRep).save(any(ClienteConcurrente.class));
	}

	@Test
	void testCreateDuplicado() {
		when(clienteConcurrenteRep.existsByCedula(dto.getCedula())).thenReturn(true);

		assertThrows(Exception.class, () -> {
			service.create(dto);
		});
	}

	@Test
	void testGetAll() {
		List<ClienteConcurrente> lista = Arrays.asList(entity);

		when(clienteConcurrenteRep.findAll()).thenReturn(lista);

		List<ClienteConcurrenteDTO> result = service.getAll();

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

	@Test
	void testDeleteByIdSuccess() {
		when(clienteConcurrenteRep.findById(1L)).thenReturn(Optional.of(entity));

		int result = service.deleteById(1L);

		assertEquals(0, result);
		verify(clienteConcurrenteRep).delete(entity);
	}

	@Test
	void testDeleteByIdNotFound() {
		when(clienteConcurrenteRep.findById(1L)).thenReturn(Optional.empty());

		int result = service.deleteById(1L);

		assertEquals(1, result);
	}

	@Test
	void testUpdateSuccess() {
		when(clienteConcurrenteRep.findById(1L)).thenReturn(Optional.of(entity));
		when(clienteConcurrenteRep.existsByCedula(dto.getCedula())).thenReturn(false);

		int result = service.updateById(1L, dto);

		assertEquals(0, result);
		verify(clienteConcurrenteRep).save(any(ClienteConcurrente.class));
	}

	@Test
	void testUpdateNotFound() {
		when(clienteConcurrenteRep.findById(1L)).thenReturn(Optional.empty());

		int result = service.updateById(1L, dto);

		assertEquals(1, result);
	}

	@Test
	void testCount() {
		when(clienteConcurrenteRep.count()).thenReturn(3L);

		long result = service.count();

		assertEquals(3, result);
	}

	@Test
	void testExist() {
		when(clienteConcurrenteRep.existsById(1L)).thenReturn(true);

		boolean result = service.exist(1L);

		assertTrue(result);
	}

	@Test
	void testFindByNombre() {
		List<ClienteConcurrente> lista = Arrays.asList(entity);

		when(clienteConcurrenteRep.findByNombre("Juan Perez")).thenReturn(Optional.of(lista));

		List<ClienteConcurrenteDTO> result = service.findByNombre("Juan Perez");

		assertFalse(result.isEmpty());
	}

	@Test
	void testFindByCedula() {
		List<ClienteConcurrente> lista = Arrays.asList(entity);

		when(clienteConcurrenteRep.findByCedula("123456")).thenReturn(Optional.of(lista));

		List<ClienteConcurrenteDTO> result = service.findByCedula("123456");

		assertFalse(result.isEmpty());
	}

	@Test
	void testFindByMetodoPago() {
		List<ClienteConcurrente> lista = Arrays.asList(entity);

		when(clienteConcurrenteRep.findByMetodoPago("EFECTIVO")).thenReturn(Optional.of(lista));

		List<ClienteConcurrenteDTO> result = service.findByMetodoPago("EFECTIVO");

		assertFalse(result.isEmpty());
	}

	@Test
	void testFindByTipoPedido() {
		List<ClienteConcurrente> lista = Arrays.asList(entity);

		when(clienteConcurrenteRep.findByTipoPedido("ALIMENTICIO")).thenReturn(Optional.of(lista));

		List<ClienteConcurrenteDTO> result = service.findByTipoPedido("ALIMENTICIO");

		assertFalse(result.isEmpty());
	}
}