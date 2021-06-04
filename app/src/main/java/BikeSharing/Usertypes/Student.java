package BikeSharing.Usertypes;

import BikeSharing.API.SchoolAPI;
import BikeSharing.Payrate.Payrate;
import BikeSharing.Payrate.StudentRate;
import BikeSharing.Usertypes.Proof.StudentProof;

/**
 * Student
 */

public class Student extends AbstractUser {

    /**
     * Proof of eligibility
     */

    private StudentProof proof;

    /**
     * Basic contructor
     * @param p proof of eligibility
     */

    public Student(StudentProof p) {
        this.proof = p;
    }

    /**
     * 
     */

    @Override
    public boolean isEligible() {
        return SchoolAPI.isStudent(this.proof);
    }

    /**
     * 
     */

    @Override
    public Payrate getPayrate() {
        return new StudentRate();
    }
    
}
