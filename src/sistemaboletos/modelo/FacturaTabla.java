package sistemaboletos.modelo;

public class FacturaTabla {
    private int id_factura;
    private String user;
    private double monto_total;
    private String metodo_pago;
    private String fecha;
    
    public FacturaTabla(int id_factura, String user, double monto_total, String metodo_pago, String fecha) {
        this.id_factura = id_factura;
        this.user = user;
        this.monto_total = monto_total;
        this.metodo_pago  = metodo_pago;
        this.fecha = fecha;
    }
    
    public FacturaTabla(String user, double monto_total, String metodo_pago) {
        this.user = user;
        this.monto_total = monto_total;
        this.metodo_pago  = metodo_pago;
    }

    public FacturaTabla() {
     
    }
    
    public int getId_factura() {
        return id_factura;
    }
    
    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }
    
    public String getUser() {
        return user;
    }
    
    public void setUsuario_id(String user) {
        this.user = user;
    }
    
    public double getMonto_total() {
        return monto_total;
    }
    
    public void setMonto_total(double monto_total) {
        this.monto_total = monto_total;
    }
    
    public String getMetodo_pago() {
        return metodo_pago;
    }
    
    public void setMetodo_pago(String metodo_pago) {
        this.metodo_pago = metodo_pago;
    }
    
    public String getFecha() {
        return fecha;
    }
    
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
}
