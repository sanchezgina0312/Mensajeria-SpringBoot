package co.edu.unbosque.mensajeria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import co.edu.unbosque.mensajeria.dto.ClienteNormalDTO;
import co.edu.unbosque.mensajeria.entity.ClienteNormal;
import co.edu.unbosque.mensajeria.repository.ClienteNormalRepository;
import co.edu.unbosque.mensajeria.service.ClienteNormalService;

class ClienteNormalServiceTest {

	@Mock
	private ClienteNormalRepository clienteNormalRep;

	private ModelMapper mapper;

	private ClienteNormalService service;

	private ClienteNormalDTO dto;
	private ClienteNormal entity;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		mapper = new ModelMapper();

		service = new ClienteNormalService();

		service.setClienteNormalRep(clienteNormalRep);
		service.setMapper(mapper);

		dto = new ClienteNormalDTO();
		dto.setNombre("Juan Perez");
		dto.setCedula("123456");
		dto.setCorreo("juan@mail.com");
		dto.setTelefono("3001234567");
		dto.setMetodoPago("TARJETA");

		entity = new ClienteNormal();
		entity.setNombre(dto.getNombre());
		entity.setCedula(dto.getCedula());
		entity.setCorreo(dto.getCorreo());
		entity.setTelefono(dto.getTelefono());
		entity.setMetodoPago(dto.getMetodoPago());
	}

	@Test
	void testCreateSuccess() {
		when(clienteNormalRep.existsByCedula(dto.getCedula())).thenReturn(false);
		when(clienteNormalRep.save(any(ClienteNormal.class))).thenReturn(entity); 

		int result = service.create(dto);

		assertEquals(0, result);
		verify(clienteNormalRep).save(any(ClienteNormal.class));
	}
	
	@Test
	void testCreateDuplicado() {
		when(clienteNormalRep.existsByCedula(dto.getCedula())).thenReturn(true);

		assertThrows(Exception.class, () -> {
			service.create(dto);
		});
	}

	@Test
	void testGetAll() {
		List<ClienteNormal> lista = Arrays.asList(entity);

		when(clienteNormalRep.findAll()).thenReturn(lista);

		List<ClienteNormalDTO> result = service.getAll();

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

	@Test
	void testDeleteByIdSuccess() {
		when(clienteNormalRep.findById(1L)).thenReturn(Optional.of(entity));

		int result = service.deleteById(1L);

		assertEquals(0, result);
		verify(clienteNormalRep).delete(entity);
	}

	@Test
	void testDeleteByIdNotFound() {
		when(clienteNormalRep.findById(1L)).thenReturn(Optional.empty());

		int result = service.deleteById(1L);

		assertEquals(1, result);
	}

	@Test
	void testUpdateSuccess() {
		when(clienteNormalRep.findById(1L)).thenReturn(Optional.of(entity));
		when(clienteNormalRep.existsByCedula(dto.getCedula())).thenReturn(false);
		when(clienteNormalRep.save(any(ClienteNormal.class))).thenReturn(entity); 

		int result = service.updateById(1L, dto);

		assertEquals(0, result);
		verify(clienteNormalRep).save(any(ClienteNormal.class));
	}

	@Test
	void testUpdateNotFound() {
		when(clienteNormalRep.findById(1L)).thenReturn(Optional.empty());

		int result = service.updateById(1L, dto);

		assertEquals(1, result);
	}

	@Test
	void testCount() {
		when(clienteNormalRep.count()).thenReturn(3L);

		long result = service.count();

		assertEquals(3, result);
	}

	@Test
	void testExist() {
		when(clienteNormalRep.existsById(1L)).thenReturn(true);

		boolean result = service.exist(1L);

		assertTrue(result);
	}

	@Test
	void testFindByNombre() {
		List<ClienteNormal> lista = Arrays.asList(entity);

		when(clienteNormalRep.findByNombre("Juan Perez")).thenReturn(Optional.of(lista));

		List<ClienteNormalDTO> result = service.findByNombre("Juan Perez");

		assertFalse(result.isEmpty());
	}

	@Test
	void testFindByCedula() {
		List<ClienteNormal> lista = Arrays.asList(entity);

		when(clienteNormalRep.findByCedula("123456")).thenReturn(Optional.of(lista));

		List<ClienteNormalDTO> result = service.findByCedula("123456");

		assertFalse(result.isEmpty());
	}

	@Test
	void testFindByMetodoPago() {
		List<ClienteNormal> lista = Arrays.asList(entity);

		when(clienteNormalRep.findByMetodoPago("TARJETA"))
				.thenReturn(Optional.of(lista));

		List<ClienteNormalDTO> result = service.findByMetodoPago("TARJETA");

		assertFalse(result.isEmpty());
	}
}