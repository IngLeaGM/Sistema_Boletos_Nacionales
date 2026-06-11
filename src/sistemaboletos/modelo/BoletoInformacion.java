
package sistemaboletos.modelo;


public class BoletoInformacion {
    private int id_boleto;
    private String nom_pasajero;
    private String cedula;
    private String ciudad_salida;
    private String ciudad_destino;
    private String asiento;
    private String datos_transaccion;

    public BoletoInformacion(int id_boleto, String nom_pasajero, String cedula, String ciudad_salida, String ciudad_destino, String asiento, String datos_transaccion) {
        this.id_boleto = id_boleto;
        this.nom_pasajero = nom_pasajero;
        this.cedula = cedula;
        this.ciudad_salida = ciudad_salida;
        this.ciudad_destino = ciudad_destino;
        this.asiento = asiento;
        this.datos_transaccion = datos_transaccion;
    }

    public BoletoInformacion() {
    }

    public int getId_boleto() {
        return id_boleto;
    }

    public void setId_boleto(int id_boleto) {
        this.id_boleto = id_boleto;
    }

    public String getNom_pasajero() {
        return nom_pasajero;
    }

    public void setNom_pasajero(String nom_pasajero) {
        this.nom_pasajero = nom_pasajero;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
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

    public String getAsiento() {
        return asiento;
    }

    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }

    public String getDatos_transaccion() {
        return datos_transaccion;
    }

    public void setDatos_transaccion(String datos_transaccion) {
        this.datos_transaccion = datos_transaccion;
    }
    
    
}
