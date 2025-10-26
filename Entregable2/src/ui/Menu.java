package ui;

import dao.DatosPersonalesDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import model.DatosPersonales;
import model.Enums.Paises;

public class Menu {

    private final Scanner in;
    private final DatosPersonalesDAO dpDao;

    public Menu(Scanner in, DatosPersonalesDAO dpDao) {
        this.in = in;
        this.dpDao = dpDao;
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1) Personas");
            System.out.println("9) (Futuro) Usuarios");
            System.out.println("10) (Futuro) Películas");
            System.out.println("0) Salir");
            int op = leerEntero("Opción: ");

            switch (op) {
                case 1 -> submenuPersonas();
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
        int dni = leerEntero("DNI: ");

        // Validación de DNI único (recomendado)
        if (dpDao.buscarPorDni(dni).isPresent()) {
            System.out.println("Ya existe una persona con DNI " + dni + ". Operación cancelada.");
            return;
        }

        String nombre = leerTexto("Nombre(s): ");
        String apellido = leerTexto("Apellido: ");
        
        System.out.println("Países disponibles:");
        for (Paises pais : Paises.values()) {
            System.out.println("- " + pais.name());
        }
        Paises pais = null;
        while (pais == null) {
            try {
                pais = Paises.valueOf(leerTexto("País de residencia (escriba exactamente como aparece arriba): ").toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("País inválido. Por favor, seleccione uno de la lista.");
            }
        }
        
        String tel = leerTexto("Número de teléfono: ");

        DatosPersonales dp = new DatosPersonales(nombre, apellido, dni, pais, tel);
        dp = dpDao.guardar(dp);

        System.out.println("Persona guardada con ID: " + dp.getId());
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
}
//pepe