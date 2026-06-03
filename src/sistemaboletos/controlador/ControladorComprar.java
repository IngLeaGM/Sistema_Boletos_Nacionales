
package sistemaboletos.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sistemaboletos.conexion.ConexionBD;
import sistemaboletos.dao.UbicacionesDAO;
import sistemaboletos.dao.UsuariosDAO;
import sistemaboletos.dao.ViajesDAO;
import sistemaboletos.modelo.Ubicacion;
import sistemaboletos.modelo.Usuario;
import sistemaboletos.modelo.Viaje;
import sistemaboletos.vista.FrameComprar;
import sistemaboletos.vista.FrameMenu;


public class ControladorComprar implements ActionListener {
    
    Connection con = ConexionBD.getConexion();
    // Atributos
    private FrameComprar vista;
    private UsuariosDAO userDao;
    private ViajesDAO viajesDao;
    private UbicacionesDAO ubicacionesDao;
    private List<Viaje> listaViajes;
    private List<Ubicacion> listaUbicaciones;
    private Usuario usuarioLog;
    
    
    //Constructor
    public ControladorComprar(FrameComprar vista, UsuariosDAO userDao, ViajesDAO viajesDao,
                                UbicacionesDAO ubicacionesDao, List<Viaje> listaViajes,
                                List<Ubicacion> listaUbicaciones, Usuario usuarioLog) throws SQLException  {
        this.vista = vista;
        this.userDao = userDao;
        this.viajesDao = viajesDao;
        this.ubicacionesDao = ubicacionesDao;
        this.listaViajes = listaViajes;
        this.listaUbicaciones = listaUbicaciones;
        this.usuarioLog = usuarioLog;
        
        this.vista.getBtnInicio().addActionListener(this);
        this.vista.getJcbDesde().addActionListener(this);
        cargarViajes(con);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getJcbDesde()) {
            try {
                Object item = vista.getJcbDesde().getSelectedItem();
                if (item == null) return;

                cargarDestinos(con);
            } catch (Exception ex) {
                System.err.print("Ocurrio un error: " + ex);
            }
        } else if (e.getSource() == vista.getBtnInicio()) {
            
            try {
                vista.dispose(); // Cierra y destruye la ventana de Login actual


                FrameMenu menuPrincipal = new FrameMenu();
                ControladorMenu ctrlMenu = new ControladorMenu(menuPrincipal, userDao, usuarioLog);
                menuPrincipal.setVisible(true);
            
            } catch (Exception ex) {
                System.out.println("Ocurrio un error: " + ex);
            } finally {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorComprar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
    }
    
    public void cargarViajes(Connection con) throws SQLException {
        vista.getJcbDesde().removeAllItems(); // Limpiamos el combo por si hay items basura
        vista.getJcbHasta().removeAllItems();
        ArrayList<Ubicacion> salidasDisponibles = this.viajesDao.listaUbicacionesDisponibles(con);
        
        
        for (Ubicacion ubObtenida : salidasDisponibles) {
            vista.getJcbDesde().addItem(ubObtenida);
        }
        
         cargarDestinos(con);
    }
    
    public void cargarDestinos(Connection con) throws SQLException {
        vista.getJcbHasta().removeAllItems();
        Ubicacion ubiSalida = (Ubicacion) vista.getJcbDesde().getSelectedItem();
        System.out.println(ubiSalida.getId_ubicacion());
        
        ArrayList<Ubicacion> destinosDisponibles = this.viajesDao.listaDestinos(con, ubiSalida);
        
        
        for (Ubicacion ubObtenida : destinosDisponibles) {
            vista.getJcbHasta().addItem(ubObtenida);
        }
        
        cargarFechas(con);
        
    }
    
    public void cargarFechas(Connection con) throws SQLException {
        vista.getJcbFecha().removeAllItems();
        Ubicacion ubiSalida = (Ubicacion) vista.getJcbDesde().getSelectedItem();
        Ubicacion ubiDestino = (Ubicacion) vista.getJcbHasta().getSelectedItem();
        System.out.println(ubiDestino.getId_ubicacion());
        
        ArrayList<Viaje> viajesDisponibles = this.viajesDao.listaFechas(con, ubiSalida, ubiDestino);
        
        for (Viaje viajeObtenido : viajesDisponibles) {
            vista.getJcbFecha().addItem(viajeObtenido);
        }
    }

}
