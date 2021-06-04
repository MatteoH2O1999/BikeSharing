package BikeSharing.Clamp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import BikeSharing.Bike.Bike;
import BikeSharing.Clamp.DAO.ClampDao;
import BikeSharing.Clamp.DAO.ClampDataTransfer;
import BikeSharing.Clamp.FSM.States.LockedFull;

public class ClampTest {

    @Test
    public void contructorTest() {
        ClampDataTransfer dataTest = new ClampDataTransfer();
        dataTest.clampID = 5;
        dataTest.rackID = 8L;
        dataTest.state = LockedFull.LOCKEDFULL;
        Clamp testClamp = new ConstructorTestClamp(dataTest);
        assertEquals(dataTest.clampID, testClamp.id);
        assertEquals(dataTest.rackID, testClamp.rackID);
        assertEquals("5: ConstructorTestClamp (LockedFull)", testClamp.toString());
    }

    @Test
    public void setStateTest() {
        ClampDataTransfer dataTest = new ClampDataTransfer();
        ClampDataTransfer toSaveData;
        final ArgumentCaptor<ClampDataTransfer> captor = ArgumentCaptor.forClass(ClampDataTransfer.class);
        dataTest.clampID = 5;
        dataTest.rackID = 8L;
        dataTest.state = LockedFull.LOCKEDFULL;
        Clamp testClamp = new ConstructorTestClamp(dataTest);
        try (MockedStatic<ClampDao> mockedDao = Mockito.mockStatic(ClampDao.class)) {
            testClamp.setState(LockedFull.LOCKEDFULL);
            mockedDao.verify(() -> ClampDao.updateClamp(captor.capture()));
            toSaveData = captor.getValue();
        }
        assertEquals(dataTest.clampID, toSaveData.clampID);
        assertEquals(dataTest.rackID, toSaveData.rackID);
        assertEquals(LockedFull.LOCKEDFULL, toSaveData.state);
    }

    private class ConstructorTestClamp extends Clamp {

        public ConstructorTestClamp(ClampDataTransfer data) {
            super(data);
        }

        @Override
        public boolean isCompatible(Bike bike) {
            return false;
        }

        @Override
        public void parkedLoop() {
        }

        @Override
        public void rentProcedures() {
        }

        @Override
        protected Bike fillBike() {
            return null;
        }
        
    }
    
}