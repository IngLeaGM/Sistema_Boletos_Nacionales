package sistemaboletos.modelo;

public class Usuario {
    private int id_usuario=0;
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
    
    public String getUser() {
        return user;
    }
    
    public String getEmail() {
        return email;
    }
    public String getPass() {
        return pass;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getApellido() {
        return apellido;
    }
    
    public String getCedula() {
        return cedula;
    }
            
    public String getTelf() {
        return telf;
    }
}
