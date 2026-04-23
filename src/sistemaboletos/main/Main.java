
package sistemaboletos.main;

import java.sql.SQLException;
import java.util.List;
import sistemaboletos.dao.BoletoDAO;
import sistemaboletos.modelo.Boleto;

public class Main {
    
    public static void main(String[] args) throws SQLException {
        
        BoletoDAO dao = new BoletoDAO();
        
        List<Boleto> misBoletos = dao.obtenerTodosLosBoletos();
        
        for (Boleto ev : misBoletos) {
            System.out.println("ID: "+ev.getId_Boleto()+", Nombre Completo: "+ev.getNombre());
        }
        
     
    }
    
}
