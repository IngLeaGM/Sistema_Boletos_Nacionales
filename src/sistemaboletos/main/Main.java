package sistemaboletos.main;

import sistemaboletos.controlador.ControladorLogin;
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
