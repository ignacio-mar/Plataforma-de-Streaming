
## De la implementación

- **Patrones**: Se implementó el patrón _DAO_ para abstraer la lógica del acceso a los datos [cite: 2414, 2397]. El manejo de la conexión se maneja a través del uso del patrón **Singleton**.
- **Mapeo**: La lógica de conversión entre objetos y filas de la base de datos ("ResultSet") se encapsula dentro del DAO.
- **Ordenación**: Los listados (Usuarios y Películas) se ordenan utilizando la interfaz `java.util.Comparator` para permitir la selección de múltiples criterios (Título, Género, Email).

---

## Ejecución

Para iniciar la ejecución de la aplicación, deberá tener el JDK instalado y la librería JDBC en el **classpath**.

1. **Compilar y empaquetar**: El empaquetado estará presente en un archivo .jar ejecutable.
2. **Ejecución**: Abrí la terminal en la carpeta que contiene el JAR y escribí el comando _`java -jar `_
3. **Interacción**: El programa mostrará un menú de opciones para interactuar con las funcionalidades detalladas en la consigna.

---

## Funcionalidades Implementadas (Criterios de Evaluación)

Se implementaron las siguientes funcionalidades requeridas para la Prueba de Concepto (POC):

- *Registro de Datos Personales y del Usuario:* Contiene validaciones (DNI único, formato de Email `xxx@yyy`).
- **Registro de Película**: El campo `Género` se valida usando un tipo *Enumerativo* (`Generos`).
- **Listado de Usuarios**: Permite ordenar por **Nombre de Usuario** o **Email**, usando clases que implementan `Comparator`.
- **Listado de Películas**: Permite ordenar por **Título**, **Género** o **Duración**, usando la interfaz `Comparator`.
- **Gestión de Reseña**: Se implementan las funcionalidades de **Registrar Reseña** y **Aprobar Reseña**.

---

## Extras

Se dejaron cargadas algunas peliculas y un usuario para probar el sistema
- user:Lionel
- password:123123