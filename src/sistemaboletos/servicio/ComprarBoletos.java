package sistemaboletos.servicio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sistemaboletos.conexion.ConexionBD;
import sistemaboletos.dao.BoletosDAO;
import sistemaboletos.dao.FacturasDAO;
import sistemaboletos.modelo.Boleto;
import sistemaboletos.modelo.BoletoInformacion;
import sistemaboletos.modelo.Factura;
import sistemaboletos.modelo.Viaje;

public class ComprarBoletos {
    
    private FacturasDAO facturasDAO = new FacturasDAO();
    private BoletosDAO boletosDAO = new BoletosDAO();
    
    public boolean ProcesarComprarCompleta(Factura factura, List<Boleto> boletos) throws SQLException {

        Connection con = null;


        try {
            con = ConexionBD.getConexion();

            con.setAutoCommit(false);

            int idFacturaGenerado = facturasDAO.insertar_factura(con, factura);

            if (idFacturaGenerado <= 0) {
                throw new SQLException("No se pudo generar el ID de la factura.");
            }

            for (Boleto boleto : boletos) {
                boleto.setId_factura(idFacturaGenerado);
            }

            boletosDAO.insertarBoletos(con, boletos);

            con.commit();
            System.out.println("Compra procesada con éxito. Factura: " + idFacturaGenerado);

            return true;
        } catch (SQLException e) {

            System.err.println("Error en la transaccion: " + e);
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error fatal al hacer rollback: " + ex.getMessage());
                }
            }
            return false;

        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true); // Restauramos el comportamiento por defecto
                    con.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar conexión: " + e.getMessage());
                }
            }
        }
    }
}
