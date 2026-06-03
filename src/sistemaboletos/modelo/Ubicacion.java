package sistemaboletos.modelo;

public class Ubicacion {
    private int id_ubicacion;
    private String nombre;
    
    public Ubicacion(int id_ubicacion, String nombre) {
        this.id_ubicacion = id_ubicacion;
        this.nombre = nombre;
    }
    
    public Ubicacion(String nombre) {
        this.nombre = nombre;
    }
    
    public int getId_ubicacion() {
        return id_ubicacion;
    }
    
    public void setId_ubicacion(int id_ubicacion) {
        this.id_ubicacion = id_ubicacion;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String toString() {
        return nombre; 
    }
}
