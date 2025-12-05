package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.Usuario;
import service.*;
import view.login.*;
import view.menuPrincipal.BienvenidaView;
import view.registrarse.RegistrarseView; 

public class LoginController {

    private final LoginView loginView;
    private final UsuariosService usuariosService;
    private final PersonasService personasService;
    private final PeliculasService peliculasService;
    private final ReseñasService reseñasService;

    public LoginController(
        LoginView loginView, 
        UsuariosService usuariosService,
        PersonasService personasService,
        PeliculasService peliculasService,
        ReseñasService reseñasService
    ) {
        this.loginView = loginView;
        this.usuariosService = usuariosService;
        this.personasService = personasService;
        this.peliculasService = peliculasService;
        this.reseñasService = reseñasService;

        //BOTON LOGIN
        this.loginView.getBotonLogin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSesion();
            }
        });

        //BOTON REGISTRARSE
        this.loginView.getBotonRegistrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirRegistro();
            }
        });
    
    }

    
    private void iniciarSesion() {
        String nombreUsuario = loginView.getNombreUsuario();
        String contrasena = new String(loginView.getContrasena());
        
        try {
            // Llamar al servicio de autenticación
            Usuario usuario = reseñasService.autenticarUsuario(nombreUsuario, contrasena);  

            if (usuario != null) {
                // Usuario autenticado correctamente
                BienvenidaView bienvenidaView = new BienvenidaView();
                
                // Crear el controlador de Bienvenida pasando las dependencias necesarias
                new BienvenidaController(
                    bienvenidaView, 
                    usuario, 
                    peliculasService,
                    reseñasService,
                    personasService,
                    usuariosService
                );

                // Mostrar la ventana de bienvenida
                bienvenidaView.setVisible(true);
                
                // Hacemos un dipose de la ventana de login
                loginView.dispose();
            } else {
                loginView.mostrarMensajeError();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(loginView, "Error de conexión: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirRegistro() {
        try {
            loginView.setVisible(false);
            RegistrarseView registroView = new RegistrarseView();
            new RegistroController(registroView, usuariosService, personasService, loginView); // le paso el loginView para volver a mostrarlo
            registroView.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(loginView, "Error al abrir Registro.", "Error", JOptionPane.ERROR_MESSAGE);
            loginView.setVisible(true);
        }
    }
}