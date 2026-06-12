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
import sistemaboletos.dao.UbicacionesDAO;
import sistemaboletos.dao.UsuariosDAO;
import sistemaboletos.dao.ViajesDAO;
import sistemaboletos.modelo.Boleto;
import sistemaboletos.modelo.Ubicacion;
import sistemaboletos.modelo.Usuario;
import sistemaboletos.modelo.Viaje;
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
        
        this.vista.getBtnRegresar();
        
        cargarMontos(this.asientosSeleccionados);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnRegresar()) {
            try {
                ejecutarRegreso();
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
    
    private void ejecutarRegreso() throws SQLException {
        
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
}
