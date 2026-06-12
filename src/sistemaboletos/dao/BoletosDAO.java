package sistemaboletos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sistemaboletos.modelo.Boleto;
import sistemaboletos.modelo.BoletoDetalle;
import sistemaboletos.modelo.BoletoInformacion;


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
                String telefono = rs.getString("telefono");
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
                String telefono = rs.getString("telefono");
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
    
    // Este metodo se encarga de cargar los datos para la tabla que se muestra en el FrameMisBoletos
    public List<BoletoDetalle> obtenerBoletosUsuario(Connection con, int id_usuario) throws SQLException {
        
        // Creacion de lista vacia para guardar los objetos
        
        List<BoletoDetalle> listaBoletos = new ArrayList<>();
        
        // Consulta SQL
        String SELECCIONAR = "SELECT b.id_boleto, b.asiento, v.fecha_salida, b.nom_pasajero " +
                                "FROM BOLETOS b " +
                                "INNER JOIN FACTURAS f ON b.id_factura = f.id_factura " +
                                "INNER JOIN VIAJES v ON b.id_viaje = v.id_viaje " +
                                "WHERE f.usuario_id = ?;";
        
        try (PreparedStatement ps = con.prepareStatement(SELECCIONAR)) {
           
                ps.setInt(1, id_usuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Se extraen los datos de MySql
                    int id_boleto = rs.getInt("id_boleto");
                    String asiento = rs.getString("asiento");
                    String fecha_salida = rs.getString("fecha_salida");
                    String nom_pasajero = rs.getString("nom_pasajero");
                        System.out.println(asiento);

                    // Se transforman los datos obtenidos en objetos
                    BoletoDetalle boletoDetalle = new BoletoDetalle(id_boleto, asiento, fecha_salida, nom_pasajero);

                    // Se añade el nuevo objeto a la lista
                    listaBoletos.add(boletoDetalle);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al selecionar tabla: "+e.getMessage());
        }
        return listaBoletos;
    }
     
    // Este metodo se encargar de cargar mas informacion del boleto seleccionado en la tabla que esta ubicada en FrameMisBoletos
    public BoletoInformacion obtenerInformacion(Connection con, int id_boleto) {
        
        BoletoInformacion informacion = new BoletoInformacion();
        
        // Consulta SQL
        String SELECCIONAR = "SELECT b.nom_pasajero, b.cedula, salida.nombre AS salida, destino.nombre AS destino, b.asiento, v.precio_x_asiento " +
                                "FROM BOLETOS b INNER JOIN FACTURAS f ON b.id_factura = f.id_factura INNER JOIN VIAJES v ON b.id_viaje = v.id_viaje " +
                                "INNER JOIN UBICACIONES salida ON v.id_salida = salida.id_ubicacion " +
                                "INNER JOIN UBICACIONES destino ON destino.id_ubicacion = v.id_destino WHERE b.id_boleto = ?;";
        
        try (PreparedStatement ps = con.prepareStatement(SELECCIONAR)) {
           
                ps.setInt(1, id_boleto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Se extraen los datos de MySql
                    // id_boleto no se extrae de la consulta porque ese valor ya se obtiene antes de ejecutar esta funcion.
                    String nom_pasajero = rs.getString("nom_pasajero");
                    String cedula = rs.getString("cedula");
                    String salida = rs.getString("salida");
                    String destino = rs.getString("destino");
                    String asiento = rs.getString("asiento");
                    double precio_x_asiento = rs.getDouble("precio_x_asiento");
                        System.out.println("nom_pasajero");

                    // Se transforman los datos obtenidos en objetos
                    informacion = new BoletoInformacion(id_boleto, nom_pasajero, cedula, salida, destino, asiento, precio_x_asiento);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al selecionar tabla: "+e.getMessage());
        }
        return informacion;
    }
    // Este metodo esta diseñado tanto para insertar un boleto o insertar varios al mismo tiempo.
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
                ps.setString(5, boleto.getTelefono());
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
