package ui.submenu;

import service.ReseñasService;
import model.Pelicula;
import model.Reseña;
import model.Usuario;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class SubmenuReseñas {

    private final Scanner in;
    private final ReseñasService reseñasService;

    public SubmenuReseñas(Scanner in, ReseñasService reseñasService) {
        this.in = in;
        this.reseñasService = reseñasService;
    }

    public void mostrar() throws SQLException {
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

        System.out.println("Por favor, ingrese sus credenciales:");
        String nombreUsuario = leerTexto("Nombre de usuario: ");
        String contrasenia = leerTexto("Contraseña: ");

        Optional<Usuario> optUsuario = reseñasService.autenticarUsuario(nombreUsuario, contrasenia);
        if (optUsuario.isEmpty()) {
            System.out.println("Credenciales inválidas.");
            return;
        }
        Usuario usuario = optUsuario.get();

        System.out.println("\nPelículas disponibles:");
        List<Pelicula> peliculas = reseñasService.listarPeliculasDisponibles();
        if (peliculas.isEmpty()) {
            System.out.println("No hay películas disponibles.");
            return;
        }

        for (Pelicula p : peliculas) {
            System.out.printf("%d) %s%n", p.getId(), p.getTitulo());
        }

        int idPelicula;
        Optional<Pelicula> optPelicula;
        do {
            idPelicula = leerEntero("\nSeleccione el número de la película: ");
            optPelicula = Optional.empty();

            for (Pelicula p : peliculas) {
                if (p.getId() == idPelicula) {
                    optPelicula = Optional.of(p);
                    break;
                }
            }

            if (optPelicula.isEmpty()) {
                System.out.println("Número de película inválido. Por favor, elija un número de la lista.");
            } else {
                break;
            }
        } while (true);

        int calificacion;
        while (true) {
            calificacion = leerEntero("Calificación (1-5): ");
            if (!reseñasService.validarCalificacion(calificacion)) {
                System.out.println("La calificación debe estar entre 1 y 5.");
            } else {
                break;
            }
        }

        String comentario = leerTexto("Comentario: ");

        reseñasService.registrarReseña(calificacion, comentario, usuario.getId(), idPelicula);
        System.out.println("¡Reseña registrada con éxito!");
    }

    private void aprobarReseña() throws SQLException {
        System.out.println("\n[Aprobar Reseña]");
        
        System.out.println("Por favor, ingrese sus credenciales:");
        String nombreUsuario = leerTexto("Nombre de usuario: ");
        String contrasenia = leerTexto("Contraseña: ");
        
        Optional<Usuario> optUsuario = reseñasService.autenticarUsuario(nombreUsuario, contrasenia);
        if (optUsuario.isEmpty()) {
            System.out.println("Credenciales inválidas.");
            return;
        }
                
        System.out.println("\nReseñas pendientes de aprobación:");
        List<Reseña> reseñasPendientes = reseñasService.listarPendientesAprobacion();
        
        if (reseñasPendientes.isEmpty()) {
            System.out.println("No hay reseñas pendientes de aprobación.");
            return;
        }
        
        for (Reseña r : reseñasPendientes) {
            System.out.printf("ID=%d | Película ID=%d | Usuario ID=%d | Calificación=%d | Comentario=%s%n",
                    r.getId(), r.getId_pelicula(), r.getId_usuario(), r.getCalificacion(), r.getComentario());
        }
        
        int idReseña = leerEntero("\nIngrese el ID de la reseña a aprobar (0 para cancelar): ");
        if (idReseña == 0) {
            System.out.println("Operación cancelada.");
            return;
        }
        
        Optional<Reseña> optReseña = reseñasService.aprobarReseña(idReseña);
        if (optReseña.isPresent()) {
            System.out.println("Reseña aprobada con éxito.");
        } else {
            System.out.println("No se encontró la reseña o no se pudo aprobar.");
        }
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
}
