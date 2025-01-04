import javax.swing.*;
import java.awt.*;

public class ViewLogin {
    private JFrame loginFrame = new JFrame();
    private Label labelEmail = new Label("Email: ");
    private Label labelPassword = new Label("Password: ");
    private Label labelUserType = new Label("User Type: ");
    private Label title = new Label("Strathclyde Management System");
    private JTextField textEmail = new JTextField(); //Text field that will contain the email for logging in
    private JPasswordField textPassword = new JPasswordField(); //Text field that contains the password
    private String[] userTypes  = {"Student", "Lecturer", "Manager"}; //Array for setting up drop down box
    private JComboBox comboUserType = new JComboBox(userTypes); //Drop down box containing the types of users
    private JButton pwReset = new JButton("Reset Password"); //Button that calls reset password function
    private JButton login = new JButton("Login"); //Button that checks for login
    private JButton signup = new JButton("Signup"); //Button that swaps to signup screen
    private JButton Exit = new JButton("Exit"); //Button that exits program

    //Function that needs to be called on startup to set up the login screen
    public void setupLogin() {
        loginFrame.setDefaultCloseOperation(loginFrame.EXIT_ON_CLOSE);
        title.setBounds(75, 5, 350, 25);
        title.setFont(new Font("Calibri", Font.BOLD,20));
        loginFrame.add(title);
        labelEmail.setBounds(100, 50, 60, 20);
        loginFrame.add(labelEmail);
        labelPassword.setBounds(100, 70, 60, 20);
        loginFrame.add(labelPassword);
        labelUserType.setBounds(100, 90, 60, 20);
        loginFrame.add(labelUserType);
        textEmail.setBounds(160, 50, 120, 20);
        loginFrame.add(textEmail);
        textPassword.setBounds(160, 70, 120, 20);
        loginFrame.add(textPassword);
        comboUserType.setBounds(160, 90, 120, 20);
        loginFrame.add(comboUserType);
        pwReset.setBounds(280, 70, 126, 20);
        loginFrame.add(pwReset);
        login.setBounds(100, 110, 80, 20);
        loginFrame.add(login);
        signup.setBounds(180, 110, 80, 20);
        loginFrame.add(signup);
        Exit.setBounds(260, 110, 80, 20);
        loginFrame.add(Exit);
        loginFrame.setSize(500, 200);
        loginFrame.setLayout(null);
        loginFrame.setVisible(true);
    }

    //Getters and Setters
    public void showLogin(){
        loginFrame.setVisible(true);}
    public void hideLogin(){
        loginFrame.setVisible(false);}
    public String getLoginEmail(){
        return textEmail.getText();}
    public String getLoginPassword(){
        return textPassword.getText();}
    public String getLoginType(){
        return userTypes[comboUserType.getSelectedIndex()];}
    public int getLoginIndex(){
        return comboUserType.getSelectedIndex();
    }
    public JFrame getLoginFrame(){
        return loginFrame;}
    public JButton getLoginBtn(){
        return login;}
    public JButton getSignupBtn(){
        return signup;}
    public JButton getPWReset(){
        return pwReset;}
    public JButton getExit(){
        return Exit;}
    public void clearLoginText(){
        textEmail.setText("");
        textPassword.setText("");
        textEmail.grabFocus();}
}
