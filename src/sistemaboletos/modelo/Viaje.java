package sistemaboletos.modelo;

public class Viaje {
    private int id_viaje;
    private int id_salida;
    private int id_destino;
    private int transporte_id;
    private String fecha_salida;
    private double precio_x_asiento;
    
    public Viaje(int id_viaje, int id_salida, int id_destino, int transporte_id, String fecha_salida, double precio_x_asiento) {
        this.id_viaje = id_viaje;
        this.id_salida = id_salida;
        this.id_destino = id_destino;
        this.transporte_id = transporte_id;
        this.fecha_salida = fecha_salida;
        this.precio_x_asiento = precio_x_asiento;
    }
    
    public Viaje(int id_salida, int id_destino, int transporte_id, String fecha_salida, double precio_x_asiento) {
        this.id_salida = id_salida;
        this.id_destino = id_destino;
        this.transporte_id = transporte_id;
        this.fecha_salida = fecha_salida;
        this.precio_x_asiento = precio_x_asiento;
    }
    
    public Viaje(int id_viaje, String fecha_salida) {
        this.id_viaje = id_viaje;
        this.fecha_salida = fecha_salida;
    }

    public Viaje() {
    }

    public int getId_viaje() {
        return id_viaje;
    }
    
    public void setId_viaje(int id_viaje) {
        this.id_viaje = id_viaje;
    }

    public int getId_salida() {
        return id_salida;
    }
    
    public void setId_salida(int id_salida) {
        this.id_salida = id_salida;
    }
    public int getId_destino() {
        return id_destino;
    }
    
    public void setId_destino(int id_destino) {
        this.id_destino = id_destino;
    }
    public int getTransporte_id() {
        return transporte_id;
    }
    
    public void setTransporte_id(int transporte_id) {
        this.transporte_id = transporte_id;
    }
    public String getFecha() {
        return fecha_salida;
    }
    
    public void setFecha(String fecha_salida) {
        this.fecha_salida = fecha_salida;
    }
    public double getPrecio_x_asiento() {
        return precio_x_asiento;
    }
    
    public void setPrecio_x_asiento(double precio_x_asiento) {
        this.precio_x_asiento = precio_x_asiento;
    }
    
    // Este metodo fue creado para jcbFecha por lo tanto solo devuelve la fecha depo String
    @Override
    public String toString() {
        return fecha_salida; 
    }
}
