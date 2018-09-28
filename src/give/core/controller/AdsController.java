/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package give.core.controller;

import give.base.controller.BaseController;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

 
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author noahwebb
 */
public class AdsController extends BaseController implements Initializable {
    
    final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";
    final String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;";

 
    @FXML
    private ImageView imageView;
    @FXML
    private ImageView closeImageView;
    @FXML
    private Button close;
    double xPos,yPos;  
   // private AnchorPane mainPane;
 
    private int currentImageIndex=0;
    private  int lookupURLIndex=0;
    private List<Image> adsImages = new ArrayList<>();
    private Map<Integer,String> adLinksLookup = new HashMap<Integer,String>();

    public Stage mainStage;
    private Timeline timeline;
    public Boolean falseClick = Boolean.FALSE;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        loadAdsImages();
        seedAdLinks();
        initMediaPlayer();
        
        close.setStyle(IDLE_BUTTON_STYLE);
        Platform.runLater(() -> imageView.getScene().getWindow().setOnCloseRequest(event -> {
            //mediaView.getMediaPlayer().stop();
            mainStage.show();
            hideTray();
        }));
    }
    
    private List<Image> loadAdsImages(){
        String strImgPath = getAppPath() + "images/ads/" ;
       
        for(int i=1;i<=31;i++){
            adsImages.add(new Image("file:///" + strImgPath+i+".png"));        
        }
        return adsImages;
    }

    public void initMediaPlayer() {
        if (timeline != null) {
            timeline.stop();
        }
//        close.setOnMouseEntered(e -> close.setStyle(IDLE_BUTTON_STYLE));
//        close.setOnMouseExited(e -> close.setStyle(IDLE_BUTTON_STYLE));
        
        imageView.setOnMouseEntered(new EventHandler<javafx.scene.input.MouseEvent>(){
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                close.setOpacity(1);
            }            
        });
        imageView.setOnMouseExited(new EventHandler<javafx.scene.input.MouseEvent>(){
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                close.setOpacity(0);
            }            
        });
           
        imageView.setCursor(Cursor.HAND);
        closeImageView.setCursor(Cursor.HAND);
               
	close.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                close.setOpacity(1);
            }
        });
        close.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                close.setOpacity(0);
            }
        });
        //initialize
        imageView.setImage(adsImages.get(0));
        this.lookupURLIndex = this.currentImageIndex;
        this.currentImageIndex++;
        
        
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) { 
                if(falseClick){
                    return;
                }
                
                //System.out.println("Tile pressed "+ currentImageIndex);
                String url = adLinksLookup.get(lookupURLIndex+1);
                if(url==null){
                   int randomIndex = getRandomNumberInRange(1,adLinksLookup.size());
                   url = adLinksLookup.get(lookupURLIndex);
                }
                try {
                    //System.out.println("URI-->"+url);
                    java.awt.Desktop.getDesktop().browse(new URI(url));
                } catch (URISyntaxException ex) {
                    Logger.getLogger(AdsController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(AdsController.class.getName()).log(Level.SEVERE, null, ex);
                }
                event.consume();
            }
       });
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(30),
                  new EventHandler() {
                    // KeyFrame event handler
                    public void handle(Event event) {
                        lookupURLIndex=currentImageIndex;
                        
                        imageView.setImage(adsImages.get(currentImageIndex++));
                         if(currentImageIndex==adsImages.size()){
                            currentImageIndex=0;
                        }                       
                      }
                }));
        timeline.playFromStart();
        
    }

    public void backButtonPressed(ActionEvent event) throws IOException {
        if(timeline!=null){
            timeline.stop();
        }
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
        mainStage.show();
        hideTray();
    }

    private void hideTray() {
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            for (TrayIcon icon : tray.getTrayIcons()) {
                tray.remove(icon);
            }
        }
    }

    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
                throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
    
    private void seedAdLinks(){
 
        adLinksLookup.put(1,"https://frontier.com/offer/experiencefios?tab=3&utm_source=217544012&utm_medium=418369413&utm_content=100521269&utm_campaign=20112138&invsrc=resi_fios_dixon&d_pid=AMsySZZWJaWUAZOa468QUdJdCKzb&g=ct=US&st=CA&city=13666&dma=195&zp=92648&bw=4");
        adLinksLookup.put(2,"https://www.autobytel.com/new-cars/buick/regal-tourx/price-quotes/?gclid=EAIaIQobChMIwZSMrMOs2wIVEiR_Ch0caAUhEAEYASABEgKOEfD_BwE");
        adLinksLookup.put(3,"https://azure.microsoft.com/en-us/free/sql-database/search/?OCID=AID667498_OLA_20906233_217716137_100724308");
        adLinksLookup.put(4,"https://newsroom.fb.com/news/2018/05/inside-feed-facing-facts/");
        adLinksLookup.put(5,"https://www.amazon.com/gift-cards/b?ie=UTF8&node=2238192011");
        adLinksLookup.put(6,"https://www.tableau.com/asset/dos-and-donts-dashboards?utm_campaign=Prospecting-ANLYTDASH-ALL-ALL-ALL-ALL&utm_medium=Display&utm_source=Google+Display&utm_campaign_id=2017047&utm_language=EN&utm_country=USCA&kw={keyword}&adgroup=CTX-Dashboards&adused={creative}&matchtype={matchtype}&placement={placement}&creative=&dclid=CPqf-dbRrNsCFdZ7YgodjfkCOQ&gclid=EAIaIQobChMIh5KHtdGs2wIVhI9iCh2WrwoqEAEYASAAEgJqx_D_BwE");
        adLinksLookup.put(7,"https://vimeo.com/business?vcid=33857&gclid=EAIaIQobChMI-Zr5tNGs2wIVA3piCh29Zg5zEAEYASAAEgIEjvD_BwE");
        adLinksLookup.put(8,"https://www.blockchain.com/?gclid=EAIaIQobChMIyZf0tNGs2wIVRIBiCh2KkAllEAEYASAAEgJnSfD_BwE");
        adLinksLookup.put(9,"https://grocery.walmart.com/?adid=1500000020090000018548&veh=dsp");
        adLinksLookup.put(10,"http://gofitch.co/");
        adLinksLookup.put(11,"https://www.missguidedus.com/");
        adLinksLookup.put(12,"https://www.bestbuy.com/site/clp/laptop-savings/pcmcat1493416439521.c?id=pcmcat1493416439521&ref=166&loc=BAN_2278018_21101089_220860392");
        adLinksLookup.put(13,"http://www.dell.com/en-us/work/shop/accessories/apd/210-amll?mpt=87280&ven5=ww4zwwalrsskzmsooa0f5g&mpcs=bypass&mpcr=102275704&mpcrset=root&dgc=ba&dgseg=soho&cid=242331&lid=30501&acd=12309242331305017c102275704&VEN3=113404351349389728");
        adLinksLookup.put(14,"http://ubiatarplay.io/");
        adLinksLookup.put(15,"https://www.autobytel.com/hybrid-cars/car-buying-guides/the-top-10-fastest-hybrids-on-the-market-124977/?id=22650&gclid=EAIaIQobChMI0_uT9tqs2wIVVHtiCh267gEaEAEYASAAEgIltvD_BwE");
        adLinksLookup.put(16,"https://www.autobytel.com/hybrid-cars/car-buying-guides/the-top-10-fastest-hybrids-on-the-market-124977/?id=22650&gclid=EAIaIQobChMI0_uT9tqs2wIVVHtiCh267gEaEAEYASAAEgIltvD_BwE");
        adLinksLookup.put(17,"https://www.bluehost.com/home");
        adLinksLookup.put(18,"https://resources.office.com/ww-landing-Forbes-Revolution-In-Teaming-WhitePaper.html?lcid=en-us&wt.mc_id=AID686824_QSG_OLA_245130&ocid=AID686824_QSG_OLA_245130");
        adLinksLookup.put(19,"https://www.ezoic.com/improve-user-experience-increase-website-revenue/?utm_source=google&utm_medium=d&utm_campaign=d&utm_term=d&utm_content=d&gclid=EAIaIQobChMI08bRtN-s2wIVEHViCh3_2QRXEAEYASAAEgLSnPD_BwE");
        adLinksLookup.put(20,"https://www.homedepot.com/b/Lumber-Composites/AppearanceBoards?cm_mmc=ola|carat|20691744|213417167|412629381|97469261");
        adLinksLookup.put(21,"https://www.directvnow.com/rokunow/?ref=EaYEz200H9XCoN0HO");
        adLinksLookup.put(22,"https://gradsoflife.org/?utm_medium=banner&utm_source=forbes.com&utm_campaign=adcouncil&utm_content=nation");
      
    }
    
    public void closeBtnPressed(ActionEvent event) throws IOException{
    /*
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(BaseController.getResourceURL("view/fxml_login.fxml"));        
        Scene homeScene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setScene(homeScene);
        stage.initStyle(StageStyle.UNDECORATED);
        ((Node)(event.getSource())).getScene().getWindow().hide();
        // Get stage and transfer to home scene
        stage.show();*/
        if(timeline!=null){
            timeline.stop();
        }
        
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
        mainStage.getIcons().add(new Image(BaseController.getResourceURL("view/images/taskbar-icon.png").toExternalForm()));

        mainStage.show();
        hideTray();
        
    }
     
    public double getXPos(){
        return xPos;
    }
    public double getYPos(){
        return yPos;
    }
    
    public void setXPos(double xPos){
        this.xPos=xPos;
    }
    public void setYPos(double yPos){
        this.yPos=yPos;
    }
}
