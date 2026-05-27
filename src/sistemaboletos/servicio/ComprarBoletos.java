package sistemaboletos.servicio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import sistemaboletos.conexion.ConexionBD;
import sistemaboletos.dao.BoletosDAO;
import sistemaboletos.dao.FacturasDAO;
import sistemaboletos.modelo.Boleto;
import sistemaboletos.modelo.Factura;

public class ComprarBoletos {
    
    private FacturasDAO facturasDAO = new FacturasDAO();
    private BoletosDAO boletosDAO = new BoletosDAO();
    
public boolean ProcesarComprarCompleta(Factura Factura, List<Boleto> Boleto) throws SQLException {
    
    Connection con = null;
    
    
    try {
        con = ConexionBD.getConexion();
        
        return true;
    } catch (SQLException e) {
        return false;
    }
}
    
}
