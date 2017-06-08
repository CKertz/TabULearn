package UI;

import TabSearch.googleSearch;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

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

        String toBeSearched = googleSearch.formatQuery("iron man");
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
         if (tabCounter <= 10){
             tabCounter = 10;
         }
    }
    @FXML
    public void loadPrevTab(){
        tabCounter--;
        if (tabCounter >= 0){
            tabCounter = 0;
        }
        webEngine.load(listedLinks.get(tabCounter));
    }
    @FXML
    public void exitTabView() throws Exception{
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        tabPane.getChildren().setAll(rootPane);
    }
}
