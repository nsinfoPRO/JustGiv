/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package give.base.controller;

import give.Give;
import static give.Give.RESOURCE_PATH;
import give.core.controller.AdsController;
import give.core.controller.GiveController;
import give.core.db.GiveDBHelper;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;


/**
 *
 * @author Administrator
 */
public class BaseController {

    protected Parent mainPane;
    double yOffset, xOffset;
    public static Connection con = Give.G_DBHelper.getDBConn();
    public static Statement stmt;
    public static ResultSet rset;
    @FXML Hyperlink linkAccount;
    @FXML AnchorPane anchorpaneAccount;
    public BaseController() {
        super();
    }

    
    public void onLinkLogoutPressed(ActionEvent event) throws IOException {
        if (this.showConfirm("Would you like to logout now?")) {
            Give.LOGIN_USER = null;

       
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(BaseController.getResourceURL("view/fxml_login.fxml"));
            Scene homeScene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(homeScene);
            stage.initStyle(StageStyle.UNDECORATED);
            ((Node) (event.getSource())).getScene().getWindow().hide();
            // Get stage and transfer to home scene
            stage.getIcons().add(new javafx.scene.image.Image(BaseController.getResourceURL("view/images/taskbar-icon.png").toExternalForm()));

            stage.show();
        }
    }

    public static String getAppPath() {
        return System.getProperty("user.dir") + "/";
    }

    /**
     *
     * @param resFile
     * @return
     */
    public static URL getResourceURL(String resFile) {
        URL newURL = null;
        try {
            newURL = new URL(RESOURCE_PATH + resFile);
        } catch (Exception e) {

        }

        return newURL;
    }

    public static Stage getWindowBy(ActionEvent event, boolean bEnableResize) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.resizableProperty().setValue(bEnableResize);

        return window;
    }

    public void showWindow(ActionEvent event, Scene scene) {
        // Get stage and transfer to home scene
        Stage window = BaseController.getWindowBy(event, false);
        window.getIcons().add(new javafx.scene.image.Image(BaseController.getResourceURL("view/images/taskbar-icon.png").toExternalForm()));

        window.setScene(scene);
        window.show();
    }

    public boolean showAlert(String strParamMessage) {
        Alert alert = new Alert(AlertType.WARNING, strParamMessage);
        Optional<ButtonType> result = alert.showAndWait();

        return (result.isPresent() && result.get() == ButtonType.OK);
    }

    public boolean showPrompt(String strParamMessage) {
        Alert alert = new Alert(AlertType.INFORMATION, strParamMessage);
        Optional<ButtonType> result = alert.showAndWait();

        return (result.isPresent() && result.get() == ButtonType.OK);
    }

    public boolean showConfirm(String strParamMessage) {
        Alert alert = new Alert(AlertType.CONFIRMATION, strParamMessage);
        Optional<ButtonType> result = alert.showAndWait();

        return (result.isPresent() && result.get() == ButtonType.OK);
    }

    //-----------------------------------------------------------------------------------
    public void closeButtonPressed() {

        Platform.exit();

    }

    public void minimizeButtonPressed(ActionEvent e) {

        Node source = (Node) e.getSource();
        javafx.stage.Window theStage = source.getScene().getWindow();
        Stage stage = (Stage) theStage;
        stage.setIconified(true);

    }

    public void accountButtonPressed(ActionEvent event) throws IOException {
        
         if(linkAccount!=null) {

            final ContextMenu contextMenu = new ContextMenu();
            contextMenu.getScene().setCursor(Cursor.HAND);
            /*
            MenuItem stats = new MenuItem("STATS        ");
            stats.setStyle("-fx-font-weight:normal;");
//            stats.setStyle("--fx-font-family: Slim Joe;");
            MenuItem settings = new MenuItem("SETTINGS");
            settings.setStyle("-fx-font-weight:normal;");
//            settings.setStyle("--fx-font-family: Slim Joe;");*/
        SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();
      
        final ImageView selectedImage = new ImageView();
        selectedImage.setFitHeight(2);
        selectedImage.setFitWidth(2);
        selectedImage.setStyle("-fx-background-color:transparent; -fx-padding: 0, 0, 0 0;"); //red         

        BaseController.getResourceURL("images/transparent_line.png");
        javafx.scene.image.Image image1 = new javafx.scene.image.Image(BaseController.getResourceURL("view/images/transparent_line.png").toExternalForm(),1,1,false,false);
        selectedImage.setImage(image1);

        separatorMenuItem.setContent(selectedImage);
     
        Label statsLabel = new Label("STATS");
        Label settingLabel = new Label("SETTINGS");
        CustomMenuItem stats = new CustomMenuItem(statsLabel);
        CustomMenuItem settings = new CustomMenuItem(settingLabel);
        contextMenu.setStyle("-fx-background-color:transparent; -fx-padding: 0, 0, 0 0; "); //red         
        stats.setId("account-menubutton");
        settings.setId("account-menubutton");
        statsLabel.setId("account-menubutton");
        settingLabel.setId("account-menubutton");
        
        
        contextMenu.getItems().addAll(stats, separatorMenuItem,settings);
            
            stats.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
//                        System.out.println("Status...");
                        Parent accountParent = FXMLLoader.load(BaseController.getResourceURL("view/fxml_account_stats.fxml"));
                        Scene accountScene = new Scene(accountParent);
                        BaseController.this.showWindow(event, accountScene);
                    } catch (IOException ex) {
                        Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            settings.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
//                        System.out.println("Setting...");
                        Parent accountParent = FXMLLoader.load(BaseController.getResourceURL("view/fxml_account_setting.fxml"));
                        Scene accountScene = new Scene(accountParent);
                        BaseController.this.showWindow(event, accountScene);
                    } catch (IOException ex) {
                        Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            double x = linkAccount.localToScreen(linkAccount.getBoundsInLocal()).getMinX();
            double y = linkAccount.localToScreen(linkAccount.getBoundsInLocal()).getMinY() + linkAccount.getBoundsInLocal().getHeight();
            contextMenu.setWidth(200);
            contextMenu.show(linkAccount, x, y);
        }
    }

    public void charityButtonPressed(ActionEvent event) throws IOException {
        Parent accountParent = FXMLLoader.load(BaseController.getResourceURL("view/fxml_home.fxml"));
        Scene accountScene = new Scene(accountParent);

        // Get stage and transfer to give scene
        this.showWindow(event, accountScene);
    }
    
    public void contactButtonPressed(ActionEvent event) throws IOException {
        Parent contactParent = FXMLLoader.load(BaseController.getResourceURL("view/fxml_contact-us.fxml"));
        Scene contactScene = new Scene(contactParent);

        // Get stage and transfer to give scene
        this.showWindow(event, contactScene);
    }

     public void giveButtonPressedAds(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getResourceURL("view/fxml_ads.fxml"));

        Parent giveParent = loader.load();
        Scene giveScene = new Scene(giveParent);
        // Get stage and transfer to give scene
        Stage window = BaseController.getWindowBy(event, false);
     

        window.hide();
        AdsController controller = loader.getController();
        controller.mainStage = window;

        Stage newWindow = new Stage();
        newWindow.setAlwaysOnTop(true);
        newWindow.setScene(giveScene);
        
        newWindow.setMaxHeight(250);
        newWindow.setMaxWidth(300);
        int width = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
        newWindow.setX(width-350);
        newWindow.setY(50);
        
        newWindow.getIcons().add(new javafx.scene.image.Image(BaseController.getResourceURL("view/images/taskbar-icon.png").toExternalForm()));

        giveParent.setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                controller.falseClick=false;
                controller.setXPos(event.getSceneX());
                controller.setYPos(event.getSceneY());
            }
        });
        
        //move around here
            giveParent.setOnMouseDragged(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
//                newWindow.setX(event.getScreenX() - controller.getXPos());
//                newWindow.setY(event.getScreenY() - controller.getYPos());
//                controller.falseClick=true;
                
                 int screenWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
                int screenHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
                
                double newPositionX = event.getScreenX() - controller.getXPos();
                double newPositionY = event.getScreenY() - controller.getYPos();
                
                if(newPositionX+300>screenWidth){
                    newPositionX = screenWidth-300;
                }
                
                if(newPositionX <= 0){
                    newPositionX = 0;
                }
                
                
                if(newPositionY+250>screenHeight){
                     newPositionY = screenHeight-250;
                }
                
                if(newPositionY <= 0){
                     newPositionY = 0;
                }
                
                newWindow.setX(newPositionX);
                newWindow.setY(newPositionY);
                controller.falseClick=true;
            }
        });
            
        //newWindow.resizableProperty().setValue(Boolean.FALSE);
        //newWindow.initStyle(StageStyle.UTILITY);
        newWindow.initStyle(StageStyle.UNDECORATED);
        newWindow.show();
        addToSystemTray(window);
    }
    public void giveButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getResourceURL("view/fxml_give.fxml"));

        Parent giveParent = loader.load();
        Scene giveScene = new Scene(giveParent);
        // Get stage and transfer to give scene
        Stage window = BaseController.getWindowBy(event, false);
        window.hide();
        GiveController controller = loader.getController();
        controller.mainStage = window;

        Stage newWindow = new Stage();
        newWindow.setAlwaysOnTop(true);
        newWindow.setScene(giveScene);

        newWindow.show();
        addToSystemTray(window);
    }

    private void addToSystemTray(Stage mainStage) {
        if (!SystemTray.isSupported()) {
            System.err.println("System tray is not supported!");
            return;
        }
        SystemTray tray = SystemTray.getSystemTray();

        Image image = Toolkit.getDefaultToolkit().createImage("tray_icon.png");
        TrayIcon trayIcon = new TrayIcon(image);
        trayIcon.setImageAutoSize(true);
        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                for (TrayIcon icon : tray.getTrayIcons()) {
                    tray.remove(icon);
                }
                Platform.runLater(mainStage::show);
            }
        });

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.err.println("Cannot add system tray!");
            e.printStackTrace();
        }
    }

}
