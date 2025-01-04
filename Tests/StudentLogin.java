import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;



class StudentLogin {

    @Test
    void loginUserNotExisting() throws SQLException {
        User userTest = new User();
        Assertions.assertFalse(userTest.login("e", "e", "student"));
    }

    @Test
    void loginCorrectDetails() throws SQLException {
        User userTest = new User();
        Assertions.assertTrue(userTest.login("Test", "Testing", "student"));
    }

    @Test
    void loginInvalidCredentials() throws SQLException{
        User userTest = new User();
        Assertions.assertFalse(userTest.login("Test", "password", "student"));
        Assertions.assertFalse(userTest.login("username", "Testing", "student"));
        Assertions.assertFalse(userTest.login("username", "password", "student"));

    }

    @Test
    void loginMissingCredentials() throws SQLException{
        User userTest = new User();
        Assertions.assertFalse(userTest.login("", "", "student"));
        Assertions.assertFalse(userTest.login("", "Testing", "student"));
        Assertions.assertFalse(userTest.login("Test", "", "student"));
    }


}