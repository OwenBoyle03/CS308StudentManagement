import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
// this class will model all the required lecturer functions
public class Lecturer extends User {

    static Connection myDB = DbConnect.getDbConnect();
//constructor for lecturer model
    public Lecturer() {
    }

    //Alternative sign up method for lecturers containing extra information as we need to take a qualifcations
    public boolean signUpLec(String email, String password, String firstName, String lastName, String gender, String typeAccount, String qualification, Date DOB) throws SQLException {
        String sqlQuery = "INSERT INTO " + typeAccount.toLowerCase() +
                " (Email, Password, First_Name, Surname, Gender, DOB, Qualification) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1, email);
        ps.setString(2, password);
        ps.setString(3, firstName);
        ps.setString(4, lastName);
        ps.setString(5, gender);
        ps.setDate(6, DOB);
        ps.setString(7, qualification);

        int rows = ps.executeUpdate();
        return rows > 0;
        // Check if successful
    }

    //Returns true if a given module exists in the database and false if not
    public boolean checkModuleExists(String moduleCode) throws SQLException {
        String sqlQuery = "Select * From module Where Module_Code = \"" + moduleCode.toLowerCase() + "\"";
        try (PreparedStatement ps = myDB.prepareStatement(sqlQuery)) {
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    //Returns true if given module info is successfully updated and false if the given module does not exist
    public boolean updateInfo(String moduleCode, String info) throws SQLException {
        if (checkModuleExists(moduleCode)) {
            String sqlQuery = "Update module Set Info = ? Where Module_Code = ?";
            PreparedStatement ps = myDB.prepareStatement(sqlQuery);
            ps.setString(1, info);
            ps.setString(2, moduleCode);

            int rows = ps.executeUpdate();
            return rows > 0;
        }
        return false;
    }

    //Returns true if there is material for a given module in a given week and returns false if not
    public boolean checkModuleMaterialExists(String moduleCode, String week) throws SQLException {
        String sqlQuery = "Select * From moduleMaterial Where Module_Code = ? AND Week = ?";
        try (PreparedStatement ps = myDB.prepareStatement(sqlQuery)) {
            ps.setString(1, moduleCode);
            ps.setString(2, week);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    //Returns true if a given module's material for a given week is successfully updated with given Lab notes
    //and false if the given module or its material don't exist, or if nothing was updated
    public boolean updateLabMaterial(String moduleCode, String week, byte[] labNote) throws SQLException {
        if (checkModuleMaterialExists(moduleCode, week) && checkModuleExists(moduleCode)) {
            String sqlQuery = "Update moduleMaterial Set Lab_Note = ? Where Module_Code = ? AND Week = ?";
            PreparedStatement ps = myDB.prepareStatement(sqlQuery);
            ps.setBytes(1, labNote);
            ps.setString(2, moduleCode);
            ps.setString(3, week);

            int rows = ps.executeUpdate();
            return rows > 0;
        } else if (checkModuleExists(moduleCode)) {
            String sqlQuery = "Insert into moduleMaterial (Module_Code, Week, Lab_Note, Lecture_Note) "
                    + "Values (?, ?, ?, NULL)";
            PreparedStatement ps = myDB.prepareStatement(sqlQuery);
            ps.setString(1, moduleCode);
            ps.setString(2, week);
            ps.setBytes(3, labNote);

            int rows = ps.executeUpdate();
            return rows > 0;
        }
        return false;
    }

    //Returns true if a given module's material for a given week is successfully updated with given lecture notes
    //and false if the given module or its material don't exist, or if nothing was updated
    public boolean updateLectureMaterial(String moduleCode, String week, byte[] lectureNote) throws SQLException {
        if (checkModuleMaterialExists(moduleCode, week)) {
            String sqlQuery = "Update moduleMaterial Set Lecture_Note = ? Where Module_Code = ? AND Week = ?";
            PreparedStatement ps = myDB.prepareStatement(sqlQuery);
            ps.setBytes(1, lectureNote);
            ps.setString(2, moduleCode);
            ps.setString(3, week);

            int rows = ps.executeUpdate();
            return rows > 0;
        } else if (checkModuleExists(moduleCode)) {
            String sqlQuery = "Insert into moduleMaterial (Module_Code, Week, Lab_Note, Lecture_Note) "
                    + "Values (?, ?, NULL, ?)";
            PreparedStatement ps = myDB.prepareStatement(sqlQuery);
            ps.setString(1, moduleCode);
            ps.setString(2, week);
            ps.setBytes(3, lectureNote);

            int rows = ps.executeUpdate();
            return rows > 0;
        }
        return false;
    }

    //Returns a string of students' full names that are enrolled in a given module
    public String displayEnrolledStudents(String moduleCode) throws SQLException {
        if (checkModuleExists(moduleCode)) {
            String sqlQuery = "Select student.First_Name, student.Surname FROM student Where Student_ID IN " +
                    "(Select Student_ID From enrolment Where Module_Code = ?)";
            PreparedStatement ps = myDB.prepareStatement(sqlQuery);
            ps.setString(1, moduleCode);

            ResultSet rs = ps.executeQuery();
            String names = "Students enrolled in module: ";
            while (rs.next()) {
                names = names.concat("\n" + rs.getString("student.First_Name") + " " + rs.getString("student.Surname"));
            }
            return names;
        }
        return "";
    }
    //Returns true if any results exist for a given student in a given module, and false if not
    public boolean checkResultExists(String studID, String moduleCode) throws SQLException {
        String sqlQuery = "Select * From results Where Student_ID = ? AND Module_Code = ?";
        try (PreparedStatement ps = myDB.prepareStatement(sqlQuery)) {
            ps.setString(1, studID);
            ps.setString(2, moduleCode);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }
    //Returns true if a given lecturer is assigned to a given module and false if not
    public boolean isLecturerForModule(String email, String moduleCode) throws SQLException {
        String sqlQuery = "Select * From module, lecturer Where module.Lecturer_ID = lecturer.ID AND lecturer.Email = ? AND module.Module_Code = ?";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1, email);
        ps.setString(2, moduleCode);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    //Returns an ArrayList of modules run by a given lecturer
    public ArrayList<String> listOfLecturersModules(String email) throws SQLException {
        String sqlQuery = "Select Module_Code From module Where module.Lecturer_ID = (Select ID From lecturer Where Email = ?)";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        ArrayList<String> list = new ArrayList<>();
        while (rs.next()) {
            list.add(rs.getString("Module_Code"));
        }
        return list;
    }

    //Returns the email and student ID for a given student ID
    public String listEmailAndID(String ID) throws SQLException{
        String sqlQuery = "Select Email, Student_ID From student Where Student_ID = ?";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1, ID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("Email") + " - " + rs.getString("Student_ID") + "\n";
    }

    //Returns an ArrayList of student IDs that are enrolled in a given module
    public ArrayList<String> listOfEnrolledIDs(String modCode) throws SQLException {
        String sqlQuery = "Select Student_ID From enrolment Where Module_Code = ?";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1, modCode);
        ResultSet rs = ps.executeQuery();
        ArrayList<String> enrolIDS = new ArrayList<>();
        while (rs.next()) {
            enrolIDS.add(rs.getString("Student_ID"));
        }
        return enrolIDS;
    }

    //Returns number of semesters a given module runs for
    public int checkModuleSemesters(String moduleCode) throws SQLException {
        if (checkModuleExists(moduleCode)) {
            String sqlQuery = "Select Semesters From module Where Module_Code = ?";
            PreparedStatement ps = myDB.prepareStatement(sqlQuery);
            ps.setString(1, moduleCode);

            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("Semesters");
        }
        return 0;
    }

    //Returns true if given student has a result in a given module for a given semester and attempt number and returns false if not
    public boolean checkForStudentMark(String studentID, String moduleCode, String semester, String attemptNumber) throws SQLException {
        String sqlQuery = "Select * From results Where Student_ID = ? AND Module_Code = ? AND Semester = ? AND Attempt_Number = ?";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1, studentID);
        ps.setString(2, moduleCode);
        ps.setString(3, semester);
        ps.setString(4, attemptNumber);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    //Returns the maximum number of attempts available for a given module
    public int checkMaxAttempts(String moduleCode) throws SQLException {
        String sqlQuery = "Select Max_Attempts From module Where Module_Code = ?";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1,moduleCode);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt("Max_Attempts");
    }

    //Returns true if a given student's mark for a given module, semester, and attempt number is successfully updated with a given mark
    //and false if not
    public boolean updateStudentMark(String studentID, String moduleCode, String markType, String mark, String semester, String attemptNumber) throws SQLException {
        //if (!moduleCode.isEmpty() & Integer.parseInt(mark) <= 100 & Integer.parseInt(mark) >= 0 & Integer.parseInt(attemptNumber) > 0 & Integer.parseInt(attemptNumber) < checkMaxAttempts(moduleCode)) {
        if (checkForStudentMark(studentID, moduleCode, semester, attemptNumber)) {
            String sqlQuery = "Update results Set " + markType +"_Mark = ? Where Student_ID = ? AND Module_Code = ? AND Semester = ? AND Attempt_Number = ?";
            PreparedStatement ps = myDB.prepareStatement(sqlQuery);
            ps.setString(1, mark);
            ps.setString(2, studentID);
            ps.setString(3, moduleCode);
            ps.setString(4, semester);
            ps.setString(5, attemptNumber);
            int rows = ps.executeUpdate();
            return rows > 0;
        } else {
            String sqlQuery = "Insert Into results (Student_ID,Module_Code," + markType + "_Mark,Semester,Attempt_Number) "
                    + "Values (?,?,?,?,?)";
            PreparedStatement ps = myDB.prepareStatement(sqlQuery);
            ps.setString(1, studentID);
            ps.setString(2, moduleCode);
            ps.setString(3, mark);
            ps.setString(4, semester);
            ps.setString(5, attemptNumber);
            int rows = ps.executeUpdate();
            return rows > 0;
        }

    }
}