package BikeSharing.Payrate;

/**
 * Factory class for payrate decoding
 */

public class PayrateBuilder {

    /**
     * Returns the decoded payrate
     * @param code the encoded payrate type
     * @return the payrate
     */

    public static Payrate decode(int code) {

        switch (code) {
            case 0:
                return new StandardRate();
            case 1:
                return new StudentRate();
            default:
                return null;
        }

    }
    
}
