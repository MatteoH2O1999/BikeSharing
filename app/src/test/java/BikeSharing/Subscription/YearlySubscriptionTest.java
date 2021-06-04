package BikeSharing.Subscription;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.util.GregorianCalendar;

import org.junit.Test;

import BikeSharing.Payrate.Payrate;
import BikeSharing.Util.CreditCard;

public class YearlySubscriptionTest {

    @Test
    public void isValidTestNotReturned() {
        CreditCard mockCard = mock(CreditCard.class);
        Payrate mockRate = mock(Payrate.class);
        Subscription spySub, sub;
        GregorianCalendar now = new GregorianCalendar();
        sub = new YearlySubscription(mockCard, "Code", "Pass", mockRate, now, 0);
        spySub = spy(sub);
        doReturn(false).when(spySub).checkLastReturned();
        assertFalse(spySub.isValid());
    }

    @Test
    public void isValidTestNoInterval() {
        CreditCard mockCard = mock(CreditCard.class);
        Payrate mockRate = mock(Payrate.class);
        Subscription spySub, sub;
        GregorianCalendar now = new GregorianCalendar();
        sub = new YearlySubscription(mockCard, "Code", "Pass", mockRate, now, 0);
        spySub = spy(sub);
        doReturn(true).when(spySub).checkLastReturned();
        doReturn(false).when(spySub).returnedInterval();
        assertFalse(spySub.isValid());
    }

    @Test
    public void isValidTestTrue() {
        CreditCard mockCard = mock(CreditCard.class);
        Payrate mockRate = mock(Payrate.class);
        Subscription spySub, sub;
        GregorianCalendar now = new GregorianCalendar();
        sub = new YearlySubscription(mockCard, "Code", "Pass", mockRate, now, 0);
        spySub = spy(sub);
        doReturn(true).when(spySub).checkLastReturned();
        doReturn(true).when(spySub).returnedInterval();
        assertTrue(spySub.isValid());
    }

    @Test
    public void calcExpirationDateTest() {
        GregorianCalendar expected = new GregorianCalendar();
        expected.add(GregorianCalendar.YEAR, 1);
        CreditCard mockCard = mock(CreditCard.class);
        Payrate mockRate = mock(Payrate.class);
        Subscription sub;
        GregorianCalendar now = new GregorianCalendar();
        sub = new YearlySubscription(mockCard, "Code", "Pass", mockRate, now, 0);
        GregorianCalendar actual = sub.calcExpirationDate();
        expected.set(GregorianCalendar.MILLISECOND, 0);
        actual.set(GregorianCalendar.MILLISECOND, 0);
        expected.set(GregorianCalendar.SECOND, 0);
        actual.set(GregorianCalendar.SECOND, 0);
        assertEquals(expected, actual);
    }

    @Test
    public void calcTypeTest() {
        CreditCard mockCard = mock(CreditCard.class);
        Payrate mockRate = mock(Payrate.class);
        Subscription sub;
        GregorianCalendar now = new GregorianCalendar();
        sub = new YearlySubscription(mockCard, "Code", "Pass", mockRate, now, 0);
        assertEquals(0, sub.calcType());
    }

    @Test
    public void subCostTest() {
        CreditCard mockCard = mock(CreditCard.class);
        Payrate mockRate = mock(Payrate.class);
        Subscription sub;
        GregorianCalendar now = new GregorianCalendar();
        sub = new YearlySubscription(mockCard, "Code", "Pass", mockRate, now, 0);
        assertEquals(36.0, sub.subCost(), 0.001);
    }

}