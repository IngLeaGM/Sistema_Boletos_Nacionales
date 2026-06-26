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
    
    public ArrayList<Transporte> obtener_Transportes(Connection con) throws SQLException {
        
        // Creacion de lista vacia para guardar los objetos
        
        ArrayList<Transporte> listaTransportes = new ArrayList<>();
        
        // Consulta SQL
        String SELECCIONAR = "SELECT id_transporte, modelo, anio_vehiculo, matricula, tipo_combustible FROM TRANSPORTES;";
        
        try (PreparedStatement ps = con.prepareStatement(SELECCIONAR);
                ResultSet rs = ps.executeQuery()) {
            
            // Este bucle recorre toda la tabla obteniendo todos los
            // resultados fila por fila
            
            while (rs.next()) {
                // Se extraen los datos de MySql
                int id_transporte = rs.getInt("id_transporte");
                String modelo = rs.getString("modelo");
                String matricula = rs.getString("matricula");
                int anio_vehiculo = rs.getInt("anio_vehiculo");
                String tipo_combustible = rs.getString("tipo_combustible");
                
                // Se transforman los datos obtenidos en objetos
                Transporte transporteExtraido = new Transporte(id_transporte, modelo, anio_vehiculo, matricula, tipo_combustible);
                
                // Se añade el nuebvo objeto a la lista
                listaTransportes.add(transporteExtraido);
            }
        } catch (SQLException e) {
            System.err.println("Error al selecionar tabla: "+e.getMessage());
        }
        return listaTransportes;
          
    }
    
    public boolean insertar_Transporte(Connection con, Transporte transporte) throws SQLException {

        String INSERT = "INSERT into TRANSPORTES (modelo, anio_vehiculo, matricula, tipo_combustible) values (?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(INSERT)) {

            //Asignación segura de valores
            ps.setString(1, transporte.getModelo());
            ps.setInt(2, transporte.getAnio_vehiculo());
            ps.setString(3, transporte.getMatricula());
            ps.setString(4, transporte.getTipo_combustible());
            // Ejecutar la actualización
            ps.executeUpdate();

            System.out.println("Se registro el transporte exitosamente.");

            return true;

        } catch (SQLException e) {

            System.err.println("Error al registrar transporte: " + e.getMessage());
            return false;
        }
    }
    
    public boolean actualizarDatos(Connection con, Transporte transporte) throws SQLException {
        
        // Consulta SQL
        String ACTUALIZAR = "UPDATE TRANSPORTES SET modelo = ?, anio = ?, matricula = ?, combustible = ? WHERE id_transporte = ?;";
        
        try (PreparedStatement ps = con.prepareStatement(ACTUALIZAR)) {
        
            //Asignación segura de valores
            ps.setString(1, transporte.getModelo());
            ps.setInt(2, transporte.getAnio_vehiculo());
            ps.setString(3, transporte.getMatricula());
            ps.setString(4, transporte.getTipo_combustible());
            ps.setInt(5, transporte.getId_transporte());

            // Ejecutar la actualización
            ps.executeUpdate();
            
            return true;
        
        } catch (SQLException e) {
         
        System.err.println("Error al actulizar datos de Transporte: " + e.getMessage());
        return false;
        }
    }
    
    public boolean eliminarTransporte(Connection con, int id_transporte) {
        
        String DELETE = "DELETE FROM TRANSPORTES WHERE id_transporte = ?";
        
        PreparedStatement ps = null;
        
        try {
            ps = con.prepareStatement(DELETE);
            ps.setInt(1, id_transporte);
            
            // executeUpdate devuelve el número de filas afectadas
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas >= 1) {
                System.out.println("Se borro el transporte correctamente");
                return true;
            } else {
                return false;
            }
            // Si afectó al menos 1 fila, significa que se borró con éxito
            
        } catch (SQLException e) {
            System.err.println("Error al intentar eliminar el transporte con ID " + id_transporte + ": " + e.getMessage());
            return false;
        }
    }
}
