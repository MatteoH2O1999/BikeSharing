package BikeSharing.Subscription.Manager;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import BikeSharing.Subscription.DAO.RentDao;
import BikeSharing.Subscription.DAO.RentDataTransfer;
import BikeSharing.Subscription.DAO.SubscriptionDao;
import BikeSharing.Subscription.DAO.SubscriptionDataTransfer;

/**
 * Deamon that runs in the background to check for strikes and 24 hours fines
 */

public class Deamon extends Thread {

    /**
     * The loop will continue as long as this variable is true
     */

    private boolean keepAlive;

    /**
     * Initializer
     */

    public Deamon() {
        this.keepAlive = true;
    }

    /**
     * Stops the deamon
     */

    public void stopDeamon() {
        this.keepAlive = false;
    }

    @Override
    public void run() {
        while (keepAlive) {
            Collection<SubscriptionDataTransfer> subs = SubscriptionDao.getAllSubscriptions();
            checkFines(subs);
            checkStrikes(subs);
            try {
                sleep(TimeUnit.MINUTES.toMillis(1));
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * Checks for fines
     * @param subscriptions the collection of subscritpions
     */

    private void checkFines(Collection<SubscriptionDataTransfer> subscriptions) {
        for (SubscriptionDataTransfer subData : subscriptions) {
            RentDataTransfer lastRent = RentDao.getLastRent(subData.subCode);
            if (lastRent.startRent == null) {
                continue;
            }
            if (lastRent.endRent != null) {
                continue;
            }
            long startMillis = lastRent.startRent.getTimeInMillis();
            long currentMillis = new GregorianCalendar().getTimeInMillis();
            long intervalHours = TimeUnit.MILLISECONDS.toHours(currentMillis - startMillis);
            if (intervalHours > 24) {
                subData.card.addPayment(subData.payrate.getFine(lastRent.bike));
                SubscriptionDao.delete(subData.subCode);
            }
        }
    }

    /**
     * Checks for more than 3 strikes
     * @param subscriptions the collection of subscriptions
     */

    private void checkStrikes(Collection<SubscriptionDataTransfer> subscriptions) {
        for (SubscriptionDataTransfer subData : subscriptions) {
            if (subData.strikes >= 3) {
                SubscriptionDao.delete(subData.subCode);
            }
        }
    }
    
}
