package BikeSharing.Payrate;

import BikeSharing.Bike.Bike;

/**
 * Abstract class that models a payrate
 */

public abstract class Payrate {

    /**
     * Returns the due payment of the rented bike
     * @param minutes renting time in minutes
     * @param bike rented bike (used to determine type of bike)
     * @return the due payment in euros
     */

    public abstract float getDuePayment(int minutes, Bike bike);

    /**
     * Returns the fine that the client has to pay if he doesn't return the bike in 24 hours
     * @param bike rented bike (used to determine type of bike)
     * @return the fine's cost in euros
     */

    public abstract float getFine(Bike bike);

    /**
     * Returns the encoding for the payrate
     * @return the encoding
     */

    public abstract int getEncoding();
    
}
