package BikeSharing.Subscription.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;

import BikeSharing.API.DBConnection;
import BikeSharing.Bike.BikeBuilder;

/**
 * Data access object for the rent following the DAO design pattern
 */

public class RentDao {

    /**
     * Fetches the last rent for the passed subscription
     * @param subCode Subscription code
     * @return The last rent
     */

    public static RentDataTransfer getLastRent(String subCode) {
        RentDataTransfer data = new RentDataTransfer();
        Connection connection = DBConnection.getDBConnection();
        PreparedStatement statement;
        ResultSet rs;
        try {
            statement = connection.prepareStatement("SELECT * FROM bikesharing.rents WHERE subCode = ? AND startRent = (SELECT MAX(startRent) FROM bikesharing.rents WHERE subCode = ?)");
            statement.setString(1, subCode);
            statement.setString(2, subCode);
            rs = statement.executeQuery();
            boolean exists = rs.next();
            if (!exists) {
                data.bike = null;
                data.endRent = null;
                data.id = -1;
                data.startRent = null;
                data.subCode = null;
            } else {
                data.bike = BikeBuilder.decode(rs.getInt("bike"));
                Timestamp endDate = rs.getTimestamp("endRent");
                GregorianCalendar c = new GregorianCalendar();
                if (endDate == null) {
                    c = null;
                } else {
                    c.setTime(endDate);
                }
                data.endRent = c;
                data.id = rs.getLong("rentId");
                Timestamp startDate = rs.getTimestamp("startRent");
                GregorianCalendar c2 = new GregorianCalendar();
                c2.setTime(startDate);
                data.startRent = c2;
                data.subCode = rs.getString("subCode");
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new RuntimeException("Query error");
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't close the connection");
        }
        return data;
    }

    /**
     * Updates the rent with the new information
     * @param data data to be saved
     */

    public static void saveRent(RentDataTransfer data) {
        Connection connection = DBConnection.getDBConnection();
        PreparedStatement statement;
        int rows = 0;
        try {
            statement = connection.prepareStatement("UPDATE bikesharing.rents SET endRent = ? WHERE rentID = ?");
            statement.setTimestamp(1, new Timestamp(data.endRent.getTimeInMillis()));
            statement.setLong(2, data.id);
            rows = statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new RuntimeException("Query error");
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't close the connection");
        }
        if (rows != 1) {
            throw new RuntimeException("Unexpected rows affected");
        }
    }

    /**
     * Creates a new rent with the provided data
     * @param data data for the rent
     */

    public static void createRent(RentDataTransfer data) {
        Connection connection = DBConnection.getDBConnection();
        PreparedStatement statement;
        int rows = 0;
        try {
            statement = connection.prepareStatement("INSERT INTO bikesharing.rents (subCode, startRent, bike) VALUES (?, ?, ?)");
            statement.setString(1, data.subCode);
            statement.setTimestamp(2, new Timestamp(data.startRent.getTimeInMillis()));
            statement.setInt(3, data.bike.encode());
            rows = statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new RuntimeException("Query error");
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't close the connection");
        }
        if (rows != 1) {
            throw new RuntimeException("Unexpected rows affected");
        }
    }

    /**
     * Returns a collection with all the rents 
     * @return a collection with all the rents. Rents of expired subscriptions have null subCode
     */

    public static Collection<RentDataTransfer> getAllRents() {
        ArrayList<RentDataTransfer> ret = new ArrayList<>();
        Connection connection = DBConnection.getDBConnection();
        Statement statement;
        ResultSet rs;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM bikesharing.rents");
            while (rs.next()) {
                RentDataTransfer data = new RentDataTransfer();
                data.bike = BikeBuilder.decode(rs.getInt("bike"));
                data.id = rs.getLong("rentId");
                data.subCode = rs.getString("subCode");
                Timestamp startDate = rs.getTimestamp("startRent");
                Timestamp endDate = rs.getTimestamp("endRent");
                GregorianCalendar startCalendar = new GregorianCalendar();
                GregorianCalendar endCalendar = new GregorianCalendar();
                startCalendar.setTime(startDate);
                if (endDate == null) {
                    endCalendar = null;
                } else {
                    endCalendar.setTime(endDate);
                }
                data.startRent = startCalendar;
                data.endRent = endCalendar;
                ret.add(data);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new RuntimeException("Query error");
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't close the connection");
        }
        return ret;
    }
    
}
