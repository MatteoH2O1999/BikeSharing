package BikeSharing.Clamp.FSM.States;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import BikeSharing.Bike.Bike;
import BikeSharing.Clamp.Clamp;
import BikeSharing.Subscription.Subscription;

public class UnlockedFreeTest {

    @Test
    public void parkTestCompatible() {
        Clamp mockClamp = Mockito.mock(Clamp.class);
        Bike bike = Mockito.mock(Bike.class);
        Mockito.when(mockClamp.isCompatible(bike)).thenReturn(true);
        UnlockedFree.UNLOCKEDFREE.park(mockClamp, bike);
        Mockito.verifyNoInteractions(bike);
        InOrder order = Mockito.inOrder(mockClamp);
        order.verify(mockClamp).isCompatible(bike);
        order.verify(mockClamp).setState(LockedFull.LOCKEDFULL);
        order.verify(mockClamp).setBike(bike);
        order.verify(mockClamp).endRent();
        order.verify(mockClamp).parkedLoop();
    }

    @Test
    public void parkTestNotCompatible() {
        Clamp mockClamp = Mockito.mock(Clamp.class);
        Bike bike = Mockito.mock(Bike.class);
        Mockito.when(mockClamp.isCompatible(bike)).thenReturn(false);
        UnlockedFree.UNLOCKEDFREE.park(mockClamp, bike);
        Mockito.verifyNoInteractions(bike);
        InOrder order = Mockito.inOrder(mockClamp);
        order.verify(mockClamp).isCompatible(bike);
        order.verify(mockClamp).setState(UnlockedFull.UNLOCKEDFULL);
        order.verify(mockClamp).setBike(bike);
    }

    @Test
    public void takeTest() {
        Clamp mockClamp = Mockito.mock(Clamp.class);
        Bike bike = UnlockedFree.UNLOCKEDFREE.take(mockClamp);
        Mockito.verifyNoInteractions(mockClamp);
        assertNull(bike);
    }

    @Test
    public void rentTest() {
        Clamp mockClamp = Mockito.mock(Clamp.class);
        Subscription mockSub = Mockito.mock(Subscription.class);
        UnlockedFree.UNLOCKEDFREE.rent(mockClamp, mockSub);
        Mockito.verifyNoInteractions(mockClamp);
        Mockito.verifyNoInteractions(mockSub);
    }

    @Test
    public void startMaintenanceTest() {
        Clamp mockClamp = Mockito.mock(Clamp.class);
        UnlockedFree.UNLOCKEDFREE.startMaintenance(mockClamp);
        Mockito.verifyNoInteractions(mockClamp);
    }

    @Test
    public void endMaintenanceTest() {
        Clamp mockClamp = Mockito.mock(Clamp.class);
        UnlockedFree.UNLOCKEDFREE.startMaintenance(mockClamp);
        Mockito.verifyNoInteractions(mockClamp);
    }

    @Test
    public void isRentableTest() {
        assertFalse(UnlockedFree.UNLOCKEDFREE.isRentable());
    }

    @Test
    public void encodeTest() {
        assertEquals(0, UnlockedFree.UNLOCKEDFREE.encode());
    }

}