package sistemaboletos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import sistemaboletos.conexion.ConexionBD;
import sistemaboletos.modelo.BoletoDetalle;
import sistemaboletos.modelo.Ubicacion;
import sistemaboletos.modelo.Viaje;
import sistemaboletos.modelo.ViajeInformacion;

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
    
    public ArrayList<Ubicacion> listaUbicacionesDisponibles(Connection con) {
        ArrayList<Ubicacion> lista = new ArrayList<>();
        
        // Consulta uniendo id_ubicacion con el id_destino de viajes
        String sql = "SELECT DISTINCT u.id_ubicacion, u.nombre " +
                     "FROM UBICACIONES u " +
                     "INNER JOIN VIAJES v ON u.id_ubicacion = v.id_salida";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Creamos el objeto con los datos de la BD
                Ubicacion ub = new Ubicacion(
                    rs.getInt("id_ubicacion"),
                    rs.getString("nombre")
                );
                lista.add(ub);
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar ubicaciones disponibles: " + e.getMessage());
        }
        for ( Ubicacion ub : lista) {
            System.out.println(ub.getNombre());
        }
        return lista;
    }
    
    public ArrayList<Ubicacion> listaDestinos(Connection con, Ubicacion ubiSalida) {
         ArrayList<Ubicacion> lista = new ArrayList<>();
        
        // Consulta uniendo id_ubicacion con el id_destino de viajes
        String sql = "SELECT DISTINCT u.id_ubicacion, u.nombre " +
                "FROM UBICACIONES u INNER JOIN VIAJES v " +
                "ON u.id_ubicacion=v.id_destino AND v.id_salida = ?;";

        try {
           
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, ubiSalida.getId_ubicacion());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Creamos el objeto con los datos de la BD
                Ubicacion ub = new Ubicacion(
                    rs.getInt("id_ubicacion"),
                    rs.getString("nombre")
                );
                lista.add(ub);
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar destinos: " + e.getMessage());
        }
        for ( Ubicacion ub : lista) {
            System.out.println(ub.getNombre());
        }
        return lista;
    }
    
    public ArrayList<Viaje> listaFechas(Connection con, Ubicacion ubiSalida, Ubicacion ubiDestino) {
        ArrayList<Viaje> lista = new ArrayList<>();
        
        String sql = "SELECT id_viaje, DATE_FORMAT(fecha_salida, '%d/%m/%Y %h:%i %p') AS fecha_formateada " +
                 "FROM VIAJES WHERE id_salida = ? AND id_destino = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, ubiSalida.getId_ubicacion());
            ps.setInt(2, ubiDestino.getId_ubicacion());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Creamos el objeto con los datos de la BD
                Viaje viaje = new Viaje(
                    rs.getInt("id_viaje"),
                    rs.getString("fecha_formateada")
                );
                lista.add(viaje);
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar fechas: " + e.getMessage());
        }
        for (Viaje viaje : lista) {
            System.out.println(viaje.getFecha());
        }
        return lista;
    }
    
    public Viaje obtener_viajeCompra(Connection con, Viaje viaje) throws SQLException, ParseException {
        
        String sql = "SELECT * FROM VIAJES WHERE id_viaje=?";

        try {
            
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, viaje.getId_viaje());
            System.out.println("Id salida:" + viaje.getId_viaje());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id_viaje = rs.getInt("id_viaje");
                    int id_salida = rs.getInt("id_salida");
                    int id_destino = rs.getInt("id_destino");
                    int transporte_id = rs.getInt("transporte_id");
                    String fecha_salida = rs.getString("fecha_salida");
                    Double precio_x_asiento = rs.getDouble("precio_x_asiento");
                    
                    viaje = new Viaje(id_viaje, id_salida, id_destino, transporte_id, fecha_salida, precio_x_asiento);
                }
                
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar viaje: " + e.getMessage());
        }
     
        return viaje;
    }
    
    public List<ViajeInformacion> ObtenerViajesInformacion(Connection con) {
    // Creacion de lista vacia para guardar los objetos
        
        List<ViajeInformacion> listaViajes = new ArrayList<>();
        
        // Consulta SQL
        String SELECCIONAR = "SELECT v.id_viaje, u_salida.nombre AS ciudad_salida, u_destino.nombre AS ciudad_destino, v.fecha_salida, v.precio_x_asiento, ve.matricula " +
                                "FROM VIAJES v " +
                                "INNER JOIN UBICACIONES u_salida ON v.id_salida = u_salida.id_ubicacion " +
                                "INNER JOIN UBICACIONES u_destino ON v.id_destino = u_destino.id_ubicacion " +
                                "INNER JOIN TRANSPORTES ve ON v.transporte_id = ve.id_transporte;";
        
        try (PreparedStatement ps = con.prepareStatement(SELECCIONAR)) {
           
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Se extraen los datos de MySql
                    int id_viaje = rs.getInt("id_viaje");
                    String ciudad_salida = rs.getString("ciudad_salida");
                    String ciudad_destino = rs.getString("ciudad_destino");
                    String fecha_salida = rs.getString("fecha_salida");
                    double precio_x_asiento = rs.getDouble("precio_x_asiento");
                    String matricula = rs.getString("matricula");
                    

                    // Se transforman los datos obtenidos en objetos
                    ViajeInformacion viajeInformacion = new ViajeInformacion(id_viaje, ciudad_salida, ciudad_destino, fecha_salida, precio_x_asiento, matricula);

                    // Se añade el nuevo objeto a la lista
                    listaViajes.add(viajeInformacion);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al selecionar tabla: "+e.getMessage());
        }
        return listaViajes;
    }
    
    public ViajeInformacion obtenerViajeInformacion (Connection con , Viaje viaje) {
        
        ViajeInformacion viajeInformacion = new ViajeInformacion();
        
        String sql = "SELECT u_salida.nombre AS ciudad_salida, u_destino.nombre AS ciudad_destino, v.fecha_salida, v.precio_x_asiento, ve.matricula " +
                                "FROM VIAJES v " +
                                "INNER JOIN UBICACIONES u_salida ON v.id_salida = u_salida.id_ubicacion " +
                                "INNER JOIN UBICACIONES u_destino ON v.id_destino = u_destino.id_ubicacion " +
                                "INNER JOIN TRANSPORTES ve ON v.transporte_id = ve.id_transporte WHERE v.id_viaje = ?";

        try {
            
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, viaje.getId_viaje());
            System.out.println("Id viaje:" + viaje.getId_viaje());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id_viaje = viaje.getId_viaje();
                    String ciudad_salida = rs.getString("ciudad_salida");
                    String ciudad_destino = rs.getString("ciudad_destino");
                    String fecha_salida = rs.getString("fecha_salida");
                    double precio_x_asiento = rs.getDouble("precio_x_asiento");
                    String matricula = rs.getString("matricula");
                    
                     viajeInformacion = new ViajeInformacion(id_viaje, ciudad_salida, ciudad_destino, fecha_salida, precio_x_asiento, matricula);
                     
                }
                
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar viaje: " + e.getMessage());
        }
     
        return viajeInformacion;
    }
    
    public boolean actualizarDatos(Connection con, Viaje viaje) throws SQLException {
        
        // Consulta SQL
        String ACTUALIZAR = "UPDATE VIAJES SET id_salida = ?, id_destino = ?, transporte_id = ?, fecha_salida = ?, precio_x_asiento = ? WHERE id_viaje = ?;";
        
        try (PreparedStatement ps = con.prepareStatement(ACTUALIZAR)) {
        
            //Asignación segura de valores
            ps.setInt(1, viaje.getId_salida());
            ps.setInt(2, viaje.getId_destino());
            ps.setInt(3, viaje.getTransporte_id());
            ps.setString(4, viaje.getFecha());
            ps.setDouble(5, viaje.getPrecio_x_asiento());
            ps.setInt(6, viaje.getId_viaje());

            // Ejecutar la actualización
            ps.executeUpdate();
            
            return true;
        
        } catch (SQLException e) {
         
        System.err.println("Error al actulizar datos de de viaje: " + e.getMessage());
        return false;
        }
    }
    
    public boolean eliminarViaje(Connection con, int id_viaje) {
        
        String DELETE = "DELETE FROM VIAJES WHERE id_viaje = ?";
        
        PreparedStatement ps = null;
        
        try {
            ps = con.prepareStatement(DELETE);
            ps.setInt(1, id_viaje);
            
            // executeUpdate devuelve el número de filas afectadas
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas >= 1) {
                System.out.println("Se borro el viaje correctamente");
                return true;
            } else {
                return false;
            }
            // Si afectó al menos 1 fila, significa que se borró con éxito
            
        } catch (SQLException e) {
            System.err.println("Error al intentar eliminar el viaje con ID " + id_viaje + ": " + e.getMessage());
            return false;
        }
    }
}
