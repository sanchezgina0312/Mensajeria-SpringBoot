package co.edu.unbosque.mensajeria;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import co.edu.unbosque.mensajeria.exception.*;
import co.edu.unbosque.mensajeria.util.LanzadorDeException;

/**
 * Clase de pruebas unitarias para la utilidad {@link LanzadorDeException}.
 * 
 * Esta clase valida el correcto funcionamiento de los métodos encargados de
 * verificar datos y lanzar excepciones personalizadas cuando los valores no
 * cumplen con las reglas de negocio.
 * 
 * Se prueban tanto casos válidos (sin excepción) como inválidos (con
 * excepción).
 */
class LanzadorDeExceptionTest {

	/**
	 * Verifica que un nombre válido no genere excepción.
	 */
	@Test
	void nombreValido() {
		assertDoesNotThrow(() -> LanzadorDeException.verificarNombre("Juan Perez"));
	}

	/**
	 * Verifica que un nombre inválido genere excepción.
	 */
	@Test
	void nombreInvalido() {
		assertThrows(NombreInvalidoException.class, () -> LanzadorDeException.verificarNombre("Juan"));
	}

	/**
	 * Verifica que un correo válido sea aceptado.
	 */
	@Test
	void correoValido() {
		assertTrue(LanzadorDeException.verificarCorreoElectronico("test@mail.com"));
	}

	/**
	 * Verifica que un correo inválido lance excepción.
	 */
	@Test
	void correoInvalido() {
		assertThrows(CorreoInvalidoException.class,
				() -> LanzadorDeException.verificarCorreoElectronico("correo-malo"));
	}

	/**
	 * Verifica que una cédula válida no genere excepción.
	 */
	@Test
	void cedulaValida() {
		assertDoesNotThrow(() -> LanzadorDeException.verificarCedula("12345678"));
	}

	/**
	 * Verifica que una cédula inválida genere excepción.
	 */
	@Test
	void cedulaInvalida() {
		assertThrows(CedulaInvalidaException.class, () -> LanzadorDeException.verificarCedula("12A34"));
	}

	/**
	 * Verifica que un teléfono válido no genere excepción.
	 */
	@Test
	void telefonoValido() {
		assertDoesNotThrow(() -> LanzadorDeException.verificarTelefono("3001234567"));
	}

	/**
	 * Verifica que un teléfono inválido genere excepción.
	 */
	@Test
	void telefonoInvalido() {
		assertThrows(TelefonoInvalidoException.class, () -> LanzadorDeException.verificarTelefono("123"));
	}

	/**
	 * Verifica que una dirección válida no genere excepción.
	 */
	@Test
	void direccionValida() {
		assertDoesNotThrow(() -> LanzadorDeException.verificarDireccion("Calle 10 # 20-30"));
	}

	/**
	 * Verifica que una dirección inválida genere excepción.
	 */
	@Test
	void direccionInvalida() {
		assertThrows(DireccionInvalidaException.class, () -> LanzadorDeException.verificarDireccion("Direccion mala"));
	}

	/**
	 * Verifica que un método de pago válido no genere excepción.
	 */
	@Test
	void metodoPagoValido() {
	    assertDoesNotThrow(() -> LanzadorDeException.verificarMetodoPago("PSE"));
	}

	/**
	 * Verifica que un método de pago inválido genere excepción.
	 */
	@Test
	void metodoPagoInvalido() {
		assertThrows(MetodoDePagoInvalidoException.class, () -> LanzadorDeException.verificarMetodoPago("BITCOIN"));
	}

	/**
	 * Verifica que un tamaño de paquete válido no genere excepción.
	 */
	@Test
	void tamanioValido() {
		assertDoesNotThrow(() -> LanzadorDeException.verificarTamanoPaquete("GRANDE"));
	}

	/**
	 * Verifica que un tamaño inválido genere excepción.
	 */
	@Test
	void tamanioInvalido() {
		assertThrows(TamanioInvalidoException.class, () -> LanzadorDeException.verificarTamanoPaquete("ENORME"));
	}

	/**
	 * Verifica que un tipo de alimento válido no genere excepción.
	 */
	@Test
	void tipoAlimentoValido() {
		assertDoesNotThrow(() -> LanzadorDeException.verificarTipoAlimento("FRUTA"));
	}

	/**
	 * Verifica que un tipo de alimento inválido genere excepción.
	 */
	@Test
	void tipoAlimentoInvalido() {
		assertThrows(TipoDeAlimentoInvalidoException.class, () -> LanzadorDeException.verificarTipoAlimento("PIZZA"));
	}

	/**
	 * Verifica que un tipo de carta válido no genere excepción.
	 */
	@Test
	void tipoCartaValido() {
		assertDoesNotThrow(() -> LanzadorDeException.verificarTipoCarta("CERTIFICADA"));
	}

	/**
	 * Verifica que un tipo de carta inválido genere excepción.
	 */
	@Test
	void tipoCartaInvalido() {
		assertThrows(TipoDeCartaInvalidaException.class, () -> LanzadorDeException.verificarTipoCarta("RANDOM"));
	}

	/**
	 * Verifica que un turno válido no genere excepción.
	 */
	@Test
	void turnoValido() {
		assertDoesNotThrow(() -> LanzadorDeException.verificarTurno('D'));
	}

	/**
	 * Verifica que un turno inválido genere excepción.
	 */
	@Test
	void turnoInvalido() {
		assertThrows(TurnoInvalidoException.class, () -> LanzadorDeException.verificarTurno('X'));
	}

	/**
	 * Verifica que un ID válido no genere excepción.
	 */
	@Test
	void idValido() {
		assertDoesNotThrow(() -> LanzadorDeException.verificarId(1L));
	}

	/**
	 * Verifica que un ID inválido genere excepción.
	 */
	@Test
	void idInvalido() {
		assertThrows(IdInvalidoException.class, () -> LanzadorDeException.verificarId(-1L));
	}

	/**
	 * Verifica que una ciudad válida no genere excepción.
	 */
	@Test
	void ciudadValida() {
		assertDoesNotThrow(() -> LanzadorDeException.verificarCiudad("Bogota"));
	}

	/**
	 * Verifica que una ciudad inválida genere excepción.
	 */
	@Test
	void ciudadInvalida() {
		assertThrows(CiudadInvalidaException.class, () -> LanzadorDeException.verificarCiudad("1234"));
	}

	/**
	 * Verifica que un estado de pedido válido no genere excepción.
	 */
	@Test
	void estadoValido() {
		assertDoesNotThrow(() -> LanzadorDeException.verificarEstadoPedido("EN_PROCESO"));
	}

	/**
	 * Verifica que un estado inválido genere excepción.
	 */
	@Test
	void estadoInvalido() {
		assertThrows(EstadoPedidoInvalidoException.class, () -> LanzadorDeException.verificarEstadoPedido("CANCELADO"));
	}

	/**
	 * Verifica que se lance excepción cuando existe duplicado.
	 */
	@Test
	void duplicadoInvalido() {
		assertThrows(CedulaInvalidaException.class, () -> LanzadorDeException.verificarDuplicado(true, "Ya existe"));
	}

	/**
	 * Verifica que no se lance excepción cuando no hay duplicado.
	 */
	@Test
	void duplicadoValido() {
		assertDoesNotThrow(() -> LanzadorDeException.verificarDuplicado(false, "OK"));
	}
}