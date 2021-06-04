package BikeSharing.Rack.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import BikeSharing.API.DBConnection;
import BikeSharing.Clamp.Clamp;
import BikeSharing.Clamp.ClampBuilder;
import BikeSharing.Clamp.DAO.ClampDao;
import BikeSharing.Clamp.DAO.ClampDataTransfer;

/**
 * Data access object for a rack in accordance with the DAO design pattern
 */

public class RackDao {

    /**
     * Returns an arraylist with the data transfer objects for all the racks
     * @return arraylist containing all the racks
     */

    public static ArrayList<RackDataTransfer> getRacks() {
        ArrayList<RackDataTransfer> returnArray = new ArrayList<>();

        Connection connection = DBConnection.getDBConnection();
        Statement query;
        try {
            query = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error in creating the statement");
        }
        ResultSet rs;
        try {
            rs = query.executeQuery("SELECT * FROM bikesharing.racks");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Query error");
        }
        boolean hasNext;
        try {
            hasNext = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Result set error");
        }
        while (hasNext) {
            long id;
            String name;
            try {
                id = rs.getLong("id");
                name = rs.getString("name");
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Error while reading data");
            }
            RackDataTransfer data = new RackDataTransfer();
            ArrayList<Clamp> clamps = new ArrayList<>();
            ArrayList<ClampDataTransfer> clampsData = ClampDao.getClampsByRack(id);
            for (ClampDataTransfer clampData : clampsData) {
                clamps.add(ClampBuilder.buildClamp(clampData));
            }
            data.id = id;
            data.name = name;
            data.clamps = clamps;
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

    /**
     * Returns the rack data transfer object with the passed id
     * @param id the id of the rack
     * @return the rack data
     */

    public static RackDataTransfer getRack(long id) {
        ArrayList<RackDataTransfer> racks = getRacks();
        for (RackDataTransfer rackData : racks) {
            if (rackData.id == id) {
                return rackData;
            }
        }
        return null;
    }

    /**
     * Deletes the specified rack
     * @param id the rack id
     */

    public static void deleteRack(long id) {
        Connection connection = DBConnection.getDBConnection();
        PreparedStatement query;
        try {
            query = connection.prepareStatement("DELETE FROM bikesharing.racks WHERE id = ?");
            query.setLong(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Prepared statement exception");
        }
        int rows;
        try {
            rows = query.executeUpdate();
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
     * Edits the rack with new info (assumes id as final)
     * @param data the updated data
     */

    public static void editRack(RackDataTransfer data) {
        Connection connection = DBConnection.getDBConnection();
        PreparedStatement query;
        try {
            query = connection.prepareStatement("UPDATE bikesharing.racks SET name = ? WHERE id = ?");
            query.setString(1, data.name);
            query.setLong(2, data.id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Prepared statement exception");
        }
        int rows;
        try {
            rows = query.executeUpdate();
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
     * Creates a new rack with the specified information (id will be automatically generated by the persistent layer)
     * @param name rack name
     */

    public static void createRack(String name) {
        Connection connection = DBConnection.getDBConnection();
        PreparedStatement query;
        try {
            query = connection.prepareStatement("INSERT INTO bikesharing.racks (name) VALUES (?)");
            query.setString(1, name);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Prepared statement exception");
        }
        int rows;
        try {
            rows = query.executeUpdate();
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
    
}
