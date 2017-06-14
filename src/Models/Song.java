package Models;

/**
 * Created by Cooper on 5/18/2017.
 */
public class Song {
    public int getSongID() {
        return songID;
    }

    public void setSongID(int songID) {
        this.songID = songID;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongURL() {
        return songURL;
    }

    public void setSongURL(String songURL) {
        this.songURL = songURL;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public String getSongAlbum() {
        return songAlbum;
    }

    public void setSongAlbum(String songAlbum) {
        this.songAlbum = songAlbum;
    }

    public int getTuningID() {
        return tuningID;
    }

    public void setTuningID(int tuningID) {
        this.tuningID = tuningID;
    }

    public int getGenreID() {
        return genreID;
    }

    public void setGenreID(int genreID) {
        this.genreID = genreID;
    }

    public int getGearID() {
        return gearID;
    }

    public void setGearID(int gearID) {
        this.gearID = gearID;
    }

    public Song (){

    }
    public Song (String songURL){
        this.songURL = songURL;
    }
    public Song(String songName, String songURL, String songArtist, String songAlbum){
        this.songAlbum = songAlbum;
        this.songName = songName;
        this.songURL = songURL;
        this.songArtist = songArtist;
    }

    private int songID;
    private String songName;
    private String songURL;
    private String songArtist;
    private String songAlbum;
    private int tuningID;
    private int genreID;
    private int gearID;

}
