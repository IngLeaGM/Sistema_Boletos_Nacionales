package sistemaboletos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sistemaboletos.conexion.ConexionBD;
import sistemaboletos.modelo.Boleto;


public class BoletoDAO {
    
    public List<Boleto> obtenerTodosLosBoletos() throws SQLException {
        
        // Creacion de lista vacia para guardar los objetos
        
        List<Boleto> listaBoletos = new ArrayList<>();
        
        // Consulta SQL
        String SELECCIONAR = "SELECT id_boleto, nombre FROM Boletos";
        
        try (Connection con = ConexionBD.getConexion();
                PreparedStatement ps = con.prepareStatement(SELECCIONAR);
                ResultSet rs = ps.executeQuery()) {
            
            // Este bucle recorre toda la tabla obteniendo todos los
            // resultados fila por fila
            
            while (rs.next()) {
                // Se extraen los datos de MySql
                int id_boleto = rs.getInt("id_boleto");
                String nombre = rs.getString("nombre");
                
                // Se transforman los datos obtenidos en objetos
                Boleto boletoExtraido = new Boleto(id_boleto, nombre);
                
                // Se añade el nuebvo objeto a la lista
                listaBoletos.add(boletoExtraido);
            }
        } catch (SQLException e) {
            System.err.println("Error al selecionar tabla: "+e.getMessage());
        }
        return listaBoletos;
        
    }
}
