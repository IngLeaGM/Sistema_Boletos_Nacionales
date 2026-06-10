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
        UbicacionesDAO ubicacionesDao = new UbicacionesDAO();
        
        List<Viaje> listaViajes = viajesDao.obtener_Viajes(con);
        List<Ubicacion> listaUbicaciones = ubicacionesDao.obtener_ubicaciones(con);
        
        ControladorComprar ctrlComprar = new ControladorComprar(vistaComprar, userDao, viajesDao, ubicacionesDao,
                                                                listaViajes, listaUbicaciones, usuarioLog, con); 
        vistaComprar.setLocationRelativeTo(null);
        vistaComprar.setVisible(true);
        System.out.println("Se entro a la ventana comprar");
    }
    
    private void abrirVentanaBoletos() throws SQLException {
        Connection con = ConexionBD.getConexion();
              
        vista.dispose();
        
        FrameMisBoletos vistaBoletos = new FrameMisBoletos();
        
        BoletosDAO boletosDao = new BoletosDAO();
        ViajesDAO viajesDao = new ViajesDAO();
        UbicacionesDAO ubicacionesDao = new UbicacionesDAO();
        
        List<Viaje> listaViajes = viajesDao.obtener_Viajes(con);
        List<Ubicacion> listaUbicaciones = ubicacionesDao.obtener_ubicaciones(con);
        
        ControladorMisBoletos ctrlBoletos = new ControladorMisBoletos(vistaBoletos, userDao, viajesDao, ubicacionesDao,
                                                                boletosDao, listaViajes, listaUbicaciones, usuarioLog, con); 
        
        vistaBoletos.setLocationRelativeTo(null);
        vistaBoletos.setVisible(true);
        System.out.println("Se entro a la ventana Mis Boletos");
    }
    
    private void abrirVentanaAdmin() throws SQLException {
        
        Connection con = ConexionBD.getConexion();
        
        vista.dispose();
        
        FrameAdmin vistaAdmin = new FrameAdmin();
        
        ViajesDAO viajesDao = new ViajesDAO();
        UbicacionesDAO ubicacionesDao = new UbicacionesDAO();
        
        List<Viaje> listaViajes = viajesDao.obtener_Viajes(con);
        List<Ubicacion> listaUbicaciones = ubicacionesDao.obtener_ubicaciones(con);
        
        ControladorAdmin ctrladmin = new ControladorAdmin(vistaAdmin); 
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
