package sistemaboletos.vista.imagenes;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelFondo extends JPanel {
    private Image imagen;

    // Constructor que recibe la ruta de la imagen
    public PanelFondo(String ruta) {
        imagen = new ImageIcon(getClass().getResource(ruta)).getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibuja la imagen estirándola al tamaño exacto del panel
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
    }
}
