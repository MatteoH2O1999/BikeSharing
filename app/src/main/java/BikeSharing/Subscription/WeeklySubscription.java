package BikeSharing.Subscription;

import java.util.GregorianCalendar;

import BikeSharing.Payrate.Payrate;
import BikeSharing.Subscription.Util.DuplicateCardException;
import BikeSharing.Subscription.Util.ExpirationAlreadySetException;
import BikeSharing.Subscription.Util.ExpirationException;
import BikeSharing.Subscription.Util.NotUniqueException;
import BikeSharing.Util.CreditCard;

/**
 * Subscription that lasts for 7 days
 */

public class WeeklySubscription extends OccasionalSubscription {

    /**
     * Tries to register a subscription with the given parameters
     * @param cCard Credit card
     * @param code Subscription code
     * @param passwd Subscription password
     * @param rate Payrate
     * @throws NullPointerException if any of the parameters are null
     * @throws ExpirationException if the credit card expiration is not suitable for the subscription
     * @throws NotUniqueException if the code is already used in the database
     * @throws DuplicateCardException if the card is not unique
     */

    public WeeklySubscription(CreditCard cCard, String code, String passwd, Payrate rate) throws NullPointerException, ExpirationException, NotUniqueException, DuplicateCardException {
        super(cCard, code, passwd, rate);
    }

    /**
     * Used to recreate the subscription from the persistent data
     * @param cCard Credit card
     * @param code Subscription code
     * @param passwd Subscription password
     * @param rate Payrate
     * @param date Expiration Date
     * @param strks Strikes
     */

    protected WeeklySubscription(CreditCard cCard, String code, String passwd, Payrate rate, GregorianCalendar date, int strks) {
        super(cCard, code, passwd, rate, date, strks);
    }

    @Override
    protected void activate() throws ExpirationException {
        if (!creditValidity()) {
            throw new ExpirationException();
        }
        GregorianCalendar date = new GregorianCalendar();
        date.add(GregorianCalendar.DATE, 7);
        try {
            setExpDate(date);
        } catch (ExpirationAlreadySetException e) {
            return;
        }
    }

    @Override
    protected int calcType() {
        return 1;
    }

    @Override
    public float subCost() {
        return (float)9.0;
    }
    
}
