package BikeSharing.Bike;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.mockito.Mockito;

import BikeSharing.Subscription.Subscription;

public class NormalBikeTest {

    @Test
    public void constructorTest1() {
        NormalBike bike = new NormalBike();
        assertNull(bike.getSubscription());
    }

    @Test
    public void setSubscriptionTest1() {
        NormalBike bike = new NormalBike();
        Subscription sub = Mockito.mock(Subscription.class);
        bike.setSubscription(sub);
        assertEquals(sub, bike.getSubscription());
    }

    @Test(expected = NullPointerException.class)
    public void setSubscriptionTest2() {
        NormalBike bike = new NormalBike();
        bike.setSubscription(null);
    }

    @Test
    public void initSubscriptiontest1() {
        NormalBike bike = new NormalBike();
        Subscription sub = Mockito.mock(Subscription.class);
        bike.setSubscription(sub);
        assertEquals(sub, bike.getSubscription());
        bike.initSubscription();
        assertNull(bike.getSubscription());
    }

    @Test
    public void encodeTest() {
        NormalBike bike = new NormalBike();
        assertEquals(0, bike.encode());
    }

    @Test
    public void toStringTest() {
        NormalBike bike = new NormalBike();
        assertEquals("NormalBike", bike.toString());
    }

}