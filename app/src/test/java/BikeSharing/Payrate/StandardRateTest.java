package BikeSharing.Payrate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import BikeSharing.Bike.Bike;
import BikeSharing.Bike.ElectricBike;
import BikeSharing.Bike.ElectricBikeBoosterSeat;
import BikeSharing.Bike.NormalBike;

public class StandardRateTest {

    @Test
    public void normalBikeRateTest() {
        Bike mockBike = mock(NormalBike.class);
        StandardRate rate = new StandardRate();
        assertEquals(0.0, rate.getDuePayment(0, mockBike), 0.001);
        assertEquals(0.50, rate.getDuePayment(31, mockBike), 0.001);
        assertEquals(1.0, rate.getDuePayment(61, mockBike), 0.001);
        assertEquals(1.50, rate.getDuePayment(91, mockBike), 0.001);
        assertEquals(3.50, rate.getDuePayment(121, mockBike), 0.001);
        assertEquals(5.50, rate.getDuePayment(181, mockBike), 0.001);
        assertEquals(150.0, rate.getFine(mockBike), 0.001);
    }

    @Test
    public void electricBikeTest() {
        Bike mockBike = mock(ElectricBike.class);
        StandardRate rate = new StandardRate();
        assertEquals(0.25, rate.getDuePayment(0, mockBike), 0.001);
        assertEquals(0.75, rate.getDuePayment(31, mockBike), 0.001);
        assertEquals(1.75, rate.getDuePayment(61, mockBike), 0.001);
        assertEquals(3.75, rate.getDuePayment(91, mockBike), 0.001);
        assertEquals(7.75, rate.getDuePayment(121, mockBike), 0.001);
        assertEquals(11.75, rate.getDuePayment(181, mockBike), 0.001);
        assertEquals(150.0, rate.getFine(mockBike), 0.001);
    }

    @Test
    public void electricBoosterSeatBikeTest() {
        Bike mockBike = mock(ElectricBikeBoosterSeat.class);
        StandardRate rate = new StandardRate();
        assertEquals(0.25, rate.getDuePayment(0, mockBike), 0.001);
        assertEquals(0.75, rate.getDuePayment(31, mockBike), 0.001);
        assertEquals(1.75, rate.getDuePayment(61, mockBike), 0.001);
        assertEquals(3.75, rate.getDuePayment(91, mockBike), 0.001);
        assertEquals(7.75, rate.getDuePayment(121, mockBike), 0.001);
        assertEquals(11.75, rate.getDuePayment(181, mockBike), 0.001);
        assertEquals(150.0, rate.getFine(mockBike), 0.001);
    }

    @Test
    public void encodingTest() {
        StandardRate rate = new StandardRate();
        assertEquals(0, rate.getEncoding());
    }

}