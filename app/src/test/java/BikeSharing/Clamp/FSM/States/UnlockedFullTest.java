package BikeSharing.Clamp.FSM.States;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import BikeSharing.Bike.Bike;
import BikeSharing.Clamp.Clamp;
import BikeSharing.Subscription.Subscription;

public class UnlockedFullTest {

    @Test
    public void parkTest() {
        Clamp mockClamp = Mockito.mock(Clamp.class);
        Bike bike = Mockito.mock(Bike.class);
        UnlockedFull.UNLOCKEDFULL.park(mockClamp, bike);
        Mockito.verifyNoInteractions(mockClamp);
        Mockito.verifyNoInteractions(bike);
    }

    @Test
    public void takeTest() {
        Clamp mockClamp = Mockito.mock(Clamp.class);
        Bike stubBike = Mockito.mock(Bike.class);
        Mockito.when(mockClamp.getBike()).thenReturn(stubBike);
        Bike returned = UnlockedFull.UNLOCKEDFULL.take(mockClamp);
        InOrder order = Mockito.inOrder(mockClamp);
        order.verify(mockClamp).setState(UnlockedFree.UNLOCKEDFREE);
        order.verify(mockClamp).getBike();
        assertEquals(stubBike, returned);
    }

    @Test
    public void rentTest() {
        Clamp mockClamp = Mockito.mock(Clamp.class);
        Subscription mockSub = Mockito.mock(Subscription.class);
        UnlockedFull.UNLOCKEDFULL.rent(mockClamp, mockSub);
        Mockito.verifyNoInteractions(mockClamp);
        Mockito.verifyNoInteractions(mockSub);
    }

    @Test
    public void startMaintenanceTest() {
        Clamp mockClamp = Mockito.mock(Clamp.class);
        UnlockedFull.UNLOCKEDFULL.startMaintenance(mockClamp);
        Mockito.verifyNoInteractions(mockClamp);
    }

    @Test
    public void endMaintenanceTest() {
        Clamp mockClamp = Mockito.mock(Clamp.class);
        UnlockedFull.UNLOCKEDFULL.endMaintenance(mockClamp);
        Mockito.verifyNoInteractions(mockClamp);
    }

    @Test
    public void isRentableTest() {
        assertFalse(UnlockedFull.UNLOCKEDFULL.isRentable());
    }

    @Test
    public void encodeTest() {
        assertEquals(0, UnlockedFull.UNLOCKEDFULL.encode());
    }

}