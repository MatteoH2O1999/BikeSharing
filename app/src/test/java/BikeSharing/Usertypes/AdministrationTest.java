package BikeSharing.Usertypes;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

import org.junit.Test;
import org.mockito.MockedStatic;

import BikeSharing.API.AdminAPI;
import BikeSharing.Usertypes.Proof.AdministrationProof;

public class AdministrationTest {

    @Test
    public void isEligibleTest() {
        AdministrationProof mockProof = mock(AdministrationProof.class);
        Administration administration = new Administration(mockProof);
        try (MockedStatic<AdminAPI> mockApi = mockStatic(AdminAPI.class)) {
            administration.isEligible();
            mockApi.verify(() -> AdminAPI.isAdministration(mockProof));
        }
    }

    @Test(expected = RuntimeException.class)
    public void getPayrateTest() {
        AdministrationProof mockProof = mock(AdministrationProof.class);
        Administration administration = new Administration(mockProof);
        administration.getPayrate();
    }

}