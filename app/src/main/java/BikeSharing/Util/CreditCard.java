package BikeSharing.Util;

import java.time.LocalDate;

import BikeSharing.API.BankAPI;

/**
 * Credit card that can cheks its validity
 */

public class CreditCard {

    /**
     * Card number
     */

    private final long cardNumber;

    /**
     * Cvv code
     */

    private final int cvv;

    /**
     * Card expiration date
     */

    private final CreditCardExpirationDate expirationDate;

    /**
     * Creates a new credit card with the specified parameters
     * @param number the number of the credit card
     * @param cv2 the cvv code of the credit card
     * @param expYear the expiration year of the credit card
     * @param expMonth the expiration month of the credit card
     * @throws RuntimeException if a parameter is invalid
     */

    public CreditCard(long number, int cv2, int expYear, int expMonth) throws RuntimeException {

        if ((number < 0) | (cv2 < 100) | (cv2 > 999) | (expYear < 0) | (expMonth < 0) | (expMonth > 11)) {
            throw new RuntimeException("Invalid parameters");
        }

        this.cardNumber = number;
        this.cvv = cv2;
        this.expirationDate = new CreditCardExpirationDate(expYear, expMonth);

        return;

    }

    /**
     * Gets the card's number
     * @return the card's number
     */

    public long getCardNumber() {
        return this.cardNumber;
    }

    /**
     * Gets the card's cvv
     * @return the card's cvv
     */

    public int getCvv() {
        return this.cvv;
    }

    /**
     * Gets the card's expiration date
     * @return the card's expiration date
     */

    public CreditCardExpirationDate getCardExpiration() {
        return this.expirationDate;
    }

    /**
     * Checks this card's validity
     * @return thid card's validity
     */

    public boolean checkValidity() {
        LocalDate localdate = LocalDate.now();
        if ((localdate.getMonthValue() - 1 > 11) | (localdate.getMonthValue() - 1 < 0) | (localdate.getYear() < 0)) {
            throw new RuntimeException("Error in gathering current date");
        }
        CreditCardExpirationDate currentDate = new CreditCardExpirationDate(localdate.getYear(), localdate.getMonthValue() - 1);
        if (this.expirationDate.compareTo(currentDate) == -1) {
            return false;
        }
        return BankAPI.checkCreditCard(this);
    }

    /**
     * Add payment request to the credit card
     * @param amount the amount to request
     */

    public void addPayment(float amount) {
        BankAPI.pay(amount, this);
        return;
    }
    
}
