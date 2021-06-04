package BikeSharing.Subscription.DAO;

import java.util.GregorianCalendar;

import BikeSharing.Payrate.Payrate;
import BikeSharing.Util.CreditCard;

/**
 * Data transfer class for the subscription following the DAO design pattern.
 */

public class SubscriptionDataTransfer {

    /**
     * Type of subscription
     */

    public int type;

    /**
     * Credit card info to be translated
     */

    public CreditCard card;

    /**
     * Subscription code
     */

    public String subCode;

    /**
     * Subscription password
     */

    public String password;

    /**
     * Payrate type to be encoded
     */

    public Payrate payrate;

    /**
     * Expiration date to be converted
     */

    public GregorianCalendar expirationDate;

    /**
     * Strikes for renting a bike for more than 2 hours
     */
    
    public int strikes;
    
}
