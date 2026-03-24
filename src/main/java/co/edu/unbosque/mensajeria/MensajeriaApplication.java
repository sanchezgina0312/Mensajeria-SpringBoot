package co.edu.unbosque.mensajeria;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Clase principal que inicia la ejecución de la aplicación de Mensajería.
 */
@SpringBootApplication
public class MensajeriaApplication {

	/**
	 * Punto de entrada principal para la ejecución de la aplicación.
	 * * @param args Argumentos de la línea de comandos.
	 */
	public static void main(String[] args) {
		SpringApplication.run(MensajeriaApplication.class, args);
	}

	/**
	 * Define el bean de ModelMapper para la inyección de dependencias.
	 * * @return Una nueva instancia de ModelMapper configurada para el sistema.
	 */
	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

}