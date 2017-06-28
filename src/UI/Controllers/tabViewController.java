package UI.Controllers;

import Models.LibraryRecord;
import TabSearch.googleSearch;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Cooper on 6/4/2017.
 */
public class tabViewController {

    public static int tabCounter = 0;
    WebEngine webEngine;
    ArrayList<String> listedLinks = new ArrayList<>();
    LibraryRecord songToBeSearched;

    @FXML
    private AnchorPane tabPane;
    @FXML
    private WebView webViewTabs;
    @FXML
    public void initialize() {
        songToBeSearched = null;
        webEngine = webViewTabs.getEngine();


    }
    public tabViewController(LibraryRecord recordGiven){
        System.out.print("yay");
    }
/*    @FXML
    public void start(Stage stage)throws Exception{
        String toBeSearched = googleSearch.formatQuery("iron man");// give the parameter in a bundle from mainmenu
        listedLinks = googleSearch.getLinks(toBeSearched);
        webEngine = webViewTabs.getEngine();
        //String test = "https://tabs.ultimate-guitar.com/d/deep_purple/smoke_on_the_water_tab.htm";
        webEngine.load(listedLinks.get(tabCounter));
        tabCounter++;

    }*/
    public void initData(LibraryRecord query){
        songToBeSearched = query;
        String toBeSearched = googleSearch.formatQuery("iron man");// give the parameter in a bundle from mainmenu
        listedLinks = googleSearch.getLinks(toBeSearched);
        //webEngine = webViewTabs.getEngine();

        //String test = "https://tabs.ultimate-guitar.com/d/deep_purple/smoke_on_the_water_tab.htm";
        webEngine.load(listedLinks.get(tabCounter));
        tabCounter++;
       System.out.print(query.getTitle());
    }
    @FXML
    public void loadNextTab(){
         webEngine.load(listedLinks.get(tabCounter));
         tabCounter++;
         if (tabCounter >= 10){
             tabCounter = 0;
         }
    }
    @FXML
    public void loadPrevTab(){
        tabCounter--;
        if (tabCounter <= 0){
            tabCounter = 0;
        }
        webEngine.load(listedLinks.get(tabCounter));
    }
    @FXML
    public void exitTabView() throws Exception{
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("../FXML_Layouts/mainMenu.fxml"));
        tabPane.getChildren().setAll(rootPane);
    }
}
