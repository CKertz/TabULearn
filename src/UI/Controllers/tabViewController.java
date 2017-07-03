package UI.Controllers;

import Models.LibraryRecord;
import TabSearch.googleSearch;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
public class tabViewController extends Application {

    public static int tabCounter = 0;
    WebEngine webEngine;
    ArrayList<String> listedLinks = new ArrayList<>();
    String queryToSearch = null;
    LibraryRecord recordToSearch;
    @FXML
    private Button btnRetreiveTabs;
    @FXML
    private AnchorPane tabPane;
    @FXML
    private WebView webViewTabs;
    @FXML
    public void initialize() {
/*        webEngine = webViewTabs.getEngine();
        String toBeSearched = googleSearch.formatQuery("iron man");
        //String toBeSearched = googleSearch.formatQuery(recordToSearch.getTitle() + " " + recordToSearch.getArtist());// give the parameter in a bundle from mainmenu
        listedLinks = googleSearch.getLinks(toBeSearched);
        //webEngine = webViewTabs.getEngine();

        //String test = "https://tabs.ultimate-guitar.com/d/deep_purple/smoke_on_the_water_tab.htm";
        webEngine.load(listedLinks.get(tabCounter));
        tabCounter++;*/

    }

    @FXML
    public void start(Stage stage)throws Exception{
 /*       webEngine = webViewTabs.getEngine();
        String toBeSearched = googleSearch.formatQuery(recordToSearch.getTitle() + " " + recordToSearch.getArtist());// give the parameter in a bundle from mainmenu
        listedLinks = googleSearch.getLinks(toBeSearched);
        //webEngine = webViewTabs.getEngine();

        //String test = "https://tabs.ultimate-guitar.com/d/deep_purple/smoke_on_the_water_tab.htm";
        webEngine.load(listedLinks.get(tabCounter));
        tabCounter++;

        //String toBeSearched = googleSearch.formatQuery("iron man");// give the parameter in a bundle from mainmenu
        listedLinks = googleSearch.getLinks(toBeSearched);
        webEngine = webViewTabs.getEngine();
        //String test = "https://tabs.ultimate-guitar.com/d/deep_purple/smoke_on_the_water_tab.htm";
        webEngine.load(listedLinks.get(tabCounter));
        tabCounter++;*/

    }

    public void setRecord(String test){
       // given = recordToSearch;
        queryToSearch = test;
        btnRetreiveTabs.setText(test);
    }
/*    public void initData(LibraryRecord query){
        songToBeSearched = query;

       System.out.print(query.getTitle());
    }*/
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
    @FXML
    public void getTabs(){
        webEngine = webViewTabs.getEngine();
        //String toBeSearched = googleSearch.formatQuery("iron man");
        String toBeSearched = googleSearch.formatQuery(queryToSearch);// give the parameter in a bundle from mainmenu
        listedLinks = googleSearch.getLinks(toBeSearched);
        //webEngine = webViewTabs.getEngine();

        //String test = "https://tabs.ultimate-guitar.com/d/deep_purple/smoke_on_the_water_tab.htm";
        webEngine.load(listedLinks.get(tabCounter));
        tabCounter++;
    }
}
