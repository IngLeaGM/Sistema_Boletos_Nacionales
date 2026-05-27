package sistemaboletos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sistemaboletos.conexion.ConexionBD;
import sistemaboletos.modelo.Viaje;

public class ViajesDAO {
    
    public List<Viaje> obtener_Viajes(Connection con) throws SQLException {
        
        // Creacion de lista vacia para guardar los objetos
        
        List<Viaje> listaViajes = new ArrayList<>();
        
        // Consulta SQL
        String SELECCIONAR = "SELECT id_viaje, id_salida, id_destino, transporte_id, fecha_salida, precio_x_asiento FROM VIAJES;";
        
        try (PreparedStatement ps = con.prepareStatement(SELECCIONAR);
                ResultSet rs = ps.executeQuery()) {
            
            // Este bucle recorre toda la tabla obteniendo todos los
            // resultados fila por fila
            
            while (rs.next()) {
                // Se extraen los datos de MySql
                int id_viaje = rs.getInt("id_viaje");
                int id_salida = rs.getInt("id_salida");
                int id_destino = rs.getInt("id_destino");
                int transporte_id = rs.getInt("transporte_id");
                String fecha_salida = rs.getString("fecha_salida");
                double precio_x_asiento = rs.getDouble("precio_x_asiento");
                
                // Se transforman los datos obtenidos en objetos
                Viaje ViajeExtraido = new Viaje(id_viaje, id_salida, id_destino, transporte_id, fecha_salida, precio_x_asiento);
                
                // Se añade el nuebvo objeto a la lista
                listaViajes.add(ViajeExtraido);
            }
        } catch (SQLException e) {
            System.err.println("Error al selecionar tabla: "+e.getMessage());
        }
        return listaViajes;
          
    }
    
    public boolean insertar_Viaje(Connection con, Viaje viaje) throws SQLException {

        String INSERT = "INSERT into VIAJES (id_salida, id_destino, transporte_id, fecha_salida, precio_x_asiento) values (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(INSERT)) {

            //Asignación segura de valores
            ps.setInt(1, viaje.getId_salida());
            ps.setInt(2, viaje.getId_destino());
            ps.setInt(3, viaje.getTransporte_id());
            ps.setString(4, viaje.getFecha());
            ps.setDouble(5, viaje.getPrecio_x_asiento());
            // Ejecutar la actualización
            ps.executeUpdate();

            System.out.println("Se registro el viaje exitosamente.");

            return true;

        } catch (SQLException e) {

            System.err.println("Error al registrar viaje: " + e.getMessage());
            return false;
        }
    }
}
