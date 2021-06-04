package BikeSharing.Usertypes;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import BikeSharing.Payrate.StandardRate;
import BikeSharing.Usertypes.Proof.StandardUserProof;

public class StandardUserTest {

    @Test
    public void isEligibleTestTrue() {
        StandardUserProof proof = new StandardUserProof(true);
        StandardUser user = new StandardUser(proof);
        assertEquals(proof.proof, user.isEligible());
    }

    @Test
    public void isEligibleTestFalse() {
        StandardUserProof proof = new StandardUserProof(false);
        StandardUser user = new StandardUser(proof);
        assertEquals(proof.proof, user.isEligible());
    }

    @Test
    public void getPayrateTest() {
        StandardUserProof mockProof = mock(StandardUserProof.class);
        StandardUser user = new StandardUser(mockProof);
        assertEquals(StandardRate.class, user.getPayrate().getClass());
    }

}