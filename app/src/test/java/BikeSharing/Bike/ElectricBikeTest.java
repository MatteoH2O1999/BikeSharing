package BikeSharing.Bike;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.mockito.Mockito;

import BikeSharing.Subscription.Subscription;

public class ElectricBikeTest {

    @Test
    public void constructorTest1() {
        ElectricBike bike = new ElectricBike(100);
        assertNull(bike.getSubscription());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorTest2() {
        ElectricBike bike = new ElectricBike(-2);
        assertNull(bike.getSubscription());
    }

    @Test
    public void setSubscriptionTest1() {
        ElectricBike bike = new ElectricBike(100);
        Subscription sub = Mockito.mock(Subscription.class);
        bike.setSubscription(sub);
        assertEquals(sub, bike.getSubscription());
    }

    @Test(expected = NullPointerException.class)
    public void setSubscriptionTest2() {
        ElectricBike bike = new ElectricBike(100);
        bike.setSubscription(null);
    }

    @Test
    public void initSubscriptiontest1() {
        ElectricBike bike = new ElectricBike(100);
        Subscription sub = Mockito.mock(Subscription.class);
        bike.setSubscription(sub);
        assertEquals(sub, bike.getSubscription());
        bike.initSubscription();
        assertNull(bike.getSubscription());
    }

    @Test
    public void encodeTest() {
        ElectricBike bike = new ElectricBike(100);
        assertEquals(1, bike.encode());
    }

    @Test
    public void toStringTest1() {
        ElectricBike bike = new ElectricBike(100);
        assertEquals("ElectricBike 100%", bike.toString());
    }

    @Test
    public void toStringTest2() {
        ElectricBike bike = new ElectricBike(89);
        assertEquals("ElectricBike 89%", bike.toString());
    }

}