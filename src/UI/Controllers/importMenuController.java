package UI.Controllers;

import DB.dbConnect;
import Models.Song;

import UI.chooseFile;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.List;


/**
 * Created by Cooper on 5/15/2017.
 */
public class importMenuController {

    @FXML
    private Label labelSong;
    @FXML
    private Label labelArtist;
    @FXML
    private Label labelTimeLeft;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField textFieldSongTitle;
    @FXML
    private TextField textFieldSongAlbum;
    @FXML
    private TextField textFieldSongArtist;
    @FXML
    private ComboBox comboBoxGenre;
    @FXML
    private Hyperlink hyperlinkSongURL;
    @FXML
    private TextField textFieldSongURL;
    @FXML
    private ComboBox comboBoxTuning;
    @FXML
    private ComboBox comboBoxGear;
    @FXML
    private ListView listViewAddedGear;
    @FXML
    private Button btnRemoveSelected;



    @FXML
    public void importSong(){
        List<String>gearAdded = listViewAddedGear.getItems();
        dbConnect insertingSong = new dbConnect();
        int genreId = insertingSong.getGenreID(comboBoxGenre.getSelectionModel().getSelectedItem().toString());
        int tuningId = insertingSong.getTuningIDFromDB(comboBoxTuning.getSelectionModel().getSelectedItem().toString());

     //@TODO handle nulls smoothly
        Song songToBeImported = new Song(
                textFieldSongTitle.getText(),
                textFieldSongURL.getText(),
                textFieldSongArtist.getText(),
                textFieldSongAlbum.getText(),
                genreId,
                tuningId
        );


        insertingSong.insertIntoLibrary(songToBeImported,gearAdded);
        int songID = insertingSong.getSongID(textFieldSongURL.getText());
        insertingSong.insertGearLayoutIntoDB(gearAdded,songID);
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void removeSelectedGear(){
        final int selectedIndex = listViewAddedGear.getSelectionModel().getSelectedIndex();
        final String selectedItemName = listViewAddedGear.getSelectionModel().getSelectedItem().toString();
        listViewAddedGear.getItems().remove(selectedIndex);
        btnRemoveSelected.setText("Removed " + selectedItemName);
    }
    @FXML
    public void cancelImportWindow() throws Exception{
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void initialize(){
        chooseFile Choosefile = new chooseFile();
        textFieldSongURL.setText(Choosefile.handle());

        ObservableList<String> gearList = FXCollections.observableArrayList();
        ObservableList<String> tuningList = FXCollections.observableArrayList();
        ObservableList<String> genreList = FXCollections.observableArrayList();

        dbConnect getData = new dbConnect();
        tuningList = getData.populateTuningComboBox();
        comboBoxTuning.getItems().removeAll(comboBoxTuning.getItems());
        comboBoxTuning.getItems().addAll(tuningList);
        comboBoxTuning.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observable, String oldValue, String newValue) {
                if (newValue.equals("Add a tuning...")){
                    Stage importStage = new Stage();
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("FXML_Layouts/addTuning.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    importStage.setTitle("Add New Tuning");
                    importStage.setScene(new Scene(root, 505, 235));
                    importStage.show();
                }

            }
        });
        genreList = getData.populateGenreComboBox();
        comboBoxGenre.getItems().removeAll(comboBoxGenre.getItems());
        comboBoxGenre.getItems().addAll(genreList);
        comboBoxGenre.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observable, String oldValue, String newValue) {
                if (newValue.equals("Add a genre...")){
                    Stage importStage = new Stage();
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("FXML_Layouts/addGenre.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    importStage.setTitle("Add New Genre");
                    importStage.setScene(new Scene(root, 505, 235));
                    importStage.show();
                }

            }
        });

        gearList = getData.populateGearComboBox();

        comboBoxGear.getItems().removeAll(comboBoxGear.getItems());
        comboBoxGear.getItems().addAll(gearList);

        ObservableSet<String> observableSet = FXCollections.observableSet();

        comboBoxGear.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observable, String oldValue, String newValue)  {
                if (newValue.equals("Add new gear..")){
                    Stage importStage = new Stage();
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("FXML_Layouts/addGear.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    importStage.setTitle("Add New Gear");
                    importStage.setScene(new Scene(root, 505, 235));
                    importStage.show();

                }else{
                    observableSet.add(newValue);
                    listViewAddedGear.setItems(FXCollections.observableArrayList(observableSet));
                }


            }
        });



    }

}
