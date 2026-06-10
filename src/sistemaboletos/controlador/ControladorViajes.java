
package sistemaboletos.controlador;

import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import sistemaboletos.dao.BoletosDAO;
import sistemaboletos.dao.UbicacionesDAO;
import sistemaboletos.dao.UsuariosDAO;
import sistemaboletos.dao.ViajesDAO;
import sistemaboletos.modelo.BoletoDetalle;
import sistemaboletos.modelo.Ubicacion;
import sistemaboletos.modelo.Usuario;
import sistemaboletos.modelo.Viaje;
import sistemaboletos.modelo.ViajeInformacion;
import sistemaboletos.vista.FrameMisBoletos;
import sistemaboletos.vista.FrameViajesProgramados;


public class ControladorViajes {
    private FrameViajesProgramados vista;
    private ViajesDAO viajesDao;
    private UbicacionesDAO ubicacionesDao;
    private Connection con;
    
    public ControladorViajes(FrameViajesProgramados vista, ViajesDAO viajesDao, UbicacionesDAO ubicacionesDao, Connection con) throws SQLException  {
        this.vista = vista;
        this.viajesDao = viajesDao;
        this.ubicacionesDao = ubicacionesDao;
        this.con = con;
        
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
            fila[2] = viaje.getCiudad_salida();
            fila[3] = viaje.getFecha();
            fila[4] = viaje.getPrecio_x_asiento();
            fila[5] = viaje.getMatricula();
            System.out.println(viaje.getPrecio_x_asiento());
            
            modelo.addRow(fila);
        }

        
    }
    
    public void seleccionarFila() {
        
        int fila = vista.getTbViajes().getSelectedRow();
        
        if (fila != -1) {
            int filaModelo = vista.getTbViajes().convertRowIndexToModel(fila);
        
            int idBoletoSeleccionado = Integer.parseInt(vista.getTbViajes().getModel().getValueAt(filaModelo, 0).toString());

            System.out.println("El usuario seleccionó la fila completa del viaje ID: " + idBoletoSeleccionado);
        }

    }
    
    public void cargarCombos(Connection con) throws SQLException {
        vista.getJcbDesde().removeAllItems(); // Limpiamos el combo por si hay items basura
        vista.getJcbHasta().removeAllItems();
        ArrayList<Ubicacion> ubicacionesDisponibles = this.ubicacionesDao.obtener_ubicaciones(this.con);
        
        
        for (Ubicacion ubObtenida : ubicacionesDisponibles) {
            vista.getJcbDesde().addItem(ubObtenida);
            vista.getJcbHasta().addItem(ubObtenida);
        }

    }
}
