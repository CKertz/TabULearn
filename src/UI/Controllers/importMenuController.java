package UI.Controllers;

import DB.dbConnect;
import Models.Song;

import UI.Main;
import UI.chooseFile;
import UI.mainMenuController;
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
import javafx.util.Duration;


import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by Cooper on 5/15/2017.
 */
public class importMenuController {


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
    public void importSong() throws Exception{
        List<String>gearAdded = listViewAddedGear.getItems();
        dbConnect insertingSong = new dbConnect();
        int genreId =0;
        int tuningId = 0;
        try{
            genreId = insertingSong.getGenreID(comboBoxGenre.getSelectionModel().getSelectedItem().toString());
        }catch (NullPointerException e){
            genreId = 0;
        }
        try{
            tuningId = insertingSong.getTuningIDFromDB(comboBoxTuning.getSelectionModel().getSelectedItem().toString());
        }catch (NullPointerException e){
            tuningId = 0;
        }
        try{
            textFieldSongURL.getText().equals(null);
        }catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Location Needed");
            alert.setContentText("Song location is required for importing a song. Please fill in a location and try again.");
            alert.showAndWait();
        }

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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXML_Layouts/mainMenu.fxml"));
        Parent root;
        try{ //refresh main menu after importing a song to show it in the tableview
            root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage mainStage = new Stage();
            mainStage.setTitle("TabULearn");
            mainStage.setScene(scene);
            Stage exitStage = (Stage) btnCancel.getScene().getWindow();
            exitStage.close();
            Main.publicStage.close();
            mainStage.show();
        }catch (IOException e){
            Logger.getLogger(mainMenuController.class.getName()).log(Level.SEVERE,null,e);
        }

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

        //Handling tuning combobox interaction
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
                        root = FXMLLoader.load(getClass().getResource("../FXML_Layouts/addTuning.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    importStage.setTitle("Add New Tuning");
                    importStage.setScene(new Scene(root, 505, 235));
                    importStage.show();
                }

            }
        });

        //Handling genre combobox interaction
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
                        root = FXMLLoader.load(getClass().getResource("../FXML_Layouts/addGenre.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    importStage.setTitle("Add New Genre");
                    importStage.setScene(new Scene(root, 505, 235));
                    importStage.show();
                }

            }
        });

        //Handling "Added Gear" listview/combobox interaction
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
                        root = FXMLLoader.load(getClass().getResource("../FXML_Layouts/addGear.fxml"));
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
