package BikeSharing.Util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CreditCardTest {

    @Test(expected = RuntimeException.class)
    public void constructorTestNegativeCardNumber() {
        long number = -2;
        int cv2 = 150;
        int expYear = 1990;
        int expMonth = 10;
        CreditCard card = new CreditCard(number, cv2, expYear, expMonth);
        assertEquals(number, card.getCardNumber());
        assertEquals(cv2, card.getCvv());
        assertEquals(expYear, card.getCardExpiration().year);
        assertEquals(expMonth, card.getCardExpiration().month);
    }

    @Test(expected = RuntimeException.class)
    public void constructorTestSmallCvv() {
        long number = 2;
        int cv2 = 90;
        int expYear = 1990;
        int expMonth = 10;
        CreditCard card = new CreditCard(number, cv2, expYear, expMonth);
        assertEquals(number, card.getCardNumber());
        assertEquals(cv2, card.getCvv());
        assertEquals(expYear, card.getCardExpiration().year);
        assertEquals(expMonth, card.getCardExpiration().month);
    }

    @Test(expected = RuntimeException.class)
    public void constructorTestBigCvv() {
        long number = 2;
        int cv2 = 1500;
        int expYear = 1990;
        int expMonth = 10;
        CreditCard card = new CreditCard(number, cv2, expYear, expMonth);
        assertEquals(number, card.getCardNumber());
        assertEquals(cv2, card.getCvv());
        assertEquals(expYear, card.getCardExpiration().year);
        assertEquals(expMonth, card.getCardExpiration().month);
    }

    @Test(expected = RuntimeException.class)
    public void constructorTestSmallYear() {
        long number = 2;
        int cv2 = 150;
        int expYear = -1000;
        int expMonth = 10;
        CreditCard card = new CreditCard(number, cv2, expYear, expMonth);
        assertEquals(number, card.getCardNumber());
        assertEquals(cv2, card.getCvv());
        assertEquals(expYear, card.getCardExpiration().year);
        assertEquals(expMonth, card.getCardExpiration().month);
    }

    @Test(expected = RuntimeException.class)
    public void constructorTestSmallMonth() {
        long number = 2;
        int cv2 = 150;
        int expYear = 1990;
        int expMonth = -1;
        CreditCard card = new CreditCard(number, cv2, expYear, expMonth);
        assertEquals(number, card.getCardNumber());
        assertEquals(cv2, card.getCvv());
        assertEquals(expYear, card.getCardExpiration().year);
        assertEquals(expMonth, card.getCardExpiration().month);
    }

    @Test(expected = RuntimeException.class)
    public void constructorTestBigMonth() {
        long number = 2;
        int cv2 = 150;
        int expYear = 1990;
        int expMonth = 12;
        CreditCard card = new CreditCard(number, cv2, expYear, expMonth);
        assertEquals(number, card.getCardNumber());
        assertEquals(cv2, card.getCvv());
        assertEquals(expYear, card.getCardExpiration().year);
        assertEquals(expMonth, card.getCardExpiration().month);
    }

    @Test
    public void constructorTestOk() {
        long number = 2;
        int cv2 = 150;
        int expYear = 1990;
        int expMonth = 10;
        CreditCard card = new CreditCard(number, cv2, expYear, expMonth);
        assertEquals(number, card.getCardNumber());
        assertEquals(cv2, card.getCvv());
        assertEquals(expYear, card.getCardExpiration().year);
        assertEquals(expMonth, card.getCardExpiration().month);
    }

}