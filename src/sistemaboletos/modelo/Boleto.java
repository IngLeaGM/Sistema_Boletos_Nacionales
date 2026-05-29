package sistemaboletos.modelo;

public class Boleto {
    private int id_boleto;
    private int id_viaje;
    private int id_factura;
    private String nom_pasajero;
    private int cedula;
    private int telefono;
    private String asiento;
    
    // Constructor
    public Boleto() {
        
    }
    
    public Boleto(int id_boleto, int id_viaje, int id_factura, String nom_pasajero, int cedula, int telefono, String asiento) {
       this.id_boleto = id_boleto;
       this.id_viaje = id_viaje;
       this.id_factura = id_factura;
       this.nom_pasajero = nom_pasajero;
       this.cedula = cedula;
       this.telefono = telefono;
       this.asiento = asiento;
    }
    
    public Boleto(int id_viaje, int id_factura, String nom_pasajero, int cedula, int telefono, String asiento) {
       this.id_viaje = id_viaje;
       this.id_factura = id_factura;
       this.nom_pasajero = nom_pasajero;
       this.cedula = cedula;
       this.telefono = telefono;
       this.asiento = asiento;
    }
    
    // Getters y Setters necesarios
    
    public int getId_Boleto() {
        return id_boleto;
    }
    
    public void setId_Boleto(int id_boleto) {
        this.id_boleto = id_boleto;
    }
    
    public int getId_viaje() {
        return id_viaje;
    }
    
    public void setId_viaje(int id_viaje) {
        this.id_viaje = id_viaje;
    }
    
    public int getId_factura() {
        return id_factura;
    }
    
    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }
    
    public String getNom_pasajero() {
        return nom_pasajero;
    }
    
    public void setNom_pasajero(String nom_pasajero) {
        this.nom_pasajero = nom_pasajero;
    }
    
    public int getCedula() {
        return cedula;
    }
    
    public void setCedula(int cedula) {
        this.cedula = cedula;
    }
    
    public int getTelefono() {
        return telefono;
    }
    
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
    
    
    public String getAsiento() {
        return asiento;
    }
    
    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }
}
