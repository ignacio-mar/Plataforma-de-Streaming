package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.*;
import model.Pelicula;
import model.Usuario;
import model.exceptions.BusquedaInvalidaException;
import model.exceptions.ErrorConexionAPIException;
import model.exceptions.PeliculaNoEncontradaException;
import model.service.OmdbService;
import service.PeliculasService;
import service.PersonasService;
import service.ReseñasService;
import service.UsuariosService;
import view.login.LoginView;
import view.menuPrincipal.*;

import java.awt.Component;

public class BienvenidaController implements ActionListener {

    private final BienvenidaView vista;
    private final Usuario usuarioLogueado;
    private final UsuariosService usuarioService;
    private final PersonasService personasService;
    private final PeliculasService peliculasService;
    private final ReseñasService reseñasService;
    private final PanelContenidoPrincipal panelListo;

    private final OmdbService omdbService = new OmdbService();


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

        panelListo.btnCerrarSesion.addActionListener(this);
        panelListo.btnBuscar.addActionListener(this);
        panelListo.btnIniciarCarga.addActionListener(this);
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

        Consumer<Exception> onError = (ex) -> SwingUtilities.invokeLater(() -> {
            mostrarError("ERROR durante la importación: " + ex.getMessage());
            panelListo.btnIniciarCarga.setEnabled(true);
            vista.mostrarTarjeta(BienvenidaView.NOMBRE_CONTENIDO);
        });

        Consumer<Integer> onProgress = (porcentaje) -> SwingUtilities.invokeLater(() -> {
            vista.progressBar.setValue(porcentaje);
        });

        
        Runnable onSuccess = () -> SwingUtilities.invokeLater(() -> {
            try {
                List<Pelicula> lista;

                if (usuarioLogueado.isPrimerAcceso()) {

                    lista = peliculasService.obtenerTop10PorRating();

                    usuarioLogueado.setPrimerAcceso(false);
                    usuarioService.actualizarUsuario(usuarioLogueado);

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

        peliculasService.importarDesdeCsvAsync(rutaCsv, onSuccess, onError, onProgress);
    }


private void buscarPelicula() {
    String tituloBuscado = panelListo.txtBuscador.getText().trim();

    try {
        // Si el término es inválido, OmdbService va a tirar BusquedaInvalidaException
        Pelicula pelicula = omdbService.buscarPelicula(tituloBuscado);

        // Ventana con datos reales de la peli
        InformacionPeliculaView infoView = new InformacionPeliculaView(
                vista,
                pelicula.getTitulo(),
                pelicula.getAnio(),
                pelicula.getSinopsis()
        );
        infoView.setVisible(true);

    } catch (BusquedaInvalidaException ex) {
        JOptionPane.showMessageDialog(
                vista,
                ex.getMessage(),
                "Búsqueda inválida",
                JOptionPane.WARNING_MESSAGE
        );

    } catch (PeliculaNoEncontradaException ex) {
        // Si no se encuentra la película, mostramos:
        // - Título buscado
        // - Resumen "No se encuentra disponible"
        InformacionPeliculaView infoView = new InformacionPeliculaView(
                vista,
                tituloBuscado,
                0,                              // año no disponible
                "No se encuentra disponible."
        );
        infoView.setVisible(true);

    } catch (ErrorConexionAPIException ex) {
        JOptionPane.showMessageDialog(
                vista,
                "No se pudo consultar la API de películas.\nDetalle: " + ex.getMessage(),
                "Error de conexión",
                JOptionPane.ERROR_MESSAGE
        );

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(
                vista,
                "Ocurrió un error inesperado al buscar la película.",
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
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
        LoginView lv = new LoginView();
        new LoginController(lv, usuarioService, personasService, peliculasService, reseñasService);
        lv.setVisible(true);
    }


    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }


    private void cargarPeliculasEnLista(List<Pelicula> peliculas) {
            // le decimos a la vista que dibuje las filas
            panelListo.mostrarPeliculas(peliculas);

            // Ahora recorremos esas filas para conectar los botones
            Component[] componentes = panelListo.panelListaPeliculas.getComponents();

            for (Component comp : componentes) {
                if (comp instanceof PanelFilaPelicula) {
                    PanelFilaPelicula fila = (PanelFilaPelicula) comp;
                    Pelicula peli = fila.getPelicula();

                    
                    boolean yaResenada = usuarioLogueado.getPeliculasResenadas().contains(peli.getId());

                    if (yaResenada) {
                      fila.marcarComoCalificada();
                    } else {
                        fila.getBtnCalificar().addActionListener(e -> {
                            
                            new CalificarController(
                                vista,             
                                usuarioLogueado,   
                                fila,              
                                usuarioService     
                            );
                        });
                    }
                }
            }
        }
}

