package co.edu.unbosque.mensajeria;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	/**
	 * Configura la aplicación para su despliegue como un archivo WAR.
	 * * @param application El constructor de la aplicación Spring.
	 * @return El constructor configurado con la fuente de la aplicación.
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MensajeriaApplication.class);
	}

}