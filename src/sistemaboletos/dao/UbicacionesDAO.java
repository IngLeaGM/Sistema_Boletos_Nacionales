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

    public boolean eliminarUbicacion(Connection con, int id_ubicacion) {
        
        String DELETE = "DELETE FROM UBICACIONES WHERE id_ubicacion = ?";
        
        PreparedStatement ps = null;
        
        try {
            ps = con.prepareStatement(DELETE);
            ps.setInt(1, id_ubicacion);
            
            // executeUpdate devuelve el número de filas afectadas
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas >= 1) {
                System.out.println("Se borro la ubicacion correctamente");
                return true;
            } else {
                return false;
            }
            // Si afectó al menos 1 fila, significa que se borró con éxito
            
        } catch (SQLException e) {
            System.err.println("Error al intentar eliminar la ubicacion con ID " + id_ubicacion + ": " + e.getMessage());
            return false;
        }
    }
}
