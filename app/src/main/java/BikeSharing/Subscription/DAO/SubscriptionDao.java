package BikeSharing.Subscription.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import BikeSharing.API.DBConnection;
import BikeSharing.Payrate.PayrateBuilder;
import BikeSharing.Subscription.Util.DuplicateCardException;
import BikeSharing.Subscription.Util.NotUniqueException;
import BikeSharing.Subscription.Util.WrongLoginException;
import BikeSharing.Util.CreditCard;

/**
 * Data access object for the subscription following the DAO design pattern
 */

public class SubscriptionDao {

    /**
     * Saves the subscription's persistent data
     * @param data the data to save
     * @throws NotUniqueException if the subscription code is found but with a different password (meaning the new subscription is trying to use a code that is already used)
     * @throws DuplicateCardException if the card is already used
     */

    public static void save(SubscriptionDataTransfer data) throws NotUniqueException, DuplicateCardException {
        Connection connection = DBConnection.getDBConnection();
        PreparedStatement statement;
        ResultSet rs;
        int rows;
        try {
            statement = connection.prepareStatement("SELECT COUNT(*) FROM bikesharing.subscriptions WHERE subCode = ?");
            statement.setString(1, data.subCode);
            rs = statement.executeQuery();
            rs.next();
            rows = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Query error");
        }
        if (rows != 0) {
            throw new NotUniqueException();
        }
        try {
            statement = connection.prepareStatement("SELECT COUNT(*) FROM bikesharing.subscriptions WHERE cardNumber = ? AND cvv = ?");
            statement.setLong(1, data.card.getCardNumber());
            statement.setInt(2, data.card.getCvv());
            rs = statement.executeQuery();
            rs.next();
            rows = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Query error");
        }
        if (rows != 0) {
            throw new DuplicateCardException();
        }
        try {
            statement = connection.prepareStatement("INSERT INTO bikesharing.subscriptions VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, data.subCode);
            statement.setString(2, data.password);
            if (data.expirationDate == null) {
                statement.setTimestamp(3, null);
            } else {
                statement.setTimestamp(3, new Timestamp(data.expirationDate.getTimeInMillis()));
            }
            statement.setInt(4, data.strikes);
            statement.setInt(5, data.type);
            statement.setInt(6, data.payrate.getEncoding());
            statement.setLong(7, data.card.getCardNumber());
            statement.setInt(8, data.card.getCvv());
            statement.setTimestamp(9, new Timestamp(new GregorianCalendar(data.card.getCardExpiration().year, data.card.getCardExpiration().month, 1).getTimeInMillis()));
            rows = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Query error");
        }
        if (rows != 1) {
            throw new RuntimeException("Unexpected rows affected");
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't close the connection");
        }
    }

    /**
     * Updates the subscription's persistent data
     * @param data the data to save
     */

    public static void update(SubscriptionDataTransfer data) {
        Connection connection = DBConnection.getDBConnection();
        PreparedStatement statement;
        int rows;
        try {
            statement = connection.prepareStatement("UPDATE bikesharing.subscriptions SET strikes = ?, expirationDate = ? WHERE subCode = ?");
            statement.setInt(1, data.strikes);
            statement.setTimestamp(2, new Timestamp(data.expirationDate.getTimeInMillis()));
            statement.setString(3, data.subCode);
            rows = statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new RuntimeException("Query error");
        }
        if (rows != 1) {
            throw new RuntimeException("Unexpected rows affected");
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't close the connection");
        }
    }

    /**
     * Reads the data from the persistent layer 
     * @param subcode subscription code
     * @param password subscription password
     * @return the subscription data
     * @throws WrongLoginException if the credentials do not correspond to a valid login
     */

    public static SubscriptionDataTransfer read(String subcode, String password) throws WrongLoginException {
        SubscriptionDataTransfer data = new SubscriptionDataTransfer();
        Connection connection = DBConnection.getDBConnection();
        PreparedStatement statement;
        int rows = 0;
        ResultSet rs;
        try {
            statement = connection.prepareStatement("SELECT COUNT(*) FROM bikesharing.subscriptions WHERE subCode = ? AND password = ?");
            statement.setString(1, subcode);
            statement.setString(2, password);
            rs = statement.executeQuery();
            rs.next();
            rows = rs.getInt(1);
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new RuntimeException("Query error");
        }
        if (rows != 1) {
            throw new WrongLoginException();
        }
        try {
            statement = connection.prepareStatement("SELECT * FROM bikesharing.subscriptions WHERE subCode = ?");
            statement.setString(1, subcode);
            rs = statement.executeQuery();
            rs.next();
            GregorianCalendar c = new GregorianCalendar();
            c.setTime(rs.getTimestamp("cardExpiration"));
            data.card = new CreditCard(rs.getLong("cardNumber"), rs.getInt("cvv"), c.get(GregorianCalendar.YEAR), c.get(GregorianCalendar.MONTH));
            Timestamp d = rs.getTimestamp("expirationDate");
            if (d == null) {
                c = null;
            } else {
                c.setTime(d);
            }
            data.expirationDate = c;
            data.password = rs.getString("password");
            data.payrate = PayrateBuilder.decode(rs.getInt("payrate"));
            data.strikes = rs.getInt("strikes");
            data.subCode = rs.getString("subCode");
            data.type = rs.getInt("type");
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new RuntimeException("Read error");
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
     * Deletes the subscription with the passed subscription code
     * @param subCode the subscription code
     */

    public static void delete(String subCode) {
        Connection connection = DBConnection.getDBConnection();
        PreparedStatement statement;
        int rows = 0;
        try {
            statement = connection.prepareStatement("DELETE FROM bikesharing.subscriptions WHERE subCode = ?");
            statement.setString(1, subCode);
            rows = statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new RuntimeException("Query error");
        }
        if (rows != 1) {
            throw new RuntimeException("Unexpected rows affected");
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't close the connection");
        }
    }

    /**
     * Creates a list that contains all the subscriptions
     * @return the list of subscriptions
     */

    public static ArrayList<SubscriptionDataTransfer> getAllSubscriptions() {
        ArrayList<SubscriptionDataTransfer> ret = new ArrayList<>();
        Connection connection = DBConnection.getDBConnection();
        ResultSet rs;
        Statement statement;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM bikesharing.subscriptions");
            while (rs.next()) {
                SubscriptionDataTransfer data = new SubscriptionDataTransfer();
                GregorianCalendar c = new GregorianCalendar();
                c.setTime(rs.getTimestamp("cardExpiration"));
                data.card = new CreditCard(rs.getLong("cardNumber"), rs.getInt("cvv"), c.get(GregorianCalendar.YEAR), c.get(GregorianCalendar.MONTH));
                Timestamp d = rs.getTimestamp("expirationDate");
                if (d == null) {
                    c = null;
                } else {
                    c.setTime(d);
                }
                data.expirationDate = c;
                data.password = rs.getString("password");
                data.payrate = PayrateBuilder.decode(rs.getInt("payrate"));
                data.strikes = rs.getInt("strikes");
                data.subCode = rs.getString("subCode");
                data.type = rs.getInt("type");
                ret.add(data);
            }
        } catch (SQLException e2) {
            e2.printStackTrace();
            throw new RuntimeException("Read error");
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
