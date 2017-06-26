package UI.Controllers;

import Models.LibraryRecord;
import TabSearch.googleSearch;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Cooper on 6/4/2017.
 */
public class tabViewController extends Application {

    public static int tabCounter = 0;
    WebEngine webEngine;
    ArrayList<String> listedLinks = new ArrayList<>();
    LibraryRecord songToBeSearched;

    @FXML
    private AnchorPane tabPane;
    @FXML
    private WebView webViewTabs;

    @Override
    public void start(Stage stage)throws Exception{
        webViewTabs = new WebView();
        webEngine = webViewTabs.getEngine();
        webEngine.loadContent("<html><body><h1>Hello</h1><body></html>");
/*        String toBeSearched = googleSearch.formatQuery("iron man");// give the parameter in a bundle from mainmenu
        listedLinks = googleSearch.getLinks(toBeSearched);

        //String test = "https://tabs.ultimate-guitar.com/d/deep_purple/smoke_on_the_water_tab.htm";

        //webEngine.load(listedLinks.get(tabCounter));
        tabCounter++;
        //webEngine.load(test);
        System.out.print(songToBeSearched.getArtist());*/
        VBox root = new VBox();
        root.getChildren().addAll(webViewTabs);
        AnchorPane tabPane = FXMLLoader.load(getClass().getResource("FXML_Layouts/tabView.fxml"));
        tabPane.getChildren().setAll(root);

    }
    public void setQueryID(LibraryRecord query){
        songToBeSearched = query;
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
