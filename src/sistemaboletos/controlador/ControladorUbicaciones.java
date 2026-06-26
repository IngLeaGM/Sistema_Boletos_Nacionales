package sistemaboletos.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sistemaboletos.conexion.ConexionBD;
import sistemaboletos.dao.BoletosDAO;
import sistemaboletos.dao.FacturasDAO;
import sistemaboletos.dao.TransportesDAO;
import sistemaboletos.dao.UbicacionesDAO;
import sistemaboletos.dao.UsuariosDAO;
import sistemaboletos.dao.ViajesDAO;
import sistemaboletos.modelo.BoletoDetalle;
import sistemaboletos.modelo.Transporte;
import sistemaboletos.modelo.Ubicacion;
import sistemaboletos.modelo.Usuario;
import sistemaboletos.modelo.Viaje;
import sistemaboletos.modelo.ViajeInformacion;
import sistemaboletos.vista.FrameFacturas;
import sistemaboletos.vista.FrameMenu;
import sistemaboletos.vista.FrameMisBoletos;
import sistemaboletos.vista.FrameUbicaciones;
import sistemaboletos.vista.FrameUsuarios;
import sistemaboletos.vista.FrameVehiculos;
import sistemaboletos.vista.FrameViajesProgramados;


public class ControladorUbicaciones implements ActionListener {
    private FrameUbicaciones vista;
    private UbicacionesDAO ubicacionesDao;
    private Usuario usuarioLog;
    private Connection con;
    private int ubicacionSeleccionada;
    
    public ControladorUbicaciones(FrameUbicaciones vista, UbicacionesDAO ubicacionesDao,
                             Usuario usuarioLog, Connection con) throws SQLException  {
        this.vista = vista;
        this.ubicacionesDao = ubicacionesDao;
        this.usuarioLog = usuarioLog;
        this.con = con;
        this.ubicacionSeleccionada = 0;
        
        this.vista.getTbtnUsuarios().addActionListener((ActionListener) this);
        this.vista.getTbtnFacturas().addActionListener((ActionListener) this);
        this.vista.getBtnAgregar().addActionListener((ActionListener) this);
        this.vista.getBtnEliminar().addActionListener((ActionListener) this);
        this.vista.getBtnVolver().addActionListener((ActionListener) this);
        this.vista.getTbtnViajes().addActionListener((ActionListener) this);  
        this.vista.getBtnViajes().addActionListener((ActionListener) this);
        this.vista.getBtnVehiculos().addActionListener((ActionListener) this);
           
        this.vista.getTbUbicaciones().addMouseListener(new java.awt.event.MouseListener() {
           
            @Override
            public void mouseClicked(MouseEvent e) {
                
                seleccionarFila();
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
               
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
               
            }

            @Override
            public void mouseExited(MouseEvent e) {
               
            }
        });
        
        this.vista.getTbUbicaciones().removeColumn(this.vista.getTbUbicaciones().getColumnModel().getColumn(0));
        llenarTablaUbicaciones();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
         if (e.getSource() == vista.getTbtnUsuarios()) {
            try {
                abrirVentanaUsuarios();
            } catch (Exception ex) {
                System.err.print("Ocurrio un error: " + ex);
            }
        } else if (e.getSource() == vista.getBtnVehiculos()) {
            try {
                abrirVentanaVehiculos();
            } catch (Exception ex) {
                System.out.println("Ocurrio un error: " + ex);
            } 
        } else if (e.getSource() == vista.getTbtnFacturas()) {
            try {
                abrirVentanaFacturas();
            } catch (Exception ex) {
                System.out.println("Ocurrio un error: " + ex);
            } 
        } else if (e.getSource() == vista.getBtnAgregar()) {
            try {
                agregarUbicacion();
            } catch (Exception ex) {
                System.out.println("Ocurrio un error: " + ex);
            } 
        } else if (e.getSource() == vista.getBtnEliminar()) {
            try {
                eliminarUbicacion();
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
            
        } else if (e.getSource() == vista.getTbtnViajes()) {
            try {
                abrirVentanaViajes();
            } catch (Exception ex) {
                System.out.println("Ocurrio un error: " + ex);
            } 
        } else if (e.getSource() == vista.getBtnViajes()) {
            try {
                abrirVentanaViajes();
            } catch (Exception ex) {
                System.out.println("Ocurrio un error: " + ex);
            } 
        }
    }
    
    private void llenarTablaUbicaciones() throws SQLException {
        System.out.println("Se ejecuto LlenarTabla");
        
        DefaultTableModel modelo = (DefaultTableModel) vista.getTbUbicaciones().getModel();
        
        modelo.setRowCount(0);
        
        List<Ubicacion> listaUbicaciones = ubicacionesDao.obtener_ubicaciones(this.con);
        
        for (Ubicacion ubi : listaUbicaciones) {
            
            Object[] fila = new Object[2];
            fila[0] = ubi.getId_ubicacion();
            System.out.println("Id: " + fila[0]);
            fila[1] = ubi.getNombre();
            
            modelo.addRow(fila);
        } 
    }
    
    public void seleccionarFila() {
        
        int fila = vista.getTbUbicaciones().getSelectedRow();
        
        if (fila != -1) {
            int filaModelo = vista.getTbUbicaciones().convertRowIndexToModel(fila);
        
            int id_ubicacion = Integer.parseInt(vista.getTbUbicaciones().getModel().getValueAt(filaModelo, 0).toString());

            System.out.println("El usuario seleccionó la fila completa del viaje ID: " + id_ubicacion);
            this.ubicacionSeleccionada = id_ubicacion;
        } else {
            this.ubicacionSeleccionada = 0;
        }

    }
     
    private void agregarUbicacion() throws SQLException {
        String nombre = vista.getTfNombre().getText();
             
        
        Ubicacion ubicacion = new Ubicacion(nombre);
        
        if (ubicacionesDao.insertar_Ubicacion(con, ubicacion)) {
            System.out.println("Ubicacion insertado con exito");
            llenarTablaUbicaciones();
        } else {
            System.out.println("error");
        }
    }
    
    private void eliminarUbicacion() throws SQLException {
        
        int respuesta = JOptionPane.showConfirmDialog(
                vista, 
                "¿Seguro que deseas eliminar la ubicacion?", 
                "Eliminar Ubicacion", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE);

        if (respuesta == JOptionPane.YES_OPTION) {
                if(ubicacionesDao.eliminarUbicacion(con, ubicacionSeleccionada)) {
                    llenarTablaUbicaciones();
                } else {
                    JOptionPane.showMessageDialog(vista, "No puedes eliminar una ubicacion con viajes asociados a ella.", "Error al eliminar", JOptionPane.WARNING_MESSAGE);
                }      
        }   
    }
    
    private void abrirVentanaUsuarios() throws SQLException {
        
        vista.dispose();
        
        FrameUsuarios vistaUsuarios = new FrameUsuarios();
        
        UsuariosDAO usuariosDao = new UsuariosDAO();
        
        ControladorUsuarios ctrlUsuarios = new ControladorUsuarios(vistaUsuarios, usuariosDao, usuarioLog, con);
       
        vistaUsuarios.setLocationRelativeTo(null);
        vistaUsuarios.setVisible(true);
        System.out.println("Se entro a la ventana Usuarios");
    }
    
    private void abrirVentanaFacturas() throws SQLException {
        
        vista.dispose();
        
        FrameFacturas vistaFacturas = new FrameFacturas();
        
        FacturasDAO facturasDao = new FacturasDAO();
        
        ControladorFacturas ctrlFacturas = new ControladorFacturas(vistaFacturas, facturasDao, usuarioLog, this.con); 
        vistaFacturas.setLocationRelativeTo(null);
        vistaFacturas.setVisible(true);
        System.out.println("Se entro a la ventana Facturas");
    }
    
    private void abrirVentanaViajes() throws SQLException {
        
        vista.dispose();
        
        FrameViajesProgramados vistaViajes = new FrameViajesProgramados();
        
        ViajesDAO viajesDao = new ViajesDAO();
        
        ControladorViajes ctrlViajes = new ControladorViajes(vistaViajes, viajesDao, ubicacionesDao, usuarioLog, con); 
        vistaViajes.setLocationRelativeTo(null);
        vistaViajes.setVisible(true);
        System.out.println("Se entro a la ventana viajes");
    }
    
    private void abrirVentanaVehiculos() throws SQLException {
        
        vista.dispose();
        
        FrameVehiculos vistaVehiculos = new FrameVehiculos();
        
        TransportesDAO transportesDao = new TransportesDAO();
        
        ControladorVehiculos ctrlVehiculos = new ControladorVehiculos(vistaVehiculos, transportesDao, usuarioLog, this.con); 
        vistaVehiculos.setLocationRelativeTo(null);
        vistaVehiculos.setVisible(true);
        System.out.println("Se entro a la ventana Vehiculos");
    }
}
