package BikeSharing.Rack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Test;
import org.mockito.MockedStatic;

import BikeSharing.Bike.Bike;
import BikeSharing.Clamp.Clamp;
import BikeSharing.Clamp.ElectricBoosterSeatClamp;
import BikeSharing.Clamp.ElectricClamp;
import BikeSharing.Clamp.NormalClamp;
import BikeSharing.Clamp.DAO.ClampDataTransfer;
import BikeSharing.Clamp.FSM.ClampState;
import BikeSharing.Rack.DAO.RackDao;
import BikeSharing.Rack.DAO.RackDataTransfer;
import BikeSharing.Rack.Util.BikeAlreadyRentedException;
import BikeSharing.Subscription.Subscription;

public class RackTest {

    @Test
    public void contructorTest() {
        RackDataTransfer data = new RackDataTransfer();
        data.id = 1L;
        data.name = "Name";
        Clamp clamp1 = mock(Clamp.class);
        Clamp clamp2 = mock(Clamp.class);
        Clamp clamp3 = mock(Clamp.class);
        data.clamps = new ArrayList<>();
        data.clamps.add(clamp1);
        data.clamps.add(clamp2);
        data.clamps.add(clamp3);
        Rack rack = new Rack(data);
        assertEquals("Name", rack.getName());
        assertEquals(1L, rack.getID());
        assertTrue(data.clamps.equals(rack.getClamps()));
        assertEquals("Name", rack.toString());
    }

    @Test
    public void getClampTest() {
        ClampDataTransfer data = new ClampDataTransfer();
        data.clampID = 3;
        NormalClamp clamp1 = new NormalClamp(data);
        data.clampID = 4;
        ElectricClamp clamp2 = new ElectricClamp(data);
        data.clampID = 5;
        ElectricBoosterSeatClamp clamp3 = new ElectricBoosterSeatClamp(data);
        RackDataTransfer rackData = new RackDataTransfer();
        ArrayList<Clamp> clamps = new ArrayList<>();
        clamps.add(clamp1);
        clamps.add(clamp2);
        clamps.add(clamp3);
        rackData.name = "Name";
        rackData.id = 1L;
        rackData.clamps = clamps;
        Rack rack = new Rack(rackData);
        assertEquals(clamp1, rack.getClamp(3));
        assertEquals(clamp2, rack.getClamp(4));
        assertEquals(clamp3, rack.getClamp(5));
        assertNull(rack.getClamp(-2));
        assertNull(rack.getClamp(6));
    }

    @Test
    public void lastRentDamagedTestWrongID() throws BikeAlreadyRentedException {
        Clamp clamp = mock(Clamp.class);
        ArrayList<Clamp> clamps = new ArrayList<>();
        clamps.add(clamp);
        RackDataTransfer rackData = new RackDataTransfer();
        rackData.id = 1L;
        rackData.name = "Name";
        rackData.clamps = clamps;
        Rack rack = new Rack(rackData);
        Subscription mockSub = mock(Subscription.class);
        rack.lastRentDamaged(mockSub, 4);
        verifyNoInteractions(clamp);
    }

    @Test(expected = BikeAlreadyRentedException.class)
    public void lastRentDamagedTestNoBikeInClamp() throws BikeAlreadyRentedException {
        ClampDataTransfer clampData = new ClampDataTransfer();
        clampData.clampID = 3;
        Clamp clamp = new NormalClamp(clampData);
        ArrayList<Clamp> clamps = new ArrayList<>();
        clamps.add(clamp);
        RackDataTransfer rackData = new RackDataTransfer();
        rackData.id = 1L;
        rackData.name = "Name";
        rackData.clamps = clamps;
        Rack rack = new Rack(rackData);
        Subscription mockSub = mock(Subscription.class);
        rack.lastRentDamaged(mockSub, 3);
    }

    @Test
    public void lastRentDamagedTestSubEqual() throws BikeAlreadyRentedException {
        Bike bike = mock(Bike.class);
        ClampDataTransfer clampData = new ClampDataTransfer();
        clampData.clampID = 3;
        clampData.state = mock(ClampState.class);
        Clamp clamp = new NormalClamp(clampData);
        ArrayList<Clamp> clamps = new ArrayList<>();
        clamps.add(clamp);
        RackDataTransfer rackData = new RackDataTransfer();
        rackData.id = 1L;
        rackData.name = "Name";
        rackData.clamps = clamps;
        Rack rack = new Rack(rackData);
        Subscription mockSub = mock(Subscription.class);
        when(bike.getSubscription()).thenReturn(mockSub);
        clamp.setBike(bike);
        rack.lastRentDamaged(mockSub, 3);
        verify(bike).getSubscription();
        verify(clampData.state).startMaintenance(clamp);
    }

    @Test(expected = BikeAlreadyRentedException.class)
    public void lastRentDamagedTestSubNotEqual() throws BikeAlreadyRentedException {
        Bike bike = mock(Bike.class);
        ClampDataTransfer clampData = new ClampDataTransfer();
        clampData.clampID = 3;
        clampData.state = mock(ClampState.class);
        Clamp clamp = new NormalClamp(clampData);
        ArrayList<Clamp> clamps = new ArrayList<>();
        clamps.add(clamp);
        RackDataTransfer rackData = new RackDataTransfer();
        rackData.id = 1L;
        rackData.name = "Name";
        rackData.clamps = clamps;
        Rack rack = new Rack(rackData);
        Subscription mockSub = mock(Subscription.class);
        Subscription mockSub2 = mock(Subscription.class);
        when(bike.getSubscription()).thenReturn(mockSub2);
        clamp.setBike(bike);
        rack.lastRentDamaged(mockSub, 3);
        verify(bike).getSubscription();
    }

    @Test
    public void getFreeBikeTestNotRentable() {
        Clamp clamp = mock(Clamp.class);
        when(clamp.isRentable()).thenReturn(false);
        ArrayList<Clamp> clamps = new ArrayList<>();
        clamps.add(clamp);
        RackDataTransfer data = new RackDataTransfer();
        data.id = 1L;
        data.name = "Name";
        data.clamps = clamps;
        Rack rack = new Rack(data);
        assertNull(rack.getFreeBike(mock(Bike.class)));
    }

    @Test
    public void getFreeBikeTestNotCompatible() {
        Clamp clamp = mock(Clamp.class);
        when(clamp.isRentable()).thenReturn(true);
        when(clamp.isCompatible(mock(Bike.class))).thenReturn(false);
        ArrayList<Clamp> clamps = new ArrayList<>();
        clamps.add(clamp);
        RackDataTransfer data = new RackDataTransfer();
        data.id = 1L;
        data.name = "Name";
        data.clamps = clamps;
        Rack rack = new Rack(data);
        assertNull(rack.getFreeBike(mock(Bike.class)));
    }

    @Test
    public void getFreeBikeTestOk() {
        Clamp clamp = mock(Clamp.class);
        when(clamp.isRentable()).thenReturn(true);
        when(clamp.isCompatible(any())).thenReturn(true);
        ArrayList<Clamp> clamps = new ArrayList<>();
        clamps.add(clamp);
        RackDataTransfer data = new RackDataTransfer();
        data.id = 1L;
        data.name = "Name";
        data.clamps = clamps;
        Rack rack = new Rack(data);
        assertEquals(clamp, rack.getFreeBike(mock(Bike.class)));
    }

    @Test
    public void updateRackTest() {
        Clamp mockClamp = mock(Clamp.class);
        ArrayList<Clamp> clamps = new ArrayList<>();
        clamps.add(mockClamp);
        RackDataTransfer data = new RackDataTransfer();
        data.clamps = clamps;
        Rack rack = new Rack(data);
        assertTrue(rack.getClamps().equals(clamps));
        Clamp mockClamp2 = mock(Clamp.class);
        clamps = new ArrayList<Clamp>();
        clamps.add(mockClamp2);
        RackDataTransfer newData = new RackDataTransfer();
        newData.clamps = clamps;
        try (MockedStatic<RackDao> mockDao = mockStatic(RackDao.class)) {
            mockDao.when(() -> RackDao.getRack(anyLong())).thenReturn(newData);
            rack.updateRack();
            assertTrue(rack.getClamps().equals(clamps));
        }
    }

}