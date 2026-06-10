package sistemaboletos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sistemaboletos.conexion.ConexionBD;
import sistemaboletos.modelo.Ubicacion;

public class UbicacionesDAO {
    
    public ArrayList<Ubicacion> obtener_ubicaciones(Connection con) throws SQLException {
        // Creacion de lista vacia para guardar los objetos
        
        ArrayList<Ubicacion> listaUbicaciones = new ArrayList<>();
        
        // Consulta SQL
        String SELECCIONAR = "SELECT id_ubicacion, nombre FROM UBICACIONES;";
        
        
        try (PreparedStatement ps = con.prepareStatement(SELECCIONAR);
                ResultSet rs = ps.executeQuery()) {
            
            // Este bucle recorre toda la tabla obteniendo todos los
            // resultados fila por fila
            
            while (rs.next()) {
                // Se extraen los datos de MySql
                int id_ubicacion = rs.getInt("id_ubicacion");
                String nombre = rs.getString("nombre");
                
                // Se transforman los datos obtenidos en objetos
                Ubicacion ubicacionExtraida = new Ubicacion(id_ubicacion, nombre);
                
                // Se añade el nuebvo objeto a la lista
                listaUbicaciones.add(ubicacionExtraida);
            }
        } catch (SQLException e) {
            System.err.println("Error al selecionar tabla: "+e.getMessage());
        }
        return listaUbicaciones;
    }
    
    public boolean insertar_Ubicacion(Connection con, Ubicacion ubicacion) throws SQLException {
        
        String INSERT = "INSERT into UBICACIONES (nombre) values (?)";

        try (PreparedStatement ps = con.prepareStatement(INSERT)) {

            //Asignación segura de valores
            ps.setString(1, ubicacion.getNombre());
            // Ejecutar la actualización
            ps.executeUpdate();

            System.out.println("Se registro la ubicacion exitosamente.");
            
            return true;

        } catch (SQLException e) {

            System.err.println("Error al registrar factura: " + e.getMessage());
            
            return false;
        }
    }

}
