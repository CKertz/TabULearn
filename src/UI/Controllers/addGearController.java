package UI.Controllers;

import DB.dbConnect;
import Models.Gear;
import UI.Main;
import UI.mainMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


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
        DB.insertNewGearIntoDB(gear);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXML_Layouts/import.fxml"));
        Parent root;
        try{ //refresh import menu after importing a song to show it in the combobox
            root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage mainStage = new Stage();
            mainStage.setTitle("TabULearn");
            mainStage.setScene(scene);
            Stage exitStage = (Stage) btnCancelGear.getScene().getWindow();
            exitStage.close();
            mainMenuController.importStage.close();
            mainStage.show();
        }catch (IOException e){
            Logger.getLogger(mainMenuController.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    @FXML
    public void cancelGearAdd(ActionEvent event){

        Stage stage = (Stage) btnCancelGear.getScene().getWindow();
        stage.close();
    }

}
