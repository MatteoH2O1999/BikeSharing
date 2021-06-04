package BikeSharing.Usertypes;

import BikeSharing.API.AdminAPI;
import BikeSharing.Payrate.Payrate;
import BikeSharing.Usertypes.Proof.AdministrationProof;

/**
 * Administration, able to edit rack and bikes
 */

public class Administration extends AbstractUser {

    /**
     * Proof of eligibility
     */

    private AdministrationProof proof;

    /**
     * Basic constructor
     * @param p proof of eligibility
     */

    public Administration(AdministrationProof p) {
        this.proof = p;
    }

    /**
     * 
     */

    @Override
    public boolean isEligible() {
        return AdminAPI.isAdministration(this.proof);
    }

    /**
     * 
     */

    @Override
    public Payrate getPayrate() {
        throw new RuntimeException("Unavailable method for this class");
    }
    
}
