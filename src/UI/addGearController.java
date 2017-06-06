package UI;

import DB.dbConnect;
import Models.Gear;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



/**
 * Created by Cooper on 5/27/2017.
 */

public class addGearController {
    @FXML
    private TextField textFieldMake;
    @FXML
    private TextField textFieldModel;
    @FXML
    private Button btnCancelGear;
    @FXML
    public void addNewGear(){

        Gear gear = new Gear();
        gear.setGearModel(textFieldModel.getText());
        gear.setGearMake(textFieldMake.getText());
        dbConnect DB = new dbConnect();
        DB.insertGearIntoDB(gear);
    }
    @FXML
    public void cancelGearAdd(ActionEvent event){

        Stage stage = (Stage) btnCancelGear.getScene().getWindow();
        stage.close();
    }

}
