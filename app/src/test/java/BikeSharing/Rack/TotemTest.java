package BikeSharing.Rack;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.MockedStatic;

import BikeSharing.Bike.Bike;
import BikeSharing.Clamp.Clamp;
import BikeSharing.Clamp.NormalClamp;
import BikeSharing.Clamp.DAO.ClampDataTransfer;
import BikeSharing.Rack.Util.BikeAlreadyRentedException;
import BikeSharing.Rack.Util.NoFreeBikesException;
import BikeSharing.Subscription.Subscription;
import BikeSharing.Subscription.Manager.SubscriptionManager;
import BikeSharing.Subscription.Util.InvalidSubscriptionException;
import BikeSharing.Subscription.Util.WrongLoginException;

public class TotemTest {

    @Test(expected = InvalidSubscriptionException.class)
    public void rentBikeTestInvalidSub() throws WrongLoginException, InvalidSubscriptionException, NoFreeBikesException {
        Subscription mockSub = mock(Subscription.class);
        Rack mockRack = mock(Rack.class);
        when(mockSub.isValid()).thenReturn(false);
        try (MockedStatic<SubscriptionManager> mockManager = mockStatic(SubscriptionManager.class)) {
            Totem t = new Totem(mockRack);
            mockManager.when(() -> SubscriptionManager.login(anyString(), anyString())).thenReturn(mockSub);
            t.rentBike(mock(Bike.class), "", "");
        }
    }

    @Test(expected = NoFreeBikesException.class)
    public void rentBikeTestNoFreeBike() throws WrongLoginException, InvalidSubscriptionException, NoFreeBikesException {
        Subscription mockSub = mock(Subscription.class);
        Rack mockRack = mock(Rack.class);
        when(mockSub.isValid()).thenReturn(true);
        when(mockRack.getFreeBike(any())).thenReturn(null);
        try (MockedStatic<SubscriptionManager> mockManager = mockStatic(SubscriptionManager.class)) {
            Totem t = new Totem(mockRack);
            mockManager.when(() -> SubscriptionManager.login(anyString(), anyString())).thenReturn(mockSub);
            t.rentBike(mock(Bike.class), "", "");
        }
    }

    @Test
    public void rentBikeTestOk() throws WrongLoginException, InvalidSubscriptionException, NoFreeBikesException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Subscription mockSub = mock(Subscription.class);
        Rack mockRack = mock(Rack.class);
        ClampDataTransfer clampData = new ClampDataTransfer();
        clampData.clampID = 4;
        Clamp mockClamp = new NormalClamp(clampData);
        Clamp spyClamp = spy(mockClamp);
        doNothing().when(spyClamp).rent(any());
        when(mockSub.isValid()).thenReturn(true);
        when(mockRack.getFreeBike(any())).thenReturn(spyClamp);
        try (MockedStatic<SubscriptionManager> mockManager = mockStatic(SubscriptionManager.class)) {
            Totem t = new Totem(mockRack);
            mockManager.when(() -> SubscriptionManager.login(anyString(), anyString())).thenReturn(mockSub);
            int clampNumber = t.rentBike(mock(Bike.class), "", "");
            verify(spyClamp).rent(mockSub);
            assertEquals(4, clampNumber);
        }
    }

    @Test
    public void reportDamageTest() throws WrongLoginException, BikeAlreadyRentedException {
        Subscription mockSub = mock(Subscription.class);
        Rack mockRack = mock(Rack.class);
        try (MockedStatic<SubscriptionManager> mockManager = mockStatic(SubscriptionManager.class)) {
            Totem t = new Totem(mockRack);
            mockManager.when(() -> SubscriptionManager.login(anyString(), anyString())).thenReturn(mockSub);
            t.reportDamage(5, "", "");
            verify(mockRack).lastRentDamaged(mockSub, 5);
        }
    }

    @Test
    public void returnBikeTest() throws WrongLoginException {
        Subscription mockSub = mock(Subscription.class);
        Rack mockRack = mock(Rack.class);
        try (MockedStatic<SubscriptionManager> mockManager = mockStatic(SubscriptionManager.class)) {
            Totem t = new Totem(mockRack);
            mockManager.when(() -> SubscriptionManager.login(anyString(), anyString())).thenReturn(mockSub);
            t.returnBike("", "");
            verify(mockSub).checkLastReturned();
        }
    }

    //Register method not tested as it is simply a wrapper

}