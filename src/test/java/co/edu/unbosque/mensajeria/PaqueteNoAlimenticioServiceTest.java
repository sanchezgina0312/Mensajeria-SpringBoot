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

class PaqueteNoAlimenticioServiceTest {

	@Mock
	private PaqueteNoAlimenticioRepository repository;

	private ModelMapper mapper;

	private PaqueteNoAlimenticioService service;

	private PaqueteNoAlimenticioDTO dto;
	private PaqueteNoAlimenticio entity;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		mapper = new ModelMapper();

		service = new PaqueteNoAlimenticioService();
		service.setPaqueteNoAlimenticioRep(repository); 
		service.setMapper(mapper); 

		dto = new PaqueteNoAlimenticioDTO();
		dto.setDireccionDestino("Calle 123");
		dto.setTamanio("MEDIANO");
		dto.setCiudadDestino("Bogotá");
		dto.setFechaCreacionPedido(LocalDateTime.now());
		dto.setEsFragil(true);

		entity = new PaqueteNoAlimenticio();
	}

	@Test
	void testCreateSuccess() {
		int result = service.create(dto);

		assertEquals(1, result);

		verify(repository).save(any(PaqueteNoAlimenticio.class));
		assertNotNull(dto.getFechaEstimadaEntrega());
		assertEquals("EN_PROCESO", dto.getEstadoPedido());
	}

	@Test
	void testGetAll() {
		List<PaqueteNoAlimenticio> list = Arrays.asList(entity);

		when(repository.findAll()).thenReturn(list);

		List<PaqueteNoAlimenticioDTO> result = service.getAll();

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

	@Test
	void testDeleteByIdExists() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		int result = service.deleteById(1L);

		assertEquals(0, result);
		verify(repository).delete(entity);
	}

	@Test
	void testDeleteByIdNotExists() {
		when(repository.findById(1L)).thenReturn(Optional.empty());

		int result = service.deleteById(1L);

		assertEquals(1, result);
	}

	@Test
	void testUpdateByIdExists() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		int result = service.updateById(1L, dto);

		assertEquals(0, result);
		verify(repository).save(any(PaqueteNoAlimenticio.class));
	}

	@Test
	void testUpdateByIdNotExists() {
		when(repository.findById(1L)).thenReturn(Optional.empty());

		int result = service.updateById(1L, dto);

		assertEquals(1, result);
	}

	@Test
	void testCount() {
		when(repository.count()).thenReturn(10L);

		long result = service.count();

		assertEquals(10L, result);
	}

	@Test
	void testExist() {
		when(repository.existsById(1L)).thenReturn(true);

		boolean result = service.exist(1L);

		assertTrue(result);
	}

	@Test
	void testFindById() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		PaqueteNoAlimenticioDTO result = service.findById(1L);

		assertNotNull(result);
	}

	@Test
	void testCalcularPrecio() {
		double result = service.calcularPrecioPorTamaño(15000, "GRANDE");

		assertEquals(27000, result);
	}

	@Test
	void testAplicarDescuento() {
		double result = service.aplicarDescuentoPorCliente(10000, "PREMIUM");

		assertEquals(7500, result);
	}

	@Test
	void testRegistrarPlazo24Horas() {
		dto.setFechaCreacionPedido(LocalDateTime.now());

		service.registrarPlazo24Horas(dto);

		assertNotNull(dto.getFechaEstimadaEntrega());
	}

	@Test
	void testProcesarEstadoEntregado() {
		dto.setFechaEstimadaEntrega(LocalDateTime.now().minusHours(1));

		service.procesarEstadoYTiempoDTO(dto);

		assertEquals("ENTREGADO", dto.getEstadoPedido());
	}

	@Test
	void testProcesarEstadoPrioritario() {
		dto.setFechaEstimadaEntrega(LocalDateTime.now().plusHours(2));

		service.procesarEstadoYTiempoDTO(dto);

		assertTrue(dto.isEsPrioritario());
	}
}