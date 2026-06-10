package sistemaboletos.modelo;


public class ViajeInformacion {
    
    private int id_viaje;
    private String ciudad_salida;
    private String ciudad_destino;
    private String fecha;
    private double precio_x_asiento;
    private String Matricula;

    public ViajeInformacion(int id_viaje, String ciudad_salida, String ciudad_destino, String fecha, double precio_x_asiento, String Matricula) {
        this.id_viaje = id_viaje;
        this.ciudad_salida = ciudad_salida;
        this.ciudad_destino = ciudad_destino;
        this.fecha = fecha;
        this.precio_x_asiento = precio_x_asiento;
        this.Matricula = Matricula;
    }

    public ViajeInformacion() {
    }

    public int getId_viaje() {
        return id_viaje;
    }

    public void setId_viaje(int id_viaje) {
        this.id_viaje = id_viaje;
    }

    public String getCiudad_salida() {
        return ciudad_salida;
    }

    public void setCiudad_salida(String ciudad_salida) {
        this.ciudad_salida = ciudad_salida;
    }

    public String getCiudad_destino() {
        return ciudad_destino;
    }

    public void setCiudad_destino(String ciudad_destino) {
        this.ciudad_destino = ciudad_destino;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getPrecio_x_asiento() {
        return precio_x_asiento;
    }

    public void setPrecio_x_asiento(double precio_x_asiento) {
        this.precio_x_asiento = precio_x_asiento;
    }

    public String getMatricula() {
        return Matricula;
    }

    public void setMatricula(String Matricula) {
        this.Matricula = Matricula;
    }
    
}
