package BikeSharing.Usertypes;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

import org.junit.Test;
import org.mockito.MockedStatic;

import BikeSharing.API.AdminAPI;
import BikeSharing.Usertypes.Proof.DataAnalystProof;

public class DataAnalystTest {

    @Test
    public void isEligibleTest() {
        DataAnalystProof mockProof = mock(DataAnalystProof.class);
        DataAnalyst dataAnalyst = new DataAnalyst(mockProof);
        try (MockedStatic<AdminAPI> mockApi = mockStatic(AdminAPI.class)) {
            dataAnalyst.isEligible();
            mockApi.verify(() -> AdminAPI.isDataAnalyst(mockProof));
        }
    }

    @Test(expected = RuntimeException.class)
    public void getPayrateTest() {
        DataAnalystProof mockProof = mock(DataAnalystProof.class);
        DataAnalyst dataAnalyst = new DataAnalyst(mockProof);
        dataAnalyst.getPayrate();
    }

}