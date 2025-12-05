## Del proyecto
- Este proyecto implementa una plataforma de streaming básica implementada en Java bajo el diseño de software bajo el patrón MVC. Este proyecto contempla conceptos como:
* **Tecnología Principal:** Java SE
* **Base de Datos:** SQLite
* **Interfaz de Usuario:** Swing

## Arquitectura y diseño 

- **Patrón MVC**: La totalidad del sistema se estructuró siguiendo el patrón MVC para lograr una clara abstracción de responsabilidades y modularidad.

- **Modelo (model)**: Contiene la lógica de negocio y el acceso a datos (servicios DAO para la BD y OmdBService para la API).
- **Vista (views)**: Se limita a la creación y presentación de la interfaz gráfica (Swing) y a capturar la interacción del usuario.
- **Controlador (controllers)**: Comunica los eventos de la vista validándolos previamente y coordina la lógica del Modelo ordenando a la vista que se actualice.

---

## Implementaciones

- **Concurrencia**: Se implementó la concurrencia para evitar el bloqueo del Hilo de Despacho de Evnetos (EDT) en operaciones lentas (como cargar la base de datos con el csv provisto).

* **Mecanismo:** La **Búsqueda en OMDb** y la **Importación de CSV** se ejecutan en **`Thread`** separados, implementados mediante `Runnable`.
* **Anti-Bloqueo de GUI:** Se utiliza **`SwingUtilities.invokeLater()`** para garantizar que el resultado (ej. mostrar la película o el error) regrese al Hilo de Despacho de Eventos (EDT) de forma segura, manteniendo la interfaz fluida.

### Manejo de Excepciones Propias
- Se crearon tres excepciones personalizadas, para diferenciar los tipos de fallos y mostrar mensajes informativos y específicos al usuario.

- **`ErrorConexionAPIException`**: Fallo de conexión o error HTTP/I/O (problema de infraestructura). 
- **`PeliculaNoEncontradaException`**: El servicio externo respondió OK, pero el recurso no existe. 
- **`BusquedaInvalidaException`**: El usuario ingresó texto de búsqueda vacío o inválido. 

---

## Implementaciones Funcionales Clave

### Servicio OMDb y Parseo 

* **`OmdbService`**: Implementa la consulta a la API de OMDb. Utiliza la librería `org.json` para realizar el **parsing del string JSON** recibido por el `HttpURLConnection` y convertirlo a un objeto `Pelicula` del Modelo.
* **Consulta:** La llamada a este servicio es la que se ejecuta concurrentemente.

### Persistencia y DAO 

* **Tecnología:** Uso de **SQLite** como motor de base de datos a través de **JDBC**.
* **Patrón:** Implementación de Data Access Objects (DAO) para manejar la conexión, las consultas y las transacciones, aislando el código SQL del Modelo de Negocio.
* **Modelo Actualizado:** La tabla de películas fue modificada para incluir los campos **`anio`**, **`rating_promedio`** y **`poster`**.

### Importación de CSV

* **Carga:** Utiliza la concurrencia para leer el archivo CSV y persistir los datos en la base de datos de manera eficiente al inicio de la aplicación, minimizando los tiempos de espera.
* **I/O:** Se emplean `BufferedReader` y `FileReader` para el manejo de los flujos de entrada.

---

## Autores 

* **Bordalecu Campodonico**, Federico.
* **Cappella**, Ezequiel.
* **Martínez**, Ignacio. 