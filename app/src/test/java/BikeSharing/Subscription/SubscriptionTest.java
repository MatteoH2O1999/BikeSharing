package BikeSharing.Subscription;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.GregorianCalendar;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;

import BikeSharing.Bike.Bike;
import BikeSharing.Payrate.Payrate;
import BikeSharing.Subscription.DAO.RentDao;
import BikeSharing.Subscription.DAO.RentDataTransfer;
import BikeSharing.Subscription.DAO.SubscriptionDao;
import BikeSharing.Subscription.DAO.SubscriptionDataTransfer;
import BikeSharing.Subscription.Util.DuplicateCardException;
import BikeSharing.Subscription.Util.ExpirationAlreadySetException;
import BikeSharing.Subscription.Util.ExpirationException;
import BikeSharing.Subscription.Util.InvalidSubscriptionException;
import BikeSharing.Subscription.Util.NotUniqueException;
import BikeSharing.Util.CreditCard;

public class SubscriptionTest {

    //Only implementation independant methods are tested

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("unused")
    public void constructorTestCardNull() throws NullPointerException, ExpirationException, NotUniqueException, DuplicateCardException {
        Payrate mockRate = mock(Payrate.class);
        SubscriptionAbstrTest subscription = new SubscriptionAbstrTest(null, "", "", mockRate);
    }

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("unused")
    public void constructorTestCodeNull() throws NullPointerException, ExpirationException, NotUniqueException, DuplicateCardException {
        CreditCard mockCard = mock(CreditCard.class);
        Payrate mockRate = mock(Payrate.class);
        SubscriptionAbstrTest subscription = new SubscriptionAbstrTest(mockCard, null, "", mockRate);
    }

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("unused")
    public void constructorTestPasswordNull() throws NullPointerException, ExpirationException, NotUniqueException, DuplicateCardException {
        CreditCard mockCard = mock(CreditCard.class);
        Payrate mockRate = mock(Payrate.class);
        SubscriptionAbstrTest subscription = new SubscriptionAbstrTest(mockCard, "", null, mockRate);
    }

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("unused")
    public void constructorTestRateNull() throws NullPointerException, ExpirationException, NotUniqueException, DuplicateCardException {
        CreditCard mockCard = mock(CreditCard.class);
        SubscriptionAbstrTest subscription = new SubscriptionAbstrTest(mockCard, "", "", null);
    }

    @Test(expected = InvalidSubscriptionException.class)
    public void startRentTestNotValid() throws NullPointerException, ExpirationException, NotUniqueException, DuplicateCardException, InvalidSubscriptionException {
        Bike mockBike = mock(Bike.class);
        CreditCard mockCard = mock(CreditCard.class);
        Payrate mockRate = mock(Payrate.class);
        try (MockedStatic<SubscriptionDao> mockDao = mockStatic(SubscriptionDao.class)) {
            SubscriptionAbstrTest subscription = new SubscriptionAbstrTest(mockCard, "Code", "Pass", mockRate);
            Subscription spySub = spy(subscription);
            when(spySub.isValid()).thenReturn(false);
            spySub.startRent(mockBike);
        }
    }

    @Test
    public void startRentValid() throws NullPointerException, ExpirationException, NotUniqueException, DuplicateCardException, InvalidSubscriptionException {
        Bike mockBike = mock(Bike.class);
        CreditCard mockCard = mock(CreditCard.class);
        Payrate mockRate = mock(Payrate.class);
        Subscription spySub;
        RentDataTransfer returned;
        try (MockedStatic<SubscriptionDao> mockDao = mockStatic(SubscriptionDao.class)) {
            SubscriptionAbstrTest sub = new SubscriptionAbstrTest(mockCard, "Code", "Pass", mockRate);
            spySub = spy(sub);
        }
        when(spySub.isValid()).thenReturn(true);
        try (MockedStatic<RentDao> mockDao = mockStatic(RentDao.class)) {
            ArgumentCaptor<RentDataTransfer> captor = ArgumentCaptor.forClass(RentDataTransfer.class);
            spySub.startRent(mockBike);
            mockDao.verify(() -> RentDao.createRent(captor.capture()));
            returned = captor.getValue();
        }
        assertEquals(mockBike, returned.bike);
        assertEquals("Code", returned.subCode);
    }

    @Test
    public void endRentNoStrike() throws NullPointerException, ExpirationException, NotUniqueException, DuplicateCardException {
        CreditCard mockCard = mock(CreditCard.class);
        Payrate mockRate = mock(Payrate.class);
        Subscription sub;
        try (MockedStatic<SubscriptionDao> mockDao = mockStatic(SubscriptionDao.class)) {
            sub = new SubscriptionAbstrTest(mockCard, "Code", "Pass", mockRate);
        }
        RentDataTransfer mockRent = new RentDataTransfer();
        mockRent.startRent = new GregorianCalendar();
        mockRent.startRent.add(GregorianCalendar.MINUTE, -60);
        Bike mockBike = mock(Bike.class);
        mockRent.bike = mockBike;
        RentDataTransfer returned;
        try (MockedStatic<RentDao> mockDao = mockStatic(RentDao.class)) {
            ArgumentCaptor<RentDataTransfer> captor = ArgumentCaptor.forClass(RentDataTransfer.class);
            mockDao.when(() -> RentDao.getLastRent(anyString())).thenReturn(mockRent);
            sub.endRent();
            verify(mockRate).getDuePayment(60, mockBike);
            verify(mockCard, times(2)).addPayment(anyFloat());
            mockDao.verify(() -> RentDao.saveRent(captor.capture()));
            returned = captor.getValue();
        }
        assertEquals(mockBike, returned.bike);
    }

    @Test
    public void endRentStrike() throws NullPointerException, ExpirationException, NotUniqueException, DuplicateCardException {
        CreditCard mockCard = mock(CreditCard.class);
        Payrate mockRate = mock(Payrate.class);
        Subscription sub;
        RentDataTransfer mockRent = new RentDataTransfer();
        mockRent.startRent = new GregorianCalendar();
        mockRent.startRent.add(GregorianCalendar.MINUTE, -130);
        Bike mockBike = mock(Bike.class);
        mockRent.bike = mockBike;
        try (MockedStatic<SubscriptionDao> mockSubDao = mockStatic(SubscriptionDao.class)) {
            try (MockedStatic<RentDao> mockRentDao = mockStatic(RentDao.class)) {
                sub = new SubscriptionAbstrTest(mockCard, "Code", "Pass", mockRate);
                mockRentDao.when(() -> RentDao.getLastRent(anyString())).thenReturn(mockRent);
                sub.endRent();
                verify(mockRate).getDuePayment(130, mockBike);
                mockSubDao.verify(() -> SubscriptionDao.update(any()));
                mockRentDao.verify(() -> RentDao.saveRent(any()));
            }
        }
    }

    @Test(expected = ExpirationAlreadySetException.class)
    public void setExpDateTest() throws NullPointerException, ExpirationException, NotUniqueException, DuplicateCardException, ExpirationAlreadySetException {
        try (MockedStatic<SubscriptionDao> mockDao = mockStatic(SubscriptionDao.class)) {
            Subscription sub = new SubscriptionAbstrTest(mock(CreditCard.class), "Code", "Pass", mock(Payrate.class));
            sub.setExpDate(null);
            GregorianCalendar mockCalendar = new GregorianCalendar();
            sub.setExpDate(mockCalendar);
            ArgumentCaptor<SubscriptionDataTransfer> captor = ArgumentCaptor.forClass(SubscriptionDataTransfer.class);
            mockDao.verify(() -> SubscriptionDao.update(captor.capture()));
            assertEquals(mockCalendar, captor.getValue().expirationDate);
            sub.setExpDate(new GregorianCalendar());
        }
    }

    @Test
    public void checkLastReturnedNewRent() throws NullPointerException, ExpirationException, NotUniqueException, DuplicateCardException {
        CreditCard mockCard = mock(CreditCard.class);
        Payrate mockRate = mock(Payrate.class);
        Subscription sub;
        try (MockedStatic<SubscriptionDao> mockDao = mockStatic(SubscriptionDao.class)) {
            sub = new SubscriptionAbstrTest(mockCard, "Code", "Pass", mockRate);
        }
        RentDataTransfer mockRent = new RentDataTransfer();
        mockRent.startRent = null;
        mockRent.endRent = new GregorianCalendar();
        try (MockedStatic<RentDao> mockDao = mockStatic(RentDao.class)) {
            mockDao.when(() -> RentDao.getLastRent(anyString())).thenReturn(mockRent);
            assertTrue(sub.checkLastReturned());
        }
    }

    @Test
    public void checkLastReturnedNotReturned() throws NullPointerException, ExpirationException, NotUniqueException, DuplicateCardException {
        CreditCard mockCard = mock(CreditCard.class);
        Payrate mockRate = mock(Payrate.class);
        Subscription sub;
        try (MockedStatic<SubscriptionDao> mockDao = mockStatic(SubscriptionDao.class)) {
            sub = new SubscriptionAbstrTest(mockCard, "Code", "Pass", mockRate);
        }
        RentDataTransfer mockRent = new RentDataTransfer();
        mockRent.startRent = new GregorianCalendar();
        mockRent.endRent = null;
        try (MockedStatic<RentDao> mockDao = mockStatic(RentDao.class)) {
            mockDao.when(() -> RentDao.getLastRent(anyString())).thenReturn(mockRent);
            assertFalse(sub.checkLastReturned());
        }
    }

    @Test
    public void checkLastReturnedIsReturned() throws NullPointerException, ExpirationException, NotUniqueException, DuplicateCardException {
        CreditCard mockCard = mock(CreditCard.class);
        Payrate mockRate = mock(Payrate.class);
        Subscription sub;
        try (MockedStatic<SubscriptionDao> mockDao = mockStatic(SubscriptionDao.class)) {
            sub = new SubscriptionAbstrTest(mockCard, "Code", "Pass", mockRate);
        }
        RentDataTransfer mockRent = new RentDataTransfer();
        mockRent.startRent = new GregorianCalendar();
        mockRent.endRent = new GregorianCalendar();
        try (MockedStatic<RentDao> mockDao = mockStatic(RentDao.class)) {
            mockDao.when(() -> RentDao.getLastRent(anyString())).thenReturn(mockRent);
            assertTrue(sub.checkLastReturned());
        }
    }

    @Test
    public void returnedIntervalTestNullStart() throws NullPointerException, ExpirationException, NotUniqueException, DuplicateCardException {
        CreditCard mockCard = mock(CreditCard.class);
        Payrate mockRate = mock(Payrate.class);
        Subscription sub;
        try (MockedStatic<SubscriptionDao> mockDao = mockStatic(SubscriptionDao.class)) {
            sub = new SubscriptionAbstrTest(mockCard, "Code", "Pass", mockRate);
        }
        RentDataTransfer mockRent = new RentDataTransfer();
        mockRent.startRent = null;
        mockRent.endRent = new GregorianCalendar();
        try (MockedStatic<RentDao> mockDao = mockStatic(RentDao.class)) {
            mockDao.when(() -> RentDao.getLastRent(anyString())).thenReturn(mockRent);
            assertTrue(sub.returnedInterval());
        }
    }

    @Test
    public void returnedIntervalTestNullEnd() throws NullPointerException, ExpirationException, NotUniqueException, DuplicateCardException {
        CreditCard mockCard = mock(CreditCard.class);
        Payrate mockRate = mock(Payrate.class);
        Subscription sub;
        try (MockedStatic<SubscriptionDao> mockDao = mockStatic(SubscriptionDao.class)) {
            sub = new SubscriptionAbstrTest(mockCard, "Code", "Pass", mockRate);
        }
        RentDataTransfer mockRent = new RentDataTransfer();
        mockRent.startRent = new GregorianCalendar();
        mockRent.endRent = null;
        try (MockedStatic<RentDao> mockDao = mockStatic(RentDao.class)) {
            mockDao.when(() -> RentDao.getLastRent(anyString())).thenReturn(mockRent);
            assertFalse(sub.returnedInterval());
        }
    }

    @Test
    public void returnedIntervalTestFalse() throws NullPointerException, ExpirationException, NotUniqueException, DuplicateCardException {
        CreditCard mockCard = mock(CreditCard.class);
        Payrate mockRate = mock(Payrate.class);
        Subscription sub;
        try (MockedStatic<SubscriptionDao> mockDao = mockStatic(SubscriptionDao.class)) {
            sub = new SubscriptionAbstrTest(mockCard, "Code", "Pass", mockRate);
        }
        RentDataTransfer mockRent = new RentDataTransfer();
        mockRent.startRent = new GregorianCalendar();
        mockRent.endRent = new GregorianCalendar();
        mockRent.endRent.add(GregorianCalendar.MINUTE, -5);
        try (MockedStatic<RentDao> mockDao = mockStatic(RentDao.class)) {
            mockDao.when(() -> RentDao.getLastRent(anyString())).thenReturn(mockRent);
            assertFalse(sub.returnedInterval());
        }
    }

    @Test
    public void returnedIntervalTestTrue() throws NullPointerException, ExpirationException, NotUniqueException, DuplicateCardException {
        CreditCard mockCard = mock(CreditCard.class);
        Payrate mockRate = mock(Payrate.class);
        Subscription sub;
        try (MockedStatic<SubscriptionDao> mockDao = mockStatic(SubscriptionDao.class)) {
            sub = new SubscriptionAbstrTest(mockCard, "Code", "Pass", mockRate);
        }
        RentDataTransfer mockRent = new RentDataTransfer();
        mockRent.startRent = new GregorianCalendar();
        mockRent.endRent = new GregorianCalendar();
        mockRent.endRent.add(GregorianCalendar.MINUTE, -6);
        try (MockedStatic<RentDao> mockDao = mockStatic(RentDao.class)) {
            mockDao.when(() -> RentDao.getLastRent(anyString())).thenReturn(mockRent);
            assertTrue(sub.returnedInterval());
        }
    }

    private class SubscriptionAbstrTest extends Subscription {

        public SubscriptionAbstrTest(CreditCard cCard, String code, String passwd, Payrate rate) throws NullPointerException, ExpirationException, NotUniqueException, DuplicateCardException {
            super(cCard, code, passwd, rate);
        }

        @Override
        public boolean isValid() {
            return false;
        }

        @Override
        public float subCost() {
            return 0;
        }

        @Override
        protected GregorianCalendar calcExpirationDate() {
            return null;
        }

        @Override
        protected int calcType() {
            return 0;
        }
        
    }

}