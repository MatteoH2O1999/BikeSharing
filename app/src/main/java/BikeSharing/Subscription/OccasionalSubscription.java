package BikeSharing.Subscription;

import java.util.GregorianCalendar;

import BikeSharing.Payrate.Payrate;
import BikeSharing.Subscription.DAO.SubscriptionDao;
import BikeSharing.Subscription.Util.DuplicateCardException;
import BikeSharing.Subscription.Util.ExpirationAlreadySetException;
import BikeSharing.Subscription.Util.ExpirationException;
import BikeSharing.Subscription.Util.NotUniqueException;
import BikeSharing.Util.CreditCard;

/**
 * Abstract model of an occasional subscription, which gets activated when the validity check gets called.
 */

public abstract class OccasionalSubscription extends Subscription {

    /**
     * Tries to register a subscription with the given parameters
     * @param cCard Credit card
     * @param code Subscription code
     * @param passwd Subscription password
     * @param rate Payrate
     * @throws NullPointerException if any of the parameters are null
     * @throws ExpirationException if the credit card expiration is not suitable for the subscription
     * @throws NotUniqueException if the code is already used in the database
     * @throws DuplicateCardException if the card is already used
     */

    public OccasionalSubscription(CreditCard cCard, String code, String passwd, Payrate rate) throws NullPointerException, ExpirationException, NotUniqueException, DuplicateCardException {
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

    protected OccasionalSubscription(CreditCard cCard, String code, String passwd, Payrate rate, GregorianCalendar date, int strks) {
        super(cCard, code, passwd, rate, date, strks);
    }

    @Override
    public boolean isValid(){
        if(!isActive()) {
            try {
                activate();
            } catch (ExpirationException e1) {
                SubscriptionDao.delete(this.getSubCode());
                return false;
            }
            save();
            return true;
        }
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
        return null;
    }

    /**
     * Activates the subscription setting the expiration date with the correct one for each type of subscription
     * @throws ExpirationException if the card is no longer valid
     */

    protected abstract void activate() throws ExpirationException;

    /**
     * Checks is the subscription is already activated
     * @return true if the subscription is already activated
     */

    protected boolean isActive() {
        try {
            setExpDate(null);
        } catch (ExpirationAlreadySetException e) {
            return true;
        }
        return false;
    }
    
}
