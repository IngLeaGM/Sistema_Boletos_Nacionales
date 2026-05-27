package sistemaboletos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sistemaboletos.conexion.ConexionBD;
import sistemaboletos.modelo.Usuario;
import org.mindrot.jbcrypt.BCrypt;

public class UsuariosDAO {
    
    public boolean RegistrarUsuario(Connection con, Usuario usuario) throws SQLException {
        
        String hashed_pass = BCrypt.hashpw(usuario.getPass(), BCrypt.gensalt());
        
        // Consulta SQL
        String INSERT = "INSERT into USUARIOS (user, email, pass, nombre, apellido, cedula, telf) values (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = con.prepareStatement(INSERT)) {
        
            //Asignación segura de valores
            ps.setString(1, usuario.getUser());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, hashed_pass);
            ps.setString(4, usuario.getNombre());
            ps.setString(5, usuario.getApellido());
            ps.setString(6, usuario.getCedula());
            ps.setString(7, usuario.getTelf());

            // Ejecutar la actualización
            ps.executeUpdate();
            
            return true;
        
        } catch (SQLException e) {
         
        System.err.println("Error al registrar usuario: " + e.getMessage());
        return false;
        }
    }
    
    public boolean LoginUsuario(Connection con, Usuario usuario) throws SQLException {
        
        String SELECCIONAR = "SELECT * FROM USUARIOS WHERE email = ?";
        
        // Busqueda en la base de datos
        try (PreparedStatement ps = con.prepareStatement(SELECCIONAR)) {
                
            // Asiganamos el valor de parametor 1
            ps.setString(1, usuario.getEmail());
            try (ResultSet rs = ps.executeQuery()) {
            
                if (rs.next()) {
                    // Se extraen los datos de MySql
                    String hashed_pass = rs.getString("pass");

                    // Se compara la clave ingresada con el hash de la base de datos.

                    if(BCrypt.checkpw(usuario.getPass(), hashed_pass)) {
                        System.out.println("Inicio de sesion exitoso");
                        return true;
                    } else {
                        System.out.println("Usuario o contraseña incorrecto");
                    }
                
                } else {
                    System.out.println("Usuario o contraseña incorrecto");
                        return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al intentar iniciar sesion: "+e.getMessage());
            return false;
        }
        return false;
    }
    
    public Usuario obtener_usuario(Connection con, int idBuscado) throws SQLException {
        
        Usuario usuarioExtraido = new Usuario(0, "none", "none", "none", "none", "none", "none", "none");
        // Consulta SQL
        String SELECCIONAR = "SELECT id_usuario, user, email, nombre, apellido, cedula, telf FROM USUARIOS WHERE id_usuario = ?;";
        
        
        try (PreparedStatement ps = con.prepareStatement(SELECCIONAR)) {
            
                ps.setInt(1, idBuscado);
                
                try (ResultSet rs = ps.executeQuery()) {
                    
                    if (rs.next()) {
                        int id_usuario = rs.getInt("id_usuario");
                        String user = rs.getString("user");
                        String email = rs.getString("email");
                        String nombre = rs.getString("nombre");
                        String apellido = rs.getString("apellido");
                        String cedula = rs.getString("Cedula");
                        String telf = rs.getString("telf");
                        
                         usuarioExtraido = new Usuario(id_usuario, user, "none", email, nombre, apellido, cedula, telf);
                    }

                }
        } catch (SQLException e) {
            System.err.println("Error al selecionar tabla: "+e.getMessage());
        }
        return usuarioExtraido;
    }
}
 

