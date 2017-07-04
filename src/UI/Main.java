package UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Cooper on 5/14/2017.
 */
public class Main extends Application{
    public static Stage publicStage = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("FXML_Layouts/mainMenu.fxml"));
        primaryStage.setTitle("TabULearn");
        primaryStage.setScene(new Scene(root, 1200, 800));
        publicStage = primaryStage;
        primaryStage.show();
    }
/*    public static void main(String[] args) {




        dbConnect test = new dbConnect();
        test.populateLibraryComponents(); //just to test the select query. this has no practical use


        launch(args);
    }*/
}
