import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
//Parent class for all the models, has all functionality that can be shared among all models
public class User {
    //constructor for user model
    public User(){
    }
    static Connection myDB = DbConnect.getDbConnect(); //reference to DbConnect class for queries

    //Returns true if a given user exists and false if not
    public boolean checkExists(String email) throws SQLException {
        String sqlQuery = "SELECT * FROM student,lecturer,manager WHERE student.Email=? OR lecturer.Email=? OR manager.Email=?";
        try (PreparedStatement ps = myDB.prepareStatement(sqlQuery)) {
            ps.setString(1, email);
            ps.setString(2,email);
            ps.setString(3,email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();  // Returns true if the result set has at least one row, false otherwise
            }
        }
    }

    //Returns true if a given user exists and matches a given account type and false if not
    public boolean checkExistsAndType(String email, String accountType) throws SQLException{
        String sqlQuery = "SELECT * FROM " + accountType.toLowerCase() + " WHERE Email = ?";
        try(PreparedStatement ps = myDB.prepareStatement(sqlQuery)){
            ps.setString(1 ,email);
            try (ResultSet rs = ps.executeQuery()){
                return rs.next();
            }
        }
    }

    //Returns true if a given account is activated and false if it is unactivated or doesn't exist
    public boolean checkActivated(String email, String typeAccount) throws SQLException{
    if(checkExists(email)){
        String sqlQuery = "SELECT Activated FROM " + typeAccount.toLowerCase() + " WHERE Email = ?";
            PreparedStatement ps = myDB.prepareStatement(sqlQuery);
            ps.setString(1,email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int activatedValue = rs.getInt("activated"); //activated is stored as an int where 1 is activated and 0 is false;
                return activatedValue == 1;
                }
    }
    return false;
    }

    //Returns true if a given user successfully logs into their account
    public boolean login(String email, String password, String typeAccount) throws SQLException { //method for logging in, takes type of account from GUI and checks that matches details
            String sqlQuery = "SELECT * From " + typeAccount.toLowerCase() + " WHERE Email=? and Password=?;";
            PreparedStatement ps = myDB.prepareStatement(sqlQuery);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        return false;
    }

    //Returns true if a given user has an unanswered password reset request and false if they don't
    public boolean checkPwResetStatus(String email, String typeAccount) throws SQLException{
        String sqlQuery = "SELECT * From " + typeAccount.toLowerCase() + " WHERE EMAIL =?";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1,email);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            int resetValue = rs.getInt("Password_Reset_Request");
            return resetValue == 1;
        }
        return false;
    }

    //Returns true if a new user is created with the given details and false if not
    public boolean signUp(String email,String password,String firstName, String lastName, String gender, String typeAccount,Date DOB) throws SQLException{  //allow users to sign up, check if the account already exists first and return false if they do
        if(email.length() == 0 || password.length() == 0 || firstName.length() == 0 || lastName.length() == 0 || gender.length() == 0 || typeAccount.length() == 0){
            return false;
        }
        String sqlQuery = "INSERT INTO " + typeAccount.toLowerCase() +
                " (Email, Password, First_Name, Surname, Gender,DOB) " +
                "VALUES (?, ?, ?, ?, ?,?)";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1,email);
        ps.setString(2,password);
        ps.setString(3,firstName);
        ps.setString(4,lastName);
        ps.setString(5,gender);
        ps.setDate(6,DOB);

        int rows = ps.executeUpdate();
        return rows > 0;    //use this to check if successful
    }

    //Returns true if a password reset request is successfully sent for a given user
    public boolean requestResetPassword(String email, String typeAccount) throws SQLException {
        String sqlQuery = "UPDATE " + typeAccount.toLowerCase() + " SET Password_Reset_Request = 1 WHERE Email = ?";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1,email);
        int rows = ps.executeUpdate();
        return rows > 0;
    }

    //Returns the password of a given user
    public String getPassword(String email, String typeAccount) throws SQLException{
        String sqlQuery = "SELECT Password from " + typeAccount.toLowerCase() +" WHERE Email = ?";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1,email);
        ResultSet rs = ps.executeQuery();
        String password = null;
        if(rs.next()){
            password = rs.getString("Password");
            return password;
        }
        return null;
    }

    //Returns true if the password of a given user is replaced with a new provided password and false if not
    public boolean updatePassword(String email, String typeAccount,String passwordNew) throws SQLException{
        String sqlQuery = "UPDATE " + typeAccount.toLowerCase() + " SET Password = ? WHERE Email = ?";
        PreparedStatement ps = myDB.prepareStatement(sqlQuery);
        ps.setString(1,passwordNew);
        ps.setString(2,email);
        int rows = ps.executeUpdate();
        return rows > 0;
    }
}
