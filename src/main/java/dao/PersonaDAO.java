package dao;

import java.sql.*;
import java.util.Vector;
import model.Persona;
import util.Config;
import util.UserSession;

public class PersonaDAO {

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(Config.get("db.url"), Config.get("db.user"), Config.get("db.pass"));
    }

    public Vector<Persona> carica() throws Exception {
        Vector<Persona> persone = new Vector<>();
        String sql = "SELECT * FROM contatti WHERE utente_id = ?";
        try (Connection conn = getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, UserSession.getUserId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Persona p = new Persona(rs.getString("nome"), rs.getString("cognome"),
                                        rs.getString("indirizzo"), rs.getString("telefono"), rs.getInt("eta"));
                p.setId(rs.getInt("id")); 
                persone.add(p);
            }
        }
        return persone;
    }

    public void aggiorna(Persona p) throws Exception {
        String sql = "UPDATE contatti SET nome=?, cognome=?, indirizzo=?, telefono=?, eta=? WHERE id=? AND utente_id=?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.getNome());
            pstmt.setString(2, p.getCognome());
            pstmt.setString(3, p.getIndirizzo());
            pstmt.setString(4, p.getTelefono());
            pstmt.setInt(5, p.getEta());
            pstmt.setInt(6, p.getId()); 
            pstmt.setInt(7, UserSession.getUserId()); 
            pstmt.executeUpdate();
        }
    }

    public void elimina(int id) throws Exception {
        String sql = "DELETE FROM contatti WHERE id = ? AND utente_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, UserSession.getUserId());
            pstmt.executeUpdate();
        }
    }

    public void salva(Vector<Persona> persone) throws Exception {
        // Svuota e riscrive il Vector (logica richiesta dal PDF)
        String deleteSql = "DELETE FROM contatti WHERE utente_id = ?";
        String insertSql = "INSERT INTO contatti (nome, cognome, indirizzo, telefono, eta, utente_id) VALUES (?,?,?,?,?,?)";
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement del = conn.prepareStatement(deleteSql);
                 PreparedStatement ins = conn.prepareStatement(insertSql)) {
                del.setInt(1, UserSession.getUserId());
                del.executeUpdate();
                for (Persona p : persone) {
                    ins.setString(1, p.getNome());
                    ins.setString(2, p.getCognome());
                    ins.setString(3, p.getIndirizzo());
                    ins.setString(4, p.getTelefono());
                    ins.setInt(5, p.getEta());
                    ins.setInt(6, UserSession.getUserId());
                    ins.addBatch();
                }
                ins.executeBatch();
                conn.commit();
            } catch (SQLException e) { conn.rollback(); throw e; }
        }
    }

    public void inserisci(Persona p) throws Exception {
        String sql = "INSERT INTO contatti (nome, cognome, indirizzo, telefono, eta, utente_id) VALUES (?,?,?,?,?,?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.getNome());
            pstmt.setString(2, p.getCognome());
            pstmt.setString(3, p.getIndirizzo());
            pstmt.setString(4, p.getTelefono());
            pstmt.setInt(5, p.getEta());
            pstmt.setInt(6, UserSession.getUserId());
            pstmt.executeUpdate();
        }
    }
}