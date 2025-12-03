package view.menuPrincipal;

import javax.swing.SwingUtilities;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        
        // Nota Importante: En una aplicación real, aquí deberían ir la conexión a la BD
        // y la inicialización de Services, pero lo omitimos para esta prueba visual.
        
        SwingUtilities.invokeLater(() -> {
            try {
                // 1. Instanciar la Vista Principal (Se inicia en modo "LOADING" por defecto)
                PrincipalView vistaPrincipal = new PrincipalView();
                
                // 2. Simular la carga del CSV y el LogIn exitoso
                
                // A. Simular el nombre del usuario logueado
                vistaPrincipal.panelListo.lblNombreUsuario.setText("Bienvenido, Ignacio (Admin)"); 
                
                // B. Simular el agregado de 10 películas (Para ver si el Grid se dibuja)
                vistaPrincipal.panelListo.panelGridPeliculas.setLayout(new java.awt.GridLayout(2, 5, 20, 20));
                
                for(int i = 1; i <= 10; i++) {
                     // En lugar de una película real, agregamos un botón con texto de ejemplo
                     vistaPrincipal.panelListo.panelGridPeliculas.add(new JButton("Película Ranking #" + i));
                }
                
                // 3. Forzar el cambio de estado de la tarjeta de "LOADING" a "CONTENIDO"
                vistaPrincipal.mostrarTarjeta(PrincipalView.NOMBRE_CONTENIDO);
                
                // 4. Mostrar la ventana maximizada
                vistaPrincipal.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}