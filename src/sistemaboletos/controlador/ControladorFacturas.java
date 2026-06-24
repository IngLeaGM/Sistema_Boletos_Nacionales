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
import javax.swing.table.DefaultTableModel;
import sistemaboletos.conexion.ConexionBD;
import sistemaboletos.dao.BoletosDAO;
import sistemaboletos.dao.FacturasDAO;
import sistemaboletos.dao.UbicacionesDAO;
import sistemaboletos.dao.UsuariosDAO;
import sistemaboletos.dao.ViajesDAO;
import sistemaboletos.modelo.Factura;
import sistemaboletos.modelo.FacturaTabla;
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
    private FacturasDAO facturasDao;
    private Usuario usuarioLog;
    private Connection con;
    
    public ControladorFacturas(FrameFacturas vista, FacturasDAO facturasDao, Usuario usuarioLog, Connection con) throws SQLException  {
        this.vista = vista;
        this.facturasDao = facturasDao;
        this.usuarioLog = usuarioLog;
        this.con = con;
        
        this.vista.getTbtnUsuarios().addActionListener((ActionListener) this);
        this.vista.getTbtnViajes().addActionListener((ActionListener) this);
        this.vista.getBtnVolver().addActionListener((ActionListener) this);
        
        llenarTablaFacturas();
        
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
        } else if (e.getSource() == vista.getBtnVolver()) {
            
            try {
                vista.dispose();


                con.close();
                
                UsuariosDAO userDao = new UsuariosDAO();
                
                FrameMenu menuPrincipal = new FrameMenu();
                ControladorMenu ctrlMenu = new ControladorMenu(menuPrincipal, userDao, usuarioLog);
                menuPrincipal.setLocationRelativeTo(null);
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
    
    private void llenarTablaFacturas() throws SQLException {
        System.out.println("Se ejecuto LlenarTabla");
        
        DefaultTableModel modelo = (DefaultTableModel) vista.getJtbFacturas().getModel();
        
        modelo.setRowCount(0);
        
        List<FacturaTabla> listaFacturas = facturasDao.obtener_facturasTabla(con);
        
        for (FacturaTabla factura : listaFacturas) {
            
            Object[] fila = new Object[5];
            fila[0] = factura.getId_factura();
            System.out.println("Id: " + fila[0]);
            fila[1] = factura.getUser();
            fila[2] = factura.getMonto_total() + "$";
            fila[3] = factura.getMetodo_pago();
            fila[4] = factura.getFecha();
            modelo.addRow(fila);
        }
        
    }
    
    private void abrirVentanaUsuarios(Connection con) throws SQLException {
        
        vista.dispose();
        
        FrameUsuarios vistaUsuarios = new FrameUsuarios();
        
        UsuariosDAO usuariosDao = new UsuariosDAO();
        
        ControladorUsuarios ctrlUsuarios = new ControladorUsuarios(vistaUsuarios, usuariosDao, usuarioLog, con); 
        vistaUsuarios.setLocationRelativeTo(null);
        vistaUsuarios.setVisible(true);
        System.out.println("Se entro a la ventana Usuarios");
    }
    
   private void abrirVentanaViajes() throws SQLException {
        
        vista.dispose();
        
        FrameViajesProgramados vistaViajes = new FrameViajesProgramados();
        
        ViajesDAO viajesDao = new ViajesDAO();
        UbicacionesDAO ubicacionesDao = new UbicacionesDAO();
        
        ControladorViajes ctrlViajes = new ControladorViajes(vistaViajes, viajesDao, ubicacionesDao, usuarioLog, con); 
        vistaViajes.setLocationRelativeTo(null);
        vistaViajes.setVisible(true);
        System.out.println("Se entro a la ventana viajes");
    }
    
}
