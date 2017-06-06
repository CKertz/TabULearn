package UI;

import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Cooper on 5/20/2017.
 */
public class chooseFile {

    private Desktop desktop = Desktop.getDesktop();
    final FileChooser fileChooser = new FileChooser();

    public String handle() {
        Stage stage = new Stage();
        configureFileChooser(fileChooser);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            return file.getAbsolutePath();
            // return openFile(file);
        }
        return null;
    }

    private static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("Import");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
    }
}


