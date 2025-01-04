import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

class ManagerAssignTest {

    @Test
    void assignExistingToExisting() throws SQLException{
        Manager managerTest = new Manager();
        Assertions.assertTrue(managerTest.addModuleToCourse("CS308", "CES"));
    }

    @Test
    void assignToNonExistingCourse() throws SQLException{
        Manager managerTest = new Manager();
        Assertions.assertFalse(managerTest.addModuleToCourse("CS308", "JELLY"));
    }

    @Test
    void assignNonExistingModule() throws SQLException{
        Manager managerTest = new Manager();
        Assertions.assertFalse(managerTest.addModuleToCourse("CS999", "CES"));
    }

    @Test
    void assignNonToNon() throws SQLException{
        Manager managerTest = new Manager();
        Assertions.assertFalse(managerTest.addModuleToCourse("CS999", "JELLY"));
    }
}
