package BikeSharing.Subscription;

import java.util.GregorianCalendar;

import BikeSharing.Payrate.Payrate;
import BikeSharing.Subscription.Util.DuplicateCardException;
import BikeSharing.Subscription.Util.ExpirationException;
import BikeSharing.Subscription.Util.NotUniqueException;
import BikeSharing.Util.CreditCard;

/**
 * Yearly subscription. Expiration date is 1 year later from the creation of the subscription
 */

public class YearlySubscription extends Subscription {

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

    public YearlySubscription(CreditCard cCard, String code, String passwd, Payrate rate) throws NullPointerException, ExpirationException, NotUniqueException, DuplicateCardException {
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

    protected YearlySubscription(CreditCard cCard, String code, String passwd, Payrate rate, GregorianCalendar date, int strks) {
        super(cCard, code, passwd, rate, date, strks);
    }

    @Override
    public boolean isValid() {
        if(!checkLastReturned()) {
            return false;
        }
        if(!returnedInterval()) {
            return false;
        }
        return true;
    }

    @Override
    protected GregorianCalendar calcExpirationDate() {
        GregorianCalendar ret = new GregorianCalendar();
        ret.add(GregorianCalendar.YEAR, 1);
        return ret;
    }

    @Override
    protected int calcType() {
        return 0;
    }

    @Override
    public float subCost() {
        return (float)36.0;
    }
    
}
