package BikeSharing.Subscription;

import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import BikeSharing.Bike.Bike;
import BikeSharing.Payrate.Payrate;
import BikeSharing.Subscription.DAO.RentDao;
import BikeSharing.Subscription.DAO.RentDataTransfer;
import BikeSharing.Subscription.DAO.SubscriptionDao;
import BikeSharing.Subscription.DAO.SubscriptionDataTransfer;
import BikeSharing.Subscription.Util.DuplicateCardException;
import BikeSharing.Subscription.Util.ExpirationAlreadySetException;
import BikeSharing.Subscription.Util.ExpirationException;
import BikeSharing.Subscription.Util.InvalidSubscriptionException;
import BikeSharing.Subscription.Util.NotUniqueException;
import BikeSharing.Subscription.Util.WrongLoginException;
import BikeSharing.Util.CreditCard;
import BikeSharing.Util.CreditCardExpirationDate;

/**
 * Abstract model of a subscription
 */

public abstract class Subscription {

    /**
     * Credit card linked with the subscription
     */

    private CreditCard card;

    /**
     * Subscription code
     */

    private String subCode;

    /**
     * Subscription password
     */

    private String password;

    /**
     * Subscription payrate
     */

    private Payrate payrate;

    /**
     * Subscription expiration date
     */

    private GregorianCalendar expirationDate;

    /**
     * Subscription strikes (for not having returned the bike in 120 minutes)
     */

    private int strikes;

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

    public Subscription(CreditCard cCard, String code, String passwd, Payrate rate) throws NullPointerException, ExpirationException, NotUniqueException, DuplicateCardException {

        if ((cCard == null) | (code == null) | (passwd == null) | (rate == null)) {
            throw new NullPointerException();
        }

        this.expirationDate = calcExpirationDate();

        if (expirationDate != null) {
            int expYear = expirationDate.get(GregorianCalendar.YEAR);
            int expMonth = expirationDate.get(GregorianCalendar.MONTH);
    
            if(cCard.getCardExpiration().compareTo(new CreditCardExpirationDate(expYear, expMonth)) < 0) {
                throw new ExpirationException();
            }
        }

        this.card = cCard;
        this.subCode = code;
        this.password = passwd;
        this.payrate = rate;
        this.strikes = 0;
        SubscriptionDataTransfer data = new SubscriptionDataTransfer();
        data.card = this.card;
        data.expirationDate = this.expirationDate;
        data.password = this.password;
        data.payrate = this.payrate;
        data.strikes = this.strikes;
        data.subCode = this.subCode;
        data.type = this.calcType();
        SubscriptionDao.save(data);
        this.card.addPayment(subCost());
        return;

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

    protected Subscription(CreditCard cCard, String code, String passwd, Payrate rate, GregorianCalendar date, int strks) {
        this.card = cCard;
        this.expirationDate = date;
        this.password = passwd;
        this.payrate = rate;
        this.strikes = strks;
        this.subCode = code;
        return;
    }

    /**
     * Checks if the subscription is suitable to start a new rent
     * @return true if the subscription is valid
     */

    public abstract boolean isValid();

    /**
     * returns the creation cost of the subscription
     * @return the creation cost of the subscription
     */

    public abstract float subCost();

    /**
     * Computes the expiration date. Each implementation should use this method to define their length
     * @return the expiration date
     */

    protected abstract GregorianCalendar calcExpirationDate();

    /**
     * Start a new rent of the determined type of bike
     * @param bike rented bike
     * @throws InvalidSubscriptionException if the subscription is invalid
     */

    public void startRent(Bike bike) throws InvalidSubscriptionException {
        if (!this.isValid()) {
            throw new InvalidSubscriptionException();
        }
        RentDataTransfer newRent = new RentDataTransfer();
        newRent.bike = bike;
        newRent.subCode = this.subCode;
        newRent.startRent = new GregorianCalendar();
        RentDao.createRent(newRent);
        return;
    }

    /**
     * Ends the still open rent and registers at what time the bike was returned
     */

    public void endRent() {
        RentDataTransfer lastRent = RentDao.getLastRent(this.subCode);
        lastRent.endRent = new GregorianCalendar();
        int rentingTime = getRentingTime(lastRent.startRent, lastRent.endRent);
        float duePayment = payrate.getDuePayment(rentingTime, lastRent.bike);
        card.addPayment(duePayment);
        if (rentingTime > 120) {
            strike();
        }
        RentDao.saveRent(lastRent);
        return;
    }
    
    /**
     * Adds a strike to the subscription
     */
    
    private void strike() {
        this.strikes++;
        save();
        return;
    }

    /**
     * Computes the renting time in minutes
     * @param startTime the time at which the bike was rented
     * @param endTime the time at which the bike war returned
     * @return the renting time in minutes
     */

    private int getRentingTime(GregorianCalendar startTime, GregorianCalendar endTime) {
        long startMillis = startTime.getTimeInMillis();
        long endMillis = endTime.getTimeInMillis();
        long interval = endMillis - startMillis;
        long intervalMinutes = TimeUnit.MILLISECONDS.toMinutes(interval);
        return (int)intervalMinutes;
    }

    /**
     * Sets the expiration date only if it wasn't already set
     * @param date the new expiration date
     * @throws ExpirationAlreadySetException if it was already set
     */

    protected void setExpDate(GregorianCalendar date) throws ExpirationAlreadySetException {
        if (this.expirationDate != null) {
            throw new ExpirationAlreadySetException();
        }
        if (date == null) {
            return;
        }
        this.expirationDate = date;
        save();
        return;
    }

    /**
     * Gets the subscription code
     * @return subscription code
     */

    public String getSubCode() {
        return this.subCode;
    }

    /**
     * Checks if the subscription returned the last bike it rented
     * @return true if there are no active rents
     */

    public boolean checkLastReturned() {
        RentDataTransfer lastRent = RentDao.getLastRent(this.subCode);
        if (lastRent.startRent == null) {
            return true;
        }
        if(lastRent.endRent == null) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the time before this subscription returned a bike is more than 5 minutes
     * @return true if the subscription returned a bike more than 5 minutes ago
     */
    
    protected boolean returnedInterval() {
        RentDataTransfer lastRent = RentDao.getLastRent(this.subCode);
        if (lastRent.startRent == null) {
            return true;
        }
        if (lastRent.endRent == null) {
            return false;
        }
        long startMillis = lastRent.endRent.getTimeInMillis();
        long endMillis = new GregorianCalendar().getTimeInMillis();
        long interval = endMillis - startMillis;
        long intervalMinutes = TimeUnit.MILLISECONDS.toMinutes(interval);
        
        if (intervalMinutes > 5) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the credit card is valid
     * @return true if the credit card is valid
     */

    protected boolean creditValidity() {
        return card.checkValidity();
    }

    /**
     * Returns the encoding for the different subscription types
     * @return the int that represents the subscription type
     */

    protected abstract int calcType();

    /**
     * Saves the current subscription to the persistent layer
     */

    protected void save() {
        SubscriptionDataTransfer toSave = new SubscriptionDataTransfer();
        toSave.card = this.card;
        toSave.expirationDate = this.expirationDate;
        toSave.password = this.password;
        toSave.payrate = this.payrate;
        toSave.strikes = this.strikes;
        toSave.subCode = this.subCode;
        toSave.type = this.calcType();
        SubscriptionDao.update(toSave);
        return;
    }

    /**
     * Reads the persistent data to create the Subscription with the corresponding Subscription code and password
     * @param code Subscription code
     * @param passwd Subscription password
     * @return the corresponding subscription
     * @throws WrongLoginException if the credentials do not correspond to a valid login
     */

    public static Subscription read (String code, String passwd) throws WrongLoginException {
        SubscriptionDataTransfer readData = SubscriptionDao.read(code, passwd);
        return SubscriptionBuilder.buildSubscription(readData);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Subscription eq = (Subscription)obj;
        if ((this.subCode.equals(eq.subCode)) && (this.password.equals(eq.password))) {
            return true;
        }
        return false;
    }
    
}
