## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

<<<<<<< HEAD
## De la implementación

- [cite_start]**Patrones**: Se implementó el patrón _DAO_ para abstraer la lógica del acceso a los datos [cite: 2414, 2397]. [cite_start]El manejo de la conexión se maneja a través del uso del patrón **Singleton**[cite: 2576].
- **Mapeo**: La lógica de conversión entre objetos y filas de la base de datos ("ResultSet") se encapsula dentro del DAO.
- [cite_start]**Ordenación**: Los listados (Usuarios y Películas) se ordenan utilizando la interfaz `java.util.Comparator` para permitir la selección de múltiples criterios (Título, Género, Email)[cite: 5036].

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
- [cite_start]**Registro de Película**: El campo `Género` se valida usando un tipo *Enumerativo* (`Generos`)[cite: 2366].
- **Listado de Usuarios**: Permite ordenar por **Nombre de Usuario** o **Email**, usando clases que implementan `Comparator`.
- [cite_start]**Listado de Películas**: Permite ordenar por **Título**, **Género** o **Duración**, usando la interfaz `Comparator`[cite: 2375, 2376].
- [cite_start]**Gestión de Reseña**: Se implementan las funcionalidades de **Registrar Reseña** y **Aprobar Reseña**[cite: 2377, 2384].

---

## Extras

Se dejaron cargadas algunas peliculas y un usuario para probar el sistema
- user:LeoMessi10
- password:123123
=======
The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
>>>>>>> parent of 4029695 (...)
