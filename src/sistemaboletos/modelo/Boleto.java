/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaboletos.modelo;

/**
 *
 * @author leanddro
 */
public class Boleto {
    private int id_boleto;
    private String nombre;
    
    // Constructor
    public Boleto(int id_boleto, String nombre) {
       this.id_boleto = id_boleto;
       this.nombre = nombre;
    }
    
    // Getters y Setters necesarios
    
    public int getId_Boleto() {
        return id_boleto;
    }
    
    public void setId_Boleto() {
        this.id_boleto = id_boleto;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre() {
        this.nombre = nombre;
    }
    
    
    
    
}
