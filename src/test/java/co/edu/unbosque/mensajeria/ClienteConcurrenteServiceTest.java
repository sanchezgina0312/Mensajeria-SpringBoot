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

import co.edu.unbosque.mensajeria.dto.ClienteConcurrenteDTO;
import co.edu.unbosque.mensajeria.entity.ClienteConcurrente;
import co.edu.unbosque.mensajeria.repository.ClienteConcurrenteRepository;
import co.edu.unbosque.mensajeria.service.ClienteConcurrenteService;
import co.edu.unbosque.mensajeria.util.LanzadorDeException;

/**
 * Clase de pruebas unitarias para el servicio {@link ClienteConcurrenteService}.
 * <p>
 * Esta clase valida el correcto funcionamiento de las operaciones principales
 * del servicio relacionadas con la entidad ClienteConcurrente, incluyendo:
 * </p>
 * <ul>
 * <li>Creación de clientes</li>
 * <li>Validación de duplicados</li>
 * <li>Actualización de registros</li>
 * <li>Búsqueda por método de pago</li>
 * </ul>
 *
 * <p>
 * Se utiliza Mockito para simular el comportamiento del repositorio y evitar
 * interacción con una base de datos real.
 * </p>
 *
 * <p>
 * Además, se emplea {@link MockedStatic} para simular los métodos estáticos de
 * {@link LanzadorDeException}, evitando que las validaciones interfieran en las
 * pruebas.
 * </p>
 *
 * 
 */
class ClienteConcurrenteServiceTest {

	/**
	 * Mock del repositorio ClienteConcurrenteRepository.
	 */
	@Mock
	private ClienteConcurrenteRepository clienteConcurrenteRep;

	/**
	 * Mapper utilizado para convertir entre DTO y entidad.
	 */
	private ModelMapper mapper;

	/**
	 * Servicio que será probado.
	 */
	private ClienteConcurrenteService service;

	/**
	 * Objeto DTO utilizado en las pruebas.
	 */
	private ClienteConcurrenteDTO dto;

	/**
	 * Entidad utilizada como referencia en las pruebas.
	 */
	private ClienteConcurrente entity;

	/**
	 * Método de configuración inicial que se ejecuta antes de cada prueba.
	 * <p>
	 * Inicializa los mocks, el mapper, el servicio y los objetos de prueba.
	 * </p>
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
		dto.setContrasenia("123456");

		entity = new ClienteConcurrente();
		entity.setNombre(dto.getNombre());
		entity.setCedula(dto.getCedula());
		entity.setCorreo(dto.getCorreo());
		entity.setTelefono(dto.getTelefono());
		entity.setMetodoPago(dto.getMetodoPago());
		entity.setContrasenia(dto.getContrasenia());
	}

	/**
	 * Verifica que un cliente se crea correctamente cuando no existe previamente.
	 * <p>
	 * Se espera que el método retorne 0 y que el repositorio guarde la entidad.
	 * </p>
	 */
	@Test
	void testCreateSuccess() {
		try (MockedStatic<LanzadorDeException> mock = Mockito.mockStatic(LanzadorDeException.class)) {

			when(clienteConcurrenteRep.existsByCedula(dto.getCedula())).thenReturn(false, false);

			int result = service.create(dto);

			assertEquals(0, result);
			verify(clienteConcurrenteRep).save(any(ClienteConcurrente.class));
		}
	}

	/**
	 * Verifica que se lance una excepción cuando se intenta crear un cliente con
	 * cédula duplicada.
	 */
	@Test
	void testCreateDuplicado() {

		when(clienteConcurrenteRep.existsByCedula(dto.getCedula())).thenReturn(true);

		assertThrows(RuntimeException.class, () -> service.create(dto));
	}

	/**
	 * Verifica la actualización exitosa de un cliente existente.
	 * <p>
	 * Se espera que el método retorne 0 y que el repositorio guarde los cambios.
	 * </p>
	 */
	@Test
	void testUpdateSuccess() {
		try (MockedStatic<LanzadorDeException> mock = Mockito.mockStatic(LanzadorDeException.class)) {

			when(clienteConcurrenteRep.findById(1L)).thenReturn(Optional.of(entity));
			when(clienteConcurrenteRep.existsByCedula(dto.getCedula())).thenReturn(false);

			int result = service.updateById(1L, dto);

			assertEquals(0, result);
			verify(clienteConcurrenteRep).save(any(ClienteConcurrente.class));
		}
	}

	/**
	 * Verifica el comportamiento cuando se intenta actualizar un cliente que no
	 * existe.
	 * <p>
	 * Se espera que el método retorne 1.
	 * </p>
	 */
	@Test
	void testUpdateNotFound() {
		try (MockedStatic<LanzadorDeException> mock = Mockito.mockStatic(LanzadorDeException.class)) {

			when(clienteConcurrenteRep.findById(1L)).thenReturn(Optional.empty());

			int result = service.updateById(1L, dto);

			assertEquals(1, result);
		}
	}

	/**
	 * Verifica la búsqueda de clientes por método de pago.
	 * <p>
	 * Se espera que la lista retornada no esté vacía cuando existen resultados.
	 * </p>
	 */
	@Test
	void testFindByMetodoPago() {
		try (MockedStatic<LanzadorDeException> mock = Mockito.mockStatic(LanzadorDeException.class)) {

			List<ClienteConcurrente> lista = Arrays.asList(entity);

			when(clienteConcurrenteRep.findByMetodoPago("EFECTIVO")).thenReturn(Optional.of(lista));

			List<ClienteConcurrenteDTO> result = service.findByMetodoPago("EFECTIVO");

			assertFalse(result.isEmpty());
		}
	}
}