/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author SystemX
 */
public class DbConnectionFactory {

    private static DbConnectionFactory instance;
    public Connection connection;

    public static DbConnectionFactory getInstance() {
        if (instance == null) {
            instance = new DbConnectionFactory();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/projekatps";
        String user = "root";
        String password = "";

       
            if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
            }return connection;
    }
}
