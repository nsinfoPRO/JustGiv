/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package give.core.controller;

import give.base.controller.BaseController;
import give.core.model.CategoryModel;
import give.core.model.CharityModel;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;

import give.core.services.CharityService;
import give.util.FileUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 *
 * @author Administrator
 */
public class CharityEditController extends BaseController implements Initializable {
    
    @FXML private TextField txtName;
    @FXML private TextField txtSite;
    @FXML private ComboBox<CategoryModel> comboCategory;
    @FXML private TextField txtAmount;
    @FXML private TextField txtNumber;
    @FXML private TextField txtMetric;
    @FXML private TextArea txtBio;
    @FXML private ImageView imgLogo;

    @FXML private ToggleButton btnUpload;
    @FXML private ToggleButton btnSave;
    @FXML public double xOffset, yOffset;
    public Stage stage;
    @FXML AnchorPane mainPane;


    
    // Data Variable
    private ObservableList<CategoryModel> comboData = FXCollections.observableArrayList();

    String m_strCurImage = "";
    CharityModel m_curCharity;
    CategoryModel emptyCategory = new CategoryModel();

    CharityService m_charityService = new CharityService();

    public void setCurrentCharity(CharityModel charity) {
        this.m_curCharity = charity;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        emptyCategory.setID(0);
        emptyCategory.setName("Select category");

        ArrayList<CategoryModel> arrCategories = this.m_charityService.getCategories();
        comboData.add(emptyCategory);
        comboData.addAll(arrCategories);

        comboCategory.setCellFactory(
            new Callback<ListView<CategoryModel>, ListCell<CategoryModel>>() {
                @Override public ListCell<CategoryModel> call(ListView<CategoryModel> param) {
                    final ListCell<CategoryModel> cell = new ListCell<CategoryModel>() {
                        {
                            super.setPrefWidth(100);
                        }
                        @Override public void updateItem(CategoryModel item,
                                                         boolean empty) {
                            super.updateItem(item, empty);
                            if (item != null) {
                                setText(item.getName());
                            }
                            else {
                                setText("");
                            }
                        }
                    };
                    return cell;
                }
            });

        comboCategory.setConverter(new StringConverter<CategoryModel>(){

            @Override
            public String toString(CategoryModel object) {
                return object == null ? null : object.getName();
            }

            @Override
            public CategoryModel fromString(String string) {
                for( int i = 0; i < comboData.size(); i++ ) {
                    CategoryModel category = comboData.get(i);
                    if(string.compareTo(category.getName()) == 0) {
                        return category;
                    }
                }

                return emptyCategory;
            }
        });

        comboCategory.setItems(comboData);

        this.initControls(this.m_curCharity);
    }
    
    public void onLogoUploadBtnPressed(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Logo File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        File file = fileChooser.showOpenDialog(getWindowBy(event, true));
        if (file != null) {
            String ext = FileUtil.getFileExt(file.getAbsolutePath());

            if(FileUtil.isJPG(file.getAbsolutePath()) || FileUtil.isPNG(file.getAbsolutePath())) {
                // Generate File name
                long lTime = System.currentTimeMillis() / 1000;

                String strImgPath = getAppPath() + "images/charity/" + lTime + "." + ext;
                File destFile = new File(strImgPath);

                // Copy File
                Files.copy(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Show Image
                m_strCurImage = strImgPath;
                imgLogo.setImage(new Image("file:///" + m_strCurImage));
            }
            else {
                this.showAlert("Invalid Image File Format.");
            }
        }
    }
    
    public void onSaveBtnPressed(ActionEvent event) throws IOException {
        // Check Name
        String strName = txtName.getText();
        if(strName.trim().length() == 0) {
            this.showAlert("Please Input the Name.");
            txtName.requestFocus();
            return;
        }

        // Check BIO
        String strBIO = txtBio.getText();
        if(strBIO.trim().length() == 0) {
            this.showAlert("Please Input the BIO.");
            txtBio.requestFocus();
            return;
        }

        // Check Web Site
        String strSite = txtSite.getText();
        if(strName.trim().length() == 0) {
            this.showAlert("Please Input the Website address.");
            txtSite.requestFocus();
            return;
        }

        // Check Category
        CategoryModel selectedCategory = comboCategory.getSelectionModel().getSelectedItem();
        if(selectedCategory.getID() == 0) {
            this.showAlert("Please Select a Category.");
            comboCategory.requestFocus();
            return;
        }

        // Check Metric
        String strMetric = txtMetric.getText();
        if(strName.trim().length() == 0) {
            this.showAlert("Please Input the Metric.");
            txtMetric.requestFocus();
            return;
        }

        // Check Number
        Integer nNumber = 0;
        try {
            nNumber = Integer.valueOf(txtNumber.getText());
        }
        catch(Exception e) {
            nNumber = -1;
        }
        if(nNumber < 0) {
            this.showAlert("Please Input the Number correctly.");
            txtNumber.requestFocus();
            return;
        }

        // Check Amount
        Double dblAmount = 0.0;
        try {
            dblAmount = Double.valueOf(txtAmount.getText());
        }
        catch(Exception e) {
            dblAmount = -1.0;
        }
        if(dblAmount < 0) {
            this.showAlert("Please Input the Amount correctly.");
            txtAmount.requestFocus();
            return;
        }

        // Check Image
        if(m_strCurImage.length() == 0) {
            this.showAlert("Please update an Image.");
            return;
        }

        // Create New Charity Object to Save
        CharityModel newCharity = new CharityModel();
        newCharity.setName(strName);
        newCharity.setBio(strBIO);
        newCharity.setImageUrl(m_strCurImage.replace(getAppPath() + "images/charity/", ""));
        newCharity.setWebSite(strSite);
        newCharity.setCategory(selectedCategory.getID());
        newCharity.setMetric(strMetric);
        newCharity.setAmount(dblAmount);
        newCharity.setNumber(nNumber);

        if(m_curCharity == null) {
            if(m_charityService.addNewCharity(newCharity)) {
                if(this.showConfirm("Would you like to add more?")) {
                    // Initialize
                    initControls(null);
                }
                else {
                    onCloseBtnPressed(event);
                }
            }
            else {
                this.showAlert("Failed to add this charity.\nPlease try again.");
            }
        }
        else {
            newCharity.setID(m_curCharity.getID());
            if(!m_charityService.updateCharity(newCharity)) {
                this.showAlert("Failed to update this charity.\nPlease try again.");
            }
        }
    }

    public void onCloseBtnPressed(ActionEvent event) throws IOException {
        // Back to List View
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(BaseController.getResourceURL("view/fxml_charity_list.fxml"));
        Scene homeScene = new Scene(loader.load());

        // Get stage and transfer to home scene
        this.showWindow(event, homeScene);
    }

    public void initControls(CharityModel charity) {
        this.m_curCharity = charity;

        if(this.m_curCharity == null) {
            txtName.setText("");
            txtSite.setText("");

            comboCategory.setValue(emptyCategory);

            txtBio.setText("");
            txtMetric.setText("");
            txtNumber.setText("0");
            txtAmount.setText("0.0");

            m_strCurImage = "";
            imgLogo.setImage(null);

            btnSave.setText("Add Charity");
        }
        else {
            txtName.setText(m_curCharity.getName());
            txtSite.setText(m_curCharity.getWebSite());

            CategoryModel selected = this.getCategory(m_curCharity);
            comboCategory.setValue(selected);

            txtBio.setText(m_curCharity.getBio());
            txtMetric.setText(m_curCharity.getMetric());
            txtNumber.setText(String.valueOf(m_curCharity.getNumber()));
            txtAmount.setText(String.valueOf(m_curCharity.getAmount()));

            m_strCurImage = getAppPath() + "images/charity/" + m_curCharity.getImageUrl();
            imgLogo.setImage(new Image("file:///" + m_strCurImage));

            btnSave.setText("Update Charity");
        }
    }

    private CategoryModel getCategory(CharityModel charity) {
        for(int i = 0; i < this.comboData.size(); i++) {
            CategoryModel item = this.comboData.get(i);
            if(item.getID() == charity.getCategory()) {
                return item;
            }
        }

        return emptyCategory;
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
