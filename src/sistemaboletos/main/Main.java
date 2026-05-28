
package sistemaboletos.main;

import java.sql.SQLException;
import java.util.List;
import sistemaboletos.controlador.ControladorLogin;
import sistemaboletos.dao.BoletosDAO;
import sistemaboletos.modelo.Boleto;
import sistemaboletos.modelo.Usuario;
import sistemaboletos.dao.UsuariosDAO;
import sistemaboletos.vista.FrameLogin;

public class Main {
    
    public static void main(String[] args) {
        
       FrameLogin vistaLogin = new FrameLogin(); 
       UsuariosDAO daoUsuarios = new UsuariosDAO();
       
       ControladorLogin ctrlLogin = new ControladorLogin(vistaLogin, daoUsuarios);
       
       vistaLogin.setLocationRelativeTo(null);
       vistaLogin.setVisible(true);
    }
    
}
