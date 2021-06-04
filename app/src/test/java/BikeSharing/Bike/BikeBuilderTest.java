package BikeSharing.Bike;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class BikeBuilderTest {

    @Test
    public void decodeTest0() {
        Bike bike = BikeBuilder.decode(0);
        assertEquals(NormalBike.class, bike.getClass());
    }

    @Test
    public void decodeTest1() {
        Bike bike = BikeBuilder.decode(1);
        assertEquals(ElectricBike.class, bike.getClass());
    }

    @Test
    public void decodeTest2() {
        Bike bike = BikeBuilder.decode(2);
        assertEquals(ElectricBikeBoosterSeat.class, bike.getClass());
    }

    @Test
    public void decodeTestOther() {
        Bike bike = BikeBuilder.decode(-4);
        assertNull(bike);
    }

}