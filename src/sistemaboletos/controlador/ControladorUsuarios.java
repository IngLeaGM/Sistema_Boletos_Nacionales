package sistemaboletos.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sistemaboletos.conexion.ConexionBD;
import sistemaboletos.dao.BoletosDAO;
import sistemaboletos.dao.FacturasDAO;
import sistemaboletos.dao.UbicacionesDAO;
import sistemaboletos.dao.UsuariosDAO;
import sistemaboletos.dao.ViajesDAO;
import sistemaboletos.modelo.Boleto;
import sistemaboletos.modelo.Ubicacion;
import sistemaboletos.modelo.Usuario;
import sistemaboletos.modelo.Viaje;
import sistemaboletos.vista.FrameComprar;
import sistemaboletos.vista.FrameMenu;
import sistemaboletos.vista.FrameMisBoletos;
import sistemaboletos.vista.FrameAdmin;
import sistemaboletos.vista.FrameContrasena;
import sistemaboletos.vista.FrameDatosBoletos;
import sistemaboletos.vista.FrameFacturas;
import sistemaboletos.vista.FrameUsuarios;
import sistemaboletos.vista.FrameViajesProgramados;

public class ControladorUsuarios implements ActionListener {
    
    private FrameUsuarios vista;
    private UsuariosDAO usuariosDao;
    private Usuario usuarioLog;
    private Connection con;
    private int usuarioSeleccionado;
    
    public ControladorUsuarios(FrameUsuarios vista, UsuariosDAO usuariosDao, Usuario usuarioLog, Connection con) throws SQLException  {
        this.vista = vista;
        this.usuariosDao = usuariosDao;
        this.usuarioLog = usuarioLog;
        this.con = con;
        this.usuarioSeleccionado = 0;
        
        this.vista.getTbtnFacturas().addActionListener((ActionListener) this);
        this.vista.getTbtnViajes().addActionListener((ActionListener) this);
        this.vista.getBtnAgregar().addActionListener((ActionListener) this);
        this.vista.getBtnEditar().addActionListener((ActionListener) this);
        this.vista.getBtnEliminar().addActionListener((ActionListener) this);
        this.vista.getBtnVolver().addActionListener((ActionListener) this);
        
         this.vista.getJtbUsuarios().addMouseListener(new java.awt.event.MouseListener() {
           
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
        llenarTablaUsuarios();
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getTbtnFacturas()) {
            try {
                abrirVentanaFacturas();
            } catch (Exception ex) {
                System.err.print("Ocurrio un error: " + ex);
            }
        } else if (e.getSource() == vista.getTbtnViajes()) {
            try {
                abrirVentanaViajes();
            } catch (Exception ex) {
                System.out.println("Ocurrio un error: " + ex);
            } 
        }  else if (e.getSource() == vista.getBtnAgregar()) {
            try {
                insertarUsuario();
            } catch (Exception ex) {
                System.out.println("Ocurrio un error: " + ex);
            } 
        } else if (e.getSource() == vista.getBtnEditar()) {
            try {
                modificarUsuario();
            } catch (Exception ex) {
                System.out.println("Ocurrio un error: " + ex);
            } 
        } else if (e.getSource() == vista.getBtnEliminar()) {
            try {
                eliminarUsuario();
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
    
    public void seleccionarFila() {
        
        int fila = vista.getJtbUsuarios().getSelectedRow();
        
        if (fila != -1) {
            int filaModelo = vista.getJtbUsuarios().convertRowIndexToModel(fila);
        
            int id_usuario = Integer.parseInt(vista.getJtbUsuarios().getModel().getValueAt(filaModelo, 0).toString());
            String user = vista.getJtbUsuarios().getModel().getValueAt(filaModelo, 1).toString();
            String correo = vista.getJtbUsuarios().getModel().getValueAt(filaModelo, 2).toString();
            String telf = vista.getJtbUsuarios().getModel().getValueAt(filaModelo, 3).toString();
            
            vista.getTfUser().setText(user);
            vista.getTfCorreo().setText(correo);
            vista.getTfTelf().setText(telf);

            System.out.println("El usuario seleccionó la fila completa del usuario ID: " + id_usuario);
            this.usuarioSeleccionado = id_usuario;
        } else {
            this.usuarioSeleccionado = 0;
        }

    }
    
    private void llenarTablaUsuarios() throws SQLException {
        System.out.println("Se ejecuto LlenarTabla");
        
        DefaultTableModel modelo = (DefaultTableModel) vista.getJtbUsuarios().getModel();
        
        modelo.setRowCount(0);
        
        List<Usuario> listaUsuarios = usuariosDao.ObtenerUsuarios(this.con);
        
        for (Usuario usuario : listaUsuarios) {
            
            Object[] fila = new Object[4];
            fila[0] = usuario.getId_usuario();
            System.out.println("Id: " + fila[0]);
            fila[1] = usuario.getUser();
            fila[2] = usuario.getEmail();
            fila[3] = usuario.getTelf();
            
            modelo.addRow(fila);
        } 
    }
    
    private void insertarUsuario() throws SQLException {
        String user = vista.getTfUser().getText();
        String correo = vista.getTfCorreo().getText();
        String telf = vista.getTfTelf().getText();
        
        String pass;
        
        Usuario usuarioInsert = new Usuario();
        
        FrameContrasena vistaDialog = new FrameContrasena(vista, true);

        vistaDialog.setLocationRelativeTo(vista);

        ControladorContrasena ctclPass = new ControladorContrasena(vistaDialog);

        vistaDialog.setVisible(true); 

        if (vistaDialog.isDatosGuardados()) {
            
             pass = new String(vistaDialog.getjpPass().getPassword());
             usuarioInsert = new Usuario(user, correo, pass, telf);
        }

        
        if (usuariosDao.RegistrarUsuario(usuarioInsert)) {
            System.out.println("Usuario insertado con exito");
            llenarTablaUsuarios();
        } else {
            System.out.println("error");
        }
    }
    
    private void modificarUsuario() throws SQLException {
        
        if (usuarioSeleccionado <= 0) {
            return;
        }
        
        int id_usuario = usuarioSeleccionado;
        String user = vista.getTfUser().getText();
        String correo = vista.getTfCorreo().getText();
        String telf = vista.getTfTelf().getText();
        
        
        Usuario usuarioInsert = new Usuario(id_usuario, user, correo, telf);
        
        
        if (usuariosDao.actualizarDatos(con, usuarioInsert)) {
            System.out.println("Usuario modificado con exito");
            llenarTablaUsuarios();
        } else {
            System.out.println("Error");
        }
    }
    
    private void eliminarUsuario() throws SQLException {
        
        int respuesta = JOptionPane.showConfirmDialog(
                vista, 
                "¿Seguro que deseas eliminar el usuario?", 
                "Eliminar Usuario", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE);

        if (respuesta == JOptionPane.YES_OPTION) {
                if(usuariosDao.eliminarUsuario(con, usuarioSeleccionado)) {
                    llenarTablaUsuarios();
                } else {
                    JOptionPane.showMessageDialog(vista, "No puedes eliminar un usuario con facturas asociados a el.", "Error al eliminar", JOptionPane.WARNING_MESSAGE);
                }      
        }   
    }
    
    private void abrirVentanaFacturas() throws SQLException {
        
        vista.dispose();
        
        FrameFacturas vistaFacturas = new FrameFacturas();
        
        FacturasDAO facturasDao = new FacturasDAO();
        
        ControladorFacturas ctrlFacturas = new ControladorFacturas(vistaFacturas, facturasDao, usuarioLog, con); 
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
