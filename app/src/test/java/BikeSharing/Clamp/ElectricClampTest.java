package BikeSharing.Clamp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import BikeSharing.Bike.ElectricBike;
import BikeSharing.Bike.ElectricBikeBoosterSeat;
import BikeSharing.Bike.NormalBike;
import BikeSharing.Clamp.DAO.ClampDataTransfer;

public class ElectricClampTest {

    @Test
    public void isCompatibleTrueTest() {
        ClampDataTransfer mockData = mock(ClampDataTransfer.class);
        ElectricClamp clamp = new ElectricClamp(mockData);
        ElectricBike mockBike = mock(ElectricBike.class);
        assertTrue(clamp.isCompatible(mockBike));
    }

    @Test
    public void isCompatibleFalseTest() {
        ClampDataTransfer mockData = mock(ClampDataTransfer.class);
        ElectricClamp clamp = new ElectricClamp(mockData);
        assertFalse(clamp.isCompatible(mock(ElectricBikeBoosterSeat.class)));
        assertFalse(clamp.isCompatible(mock(NormalBike.class)));
    }

    @Test
    public void isCompatibleNullTest() {
        ClampDataTransfer mockData = mock(ClampDataTransfer.class);
        ElectricClamp clamp = new ElectricClamp(mockData);
        assertFalse(clamp.isCompatible(null));
    }

    @Test
    public void fillBikeTest() {
        ClampDataTransfer mockData = mock(ClampDataTransfer.class);
        ElectricClamp clamp = new ElectricClamp(mockData);
        assertEquals(ElectricBike.class, clamp.fillBike().getClass());
    }

}