package UI;

import Models.Song;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import DB.dbConnect;

/**
 * Created by Cooper on 5/14/2017.
 */
public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        primaryStage.setTitle("TabULearn");
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.show();
    }
/*    public static void main(String[] args) {




        dbConnect test = new dbConnect();
        test.populateLibraryComponents(); //just to test the select query. this has no practical use


        launch(args);
    }*/
}
