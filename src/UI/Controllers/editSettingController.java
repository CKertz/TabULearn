package UI.Controllers;

import DB.dbConnect;
import Models.GearRecord;
import Models.LibraryRecord;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

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
    private Button btnExitEditSetting;

    ObservableList<String> gearList = FXCollections.observableArrayList();
    dbConnect getData = new dbConnect();
    @FXML
    public void initialize(){
        gearList = getData.populateGearComboBox();

        comboBoxGearToEdit.getItems().removeAll(comboBoxGearToEdit.getItems());
        comboBoxGearToEdit.getItems().addAll(gearList);

        ObservableSet<String> observableSet = FXCollections.observableSet();

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
    dbConnect loadGear = new dbConnect();
    ObservableList<GearRecord> data = FXCollections.observableArrayList();
    int songID;
    public void setSongID(int songID){
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
}
