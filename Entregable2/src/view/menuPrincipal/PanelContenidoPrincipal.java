package view.menuPrincipal;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Pelicula;

public class PanelContenidoPrincipal extends JPanel {
    public JLabel lblNombreUsuario;
    public JTextField txtBuscador;
    public JButton btnBuscar;
    public JButton btnCerrarSesion;
    public JButton btnIniciarCarga; 
    
    // Panel donde van a estar  las filas de películas
    public JPanel panelListaPeliculas; 
    public PanelContenidoPrincipal() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
          // PARTE SUPERIOR (Usuario, Buscador, Botones)
        JPanel panelHeaderPrincipal = new JPanel(new BorderLayout());
        panelHeaderPrincipal.setBackground(new Color(245, 245, 245));
        panelHeaderPrincipal.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        // Usuario (Izquierda)
        lblNombreUsuario = new JLabel("Bienvenido, [Usuario]");
        lblNombreUsuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNombreUsuario.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        panelHeaderPrincipal.add(lblNombreUsuario, BorderLayout.WEST);

        // Buscador y Botones (Derecha)
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 12)); 
        panelControles.setOpaque(false);
        
        btnIniciarCarga = new JButton("Cargar Películas");
        btnIniciarCarga.setBackground(new Color(40, 167, 69)); // Verde
        btnIniciarCarga.setForeground(Color.WHITE);
        
        txtBuscador = new JTextField(20);
        btnBuscar = new JButton("Buscar");
        btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setBackground(new Color(220, 53, 69)); // Rojo
        btnCerrarSesion.setForeground(Color.WHITE);
        
        panelControles.add(btnIniciarCarga);
        panelControles.add(txtBuscador);
        panelControles.add(btnBuscar);
        panelControles.add(btnCerrarSesion);
        
        panelHeaderPrincipal.add(panelControles, BorderLayout.EAST);
        add(panelHeaderPrincipal, BorderLayout.NORTH);

        //  CUERPO CENTRAL 
        JPanel panelCuerpo = new JPanel(new BorderLayout());
        panelCuerpo.setBackground(Color.WHITE);

        // Subtítulo
        JLabel lblSubtitulo = new JLabel("Seguro viste alguna de estas películas...", SwingConstants.CENTER);
  
        // Panel contenedor de Lista + encabezados
        JPanel panelCentralLista = new JPanel(new BorderLayout());
        panelCentralLista.add(lblSubtitulo, BorderLayout.NORTH);

        // FILA DE ENCABEZADOS (Titulos de Columnas)
        JPanel panelEncabezados = new JPanel(new GridBagLayout());
        panelEncabezados.setBackground(new Color(230, 230, 230)); // Gris clarito para diferenciar
        panelEncabezados.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        
        agregarEncabezado(panelEncabezados, "PÓSTER", 0, 0.10);
        agregarEncabezado(panelEncabezados, "TÍTULO", 1, 0.20);
        agregarEncabezado(panelEncabezados, "GÉNERO", 2, 0.15);
        agregarEncabezado(panelEncabezados, "RESUMEN", 3, 0.40);
        agregarEncabezado(panelEncabezados, "ACCIÓN", 4, 0.15);

        // Agregamos encabezados antes de la lista
        panelCentralLista.add(panelEncabezados, BorderLayout.CENTER); // Truco temporal que me paso la ia ya que es mejor usar un panel vertical
        
        // Mejor estructura
        JPanel panelContenedorLista = new JPanel(new BorderLayout());
        panelContenedorLista.add(panelEncabezados, BorderLayout.NORTH);
        
        // Lista de Películas
        panelListaPeliculas = new JPanel();
        panelListaPeliculas.setLayout(new BoxLayout(panelListaPeliculas, BoxLayout.Y_AXIS));
        panelListaPeliculas.setBackground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(panelListaPeliculas);
        scrollPane.setBorder(null);
        panelContenedorLista.add(scrollPane, BorderLayout.CENTER);
        
        panelCuerpo.add(panelContenedorLista, BorderLayout.CENTER);
        add(panelCuerpo, BorderLayout.CENTER);
    }

    private void agregarEncabezado(JPanel panel, String texto, int gridx, double peso) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = 0;
        gbc.weightx = peso;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 5, 0, 5);
        
        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        lbl.setForeground(Color.DARK_GRAY);
        
        // uso wrapper para forzar tamaño igual que en las filas
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(lbl);
        wrapper.setPreferredSize(new Dimension(0, 30)); // Altura fija encabezado
        
        panel.add(wrapper, gbc);
    }
    public void mostrarPeliculas(List<Pelicula> peliculas) {
        panelListaPeliculas.removeAll();
        
        //  Por cada película, creamos una "Fila Perfecta" y la agregamos
        for (Pelicula p : peliculas) {
            panelListaPeliculas.add(new PanelFilaPelicula(p));
        }
        
        // refrescamos la interfaz para que aparezcan los cambios
        panelListaPeliculas.revalidate();
        panelListaPeliculas.repaint();
    }

}