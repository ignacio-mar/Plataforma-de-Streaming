package ui.submenu;

import service.PeliculasService;
import model.Pelicula;
import model.enums.Generos;
import model.enums.Idiomas;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class SubmenuPeliculas {

    private final Scanner in;
    private final PeliculasService peliculasService;

    public SubmenuPeliculas(Scanner in, PeliculasService peliculasService) {
        this.in = in;
        this.peliculasService = peliculasService;
    }

    public void mostrar() throws SQLException {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- Peliculas---");
            System.out.println("1) Alta (guardar)");
            System.out.println("2) Listar Por Titulo");
            System.out.println("3) Listar Por Genero");
            System.out.println("4) listar Por Duracion");
            System.out.println("0) Volver");
            int op = leerEntero("Opción: ");

            switch (op) {
                case 1 -> altaPelicula();
                case 2 -> listarPorTitulo();
                case 3 -> listarPorGenero();
                case 4 -> listarPorDuracion();
                case 0 -> volver = true;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private void altaPelicula() throws SQLException {
        System.out.println("\n[Alta de Pelicula]");

        String titulo;
        while (true) {
            titulo = leerTexto("Título: ");
            if (!peliculasService.validarTitulo(titulo)) {
                System.out.println("El título no puede estar vacío.");
                continue;
            }
            break;
        }

        String elenco;
        while (true) {
            elenco = leerTexto("Elenco (separado por comas, ej: Actor A, Actor B, ...): ");
            if (!peliculasService.validarElenco(elenco)) {
                System.out.println("El elenco no puede estar vacío y debe contener múltiples nombres separados por comas.");
                continue;
            }
            break;
        }

        String director;
        while (true) {
            director = leerTexto("Director: ");
            if (!peliculasService.validarDirector(director)) {
                System.out.println("El director no puede estar vacío y solo puede contener letras y espacios.");
                continue;
            }
            break;
        }

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

        double duracion;
        while (true) {
            duracion = leerDouble("Duración de la película (en minutos, ej: 90.5): ");
            if (!peliculasService.validarDuracion(duracion)) {
                System.out.println("La duración debe ser un valor positivo (mayor que cero).");
                continue;
            }
            break;
        }

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

        String sinopsis = leerTexto("Sinopsis (opcional): ");

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

        if (confirma.equals("s")) {
            Pelicula pelicula = peliculasService.crearPelicula(titulo, elenco, director, genero, duracion, audio, subtitulos, sinopsis);
            System.out.println("Película guardada con ID: " + pelicula.getId());
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    private void listarPorTitulo() throws SQLException {
        System.out.println("\n[Listado de Peliculas ordenados por titulo]");
        listarPeliculasAux(peliculasService.listarPorTitulo());
    }

    private void listarPorGenero() throws SQLException {
        System.out.println("\n[Listado de Peliculas ordenados por género]");
        listarPeliculasAux(peliculasService.listarPorGenero());
    }
    
    private void listarPorDuracion() throws SQLException {
        System.out.println("\n[Listado de Peliculas ordenados por duracion]");
        listarPeliculasAux(peliculasService.listarPorDuracion());
    }

    private void listarPeliculasAux(List<Pelicula> lista) {
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
}
