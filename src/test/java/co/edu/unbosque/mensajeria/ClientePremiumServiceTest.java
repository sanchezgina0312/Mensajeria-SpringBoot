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

/**
 * Clase de pruebas unitarias para el servicio {@link ClientePremiumService}.
 * 
 * Se encarga de validar el correcto funcionamiento de las operaciones CRUD y de
 * búsqueda relacionadas con la entidad ClientePremium.
 * 
 * Se utiliza Mockito para simular el comportamiento del repositorio y evitar
 * dependencias reales con la base de datos.
 */
class ClientePremiumServiceTest {

	/**
	 * Mock del repositorio de ClientePremium. Simula el acceso a datos sin
	 * conectarse a la base real.
	 */
	@Mock
	private ClientePremiumRepository repo;

	/**
	 * Mapper utilizado para convertir entre entidad y DTO.
	 */
	private ModelMapper mapper;

	/**
	 * Servicio a probar.
	 */
	private ClientePremiumService service;

	/**
	 * Objeto DTO de prueba.
	 */
	private ClientePremiumDTO dto;

	/**
	 * Entidad de prueba.
	 */
	private ClientePremium entity;

	/**
	 * Configuración inicial antes de cada prueba. Inicializa mocks, servicio,
	 * mapper y objetos de prueba.
	 */
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

	/**
	 * Verifica que un cliente se crea correctamente cuando no existe previamente.
	 */
	@Test
	void testCreateSuccess() {
		when(repo.existsByCedula(dto.getCedula())).thenReturn(false);

		int result = service.create(dto);

		assertEquals(0, result);
		verify(repo).save(any(ClientePremium.class));
	}

	/**
	 * Verifica que se lance una excepción cuando se intenta crear un cliente con
	 * cédula duplicada.
	 */
	@Test
	void testCreateDuplicado() {
		when(repo.existsByCedula(dto.getCedula())).thenReturn(true);

		assertThrows(Exception.class, () -> service.create(dto));
	}

	/**
	 * Verifica que se obtenga correctamente la lista de clientes.
	 */
	@Test
	void testGetAll() {
		when(repo.findAll()).thenReturn(Arrays.asList(entity));

		List<ClientePremiumDTO> result = service.getAll();

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

	/**
	 * Verifica eliminación exitosa de un cliente existente.
	 */
	@Test
	void testDeleteSuccess() {
		when(repo.findById(1L)).thenReturn(Optional.of(entity));

		int result = service.deleteById(1L);

		assertEquals(0, result);
		verify(repo).delete(entity);
	}

	/**
	 * Verifica comportamiento cuando se intenta eliminar un cliente inexistente.
	 */
	@Test
	void testDeleteNotFound() {
		when(repo.findById(1L)).thenReturn(Optional.empty());

		int result = service.deleteById(1L);

		assertEquals(1, result);
	}

	/**
	 * Verifica actualización exitosa de un cliente existente.
	 */
	@Test
	void testUpdateSuccess() {
		when(repo.findById(1L)).thenReturn(Optional.of(entity));
		when(repo.existsByCedula(dto.getCedula())).thenReturn(false);

		int result = service.updateById(1L, dto);

		assertEquals(0, result);
		verify(repo).save(any(ClientePremium.class));
	}

	/**
	 * Verifica comportamiento cuando se intenta actualizar un cliente inexistente.
	 */
	@Test
	void testUpdateNotFound() {
		when(repo.findById(1L)).thenReturn(Optional.empty());

		int result = service.updateById(1L, dto);

		assertEquals(1, result);
	}

	/**
	 * Verifica el conteo total de clientes.
	 */
	@Test
	void testCount() {
		when(repo.count()).thenReturn(10L);

		long result = service.count();

		assertEquals(10, result);
	}

	/**
	 * Verifica si existe un cliente por ID.
	 */
	@Test
	void testExist() {
		when(repo.existsById(1L)).thenReturn(true);

		boolean result = service.exist(1L);

		assertTrue(result);
	}

	/**
	 * Verifica búsqueda por nombre.
	 */
	@Test
	void testFindByNombre() {
		when(repo.findByNombre(dto.getNombre())).thenReturn(Optional.of(Arrays.asList(entity)));

		List<ClientePremiumDTO> result = service.findByNombre(dto.getNombre());

		assertFalse(result.isEmpty());
	}

	/**
	 * Verifica búsqueda por cédula.
	 */
	@Test
	void testFindByCedula() {
		when(repo.findByCedula(dto.getCedula())).thenReturn(Optional.of(Arrays.asList(entity)));

		List<ClientePremiumDTO> result = service.findByCedula(dto.getCedula());

		assertFalse(result.isEmpty());
	}

	/**
	 * Verifica búsqueda por correo.
	 */
	@Test
	void testFindByCorreo() {
		when(repo.findByCorreo(dto.getCorreo())).thenReturn(Optional.of(Arrays.asList(entity)));

		List<ClientePremiumDTO> result = service.findByCorreo(dto.getCorreo());

		assertFalse(result.isEmpty());
	}

	/**
	 * Verifica búsqueda por teléfono.
	 */
	@Test
	void testFindByTelefono() {
		when(repo.findByTelefono(dto.getTelefono())).thenReturn(Optional.of(Arrays.asList(entity)));

		List<ClientePremiumDTO> result = service.findByTelefono(dto.getTelefono());

		assertFalse(result.isEmpty());
	}

	/**
	 * Verifica búsqueda por método de pago.
	 */
	@Test
	void testFindByMetodoPago() {
		when(repo.findByMetodoPago(dto.getMetodoPago())).thenReturn(Optional.of(Arrays.asList(entity)));

		List<ClientePremiumDTO> result = service.findByMetodoPago(dto.getMetodoPago());

		assertFalse(result.isEmpty());
	}

	/**
	 * Verifica búsqueda por tipo de pedido.
	 */
	@Test
	void testFindByTipoPedido() {
		when(repo.findByTipoPedido(dto.getTipoPedido())).thenReturn(Optional.of(Arrays.asList(entity)));

		List<ClientePremiumDTO> result = service.findByTipoPedido(dto.getTipoPedido());

		assertFalse(result.isEmpty());
	}

	/**
	 * Verifica búsqueda combinada por nombre y cédula.
	 */
	@Test
	void testFindByNombreAndCedula() {
		when(repo.findByNombreAndCedula(dto.getNombre(), dto.getCedula()))
				.thenReturn(Optional.of(Arrays.asList(entity)));

		List<ClientePremiumDTO> result = service.findByNombreAndCedula(dto.getNombre(), dto.getCedula());

		assertFalse(result.isEmpty());
	}

	/**
	 * Verifica búsqueda combinada por tipo de pedido y método de pago.
	 */
	@Test
	void testFindByTipoPedidoAndMetodoPago() {
		when(repo.findByTipoPedidoAndMetodoPago(dto.getTipoPedido(), dto.getMetodoPago()))
				.thenReturn(Optional.of(Arrays.asList(entity)));

		List<ClientePremiumDTO> result = service.findByTipoPedidoAndMetodoPago(dto.getTipoPedido(),
				dto.getMetodoPago());

		assertFalse(result.isEmpty());
	}
}