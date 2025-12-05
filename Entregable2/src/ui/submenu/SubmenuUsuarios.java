package ui.submenu;

import service.UsuariosService;
import model.Usuario;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class SubmenuUsuarios {

    private final Scanner in;
    private final UsuariosService usuariosService;

    public SubmenuUsuarios(Scanner in, UsuariosService usuariosService) {
        this.in = in;
        this.usuariosService = usuariosService;
    }

    public void mostrar() throws SQLException {
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
        }
    }

    private void altaUsuario() throws SQLException {
        System.out.println("\n[Alta de Usuario]");
        
        int dni;
        do {
            dni = leerEntero("DNI de la persona: ");
            if (dni <= 0) {
                System.out.println("El DNI debe ser un número positivo.");
                continue;
            }
            if (!usuariosService.personaExiste(dni)) {
                System.out.println("No existe una persona con ese DNI. Debe registrar primero sus datos personales.");
                continue;
            }
            break;
        } while (true);

        String nombreUsuario;
        do {
            nombreUsuario = leerTexto("Nombre de usuario: ");
            if (!usuariosService.validarNombreUsuario(nombreUsuario)) {
                System.out.println("El nombre de usuario no puede estar vacío o ya existe. Por favor, elija otro.");
                continue;
            }
            break;
        } while (true);

        String email;
        do {
            email = leerTexto("Email: ");
            if (!usuariosService.validarEmail(email)) {
                System.out.println("Email inválido.");
                continue;
            }
            break;
        } while (true);

        String contrasenia;
        do {
            contrasenia = leerTexto("Contraseña: ");
            if (!usuariosService.validarContrasenia(contrasenia)) {
                System.out.println("La contraseña debe tener al menos 6 caracteres.");
                continue;
            }
            break;
        } while (true);

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
            usuario = usuariosService.crearUsuario(nombreUsuario, email, contrasenia, dni);
            System.out.println("Usuario creado con ID: " + usuario.getId());
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    private void listarUsuariosPorNombre() throws SQLException {
        System.out.println("\n[Listado de Usuarios ordenados por nombre]");
        List<Usuario> lista = usuariosService.listarTodosPorNombre();
        listarUsuariosAux(lista);
    }

    private void listarUsuariosPorEmail() throws SQLException {
        System.out.println("\n[Listado de Usuarios ordenados por email]");
        List<Usuario> lista = usuariosService.listarTodosPorEmail();
        listarUsuariosAux(lista);
    }

    private void listarUsuariosAux(List<Usuario> lista) throws SQLException {
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
        Optional<Usuario> opt = usuariosService.buscarPorId(id);
        if (opt.isEmpty()) {
            System.out.println("No existe usuario con ID " + id);
            return;
        }
        imprimirUsuario(opt.get());
    }

    private void buscarUsuarioPorNombre() throws SQLException {
        System.out.println("\n[Buscar Usuario por nombre]");
        String nombre = leerTexto("Nombre de usuario: ");
        Optional<Usuario> opt = usuariosService.buscarPorNombreUsuario(nombre);
        if (opt.isEmpty()) {
            System.out.println("No existe usuario con nombre '" + nombre + "'");
            return;
        }
        imprimirUsuario(opt.get());
    }

    private void actualizarUsuario() throws SQLException {
        System.out.println("\n[Actualizar Usuario]");
        int id = leerEntero("ID del usuario a actualizar: ");
        Optional<Usuario> opt = usuariosService.buscarPorId(id);

        if (opt.isEmpty()) {
            System.out.println("No existe usuario con ID " + id);
            return;
        }

        Usuario actual = opt.get();
        imprimirUsuario(actual);

        String nombreUsuario = leerTextoOpcional("Nuevo nombre de usuario (enter para mantener): ");
        if (!nombreUsuario.isBlank() && !nombreUsuario.equals(actual.getNombreUsuario())) {
            if (!usuariosService.validarNombreUsuario(nombreUsuario)) {
                System.out.println("Ya existe un usuario con ese nombre. No se actualizará el nombre.");
                nombreUsuario = actual.getNombreUsuario();
            }
        } else {
            nombreUsuario = actual.getNombreUsuario();
        }

        String email = leerTextoOpcional("Nuevo email (enter para mantener): ");
        if (!email.isBlank() && !usuariosService.validarEmail(email)) {
            System.out.println("Email inválido. Se mantiene el anterior.");
            email = actual.getEmail();
        } else if (email.isBlank()) {
            email = actual.getEmail();
        }

        String contrasenia = leerTextoOpcional("Nueva contraseña (enter para mantener): ");
        if (!contrasenia.isBlank() && !usuariosService.validarContrasenia(contrasenia)) {
            System.out.println("La contraseña debe tener al menos 6 caracteres. Se mantiene la anterior.");
            contrasenia = actual.getContrasenia();
        } else if (contrasenia.isBlank()) {
            contrasenia = actual.getContrasenia();
        }

        boolean ok = usuariosService.actualizarUsuario(id, nombreUsuario, email, contrasenia, actual.getDniPersona());
        System.out.println(ok ? "Actualización exitosa." : "No se realizó la actualización.");
    }

    private void eliminarUsuario() throws SQLException {
        System.out.println("\n[Eliminar Usuario]");
        int id = leerEntero("ID del usuario a eliminar: ");
        boolean ok = usuariosService.eliminarUsuario(id);
        System.out.println(ok ? "Usuario eliminado correctamente." : "No se encontró usuario con ese ID.");
    }

    private void imprimirUsuario(Usuario u) throws SQLException {
        String nombreCompleto = usuariosService.obtenerNombreCompletoPersona(u.getDniPersona());
        System.out.printf("ID=%d | Usuario: %s | Email: %s | Persona: %s (DNI %d)%n",
                u.getId(), u.getNombreUsuario(), u.getEmail(), nombreCompleto, u.getDniPersona());
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

    private String leerTexto(String msg) {
        System.out.print(msg);
        return in.nextLine().trim();
    }

    private String leerTextoOpcional(String msg) {
        System.out.print(msg);
        return in.nextLine();
    }
}
