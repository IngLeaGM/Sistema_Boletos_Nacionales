package sistemaboletos.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sistemaboletos.conexion.ConexionBD;
import sistemaboletos.dao.UbicacionesDAO;
import sistemaboletos.dao.UsuariosDAO;
import sistemaboletos.dao.ViajesDAO;
import sistemaboletos.modelo.Ubicacion;
import sistemaboletos.modelo.Usuario;
import sistemaboletos.modelo.Viaje;
import sistemaboletos.vista.FrameComprar;
import sistemaboletos.vista.FrameMenu;

public class ControladorMenu implements ActionListener {
    
    private FrameMenu vista;
    private UsuariosDAO dao;
    private Usuario usuarioLog;
    
    public ControladorMenu(FrameMenu vista, UsuariosDAO dao, Usuario usuarioLog)  {
        this.vista = vista;
        this.dao = dao;
        this.usuarioLog = usuarioLog;
        
        this.vista.getBtnComprar().addActionListener((ActionListener) this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnComprar()) {
                try {
                abrirVentanaComprar();
            } catch (Exception ex) {
                System.err.print("Ocurrio un error: " + ex);
            }
        }
    }
    
    
    
    private void abrirVentanaComprar() throws SQLException {
        
        Connection con = ConexionBD.getConexion();
        
        vista.dispose();
        
        FrameComprar vistaComprar = new FrameComprar();
        
        UsuariosDAO userDao = new UsuariosDAO();
        ViajesDAO viajesDao = new ViajesDAO();
        UbicacionesDAO ubicacionesDao = new UbicacionesDAO();
        
        List<Viaje> listaViajes = viajesDao.obtener_Viajes(con);
        List<Ubicacion> listaUbicaciones = ubicacionesDao.obtener_ubicaciones(con);
        
        ControladorComprar ctrlComprar = new ControladorComprar(vistaComprar, userDao, viajesDao, ubicacionesDao,
                                                                listaViajes, listaUbicaciones, usuarioLog); 
        
        vistaComprar.setLocationRelativeTo(null);
        vistaComprar.setVisible(true);
        System.out.println("Se entro a la ventana comprar");
    }
}
