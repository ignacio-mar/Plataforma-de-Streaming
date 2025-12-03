package view.menuPrincipal;
import javax.swing.*;
import java.awt.*;

public class PanelContenidoPrincipal extends JPanel {
    
    // Componentes del Header (Punto 2.C)
    public JLabel lblNombreUsuario;
    public JTextField txtBuscador;
    public JButton btnBuscar;
    public JButton btnCerrarSesion;
    
    // Panel donde irán las 10 películas (Para que el Controller las agregue)
    public JPanel panelGridPeliculas; 

    public PanelContenidoPrincipal() {
        // Layout principal: NORTE (Cabecera) y CENTRO (Películas)
        setLayout(new BorderLayout());
        
        // ----------------------------------------------------
        // A. CABECERA (NORTE): Buscador y Datos de Usuario
        // ----------------------------------------------------
        JPanel panelHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelHeader.setBackground(new Color(240, 240, 240));
        
        // 1. Datos del Usuario (Placeholder)
        lblNombreUsuario = new JLabel("Bienvenido, [Usuario Aquí]");
        lblNombreUsuario.setFont(new Font("Arial", Font.BOLD, 14));
        panelHeader.add(lblNombreUsuario);

        panelHeader.add(Box.createHorizontalGlue()); // Empuja los elementos a los extremos
        
        // 2. Buscador de Películas (Punto 4)
        txtBuscador = new JTextField(25);
        btnBuscar = new JButton("Buscar Película");
        panelHeader.add(txtBuscador);
        panelHeader.add(btnBuscar);
        
        panelHeader.add(Box.createHorizontalStrut(20)); // Espacio

        // 3. Botón para cerrar sesión (Punto 2.C)
        btnCerrarSesion = new JButton("Cerrar Sesión");
        panelHeader.add(btnCerrarSesion);
        
        add(panelHeader, BorderLayout.NORTH);


        // ----------------------------------------------------
        // B. GRID DE PELÍCULAS (CENTRO)
        // ----------------------------------------------------
        panelGridPeliculas = new JPanel();
       // Usaremos 2 filas y 5 columnas para mostrar las 10 películas rankeadas [cite: 1325]
        panelGridPeliculas.setLayout(new GridLayout(2, 5, 20, 20)); 
        panelGridPeliculas.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Usamos un JScrollPane por si la lista es grande
        JScrollPane scrollPane = new JScrollPane(panelGridPeliculas);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); 
        
        add(scrollPane, BorderLayout.CENTER);
    }
}