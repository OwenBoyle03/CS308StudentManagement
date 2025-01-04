import javax.swing.*;
import java.awt.*;

public class ViewManager {
    //FRAMES
    private JFrame managerHomeFrame = new JFrame();
    private JFrame managerDecisionFrame = new JFrame();
    private JFrame managerOneBoxFrame = new JFrame();
    private JFrame managerTwoBoxFrame = new JFrame();
    private JFrame managerWorkflowFrame = new JFrame();
    private JFrame managerEditUserFrame = new JFrame(); //PW Reset can fall under this, it can just set to a default pw
    private JFrame managerCreateManagerFrame = new JFrame();
    //Only need password and email to make a manager
    private JFrame managerModuleFrame = new JFrame();
    private JFrame managerCourseFrame = new JFrame();
    private JFrame managerUpdateCourseFrame = new JFrame();

    //LABELS
    private Label labelGreeting = new Label("Hello Admin");
    private Label decLabel2 = new Label("Please enter the student Email you wish to make the decision for: ");
    private Label workflowLabel = new Label("Workflow");
    private Label oneBoxLabel = new Label("");
    private Label twoBoxLabel1 = new Label("");
    private Label twoBoxLabel2 = new Label("");
    private Label wfLabel = new Label("");
    private Label typeLabel = new Label("Please select the type of the user");
    private Label managerEmailLabel = new Label("Enter the email of the new Manager");
    private Label managerPasswordLabel = new Label("Enter their password");
    private Label modNameLabel = new Label("Enter the module name");
    private Label modCodeLabel = new Label("Enter the module code");
    private Label modInfoLabel = new Label("Enter the module info");
    private Label modSemsLabel = new Label("Select the semesters the module runs");
    private Label modCredLabel = new Label("Enter the module credit weighting");
    private Label modAttLabel = new Label("Enter the max number of attempts");
    private Label courNameLabel = new Label("Enter the course name");
    private Label courCodeLabel = new Label("Enter the course code");
    private Label courDeptLabel = new Label("Enter the course Department Name");
    private Label courGradLabel = new Label("Select grad type");
    private Label updateCourseLabel1 = new Label("Enter Course Code");
    private Label updateCourseLabel2 = new Label("Select Grad Status");
    private Label updateCourseLabel3 = new Label("Enter the number of compensated modules");

    //BUTTONS
    //These buttons change the screen from the manager home screen to their respective screens, the text for each button shows what screen
    private JButton buttonModtoLect = new JButton("Assign Module to Lecturer");
    private JButton buttonDisplayModDets = new JButton("View Module Details");
    private JButton buttonDisplayCourDets = new JButton("View Course Details");
    private JButton buttonIssueDecision = new JButton("Issue Decision to Student");
    private JButton buttonAddModule = new JButton("Add New Module");
    private JButton buttonUpdateCourDets = new JButton("Update Course Details");
    private JButton buttonIssuePWReset = new JButton("Issue Password Reset");
    private JButton buttonAddCourse = new JButton("Add New Course");
    private JButton buttonModtoCour = new JButton("Assign Module to Course");
    private JButton buttonAddRules = new JButton("Update Max Attempts");
    private JButton buttonEnrolStud = new JButton("Enrol Student into Course");
    private JButton buttonLogout = new JButton("Logout");
    private JButton buttonWorkFlow = new JButton("View Workflow"); //Switches from manager home screen to workflow
    //These buttons are used on the workflow screen
    private JButton buttonDeactivate = new JButton("Deactivate User"); //Submits input for deactivating a user
    private JButton buttonActivate = new JButton("Activate User"); //Switches to activate screen, on the workflow page
    private JButton buttonCreate = new JButton("Create New Manager"); //Switches to create manager screen, from workflow
    private JButton buttonWorkflowBack = new JButton("Back");//Go from workflow screen to home
    private JButton buttonUpdatePassword = new JButton("Update password"); //button to go to update password screen

    //These Buttons are used on edit Screen
    private JButton buttonSubmitActivation = new JButton("Submit");
    private JButton buttonSubmitDeactivation = new JButton("Submit");
    private JButton buttonSubmitNewPW = new JButton("Submit");
    private JButton buttonBacktivation = new JButton("Back");

    //These buttons are used in the issue decision screen
    private JButton buttonDecSubmit = new JButton("Upload"); //This button submits the decision
    private JButton buttonDecBack = new JButton("Back"); //Go from decision screen to manager home screen

    //These buttons are for the one input box screen, they include; View module and course details and deactivate a user
    private JButton buttonOneBoxBack = new JButton("Back"); //Go from the one input box screen to manager home screen
    private JButton buttonViewModuleDetails = new JButton("Check"); //Submits input for the view module details function
    private JButton buttonViewCourseDetails = new JButton("Check"); //Submits input for the view course details function

    //These buttons are used in the two input box screen
    private JButton buttonTwoBoxBack = new JButton("Back"); //Go from two box screen to manager home screen
    private JButton buttonAssignModuleToLecturer = new JButton("Submit"); //Submit inputs for the assign module to lecturer method
    private JButton buttonUpdateCourseDetails = new JButton("Submit"); //Submit inputs for the update course details method
    private JButton buttonAssignModuleToCourse = new JButton("Submit"); //Submit inputs for the assign module to course method
    private JButton buttonAddNewRule = new JButton("Submit"); //Submit inputs for the add new rule method
    private JButton buttonEnrolStudent = new JButton("Submit"); //Submits inputs for the enrol student in course method

    //Buttons for create new manager screen
    private JButton buttonCreateSubmit = new JButton("Submit");
    private JButton buttonCreateBack = new JButton("Back");

    //Buttons for Add new Module Screen
    private JButton buttonSubmitNewModule = new JButton("Submit");
    private JButton buttonBackNewModule = new JButton("Back");

    //Buttons for Add new Course Screen
    private JButton buttonSubmitNewCourse = new JButton("Submit");
    private JButton buttonBackNewCourse = new JButton("Back");

    //Buttons for update Course details
    private JButton buttonUpdateBack = new JButton("Back");

    //TEXT BOXES
    private JTextField textDecStu = new JTextField(); //Stores the student email for the decision screen
    private JTextField oneBoxText = new JTextField();
    private JTextField textTwoBox1 = new JTextField();  //Stores the following based on selected GUI screen:
    //Assign module to lecturer - Module Code
    //Update Course Details - Course Code
    //Assign module to course - Module code
    //Add new rule - Module Code
    //Enrol student in course - Student email
    private JTextField textTwoBox2 = new JTextField();//Stores the following based on selected GUI screen:
    //Assign module to lecturer - Lecturer Email
    //Update Course Details - New Details
    //Assign module to course - course code
    //Add new rule - New Rule
    //Enrol student in course - Course Code
    private JTextField textWorkFlow = new JTextField();
    private JTextArea listOfUsers = new JTextArea();
    private JTextField textManEmail = new JTextField();
    private JPasswordField textManPW = new JPasswordField();
    private JTextField textModCode = new JTextField();
    private JTextField textModName = new JTextField();
    private JTextField textModCreds = new JTextField();
    private JTextField textModInfo = new JTextField();
    private JTextField textModAttempts = new JTextField();
    private JTextField textCourseCode = new JTextField();
    private JTextField textCourseName = new JTextField();
    private JTextField textCourseDept = new JTextField();
    private JTextField textUpdateCode = new JTextField();
    private JTextField textUpdateComp = new JTextField();

    //COMBO BOXES
    private String[] types = {"Student", "Lecturer", "Manager"};
    private JComboBox comboType = new JComboBox(types);
    private String[] semesters = {"1", "2", "Both"};
    private JComboBox comboSems = new JComboBox(semesters);
    private String[] gradType = {"Post", "Under"};
    private JComboBox comboGrad = new JComboBox(gradType);
    private JComboBox comboUpdateGrad = new JComboBox(gradType);

    //SETUPS
    public void setupManager(){
        labelGreeting.setBounds(290,10,200,25);
        buttonDisplayModDets.setBounds(230, 40, 200,25);
        buttonModtoLect.setBounds(25,40,200,25);
        buttonDisplayCourDets.setBounds(435, 40, 200,25);
        buttonIssueDecision.setBounds(25, 70, 200,25);
        buttonAddModule.setBounds(230, 70, 200,25);
        buttonUpdateCourDets.setBounds(435, 70, 200,25);
        buttonAddCourse.setBounds(230, 100, 200,25);
        buttonModtoCour.setBounds(435, 100, 200,25);
        buttonWorkFlow.setBounds(25, 100, 200,25);
        buttonAddRules.setBounds(230, 130, 200,25);
        buttonEnrolStud.setBounds(435, 130, 200,25);
        buttonUpdatePassword.setBounds(25,130,200,25);
        buttonLogout.setBounds(25, 160, 200,25);
        managerHomeFrame.add(labelGreeting);
        managerHomeFrame.add(buttonUpdatePassword);
        managerHomeFrame.add(buttonModtoLect);
        managerHomeFrame.add(buttonDisplayModDets);
        managerHomeFrame.add(buttonDisplayCourDets);
        managerHomeFrame.add(buttonIssueDecision);
        managerHomeFrame.add(buttonAddModule);
        managerHomeFrame.add(buttonUpdateCourDets);
        managerHomeFrame.add(buttonAddCourse);
        managerHomeFrame.add(buttonModtoCour);
        managerHomeFrame.add(buttonWorkFlow);
        managerHomeFrame.add(buttonAddRules);
        managerHomeFrame.add(buttonEnrolStud);
        managerHomeFrame.add(buttonLogout);
        managerHomeFrame.setSize(670, 250);
        managerHomeFrame.setLayout(null);
        managerHomeFrame.setVisible(false);
        setupManagerDecision();
        setupManagerOneBox();
        setupManagerTwoBox();
        setupManagerWorkflow();
        setupManagerEdit();
        setupManagerCreate();
        setupModuleFrame();
        setupCourse();
        setupUpdateCourse();
    }
    //sets up the manager workflow screen
    public void setupManagerWorkflow(){
        workflowLabel.setBounds(25,10,350,20);
        buttonActivate.setBounds(25,35,350,20);
        buttonDeactivate.setBounds(25,60,350,20);
        buttonCreate.setBounds(25,85,350,20);
        buttonIssuePWReset.setBounds(25,110,350,20);
        buttonWorkflowBack.setBounds(25,135,350,20);
        managerWorkflowFrame.add(workflowLabel);
        managerWorkflowFrame.add(buttonActivate);
        managerWorkflowFrame.add(buttonDeactivate);
        managerWorkflowFrame.add(buttonCreate);
        managerWorkflowFrame.add(buttonIssuePWReset);
        managerWorkflowFrame.add(buttonWorkflowBack);
        managerWorkflowFrame.setSize(400, 200);
        managerWorkflowFrame.setLayout(null);
        managerWorkflowFrame.setVisible(false);
    }
    //setup the manager decision screen
    public void setupManagerDecision(){
        decLabel2.setBounds(25,10,350,20);
        textDecStu.setBounds(25,35,350,20);
        buttonDecSubmit.setBounds(25,110,150,20);
        buttonDecBack.setBounds(225, 110, 150,20);
        managerDecisionFrame.add(decLabel2);
        managerDecisionFrame.add(textDecStu);
        managerDecisionFrame.add(buttonDecSubmit);
        managerDecisionFrame.add(buttonDecBack);
        managerDecisionFrame.setSize(400,250);
        managerDecisionFrame.setLayout(null);
        managerDecisionFrame.setVisible(false);
    }
    //
    public void setupManagerOneBox(){
        managerOneBoxFrame.setSize(400,250);
        managerOneBoxFrame.setLayout(null);
        managerOneBoxFrame.setVisible(false);
        oneBoxLabel.setBounds(25,10,350,20);
        oneBoxText.setBounds(25,35,350,20);
        buttonOneBoxBack.setBounds(225, 60, 150,20);
        buttonDeactivate.setBounds(25,60,150,20);
        buttonViewCourseDetails.setBounds(25,60,150,20);
        buttonViewModuleDetails.setBounds(25,60,150,20);
        managerOneBoxFrame.add(oneBoxLabel);
        managerOneBoxFrame.add(oneBoxText);
        managerOneBoxFrame.add(buttonOneBoxBack);
    }
    //sets up a screen used for assigning modules to courses, updating course details, and assigning modules to lecturers
    public void setupManagerTwoBox(){
        twoBoxLabel1.setBounds(25,10,350,20);
        twoBoxLabel2.setBounds(25,60,350,20);
        textTwoBox1.setBounds(25,35,350,20);
        textTwoBox2.setBounds(25,85,350,20);
        buttonAssignModuleToLecturer.setBounds(25,110,150,20);
        buttonUpdateCourseDetails.setBounds(25,110,150,20);
        buttonAssignModuleToCourse.setBounds(25,110,150,20);
        buttonAddNewRule.setBounds(25,110,150,20);
        buttonEnrolStudent.setBounds(25,110,150,20);
        buttonTwoBoxBack.setBounds(225, 110, 150,20);
        managerTwoBoxFrame.setSize(400,250);
        managerTwoBoxFrame.add(twoBoxLabel1);
        managerTwoBoxFrame.add(twoBoxLabel2);
        managerTwoBoxFrame.add(textTwoBox1);
        managerTwoBoxFrame.add(textTwoBox2);
        managerTwoBoxFrame.add(buttonTwoBoxBack);
        managerTwoBoxFrame.setLayout(null);
        managerTwoBoxFrame.setVisible(false);
    }
    //sets up the manager edit screen
    public void setupManagerEdit(){
        managerEditUserFrame.setSize(650,500);
        managerEditUserFrame.setLayout(null);
        managerEditUserFrame.setVisible(false);
        listOfUsers.setBounds(10,10, 200,400);
        listOfUsers.setText("Testing \n 1 \n 2 \n 3 \n 4");
        listOfUsers.setEditable(false);
        wfLabel.setBounds(220,10,350,20);
        textWorkFlow.setBounds(220,35,350,20);
        typeLabel.setBounds(220,60,350,20);
        comboType.setBounds(220,85,350,20);
        buttonSubmitDeactivation.setBounds(220,110,90,20);
        buttonSubmitActivation.setBounds(220,110,90,20);
        buttonSubmitNewPW.setBounds(220,110,90,20);
        buttonBacktivation.setBounds(330,110,90,20);
        managerEditUserFrame.add(listOfUsers);
        managerEditUserFrame.add(wfLabel);
        managerEditUserFrame.add(textWorkFlow);
        managerEditUserFrame.add(buttonBacktivation);
        managerEditUserFrame.add(comboType);
        managerEditUserFrame.add(typeLabel);
    }

    //sets up the manager create screen
    public void setupManagerCreate(){
        managerCreateManagerFrame.setSize(500,500);
        managerCreateManagerFrame.setLayout(null);
        managerCreateManagerFrame.setVisible(false);
        managerEmailLabel.setBounds(25,10,350,20);
        textManEmail.setBounds(25,35,350,20);
        managerPasswordLabel.setBounds(25,60,350,20);
        textManPW.setBounds(25,85,350,20);
        buttonCreateSubmit.setBounds(25,110,150,20);
        buttonCreateBack.setBounds(225,110,150,20);
        managerCreateManagerFrame.add(textManEmail);
        managerCreateManagerFrame.add(managerEmailLabel);
        managerCreateManagerFrame.add(managerPasswordLabel);
        managerCreateManagerFrame.add(textManPW);
        managerCreateManagerFrame.add(buttonCreateSubmit);
        managerCreateManagerFrame.add(buttonCreateBack);
    }
    //sets up the module info screen
    public void setupModuleFrame(){
        managerModuleFrame.setVisible(false);
        managerModuleFrame.setLayout(null);
        managerModuleFrame.setSize(500,500);
        modCodeLabel.setBounds(25,10,350,20);
        textModCode.setBounds(25,35,350,20);
        modNameLabel.setBounds(25,60,350,20);
        textModName.setBounds(25,85,350,20);
        modCredLabel.setBounds(25,110,350,20);
        textModCreds.setBounds(25,135,350,20);
        modSemsLabel.setBounds(25,160,350,20);
        comboSems.setBounds(25,185,350,20);
        modInfoLabel.setBounds(25,210,350,20);
        textModInfo.setBounds(25,235,350,20);
        modAttLabel.setBounds(25,260,350,20);
        textModAttempts.setBounds(25,285,350,20);
        buttonBackNewModule.setBounds(205,310,170,20);
        buttonSubmitNewModule.setBounds(25,310,170,20);
        managerModuleFrame.add(modCodeLabel);
        managerModuleFrame.add(textModCode);
        managerModuleFrame.add(modNameLabel);
        managerModuleFrame.add(textModName);
        managerModuleFrame.add(modCredLabel);
        managerModuleFrame.add(textModCreds);
        managerModuleFrame.add(modSemsLabel);
        managerModuleFrame.add(comboSems);
        managerModuleFrame.add(modInfoLabel);
        managerModuleFrame.add(textModInfo);
        managerModuleFrame.add(modAttLabel);
        managerModuleFrame.add(textModAttempts);
        managerModuleFrame.add(buttonBackNewModule);
        managerModuleFrame.add(buttonSubmitNewModule);
    }
    //sets up the course screen
    public void setupCourse(){
        managerCourseFrame.setVisible(false);
        managerCourseFrame.setSize(500,500);
        managerCourseFrame.setLayout(null);
        courCodeLabel.setBounds(25,10,350,20);
        textCourseCode.setBounds(25,35,350,20);
        courNameLabel.setBounds(25,60,350,20);
        textCourseName.setBounds(25,85,350,20);
        courDeptLabel.setBounds(25,110,350,20);
        textCourseDept.setBounds(25,135,350,20);
        courGradLabel.setBounds(25,160,350,20);
        comboGrad.setBounds(25,185,350,20);
        buttonBackNewCourse.setBounds(205,210,170,20);
        buttonSubmitNewCourse.setBounds(25,210,170,20);
        managerCourseFrame.add(courCodeLabel);
        managerCourseFrame.add(textCourseCode);
        managerCourseFrame.add(courNameLabel);
        managerCourseFrame.add(textCourseName);
        managerCourseFrame.add(courDeptLabel);
        managerCourseFrame.add(textCourseDept);
        managerCourseFrame.add(courGradLabel);
        managerCourseFrame.add(comboGrad);
        managerCourseFrame.add(buttonBackNewCourse);
        managerCourseFrame.add(buttonSubmitNewCourse);
    }
    //ests up the update course screen
    public void setupUpdateCourse(){
        managerUpdateCourseFrame.setVisible(false);
        managerUpdateCourseFrame.setSize(350,300);
        managerUpdateCourseFrame.setLayout(null);
        updateCourseLabel1.setBounds(25,10,300,20);
        updateCourseLabel2.setBounds(25,60,300,20);
        updateCourseLabel3.setBounds(25,110,300,20);
        textUpdateCode.setBounds(25,35,300,20);
        comboUpdateGrad.setBounds(25,85,300,20);
        textUpdateComp.setBounds(25,135,300,20);
        buttonUpdateCourseDetails.setBounds(25,160,140,20);
        buttonUpdateBack.setBounds(185,160,140,20);
        managerUpdateCourseFrame.add(updateCourseLabel1);
        managerUpdateCourseFrame.add(updateCourseLabel2);
        managerUpdateCourseFrame.add(updateCourseLabel3);
        managerUpdateCourseFrame.add(textUpdateCode);
        managerUpdateCourseFrame.add(textUpdateComp);
        managerUpdateCourseFrame.add(comboUpdateGrad);
        managerUpdateCourseFrame.add(buttonUpdateBack);
        managerUpdateCourseFrame.add(buttonUpdateCourseDetails);
    }

    //TRANSITIONS
    public JFrame getManagerWorkflowFrame(){
        return managerWorkflowFrame;
    }
    public void showManager(){
        managerHomeFrame.setVisible(true);}
    public void hideManager(){
        managerHomeFrame.setVisible(false);}
    public void hideOneBox(){
        managerOneBoxFrame.setVisible(false);}
    public void showWorkflow(){
        managerWorkflowFrame.setVisible(true);
    }
    public void hideWorkflow(){
        managerWorkflowFrame.setVisible(false);
    }
    public void hideModule(){
        managerModuleFrame.setVisible(false);
    }
    public void showModule(){
        managerModuleFrame.setVisible(true);
    }
    public void showCourse(){
        managerCourseFrame.setVisible(true);
    }
    public void hideCourse(){
        managerCourseFrame.setVisible(false);
    }
    public void showUpdate(){ managerUpdateCourseFrame.setVisible(true);}
    public void hideUpdate(){ managerUpdateCourseFrame.setVisible(false);
    clearUpdate();}
    //functions to clear the text from boxes when transitioning
    public void clearUpdate(){
        textUpdateComp.setText("");
        textUpdateCode.setText("");
    }
    public void hideCreate(){
        managerCreateManagerFrame.setVisible(false);
        clearManagerBox();
    }
    public void showCreate(){
        managerCreateManagerFrame.setVisible(true);}

    public void swapToCourseDetails(){
        oneBoxLabel.setText("Please enter a course code to see its details: ");
        managerOneBoxFrame.add(buttonViewCourseDetails);
        clearBoxOne();
        managerOneBoxFrame.remove(buttonViewModuleDetails);
        managerOneBoxFrame.setVisible(true);
    }
    public void swapToModuleDetails(){
        oneBoxLabel.setText("Please enter a module code to see its details: ");
        managerOneBoxFrame.remove(buttonViewCourseDetails);
        clearBoxOne();
        managerOneBoxFrame.add(buttonViewModuleDetails);
        managerOneBoxFrame.setVisible(true);
    }
    public void hideTwoBox(){
        managerTwoBoxFrame.setVisible(false);
    }
    public void swapToAssignModuleToLecturer(){
        managerTwoBoxFrame.setVisible(true);
        managerTwoBoxFrame.add(buttonAssignModuleToLecturer);
        managerTwoBoxFrame.remove(buttonEnrolStudent);
        managerTwoBoxFrame.remove(buttonAssignModuleToCourse);
        managerTwoBoxFrame.remove(buttonUpdateCourseDetails);
        managerTwoBoxFrame.remove(buttonAddNewRule);
        twoBoxLabel1.setText("Enter Module Code: ");
        twoBoxLabel2.setText("Enter the lecturer's Email: ");

    }
    public void swapToAssignModuleToCourse(){
        managerTwoBoxFrame.setVisible(true);
        managerTwoBoxFrame.remove(buttonAssignModuleToLecturer);
        managerTwoBoxFrame.remove(buttonEnrolStudent);
        managerTwoBoxFrame.add(buttonAssignModuleToCourse);
        managerTwoBoxFrame.remove(buttonUpdateCourseDetails);
        managerTwoBoxFrame.remove(buttonAddNewRule);
        twoBoxLabel1.setText("Enter Module Code: ");
        twoBoxLabel2.setText("Enter Course Code: ");
    }
    public void swapToAddNewRule(){
        managerTwoBoxFrame.setVisible(true);
        managerTwoBoxFrame.remove(buttonAssignModuleToLecturer);
        managerTwoBoxFrame.remove(buttonEnrolStudent);
        managerTwoBoxFrame.remove(buttonAssignModuleToCourse);
        managerTwoBoxFrame.remove(buttonUpdateCourseDetails);
        managerTwoBoxFrame.add(buttonAddNewRule);
        twoBoxLabel1.setText("Please enter a module code: ");
        twoBoxLabel2.setText("Please enter the new max number of resit attempts: ");
    }
    public void swapToEnrolStudent(){
        managerTwoBoxFrame.setVisible(true);
        managerTwoBoxFrame.remove(buttonAssignModuleToLecturer);
        managerTwoBoxFrame.add(buttonEnrolStudent);
        managerTwoBoxFrame.remove(buttonAssignModuleToCourse);
        managerTwoBoxFrame.remove(buttonUpdateCourseDetails);
        managerTwoBoxFrame.remove(buttonAddNewRule);
        twoBoxLabel1.setText("Please enter the student email: ");
        twoBoxLabel2.setText("Please enter the course they will be enrolled into: ");
    }
    public void swapToActivate(){
        managerEditUserFrame.setVisible(true);
        managerEditUserFrame.remove(buttonSubmitDeactivation);
        managerEditUserFrame.remove(buttonSubmitNewPW);
        managerEditUserFrame.add(buttonSubmitActivation);
        wfLabel.setText("Enter an email from the list to activate:");
        textWorkFlow.setText("");
    }
    public void swapToDeactivate(){
        managerEditUserFrame.setVisible(true);
        managerEditUserFrame.remove(buttonSubmitActivation);
        managerEditUserFrame.remove(buttonSubmitNewPW);
        managerEditUserFrame.add(buttonSubmitDeactivation);
        wfLabel.setText("Enter an email from the list to deactivate:");
        textWorkFlow.setText("");
    }
    public void swapToReset(){
        managerEditUserFrame.setVisible(true);
        managerEditUserFrame.remove(buttonSubmitActivation);
        managerEditUserFrame.remove(buttonSubmitDeactivation);
        managerEditUserFrame.add(buttonSubmitNewPW);
        wfLabel.setText("Enter an email from the list to reset the password:");
        textWorkFlow.setText("");
    }
    public void hideEditUser(){
        managerEditUserFrame.setVisible(false);
    }
    public void showDec(){
        managerDecisionFrame.setVisible(true);
    }
    public void hideDec(){
        managerDecisionFrame.setVisible(false);
    }

    //BUTTON GETTERS
    public JFrame getManagerEditUserFrame(){
        return managerEditUserFrame;
    }
    public JButton getButtonToDisplayModDets(){
        return buttonDisplayModDets;}
    public JButton getButtonUpdatePassword(){
        return buttonUpdatePassword;
    }
    public JButton getButtonToDisplayCourDets(){
        return buttonDisplayCourDets;}
    public JButton getButtonToIssueDecision(){
        return buttonIssueDecision;}
    public JButton getButtonToAddModule(){
        return buttonAddModule;}
    public JButton getButtonToUpdateCourDets(){
        return buttonUpdateCourDets;}
    public JButton getButtonToIssuePwReset(){
        return buttonIssuePWReset;}
    public JButton getButtonToAddCourse(){
        return buttonAddCourse;}
    public JButton getButtonToModToCour(){
        return buttonModtoCour;}
    public JButton getButtonToModToLect(){
        return buttonModtoLect;
    }

    public JButton getButtonToAddRules(){
        return buttonAddRules;}
    public JButton getButtonToEnrolStud(){
        return buttonEnrolStud;}
    public JButton getButtonToLogout(){
        return buttonLogout;}
    public JButton getButtonOneBoxBack(){
        return buttonOneBoxBack;}
    public JButton getButtonTwoBoxBack(){
        return buttonTwoBoxBack;}
    public JButton getButtonDecBack(){
        return buttonDecBack;}
    public JButton getButtonDecSubmit(){
        return buttonDecSubmit;}
    public JButton getButtonViewModuleDetails(){
        return buttonViewModuleDetails;}
    public JButton getButtonViewCourseDetails(){
        return buttonViewCourseDetails;}
    public JButton getButtonDeactivate(){
        return buttonDeactivate;}
    public JButton getButtonAssignModuleToLecturer(){
        return buttonAssignModuleToLecturer;}
    public JButton getButtonUpdateCourseDetails(){
        return buttonUpdateCourseDetails;}
    public JButton getButtonAssignModuleToCourse(){
        return buttonAssignModuleToCourse;}
    public JButton getButtonAddNewRule(){
        return buttonAddNewRule;}
    public JButton getButtonEnrolStudent(){
        return buttonEnrolStudent;}
    public JButton getButtonWorkFlow(){
        return buttonWorkFlow;}
    public JButton getButtonWorkflowBack(){
        return buttonWorkflowBack;}
    public JButton getButtonActivate(){
        return buttonActivate;}
    public JButton getButtonCreate(){
        return buttonCreate;}
    public JButton getButtonBacktivation(){
        return buttonBacktivation;}
    public JButton getButtonSubmitActivation(){
        return buttonSubmitActivation;}
    public JButton getButtonSubmitDeactivation(){
        return buttonSubmitDeactivation;}
    public JButton getButtonCreateSubmit(){
        return buttonCreateSubmit;}
    public JButton getButtonCreateBack(){
        return buttonCreateBack;}
    public JButton getButtonSubmitNewPW(){
        return buttonSubmitNewPW;
    }
    public JButton getButtonBackNewModule(){
        return buttonBackNewModule;
    }
    public JButton getButtonSubmitNewModule(){
        return buttonSubmitNewModule;
    }
    public JButton getButtonBackNewCourse(){
        return buttonBackNewCourse;
    }
    public JButton getButtonSubmitNewCourse(){
        return buttonSubmitNewCourse;
    }

    public String getAccountType(){
        return types[comboType.getSelectedIndex()];}
    public String getTextWorkFlow(){
        return textWorkFlow.getText();
    }
    public String getDecisionStudent(){
        return textDecStu.getText();}
    public String getOneBoxText(){
        return oneBoxText.getText();}
    public String getTwoBoxText1(){
        return textTwoBox1.getText();}
    public String getTwoBoxText2(){
        return textTwoBox2.getText();}
    public JTextArea getListOfUsers(){
        return listOfUsers;}
    public String getManEmail(){
        return textManEmail.getText();
    }
    public String getManPW(){
        return textManPW.getText();}
    public String getModName(){
        return textModName.getText();}
    public String getModCode(){
        return textModCode.getText();}
    public String getModInfo(){
        return textModInfo.getText();}
    public String getModCreds(){
        return textModCreds.getText();}
    public String getModSems(){
        return semesters[comboSems.getSelectedIndex()];
    }
    public String getModAttempts(){
        return textModAttempts.getText();}
    public void clearMod(){
        textModName.setText("");
        textModAttempts.setText("");
        textModInfo.setText("");
        textModCreds.setText("");
        textModCode.setText("");
        comboSems.setSelectedIndex(0);
    }
    public String getCourName(){
        return textCourseName.getText();}
    public String getCourCode(){
        return  textCourseCode.getText();}
    public String getCourDept(){
        return textCourseDept.getText();}
    public String getCourGrad(){
        return gradType[comboGrad.getSelectedIndex()];}
    public void clearCour(){
        textCourseName.setText("");
        textCourseDept.setText("");
        textCourseCode.setText("");
        comboGrad.setSelectedIndex(0);
        textCourseName.grabFocus();
    }

    public void clearBoxTwo(){
        textTwoBox1.setText("");
        textTwoBox2.setText("");
    }
    public void clearManagerBox(){
        textManEmail.setText("");
        textManPW.setText("");
    }
    public JFrame getManagerCreateManagerFrame(){
        return managerCreateManagerFrame;
    }

    public void clearBoxOne(){
        oneBoxText.setText("");
    }

    public void clearDec(){
        textDecStu.setText("");
    }
    public String getUpdateComp(){
        return textUpdateComp.getText();
    }
    public String getUpdateCode(){
        return textUpdateCode.getText();
    }
    public String getUpdateGrad(){
        return (String) comboUpdateGrad.getSelectedItem();
    }
    public JButton getButtonUpdateBack(){
        return buttonUpdateBack;
    }
}


