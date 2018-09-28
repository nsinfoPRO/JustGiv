/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package give.core.controller;

import give.Give;
import give.base.controller.BaseController;
import give.core.model.UserModel;
import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.awt.Paint;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.*;
import javafx.collections.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * FXML Controller class
 *
 * @author noahwebb
 */
public class ContactController extends BaseController implements Initializable {
    public ScrollPane scrollPane;
    @FXML
    public Pane menuBar;
    @FXML
    public Stage stage;
    @FXML public double xOffset, yOffset;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
      
    }
    @FXML
    private void onClose(ActionEvent event) {
        
    }
 
    public void menuBackPressed(ActionEvent event) throws IOException {
    }
    
   
    
    public void menuBarPressed(MouseEvent event){
    
        stage = (Stage)menuBar.getScene().getWindow();
         xOffset = event.getSceneX();
         yOffset = event.getSceneY();
        
    }
//    
    public void menuBarDragged(MouseEvent event){
        stage = (Stage)menuBar.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }
}
