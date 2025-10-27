package ui;

import dao.DatosPersonalesDAO;
import dao.PeliculasDAO;
import dao.ReseñasDAO;
import dao.UsuariosDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import model.DatosPersonales;
import model.enums.Generos;
import model.enums.Idiomas;
import model.enums.Paises;
import model.Usuario;
import model.Pelicula;
import model.Reseña;
import dao.compar.ComparatorIdPelicula;
import dao.compar.ComparadorTitulo;
import dao.compar.ComparadorGenero;
import dao.compar.ComparadorDuracion;
import dao.compar.ComparadorTitulo;
import dao.compar.ComparadorGenero;
import dao.compar.ComparadorDuracion;

public class Menu {

    private final Scanner in;
    private final DatosPersonalesDAO dpDao;
    private final UsuariosDAO usuariosDao;
    private final ReseñasDAO reseñasDao;
    private final PeliculasDAO peliculasDao;

    public Menu(Scanner in, DatosPersonalesDAO dpDao, UsuariosDAO usuariosDao, ReseñasDAO reseñasDao, PeliculasDAO peliculasDao) {
        this.in = in;
        this.dpDao = dpDao;
        this.usuariosDao = usuariosDao;
        this.reseñasDao = reseñasDao;
        this.peliculasDao = peliculasDao;
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1) Personas");
            System.out.println("2) Usuarios");
            System.out.println("3) Películas");
            System.out.println("4) Reseñas");
            System.out.println("0) Salir");
            int op = leerEntero("Opción: ");

            try {
                switch (op) {
                    case 1 -> submenuPersonas();
                    case 2 -> submenuUsuarios();
                    case 3 -> submenuPeliculas();
                    case 4 -> submenuReseñas();
                    case 0 -> salir = true;
                    default -> System.out.println("Opción inválida.");
                }
            } catch (SQLException e) {
                System.out.println("Error SQL: " + e.getMessage());
            }
        }
        System.out.println("¡Hasta luego!");
    }
    
    // ===== SUBMENU PERSONAS =====
    private void submenuPersonas() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- Personas ---");
            System.out.println("1) Alta (guardar)");
            System.out.println("2) Listar todas");
            System.out.println("3) Buscar por ID");
            System.out.println("4) Buscar por DNI");
            System.out.println("5) Actualizar");
            System.out.println("6) Eliminar");
            System.out.println("0) Volver");
            int op = leerEntero("Opción: ");

            try {
                switch (op) {
                    case 1 -> altaPersona();
                    case 2 -> listarPersonas();
                    case 3 -> buscarPorId();
                    case 4 -> buscarPorDni();
                    case 5 -> actualizarPersona();
                    case 6 -> eliminarPersona();
                    case 0 -> volver = true;
                    default -> System.out.println("Opción inválida.");
                }
            } catch (SQLException e) {
                System.out.println("Error SQL: " + e.getMessage());
            }
        }
    }

    // ===== OPERACIONES PERSONAS =====

    private void altaPersona() throws SQLException {
        System.out.println("\n[Alta de Persona]");
        
        // DNI
        int dni;
        do {
            dni = leerEntero("DNI: ");
            if (dni <= 0) {
                System.out.println("El DNI debe ser un número positivo.");
                continue;
            }
            if (dpDao.buscarPorDni(dni).isPresent()) {
                System.out.println("Ya existe una persona con DNI " + dni + ". Por favor, ingrese otro.");
                continue;
            }
            break;
        } while (true);

        // Nombre
        String nombre;
        do {
            nombre = leerTexto("Nombre(s): ");
            if (nombre.isEmpty()) {
                System.out.println("El nombre no puede estar vacío.");
                continue;
            }
            if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
                System.out.println("El nombre solo puede contener letras y espacios.");
                continue;
            }
            break;
        } while (true);

        // Apellido
        String apellido;
        do {
            apellido = leerTexto("Apellido: ");
            if (apellido.isEmpty()) {
                System.out.println("El apellido no puede estar vacío.");
                continue;
            }
            if (!apellido.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
                System.out.println("El apellido solo puede contener letras y espacios.");
                continue;
            }
            break;
        } while (true);
        
        // País
        System.out.println("Países disponibles:");
        for (Paises pais : Paises.values()) {
            System.out.println("- " + pais.name());
        }
        Paises pais = null;
        while (pais == null) {
            try {
                String paisInput = leerTexto("País de residencia (escriba exactamente como aparece arriba): ");
                if (paisInput.isEmpty()) {
                    System.out.println("Debe seleccionar un país.");
                    continue;
                }
                pais = Paises.valueOf(paisInput.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("País inválido. Por favor, seleccione uno de la lista.");
            }
        }
        
        // Teléfono
        String tel;
        do {
            tel = leerTexto("Número de teléfono: ");
            if (tel.isEmpty()) {
                System.out.println("El número de teléfono no puede estar vacío.");
                continue;
            }
            break;
        } while (true);

        // Mostrar resumen y pedir confirmación
        DatosPersonales dp = new DatosPersonales(nombre, apellido, dni, pais, tel);
        System.out.println("\nResumen de datos ingresados:");
        System.out.println("============================");
        System.out.println("DNI: " + dp.getDni());
        System.out.println("Nombre: " + dp.getNombres());
        System.out.println("Apellido: " + dp.getApellido());
        System.out.println("País: " + dp.getPaisResidencia());
        System.out.println("Teléfono: " + dp.getNumeroTelefono());
        System.out.println("============================");
        
        String confirma;
        do {
            confirma = leerTexto("¿Desea guardar estos datos? (s/n): ").toLowerCase();
            if (!confirma.equals("s") && !confirma.equals("n")) {
                System.out.println("Por favor, responda 's' o 'n'");
                continue;
            }
            break;
        } while (true);

        if (confirma.equals("s")) {
            dp = dpDao.guardar(dp);
            System.out.println("Persona guardada con ID: " + dp.getId());
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    private void listarPersonas() throws SQLException {
        System.out.println("\n[Listado de Personas]");
        List<DatosPersonales> lista = dpDao.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("(sin registros)");
            return;
        }
        for (DatosPersonales p : lista) {
            System.out.printf("ID=%d | DNI=%d | %s %s | %s | %s%n",
                    p.getId(), p.getDni(), p.getNombres(), p.getApellido(),
                    p.getPaisResidencia(), p.getNumeroTelefono());
        }
    }

    private void buscarPorId() throws SQLException {
        System.out.println("\n[Buscar por ID]");
        int id = leerEntero("ID: ");
        Optional<DatosPersonales> opt = dpDao.buscarPorId(id);
        if (opt.isEmpty()) {
            return;
        }
        DatosPersonales p = opt.get();
        imprimirPersona(p);
    }

    private void buscarPorDni() throws SQLException {
        System.out.println("\n[Buscar por DNI]");
        int dni = leerEntero("DNI: ");
        Optional<DatosPersonales> opt = dpDao.buscarPorDni(dni);
        if (opt.isEmpty()) {
            System.out.println("No se encontró persona con DNI " + dni);
            return;
        }
        DatosPersonales p = opt.get();
        imprimirPersona(p);
    }

    private void actualizarPersona() throws SQLException {
        System.out.println("\n[Actualizar Persona]");
        int id = leerEntero("ID de la persona a actualizar: ");
        Optional<DatosPersonales> opt = dpDao.buscarPorId(id);

        if (opt.isEmpty()) {
            System.out.println("No existe persona con ID " + id);
            return;
        }

        // Mostrar datos actuales
        DatosPersonales actual = opt.get();
        imprimirPersona(actual);

        // Pedir nuevos datos (enter para mantener)
        String nombre = leerTextoOpcional("Nuevo nombre(s) (enter para mantener): ");
        String apellido = leerTextoOpcional("Nuevo apellido (enter para mantener): ");
        
        System.out.println("País actual: " + actual.getPaisResidencia());
        System.out.println("Países disponibles:");
        for (Paises pais : Paises.values()) {
            System.out.println("- " + pais.name());
        }
        
        String paisInput = leerTextoOpcional("Nuevo país (enter para mantener, o escriba exactamente como aparece arriba): ");
        Paises nuevoPais = actual.getPaisResidencia();
        if (!paisInput.isBlank()) {
            try {
                nuevoPais = Paises.valueOf(paisInput.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("País inválido. Se mantiene el anterior.");
            }
        }
        
        String tel = leerTextoOpcional("Nuevo teléfono (enter para mantener): ");
        String dniStr = leerTextoOpcional("Nuevo DNI (enter para mantener): ");

        int nuevoDni = actual.getDni();
        if (!dniStr.isBlank()) {
            int dniIngresado = parseEnteroSeguro(dniStr);
            if (dniIngresado <= 0) {
                System.out.println("DNI inválido. Se mantiene el anterior.");
            } else if (dniIngresado != actual.getDni()
                    && dpDao.buscarPorDni(dniIngresado).isPresent()) {
                System.out.println("Ya existe una persona con ese DNI. Se mantiene el anterior.");
            } else {
                nuevoDni = dniIngresado;
            }
        }

        DatosPersonales mod = new DatosPersonales(
                nombre.isBlank() ? actual.getNombres() : nombre,
                apellido.isBlank() ? actual.getApellido() : apellido,
                nuevoDni,
                nuevoPais,
                tel.isBlank() ? actual.getNumeroTelefono() : tel
        );
        mod.setId(id);

        boolean ok = dpDao.actualizar(mod);
        System.out.println(ok ? "Actualización exitosa." : "No se actualizó ninguna fila.");
    }

    private void eliminarPersona() throws SQLException {
        System.out.println("\n[Eliminar Persona]");
        int id = leerEntero("ID a eliminar: ");
        boolean ok = dpDao.eliminar(id);
        System.out.println(ok ? "Eliminada correctamente." : "No se encontró ese ID.");
    }

    private void imprimirPersona(DatosPersonales p) {
        System.out.printf("ID=%d | DNI=%d | %s %s | %s | %s%n",
                p.getId(), p.getDni(), p.getNombres(), p.getApellido(),
                p.getPaisResidencia(), p.getNumeroTelefono());
    }

    // ===== Helpers de lectura =====

    private int leerEntero(String msg) {
        while (true) {
            System.out.print(msg);
            String s = in.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }

    private int parseEnteroSeguro(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return -1;
        }
    }

    private String leerTexto(String msg) {
        System.out.print(msg);
        return in.nextLine().trim();
    }

    private String leerTextoOpcional(String msg) {
        System.out.print(msg);
        return in.nextLine();
    }

    // ===== SUBMENU USUARIOS =====
    private void submenuUsuarios() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- Usuarios ---");
            System.out.println("1) Alta (asociar nuevo usuario)");
            System.out.println("2) Listar todos ordenados por nombre");
            System.out.println("3) Listar todos ordenados por email");
            System.out.println("4) Buscar por ID");
            System.out.println("5) Buscar por nombre de usuario");
            System.out.println("6) Actualizar");
            System.out.println("7) Eliminar");
            System.out.println("0) Volver");
            int op = leerEntero("Opción: ");

            try {
                switch (op) {
                    case 1 -> altaUsuario();
                    case 2 -> listarUsuariosPorNombre();
                    case 3 -> listarUsuariosPorEmail();
                    case 4 -> buscarUsuarioPorId();
                    case 5 -> buscarUsuarioPorNombre();
                    case 6 -> actualizarUsuario();
                    case 7 -> eliminarUsuario();
                    case 0 -> volver = true;
                    default -> System.out.println("Opción inválida.");
                }
            } catch (SQLException e) {
                System.out.println("Error SQL: " + e.getMessage());
            }
        }
    }

    // ===== OPERACIONES USUARIOS =====

    private void altaUsuario() throws SQLException {
        System.out.println("\n[Alta de Usuario]");
        
        // DNI de la persona
        int dni;
        Optional<DatosPersonales> persona;
        do {
            dni = leerEntero("DNI de la persona: ");
            if (dni <= 0) {
                System.out.println("El DNI debe ser un número positivo.");
                continue;
            }
            persona = dpDao.buscarPorDni(dni);
            if (persona.isEmpty()) {
                System.out.println("No existe una persona con ese DNI. Debe registrar primero sus datos personales.");
                continue;
            }
            break;
        } while (true);

        // Nombre de usuario
        String nombreUsuario;
        do {
            nombreUsuario = leerTexto("Nombre de usuario: ");
            if (nombreUsuario.isEmpty()) {
                System.out.println("El nombre de usuario no puede estar vacío.");
                continue;
            }
            if (usuariosDao.buscarPorNombreUsuario(nombreUsuario).isPresent()) {
                System.out.println("Ya existe un usuario con ese nombre. Por favor, elija otro.");
                continue;
            }
            break;
        } while (true);

        // Email
        String email;
        do {
            email = leerTexto("Email: ");
            if (email.isEmpty()) {
                System.out.println("El email no puede estar vacío.");
                continue;
            }
            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                System.out.println("Email inválido.");
                continue;
            }
            break;
        } while (true);

        // Contraseña
        String contrasenia;
        do {
            contrasenia = leerTexto("Contraseña: ");
            if (contrasenia.length() < 6) {
                System.out.println("La contraseña debe tener al menos 6 caracteres.");
                continue;
            }
            break;
        } while (true);

        // Mostrar resumen y pedir confirmación
        Usuario usuario = new Usuario(nombreUsuario, email, contrasenia, dni);
        System.out.println("\nResumen de datos ingresados:");
        System.out.println("============================");
        System.out.println("DNI Persona: " + dni);
        System.out.println("Nombre: " + nombreUsuario);
        System.out.println("Email: " + email);
        System.out.println("============================");
        
        String confirma;
        do {
            confirma = leerTexto("¿Desea crear este usuario? (s/n): ").toLowerCase();
            if (!confirma.equals("s") && !confirma.equals("n")) {
                System.out.println("Por favor, responda 's' o 'n'");
                continue;
            }
            break;
        } while (true);

        if (confirma.equals("s")) {
            usuario = usuariosDao.asociarUsuario(usuario);
            System.out.println("Usuario creado con ID: " + usuario.getId());
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    private void listarUsuariosPorNombre() throws SQLException {
        System.out.println("\n[Listado de Usuarios ordenados por nombre]");
        listarUsuarios((u1, u2) -> u1.getNombreUsuario().compareToIgnoreCase(u2.getNombreUsuario()));
    }

    private void listarUsuariosPorEmail() throws SQLException {
        System.out.println("\n[Listado de Usuarios ordenados por email]");
        listarUsuarios((u1, u2) -> u1.getEmail().compareToIgnoreCase(u2.getEmail()));
    }

    private void listarUsuarios(Comparator<Usuario> comparador) throws SQLException {
        List<Usuario> lista = usuariosDao.listarTodos(comparador);
        if (lista.isEmpty()) {
            System.out.println("(sin registros)");
            return;
        }
        for (Usuario u : lista) {
            imprimirUsuario(u);
        }
    }

    private void buscarUsuarioPorId() throws SQLException {
        System.out.println("\n[Buscar Usuario por ID]");
        int id = leerEntero("ID: ");
        Optional<Usuario> opt = usuariosDao.buscarPorId(id);
        if (opt.isEmpty()) {
            System.out.println("No existe usuario con ID " + id);
            return;
        }
        imprimirUsuario(opt.get());
    }

    private void buscarUsuarioPorNombre() throws SQLException {
        System.out.println("\n[Buscar Usuario por nombre]");
        String nombre = leerTexto("Nombre de usuario: ");
        Optional<Usuario> opt = usuariosDao.buscarPorNombreUsuario(nombre);
        if (opt.isEmpty()) {
            System.out.println("No existe usuario con nombre '" + nombre + "'");
            return;
        }
        imprimirUsuario(opt.get());
    }

    private void actualizarUsuario() throws SQLException {
        System.out.println("\n[Actualizar Usuario]");
        int id = leerEntero("ID del usuario a actualizar: ");
        Optional<Usuario> opt = usuariosDao.buscarPorId(id);

        if (opt.isEmpty()) {
            System.out.println("No existe usuario con ID " + id);
            return;
        }

        // Mostrar datos actuales
        Usuario actual = opt.get();
        imprimirUsuario(actual);

        // Pedir nuevos datos (enter para mantener)
        String nombreUsuario = leerTextoOpcional("Nuevo nombre de usuario (enter para mantener): ");
        if (!nombreUsuario.isBlank() && !nombreUsuario.equals(actual.getNombreUsuario())) {
            if (usuariosDao.buscarPorNombreUsuario(nombreUsuario).isPresent()) {
                System.out.println("Ya existe un usuario con ese nombre. No se actualizará el nombre.");
                nombreUsuario = actual.getNombreUsuario();
            }
        } else {
            nombreUsuario = actual.getNombreUsuario();
        }

        String email = leerTextoOpcional("Nuevo email (enter para mantener): ");
        if (!email.isBlank() && !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            System.out.println("Email inválido. Se mantiene el anterior.");
            email = actual.getEmail();
        } else if (email.isBlank()) {
            email = actual.getEmail();
        }

        String contrasenia = leerTextoOpcional("Nueva contraseña (enter para mantener): ");
        if (!contrasenia.isBlank() && contrasenia.length() < 6) {
            System.out.println("La contraseña debe tener al menos 6 caracteres. Se mantiene la anterior.");
            contrasenia = actual.getContrasenia();
        } else if (contrasenia.isBlank()) {
            contrasenia = actual.getContrasenia();
        }

        Usuario mod = new Usuario(id, nombreUsuario, email, contrasenia, actual.getDniPersona());

        boolean ok = usuariosDao.actualizar(mod);
        System.out.println(ok ? "Actualización exitosa." : "No se realizó la actualización.");
    }

    private void eliminarUsuario() throws SQLException {
        System.out.println("\n[Eliminar Usuario]");
        int id = leerEntero("ID del usuario a eliminar: ");
        boolean ok = usuariosDao.eliminar(id);
        System.out.println(ok ? "Usuario eliminado correctamente." : "No se encontró usuario con ese ID.");
    }

    private void imprimirUsuario(Usuario u) {
        // Buscar datos personales asociados
        try {
            Optional<DatosPersonales> persona = dpDao.buscarPorDni(u.getDniPersona());
            String nombreCompleto = persona.map(p -> p.getNombres() + " " + p.getApellido())
                                        .orElse("(persona no encontrada)");
            System.out.printf("ID=%d | Usuario: %s | Email: %s | Persona: %s (DNI %d)%n",
                    u.getId(), u.getNombreUsuario(), u.getEmail(), nombreCompleto, u.getDniPersona());
        } catch (SQLException e) {
            System.out.printf("ID=%d | Usuario: %s | Email: %s | DNI %d (error al buscar persona)%n",
                    u.getId(), u.getNombreUsuario(), u.getEmail(), u.getDniPersona());
        }
    }

// ===== SUBMENU PELICULAS =====  
    private void submenuPeliculas() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- Peliculas---");
            System.out.println("1) Alta (guardar)");
            System.out.println("2) Listar Por Titulo");
            System.out.println("3) Listar Por Genero");
            System.out.println("4) listar Por Duracion");
            System.out.println("0) Volver");
            int op = leerEntero("Opción: ");

            try {
                switch (op) {
                    case 1 -> altaPelicula();
                    case 2 -> listarPorTitulo();
                    case 3 -> listarPorGenero();
                    case 4 -> listarPorDuracion();
                    case 0 -> volver = true;
                    default -> System.out.println("Opción inválida.");
                }
            } catch (SQLException e) {
                System.out.println("Error SQL: " + e.getMessage());
            }
        }

    }
    // ===== OPERACIONES PELICULAS =====
private void altaPelicula() throws SQLException {
    System.out.println("\n[Alta de Pelicula]");

    // ---- Título ----
    String titulo;
    while (true) {
        titulo = leerTexto("Título: ");
        if (titulo.isEmpty()) {
            System.out.println("El título no puede estar vacío.");
            continue;
        }
        break;
    }

    // ---- Elenco ----
    String elenco;
    while (true) {
        elenco = leerTexto("Elenco (separado por comas, ej: Actor A, Actor B, ...): ");
        if (elenco.isEmpty()) {
            System.out.println("El elenco no puede estar vacío.");
            continue;
        }
        if (!elenco.contains(",")) {
            System.out.println("Debe ingresar múltiples nombres separados por comas (ej: Nombre1, Nombre2).");
            continue;
        }
        break;
    }

    // ---- Director ----
    String director;
    while (true) {
        director = leerTexto("Director: ");
        if (director.isEmpty()) {
            System.out.println("El director no puede estar vacío.");
            continue;
        }
        if (!director.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            System.out.println("El nombre del director solo puede contener letras y espacios.");
            continue;
        }
        break;
    }

    // ---- Género (enum) ----
    System.out.println("Géneros disponibles:");
    for (Generos g : Generos.values()) {
        System.out.println("- " + g.name());
    }
    Generos genero = null;
    while (genero == null) {
        String generoInput = leerTexto("Género (escriba exactamente como aparece arriba): ");
        if (generoInput.isEmpty()) {
            System.out.println("Debe seleccionar un género.");
            continue;
        }
        try {
            genero = Generos.valueOf(generoInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Género inválido. Por favor, seleccione uno de la lista.");
        }
    }

    // ---- Duración ----
    double duracion;
    while (true) {
        duracion = leerDouble("Duración de la película (en minutos, ej: 90.5): ");
        if (duracion <= 0) {
            System.out.println("La duración debe ser un valor positivo (mayor que cero).");
            continue;
        }
        break;
    }

    // ---- Idioma de Audio (enum) ----
    System.out.println("Idiomas de Audio disponibles:");
    for (Idiomas idioma : Idiomas.values()) {
        System.out.println("- " + idioma.name());
    }
    Idiomas audio = null;
    while (audio == null) {
        String idiomaInput = leerTexto("Idioma de Audio: ");
        try {
            audio = Idiomas.valueOf(idiomaInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Idioma inválido. Escriba exactamente como aparece arriba.");
        }
    }

    // ---- Idioma de Subtítulos (enum) ----
    System.out.println("Idiomas de Subtítulos disponibles:");
    for (Idiomas idioma : Idiomas.values()) {
        System.out.println("- " + idioma.name());
    }
    Idiomas subtitulos = null;
    while (subtitulos == null) {
        String idiomaInput = leerTexto("Idioma de Subtítulos: ");
        try {
            subtitulos = Idiomas.valueOf(idiomaInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Idioma inválido. Escriba exactamente como aparece arriba.");
        }
    }

    // ---- Sinopsis (opcional) ----
    String sinopsis = leerTexto("Sinopsis (opcional): ");

    // ---- Resumen y confirmación ----
    System.out.println("\nResumen de datos ingresados:");
    System.out.println("============================");
    System.out.println("Título: " + titulo);
    System.out.println("Elenco: " + elenco);
    System.out.println("Director: " + director);
    System.out.println("Género: " + genero);
    System.out.println("Duración: " + duracion + " minutos");
    System.out.println("Audio: " + audio);
    System.out.println("Subtítulos: " + subtitulos);
    System.out.println("Sinopsis: " + sinopsis);
    System.out.println("============================");

    String confirma;
    while (true) {
        confirma = leerTexto("¿Desea guardar estos datos? (s/n): ").toLowerCase();
        if (!confirma.equals("s") && !confirma.equals("n")) {
            System.out.println("Por favor, responda 's' o 'n'");
            continue;
        }
        break;
    }

    // ---- Guardar ----
    if (confirma.equals("s")) {
        Pelicula pelicula = new Pelicula(titulo, elenco, director, genero, duracion, audio, subtitulos, sinopsis);
        pelicula = peliculasDao.guardar(pelicula);
        System.out.println("Película guardada con ID: " + pelicula.getId());
    } else {
        System.out.println("Operación cancelada.");
    }
}

    private void listarPorTitulo() throws SQLException {
        System.out.println("\n[Listado de Peliculas ordenados por titulo]");
        listarPeliculas(ComparadorTitulo.POR_TITULO);
    }

    private void listarPorGenero() throws SQLException {
        System.out.println("\n[Listado de Peliculas ordenados por género]");
        listarPeliculas(ComparadorGenero.POR_GENERO);
    }
    
    private void listarPorDuracion() throws SQLException {
        System.out.println("\n[Listado de Peliculas ordenados por duracion]");
        listarPeliculas(ComparadorDuracion.POR_DURACION);
    }

    private void listarPeliculas(Comparator<Pelicula> comparador) throws SQLException {
        List<Pelicula> lista = peliculasDao.listarTodos(comparador);
        if (lista.isEmpty()) {
            System.out.println("(sin registros)");
            return;
        }
        for (Pelicula p : lista) {
            imprimirPelicula(p);
        }
    }

    private void imprimirPelicula(Pelicula p) {
            System.out.printf("Titulo=%s | ELenco: %s | Director: %s | Genero: %s | Duracion: %.2f | Idiomas de Audio: %s | Idiomas de Subtitulos= %s | Sinopsis: %s%n",
                    p.getTitulo(), p.getElenco(), p.getDirector(), p.getGenero(), p.getDuracion(), p.getAudio(), p.getSubtitulos(), p.getSinopsis());
        }
    
      private double leerDouble(String msg) {
    while (true) {
        System.out.print(msg);
        String s = in.nextLine().trim();
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            System.out.println("Ingrese un número decimal válido.");
        }
    }
}     


    // ===== SUBMENU RESEÑAS =====
    private void submenuReseñas() throws SQLException {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- Reseñas ---");
            System.out.println("1) Registrar reseña");
            System.out.println("2) Aprobar reseña");
            System.out.println("0) Volver");
            int op = leerEntero("Opción: ");

            switch (op) {
                case 1 -> registrarReseña();
                case 2 -> aprobarReseña();
                case 0 -> volver = true;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

   private void registrarReseña() throws SQLException {
        System.out.println("\n[Registrar Reseña]");
        
        // 1. Validar usuario
        System.out.println("Por favor, ingrese sus credenciales:");
        String nombreUsuario = leerTexto("Nombre de usuario: ");
        String contrasenia = leerTexto("Contraseña: ");
        
        Optional<Usuario> optUsuario = usuariosDao.buscarPorNombreUsuario(nombreUsuario);
        if (optUsuario.isEmpty() || !optUsuario.get().getContrasenia().equals(contrasenia)) {
            System.out.println("Credenciales inválidas.");
            return;
        }
        Usuario usuario = optUsuario.get();
        
        // 2. Mostrar películas disponibles ordenadas por ID
        System.out.println("\nPelículas disponibles:");
        List<Pelicula> peliculas = peliculasDao.listarTodos(ComparatorIdPelicula.POR_ID);
        if (peliculas.isEmpty()) {
            System.out.println("No hay películas disponibles.");
            return;
        }
        
        for (Pelicula p : peliculas) {
            System.out.printf("%d) %s%n", p.getId(), p.getTitulo());
        }
        
        // 3. Seleccionar película
        int idPelicula;
        Optional<Pelicula> optPelicula;
        do {
            idPelicula = leerEntero("\nSeleccione el número de la película: ");
            optPelicula = peliculas.stream()
                                 .filter(p -> p.getId() == idPelicula)
                                 .findFirst();
            if (optPelicula.isEmpty()) {
                System.out.println("Número de película inválido. Por favor, elija un número de la lista.");
            } else {
                break;
            }
        } while (true);
        
        // 4. Ingresar datos de la reseña
        int calificacion;
        do {
            calificacion = leerEntero("Calificación (1-5): ");
            if (calificacion < 1 || calificacion > 5) {
                System.out.println("La calificación debe estar entre 1 y 5.");
            } else {
                break;
            }
        } while (true);
        
        String comentario = leerTexto("Comentario: ");
        
        // 5. Crear y guardar la reseña
        Reseña nuevaReseña = new Reseña(calificacion, comentario, usuario.getId(), idPelicula);
        reseñasDao.registrar(nuevaReseña);
        
        System.out.println("¡Reseña registrada con éxito!");
    }


    private void aprobarReseña() throws SQLException {
        System.out.println("\n[Aprobar Reseña]");
        
        // Primero verificar que sea un usuario administrador
        System.out.println("Por favor, ingrese sus credenciales de administrador:");
        String nombreUsuario = leerTexto("Nombre de usuario: ");
        String contrasenia = leerTexto("Contraseña: ");
        
        Optional<Usuario> optUsuario = usuariosDao.buscarPorNombreUsuario(nombreUsuario);
        if (optUsuario.isEmpty() || !optUsuario.get().getContrasenia().equals(contrasenia)) {
            System.out.println("Credenciales inválidas.");
            return;
        }
                
        // Listar reseñas pendientes de aprobación
        System.out.println("\nReseñas pendientes de aprobación:");
        List<Reseña> reseñasPendientes = reseñasDao.listarPendientesAprobacion();
        
        if (reseñasPendientes.isEmpty()) {
            System.out.println("No hay reseñas pendientes de aprobación.");
            return;
        }
        
        for (Reseña r : reseñasPendientes) {
            System.out.printf("ID=%d | Película ID=%d | Usuario ID=%d | Calificación=%d | Comentario=%s%n",
                    r.getId(), r.getId_pelicula(), r.getId_usuario(), r.getCalificacion(), r.getComentario());
        }
        
        // Seleccionar reseña a aprobar
        int idReseña = leerEntero("\nIngrese el ID de la reseña a aprobar (0 para cancelar): ");
        if (idReseña == 0) {
            System.out.println("Operación cancelada.");
            return;
        }
        
        Optional<Reseña> optReseña = reseñasDao.aprobar(idReseña);
        if (optReseña.isPresent()) {
            System.out.println("Reseña aprobada con éxito.");
        } else {
            System.out.println("No se encontró la reseña o no se pudo aprobar.");
        }
    }
}