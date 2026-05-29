package sistemaboletos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sistemaboletos.modelo.Boleto;


public class BoletosDAO {
    
    public List<Boleto> obtenerTodosLosBoletos(Connection con) throws SQLException {
        
        // Creacion de lista vacia para guardar los objetos
        
        List<Boleto> listaBoletos = new ArrayList<>();
        
        // Consulta SQL
        String SELECCIONAR = "SELECT * FROM BOLETOS";
        
        try (PreparedStatement ps = con.prepareStatement(SELECCIONAR);
                ResultSet rs = ps.executeQuery()) {
            
            // Este bucle recorre toda la tabla obteniendo todos los
            // resultados fila por fila
            
            while (rs.next()) {
                // Se extraen los datos de MySql
                int id_boleto = rs.getInt("id_boleto");
                int id_viaje = rs.getInt("id_viaje");
                int id_factura = rs.getInt("id_factura");
                String nom_pasajero = rs.getString("nom_pasajero");
                int cedula = rs.getInt("cedula");
                int telefono = rs.getInt("telefono");
                String asiento = rs.getString("asiento");
                
                // Se transforman los datos obtenidos en objetos
                Boleto boletoExtraido = new Boleto(id_boleto, id_viaje, id_factura, nom_pasajero, cedula, telefono, asiento);
                
                // Se añade el nuevo objeto a la lista
                listaBoletos.add(boletoExtraido);
            }
        } catch (SQLException e) {
            System.err.println("Error al selecionar tabla: "+e.getMessage());
        }
        return listaBoletos;
          
    }
    
    public List<Boleto> obtenerBoletosFactura(Connection con, int id_facturaBuscada) throws SQLException {
        
        // Creacion de lista vacia para guardar los objetos
        
        List<Boleto> listaBoletos = new ArrayList<>();
        
        // Consulta SQL
        String SELECCIONAR = "SELECT * FROM BOLETOS WHERE id_factura=?";
        
        try (PreparedStatement ps = con.prepareStatement(SELECCIONAR)) {
           
                ps.setInt(1, id_facturaBuscada);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                // Se extraen los datos de MySql
                int id_boleto = rs.getInt("id_boleto");
                int id_viaje = rs.getInt("id_viaje");
                int id_factura = rs.getInt("id_factura");
                String nom_pasajero = rs.getString("nom_pasajero");
                int cedula = rs.getInt("cedula");
                int telefono = rs.getInt("telefono");
                String asiento = rs.getString("asiento");
                
                // Se transforman los datos obtenidos en objetos
                Boleto boletoExtraido = new Boleto(id_boleto, id_viaje, id_factura, nom_pasajero, cedula, telefono, asiento);
                
                // Se añade el nuevo objeto a la lista
                listaBoletos.add(boletoExtraido);
            }
            }
        } catch (SQLException e) {
            System.err.println("Error al selecionar tabla: "+e.getMessage());
        }
        return listaBoletos;
    }
     
    // Este metodo esta diseñado tanto para insertar un solo boleto, como insertar varios al mismo tiempo.
     public boolean insertarBoletos(Connection con, List<Boleto> listaBoletos) {
            String INSERT = "INSERT INTO BOLETOS (id_viaje, id_factura, nom_pasajero, cedula, telefono, asiento) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = con.prepareStatement(INSERT)) {
            
            // En esta linea se desactiva el auto guardado por que puede ocacionar problemas
            // con este proceso.
            con.setAutoCommit(false); 
            
            // Iteramos la lista de objetos que se añádiran a la consulta
            for (Boleto boleto : listaBoletos) {
                ps.setInt(1, boleto.getId_viaje());
                ps.setInt(2, boleto.getId_factura());
                ps.setString(3, boleto.getNom_pasajero());
                ps.setInt(4, boleto.getCedula());
                ps.setInt(5, boleto.getTelefono());
                ps.setString(6, boleto.getAsiento());
                
                ps.addBatch(); 
            }
            
            // Se ejecutan todos los INSERTs.
            ps.executeBatch();
            
            // Se confirman los cambios en la base de datos.
            con.commit();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al insertar lote de boletos: " + e.getMessage());
     
            return false;
        }
            
        }
           
}
