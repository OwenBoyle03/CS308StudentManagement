import java.sql.SQLException;
public class Main {
    public static void main(String[] args) throws SQLException {
        //initialise all of the models and required GUI views, initialise the controller with these models and views as parameters, then initialise the controller
        User use = new User();
        Student stu = new Student();
        Lecturer lec = new Lecturer();
        Manager man = new Manager();
        ViewLecturer vlec = new ViewLecturer();
        ViewLogin vlog = new ViewLogin();
        ViewManager vman = new ViewManager();
        ViewSignup vsign = new ViewSignup();
        ViewStudent vstud = new ViewStudent();
        ViewUpdatePassword vuppass = new ViewUpdatePassword();

        Controller c = new Controller(use, stu, lec, man, vlec, vlog, vman, vsign, vstud, vuppass);
        c.initialiseController();
    }
}