package BikeSharing.Subscription.Manager;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.MockedConstruction;

import BikeSharing.Subscription.DailySubscription;
import BikeSharing.Subscription.WeeklySubscription;
import BikeSharing.Subscription.YearlySubscription;
import BikeSharing.Subscription.Util.DuplicateCardException;
import BikeSharing.Subscription.Util.ExpirationException;
import BikeSharing.Usertypes.AbstractUser;
import BikeSharing.Usertypes.Util.ProofException;
import BikeSharing.Util.CreditCard;

public class SubscriptionManagerTest {

    @Test(expected = ProofException.class)
    public void createSubscriptionTestNotEligible() throws NullPointerException, ProofException, ExpirationException, DuplicateCardException {
        AbstractUser mockUser = mock(AbstractUser.class);
        CreditCard mockCard = mock(CreditCard.class);
        when(mockUser.isEligible()).thenReturn(false);
        SubscriptionManager.createSubscription(mockUser, 0, "", mockCard);
    }

    @Test(expected = ExpirationException.class)
    public void createSubscriptionTestNotValid() throws NullPointerException, ProofException, ExpirationException, DuplicateCardException {
        AbstractUser mockUser = mock(AbstractUser.class);
        CreditCard mockCard = mock(CreditCard.class);
        when(mockUser.isEligible()).thenReturn(true);
        when(mockCard.checkValidity()).thenReturn(false);
        SubscriptionManager.createSubscription(mockUser, 0, "", mockCard);
    }

    @Test
    public void createSubscriptionTestYearly() throws NullPointerException, ProofException, ExpirationException, DuplicateCardException {
        AbstractUser mockUser = mock(AbstractUser.class);
        CreditCard mockCard = mock(CreditCard.class);
        when(mockUser.isEligible()).thenReturn(true);
        when(mockCard.checkValidity()).thenReturn(true);
        try (MockedConstruction<YearlySubscription> mockConstructor = mockConstruction(YearlySubscription.class)) {
            SubscriptionManager.createSubscription(mockUser, 0, "", mockCard);
            assertTrue(mockConstructor.constructed().size() > 0);
        }
    }

    @Test
    public void createSubscriptionTestWeekly() throws NullPointerException, ProofException, ExpirationException, DuplicateCardException {
        AbstractUser mockUser = mock(AbstractUser.class);
        CreditCard mockCard = mock(CreditCard.class);
        when(mockUser.isEligible()).thenReturn(true);
        when(mockCard.checkValidity()).thenReturn(true);
        try (MockedConstruction<WeeklySubscription> mockConstructor = mockConstruction(WeeklySubscription.class)) {
            SubscriptionManager.createSubscription(mockUser, 1, "", mockCard);
            assertTrue(mockConstructor.constructed().size() > 0);
        }
    }

    @Test
    public void createSubscriptionTestDaily() throws NullPointerException, ProofException, ExpirationException, DuplicateCardException {
        AbstractUser mockUser = mock(AbstractUser.class);
        CreditCard mockCard = mock(CreditCard.class);
        when(mockUser.isEligible()).thenReturn(true);
        when(mockCard.checkValidity()).thenReturn(true);
        try (MockedConstruction<DailySubscription> mockConstructor = mockConstruction(DailySubscription.class)) {
            SubscriptionManager.createSubscription(mockUser, 2, "", mockCard);
            assertTrue(mockConstructor.constructed().size() > 0);
        }
    }

}