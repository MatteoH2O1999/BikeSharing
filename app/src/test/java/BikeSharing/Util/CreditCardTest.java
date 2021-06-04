package BikeSharing.Util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mockStatic;

import java.time.LocalDate;

import org.junit.Test;
import org.mockito.MockedStatic;

import BikeSharing.API.BankAPI;

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

    @Test
    public void checkValidityTestExpired() {
        CreditCard card = new CreditCard(2, 150, 2020, 2);
        LocalDate mockLocal = LocalDate.of(2020, 4, 1);
        try (MockedStatic<LocalDate> mockLocalDate = mockStatic(LocalDate.class)) {
            try (MockedStatic<BankAPI> mockApi = mockStatic(BankAPI.class)) {
                mockLocalDate.when(LocalDate::now).thenReturn(mockLocal);
                assertFalse(card.checkValidity());
                mockApi.verifyNoInteractions();
            }
        }
    }

    @Test
    public void checkValidityTestApi() {
        CreditCard card = new CreditCard(2, 150, 2020, 2);
        LocalDate mockLocal = LocalDate.of(2020, 1, 1);
        try (MockedStatic<LocalDate> mockLocalDate = mockStatic(LocalDate.class)) {
            try (MockedStatic<BankAPI> mockApi = mockStatic(BankAPI.class)) {
                mockLocalDate.when(LocalDate::now).thenReturn(mockLocal);
                card.checkValidity();
                mockApi.verify(() -> BankAPI.checkCreditCard(card));
            }
        }
    }

    @Test
    public void addPaymentTest() {
        CreditCard card = new CreditCard(2, 150, 2020, 2);
        float amount = (float)2.0;
        try (MockedStatic<BankAPI> mockApi = mockStatic(BankAPI.class)) {
            card.addPayment(amount);
            mockApi.verify(() -> BankAPI.pay(amount, card));
        }
    }

}