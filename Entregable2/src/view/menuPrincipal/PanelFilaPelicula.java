package view.menuPrincipal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import model.Pelicula;

public class PanelFilaPelicula extends JPanel {

    public PanelFilaPelicula(Pelicula pelicula) {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230))); // Línea gris abajo
        
        // Altura fija para que todas las filas sean iguales 
        setPreferredSize(new Dimension(1100, 120));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        // DEFINICIÓN DE COLUMNAS 
        
        //  POSTER (10%)
        JLabel lblPoster = new JLabel("IMG", SwingConstants.CENTER);
        lblPoster.setOpaque(true);
        lblPoster.setBackground(Color.LIGHT_GRAY); 
        agregarColumna(lblPoster, 0, 0.10); 

        // TITULO 20%   Usamos JTextArea para que baje el texto si es largo
        JTextArea txtTitulo = crearTextoArea(pelicula.getTitulo(), true);
        agregarColumna(txtTitulo, 1, 0.20);

        //  GENERO (15%)
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
        // Lo envolvemos en un panel para que no se estire feo
        JPanel panelLink = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelLink.setBackground(Color.WHITE);
        panelLink.add(lblLinkResumen);
        agregarColumna(panelLink, 3, 0.40);

        // CALIFICAR (15%)
        JButton btnCalificar = new JButton("CALIFICAR");
        btnCalificar.setBackground(new Color(0, 102, 204)); // Azul
        btnCalificar.setForeground(Color.WHITE);            // Blanco
        btnCalificar.setFont(new Font("Arial", Font.BOLD, 11));
        btnCalificar.setFocusPainted(false);
        btnCalificar.setPreferredSize(new Dimension(100, 30)); // Tamaño fijo del botón para que sean todos iguales
        
        // Panel para centrar el boton
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtn.setBackground(Color.WHITE);
        panelBtn.add(btnCalificar);
        agregarColumna(panelBtn, 4, 0.15);
    }

    
    private void agregarColumna(JComponent comp, int gridx, double peso) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = 0;
        gbc.weightx = peso; // El peso define el ancho proporcional
        gbc.weighty = 1.0;  // Ocupar todo el alto de la fila
        gbc.fill = GridBagConstraints.BOTH; // Estirar para llenar la celda
        gbc.insets = new Insets(5, 5, 5, 5); // Margen entre columnas

        
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.add(comp, BorderLayout.CENTER);
        wrapper.setPreferredSize(new Dimension(0, 0)); // <--- LA CLAVE
        
        add(wrapper, gbc);
    }


    private JTextArea crearTextoArea(String texto, boolean esNegrita) {
        JTextArea area = new JTextArea(texto);
        area.setWrapStyleWord(true);
        area.setLineWrap(true); // Si no entra, baja a la línea siguiente
        area.setEditable(false);
        area.setOpaque(false); // Fondo transparente
        area.setFont(new Font("Arial", esNegrita ? Font.BOLD : Font.PLAIN, 13));
        return area;
    }

    
    private void mostrarVentanaResumen(String titulo, String sinopsis) {
        // Crear un area de Texto (JTextArea) en lugar de un String simple
        JTextArea areaTexto = new JTextArea(sinopsis);
        
        // Configuración Mágica para el Salto de Línea
        areaTexto.setLineWrap(true);       
        areaTexto.setWrapStyleWord(true);   
        areaTexto.setEditable(false);      
        areaTexto.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Letra legible
        areaTexto.setBackground(new Color(240, 240, 240)); 
        
        
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        scrollPane.setPreferredSize(new Dimension(400, 200)); 
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); 

        //  Mostrar el JOptionPane pasando el objeto scrollPane en vez del texto
        JOptionPane.showMessageDialog(
            this, 
            scrollPane, 
            "Sinopsis: " + titulo, 
            JOptionPane.INFORMATION_MESSAGE
        );
}
}