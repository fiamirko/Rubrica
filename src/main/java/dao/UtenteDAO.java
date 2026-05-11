package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.Config;
import util.UserSession; 

public class UtenteDAO {
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            Config.get("db.url"), 
            Config.get("db.user"), 
            Config.get("db.pass")
        );
    }

    public boolean verificaCredenziali(String username, String password) {
        String sql = "SELECT id, username FROM utenti WHERE username = ? AND password = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int idTrovato = rs.getInt("id");
                    String userTrovato = rs.getString("username");
                    
                    UserSession.setInstance(idTrovato, userTrovato);
                    
                    return true; 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean registraUtente(String username, String password) {
        String sql = "INSERT INTO utenti (username, password) VALUES (?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
            
        } catch (SQLException e) {
            return false;
        }
    }
}