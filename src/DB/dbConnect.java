package DB;

import Models.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import playSongTest.playSong;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cooper on 5/18/2017.
 */
public class dbConnect {

/*   public dbConnect(){
        selectAll();
    }*/

    private Connection connect(){

        //String url = "jdbc:sqlite:D://sqlite/library.db";
        String testurl = "jdbc:sqlite:D://Old PC Transfer//Old PC Transfer/SQLite/library.db";
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(testurl);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public void insertTuningIntoDB(Tuning tuningToBeInserted){
        String query = "INSERT INTO Tuning (tuningName) VALUES (?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, tuningToBeInserted.getTuningName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insertGenreIntoDB(Genre genreToBeInserted){
        String query = "INSERT INTO Genre (genreName) VALUES (?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, genreToBeInserted.getGenreName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public int getSongID(String url){
        String sql = "SELECT songID FROM Song WHERE songURL = '"+url+"'";
        int result = 0;
        try(Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

            while (rs.next()){
                result = rs.getInt("songID");
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return result;
    }
    public String getTuningNameFromDB(int tuningID){

        String sql = "SELECT tuningName FROM Tuning WHERE tuningID = ?";
        String result = null;
        try {
            Connection conn = connect();
            PreparedStatement ptsmt = conn.prepareStatement(sql);
            ptsmt.setInt(1,tuningID);
            ResultSet rs = ptsmt.executeQuery();
            while (rs.next()){
                result = rs.getString("tuningName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
    public String getGenreNameFromDB(int genreID){

        String sql = "SELECT genreName FROM Genre WHERE genreID = ?";
        String result = null;
        try {
            Connection conn = connect();
            PreparedStatement ptsmt = conn.prepareStatement(sql);
            ptsmt.setInt(1,genreID);
            ResultSet rs = ptsmt.executeQuery();
            while (rs.next()){
                result = rs.getString("genreName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
    public int getTuningIDFromDB(String tuning){

        String sql = "SELECT tuningID FROM Tuning WHERE tuningName = ?";
        int result = 0;
        try {
            Connection conn = connect();
            PreparedStatement ptsmt = conn.prepareStatement(sql);
            ptsmt.setString(1,tuning);
            ResultSet rs = ptsmt.executeQuery();
            while (rs.next()){
                result = rs.getInt("tuningID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
    public int getGenreID(String genre){

        String sql = "SELECT genreID FROM Genre WHERE genreName = ?";
        int result = 0;
        try {
            Connection conn = connect();
            PreparedStatement ptsmt = conn.prepareStatement(sql);
            ptsmt.setString(1,genre);
            ResultSet rs = ptsmt.executeQuery();
            while (rs.next()){
                result = rs.getInt("genreID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
    public void insertIntoLibrary(Song songToBeInserted, List<String> gearList){

            String songURLInserted = songToBeInserted.getSongURL();

            String query = "INSERT INTO Song (songName, songURL, songArtist, songAlbum, genreID, tuningID) VALUES (?,?,?,?,?,?)";

            try (Connection conn = this.connect();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                 pstmt.setString(1, songToBeInserted.getSongName());
                 pstmt.setString(2, songToBeInserted.getSongURL());
                 pstmt.setString(3, songToBeInserted.getSongArtist());
                 pstmt.setString(4, songToBeInserted.getSongAlbum());
                 pstmt.setInt(5, songToBeInserted.getGenreID());
                 pstmt.setInt(6, songToBeInserted.getTuningID());

                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }



    }
    public void insertGearLayoutIntoDB(List<String> gearList, int songID){
        String query = "INSERT INTO GearLayout (gearPiece, songID) VALUES (?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            for (int i = 0; i < gearList.size(); i++){
                pstmt.setString(1,gearList.get(i));
                pstmt.setInt(2, songID);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insertNewGearIntoDB(Gear gearToBeInserted){
        String query = "INSERT INTO Gear (gearMake, gearModel) VALUES (?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, gearToBeInserted.getGearMake());
            pstmt.setString(2, gearToBeInserted.getGearModel());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ObservableList<String> populateGearComboBox(){
        String sql = "SELECT distinct gearMake, gearModel FROM Gear";
        ObservableList<String> gearList = FXCollections.observableArrayList();

        try(Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

            while (rs.next()){
                Gear tempGear = new Gear();
                tempGear.setGearMake(rs.getString("gearMake"));
                tempGear.setGearModel(rs.getString("gearModel"));
                gearList.add(tempGear.getGearMake() + " " + tempGear.getGearModel()); //populate arrayList with make/model pairs. ex: 'Roland Cube30'

            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return gearList;
    }
    public ObservableList<String> populateGenreComboBox(){

        String sql = "SELECT genreName FROM Genre";
        final ObservableList<String> genreList = FXCollections.observableArrayList();

        try(Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

            while (rs.next()){
                genreList.add(rs.getString("genreName"));

            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return genreList;
    }
    public ObservableList<String> populateTuningComboBox(){

        String sql = "SELECT tuningName FROM Tuning";
        final ObservableList<String> tuningList = FXCollections.observableArrayList();

        try(Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

            while (rs.next()){
                tuningList.add(rs.getString("tuningName"));

            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return tuningList;
    }
    public ObservableList<Song> populateLibraryComponents() throws Exception{


        //query 3 times, one for song details, for genre, for tuning
        String sql = "SELECT * FROM Song";
        final ObservableList<Song> songList = FXCollections.observableArrayList();
        
        try(Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

            while (rs.next()){
                Song tempSong = new Song();
                tempSong.setSongName(rs.getString("songName"));
                tempSong.setSongArtist(rs.getString("songArtist"));
                tempSong.setSongAlbum(rs.getString("songAlbum"));
                songList.add(tempSong);
            }

/*            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                System.out.println("Column ["+i+"] ");
                //return col;
                //tableview.getColumns().addAll(col);

            }*/
            } catch (SQLException e){
                System.out.println(e.getMessage());
        }
        return songList;
    }
    public ObservableList<LibraryRecord> populateLibraryRecords() throws Exception{


        //query 3 times, one for song details, for genre, for tuning
        String sql = "SELECT * FROM Song";
        final ObservableList<LibraryRecord> recordList = FXCollections.observableArrayList();

        try(Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

            while (rs.next()){

                int tuningID = rs.getInt("tuningID");
                String tuning = getTuningNameFromDB(tuningID);
                int genreID = rs.getInt("genreID");
                String genre = getGenreNameFromDB(genreID);
                String title = rs.getString("songName");
                String artist = rs.getString("songArtist");
                String album = rs.getString("songAlbum");
                String url = rs.getString("songURL");
                LibraryRecord tempRecord = new LibraryRecord(title,artist,album,url,tuning,genre);
                recordList.add(tempRecord);
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return recordList;
    }

    public ObservableList<newSong> newPopulateLibraryComponents() throws Exception{ //@TODO to be deleted once LibraryRecord is implemented


        //query 3 times, one for song details, for genre, for tuning
        String sql = "SELECT * FROM Song";
        final ObservableList<newSong> songList = FXCollections.observableArrayList();

        try(Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

            while (rs.next()){

                String title = rs.getString("songName");
                String artist = rs.getString("songArtist");
                String album = rs.getString("songAlbum");
                newSong newSong = new newSong(title,album,artist);
                songList.add(newSong);
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return songList;
    }
}
