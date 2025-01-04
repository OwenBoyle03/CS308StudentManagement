import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ViewSignup {
    private JFrame signupFrame = new JFrame();
    private Label emailLabel = new Label("Please enter your email");
    private Label pwLabel = new Label("Please enter your password");
    private Label pwConfirmLabel = new Label("Please confirm your password");
    private Label fnLabel = new Label("Please enter your first name");
    private Label surLabel = new Label("Please enter your surname");
    private Label genLabel = new Label("Please select your gender");
    private Label dobLabel = new Label("Please enter your DOB");
    private Label typeLabel = new Label ("Please select your account type");
    private Label qualLabel = new Label ("Please select your qualification");
    private JTextField emailText = new JTextField(); //Text field that takes in the signup email
    private JPasswordField pwText = new JPasswordField(); //Text field that takes in the signup password
    private JPasswordField pwTextConfirm = new JPasswordField(); //Text field that for confirming signup password
    private JTextField fnText = new JTextField(); //Text field that takes in the signup first name
    private JTextField surText = new JTextField(); //Text field that takes in the signup surname
    private String[] genders = {"Male", "Female", "Other"}; //Array for setting up genCombo
    private String[] types = {"Student", "Lecturer"}; //Array for sett  ing up typeCombo
    private String[] qualification = {"N/A", "PhD", "MSc", "BSc"};  //Array for picking qualification if lecturer
    private JComboBox genCombo = new JComboBox(genders); //Drop down box for selecting gender
    private JComboBox typeCombo = new JComboBox(types); //Drop down box for selecting user type
    private JComboBox qualCombo = new JComboBox(qualification); //Drop down box for selecting qualification as lecturer
    private JSpinner daySpinner = new JSpinner();
    private JComboBox monthCombo = new JComboBox();
    private JComboBox yearCombo = new JComboBox();
    private JButton buttonEnter = new JButton("Enter"); //Button to submit signup details
    private JButton buttonBack = new JButton("Back"); //Button to go back to log in screen
    private JButton button1;

    //Setup function for signup screen, must be called on project startup
    public void setupSignup(){
        signupFrame.setSize(300, 600);
        signupFrame.setLayout(null);
        signupFrame.setVisible(false);
        signupFrame.setDefaultCloseOperation(signupFrame.EXIT_ON_CLOSE);
        emailLabel.setBounds(25,10,200,20);
        emailText.setBounds(25,35,200,20);
        pwLabel.setBounds(25,60,200,20);
        pwText.setBounds(25,85,200,20);
        pwConfirmLabel.setBounds(25,110,200,20);
        pwTextConfirm.setBounds(25,135,200,20);
        fnLabel.setBounds(25,160,200,20);
        fnText.setBounds(25,185,200,20);
        surLabel.setBounds(25,210,200,20);
        surText.setBounds(25,235,200,20);
        genLabel.setBounds(25,260,200,20);
        genCombo.setBounds(25,285,200,20);
        typeLabel.setBounds(25,310,200,20);
        typeCombo.setBounds(25,335,200,20);
        dobLabel.setBounds(25,360,200,20);
        daySpinner.setBounds(25,385,50,20);
        monthCombo.setBounds(75,385,100,20);
        yearCombo.setBounds(175,385,100,20);
        qualLabel.setBounds(25,410,200,20);
        qualLabel.setVisible(false);
        qualCombo.setBounds(25,435,200,20);
        qualCombo.setVisible(false);
        buttonEnter.setBounds(25,480,90,20);
        buttonBack.setBounds(135, 480, 90,20);
        signupFrame.add(emailLabel);
        signupFrame.add(emailText);
        signupFrame.add(pwLabel);
        signupFrame.add(pwConfirmLabel);
        signupFrame.add(pwText);
        signupFrame.add(pwTextConfirm);
        signupFrame.add(fnLabel);
        signupFrame.add(fnText);
        signupFrame.add(surLabel);
        signupFrame.add(surText);
        signupFrame.add(genLabel);
        signupFrame.add(genCombo);
        signupFrame.add(typeLabel);
        signupFrame.add(typeCombo);
        signupFrame.add(dobLabel);
        signupFrame.add(daySpinner);
        signupFrame.add(monthCombo);
        signupFrame.add(yearCombo);
        signupFrame.add(qualLabel);
        signupFrame.add(qualCombo);
        signupFrame.add(buttonEnter);
        signupFrame.add(buttonBack);
        for(int i = 2023; i>= 2023-100; i--){
            yearCombo.addItem(i);
        }
        for(int i = 1; i<13; i++){
            monthCombo.addItem(i);
        }
        updateDays();
    }
    //this calculate the amount of days in the selected month and year in the other boxes, and updates the days to the correct number so invalid dates can't be selected
    public void updateDays(){
        int month = monthCombo.getSelectedIndex()+1;
        int year = yearCombo.getSelectedIndex()+ 1;
        int daysForMonth = LocalDate.of(year,month,1).lengthOfMonth();
        ArrayList<Integer> days = new ArrayList<>();
        for(int i = 1; i <= daysForMonth; i++){
            days.add(i);
        }
        SpinnerListModel model = new SpinnerListModel(days);
        daySpinner.setModel(model);
    }

    //Getters and Setters and Clearer
    public JComboBox getMonthCombo(){
        return monthCombo;
    }
    public JComboBox getYearCombo(){
        return yearCombo;
    }
    public int getMonth(){
        return monthCombo.getSelectedIndex();
    }
    public int getDay(){
        return (int) daySpinner.getValue();
    }
    public int getYear(){
        return 2023 - yearCombo.getSelectedIndex();
    }
    public void showSignup(){
        signupFrame.setVisible(true);}
    public JFrame getSignupFrame(){
        return signupFrame;
    }
    public void hideSignup(){
        signupFrame.setVisible(false);}
    public String getSignupEmail(){
        return emailText.getText();}
    public String getSignupPW(){
        return pwText.getText();}
    public String getSignupPWConfirm(){
        return pwTextConfirm.getText();
    }
    public String getSignupFN(){
        return fnText.getText();}
    public String getSignupSur(){
        return surText.getText();}
    public String getSignupGender(){
        return genders[genCombo.getSelectedIndex()];}
    public String getSignupType(){
        return types[typeCombo.getSelectedIndex()];}
    public String getSignupQual(){
        return qualification[qualCombo.getSelectedIndex()];
    }
    public JComboBox getTypeCombo(){
        return typeCombo;
    }
    public JButton getSignupEnter(){
        return buttonEnter;}
    public JButton getSignupBack(){
        return buttonBack;}
    public void showQualSections(){
        qualLabel.setVisible(true);
        qualCombo.setVisible(true);
        qualCombo.setSelectedIndex(0);
    }
    public void hideQualSections(){
        qualLabel.setVisible(false);
        qualCombo.setVisible(false);
    }
    public void clearSignup(){
        emailText.setText("");
        pwText.setText("");
        pwTextConfirm.setText("");
        fnText.setText("");
        surText.setText("");
        genCombo.setSelectedIndex(0);
        typeCombo.setSelectedIndex(0);
        qualCombo.setSelectedIndex(0);
        emailText.grabFocus();
    }
}
