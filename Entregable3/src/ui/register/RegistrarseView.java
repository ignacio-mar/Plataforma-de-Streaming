package ui.register;

import javax.swing.*;
import java.awt.*;

public class RegistrarseView extends JFrame {

    // Datos Personales
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextField txtDni;
    private JTextField txtTelefono;
    private JList<String> lstPais;
    
    // Datos de Usuario
    private JTextField txtEmail;
    private JTextField txtNombreUsuario;
    private JPasswordField txtContrasena;
    
    // Botones
    private JButton btnRegistrarse;
    private JButton btnCancelar;

    private JLabel lblMensajeError;

    public RegistrarseView() {
        setTitle("Plataforma de Streaming - Registración");
        setSize(550, 600); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitulo = new JLabel("Creación de Cuenta", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        String[] paisesEjemplo = {"ARGENTINA","BRASIL","EEUU","FRANCIA"};
        
        int fila = 0;
        
        txtNombres = new JTextField(20);
        agregarFila(panelFormulario, gbc, "Nombres:", txtNombres, fila++);
        
        txtApellidos = new JTextField(20);
        agregarFila(panelFormulario, gbc, "Apellidos:", txtApellidos, fila++);
        
        txtDni = new JTextField(20);
        agregarFila(panelFormulario, gbc, "DNI:", txtDni, fila++);
        
        // País de Residencia como JList
        lstPais = new JList<>(paisesEjemplo);
        lstPais.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstPais.setSelectedIndex(0);
        lstPais.setVisibleRowCount(3);
        JScrollPane scrollPaises = new JScrollPane(lstPais);
        scrollPaises.setPreferredSize(new Dimension(200, 80));
        agregarFila(panelFormulario, gbc, "País de Residencia:", scrollPaises, fila++);
        
        txtTelefono = new JTextField(20);
        agregarFila(panelFormulario, gbc, "Número de Teléfono:", txtTelefono, fila++);

        txtEmail = new JTextField(20);
        agregarFila(panelFormulario, gbc, "Email:", txtEmail, fila++);
        
        txtNombreUsuario = new JTextField(20);
        agregarFila(panelFormulario, gbc, "Nombre de Usuario:", txtNombreUsuario, fila++);
        
        txtContrasena = new JPasswordField(20);
        agregarFila(panelFormulario, gbc, "Contraseña:", txtContrasena, fila++);

        lblMensajeError = new JLabel(" ");
        lblMensajeError.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = fila++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panelFormulario.add(lblMensajeError, gbc);

        btnRegistrarse = new JButton("Registrarse");
        btnCancelar = new JButton("Cancelar");
        
        btnRegistrarse.setBackground(new Color(0, 102, 204));
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

    private void agregarFila(JPanel panel, GridBagConstraints gbc, String label, JComponent componente, int fila) {
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST; 
        panel.add(new JLabel(label), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = fila;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0; 
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(componente, gbc);
    }

    public String getNombres() {
        return txtNombres.getText();
    }

    public String getApellidos() {
        return txtApellidos.getText();
    }

    public int getDni() {
        try {
            return Integer.parseInt(txtDni.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public String getTelefono() {
        return txtTelefono.getText();
    }

    public String getPais() {
        return lstPais.getSelectedValue();
    }

    public String getEmail() {
        return txtEmail.getText();
    }

    public String getNombreUsuario() {
        return txtNombreUsuario.getText();
    }

    public String getContrasena() {
        return new String(txtContrasena.getPassword());
    }

    public JButton getBotonRegistrarse() {
        return btnRegistrarse;
    }

    public JButton getBotonCancelar() {
        return btnCancelar;
    }

    public void setMensajeError(String mensaje) {
        lblMensajeError.setText(mensaje);
    }
}
