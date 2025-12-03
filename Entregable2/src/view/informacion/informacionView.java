package view.informacion;

import modelo.Pelicula;
import javax.swing.*;
import java.awt.*;

public class informacionView  extends JFrame {

    private final JTextField txtTitulo;
    private final JButton btnBuscar;

    private final JTextArea areaSinopsis;
    private final JLabel lblTituloAnio;
    private final JLabel lblDirector;
    private final JLabel lblGenero;
    private final JLabel lblDuracion;
    private final JLabel lblEstadoBusqueda;

    public InformacionVista() {
        super("Información de la Película ~ OMDb ~");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(600, 450);

        txtTitulo = new JTextArea(10, 40);
        btnBuscar = new JButton("Buscar");

        areaSinopsis = new JTextArea(10, 40);
        areaSinopsis.setLineWrap(true);
        areaSinopsis.setWrapStyleWord(true);
        areaSinopsis.setEditable(false);
        lblTituloAnio = new JLabel("Titulo y año: -");
        lblDirector = new JLabel("Director: -");
        lblDuracion = new JLabel("Duración: -");
        lblGenero = new JLabel("Género: -");
        lblEstadoBusqueda = new JLabel("Estado de la búsqueda: -");

        JPanel panelSuperior = new JPanel(new FlowLayout());
        panelSuperior.add(new JLabel("Título de la película:"));
        panelSuperior.add(txtTitulo);
        panelSuperior.add(btnBuscar);

        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBorder(BoderFactory.createEmptyBorder(10, 20, 10, 20));

        panelCentral.add(lblTituloAnio);
        panelCentral.add(lblDirector);
        panelCentral.add(lblDuracion);
        panelCentral.add(lblGenero);
        panelCentral.add(Box.createVerticalStrut(10));
        panelCentral.add(new JLabel("Sinopsis:"));
        panelCentral.add(new JScrollPane(areaSinopsis));

        this.add(panelSuperior, BorderLayout.NORTH);
        this.add(panelCentral, BorderLayout.CENTER);
        this.add(lblEstadoBusqueda, BorderLayout.SOUTH);

        this.setLocationRelativeTo(null);
    }

    public JButton getBuscarButton() {
        return btnBuscar;
    }

    public JTextField getTituloTextField() {
        return txtTitulo;
    }

    public void mostrarInformacionPelicula(Pelicula pelicula) {
        lblTituloAnio.setText("Título y año: " + pelicula.getTitulo() + " (" + pelicula.getAnio() + ")");
        lblDirector.setText("Director: " + pelicula.getDirector());
        lblDuracion.setText("Duración: " + pelicula.getDuracion() + " minutos.");

        lblGenero.setText("Género: " + pelicula.getGenero());
        areaSinopsis.setText(pelicula.getSinopsis() + "\n\nElenco: " + pelicula.getElenco());
        areaSinopsis.setCarePosition(0);
    }

    public void mostrarError(String msj) {
        lblEstadoBusqueda.setText("Error: " + msj);
        limpiarResultados();
    }

    public void setEStadoBusqueda(String estado) {
        lblEstadoBusqueda.setText("Estado: " + estado);
    }

    private void limpiarResultados() {
        lblTituloAnio.setTExt("Título y año: -");
        lblDirector.setText("Director: -");
        lblDuracion.setText("Duración: -");
        lblGenero.setText("Género: -");
        areaSinopsis.setText("");
    }
}
