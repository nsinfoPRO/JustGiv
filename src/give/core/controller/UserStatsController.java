/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package give.core.controller;

import give.base.controller.BaseController;
import give.core.model.UserModel;
import give.core.services.UserService;
import java.awt.Desktop;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;

/**
 *
 * @author Administrator
 */
public class UserStatsController extends BaseController implements Initializable {
    
    @FXML public Pane menuBar;    
    
    @FXML private HBox accountStatusPane;
    @FXML private TextField yourDonationField, firstTopDonationField, secondTopDonationField, thirdTopDonationField, firstDonationNameField, secondDonationNameField, thirdDonationNameField;
    public PieChart piechart;
    @FXML public Label emptyChartText;
    String m_strCurLocation = "";
    UserService m_userService = new UserService();
    @FXML public Stage stage;
    @FXML public double xOffset, yOffset;

    @FXML public Label thirdLabel;
    @FXML public Label fourthLabel;
    @FXML public Label fifthLabel;
    
    
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
        yourDonationField.setText(allTime.toString());
        
        //allTime = 0;
        if (allTime == 0)
        {
                emptyChartText.setVisible(true);
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                        new PieChart.Data("Education", 100),
                        new PieChart.Data("Animals", 0),
                        new PieChart.Data("Environment", 0),
                        new PieChart.Data("Health", 0),
                        new PieChart.Data("Refugees", 0),
                        new PieChart.Data("Hunger", 0),
                        new PieChart.Data("Water", 0),
                        new PieChart.Data("Children", 0),
                        new PieChart.Data("Poverty", 0));

                piechart.setLayoutX(100);
                piechart.setLayoutY(100);
                piechart.setMaxHeight(200);
                piechart.setMaxWidth(200);
                piechart.setData(pieChartData); 
                piechart.setLabelsVisible(false);
                //piechart.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

                //piechart.setDisable(true);
                PieChart.Data node = piechart.getData().get(0);
                node.getNode().setStyle("-fx-pie-color:#808080;\n"
                        + "-fx-border-width: 0px;");
                //piechart.setLegendVisible(true);
               
                
                firstTopDonationField.setVisible(false);
                secondTopDonationField.setVisible(false);
                thirdTopDonationField.setVisible(false);
                firstDonationNameField.setVisible(false);
                secondDonationNameField.setVisible(false);
                thirdDonationNameField.setVisible(false);
                thirdLabel.setVisible(false);
                fourthLabel.setVisible(false);
         }
        else{
            emptyChartText.setVisible(false);
            ObservableList<PieChart.Data> pieChartData = 
                FXCollections.observableArrayList(
                    new PieChart.Data("Education"+"("+education.toString()+")", education),
                    new PieChart.Data("Animals"+"("+animals.toString()+")", animals),
                    new PieChart.Data("Enivironment"+"("+enivironment.toString()+")", enivironment),
                    new PieChart.Data("Health"+"("+health.toString()+")", health),
                    new PieChart.Data("Refuges"+"("+refuges.toString()+")", refuges),
                    new PieChart.Data("Hunger"+"("+hunger.toString()+")", hunger),
                    new PieChart.Data("Water"+"("+water.toString()+")", water),
                    new PieChart.Data("Children"+"("+children.toString()+")", children),
                    new PieChart.Data("Poverty"+"("+poverty.toString()+")", poverty),
                    new PieChart.Data("Injustice"+"("+injustice.toString()+")", injustice));

            piechart.setData(pieChartData);
        }
         
    }
    
    public void menuBackPressed(ActionEvent event) throws IOException {
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
