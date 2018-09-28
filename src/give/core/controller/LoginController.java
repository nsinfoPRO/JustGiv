/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package give.core.controller;

import give.Give;
import give.base.controller.BaseController;
import give.core.model.UserModel;
import give.core.services.UserService;
import give.util.ValidationUtil;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
 
public class LoginController extends BaseController {
    @FXML private TextField txtUserEmail;
    @FXML private TextField txtPassword;
    @FXML private Hyperlink createAccount;
    
    /**
     * 
     * @param event
     * @throws IOException 
     */
    public void loginButtonPressed(ActionEvent event) throws IOException {
        
        String strUserEmail= txtUserEmail.getText();
        String strPassword = txtPassword.getText();
        
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
        
        UserModel user = new UserModel();
        user.setUserEmail(strUserEmail);
        user.setPassword(strPassword);
        
        
        
        // Authenticate User
        UserService userService = new UserService();
        UserModel findUser = userService.authenticateUser(strUserEmail, strPassword);
        if(findUser == null) {
            this.showAlert("Unregistered Email or Password is wrong.");
            txtUserEmail.requestFocus();
           
            return;
        }
        
        // Set current User to Global Variable
        Give.LOGIN_USER = findUser;
        
        // Move to Home Screen
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(BaseController.getResourceURL(findUser.getUserType() == UserModel.USER_TYPE_ADMIN ? "view/fxml_admin_home.fxml" : "view/fxml_home.fxml"));
        Scene homeScene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setScene(homeScene);
        stage.initStyle(StageStyle.UNDECORATED);
        ((Node)(event.getSource())).getScene().getWindow().hide();
        // Get stage and transfer to home scene
        stage.show();
    }
    
    public void onCreateAccountPressed(ActionEvent event) throws IOException {
        
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(BaseController.getResourceURL("view/fxml_signup.fxml"));        
        Scene homeScene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setScene(homeScene);
        stage.initStyle(StageStyle.UNDECORATED);
        ((Node)(event.getSource())).getScene().getWindow().hide();
        
        // Get stage and transfer to home scene
        
        stage.show();
    }
    
    public void onForgotPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(BaseController.getResourceURL("view/fxml_forgot.fxml"));        
        Scene homeScene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setScene(homeScene);
        stage.initStyle(StageStyle.UNDECORATED);
        ((Node)(event.getSource())).getScene().getWindow().hide();
        // Get stage and transfer to home scene
        stage.show();
        
    }
    
    
         
}
