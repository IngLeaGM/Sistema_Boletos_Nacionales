
package sistemaboletos.vista;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;


public class PanelRedondeado extends JPanel {
    private int infoRadio = 30; 

    public PanelRedondeado() {
        super();
        setOpaque(false); 
    }

  
    public PanelRedondeado(int radio) {
        super();
        this.infoRadio = radio;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
      
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
       
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), infoRadio, infoRadio);
    }
}
