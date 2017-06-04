package UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
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
    public void importSong() throws Exception{ //pressing Import Button at the main menu will run this code


        Stage importStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("import.fxml"));
        importStage.setTitle("Import");
        importStage.setScene(new Scene(root, 505, 300));
        importStage.show();


    }
    @FXML
    public void initialize(){


    }

}
