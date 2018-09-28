package give.core.controller;

import give.base.controller.BaseController;
import give.core.model.CategoryModel;
import give.core.model.CharityModel;
import give.core.services.CharityService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.ResourceBundle;

public class UserCharityController extends BaseController implements Initializable {

    @FXML private FlowPane flowPaneCurCharities;
    @FXML private FlowPane flowPaneCategories;

    @FXML private TextField txtSearch;

    CharityService charityService = new CharityService();

    ArrayList<CategoryModel> allCategories = new ArrayList<>();

    HashMap<CategoryModel, ArrayList<CharityModel>> mapCharities = null;

    ArrayList<FlowPane> allSubPanes = new ArrayList<>();

    int m_nSelectedIndex = -1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtSearch.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                    String text = txtSearch.getText();

                    searchCharity(text);
                }
            }
        });

        flowPaneCurCharities.autosize();
        searchCharity("");
    }

    public void onCloseBtnPressed(ActionEvent event) throws IOException {
        // Back to Home View
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(BaseController.getResourceURL("view/fxml_home.fxml"));
        Scene homeScene = new Scene(loader.load());

        // Get stage and transfer to home scene
        this.showWindow(event, homeScene);
    }

    private void searchCharity(String keyword) {
        allCategories.clear();
        m_nSelectedIndex = -1;
        flowPaneCategories.getChildren().clear();
        allSubPanes.clear();

        mapCharities = this.charityService.searchCharities(keyword);
        Object[] keys = mapCharities.keySet().toArray();
        for(int i = 0; i < keys.length; i++) {
            CategoryModel category = (CategoryModel)keys[i];
            allCategories.add(category);
        }

        Collections.sort(allCategories, CategoryModel.IDComparator);

        for(int i = 0; i < allCategories.size(); i++) {
            CategoryModel category = allCategories.get(i);

            // Configure Category Pane
            FlowPane catPane = new FlowPane();
            catPane.setMinHeight(230.0);
            catPane.setMaxWidth(230.0);
            catPane.setStyle("" +
                    "-fx-padding: 5px; -fx-cursor: hand; " +
                    "-fx-start-margin: 5px; -fx-end-margin: 5px; " +
                    "-fx-border-color: #9a9a9a; -fx-border-width: 1px; -fx-border-style: dotted;"
            );

            HBox hBox = new HBox();

            VBox catDetailPane = new VBox();

            ImageView imageView = new ImageView();
            imageView.setFitWidth(220.0);
            imageView.setFitHeight(220.0);

            String strPath = getAppPath() + "images/category/" + category.getImageUrl();
            File file = new File(strPath);

            Image image = null;
            if(file.exists()) {
                image = new Image("file:///" + strPath);
            }
            imageView.setImage(image);

            catDetailPane.getChildren().add(imageView);

            Label lblName = new Label();
            lblName.setText(category.getName());
            lblName.setMinHeight(30.0);
            lblName.setMinWidth(220.0);
            // lblName.setFont(new Font());

            catDetailPane.getChildren().add(lblName);

            hBox.getChildren().add(catDetailPane);

            catPane.getChildren().add(hBox);

            //
            catPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    int index = allSubPanes.indexOf(catPane);

                    System.out.println("clicked on " + allCategories.get(index).getName());

                    if(m_nSelectedIndex != -1) {
                        FlowPane prevPane = allSubPanes.get(m_nSelectedIndex);
                        prevPane.setStyle("-fx-padding: 5px; -fx-cursor: hand; " +
                                "-fx-start-margin: 5px; -fx-end-margin: 5px; " +
                                "-fx-border-color: #9a9a9a; -fx-border-width: 1px; -fx-border-style: dotted;");
                    }

                    if(m_nSelectedIndex != index) {
                        catPane.setStyle("-fx-padding: 5px; -fx-cursor: hand; " +
                                "-fx-start-margin: 5px; -fx-end-margin: 5px; " +
                                "-fx-border-color: #3388ff; -fx-border-width: 1px; -fx-border-style: solid;");
                        m_nSelectedIndex = index;
                    }
                    else {
                        m_nSelectedIndex = -1;
                    }
                }
            });

            flowPaneCategories.getChildren().add(catPane);
            allSubPanes.add(catPane);
        }

        flowPaneCategories.setHgap(50);
        flowPaneCategories.setVgap(30);
        flowPaneCategories.setStyle("-fx-padding: 0 10px 0 10px;");
        flowPaneCategories.autosize();
    }
}
