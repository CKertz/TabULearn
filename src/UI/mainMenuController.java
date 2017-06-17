package UI;

import DB.dbConnect;
import Models.LibraryRecord;
import Models.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Created by Cooper on 5/14/2017.
 */
public class mainMenuController {
    @FXML
    private Button btnImport;
    @FXML
    private Button btnTab;
    @FXML
    private Button btnGear;
    @FXML
    private TableView<Song> tableLibrary;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colArtist;
    @FXML
    private TableColumn colAlbum;

    @FXML
    public void importSong() throws Exception{ //pressing Import Button at the main menu will run this code


        Stage importStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("import.fxml"));
        importStage.setTitle("Import");
        importStage.setScene(new Scene(root, 600, 300));
        importStage.show();


    }
    @FXML
    public void loadTabs() throws Exception{

        AnchorPane tabPane = FXMLLoader.load(getClass().getResource("tabView.fxml"));
        rootPane.getChildren().setAll(tabPane);

/*        Stage importStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("tabView.fxml"));
        importStage.setTitle("tabView");
        importStage.setScene(new Scene(root, 800, 650));
        importStage.show();*/
    }
    @FXML
    public void initialize() throws Exception {
        dbConnect loadSongs = new dbConnect();

       // ObservableList<Song> songHolder = loadSongs.populateLibraryComponents();

        tableLibrary = new TableView<Song>();

        colName = new TableColumn<>("Title");
        colName.setCellValueFactory(new PropertyValueFactory<Song,String>("songName"));

        colAlbum = new TableColumn<>("Album");
        colAlbum.setCellValueFactory(new PropertyValueFactory<Song,String>("songAlbum"));

        colArtist = new TableColumn<>("Artist");
        colArtist.setCellValueFactory(new PropertyValueFactory<Song,String>("songArtist"));

        final ObservableList<Song> data = FXCollections.observableArrayList(new Song("Back in Black","AC/DC","Back in Black Album"));
        //songHolder = FXCollections.observableArrayList();
        ObservableList<Song> songHolder = loadSongs.populateLibraryComponents();

        tableLibrary.setItems(songHolder);
        tableLibrary.getColumns().addAll(colName,colArtist,colAlbum);
        tableLibrary.setVisible(true);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));


    }

}
