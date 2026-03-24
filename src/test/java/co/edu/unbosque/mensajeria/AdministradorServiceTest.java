package co.edu.unbosque.mensajeria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import co.edu.unbosque.mensajeria.dto.AdministradorDTO;
import co.edu.unbosque.mensajeria.entity.Administrador;
import co.edu.unbosque.mensajeria.repository.AdministradorRepository;
import co.edu.unbosque.mensajeria.service.AdministradorService;

class AdministradorServiceTest {

	@Mock
	private AdministradorRepository administradorRep;

	private ModelMapper mapper;

	private AdministradorService administradorService;

	private AdministradorDTO dto;
	private Administrador entity;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		mapper = new ModelMapper();

		administradorService = new AdministradorService();
		administradorService.setAdministradorRep(administradorRep); 
		administradorService.setMapper(mapper);                     

		dto = new AdministradorDTO();
		dto.setNombre("admin");
		dto.setCedula("1234");
		dto.setCorreo("juan@mail.com");
		dto.setTelefono("3001234567");
		dto.setTurno('D');
		dto.setUsuario("juan");
		dto.setContrasenia("1234");

		entity = new Administrador();
		entity.setNombre(dto.getNombre());
		entity.setCedula(dto.getCedula());
		entity.setCorreo(dto.getCorreo());
		entity.setTelefono(dto.getTelefono());
		entity.setTurno(dto.getTurno());
		entity.setUsuario(dto.getUsuario());
		entity.setContrasenia(dto.getContrasenia());
	}

	@Test
	void testCreateSuccess() {
		when(administradorRep.existsByCedula(dto.getCedula())).thenReturn(false);

		int result = administradorService.create(dto);

		assertEquals(0, result);
		verify(administradorRep).save(any(Administrador.class));
	}

	@Test
	void testCreateDuplicado() {
		when(administradorRep.existsByCedula(dto.getCedula())).thenReturn(true);

		assertThrows(Exception.class, () -> administradorService.create(dto));
	}

	@Test
	void testGetAll() {
		List<Administrador> lista = Arrays.asList(entity);

		when(administradorRep.findAll()).thenReturn(lista);

		List<AdministradorDTO> result = administradorService.getAll();

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

	@Test
	void testDeleteByIdSuccess() {
		when(administradorRep.findById(1L)).thenReturn(Optional.of(entity));

		int result = administradorService.deleteById(1L);

		assertEquals(0, result);
		verify(administradorRep).delete(entity);
	}

	@Test
	void testDeleteByIdNotFound() {
		when(administradorRep.findById(1L)).thenReturn(Optional.empty());

		int result = administradorService.deleteById(1L);

		assertEquals(1, result);
	}

	@Test
	void testUpdateSuccess() {
		when(administradorRep.findById(1L)).thenReturn(Optional.of(entity));
		when(administradorRep.existsByCedula(dto.getCedula())).thenReturn(false);

		int result = administradorService.updateById(1L, dto);

		assertEquals(0, result);
		verify(administradorRep).save(any(Administrador.class));
	}

	@Test
	void testUpdateNotFound() {
		when(administradorRep.findById(1L)).thenReturn(Optional.empty());

		int result = administradorService.updateById(1L, dto);

		assertEquals(1, result);
	}

	@Test
	void testCount() {
		when(administradorRep.count()).thenReturn(5L);

		long result = administradorService.count();

		assertEquals(5, result);
	}

	@Test
	void testExist() {
		when(administradorRep.existsById(1L)).thenReturn(true);

		boolean result = administradorService.exist(1L);

		assertTrue(result);
	}

	@Test
	void testFindByNombre() {
		List<Administrador> lista = Arrays.asList(entity);

		when(administradorRep.findByNombre("admin")).thenReturn(Optional.of(lista));

		List<AdministradorDTO> result = administradorService.findByNombre("admin");

		assertFalse(result.isEmpty());
	}

	@Test
	void testFindByCedula() {
		List<Administrador> lista = Arrays.asList(entity);

		when(administradorRep.findByCedula("1234")).thenReturn(Optional.of(lista));

		List<AdministradorDTO> result = administradorService.findByCedula("1234");

		assertFalse(result.isEmpty());
	}
}