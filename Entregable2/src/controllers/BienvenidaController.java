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
import javax.swing.SwingUtilities; 

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
                //  Obtener Top 10
                List<Pelicula> top10 = peliculasService.obtenerTop10PorRating();
                
                // Llamamos al método nuevo de la vista que usa las filas detalladas
                cargarPeliculasEnLista(top10); 

                // Mostrar contenido
                vista.mostrarTarjeta(BienvenidaView.NOMBRE_CONTENIDO); 
                
            } catch (SQLException ex) {
                mostrarError("Error al obtener Top 10: " + ex.getMessage());
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
        
        peliculasService.importarDesdeCsvAsync(rutaCsv, onSuccess, onError, onProgress);
    }

    private void cargarPeliculasEnLista(List<Pelicula> peliculas) {
        // le pasamos  la creación de los paneles visuales a la Vista
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
            JOptionPane.showMessageDialog(vista, "Ingrese un título para buscar.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        System.out.println("Buscando película: " + tituloBuscado);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}