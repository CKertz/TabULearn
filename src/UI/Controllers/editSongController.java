package UI.Controllers;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    private CheckBox checkBoxLooping;
    @FXML
    private Button btnExitEdit;

    String nowPlayingArtist = null;
    String nowPlayingSong = null;
    int songDuration = 0;
    int tempoAdjustment = 0;
    boolean loopingActive = false;

    @FXML
    public void start(Stage stage)throws Exception{
        textFieldTempoPercent.setText("0");

    }
    public void initData(String nowPlayingArtist, String nowPlayingSong){
        this.nowPlayingArtist = nowPlayingArtist;
        this.nowPlayingSong = nowPlayingSong;
        //songDuration = songDuration;
        labelEditSong.setText(nowPlayingSong + " by " + nowPlayingArtist);
        //textFieldSelectedEnd.setText(songDuration.toString());

    }
    @FXML
    public void exitEditSong(){
        Stage stage = (Stage) btnExitEdit.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void confirmEditChanges(){
        
    }
}
