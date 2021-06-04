package BikeSharing.Util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CreditCardExpirationDateTest {

    @Test
    public void constructorTest() {
        int year = 2020;
        int month = 10;
        CreditCardExpirationDate expDate = new CreditCardExpirationDate(year, month);
        assertEquals(year, expDate.year);
        assertEquals(month, expDate.month);
    }

    @Test
    public void compareToTestYearAfter() {
        CreditCardExpirationDate expDate = new CreditCardExpirationDate(2020, 2);
        CreditCardExpirationDate expComp = new CreditCardExpirationDate(2021, 2);
        assertTrue(expDate.compareTo(expComp) < 0);
    }

    @Test
    public void compareToTestYearBefore() {
        CreditCardExpirationDate expDate = new CreditCardExpirationDate(2020, 2);
        CreditCardExpirationDate expComp = new CreditCardExpirationDate(2019, 2);
        assertTrue(expDate.compareTo(expComp) > 0);
    }

    @Test
    public void compareToTestYearSameMonthAfter() {
        CreditCardExpirationDate expDate = new CreditCardExpirationDate(2020, 2);
        CreditCardExpirationDate expComp = new CreditCardExpirationDate(2020, 3);
        assertTrue(expDate.compareTo(expComp) < 0);
    }

    @Test
    public void compareToTestYearSameMonthBefore() {
        CreditCardExpirationDate expDate = new CreditCardExpirationDate(2020, 2);
        CreditCardExpirationDate expComp = new CreditCardExpirationDate(2020, 1);
        assertTrue(expDate.compareTo(expComp) > 0);
    }

    @Test
    public void compareToTestYearSameMonthSame() {
        CreditCardExpirationDate expDate = new CreditCardExpirationDate(2020, 2);
        CreditCardExpirationDate expComp = new CreditCardExpirationDate(2020, 2);
        assertTrue(expDate.compareTo(expComp) == 0);
    }

}