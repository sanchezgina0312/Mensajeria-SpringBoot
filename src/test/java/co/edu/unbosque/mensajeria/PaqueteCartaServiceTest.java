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

class PaqueteCartaServiceTest {

	@Mock
	private PaqueteCartaRepository repository;

	private ModelMapper mapper;

	private PaqueteCartaService service;

	private PaqueteCartaDTO dto;
	private PaqueteCarta entity;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		mapper = new ModelMapper();

		service = new PaqueteCartaService();
		service.setPaqueteCartaRep(repository); 
		service.setMapper(mapper); 

		dto = new PaqueteCartaDTO();
		dto.setDireccionDestino("Calle 123");
		dto.setTipoCarta("EXPRESS");
		dto.setTamanio("MEDIANO");
		dto.setCiudadDestino("Bogotá");
		dto.setFechaCreacionPedido(LocalDateTime.now());

		entity = new PaqueteCarta();
	}

	@Test
	void testCreate() {
		int result = service.create(dto);

		assertEquals(0, result);
		verify(repository).save(any(PaqueteCarta.class));
		assertNotNull(dto.getFechaEstimadaEntrega());
		assertEquals("EN_PROCESO", dto.getEstadoPedido());
	}

	@Test
	void testGetAll() {
		List<PaqueteCarta> list = Arrays.asList(entity);

		when(repository.findAll()).thenReturn(list);

		List<PaqueteCartaDTO> result = service.getAll();

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

	@Test
	void testDeleteById_Exists() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		int result = service.deleteById(1L);

		assertEquals(0, result);
		verify(repository).delete(entity);
	}

	@Test
	void testDeleteById_NotExists() {
		when(repository.findById(1L)).thenReturn(Optional.empty());

		int result = service.deleteById(1L);

		assertEquals(1, result);
	}

	@Test
	void testUpdateById_Exists() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		int result = service.updateById(1L, dto);

		assertEquals(0, result);
		verify(repository).save(any(PaqueteCarta.class));
	}

	@Test
	void testUpdateById_NotExists() {
		when(repository.findById(1L)).thenReturn(Optional.empty());

		int result = service.updateById(1L, dto);

		assertEquals(1, result);
	}

	@Test
	void testCount() {
		when(repository.count()).thenReturn(5L);

		long result = service.count();

		assertEquals(5L, result);
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

		PaqueteCartaDTO result = service.findById(1L);

		assertNotNull(result);
	}

	@Test
	void testCalcularPrecioPorTamanio() {
		double result = service.calcularPrecioPorTamaño(5000, "MEDIANO");

		assertEquals(7000, result);
	}

	@Test
	void testAplicarDescuento() {
		double result = service.aplicarDescuentoPorCliente(10000, "PREMIUM");

		assertEquals(7500, result);
	}

	@Test
	void testRegistrarPlazo72Horas() {
		dto.setFechaCreacionPedido(LocalDateTime.now());

		service.registrarPlazo72Horas(dto);

		assertNotNull(dto.getFechaEstimadaEntrega());
	}

	@Test
	void testProcesarEstado() {
		dto.setFechaEstimadaEntrega(LocalDateTime.now().minusHours(1));

		service.procesarEstadoYTiempoDTO(dto);

		assertEquals("ENTREGADO", dto.getEstadoPedido());
	}
}