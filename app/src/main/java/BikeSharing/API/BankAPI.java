package BikeSharing.API;

import BikeSharing.Util.CreditCard;

/**
 * Interacts with the banking system
 */

public class BankAPI {

    /**
     * Checks the credit card validity with the bank
     * @param c credit card
     * @return the card's validity
     */

    public static boolean checkCreditCard(CreditCard c) {
        if((c.getCardNumber() % 27) == 0) {
            return false;
        }
        return true;
    }

    /**
     * Add the payment to the credit card with the bank system
     * @param amount the amount to pay
     * @param c the credit card
     */

    public static void pay(float amount, CreditCard c) {
        System.out.println("Card number: " + c.getCardNumber() + " Cvv: " + c.getCvv() + " Amount: €" + amount);
        return;
    }
    
}
