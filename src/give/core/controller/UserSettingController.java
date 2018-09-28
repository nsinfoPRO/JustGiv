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
import java.awt.Desktop;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author Administrator
 */
public class UserSettingController extends BaseController implements Initializable {
    
    @FXML public Pane menuBar;
    @FXML private Label lblUserName;
    @FXML private VBox accountSettingsPane;
    @FXML private RadioButton boxEmailChange, boxPasswordChange;
    
    @FXML private TextField txtUserName;
    @FXML private TextField txtCurPwd;
    @FXML private TextField txtPassword;
    @FXML private TextField txtConfPwd;
    @FXML public double xOffset, yOffset;
    @FXML
    public Stage stage;

    String m_strCurLocation = "";
    UserService m_userService = new UserService();
    @FXML
    public void onFacebookPressed(ActionEvent event) throws IOException, URISyntaxException {
        Desktop.getDesktop().browse(new URI("www.facebook.com"));
    }
    @FXML
    public void onTwitterPressed(ActionEvent event) throws IOException, URISyntaxException {
        Desktop.getDesktop().browse(new URI("www.twitter.com"));
    }
    
    public void initialize(URL location, ResourceBundle resources) {
        
        this.m_strCurLocation = location.toString();
        Integer allTime = 0, education = 0, animals = 0, enivironment = 0, health = 0, refuges = 0, hunger = 0, water = 0, children = 0, poverty = 0, injustice = 0;
        try {
            String queryString = "SELECT * FROM user WHERE email=\"" + UserModel.getUserEmail() + "\"";
            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery(queryString);
            while (rset.next()) {

                education = rset.getInt(10);
                animals = rset.getInt(11);
                enivironment = rset.getInt(12);
                health = rset.getInt(13);
                refuges = rset.getInt(14);
                hunger = rset.getInt(15);
                water = rset.getInt(16);
                children = rset.getInt(17);
                poverty = rset.getInt(18);
                injustice = rset.getInt(19);

            }

        } catch (Exception e){
            System.out.println("Exception" + e.getMessage());
        }
        allTime = education + animals + enivironment + health + refuges + hunger + water + children + poverty + injustice;
        // If Edit User
        if(this.m_strCurLocation.contains("fxml_account")) {
            lblUserName.setText(Give.LOGIN_USER.getUserName());
        }
    }
    
    public void menuBackPressed(ActionEvent event) throws IOException {
    }
    
    public void statsPressed(){
        accountSettingsPane.setVisible(false);
    }
    
    public void settingsPressed(){
        accountSettingsPane.setVisible(true);
    }
 
    public void onSignupBtnPressed(ActionEvent event) throws IOException {
        String strUserName = txtUserName.getText();
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
        
        if(strPassword.compareTo(strConfPwd) != 0) {
           this.showAlert("Please confirm your password.");
           txtConfPwd.requestFocus();
           return;
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
    
    public void menuBarPressed(MouseEvent event){
        stage = (Stage) menuBar.getScene().getWindow();
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }
    
    public void menuBarDragged(MouseEvent event){
        stage = (Stage)menuBar.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }
}
