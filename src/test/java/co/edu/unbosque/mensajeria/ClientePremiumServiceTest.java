package co.edu.unbosque.mensajeria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import co.edu.unbosque.mensajeria.dto.ClientePremiumDTO;
import co.edu.unbosque.mensajeria.entity.ClientePremium;
import co.edu.unbosque.mensajeria.repository.ClientePremiumRepository;
import co.edu.unbosque.mensajeria.service.ClientePremiumService;

class ClientePremiumServiceTest {

	@Mock
	private ClientePremiumRepository repo;

	private ModelMapper mapper;

	private ClientePremiumService service;

	private ClientePremiumDTO dto;
	private ClientePremium entity;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		mapper = new ModelMapper();

		service = new ClientePremiumService();
		service.setClientePremiumRep(repo);              
		service.setMapper(mapper);          

		dto = new ClientePremiumDTO();
		dto.setCedula("123456789");
		dto.setNombre("Juan Perez");
		dto.setCorreo("juan@gmail.com");
		dto.setTelefono("3123456789");
		dto.setMetodoPago("EFECTIVO");
		dto.setTipoPedido("ALIMENTICIO");

		entity = new ClientePremium();
		entity.setCedula(dto.getCedula());
		entity.setNombre(dto.getNombre());
		entity.setCorreo(dto.getCorreo());
		entity.setTelefono(dto.getTelefono());
		entity.setMetodoPago(dto.getMetodoPago());
		entity.setTipoPedido(dto.getTipoPedido());
	}

	@Test
	void testCreateSuccess() {
		when(repo.existsByCedula(dto.getCedula())).thenReturn(false);

		int result = service.create(dto);

		assertEquals(0, result);
		verify(repo).save(any(ClientePremium.class));
	}

	@Test
	void testCreateDuplicado() {
		when(repo.existsByCedula(dto.getCedula())).thenReturn(true);

		assertThrows(Exception.class, () -> service.create(dto));
	}

	@Test
	void testGetAll() {
		List<ClientePremium> lista = Arrays.asList(entity);

		when(repo.findAll()).thenReturn(lista);

		List<ClientePremiumDTO> result = service.getAll();

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
		verify(repo).save(any(ClientePremium.class));
	}

	@Test
	void testUpdateNotFound() {
		when(repo.findById(1L)).thenReturn(Optional.empty());

		int result = service.updateById(1L, dto);

		assertEquals(1, result);
	}

	@Test
	void testCount() {
		when(repo.count()).thenReturn(10L);

		long result = service.count();

		assertEquals(10, result);
	}

	@Test
	void testExist() {
		when(repo.existsById(1L)).thenReturn(true);

		boolean result = service.exist(1L);

		assertTrue(result);
	}

	@Test
	void testFindByNombre() {
		List<ClientePremium> lista = Arrays.asList(entity);

		when(repo.findByNombre(dto.getNombre())).thenReturn(Optional.of(lista));

		List<ClientePremiumDTO> result = service.findByNombre(dto.getNombre());

		assertFalse(result.isEmpty());
	}

	@Test
	void testFindByCedula() {
		List<ClientePremium> lista = Arrays.asList(entity);

		when(repo.findByCedula(dto.getCedula())).thenReturn(Optional.of(lista));

		List<ClientePremiumDTO> result = service.findByCedula(dto.getCedula());

		assertFalse(result.isEmpty());
	}

	@Test
	void testFindByCorreo() {
		List<ClientePremium> lista = Arrays.asList(entity);

		when(repo.findByCorreo(dto.getCorreo())).thenReturn(Optional.of(lista));

		List<ClientePremiumDTO> result = service.findByCorreo(dto.getCorreo());

		assertFalse(result.isEmpty());
	}

	@Test
	void testFindByTelefono() {
		List<ClientePremium> lista = Arrays.asList(entity);

		when(repo.findByTelefono(dto.getTelefono())).thenReturn(Optional.of(lista));

		List<ClientePremiumDTO> result = service.findByTelefono(dto.getTelefono());

		assertFalse(result.isEmpty());
	}

	@Test
	void testFindByMetodoPago() {
		List<ClientePremium> lista = Arrays.asList(entity);

		when(repo.findByMetodoPago(dto.getMetodoPago())).thenReturn(Optional.of(lista));

		List<ClientePremiumDTO> result = service.findByMetodoPago(dto.getMetodoPago());

		assertFalse(result.isEmpty());
	}

	@Test
	void testFindByTipoPedido() {
		List<ClientePremium> lista = Arrays.asList(entity);

		when(repo.findByTipoPedido(dto.getTipoPedido())).thenReturn(Optional.of(lista));

		List<ClientePremiumDTO> result = service.findByTipoPedido(dto.getTipoPedido());

		assertFalse(result.isEmpty());
	}

	@Test
	void testFindByNombreAndCedula() {
		List<ClientePremium> lista = Arrays.asList(entity);

		when(repo.findByNombreAndCedula(dto.getNombre(), dto.getCedula()))
				.thenReturn(Optional.of(lista));

		List<ClientePremiumDTO> result = service.findByNombreAndCedula(dto.getNombre(), dto.getCedula());

		assertFalse(result.isEmpty());
	}

	@Test
	void testFindByTipoPedidoAndMetodoPago() {
		List<ClientePremium> lista = Arrays.asList(entity);

		when(repo.findByTipoPedidoAndMetodoPago(dto.getTipoPedido(), dto.getMetodoPago()))
				.thenReturn(Optional.of(lista));

		List<ClientePremiumDTO> result = service.findByTipoPedidoAndMetodoPago(dto.getTipoPedido(), dto.getMetodoPago());

		assertFalse(result.isEmpty());
	}
}