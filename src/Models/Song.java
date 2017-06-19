package Models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Cooper on 5/18/2017.
 */
public class Song {


    public Song(){

    }

    public Song (String songName, String songArtist, String songAlbum){

        setSongName(songName);
        setSongArtist(songArtist);
        setSongAlbum(songAlbum);

    }
    public Song (String songURL){
        setSongURL(songURL);
    }
    public Song(String songName, String songURL, String songArtist, String songAlbum){
        setSongName(songName);
        setSongArtist(songArtist);
        setSongAlbum(songAlbum);
        setSongURL(songURL);
    }
    public Song(String songName, String songURL, String songArtist, String songAlbum, String genre){
        setSongName(songName);
        setSongArtist(songArtist);
        setSongAlbum(songAlbum);
        setSongURL(songURL);
        setSongGenre(genre);

    }

    private SimpleIntegerProperty songID;
    private SimpleStringProperty songName;
    private SimpleStringProperty songURL;
    private SimpleStringProperty songArtist;
    private SimpleStringProperty songAlbum;
    private SimpleIntegerProperty tuningID;
    private SimpleIntegerProperty genreID;
    private SimpleIntegerProperty gearID;
    private SimpleStringProperty songGenre;

    public String getSongGenre() {
        return songGenre.get();
    }

    public SimpleStringProperty songGenreProperty() {
        return songGenre;
    }

    public void setSongGenre(String songGenre) {
        this.songGenre.set(songGenre);
    }

    public int getSongID() {
        return songID.get();
    }

    public SimpleIntegerProperty songIDProperty() {
        return songID;
    }

    public void setSongID(int songID) {
        this.songID.set(songID);
    }

    public String getSongName() {
        return songName.get();
    }

    public SimpleStringProperty songNameProperty() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName.set(songName);
    }

    public String getSongURL() {
        return songURL.get();
    }

    public SimpleStringProperty songURLProperty() {
        return songURL;
    }

    public void setSongURL(String songURL) {
        this.songURL.set(songURL);
    }

    public String getSongArtist() {
        return songArtist.get();
    }

    public SimpleStringProperty songArtistProperty() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist.set(songArtist);
    }

    public String getSongAlbum() {
        return songAlbum.get();
    }

    public SimpleStringProperty songAlbumProperty() {
        return songAlbum;
    }

    public void setSongAlbum(String songAlbum) {
        this.songAlbum.set(songAlbum);
    }

    public int getTuningID() {
        return tuningID.get();
    }

    public SimpleIntegerProperty tuningIDProperty() {
        return tuningID;
    }

    public void setTuningID(int tuningID) {
        this.tuningID.set(tuningID);
    }

    public int getGenreID() {
        return genreID.get();
    }

    public SimpleIntegerProperty genreIDProperty() {
        return genreID;
    }

    public void setGenreID(int genreID) {
        this.genreID.set(genreID);
    }

    public int getGearID() {
        return gearID.get();
    }

    public SimpleIntegerProperty gearIDProperty() {
        return gearID;
    }

    public void setGearID(int gearID) {
        this.gearID.set(gearID);
    }
}
