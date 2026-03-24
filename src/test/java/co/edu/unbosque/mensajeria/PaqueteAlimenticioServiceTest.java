package co.edu.unbosque.mensajeria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import co.edu.unbosque.mensajeria.dto.PaqueteAlimenticioDTO;
import co.edu.unbosque.mensajeria.entity.PaqueteAlimenticio;
import co.edu.unbosque.mensajeria.repository.PaqueteAlimenticioRepository;
import co.edu.unbosque.mensajeria.service.PaqueteAlimenticioService;

/**
 * Clase de pruebas unitarias para {@link PaqueteAlimenticioService}.
 *
 * <p>
 * Se validan operaciones CRUD, consultas personalizadas, cálculos de negocio y
 * lógica de estados de entrega.
 * </p>
 *
 * <p>
 * Se utiliza Mockito para simular el repositorio y evitar dependencias
 * externas.
 * </p>
 *
 * <b>IMPORTANTE:</b>
 * <ul>
 * <li>El método create retorna 1 cuando es exitoso.</li>
 * <li>El método updateById retorna 1 si actualiza y 0 si no encuentra.</li>
 * <li>El DTO NO se modifica en create(), solo la entidad.</li>
 * </ul>
 *
 */
class PaqueteAlimenticioServiceTest {

	/** Mock del repositorio */
	@Mock
	private PaqueteAlimenticioRepository repository;

	/** Mapper para conversión DTO - Entidad */
	private ModelMapper mapper;

	/** Servicio a probar */
	private PaqueteAlimenticioService service;

	/** Objeto DTO de prueba */
	private PaqueteAlimenticioDTO dto;

	/** Entidad de prueba */
	private PaqueteAlimenticio entity;

	/**
	 * Inicializa mocks, mapper, servicio y datos base para las pruebas.
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

		when(repository.save(any(PaqueteAlimenticio.class))).thenReturn(entity);

		int result = service.create(dto);

		assertEquals(1, result);
		verify(repository).save(any(PaqueteAlimenticio.class));
	}

	/**
	 * Verifica la obtención de todos los registros.
	 */
	@Test
	void testGetAll() {
		when(repository.findAll()).thenReturn(Arrays.asList(entity));

		List<PaqueteAlimenticioDTO> result = service.getAll();

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

	/**
	 * Verifica eliminación exitosa de un registro existente.
	 */
	@Test
	void testDeleteByIdSuccess() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		int result = service.deleteById(1L);

		assertEquals(0, result);
		verify(repository).delete(entity);
	}

	/**
	 * Verifica comportamiento cuando se intenta eliminar un registro inexistente.
	 */
	@Test
	void testDeleteByIdNotFound() {
		when(repository.findById(1L)).thenReturn(Optional.empty());

		int result = service.deleteById(1L);

		assertEquals(1, result);
	}

	/**
	 * Verifica actualización exitosa de un paquete existente.
	 */
	@Test
	void testUpdateSuccess() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(any(PaqueteAlimenticio.class))).thenReturn(entity);

		int result = service.updateById(1L, dto);

		assertEquals(1, result);
		verify(repository).save(any(PaqueteAlimenticio.class));
	}

	/**
	 * Verifica comportamiento cuando se intenta actualizar un registro inexistente.
	 */
	@Test
	void testUpdateNotFound() {
		when(repository.findById(1L)).thenReturn(Optional.empty());

		int result = service.updateById(1L, dto);

		assertEquals(0, result);
	}

	/**
	 * Verifica el conteo total de registros.
	 */
	@Test
	void testCount() {
		when(repository.count()).thenReturn(10L);

		assertEquals(10, service.count());
	}

	/**
	 * Verifica la existencia de un registro por ID.
	 */
	@Test
	void testExist() {
		when(repository.existsById(1L)).thenReturn(true);

		assertTrue(service.exist(1L));
	}

	/**
	 * Verifica búsqueda por tamaño.
	 */
	@Test
	void testFindByTamanio() {
		when(repository.findByTamanio("MEDIANO")).thenReturn(Optional.of(Arrays.asList(entity)));

		assertFalse(service.findByTamanio("MEDIANO").isEmpty());
	}

	/**
	 * Verifica búsqueda por estado de envío en el día actual.
	 */
	@Test
	void testFindBySeEnviaHoy() {
		when(repository.findBySeEnviaHoy(true)).thenReturn(Optional.of(Arrays.asList(entity)));

		assertFalse(service.findBySeEnviaHoy(true).isEmpty());
	}

	/**
	 * Verifica búsqueda por tipo de alimento.
	 */
	@Test
	void testFindByTipoAlimento() {
		when(repository.findByTipoDeAlimento("FRUTA")).thenReturn(Optional.of(Arrays.asList(entity)));

		assertFalse(service.findByTipoDeAlimento("FRUTA").isEmpty());
	}

	/**
	 * Verifica búsqueda por ID.
	 */
	@Test
	void testFindById() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		assertNotNull(service.findById(1L));
	}

	/**
	 * Verifica cálculo de precio según tamaño del paquete.
	 */
	@Test
	void testCalcularPrecio() {
		double result = service.calcularPrecioPorTamaño(10000, "GRANDE");

		assertEquals(20000, result);
	}

	/**
	 * Verifica aplicación de descuento según tipo de cliente.
	 */
	@Test
	void testDescuento() {
		double result = service.aplicarDescuentoPorCliente(10000, "PREMIUM");

		assertEquals(7500, result);
	}

	/**
	 * Verifica que el estado cambia a ENTREGADO cuando la fecha ya pasó.
	 */
	@Test
	void testProcesarEstado_Entregado() {
		dto.setFechaEstimadaEntrega(LocalDateTime.now().minusHours(1));

		service.procesarEstadoYTiempoDTO(dto);

		assertEquals("ENTREGADO", dto.getEstadoPedido());
		assertFalse(dto.isEsPrioritario());
	}

	/**
	 * Verifica que el paquete se marque como prioritario cuando está próximo a
	 * entregarse.
	 */
	@Test
	void testProcesarEstado_Prioritario() {
		dto.setFechaEstimadaEntrega(LocalDateTime.now().plusHours(2));

		service.procesarEstadoYTiempoDTO(dto);

		assertTrue(dto.isEsPrioritario());
	}

	/**
	 * Verifica el registro de plazo de entrega de 6 horas.
	 */
	@Test
	void testRegistrarPlazo() {
		dto.setFechaCreacionPedido(LocalDateTime.now());

		service.registrarPlazo6Horas(dto);

		assertNotNull(dto.getFechaEstimadaEntrega());
	}
}