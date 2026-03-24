package co.edu.unbosque.mensajeria;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import co.edu.unbosque.mensajeria.exception.*;
import co.edu.unbosque.mensajeria.util.LanzadorDeException;

class LanzadorDeExceptionTest {

	
	@Test
	void nombreValido() {
		assertDoesNotThrow(() -> LanzadorDeException.verificarNombre("Juan Perez"));
	}

	@Test
	void nombreInvalido() {
		assertThrows(NombreInvalidoException.class, () -> LanzadorDeException.verificarNombre("Juan"));
	}

	
	@Test
	void correoValido() {
		assertTrue(LanzadorDeException.verificarCorreoElectronico("test@mail.com"));
	}

	@Test
	void correoInvalido() {
		assertThrows(CorreoInvalidoException.class,
				() -> LanzadorDeException.verificarCorreoElectronico("correo-malo"));
	}


	@Test
	void cedulaValida() {
		assertDoesNotThrow(() -> LanzadorDeException.verificarCedula("12345678"));
	}

	@Test
	void cedulaInvalida() {
		assertThrows(CedulaInvalidaException.class, () -> LanzadorDeException.verificarCedula("12A34"));
	}

	
	@Test
	void telefonoValido() {
		assertDoesNotThrow(() -> LanzadorDeException.verificarTelefono("3001234567"));
	}

	@Test
	void telefonoInvalido() {
		assertThrows(TelefonoInvalidoException.class, () -> LanzadorDeException.verificarTelefono("123"));
	}

	
	@Test
	void direccionValida() {
		assertDoesNotThrow(() -> LanzadorDeException.verificarDireccion("Calle 10 # 20-30"));
	}

	@Test
	void direccionInvalida() {
		assertThrows(DireccionInvalidaException.class, () -> LanzadorDeException.verificarDireccion("Direccion mala"));
	}

	
	@Test
	void metodoPagoValido() {
		assertDoesNotThrow(() -> LanzadorDeException.verificarMetodoPago("EFECTIVO"));
	}

	@Test
	void metodoPagoInvalido() {
		assertThrows(MetodoDePagoInvalidoException.class, () -> LanzadorDeException.verificarMetodoPago("BITCOIN"));
	}

	
	@Test
	void tamanioValido() {
		assertDoesNotThrow(() -> LanzadorDeException.verificarTamanoPaquete("GRANDE"));
	}

	@Test
	void tamanioInvalido() {
		assertThrows(TamanioInvalidoException.class, () -> LanzadorDeException.verificarTamanoPaquete("ENORME"));
	}

	
	@Test
	void tipoAlimentoValido() {
		assertDoesNotThrow(() -> LanzadorDeException.verificarTipoAlimento("FRUTA"));
	}

	@Test
	void tipoAlimentoInvalido() {
		assertThrows(TipoDeAlimentoInvalidoException.class, () -> LanzadorDeException.verificarTipoAlimento("PIZZA"));
	}

	
	@Test
	void tipoCartaValido() {
		assertDoesNotThrow(() -> LanzadorDeException.verificarTipoCarta("CERTIFICADA"));
	}

	@Test
	void tipoCartaInvalido() {
		assertThrows(TipoDeCartaInvalidaException.class, () -> LanzadorDeException.verificarTipoCarta("RANDOM"));
	}

	
	@Test
	void turnoValido() {
		assertDoesNotThrow(() -> LanzadorDeException.verificarTurno('D'));
	}

	@Test
	void turnoInvalido() {
		assertThrows(TurnoInvalidoException.class, () -> LanzadorDeException.verificarTurno('X'));
	}

	
	@Test
	void idValido() {
		assertDoesNotThrow(() -> LanzadorDeException.verificarId(1L));
	}

	@Test
	void idInvalido() {
		assertThrows(IdInvalidoException.class, () -> LanzadorDeException.verificarId(-1L));
	}

	
	@Test
	void ciudadValida() {
		assertDoesNotThrow(() -> LanzadorDeException.verificarCiudad("Bogota"));
	}

	@Test
	void ciudadInvalida() {
		assertThrows(CiudadInvalidaException.class, () -> LanzadorDeException.verificarCiudad("1234"));
	}

	
	@Test
	void estadoValido() {
		assertDoesNotThrow(() -> LanzadorDeException.verificarEstadoPedido("EN_PROCESO"));
	}

	@Test
	void estadoInvalido() {
		assertThrows(EstadoPedidoInvalidoException.class, () -> LanzadorDeException.verificarEstadoPedido("CANCELADO"));
	}

	
	@Test
	void duplicadoInvalido() {
		assertThrows(CedulaInvalidaException.class, () -> LanzadorDeException.verificarDuplicado(true, "Ya existe"));
	}

	@Test
	void duplicadoValido() {
		assertDoesNotThrow(() -> LanzadorDeException.verificarDuplicado(false, "OK"));
	}
}