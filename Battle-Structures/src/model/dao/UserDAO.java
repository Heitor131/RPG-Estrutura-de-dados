package model.dao;

import connection.ConnectionFactory;
import java.sql.*;
import javax.swing.JOptionPane;
import model.entities.User;

public class UserDAO {
    
    public User findById(int id) throws SQLException {
        
        String sql = "SELECT * FROM users WHERE id = ?";
        
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                
                if (rs.next()) {
                    return new User(
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getString("email")
                    );
                }
            }
        }
        return null;
    }
    
    public boolean checkLogin(String email, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // login válido se encontrou o usuário
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Unsuccessful connection", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public void create(User u) throws SQLException {
        String sql = "INSERT INTO users(userName, password, email) VALUES(?,?,?)";
        
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, u.getUserName());
            stmt.setString(2, u.getPassword());
            stmt.setString(3, u.getEmail());

            stmt.executeUpdate();

            System.out.println("Saved user");
            //JOptionPane.showMessageDialog(null, "Saved successfully");

        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Save error: " + ex);
            System.out.println("Error");
        }
    }
    
    public void delete(User u) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, u.getId());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Account deleted successfully");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Delete error: " + ex);
        }
    }
}
