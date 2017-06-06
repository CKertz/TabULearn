package playSongTest;

import javazoom.jl.player.Player;

import java.io.FileInputStream;

/**
 * Created by Cooper on 5/9/2017.
 */
public class playSong {

    public static void main(String []args){



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
