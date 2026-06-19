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
import sistemaboletos.vista.FrameFacturas;
import sistemaboletos.vista.FrameUsuarios;
import sistemaboletos.vista.FrameViajesProgramados;

public class ControladorFacturas implements ActionListener {
    
    private FrameFacturas vista;
    private Connection con;
    
    public ControladorFacturas(FrameFacturas vista, Connection con)  {
        this.vista = vista;
        this.con = con;
        
        this.vista.getTbtnUsuarios().addActionListener((ActionListener) this);
        this.vista.getTbtnViajes().addActionListener((ActionListener) this);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getTbtnUsuarios()) {
            try {
                abrirVentanaUsuarios(con);
            } catch (Exception ex) {
                System.err.print("Ocurrio un error: " + ex);
            }
        } else if (e.getSource() == vista.getTbtnViajes()) {
            try {
                abrirVentanaViajes();
            } catch (Exception ex) {
                System.out.println("Ocurrio un error: " + ex);
            } 
        }
    }
    
    private void abrirVentanaUsuarios(Connection con) throws SQLException {
        
        vista.dispose();
        
        FrameUsuarios vistaUsuarios = new FrameUsuarios();
        
        ViajesDAO viajesDao = new ViajesDAO();
        UbicacionesDAO ubicacionesDao = new UbicacionesDAO();
        
        ControladorUsuarios ctrlUsuarios = new ControladorUsuarios(vistaUsuarios, con); 
        vistaUsuarios.setLocationRelativeTo(null);
        vistaUsuarios.setVisible(true);
        System.out.println("Se entro a la ventana Usuarios");
    }
    
   private void abrirVentanaViajes() throws SQLException {
        
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
