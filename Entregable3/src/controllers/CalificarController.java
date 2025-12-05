package controllers;

import ui.mainmenu.CalificarPeliculaView;
import ui.mainmenu.PanelFilaPelicula;
import ui.mainmenu.VentanaInformacion;
import service.UsuariosService; 
import model.Pelicula;
import model.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.Color;

public class CalificarController implements ActionListener {

    private final CalificarPeliculaView vista;

    private final UsuariosService usuariosService;
    private final Usuario usuarioLogueado;
    private final PanelFilaPelicula filaPelicula; 
    private final JFrame ownerFrame;

    public CalificarController(JFrame ownerFrame, Usuario usuario, PanelFilaPelicula filaPelicula, 
                               UsuariosService usuariosService) {
        
        this.ownerFrame = ownerFrame;
        this.usuarioLogueado = usuario;
        this.filaPelicula = filaPelicula;
        this.usuariosService = usuariosService; 
        Pelicula pelicula = filaPelicula.getPelicula();

        this.vista = new CalificarPeliculaView(ownerFrame, pelicula.getTitulo());
        
        this.vista.getBtnGuardar().addActionListener(this);
        this.vista.getBtnCancelar().addActionListener(e -> vista.dispose());
        
        this.vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnGuardar()) {
            guardarCalificacion();
        }
    }

    private void guardarCalificacion() {

        int idPelicula = filaPelicula.getPelicula().getId();

        try {
            usuariosService.marcarPeliculaComoResenada(usuarioLogueado, idPelicula);

        
            vista.dispose(); 
            
            
            VentanaInformacion info = new VentanaInformacion(ownerFrame);
            
            
            info.getBtnContinuar().addActionListener(e -> info.dispose());
            
            info.setVisible(true); 

        
            JButton btn = filaPelicula.getBtnCalificar();
            btn.setEnabled(false);
            btn.setText("Calificado");
            btn.setBackground(Color.GRAY.darker()); 
            
            filaPelicula.revalidate();
            filaPelicula.repaint();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error de base de datos: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
