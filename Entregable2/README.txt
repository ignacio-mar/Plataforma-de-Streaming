# TDL II - Plataforma de Streaming - Entregable 2
---

## Integrantes - Grupo 32

*Bordalecu Campodónico, Federico* federicobc04@gmail.com; *Cappella, Ezequiel Osvaldo* ezequielcappella03@gmail.com; *Martínez Ignacio* ignaciorubenm12@gmail.com

---

## Contenido
El contenido de este proyecto está dividido en distintas carpetas separando las partes funcionales del sistema.
- Entregable2/.vscode -> Contiene la **configuración específica** de Visual Studio Code para el entorno de desarrollo del grupo.
- Entregable2/bin -> Contiene los archivos .class generados al compilar el proyecto.
- Entregable2/consgina -> Contiene el archivo PDF con la consigna y las pautas a seguir para el desarrollo de la plataforma.
- Entregable2/db -> Contiene el archivo de la base de datos. Es posible visualizarla con un browser.
- Entregable2/lib -> Contiene las librerías externas que necesita el proyecto para ejecutarse (El driver JDBC de tipo 4 necesario para conectarse a la base de datos SQLite).
- Entregable2/src -> **Código fuente** de la aplicación. Contiene los paquetes principales de la aplicación.

---

## Flujo de ejecución

[cite_start]La arquitectura sigue el patrón **Data Access Object (DAO)** y usa la capa JDBC para acceder a la base de datos.[cite: 2414].

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
