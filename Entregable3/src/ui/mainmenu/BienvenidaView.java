package ui.mainmenu;

import java.awt.*;
import java.net.URL;
import javax.swing.*;

public class BienvenidaView extends JFrame {
    
    // Contenedor que usa CardLayout 
    private final JPanel panelContenidoCentral; 
    
    // Constantes para saber el estado de la vista
    public static final String NOMBRE_CARGA = "LOADING";
    public static final String NOMBRE_CONTENIDO = "CONTENIDO";
    public static final String RUTA_CSV_PELICULAS = "Entregable3/lib/movies_database.csv"; 
    // El panel de contenido 
    public PanelContenidoPrincipal panelListo;
    // barra de progreso para el controlador
    public JProgressBar progressBar; 

    public BienvenidaView() {
        setTitle("Plataforma Streaming - Bienvenida");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // TÍTULO DE LA PANTALLA 
        JLabel lblTitulo = new JLabel("Bienvenido a la plataforma streaming", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24)); 
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        lblTitulo.setBackground(Color.DARK_GRAY);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setOpaque(true);
        add(lblTitulo, BorderLayout.NORTH);
        
        //PANEL CENTRAL CON CARDLAYOUT 
        panelContenidoCentral = new JPanel();
        panelContenidoCentral.setLayout(new CardLayout()); 
        
        // ESTADO INICIAL: PANTALLA DE CARGA 
        JPanel panelCarga = crearPanelCargando();
        panelContenidoCentral.add(panelCarga, NOMBRE_CARGA);
        
        // ESTADO FINAL: CONTENIDO
        panelListo = new PanelContenidoPrincipal(); 
        panelContenidoCentral.add(panelListo, NOMBRE_CONTENIDO);
        
        add(panelContenidoCentral, BorderLayout.CENTER);
        mostrarTarjeta(NOMBRE_CARGA); // Inicia en la pantalla de carga
    }

    // Metodo que el controlador va a usar para cambiar la vista de loading a contenido
    public void mostrarTarjeta(String nombreTarjeta) {
        CardLayout cl = (CardLayout)(panelContenidoCentral.getLayout());
        cl.show(panelContenidoCentral, nombreTarjeta);
    }
    
    // Método para crear el panel de carga (con barra de progreso)
    private JPanel crearPanelCargando() {
        JPanel panelCarga = new JPanel(new GridBagLayout());
        panelCarga.setBackground(Color.LIGHT_GRAY); // Color para que se vea el progreso
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; 
        gbc.insets = new Insets(10, 10, 10, 10);
        
        //Texto Superior: "Loading imagen..."
        JLabel lblLoading = new JLabel("Loading imagen...", SwingConstants.CENTER);
        gbc.gridy = 0; 
        panelCarga.add(lblLoading, gbc);
        
        // Imagen: "cargando.png" (HAY QUE ARREGLAR ESTO DESPUES PORQUE NO CARGE NINGUNA FOTO TODAVIA)
        URL rutaImagen = getClass().getResource("/model/images/cargando.png"); 
        JLabel lblImagen = new JLabel();
        if (rutaImagen != null) {
            // Se usa ImageIcon para cargar la imagen
            lblImagen.setIcon(new ImageIcon(rutaImagen));
        } else {
             lblImagen.setText("[IMAGEN CARGANDO.PNG NO ENCONTRADA]");
        }
        gbc.gridy = 1; 
        panelCarga.add(lblImagen, gbc);

        // Texto Inferior:
        JLabel lblEspera = new JLabel("Un momento, por favor...", SwingConstants.CENTER);
        lblEspera.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy = 2; 
        panelCarga.add(lblEspera, gbc);
        
        // BARRA DE PROGRESO 
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true); 
        progressBar.setPreferredSize(new Dimension(300, 25)); 
        gbc.gridy = 3; 
        panelCarga.add(progressBar, gbc);
        
        return panelCarga;
    }
}
