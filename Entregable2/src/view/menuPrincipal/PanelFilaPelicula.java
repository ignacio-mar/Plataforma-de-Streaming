package view.menuPrincipal;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.*;
import model.Pelicula;

public class PanelFilaPelicula extends JPanel {
     private Pelicula pelicula;
    private JButton btnCalificar;
    
    public PanelFilaPelicula(Pelicula pelicula) {
        this.pelicula=pelicula;
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
        setPreferredSize(new Dimension(1100, 120));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        // POSTER (10%)
        JLabel lblPoster = new JLabel("IMG", SwingConstants.CENTER);
        lblPoster.setOpaque(true);
        lblPoster.setBackground(Color.LIGHT_GRAY);
        lblPoster.setPreferredSize(new Dimension(80, 110));

        String posterUrl = pelicula.getPosterUrl();
        if (posterUrl != null && !posterUrl.isBlank()) {
            try {
                ImageIcon iconOriginal = new ImageIcon(new URL(posterUrl));
                if (iconOriginal.getIconWidth() > 0 && iconOriginal.getIconHeight() > 0) {
                    Image img = iconOriginal.getImage().getScaledInstance(80, 110, Image.SCALE_SMOOTH);
                    lblPoster.setIcon(new ImageIcon(img));
                    lblPoster.setText("");
                    lblPoster.setBackground(Color.WHITE);
                }
            } catch (Exception e) {
               
            }
        }
        agregarColumna(lblPoster, 0, 0.10);

        // TITULO (20%)
        JTextArea txtTitulo = crearTextoArea(pelicula.getTitulo(), true);
        agregarColumna(txtTitulo, 1, 0.20);

        // GENERO (15%)
        String generoStr = (pelicula.getGenero() != null) ? pelicula.getGenero().toString() : "-";
        JTextArea txtGenero = crearTextoArea(generoStr, false);
        agregarColumna(txtGenero, 2, 0.15);

        // RESUMEN (40%)
        JLabel lblLinkResumen = new JLabel("<html><u>Ver Sinopsis</u></html>");
        lblLinkResumen.setForeground(new Color(0, 102, 204));
        lblLinkResumen.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblLinkResumen.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mostrarVentanaResumen(pelicula.getTitulo(), pelicula.getSinopsis());
            }
        });
        JPanel panelLink = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelLink.setBackground(Color.WHITE);
        panelLink.add(lblLinkResumen);
        agregarColumna(panelLink, 3, 0.40);

        // CALIFICAR (15%)
        btnCalificar = new JButton("CALIFICAR");
        btnCalificar.setBackground(new Color(0, 102, 204));
        btnCalificar.setForeground(Color.WHITE);
        btnCalificar.setFont(new Font("Arial", Font.BOLD, 11));
        btnCalificar.setFocusPainted(false);
        btnCalificar.setPreferredSize(new Dimension(100, 30));

        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtn.setBackground(Color.WHITE);
        panelBtn.add(btnCalificar);
        agregarColumna(panelBtn, 4, 0.15);
    }

    private void agregarColumna(JComponent comp, int gridx, double peso) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = 0;
        gbc.weightx = peso;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.add(comp, BorderLayout.CENTER);
        wrapper.setPreferredSize(new Dimension(0, 0));

        add(wrapper, gbc);
    }

    private JTextArea crearTextoArea(String texto, boolean esNegrita) {
        JTextArea area = new JTextArea(texto);
        area.setWrapStyleWord(true);
        area.setLineWrap(true);
        area.setEditable(false);
        area.setOpaque(false);
        area.setFont(new Font("Arial", esNegrita ? Font.BOLD : Font.PLAIN, 13));
        return area;
    }

    private void mostrarVentanaResumen(String titulo, String sinopsis) {
        JTextArea areaTexto = new JTextArea(sinopsis);
        areaTexto.setLineWrap(true);
        areaTexto.setWrapStyleWord(true);
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        areaTexto.setBackground(new Color(240, 240, 240));

        JScrollPane scrollPane = new JScrollPane(areaTexto);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "Sinopsis: " + titulo,
                JOptionPane.INFORMATION_MESSAGE
        );

    }
    public JButton getBtnCalificar() {
        return btnCalificar;
    }
    
    public Pelicula getPelicula() {
        return pelicula;
    }
    public void marcarComoCalificada() {
        btnCalificar.setEnabled(false);           
        btnCalificar.setText("Calificado");      
        btnCalificar.setBackground(Color.GRAY);  
    }

    
    
}