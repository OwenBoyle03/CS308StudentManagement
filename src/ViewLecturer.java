import javax.swing.*;
import java.awt.*;

public class ViewLecturer {

    //FRAMES
    private JFrame lecturerFrame = new JFrame();
    private JFrame modMatFrame = new JFrame();
    private JFrame modDecFrame = new JFrame();
    private JFrame lecOtherFrame = new JFrame();
    private JFrame updateModuleFrame = new JFrame();

    //LABELS
    private Label moduleNameLabel = new Label("Module code: ");
    private Label moduleInfoLabel = new Label("Module info: ");
    private Label labelGreeting = new Label("Hello Lecturer");
    private Label inputLabel = new Label("Please enter the module code you wish to upload content to: ");
    private Label inputLabel2 = new Label("Please enter the week you wish to upload to");
    private Label modLabel = new Label("Enter a module code: ");
    private Label stuLabel = new Label("Student ID: ");
    private Label inputLabel3 = new Label("Mark: ");
    private Label inputLabel4 = new Label("Mark Type: ");
    private Label inputLabel5 = new Label("Attempt Num: ");
    private Label inputLabel6 = new Label("Semester: ");



    private Label messageLabel = new Label("Please enter the student's ID that you wish to see the record of: ");

    //BUTTONS
    private JButton buttonUpdateInfoEnter = new JButton("Enter"); // Button for confirming updating material
    private JButton buttonUpdateInfoBack = new JButton("Back"); // Button to go back to lecturer screen
    private JButton buttonModMat = new JButton("Upload module material"); //Button to swap to upload module material screen
    private JButton buttonViewEnrol = new JButton("View enrolled students"); //Button to swap to view enrolled students screen
    private JButton buttonUpdateDecision = new JButton("Update module decision"); //Button to swap to update module decision screen
    private JButton buttonLogout = new JButton("Logout"); //Button to go from Lecturer home screen to log in screen
    private JButton buttonAddLabMaterial = new JButton("Add Lab Material"); //Button to add/select lab material from file explorer
    private JButton buttonAddLectureMaterial = new JButton("Add Lecture Material"); //Button to add/select lecture material from file explorer
    private JButton buttonSubmit = new JButton("Upload"); //Button to upload module material to database
    private JButton buttonUpdateInfo = new JButton("Update Module Information"); // Button to update module information
    private JButton buttonBack = new JButton("Back"); //Button to go from module material screen to lecturer home screen
    private JButton buttonSubmitDec = new JButton("Upload"); //Button to upload new module decision for a student
    private JButton buttonBackDec = new JButton("Back"); //Button to go from module decision screen to lecturer home screen
    private JButton buttonRec = new JButton("Check"); //Button to check a student's record
    private JButton buttonEnrol = new JButton("Check"); //Button to view all enrolled students in a module
    private JButton buttonBackOther = new JButton("Back"); //Button to go from enrolled/record screen to lecturer home screen
    private JButton buttonUpdatePassword = new JButton("Update password"); //button to go to update password view

    //TEXT BOXES
    private JTextField moduleNameBox = new JTextField();  //Text field for entering Module name
    private JTextField moduleInfoBox = new JTextField(); //Text field for entering Module info
    private JTextField textMod = new JTextField();        //Text field holding the module code chosen to upload material to
    private JTextField textWeek = new JTextField();       //Text field holding the week that will be uploaded to
    private JTextField textModule = new JTextField();     //Text field holding the module code that the lecturer is making a decision on
    private JTextField textStu = new JTextField();        //Text field holding the student ID that will get the module decision
    private JTextField textMark = new JTextField();       //Text field holding mark
    private JTextField textRec = new JTextField();        //Text field holding either student email for checking their record
                                                          //or holding module code for checking enrolled students
    private JTextField textAtt = new JTextField();        //Text field holding the attempt number for the exam record
    private JTextArea listOfModules = new JTextArea("Imagine \n a \n cool \n list \n of \n modules"); //This will populate when frame is open, list of all modules the lecturer runs
    private JTextArea listOfStudents = new JTextArea("Imagine \n a \n cool \n list \n of \n students"); //Populates after check button pressed, list of all students in selected module

    //COMBO BOXES
    private String[] markType = {"Lab", "Exam"};
    private JComboBox comboMark = new JComboBox(markType);
    private JComboBox comboStudents = new JComboBox();
    private JComboBox comboModule = new JComboBox();
    private String[] sems = {"1" , "2"};
    private JComboBox comboSem = new JComboBox(sems);



    //FRAME SETUP FUNCTIONS
    //main lecturer screen setup
    public void setupLecturer(){
        lecturerFrame.setDefaultCloseOperation(lecturerFrame.EXIT_ON_CLOSE);
        labelGreeting.setBounds(25,10,200,25);
        buttonModMat.setBounds(25, 40, 200,25);
        buttonViewEnrol.setBounds(25, 70, 200,25);
        buttonUpdateDecision.setBounds(25, 100, 200,25);
        buttonLogout.setBounds(25, 190, 200,25);
        buttonUpdatePassword.setBounds(25,160,200,25);
        buttonUpdateInfo.setBounds(25,130,200,25);
        lecturerFrame.setSize(250, 280);
        lecturerFrame.setLayout(null);
        lecturerFrame.setVisible(false);
        lecturerFrame.add(labelGreeting);
        lecturerFrame.add(buttonModMat);
        lecturerFrame.add(buttonViewEnrol);
        lecturerFrame.add(buttonUpdateDecision);
        lecturerFrame.add(buttonLogout);
        lecturerFrame.add(buttonUpdatePassword);
        lecturerFrame.add(buttonUpdateInfo);
        setupLecOther();
        setupModDec();
        setupModMat();
        setupUpdateModuleFrame();
    }
    //module material view setup
    public void setupModMat(){
        inputLabel.setBounds(25,10,350,20);
        textMod.setBounds(25,35,350,20);
        inputLabel2.setBounds(25,60,350,20);
        textWeek.setBounds(25,85,350,20);
        buttonAddLabMaterial.setBounds(25,110,150,20);
        buttonAddLectureMaterial.setBounds(225,110,150,20);
        buttonSubmit.setBounds(25,135,150,20);
        buttonBack.setBounds(225, 135, 150,20);
        modMatFrame.setDefaultCloseOperation(modMatFrame.EXIT_ON_CLOSE);
        modMatFrame.setSize(400,250);
        modMatFrame.setLayout(null);
        modMatFrame.setVisible(false);
        modMatFrame.add(inputLabel);
        modMatFrame.add(inputLabel2);
        modMatFrame.add(textMod);
        modMatFrame.add(textWeek);
        modMatFrame.add(buttonAddLabMaterial);
        modMatFrame.add(buttonAddLectureMaterial);
        modMatFrame.add(buttonSubmit);
        modMatFrame.add(buttonBack);

    }
    //set up function for decision
    public void setupModDec(){
        modDecFrame.setDefaultCloseOperation(modDecFrame.EXIT_ON_CLOSE);
        modDecFrame.setSize(500,525);
        modDecFrame.setLayout(null);
        modDecFrame.setVisible(false);
        listOfModules.setEditable(false);
        listOfStudents.setEditable(false);
        //listOfModules.setBounds(25,10,200,100);
        listOfStudents.setBounds(25,130,200,345);
        modLabel.setBounds(25,10,430,20);
        comboModule.setBounds(25,35,430,40);
        stuLabel.setBounds(245,130,100,20);
        comboStudents.setBounds(355,130,100,20);
        inputLabel3.setBounds(245,165,100,20);
        textMark.setBounds(355,165,100,20);
        inputLabel4.setBounds(245,190,100,20);
        comboMark.setBounds(355,190,100,20);
        inputLabel5.setBounds(245,215,100,20);
        textAtt.setBounds(355,215,100,20);
        inputLabel6.setBounds(245,240,100,20);
        comboSem.setBounds(355,240,100,20);
        buttonSubmitDec.setBounds(245,265,100,20);
        buttonBackDec.setBounds(355, 265, 100,20);
        //modDecFrame.add(listOfModules);
        modDecFrame.add(listOfStudents);
        modDecFrame.add(modLabel);
        modDecFrame.add(comboModule);
        modDecFrame.add(stuLabel);
        modDecFrame.add(comboStudents);
        modDecFrame.add(inputLabel3);
        modDecFrame.add(textMark);
        modDecFrame.add(comboMark);
        modDecFrame.add(buttonSubmitDec);
        modDecFrame.add(buttonBackDec);
        modDecFrame.add(inputLabel4);
        modDecFrame.add(inputLabel5);
        modDecFrame.add(inputLabel6);
        modDecFrame.add(textAtt);
        modDecFrame.add(comboSem);
    }
    //set up function for lecturer frame
    public void setupLecOther(){
        lecOtherFrame.setDefaultCloseOperation(lecOtherFrame.EXIT_ON_CLOSE);
        lecOtherFrame.setSize(400,250);
        lecOtherFrame.setLayout(null);
        lecOtherFrame.setVisible(false);
        messageLabel.setBounds(25,10,350,20);
        lecOtherFrame.add(messageLabel);
        textRec.setBounds(25,35,350,20);
        lecOtherFrame.add(textRec);
        buttonRec.setBounds(25,60,150,20);
        buttonEnrol.setBounds(25,60,150,20);
        buttonBackOther.setBounds(225, 60, 150,20);
        lecOtherFrame.add(buttonBackOther);
    }
    //setup for update Module frame
    public void setupUpdateModuleFrame(){
        updateModuleFrame.setDefaultCloseOperation(updateModuleFrame.EXIT_ON_CLOSE);
        updateModuleFrame.setSize(300,300);
        updateModuleFrame.setLayout(null);
        updateModuleFrame.setVisible(false);
        moduleNameLabel.setBounds(25,35,200,20);
        moduleNameBox.setBounds(25,60,200,20);
        moduleInfoLabel.setBounds(25,85,200,20);
        moduleInfoBox.setBounds(25,110,200,20);
        buttonUpdateInfoBack.setBounds(25,220,100,20);
        buttonUpdateInfoEnter.setBounds(150,220,100,20);
        updateModuleFrame.add(moduleNameBox);
        updateModuleFrame.add(moduleNameLabel);
        updateModuleFrame.add(moduleInfoLabel);
        updateModuleFrame.add(moduleInfoBox);
        updateModuleFrame.add(buttonUpdateInfoEnter);
        updateModuleFrame.add(buttonUpdateInfoBack);
    }

    //The switchTo Methods could be moved into controller class, just here to show how to swap between recycled frames
    public void switchToEnrolled(){
        messageLabel.setText("Please enter a module code to see the students enrolled in it: ");
        lecOtherFrame.remove(buttonRec);
        lecOtherFrame.add(buttonEnrol);
        lecOtherFrame.setVisible(true);}
    public void switchToRecord(){
        messageLabel.setText("Please enter the student's ID that you wish to see the record of: ");
        lecOtherFrame.add(buttonRec);
        lecOtherFrame.remove(buttonEnrol);
        lecOtherFrame.setVisible(true);}
//functions for showing screens, and getting info from the boxes
    public void showLecturer(){
        lecturerFrame.setVisible(true);
    }
    public void hideLecturer(){
        lecturerFrame.setVisible(false);}
    public void showUpdateModInfo(){
        updateModuleFrame.setVisible(true);
    }
    public JButton getModInfoEnter(){
        return buttonUpdateInfoEnter;
    }
    public JButton getModInfoBack(){
        return buttonUpdateInfoBack;
    }
    public void hideUpdateModInfo(){
        updateModuleFrame.setVisible(false);
    }
    public String getModuleName(){
        return moduleNameBox.getText();
    }
    public String getModuleInfo(){
        return moduleInfoBox.getText();
    }
    public JButton getButtonUpdateInfo(){
        return buttonUpdateInfo;
    }
    public void hideModMat(){
        modMatFrame.setVisible(false);}
    public void showModMat(){
        modMatFrame.setVisible(true);
    }
    public void hideModDec(){
        modDecFrame.setVisible(false);
    }
    public void showModDec(){
        modDecFrame.setVisible(true);
    }
    public void hideLecOther(){
        lecOtherFrame.setVisible(false);}
    public JButton getButtonModMat(){
        return buttonModMat;}
    public JButton getButtonViewEnrol(){
        return buttonViewEnrol;}
    public JButton getButtonUpdateDecision(){
        return buttonUpdateDecision;}
    public JButton getButtonLogout(){
        return  buttonLogout;}
    public JButton getButtonBack(){
        return buttonBack;}
    public JButton getButtonSubmit(){
        return buttonSubmit;}
    public JButton getButtonSubmitDec(){
        return buttonSubmitDec;}
    public JButton getButtonRec(){
        return buttonRec;}
    public JButton getButtonBackDec(){
        return buttonBackDec;}
    public JButton getButtonEnrol(){
        return buttonEnrol;}
    public JButton getButtonUpdatePassword(){
        return buttonUpdatePassword;
    }
    public JButton getButtonBackOther(){
        return buttonBackOther;}
    public JButton getButtonAddLabMaterial(){
        return buttonAddLabMaterial;
    }
    public JButton getButtonAddLectureMaterial(){
        return buttonAddLectureMaterial;
    }
    public JTextArea getListOfStudents(){
        return listOfStudents;
    }
    public JFrame getModMatFrame(){
        return modMatFrame;}
    public JFrame getModDecFrame(){
        return modDecFrame;}
    public JFrame getLecOtherFrame(){
        return lecOtherFrame;}
    public String getTextMod(){
        return textMod.getText();}
    public String getTextWeek(){
        return textWeek.getText();}
    public String getTextModule(){
        return (String) comboModule.getSelectedItem();}
    public String getMarkType(){
        return (String) comboMark.getSelectedItem();
    }
    public String getTextStu(){
        return (String) comboStudents.getSelectedItem();}
    public String getTextMark(){
        return textMark.getText();
    }
    public String getTextRec(){
        return textRec.getText();}
// functions to clear the text
    public void clearModMat(){
        textMod.setText("");
        textWeek.setText("");
        textMod.grabFocus();
    }
    public void clearModDec(){
        textModule.setText("");
        textStu.setText("");
        textMark.setText("");
        listOfStudents.setText("");
        textModule.grabFocus();
    }
    public void clearLecOther(){
        textRec.setText("");
        textRec.grabFocus();
    }
    public void clearModInfo(){
        moduleNameBox.setText("");
        moduleInfoBox.setText("");
    }
    public JFrame getUpdateModuleFrame(){
        return updateModuleFrame;
    }

    public JComboBox getComboStudents() {
        return comboStudents;
    }

    public JComboBox getComboModule() {
        return comboModule;
    }
    public String getModuleFromCombo(){
        return (String) comboModule.getSelectedItem();
    }
    public String getSemester(){
        return sems[comboSem.getSelectedIndex()];
    }
    public String getAttemptNum(){
        return textAtt.getText();
    }


}
