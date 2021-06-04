package BikeSharing.Clamp.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import BikeSharing.API.DBConnection;
import BikeSharing.Clamp.FSM.StateDecoder;
import BikeSharing.Clamp.FSM.States.UnlockedFree;

/**
 * Data transfer object in accordance with the DAO design pattern
 */

public class ClampDao {

    /**
     * Creates a new clamp with the specified parameters
     * @param rackID the id of the rack
     * @param type the clamp type
     */

    public static void createClamp(long rackID, int type) {
        Connection connection = DBConnection.getDBConnection();
        PreparedStatement query;
        int tempID = 1;
        int rows = 1;
        try {
            query = connection.prepareStatement("SELECT COUNT(*) FROM bikesharing.clamps WHERE rackid = ? AND id = ?");
            query.setLong(1, rackID);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Query error");
        }
        while (rows > 0) {
            try {
                query.setInt(2, tempID);
                ResultSet rs = query.executeQuery();
                rs.next();
                rows = rs.getInt(1);
                tempID++;
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
        int id = tempID - 1;
        PreparedStatement createStatement;
        try {
            createStatement = connection.prepareStatement("INSERT INTO bikesharing.clamps VALUES (?, ?, ?, ?)");
            createStatement.setInt(1, id);
            createStatement.setLong(2, rackID);
            createStatement.setInt(3, UnlockedFree.UNLOCKEDFREE.encode());
            createStatement.setInt(4, type);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Query error");
        }
        try {
            rows = createStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Insert error");
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
     * Deletes the specified clamp
     * @param clampID Clamp id
     * @param rackID Rack id
     */

    public static void deleteClamp(int clampID, long rackID) {
        Connection connection = DBConnection.getDBConnection();
        PreparedStatement statement;
        int rows = 0;
        try {
            statement = connection.prepareStatement("DELETE FROM bikesharing.clamps WHERE id = ? AND rackid = ?");
            statement.setInt(1, clampID);
            statement.setLong(2, rackID);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Query error");
        }
        try {
            rows = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Delete error");
        }
        if (rows != 1) {
            throw new RuntimeException("Unexpected rows affected");
        }
    }

    /**
     * Updates an existing clamp
     * @param data updated clamp data
     */

    public static void updateClamp(ClampDataTransfer data) {
        Connection connection = DBConnection.getDBConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("UPDATE bikesharing.clamps SET state = ? WHERE id = ? AND rackid = ?");
            statement.setInt(1, data.state.encode());
            statement.setInt(2, data.clampID);
            statement.setLong(3, data.rackID);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Query error");
        }
        int rows = 0;
        try {
            rows = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("update error");
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
     * Returns a collection with all the clamps data transfer objects for the specified rack
     * @param rackID rack id
     * @return a collection with all the clamps data transfer objects for the specified rack
     */

    public static ArrayList<ClampDataTransfer> getClampsByRack(long rackID) {
        ArrayList<ClampDataTransfer> returnArray = new ArrayList<>();
        Connection connection = DBConnection.getDBConnection();
        PreparedStatement query;
        try {
            query = connection.prepareStatement("SELECT * FROM bikesharing.clamps WHERE rackid = ?");
            query.setLong(1, rackID);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while creating query");
        }
        ResultSet rs;
        try {
            rs = query.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while executing the query");
        }
        boolean hasNext;
        try {
            hasNext = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Result set error");
        }
        while (hasNext) {
            ClampDataTransfer data = new ClampDataTransfer();
            int state;
            int id;
            int type;
            try {
                state = rs.getInt("state");
                id = rs.getInt("id");
                type = rs.getInt("type");
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Result set error");
            }
            data.clampID = id;
            data.rackID = rackID;
            data.state = StateDecoder.decodeState(state);
            data.type = type;
            returnArray.add(data);
            try {
                hasNext = rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Result set error");
            }
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't close the connection");
        }
        return returnArray;
    }

}
