package sistemaboletos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sistemaboletos.conexion.ConexionBD;
import sistemaboletos.modelo.Transporte;

public class TransportesDAO {
    
    public List<Transporte> obtener_Transportes(Connection con) throws SQLException {
        
        // Creacion de lista vacia para guardar los objetos
        
        List<Transporte> listaTransportes = new ArrayList<>();
        
        // Consulta SQL
        String SELECCIONAR = "SELECT id_transporte, matricula FROM TRANSPORTES;";
        
        try (PreparedStatement ps = con.prepareStatement(SELECCIONAR);
                ResultSet rs = ps.executeQuery()) {
            
            // Este bucle recorre toda la tabla obteniendo todos los
            // resultados fila por fila
            
            while (rs.next()) {
                // Se extraen los datos de MySql
                int id_transporte = rs.getInt("id_transporte");
                String matricula = rs.getString("matricula");
                
                // Se transforman los datos obtenidos en objetos
                Transporte transporteExtraido = new Transporte(id_transporte, matricula);
                
                // Se añade el nuebvo objeto a la lista
                listaTransportes.add(transporteExtraido);
            }
        } catch (SQLException e) {
            System.err.println("Error al selecionar tabla: "+e.getMessage());
        }
        return listaTransportes;
          
    }
    
    public boolean insertar_Transporte(Connection con, Transporte transporte) throws SQLException {

        String INSERT = "INSERT into TRANSPORTES (matricula) values (?)";

        try (PreparedStatement ps = con.prepareStatement(INSERT)) {

            //Asignación segura de valores
            ps.setString(1, transporte.getMatricula());
            // Ejecutar la actualización
            ps.executeUpdate();

            System.out.println("Se registro el transporte exitosamente.");

            return true;

        } catch (SQLException e) {

            System.err.println("Error al registrar transporte: " + e.getMessage());
            return false;
        }
    }
}
