import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;

public class Controller {
    private User use;
    private Student sm;
    private Lecturer lm;
    private Manager m;
    private String currentUser;
    private String currentAccountType;
    private ViewLecturer vlec;
    private ViewLogin vlog;
    private ViewManager vman;
    private ViewSignup vsign;
    private ViewStudent vstud;
    private ViewUpdatePassword vuppass;
    private File labFile;
    private File lectureFile;

    public Controller(User use, Student sm, Lecturer lm, Manager m, ViewLecturer vlec, ViewLogin vlog, ViewManager vman, ViewSignup vsign, ViewStudent vstud,ViewUpdatePassword vuppass) {
        this.use = use;
        this.sm = sm;
        this.lm = lm;
        this.m = m;
        this.vlec = vlec;
        this.vlog = vlog;
        this.vman = vman;
        this.vsign = vsign;
        this.vstud = vstud;
        this.vuppass = vuppass;
        this.currentUser = ""; //this is a bit dodgy, not sure if we can find another work around
        this.currentAccountType = "";
    }

    public void initialiseController() throws SQLException { ///add set up Manager once complete
        vlec.setupLecturer();
        vlog.setupLogin();
        vsign.setupSignup();
        vstud.setupStudent();
        vman.setupManager();
        vuppass.setupUpdatePassword();

        //functionality of swapping to sign up screen
        vlog.getSignupBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vsign.showSignup();
                vlog.clearLoginText();
                vlog.hideLogin();
            }
        });

        //functionality of closing when pressing exit button
        vlog.getExit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //added PWReset, prompts them to enter an email if text left blank, tells user if account not found, tells them if the reset is sent successfully and if it fails
        vlog.getPWReset().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PWReset();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //functionality of login
        //checks if they've entered into both boxes, then checks if the user exists, then checks if credentials are correct
        //if correct credentials but not activated sends message, if everything ok navigates to the correct view
        vlog.getLoginBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    login();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        //these update the days in the dob based on what month and year is selected
        vsign.getMonthCombo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vsign.updateDays();
            }
        });
        //same as above
        vsign.getYearCombo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vsign.updateDays();
            }
        });
        //this hides the qualification section of signup if not lecturer selected, and makes it visible if it is
        vsign.getTypeCombo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(vsign.getSignupType().equals("Lecturer")){
                    vsign.showQualSections();
                }
                else{
                    vsign.hideQualSections();
                }
            }
        });
        //allows the user to go back to the login screen by pressing back
        vsign.getSignupBack().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vsign.clearSignup();
                vsign.hideSignup();
                vlog.showLogin();
            }
        });
        //calls the signup function when enter is pressed on signup page
        vsign.getSignupEnter().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    signUp();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //Navigations for the update password screen based on user type
        vuppass.getUpdateBack().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(currentAccountType.equals("Student")){
                    vuppass.hideUpdatePassword();
                    vuppass.clearUpdatePassText();
                    vstud.showStudent();
                }
                else if(currentAccountType.equals("Lecturer")){
                    vuppass.hideUpdatePassword();
                    vuppass.clearUpdatePassText();
                    vlec.showLecturer();
                }
                else if(currentAccountType.equals("Manager")){
                    vuppass.hideUpdatePassword();
                    vuppass.clearUpdatePassText();
                    vman.showManager();
                }
            }
        });
        //deals with when the user presses enter on the update password screen
        vuppass.getPasswordEnter().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String oldPass = vuppass.getOldPassword();
                String newPass = vuppass.getNewPassword();
                String newPassConfirm = vuppass.getNewPassConfirm();
                String currentOldPass;
                try {
                    currentOldPass = use.getPassword(currentUser, currentAccountType);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                if (vuppass.getOldPassword().length() == 0 || vuppass.getNewPassword().length() == 0 || vuppass.getNewPassConfirm().length() == 0) {
                    JOptionPane.showMessageDialog(vuppass.getUpdatePasswordFrame(), "Please fill out all of the fields.");
                }
                else if (!oldPass.equals(currentOldPass)) {
                    JOptionPane.showMessageDialog(vuppass.getUpdatePasswordFrame(), "You have not provided your current password correctly.");
                } else if (!newPass.equals(newPassConfirm)) {
                    JOptionPane.showMessageDialog(vuppass.getUpdatePasswordFrame(), "Passwords do not match, please check and try again.");
                } else if(newPass.equals(currentOldPass)){
                    JOptionPane.showMessageDialog(vuppass.getUpdatePasswordFrame(),"Your new password can not be equal to your current password.");
                }
                else {
                    try {
                        if (use.updatePassword(currentUser, currentAccountType, newPass)) {
                            JOptionPane.showMessageDialog(vuppass.getUpdatePasswordFrame(), "Password updated successfully");
                            vuppass.clearUpdatePassText();
                        } else {
                            JOptionPane.showMessageDialog(vuppass.getUpdatePasswordFrame(), "Error updating password.");
                            vuppass.clearUpdatePassText();
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        //STUDENT ACTION LISTENERS
        //This makes the logout out button takes the user from student home screen to log in screen
        vstud.getUpdatePassButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vstud.hideStudent();
                vuppass.showUpdatePassword();
            }
        });
        //takes a student back to the login screen once they click logout
        vstud.getButtonLogout().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vstud.hideStudent();
                vlog.showLogin();
                vlog.clearLoginText();
            }
        });
        //When view course button is pressed, make a popup window displaying the student's course
        vstud.getButtonViewCourse().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(sm.hasCourse(currentUser)){
                        JOptionPane.showMessageDialog(vstud.getStudentFrame(), "Your assigned course is " + sm.viewCourse(currentUser));
                    }
                    else{
                        JOptionPane.showMessageDialog(vstud.getStudentFrame(), "You currently have no course assigned");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        //When the button is pressed, make a popup window displaying the student's modules
        vstud.getButtonViewModules().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(sm.hasModules(currentUser)){
                        JOptionPane.showMessageDialog(vstud.getStudentFrame(), "Your modules are : " + sm.viewModules(currentUser));
                    }else{
                        JOptionPane.showMessageDialog(vstud.getStudentFrame(), "You do not currently have any modules assigned.");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        //When the button is pressed, make a popup window displaying the student's pass/fail/resit status
        vstud.getButtonPassFail().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(sm.hasDecision(currentUser)){
                    JOptionPane.showMessageDialog(vstud.getStudentFrame(), "Your current pass status for the year is :" + sm.getDecision(currentUser));
                } else{
                        JOptionPane.showMessageDialog(vstud.getStudentFrame(), "You do not currently have a pass status for the year.");
                    }
                }catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //When pressed the screen changes to the download material screen, clears any previously typed in text
        vstud.getButtonDownloadMat().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vstud.showDownload();
                vstud.hideStudent();
                vstud.clearDownloadText();
            }
        });
        //When pressed the screen changes back to the student home screen
        vstud.getButtonBack().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vstud.hideDownload();
                vstud.showStudent();
            }
        });
        //This will call the download material function
        vstud.getButtonEnter().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String downloadModuleCode = vstud.getDLModuleCode().toLowerCase();
                String downloadWeekNumber = vstud.getDLWeekNum();
                String downloadNoteType = vstud.getNoteType();

                if (!checkIsInt(downloadWeekNumber)) {
                    downloadWeekNumber = "0";
                }

                try {
                    if (downloadModuleCode.isEmpty() && downloadWeekNumber.isEmpty()) {
                        JOptionPane.showMessageDialog(vstud.getDownloadFrame(), "Please enter a module code and week number to download from.");
                    } else if (downloadModuleCode.isEmpty()) {
                        JOptionPane.showMessageDialog(vstud.getDownloadFrame(), "Please enter a module code to download from.");
                    } else if (downloadWeekNumber.isEmpty()) {
                        JOptionPane.showMessageDialog(vstud.getDownloadFrame(), "Please enter a week number to download from.");
                    } else if ((sm.checkModuleSemesters(downloadModuleCode) == 1 && (Integer.parseInt(downloadWeekNumber) < 1 || Integer.parseInt(downloadWeekNumber) > 11)) || (sm.checkModuleSemesters(downloadModuleCode) == 2 && (Integer.parseInt(downloadWeekNumber) < 1 || Integer.parseInt(downloadWeekNumber) > 22))) {
                        JOptionPane.showMessageDialog(vlec.getModMatFrame(), "Please enter a valid week for this module i.e. no more than week 11 for a one module class.");
                    } else if (!sm.checkModuleExists(downloadModuleCode)) {
                        JOptionPane.showMessageDialog(vstud.getDownloadFrame(), "Sorry but that module does not exist.");
                    } else if (!sm.takesModule(currentUser,downloadModuleCode)) {
                        JOptionPane.showMessageDialog(vstud.getDownloadFrame(), "Sorry but you don't appear to take this module.");
                    } else if (!sm.checkModuleMaterialTypeExists(downloadModuleCode, downloadWeekNumber, downloadNoteType)) {
                        JOptionPane.showMessageDialog(vstud.getDownloadFrame(), "Sorry but that module does not have that type of material for this week.");
                    } else if (downloadNoteType.equals("Lab")) {
                        if (sm.downloadLabMaterial(downloadModuleCode, downloadWeekNumber)) {
                            JOptionPane.showMessageDialog(vstud.getDownloadFrame(), "Lab material downloaded.");
                        } else {
                            JOptionPane.showMessageDialog(vstud.getDownloadFrame(), "Lab material download failed.");
                        }
                    } else {
                        if (sm.downloadLectureMaterial(downloadModuleCode, downloadWeekNumber)) {
                            JOptionPane.showMessageDialog(vstud.getDownloadFrame(), "Lecture material downloaded.");
                        } else {
                            JOptionPane.showMessageDialog(vstud.getDownloadFrame(), "Lecture material download failed.");
                        }
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //LECTURER ACTION LISTENERS
        //takes the lecturer to the update module info screen
        vlec.getButtonUpdateInfo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vlec.hideLecturer();
                vlec.showUpdateModInfo();
            }
        });
        //takes the lecturer back to main screen from update module screen
        vlec.getModInfoBack().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vlec.hideUpdateModInfo();
                vlec.clearModInfo();
                vlec.showLecturer();
            }
        });
        //deals with updating module information for a lecturer when they press enter
        vlec.getModInfoEnter().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String moduleName = vlec.getModuleName();
                String moduleInfo = vlec.getModuleInfo();
                try {
                    if(!lm.isLecturerForModule(currentUser,moduleName)){
                        JOptionPane.showMessageDialog(vlec.getUpdateModuleFrame(),"You are not the lecturer for this module");
                    }
                    else if(moduleName.length() == 0 || moduleInfo.length() == 0){
                        JOptionPane.showMessageDialog(vlec.getUpdateModuleFrame(),"Please fill out all the fields");
                    }
                    else if(!lm.checkModuleExists(moduleName)){
                        JOptionPane.showMessageDialog(vlec.getUpdateModuleFrame(),"This module does not exist");
                    }
                    else if(lm.updateInfo(moduleName,moduleInfo)){
                        JOptionPane.showMessageDialog(vlec.getUpdateModuleFrame(),"Module info updated successfully");
                    }
                    else{
                        JOptionPane.showMessageDialog(vlec.getUpdateModuleFrame(),"Error updating information, please try again later");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        //takes the lecturer to the update password screen
        vlec.getButtonUpdatePassword().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vlec.hideLecturer();
                vuppass.showUpdatePassword();
            }
        });
        //takes the lecturer to the module material screen
        vlec.getButtonModMat().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vlec.hideLecturer();
                vlec.showModMat();
                vlec.clearModMat();
            }
        });
        //tales the lecturer to the view enrolled students screen
        vlec.getButtonViewEnrol().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vlec.switchToEnrolled();
                vlec.hideLecturer();
                vlec.clearLecOther();
            }
        });
        vlec.getButtonUpdateDecision().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vlec.getComboStudents().removeAllItems();
                try {
                    ArrayList<String> modules = lm.listOfLecturersModules(currentUser);
                    for(int i = 0; i < modules.size(); i++){
                        vlec.getComboModule().addItem(modules.get(i));
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                vlec.showModDec();
                vlec.hideLecturer();
                vlec.clearModDec();
            }
        });
        vlec.getButtonLogout().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vlec.hideLecturer();
                vlog.showLogin();
            }
        });

        vlec.getButtonAddLabMaterial().addActionListener(new ActionListener() {
            // Make a file chooser
            final JFileChooser fc = new JFileChooser();
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(vlec.getButtonAddLabMaterial());

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    labFile = fc.getSelectedFile();
                }
            }
        });
        vlec.getButtonAddLectureMaterial().addActionListener(new ActionListener() {
            // Make a file chooser
            final JFileChooser fc = new JFileChooser();
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(vlec.getButtonAddLectureMaterial());

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    lectureFile = fc.getSelectedFile();
                }
            }
        });
        vlec.getButtonSubmit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String module = vlec.getTextMod().toLowerCase();
                String week = vlec.getTextWeek();

                if (!checkIsInt(week)) {
                    week = "0";
                }

                byte[] encodedLabFileBytes;
                if (labFile != null) {
                    try {
                        FileInputStream input = new FileInputStream(labFile);
                        encodedLabFileBytes = new byte[(int) labFile.length()];
                        input.read(encodedLabFileBytes);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    encodedLabFileBytes = null;
                }

                byte[] encodedLectureFileBytes;
                if (lectureFile != null) {
                    try {
                        FileInputStream input = new FileInputStream(lectureFile);
                        encodedLectureFileBytes = new byte[(int) lectureFile.length()];
                        input.read(encodedLectureFileBytes);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    encodedLectureFileBytes = null;
                }

                try {
                    if (module.isEmpty() && week.isEmpty() && (encodedLectureFileBytes == null && encodedLabFileBytes == null)) {
                        JOptionPane.showMessageDialog(vlec.getModMatFrame(), "Please fill in the module and week that you wish to upload to as well as a file.");
                    } else if (module.isEmpty() && week.isEmpty()) {
                        JOptionPane.showMessageDialog(vlec.getModMatFrame(), "Please fill in the module and week that you wish to upload to.");
                    } else if (module.isEmpty()) {
                        JOptionPane.showMessageDialog(vlec.getModMatFrame(), "Please fill in the module that you would like to upload to.");
                    } else if (!(lm.checkModuleExists(module))) {
                        JOptionPane.showMessageDialog(vlec.getModMatFrame(), "Sorry but that module doesn't exist.");
                    } else if (!(lm.isLecturerForModule(currentUser, module))) {
                        JOptionPane.showMessageDialog(vlec.getModMatFrame(), "Sorry but you don't have access to that module.");
                    } else if (week.isEmpty()) {
                        JOptionPane.showMessageDialog(vlec.getModMatFrame(), "Please fill in the week that you would like to upload to.");
                    } else if ((lm.checkModuleSemesters(module) == 1 && (Integer.parseInt(week) < 1 || Integer.parseInt(week) > 11)) || (lm.checkModuleSemesters(module) == 2 && (Integer.parseInt(week) < 1 || Integer.parseInt(week) > 22))) {
                        JOptionPane.showMessageDialog(vlec.getModMatFrame(), "Please enter a valid week for this module i.e. no more than week 11 for a one module class.");
                    } else if (encodedLectureFileBytes == null && encodedLabFileBytes == null) {
                        JOptionPane.showMessageDialog(vlec.getModMatFrame(), "Please input at least one file to be uploaded.");
                    } else {
                        if (encodedLabFileBytes != null) {
                            lm.updateLabMaterial(module, week, encodedLabFileBytes);
                        }

                        if (encodedLectureFileBytes != null) {
                            lm.updateLectureMaterial(module, week, encodedLectureFileBytes);
                        }

                        JOptionPane.showMessageDialog(vlec.getModMatFrame(), "The upload was successful.");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        vlec.getButtonBack().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vlec.hideModMat();
                vlec.showLecturer();
            }
        });
        vlec.getButtonSubmitDec().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String modCode = vlec.getTextModule(); //Module code
                String studentID = vlec.getTextStu(); //Student who is getting the decision
                String mark = vlec.getTextMark(); //the mark
                String markType = vlec.getMarkType(); //Lab or Lecture
                String sem = vlec.getSemester();
                String attempt = vlec.getAttemptNum();

                if (!checkIsInt(mark)) {
                    mark = "1000";
                }
                if (!checkIsInt(attempt)){
                    attempt = "0";
                }

                try{
                if(modCode.isEmpty()){
                    JOptionPane.showMessageDialog(vlec.getModDecFrame(),"Please fill out all details");
                }
                else if(Integer.parseInt(mark) < 0 || Integer.parseInt(mark) > 100){
                    JOptionPane.showMessageDialog(vlec.getModDecFrame(),"Invalid Mark Entered");

                } else if (Integer.parseInt(attempt) <= 0 || Integer.parseInt(attempt) > lm.checkMaxAttempts(modCode) ) {
                    JOptionPane.showMessageDialog(vlec.getModDecFrame(),"Invalid Attempt Number Entered");
                }
                else {
                    lm.updateStudentMark(studentID,modCode,markType,mark,sem,attempt);
                    JOptionPane.showMessageDialog(vlec.getModDecFrame(),"Updated sent to database!");
                }
                }catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        vlec.getButtonBackDec().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vlec.hideModDec();
                vlec.showLecturer();
            }
        });
        vlec.getButtonRec().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studEmail = vlec.getTextRec(); //Student email who's record you wish to check

                    try {
                        if(sm.hasModules(studEmail)){
                            JOptionPane.showMessageDialog(vstud.getStudentFrame(), "Student's Modules and marks are : " + sm.viewModules(studEmail));
                        }else{
                            JOptionPane.showMessageDialog(vstud.getStudentFrame(), "You do not currently have any modules assigned.");
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
            }
        });
        vlec.getButtonEnrol().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String modCode = vlec.getTextRec(); //Module code for checking who is enrolled in the course

                try{
                    if(!lm.isLecturerForModule(currentUser,modCode)){
                        JOptionPane.showMessageDialog(vlec.getLecOtherFrame(),"You are not the lecturer for this module.");
                    }
                    else if(!lm.checkModuleExists(modCode)){
                        JOptionPane.showMessageDialog(vlec.getLecOtherFrame(), "A module with this code does not exist.");
                    }else{
                        String students = lm.displayEnrolledStudents(modCode);
                        if (students.equals("")){
                            JOptionPane.showMessageDialog(vlec.getLecOtherFrame(), "You have no students enrolled on this course");
                        }else{
                            JOptionPane.showMessageDialog(vlec.getLecOtherFrame(), students);
                        }
                    }
                }catch(SQLException ex){
                    throw new RuntimeException(ex);
                }

            }
        });
        vlec.getButtonBackOther().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vlec.hideLecOther();
                vlec.showLecturer();
            }
        });
        vlec.getComboModule().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //WHEN BUTTON IS PRESSED:
                //USE THIS MODULE CODE AND SEARCH FOR ALL STUDENTS WHO TAKE THE MODULE
                //THIS WILL POPULATE THE BOX OF TEXT THAT SHOULD CONTAIN ALL STUDENTS THAT TAKE A MODULE
                vlec.getListOfStudents().setText("");
                vlec.getComboStudents().removeAllItems();
                String display = "";
                try {
                    ArrayList<String> StudentIDs = lm.listOfEnrolledIDs(vlec.getModuleFromCombo());
                    for (int i = 0; i < StudentIDs.size(); i++){
                        vlec.getComboStudents().addItem(StudentIDs.get(i));
                        display = display.concat(lm.listEmailAndID(StudentIDs.get(i)));
                    }
                    vlec.getListOfStudents().setText(display);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        //MANAGER ACTION LISTENERS
        vman.getButtonUpdatePassword().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vman.hideManager();
                vuppass.showUpdatePassword();
            }
        });
        vman.getButtonWorkFlow().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vman.hideManager();
                vman.showWorkflow();
            }
        });
        vman.getButtonWorkflowBack().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vman.showManager();
                vman.hideWorkflow();
            }
        });
        vman.getButtonActivate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vman.swapToActivate();
                try {
                    vman.getListOfUsers().setText(m.getDeactivated("Student") + m.getDeactivated("Lecturer") + m.getDeactivated("Manager"));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        vman.getButtonDeactivate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vman.swapToDeactivate();
                try {
                    vman.getListOfUsers().setText(m.getActivated("Student") + m.getActivated("Lecturer") + m.getActivated("Manager"));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        vman.getButtonBacktivation().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vman.hideEditUser();
                vman.showWorkflow();
            }
        });
        vman.getButtonSubmitActivation().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String email = vman.getTextWorkFlow();
                    String accountType = vman.getAccountType();
                    if(!use.checkExists(email)){
                        JOptionPane.showMessageDialog(vman.getManagerEditUserFrame(),"This account does not exist");
                    }
                    else if(!use.checkExistsAndType(email,accountType)){
                        JOptionPane.showMessageDialog(vman.getManagerEditUserFrame(),"Wrong account type selected.");
                    }
                    else if(use.checkActivated(email,accountType)){
                        JOptionPane.showMessageDialog(vman.getManagerEditUserFrame(),"This user is already activated.");
                    }else{
                        m.editAccount(email,1,accountType);
                    }
                    vman.getListOfUsers().setText(m.getDeactivated("Student") +  m.getDeactivated("Lecturer") + m.getDeactivated("Manager"));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        vman.getButtonSubmitDeactivation().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String email = vman.getTextWorkFlow();
                    String accountType = vman.getAccountType();
                    if(!use.checkExists(email)){
                        JOptionPane.showMessageDialog(vman.getManagerEditUserFrame(),"This user does not exist.");
                    }
                    else if(!use.checkExistsAndType(email,accountType)){
                        JOptionPane.showMessageDialog(vman.getManagerEditUserFrame(),"Wrong account type selected.");
                    }
                    else if(!use.checkActivated(email,accountType)){
                        JOptionPane.showMessageDialog(vman.getManagerEditUserFrame(),"This account is already deactivated.");
                    }else {
                        m.editAccount(email, 0, accountType);
                    }
                    vman.getListOfUsers().setText(m.getActivated("Student")+  m.getActivated("Lecturer")  + m.getActivated("Manager"));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        vman.getButtonToLogout().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vman.hideManager();
                vlog.showLogin();
            }
        });
        vman.getButtonToModToLect().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vman.swapToAssignModuleToLecturer();
                vman.hideManager();
            }
        });
        vman.getButtonToDisplayModDets().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vman.swapToModuleDetails();
                vman.clearDec();
                vman.hideManager();
            }
        });
        vman.getButtonToDisplayCourDets().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vman.swapToCourseDetails();
                vman.clearDec();
                vman.hideManager();
            }
        });
        vman.getButtonToIssueDecision().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vman.showDec();
                vman.hideManager();
            }
        });
        vman.getButtonToAddModule().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vman.hideManager();
                vman.showModule();
                vman.clearMod();
            }
        });
        vman.getButtonToUpdateCourDets().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vman.showUpdate();
                vman.hideManager();
            }
        });
        vman.getButtonToIssuePwReset().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vman.swapToReset();
                vman.hideWorkflow();
                String email = vman.getTextWorkFlow();
                String accountType = vman.getAccountType();
                try {
                    vman.getListOfUsers().setText(m.getResets("Student") + m.getResets("Lecturer"));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        vman.getButtonToAddCourse().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vman.hideManager();
                vman.showCourse();
                vman.clearCour();
            }
        });
        vman.getButtonToModToCour().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vman.swapToAssignModuleToCourse();
                vman.hideManager();
            }
        });
        vman.getButtonToAddRules().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vman.swapToAddNewRule();
                vman.hideManager();
            }
        });
        vman.getButtonToEnrolStud().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vman.swapToEnrolStudent();
                vman.hideManager();
            }
        });
        vman.getButtonDecSubmit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String decisionStudent = vman.getDecisionStudent();

                try{
                    if(decisionStudent.equals("")){
                        JOptionPane.showMessageDialog(vman.getButtonEnrolStudent(), "Please fill out all details.");
                    }else if (!use.checkExists(decisionStudent)){
                        JOptionPane.showMessageDialog(vman.getButtonEnrolStudent(), "Student with that email does not exist.");
                    } else if (m.issueDecision(decisionStudent)) {
                        JOptionPane.showMessageDialog(vman.getButtonEnrolStudent(), "Decision for student successfully updated.");
                    }else{
                        JOptionPane.showMessageDialog(vman.getButtonEnrolStudent(), "Something went Wrong. :/");
                    }
                }catch(SQLException ex){
                    throw new RuntimeException(ex);
                }


            }
        });
        vman.getButtonDecBack().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vman.hideDec();
                vman.showManager();
            }
        });
        vman.getButtonOneBoxBack().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vman.hideOneBox();
                vman.showManager();
            }
        });
        vman.getButtonViewModuleDetails().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String moduleCode = vman.getOneBoxText();
                try{
                    if (!lm.checkModuleExists(moduleCode)){
                        JOptionPane.showMessageDialog(vman.getButtonViewModuleDetails(), "Module does not exist. Enter a valid module code");
                    } else{
                        JOptionPane.showMessageDialog(vman.getButtonViewModuleDetails(),m.dispModuleDetails(moduleCode));
                    }
                }catch (SQLException ex){
                    throw new RuntimeException(ex);
                }
            }
        });
        vman.getButtonViewCourseDetails().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String courseCode = vman.getOneBoxText();

                try{
                    if (!m.checkCourseExists(courseCode)){
                        JOptionPane.showMessageDialog(vman.getButtonViewModuleDetails(), "Module does not exist. Enter a valid module code");
                    } else{
                        JOptionPane.showMessageDialog(vman.getButtonViewModuleDetails(),m.dispCourseDetails(courseCode.toUpperCase()));
                    }
                }catch (SQLException ex){
                    throw new RuntimeException(ex);
                }
            }
        });
        vman.getButtonTwoBoxBack().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vman.hideTwoBox();
                vman.clearBoxTwo();
                vman.showManager();
            }
        });
        vman.getButtonAssignModuleToLecturer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String moduleCode = vman.getTwoBoxText1();
                String lecturerEmail = vman.getTwoBoxText2();

                try{
                    if (moduleCode.equals("") || lecturerEmail.equals("")){
                        JOptionPane.showMessageDialog(vman.getButtonAssignModuleToLecturer(), "Please fill out all details.");
                    }else if (!lm.checkModuleExists(moduleCode)) {
                        JOptionPane.showMessageDialog(vman.getButtonAssignModuleToLecturer(), "Module does not exist. Enter a valid module code");
                    }else if (!use.checkExists(lecturerEmail)){
                        JOptionPane.showMessageDialog(vman.getButtonAssignModuleToLecturer(), "Lecturer with that email does not exist.");
                    }else if (m.assignModule(lecturerEmail, moduleCode)){
                        JOptionPane.showMessageDialog(vman.getButtonAssignModuleToLecturer(), "Lecturer successfully assigned to module.");
                    }else{
                        JOptionPane.showMessageDialog(vman.getButtonAssignModuleToLecturer(), "Something went wrong. :/");
                    }
                }catch(SQLException ex){
                    throw new RuntimeException(ex);
                }
            }
        });
        vman.getButtonUpdateCourseDetails().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String courseCode = vman.getUpdateCode();
                String numberOfCompensated = vman.getUpdateComp();
                String gradChoice = vman.getUpdateGrad();

                try{
                    if(courseCode.equals("") || numberOfCompensated.equals("") || gradChoice.equals("")){
                        JOptionPane.showMessageDialog(vman.getButtonAssignModuleToLecturer(), "Please fill out all details.");
                    }else if(Integer.parseInt(numberOfCompensated) < 0){
                        JOptionPane.showMessageDialog(vman.getButtonAssignModuleToLecturer(), "Enter a positive number");
                    }else if(m.updateCourseInfo(courseCode, gradChoice, Integer.parseInt(numberOfCompensated))){
                        JOptionPane.showMessageDialog(vman.getButtonAssignModuleToLecturer(), "Course details updated successfully.");
                    }else{
                        JOptionPane.showMessageDialog(vman.getButtonAssignModuleToLecturer(), "Something went wrong :/");
                    }
                }catch (SQLException ex){
                    throw new RuntimeException(ex);
                }catch (NumberFormatException cex){
                    JOptionPane.showMessageDialog(vman.getButtonAddNewRule(), "Ensure that the new number of attempts is an integer value.");
                }
            }
        });
        vman.getButtonAssignModuleToCourse().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String moduleCode = vman.getTwoBoxText1();
                String courseCode = vman.getTwoBoxText2();

                try{
                    if (moduleCode.equals("") || courseCode.equals("")){
                        JOptionPane.showMessageDialog(vman.getButtonAssignModuleToCourse(), "Please fill out all details.");
                    } else if (!lm.checkModuleExists(moduleCode)) {
                        JOptionPane.showMessageDialog(vman.getButtonAssignModuleToCourse(), "Module does not exist. Enter a valid module code");
                    } else if (!m.checkCourseExists(courseCode)) {
                        JOptionPane.showMessageDialog(vman.getButtonAssignModuleToCourse(), "Course Does not Exist. Enter a valid course code");
                    } else if (m.checkModuleCourseAssignment(moduleCode)) {
                        JOptionPane.showMessageDialog(vman.getButtonAssignModuleToCourse(), "Module already added to this course.");
                    } else if (m.addModuleToCourse(moduleCode, courseCode.toUpperCase())){
                        m.updateEnrolment(m.checkStudentsInCourse(courseCode), moduleCode);
                        JOptionPane.showMessageDialog(vman.getButtonAssignModuleToCourse(), "Module successfully added to course");
                    }
                }catch (SQLException ex){
                    throw new RuntimeException(ex);
                }
            }
        });
        vman.getButtonAddNewRule().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String moduleCode = vman.getTwoBoxText1();
                String newRule = vman.getTwoBoxText2();

                try {
                    if (moduleCode.equals("") || newRule.equals("")){
                        JOptionPane.showMessageDialog(vman.getButtonAddNewRule(), "Please fill out all details.");
                    }else if(!lm.checkModuleExists(moduleCode)){
                        JOptionPane.showMessageDialog(vman.getButtonAddNewRule(), "Module does not exist. Enter a valid module code");
                    }else if (Integer.parseInt(newRule) <=0){
                        JOptionPane.showMessageDialog(vman.getButtonAddNewRule(), "Enter a number that is not 0 or negative.");
                    } else if (m.updateMaxAttempts(moduleCode, Integer.parseInt(newRule))) {
                        JOptionPane.showMessageDialog(vman.getButtonAddNewRule(), "Max attempts updated successfully.");
                    }else{
                        JOptionPane.showMessageDialog(vman.getButtonAddNewRule(), "Something went wrong. :/");
                    }

                }catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }catch (NumberFormatException cex){
                    JOptionPane.showMessageDialog(vman.getButtonAddNewRule(), "Ensure that the new number of attempts is an integer value.");
                }
            }
        });
        vman.getButtonEnrolStudent().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentEmail = vman.getTwoBoxText1();
                String courseCode = vman.getTwoBoxText2();
                try {
                    if (studentEmail.equals("") || studentEmail.equals("")){
                        JOptionPane.showMessageDialog(vman.getButtonEnrolStudent(), "Please fill out all details.");
                    }else if(!sm.checkExists(studentEmail)){
                        JOptionPane.showMessageDialog(vman.getButtonEnrolStudent(), "Student does not exist. Enter a valid student Email.");
                    }else if (!m.checkCourseExists(courseCode)){
                        JOptionPane.showMessageDialog(vman.getButtonEnrolStudent(), "Course does not exist. Enter a valid course code.");
                    }else if(m.enrollStudent(studentEmail, courseCode)){
                        m.deleteOccurrences(studentEmail);
                        m.updateEnrolmentForNewStudent(m.checkModulesInCourse(courseCode), studentEmail);
                        JOptionPane.showMessageDialog(vman.getButtonEnrolStudent(), "Student has successfully been enrolled into course");
                    }else{
                        JOptionPane.showMessageDialog(vman.getButtonEnrolStudent(), "Something went wrong. :/");
                    }

                }catch(SQLException ex){
                    throw new RuntimeException(ex);
                }
            }
        });
        vman.getButtonCreateBack().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vman.showWorkflow();
                vman.hideCreate();
            }
        });
        vman.getButtonCreateSubmit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String managerEmail = vman.getManEmail();
                String managerPW = vman.getManPW();
                try {
                    if(managerEmail.length() == 0 || managerPW.length() == 0){
                        JOptionPane.showMessageDialog(vman.getManagerCreateManagerFrame(),"Please fill out all the fields");
                    }
                    else if(use.checkExists(managerEmail)){
                        JOptionPane.showMessageDialog(vman.getManagerCreateManagerFrame(),"An account already exists with this name");
                    }else{
                        m.createManager(managerEmail,managerPW);
                        JOptionPane.showMessageDialog(vman.getManagerCreateManagerFrame(), "Manager account created successfully ");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        vman.getButtonCreate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vman.hideManager();
                vman.showCreate();
            }
        });
        vman.getButtonSubmitNewPW().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vman.getListOfUsers().setText(m.getResets("Student") + m.getResets("Lecturer"));
                    String email = vman.getTextWorkFlow();
                    String accountType = vman.getAccountType();
                    if(!use.checkExists(email)){
                        JOptionPane.showMessageDialog(vman.getManagerWorkflowFrame(), "Invalid user, please check");
                    }
                    else if(!use.checkExistsAndType(email,accountType)){
                        JOptionPane.showMessageDialog(vman.getManagerWorkflowFrame(),"Wrong account type selected for this user.");
                    }
                    else if(!use.checkPwResetStatus(email,accountType)){
                        JOptionPane.showMessageDialog(vman.getManagerWorkflowFrame(),"This user has not requested a password reset");
                    }else{
                        m.resetPassword(email,accountType);
                        vman.getListOfUsers().setText(m.getResets("Student") + m.getResets("Lecturer"));

                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        vman.getButtonBackNewModule().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vman.hideModule();
                vman.showManager();
            }
        });
        vman.getButtonBackNewCourse().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vman.hideCourse();
                vman.showManager();
            }
        });
        vman.getButtonSubmitNewCourse().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String courseCode = vman.getCourCode(); //Course Code
                String courseDept = vman.getCourDept(); //Course Department
                String courseGrad = ""; //Course Post or Under Grad
                String courseName = vman.getCourName(); //Course Name

                switch (vman.getCourGrad()) {
                    case "Undergraduate" -> courseGrad = "Under";
                    case "Postgraduate" -> courseGrad = "Post";
                }

                try{
                    if(courseCode.equals("") || courseDept.equals("") || courseName.equals("")){
                        JOptionPane.showMessageDialog(vman.getButtonSubmitNewCourse(), "Please fill out all details.");
                    }else if(m.checkCourseExists(courseCode.toUpperCase())){
                        JOptionPane.showMessageDialog(vman.getButtonSubmitNewCourse(), "A course with this course Code already exists.");
                    }else if(!m.checkDeptExists(courseDept.toUpperCase())) {
                        JOptionPane.showMessageDialog(vman.getButtonSubmitNewCourse(), "Department with that name does not exist.");
                    }else if(m.addCourse(courseCode, courseName, courseDept.toUpperCase(), courseGrad)){
                        JOptionPane.showMessageDialog(vman.getButtonSubmitNewCourse(), "New Course created successfully.");
                    }else{
                        JOptionPane.showMessageDialog(vman.getButtonSubmitNewCourse(), "Something went wrong. :/");
                    }
                }catch (SQLException ex){
                    throw new RuntimeException(ex);
                }
            }
        });
        vman.getButtonUpdateBack().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vman.hideUpdate();
                vman.showManager();
            }
        });
        vman.getButtonSubmitNewModule().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String modName = vman.getModName();      //Module name
                String modCode = vman.getModCode();      //Module Code
                String modAttempts = vman.getModAttempts();  //Module max attempts
                String modCreds = vman.getModCreds();     //Module Credits
                String modInfo = vman.getModInfo();      //Module info
                String modSems = vman.getModSems();      //Module semesters

                int sems = 0;
                switch (modSems){
                    case "1", "2":
                        sems = 1;
                        break;
                    case "Both":
                        sems = 2;
                }

                try {
                    if (modName.equals("") || modCode.equals("") || modAttempts.equals("") || modCreds.equals("") || modInfo.equals("") || modSems.equals("")){
                        JOptionPane.showMessageDialog(vman.getButtonSubmitNewModule(), "Please fill out all details.");
                    } else if (Integer.parseInt(modCreds) <=0 || Integer.parseInt(modAttempts) <=0 || Integer.parseInt(modSems)<=0) {
                        JOptionPane.showMessageDialog(vman.getButtonSubmitNewModule(), "Ensure all integer fields contain positive, non-zero integers.");
                    } else if (lm.checkModuleExists(modCode)){
                        JOptionPane.showMessageDialog(vman.getButtonSubmitNewModule(), "A module with this module code alerady exists.");
                    } else if (m.addModule(modCode, modName, Integer.parseInt(modCreds), modInfo, sems, Integer.parseInt(modAttempts), 0)) {
                        JOptionPane.showMessageDialog(vman.getButtonSubmitNewModule(), "New module added successfully.");
                    }else{
                        JOptionPane.showMessageDialog(vman.getButtonSubmitNewModule(), "Something went wrong. :/");
                    }
                }catch (SQLException ex){
                    throw new RuntimeException(ex);
                }catch (NumberFormatException cex){
                    JOptionPane.showMessageDialog(vman.getButtonSubmitNewModule(), "Ensure that the Credits of the module, as well as the number of attempts and number of semesters are integer values.");
                }
            }
        });
    }
    //methods to go here
    //login view methods
    //request password reset implemented
    public void PWReset() throws SQLException {
        if (vlog.getLoginEmail().length() == 0) {
            JOptionPane.showMessageDialog(vlog.getLoginFrame(), "Please enter your email first.");
        } else try {
            if (!use.checkExists(vlog.getLoginEmail())) {
                JOptionPane.showMessageDialog(vlog.getLoginFrame(), "This user does not exist.");
            } else {
                if (use.requestResetPassword(vlog.getLoginEmail(), vlog.getLoginType())) {
                    JOptionPane.showMessageDialog(vlog.getLoginFrame(), "Password reset request sent successfully.");     //could potentially add another method to check if they've already requested a reset
                } else {
                    JOptionPane.showMessageDialog(vlog.getLoginFrame(), "Details correct but Error sending reset request, please try again later.");
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    //login method implemented
    public void login () throws SQLException {
        if (vlog.getLoginEmail().length() == 0 || vlog.getLoginPassword().length() == 0) {
            JOptionPane.showMessageDialog(vlog.getLoginFrame(), "Please enter a username and a password");
        } else try {
            if (!use.checkExists(vlog.getLoginEmail())) {
                JOptionPane.showMessageDialog(vlog.getLoginFrame(), "This user does not exist.");
            } else if (!use.login(vlog.getLoginEmail(), vlog.getLoginPassword(), vlog.getLoginType())) {
                JOptionPane.showMessageDialog(vlog.getLoginFrame(), "Invalid credentials.");
           } else if (!use.checkActivated(vlog.getLoginEmail(), vlog.getLoginType())) {
                JOptionPane.showMessageDialog(vlog.getLoginFrame(), "This user is not activated");
            } else if (use.login(vlog.getLoginEmail(), vlog.getLoginPassword(), vlog.getLoginType())) {
                currentUser = vlog.getLoginEmail();
                currentAccountType = vlog.getLoginType();
                switch (vlog.getLoginIndex()) {
                    case 0:
                        vstud.showStudent();
                        break;
                    case 1:
                        vlec.showLecturer();
                        break;
                    case 2:
                        vman.showManager();
                }
                vlog.clearLoginText();
                vlog.hideLogin();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
       }
    }

    //deals with the signup functionality
    public void signUp() throws SQLException {
        int day = vsign.getDay();
        int year = vsign.getYear();
        int month = vsign.getMonth()+1;

        LocalDate local = LocalDate.of(year,month,day);
        Date sqlDate = java.sql.Date.valueOf(local);

        if (vsign.getSignupEmail().length() == 0 || vsign.getSignupPW().length() == 0 || vsign.getSignupFN().length() == 0 || vsign.getSignupSur().length() == 0) {
            JOptionPane.showMessageDialog(vsign.getSignupFrame(), "Please fill out all of the details.");
        }
        else if(!(vsign.getSignupPW().equals(vsign.getSignupPWConfirm()))){
            JOptionPane.showMessageDialog(vsign.getSignupFrame(),"Passwords do not match");
        }
        else try {
            if (use.checkExists(vsign.getSignupEmail())){
                JOptionPane.showMessageDialog(vsign.getSignupFrame(), "This account already exists.");
            }
            else if(vsign.getSignupType().equals("Lecturer")){
                if(vsign.getSignupQual().equals("N/A")){
                    JOptionPane.showMessageDialog(vsign.getSignupFrame(),"Please select a qualification");
                }else if (lm.signUpLec(vsign.getSignupEmail(),vsign.getSignupPW(),vsign.getSignupFN(), vsign.getSignupSur(),vsign.getSignupGender(),vsign.getSignupType(),vsign.getSignupQual(),sqlDate)) {
                    JOptionPane.showMessageDialog(vsign.getSignupFrame(),"Lecturer signed up successfully.");
                }else{
                    JOptionPane.showMessageDialog(vsign.getSignupFrame(),"Error occurred, please try again later.");
                }
            } else if (use.signUp(vsign.getSignupEmail(), vsign.getSignupPW(), vsign.getSignupFN(), vsign.getSignupSur(), vsign.getSignupGender(), vsign.getSignupType(),sqlDate) && !vsign.getSignupType().equals("Lecturer")) {
                JOptionPane.showMessageDialog(vsign.getSignupFrame(), vsign.getSignupType() + " signed up successfully.");
            } else {
                JOptionPane.showMessageDialog(vsign.getSignupFrame(), "Error occurred, please try again later.");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean checkIsInt(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}






