package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import model.enums.Paises;
import service.PersonasService;
import service.UsuariosService;
import ui.login.LoginView;
import ui.register.RegistrarseView;

public class RegistroController implements ActionListener {

    private final RegistrarseView vista;
    private final UsuariosService usuariosService;
    private final PersonasService personasService;
    private final LoginView loginView;

   

    public RegistroController(RegistrarseView vista, UsuariosService usuariosService, PersonasService personasService, LoginView loginView) {
        this.vista = vista;
        this.usuariosService = usuariosService;
        this.personasService = personasService;
        this.loginView = loginView;

        this.vista.getBotonRegistrarse().addActionListener(this);
        this.vista.getBotonCancelar().addActionListener(this);
    }

    
     // Maneja los eventos de acción de los botones.
     
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBotonRegistrarse()) {
            registrarUsuario();
        } else if (e.getSource() == vista.getBotonCancelar()) {
            cancelarRegistro();
        }
    }

    private void registrarUsuario() {
        vista.setMensajeError(" ");

        // Obtener datos del formulario
        String nombres = vista.getNombres();
        String apellidos = vista.getApellidos();
        int dni = vista.getDni();
        String telefono = vista.getTelefono();
        String paisStr = vista.getPais();
        String email = vista.getEmail();
        String nombreUsuario = vista.getNombreUsuario();
        String contrasena = vista.getContrasena();
        
        // Validar datos personales
        if (!personasService.validarNombre(nombres)) {
            vista.setMensajeError("Error: El nombre es inválido (solo letras).");
            return;
        }
        if (!personasService.validarApellido(apellidos)) {
            vista.setMensajeError("Error: El apellido es inválido (solo letras).");
            return;
        }
        if (!personasService.validarDni(dni)) {
            vista.setMensajeError("Error: El DNI debe ser un número positivo válido.");
            return;
        }
        if (!personasService.validarTelefono(telefono)) {
            vista.setMensajeError("Error: Debe ingresar un teléfono.");
            return;
        }

        // Validar credenciales
        if (!usuariosService.validarEmail(email)) {
            vista.setMensajeError("Error: El formato del email es inválido.");
            return;
        }
        if (!usuariosService.validarContrasenia(contrasena)) {
            vista.setMensajeError("Error: La contraseña debe tener al menos 6 caracteres.");
            return;
        }

        // Convertir país a enum
        Paises paisEnum;
        try {
            paisEnum = Paises.valueOf(paisStr.toUpperCase());
        } catch (IllegalArgumentException ex) {
            vista.setMensajeError("Error: Seleccione un país válido.");
            return;
        }

        try {
            // Validar disponibilidad en base de datos
            if (personasService.dniExiste(dni)) {
                vista.setMensajeError("Error: Ya existe una persona con ese DNI.");
                return;
            }

            if (!usuariosService.validarNombreUsuario(nombreUsuario)) {
                vista.setMensajeError("Error: El nombre de usuario ya existe o es inválido.");
                return; 
            }

            // Crear la persona y el usuario
            personasService.crearPersona(nombres, apellidos, dni, paisEnum, telefono);
            usuariosService.crearUsuario(nombreUsuario, email, contrasena, dni);

            JOptionPane.showMessageDialog(vista, "¡Usuario registrado con éxito!", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
            
            volverAlLogin();

        } catch (SQLException ex) {
            ex.printStackTrace();
            vista.setMensajeError("Error SQL: " + ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            vista.setMensajeError("Error inesperado: " + ex.getMessage());
        }
    }

  
     // Cancela el registro y retorna al login.
  
    private void cancelarRegistro() {
        volverAlLogin();
    }

   
     // Cierra la ventana de registro y muestra nuevamente la ventana de login.

    private void volverAlLogin() {
        vista.dispose();
        loginView.setVisible(true);
    }
}
