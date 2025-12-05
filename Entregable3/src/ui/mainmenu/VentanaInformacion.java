package ui.mainmenu;

import javax.swing.*;
import java.awt.*;

public class VentanaInformacion extends JDialog {

    private JButton btnContinuar; 
    

    public VentanaInformacion(JFrame owner) {
        super(owner, "Información", true);
        setSize(350, 150);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10)); 
        
        getContentPane().setBackground(new Color(230, 230, 230));

    
        // Título del Mensaje
        JTextArea lblTitulo = new JTextArea("Se registró correctamente su calificación, muchas gracias");
        lblTitulo.setLineWrap(true);       
        lblTitulo.setWrapStyleWord(true);  
        lblTitulo.setEditable(false);     
        lblTitulo.setOpaque(false);     
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo.setForeground(Color.GRAY.darker());

        // Botón
        btnContinuar = new JButton("Continuar"); 
        btnContinuar.setBackground(Color.WHITE);
        btnContinuar.setForeground(Color.DARK_GRAY);
        btnContinuar.setFocusPainted(false);
        
    

        add(lblTitulo, BorderLayout.CENTER);
        add(btnContinuar, BorderLayout.SOUTH);
    }

   
    public JButton getBtnContinuar() {
        return btnContinuar;
    }
}
