package view;
import java.awt.*;
import javax.swing.*;
import view.clasesInternas.PanelConImagen;


public class LoginView extends JFrame {
    private JLabel etiquetaNombreUsuario;
    private JTextField nombreUsuario;
    private JLabel etiquetaContrasena;
    private JPasswordField contrasena;
    private JButton botonLogin;
    private JLabel mensajeRegistro;
    private JButton botonRegistrar;
    private final String url_name="imagenes/imagenPrincipal.jpg";


public LoginView() {
    setTitle("Bienvenido a la Plataforma de Streaming");
    setSize(1200, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setResizable(false);
    setLayout(new GridLayout(1,2));
    
    // PARTE IZQUIERDA: TRABAJAMOS CON LA IMAGEN 
    PanelConImagen panelIzquierdo = new PanelConImagen("/"+url_name);
    add(panelIzquierdo);// Agregamos el panel izquierdo al JFrame

    // PARTE DERECHA: FORMULARIO DE LOGIN
    JPanel panelDerecho= new JPanel();
    panelDerecho.setBackground(Color.WHITE);
    panelDerecho.setLayout(new FlowLayout()); 
    etiquetaNombreUsuario = new JLabel("Nombre de Usuario:");
    panelDerecho.add(etiquetaNombreUsuario);
    nombreUsuario = new JTextField(20);
    panelDerecho.add(nombreUsuario);        
    etiquetaContrasena = new JLabel("Contraseña:");
    panelDerecho.add(etiquetaContrasena);
    contrasena = new JPasswordField(20);
    panelDerecho.add(contrasena);
    botonLogin = new JButton("Iniciar Sesión");
    panelDerecho.add(botonLogin);
    mensajeRegistro = new JLabel("Aun no sos usuario?");
    panelDerecho.add(mensajeRegistro);
    botonRegistrar = new JButton("Registrarse");
    panelDerecho.add(botonRegistrar);
   

    add(panelDerecho);// Agregamos el panel derecho al JFrame
    
    setVisible(true);// Hacemos visible el JFrame

}
}



