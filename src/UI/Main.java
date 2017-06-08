package UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
