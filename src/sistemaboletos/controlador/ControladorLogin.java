package sistemaboletos.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import sistemaboletos.dao.UsuariosDAO;
import sistemaboletos.modelo.Usuario;
import sistemaboletos.vista.FrameLogin;
import sistemaboletos.vista.FrameMenu;
import sistemaboletos.vista.FrameRegistrar;

public class ControladorLogin implements ActionListener {
    
    // Atributos para FrameLogin
    private FrameLogin vista;
    private UsuariosDAO dao;
    
    public ControladorLogin(FrameLogin vista, UsuariosDAO dao) {
        this.vista = vista;
        this.dao = dao;
        
        this.vista.getBtnIngresar().addActionListener(this);
        this.vista.getBtnRegistrarse().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnIngresar()) {
            try {
                ejecutarLogin();
            } catch (SQLException ex) {
                Logger.getLogger(ControladorLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == vista.getBtnRegistrarse()) {
            try {
                abrirVentanaRegistro();
            } catch (Exception ex) {
                System.err.print("Ocurrio un error: " + ex);
            }
        }
        
        
    }
    
    private void ejecutarLogin() throws SQLException {
        String email = vista.getTfEmail().getText().trim();
        String pass = new String(vista.getJpPass().getPassword());
        
        if (email.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Usuario usuarioIntento = new Usuario();
        usuarioIntento.setEmail(email);
        usuarioIntento.setPass(pass);
        
        try {
            
            boolean loginExitoso = dao.LoginUsuario(usuarioIntento);
            if (loginExitoso) {
                JOptionPane.showMessageDialog(vista, "¡Inicio de sesión exitoso! Bienvenido.");
               
                vista.dispose(); // Cierra y destruye la ventana de Login actual
                
              
                FrameMenu menuPrincipal = new FrameMenu();
                // ControladorMenu ctrlMenu = new ControladorMenu(FrameMenu);
                menuPrincipal.setVisible(true);
                
            } else {
                // Mensaje si BCrypt retorna false o el correo no existe
                JOptionPane.showMessageDialog(vista, "Correo o contraseña incorrectos.", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            // Manejo de errores si se cae la conexión a MariaDB/MySQL
            JOptionPane.showMessageDialog(vista, "Error de conexión con la base de datos: " + ex.getMessage(), "Error de Sistema", JOptionPane.ERROR_MESSAGE);
        }

    }
    
    private void abrirVentanaRegistro() {
        vista.dispose();
        
        FrameRegistrar vistaRegistro = new FrameRegistrar();
        
        UsuariosDAO dao = new UsuariosDAO();
        
        // ControladorRegistrar ctrlRegistrar = new ControladorRegistro(vistaRegistro, dao); 
        
        vistaRegistro.setLocationRelativeTo(null);
        vistaRegistro.setVisible(true);
        System.out.println("Se entro a la ventana de registro");
    }
    
        
}
