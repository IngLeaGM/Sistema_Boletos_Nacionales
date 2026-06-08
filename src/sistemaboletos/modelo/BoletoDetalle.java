package sistemaboletos.modelo;

public class BoletoDetalle {
    private int id_boleto;
    private String asiento;
    private String fecha_salida;
    private String nom_pasajero;
    
    public BoletoDetalle() {
    }
    
    public BoletoDetalle(int id_boleto, String asiento, String fecha_salida,
                            String nom_pasajero) {
        this.id_boleto = id_boleto;
        this.asiento = asiento;
        this.fecha_salida = fecha_salida;
        this.nom_pasajero = nom_pasajero;
    }

    public int getId_boleto() {
        return id_boleto;
    }

    public void setId_boleto(int id_boleto) {
        this.id_boleto = id_boleto;
    }

    public String getAsiento() {
        return asiento;
    }

    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }

    public String getFecha_salida() {
        return fecha_salida;
    }

    public void setFecha_salida(String fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

    public String getNom_pasajero() {
        return nom_pasajero;
    }

    public void setNom_pasajero(String nom_pasajero) {
        this.nom_pasajero = nom_pasajero;
    }
}
