package controllers;

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
                if (usuarioLogueado.isPrimerAcceso()) {

                    lista = peliculasService.obtenerTop10PorRating();

                    usuarioLogueado.setPrimerAcceso(false);

                    usuarioService.actualizarUsuario(
                            usuarioLogueado.getId(),
                            usuarioLogueado.getNombreUsuario(),
                            usuarioLogueado.getEmail(),
                            usuarioLogueado.getContrasenia(),
                            usuarioLogueado.getDniPersona()
                    );

                } else {
                    lista = peliculasService.obtener10RandomExcluyendo(
                            usuarioLogueado.getPeliculasResenadas()
                    );
                }

                cargarPeliculasEnLista(lista);
                vista.mostrarTarjeta(BienvenidaView.NOMBRE_CONTENIDO);

            } catch (SQLException ex) {
                mostrarError("Error al cargar películas: " + ex.getMessage());
                vista.mostrarTarjeta(BienvenidaView.NOMBRE_CONTENIDO);
            }
        });

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
