import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;


class StudentSignUp {

    @Test
    void signUpCorrectStudent() throws SQLException {
        User userTest = new User();
        Assertions.assertTrue(userTest.signUp("unitTestEmail", "unitTestPassword", "unitTestForename", "unitTestSurname", "Male", "student", Date.valueOf("2023-01-01")));
    }

    @Test
    void signUpExistingDetails() throws SQLException {
        User userTest = new User();
        boolean test = userTest.checkExists("Test");
        if (test == false){
            Assertions.assertFalse(userTest.signUp("Test", "Testing", "testing", "the test", "Male", "student", Date.valueOf("2023-01-01")));
        }
    }
    @Test
    void signUpMissing() throws SQLException{
        User userTest = new User();
        Assertions.assertFalse(userTest.signUp("", "smelly", "smelly", "smelly", "Male", "student", Date.valueOf("2023-01-01")));
        Assertions.assertFalse(userTest.signUp("smelly", "", "smelly", "smelly", "Male", "student", Date.valueOf("2023-01-01")));
        Assertions.assertFalse(userTest.signUp("smelly", "smelly", "", "smelly", "Male", "student", Date.valueOf("2023-01-01")));
        Assertions.assertFalse(userTest.signUp("smelly", "smelly", "smelly", "", "Male", "student", Date.valueOf("2023-01-01")));
        Assertions.assertFalse(userTest.signUp("smelly", "smelly", "smelly", "smelly", "", "student", Date.valueOf("2023-01-01")));
        Assertions.assertFalse(userTest.signUp("smelly", "smelly", "smelly", "smelly", "Male", "", Date.valueOf("2023-01-01")));
    }

    @Test
    void signUpCorrectLecturer() throws SQLException{
        Lecturer lectTest = new Lecturer();
        Assertions.assertTrue(lectTest.signUpLec("MrJUnit","BigBoy","Gerald","Soup","Male","Lecturer","PhD",Date.valueOf("2023-01-01")));
    }


}