package BikeSharing.Usertypes;

import BikeSharing.Payrate.Payrate;

/**
 * Abstraction of a user of the sistem. Check if the user is eligible for a certain class of user
 */

public abstract class AbstractUser {

    /**
     * Checks if the user is eligible for a certain class of users
     * @return true if the user is eligible
     */

    public abstract boolean isEligible();

    /**
     * Returns the payrate for the class of user
     * @return the payrate for the class of user
     */

    public abstract Payrate getPayrate();

}
