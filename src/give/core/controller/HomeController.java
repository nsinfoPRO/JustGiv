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
public class HomeController extends BaseController implements Initializable {

    @FXML
    private Label lblUserName;
    private String komanda;
    public ScrollPane scrollPane;
    public FlowPane charitiesMajorCategoryList;
    public FlowPane charitiesMinorCategoryList;
    
    public VBox charityCheckList;

    public Pane firstField, secondField, thirdField, fourthField;
    public boolean first = false, second = false, third = false, fourth = false;
    @FXML
    public Pane menuBar;
    @FXML
    public double xOffset, yOffset;
    @FXML
    public Stage stage;

    @FXML
    public Button btnClose1;
    @FXML
    public Button btnClose2;
    @FXML
    public Button btnClose3;
    @FXML
    public Button btnClose4;
            
    private ObservableSet<CheckBox> selectedCheckBoxes = FXCollections.observableSet();
    private ObservableSet<CheckBox> unselectedCheckBoxes = FXCollections.observableSet();

    private IntegerBinding numCheckBoxesSelected = Bindings.size(selectedCheckBoxes);
    private final int maxNumSelected = 4;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        lblUserName.setText(Give.LOGIN_USER.getUserName());

        //submitButton.setDisable(true);
        numCheckBoxesSelected.addListener((obs, oldSelectedCount, newSelectedCount) -> {
            if (newSelectedCount.intValue() >= maxNumSelected) {
                unselectedCheckBoxes.forEach(cb -> cb.setDisable(true));
            } else {
                unselectedCheckBoxes.forEach(cb -> cb.setDisable(false));
            }
        });
        
      
    }
    @FXML
    private void onClose(ActionEvent event) {
        
        if(event.getSource()==btnClose1) {
            for(int i = 0; i < 4; i++) {
                String checkText = ((CheckBox)((AnchorPane)(charityCheckList.getChildren().get(i + 1))).getChildren().get(0)).getText();
                if(checkText.equals(((Button)firstField.getChildren().get(0)).getText())) {
                    HomeController.this.selectImageProc(false, ((Button)firstField.getChildren().get(0)).getText());
                    ((CheckBox)((AnchorPane)(charityCheckList.getChildren().get(i + 1))).getChildren().get(0)).setSelected(false);
                    break;
                }
            }
        }
        else if(event.getSource()==btnClose2) {
            for(int i = 0; i < 4; i++) {
                String checkText = ((CheckBox)((AnchorPane)(charityCheckList.getChildren().get(i + 1))).getChildren().get(0)).getText();
                if(checkText.equals(((Button)secondField.getChildren().get(0)).getText())) {
                    HomeController.this.selectImageProc(false, ((Button)secondField.getChildren().get(0)).getText());
                    ((CheckBox)((AnchorPane)(charityCheckList.getChildren().get(i + 1))).getChildren().get(0)).setSelected(false);
                    break;
                }
            }
        }
        else if(event.getSource()==btnClose3) {
            for(int i = 0; i < 4; i++) {
                String checkText = ((CheckBox)((AnchorPane)(charityCheckList.getChildren().get(i + 1))).getChildren().get(0)).getText();
                if(checkText.equals(((Button)thirdField.getChildren().get(0)).getText())) {
                    HomeController.this.selectImageProc(false, ((Button)thirdField.getChildren().get(0)).getText());
                    ((CheckBox)((AnchorPane)(charityCheckList.getChildren().get(i + 1))).getChildren().get(0)).setSelected(false);
                    break;
                }
            }
        }
        else if(event.getSource()==btnClose4) {
             for(int i = 0; i < 4; i++) {
                String checkText = ((CheckBox)((AnchorPane)(charityCheckList.getChildren().get(i + 1))).getChildren().get(0)).getText();
                if(checkText.equals(((Button)fourthField.getChildren().get(0)).getText())) {
                    HomeController.this.selectImageProc(false, ((Button)fourthField.getChildren().get(0)).getText());
                    ((CheckBox)((AnchorPane)(charityCheckList.getChildren().get(i + 1))).getChildren().get(0)).setSelected(false);
                    break;
                }
            }
        }
    }
   
    public void createField(String nm, String website, String desc) {

        AnchorPane checkPane = new AnchorPane();
        checkPane.setId("checkPane");

        CheckBox check = new CheckBox();
        check.setId("checkBox");
        check.setLayoutX(22);
        check.setLayoutY(16);
        check.setText(nm);
        check.setOnAction(checkBoxEvent);

        Label name = new Label(nm);
        name.setId("nameLabel");
        name.setLayoutX(60);
        name.setLayoutY(10);

        Label seperator = new Label("|");//|
        seperator.setId("seperatorLabel");

        seperator.setLayoutY(10);

        Hyperlink link = new Hyperlink();
        link.setId("websiteUrl");
        link.setText(website);
        link.setLayoutX(20);
        link.setLayoutY(7);

        link.setOnAction((ActionEvent event) -> {
            Hyperlink h = (Hyperlink) event.getTarget();
            try {
                Desktop.getDesktop().browse(new URI(h.getText()));
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            event.consume();
        });
        
        Label description = new Label(desc);
        description.setWrapText(true);
         
        description.setId("descriptionLabel");
        
        description.setLayoutX(60);
        description.setLayoutY(35);

        checkPane.getChildren().addAll(check, name, seperator, link, description);
        charityCheckList.getChildren().add(checkPane);
        configureCheckBox(check);

        name.applyCss();
        double dWidthName = name.prefWidth(-1);
        seperator.setLayoutX(60 + 10 + dWidthName);

        seperator.applyCss();
        double dWidthSeperator = seperator.prefWidth(-1);
        link.setLayoutX(60 + 10 + dWidthName + dWidthSeperator);

        //	       AnchorPane checkPane = new AnchorPane();
        //	        checkPane.setId("checkPane");
        //	        CheckBox check = new CheckBox();
        //	        check.setId("checkBox");
        //	        check.setLayoutX(20);
        //	        check.setLayoutY(30);
        //	        check.setText(nm);
        //	        check.setOnAction(checkBoxEvent);
        //
        //	        Label name = new Label(nm);
        //	        name.setId("nameLabel");
        //	        name.setLayoutX(60);
        //	        name.setLayoutY(10);
        //
        //	        Label description = new Label(desc);
        //	        description.setId("descriptionLabel");
        //	        description.setLayoutX(60);
        //	        description.setLayoutY(35);
        //
        //	        checkPane.getChildren().addAll(check, description, name);
        //	        charityCheckList.getChildren().add(checkPane);
        //	        configureCheckBox(check);
    }

    private void configureCheckBox(CheckBox checkBox) {

        if (checkBox.isSelected()) {
            selectedCheckBoxes.add(checkBox);
        } else {
            unselectedCheckBoxes.add(checkBox);
        }

        checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                unselectedCheckBoxes.remove(checkBox);
                selectedCheckBoxes.add(checkBox);
            } else {
                selectedCheckBoxes.remove(checkBox);
                unselectedCheckBoxes.add(checkBox);
            }
        });
    }

    public void menuBackPressed(ActionEvent event) throws SQLException {
    }

    public void btnBackCatPreesed(ActionEvent event) throws SQLException {
        scrollPane.setVisible(false);
        charitiesMajorCategoryList.setVisible(true);
    }

    public void charPressed(ActionEvent event) throws SQLException {
        charitiesMajorCategoryList.setVisible(false);
        scrollPane.setVisible(true);
        scrollPane.setVvalue(0);

        charityCheckList.setVisible(true);
        Button button = (Button) event.getSource();
        String txtButton = button.getText();
        int tagIndex = txtButton.lastIndexOf("$");

        String[] source = new String[2];

        if (tagIndex != -1) {
            source[0] = txtButton.substring(0, tagIndex);
            source[1] = txtButton.substring(tagIndex + 1, txtButton.length());
        } else {
            source[0] = txtButton;
            source[1] = txtButton;
        }

        //String[] source = button.getText().split("$", 3);
        charityCheckList.getChildren().clear();
        Label charityName = new Label(source[1]);
//        charityName.setPrefWidth(900);
//        charityName.setMaxWidth(900);
        
        String categoryLogoImageURL = getAppPath() + "\\src\\give\\view\\images\\" + charityName.getText().toLowerCase() + "-pic.png";
        Image image = new Image("file:///" + categoryLogoImageURL);
        image = new Image("/give/view/images/" + charityName.getText().toLowerCase() +"-pic.png");
        //image = new Image("https://www.tutorialspoint.com/javafx/images/loading_image.jpg", true);
        ImageView listLogo = new ImageView(image);
        listLogo.setFitWidth(50);
        listLogo.setFitHeight(50);
        Button b = new Button();
        b.setId("charityCategoryLogoImg");
        b.setGraphic(listLogo);

        Pane nameLogoBox = new Pane();
        nameLogoBox.minWidth(900);
        nameLogoBox.prefWidth(900);
        nameLogoBox.minHeight(70);
        
//        nameLogoBox.setAlignment(Pos.CENTER_LEFT);
//        nameLogoBox.setSpacing(25);
        
        nameLogoBox.setId("nameLogoBox");
        nameLogoBox.getChildren().add(b);
        
        charityName.setLayoutX(80);
        charityName.setLayoutY(10);
        charityName.setId("charityName"); // charity name
        nameLogoBox.getChildren().add(charityName);
        
        charityCheckList.setStyle("-fx-padding: 20 30 20 30;\n"
                + "-fx-background-insets: 20 30 20 30;\n"
                + "-fx-border-insets: 20 30 20 30;"
                );
        charityCheckList.getChildren().add(nameLogoBox);
        
        //nameLogoBox.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        
        String queryString = "SELECT * FROM charity WHERE category=" + "\"" + source[0] + "\"";
        Statement stmt = con.createStatement();
        ResultSet rset = stmt.executeQuery(queryString);
        try {
            while (rset.next()) {
                String name = rset.getString("name");
                String website = rset.getString("website");
                String description = rset.getString("bio");
                createField(name, website, description);
            }

        } catch (Exception ex) {
            System.out.println("Exception" + ex.getMessage());
        }

    }

    EventHandler checkBoxEvent = (EventHandler<ActionEvent>) (ActionEvent event) -> {
        CheckBox box = (CheckBox) event.getSource();
        selectImageProc(box.isSelected(), box.getText());
    };
    
    private void selectImageProc(boolean isSelected, String text) {
        
        String strImgPath = "";
        Button btnClose1 = (Button) firstField.getChildren().get(1);
        Button btnClose2 = (Button) secondField.getChildren().get(1);
        Button btnClose3 = (Button) thirdField.getChildren().get(1);
        Button btnClose4 = (Button) fourthField.getChildren().get(1);
        
        btnClose1.setStyle("-fx-text-fill: #ff0000;");
        btnClose2.setStyle("-fx-text-fill: #ff0000;");
        btnClose3.setStyle("-fx-text-fill: #ff0000;");
        btnClose4.setStyle("-fx-text-fill: #ff0000;");
        
        if (isSelected) {
            
                
            try {
                String queryString = "SELECT logo FROM charity where name=\"" + text + "\";";
                Statement stmt = con.createStatement();
                ResultSet rset = stmt.executeQuery(queryString);

                while (rset.next()) {
                    strImgPath = getAppPath() + "images\\charity\\" + rset.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            Image image = new Image("file:///" + strImgPath);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(180);
            imageView.setFitHeight(180);
            imageView.setId("charityPic");

            if (first == false) {

                Button btn = (Button) firstField.getChildren().get(0);
                
                btn.setGraphic(imageView);
                btnClose1.setVisible(true);
                btn.setText(text);
                btn.setTextFill(Color.TRANSPARENT);
                

                first = true;

            } else if (second == false) {

                Button btn = (Button) secondField.getChildren().get(0);
                btn.setGraphic(imageView);
                btnClose2.setVisible(true);
                btn.setText(text);
                btn.setTextFill(Color.TRANSPARENT);
                second = true;

            } else if (third == false) {
                Button btn = (Button) thirdField.getChildren().get(0);
                btn.setGraphic(imageView);
                btnClose3.setVisible(true);
                btn.setText(text);
                btn.setTextFill(Color.TRANSPARENT);
                third = true;

            } else if (fourth == false) {
                Button btn = (Button) fourthField.getChildren().get(0);
                btn.setGraphic(imageView);
                btnClose4.setVisible(true);
                btn.setText(text);
                fourth = true;
            }

        } else {
            
            
            
            if (text.equals(((Button) firstField.getChildren().get(0)).getText())) {
                first = false;
                ((Button) firstField.getChildren().get(0)).setGraphic(null);
                btnClose1.setVisible(false);

                ((Button) firstField.getChildren().get(0)).setText("");
                ((Button) firstField.getChildren().get(0)).setTextFill(Color.rgb(255, 255, 255, 0.6));
                //   firstField.setWrapText(true);
                //  firstField.setStyle("-fx-font-size:18; -fx-text-fill: rgba(255, 255, 255, 0.6);");
            } else if (text.equals(((Button) secondField.getChildren().get(0)).getText())) {
                second = false;
                ((Button) secondField.getChildren().get(0)).setGraphic(null);
                btnClose2.setVisible(false);
                ((Button) secondField.getChildren().get(0)).setText("");
                ((Button) secondField.getChildren().get(0)).setTextFill(Color.rgb(255, 255, 255, 0.6));
            } else if (text.equals(((Button) thirdField.getChildren().get(0)).getText())) {
                third = false;
                ((Button) thirdField.getChildren().get(0)).setGraphic(null);
                btnClose3.setVisible(false);
                ((Button) thirdField.getChildren().get(0)).setText("");
                ((Button) thirdField.getChildren().get(0)).setTextFill(Color.rgb(255, 255, 255, 0.6));
            } else if (text.equals(((Button) fourthField.getChildren().get(0)).getText())) {
                fourth = false;
                ((Button) fourthField.getChildren().get(0)).setGraphic(null);
                btnClose4.setVisible(false);
                ((Button) fourthField.getChildren().get(0)).setText("");
                ((Button) fourthField.getChildren().get(0)).setTextFill(Color.rgb(255, 255, 255, 0.6));
            } else if (text.equals(((Button) fourthField.getChildren().get(0)).getText())) {
            }

        }
    }
    
    public void menuBarPressed(MouseEvent event) {

        stage = (Stage) menuBar.getScene().getWindow();
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();

    }

    public void menuBarDragged(MouseEvent event) {
        stage = (Stage) menuBar.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);

    }

   

}
