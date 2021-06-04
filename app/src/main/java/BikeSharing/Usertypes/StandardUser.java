package BikeSharing.Usertypes;

import BikeSharing.Payrate.Payrate;
import BikeSharing.Payrate.StandardRate;
import BikeSharing.Usertypes.Proof.StandardUserProof;

/**
 * Standard user
 */

public class StandardUser extends AbstractUser {

    /**
     * Proof of eligibility for standard user
     */

    private StandardUserProof proof;

    /**
     * Basic constructor
     * @param p proof of eligibility
     */

    public StandardUser(StandardUserProof p) {
        this.proof = p;
    }

    /**
     * 
     */

    @Override
    public boolean isEligible() {
        return proof.proof;
    }

    /**
     * 
     */

    @Override
    public Payrate getPayrate() {
        return new StandardRate();
    }
    
}
