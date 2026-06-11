
package sistemaboletos.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import sistemaboletos.conexion.ConexionBD;
import sistemaboletos.dao.BoletosDAO;
import sistemaboletos.dao.UbicacionesDAO;
import sistemaboletos.dao.UsuariosDAO;
import sistemaboletos.dao.ViajesDAO;
import sistemaboletos.modelo.Boleto;
import sistemaboletos.modelo.Ubicacion;
import sistemaboletos.modelo.Usuario;
import sistemaboletos.modelo.Viaje;
import sistemaboletos.vista.FrameComprar;
import sistemaboletos.vista.FrameDatosBoletos;
import sistemaboletos.vista.FrameMenu;
import sistemaboletos.vista.FrameMisBoletos;


public class ControladorComprar implements ActionListener {
    
    private HashMap<String, Boleto> asientosSeleccionados = new HashMap<>();
    // Atributos
    private FrameComprar vista;
    private UsuariosDAO userDao;
    private ViajesDAO viajesDao;
    private UbicacionesDAO ubicacionesDao;
    private List<Viaje> listaViajes;
    private List<Ubicacion> listaUbicaciones;
    private Usuario usuarioLog;
    private Connection con;
    
    
    //Constructor
    public ControladorComprar(FrameComprar vista, UsuariosDAO userDao, ViajesDAO viajesDao,
                                UbicacionesDAO ubicacionesDao, List<Viaje> listaViajes,
                                List<Ubicacion> listaUbicaciones, Usuario usuarioLog, Connection con) throws SQLException  {
        this.vista = vista;
        this.userDao = userDao;
        this.viajesDao = viajesDao;
        this.ubicacionesDao = ubicacionesDao;
        this.listaViajes = listaViajes;
        this.listaUbicaciones = listaUbicaciones;
        this.usuarioLog = usuarioLog;
        this.con = con;
        
        this.vista.getBtnInicio().addActionListener(this);
        this.vista.getJcbDesde().addActionListener(this);
        this.vista.getBtnMisBoletos().addActionListener(this);
        
        cargarViajes(con);
        
        // Logica de selecion de asientos
        vista.getBtnV01().addItemListener(new java.awt.event.ItemListener() {
            @Override
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                // Obtenemos el botón que disparó el evento y su texto
                javax.swing.JToggleButton btn = (javax.swing.JToggleButton) evt.getSource();
                String numeroAsiento = btn.getText(); 

                //  El usuario selecciona el asiento
                if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {

                    // Instanciamos el JDialog (pasando la vista principal como padre y 'true' para hacerlo Modal)
                    FrameDatosBoletos vistaDialog = new FrameDatosBoletos(vista, true);

                    vistaDialog.setLocationRelativeTo(vista);

                    //  Aquí iría el controlador de esa mini-ventana (que escucha el botón guardar)
                    // Cuando le den "Guardar", ese controlador hace: vistaDialog.setDatosGuardados(true); vistaDialog.dispose();

                    vistaDialog.setVisible(true); 

                    // Cuando el JDialog se cierra, el código continúa aquí. Evaluamos qué pasó:
                    if (vistaDialog.isDatosGuardados()) {
                        // El usuario llenó los datos y le dio a guardar
                        Boleto nuevoBoleto = new Boleto();
                        nuevoBoleto.setAsiento(numeroAsiento);
                        nuevoBoleto.setNom_pasajero(vistaDialog.getTfNombre().getText());
                        nuevoBoleto.setCedula(Integer.parseInt(vistaDialog.getTfCedula().getText()));

                        // Guardamos en nuestro HashMap temporal
                        asientosSeleccionados.put(numeroAsiento, nuevoBoleto);

                    } else {
                        // El usuario cerró la ventana con la "X" o le dio a Cancelar.
                        // Como no guardó, revertimos el botón para que no quede marcado.

                        // Removemos el listener temporalmente para que este 'setSelected(false)' no vuelva a disparar el evento
                        btn.removeItemListener(this); 
                        btn.setSelected(false);
                        btn.addItemListener(this);
                    }

                 // Si el usuario deselecciona un asiento
                } else if (evt.getStateChange() == java.awt.event.ItemEvent.DESELECTED) {
                    // Simplemente lo borramos de nuestro HashMap de almacenamiento temporal
                    asientosSeleccionados.remove(numeroAsiento);
                }
            }
        });
        
        vista.getBtnV02().addItemListener(new java.awt.event.ItemListener() {
            @Override
            public void itemStateChanged(java.awt.event.ItemEvent evt) {

                javax.swing.JToggleButton btn = (javax.swing.JToggleButton) evt.getSource();
                String numeroAsiento = btn.getText(); 


                if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {


                    FrameDatosBoletos vistaDialog = new FrameDatosBoletos(vista, true);

                    vistaDialog.setLocationRelativeTo(vista);

                    vistaDialog.setVisible(true); 


                    if (vistaDialog.isDatosGuardados()) {
                        // El usuario llenó los datos y le dio a guardar
                        Boleto nuevoBoleto = new Boleto();
                        nuevoBoleto.setAsiento(numeroAsiento);
                        nuevoBoleto.setNom_pasajero(vistaDialog.getTfNombre().getText());
                        nuevoBoleto.setCedula(Integer.parseInt(vistaDialog.getTfCedula().getText()));



                        // Guardamos en nuestro HashMap temporal
                        asientosSeleccionados.put(numeroAsiento, nuevoBoleto);

                    } else {
                        // El usuario cerró la ventana con la "X" o le dio a Cancelar.
                        // Como no guardó, revertimos el botón para que no quede marcado.

                        // Removemos el listener temporalmente para que este 'setSelected(false)' no vuelva a disparar el evento
                        btn.removeItemListener(this); 
                        btn.setSelected(false);
                        btn.addItemListener(this);
                    }


                } else if (evt.getStateChange() == java.awt.event.ItemEvent.DESELECTED) {
                    
                    asientosSeleccionados.remove(numeroAsiento);
                }
            }
        });
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
                vista.dispose();


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
            
        } else if (e.getSource() == vista.getBtnMisBoletos()) {
            try {
                abrirVentanaBoletos();
            } catch (Exception ex) {
                System.err.print("Ocurrio un error: " + ex);
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
    
    private void abrirVentanaBoletos() throws SQLException {
        
        System.out.println(usuarioLog.getId_usuario());
        
        vista.dispose();
        
        FrameMisBoletos vistaBoletos = new FrameMisBoletos();
        
        BoletosDAO boletosDao = new BoletosDAO();
        
        ControladorMisBoletos ctrlBoletos = new ControladorMisBoletos(vistaBoletos, userDao, viajesDao, ubicacionesDao,
                                                                boletosDao, listaViajes, listaUbicaciones, usuarioLog, con); 
        
        vistaBoletos.setLocationRelativeTo(null);
        vistaBoletos.setVisible(true);
        System.out.println("Se entro a la ventana Mis Boletos");
    }

}
