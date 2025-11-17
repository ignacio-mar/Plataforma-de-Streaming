package ui;

import ui.submenu.SubmenuPersonas;
import ui.submenu.SubmenuUsuarios;
import ui.submenu.SubmenuPeliculas;
import ui.submenu.SubmenuReseñas;
import service.PersonasService;
import service.UsuariosService;
import service.PeliculasService;
import service.ReseñasService;
import java.sql.SQLException;
import java.util.Scanner;

public class Menu {

    private final Scanner in;
    private final SubmenuPersonas submenuPersonas;
    private final SubmenuUsuarios submenuUsuarios;
    private final SubmenuPeliculas submenuPeliculas;
    private final SubmenuReseñas submenuReseñas;

    public Menu(Scanner in, PersonasService personasService, UsuariosService usuariosService, 
                PeliculasService peliculasService, ReseñasService reseñasService) {
        this.in = in;
        this.submenuPersonas = new SubmenuPersonas(in, personasService);
        this.submenuUsuarios = new SubmenuUsuarios(in, usuariosService);
        this.submenuPeliculas = new SubmenuPeliculas(in, peliculasService);
        this.submenuReseñas = new SubmenuReseñas(in, reseñasService);
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
                    case 1 -> submenuPersonas.mostrar();
                    case 2 -> submenuUsuarios.mostrar();
                    case 3 -> submenuPeliculas.mostrar();
                    case 4 -> submenuReseñas.mostrar();
                    case 0 -> salir = true;
                    default -> System.out.println("Opción inválida.");
                }
            } catch (SQLException e) {
                System.out.println("Error SQL: " + e.getMessage());
            }
        }
        System.out.println("¡Hasta luego!");
    }

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
}