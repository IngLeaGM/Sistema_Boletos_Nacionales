package sistemaboletos.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import sistemaboletos.dao.UsuariosDAO;
import sistemaboletos.modelo.Usuario;
import sistemaboletos.vista.FrameLogin;
import sistemaboletos.vista.FrameRegistrar;


public class ControladorRegistrar implements ActionListener {
    
    private FrameRegistrar vista;
    private UsuariosDAO dao;
    
    public ControladorRegistrar(FrameRegistrar vista, UsuariosDAO dao) {
        this.vista = vista;
        this.dao = dao;
        
        this.vista.getBtnRegistrar().addActionListener(this);
        this.vista.getBtnLogin().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnRegistrar()) {
            try {
                ejecutarRegistro();
            } catch (SQLException ex) {
                System.err.println("Ocurrio un error" + ex);
            }
        } else if (e.getSource() == vista.getBtnLogin()) {
            vista.dispose();
              
                FrameLogin frameLogin = new FrameLogin();
                ControladorLogin ctrlLogin = new ControladorLogin(frameLogin, dao);
                frameLogin.setLocationRelativeTo(null);
                frameLogin.setVisible(true);
        }
    }
    
    public void ejecutarRegistro() throws SQLException {
        String user = vista.getTfUsuario().getText().trim();
        String email = vista.getTfEmail().getText().trim().toLowerCase();
        String telf1 = String.valueOf(vista.getJcTelefono().getSelectedItem());
        String telf2 = vista.getTfTelefono().getText().trim();
        String telf = telf1 + telf2;
        String pass = new String(vista.getJpPass().getPassword());
        
        if (user.isEmpty() || email.isEmpty() || telf2.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Usuario usuarioIntento = new Usuario();
        usuarioIntento.setUser(user);
        usuarioIntento.setEmail(email);
        
        
        usuarioIntento.setTelf(telf);
        usuarioIntento.setPass(pass);
        
       try {
            
            boolean RegistroExitoso = dao.RegistrarUsuario(usuarioIntento);
            if (RegistroExitoso) {
                JOptionPane.showMessageDialog(vista, "¡Registro Exitoso! Bienvenido.");
               
                vista.dispose();
              
                FrameLogin frameLogin = new FrameLogin();
                ControladorLogin ctrlLogin = new ControladorLogin(frameLogin, dao);
                frameLogin.setVisible(true);
            }
        } catch (SQLException ex) {
            // Manejo de errores si se cae la conexión a MariaDB/MySQL
            JOptionPane.showMessageDialog(vista, "Error de conexión con la base de datos: " + ex.getMessage(), "Error de Sistema", JOptionPane.ERROR_MESSAGE);
        }
    }
}
