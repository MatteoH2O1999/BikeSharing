package BikeSharing.Clamp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.Test;

import BikeSharing.Bike.Bike;
import BikeSharing.Bike.ElectricBike;
import BikeSharing.Bike.NormalBike;
import BikeSharing.Clamp.DAO.ClampDataTransfer;

public class NormalClampTest {

    @Test
    public void isCompatibleFalseTest() {
        ClampDataTransfer mockData = mock(ClampDataTransfer.class);
        NormalClamp clamp = new NormalClamp(mockData);
        Bike bike = new ElectricBike(20);
        assertFalse(clamp.isCompatible(bike));
    }

    @Test
    public void isCompatibleTrueTest() {
        ClampDataTransfer mockData = mock(ClampDataTransfer.class);
        NormalClamp clamp = new NormalClamp(mockData);
        Bike bike = new NormalBike();
        assertTrue(clamp.isCompatible(bike));
    }

    @Test
    public void isCompatibleNullTest() {
        ClampDataTransfer mockData = mock(ClampDataTransfer.class);
        NormalClamp clamp = new NormalClamp(mockData);
        assertFalse(clamp.isCompatible(null));
    }

    @Test
    public void fillBikeTest() {
        ClampDataTransfer mockData = mock(ClampDataTransfer.class);
        NormalClamp clamp = new NormalClamp(mockData);
        assertEquals(NormalBike.class, clamp.fillBike().getClass());
    }

    @Test
    public void parkedLoopTest() {
        ClampDataTransfer mockData = mock(ClampDataTransfer.class);
        NormalClamp clamp = new NormalClamp(mockData);
        Bike mockBike = mock(NormalBike.class);
        clamp.parkedLoop();
        verifyNoInteractions(mockBike);
    }

    @Test
    public void rentProceduresTest() {
        ClampDataTransfer mockData = mock(ClampDataTransfer.class);
        NormalClamp clamp = new NormalClamp(mockData);
        Bike mockBike = mock(NormalBike.class);
        clamp.rentProcedures();
        verifyNoInteractions(mockBike);
    }

}