package datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    // Modificado: Se agregó .trim() por si hay espacios accidentales en el nombre del host
    private static final String URL = "jdbc:mariadb://mariadb:3306/StudyPlanner".trim(); // ← modificado
    private static final String USER = "demo_user";
    private static final String PASSWORD = "demo_pass";

    public static Connection obtenerConexion() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

            // Modificado: Mensaje más claro para depuración
            System.out.println("Conexión establecida con: " + URL); // ← modificado
            return conn;
        } catch (ClassNotFoundException e) {
            System.err.println("No se encontró el driver de MariaDB.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos:");
            System.err.println("URL: " + URL);
            System.err.println("Usuario: " + USER);

            // Añadido: Mensaje específico si el host no se resuelve
            if (e.getMessage().contains("UnknownHostException")) { // ← añadido
                System.err.println("⚠️ El host 'mariadb' no se puede resolver. Verifica tu red de Docker."); // ← añadido
            }

            e.printStackTrace();
        }
        return null;
    }
}
