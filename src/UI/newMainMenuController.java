package UI;

import DB.dbConnect;
import Models.Song;
import Models.newSong;
import com.sun.org.apache.xml.internal.security.Init;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Cooper on 6/14/2017.
 */
public class newMainMenuController implements Initializable {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<newSong> tableLibrary;
    @FXML
    private TableColumn<newSong, String> colName;
    @FXML
    private TableColumn<newSong, String> colArtist;
    @FXML
    private TableColumn<newSong, String> colAlbum;

    dbConnect loadSongs = new dbConnect();
      ObservableList<newSong> data = FXCollections.observableArrayList(
            new newSong("Back In Black","Greatest Hits", "AC/DC")
    );//put query results in parameter of observarraylist
    @Override
    public void initialize(URL Location, ResourceBundle resources){

        try {
            data = loadSongs.newPopulateLibraryComponents();
        } catch (Exception e) {
            e.printStackTrace();
        }

        colName.setCellValueFactory(new PropertyValueFactory<>("songName"));
        colArtist.setCellValueFactory(new PropertyValueFactory<>("songArtist"));
        colAlbum.setCellValueFactory(new PropertyValueFactory<>("songAlbum"));

        tableLibrary.setItems(data);
    }
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
}
