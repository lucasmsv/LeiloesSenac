import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conectaDAO {

    public Connection conectaDAO() {
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost/db_uc11_atv02?useSSL=false",
                    "root",
                    "2001"
            );
            return conn;

        } 
        catch (SQLException e) {
            System.out.println("Erro ao Conectar: " + e.getMessage());
            return null;
        }
    }

}
