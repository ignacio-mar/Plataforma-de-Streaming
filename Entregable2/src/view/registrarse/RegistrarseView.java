package view.registrarse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener; // Necesario para el controlador

public class RegistrarseView extends JFrame {

   
    // ATRIBUTOS (Componentes Públicos para el Controlador)
    // Datos Personales
    public JTextField txtNombres;
    public JTextField txtApellidos;
    public JTextField txtDni;
    public JTextField txtTelefono;
    public JComboBox<String> cmbPais; // Usamos JComboBox para País
    
    // Datos de Usuario
    public JTextField txtEmail;
    public JTextField txtNombreUsuario;
    public JPasswordField txtContrasena;
    
    // Botones
    public JButton btnRegistrarse;
    public JButton btnCancelar;

    public JLabel lblMensajeError;

    public RegistrarseView() {
        setTitle("Plataforma de Streaming - Registración");
        setSize(550, 600); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Contenedor principal 
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // --- TÍTULO ---
        JLabel lblTitulo = new JLabel("Creación de Cuenta", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // --- FORMULARIO CENTRAL ---
        
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5); // Espacio entre campos
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Lista de Países (Ejemplo, en el controlador cargarías los ENUMs reales)
        String[] paisesEjemplo = {"Argentina", "Chile", "Colombia", "España", "México", "Otros"};
        
        int fila = 0;
        
        // carga de Datos Personales
        
        
        // Nombres
        txtNombres = new JTextField(20);
        agregarFila(panelFormulario, gbc, "Nombres:", txtNombres, fila++);
        
        // Apellidos
        txtApellidos = new JTextField(20);
        agregarFila(panelFormulario, gbc, "Apellidos:", txtApellidos, fila++);
        
        // DNI
        txtDni = new JTextField(20);
        agregarFila(panelFormulario, gbc, "DNI:", txtDni, fila++);
        
        // País de Residencia (JComboBox)
        cmbPais = new JComboBox<>(paisesEjemplo);
        cmbPais.setPreferredSize(new Dimension(200, 25)); // Tamaño fijo para que se vea bien
        agregarFila(panelFormulario, gbc, "País de Residencia:", cmbPais, fila++);
        
        // Número de Teléfono
        txtTelefono = new JTextField(20);
        agregarFila(panelFormulario, gbc, "Número de Teléfono:", txtTelefono, fila++);


        // --- Datos de Usuario ---

        // Email
        txtEmail = new JTextField(20);
        agregarFila(panelFormulario, gbc, "Email:", txtEmail, fila++);
        
        // Nombre de Usuario
        txtNombreUsuario = new JTextField(20);
        agregarFila(panelFormulario, gbc, "Nombre de Usuario:", txtNombreUsuario, fila++);
        
        // Contraseña
        txtContrasena = new JPasswordField(20);
        agregarFila(panelFormulario, gbc, "Contraseña:", txtContrasena, fila++);


        // --- MENSAJE DE ERROR ---
        lblMensajeError = new JLabel(" ");
        lblMensajeError.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = fila++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panelFormulario.add(lblMensajeError, gbc);

        
        btnRegistrarse = new JButton("Registrarse");
        btnCancelar = new JButton("Cancelar");
        

        btnRegistrarse.setBackground(new Color(0, 102, 204)); // Azul fuerte
        btnRegistrarse.setForeground(Color.WHITE);
        btnRegistrarse.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrarse.setFocusPainted(false);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        panelBotones.add(btnRegistrarse);
        panelBotones.add(btnCancelar);

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        setContentPane(panelPrincipal);
        
    }



    // Agregar una fila con Label y Componente
    private void agregarFila(JPanel panel, GridBagConstraints gbc, String label, JComponent componente, int fila) {
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST; 
        panel.add(new JLabel(label), gbc);
        
        // Componente (Columna 1)
        gbc.gridx = 1;
        gbc.gridy = fila;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0; 
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(componente, gbc);
    }
}

    /*// Agregar un separador de sección
    private int agregarSeparador(JPanel panel, GridBagConstraints gbc, String texto, int fila) {
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 2; // Ocupa ambas columnas
        gbc.insets = new Insets(15, 5, 5, 5); // Margen superior extra
        JLabel separador = new JLabel("<html><b>"+texto+"</b></html>");
        separador.setForeground(Color.GRAY.darker());
        panel.add(separador, gbc);
        
        gbc.insets = new Insets(8, 5, 8, 5); // Restablecer margen
        return fila + 1;
    }
}*/