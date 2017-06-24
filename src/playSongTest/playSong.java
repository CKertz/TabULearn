package playSongTest;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javazoom.jl.player.Player;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by Cooper on 5/9/2017.
 */
public class playSong {

    public static void main(String []args){

    playSong test = new playSong();
            test.playTest("C:/Users/Cooper/Desktop/01_courage.mp3");

    }
    public void playTest(String song){
        Media title = new Media(new File(song).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(title);
        mediaPlayer.play();
    }
    public void playMySong(String songURL){
        try{

            FileInputStream fis = new FileInputStream(songURL);
            Player playMp3 = new Player(fis);
            playMp3.play();

        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
