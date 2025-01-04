import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//manager model for required functionality

public class Manager extends User{
    public Manager() {
    }
    static Connection myDB = DbConnect.getDbConnect(); //reference to db connect3 class

    //Returns the names of all users awaiting activation
    public String getDeactivated(String accountType) throws SQLException {
        String sqlQuery = "SELECT Email FROM " +  accountType.toLowerCase() + " WHERE Activated = 0";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ResultSet rs = ps.executeQuery();
        String names = accountType + "\n";
        while (rs.next()) {
            String param = accountType.toLowerCase() + ".Email";
            names = names.concat(rs.getString(param) + "\n");
        }
        names = names + "\n";
        return names;
    }

    //Returns the names of all users that have been activated
    public String getActivated(String accountType) throws SQLException {
        String sqlQuery = "SELECT Email FROM " +  accountType.toLowerCase() + " WHERE Activated = 1";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ResultSet rs = ps.executeQuery();
        String names = accountType + "\n";
        while (rs.next()) {
            String param = accountType.toLowerCase() + ".Email";
            names = names.concat(rs.getString(param) + "\n");
        }
        names = names + "\n";
        return names;
    }

    //Allows the Manager to activate or deactivate account a given account
    public boolean editAccount(String email, int setAct,String accountType)throws SQLException {
        String sqlQuery = "UPDATE " + accountType.toLowerCase() + " SET Activated = ? WHERE Email = ?";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setInt(1, setAct);
        ps.setString(2,email);

        int rows = ps.executeUpdate();
        return rows > 0;
    }

    //Resets a given user's password to 'password'
    public boolean resetPassword(String email, String accountType)throws SQLException{
        String sqlQuery = "UPDATE " + accountType.toLowerCase() + " SET Password = 'password', Password_Reset_Request = 0 WHERE Email = ? and Password_Reset_Request = 1";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1,email);
        int rows = ps.executeUpdate();
        return rows > 0;
    }

    //Returns a list of names of users from a given account type that have requested a password reset
    public String getResets(String accountType) throws SQLException {
        String sqlQuery = "SELECT Email FROM " +  accountType.toLowerCase() + " WHERE Password_Reset_Request = 1";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ResultSet rs = ps.executeQuery();
        String names = accountType + "\n";
        while (rs.next()) {
            String param = accountType.toLowerCase() + ".Email";
            names = names.concat(rs.getString(param) + "\n");
        }
        names = names + "\n";
        return names;
    }

    //Returns true if a given lecturer is successfully assigned to a given module
    public boolean assignModule(String email, String moduleCode)throws SQLException{
        String sqlQuery = "UPDATE module SET Lecturer_ID = (SELECT lecturer.ID FROM lecturer WHERE lecturer.Email = ?) WHERE module.Module_Code =  ?";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1,email);
        ps.setString(2,moduleCode);
        int rows = ps.executeUpdate();
        return rows > 0;
    }

    //Returns true if a given module is assigned to a course and false if not
    public boolean checkModuleCourseAssignment(String moduleCode) throws SQLException{
        String sqlQuery = "SELECT Course_Code FROM courseContent WHERE Module_Code = ?";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1, moduleCode);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    //Returns true if a given module is successfully added to a given course and false if not
    public boolean addModuleToCourse(String moduleCode, String courseCode) throws SQLException{
        Lecturer lm = new Lecturer();
        if((!checkCourseExists(courseCode) || !lm.checkModuleExists(moduleCode))){
            return false;
        }
        String sqlQuery = "INSERT INTO courseContent VALUES (?, ?)";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1, courseCode);
        ps.setString(2, moduleCode);
        return ps.executeUpdate() > 0;
    }

    //Returns true if a given student is successfully added to a given course and false if not
    public boolean enrollStudent (String email, String courseCode)throws SQLException{
        //ensure account both exists and is activated before changing password
        if((!checkExists(email) || !checkActivated(email,"student") || !checkCourseExists(courseCode) || checkEmailsInCourse(courseCode).contains(email))){
            return false;
        }
        String sqlQuery = "UPDATE student SET Course_Code = (SELECT course.Course_Code FROM course WHERE course.Course_Code = ?) WHERE Email = ?";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1,courseCode.toUpperCase());
        ps.setString(2,email);
        int rows = ps.executeUpdate();
        return rows > 0;
    }

    //Returns true if a given student is successfully issued a pass/fail/resit decision
    //and returns false not, or if student doesn't exist or isn't activated
    public boolean issueDecision (String email)throws SQLException{
        //ensure account both exists and is activated before changing password
        if((!checkExists(email) || !checkActivated(email,"student"))){
            return false;
        }
        String sqlQuery = "UPDATE student SET Decision = ? WHERE Email = ?";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1, checkAllPasses(email));
        ps.setString(2,email);
        int rows = ps.executeUpdate();
        return rows > 0;
    }

    //Returns the pass status of every module for a given student
    public String checkAllPasses(String email) throws SQLException{
        //(module INNER JOIN enrolment ON module.Module_Code = enrolment.Module_Code) INNER JOIN student ON enrolment.Student_ID = student.Student_ID
        String sqlQuery = "SELECT enrolment.Grade, enrolment.Attempt_Number, module.Max_Attempts, course.Num_Compensated_Modules FROM ((enrolment INNER JOIN module ON enrolment.Module_Code = module.Module_Code) INNER JOIN courseContent ON module.Module_Code = courseContent.Module_Code) INNER JOIN course ON courseContent.Course_Code = course.Course_Code WHERE enrolment.Student_ID = (SELECT student.Student_ID FROM student WHERE Email = ?);";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1,email);
        ResultSet rs = ps.executeQuery();
        int comps = 0;
        while(rs.next()){
            if (rs.getInt("enrolment.Grade") < 50){
                if (rs.getInt("enrolment.Grade") < 40){
                    if (rs.getInt("enrolment.Attempt_Number") > rs.getInt("module.Max_Attempts")){
                        return "W";
                    }else{
                        return "R";
                    }
                }else{
                    comps++;
                    if (comps > rs.getInt("course.Num_Compensated_Modules")){
                        return "R";
                    }
                }
            }
        }
        return "P";
    }

    //Returns an ArrayList of modules part of a given course
    public ArrayList<String> checkModulesInCourse(String course) throws SQLException{
        String sqlQuery = "Select Module_Code from courseContent where Course_Code = ?";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1,course);
        ResultSet rs = ps.executeQuery();

        ArrayList<String> moduleIDs = new ArrayList<>();
        while (rs.next()) {
            moduleIDs.add(rs.getString("Module_Code"));
        }
        return moduleIDs;
    }

    //Returns true if a given student is successfully enrolled in a given ArrayList of modules and false if not
    public boolean updateEnrolmentForNewStudent(ArrayList<String> moduleIDs, String email) throws SQLException{
        String sqlQuery = "Insert into enrolment (Student_ID, Module_Code) Values ((SELECT Student_ID FROM student WHERE email = ?),?)";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1, email);
        int rs = 0;
        for (int i = 0; i < moduleIDs.size(); i++){
            ps.setString(2, moduleIDs.get(i));
            ps.executeUpdate();
            rs++;
        }
        return rs > moduleIDs.size();
    }

    //Returns an ArrayList of studentIDs enrolled in a given course
    public ArrayList<String> checkStudentsInCourse(String course) throws SQLException{
        String sqlQuery = "Select Student_ID from student where Course_Code = ?";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1,course);
        ResultSet rs = ps.executeQuery();

        ArrayList<String> studentIDs = new ArrayList<>();
        while (rs.next()) {
            studentIDs.add(rs.getString("Student_ID"));
        }
        return studentIDs;
    }

    //Returns an ArrayList of student emails enrolled in a given course
    public ArrayList<String> checkEmailsInCourse(String course) throws SQLException{
        String sqlQuery = "Select Email from student where Course_Code = ?";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1,course);
        ResultSet rs = ps.executeQuery();

        ArrayList<String> studentIDs = new ArrayList<>();
        while (rs.next()) {
            studentIDs.add(rs.getString("Email"));
        }
        return studentIDs;
    }

    //Returns true if a given ArrayList of students is successfully enrolled in a given course and false if not
    public boolean updateEnrolment(ArrayList<String> studentIDs, String moduleCode) throws SQLException{
        String sqlQuery = "Insert into enrolment (Student_ID, Module_Code) Values (?,?)";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(2, moduleCode);
        int rs = 0;
        for (int i = 0; i < studentIDs.size(); i++){
            ps.setString(1, studentIDs.get(i));
            ps.executeUpdate();
            rs++;
        }
        return rs > studentIDs.size();
    }

    //Returns true if a given student is successfully deleted from a course and false if not
    public boolean deleteOccurrences(String email) throws SQLException{
        String sqlQuery = "DELETE FROM enrolment WHERE Student_ID = (SELECT Student_ID FROM student WHERE Email = ?)";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1, email);
        int rows = ps.executeUpdate();
        return rows <= 0;
    }

    //Returns true if the maximum number of attempts a student can have at a given module is successfully updated to a given value
    //and returns false if unsuccessful
    public boolean updateMaxAttempts(String moduleCode, int newAttempts)throws SQLException{
        String sqlQuery = "UPDATE module SET Max_Attempts = ? WHERE Module_Code = ?";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setInt(1, newAttempts);
        ps.setString(2,moduleCode);
        int rows = ps.executeUpdate();
        return rows > 0;
    }

    //Returns true if the maximum number of compensated modules for a given course is successfully updated to a given value
    //and false if unsuccessful
    public boolean updateCompensatedModules(int newLimit, String courseID)throws SQLException{//Discuss with others
        String sqlQuery = "UPDATE course SET Num_Compensated_Modules = ? WHERE Course_Code = ?";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setInt(1, newLimit);
        ps.setString(2,courseID);
        int rows = ps.executeUpdate();
        return rows > 0;
    }

    //Returns true if a new course is successfully created from a set of given details and false if not.
    public boolean addCourse(String courseCode, String courseName, String deptID, String gradCourse)throws SQLException{

        String sqlQuery = "INSERT INTO course VALUES (?, ?, (SELECT Department_ID from department WHERE Name = ?), ?, ?)";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1,courseCode);
        ps.setString(2,courseName);
        ps.setString(3,deptID);
        ps.setString(4,gradCourse);
        ps.setInt(5,0);

        int rows = ps.executeUpdate();
        return rows > 0;
    }

    //Returns true if a new module is successfully created from a set of given values and false if not
    public boolean addModule(String moduleCode, String moduleName, int credits, String moduleInfo, int numSemesters, int maxAttempt, int lectID)throws SQLException{
        String sqlQuery = "INSERT INTO module VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1,moduleCode);
        ps.setString(2,moduleName);
        ps.setInt(3,credits);
        ps.setString(4, moduleInfo);
        ps.setInt(5,numSemesters);
        ps.setInt(6, maxAttempt);
        ps.setNull(7,lectID);


        int rows = ps.executeUpdate();
        return rows > 0;
    }

    //Returns all details related to a given course
    public String dispCourseDetails(String courseCode) throws SQLException{
        String sqlQuery = "SELECT * FROM course WHERE Course_Code = ?";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1,courseCode);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return "Course Code: " + rs.getString("Course_Code") + "\nCourse Name: " + rs.getString("Course_Name")  + "\nCourse's Department ID: " + rs.getString("Department_ID")  + "\nGraduate Type: " + rs.getString("Post_Grad");
    }

    //Returns all details related to a given module
    public String dispModuleDetails(String moduleCode)throws SQLException{
        String sqlQuery = "SELECT * FROM module WHERE Module_Code = ?";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1,moduleCode);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return "Module Code: " + rs.getString("Module_Code") + "\nName: " + rs.getString("Name") + "\nCredits: " + rs.getString("Credit") + "\nModule Info: " + rs.getString("Info") + "\nSemesters: " + rs.getString("Semesters") + "\nMaximum Attempts: " + rs.getString("Max_Attempts") + "\nLecturer ID: " + rs.getString("Lecturer_ID");
    }

    //Returns true if a given course is successfully updated with a given grad status and max number of compensated modules
    //and return false if unsuccessful
    public boolean updateCourseInfo(String courseCode ,String newGradStatus, int newCompensatedModules)throws SQLException{
        String sqlQuery = "UPDATE course SET Num_Compensated_Modules = ?, Post_Grad = ? WHERE Course_Code = ?";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setInt(1,newCompensatedModules);
        ps.setString(2,newGradStatus);
        ps.setString(3, courseCode);
        int rows = ps.executeUpdate();
        return rows > 0;
    }

    //Returns true if a given course exists and false if not
    public boolean checkCourseExists(String courseCode) throws SQLException {
        String sqlQuery = "Select * FROM course WHERE Course_Code = \"" + courseCode.toUpperCase() + "\"";
        try (PreparedStatement ps = myDB.prepareStatement(sqlQuery)) {
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    //Returns true if a given department exists and false if not
    public boolean checkDeptExists(String deptName) throws SQLException {
        String sqlQuery = "Select * FROM department WHERE Name = \"" + deptName.toUpperCase() + "\"";
        try (PreparedStatement ps = myDB.prepareStatement(sqlQuery)) {
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    //Returns true if a new Manager is successfully created from given details
    public boolean createManager(String username, String password) throws SQLException{
        String sqlQuery = "Insert into manager (Email,Password) VALUES (?,?)";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1,username);
        ps.setString(2,password);
        int rows = ps.executeUpdate();
        return rows > 0;
        }
    }


