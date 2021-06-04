package BikeSharing.Clamp;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.mockito.InOrder;

import BikeSharing.Bike.Bike;
import BikeSharing.Bike.ElectricBike;
import BikeSharing.Clamp.DAO.ClampDataTransfer;

public class ElectricClampAbstractTest {

    @Test
    public void parkedLoopTest() {
        ClampDataTransfer mockData = mock(ClampDataTransfer.class);
        FakeElectricClampAbstract clamp = new FakeElectricClampAbstract(mockData);
        ElectricBike mockBike = mock(ElectricBike.class);
        clamp.setBike(mockBike);
        clamp.parkedLoop();
        InOrder order = inOrder(mockBike);
        order.verify(mockBike).stopBattery();
        order.verify(mockBike).charge();
    }

    @Test
    public void rentProceduresTest() {
        ClampDataTransfer mockData = mock(ClampDataTransfer.class);
        FakeElectricClampAbstract clamp = new FakeElectricClampAbstract(mockData);
        ElectricBike mockBike = mock(ElectricBike.class);
        clamp.setBike(mockBike);
        clamp.rentProcedures();
        verify(mockBike).startBattery();
    }

    private class FakeElectricClampAbstract extends ElectricClampAbstract {

        public FakeElectricClampAbstract(ClampDataTransfer data) {
            super(data);
        }

        @Override
        public boolean isCompatible(Bike bike) {
            return false;
        }

        @Override
        protected Bike fillBike() {
            return null;
        }
        
    }

}