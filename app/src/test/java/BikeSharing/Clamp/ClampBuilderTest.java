package BikeSharing.Clamp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import BikeSharing.Clamp.DAO.ClampDataTransfer;

public class ClampBuilderTest {

    @Test
    public void buildClampTest0() {
        ClampDataTransfer data = new ClampDataTransfer();
        data.type = 0;
        Clamp clamp = ClampBuilder.buildClamp(data);
        assertEquals(NormalClamp.class, clamp.getClass());
    }

    @Test
    public void buildClampTest1() {
        ClampDataTransfer data = new ClampDataTransfer();
        data.type = 1;
        Clamp clamp = ClampBuilder.buildClamp(data);
        assertEquals(ElectricClamp.class, clamp.getClass());
    }

    @Test
    public void buildClampTest2() {
        ClampDataTransfer data = new ClampDataTransfer();
        data.type = 2;
        Clamp clamp = ClampBuilder.buildClamp(data);
        assertEquals(ElectricBoosterSeatClamp.class, clamp.getClass());
    }

    @Test
    public void buildClampTestOther() {
        ClampDataTransfer data = new ClampDataTransfer();
        data.type = 8;
        Clamp clamp = ClampBuilder.buildClamp(data);
        assertNull(clamp);
    }

}