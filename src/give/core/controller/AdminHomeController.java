package give.core.controller;

import give.Give;
import give.base.controller.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdminHomeController extends BaseController implements Initializable {
    @FXML
    private Label lblUserName;
    @FXML public double xOffset, yOffset;
    public Stage stage;
    @FXML AnchorPane mainPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblUserName.setText(Give.LOGIN_USER.getUserName());
    }
    
    public void menuBackButtonPressed(ActionEvent event) throws IOException {
        
    }

    public void accountButtonPressed(ActionEvent event) throws IOException {
        Parent accountParent = FXMLLoader.load(BaseController.getResourceURL("view/fxml_account_stats.fxml"));
        Scene accountScene = new Scene(accountParent);

        // Get stage and transfer to give scene
        this.showWindow(event, accountScene);
    }

    public void charityButtonPressed(ActionEvent event) throws IOException {
        Parent charityParent = FXMLLoader.load(BaseController.getResourceURL("view/fxml_charity_list.fxml"));
        Scene charityScene = new Scene(charityParent);

        // Get stage and transfer to charity scene
        this.showWindow(event, charityScene);
    }
    
    public void mainPanePressed(MouseEvent event){
    
        stage = (Stage)mainPane.getScene().getWindow();
         xOffset = event.getSceneX();
         yOffset = event.getSceneY();
        
    }
    
    
    public void mainPaneDragged(MouseEvent event){
        stage = (Stage)mainPane.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
        
        
    }
    
}
