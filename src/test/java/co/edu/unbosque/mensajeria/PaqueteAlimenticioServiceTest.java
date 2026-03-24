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
 * Se validan las operaciones CRUD, métodos de búsqueda, cálculo de precios,
 * aplicación de descuentos y lógica de negocio relacionada con el manejo de
 * paquetes alimenticios.
 * </p>
 * 
 * <p>
 * Se utiliza Mockito para simular el comportamiento del repositorio y evitar
 * dependencias externas como la base de datos.
 * </p>
 * 
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
	 * Inicializa los mocks y objetos necesarios antes de cada prueba.
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
	}

	/**
	 * Verifica la creación exitosa de un paquete alimenticio.
	 */
	@Test
	void testCreateSuccess() {
		int result = service.create(dto);

		assertEquals(0, result);
		assertNotNull(dto.getFechaEstimadaEntrega());
		assertEquals("EN_PROCESO", dto.getEstadoPedido());
		verify(repository).save(any(PaqueteAlimenticio.class));
	}

	/**
	 * Verifica la obtención de todos los paquetes alimenticios.
	 */
	@Test
	void testGetAll() {
		List<PaqueteAlimenticio> lista = Arrays.asList(entity);

		when(repository.findAll()).thenReturn(lista);

		List<PaqueteAlimenticioDTO> result = service.getAll();

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

	/**
	 * Verifica la eliminación exitosa de un paquete por ID.
	 */
	@Test
	void testDeleteByIdSuccess() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		int result = service.deleteById(1L);

		assertEquals(0, result);
		verify(repository).delete(entity);
	}

	/**
	 * Verifica el comportamiento al intentar eliminar un paquete inexistente.
	 */
	@Test
	void testDeleteByIdNotFound() {
		when(repository.findById(1L)).thenReturn(Optional.empty());

		int result = service.deleteById(1L);

		assertEquals(1, result);
	}

	/**
	 * Verifica la actualización exitosa de un paquete existente.
	 */
	@Test
	void testUpdateSuccess() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		int result = service.updateById(1L, dto);

		assertEquals(0, result);
		verify(repository).save(any(PaqueteAlimenticio.class));
	}

	/**
	 * Verifica el comportamiento al actualizar un paquete inexistente.
	 */
	@Test
	void testUpdateNotFound() {
		when(repository.findById(1L)).thenReturn(Optional.empty());

		int result = service.updateById(1L, dto);

		assertEquals(1, result);
	}

	/**
	 * Verifica el conteo de registros en el repositorio.
	 */
	@Test
	void testCount() {
		when(repository.count()).thenReturn(10L);

		long result = service.count();

		assertEquals(10, result);
	}

	/**
	 * Verifica si un paquete existe por su ID.
	 */
	@Test
	void testExist() {
		when(repository.existsById(1L)).thenReturn(true);

		boolean result = service.exist(1L);

		assertTrue(result);
	}

	/**
	 * Verifica la búsqueda de paquetes por tamaño.
	 */
	@Test
	void testFindByTamanio() {
		List<PaqueteAlimenticio> lista = Arrays.asList(entity);

		when(repository.findByTamanio("MEDIANO")).thenReturn(Optional.of(lista));

		List<PaqueteAlimenticioDTO> result = service.findByTamanio("MEDIANO");

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

	/**
	 * Verifica la búsqueda de paquetes que se envían hoy.
	 */
	@Test
	void testFindBySeEnviaHoy() {
		List<PaqueteAlimenticio> lista = Arrays.asList(entity);

		when(repository.findBySeEnviaHoy(true)).thenReturn(Optional.of(lista));

		List<PaqueteAlimenticioDTO> result = service.findBySeEnviaHoy(true);

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

	/**
	 * Verifica la búsqueda de paquetes por tipo de alimento.
	 */
	@Test
	void testFindByTipoAlimento() {
		List<PaqueteAlimenticio> lista = Arrays.asList(entity);

		when(repository.findByTipoDeAlimento("FRUTA")).thenReturn(Optional.of(lista));

		List<PaqueteAlimenticioDTO> result = service.findByTipoDeAlimento("FRUTA");

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

	/**
	 * Verifica la búsqueda de un paquete por ID.
	 */
	@Test
	void testFindById() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		PaqueteAlimenticioDTO result = service.findById(1L);

		assertNotNull(result);
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
	 * Verifica la aplicación de descuentos para clientes.
	 */
	@Test
	void testDescuento() {
		double result = service.aplicarDescuentoPorCliente(10000, "PREMIUM");

		assertEquals(7500, result);
	}

	/**
	 * Verifica el cambio de estado a ENTREGADO cuando la fecha ya pasó.
	 */
	@Test
	void testProcesarEstado_Entregado() {
		dto.setFechaEstimadaEntrega(LocalDateTime.now().minusHours(1));

		service.procesarEstadoYTiempoDTO(dto);

		assertEquals("ENTREGADO", dto.getEstadoPedido());
		assertFalse(dto.isEsPrioritario());
	}

	/**
	 * Verifica que un paquete sea marcado como prioritario si está próximo a
	 * entregarse.
	 */
	@Test
	void testProcesarEstado_Prioritario() {
		dto.setFechaEstimadaEntrega(LocalDateTime.now().plusHours(2));

		service.procesarEstadoYTiempoDTO(dto);

		assertTrue(dto.isEsPrioritario());
	}

	/**
	 * Verifica el registro del plazo de entrega de 6 horas.
	 */
	@Test
	void testRegistrarPlazo() {
		dto.setFechaCreacionPedido(LocalDateTime.now());

		service.registrarPlazo6Horas(dto);

		assertNotNull(dto.getFechaEstimadaEntrega());
	}
}