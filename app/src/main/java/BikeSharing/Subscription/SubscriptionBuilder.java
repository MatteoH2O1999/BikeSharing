package BikeSharing.Subscription;

import BikeSharing.Subscription.DAO.SubscriptionDataTransfer;

/**
 * Factory class for subscription from persistent layer
 */

public class SubscriptionBuilder {

    /**
     * Returns the subscription built from the persistent data
     * @param readData persistent data
     * @return built subscription
     */

    public static Subscription buildSubscription(SubscriptionDataTransfer readData) {

        switch (readData.type) {
            case 0:
                //Yearly subscription
                return new YearlySubscription(readData.card, readData.subCode, readData.password, readData.payrate, readData.expirationDate, readData.strikes);
            case 1:
                //Weekly subscription
                return new WeeklySubscription(readData.card, readData.subCode, readData.password, readData.payrate, readData.expirationDate, readData.strikes);
            case 2:
                //Daily subscription
                return new DailySubscription(readData.card, readData.subCode, readData.password, readData.payrate, readData.expirationDate, readData.strikes);
            default:
                return null;
        }

    }
    
}
