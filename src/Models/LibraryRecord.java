package Models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Cooper on 6/8/2017.
 */
public class LibraryRecord {
    private SimpleStringProperty Title = new SimpleStringProperty();
    private SimpleStringProperty Artist = new SimpleStringProperty();;
    private SimpleStringProperty Album = new SimpleStringProperty();;
    private SimpleStringProperty Genre = new SimpleStringProperty();;
    private SimpleStringProperty Tuning = new SimpleStringProperty();;
    private SimpleStringProperty URL = new SimpleStringProperty();;
    private SimpleIntegerProperty ID = new SimpleIntegerProperty();


    public LibraryRecord(String title, String artist, String album, String url, String tuning, String genre, int id){
        this.Album = new SimpleStringProperty(album);
        this.Artist = new SimpleStringProperty(artist);
        this.Title = new SimpleStringProperty(title);
        this.URL = new SimpleStringProperty(url);
        this.Tuning = new SimpleStringProperty(tuning);
        this.Genre = new SimpleStringProperty(genre);
        this.ID = new SimpleIntegerProperty(id);
    }
    public LibraryRecord(String title, String artist, String album, String url, String tuning, String genre){
        this.Album = new SimpleStringProperty(album);
        this.Artist = new SimpleStringProperty(artist);
        this.Title = new SimpleStringProperty(title);
        this.URL = new SimpleStringProperty(url);
        this.Tuning = new SimpleStringProperty(tuning);
        this.Genre = new SimpleStringProperty(genre);
    }

    public int getID() {
        return ID.get();
    }

    public SimpleIntegerProperty IDProperty() {
        return ID;
    }

    public void setID(int ID) {
        this.ID.set(ID);
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
