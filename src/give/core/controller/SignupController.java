
package give.core.controller;

import give.base.controller.BaseController;
import give.core.model.UserModel;
import give.core.services.UserService;
import give.util.ValidationUtil;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;


public class SignupController extends BaseController implements Initializable {

    @FXML private TextField txtUserName;
    @FXML private TextField txtUserEmail;
    
    @FXML private TextField txtCurPwd;
    @FXML private TextField txtPassword;
    @FXML private TextField txtConfPwd;
    
    String m_strCurLocation = "";
    UserService m_userService = new UserService();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
     public void onSignupBtnPressed(ActionEvent event) throws IOException {
        String strUserName = txtUserName.getText();
        String strUserEmail= txtUserEmail.getText();
        String strPassword = txtPassword.getText();
        String strConfPwd  = txtConfPwd.getText();
        
        // Check User Name
        if(strUserName.length() == 0) {
            this.showAlert("Please input full name.");
            txtUserName.requestFocus();
            return;
        }
        
        if(!ValidationUtil.isVaildName(strUserName)) {
            this.showAlert("Please input full name correctly.");
            txtUserName.requestFocus();
            return;
        }
        
        // Check User Email
        if(strUserEmail.length() == 0) {
           this.showAlert("Please Input Email.");
           txtUserEmail.requestFocus();
           
           return;
        }
        
        if(!ValidationUtil.isValidEmail(strUserEmail)) {
            this.showAlert("Please Input Email correctly.");
           txtUserEmail.requestFocus();
           
           return;
        }
        
        // Check User Password
        if(strPassword.length() == 0) {
           this.showAlert("Please Input Password.");
           txtUserEmail.requestFocus();
           
           return;
        }
       
        if(strPassword.length() < 6) {
           this.showAlert("The length of password is 6 at least.");
           txtUserEmail.requestFocus();
           
           return;
        }
        
        if(strPassword.compareTo(strConfPwd) != 0) {
           this.showAlert("Please confirm your password.");
           txtConfPwd.requestFocus();
           
           return;
        }
        
        // Check duplication
        if(m_userService.findUserByEmail(strUserEmail) != null) {
            this.showAlert("The email is already used.\nPlease input different Email.");
            txtUserEmail.requestFocus();
           
            return;
        }
        
        // Create UserModel
        UserModel newUser = new UserModel(strUserName, strUserEmail, strPassword);
        

        if(m_userService.registerUser(newUser)) {
            this.showPrompt("New user registered successfuly.\nPlease login to check that.");
                
            onCancelBtnPressed(event);
        }
        else {
            this.showAlert("Failed to register.\nPlease try again.");
            txtUserEmail.requestFocus();
        }
    }
     
      public void onCancelBtnPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(BaseController.getResourceURL(m_strCurLocation.contains("") ? "view/fxml_login.fxml" : "view/fxml_home.fxml"));
        Scene homeScene = new Scene(loader.load());
        homeScene.getStylesheets().add("give/view/login.css");
        
        // Get stage and transfer to home scene
        this.showWindow(event, homeScene);
    }
    
}
