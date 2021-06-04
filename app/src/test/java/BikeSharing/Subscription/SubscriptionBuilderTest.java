package BikeSharing.Subscription;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mockConstruction;

import org.junit.Test;
import org.mockito.MockedConstruction;

import BikeSharing.Subscription.DAO.SubscriptionDataTransfer;

public class SubscriptionBuilderTest {

    @Test
    public void buildTest0() {
        SubscriptionDataTransfer mockData = new SubscriptionDataTransfer();
        mockData.type = 0;
        try (MockedConstruction<YearlySubscription> constructor = mockConstruction(YearlySubscription.class)) {
            SubscriptionBuilder.buildSubscription(mockData);
            assertTrue(constructor.constructed().size() > 0);
        }
    }

    @Test
    public void buildTest1() {
        SubscriptionDataTransfer mockData = new SubscriptionDataTransfer();
        mockData.type = 1;
        try (MockedConstruction<WeeklySubscription> constructor = mockConstruction(WeeklySubscription.class)) {
            SubscriptionBuilder.buildSubscription(mockData);
            assertTrue(constructor.constructed().size() > 0);
        }
    }

    @Test
    public void buildTest2() {
        SubscriptionDataTransfer mockData = new SubscriptionDataTransfer();
        mockData.type = 2;
        try (MockedConstruction<DailySubscription> constructor = mockConstruction(DailySubscription.class)) {
            SubscriptionBuilder.buildSubscription(mockData);
            assertTrue(constructor.constructed().size() > 0);
        }
    }

    @Test
    public void buildTestOther() {
        SubscriptionDataTransfer mockData = new SubscriptionDataTransfer();
        mockData.type = -5;
        assertNull(SubscriptionBuilder.buildSubscription(mockData));
        mockData.type = 4;
        assertNull(SubscriptionBuilder.buildSubscription(mockData));
    }

}