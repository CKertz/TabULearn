package UI.Controllers;

import Models.LibraryRecord;
import com.sun.javafx.css.converters.DurationConverter;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

/**
 * Created by Cooper on 7/2/2017.
 */
public class editSongController extends Application{
    @FXML
    private Label labelEditSong;
    @FXML
    private TextField textFieldSelectedStart;
    @FXML
    private TextField textFieldSelectedEnd;
    @FXML
    private TextField textFieldTempoPercent;
    @FXML
    private CheckBox checkBoxTempo;
    @FXML
    private CheckBox checkBoxLoop;
    @FXML
    private Button btnExitEdit;
    MediaPlayer previewMedia = null;
    LibraryRecord testRecord = new LibraryRecord();
    String nowPlayingArtist = null;
    String nowPlayingSong = null;
    String songDuration = null;
    int tempoAdjustment = 0;
    boolean loopingActive = false;
    boolean tempoAdjustmentActive = false;

    @FXML
    public void start(Stage stage)throws Exception{
        //textFieldTempoPercent.setText("0");
    }
    public void initData(String nowPlayingArtist, String nowPlayingSong, String songDuration, LibraryRecord test){
        this.nowPlayingArtist = nowPlayingArtist;
        this.nowPlayingSong = nowPlayingSong;
        this.songDuration = songDuration;
        textFieldSelectedEnd.setText(songDuration);
        labelEditSong.setText(nowPlayingSong + " by " + nowPlayingArtist);
        //textFieldSelectedEnd.setText(songDuration.toString());
        testRecord = test;
    }
    @FXML
    public void exitEditSong(){
        Stage stage = (Stage) btnExitEdit.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void confirmEditChanges(){
        if (checkBoxLoop.isSelected() == true) {
            loopingActive = true;
        }
        if(checkBoxTempo.isSelected() == true){
            tempoAdjustmentActive = true;
            try{
                int tempo = Integer.parseInt(textFieldTempoPercent.getText());
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid tempo adjustment");
                alert.setHeaderText(null);
                alert.setContentText("No tempo adjustment selected, please try again.");
                alert.showAndWait();
            }

        }
    }
    public double parseMinutes(String given){
        double minutes = 0;

        return minutes;
    }
    @FXML public void previewChanges(){
        Media media = new Media(new File(testRecord.getURL()).toURI().toString());
        previewMedia = new MediaPlayer(media);
        String fullStartTime = textFieldSelectedStart.getText();
        String[] startParts = fullStartTime.split(":");
        String partMinutes = startParts[0];
        String partSeconds = startParts[1];

        previewMedia.setStartTime(Duration.seconds(Double.parseDouble(partSeconds)+Double.parseDouble(partMinutes)*60));
        String fullEndTime = textFieldSelectedEnd.getText();
        String[] endParts = fullEndTime.split(":");
        String endPartMinutes = endParts[0];
        String endPartSeconds = endParts[1];

        previewMedia.setStopTime(Duration.seconds(Double.parseDouble(endPartSeconds)+Double.parseDouble(endPartMinutes)*60));

        if (loopingActive == true){
            previewMedia.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    previewMedia.play();
                }
            });
        }
        if (checkBoxLoop.isSelected() == true){
            loopingActive = true;
            while (loopingActive == true)
            previewMedia.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {

                    previewMedia.play();
                }
            });
        }
        if (checkBoxTempo.isSelected() == true){
            previewMedia.setRate(Double.parseDouble(textFieldTempoPercent.getText())/100);

        }
        previewMedia.play();
    }
}
