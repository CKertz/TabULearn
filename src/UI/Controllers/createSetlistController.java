package UI.Controllers;

import DB.dbConnect;
import Models.LibraryRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Cooper on 6/28/2017.
 */
public class createSetlistController implements Initializable {
    @FXML
    private TableView<LibraryRecord> tableViewSetList;
    @FXML
    private TableView<LibraryRecord> tableViewMusicLibrary;
    @FXML
    private TableColumn<LibraryRecord, String> colLibraryTitle;
    @FXML
    private TableColumn<LibraryRecord, String> colLibraryArtist;
    @FXML
    private TableColumn<LibraryRecord, String> colSetlistTitle;
    @FXML
    private TableColumn<LibraryRecord, String> colSetlistArtist;
    @FXML
    private Button btnAddSong;
    @FXML
    private Button btnRemoveSong;


    @FXML
    public void initialize(URL Location, ResourceBundle resources ){

        //@TODO walk through mainmenu code and see why this would throw an error upon load. commenting it all out works fine
        dbConnect dbLoad = new dbConnect();
        ObservableList<LibraryRecord> data = FXCollections.observableArrayList();
        ObservableList<LibraryRecord> setlistData = FXCollections.observableArrayList();
        try {
            data = dbLoad.populateLibraryRecords();
        } catch (Exception e) {
            e.printStackTrace();
        }
        colLibraryTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        colLibraryArtist.setCellValueFactory(new PropertyValueFactory<>("Artist"));
        colSetlistTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        colSetlistArtist.setCellValueFactory(new PropertyValueFactory<>("Artist"));

        tableViewMusicLibrary.setItems(data);

        btnAddSong.setOnMouseClicked(event -> {
            try{
                LibraryRecord toBeAdded = tableViewMusicLibrary.getSelectionModel().getSelectedItem();

                setlistData.add(toBeAdded);
                tableViewSetList.setItems(setlistData);
            }catch (NullPointerException ex){
                return;
            }

        });
    }
}
