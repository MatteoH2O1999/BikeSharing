package BikeSharing.Subscription.Manager;

import org.apache.commons.lang3.RandomStringUtils;

import BikeSharing.Payrate.Payrate;
import BikeSharing.Subscription.DailySubscription;
import BikeSharing.Subscription.Subscription;
import BikeSharing.Subscription.WeeklySubscription;
import BikeSharing.Subscription.YearlySubscription;
import BikeSharing.Subscription.Util.DuplicateCardException;
import BikeSharing.Subscription.Util.ExpirationException;
import BikeSharing.Subscription.Util.NotUniqueException;
import BikeSharing.Subscription.Util.WrongLoginException;
import BikeSharing.Usertypes.AbstractUser;
import BikeSharing.Usertypes.Util.ProofException;
import BikeSharing.Util.CreditCard;

/**
 * Manager class for subscriptions.  It handles sign in and log in
 */

public class SubscriptionManager {

    /**
     * Deamon to check for strikes and fines
     */

    private static Deamon d;

    /**
     * Logins with the subscription code and password
     * @param subCode Subscription code
     * @param password Password
     * @return the subscription
     * @throws WrongLoginException if the credentials are wrong or do not correspond to an existing subscription
     */

    public static Subscription login(String subCode, String password) throws WrongLoginException {
        return Subscription.read(subCode, password);
    }

    /**
     * Creates a subscription and returns to the user the subscription code (factory method)
     * @param userType user type
     * @param subType subscription type
     * @param password password
     * @param card credit card
     * @return the subscription code
     * @throws ProofException if the user is not eligible for the user class
     * @throws DuplicateCardException if the card is already used for another subscription
     * @throws ExpirationException if the card isn't suitable for the subscription type due to its expiring
     * @throws NullPointerException if any of the arguments is null
     */

    public static String createSubscription(AbstractUser userType, int subType, String password, CreditCard card) throws ProofException, NullPointerException, ExpirationException, DuplicateCardException {
        if(!userType.isEligible()) {
            throw new ProofException();
        }
        if (!card.checkValidity()) {
            throw new ExpirationException();
        }
        Payrate rate = userType.getPayrate();
        String tmpCode;
        String subCode = null;
        while (subCode == null) {
            tmpCode = newSubCode();
            try {
                switch (subType) {
                    case 0:
                        new YearlySubscription(card, tmpCode, password, rate);
                        break;
                    case 1:
                        new WeeklySubscription(card, tmpCode, password, rate);
                        break;
                    case 2:
                        new DailySubscription(card, tmpCode, password, rate);
                        break;
                    default:
                        return null;
                }
                subCode = tmpCode;
            } catch (NotUniqueException e) {
                //New iteration with new subscription code until unique
                continue;
            }
        }
        return subCode;
    }

    /**
     * Creates a new subscription code randomly
     * @return the new subscription code
     */

    private static String newSubCode() {
        return RandomStringUtils.randomAlphanumeric(20);
    }

    /**
     * Launches deamons to monitor strikes and 24 hours fines
     */

    public static void startDeamon() {
        if ((d != null) && (d.isAlive())) {
            return;
        }
        d = new Deamon();
        d.start();
    }

    /**
     * Stops deamons to monitor strikes and 24 hours fines
     */

    public static void stopDeamon() {
        d.stopDeamon();
    }
    
}
