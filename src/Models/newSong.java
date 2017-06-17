package Models;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Cooper on 6/14/2017.
 */
public class newSong {
    private SimpleStringProperty songName;
    private SimpleStringProperty songArtist;
    private SimpleStringProperty songAlbum;

    public newSong(String songName, String songAlbum, String songArtist){
        this.songAlbum = new SimpleStringProperty(songAlbum);
        this.songArtist = new SimpleStringProperty(songArtist);
        this.songName = new SimpleStringProperty(songName);
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
}
