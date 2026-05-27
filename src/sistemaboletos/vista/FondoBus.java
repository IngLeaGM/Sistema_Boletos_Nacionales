/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaboletos.vista;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class FondoBus extends JPanel {
    private Image imagen;

    @Override
    public void paint(Graphics g) {
        // Cambia la ruta por la ubicación de tu imagen dentro del proyecto
        imagen = new ImageIcon(getClass().getResource("/sistemaboletos/vista/imagenes/imagenbus.jpg")).getImage();
        
        // Dibuja la imagen del tamaño exacto del panel
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        
        setOpaque(false);
        super.paint(g);
    }
}
