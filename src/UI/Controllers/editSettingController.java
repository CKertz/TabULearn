package UI.Controllers;

import DB.dbConnect;
import Models.GearRecord;
import Models.LibraryRecord;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Cooper on 7/7/2017.
 */
public class editSettingController {
    @FXML
    private ComboBox comboBoxGearToEdit;
    @FXML
    private TableView<GearRecord> tableViewEditSetting;
    @FXML
    private TableColumn<GearRecord, String> colGearEdit;
    @FXML
    private TableColumn<LibraryRecord, String> colSettingEdit;
    @FXML
    private TextField textFieldEffect;
    @FXML
    private TextField textFieldIntensity;
    @FXML
    private Button btnExitEditSetting;

    ObservableList<String> gearList = FXCollections.observableArrayList();
    dbConnect getData = new dbConnect();
    dbConnect loadGear = new dbConnect();
    ObservableList<GearRecord> data = FXCollections.observableArrayList();
    int songID;
    @FXML
    public void initialize(){
        gearList = getData.populateGearComboBox();

        comboBoxGearToEdit.getItems().removeAll(comboBoxGearToEdit.getItems());
        comboBoxGearToEdit.getItems().addAll(gearList);


        comboBoxGearToEdit.valueProperty().addListener(new ChangeListener<String>() {
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

                }
            }
        });
    }
    @FXML
    public void addSettingChanges() {
        try{
            loadGear.insertSettingIntoDB(songID,(comboBoxGearToEdit.getSelectionModel().getSelectedItem().toString()),textFieldEffect.getText(),textFieldIntensity.getText());
            Stage stage = (Stage) btnExitEditSetting.getScene().getWindow();
            stage.close();
        }catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill in all fields before confirming changes.");
            alert.showAndWait();
        }

    }
    @FXML
    public void removeSetting() throws Exception {

        getData.deleteGear(songID,formatSettingName(tableViewEditSetting.getSelectionModel().getSelectedItem().getSetting()));

    }
    public void setSongID(int songID){ // passed from tabView
        this.songID = songID;

        try {
            data = loadGear.populateTabviewTable(songID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        colGearEdit.setCellValueFactory(new PropertyValueFactory<>("Gear"));
        colSettingEdit.setCellValueFactory(new PropertyValueFactory<>("Setting"));
        tableViewEditSetting.setItems(data);
    }
    @FXML
    public void exitEditSetting(){
        Stage stage = (Stage) btnExitEditSetting.getScene().getWindow();
        stage.close();
    }
    public String formatSettingName(String given){//table presents data in format of "Bass:2", or similar. reformat it to only contain "Bass" to allow for easier DB deletion
        String result = "";
        for (int i = 0; i < given.length(); i++) {
            if (given.charAt(i) != ':' && Character.isDigit(given.charAt(i)) == false ){
                result+=given.charAt(i);
            }
        }
        return result;
    }
}
