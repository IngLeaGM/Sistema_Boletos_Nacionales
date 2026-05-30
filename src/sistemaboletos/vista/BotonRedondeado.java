/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaboletos.vista;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;

 public class BotonRedondeado extends JButton {

    private int infoRadio = 20;

    public BotonRedondeado() {
        super();
        setContentAreaFilled(false); // Quita el fondo gris por defecto de Swing
        setBorderPainted(false);     // Quita el borde cuadrado clásico
        setFocusPainted(false);      // Quita las líneas de enfoque feas al hacer clic
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Efecto visual: Cambia ligeramente de color si se mantiene presionado
        if (getModel().isArmed()) {
            g2.setColor(getBackground().darker());
        } else {
            g2.setColor(getBackground());
        }

        // Pintar el fondo redondeado
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), infoRadio, infoRadio);

        // Esto le dice a Java que pinte el texto y los iconos encima del fondo redondeado
        super.paintComponent(g);
    }
}
