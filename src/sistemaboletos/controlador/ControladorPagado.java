package sistemaboletos.controlador;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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
import sistemaboletos.vista.FrameMenu;
import sistemaboletos.vista.FramePagoCompletado;

public class ControladorPagado implements ActionListener {
    
    // Atributos
    private FramePagoCompletado vista;
    private Usuario usuarioLog;
    private UsuariosDAO userDao;
    private String ruta;
    private Connection con;

    public ControladorPagado(FramePagoCompletado vista, Usuario usuarioLog, UsuariosDAO userDao, String ruta, Connection con)  {
        this.vista = vista;
        this.usuarioLog = usuarioLog;
        this.userDao = userDao;
        this.ruta = ruta;
        this.con = con;
        
        this.vista.getBtnFactura().addActionListener(this);
        this.vista.getBtnMenu().addActionListener(this);
        
        System.out.println("Se carga el controlador");
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnFactura()) {
            verFactura(ruta);
            System.out.println("Funcion completar");
        } else if (e.getSource() == vista.getBtnMenu()) {
            try {
                ejecutarRegreso(con);
            } catch (SQLException ex) {
                Logger.getLogger(ControladorLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    private void ejecutarRegreso(Connection con) throws SQLException {
        
        try {
            vista.dispose();

            con.close();
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
    
    private void verFactura(String rutaPDF) {
        
        try {
            File archivoPdf = new File(rutaPDF);

            // Verificamos que la computadora soporte esta función y que el archivo exista
            if (Desktop.isDesktopSupported() && archivoPdf.exists()) {

                Desktop.getDesktop().open(archivoPdf); // Esta linea abre el pdf

            } else {
                JOptionPane.showMessageDialog(null, "El archivo PDF no existe o la función no está soportada en este sistema.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
                System.err.println("Error al intentar abrir el PDF: " + ex.getMessage());
        } 
    }
    
}
