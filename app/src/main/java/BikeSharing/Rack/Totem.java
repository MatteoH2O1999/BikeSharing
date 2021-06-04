package BikeSharing.Rack;

import BikeSharing.Bike.Bike;
import BikeSharing.Clamp.Clamp;
import BikeSharing.Rack.Util.BikeAlreadyRentedException;
import BikeSharing.Rack.Util.NoFreeBikesException;
import BikeSharing.Subscription.Subscription;
import BikeSharing.Subscription.Manager.SubscriptionManager;
import BikeSharing.Subscription.Util.DuplicateCardException;
import BikeSharing.Subscription.Util.ExpirationException;
import BikeSharing.Subscription.Util.InvalidSubscriptionException;
import BikeSharing.Subscription.Util.WrongLoginException;
import BikeSharing.Usertypes.AbstractUser;
import BikeSharing.Usertypes.Util.ProofException;
import BikeSharing.Util.CreditCard;

/**
 * Totem assigned to a rack
 */

public class Totem {

    /**
     * Rack
     */

    private final Rack rack;

    /**
     * Creates a totem assigned to the specified rack
     * @param r the rack
     */

    public Totem(Rack r) {
        this.rack = r;
    }

    /**
     * Tries to rent a bike of the specified type with the specified subscription code and password
     * @param b bike type
     * @param subCode subscription code
     * @param password password
     * @return the id of the clamp of the rented bike
     * @throws WrongLoginException if the login is invalid or the credentials are wrong
     * @throws InvalidSubscriptionException if the subscription is invalid for a new rent
     * @throws NoFreeBikesException if there are no free bikes of the selected type
     */

    public int rentBike(Bike b, String subCode, String password) throws WrongLoginException, InvalidSubscriptionException, NoFreeBikesException {
        Subscription userSub = SubscriptionManager.login(subCode, password);
        if (!userSub.isValid()) {
            throw new InvalidSubscriptionException();
        }
        Clamp userClamp = rack.getFreeBike(b);
        if (userClamp == null) {
            throw new NoFreeBikesException();
        }
        userClamp.rent(userSub);
        return userClamp.id;
    }

    /**
     * Allows the user to check if he returned the last rent correctly
     * @param subCode subscription code
     * @param password passsword
     * @return true if the last rented bike was returned successfully
     * @throws WrongLoginException is the login is invalid or the credentials are wrong
     */

    public boolean returnBike(String subCode, String password) throws WrongLoginException {
        Subscription userSub = SubscriptionManager.login(subCode, password);
        return userSub.checkLastReturned();
    }

    /**
     * Reports a damage to the bike parked at the specified clamp
     * @param clampID clamp id
     * @param subCode subscription code
     * @param password password
     * @throws WrongLoginException if the login is invalid or the credentials are wrong
     * @throws BikeAlreadyRentedException if the bike is already being rented to another subscription
     */

    public void reportDamage(int clampID, String subCode, String password) throws WrongLoginException, BikeAlreadyRentedException {
        Subscription userSub = SubscriptionManager.login(subCode, password);
        rack.lastRentDamaged(userSub, clampID);
    }

    /**
     * Registers a new subscription
     * @param password password
     * @param card credit card
     * @param user user type
     * @param subType subscritpion type
     * @return the subscription code
     * @throws NullPointerException if any of the parameters is null
     * @throws ProofException if the user is not eligible for the user class
     * @throws ExpirationException if the card isn't suitable for the selected sub type due to its expiring
     * @throws DuplicateCardException if the card is already used for another subscription
     */

    public String register(String password, CreditCard card, AbstractUser user, int subType) throws NullPointerException, ProofException, ExpirationException, DuplicateCardException {
        return SubscriptionManager.createSubscription(user, subType, password, card);
    }
    
}
