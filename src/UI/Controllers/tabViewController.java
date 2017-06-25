package UI.Controllers;

import TabSearch.googleSearch;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Cooper on 6/4/2017.
 */
public class tabViewController {

    public static int tabCounter = 0;
    WebEngine webEngine;
    ArrayList<String> listedLinks = new ArrayList<>();
    @FXML
    private AnchorPane tabPane;
    @FXML
    private WebView webViewTabs;
    @FXML
    public void initialize(){

        String toBeSearched = googleSearch.formatQuery("iron man");// give the parameter in a bundle from mainmenu
        listedLinks = googleSearch.getLinks(toBeSearched);
        webEngine = webViewTabs.getEngine();
        //String test = "https://tabs.ultimate-guitar.com/d/deep_purple/smoke_on_the_water_tab.htm";
        webEngine.load(listedLinks.get(tabCounter));
        tabCounter++;
        //webEngine.load(test);


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
