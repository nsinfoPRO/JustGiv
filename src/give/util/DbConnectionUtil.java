/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package give.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
/**
 *
 * @author Administrator
 */
public class DbConnectionUtil {
    // in real life, use a connection pool....
    private Connection connection;

    public DbConnectionUtil(String dbURL, String user, String password) throws SQLException, ClassNotFoundException {        
        connection = DriverManager.getConnection(dbURL, user, password);
        // "jdbc:mysql://xxx.xxx.xxx.xxx:YOUR_PORT", "YOUR_DB_USER", "YOUR_PASSWORD")
    }

    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
    
    public ResultSet queryForResult(String query) throws SQLException {
        try {
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery(query);
            return rs;
        } catch (Exception e) {
            return null;
        }
    }
    
    public void queryForExecute(String query) throws SQLException {
        try {
            Statement stmnt = connection.createStatement();
            stmnt.executeQuery(query);            
        } catch (Exception e) {
            
        }
    }
}
