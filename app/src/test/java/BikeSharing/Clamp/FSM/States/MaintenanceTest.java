package BikeSharing.Clamp.FSM.States;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.mockito.Mockito;

import BikeSharing.Bike.Bike;
import BikeSharing.Clamp.Clamp;
import BikeSharing.Subscription.Subscription;

public class MaintenanceTest {

    @Test
    public void parkTest() {
        Clamp mockClamp = Mockito.mock(Clamp.class);
        Bike bike = Mockito.mock(Bike.class);
        Maintenance.MAINTENANCE.park(mockClamp, bike);
        Mockito.verifyNoInteractions(mockClamp);
        Mockito.verifyNoInteractions(bike);
    }

    @Test
    public void takeTest() {
        Clamp mockClamp = Mockito.mock(Clamp.class);
        Bike bike = Maintenance.MAINTENANCE.take(mockClamp);
        Mockito.verifyNoInteractions(mockClamp);
        assertNull(bike);
    }

    @Test
    public void rentTest() {
        Clamp mockClamp = Mockito.mock(Clamp.class);
        Subscription mockSub = Mockito.mock(Subscription.class);
        Maintenance.MAINTENANCE.rent(mockClamp, mockSub);
        Mockito.verifyNoInteractions(mockClamp);
        Mockito.verifyNoInteractions(mockSub);
    }

    @Test
    public void startMaintenanceTest() {
        Clamp mockClamp = Mockito.mock(Clamp.class);
        Maintenance.MAINTENANCE.startMaintenance(mockClamp);
        Mockito.verifyNoInteractions(mockClamp);
    }

    @Test
    public void endMaintenanceTest() {
        Clamp mockClamp = Mockito.mock(Clamp.class);
        Maintenance.MAINTENANCE.endMaintenance(mockClamp);
        Mockito.verify(mockClamp).setState(LockedFull.LOCKEDFULL);
    }

    @Test
    public void isRentableTest() {
        assertFalse(Maintenance.MAINTENANCE.isRentable());
    }

    @Test
    public void encodeTest() {
        assertEquals(2, Maintenance.MAINTENANCE.encode());
    }

}