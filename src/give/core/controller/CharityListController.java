/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package give.core.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import give.Give;
import give.base.controller.BaseController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import give.base.model.BaseModel;
import give.core.model.CharityModel;
import give.core.model.UserModel;
import give.core.services.CharityService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;


/**
 *
 * @author Administrator
 */
public class CharityListController extends BaseController implements Initializable {

    @FXML private Hyperlink linkNew;
    @FXML private Hyperlink linkEdit;
    @FXML private Hyperlink linkDelete;
    @FXML public double xOffset, yOffset;
    public Stage stage;
    @FXML AnchorPane mainPane;

    @FXML private ImageView imgLogo;
    @FXML private TableView<CharityModel> tableCharities;

    private ObservableList<CharityModel> m_tblData = FXCollections.observableArrayList();

    CharityService m_charityService = new CharityService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        m_tblData.addAll(this.m_charityService.getAllCharities());

        TableColumn logoCol = new TableColumn("Logo");
        logoCol.setCellValueFactory(new PropertyValueFactory<CharityModel, String>("imageUrl"));
        logoCol.setCellFactory(new Callback<TableColumn<CharityModel, String>, TableCell<CharityModel, String>>() {
            @Override
            public TableCell<CharityModel, String> call(TableColumn<CharityModel, String> param) {
                final ImageView imageview = new ImageView();
                imageview.setFitHeight(50);
                imageview.setFitWidth(50);

                //Set up the Table
                TableCell<CharityModel, String> cell = new TableCell<CharityModel, String>() {
                    public void updateItem(String charity, boolean empty) {
                        if(charity != null) {
                            String strImgPath = getAppPath() + "images/charity/" + charity;
                            imageview.setImage(new Image("file:///" + strImgPath));
                        }
                        else {
                            imageview.setImage(null);
                        }
                    }
                };

                cell.setGraphic(imageview);
                cell.setStyle("-fx-alignment: CENTER;");
                return cell;
            }
        });

        logoCol.setEditable(false);

        TableColumn nameCol = new TableColumn("Name");
        nameCol.setMinWidth(120);
        nameCol.setCellValueFactory(new PropertyValueFactory<CharityModel, String>("name"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setEditable(false);

        TableColumn siteCol = new TableColumn("WebSite");
        siteCol.setMinWidth(210);
        siteCol.setCellValueFactory(new PropertyValueFactory<CharityModel, String>("webSite"));
        siteCol.setCellFactory(TextFieldTableCell.forTableColumn());
        siteCol.setEditable(false);

        TableColumn categoryCol = new TableColumn("Category");
        categoryCol.setMinWidth(105);
        categoryCol.setCellValueFactory(new PropertyValueFactory<CharityModel, String>("categoryName"));
        categoryCol.setCellFactory(TextFieldTableCell.forTableColumn());
        categoryCol.setEditable(false);

        TableColumn bioCol = new TableColumn("BIO");
        bioCol.setMinWidth(330);
        bioCol.setCellValueFactory(new PropertyValueFactory<CharityModel, String>("bio"));
        bioCol.setCellFactory(TextFieldTableCell.forTableColumn());
        bioCol.setEditable(false);

        tableCharities.setItems(this.m_tblData);
        tableCharities.getColumns().addAll(logoCol, bioCol, nameCol, siteCol, categoryCol);

        tableCharities.setEditable(true);
        //tableCharities.setSelectionModel();
        tableCharities.refresh();
    }

    public void onCloseBtnPressed(ActionEvent event) throws IOException {
        // Back to Home View
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(BaseController.getResourceURL("view/fxml_admin_home.fxml"));
        Scene homeScene = new Scene(loader.load());

        // Get stage and transfer to home scene
        this.showWindow(event, homeScene);
    }

    public void onLinkNewPressed(ActionEvent event) throws IOException {
        // Back to List View
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(BaseController.getResourceURL("view/fxml_charity_edit.fxml"));
        Scene homeScene = new Scene(loader.load());

        // Get stage and transfer to home scene
        this.showWindow(event, homeScene);
    }

    public void onLinkEditPressed(ActionEvent event) throws IOException {
        CharityModel selectedCharity = tableCharities.getSelectionModel().getSelectedItem();

        if(selectedCharity == null) {
            this.showPrompt("Please select a charity");
            return;
        }

        // Back to List View
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(BaseController.getResourceURL("view/fxml_charity_edit.fxml"));
        Scene homeScene = new Scene(loader.load());
        CharityEditController controller = loader.getController();
        controller.initControls(selectedCharity);

        // Get stage and transfer to home scene
        this.showWindow(event, homeScene);
    }

    public void onLinkDeletePressed(ActionEvent event) throws IOException {
        CharityModel selectedCharity = tableCharities.getSelectionModel().getSelectedItem();

        if(selectedCharity == null) {
            this.showPrompt("Please select a charity");
        }
        else {
            if(this.showConfirm("Would you like to delete now?")) {
                ArrayList<CharityModel> arr = new ArrayList<>();
                arr.add(selectedCharity);
                this.m_charityService.deleteCharities(arr);
                this.m_tblData.remove(selectedCharity);
            }
        }
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
