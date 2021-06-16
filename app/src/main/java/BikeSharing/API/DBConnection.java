package BikeSharing.API;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manages database connection
 */

public class DBConnection {

    /**
     * Returns the DB connection
     * @return the DB connection
     */

    public static Connection getDBConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=bikeSharingSystem&password=bikesharing");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Impossible to connect with database");
        }
        return connection;
    }
    
}
