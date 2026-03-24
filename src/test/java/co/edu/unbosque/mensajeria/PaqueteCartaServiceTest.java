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

import co.edu.unbosque.mensajeria.dto.PaqueteCartaDTO;
import co.edu.unbosque.mensajeria.entity.PaqueteCarta;
import co.edu.unbosque.mensajeria.repository.PaqueteCartaRepository;
import co.edu.unbosque.mensajeria.service.PaqueteCartaService;

/**
 * Clase de pruebas unitarias para {@link PaqueteCartaService}.
 * <p>
 * Esta clase valida el correcto funcionamiento de las operaciones CRUD y
 * métodos auxiliares del servicio encargado de la gestión de paquetes tipo
 * carta.
 * </p>
 * <p>
 * Se utilizan mocks de {@link PaqueteCartaRepository} para simular el acceso a
 * datos y {@link ModelMapper} para la conversión entre entidades y DTOs.
 * </p>
 * 
 * @author
 * @version 1.0
 */
class PaqueteCartaServiceTest {

	/**
	 * Mock del repositorio de paquetes carta.
	 */
	@Mock
	private PaqueteCartaRepository repository;

	/**
	 * Mapper para conversión entre entidad y DTO.
	 */
	private ModelMapper mapper;

	/**
	 * Servicio a probar.
	 */
	private PaqueteCartaService service;

	/**
	 * Objeto DTO de prueba.
	 */
	private PaqueteCartaDTO dto;

	/**
	 * Entidad de prueba.
	 */
	private PaqueteCarta entity;

	/**
	 * Método de inicialización que se ejecuta antes de cada prueba.
	 * <p>
	 * Configura los mocks, instancia el servicio y crea objetos de prueba con datos
	 * válidos según las reglas de negocio.
	 * </p>
	 */
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		mapper = new ModelMapper();

		service = new PaqueteCartaService();
		service.setPaqueteCartaRep(repository);
		service.setMapper(mapper);

		dto = new PaqueteCartaDTO();
		dto.setDireccionDestino("Calle 123 # 45-67");
		dto.setTipoCarta("ESTANDAR");
		dto.setTamanio("MEDIANO");
		dto.setCiudadDestino("Bogota");
		dto.setFechaCreacionPedido(LocalDateTime.now());

		entity = new PaqueteCarta();
		entity.setId(1L);
		entity.setDireccionDestino(dto.getDireccionDestino());
		entity.setTipoCarta(dto.getTipoCarta());
		entity.setTamanio(dto.getTamanio());
		entity.setCiudadDestino(dto.getCiudadDestino());
		entity.setFechaCreacionPedido(dto.getFechaCreacionPedido());
		entity.setEstadoPedido("EN_PROCESO");
		entity.setFechaEstimadaEntrega(LocalDateTime.now().plusDays(2));
		entity.setEsPrioritario(false);
	}

	/**
	 * Verifica la creación de un paquete carta.
	 * <p>
	 * Se simula el guardado en el repositorio y se valida que el resultado sea
	 * exitoso.
	 * </p>
	 */
	@Test
	void testCreate() {

		when(repository.save(any(PaqueteCarta.class))).thenReturn(entity);

		int result = service.create(dto);

		assertEquals(1, result);
		verify(repository).save(any(PaqueteCarta.class));
	}

	/**
	 * Verifica la obtención de todos los paquetes carta.
	 * <p>
	 * Se valida que la lista retornada no esté vacía y contenga los datos
	 * esperados.
	 * </p>
	 */
	@Test
	void testGetAll() {

		when(repository.findAll()).thenReturn(Arrays.asList(entity));
		when(repository.save(any(PaqueteCarta.class))).thenReturn(entity);

		List<PaqueteCartaDTO> result = service.getAll();

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

	/**
	 * Verifica la eliminación de un paquete existente por ID.
	 */
	@Test
	void testDeleteById_Exists() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		int result = service.deleteById(1L);

		assertEquals(0, result);
		verify(repository).delete(entity);
	}

	/**
	 * Verifica el comportamiento al intentar eliminar un paquete inexistente.
	 */
	@Test
	void testDeleteById_NotExists() {
		when(repository.findById(1L)).thenReturn(Optional.empty());

		int result = service.deleteById(1L);

		assertEquals(1, result);
	}

	/**
	 * Verifica la actualización de un paquete existente.
	 */
	@Test
	void testUpdateById_Exists() {

		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(any(PaqueteCarta.class))).thenReturn(entity);

		int result = service.updateById(1L, dto);

		assertEquals(1, result);
		verify(repository).save(any(PaqueteCarta.class));
	}

	/**
	 * Verifica el comportamiento al intentar actualizar un paquete inexistente.
	 */
	@Test
	void testUpdateById_NotExists() {

		when(repository.findById(1L)).thenReturn(Optional.empty());

		int result = service.updateById(1L, dto);

		assertEquals(0, result);
	}

	/**
	 * Verifica el conteo total de registros.
	 */
	@Test
	void testCount() {
		when(repository.count()).thenReturn(5L);

		assertEquals(5L, service.count());
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
	 * Verifica la búsqueda de un paquete por ID.
	 */
	@Test
	void testFindById() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		assertNotNull(service.findById(1L));
	}

	/**
	 * Verifica el cálculo del precio basado en el tamaño del paquete.
	 */
	@Test
	void testCalcularPrecioPorTamanio() {
		double result = service.calcularPrecioPorTamaño(5000, "MEDIANO");

		assertEquals(7000, result);
	}

	/**
	 * Verifica la aplicación de descuento según tipo de cliente.
	 */
	@Test
	void testAplicarDescuento() {
		double result = service.aplicarDescuentoPorCliente(10000, "PREMIUM");

		assertEquals(7500, result);
	}

	/**
	 * Verifica el registro automático de la fecha estimada de entrega (72 horas).
	 */
	@Test
	void testRegistrarPlazo72Horas() {
		dto.setFechaCreacionPedido(LocalDateTime.now());

		service.registrarPlazo72Horas(dto);

		assertNotNull(dto.getFechaEstimadaEntrega());
	}

	/**
	 * Verifica el procesamiento del estado del pedido basado en el tiempo.
	 */
	@Test
	void testProcesarEstado() {
		dto.setFechaEstimadaEntrega(LocalDateTime.now().minusHours(1));

		service.procesarEstadoYTiempoDTO(dto);

		assertEquals("ENTREGADO", dto.getEstadoPedido());
	}
}