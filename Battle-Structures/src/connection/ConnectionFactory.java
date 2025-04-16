package connection;

import java.sql.*;

public class ConnectionFactory {
    
    private static final String URL = "jdbc:postgresql://localhost:5432/battle_structures_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "110723";
    
    public static Connection getConnection() throws SQLException{
        
        try{
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
        catch(SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados:" + e.getMessage());
            throw e;
        }
    }
    
    public static void closeConnection(Connection con){
        try{
            if(con != null){
                con.close();
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void closeConnection(Connection con, PreparedStatement stmt){
        
        closeConnection(con);
        
        try{
            if(stmt != null){
                stmt.close();
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs){
        
        closeConnection(con, stmt);
        
        try{
            if(rs != null){
                rs.close();
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
