import javax.swing.*;
import java.awt.*;
public class ViewStudent {
    private JFrame studentFrame = new JFrame();
    private JFrame downloadFrame = new JFrame();
    private Label intro = new Label("Hello Student");
    private Label dlModuleLabel = new Label("Please enter the module code you wish to select");
    private Label dlWeekLabel = new Label("Please enter the week number you wish to download");
    private Label dlTypeLabel = new Label("Please select the type of material: ");
    private JButton buttonViewModules = new JButton("View Modules"); //Button to swap to create a pop window and view modules
    private JButton buttonViewCourse = new JButton("View Courses"); //Button to swap to view student's courses
    private JButton buttonPassFail = new JButton("View Pass Status"); //Button to view their pass status for the year
    private JButton buttonDownloadMat = new JButton("Download Material"); //Button to swap to download screen
    private JButton buttonChangePassword = new JButton("Update Password"); // Button to update user's password
    private JButton buttonLogout = new JButton("Logout"); //Button to go back to log in screen
    private JButton buttonEnter = new JButton("Enter"); //Button to submit download details
    private JButton buttonBack = new JButton("Back"); //Button to back from download screen to student screen
    private JTextField dlModuleCode = new JTextField(); //Text field storing the selected module code to download from
    private JTextField dlWeekNum = new JTextField(); //Text field containing the selected week number to download
    private String[] noteChoice = {"Lab", "Lecture"}; //Array for setting up noteType
    private JComboBox noteType = new JComboBox<>(noteChoice); //Drop down for containing the types of notes

    //Set up method for the student home screen, must be called on program start up
    public void setupStudent(){
        intro.setBounds(60, 30, 150, 15);
        intro.setFont(new Font("Calibri", Font.BOLD,16));
        studentFrame.add(intro);
        studentFrame.setDefaultCloseOperation(studentFrame.EXIT_ON_CLOSE);
        buttonViewModules.setBounds(50, 50, 140,20);
        buttonViewCourse.setBounds(50, 75, 140,20);
        buttonPassFail.setBounds(50, 100, 140,20);
        buttonDownloadMat.setBounds(50,125,140,20);
        buttonLogout.setBounds(50, 175, 140,20);
        buttonChangePassword.setBounds(50,150,140,20);
        studentFrame.add(buttonViewModules);
        studentFrame.add(buttonViewCourse);
        studentFrame.add(buttonPassFail);
        studentFrame.add(buttonDownloadMat);
        studentFrame.add(buttonLogout);
        studentFrame.add(buttonChangePassword);
        studentFrame.setSize(250, 250);
        studentFrame.setLayout(null);
        studentFrame.setVisible(false);
        setupDownload();
    }

    //Set up method for the download module material screen, must be called on program start up
    public void setupDownload(){
        dlModuleLabel.setBounds(20,30,300,20);
        dlModuleCode.setBounds(20,55,300,20);
        dlWeekLabel.setBounds(20, 80, 300,20);
        dlTypeLabel.setBounds(20,130,300,20);
        dlWeekNum.setBounds(20,105,300,20);
        noteType.setBounds(20,160,300,20);
        buttonEnter.setBounds(20,190,140,20);
        buttonBack.setBounds(180,190,140,20);
        downloadFrame.add(dlWeekLabel);
        downloadFrame.add(dlTypeLabel);
        downloadFrame.add(dlModuleCode);
        downloadFrame.add(dlModuleLabel);
        downloadFrame.add(dlWeekNum);
        downloadFrame.add(noteType);
        downloadFrame.add(buttonEnter);
        downloadFrame.add(buttonBack);
        downloadFrame.setDefaultCloseOperation(downloadFrame.EXIT_ON_CLOSE);
        downloadFrame.setSize(500,300);
        downloadFrame.setLayout(null);
        downloadFrame.setVisible(false);}


    public void showStudent(){
        studentFrame.setVisible(true);}
    public void hideStudent(){
        studentFrame.setVisible(false);}
    public JFrame getStudentFrame(){
        return studentFrame;
    }
    public JFrame getDownloadFrame(){return downloadFrame;}
    public void showDownload(){
        downloadFrame.setVisible(true);}
    public void hideDownload(){
        downloadFrame.setVisible(false);}
    public JButton getButtonViewModules(){
        return buttonViewModules;}
    public JButton getButtonViewCourse(){
        return buttonViewCourse;}
    public JButton getButtonPassFail(){
        return buttonPassFail;}
    public JButton getButtonLogout(){
        return buttonLogout;}
    public JButton getButtonEnter(){
        return buttonEnter;}
    public JButton getButtonBack(){
        return buttonBack;}
    public JButton getButtonDownloadMat(){
        return buttonDownloadMat;}
    public String getDLModuleCode(){
        return dlModuleCode.getText();}
    public String getDLWeekNum(){
        return dlWeekNum.getText();}
    public String getNoteType(){
        return noteChoice[noteType.getSelectedIndex()];}
    public JButton getUpdatePassButton(){
        return buttonChangePassword;
    }
    public void clearDownloadText(){
        dlModuleCode.setText("");
        dlWeekNum.setText("");
        noteType.setSelectedIndex(0);
        dlModuleCode.grabFocus();
    }
}

