package controllers;

import modelo.Pelicula;
import modelo.service.OmdbService;
import views.InformacionVista;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;
import modelo.exceptions.BusquedaInvalidaException;
import modelo.exceptions.ErrorConexionAPIException;
import modelo.exceptions.PeliculaNoEncontradaException;


public class InformacionController {
    
    private final OmdbService omdbService;
    private final InformacionVista vista;

    public InformacionController(OmdbService omdbService, InformacionVista vista) {
        this.omdbService = omdbService;
        this.vista = vista;

        this.vista.getBotonBuscar().addActionListener(new BusquedaListener());
    }

    private class BusquedaListener implements ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {

            String titulo = vista.getCampoTitulo().getText().trim();
            try {
                if (titulo.isEmpty() || titulo.length() < 3) {
                    throw new BusquedaInvalidaException(titulo);
                }
                
                iniciarBusquedaConcurrente(titulo);

            } catch (Exception ex) {
                vista.mostrarError(ex.getMessage());
            }
        }

        private void iniciarBusquedaConcurrente(String titulo) {
            vista.setEstadoBusqueda("Buscando...");

            Runnable tareaBusqueda = () -> {
                Pelicula resultadoPeli = null;
                String mensajeError = null;

                try {
                    resultadoPeli = omdbService.buscarPelicula(titulo);
                } catch (PeliculaNoEncontradaException ex) {
                    mensajeError = ex.getMessage();
                }
                catch (ErrorConexionAPIException ex) {
                    mensajeError = "Error de red. Intenta de nuevo más tarde. Causa: " + ex.getMessage();
                }
                catch (Exception ex) {
                    mensajeError = "Error al buscar la película: " + ex.getMessage();
                }

                SwingUtilities.invokeLater(() -> {
                    if (resultadoPeli != null) {
                        vista.mostrarInformacionPelicula(resultadoPeli);
                        vista.setEstadoBusqueda("Búsqueda completada.");
                    } else {
                        vista.mostrarError(mensajeError);
                        vista.setEstadoBusqueda("Búsqueda fallida.");
                    }
                });
            };

            new Thread(tareaBusqueda).start();
        }    

    }

}
