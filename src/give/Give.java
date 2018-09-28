/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package give;

import give.base.controller.BaseController;
import give.base.db.DBConfig;
import give.core.db.GiveDBHelper;
import give.core.model.UserModel;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author noahwebb
 */
public class Give extends Application {
    
    public double xOffset, yOffset;
    public static GiveDBHelper G_DBHelper = null;
    public static String RESOURCE_PATH = null;
    public static UserModel LOGIN_USER = null;
    public static Stage login;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        login = stage;
        RESOURCE_PATH = getClass().getResource("").toString();
        
        //Parent root = FXMLLoader.load(BaseController.getResourceURL("view/fxml_account_stats.fxml"));
        
        
        
        Parent root = FXMLLoader.load(BaseController.getResourceURL("view/fxml_login.fxml"));
        
        Scene scene = new Scene(root);
        stage.resizableProperty().setValue(Boolean.FALSE);
        scene.getStylesheets().add("give/view/login.css");
        
        //scene.getStylesheets().add("give/view/account.css");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Give");
        stage.setScene(scene);
        stage.centerOnScreen();
        //System.out.println(BaseController.getResourceURL("view/images/taskbar-icon.png").toExternalForm());
        stage.getIcons().add(new Image(BaseController.getResourceURL("view/images/taskbar-icon.png").toExternalForm()));
        stage.show();
       
             root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        
        //move around here
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        G_DBHelper = new GiveDBHelper(new DBConfig());
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
              G_DBHelper.closeDB();
            }
        });
                
        launch(args);
    }
    
    
    
    
}
