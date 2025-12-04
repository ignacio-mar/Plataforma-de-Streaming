package view.menuPrincipal;

import java.awt.*;
import javax.swing.*;

public class InformacionPeliculaView extends JDialog {

    private final JLabel lblTitulo;
    private final JLabel lblAnio;
    private final JTextArea txtResumen;
    private final JButton btnContinuar;

    /**
     * Ventana de información de película.
     *
     * @param owner   ventana padre (por ejemplo, BienvenidaView)
     * @param titulo  título de la película
     * @param anio    año de la película (si es <= 0 se muestra "No disponible")
     * @param resumen texto del resumen / sinopsis
     */
    public InformacionPeliculaView(JFrame owner, String titulo, int anio, String resumen) {
        super(owner, "Plataforma de Streaming - Información", true); // modal

        // Configuración básica
        setSize(500, 300);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Margen general
        JPanel panelContenido = new JPanel(new BorderLayout(10, 10));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(panelContenido, BorderLayout.CENTER);

        // ----- TÍTULO -----
        lblTitulo = new JLabel(titulo != null ? titulo : "Título no disponible");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        panelContenido.add(lblTitulo, BorderLayout.NORTH);

        // ----- PANEL CENTRAL CON AÑO + RESUMEN -----
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelContenido.add(panelCentro, BorderLayout.CENTER);

        // Año
        String textoAnio = (anio > 0) ? "Año: " + anio : "Año: No disponible";
        lblAnio = new JLabel(textoAnio);
        lblAnio.setFont(new Font("Arial", Font.PLAIN, 14));
        lblAnio.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelCentro.add(lblAnio);
        panelCentro.add(Box.createVerticalStrut(10));

        // Resumen
        txtResumen = new JTextArea();
        txtResumen.setEditable(false);
        txtResumen.setLineWrap(true);
        txtResumen.setWrapStyleWord(true);
        txtResumen.setFont(new Font("Arial", Font.PLAIN, 14));
        txtResumen.setText(resumen != null ? resumen : "No se encuentra disponible.");

        JScrollPane scroll = new JScrollPane(txtResumen);
        scroll.setBorder(null);
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelCentro.add(scroll);

        // ----- BOTÓN CONTINUAR -----
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnContinuar = new JButton("Continuar");
        btnContinuar.addActionListener(e -> dispose());
        panelBoton.add(btnContinuar);

        add(panelBoton, BorderLayout.SOUTH);
    }
}
