package BikeSharing.Usertypes;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

import org.junit.Test;
import org.mockito.MockedStatic;

import BikeSharing.API.SchoolAPI;
import BikeSharing.Payrate.StudentRate;
import BikeSharing.Usertypes.Proof.StudentProof;

public class StudentTest {

    @Test
    public void isEligibleTest() {
        StudentProof mockProof = mock(StudentProof.class);
        Student user = new Student(mockProof);
        try (MockedStatic<SchoolAPI> mockApi = mockStatic(SchoolAPI.class)) {
            user.isEligible();
            mockApi.verify(() -> SchoolAPI.isStudent(mockProof));
        }
    }

    @Test
    public void getPayrateTest() {
        StudentProof mockProof = mock(StudentProof.class);
        Student user = new Student(mockProof);
        assertEquals(StudentRate.class, user.getPayrate().getClass());
    }

}