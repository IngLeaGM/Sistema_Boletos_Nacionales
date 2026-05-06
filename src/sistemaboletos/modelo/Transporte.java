package sistemaboletos.modelo;

public class Transporte {
    private int id_transporte;
    private String matricula;
    
    public Transporte(int id_transporte, String matricula) {
        this.id_transporte = id_transporte;
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
}
