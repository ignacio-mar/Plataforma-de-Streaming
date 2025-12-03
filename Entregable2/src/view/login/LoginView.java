package view.login;
import java.awt.*;
import javax.swing.*;


public class LoginView extends JFrame {
    private JLabel etiquetaNombreUsuario;
    private JTextField nombreUsuario;
    private JLabel etiquetaContrasena;
    private JPasswordField contrasena;
    private JButton botonLogin;
    private JLabel mensajeRegistro;
    private JButton botonRegistrar;
    private final String url_name="imagenes/imagenPrincipal.jpg";
    private final String mensajeError="Error, Los datos no son correctos";


public LoginView() {
    setTitle("Bienvenido a la Plataforma de Streaming");
    setSize(1100, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setResizable(false);
    setLayout(new GridLayout(1,2));
    
    //--------- PARTE IZQUIERDA: TRABAJAMOS CON LA IMAGEN -----------
    PanelConImagen panelIzquierdo = new PanelConImagen("/"+url_name);
    add(panelIzquierdo);// Agregamos el panel izquierdo al JFrame


    // ----------PARTE DERECHA-----------
    
    //declaro fuentes que vamos a usar
    Font fuenteNegrita = new Font("Arial", Font.BOLD, 14);
    //---------

    JPanel panelDerecho= new JPanel();
    panelDerecho.setBackground(Color.WHITE);
    panelDerecho.setLayout(new GridBagLayout()); // se usa GridBagLayout para alinear los campos

    // Contenedor del formulario 
    JPanel formulario= new JPanel();    
    formulario.setBackground(Color.WHITE);
    
    // GridLayout de dos columnas para alinear el label y el TextField
    formulario.setLayout(new GridLayout(2,2,10,10));
    
    // Nombre de usuario
    
    etiquetaNombreUsuario = new JLabel("Nombre de Usuario:");
    etiquetaNombreUsuario.setFont(fuenteNegrita);
    formulario.add(etiquetaNombreUsuario);
    nombreUsuario = new JTextField(5);
    formulario.add(nombreUsuario);    

    // Contraseña
    etiquetaContrasena = new JLabel("Contraseña:");
    etiquetaContrasena.setFont(fuenteNegrita);
    formulario.add(etiquetaContrasena);
    contrasena = new JPasswordField(5);
    formulario.add(contrasena);

    // Boton Login
    JPanel panelBotonLogin= new JPanel();
    panelBotonLogin.setBackground(Color.WHITE);
    panelBotonLogin.setLayout(new FlowLayout(FlowLayout.CENTER));
    botonLogin= new JButton("Iniciar Sesión");
    botonLogin.setBackground(new Color(229,9,20));
    botonLogin.setForeground(Color.WHITE);
    botonLogin.setFont(fuenteNegrita);
    botonLogin.setFocusPainted(false);
    panelBotonLogin.add(botonLogin);

    // Registrarse
    JPanel panelRegistro= new JPanel();    
    panelRegistro.setBackground(Color.WHITE);
    panelRegistro.setLayout(new GridLayout(1,2,10,0));
    mensajeRegistro= new JLabel ("Aún no sos usuario?");
    botonRegistrar= new JButton("Registrarse");
    botonRegistrar.setBackground(Color.WHITE);
    botonRegistrar.setForeground(Color.BLUE);
    botonRegistrar.setFont(fuenteNegrita);
    panelRegistro.add(mensajeRegistro);
    panelRegistro.add(botonRegistrar);

   // Uso GridBAgCOnstraints para centrar el formulario y el boton
      // Posicionar el formulario
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(70, 10, 5, 10); // Margen alrededor del formulario
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.gridwidth = 1;
      gbc.weighty= 0;
      gbc.fill = GridBagConstraints.HORIZONTAL; // no se si va
      gbc.anchor = GridBagConstraints.NORTH;
      panelDerecho.add(formulario,gbc);
      
     // Posicionar el boton de login
    gbc.insets = new Insets(15, 20, 5, 5);
     gbc.gridy=1;
     gbc.weighty= 0;
     gbc.fill = GridBagConstraints.NONE;
     panelDerecho.add(panelBotonLogin,gbc);
     
     // Posicionar el mensaje de registro y el boton de registrar
     gbc.gridy=2;
     gbc.weighty = 1;    
     gbc.insets = new Insets(20, 20, 100, 20);
     gbc.anchor = GridBagConstraints.SOUTH;
     panelDerecho.add(panelRegistro, gbc);


     add(panelDerecho);// Agregamos el panel derecho al JFrame
    
     setVisible(true);// Hacemos visible el JFrame

}
public String getNombreUsuario() {
    return nombreUsuario.getText();
}
public String getContrasena() {
    return new String(contrasena.getPassword());
}
public JButton getBotonLogin() {
    return botonLogin;
}
public JButton getBotonRegistrar() {
    return botonRegistrar;
}
public void mostrarMensajeError() {
    //este método es una forma limpia de encapsular la retroalimentación visual negativa para el usuario. lo hice con ia
    JOptionPane.showMessageDialog(this, mensajeError, "Error de Login", JOptionPane.ERROR_MESSAGE); 
}

}


