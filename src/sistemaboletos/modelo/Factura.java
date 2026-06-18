package sistemaboletos.modelo;

public class Factura {
    private int id_factura;
    private int usuario_id;
    private double monto_total;
    private String metodo_pago;
    private String fecha;
    
    public Factura(int id_factura, int usuario_id, double monto_total, String metodo_pago, String fecha) {
        this.id_factura = id_factura;
        this.usuario_id = usuario_id;
        this.monto_total = monto_total;
        this.metodo_pago  = metodo_pago;
        this.fecha = fecha;
    }
    
    public Factura(int usuario_id, double monto_total, String metodo_pago) {
        this.usuario_id = usuario_id;
        this.monto_total = monto_total;
        this.metodo_pago  = metodo_pago;
    }

    public Factura() {
     
    }
    
    public int getId_factura() {
        return id_factura;
    }
    
    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }
    
    public int getId_usuario() {
        return usuario_id;
    }
    
    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
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
