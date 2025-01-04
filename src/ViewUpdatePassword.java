import javax.swing.*;
import java.awt.*;

public class ViewUpdatePassword {
    private JFrame updatePasswordFrame = new JFrame();
    private Label oldPassLabel = new Label("Please enter your old password");
    private Label newPassLabel = new Label("Please enter your new password");
    private Label newPassConfirmLabel = new Label("Please confirm your new password");
    private JButton updateBack = new JButton("Back"); //Button to back from update password screen
    private JPasswordField oldPassword = new JPasswordField(); //Button for user to confirm their current password
    private JPasswordField newPassword = new JPasswordField(); //Button for user to enter their new password
    private JPasswordField newPassConfirm = new JPasswordField(); // Button for user to confirm their new password
    private JButton passwordEnter = new JButton("Enter"); // Button to submit password change
//set up function for update password screen
    public void setupUpdatePassword(){
        updatePasswordFrame.setSize(350,300);
        oldPassLabel.setBounds(20,30,300,20);
        oldPassword.setBounds(20,55,300,20);
        newPassLabel.setBounds(20,80,300,20);
        newPassword.setBounds(20,105,300,20);
        newPassConfirmLabel.setBounds(20,130,300,20);
        newPassConfirm.setBounds(20,155,300,20);
        updateBack.setBounds(20,220,100,20);
        passwordEnter.setBounds(220,220,100,20);
        updatePasswordFrame.add(oldPassword);
        updatePasswordFrame.add(newPassword);
        updatePasswordFrame.add(newPassConfirm);
        updatePasswordFrame.add(passwordEnter);
        updatePasswordFrame.add(updateBack);
        updatePasswordFrame.add(oldPassLabel);
        updatePasswordFrame.add(newPassLabel);
        updatePasswordFrame.add(newPassConfirmLabel);
        updatePasswordFrame.add(passwordEnter);
        updatePasswordFrame.add(updateBack);
        updatePasswordFrame.setLayout(null);
        updatePasswordFrame.setVisible(false);
    }
    public JButton getUpdateBack(){
        return updateBack;
    }
    public JButton getPasswordEnter(){
        return passwordEnter;
    }
    public void showUpdatePassword(){
        updatePasswordFrame.setVisible(true);
    }
    public void hideUpdatePassword(){
        updatePasswordFrame.setVisible(false);
    }
    public String getOldPassword(){
        return oldPassword.getText();
    }
    public String getNewPassword(){
        return newPassword.getText();
    }
    public String getNewPassConfirm(){
        return newPassConfirm.getText();
    }
    public void clearUpdatePassText(){
        oldPassword.setText("");
        newPassword.setText("");
        newPassConfirm.setText("");
    }
    public JFrame getUpdatePasswordFrame(){
        return updatePasswordFrame;
    }
}
