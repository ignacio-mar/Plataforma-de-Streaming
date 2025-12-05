package view.menuPrincipal;

import javax.swing.*;
import java.awt.*;

public class CalificarPeliculaView extends JDialog {

    private JComboBox<Integer> comboEstrellas;
    private JTextArea txtComentario;
    private JButton btnGuardar;
    private JButton btnCancelar;

    public CalificarPeliculaView(JFrame owner, String tituloPelicula) {
        // Configuraciones del diálogo
        super(owner, "Plataforma de Streaming - Calificar Película", true);
        setSize(400, 350);
        setLocationRelativeTo(owner);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        // 1. Título de la Película
        JLabel lblTitulo = new JLabel("Calificar: " + tituloPelicula);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupar dos columnas
        panelPrincipal.add(lblTitulo, gbc);

        // 2. Selección de Estrellas (1-10)
        JPanel panelEstrellas = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelEstrellas.setBackground(Color.WHITE);
        panelEstrellas.add(new JLabel("Puntaje (1-10): "));
        
        Integer[] estrellas = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        comboEstrellas = new JComboBox<>(estrellas);
        comboEstrellas.setSelectedIndex(9); // Por defecto 10
        panelEstrellas.add(comboEstrellas);
        
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panelPrincipal.add(panelEstrellas, gbc);

        // 3. Comentario
        JLabel lblComentario = new JLabel("Escribe tu reseña:");
        gbc.gridy = 2;
        panelPrincipal.add(lblComentario, gbc);

        txtComentario = new JTextArea(5, 20);
        txtComentario.setLineWrap(true);
        txtComentario.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(txtComentario);
        
        gbc.gridy = 3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panelPrincipal.add(scroll, gbc);

        // 4. Botones
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new Color(0, 102, 204)); // Azul
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 12));
        
        btnCancelar = new JButton("Cancelar");
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        add(panelPrincipal, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    // Getters para el controlador
    public int getPuntaje() { return (Integer) comboEstrellas.getSelectedItem(); }
    public String getComentario() { return txtComentario.getText(); }
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnCancelar() { return btnCancelar; }
}