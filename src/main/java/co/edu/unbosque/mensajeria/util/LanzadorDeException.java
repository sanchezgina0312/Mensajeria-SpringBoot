package co.edu.unbosque.mensajeria.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.edu.unbosque.mensajeria.exception.CedulaInvalidaException;
import co.edu.unbosque.mensajeria.exception.CorreoInvalidoException;
import co.edu.unbosque.mensajeria.exception.DireccionDestinoInvalidaException;
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

public class LanzadorDeException {
	
	public static void verificarNombre(String nombre) throws NombreInvalidoException {
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
	
	public static boolean verificarCorreoElectronico(String correo) throws CorreoInvalidoException {
	    Pattern pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
	    Matcher matcher = pattern.matcher(correo);

	    if (matcher.find()) {
	        return true;
	    } else {
	        throw new CorreoInvalidoException("El correo electrónico ingresado no es válido. Verifique el formato (ejemplo: usuario@dominio.com).");
	    }
	}
	
	public static void verificarCedula(String cedula) throws CedulaInvalidaException {
	    
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
	
	public static void verificarDireccion(String direccion) throws DireccionDestinoInvalidaException {
		
		 /*
	     * Ejemplos de direcciones válidas:
	     * 
	     * Calle 123 # 45-67
	     * Cra 7 # 32-16
	     * Avenida 68 # 10-45
	     * Av 68 # 10-45 apto 302
	     * Carrera 15 # 100-25
	     * Transversal 45A # 12-34
	     * Diagonal 23 # 45-10
	     * Cl 10 # 8-20 barrio Centro
	     */

	    if (direccion == null || direccion.isEmpty()) {
	        throw new DireccionDestinoInvalidaException("La dirección no puede estar vacía");
	    }

	    if (direccion.contains("  ")) {
	        throw new DireccionDestinoInvalidaException("La dirección no puede contener espacios dobles");
	    }

	    if (!direccion.matches("^[A-Za-z0-9#\\-\\.\\s]+$")) {
	        throw new DireccionDestinoInvalidaException("La dirección contiene caracteres inválidos");
	    }

	    if (!direccion.matches("^(Calle|Carrera|Transversal|Diagonal|Avenida|Av|Cl|Cra|Tv|Dg)\\s+[0-9A-Za-z]+\\s+#\\s*[0-9A-Za-z]+-\\s*[0-9A-Za-z]+.*$")) {
	        throw new DireccionDestinoInvalidaException("La dirección no cumple con un formato válido en Colombia");
	    }

	    if (direccion.length() < 5 || direccion.length() > 100) {
	        throw new DireccionDestinoInvalidaException("La dirección debe tener entre 5 y 100 caracteres");
	    }
	}
	
	public static void verificarMetodoPago(String metodoPago) throws MetodoDePagoInvalidoException {

	    /*
	     * Métodos de pago aceptados:
	     * 
	     * EFECTIVO
	     * TARJETA_CREDITO
	     * TARJETA_DEBITO
	     * NEQUI
	     * DAVIPLATA
	     * TRANSFERENCIA
	     */

	    if (metodoPago == null || metodoPago.isEmpty()) {
	        throw new MetodoDePagoInvalidoException("El método de pago no puede estar vacío");
	    }

	    if (metodoPago.contains(" ")) {
	        throw new MetodoDePagoInvalidoException("El método de pago no debe contener espacios");
	    }

	    // Normalizamos a mayúsculas para evitar errores
	    metodoPago = metodoPago.toUpperCase();

	    if (!metodoPago.equals("EFECTIVO") &&
	        !metodoPago.equals("TARJETA_CREDITO") &&
	        !metodoPago.equals("TARJETA_DEBITO") &&
	        !metodoPago.equals("NEQUI") &&
	        !metodoPago.equals("DAVIPLATA") &&
	        !metodoPago.equals("TRANSFERENCIA")) {
	        
	        throw new MetodoDePagoInvalidoException("Método de pago no válido");
	    }
	}
	
	public static void verificarPlaca(String placa) throws PlacaInvalidaException {

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

	public static void verificarTamanoPaquete(String tamano) throws TamanioInvalidoException {

	    /*
	     * Tamaños de paquete aceptados:
	     * 
	     * PEQUENO
	     * MEDIANO
	     * GRANDE
	     * 
	     */

	    if (tamano == null || tamano.isEmpty()) {
	        throw new TamanioInvalidoException("El tamaño del paquete no puede estar vacío");
	    }

	    if (tamano.contains(" ")) {
	        throw new TamanioInvalidoException("El tamaño no debe contener espacios");
	    }

	    tamano = tamano.toUpperCase();

	    if (!tamano.equals("PEQUENO") &&
	        !tamano.equals("MEDIANO") &&
	        !tamano.equals("GRANDE")) {
	        
	        throw new TamanioInvalidoException("Tamaño de paquete no válido");
	    }
	}
	
	public static void verificarTelefono(String telefono) throws TelefonoInvalidoException {

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
	
	public static void verificarTipoAlimento(String tipo) throws TipoDeAlimentoInvalidoException {

	    /*
	     * Tipos de alimento aceptados:
	     * 
	     * FRUTA
	     * VERDURA
	     * CARNE
	     * LACTEO
	     * CEREAL
	     * BEBIDA
	     * 
	     */

	    if (tipo == null || tipo.isEmpty()) {
	        throw new TipoDeAlimentoInvalidoException("El tipo de alimento no puede estar vacío");
	    }

	    if (tipo.contains(" ")) {
	        throw new TipoDeAlimentoInvalidoException("El tipo no debe contener espacios");
	    }

	    tipo = tipo.toUpperCase();

	    if (!tipo.equals("FRUTA") &&
	        !tipo.equals("VERDURA") &&
	        !tipo.equals("CARNE") &&
	        !tipo.equals("LACTEO") &&
	        !tipo.equals("CEREAL") &&
	        !tipo.equals("BEBIDA")) {
	        
	        throw new TipoDeAlimentoInvalidoException("Tipo de alimento no válido");
	    }
	}
	
	public static void verificarTipoCarta(String tipo) throws TipoDeCartaInvalidaException {

	    /*
	     * Tipos de carta aceptados:
	     * 
	     * ESTANDAR
	     * CERTIFICADA
	     * JURIDICA
	     * PUBLICITARIA
	     * 
	     */

	    if (tipo == null || tipo.isEmpty()) {
	        throw new TipoDeCartaInvalidaException("El tipo de carta no puede estar vacío");
	    }

	    if (tipo.contains(" ")) {
	        throw new TipoDeCartaInvalidaException("El tipo no debe contener espacios");
	    }

	    // Normalizar a mayúsculas
	    tipo = tipo.toUpperCase();

	    if (!tipo.equals("ESTANDAR") &&
	        !tipo.equals("CERTIFICADA") &&
	        !tipo.equals("JURIDICA") &&
	        !tipo.equals("PUBLICITARIA")) {
	        
	        throw new TipoDeCartaInvalidaException("Tipo de carta no válido");
	    }
	}
	
	public static void verificarTipoManipulador(String tipo) throws TipoManipuladorInvalidoException {

	    /*
	     * Tipos de manipulador aceptados:
	     * 
	     * PAQUETES_ALIMENTICIOS
	     * PAQUETES_NO_ALIMENTICIOS
	     * CARTAS
	     * 
	     */

	    if (tipo == null || tipo.isEmpty()) {
	        throw new TipoManipuladorInvalidoException("El tipo de manipulador no puede estar vacío");
	    }

	    if (tipo.contains(" ")) {
	        throw new TipoManipuladorInvalidoException("El tipo no debe contener espacios");
	    }

	    // Normalizar a mayúsculas
	    tipo = tipo.toUpperCase();

	    if (!tipo.equals("PAQUETES_ALIMENTICIOS") &&
	        !tipo.equals("PAQUETES_NO_ALIMENTICIOS") &&
	        !tipo.equals("CARTAS")) {
	        
	        throw new TipoManipuladorInvalidoException("Tipo de manipulador no válido");
	    }
	}
	
	public static void verificarTipoPedido(String tipo) throws TipoPedidoInvalidoException {

	    /*
	     * Tipos de pedido aceptados:
	     * 
	     * ALIMENTICIO
	     * NO_ALIMENTICIO
	     * CARTA
	     * 
	     */

	    if (tipo == null || tipo.isEmpty()) {
	        throw new TipoPedidoInvalidoException("El tipo de pedido no puede estar vacío");
	    }

	    if (tipo.contains(" ")) {
	        throw new TipoPedidoInvalidoException("El tipo no debe contener espacios");
	    }

	    tipo = tipo.toUpperCase();

	    if (!tipo.equals("ALIMENTICIO") &&
	        !tipo.equals("NO_ALIMENTICIO") &&
	        !tipo.equals("CARTA")) {
	        
	        throw new TipoPedidoInvalidoException("Tipo de pedido no válido");
	    }
	}
	
	public static void verificarTurno(char turno) throws TurnoInvalidoException {

	    /*
	     * Turnos aceptados:
	     * D -> Día
	     * N -> Noche
	     * M -> Mixto
	     */

	    turno = Character.toUpperCase(turno);

	    if (turno != 'D' && turno != 'N' && turno != 'M') {
	        throw new TurnoInvalidoException("Turno no válido. Use D, N o M");
	    }
	}
	
	public static void verificarId(String id) throws IdInvalidoException {

	    if (id == null || id.isEmpty()) {
	        throw new IdInvalidoException("El ID no puede estar vacío");
	    }

	    if (!id.matches("^[0-9]+$")) {
	        throw new IdInvalidoException("El ID solo debe contener números sin espacios ni caracteres");
	    }
	}
}
