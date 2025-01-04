import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ManagerTest {

    @Test
    void enrollStudentRegular() throws SQLException {
        Manager managerTest = new Manager();
        managerTest.enrollStudent("seantest","EME");
        Assertions.assertTrue(managerTest.enrollStudent("seantest", "CES"));

    }

    @Test
    void enrollImaginaryStudent() throws SQLException {
        Manager managerTest = new Manager();
        Assertions.assertFalse(managerTest.enrollStudent("wbjfaj", "CES"));
    }
    @Test
    void enrollImaginaryCourse() throws SQLException {
        Manager managerTest = new Manager();
        Assertions.assertFalse(managerTest.enrollStudent("owen","Fake Course"));
    }
    @Test
    void enrollAlready() throws SQLException {
        Manager managerTest = new Manager();
        managerTest.enrollStudent("owen","CES");
        Assertions.assertFalse(managerTest.enrollStudent("owen","CES"));
    }


}