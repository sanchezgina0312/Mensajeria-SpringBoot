package co.edu.unbosque.mensajeria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import co.edu.unbosque.mensajeria.dto.ClienteNormalDTO;
import co.edu.unbosque.mensajeria.entity.ClienteNormal;
import co.edu.unbosque.mensajeria.repository.ClienteNormalRepository;
import co.edu.unbosque.mensajeria.service.ClienteNormalService;
import co.edu.unbosque.mensajeria.util.LanzadorDeException;

/**
 * Clase de pruebas unitarias para {@link ClienteNormalService}.
 *
 * <p>
 * Se valida el correcto funcionamiento de los métodos principales del servicio:
 * creación, actualización y búsqueda por método de pago.
 * </p>
 *
 * <p>
 * Se utiliza Mockito para simular el comportamiento del repositorio y evitar
 * dependencias externas con la base de datos.
 * </p>
 *
 * <p>
 * También se hace uso de {@link MockedStatic} para simular el comportamiento de
 * la clase {@link LanzadorDeException}, evitando que las validaciones estáticas
 * interfieran con las pruebas.
 * </p>
 *
 * <b>Casos probados:</b>
 * <ul>
 * <li>Creación exitosa de cliente</li>
 * <li>Creación con duplicado (excepción)</li>
 * <li>Actualización exitosa</li>
 * <li>Actualización cuando el cliente no existe</li>
 * <li>Búsqueda por método de pago</li>
 * </ul>
 *
 */
class ClienteNormalServiceTest {

	/**
	 * Mock del repositorio ClienteNormalRepository.
	 */
	@Mock
	private ClienteNormalRepository clienteNormalRep;

	/**
	 * Mapper para conversión entre entidad y DTO.
	 */
	private ModelMapper mapper;

	/**
	 * Servicio a probar.
	 */
	private ClienteNormalService service;

	/**
	 * DTO de prueba.
	 */
	private ClienteNormalDTO dto;

	/**
	 * Entidad de prueba.
	 */
	private ClienteNormal entity;

	/**
	 * Configuración inicial antes de cada prueba. Se inicializan los mocks, mapper,
	 * servicio y objetos base.
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
		dto.setMetodoPago("EFECTIVO");

		entity = new ClienteNormal();
		entity.setNombre(dto.getNombre());
		entity.setCedula(dto.getCedula());
		entity.setCorreo(dto.getCorreo());
		entity.setTelefono(dto.getTelefono());
		entity.setMetodoPago(dto.getMetodoPago());
	}

	/**
	 * Verifica que un cliente se crea correctamente cuando no existe previamente.
	 */
	@Test
	void testCreateSuccess() {
		try (MockedStatic<LanzadorDeException> mock = Mockito.mockStatic(LanzadorDeException.class)) {

			when(clienteNormalRep.existsByCedula(dto.getCedula())).thenReturn(false, false);

			int result = service.create(dto);

			assertEquals(0, result);
			verify(clienteNormalRep).save(any(ClienteNormal.class));
		}
	}

	/**
	 * Verifica que se lance una excepción cuando se intenta crear un cliente con
	 * cédula duplicada.
	 */
	@Test
	void testCreateDuplicado() {
		try (MockedStatic<LanzadorDeException> mock = Mockito.mockStatic(LanzadorDeException.class)) {
			when(clienteNormalRep.existsByCedula(dto.getCedula())).thenReturn(true);

			int result = service.create(dto);

			assertEquals(1, result, "Debería retornar 1 cuando el cliente ya existe");

			verify(clienteNormalRep, never()).save(any(ClienteNormal.class));
		}
	}

	/**
	 * Verifica que la actualización de un cliente existente se realiza
	 * correctamente.
	 */
	@Test
	void testUpdateSuccess() {
		try (MockedStatic<LanzadorDeException> mock = Mockito.mockStatic(LanzadorDeException.class)) {

			when(clienteNormalRep.findById(1L)).thenReturn(Optional.of(entity));
			when(clienteNormalRep.existsByCedula(dto.getCedula())).thenReturn(false);

			int result = service.updateById(1L, dto);

			assertEquals(0, result);
			verify(clienteNormalRep).save(any(ClienteNormal.class));
		}
	}

	/**
	 * Verifica que el servicio retorne 1 cuando se intenta actualizar un cliente
	 * que no existe.
	 */
	@Test
	void testUpdateNotFound() {
		try (MockedStatic<LanzadorDeException> mock = Mockito.mockStatic(LanzadorDeException.class)) {

			when(clienteNormalRep.findById(1L)).thenReturn(Optional.empty());

			int result = service.updateById(1L, dto);

			assertEquals(1, result);
		}
	}

	/**
	 * Verifica la búsqueda de clientes por método de pago.
	 */
	@Test
	void testFindByMetodoPago() {
		try (MockedStatic<LanzadorDeException> mock = Mockito.mockStatic(LanzadorDeException.class)) {

			List<ClienteNormal> lista = Arrays.asList(entity);

			when(clienteNormalRep.findByMetodoPago("EFECTIVO")).thenReturn(Optional.of(lista));

			List<ClienteNormalDTO> result = service.findByMetodoPago("EFECTIVO");

			assertNotNull(result);
			assertFalse(result.isEmpty());
		}
	}
}