package BikeSharing.Subscription;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.GregorianCalendar;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import BikeSharing.Payrate.Payrate;
import BikeSharing.Subscription.Util.ExpirationAlreadySetException;
import BikeSharing.Subscription.Util.ExpirationException;
import BikeSharing.Util.CreditCard;

public class DailySubscriptionTest {

    @Test(expected = ExpirationException.class)
    public void activateTestNoCreditValidity() throws ExpirationException {
        CreditCard mockCard = mock(CreditCard.class);
        Payrate mockRate = mock(Payrate.class);
        OccasionalSubscription spySub, sub;
        GregorianCalendar now = new GregorianCalendar();
        sub = new DailySubscription(mockCard, "Code", "Pass", mockRate, now, 0);
        spySub = spy(sub);
        doReturn(false).when(spySub).creditValidity();
        spySub.activate();
    }

    @Test
    public void activateTestCreditValid() throws ExpirationAlreadySetException, ExpirationException {
        CreditCard mockCard = mock(CreditCard.class);
        Payrate mockRate = mock(Payrate.class);
        OccasionalSubscription spySub, sub;
        GregorianCalendar now = new GregorianCalendar();
        ArgumentCaptor<GregorianCalendar> captor = ArgumentCaptor.forClass(GregorianCalendar.class);
        sub = new DailySubscription(mockCard, "Code", "Pass", mockRate, now, 0);
        spySub = spy(sub);
        doReturn(true).when(spySub).creditValidity();
        doNothing().when(spySub).setExpDate(any());
        spySub.activate();
        verify(spySub).setExpDate(captor.capture());
        GregorianCalendar expected = new GregorianCalendar();
        expected.add(GregorianCalendar.HOUR_OF_DAY, 24);
        expected.set(GregorianCalendar.MILLISECOND, 0);
        expected.set(GregorianCalendar.SECOND, 0);
        GregorianCalendar actual = captor.getValue();
        actual.set(GregorianCalendar.MILLISECOND, 0);
        actual.set(GregorianCalendar.SECOND, 0);
        assertEquals(expected, actual);
    }

    @Test
    public void calcTypeTest() {
        CreditCard mockCard = mock(CreditCard.class);
        Payrate mockRate = mock(Payrate.class);
        OccasionalSubscription sub;
        GregorianCalendar now = new GregorianCalendar();
        sub = new DailySubscription(mockCard, "Code", "Pass", mockRate, now, 0);
        assertEquals(2, sub.calcType());
    }

    @Test
    public void subCostTest() {
        CreditCard mockCard = mock(CreditCard.class);
        Payrate mockRate = mock(Payrate.class);
        OccasionalSubscription sub;
        GregorianCalendar now = new GregorianCalendar();
        sub = new DailySubscription(mockCard, "Code", "Pass", mockRate, now, 0);
        assertEquals(4.5, sub.subCost(), 0.001);
    }

}