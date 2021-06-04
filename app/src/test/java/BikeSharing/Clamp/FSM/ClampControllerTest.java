package BikeSharing.Clamp.FSM;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import BikeSharing.Bike.Bike;
import BikeSharing.Subscription.Subscription;
import BikeSharing.Subscription.Util.InvalidSubscriptionException;

public class ClampControllerTest {

    @Test
    public void constructorTest() {
        ClampController controller = new ClampController();
        assertNull(controller.readBike());
    }

    @Test
    public void getBikeTestNotNull() {
        ClampController controller = new ClampController();
        Bike mockBike = Mockito.mock(Bike.class);
        controller.setBike(mockBike);
        assertEquals(mockBike, controller.readBike());
        assertEquals(mockBike, controller.getBike());
        assertNull(controller.readBike());
    }

    @Test
    public void getBikeTestNull() {
        ClampController controller = new ClampController();
        controller.setBike(null);
        assertNull(controller.readBike());
        assertNull(controller.getBike());
        assertNull(controller.readBike());
    }

    @Test
    public void setBikeNotNull() {
        ClampController controller = new ClampController();
        assertNull(controller.readBike());
        Bike mockBike = Mockito.mock(Bike.class);
        controller.setBike(mockBike);
        assertEquals(mockBike, controller.readBike());
    }

    @Test
    public void setBikeNull() {
        ClampController controller = new ClampController();
        assertNull(controller.readBike());
        Bike mockBike = Mockito.mock(Bike.class);
        controller.setBike(mockBike);
        assertEquals(mockBike, controller.readBike());
        controller.setBike(null);
        assertNull(controller.readBike());
    }

    @Test
    public void readBikeNotNull() {
        ClampController controller = new ClampController();
        assertNull(controller.readBike());
        Bike mockBike = Mockito.mock(Bike.class);
        controller.setBike(mockBike);
        assertEquals(mockBike, controller.readBike());
    }

    @Test
    public void readBikeNull() {
        ClampController controller = new ClampController();
        assertNull(controller.readBike());
    }

    @Test
    public void setRenterValid() throws InvalidSubscriptionException {
        ClampController controller = new ClampController();
        Subscription mockSub = Mockito.mock(Subscription.class);
        Bike mockBike = Mockito.mock(Bike.class);
        controller.setBike(mockBike);
        controller.setRenter(mockSub);
        InOrder order = Mockito.inOrder(mockSub, mockBike);
        order.verify(mockBike).setSubscription(mockSub);
        order.verify(mockSub).startRent(mockBike);
    }

    @Test(expected = RuntimeException.class)
    public void setRenterNotValid() throws InvalidSubscriptionException {
        ClampController controller = new ClampController();
        Subscription mockSub = Mockito.mock(Subscription.class);
        Bike mockBike = Mockito.mock(Bike.class);
        Mockito.doThrow(InvalidSubscriptionException.class).when(mockSub).startRent(mockBike);
        controller.setBike(mockBike);
        controller.setRenter(mockSub);
        InOrder order = Mockito.inOrder(mockSub, mockBike);
        order.verify(mockBike).setSubscription(mockSub);
        order.verify(mockSub).startRent(mockBike);
    }

    @Test
    public void endRentTest() {
        ClampController controller = new ClampController();
        Subscription mockSub = Mockito.mock(Subscription.class);
        Bike mockBike = Mockito.mock(Bike.class);
        Mockito.when(mockBike.getSubscription()).thenReturn(mockSub);
        controller.setBike(mockBike);
        controller.endRent();
        Mockito.verify(mockSub).endRent();
    }

}