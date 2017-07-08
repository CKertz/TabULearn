package UI.Controllers;

import DB.dbConnect;
import Models.GearRecord;
import Models.LibraryRecord;
import TabSearch.googleSearch;
import UI.mainMenuController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Cooper on 6/4/2017.
 */
public class tabViewController extends Application {

    ObservableList<GearRecord> data = FXCollections.observableArrayList();
    public static int tabCounter = 0;
    WebEngine webEngine;
    ArrayList<String> listedLinks = new ArrayList<>();
    String queryToSearch = null;
    String tuning = null;
    String songDisplayed = null;
    String songURL = null;
    private MediaPlayer mediaPlayer = null;
    boolean songPlaying = false;
    dbConnect loadGear = new dbConnect();
    int songID = 0;
    LibraryRecord recordToSearch;
    @FXML
    private TableView<GearRecord> tableViewGear;
    @FXML
    private TableColumn<GearRecord, String> colGear;
    @FXML
    private TableColumn<LibraryRecord, String> colSetting;
    @FXML
    private Label labelTuning;
    @FXML
    private Label labelSongName;
    @FXML
    private Button btnRetreiveTabs;
    @FXML
    private AnchorPane tabPane;
    @FXML
    private WebView webViewTabs;
    @FXML
    private Button btnExitTabView;
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

    public void setRecord(String tuningGiven, String artist, String title, String url, int songID){
       // given = recordToSearch;
        this.songID = songID;
        queryToSearch = title + " " + artist;
        tuning = tuningGiven;
        labelSongName.setText(title + " - " + artist);
        labelTuning.setText(tuningGiven);
        songURL = url;
        Media titlePlaying = new Media(new File(songURL).toURI().toString());
        mediaPlayer = new MediaPlayer(titlePlaying);
        try {
            data = loadGear.populateTabviewTable(songID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        colGear.setCellValueFactory(new PropertyValueFactory<>("Gear"));
        colSetting.setCellValueFactory(new PropertyValueFactory<>("Setting"));
        tableViewGear.setItems(data);
    }
/*    public void initData(LibraryRecord query){
        songToBeSearched = query;

       System.out.print(query.getTitle());
    }*/
    @FXML
    public void modifySetting(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXML_Layouts/editSetting.fxml"));
        Parent root;
        try{
            root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            ((editSettingController)fxmlLoader.getController()).setSongID(songID);
            stage.show();
        }catch (IOException e){
            Logger.getLogger(mainMenuController.class.getName()).log(Level.SEVERE,null,e);
        }
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
    public void muteSong(){
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING && mediaPlayer.isMute() == false){
            mediaPlayer.setMute(true);
        }else{
            mediaPlayer.setMute(false);
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
        Stage stage = (Stage) btnExitTabView.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void playPauseSong(){
        if (songPlaying == true) {
            mediaPlayer.pause();
            songPlaying = false;
        }else{

            mediaPlayer.play();
            songPlaying = true;
        }

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
