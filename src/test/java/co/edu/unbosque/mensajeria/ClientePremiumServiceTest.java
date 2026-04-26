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

import co.edu.unbosque.mensajeria.dto.ClientePremiumDTO;
import co.edu.unbosque.mensajeria.entity.ClientePremium;
import co.edu.unbosque.mensajeria.repository.ClientePremiumRepository;
import co.edu.unbosque.mensajeria.service.ClientePremiumService;
import co.edu.unbosque.mensajeria.util.LanzadorDeException;

/**
 * Clase de pruebas unitarias para {@link ClientePremiumService}.
 *
 * <p>
 * Esta clase valida el correcto funcionamiento de las operaciones CRUD
 * y de búsqueda del servicio ClientePremium.
 * </p>
 *
 * <p>
 * Se utiliza Mockito para simular el comportamiento del repositorio
 * {@link ClientePremiumRepository}, evitando dependencias con la base de datos.
 * </p>
 *
 * <p>
 * También se hace uso de {@link MockedStatic} para controlar el comportamiento
 * de la clase {@link LanzadorDeException}, evitando que las validaciones
 * estáticas interfieran con la ejecución de las pruebas.
 * </p>
 *
 * <b>Casos probados:</b>
 * <ul>
 * <li>Creación exitosa de cliente</li>
 * <li>Creación con duplicado (lanza excepción)</li>
 * <li>Obtención de todos los registros</li>
 * <li>Eliminación de cliente existente</li>
 * <li>Eliminación de cliente inexistente</li>
 * <li>Actualización exitosa</li>
 * <li>Actualización de cliente no encontrado</li>
 * <li>Conteo de registros</li>
 * <li>Verificación de existencia por ID</li>
 * <li>Búsquedas por nombre, cédula, correo, teléfono y método de pago</li>
 * <li>Búsqueda combinada por nombre y cédula</li>
 * </ul>
 *
 */
class ClientePremiumServiceTest {

    /**
     * Mock del repositorio ClientePremiumRepository.
     * Simula el acceso a datos sin conexión real a base de datos.
     */
    @Mock
    private ClientePremiumRepository repo;

    /**
     * Mapper para conversión entre entidad y DTO.
     */
    private ModelMapper mapper;

    /**
     * Servicio que será probado.
     */
    private ClientePremiumService service;

    /**
     * DTO utilizado para pruebas.
     */
    private ClientePremiumDTO dto;

    /**
     * Entidad utilizada como referencia en pruebas.
     */
    private ClientePremium entity;

    /**
     * Método de configuración que se ejecuta antes de cada prueba.
     * Inicializa mocks, mapper, servicio y datos base.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mapper = new ModelMapper();
        service = new ClientePremiumService();

        service.setClientePremiumRep(repo);
        service.setMapper(mapper);

        dto = new ClientePremiumDTO();
        dto.setCedula("123456789");
        dto.setNombre("Juan Perez");
        dto.setCorreo("juan@gmail.com");
        dto.setTelefono("3123456789");
        dto.setMetodoPago("EFECTIVO");
        dto.setContrasenia("123456");

        entity = new ClientePremium();
        entity.setCedula(dto.getCedula());
        entity.setNombre(dto.getNombre());
        entity.setCorreo(dto.getCorreo());
        entity.setTelefono(dto.getTelefono());
        entity.setMetodoPago(dto.getMetodoPago());
        entity.setContrasenia(dto.getContrasenia());
    }

    /**
     * Verifica que un cliente se crea correctamente cuando no existe previamente.
     */
    @Test
    void testCreateSuccess() {
        try (MockedStatic<LanzadorDeException> mock = Mockito.mockStatic(LanzadorDeException.class)) {

            when(repo.existsByCedula(dto.getCedula())).thenReturn(false, false);

            int result = service.create(dto);

            assertEquals(0, result);
            verify(repo).save(any(ClientePremium.class));
        }
    }

    /**
     * Verifica que se lance una excepción cuando se intenta crear
     * un cliente con cédula duplicada.
     */
    @Test
    void testCreateDuplicado() {
        when(repo.existsByCedula(dto.getCedula())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> service.create(dto));
    }

    /**
     * Verifica la obtención de todos los clientes.
     */
    @Test
    void testGetAll() {
        when(repo.findAll()).thenReturn(Arrays.asList(entity));

        List<ClientePremiumDTO> result = service.getAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    /**
     * Verifica la eliminación exitosa de un cliente existente.
     */
    @Test
    void testDeleteSuccess() {
        try (MockedStatic<LanzadorDeException> mock = Mockito.mockStatic(LanzadorDeException.class)) {

            when(repo.findById(1L)).thenReturn(Optional.of(entity));

            int result = service.deleteById(1L);

            assertEquals(0, result);
            verify(repo).delete(entity);
        }
    }

    /**
     * Verifica el comportamiento cuando se intenta eliminar un cliente inexistente.
     */
    @Test
    void testDeleteNotFound() {
        try (MockedStatic<LanzadorDeException> mock = Mockito.mockStatic(LanzadorDeException.class)) {

            when(repo.findById(1L)).thenReturn(Optional.empty());

            int result = service.deleteById(1L);

            assertEquals(1, result);
        }
    }

    /**
     * Verifica la actualización exitosa de un cliente existente.
     */
    @Test
    void testUpdateSuccess() {
        try (MockedStatic<LanzadorDeException> mock = Mockito.mockStatic(LanzadorDeException.class)) {

            when(repo.findById(1L)).thenReturn(Optional.of(entity));
            when(repo.existsByCedula(dto.getCedula())).thenReturn(false);

            int result = service.updateById(1L, dto);

            assertEquals(0, result);
            verify(repo).save(any(ClientePremium.class));
        }
    }

    /**
     * Verifica el comportamiento cuando se intenta actualizar un cliente inexistente.
     */
    @Test
    void testUpdateNotFound() {
        try (MockedStatic<LanzadorDeException> mock = Mockito.mockStatic(LanzadorDeException.class)) {

            when(repo.findById(1L)).thenReturn(Optional.empty());

            int result = service.updateById(1L, dto);

            assertEquals(1, result);
        }
    }

    /**
     * Verifica el conteo total de registros.
     */
    @Test
    void testCount() {
        when(repo.count()).thenReturn(10L);

        long result = service.count();

        assertEquals(10, result);
    }

    /**
     * Verifica si existe un cliente por ID.
     */
    @Test
    void testExist() {
        when(repo.existsById(1L)).thenReturn(true);

        boolean result = service.exist(1L);

        assertTrue(result);
    }

    /**
     * Verifica la búsqueda de clientes por nombre.
     */
    @Test
    void testFindByNombre() {
        try (MockedStatic<LanzadorDeException> mock = Mockito.mockStatic(LanzadorDeException.class)) {

            when(repo.findByNombre(dto.getNombre())).thenReturn(Optional.of(Arrays.asList(entity)));

            assertFalse(service.findByNombre(dto.getNombre()).isEmpty());
        }
    }

    /**
     * Verifica la búsqueda de clientes por cédula.
     */
    @Test
    void testFindByCedula() {
        try (MockedStatic<LanzadorDeException> mock = Mockito.mockStatic(LanzadorDeException.class)) {

            when(repo.findByCedula(dto.getCedula())).thenReturn(Optional.of(Arrays.asList(entity)));

            assertFalse(service.findByCedula(dto.getCedula()).isEmpty());
        }
    }

    /**
     * Verifica la búsqueda de clientes por correo electrónico.
     */
    @Test
    void testFindByCorreo() {
        try (MockedStatic<LanzadorDeException> mock = Mockito.mockStatic(LanzadorDeException.class)) {

            when(repo.findByCorreo(dto.getCorreo())).thenReturn(Optional.of(Arrays.asList(entity)));

            assertFalse(service.findByCorreo(dto.getCorreo()).isEmpty());
        }
    }

    /**
     * Verifica la búsqueda de clientes por número de teléfono.
     */
    @Test
    void testFindByTelefono() {
        try (MockedStatic<LanzadorDeException> mock = Mockito.mockStatic(LanzadorDeException.class)) {

            when(repo.findByTelefono(dto.getTelefono())).thenReturn(Optional.of(Arrays.asList(entity)));

            assertFalse(service.findByTelefono(dto.getTelefono()).isEmpty());
        }
    }

    /**
     * Verifica la búsqueda de clientes por método de pago.
     */
    @Test
    void testFindByMetodoPago() {
        try (MockedStatic<LanzadorDeException> mock = Mockito.mockStatic(LanzadorDeException.class)) {

            when(repo.findByMetodoPago(dto.getMetodoPago())).thenReturn(Optional.of(Arrays.asList(entity)));

            assertFalse(service.findByMetodoPago(dto.getMetodoPago()).isEmpty());
        }
    }

    /**
     * Verifica la búsqueda combinada por nombre y cédula.
     */
    @Test
    void testFindByNombreAndCedula() {
        try (MockedStatic<LanzadorDeException> mock = Mockito.mockStatic(LanzadorDeException.class)) {

            when(repo.findByNombreAndCedula(dto.getNombre(), dto.getCedula()))
                    .thenReturn(Optional.of(Arrays.asList(entity)));

            assertFalse(service.findByNombreAndCedula(dto.getNombre(), dto.getCedula()).isEmpty());
        }
    }
}