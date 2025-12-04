package view.login;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
public class PanelConImagen extends JPanel {
        private Image imagen;

        public PanelConImagen(String ruta) {
            setBackground(Color.WHITE);
            try {
                // 1. Cargamos la imagen (solo una vez)
                URL url = getClass().getResource(ruta);
                if (url != null) {
                    imagen = ImageIO.read(url);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        private void dibujar(Graphics g) {
            if (imagen != null) {
                // Dibuja la imagen, estirándola al tamaño actual del panel
                g.drawImage(imagen,10, 45, 580, 470, this);
            } else {
                // Si la imagen no cargó, pintamos un fondo negro
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(Color.WHITE);
                g.drawString("Imagen no disponible", 50, 50);
            }
        }

        // EL MÉTODO DE CICLO DE VIDA DE SWING 
        @Override
        public void paint(Graphics g) {
    
            super.paint(g); 
            
            dibujar(g); 
        }
    }
