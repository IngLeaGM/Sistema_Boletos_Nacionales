
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


public class ControladorViajes implements ActionListener {
    private FrameViajesProgramados vista;
    private ViajesDAO viajesDao;
    private UbicacionesDAO ubicacionesDao;
    private Usuario usuarioLog;
    private Connection con;
    private int viajeSeleccionado;
    
    public ControladorViajes(FrameViajesProgramados vista, ViajesDAO viajesDao, UbicacionesDAO ubicacionesDao,
                             Usuario usuarioLog, Connection con) throws SQLException  {
        this.vista = vista;
        this.viajesDao = viajesDao;
        this.ubicacionesDao = ubicacionesDao;
        this.usuarioLog = usuarioLog;
        this.con = con;
        this.viajeSeleccionado = 0;
        
        this.vista.getTbtnUsuarios().addActionListener((ActionListener) this);
        this.vista.getTbtnFacturas().addActionListener((ActionListener) this);
        this.vista.getBtnInsertar().addActionListener((ActionListener) this);
        this.vista.getBtnModificar().addActionListener((ActionListener) this);
        this.vista.getBtnEliminar().addActionListener((ActionListener) this);
        this.vista.getBtnVolver().addActionListener((ActionListener) this);
        this.vista.getBtnVehiculos().addActionListener((ActionListener) this);
        this.vista.getjCalendar().addMouseListener(new java.awt.event.MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
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
           
        this.vista.getTbViajes().addMouseListener(new java.awt.event.MouseListener() {
           
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
        
        this.vista.getTbViajes().removeColumn(this.vista.getTbViajes().getColumnModel().getColumn(0));
        llenarTablaViajes();
        cargarCombos(this.con);
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
        } else if (e.getSource() == vista.getBtnVehiculos()) {
            try {
                abrirVentanaVehiculos();
            } catch (Exception ex) {
                System.out.println("Ocurrio un error: " + ex);
            } 
        } else if (e.getSource() == vista.getBtnInsertar()) {
            try {
                insertarViaje();
            } catch (Exception ex) {
                System.out.println("Ocurrio un error: " + ex);
            } 
        } else if (e.getSource() == vista.getBtnModificar()) {
            try {
                modificarViaje();
            } catch (Exception ex) {
                System.out.println("Ocurrio un error: " + ex);
            } 
        } else if (e.getSource() == vista.getBtnEliminar()) {
            try {
                eliminarViaje();
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
    
    private void llenarTablaViajes() throws SQLException {
        System.out.println("Se ejecuto LlenarTabla");
        
        DefaultTableModel modelo = (DefaultTableModel) vista.getTbViajes().getModel();
        
        modelo.setRowCount(0);
        
        List<ViajeInformacion> viajesInformacion = viajesDao.ObtenerViajesInformacion(this.con);
        
        for (ViajeInformacion viaje : viajesInformacion) {
            
            Object[] fila = new Object[6];
            fila[0] = viaje.getId_viaje();
            System.out.println("Id: " + fila[0]);
            fila[1] = viaje.getCiudad_salida();
            fila[2] = viaje.getCiudad_destino();
            fila[3] = viaje.getFecha();
            fila[4] = viaje.getPrecio_x_asiento() + "$";
            fila[5] = viaje.getMatricula();
            System.out.println(viaje.getPrecio_x_asiento());
            
            modelo.addRow(fila);
        } 
    }
    
    public void seleccionarFila() {
        
        int fila = vista.getTbViajes().getSelectedRow();
        
        if (fila != -1) {
            int filaModelo = vista.getTbViajes().convertRowIndexToModel(fila);
        
            int id_Viaje = Integer.parseInt(vista.getTbViajes().getModel().getValueAt(filaModelo, 0).toString());

            System.out.println("El usuario seleccionó la fila completa del viaje ID: " + id_Viaje);
            this.viajeSeleccionado = id_Viaje;
        } else {
            this.viajeSeleccionado = 0;
        }

    }
    
    private void cargarCombos(Connection con) throws SQLException {
        
        TransportesDAO transportesDao = new TransportesDAO();
        
        vista.getJcbDesde().removeAllItems(); // Limpiamos el combo por si hay items basura
        vista.getJcbHasta().removeAllItems();
        ArrayList<Ubicacion> ubicacionesDisponibles = this.ubicacionesDao.obtener_ubicaciones(this.con);
        ArrayList<Transporte> transportesDisponibles = transportesDao.obtener_Transportes(con);
        
        
        for (Ubicacion ubObtenida : ubicacionesDisponibles) {
            vista.getJcbDesde().addItem(ubObtenida);
            vista.getJcbHasta().addItem(ubObtenida);
        }
        
        for (Transporte tpObtenido : transportesDisponibles) {
            vista.getJcbMatricula().addItem(tpObtenido);
        }

    }
    
    public String seleccionarFecha() {
        Date fechaSeleccionada = vista.getjCalendar().getDate();
        
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String fechaTexto = formato.format(fechaSeleccionada);
        
        return fechaTexto;
    }
    
    
    private void insertarViaje() throws SQLException {
        Ubicacion u_salida = (Ubicacion) vista.getJcbDesde().getSelectedItem();
        Ubicacion u_destino = (Ubicacion) vista.getJcbHasta().getSelectedItem();
        
        Transporte transporte = (Transporte) vista.getJcbMatricula().getSelectedItem();
        
        int id_salida = u_salida.getId_ubicacion();
        int id_destino = u_destino.getId_ubicacion();
        int id_transporte = transporte.getId_transporte();
        
        String fecha = seleccionarFecha();
        
        Date hora_date = (Date) vista.getJspHora().getValue();
        
        SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
        String hora = formato.format(hora_date);
        String fecha_completa = fecha + " " + hora;
        
        double precio = Double.parseDouble(vista.getTfPrecio().getText());
        Viaje viaje = new Viaje(id_salida, id_destino, id_transporte, fecha_completa, precio);
        
        if (viajesDao.insertar_Viaje(con, viaje)) {
            System.out.println("Viaje insertado con exito");
            llenarTablaViajes();
        } else {
            System.out.println("error");
        }
    }
    
    private void modificarViaje() throws SQLException {
        
        if (viajeSeleccionado <= 0) {
            return;
        }
        
        Ubicacion u_salida = (Ubicacion) vista.getJcbDesde().getSelectedItem();
        Ubicacion u_destino = (Ubicacion) vista.getJcbHasta().getSelectedItem();
        
        Transporte transporte = (Transporte) vista.getJcbMatricula().getSelectedItem();
        
        int id_salida = u_salida.getId_ubicacion();
        int id_destino = u_destino.getId_ubicacion();
        int id_transporte = transporte.getId_transporte();
        
        String fecha = seleccionarFecha();
        
        Date hora_date = (Date) vista.getJspHora().getValue();
        
        SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
        String hora = formato.format(hora_date);
        String fecha_completa = fecha + " " + hora;
        
        double precio = Double.parseDouble(vista.getTfPrecio().getText());
        Viaje viaje = new Viaje(this.viajeSeleccionado, id_salida, id_destino, id_transporte, fecha_completa, precio);
        
        if (viajesDao.actualizarDatos(con, viaje)) {
            System.out.println("Viaje modificado con exito");
            llenarTablaViajes();
        } else {
            System.out.println("Error");
        }
    }
    
    private void eliminarViaje() throws SQLException {
        
        int respuesta = JOptionPane.showConfirmDialog(
                vista, 
                "¿Seguro que deseas eliminar el viaje?", 
                "Eliminar Viaje", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE);

        if (respuesta == JOptionPane.YES_OPTION) {
                if(viajesDao.eliminarViaje(con, viajeSeleccionado)) {
                    llenarTablaViajes();
                } else {
                    JOptionPane.showMessageDialog(vista, "No puedes eliminar un viaje con boletos asociados a el.", "Error al eliminar", JOptionPane.WARNING_MESSAGE);
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
