package BikeSharing.Usertypes.Proof;

/**
 * Proof that the user is eligible to be considered a standard user
 */

public class StandardUserProof {

    /**
     * Lazy proof
     */

    public boolean proof;

    /**
     * Lazy constructor
     * @param b boolean
     */

    public StandardUserProof(boolean b) {
        this.proof = b;
    }

}
