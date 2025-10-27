package ui;

import dao.DatosPersonalesDAO;
import dao.UsuariosDAO;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import model.DatosPersonales;
import model.Enums.Paises;
import model.Usuario;

public class Menu {

    private final Scanner in;
    private final DatosPersonalesDAO dpDao;
    private final UsuariosDAO usuariosDao;

    public Menu(Scanner in, DatosPersonalesDAO dpDao, UsuariosDAO usuariosDao) {
        this.in = in;
        this.dpDao = dpDao;
        this.usuariosDao = usuariosDao;
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1) Personas");
            System.out.println("2) Usuarios");
            System.out.println("10) (Futuro) Películas");
            System.out.println("0) Salir");
            int op = leerEntero("Opción: ");

            switch (op) {
                case 1 -> submenuPersonas();
                case 2 -> submenuUsuarios();
                case 0 -> salir = true;
                default -> System.out.println("Opción inválida.");
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
}