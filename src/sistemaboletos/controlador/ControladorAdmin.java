package sistemaboletos.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import sistemaboletos.conexion.ConexionBD;
import sistemaboletos.dao.BoletosDAO;
import sistemaboletos.dao.UbicacionesDAO;
import sistemaboletos.dao.UsuariosDAO;
import sistemaboletos.dao.ViajesDAO;
import sistemaboletos.modelo.Ubicacion;
import sistemaboletos.modelo.Usuario;
import sistemaboletos.modelo.Viaje;
import sistemaboletos.vista.FrameComprar;
import sistemaboletos.vista.FrameMenu;
import sistemaboletos.vista.FrameMisBoletos;
import sistemaboletos.vista.FrameAdmin;
import sistemaboletos.vista.FrameViajesProgramados;

public class ControladorAdmin implements ActionListener {
    
    private FrameAdmin vista;
    private UsuariosDAO userDao;
    private Usuario usuarioLog;
    
    public ControladorAdmin(FrameAdmin vista, UsuariosDAO userDao, Usuario usuarioLog)  {
        this.vista = vista;
        this.userDao = userDao;
        this.usuarioLog = usuarioLog;
        
        this.vista.getTbtnViajes().addActionListener((ActionListener) this);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getTbtnViajes()) {
                try {
                abrirVentanaViajes();
            } catch (Exception ex) {
                System.err.print("Ocurrio un error: " + ex);
            }
        }
    }
    
   private void abrirVentanaViajes() throws SQLException {
        
        Connection con = ConexionBD.getConexion();
        
        vista.dispose();
        
        FrameViajesProgramados vistaViajes = new FrameViajesProgramados();
        
        ViajesDAO viajesDao = new ViajesDAO();
        UbicacionesDAO ubicacionesDao = new UbicacionesDAO();
        
        ControladorViajes ctrlViajes = new ControladorViajes(vistaViajes, viajesDao, ubicacionesDao, con); 
        vistaViajes.setLocationRelativeTo(null);
        vistaViajes.setVisible(true);
        System.out.println("Se entro a la ventana viajes");
    }
    
}
