package UI.Controllers;

import DB.dbConnect;
import Models.Genre;
import Models.Tuning;
import UI.mainMenuController;
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
 * Created by Cooper on 6/18/2017.
 */
public class addGenreController {
    @FXML
    private TextField textFieldGenre;
    @FXML
    private Button btnCancelGenre;
    @FXML
    private Button btnAddGenre;
    @FXML
    public void addNewGenre(){
        Genre genre = new Genre();
        genre.setGenreName(textFieldGenre.getText());
        dbConnect DB = new dbConnect();
        DB.insertGenreIntoDB(genre);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXML_Layouts/import.fxml"));
        Parent root;
        try{ //refresh import menu after importing a song to show it in the combobox
            root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage mainStage = new Stage();
            mainStage.setTitle("TabULearn");
            mainStage.setScene(scene);
            Stage exitStage = (Stage) btnCancelGenre.getScene().getWindow();
            exitStage.close();
            mainMenuController.importStage.close();
            mainStage.show();
        }catch (IOException e){
            Logger.getLogger(mainMenuController.class.getName()).log(Level.SEVERE,null,e);
        }

    }
    @FXML
    public void cancelGenreAdd(){
        Stage stage = (Stage) btnCancelGenre.getScene().getWindow();
        stage.close();
    }
}
