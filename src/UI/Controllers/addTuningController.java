package UI.Controllers;

import DB.dbConnect;
import Models.Gear;
import Models.Tuning;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 * Created by Cooper on 5/27/2017.
 */

public class addTuningController {
    @FXML
    private TextField textFieldTuning;
    @FXML
    private Button btnCancelTuning;
    @FXML
    private Button btnAddTuning;
    @FXML
    public void addNewTuning(){
        Tuning tuning = new Tuning();
        tuning.setTuningName(textFieldTuning.getText());
        dbConnect DB = new dbConnect();
        DB.insertTuningIntoDB(tuning);
        Stage stage = (Stage) btnAddTuning.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void cancelTuningAdd(){
        Stage stage = (Stage) btnCancelTuning.getScene().getWindow();
        stage.close();
    }
}
