
package sistemaboletos.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import sistemaboletos.vista.FrameDatosBoletos;


public class ControladorDatosBoletos implements ActionListener {
  
    private FrameDatosBoletos vista;
    
    public ControladorDatosBoletos(FrameDatosBoletos vista) {
        this.vista = vista;
        
        this.vista.getBtnAgregar().addActionListener((ActionListener) this);
        
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnAgregar()) {
            try {
                this.vista.setDatosGuardados(true);
                this.vista.dispose();
                
            } catch (Exception ex) {
                System.err.print("Ocurrio un error: " + ex);
            }
        }
    }
}
