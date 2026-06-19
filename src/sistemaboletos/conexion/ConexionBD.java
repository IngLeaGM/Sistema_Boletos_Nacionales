package sistemaboletos.conexion;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConexionBD {
    
    private static HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        
        // Configuración de la base de datos
        config.setJdbcUrl("jdbc:mysql://localhost/SistemaBoletos");
        config.setUsername("root");
        config.setPassword("");
        
        // Configuración del Pool 
        config.setMaximumPoolSize(20); 
        config.setMinimumIdle(2);     
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        
        dataSource = new HikariDataSource(config);
    }
    
    public static Connection getConexion() throws SQLException {
        return dataSource.getConnection();
    }
}
