package UI;

import DB.dbConnect;
import Models.LibraryRecord;
import UI.Controllers.editSongController;
import UI.Controllers.tabViewController;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.event.HyperlinkListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javafx.util.Duration.ZERO;


/**
 * Created by Cooper on 5/14/2017.
 */
public class mainMenuController implements Initializable {
    @FXML
    private Label labelSong;
    @FXML
    private Label labelArtist;
    @FXML
    private Label labelTimeLeft;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<LibraryRecord> tableLibrary;
    @FXML
    private TableColumn<LibraryRecord, String> colName;
    @FXML
    private TableColumn<LibraryRecord, String> colArtist;
    @FXML
    private TableColumn<LibraryRecord, String> colAlbum;
    @FXML
    private TableColumn<LibraryRecord, String> colGenre;
    @FXML
    private TableColumn<LibraryRecord, String> colTuning;
    @FXML
    private ListView listViewSetlist;
    @FXML
    private Slider sliderVolume;
    @FXML
    private Button btnRewind;
    @FXML
    private Button btnForward;
    @FXML
    private Hyperlink hyperLinkNewSelist;

    boolean setListDisplayed = false;
    int forwardCount = 1;
    int forwardRow = 0;
    int prevRow = 0;
    int rewindCount = 1;
    dbConnect loadSongs = new dbConnect();
    ObservableList<String> setlists = FXCollections.observableArrayList();
    ObservableList<LibraryRecord> data = FXCollections.observableArrayList();
    ListIterator<String> itr;

    //ListIterator<LibraryRecord> itr = data.listIterator();
    private MediaPlayer mediaPlayer = null;
    boolean songPlaying = false;
    //TODO: BOOLEAN that is triggered when new screen is loaded. when it is triggered, requery/reload main menu
    @FXML
    public void initialize(URL Location, ResourceBundle resources) {
        //sliderVolume = new Slider(0, 1, 0.5);
        //mediaPlayer.setAutoPlay(true);

        try {
            data = loadSongs.populateLibraryRecords();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setlists = loadSongs.populateSetlistListView();
        listViewSetlist.setItems(setlists);

        colName.setCellValueFactory(new PropertyValueFactory<>("Title"));
        colArtist.setCellValueFactory(new PropertyValueFactory<>("Artist"));
        colAlbum.setCellValueFactory(new PropertyValueFactory<>("Album"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("Genre"));
        colTuning.setCellValueFactory(new PropertyValueFactory<>("Tuning"));
        tableLibrary.setItems(data);


        tableLibrary.setRowFactory(tv -> {
            TableRow<LibraryRecord> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    if (songPlaying == true) {
                        mediaPlayer.stop();
                        songPlaying = false;
                    }
                    //mediaPlayer.stop();
                    LibraryRecord rowData = row.getItem();
                    Media title = new Media(new File(rowData.getURL()).toURI().toString());
                    labelArtist.setText(rowData.getArtist());
                    labelSong.setText(rowData.getTitle());
                    mediaPlayer = new MediaPlayer(title);
                    ArrayList<String> formattedSongs = formatSongList(data);
                    itr = formattedSongs.listIterator();
                    advanceIterator(row.getIndex());
                    play(itr.next());// testing this out

                    //mediaPlayer.play();*/
                    //mediaPlayer.setAutoPlay(true);
                    songPlaying = true;

                    sliderVolume.setValue(mediaPlayer.getVolume()*100);
                    System.out.println("Now Playing: " + rowData.getTitle() + " - " + rowData.getArtist());
                }
            });
            return row;
        });
        sliderVolume.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaPlayer.setVolume(sliderVolume.getValue()/100);
            }
        });
        listViewSetlist.setOnMouseClicked(e ->{ //filters Music library to only contain songs in the selected setlist
            if (setListDisplayed == false){
                String setlistName =  listViewSetlist.getSelectionModel().getSelectedItem().toString();
                ObservableList<LibraryRecord> setlistData = loadSongs.getSongsFromSetlist(setlistName);
                tableLibrary.setItems(setlistData);
                hyperLinkNewSelist.setText("Back to library..");
                setListDisplayed = true;
            }/*else{
                tableLibrary.setItems(data);
                hyperLinkNewSelist.setText("New...");
                setListDisplayed = false;
            }*/



        });
    }


    @FXML
    public void importSong() throws Exception{ //pressing Import Button at the main menu will run this code


        Stage importStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML_Layouts/import.fxml"));
        importStage.setTitle("Import");
        importStage.setScene(new Scene(root, 600, 300));
        importStage.show();


    }
    @FXML
    public void createSetlist() throws Exception{
        if (setListDisplayed == true){
            tableLibrary.setItems(data);
            setListDisplayed = false;
            hyperLinkNewSelist.setText("New...");
        }else{
            Stage setlistStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("FXML_Layouts/createSetlist.fxml"));
            setlistStage.setTitle("Create Setlist");
            setlistStage.setScene(new Scene(root, 1200, 800));
            setlistStage.show();
        }

    }
    @FXML
    public void rewindSong(){

        btnRewind.setOnMousePressed((e) ->{
            try{
                if (e.getClickCount() == 2 && tableLibrary.getSelectionModel().getSelectedIndex() !=0){
                    prevRow = tableLibrary.getSelectionModel().getSelectedIndex() - rewindCount;
                    LibraryRecord prevSong = tableLibrary.getItems().get(prevRow);
                    Media prevMedia = new Media (new File(prevSong.getURL()).toURI().toString());
                    mediaPlayer.stop();
                    mediaPlayer = new MediaPlayer(prevMedia);
                    mediaPlayer.play();
                    rewindCount++;
                    // prevRow--;
                }else{
                    mediaPlayer.stop();
                    mediaPlayer.setStartTime(Duration.ZERO);
                    mediaPlayer.play();
                }
            }catch(IndexOutOfBoundsException ex){
                mediaPlayer.stop();
            }

        });

    }
    @FXML
    public void forwardSong(){
        btnForward.setOnMouseClicked((e) ->{

            try {
                forwardRow = tableLibrary.getSelectionModel().getSelectedIndex() + forwardCount;
                LibraryRecord forwardSong = tableLibrary.getItems().get(forwardRow);
                Media forwardMedia = new Media (new File(forwardSong.getURL()).toURI().toString());
                mediaPlayer.stop();
                mediaPlayer = new MediaPlayer(forwardMedia);
                mediaPlayer.play();
                forwardCount++;


                mediaPlayer.stop();
                mediaPlayer.setStartTime(Duration.ZERO);
                mediaPlayer.play();
            }catch (IndexOutOfBoundsException ex){
                mediaPlayer.stop();
            }

        });

    }
    @FXML
    public void pausePlaySong(){
        if (mediaPlayer== null){
            return;
        }
        if (songPlaying == true){
            mediaPlayer.pause();
            songPlaying = false;
        }else{
            mediaPlayer.play();
            songPlaying = true;
        }
    }
    @FXML
    public void editSong() throws Exception{
        LibraryRecord selectedRecord = tableLibrary.getSelectionModel().getSelectedItem();
        String artist= selectedRecord.getArtist();
        String title = selectedRecord.getTitle();

        //mediaPlayer.getTotalDuration().toString();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML_Layouts/editSong.fxml"));
        Parent root;
        try{
            root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            ((editSongController)fxmlLoader.getController()).initData(artist,title);
            stage.show();
        }catch (IOException e){
            Logger.getLogger(mainMenuController.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    @FXML
    public void loadTabs() throws Exception{
        LibraryRecord selectedRecord = tableLibrary.getSelectionModel().getSelectedItem();
        String artistToSearch = selectedRecord.getArtist();
        String titleToSearch = selectedRecord.getTitle();
        String tuningToSearch = selectedRecord.getTuning();
        String songURL = selectedRecord.getURL();
        int songID = selectedRecord.getID();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML_Layouts/tabView.fxml"));
        Parent root;
        try{
            root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            ((tabViewController)fxmlLoader.getController()).setRecord(tuningToSearch,artistToSearch,titleToSearch, songURL,songID);
            stage.show();
        }catch (IOException e){
            Logger.getLogger(mainMenuController.class.getName()).log(Level.SEVERE,null,e);
        }

        /*
        String songID = tableLibrary.getSelectionModel().getSelectedItem().getURL();





        //Parent root = (Parent)fxmlLoader.load();

        tabViewController controller = fxmlLoader.<tabViewController>getController();
        controller.setQueryID(selectedRecord);*/
/*        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML_Layouts/tabView.fxml"));
        AnchorPane testPane = (AnchorPane) fxmlLoader.load();
        tabViewController controller = fxmlLoader.<tabViewController>getController();*/
        //controller.initData(selectedRecord);

        //@TODO this code is what works/////////////////////////////////////////////////////////////
/*        AnchorPane tabPane = FXMLLoader.load(getClass().getResource("FXML_Layouts/tabView.fxml"));
        rootPane.getChildren().setAll(tabPane);*/
        // @TODO ////////////////////////////////////////////////////////////////////////////////////////

    }
    //putting all song locations into a useable URI for the MediaPlayer
    public ArrayList<String> formatSongList(ObservableList<LibraryRecord> given){
        ArrayList<String> resultList = new ArrayList<>();
        for (int i = 0; i < given.size(); i++) {
            resultList.add(new File(given.get(i).getURL()).toURI().toString());
        }
        return resultList;
    }
    //push the iterator to the selected index in the tableview
    public void advanceIterator(int selectedIndex){
        while (itr.nextIndex() != selectedIndex){
            itr.next();
        }
    }
    public void play(String mediaFile){
        Media media = new Media(mediaFile);
        mediaPlayer.dispose();
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.stop();
                if (itr.hasNext()) {
                    //Plays the subsequent files

                    play(itr.next());
                }
                return;
            }
        });
    }



}
