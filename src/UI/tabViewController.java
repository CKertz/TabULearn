package UI;

import TabSearch.googleSearch;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.ArrayList;

/**
 * Created by Cooper on 6/4/2017.
 */
public class tabViewController {
    @FXML
    private WebView webViewTabs;
    @FXML
    public void initialize(){
        ArrayList<String> listedLinks = new ArrayList<>();
        String toBeSearched = googleSearch.formatQuery("smoke on the water");
        listedLinks = googleSearch.getLinks(toBeSearched);
        WebEngine webEngine = webViewTabs.getEngine();
        webEngine.load(listedLinks.get(2));

    }
}
