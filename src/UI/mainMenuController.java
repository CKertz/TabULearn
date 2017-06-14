package UI;

import DB.dbConnect;
import Models.LibraryRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
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
    private TableView tableLibrary;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableColumn<LibraryRecord, String> colName;
    @FXML
    private TableColumn<LibraryRecord, String> colArtist;
    @FXML
    private TableColumn<LibraryRecord, String> colAlbum;

    @FXML
    public void importSong() throws Exception{ //pressing Import Button at the main menu will run this code


        Stage importStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("import.fxml"));
        importStage.setTitle("Import");
        importStage.setScene(new Scene(root, 505, 300));
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
        loadSongs.populateLibraryComponents();
         

    }

}
