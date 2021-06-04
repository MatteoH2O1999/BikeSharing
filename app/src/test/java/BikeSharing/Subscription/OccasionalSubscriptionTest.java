package BikeSharing.Subscription;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;

import java.util.GregorianCalendar;

import org.junit.Test;
import org.mockito.MockedStatic;

import BikeSharing.Payrate.Payrate;
import BikeSharing.Subscription.DAO.SubscriptionDao;
import BikeSharing.Subscription.Util.ExpirationAlreadySetException;
import BikeSharing.Subscription.Util.ExpirationException;
import BikeSharing.Util.CreditCard;

public class OccasionalSubscriptionTest {

    @Test
    public void isValidTestNotActiveNotExpired() {
        CreditCard mockCard = mock(CreditCard.class);
        Payrate mockRate = mock(Payrate.class);
        OccasionalSubscription spySub, sub;
        GregorianCalendar now = new GregorianCalendar();
        sub = new OccasionalSubscriptionImpl(mockCard, "Code", "Pass", mockRate, now, 0);
        spySub = spy(sub);
        doReturn(false).when(spySub).isActive();
        try (MockedStatic<SubscriptionDao> mockDao = mockStatic(SubscriptionDao.class)) {
            assertTrue(spySub.isValid());
            mockDao.verify(() -> SubscriptionDao.update(any()));
        }
    }

    @Test
    public void isValidTestNotActiveExpired() throws ExpirationException {
        CreditCard mockCard = mock(CreditCard.class);
        Payrate mockRate = mock(Payrate.class);
        OccasionalSubscription spySub, sub;
        GregorianCalendar now = new GregorianCalendar();
        sub = new OccasionalSubscriptionImpl(mockCard, "Code", "Pass", mockRate, now, 0);
        spySub = spy(sub);
        doReturn(false).when(spySub).isActive();
        doThrow(ExpirationException.class).when(spySub).activate();
        try (MockedStatic<SubscriptionDao> mockDao = mockStatic(SubscriptionDao.class)) {
            assertFalse(spySub.isValid());
            mockDao.verify(() -> SubscriptionDao.delete(anyString()));
        }
    }

    @Test
    public void isValidTestActiveNoLastReturned() {
        CreditCard mockCard = mock(CreditCard.class);
        Payrate mockRate = mock(Payrate.class);
        OccasionalSubscription spySub, sub;
        GregorianCalendar now = new GregorianCalendar();
        sub = new OccasionalSubscriptionImpl(mockCard, "Code", "Pass", mockRate, now, 0);
        spySub = spy(sub);
        doReturn(true).when(spySub).isActive();
        doReturn(false).when(spySub).checkLastReturned();
        assertFalse(spySub.isValid());
    }

    @Test
    public void isValidTestActiveNoInterval() {
        CreditCard mockCard = mock(CreditCard.class);
        Payrate mockRate = mock(Payrate.class);
        OccasionalSubscription spySub, sub;
        GregorianCalendar now = new GregorianCalendar();
        sub = new OccasionalSubscriptionImpl(mockCard, "Code", "Pass", mockRate, now, 0);
        spySub = spy(sub);
        doReturn(true).when(spySub).isActive();
        doReturn(true).when(spySub).checkLastReturned();
        doReturn(false).when(spySub).returnedInterval();
        assertFalse(spySub.isValid());
    }

    @Test
    public void isValidTestTrue() {
        CreditCard mockCard = mock(CreditCard.class);
        Payrate mockRate = mock(Payrate.class);
        OccasionalSubscription spySub, sub;
        GregorianCalendar now = new GregorianCalendar();
        sub = new OccasionalSubscriptionImpl(mockCard, "Code", "Pass", mockRate, now, 0);
        spySub = spy(sub);
        doReturn(true).when(spySub).isActive();
        doReturn(true).when(spySub).checkLastReturned();
        doReturn(true).when(spySub).returnedInterval();
        assertTrue(spySub.isValid());
    }

    @Test
    public void isActiveTestFalse() throws ExpirationAlreadySetException {
        CreditCard mockCard = mock(CreditCard.class);
        Payrate mockRate = mock(Payrate.class);
        OccasionalSubscription spySub, sub;
        GregorianCalendar now = new GregorianCalendar();
        sub = new OccasionalSubscriptionImpl(mockCard, "Code", "Pass", mockRate, now, 0);
        spySub = spy(sub);
        doNothing().when(spySub).setExpDate(any());
        assertFalse(spySub.isActive());
    }

    @Test
    public void isActiveTestTrue() throws ExpirationAlreadySetException {
        CreditCard mockCard = mock(CreditCard.class);
        Payrate mockRate = mock(Payrate.class);
        OccasionalSubscription spySub, sub;
        GregorianCalendar now = new GregorianCalendar();
        sub = new OccasionalSubscriptionImpl(mockCard, "Code", "Pass", mockRate, now, 0);
        spySub = spy(sub);
        doThrow(ExpirationAlreadySetException.class).when(spySub).setExpDate(any());
        assertTrue(spySub.isActive());
    }

    private class OccasionalSubscriptionImpl extends OccasionalSubscription {

        protected OccasionalSubscriptionImpl(CreditCard cCard, String code, String passwd, Payrate rate, GregorianCalendar date, int strks) {
            super(cCard, code, passwd, rate, date, strks);
        }

        @Override
        protected void activate() throws ExpirationException {
        }

        @Override
        public float subCost() {
            return 0;
        }

        @Override
        protected int calcType() {
            return 0;
        }
        
    }

}