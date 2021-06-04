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

public class ElectricBoosterSeatClampTest {

    @Test
    public void isCompatibleTrueTest() {
        ElectricBoosterSeatClamp clamp = new ElectricBoosterSeatClamp(mock(ClampDataTransfer.class));
        assertTrue(clamp.isCompatible(mock(ElectricBikeBoosterSeat.class)));
    }

    @Test
    public void isCompatibleFalseTest() {
        ElectricBoosterSeatClamp clamp = new ElectricBoosterSeatClamp(mock(ClampDataTransfer.class));
        assertFalse(clamp.isCompatible(mock(ElectricBike.class)));
        assertFalse(clamp.isCompatible(mock(NormalBike.class)));
    }

    @Test
    public void isCompatibleNullTest() {
        ElectricBoosterSeatClamp clamp = new ElectricBoosterSeatClamp(mock(ClampDataTransfer.class));
        assertFalse(clamp.isCompatible(null));
    }

    @Test
    public void fillBikeTest() {
        ElectricBoosterSeatClamp clamp = new ElectricBoosterSeatClamp(mock(ClampDataTransfer.class));
        assertEquals(ElectricBikeBoosterSeat.class, clamp.fillBike().getClass());
    }

}