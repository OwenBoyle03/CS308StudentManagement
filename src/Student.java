import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.sql.*;
//model for students
public class Student extends User {
    //constructor for student model
    public Student() {
    }
    static Connection myDB = DbConnect.getDbConnect(); //reference to DbConnect class for queries

    //Returns true if given student has a pass status decision and returns false if not
    public boolean hasDecision(String email) throws SQLException{
        String sqlQuery = "Select Decision FROM student WHERE Email = ?";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1,email);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString("Decision") != null;
        }
        return false;
    }

    //Returns the pass status decision of given student
    public String getDecision(String email) throws SQLException {
        //view decision of given student
        String sqlQuery = "SELECT Decision FROM student WHERE Email = ?";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1,email);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("Decision");

    }

    //Returns true if given student is enrolled on a course and false if not
    public boolean hasCourse(String email) throws SQLException{
        String sqlQuery = "SELECT course.Course_Name FROM student INNER JOIN course ON student.Course_Code = course.Course_Code WHERE student.Email = ?";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1,email);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return true;
        }
        else return false;
    }

    //Returns the course that a given student is enrolled on
    public String viewCourse(String email) throws SQLException{
        String sqlQuery = "SELECT course.Course_Name FROM student INNER JOIN course ON student.Course_Code = course.Course_Code WHERE student.Email = ?";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1,email);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("Course_Name");
    }

    //Returns true if a given student has any modules and false if not
    public boolean hasModules(String email) throws SQLException{
        String sqlQuery = "SELECT module.Name FROM (module INNER JOIN enrolment ON module.Module_Code = enrolment.Module_Code) INNER JOIN student ON enrolment.Student_ID = student.Student_ID  WHERE student.Email = ?";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1,email);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return true;
        }
        else{
            return false;
        }
    }

    //Returns a string of all modules a given student has, the grade of the student in each module, and the student's pass status for the module
    public String viewModules(String email) throws SQLException{
        //query for course where course id in course matches the course id in student for a given student
        String sqlQuery = "SELECT module.Module_Code, enrolment.Grade FROM (module INNER JOIN enrolment ON module.Module_Code = enrolment.Module_Code) INNER JOIN student ON enrolment.Student_ID = student.Student_ID  WHERE student.Email = ?";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1,email);
        ResultSet rs = ps.executeQuery();
        String names = "";
        while (rs.next()) {
            names = names.concat("\n" + rs.getString("module.Module_Code") + "   Grade: " + rs.getString("enrolment.Grade") + " " + passOrFail(rs.getString("enrolment.Grade")));
        }
        return names;
    }

    //Returns whether a given grade is a pass or a fail
    public String passOrFail(String grade){
        int gradeInt = Integer.parseInt(grade);
        return gradeInt >=50 ? "Pass" : "Fail";
    }

    //Returns any module materials for a given module and week
    public boolean checkModuleMaterialExists(String moduleCode, String week) throws SQLException {
        String sqlQuery = "Select * From moduleMaterial Where Module_Code = ? AND Week = ?";
        try (PreparedStatement ps = myDB.prepareStatement(sqlQuery)) {
            ps.setString(1,moduleCode);
            ps.setString(2,week);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    //Returns true if there is module material for a given module, week, and type of material and returns false if not
    public boolean checkModuleMaterialTypeExists(String moduleCode, String week, String type) throws SQLException {
        if (checkModuleMaterialExists(moduleCode,week)) {
            if (type.equals("Lab")) {
                String sqlQuery = "Select Lab_Note From moduleMaterial Where Module_Code = ? AND Week = ?";
                PreparedStatement ps = myDB.prepareStatement(sqlQuery);
                ps.setString(1, moduleCode);
                ps.setString(2, week);
                ResultSet rs = ps.executeQuery();
                rs.next();
                rs.getBlob("Lab_Note");
                return !(rs.wasNull());
            } else {
                String sqlQuery = "Select Lecture_Note From moduleMaterial Where Module_Code = ? AND Week = ?";
                PreparedStatement ps = myDB.prepareStatement(sqlQuery);
                ps.setString(1, moduleCode);
                ps.setString(2, week);
                ResultSet rs = ps.executeQuery();
                rs.next();
                rs.getBlob("Lecture_Note");
                return !(rs.wasNull());
            }
        }
        return false;
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

    //Writes given material to a file located at a given file path
    public boolean writeBlobToFile(Blob material, String filePath) throws SQLException {
        try {
            OutputStream output = new FileOutputStream(filePath);
            byte[] materialData = material.getBytes(1, (int) material.length());
            output.write(materialData);
            output.flush();
            output.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return true;
    }

    //Downloads lab materials for a given module and week to the user's downloads folder
    public boolean downloadLabMaterial(String moduleCode, String week) throws SQLException {
        String sqlQuery = "SELECT Lab_Note FROM moduleMaterial WHERE Module_Code = ? AND Week = ?";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1,moduleCode);
        ps.setString(2,week);
        ResultSet rs = ps.executeQuery();
        String downloadFolderPath = Paths.get(System.getProperty("user.home"),"Downloads").toString();
        String filePath = (downloadFolderPath + "/Week" + week + "LabMaterial.pdf");
        rs.next();
        Blob labNoteBlob = rs.getBlob("Lab_Note");
        return writeBlobToFile(labNoteBlob,filePath);
    }

    //Downloads lecture materials for a given module and week to the user's downloads folder
    public boolean downloadLectureMaterial(String moduleCode, String week) throws SQLException {
        String sqlQuery = "SELECT Lecture_Note FROM moduleMaterial WHERE Module_Code = ? AND Week = ?";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1,moduleCode);
        ps.setString(2,week);
        ResultSet rs = ps.executeQuery();
        String downloadFolderPath = Paths.get(System.getProperty("user.home"),"Downloads").toString();
        String filePath = (downloadFolderPath + "/Week" + week + "LectureMaterial.pdf");
        rs.next();
        Blob lectureNoteBlob = rs.getBlob("Lecture_Note");
        return writeBlobToFile(lectureNoteBlob,filePath);
    }

    //Returns a given module if a given student is enrolled in it
    public boolean takesModule(String email, String moduleCode) throws SQLException{
        String sqlQuery = "SELECT module.Name FROM (module INNER JOIN enrolment ON module.Module_Code = enrolment.Module_Code) "
                        + "INNER JOIN student ON enrolment.Student_ID = student.Student_ID  WHERE student.Email = ? AND enrolment.Module_Code = ?";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1,email);
        ps.setString(2,moduleCode);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    //Returns the number of semesters a given module runs for
    public int checkModuleSemesters(String moduleCode) throws SQLException{
        if (checkModuleExists(moduleCode)) {
            String sqlQuery = "Select Semesters From module Where Module_Code = ?";
            PreparedStatement ps = myDB.prepareStatement(sqlQuery);
            ps.setString(1,moduleCode);

            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("Semesters");
        }
        return 0;
    }
}