package sistemaboletos.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import sistemaboletos.conexion.ConexionBD;
import sistemaboletos.dao.BoletosDAO;
import sistemaboletos.dao.FacturasDAO;
import sistemaboletos.dao.UbicacionesDAO;
import sistemaboletos.dao.UsuariosDAO;
import sistemaboletos.dao.ViajesDAO;
import sistemaboletos.modelo.Boleto;
import sistemaboletos.modelo.BoletoInformacion;
import sistemaboletos.modelo.Factura;
import sistemaboletos.modelo.Ubicacion;
import sistemaboletos.modelo.Usuario;
import sistemaboletos.modelo.Viaje;
import sistemaboletos.modelo.ViajeInformacion;
import sistemaboletos.servicio.ServicioFactura;
import sistemaboletos.vista.FrameCompletarPago;
import sistemaboletos.vista.FrameComprar;

public class ControladorPago implements ActionListener {
    
    // Atributos
    private FrameCompletarPago vista;
    private Usuario usuarioLog;
    private UsuariosDAO userDao;
    private Viaje viaje;
    private ArrayList<Boleto> asientosSeleccionados;
    private Connection con;

    public ControladorPago(FrameCompletarPago vista, Usuario usuarioLog, UsuariosDAO userDao, Viaje viaje, ArrayList<Boleto> asientosSeleccionados, Connection con)  {
        this.vista = vista;
        this.usuarioLog = usuarioLog;
        this.userDao = userDao;
        this.viaje = viaje;
        this.con = con;
        this.asientosSeleccionados = asientosSeleccionados;
        
        this.vista.getLbDolares();
        this.vista.getLbBolivares();
        
        this.vista.getBtnRegresar().addActionListener(this);
        this.vista.getBtnCompletar().addActionListener(this);
        
        cargarMontos(this.asientosSeleccionados);
        System.out.println("Se carga el controlador");
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnCompletar()) {
            
            try {
                System.out.println("Funcion completar");
                completarPago(usuarioLog, asientosSeleccionados);
            } catch (SQLException ex) {
                Logger.getLogger(ControladorPago.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == vista.getBtnRegresar()) {
            
            try {
                ejecutarRegreso(con);
            } catch (SQLException ex) {
                Logger.getLogger(ControladorLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    public void cargarMontos(ArrayList<Boleto> asientosSeleccionados) {
        final double tasa_dolares = 580;
        double montoDolares = 0;
        double montoBolivares = 0;
        
        montoDolares = (asientosSeleccionados.size() * this.viaje.getPrecio_x_asiento());
        montoBolivares = tasa_dolares * montoDolares;
        
        this.vista.getLbDolares().setText(String.valueOf(montoDolares + "$"));
        this.vista.getLbBolivares().setText(String.valueOf(montoBolivares + " Bs."));
    }
    
    private void ejecutarRegreso(Connection con) throws SQLException {
        
        vista.dispose();
        
        FrameComprar vistaComprar = new FrameComprar();
        
        ViajesDAO viajesDao = new ViajesDAO();
        UbicacionesDAO ubicacionesDao = new UbicacionesDAO();
        
        List<Viaje> listaViajes = viajesDao.obtener_Viajes(this.con);
        List<Ubicacion> listaUbicaciones = ubicacionesDao.obtener_ubicaciones(con);
        
        ControladorComprar ctrlComprar = new ControladorComprar(vistaComprar, userDao, viajesDao, ubicacionesDao,
                                                                listaViajes, listaUbicaciones, usuarioLog, con); 
        vistaComprar.setLocationRelativeTo(null);
        vistaComprar.setVisible(true);
        System.out.println("Se entro a la ventana comprar");
    }
    
    private void completarPago(Usuario usuarioLog, ArrayList<Boleto> asientosSeleccionados) throws SQLException {
        
        Connection con = ConexionBD.getConexion();
        
        String cedula = vista.getTfCedula().getText();
        String telf = vista.getTfTelf().getText();
        String clave = vista.getTfClave().getText();
        
        if (cedula.isEmpty() || telf.isEmpty() || clave.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }
        FacturasDAO facturaDao = new FacturasDAO();
        BoletosDAO boletosDao = new BoletosDAO();
        ViajesDAO viajeDao = new ViajesDAO();
        
        Factura factura = new Factura();
     
        
        factura.setUsuario_id(usuarioLog.getId_usuario());
        factura.setMonto_total(Double.valueOf(this.vista.getLbDolares().getText().replace("$", "").trim()));
        factura.setMetodo_pago("Pago Movil");
        
        ViajeInformacion viajeInformacion = viajeDao.obtenerViajeInformacion(con, this.viaje);
        int id_viaje = viajeInformacion.getId_viaje();
        Double monto_total = factura.getMonto_total();
        
        int id_factura = facturaDao.insertar_factura(con, factura);
        
        factura = facturaDao.obtener_Factura(con, id_factura);
        
                
        for (Boleto b : asientosSeleccionados) {
            b.setId_viaje(id_viaje);
            b.setId_factura(id_factura);
        }
        
        if (boletosDao.insertarBoletos(con, asientosSeleccionados)) {
            ServicioFactura facturacion = new ServicioFactura();
        
            facturacion.generarFacturaPDF(factura, viajeInformacion, monto_total, asientosSeleccionados);
            ejecutarRegreso(con);
        }

    }
}
