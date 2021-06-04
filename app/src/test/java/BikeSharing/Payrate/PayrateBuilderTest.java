package BikeSharing.Payrate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class PayrateBuilderTest {

    @Test
    public void decodeTest0() {
        assertEquals(StandardRate.class, PayrateBuilder.decode(0).getClass());
    }

    @Test
    public void decodeTest1() {
        assertEquals(StudentRate.class, PayrateBuilder.decode(1).getClass());
    }

    @Test
    public void decodeTestOther() {
        assertNull(PayrateBuilder.decode(-9));
    }

}