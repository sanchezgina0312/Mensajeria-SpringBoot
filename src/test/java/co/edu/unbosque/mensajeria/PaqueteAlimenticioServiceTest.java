package co.edu.unbosque.mensajeria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import co.edu.unbosque.mensajeria.dto.PaqueteAlimenticioDTO;
import co.edu.unbosque.mensajeria.entity.PaqueteAlimenticio;
import co.edu.unbosque.mensajeria.repository.PaqueteAlimenticioRepository;
import co.edu.unbosque.mensajeria.service.PaqueteAlimenticioService;
import co.edu.unbosque.mensajeria.util.LanzadorDeException;

/**
 * Clase de pruebas unitarias para el servicio {@link PaqueteAlimenticioService}.
 *
 * <p>
 * Esta clase valida el correcto funcionamiento de las operaciones del servicio
 * encargado de gestionar los paquetes alimenticios dentro del sistema.
 * </p>
 *
 * <p>
 * Se utilizan pruebas unitarias con {@link org.mockito.Mockito} para simular el
 * comportamiento del repositorio {@link PaqueteAlimenticioRepository} y evitar
 * dependencias con bases de datos reales.
 * </p>
 *
 * <p>
 * También se emplea {@link ModelMapper} para validar la correcta conversión
 * entre entidades y DTOs.
 * </p>
 *
 * <h2>Funcionalidades evaluadas</h2>
 * <ul>
 * <li>Operaciones CRUD (crear, consultar, actualizar y eliminar)</li>
 * <li>Búsquedas personalizadas por atributos</li>
 * <li>Cálculos de negocio (precio y descuentos)</li>
 * <li>Lógica de estados del paquete (entregado, prioritario)</li>
 * </ul>
 *
 * <h2>Consideraciones</h2>
 * <ul>
 * <li>Se usa {@link LanzadorDeException} para validar datos de entrada</li>
 * <li>Los métodos del servicio retornan valores enteros para indicar estado:
 *     <ul>
 *         <li>1 → operación exitosa</li>
 *         <li>0 → operación fallida o no encontrada</li>
 *     </ul>
 * </li>
 * </ul>
 *
 * @author Jairo
 */
class PaqueteAlimenticioServiceTest {

    /**
     * Mock del repositorio de paquetes alimenticios.
     * Simula el acceso a datos sin necesidad de conexión real.
     */
	@Mock
	private PaqueteAlimenticioRepository repository;

    /**
     * Mapper utilizado para convertir entre DTO y entidad.
     */
	private ModelMapper mapper;

    /**
     * Servicio que se va a probar.
     */
	private PaqueteAlimenticioService service;

    /**
     * Objeto DTO utilizado como entrada en las pruebas.
     */
	private PaqueteAlimenticioDTO dto;

    /**
     * Entidad simulada para pruebas.
     */
	private PaqueteAlimenticio entity;

    /**
     * Método de configuración inicial que se ejecuta antes de cada prueba.
     *
     * <p>
     * Inicializa los mocks, el servicio, el mapper y los objetos de prueba.
     * </p>
     */
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		mapper = new ModelMapper();

		service = new PaqueteAlimenticioService();
		service.setPaqueteAlimenticioRep(repository);
		service.setMapper(mapper);

		dto = new PaqueteAlimenticioDTO();
		dto.setDireccionDestino("Calle 10 # 20-30");
		dto.setTamanio("MEDIANO");
		dto.setTipoDeAlimento("FRUTA");
		dto.setCiudadDestino("Bogota");
		dto.setFechaCreacionPedido(LocalDateTime.now());
		dto.setSeEnviaHoy(true);

		entity = new PaqueteAlimenticio();
		entity.setDireccionDestino(dto.getDireccionDestino());
		entity.setTamanio(dto.getTamanio());
		entity.setTipoDeAlimento(dto.getTipoDeAlimento());
		entity.setCiudadDestino(dto.getCiudadDestino());
		entity.setFechaCreacionPedido(dto.getFechaCreacionPedido());
		entity.setSeEnviaHoy(dto.isSeEnviaHoy());
	}

    /**
     * Verifica que la creación de un paquete se realiza correctamente.
     */
	@Test
	void testCreateSuccess() {
		try (MockedStatic<LanzadorDeException> mock = Mockito.mockStatic(LanzadorDeException.class)) {

			when(repository.save(any(PaqueteAlimenticio.class))).thenReturn(entity);

			int result = service.create(dto);

			assertEquals(1, result);
			verify(repository).save(any(PaqueteAlimenticio.class));
		}
	}

    /**
     * Verifica la obtención de todos los paquetes registrados.
     */
	@Test
	void testGetAll() {
		when(repository.findAll()).thenReturn(Arrays.asList(entity));

		List<PaqueteAlimenticioDTO> result = service.getAll();

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

    /**
     * Verifica la eliminación exitosa de un paquete existente.
     */
	@Test
	void testDeleteByIdSuccess() {
		try (MockedStatic<LanzadorDeException> mock = Mockito.mockStatic(LanzadorDeException.class)) {

			when(repository.findById(1L)).thenReturn(Optional.of(entity));

			int result = service.deleteById(1L);

			assertEquals(0, result);
			verify(repository).delete(entity);
		}
	}

    /**
     * Verifica el comportamiento cuando se intenta eliminar un paquete inexistente.
     */
	@Test
	void testDeleteByIdNotFound() {
		try (MockedStatic<LanzadorDeException> mock = Mockito.mockStatic(LanzadorDeException.class)) {

			when(repository.findById(1L)).thenReturn(Optional.empty());

			int result = service.deleteById(1L);

			assertEquals(1, result);
		}
	}

    /**
     * Verifica la actualización exitosa de un paquete existente.
     */
	@Test
	void testUpdateSuccess() {
		try (MockedStatic<LanzadorDeException> mock = Mockito.mockStatic(LanzadorDeException.class)) {

			when(repository.findById(1L)).thenReturn(Optional.of(entity));
			when(repository.save(any(PaqueteAlimenticio.class))).thenReturn(entity);

			int result = service.updateById(1L, dto);

			assertEquals(1, result);
			verify(repository).save(any(PaqueteAlimenticio.class));
		}
	}

    /**
     * Verifica el comportamiento cuando el paquete a actualizar no existe.
     */
	@Test
	void testUpdateNotFound() {
		try (MockedStatic<LanzadorDeException> mock = Mockito.mockStatic(LanzadorDeException.class)) {

			when(repository.findById(1L)).thenReturn(Optional.empty());

			int result = service.updateById(1L, dto);

			assertEquals(0, result);
		}
	}

    /**
     * Verifica el conteo total de paquetes.
     */
	@Test
	void testCount() {
		when(repository.count()).thenReturn(10L);

		assertEquals(10, service.count());
	}

    /**
     * Verifica si un paquete existe por su ID.
     */
	@Test
	void testExist() {
		when(repository.existsById(1L)).thenReturn(true);

		assertTrue(service.exist(1L));
	}

    /**
     * Verifica la búsqueda de paquetes por tamaño.
     */
	@Test
	void testFindByTamanio() {
		try (MockedStatic<LanzadorDeException> mock = Mockito.mockStatic(LanzadorDeException.class)) {

			when(repository.findByTamanio("MEDIANO")).thenReturn(Optional.of(Arrays.asList(entity)));

			assertFalse(service.findByTamanio("MEDIANO").isEmpty());
		}
	}

    /**
     * Verifica la búsqueda de paquetes que se envían el mismo día.
     */
	@Test
	void testFindBySeEnviaHoy() {
		when(repository.findBySeEnviaHoy(true)).thenReturn(Optional.of(Arrays.asList(entity)));

		assertFalse(service.findBySeEnviaHoy(true).isEmpty());
	}

    /**
     * Verifica la búsqueda de paquetes por tipo de alimento.
     */
	@Test
	void testFindByTipoAlimento() {
		try (MockedStatic<LanzadorDeException> mock = Mockito.mockStatic(LanzadorDeException.class)) {

			when(repository.findByTipoDeAlimento("FRUTA")).thenReturn(Optional.of(Arrays.asList(entity)));

			assertFalse(service.findByTipoDeAlimento("FRUTA").isEmpty());
		}
	}

    /**
     * Verifica la búsqueda de un paquete por ID.
     */
	@Test
	void testFindById() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		assertNotNull(service.findById(1L));
	}

    /**
     * Verifica el cálculo del precio según el tamaño del paquete.
     */
	@Test
	void testCalcularPrecio() {
		double result = service.calcularPrecioPorTamaño(10000, "GRANDE");

		assertEquals(20000, result);
	}

    /**
     * Verifica la aplicación de descuento según el tipo de cliente.
     */
	@Test
	void testDescuento() {
		double result = service.aplicarDescuentoPorCliente(10000, "PREMIUM");

		assertEquals(7500, result);
	}

    /**
     * Verifica que el estado cambie a "ENTREGADO" cuando la fecha de entrega ya pasó.
     */
	@Test
	void testProcesarEstado_Entregado() {
		dto.setFechaEstimadaEntrega(LocalDateTime.now().minusHours(1));

		service.procesarEstadoYTiempoDTO(dto);

		assertEquals("ENTREGADO", dto.getEstadoPedido());
		assertFalse(dto.isEsPrioritario());
	}

    /**
     * Verifica que el paquete se marque como prioritario cuando está próximo a entregarse.
     */
	@Test
	void testProcesarEstado_Prioritario() {
		dto.setFechaEstimadaEntrega(LocalDateTime.now().plusHours(2));

		service.procesarEstadoYTiempoDTO(dto);

		assertTrue(dto.isEsPrioritario());
	}

}