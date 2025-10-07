package dao;

import datasource.ConnectionDB;
import java.sql.*;
import model.User;

public class UserDao {

    // Registers a new user in the database
    public void register(User user) {
        String sql = "INSERT INTO Users (username, password, confirmPassword) VALUES (?, ?, ?)";
        Connection conn = ConnectionDB.obtenerConexion();

        // Validación añadida para evitar NullPointerException
        if (conn == null) {
            System.err.println("Error: la conexión a la base de datos es null.");
            return;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getConfirmPassword());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Authenticates a user based on username and password
    public User login(String username, String password) {
        String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
        Connection conn = ConnectionDB.obtenerConexion(); // ← moved outside try-with-resources

        // Added: Check if connection is null to avoid NullPointerException
        if (conn == null) {
            System.err.println("Error: Database connection is null.");
            return null;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) { // ← modified: use try-with-resources only for stmt
            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User(
                            rs.getString("username"),
                            rs.getString("password")
                    );
                    user.setId(rs.getInt("id"));
                    return user;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return null if login fails
        return null;
    }
}
