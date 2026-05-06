
package sistemaboletos.main;

import java.sql.SQLException;
import java.util.List;
import sistemaboletos.dao.BoletoDAO;
import sistemaboletos.modelo.Boleto;
import sistemaboletos.modelo.Usuario;
import sistemaboletos.dao.UsuariosDAO;

public class Main {
    
    public static void main(String[] args) throws SQLException {
        
         Usuario nuevoUsuario = new Usuario("LeaGM", "gutierrez@gmail.com", "Lean06dro.", "Leandro", "Gutierrez", "31567096", "04120706590");
     
         UsuariosDAO uDAO = new UsuariosDAO();
         
         uDAO.LoginUsuario(nuevoUsuario);
    }
    
}
