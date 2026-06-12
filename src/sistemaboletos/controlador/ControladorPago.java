package sistemaboletos.controlador;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import sistemaboletos.dao.UbicacionesDAO;
import sistemaboletos.dao.UsuariosDAO;
import sistemaboletos.dao.ViajesDAO;
import sistemaboletos.modelo.Boleto;
import sistemaboletos.modelo.Ubicacion;
import sistemaboletos.modelo.Usuario;
import sistemaboletos.modelo.Viaje;
import sistemaboletos.vista.FrameCompletarPago;
import sistemaboletos.vista.FrameComprar;

public class ControladorPago {
    
    // Atributos
    private FrameCompletarPago vista;
    private Usuario usuarioLog;
    private Viaje viaje;
    private ArrayList<Boleto> asientosSeleccionados;
    private Connection con;

    public ControladorPago(FrameCompletarPago vista, Usuario usuarioLog, Viaje viaje, ArrayList<Boleto> asientosSeleccionados, Connection con) {
        this.vista = vista;
        this.usuarioLog = usuarioLog;
        this.viaje = viaje;
        this.con = con;
        this.asientosSeleccionados = asientosSeleccionados;
        
        this.vista.getLbDolares();
        this.vista.getLbBolivares();
        
        cargarMontos(this.asientosSeleccionados);
    }
    
    public void cargarMontos(ArrayList<Boleto> asientosSeleccionados) {
        double montoDolares = 0;
        double montoBolivares = 0;
        
        montoDolares = (asientosSeleccionados.size() * this.viaje.getPrecio_x_asiento());
        
        this.vista.getLbDolares().setText(String.valueOf(montoDolares));
    }
}
