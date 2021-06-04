package BikeSharing.Clamp.FSM.States;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import BikeSharing.Bike.Bike;
import BikeSharing.Clamp.Clamp;
import BikeSharing.Subscription.Subscription;

public class LockedFullTest {

    @Test
    public void parkTest() {
        Clamp mockClamp = Mockito.mock(Clamp.class);
        Bike bike = Mockito.mock(Bike.class);
        LockedFull.LOCKEDFULL.park(mockClamp, bike);
        Mockito.verifyNoInteractions(mockClamp);
    }

    @Test
    public void takeTest() {
        Clamp mockClamp = Mockito.mock(Clamp.class);
        Bike bike = LockedFull.LOCKEDFULL.take(mockClamp);
        Mockito.verifyNoInteractions(mockClamp);
        assertNull(bike);
    }

    @Test
    public void rentTest() {
        Clamp mockClamp = Mockito.mock(Clamp.class);
        Subscription mockSub = Mockito.mock(Subscription.class);
        LockedFull.LOCKEDFULL.rent(mockClamp, mockSub);
        InOrder order = Mockito.inOrder(mockClamp);
        Mockito.verifyNoInteractions(mockSub);
        order.verify(mockClamp).setRenter(mockSub);
        order.verify(mockClamp).rentProcedures();
        order.verify(mockClamp).setState(UnlockedFull.UNLOCKEDFULL);
    }

    @Test
    public void startMaintenanceTest() {
        Clamp mockClamp = Mockito.mock(Clamp.class);
        LockedFull.LOCKEDFULL.startMaintenance(mockClamp);
        Mockito.verify(mockClamp).setState(Maintenance.MAINTENANCE);
    }

    @Test
    public void endMaintenanceTest() {
        Clamp mockClamp = Mockito.mock(Clamp.class);
        LockedFull.LOCKEDFULL.endMaintenance(mockClamp);
        Mockito.verifyNoInteractions(mockClamp);
    }

    @Test
    public void isRentableTest() {
        assertTrue(LockedFull.LOCKEDFULL.isRentable());
    }

    @Test
    public void encodeTest() {
        assertEquals(1, LockedFull.LOCKEDFULL.encode());
    }

}