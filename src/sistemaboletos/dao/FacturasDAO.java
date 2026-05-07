package sistemaboletos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sistemaboletos.conexion.ConexionBD;
import sistemaboletos.modelo.Boleto;
import sistemaboletos.modelo.Factura;

public class FacturasDAO {
    
    public List<Factura> obtener_facturas_usuario(Factura factura) throws SQLException {
        
        // Creacion de lista vacia para guardar los objetos
        
        List<Factura> listaFacturas = new ArrayList<>();
        
        // Consulta SQL
        String SELECCIONAR = "SELECT  id_factura, usuario_id, monto_total, metodo_pago, fecha FROM FACTURAS WHERE usuario_id = ?";
        
        try (Connection con = ConexionBD.getConexion();
                PreparedStatement ps = con.prepareStatement(SELECCIONAR)) {
           
                ps.setInt(1, factura.getId_usuario());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                // Se extraen los datos de MySql
                int id_factura = rs.getInt("id_factura");
                int usuario_id = rs.getInt("usuario_id");
                double monto_total = rs.getFloat("monto_total");
                String metodo_pago = rs.getString("metodo_pago");
                String fecha = rs.getString("fecha");
                
                // Se transforman los datos obtenidos en objetos
                Factura facturaExtraida = new Factura(id_factura, usuario_id, monto_total, metodo_pago, fecha);
                
                // Se añade el nuebvo objeto a la lista
                listaFacturas.add(facturaExtraida);
            }
            }
        } catch (SQLException e) {
            System.err.println("Error al selecionar tabla: "+e.getMessage());
        }
        return listaFacturas;
    }
    
    public List<Factura> obtener_facturas() throws SQLException {
        
        // Creacion de lista vacia para guardar los objetos
        
        List<Factura> listaFacturas = new ArrayList<>();
        
        // Consulta SQL
        String SELECCIONAR = "SELECT * FROM FACTURAS";
        
        try (Connection con = ConexionBD.getConexion();
                PreparedStatement ps = con.prepareStatement(SELECCIONAR);
                ResultSet rs = ps.executeQuery()) {
            
            // Este bucle recorre toda la tabla obteniendo todos los
            // resultados fila por fila
            
            while (rs.next()) {
                // Se extraen los datos de MySql
                int id_factura = rs.getInt("id_factura");
                int usuario_id = rs.getInt("usuario_id");
                double monto_total = rs.getFloat("monto_total");
                String metodo_pago = rs.getString("metodo_pago");
                String fecha = rs.getString("fecha");
                
                // Se transforman los datos obtenidos en objetos
                Factura facturaExtraida = new Factura(id_factura, usuario_id, monto_total, metodo_pago, fecha);
                
                // Se añade el nuebvo objeto a la lista
                listaFacturas.add(facturaExtraida);
            }
        } catch (SQLException e) {
            System.err.println("Error al selecionar tabla: "+e.getMessage());
        }
        return listaFacturas;
    }
    
    public void insertar_factura (Factura factura) throws SQLException {
        
        String INSERT = "INSERT into FACTURAS (usuario_id, monto_total, metodo_pago) values (?, ?, ?)";
        
        try (Connection con = ConexionBD.getConexion();
        PreparedStatement ps = con.prepareStatement(INSERT)) {
        
            //Asignación segura de valores
            ps.setInt(1, factura.getId_usuario());
            ps.setDouble(2, factura.getMonto_total());
            ps.setString(3, factura.getMetodo_pago());
            // Ejecutar la actualización
            ps.executeUpdate();
            
            System.out.println("Se registro la factura exitosamente.");
        
        } catch (SQLException e) {
         
        System.err.println("Error al registrar factura: " + e.getMessage());
        throw e; // Re-lanzar para que el Controlador pueda avisar a la Vista
        }
    }
}
