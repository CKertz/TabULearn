package UI;

import DB.dbConnect;
import Models.LibraryRecord;
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
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by Cooper on 5/14/2017.
 */
public class mainMenuController implements Initializable {
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
    private Slider sliderVolume;

    dbConnect loadSongs = new dbConnect();
    ObservableList<LibraryRecord> data = FXCollections.observableArrayList(
    );
    private MediaPlayer mediaPlayer = null;
    boolean songPlaying = false;
    @FXML
    public void initialize(URL Location, ResourceBundle resources) {
        //sliderVolume = new Slider(0, 1, 0.5);


        try {
            data = loadSongs.populateLibraryRecords();
        } catch (Exception e) {
            e.printStackTrace();
        }


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
                    mediaPlayer = new MediaPlayer(title);
                    mediaPlayer.play();
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
    public void loadTabs() throws Exception{

        LibraryRecord selectedRecord = tableLibrary.getSelectionModel().getSelectedItem();
        String songID = tableLibrary.getSelectionModel().getSelectedItem().getURL();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML_Layouts/tabView.fxml"));


        Parent root = (Parent)fxmlLoader.load();
        tabViewController controller = fxmlLoader.<tabViewController>getController();
        controller.setQueryID(selectedRecord);
        //Scene scene = new Scene(root);

        AnchorPane tabPane = FXMLLoader.load(getClass().getResource("FXML_Layouts/tabView.fxml"));

        rootPane.getChildren().setAll(root);


    }


}
