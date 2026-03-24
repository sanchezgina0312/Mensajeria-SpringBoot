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

/**
 * Clase de prueba unitaria para el servicio {@link ClienteConcurrenteService}.
 * Utiliza Mockito para simular el comportamiento del repositorio y asegurar 
 * que la lógica de negocio funcione de manera aislada.
 * @version 1.0
 */
class ClienteConcurrenteServiceTest {

	/** Mock del repositorio de clientes concurrentes */
	@Mock
	private ClienteConcurrenteRepository clienteConcurrenteRep;

	/** Mapper para la conversión entre Entidades y DTOs */
	private ModelMapper mapper;

	/** Instancia del servicio bajo prueba */
	private ClienteConcurrenteService service;

	/** Objeto de transferencia de datos de ejemplo para las pruebas */
	private ClienteConcurrenteDTO dto;
    
	/** Entidad de ejemplo para las pruebas */
	private ClienteConcurrente entity;

	/**
	 * Configuración inicial antes de cada prueba.
	 * Inicializa los mocks, el mapper y los objetos de prueba (DTO y Entidad).
	 */
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

	/**
	 * Prueba la creación exitosa de un cliente cuando la cédula no existe previamente.
	 */
	@Test
	void testCreateSuccess() {
		when(clienteConcurrenteRep.existsByCedula(dto.getCedula())).thenReturn(false);

		int result = service.create(dto);

		assertEquals(0, result);
		verify(clienteConcurrenteRep).save(any(ClienteConcurrente.class));
	}

	/**
	 * Prueba que se lance una excepción al intentar crear un cliente con una cédula duplicada.
	 */
	@Test
	void testCreateDuplicado() {
		when(clienteConcurrenteRep.existsByCedula(dto.getCedula())).thenReturn(true);

		assertThrows(Exception.class, () -> {
			service.create(dto);
		});
	}

	/**
	 * Prueba la obtención de todos los clientes concurrentes.
	 */
	@Test
	void testGetAll() {
		List<ClienteConcurrente> lista = Arrays.asList(entity);
		when(clienteConcurrenteRep.findAll()).thenReturn(lista);

		List<ClienteConcurrenteDTO> result = service.getAll();

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

	/**
	 * Prueba la eliminación exitosa de un cliente por su ID.
	 */
	@Test
	void testDeleteByIdSuccess() {
		when(clienteConcurrenteRep.findById(1L)).thenReturn(Optional.of(entity));

		int result = service.deleteById(1L);

		assertEquals(0, result);
		verify(clienteConcurrenteRep).delete(entity);
	}

	/**
	 * Prueba el escenario donde se intenta eliminar un cliente que no existe.
	 */
	@Test
	void testDeleteByIdNotFound() {
		when(clienteConcurrenteRep.findById(1L)).thenReturn(Optional.empty());

		int result = service.deleteById(1L);

		assertEquals(1, result);
	}

	/**
	 * Prueba la actualización exitosa de la información de un cliente existente.
	 */
	@Test
	void testUpdateSuccess() {
		when(clienteConcurrenteRep.findById(1L)).thenReturn(Optional.of(entity));
		when(clienteConcurrenteRep.existsByCedula(dto.getCedula())).thenReturn(false);

		int result = service.updateById(1L, dto);

		assertEquals(0, result);
		verify(clienteConcurrenteRep).save(any(ClienteConcurrente.class));
	}

	/**
	 * Prueba el escenario donde se intenta actualizar un cliente que no existe.
	 */
	@Test
	void testUpdateNotFound() {
		when(clienteConcurrenteRep.findById(1L)).thenReturn(Optional.empty());

		int result = service.updateById(1L, dto);

		assertEquals(1, result);
	}

	/**
	 * Prueba el método que cuenta el total de clientes concurrentes.
	 */
	@Test
	void testCount() {
		when(clienteConcurrenteRep.count()).thenReturn(3L);

		long result = service.count();

		assertEquals(3, result);
	}

	/**
	 * Prueba la verificación de existencia de un cliente por ID.
	 */
	@Test
	void testExist() {
		when(clienteConcurrenteRep.existsById(1L)).thenReturn(true);

		boolean result = service.exist(1L);

		assertTrue(result);
	}

	/**
	 * Prueba la búsqueda de clientes filtrando por nombre.
	 */
	@Test
	void testFindByNombre() {
		List<ClienteConcurrente> lista = Arrays.asList(entity);
		when(clienteConcurrenteRep.findByNombre("Juan Perez")).thenReturn(Optional.of(lista));

		List<ClienteConcurrenteDTO> result = service.findByNombre("Juan Perez");

		assertFalse(result.isEmpty());
	}

	/**
	 * Prueba la búsqueda de clientes filtrando por número de cédula.
	 */
	@Test
	void testFindByCedula() {
		List<ClienteConcurrente> lista = Arrays.asList(entity);
		when(clienteConcurrenteRep.findByCedula("123456")).thenReturn(Optional.of(lista));

		List<ClienteConcurrenteDTO> result = service.findByCedula("123456");

		assertFalse(result.isEmpty());
	}

	/**
	 * Prueba la búsqueda de clientes filtrando por método de pago.
	 */
	@Test
	void testFindByMetodoPago() {
		List<ClienteConcurrente> lista = Arrays.asList(entity);
		when(clienteConcurrenteRep.findByMetodoPago("EFECTIVO")).thenReturn(Optional.of(lista));

		List<ClienteConcurrenteDTO> result = service.findByMetodoPago("EFECTIVO");

		assertFalse(result.isEmpty());
	}

	/**
	 * Prueba la búsqueda de clientes filtrando por tipo de pedido.
	 */
	@Test
	void testFindByTipoPedido() {
		List<ClienteConcurrente> lista = Arrays.asList(entity);
		when(clienteConcurrenteRep.findByTipoPedido("ALIMENTICIO")).thenReturn(Optional.of(lista));

		List<ClienteConcurrenteDTO> result = service.findByTipoPedido("ALIMENTICIO");

		assertFalse(result.isEmpty());
	}
}