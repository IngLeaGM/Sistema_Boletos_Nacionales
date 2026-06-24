package sistemaboletos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import sistemaboletos.conexion.ConexionBD;
import sistemaboletos.modelo.Usuario;
import org.mindrot.jbcrypt.BCrypt;

public class UsuariosDAO {
    
    // Lista de dominios que tu sistema va a aceptar
    private static final List<String> DOMINIOS_PERMITIDOS = Arrays.asList(
        "@gmail.com",
        "@outlook.com",
        "@hotmail.com",
        "@yahoo.com",
        "@hotmail.es",
        "@outlook.es"
    );
    
    public boolean RegistrarUsuario(Usuario usuario) throws SQLException {
        
        Connection con = ConexionBD.getConexion();
        
        String hashed_pass = BCrypt.hashpw(usuario.getPass(), BCrypt.gensalt());
        
        // Consulta SQL
        String INSERT = "INSERT into USUARIOS (user, email, pass, telf) values (?, ?, ?, ?)";
        
        if (!this.esDominioPermitido(usuario.getEmail())) {
            return false;
        }
        
        if (usuario.getTelf().length() != 11) {
            JOptionPane.showMessageDialog(null, "Telefono invalido");
            return false;
        }
        
        boolean verificacion = this.vericarDatos(con, usuario);
        
        if (!verificacion) {
            return false;
        }
        
        try (PreparedStatement ps = con.prepareStatement(INSERT)) {
        
            //Asignación segura de valores
            ps.setString(1, usuario.getUser());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, hashed_pass);
            ps.setString(4, usuario.getTelf());

            // Ejecutar la actualización
            ps.executeUpdate();
            
            return true;
        
        } catch (SQLException e) {
         
        System.err.println("Error al registrar usuario: " + e.getMessage());
        return false;
        }
    }
    
    public boolean LoginUsuario(Usuario usuario) throws SQLException {
        
        Connection con = ConexionBD.getConexion();
        
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
        
        Usuario usuarioExtraido = new Usuario(0, "none", "none", "none", "none");
        // Consulta SQL
        String SELECCIONAR = "SELECT id_usuario, user, email, telf FROM USUARIOS WHERE id_usuario = ?;";
        
        
        try (PreparedStatement ps = con.prepareStatement(SELECCIONAR)) {
            
                ps.setInt(1, idBuscado);
                
                try (ResultSet rs = ps.executeQuery()) {
                    
                    if (rs.next()) {
                        int id_usuario = rs.getInt("id_usuario");
                        String user = rs.getString("user");
                        String email = rs.getString("email");
                        String telf = rs.getString("telf");
                        
                         usuarioExtraido = new Usuario(id_usuario, user, "none", email, telf);
                    }

                }
        } catch (SQLException e) {
            System.err.println("Error al selecionar tabla: "+e.getMessage());
        }
        return usuarioExtraido;
    }
    
    public boolean vericarDatos(Connection con, Usuario usuario) throws SQLException {
        String SELECT = "SELECT user, email, telf FROM USUARIOS WHERE user = ? OR email = ? OR telf = ?";
        
        try (PreparedStatement ps = con.prepareStatement(SELECT)) {
            
            ps.setString(1, usuario.getUser());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getTelf());
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Si se entra aquí, es porque al menos uno de los datos ya existe.
                    // Ahora verificamos cuál de ellos causo la coincidencia:
                    
                    if (usuario.getUser().equals(rs.getString("user"))) {
                        JOptionPane.showMessageDialog(null, "Usuario ya existe");
                        return false;
                    }
                    if (usuario.getEmail().equals(rs.getString("email"))) {
                        JOptionPane.showMessageDialog(null, "Email ya existe");
                        return false;
                    }
                    if (usuario.getTelf().equals(rs.getString("telf"))) {
                        JOptionPane.showMessageDialog(null, "Telefono ya fue utilizado");
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar duplicados: " + e.getMessage());
            return false;
        }
        return true;
    }
    
    public boolean vericarDatosUPDATE(Connection con, Usuario usuario) throws SQLException {
        String SELECT = "SELECT user, email, telf FROM USUARIOS WHERE (user = ? OR email = ? OR telf = ?) AND id_usuario != ?;";
        
        try (PreparedStatement ps = con.prepareStatement(SELECT)) {
            
            ps.setString(1, usuario.getUser());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getTelf());
            ps.setInt(4, usuario.getId_usuario());
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Si se entra aquí, es porque al menos uno de los datos ya existe.
                    // Ahora verificamos cuál de ellos causo la coincidencia:
                    
                    if (usuario.getUser().equals(rs.getString("user"))) {
                        JOptionPane.showMessageDialog(null, "Usuario ya existe");
                        return false;
                    }
                    if (usuario.getEmail().equals(rs.getString("email"))) {
                        JOptionPane.showMessageDialog(null, "Email ya existe");
                        return false;
                    }
                    if (usuario.getTelf().equals(rs.getString("telf"))) {
                        JOptionPane.showMessageDialog(null, "Telefono ya fue utilizado");
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar duplicados: " + e.getMessage());
            return false;
        }
        return true;
    }
    
    public Usuario obtenerId_usuario(Usuario usuario) throws SQLException {
        
        Connection con = ConexionBD.getConexion();
        
        // Consulta SQL
        String SELECCIONAR = "SELECT id_usuario FROM USUARIOS WHERE email = ?;";
        
        try (PreparedStatement ps = con.prepareStatement(SELECCIONAR)) {
            
                ps.setString(1, usuario.getEmail());
                
                try (ResultSet rs = ps.executeQuery()) {
                    
                    if (rs.next()) {
                        int id_usuario = rs.getInt("id_usuario");
                        
                        usuario.setId_usuario(id_usuario);
                    }

                }
        } catch (SQLException e) {
            System.err.println("Error al selecionar tabla: "+e.getMessage());
        }
        return usuario;
    }
    
    public boolean actualizarDatos(Connection con, Usuario usuario) throws SQLException {
        
        // Consulta SQL
        String ACTUALIZAR = "UPDATE USUARIOS SET user = ?, email = ?, telf = ? WHERE id_usuario = ?;";
        
        boolean verificacion = this.vericarDatosUPDATE(con, usuario);
        
        if (!verificacion) {
            return false;
        }
        
        
        try (PreparedStatement ps = con.prepareStatement(ACTUALIZAR)) {
            //Asignación segura de valores
            ps.setString(1, usuario.getUser());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getTelf());
            ps.setInt(4, usuario.getId_usuario());

            // Ejecutar la actualización
            ps.executeUpdate();
            
            return true;
        
        } catch (SQLException e) {
         
        System.err.println("Error al actulizar datos de usuario: " + e.getMessage());
        return false;
        }
    }
    
    public boolean actualizarContraseña(Connection con, Usuario usuario) throws SQLException {
        
        String hashed_pass = BCrypt.hashpw(usuario.getPass(), BCrypt.gensalt());
        
        // Consulta SQL
        String ACTUALIZAR = "UPDATE USUARIOS SET pass = ? WHERE id_usuario = ?;";
          
        try (PreparedStatement ps = con.prepareStatement(ACTUALIZAR)) {
        
            //Asignación segura de valores
            ps.setString(1, hashed_pass);
            ps.setInt(2, usuario.getId_usuario());

            // Ejecutar la actualización
            ps.executeUpdate();
            
            return true;
        
        } catch (SQLException e) {
         
        System.err.println("Error al actulizar datos de usuario: " + e.getMessage());
        return false;
        }
    }
    
    public boolean esDominioPermitido(String correo) {
        if (correo == null || correo.trim().isEmpty()) {
            return false;
        }
        
        String correoLimpio = correo.trim().toLowerCase();
        
        // Buscamos si el correo termina con alguno de los dominios de nuestra lista
        for (String dominio : DOMINIOS_PERMITIDOS) {
            if (correoLimpio.endsWith(dominio)) {
                return true; // Es válido
            } else {
                JOptionPane.showMessageDialog(null, "Email invalido");
                return false;
            }
        }
        
        return false; // No pertenece a ningún dominio de la lista
    }
    
    public List<Usuario> ObtenerUsuarios(Connection con) {
    // Creacion de lista vacia para guardar los objetos
        
        List<Usuario> listaUsuarios = new ArrayList<>();
        
        // Consulta SQL
        String SELECCIONAR = "SELECT id_usuario, user, email, telf FROM USUARIOS;";
        
        try (PreparedStatement ps = con.prepareStatement(SELECCIONAR)) {
           
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Se extraen los datos de MySql
                    int id_usuario = rs.getInt("id_usuario");
                    String user = rs.getString("user");
                    String email = rs.getString("email");
                    String telf = rs.getString("telf");
                    
                    // Se transforman los datos obtenidos en objetos
                    Usuario usuario = new Usuario(id_usuario, user, email, telf);

                    // Se añade el nuevo objeto a la lista
                    listaUsuarios.add(usuario);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al selecionar tabla: "+e.getMessage());
        }
        return listaUsuarios;
    }
    
    public boolean eliminarUsuario(Connection con, int id_usuario) {
        
        String DELETE = "DELETE FROM USUARIOS WHERE id_usuario = ?";
        
        PreparedStatement ps = null;
        
        try {
            ps = con.prepareStatement(DELETE);
            ps.setInt(1, id_usuario);
            
            // executeUpdate devuelve el número de filas afectadas
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas >= 1) {
                System.out.println("Se borro el usuario correctamente");
                return true;
            } else {
                return false;
            }
            // Si afectó al menos 1 fila, significa que se borró con éxito
            
        } catch (SQLException e) {
            System.err.println("Error al intentar eliminar el usuario con ID " + id_usuario + ": " + e.getMessage());
            return false;
        }
    }
}
