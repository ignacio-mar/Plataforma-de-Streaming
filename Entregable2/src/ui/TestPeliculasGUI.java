package ui;

import dao.impl.PeliculasDAOjdbc;
import model.Pelicula;
import service.PeliculasService;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class TestPeliculasGUI extends JFrame {

    private final PeliculasService peliculasService;
    private final JTextArea txtLog;
    private final JButton btnCargar;
    private final JProgressBar progressBar;

    public TestPeliculasGUI() {
        this.peliculasService = new PeliculasService(new PeliculasDAOjdbc());

        setTitle("Test Películas - Import CSV + Top 10");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        txtLog = new JTextArea();
        txtLog.setEditable(false);
        txtLog.setFont(new Font("Consolas", Font.PLAIN, 12));

        JScrollPane scroll = new JScrollPane(txtLog);

        btnCargar = new JButton("Importar CSV y mostrar Top 10");
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setValue(0);

        setLayout(new BorderLayout());
        add(btnCargar, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(progressBar, BorderLayout.SOUTH);

        btnCargar.addActionListener(e -> cargarPeliculas());
    }

    private void cargarPeliculas() {
        btnCargar.setEnabled(false);
        progressBar.setValue(0);
        txtLog.setText("");
        txtLog.append("Iniciando importación desde CSV...\n\n");

        String rutaCsv = "C:\\Users\\feder\\OneDrive\\Documentos\\GitHub\\Plataforma-de-Streaming\\Entregable2\\lib\\movies_database.csv";

        peliculasService.importarDesdeCsvAsync(
                rutaCsv,
                () -> SwingUtilities.invokeLater(() -> {
                    txtLog.append("Importación finalizada correctamente.\n\n");
                    try {
                        List<Pelicula> top10 = peliculasService.obtenerTop10PorRating();
                        txtLog.append("Top 10 por rating:\n");
                        txtLog.append("-------------------\n");
                        for (int i = 0; i < top10.size(); i++) {
                            Pelicula p = top10.get(i);
                            txtLog.append(
                                    (i + 1) + ") " +
                                            p.getTitulo() + " | Rating: " + p.getRatingPromedio() +
                                            " | Año: " + p.getAnio() +
                                            " | Género: " + p.getGenero() +
                                            "\n"
                            );
                        }
                        progressBar.setValue(100);
                    } catch (SQLException ex) {
                        txtLog.append("\nError al obtener Top 10 desde BD: " + ex.getMessage() + "\n");
                    }
                    btnCargar.setEnabled(true);
                }),
                (ex) -> SwingUtilities.invokeLater(() -> {
                    txtLog.append("\nERROR durante la importación:\n" + ex.getMessage() + "\n");
                    btnCargar.setEnabled(true);
                }),
                (porcentaje) -> SwingUtilities.invokeLater(() -> progressBar.setValue(porcentaje))
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TestPeliculasGUI frame = new TestPeliculasGUI();
            frame.setVisible(true);
        });
    }
}
