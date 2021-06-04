package BikeSharing.API;

import BikeSharing.Usertypes.Proof.StudentProof;

/**
 * Interacts with the school system
 */

public class SchoolAPI {

    /**
     * Returns true if the proof is valid
     * @param proof proof of eligibility
     * @return true if the proof is valid
     */

    public static boolean isStudent(StudentProof proof) {
        return proof.proof;
    }
    
}
