package sistemaboletos.modelo;

public class Usuario {
    private int id_usuario;
    private String user;
    private String email;
    private String pass;
    private String nombre;
    private String apellido;
    private String cedula;
    private String telf;
    
    
    // Constructor para registros
    public Usuario(String user, String email, String pass,
                    String nombre, String apellido, String cedula, String telf) {
        
        this.user = user;
        this.email = email;
        this.pass = pass;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.telf = telf;
    }
    
    // Constructor para login o consultas
    public Usuario(int id_usuario, String user, String email, String pass,
                    String nombre, String apellido, String cedula, String telf) {
        
        this.id_usuario = id_usuario;
        this.user = user;
        this.email = email;
        this.pass = pass;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.telf = telf;
    }
    
    public int getId_usuario() {
        return id_usuario;
    }
    
    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
    
    public String getUser() {
        return user;
    }
    
    public void setUser(String user) {
        this.user = user;
    }
    
    public void setPassword(String pass) {
        this.pass = pass;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPass() {
        return pass;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
  
    public String getApellido() {
        return apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public String getCedula() {
        return cedula;
    }
    
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
            
    public String getTelf() {
        return telf;
    }
    
    public void setTelf(String telf) {
        this.telf = telf;
    }
}
