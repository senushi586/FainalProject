package lk.ijse.project.coffeeshop.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static DbConnection dbConnection;
    private Connection connection;

    private DbConnection () throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/coffeshop",
                "root",
                "root"
        );
    }
    public static DbConnection getInstance() throws SQLException {
        return (dbConnection == null) ? dbConnection = new DbConnection():dbConnection;
    }
    public Connection getConnection(){
        return connection;
    }
}
