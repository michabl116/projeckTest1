package datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static final String URL = "jdbc:mariadb://mariadb:3306/StudyPlanner";
    private static final String USER = "demo_user";
    private static final String PASSWORD = "demo_pass";

    public static Connection obtenerConexion() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Conexión exitosa a la base de datos.");
            return conn;
        } catch (ClassNotFoundException e) {
            System.err.println("❌ No se encontró el driver de MariaDB.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar con la base de datos:");
            System.err.println("URL: " + URL);
            System.err.println("Usuario: " + USER);
            e.printStackTrace();
        }
        return null;
    }
}
