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
import sistemaboletos.vista.FrameUsuarios;
import sistemaboletos.vista.FrameVehiculos;
import sistemaboletos.vista.FrameViajesProgramados;


public class ControladorVehiculos implements ActionListener {
    private FrameVehiculos vista;
    private TransportesDAO transportesDao;
    private Usuario usuarioLog;
    private Connection con;
    private int vehiculoSeleccionado;
    
    public ControladorVehiculos(FrameVehiculos vista, TransportesDAO transportesDao,
                             Usuario usuarioLog, Connection con) throws SQLException  {
        this.vista = vista;
        this.transportesDao = transportesDao;
        this.usuarioLog = usuarioLog;
        this.con = con;
        this.vehiculoSeleccionado = 0;
        
        this.vista.getTbtnUsuarios().addActionListener((ActionListener) this);
        this.vista.getTbtnFacturas().addActionListener((ActionListener) this);
        this.vista.getBtnAgregar().addActionListener((ActionListener) this);
        this.vista.getBtnModificar().addActionListener((ActionListener) this);
        this.vista.getBtnEliminar().addActionListener((ActionListener) this);
        this.vista.getBtnVolver().addActionListener((ActionListener) this);
        this.vista.getTbtnViajes().addActionListener((ActionListener) this);  
        this.vista.getBtnViajes().addActionListener((ActionListener) this);
           
        this.vista.getTbVehiculos().addMouseListener(new java.awt.event.MouseListener() {
           
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
        
        this.vista.getTbVehiculos().removeColumn(this.vista.getTbVehiculos().getColumnModel().getColumn(0));
        llenarTablaVehiculos();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
         if (e.getSource() == vista.getTbtnUsuarios()) {
            try {
                abrirVentanaUsuarios();
            } catch (Exception ex) {
                System.err.print("Ocurrio un error: " + ex);
            }
        } else if (e.getSource() == vista.getTbtnFacturas()) {
            try {
                abrirVentanaFacturas();
            } catch (Exception ex) {
                System.out.println("Ocurrio un error: " + ex);
            } 
        } else if (e.getSource() == vista.getBtnAgregar()) {
            try {
                agregarVehiculo();
            } catch (Exception ex) {
                System.out.println("Ocurrio un error: " + ex);
            } 
        } else if (e.getSource() == vista.getBtnModificar()) {
            try {
                modificarVehiculo();
            } catch (Exception ex) {
                System.out.println("Ocurrio un error: " + ex);
            } 
        } else if (e.getSource() == vista.getBtnEliminar()) {
            try {
                eliminarVehiculo();
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
    
    private void llenarTablaVehiculos() throws SQLException {
        System.out.println("Se ejecuto LlenarTabla");
        
        DefaultTableModel modelo = (DefaultTableModel) vista.getTbVehiculos().getModel();
        
        modelo.setRowCount(0);
        
        List<Transporte> listaVehiculos = transportesDao.obtener_Transportes(this.con);
        
        for (Transporte vehiculo : listaVehiculos) {
            
            Object[] fila = new Object[5];
            fila[0] = vehiculo.getId_transporte();
            System.out.println("Id: " + fila[0]);
            fila[1] = vehiculo.getModelo();
            fila[2] = vehiculo.getAnio_vehiculo();
            fila[3] = vehiculo.getMatricula();
            fila[4] = vehiculo.getTipo_combustible();
            
            modelo.addRow(fila);
        } 
    }
    
    public void seleccionarFila() {
        
        int fila = vista.getTbVehiculos().getSelectedRow();
        
        if (fila != -1) {
            int filaModelo = vista.getTbVehiculos().convertRowIndexToModel(fila);
        
            int id_transporte = Integer.parseInt(vista.getTbVehiculos().getModel().getValueAt(filaModelo, 0).toString());

            System.out.println("El usuario seleccionó la fila completa del viaje ID: " + id_transporte);
            this.vehiculoSeleccionado = id_transporte;
        } else {
            this.vehiculoSeleccionado = 0;
        }

    }
     
    private void agregarVehiculo() throws SQLException {
        String modelo = String.valueOf(vista.getJcbModelo().getSelectedItem());
        int anio = Integer.parseInt((String) vista.getJcbAnio().getSelectedItem()) ;
        String matricula = vista.getTfMatricula().getText();
        String combustible = String.valueOf(vista.getJcbCombustible().getSelectedItem());
             
        
        Transporte vehiculo = new Transporte(modelo, anio, matricula, combustible);
        
        if (transportesDao.insertar_Transporte(con, vehiculo)) {
            System.out.println("Transporte insertado con exito");
            llenarTablaVehiculos();
        } else {
            System.out.println("error");
        }
    }
    
    private void modificarVehiculo() throws SQLException {
        
        if (vehiculoSeleccionado <= 0) {
            return;
        }
        
        String modelo = String.valueOf(vista.getJcbModelo().getSelectedItem());
        int anio = Integer.parseInt((String)vista.getJcbModelo().getSelectedItem());
        String matricula = vista.getTfMatricula().getText();
        String combustible = String.valueOf(vista.getJcbCombustible().getSelectedItem());
             
        
        Transporte vehiculo = new Transporte(this.vehiculoSeleccionado, modelo, anio, matricula, combustible);
        
        if (transportesDao.actualizarDatos(con, vehiculo)) {
            System.out.println("Vehiculo modificado con exito");
            llenarTablaVehiculos();
        } else {
            System.out.println("Error");
        }
    }
    
    private void eliminarVehiculo() throws SQLException {
        
        int respuesta = JOptionPane.showConfirmDialog(
                vista, 
                "¿Seguro que deseas eliminar el vehiculo?", 
                "Eliminar Vehiculo", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE);

        if (respuesta == JOptionPane.YES_OPTION) {
                if(transportesDao.eliminarTransporte(con, vehiculoSeleccionado)) {
                    llenarTablaVehiculos();
                } else {
                    JOptionPane.showMessageDialog(vista, "No puedes eliminar un vehiculo con viajes asociados a el.", "Error al eliminar", JOptionPane.WARNING_MESSAGE);
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
        UbicacionesDAO ubicacionesDao = new UbicacionesDAO();
        
        ControladorViajes ctrlViajes = new ControladorViajes(vistaViajes, viajesDao, ubicacionesDao, usuarioLog, con); 
        vistaViajes.setLocationRelativeTo(null);
        vistaViajes.setVisible(true);
        System.out.println("Se entro a la ventana viajes");
    }
}
