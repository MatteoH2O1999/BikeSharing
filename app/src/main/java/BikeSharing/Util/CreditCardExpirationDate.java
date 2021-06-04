package BikeSharing.Util;

/**
 * Data class used to store expiration date of a credit card.
 */

public class CreditCardExpirationDate implements Comparable<CreditCardExpirationDate> {

    /**
     * Year of expiration
     */

    public int year;

    /**
     * Month of expiration (needs to be between 0 and 11)
     */

    public int month;

    /**
     * Creates an expiration date with the passed parameters
     * @param y Expiration year
     * @param m Expiration month
     */

    public CreditCardExpirationDate(int y, int m) {
        this.year = y;
        this.month = m;
        return;
    }

    @Override
    public int compareTo(CreditCardExpirationDate o) {
        if (this.year < o.year) {
            return -1;
        } else if (this.year > o.year) {
            return 1;
        } else {
            if (this.month < o.month) {
                return -1;
            } else if (this.month > o.month) {
                return 1;
            } else {
                return 0;
            }
        }
    }
    
}
