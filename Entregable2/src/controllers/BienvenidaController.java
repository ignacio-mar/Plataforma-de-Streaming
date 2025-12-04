/* package controllers

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.*;
import model.Pelicula;
import model.Usuario;
import service.PeliculasService;
import service.PersonasService;
import service.ReseñasService;
import service.UsuariosService;
import view.login.LoginView;
import view.menuPrincipal.*;

public class BienvenidaController implements ActionListener {

    private final BienvenidaView vista;
    private final Usuario usuarioLogueado;

    private final UsuariosService usuarioService; 
    private final PersonasService personasService;
    private final PeliculasService peliculasService;
    private final ReseñasService reseñasService;

    private final PanelContenidoPrincipal panelListo;

    public BienvenidaController(
        BienvenidaView vista, 
        Usuario usuarioLogueado, 
        PeliculasService peliculasService, 
        ReseñasService reseñasService,
        PersonasService personasService,
        UsuariosService usuarioService
    ) {
        this.vista = vista;
        this.usuarioLogueado = usuarioLogueado;
        this.usuarioService = usuarioService;
        this.personasService = personasService;
        this.peliculasService = peliculasService;
        this.reseñasService = reseñasService;
        this.panelListo = vista.panelListo;

        configurarVistaInicial();

        this.panelListo.btnCerrarSesion.addActionListener(this);
        this.panelListo.btnBuscar.addActionListener(this);
        this.panelListo.btnIniciarCarga.addActionListener(this); 
    }
    
    private void configurarVistaInicial() {
        panelListo.lblNombreUsuario.setText("Bienvenido, " + usuarioLogueado.getNombreUsuario());
        vista.mostrarTarjeta(BienvenidaView.NOMBRE_CONTENIDO);
        vista.setVisible(true);
    }

  // En BienvenidaController.java

private void iniciarCargaPeliculas() {
    
    panelListo.btnIniciarCarga.setEnabled(false);
    vista.mostrarTarjeta(BienvenidaView.NOMBRE_CARGA);
    
    String rutaCsv = BienvenidaView.RUTA_CSV_PELICULAS;
    
    Runnable onSuccess = () -> SwingUtilities.invokeLater(() -> {
        try {
            List<Pelicula> lista;

            // -------------------------------
            // PRIMERA VEZ: TOP 10
            // -------------------------------
            if (usuarioLogueado.isPrimerAcceso()) { // Asumiendo que existe el getter
                
                // 1. Cargar todas las películas del CSV y obtener el Top 10
                lista = peliculasService.obtenerTop10PorRating();

                // 2. Marcar al usuario como NO PRIMER ACCESO (Persistencia)
                usuarioLogueado.setPrimerAcceso(false); // Actualizar objeto en memoria
                usuarioService.actualizarUsuario( // Persistir en la BD
                        usuarioLogueado.getId(),
                        usuarioLogueado.getNombreUsuario(),
                        usuarioLogueado.getEmail(),
                        usuarioLogueado.getContrasenia(),
                        usuarioLogueado.getDniPersona()
                );

            } else {
                // -------------------------------
                // ACCESO POSTERIOR: 10 al azar (Excluyendo las reseñadas)
                // -------------------------------
                lista = peliculasService.obtener10RandomExcluyendo(
                        usuarioLogueado.getPeliculasResenadas()
                );
            }

            // --- MOSTRAR CONTENIDO ---
            cargarPeliculasEnLista(lista);
            vista.mostrarTarjeta(BienvenidaView.NOMBRE_CONTENIDO);

        } catch (SQLException ex) {
            mostrarError("Error al obtener datos de la BD: " + ex.getMessage());
            vista.mostrarTarjeta(BienvenidaView.NOMBRE_CONTENIDO);
        }
    });

    // ... (onError, onProgress, y llamada a importarDesdeCsvAsync son correctos) ...
}

        Consumer<Exception> onError = (ex) -> SwingUtilities.invokeLater(() -> {
            mostrarError("ERROR CRÍTICO: " + ex.getMessage());
            panelListo.btnIniciarCarga.setEnabled(true);
            vista.mostrarTarjeta(BienvenidaView.NOMBRE_CONTENIDO);
        });

        Consumer<Integer> onProgress = (porcentaje) -> SwingUtilities.invokeLater(() -> {
            vista.progressBar.setValue(porcentaje);
        });

        peliculasService.importarDesdeCsvAsync(
                rutaCsv,
                onSuccess,
                onError,
                onProgress
        );
    }


    private void cargarPeliculasEnLista(List<Pelicula> peliculas) {
        panelListo.mostrarPeliculas(peliculas);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelListo.btnCerrarSesion) {
            cerrarSesion();
        } else if (e.getSource() == panelListo.btnBuscar) {
            buscarPelicula();
        } else if (e.getSource() == panelListo.btnIniciarCarga) {
            iniciarCargaPeliculas();
        }
    }
    
    private void cerrarSesion() {
        vista.dispose();
        LoginView nuevaLoginView = new LoginView();
        
        new LoginController(
            nuevaLoginView, 
            this.usuarioService, 
            this.personasService,
            this.peliculasService,
            this.reseñasService
        );
        
        nuevaLoginView.setVisible(true);
    }
    
    private void buscarPelicula() {
        String tituloBuscado = panelListo.txtBuscador.getText().trim();
        if (tituloBuscado.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese un título para buscar.", 
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        System.out.println("Buscando película: " + tituloBuscado);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
*/
package controllers;

import view.menuPrincipal.*;
import view.login.LoginView;
import service.PeliculasService;
import service.ReseñasService;
import service.UsuariosService;
import service.PersonasService;
import model.Usuario;
import model.Pelicula;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer; 

public class BienvenidaController implements ActionListener {

    private final BienvenidaView vista;
    private final Usuario usuarioLogueado;
    private final UsuariosService usuarioService;
    private final PersonasService personasService;
    private final PeliculasService peliculasService;
    private final ReseñasService reseñasService;
    private final PanelContenidoPrincipal panelListo;

    public BienvenidaController(
        BienvenidaView vista, 
        Usuario usuarioLogueado, 
        PeliculasService peliculasService, 
        ReseñasService reseñasService, 
        PersonasService personasService, 
        UsuariosService usuarioService
    ) {
        this.vista = vista;
        this.usuarioLogueado = usuarioLogueado;
        this.usuarioService = usuarioService;
        this.personasService = personasService;
        this.peliculasService = peliculasService;
        this.reseñasService = reseñasService;
        this.panelListo = vista.panelListo;

        // CONFIGURACION INICIAL
        configurarVistaInicial();

        // CABLEADO DE BOTONES (Listeners)
        this.panelListo.btnCerrarSesion.addActionListener(this);
        this.panelListo.btnBuscar.addActionListener(this);
        this.panelListo.btnIniciarCarga.addActionListener(this);
    }

    private void configurarVistaInicial() {
        panelListo.lblNombreUsuario.setText("Bienvenido, " + usuarioLogueado.getNombreUsuario());
        vista.mostrarTarjeta(BienvenidaView.NOMBRE_CONTENIDO);
        vista.setVisible(true);
    }

    private void iniciarCargaPeliculas() {
    panelListo.btnIniciarCarga.setEnabled(false);
    vista.mostrarTarjeta(BienvenidaView.NOMBRE_CARGA);

    String rutaCsv = BienvenidaView.RUTA_CSV_PELICULAS;

    // =========================================================
    // 1. DEFINICIÓN DE CALLBACKS (onError y onProgress)
    // =========================================================

    // Callback de Error: Se ejecuta si algo falla en la lectura/inserción del CSV
    Consumer<Exception> onError = (ex) -> SwingUtilities.invokeLater(() -> {
        mostrarError("ERROR CRÍTICO durante la importación: " + ex.getMessage());
        panelListo.btnIniciarCarga.setEnabled(true);
        vista.mostrarTarjeta(BienvenidaView.NOMBRE_CONTENIDO);
    });

    // Callback de Progreso: Se ejecuta por el servicio con el porcentaje
    Consumer<Integer> onProgress = (porcentaje) -> SwingUtilities.invokeLater(() -> {
        vista.progressBar.setValue(porcentaje);
    });
    
    // =========================================================
    // 2. DEFINICIÓN DEL CALLBACK DE ÉXITO (Runnable onSuccess)
    // =========================================================

    Runnable onSuccess = () -> SwingUtilities.invokeLater(() -> {
        
        // 1. Declarar 'lista' fuera del if/else para que sea visible
        List<Pelicula> lista;
        
        try {
            // 2. Lógica para obtener las películas
            if (usuarioLogueado.isPrimerAcceso()) {
                
                // PRIMERA VEZ: TOP 10
                lista = peliculasService.obtenerTop10PorRating();

                // 3. Persistir el cambio de estado
                usuarioLogueado.setPrimerAcceso(false); 
                usuarioService.actualizarUsuario(
                    usuarioLogueado.getId(),
                    usuarioLogueado.getNombreUsuario(),
                    usuarioLogueado.getEmail(),
                    usuarioLogueado.getContrasenia(),
                    usuarioLogueado.getDniPersona()
                );

            } else {
                // ACCESO POSTERIOR: 10 al azar
                lista = peliculasService.obtener10RandomExcluyendo(
                    usuarioLogueado.getPeliculasResenadas()
                );
            }

            // 4. MOSTRAR CONTENIDO (Fuera del try/catch anidado)
            cargarPeliculasEnLista(lista);
            vista.mostrarTarjeta(BienvenidaView.NOMBRE_CONTENIDO);

        } catch (SQLException ex) {
            // Capturamos cualquier error que haya ocurrido durante la persistencia o carga de datos
            mostrarError("Error al cargar películas o actualizar estado: " + ex.getMessage());
            vista.mostrarTarjeta(BienvenidaView.NOMBRE_CONTENIDO); // Aseguramos que la UI regrese
        }
    });

    // =========================================================
    // 3. LLAMADA FINAL AL SERVICIO ASÍNCRONO
    // =========================================================
    // Se pasan los 4 argumentos: ruta, onSuccess, onError, onProgress
    peliculasService.importarDesdeCsvAsync(rutaCsv, onSuccess, onError, onProgress);
}

    private void cargarPeliculasEnLista (List<Pelicula> peliculas) {
        // le pasamos la creación de los paneles visuales a la Vista
        panelListo.mostrarPeliculas(peliculas);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelListo.btnCerrarSesion) {
            cerrarSesion();
        } else if (e.getSource() == panelListo.btnBuscar) {
            buscarPelicula();
        } else if (e.getSource() == panelListo.btnIniciarCarga) {
            iniciarCargaPeliculas();
        }
    }
    
    private void cerrarSesion() {
        vista.dispose();
        LoginView nuevaLoginView = new LoginView();
        
        new LoginController(
            nuevaLoginView, 
            this.usuarioService, 
            this.personasService,
            this.peliculasService,
            this.reseñasService
        );
        
        nuevaLoginView.setVisible(true);
    }
    
    private void buscarPelicula() {
        String tituloBuscado = panelListo.txtBuscador.getText().trim();
        if (tituloBuscado.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese un título para buscar.", 
                                        "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        System.out.println("Buscando película: " + tituloBuscado);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}