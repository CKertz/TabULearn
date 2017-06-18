package Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Cooper on 6/8/2017.
 */
public class LibraryRecord {
    private SimpleStringProperty Title;
    private SimpleStringProperty Artist;
    private SimpleStringProperty Album;
    private SimpleStringProperty Genre;
    private SimpleStringProperty Tuning;
    private SimpleStringProperty URL;


    public LibraryRecord(String title, String artist, String album, String url){
        this.Album = new SimpleStringProperty(album);
        this.Artist = new SimpleStringProperty(artist);
        this.Title = new SimpleStringProperty(title);
        this.URL = new SimpleStringProperty(url);
    }

    public String getTitle() {
        return Title.get();
    }

    public StringProperty titleProperty() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title.set(title);
    }

    public String getArtist() {
        return Artist.get();
    }

    public StringProperty artistProperty() {
        return Artist;
    }

    public void setArtist(String artist) {
        this.Artist.set(artist);
    }

    public String getAlbum() {
        return Album.get();
    }

    public StringProperty albumProperty() {
        return Album;
    }

    public void setAlbum(String album) {
        this.Album.set(album);
    }

    public String getGenre() {
        return Genre.get();
    }

    public StringProperty genreProperty() {
        return Genre;
    }

    public void setGenre(String genre) {
        this.Genre.set(genre);
    }

    public String getTuning() {
        return Tuning.get();
    }

    public StringProperty tuningProperty() {
        return Tuning;
    }

    public void setTuning(String tuning) {
        this.Tuning.set(tuning);
    }

    public String getURL() {
        return URL.get();
    }

    public StringProperty URLProperty() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL.set(URL);
    }
}