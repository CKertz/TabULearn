package UI.Controllers;

import DB.dbConnect;
import Models.Genre;
import Models.Tuning;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
        Stage stage = (Stage) btnAddGenre.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void cancelGenreAdd(){
        Stage stage = (Stage) btnCancelGenre.getScene().getWindow();
        stage.close();
    }
}
