package ui.submenu;

import service.PersonasService;
import model.DatosPersonales;
import model.enums.Paises;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class SubmenuPersonas {

    private final Scanner in;
    private final PersonasService personasService;

    public SubmenuPersonas(Scanner in, PersonasService personasService) {
        this.in = in;
        this.personasService = personasService;
    }

    public void mostrar() throws SQLException {
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
        }
    }

    private void altaPersona() throws SQLException {
        System.out.println("\n[Alta de Persona]");
        
        int dni;
        do {
            dni = leerEntero("DNI: ");
            if (!personasService.validarDni(dni)) {
                System.out.println("El DNI debe ser un número positivo.");
                continue;
            }
            if (personasService.dniExiste(dni)) {
                System.out.println("Ya existe una persona con DNI " + dni + ". Por favor, ingrese otro.");
                continue;
            }
            break;
        } while (true);

        String nombre;
        do {
            nombre = leerTexto("Nombre(s): ");
            if (!personasService.validarNombre(nombre)) {
                System.out.println("El nombre no puede estar vacío y solo puede contener letras y espacios.");
                continue;
            }
            break;
        } while (true);

        String apellido;
        do {
            apellido = leerTexto("Apellido: ");
            if (!personasService.validarApellido(apellido)) {
                System.out.println("El apellido no puede estar vacío y solo puede contener letras y espacios.");
                continue;
            }
            break;
        } while (true);
        
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
        
        String tel;
        do {
            tel = leerTexto("Número de teléfono: ");
            if (!personasService.validarTelefono(tel)) {
                System.out.println("El número de teléfono no puede estar vacío.");
                continue;
            }
            break;
        } while (true);

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
            dp = personasService.crearPersona(nombre, apellido, dni, pais, tel);
            System.out.println("Persona guardada con ID: " + dp.getId());
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    private void listarPersonas() throws SQLException {
        System.out.println("\n[Listado de Personas]");
        List<DatosPersonales> lista = personasService.listarTodas();
        if (lista.isEmpty()) {
            System.out.println("(sin registros)");
            return;
        }
        for (DatosPersonales p : lista) {
            imprimirPersona(p);
        }
    }

    private void buscarPorId() throws SQLException {
        System.out.println("\n[Buscar por ID]");
        int id = leerEntero("ID: ");
        Optional<DatosPersonales> opt = personasService.buscarPorId(id);
        if (opt.isEmpty()) {
            System.out.println("No existe persona con ID " + id);
            return;
        }
        imprimirPersona(opt.get());
    }

    private void buscarPorDni() throws SQLException {
        System.out.println("\n[Buscar por DNI]");
        int dni = leerEntero("DNI: ");
        Optional<DatosPersonales> opt = personasService.buscarPorDni(dni);
        if (opt.isEmpty()) {
            System.out.println("No se encontró persona con DNI " + dni);
            return;
        }
        imprimirPersona(opt.get());
    }

    private void actualizarPersona() throws SQLException {
        System.out.println("\n[Actualizar Persona]");
        int id = leerEntero("ID de la persona a actualizar: ");
        Optional<DatosPersonales> opt = personasService.buscarPorId(id);

        if (opt.isEmpty()) {
            System.out.println("No existe persona con ID " + id);
            return;
        }

        DatosPersonales actual = opt.get();
        imprimirPersona(actual);

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
            } else if (dniIngresado != actual.getDni() && personasService.dniExiste(dniIngresado)) {
                System.out.println("Ya existe una persona con ese DNI. Se mantiene el anterior.");
            } else {
                nuevoDni = dniIngresado;
            }
        }

        boolean ok = personasService.actualizarPersona(id, 
            nombre.isBlank() ? actual.getNombres() : nombre,
            apellido.isBlank() ? actual.getApellido() : apellido,
            nuevoDni,
            nuevoPais,
            tel.isBlank() ? actual.getNumeroTelefono() : tel
        );
        System.out.println(ok ? "Actualización exitosa." : "No se actualizó ninguna fila.");
    }

    private void eliminarPersona() throws SQLException {
        System.out.println("\n[Eliminar Persona]");
        int id = leerEntero("ID a eliminar: ");
        boolean ok = personasService.eliminarPersona(id);
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
