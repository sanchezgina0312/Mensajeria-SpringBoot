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

class PaqueteAlimenticioServiceTest {

	@Mock
	private PaqueteAlimenticioRepository repository;

	private ModelMapper mapper;

	private PaqueteAlimenticioService service;

	private PaqueteAlimenticioDTO dto;
	private PaqueteAlimenticio entity;

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

	@Test
	void testCreateSuccess() {
		int result = service.create(dto);

		assertEquals(0, result);
		assertNotNull(dto.getFechaEstimadaEntrega());
		assertEquals("EN_PROCESO", dto.getEstadoPedido());
		verify(repository).save(any(PaqueteAlimenticio.class));
	}

	@Test
	void testGetAll() {
		List<PaqueteAlimenticio> lista = Arrays.asList(entity);

		when(repository.findAll()).thenReturn(lista);

		List<PaqueteAlimenticioDTO> result = service.getAll();

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

	@Test
	void testDeleteByIdSuccess() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		int result = service.deleteById(1L);

		assertEquals(0, result);
		verify(repository).delete(entity);
	}

	@Test
	void testDeleteByIdNotFound() {
		when(repository.findById(1L)).thenReturn(Optional.empty());

		int result = service.deleteById(1L);

		assertEquals(1, result);
	}

	@Test
	void testUpdateSuccess() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		int result = service.updateById(1L, dto);

		assertEquals(0, result);
		verify(repository).save(any(PaqueteAlimenticio.class));
	}

	@Test
	void testUpdateNotFound() {
		when(repository.findById(1L)).thenReturn(Optional.empty());

		int result = service.updateById(1L, dto);

		assertEquals(1, result);
	}

	@Test
	void testCount() {
		when(repository.count()).thenReturn(10L);

		long result = service.count();

		assertEquals(10, result);
	}

	@Test
	void testExist() {
		when(repository.existsById(1L)).thenReturn(true);

		boolean result = service.exist(1L);

		assertTrue(result);
	}

	@Test
	void testFindByTamanio() {
		List<PaqueteAlimenticio> lista = Arrays.asList(entity);

		when(repository.findByTamanio("MEDIANO")).thenReturn(Optional.of(lista));

		List<PaqueteAlimenticioDTO> result = service.findByTamanio("MEDIANO");

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

	@Test
	void testFindBySeEnviaHoy() {
		List<PaqueteAlimenticio> lista = Arrays.asList(entity);

		when(repository.findBySeEnviaHoy(true)).thenReturn(Optional.of(lista));

		List<PaqueteAlimenticioDTO> result = service.findBySeEnviaHoy(true);

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

	@Test
	void testFindByTipoAlimento() {
		List<PaqueteAlimenticio> lista = Arrays.asList(entity);

		when(repository.findByTipoDeAlimento("FRUTA")).thenReturn(Optional.of(lista));

		List<PaqueteAlimenticioDTO> result = service.findByTipoDeAlimento("FRUTA");

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

	@Test
	void testFindById() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		PaqueteAlimenticioDTO result = service.findById(1L);

		assertNotNull(result);
	}

	@Test
	void testCalcularPrecio() {
		double result = service.calcularPrecioPorTamaño(10000, "GRANDE");

		assertEquals(20000, result);
	}

	@Test
	void testDescuento() {
		double result = service.aplicarDescuentoPorCliente(10000, "PREMIUM");

		assertEquals(7500, result);
	}

	@Test
	void testProcesarEstado_Entregado() {
		dto.setFechaEstimadaEntrega(LocalDateTime.now().minusHours(1));

		service.procesarEstadoYTiempoDTO(dto);

		assertEquals("ENTREGADO", dto.getEstadoPedido());
		assertFalse(dto.isEsPrioritario());
	}

	@Test
	void testProcesarEstado_Prioritario() {
		dto.setFechaEstimadaEntrega(LocalDateTime.now().plusHours(2));

		service.procesarEstadoYTiempoDTO(dto);

		assertTrue(dto.isEsPrioritario());
	}

	@Test
	void testRegistrarPlazo() {
		dto.setFechaCreacionPedido(LocalDateTime.now());

		service.registrarPlazo6Horas(dto);

		assertNotNull(dto.getFechaEstimadaEntrega());
	}
}