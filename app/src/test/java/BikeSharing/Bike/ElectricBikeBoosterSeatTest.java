package BikeSharing.Bike;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.mockito.Mockito;

import BikeSharing.Subscription.Subscription;

public class ElectricBikeBoosterSeatTest {

    @Test
    public void constructorTest1() {
        ElectricBikeBoosterSeat bike = new ElectricBikeBoosterSeat(100);
        assertNull(bike.getSubscription());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorTest2() {
        ElectricBikeBoosterSeat bike = new ElectricBikeBoosterSeat(-2);
        assertNull(bike.getSubscription());
    }

    @Test
    public void setSubscriptionTest1() {
        ElectricBikeBoosterSeat bike = new ElectricBikeBoosterSeat(100);
        Subscription sub = Mockito.mock(Subscription.class);
        bike.setSubscription(sub);
        assertEquals(sub, bike.getSubscription());
    }

    @Test(expected = NullPointerException.class)
    public void setSubscriptionTest2() {
        ElectricBikeBoosterSeat bike = new ElectricBikeBoosterSeat(100);
        bike.setSubscription(null);
    }

    @Test
    public void initSubscriptiontest1() {
        ElectricBikeBoosterSeat bike = new ElectricBikeBoosterSeat(100);
        Subscription sub = Mockito.mock(Subscription.class);
        bike.setSubscription(sub);
        assertEquals(sub, bike.getSubscription());
        bike.initSubscription();
        assertNull(bike.getSubscription());
    }

    @Test
    public void encodeTest() {
        ElectricBikeBoosterSeat bike = new ElectricBikeBoosterSeat(100);
        assertEquals(2, bike.encode());
    }

    @Test
    public void toStringTest1() {
        ElectricBikeBoosterSeat bike = new ElectricBikeBoosterSeat(100);
        assertEquals("ElectricBikeBoosterSeat 100%", bike.toString());
    }

    @Test
    public void toStringTest2() {
        ElectricBikeBoosterSeat bike = new ElectricBikeBoosterSeat(89);
        assertEquals("ElectricBikeBoosterSeat 89%", bike.toString());
    }
    
}