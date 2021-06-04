package BikeSharing.Subscription.DAO;

import java.util.GregorianCalendar;

import BikeSharing.Bike.Bike;

/**
 * Data transfer class for the rent following the DAO design pattern.
 */

public class RentDataTransfer {

    /**
     * Rent id
     */

    public long id;

    /**
     * Renting subscription code
     */

    public String subCode;
    
    /**
     * Start of the renting period
     */

    public GregorianCalendar startRent;

    /**
     * Rented bike (to be encoded and used to save the type of bike)
     */

    public Bike bike;

    /**
     * End of the renting period
     */

    public GregorianCalendar endRent;
    
}
