package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    final private static String url = "jdbc:mysql://localhost:3306/live_chat_room";
    final private static Properties prop = new Properties();

    static {
        prop.setProperty("user", "root");
        prop.setProperty("password", "1234");
    }

    private Connection connection;
    public static DBConnection dbConnection;

    private DBConnection() throws SQLException {
        connection = DriverManager.getConnection(url, prop);
    }
    public static DBConnection getInstance() throws SQLException {
        if (dbConnection == null) {
            return dbConnection = new DBConnection();
        }else {
            return dbConnection;
        }
    }
    public Connection getConnection() {
        return connection;
    }

}
