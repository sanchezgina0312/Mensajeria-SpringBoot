package co.edu.unbosque.mensajeria.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.edu.unbosque.mensajeria.exception.CedulaInvalidaException;
import co.edu.unbosque.mensajeria.exception.CiudadInvalidaException;
import co.edu.unbosque.mensajeria.exception.ContraseniaInvalidaException;
import co.edu.unbosque.mensajeria.exception.CorreoInvalidoException;
import co.edu.unbosque.mensajeria.exception.DireccionInvalidaException;
import co.edu.unbosque.mensajeria.exception.EstadoPedidoInvalidoException;
import co.edu.unbosque.mensajeria.exception.IdInvalidoException;
import co.edu.unbosque.mensajeria.exception.MetodoDePagoInvalidoException;
import co.edu.unbosque.mensajeria.exception.NombreInvalidoException;
import co.edu.unbosque.mensajeria.exception.PlacaInvalidaException;
import co.edu.unbosque.mensajeria.exception.TamanioInvalidoException;
import co.edu.unbosque.mensajeria.exception.TelefonoInvalidoException;
import co.edu.unbosque.mensajeria.exception.TipoDeAlimentoInvalidoException;
import co.edu.unbosque.mensajeria.exception.TipoDeCartaInvalidaException;
import co.edu.unbosque.mensajeria.exception.TipoManipuladorInvalidoException;
import co.edu.unbosque.mensajeria.exception.TipoPedidoInvalidoException;
import co.edu.unbosque.mensajeria.exception.TurnoInvalidoException;

/**
 * Clase utilitaria que centraliza la validación de datos del dominio del sistema de mensajería.
 * <p>
 * Cada método verifica las reglas de negocio correspondientes a un campo específico
 * y lanza una excepción personalizada en caso de que el valor no cumpla con los requisitos.
 * Todos los métodos son estáticos, por lo que no es necesario instanciar la clase.
 * </p>
 *
 * @author Angie Villarreal
 * @version 1.0
 */
public class LanzadorDeException {

	/**
	 * Verifica que el nombre proporcionado sea válido según las reglas del sistema.
	 * <p>
	 * El nombre debe cumplir las siguientes condiciones:
	 * <ul>
	 *   <li>No contener espacios dobles.</li>
	 *   <li>Contener únicamente letras (incluyendo tildes y ñ) y espacios simples.</li>
	 *   <li>Tener al menos dos palabras (nombre y apellido).</li>
	 * </ul>
	 * </p>
	 *
	 * @param nombre el nombre completo a validar.
	 * @throws NombreInvalidoException si el nombre contiene espacios dobles, caracteres no permitidos
	 *                                  o tiene menos de dos palabras.
	 */
	public static void verificarNombre(String nombre) {
		if (nombre.contains("  ")) {
			throw new NombreInvalidoException("El nombre no puede contener espacios dobles");
		}
		if (!nombre.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
			throw new NombreInvalidoException("El nombre solo debe contener letras y espacios");
		}
		String[] palabras = nombre.trim().split("\\s+");
		if (palabras.length < 2) {
			throw new NombreInvalidoException("El nombre debe tener al menos dos palabras");
		}
	}

	/**
	 * Verifica que el correo electrónico proporcionado tenga un formato válido.
	 * <p>
	 * El correo debe seguir el patrón estándar: {@code usuario@dominio.extension},
	 * donde la extensión debe tener entre 2 y 6 caracteres alfabéticos.
	 * Ejemplo válido: {@code usuario@dominio.com}
	 * </p>
	 *
	 * @param correo la dirección de correo electrónico a validar.
	 * @return {@code true} si el correo tiene un formato válido.
	 * @throws CorreoInvalidoException si el correo no cumple con el formato esperado.
	 */
	public static boolean verificarCorreoElectronico(String correo) {
		Pattern pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
		Matcher matcher = pattern.matcher(correo);

		if (matcher.find()) {
			return true;
		} else {
			throw new CorreoInvalidoException(
					"El correo electrónico ingresado no es válido. Verifique el formato (ejemplo: usuario@dominio.com).");
		}
	}

	/**
	 * Verifica que el número de cédula proporcionado sea válido.
	 * <p>
	 * La cédula debe cumplir las siguientes condiciones:
	 * <ul>
	 *   <li>No ser nula ni vacía.</li>
	 *   <li>No contener espacios.</li>
	 *   <li>Contener únicamente dígitos numéricos.</li>
	 *   <li>Tener entre 6 y 10 dígitos.</li>
	 * </ul>
	 * </p>
	 *
	 * @param cedula el número de cédula a validar.
	 * @throws CedulaInvalidaException si la cédula es nula, vacía, contiene espacios,
	 *                                  caracteres no numéricos o no tiene la longitud esperada.
	 */
	public static void verificarCedula(String cedula) {

		if (cedula == null || cedula.isEmpty()) {
			throw new CedulaInvalidaException("La cédula no puede estar vacía");
		}

		if (cedula.contains(" ")) {
			throw new CedulaInvalidaException("La cédula no debe contener espacios");
		}

		if (!cedula.matches("^[0-9]+$")) {
			throw new CedulaInvalidaException("La cédula solo debe contener números");
		}

		if (cedula.length() < 6 || cedula.length() > 10) {
			throw new CedulaInvalidaException("La cédula debe tener entre 6 y 10 dígitos");
		}
	}

	/**
	 * Verifica que la dirección proporcionada sea válida según el formato colombiano.
	 * <p>
	 * La dirección debe cumplir las siguientes condiciones:
	 * <ul>
	 *   <li>No ser nula ni vacía.</li>
	 *   <li>No contener espacios dobles.</li>
	 *   <li>Contener únicamente letras, números, {@code #}, {@code -}, {@code .} y espacios.</li>
	 *   <li>Seguir el formato colombiano, iniciando con un tipo de vía reconocido
	 *       (Calle, Carrera, Transversal, Diagonal, Avenida, Av, Cl, Cra, Tv, Dg)
	 *       seguido del número y la nomenclatura {@code # número-número}.</li>
	 *   <li>Tener entre 5 y 100 caracteres.</li>
	 * </ul>
	 * Ejemplos válidos: {@code Calle 123 # 45-67}, {@code Cra 7 # 32-16}, {@code Av 68 # 10-45 apto 302}
	 * </p>
	 *
	 * @param direccion la dirección a validar.
	 * @throws DireccionInvalidaException si la dirección es nula, vacía, contiene caracteres inválidos,
	 *                                     no sigue el formato colombiano o excede los límites de longitud.
	 */
	public static void verificarDireccion(String direccion) {

		if (direccion == null || direccion.isEmpty()) {
			throw new DireccionInvalidaException("La dirección no puede estar vacía");
		}

		if (direccion.contains("  ")) {
			throw new DireccionInvalidaException("La dirección no puede contener espacios dobles");
		}

		if (!direccion.matches("^[A-Za-z0-9#\\-\\.\\s]+$")) {
			throw new DireccionInvalidaException("La dirección contiene caracteres inválidos");
		}

		if (!direccion.matches(
				"^(Calle|Carrera|Transversal|Diagonal|Avenida|Av|Cl|Cra|Tv|Dg)\\s+[0-9A-Za-z]+\\s+#\\s*[0-9A-Za-z]+-\\s*[0-9A-Za-z]+.*$")) {
			throw new DireccionInvalidaException("La dirección no cumple con un formato válido en Colombia");
		}

		if (direccion.length() < 5 || direccion.length() > 100) {
			throw new DireccionInvalidaException("La dirección debe tener entre 5 y 100 caracteres");
		}
	}

	/**
	 * Verifica que el método de pago proporcionado sea uno de los aceptados por el sistema.
	 * <p>
	 * Los métodos de pago válidos son (sin distinción de mayúsculas/minúsculas):
	 * <ul>
	 *   <li>{@code EFECTIVO}</li>
	 *   <li>{@code TARJETA_CREDITO}</li>
	 *   <li>{@code TARJETA_DEBITO}</li>
	 *   <li>{@code NEQUI}</li>
	 *   <li>{@code DAVIPLATA}</li>
	 *   <li>{@code TRANSFERENCIA}</li>
	 * </ul>
	 * </p>
	 *
	 * @param metodoPago el método de pago a validar.
	 * @throws MetodoDePagoInvalidoException si el método de pago es nulo, vacío, contiene espacios
	 *                                        o no corresponde a ninguno de los valores permitidos.
	 */
	public static void verificarMetodoPago(String metodoPago) {

		if (metodoPago == null || metodoPago.isEmpty()) {
			throw new MetodoDePagoInvalidoException("El método de pago no puede estar vacío");
		}

		if (metodoPago.contains(" ")) {
			throw new MetodoDePagoInvalidoException("El método de pago no debe contener espacios");
		}

		metodoPago = metodoPago.toUpperCase();

		if (!metodoPago.equals("EFECTIVO") && !metodoPago.equals("TARJETA_CREDITO")
				&& !metodoPago.equals("TARJETA_DEBITO") && !metodoPago.equals("NEQUI")
				&& !metodoPago.equals("DAVIPLATA") && !metodoPago.equals("TRANSFERENCIA")) {

			throw new MetodoDePagoInvalidoException("Método de pago no válido");
		}
	}

	/**
	 * Verifica que la placa vehicular proporcionada sea válida según el formato colombiano.
	 * <p>
	 * La placa debe cumplir las siguientes condiciones:
	 * <ul>
	 *   <li>No ser nula ni vacía.</li>
	 *   <li>No contener espacios.</li>
	 *   <li>Seguir el formato colombiano: tres letras mayúsculas seguidas de tres dígitos
	 *       (ejemplo: {@code ABC123}).</li>
	 * </ul>
	 * </p>
	 *
	 * @param placa la placa vehicular a validar.
	 * @throws PlacaInvalidaException si la placa es nula, vacía, contiene espacios
	 *                                 o no sigue el formato {@code ABC123}.
	 */
	public static void verificarPlaca(String placa) {

		if (placa == null || placa.isEmpty()) {
			throw new PlacaInvalidaException("La placa no puede estar vacía");
		}

		if (placa.contains(" ")) {
			throw new PlacaInvalidaException("La placa no debe contener espacios");
		}

		placa = placa.toUpperCase();

		if (!placa.matches("^[A-Z]{3}[0-9]{3}$")) {
			throw new PlacaInvalidaException("La placa no cumple con el formato colombiano (ABC123)");
		}
	}

	/**
	 * Verifica que el tamaño del paquete sea uno de los valores aceptados por el sistema.
	 * <p>
	 * Los tamaños válidos son (sin distinción de mayúsculas/minúsculas):
	 * <ul>
	 *   <li>{@code PEQUENO}</li>
	 *   <li>{@code MEDIANO}</li>
	 *   <li>{@code GRANDE}</li>
	 * </ul>
	 * </p>
	 *
	 * @param tamano el tamaño del paquete a validar.
	 * @throws TamanioInvalidoException si el tamaño es nulo, vacío, contiene espacios
	 *                                   o no corresponde a ninguno de los valores permitidos.
	 */
	public static void verificarTamanoPaquete(String tamano) {

		if (tamano == null || tamano.isEmpty()) {
			throw new TamanioInvalidoException("El tamaño del paquete no puede estar vacío");
		}

		if (tamano.contains(" ")) {
			throw new TamanioInvalidoException("El tamaño no debe contener espacios");
		}

		tamano = tamano.toUpperCase();

		if (!tamano.equals("PEQUENO") && !tamano.equals("MEDIANO") && !tamano.equals("GRANDE")) {

			throw new TamanioInvalidoException("Tamaño de paquete no válido");
		}
	}

	/**
	 * Verifica que el número de teléfono proporcionado sea válido para Colombia.
	 * <p>
	 * El teléfono debe cumplir las siguientes condiciones:
	 * <ul>
	 *   <li>No ser nulo ni vacío.</li>
	 *   <li>No contener espacios.</li>
	 *   <li>Contener únicamente dígitos numéricos.</li>
	 *   <li>Tener exactamente 10 dígitos.</li>
	 *   <li>Iniciar con el dígito {@code 3} (formato de celular colombiano).</li>
	 * </ul>
	 * </p>
	 *
	 * @param telefono el número de teléfono a validar.
	 * @throws TelefonoInvalidoException si el teléfono es nulo, vacío, contiene espacios,
	 *                                    caracteres no numéricos, no tiene 10 dígitos
	 *                                    o no inicia con {@code 3}.
	 */
	public static void verificarTelefono(String telefono) {

		if (telefono == null || telefono.isEmpty()) {
			throw new TelefonoInvalidoException("El número de teléfono no puede estar vacío");
		}

		if (telefono.contains(" ")) {
			throw new TelefonoInvalidoException("El número no debe contener espacios");
		}

		if (!telefono.matches("^[0-9]+$")) {
			throw new TelefonoInvalidoException("El número solo debe contener dígitos");
		}

		if (telefono.length() != 10) {
			throw new TelefonoInvalidoException("El número debe tener exactamente 10 dígitos");
		}

		if (!telefono.startsWith("3")) {
			throw new TelefonoInvalidoException("El número debe iniciar con 3 (celular en Colombia)");
		}
	}

	/**
	 * Verifica que el tipo de alimento proporcionado sea uno de los valores aceptados por el sistema.
	 * <p>
	 * Los tipos de alimento válidos son (sin distinción de mayúsculas/minúsculas):
	 * <ul>
	 *   <li>{@code FRUTA}</li>
	 *   <li>{@code VERDURA}</li>
	 *   <li>{@code CARNE}</li>
	 *   <li>{@code LACTEO}</li>
	 *   <li>{@code CEREAL}</li>
	 *   <li>{@code BEBIDA}</li>
	 * </ul>
	 * </p>
	 *
	 * @param tipo el tipo de alimento a validar.
	 * @throws TipoDeAlimentoInvalidoException si el tipo es nulo, vacío, contiene espacios
	 *                                          o no corresponde a ninguno de los valores permitidos.
	 */
	public static void verificarTipoAlimento(String tipo) {

		if (tipo == null || tipo.isEmpty()) {
			throw new TipoDeAlimentoInvalidoException("El tipo de alimento no puede estar vacío");
		}

		if (tipo.contains(" ")) {
			throw new TipoDeAlimentoInvalidoException("El tipo no debe contener espacios");
		}

		tipo = tipo.toUpperCase();

		if (!tipo.equals("FRUTA") && !tipo.equals("VERDURA") && !tipo.equals("CARNE") && !tipo.equals("LACTEO")
				&& !tipo.equals("CEREAL") && !tipo.equals("BEBIDA")) {

			throw new TipoDeAlimentoInvalidoException("Tipo de alimento no válido");
		}
	}

	/**
	 * Verifica que el tipo de carta proporcionado sea uno de los valores aceptados por el sistema.
	 * <p>
	 * Los tipos de carta válidos son (sin distinción de mayúsculas/minúsculas):
	 * <ul>
	 *   <li>{@code ESTANDAR}</li>
	 *   <li>{@code CERTIFICADA}</li>
	 *   <li>{@code JURIDICA}</li>
	 *   <li>{@code PUBLICITARIA}</li>
	 * </ul>
	 * </p>
	 *
	 * @param tipo el tipo de carta a validar.
	 * @throws TipoDeCartaInvalidaException si el tipo es nulo, vacío, contiene espacios
	 *                                       o no corresponde a ninguno de los valores permitidos.
	 */
	public static void verificarTipoCarta(String tipo) {

		if (tipo == null || tipo.isEmpty()) {
			throw new TipoDeCartaInvalidaException("El tipo de carta no puede estar vacío");
		}

		if (tipo.contains(" ")) {
			throw new TipoDeCartaInvalidaException("El tipo no debe contener espacios");
		}

		tipo = tipo.toUpperCase();

		if (!tipo.equals("ESTANDAR") && !tipo.equals("CERTIFICADA") && !tipo.equals("JURIDICA")
				&& !tipo.equals("PUBLICITARIA")) {

			throw new TipoDeCartaInvalidaException("Tipo de carta no válido");
		}
	}

	/**
	 * Verifica que el tipo de manipulador proporcionado sea uno de los valores aceptados por el sistema.
	 * <p>
	 * Los tipos de manipulador válidos son (sin distinción de mayúsculas/minúsculas):
	 * <ul>
	 *   <li>{@code PAQUETES_ALIMENTICIOS}</li>
	 *   <li>{@code PAQUETES_NO_ALIMENTICIOS}</li>
	 *   <li>{@code CARTAS}</li>
	 * </ul>
	 * </p>
	 *
	 * @param tipo el tipo de manipulador a validar.
	 * @throws TipoManipuladorInvalidoException si el tipo es nulo, vacío, contiene espacios
	 *                                           o no corresponde a ninguno de los valores permitidos.
	 */
	public static void verificarTipoManipulador(String tipo) {

		if (tipo == null || tipo.isEmpty()) {
			throw new TipoManipuladorInvalidoException("El tipo de manipulador no puede estar vacío");
		}

		if (tipo.contains(" ")) {
			throw new TipoManipuladorInvalidoException("El tipo no debe contener espacios");
		}

		tipo = tipo.toUpperCase();

		if (!tipo.equals("PAQUETES_ALIMENTICIOS") && !tipo.equals("PAQUETES_NO_ALIMENTICIOS")
				&& !tipo.equals("CARTAS")) {

			throw new TipoManipuladorInvalidoException("Tipo de manipulador no válido");
		}
	}

	/**
	 * Verifica que el tipo de pedido proporcionado sea uno de los valores aceptados por el sistema.
	 * <p>
	 * Los tipos de pedido válidos son (sin distinción de mayúsculas/minúsculas):
	 * <ul>
	 *   <li>{@code ALIMENTICIO}</li>
	 *   <li>{@code NO_ALIMENTICIO}</li>
	 *   <li>{@code CARTA}</li>
	 * </ul>
	 * </p>
	 *
	 * @param tipo el tipo de pedido a validar.
	 * @throws TipoPedidoInvalidoException si el tipo es nulo, vacío, contiene espacios
	 *                                      o no corresponde a ninguno de los valores permitidos.
	 */
	public static void verificarTipoPedido(String tipo) {

		if (tipo == null || tipo.isEmpty()) {
			throw new TipoPedidoInvalidoException("El tipo de pedido no puede estar vacío");
		}

		if (tipo.contains(" ")) {
			throw new TipoPedidoInvalidoException("El tipo no debe contener espacios");
		}

		tipo = tipo.toUpperCase();

		if (!tipo.equals("ALIMENTICIO") && !tipo.equals("NO_ALIMENTICIO") && !tipo.equals("CARTA")) {

			throw new TipoPedidoInvalidoException("Tipo de pedido no válido");
		}
	}

	/**
	 * Verifica que el turno proporcionado sea uno de los valores aceptados por el sistema.
	 * <p>
	 * Los turnos válidos son (sin distinción de mayúsculas/minúsculas):
	 * <ul>
	 *   <li>{@code D} - Turno de día.</li>
	 *   <li>{@code N} - Turno de noche.</li>
	 *   <li>{@code M} - Turno mixto.</li>
	 * </ul>
	 * </p>
	 *
	 * @param turno el carácter que representa el turno a validar.
	 * @throws TurnoInvalidoException si el turno no corresponde a {@code D}, {@code N} ni {@code M}.
	 */
	public static void verificarTurno(char turno) {

		turno = Character.toUpperCase(turno);

		if (turno != 'D' && turno != 'N' && turno != 'M') {
			throw new TurnoInvalidoException("Turno no válido. Use D, N o M");
		}
	}

	/**
	 * Verifica que el ID proporcionado sea válido.
	 * <p>
	 * El ID debe cumplir las siguientes condiciones:
	 * <ul>
	 *   <li>No ser nulo.</li>
	 *   <li>Ser un número positivo (mayor que cero).</li>
	 * </ul>
	 * </p>
	 *
	 * @param id el identificador a validar.
	 * @throws IdInvalidoException si el ID es nulo o menor o igual a cero.
	 */
	public static void verificarId(Long id) {
		if (id == null) {
			throw new IdInvalidoException("El ID no puede ser nulo");
		}

		if (id <= 0) {
			throw new IdInvalidoException("El ID debe ser un número positivo");
		}
	}

	/**
	 * Verifica que la ciudad de destino proporcionada sea válida.
	 * <p>
	 * La ciudad debe cumplir las siguientes condiciones:
	 * <ul>
	 *   <li>No ser nula ni estar vacía (incluyendo solo espacios en blanco).</li>
	 *   <li>Contener únicamente letras (incluyendo tildes y ñ) y espacios.</li>
	 * </ul>
	 * </p>
	 *
	 * @param ciudadDestino el nombre de la ciudad de destino a validar.
	 * @throws CiudadInvalidaException si la ciudad es nula, vacía o contiene caracteres no permitidos.
	 */
	public static void verificarCiudad(String ciudadDestino) {

		if (ciudadDestino == null || ciudadDestino.trim().isEmpty()) {
			throw new CiudadInvalidaException("La ciudad destino no puede estar vacía");
		}

		if (!ciudadDestino.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
			throw new CiudadInvalidaException("La ciudad destino solo debe contener letras");
		}
	}

	/**
	 * Verifica que el estado del pedido proporcionado sea uno de los valores permitidos.
	 * <p>
	 * Solo se aceptan los siguientes estados de transición para actualización:
	 * <ul>
	 *   <li>{@code EN_PROCESO}</li>
	 *   <li>{@code ENTREGADO}</li>
	 * </ul>
	 * </p>
	 *
	 * @param estadoPedido el estado del pedido a validar.
	 * @throws EstadoPedidoInvalidoException si el estado es nulo, vacío o no corresponde
	 *                                        a {@code EN_PROCESO} ni {@code ENTREGADO}.
	 */
	public static void verificarEstadoPedido(String estadoPedido) {

		if (estadoPedido == null || estadoPedido.trim().isEmpty()) {
			throw new EstadoPedidoInvalidoException("El estado del pedido no puede estar vacío");
		}

		if (!estadoPedido.matches("EN_PROCESO|ENTREGADO")) {
			throw new EstadoPedidoInvalidoException(
					"Estado inválido. Valores permitidos: EN_PROCESO, ENTREGADO");
		}
	}

	/**
	 * Verifica si un registro duplicado ya existe en el sistema y lanza una excepción si es así.
	 * <p>
	 * Este método es utilizado para evitar la creación de registros con datos ya existentes,
	 * como cédulas duplicadas. El mensaje de la excepción es personalizable según el contexto.
	 * </p>
	 *
	 * @param existe  {@code true} si el registro ya existe; {@code false} en caso contrario.
	 * @param mensaje el mensaje descriptivo que se incluirá en la excepción si hay duplicado.
	 * @throws CedulaInvalidaException si {@code existe} es {@code true}, indicando que el registro
	 *                                  ya se encuentra registrado en el sistema.
	 */
	public static void verificarDuplicado(boolean existe, String mensaje) {
		if (existe) {
			throw new CedulaInvalidaException(mensaje);
		}
	}
	
	public static void verificarContrasena(String contrasena) {

	    if (contrasena == null || contrasena.isEmpty()) {
	        throw new ContraseniaInvalidaException("La contraseña no puede estar vacía");
	    }

	    if (contrasena.contains(" ")) {
	        throw new ContraseniaInvalidaException("La contraseña no debe contener espacios");
	    }

	    if (contrasena.length() < 8) {
	        throw new ContraseniaInvalidaException("La contraseña debe tener al menos 8 caracteres");
	    }

	    if (!contrasena.matches("^(?=.*[A-Z])(?=.*[0-9]).+$")) {
	        throw new ContraseniaInvalidaException("La contraseña debe contener al menos una mayúscula y un número");
	    }
	}
}