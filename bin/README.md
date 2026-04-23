Sistema de Gestión de Mensajería (Backend)
Este proyecto consiste en el desarrollo de una API REST diseñada para automatizar la logística de una empresa de mensajería. El sistema permite gestionar diferentes tipos de envíos, aplicando reglas de negocio específicas según la naturaleza del paquete y las condiciones de entrega.

🚀 Funcionalidades Principales
- Gestión de Envíos: Clasificación y manejo de tres categorías de paquetes: Alimenticios, Cartas y No Alimenticios.
- Cálculo Dinámico de Costos: Implementación de lógica para determinar el precio final basándose en el tamaño del paquete y tarifas base predefinidas.
- Monitoreo de Entregas: Sistema de actualización automática de estados (En Proceso / Entregado) y detección de envíos prioritarios según el tiempo restante de entrega.
- Validación de Datos: Uso de excepciones personalizadas para asegurar que la información ingresada (direcciones, ciudades, identidades) cumpla con los requisitos del sistema.

🛠️ Tecnologías Utilizadas
- Java 21: Lenguaje de programación base.
- Spring Boot: Framework para el desarrollo de la API.
- Spring Data JPA: Para la persistencia y gestión de la base de datos.
- ModelMapper: Para la transformación eficiente de datos entre entidades y DTOs.
- Swagger/OpenAPI: Documentación interactiva de los puntos de acceso (endpoints).

📂 Estructura del Proyecto
- El código está organizado siguiendo una arquitectura de capas, lo que facilita su mantenimiento y escalabilidad:
- Controllers: Manejo de las peticiones HTTP y comunicación con el cliente.
- Services: Implementación de la lógica de negocio y cálculos.
- Repositories: Interfaz de comunicación con la base de datos.
- DTOs & Entities: Modelado de los datos internos y de transferencia.

🔧 Ejecución
- Clonar el repositorio.
- Importar el proyecto como un proyecto Maven en el IDE (Eclipse, IntelliJ, etc.).
- Ejecutar la clase principal de Spring Boot.
- Acceder a la documentación en: http://localhost:8080/swagger-ui/index.html
