package sistemaboletos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import sistemaboletos.conexion.ConexionBD;
import sistemaboletos.modelo.Factura;
import sistemaboletos.modelo.FacturaTabla;
import sistemaboletos.modelo.Usuario;

public class FacturasDAO {
    
    // Este metodo depende del modelo usuario para funcionar.
    public List<Factura> obtener_facturas_usuario(Connection con, int idBuscado) throws SQLException {
        
        // Creacion de lista vacia para guardar los objetos
        
        List<Factura> listaFacturas = new ArrayList<>();
        
        // Consulta SQL
        String SELECCIONAR = "SELECT  id_factura, usuario_id, monto_total, metodo_pago, fecha FROM FACTURAS WHERE usuario_id = ?";
        
        try (PreparedStatement ps = con.prepareStatement(SELECCIONAR)) {
           
                ps.setInt(1, idBuscado);
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
    
    public List<Factura> obtener_facturas(Connection con) throws SQLException {
        
        // Creacion de lista vacia para guardar los objetos
        
        List<Factura> listaFacturas = new ArrayList<>();
        
        // Consulta SQL
        String SELECCIONAR = "SELECT * FROM FACTURAS";
        
        try (PreparedStatement ps = con.prepareStatement(SELECCIONAR);
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
    
    public List<FacturaTabla> obtener_facturasTabla(Connection con) throws SQLException {
        
        // Creacion de lista vacia para guardar los objetos
        
        List<FacturaTabla> listaFacturas = new ArrayList<>();
        
        // Consulta SQL
        String SELECCIONAR = "SELECT f.id_factura, u.user, f.monto_total, f.metodo_pago, f.fecha FROM FACTURAS f " +
                                "INNER JOIN USUARIOS u WHERE f.usuario_id = u.id_usuario;";
        
        try (PreparedStatement ps = con.prepareStatement(SELECCIONAR);
                ResultSet rs = ps.executeQuery()) {
            
            // Este bucle recorre toda la tabla obteniendo todos los
            // resultados fila por fila
            
            while (rs.next()) {
                // Se extraen los datos de MySql
                int id_factura = rs.getInt("id_factura");
                String user = rs.getString("user");
                double monto_total = rs.getFloat("monto_total");
                String metodo_pago = rs.getString("metodo_pago");
                String fecha = rs.getString("fecha");
                
                // Se transforman los datos obtenidos en objetos
                FacturaTabla facturaExtraida = new FacturaTabla(id_factura, user, monto_total, metodo_pago, fecha);
                
                // Se añade el nuebvo objeto a la lista
                listaFacturas.add(facturaExtraida);
            }
        } catch (SQLException e) {
            System.err.println("Error al selecionar tabla: "+e.getMessage());
        }
        return listaFacturas;
    }
    
    public int insertar_factura (Connection con, Factura factura) throws SQLException {
        
        int id_generado = 0;
        
        String INSERT = "INSERT into FACTURAS (usuario_id, monto_total, metodo_pago) values (?, ?, ?)";
        
        try (PreparedStatement ps = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
        
            //Asignación segura de valores
            ps.setInt(1, factura.getId_usuario());
            ps.setDouble(2, factura.getMonto_total());
            ps.setString(3, factura.getMetodo_pago());
            // Ejecutar la actualización
            ps.executeUpdate();
            
    
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if(rs.next()) {
                    System.out.println("Se registro la factura exitosamente.");
                    id_generado = rs.getInt(1);
                }
                return id_generado;
            }
            

        } catch (SQLException e) {
         
            System.err.println("Error al registrar factura: " + e.getMessage());
            return 0;
        }
    }
      
    public Factura obtener_Factura(Connection con, int id_factura_buscada) {
        
        Factura facturaExtraida = new Factura();
        
        String sql = "SELECT * FROM FACTURAS WHERE id_factura=?";

        try {
            
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, id_factura_buscada);
            System.out.println("Id factura:" + id_factura_buscada);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                int id_factura = rs.getInt("id_factura");
                int usuario_id = rs.getInt("usuario_id");
                double monto_total = rs.getFloat("monto_total");
                String metodo_pago = rs.getString("metodo_pago");
                String fecha = rs.getString("fecha");
                    
                    facturaExtraida = new Factura(id_factura, usuario_id, monto_total, metodo_pago, fecha);
                }
                
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar viaje: " + e.getMessage());
        }
     
        return facturaExtraida;
    }
}
