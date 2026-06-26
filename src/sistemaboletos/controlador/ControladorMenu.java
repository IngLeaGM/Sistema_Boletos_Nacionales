package sistemaboletos.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

public class ControladorMenu implements ActionListener {
    
    private FrameMenu vista;
    private UsuariosDAO userDao;
    private Usuario usuarioLog;
    
    public ControladorMenu(FrameMenu vista, UsuariosDAO userDao, Usuario usuarioLog)  {
        this.vista = vista;
        this.userDao = userDao;
        this.usuarioLog = usuarioLog;
        
        this.vista.getBtnComprar().addActionListener((ActionListener) this);
        this.vista.getBtnMisBoletos().addActionListener((ActionListener) this);
        this.vista.getBtnSalir().addActionListener((ActionListener) this);
        this.vista.getBtnAdmin().addActionListener((ActionListener) this);
        
        this.vista.getLabelUser().setText(usuarioLog.getUser());
        
        if(usuarioLog.getId_usuario() == 1) {
            vista.getBtnAdmin().setVisible(true);
        } else {
            vista.getBtnAdmin().setVisible(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnComprar()) {
                try {
                abrirVentanaComprar();
            } catch (Exception ex) {
                System.err.print("Ocurrio un error: " + ex);
            }
        } else if (e.getSource() == vista.getBtnMisBoletos()) {
            try {
                abrirVentanaBoletos();
            } catch (Exception ex) {
                System.err.print("Ocurrio un error: " + ex);
            }
        } else if (e.getSource() == vista.getBtnAdmin()) {
            try {
                abrirVentanaAdmin();
            } catch (SQLException ex) {
                Logger.getLogger(ControladorMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == vista.getBtnSalir()) {
            cerrarAplicacion();
        }
    }
    
    
    private void abrirVentanaComprar() throws SQLException {
        
        Connection con = ConexionBD.getConexion();
        
        vista.dispose();
        
        FrameComprar vistaComprar = new FrameComprar();
        
        ViajesDAO viajesDao = new ViajesDAO();
        
        ControladorComprar ctrlComprar = new ControladorComprar(vistaComprar, userDao, viajesDao, usuarioLog, con); 
        vistaComprar.setLocationRelativeTo(null);
        vistaComprar.setVisible(true);
        System.out.println("Se entro a la ventana comprar");
    }
    
    private void abrirVentanaBoletos() throws SQLException {
        Connection con = ConexionBD.getConexion();
              
        vista.dispose();
        
        FrameMisBoletos vistaBoletos = new FrameMisBoletos();
        
        BoletosDAO boletosDao = new BoletosDAO();
        
        ControladorMisBoletos ctrlBoletos = new ControladorMisBoletos(vistaBoletos, boletosDao, usuarioLog, con); 
        
        vistaBoletos.setLocationRelativeTo(null);
        vistaBoletos.setVisible(true);
        System.out.println("Se entro a la ventana Mis Boletos");
    }
    
    private void abrirVentanaAdmin() throws SQLException {
        
        Connection con = ConexionBD.getConexion();
        
        vista.dispose();
        
        FrameAdmin vistaAdmin = new FrameAdmin();
             
        ControladorAdmin ctrladmin = new ControladorAdmin(vistaAdmin, usuarioLog); 
        vistaAdmin.setLocationRelativeTo(null);
        vistaAdmin.setVisible(true);
        System.out.println("Se entro a la ventana comprar");
    }
    
    private void cerrarAplicacion() {
   
        int respuesta = JOptionPane.showConfirmDialog(
                vista, 
                "¿Seguro que deseas salir?", 
                "Cerrar Aplicación", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE);

        if (respuesta == JOptionPane.YES_OPTION) {

            vista.dispose(); 

           System.exit(0); 
        }
    }
}
