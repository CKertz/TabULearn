package UI;

import DB.dbConnect;
import Models.LibraryRecord;
import UI.Controllers.editSongController;
import UI.Controllers.tabViewController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
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
import java.util.Map;
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
    private Button btnEditSong;
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
    private Button btnPlay;
    @FXML
    private Hyperlink hyperLinkNewSelist;
    @FXML
    private TextField filterField;


    String editedSongURL = null;
    boolean editStatus = false; //when we listen in tableview for edited song to play, if this is false we can skip scanning through URLs for the edited song
    boolean setListDisplayed = false;
    String minTimeStr = null;
    String secTimeStr= null;
    int forwardCount = 1;
    int forwardRow = 0;
    int prevRow = 0;
    int rewindCount = 1;
    dbConnect loadSongs = new dbConnect();
    ObservableList<String> setlists = FXCollections.observableArrayList();
    ObservableList<LibraryRecord> data = FXCollections.observableArrayList();
    ListIterator<String> itr;
    MediaPlayer editedSong = null;
    int editedSongID = 0;
    //ListIterator<LibraryRecord> itr = data.listIterator();
    private MediaPlayer mediaPlayer = null;
    boolean songPlaying = false;
    //TODO: BOOLEAN that is triggered when new screen is loaded. when it is triggered, requery/reload main menu
    @FXML
    public void initialize(URL Location, ResourceBundle resources) {
        //sliderVolume = new Slider(0, 1, 0.5);
        //mediaPlayer.setAutoPlay(true);
        Image imagePause = new Image(getClass().getResourceAsStream("resources/play.png"));
        btnPlay.setGraphic(new ImageView(imagePause));
        Image imageForward = new Image(getClass().getResourceAsStream("resources/fastForward.png"));
        btnForward.setGraphic(new ImageView(imageForward));
        Image imageBackward = new Image(getClass().getResourceAsStream("resources/rewind.png"));
        btnRewind.setGraphic(new ImageView(imageBackward));
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
                        if (editStatus == true){
                            if (editedSong.getStatus() == MediaPlayer.Status.PLAYING){
                                editedSong.stop();
                                songPlaying = false;
                            }

                        }else{
                            mediaPlayer.stop();
                            songPlaying = false;
                        }

                    }
                    //mediaPlayer.stop();
                    LibraryRecord rowData = row.getItem();
                    if(rowData.getURL().equals(editedSongURL)){
                        editedSong.play();
                        songPlaying = true;
                        return;
                    }
                    Media title = new Media(new File(rowData.getURL()).toURI().toString());
                    labelArtist.setText(rowData.getArtist());
                    labelSong.setText(rowData.getTitle());
                    mediaPlayer = new MediaPlayer(title);
                    ArrayList<String> formattedSongs = formatSongList(data); //reformat into URIs playable for mediaPlayer
                    itr = formattedSongs.listIterator();
                    advanceIterator(row.getIndex());

                    play(itr.next());

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
        //@TODO Search feature if implemented later on
/*        FilteredList<LibraryRecord> filteredData = new FilteredList<>(data, p -> true);


        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(song -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (song.getArtist().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (song.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (song.getAlbum().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (song.getArtist().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (song.getTuning().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (song.getGenre().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }

                return false; // Does not match.
            });
        });

        SortedList<LibraryRecord> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tableLibrary.comparatorProperty());

        tableLibrary.setItems(sortedData);*/
    }

    public static Stage importStage = new Stage();
    @FXML
    public void importSong() throws Exception{ //pressing Import Button at the main menu will run this code


        //Stage importStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML_Layouts/import.fxml"));
        importStage.setTitle("Import");
        importStage.setScene(new Scene(root));
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
            setlistStage.setScene(new Scene(root));
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
            Image imagePlay = new Image(getClass().getResourceAsStream("resources/play.png"));
            btnPlay.setGraphic(new ImageView(imagePlay));
            timeline.pause();
            mediaPlayer.pause();
            songPlaying = false;
        }else{
            Image imagePause = new Image(getClass().getResourceAsStream("resources/pause.png"));
            btnPlay.setGraphic(new ImageView(imagePause));
            timeline.play();
            mediaPlayer.play();
            songPlaying = true;
        }
    }
    @FXML
    public void editSong() throws Exception{
        if(editStatus == true){
            editedSong.dispose();
        }
        LibraryRecord selectedRecord = tableLibrary.getSelectionModel().getSelectedItem();
        String artist= selectedRecord.getArtist();
        String title = selectedRecord.getTitle();

        Media media = new Media(new File(selectedRecord.getURL()).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnReady(new Runnable() {

            @Override
            public void run() {

                Duration newValue = media.getDuration();
                int hTime = (int) newValue.toHours();
                int minTime = (int) newValue.toMinutes();
                int secTime= (int) newValue.toSeconds();
                if(secTime/60>=1){
                    secTime%=60;
                }
                if(minTime/60>=1){
                    minTime%=60;
                }
                minTimeStr = Integer.toString(minTime);
                secTimeStr = Integer.toString(secTime);
                String songDuration = minTimeStr + ":" + secTimeStr;


                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML_Layouts/editSong.fxml"));
                Parent root;
                try{
                    root = fxmlLoader.load();
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    ((editSongController)fxmlLoader.getController()).initData(artist,title,songDuration,selectedRecord);
                    stage.show();
                }catch (IOException e){
                    Logger.getLogger(mainMenuController.class.getName()).log(Level.SEVERE,null,e);
                }
            }
        });


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

        //@TODO this code is what works/////////////////////////////////////////////////////////////
/*        AnchorPane tabPane = FXMLLoader.load(getClass().getResource("FXML_Layouts/tabView.fxml"));
        rootPane.getChildren().setAll(tabPane);*/
        // @TODO ////////////////////////////////////////////////////////////////////////////////////////

    }
    public void alterEditBtnText(String test){
        btnEditSong.setText(test);

    }
    public void setEditedSong(String url, boolean loopingStatus, boolean tempoStatus, Duration startTime, Duration stopTime, boolean editStatus, double tempo){
        this.editStatus = editStatus;
        Media editedMedia = new Media(new File(url).toURI().toString());
        editedSong = new MediaPlayer(editedMedia);
        editedSong.setStartTime(startTime);
        editedSong.setStopTime(stopTime);
        editedSongURL = url;
        editedSong.setRate(tempo/100);
        if(loopingStatus == true){
                editedSong.setOnEndOfMedia(new Runnable() {
                    @Override
                    public void run() {
                        editedSong.seek(startTime);
                    }

                });
                //editedSong.play();
        }
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
        mediaPlayer.setOnReady(new Runnable() {

            @Override
            public void run() {

                Duration newValue = media.getDuration();
                int hTime = (int) newValue.toHours();
                int minTime = (int) newValue.toMinutes();
                int secTime= (int) newValue.toSeconds();
                if(secTime/60>=1){ // this are to display later something like a clock 19:02:20
                    secTime%=60; //if you want just the time in minutes use only the toMinutes()
                }
                if(minTime/60>=1){
                    minTime%=60;
                }
                minTimeStr = Integer.toString(minTime);
                secTimeStr = Integer.toString(secTime);
                System.out.println(minTime+":"+secTime);
                // display media's metadata
/*                for (Map.Entry<String, Object> entry : media.getMetadata().entrySet()){
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                }*/
                startTimer(media);
                // play if you want
               // mediaPlayer.play();
            }
        });

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
    private Timeline timeline = new Timeline();
    //private int min;
    private int startTimeSec, startTimeMin;
    //private Parent borderPane;
    //public BorderPane timeBorderPane;
    private boolean isRunning;
    private boolean newSongRunning = false;
    int playCount = 0;
    public void startTimer(Media media) {

       // if(isRunning == false) { //Added a if statement to switch on "running"
            if (!(startTimeMin < 0)) {
                labelTimeLeft.setText(getMinFromMedia(media)+ ":" + getSecFromMedia(media));
                ++playCount;
                KeyFrame keyframe = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        startTimeSec--;
                        boolean isSecondsZero = startTimeSec == 0;
                        boolean timeToChangeBackground = startTimeSec == 0 && startTimeMin == 0;

                        if (isSecondsZero) {
                            startTimeMin--;
                            startTimeSec = 60;
                        }
                        if (timeToChangeBackground) {
                            timeline.stop();
                            startTimeMin = 0;
                            startTimeSec = 0;
                            labelTimeLeft.setTextFill(Color.RED);

                        }

                        labelTimeLeft.setText(startTimeMin + ":" +  startTimeSec);

                    }
                });
                labelTimeLeft.setTextFill(Color.BLACK);
                startTimeSec = getSecFromMedia(media); // Change to 60!
                startTimeMin = getMinFromMedia(media);
                timeline.setCycleCount(Timeline.INDEFINITE);
                if (playCount > 1){ //Prevents keyframes from stacking on each other. If you play 1 song, then another, it counts by 2's, 3's, so on
                    timeline.playFromStart();
                    return;
                }
                timeline.getKeyFrames().add(keyframe);
                timeline.playFromStart();
                isRunning = true;
            } /*else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have not entered a time!");
                alert.showAndWait();
            }
        }else {
            timeline.play(); //Playes from current position in in the direction indicated by rate.
        }*/

    }
    public int getSecFromMedia(Media media){
        Duration newValue = media.getDuration();
        int hTime = (int) newValue.toHours();
        int minTime = (int) newValue.toMinutes();
        int secTime= (int) newValue.toSeconds();
        if(secTime/60>=1){ // this are to display later something like a clock 19:02:20
            secTime%=60; //if you want just the time in minutes use only the toMinutes()
        }
        if(minTime/60>=1){
            minTime%=60;
        }

        return secTime;
    }
    public int getMinFromMedia(Media media){
        Duration newValue = media.getDuration();
        int hTime = (int) newValue.toHours();
        int minTime = (int) newValue.toMinutes();
        int secTime= (int) newValue.toSeconds();
        if(secTime/60>=1){ // this are to display later something like a clock 19:02:20
            secTime%=60; //if you want just the time in minutes use only the toMinutes()
        }
        if(minTime/60>=1){
            minTime%=60;
        }

        return minTime;
    }

}
