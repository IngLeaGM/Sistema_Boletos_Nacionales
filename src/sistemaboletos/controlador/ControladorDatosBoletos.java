
package sistemaboletos.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;
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
                String cedula = vista.getTfCedula().getText();
                String nombre = vista.getTfNombre().getText();
                String telf = vista.getTfTelefono().getText();
               
        
                if (cedula.isEmpty() || nombre.isEmpty() || telf.isEmpty()) {
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
