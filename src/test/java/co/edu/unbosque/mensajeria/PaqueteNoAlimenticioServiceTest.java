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

import co.edu.unbosque.mensajeria.dto.PaqueteNoAlimenticioDTO;
import co.edu.unbosque.mensajeria.entity.PaqueteNoAlimenticio;
import co.edu.unbosque.mensajeria.repository.PaqueteNoAlimenticioRepository;
import co.edu.unbosque.mensajeria.service.PaqueteNoAlimenticioService;

/**
 * Clase de pruebas unitarias para {@link PaqueteNoAlimenticioService}.
 * 
 * <p>
 * Se validan las operaciones CRUD y métodos de negocio relacionados con
 * paquetes no alimenticios, utilizando Mockito para simular el repositorio.
 * </p>
 * 
 * @author Natalia D
 * @version 1.0
 */
class PaqueteNoAlimenticioServiceTest {

	/** Mock del repositorio */
	@Mock
	private PaqueteNoAlimenticioRepository repository;

	/** Mapper de objetos */
	private ModelMapper mapper;

	/** Servicio a probar */
	private PaqueteNoAlimenticioService service;

	/** DTO de prueba */
	private PaqueteNoAlimenticioDTO dto;

	/** Entidad de prueba */
	private PaqueteNoAlimenticio entity;

	/**
	 * Inicializa los datos antes de cada test.
	 */
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		mapper = new ModelMapper();

		service = new PaqueteNoAlimenticioService();
		service.setPaqueteNoAlimenticioRep(repository);
		service.setMapper(mapper);

		dto = new PaqueteNoAlimenticioDTO();
		dto.setDireccionDestino("Calle 123 # 45-67");
		dto.setTamanio("MEDIANO");
		dto.setCiudadDestino("Bogota");
		dto.setFechaCreacionPedido(LocalDateTime.now());
		dto.setEsFragil(true);

		entity = new PaqueteNoAlimenticio();
		entity.setId(1L);
		entity.setDireccionDestino(dto.getDireccionDestino());
		entity.setTamanio(dto.getTamanio());
		entity.setCiudadDestino(dto.getCiudadDestino());
		entity.setFechaCreacionPedido(dto.getFechaCreacionPedido());
		entity.setEstadoPedido("EN_PROCESO");
		entity.setFechaEstimadaEntrega(LocalDateTime.now().plusDays(1));
		entity.setEsFragil(true);
	}

	/**
	 * Verifica la creación de un paquete.
	 */
	@Test
	void testCreateSuccess() {

		when(repository.save(any(PaqueteNoAlimenticio.class))).thenReturn(entity);

		int result = service.create(dto);

		assertEquals(1, result);
		verify(repository).save(any(PaqueteNoAlimenticio.class));

		// ❌ EL DTO NO SE MODIFICA EN CREATE
	}

	/**
	 * Verifica la obtención de todos los registros.
	 */
	@Test
	void testGetAll() {

		when(repository.findAll()).thenReturn(Arrays.asList(entity));
		when(repository.save(any(PaqueteNoAlimenticio.class))).thenReturn(entity);

		List<PaqueteNoAlimenticioDTO> result = service.getAll();

		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

	/**
	 * Verifica eliminación cuando el registro existe.
	 */
	@Test
	void testDeleteByIdExists() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		int result = service.deleteById(1L);

		assertEquals(0, result);
		verify(repository).delete(entity);
	}

	/**
	 * Verifica eliminación cuando no existe.
	 */
	@Test
	void testDeleteByIdNotExists() {
		when(repository.findById(1L)).thenReturn(Optional.empty());

		int result = service.deleteById(1L);

		assertEquals(1, result);
	}

	/**
	 * Verifica actualización cuando el registro existe.
	 */
	@Test
	void testUpdateByIdExists() {

		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(any(PaqueteNoAlimenticio.class))).thenReturn(entity);

		int result = service.updateById(1L, dto);

		assertEquals(1, result); // ✅ CORREGIDO
		verify(repository).save(any(PaqueteNoAlimenticio.class));
	}

	/**
	 * Verifica actualización cuando el registro no existe.
	 */
	@Test
	void testUpdateByIdNotExists() {

		when(repository.findById(1L)).thenReturn(Optional.empty());

		int result = service.updateById(1L, dto);

		assertEquals(0, result); // ✅ CORREGIDO
	}

	/**
	 * Verifica el conteo total.
	 */
	@Test
	void testCount() {
		when(repository.count()).thenReturn(10L);

		long result = service.count();

		assertEquals(10L, result);
	}

	/**
	 * Verifica existencia por ID.
	 */
	@Test
	void testExist() {
		when(repository.existsById(1L)).thenReturn(true);

		boolean result = service.exist(1L);

		assertTrue(result);
	}

	/**
	 * Verifica búsqueda por ID.
	 */
	@Test
	void testFindById() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		PaqueteNoAlimenticioDTO result = service.findById(1L);

		assertNotNull(result);
	}

	/**
	 * Verifica cálculo de precio por tamaño.
	 */
	@Test
	void testCalcularPrecio() {
		double result = service.calcularPrecioPorTamaño(15000, "GRANDE");

		assertEquals(27000, result);
	}

	/**
	 * Verifica aplicación de descuento.
	 */
	@Test
	void testAplicarDescuento() {
		double result = service.aplicarDescuentoPorCliente(10000, "PREMIUM");

		assertEquals(7500, result);
	}

	/**
	 * Verifica asignación de plazo de 24 horas.
	 */
	@Test
	void testRegistrarPlazo24Horas() {
		dto.setFechaCreacionPedido(LocalDateTime.now());

		service.registrarPlazo24Horas(dto);

		assertNotNull(dto.getFechaEstimadaEntrega());
	}

	/**
	 * Verifica cambio de estado a ENTREGADO.
	 */
	@Test
	void testProcesarEstadoEntregado() {
		dto.setFechaEstimadaEntrega(LocalDateTime.now().minusHours(1));

		service.procesarEstadoYTiempoDTO(dto);

		assertEquals("ENTREGADO", dto.getEstadoPedido());
	}

	/**
	 * Verifica activación de prioridad.
	 */
	@Test
	void testProcesarEstadoPrioritario() {
		dto.setFechaEstimadaEntrega(LocalDateTime.now().plusHours(2));

		service.procesarEstadoYTiempoDTO(dto);

		assertTrue(dto.isEsPrioritario());
	}
}