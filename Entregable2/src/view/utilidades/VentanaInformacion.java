package view.utilidades;

import javax.swing.*;
import java.awt.*;

public class VentanaInformacion extends JDialog {

    private JButton btnContinuar; // 🛑 Lo hacemos privado
    
    // ⭐️ El constructor ya no tiene el ActionListener
    public VentanaInformacion(JFrame owner) {
        super(owner, "Información", true);
        setSize(350, 150);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10)); 
        
        getContentPane().setBackground(new Color(230, 230, 230));

    
        // Título del Mensaje
        JLabel lblTitulo = new JLabel("Se registró correctamente su calificación, muchas gracias");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTitulo.setForeground(Color.GRAY.darker()); // Letras grises
        // Botón
        btnContinuar = new JButton("Continuar"); 
        btnContinuar.setBackground(Color.WHITE);
        btnContinuar.setForeground(Color.DARK_GRAY);
        btnContinuar.setFocusPainted(false);
        
        // 🛑 ELIMINAMOS ESTA LÍNEA (El Control va al Controller):
        // btnContinuar.addActionListener(e -> dispose()); 

        add(lblTitulo, BorderLayout.CENTER);
        add(btnContinuar, BorderLayout.SOUTH);
    }

    // ⭐️ MÉTODO PÚBLICO para que el Controlador acceda al botón
    public JButton getBtnContinuar() {
        return btnContinuar;
    }
}