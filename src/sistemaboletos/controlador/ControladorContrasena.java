
package sistemaboletos.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;
import sistemaboletos.vista.FrameContrasena;
import sistemaboletos.vista.FrameDatosBoletos;


public class ControladorContrasena implements ActionListener {
  
    private FrameContrasena vista;
    
    public ControladorContrasena(FrameContrasena vista) {
        this.vista = vista;
        
        this.vista.getBtnAgregar().addActionListener((ActionListener) this);
        
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnAgregar()) {
            try {
                String pass = new String(vista.getjpPass().getPassword());
                
               
                if (pass.isEmpty()) {
                    JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                this.vista.setDatosGuardados(true);
                this.vista.dispose();

            } catch (Exception ex) {
                System.err.print("Ocurrio un error: " + ex);
            }
        }
    }
}
