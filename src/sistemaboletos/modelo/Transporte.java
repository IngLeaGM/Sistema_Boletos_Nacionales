package sistemaboletos.modelo;

public class Transporte {
    private int id_transporte;
    private String modelo;
    private int anio_vehiculo; 
    private String matricula;
    private String tipo_combustible;

    public Transporte(int id_transporte, String modelo, int anio_vehiculo, String matricula, String tipo_combustible) {
        this.id_transporte = id_transporte;
        this.modelo = modelo;
        this.anio_vehiculo = anio_vehiculo;
        this.matricula = matricula;
        this.tipo_combustible = tipo_combustible;
    }

    public Transporte(String modelo, int anio_vehiculo, String matricula, String tipo_combustible) {
        this.modelo = modelo;
        this.anio_vehiculo = anio_vehiculo;
        this.matricula = matricula;
        this.tipo_combustible = tipo_combustible;
    }
    
    public Transporte(String matricula) {
        this.matricula = matricula;
    }
    
    public int getId_transporte() {
        return id_transporte;
    }
    
    public void setId_transporte(int id_transporte) {
        this.id_transporte = id_transporte;
    }
    
    public String getMatricula() {
        return matricula;
    }
    
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnio_vehiculo() {
        return anio_vehiculo;
    }

    public void setAnio_vehiculo(int anio_vehiculo) {
        this.anio_vehiculo = anio_vehiculo;
    }

    public String getTipo_combustible() {
        return tipo_combustible;
    }

    public void setTipo_combustible(String tipo_combustible) {
        this.tipo_combustible = tipo_combustible;
    }
    
    @Override
    public String toString() {
        return matricula; 
    }
    
}
