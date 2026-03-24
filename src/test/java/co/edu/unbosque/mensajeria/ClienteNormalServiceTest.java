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

/**
 * Clase de pruebas unitarias para el servicio ClienteNormalService. Se valida
 * el correcto funcionamiento de las operaciones CRUD y consultas personalizadas
 * usando Mockito.
 * 
 * @author Natalia D
 */
class ClienteNormalServiceTest {

	/**
	 * Mock del repositorio ClienteNormalRepository
	 */
	@Mock
	private ClienteNormalRepository clienteNormalRep;

	/**
	 * Mapper para conversión entre Entity y DTO
	 */
	private ModelMapper mapper;

	/**
	 * Servicio a probar
	 */
	private ClienteNormalService service;

	/**
	 * Objeto DTO de prueba
	 */
	private ClienteNormalDTO dto;

	/**
	 * Objeto Entity de prueba
	 */
	private ClienteNormal entity;

	/**
	 * Valores válidos según las validaciones del sistema
	 */
	private static final String METODO_VALIDO = "EFECTIVO";
	private static final String TIPO_VALIDO = "ALIMENTICIO";

	/**
	 * Configuración inicial antes de cada prueba. Se inicializan mocks, mapper,
	 * servicio y objetos de prueba.
	 */
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
		dto.setMetodoPago(METODO_VALIDO);
		dto.setTipoPedido(TIPO_VALIDO);

		entity = new ClienteNormal();
		entity.setNombre(dto.getNombre());
		entity.setCedula(dto.getCedula());
		entity.setCorreo(dto.getCorreo());
		entity.setTelefono(dto.getTelefono());
		entity.setMetodoPago(dto.getMetodoPago());
		entity.setTipoPedido(dto.getTipoPedido());
	}

	/**
	 * Prueba la creación exitosa de un cliente.
	 */
	@Test
	void testCreateSuccess() {
		when(clienteNormalRep.existsByCedula(dto.getCedula())).thenReturn(false);
		when(clienteNormalRep.save(any(ClienteNormal.class))).thenReturn(entity);

		int result = service.create(dto);

		assertEquals(0, result);
		verify(clienteNormalRep).save(any(ClienteNormal.class));
	}

	/**
	 * Prueba la creación cuando el cliente ya existe (duplicado).
	 */
	@Test
	void testCreateDuplicado() {
		when(clienteNormalRep.existsByCedula(dto.getCedula())).thenReturn(true);

		assertThrows(Exception.class, () -> service.create(dto));
	}

	/**
	 * Prueba la obtención de todos los clientes.
	 */
	@Test
	void testGetAll() {
		when(clienteNormalRep.findAll()).thenReturn(Arrays.asList(entity));

		List<ClienteNormalDTO> result = service.getAll();

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

	/**
	 * Prueba la eliminación de un cliente existente por ID.
	 */
	@Test
	void testDeleteByIdSuccess() {
		when(clienteNormalRep.findById(1L)).thenReturn(Optional.of(entity));

		int result = service.deleteById(1L);

		assertEquals(0, result);
		verify(clienteNormalRep).delete(entity);
	}

	/**
	 * Prueba la eliminación cuando el cliente no existe.
	 */
	@Test
	void testDeleteByIdNotFound() {
		when(clienteNormalRep.findById(1L)).thenReturn(Optional.empty());

		int result = service.deleteById(1L);

		assertEquals(1, result);
	}

	/**
	 * Prueba la actualización exitosa de un cliente.
	 */
	@Test
	void testUpdateSuccess() {
		when(clienteNormalRep.findById(1L)).thenReturn(Optional.of(entity));
		when(clienteNormalRep.existsByCedula(dto.getCedula())).thenReturn(false);
		when(clienteNormalRep.save(any(ClienteNormal.class))).thenReturn(entity);

		int result = service.updateById(1L, dto);

		assertEquals(0, result);
		verify(clienteNormalRep).save(any(ClienteNormal.class));
	}

	/**
	 * Prueba la actualización cuando el cliente no existe.
	 */
	@Test
	void testUpdateNotFound() {
		when(clienteNormalRep.findById(1L)).thenReturn(Optional.empty());

		int result = service.updateById(1L, dto);

		assertEquals(1, result);
	}

	/**
	 * Prueba el conteo total de registros.
	 */
	@Test
	void testCount() {
		when(clienteNormalRep.count()).thenReturn(3L);

		assertEquals(3, service.count());
	}

	/**
	 * Prueba la existencia de un cliente por ID.
	 */
	@Test
	void testExist() {
		when(clienteNormalRep.existsById(1L)).thenReturn(true);

		assertTrue(service.exist(1L));
	}

	/**
	 * Prueba la búsqueda de clientes por nombre.
	 */
	@Test
	void testFindByNombre() {
		when(clienteNormalRep.findByNombre("Juan Perez")).thenReturn(Optional.of(Arrays.asList(entity)));

		assertFalse(service.findByNombre("Juan Perez").isEmpty());
	}

	/**
	 * Prueba la búsqueda de clientes por cédula.
	 */
	@Test
	void testFindByCedula() {
		when(clienteNormalRep.findByCedula("123456")).thenReturn(Optional.of(Arrays.asList(entity)));

		assertFalse(service.findByCedula("123456").isEmpty());
	}

	/**
	 * Prueba la búsqueda de clientes por método de pago.
	 */
	@Test
	void testFindByMetodoPago() {
		when(clienteNormalRep.findByMetodoPago(METODO_VALIDO)).thenReturn(Optional.of(Arrays.asList(entity)));

		assertFalse(service.findByMetodoPago(METODO_VALIDO).isEmpty());
	}
}