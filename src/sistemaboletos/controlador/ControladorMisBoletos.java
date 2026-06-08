package sistemaboletos.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import sistemaboletos.conexion.ConexionBD;
import sistemaboletos.dao.BoletosDAO;
import sistemaboletos.dao.UbicacionesDAO;
import sistemaboletos.dao.UsuariosDAO;
import sistemaboletos.dao.ViajesDAO;
import sistemaboletos.modelo.BoletoDetalle;
import sistemaboletos.modelo.BoletoInformacion;
import sistemaboletos.modelo.Ubicacion;
import sistemaboletos.modelo.Usuario;
import sistemaboletos.modelo.Viaje;
import sistemaboletos.vista.FrameComprar;
import sistemaboletos.vista.FrameMenu;
import sistemaboletos.vista.FrameMisBoletos;


public class ControladorMisBoletos implements ActionListener {
    
    // Atributos
    private FrameMisBoletos vista;
    private UsuariosDAO userDao;
    private ViajesDAO viajesDao;
    private UbicacionesDAO ubicacionesDao;
    private BoletosDAO boletosDao;
    private List<Viaje> listaViajes;
    private List<Ubicacion> listaUbicaciones;
    private Usuario usuarioLog;
    private Connection con;
    
    public ControladorMisBoletos(FrameMisBoletos vista, UsuariosDAO userDao, ViajesDAO viajesDao,
                                UbicacionesDAO ubicacionesDao, BoletosDAO boletosDao, List<Viaje> listaViajes,
                                List<Ubicacion> listaUbicaciones, Usuario usuarioLog, Connection con) throws SQLException  {
        this.vista = vista;
        this.userDao = userDao;
        this.viajesDao = viajesDao;
        this.ubicacionesDao = ubicacionesDao;
        this.boletosDao = boletosDao;
        this.listaViajes = listaViajes;
        this.listaUbicaciones = listaUbicaciones;
        this.usuarioLog = usuarioLog;
        this.con = con;
        
        this.vista.getBtnInicio().addActionListener(this);
        this.vista.getBtnComprar().addActionListener(this);
        this.vista.getTbBoletos().addMouseListener(new java.awt.event.MouseListener() {
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
        this.vista.getTbBoletos().removeColumn(this.vista.getTbBoletos().getColumnModel().getColumn(0));
        llenarTablaBoletos();
        
    }
    
    
    private void llenarTablaBoletos() throws SQLException {
        System.out.println("Se ejecuto LlenarTabla");
        int id_usuario = usuarioLog.getId_usuario();
        System.out.println(id_usuario);
        
        DefaultTableModel modelo = (DefaultTableModel) vista.getTbBoletos().getModel();
        
        modelo.setRowCount(0);
        
        List<BoletoDetalle> boletosUsuario = boletosDao.obtenerBoletosUsuario(con, id_usuario);
        
        for (BoletoDetalle boleto : boletosUsuario) {
            
            Object[] fila = new Object[4];
            fila[0] = boleto.getId_boleto();
            System.out.println("Id: " + fila[0]);
            fila[1] = boleto.getAsiento();
            fila[2] = boleto.getFecha_salida();
            fila[3] = boleto.getNom_pasajero();
            System.out.println(boleto.getAsiento());
            
            modelo.addRow(fila);
        }

        
    }

    public void seleccionarFila() {
        
        int fila = vista.getTbBoletos().getSelectedRow();
        
        if (fila != -1) {
            int filaModelo = vista.getTbBoletos().convertRowIndexToModel(fila);
        
            int idBoletoSeleccionado = Integer.parseInt(vista.getTbBoletos().getModel().getValueAt(filaModelo, 0).toString());

            System.out.println("El usuario seleccionó la fila completa del viaje ID: " + idBoletoSeleccionado);
            cargarInformacion(this.con, idBoletoSeleccionado, boletosDao);
        }

    }
    
    public void cargarInformacion(Connection con, int id_boleto, BoletosDAO boletosDao) {
        BoletoInformacion informacion = boletosDao.obtenerInformacion(con, id_boleto);
        
        vista.getTfNombre().setText(informacion.getNom_pasajero());
        vista.getTfCedula().setText(informacion.getCedula());
        vista.getTfSalida().setText(informacion.getCiudad_salida());
        vista.getTfHasta().setText(informacion.getCiudad_destino());
        vista.getTfDatos().setText(informacion.getDatos_transaccion());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
         if (e.getSource() == vista.getBtnComprar()) {
            try {
                abrirVentanaComprar();
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
        }
    }
    
    private void abrirVentanaComprar() throws SQLException {
          
        vista.dispose();
        
        FrameComprar vistaComprar = new FrameComprar();    
        
        ControladorComprar ctrlComprar = new ControladorComprar(vistaComprar, userDao, viajesDao, ubicacionesDao,
                                                                listaViajes, listaUbicaciones, usuarioLog, con); 
        vistaComprar.setLocationRelativeTo(null);
        vistaComprar.setVisible(true);
        System.out.println("Se entro a la ventana comprar");
    }
}
