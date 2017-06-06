package DB;

import Models.Gear;
import Models.Genre;
import Models.Song;
import Models.Tuning;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import playSongTest.playSong;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Cooper on 5/18/2017.
 */
public class dbConnect {

/*   public dbConnect(){
        selectAll();
    }*/

    private Connection connect(){

        String url = "jdbc:sqlite:C://sqlite/library.db";
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url);
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
    public void insertIntoLibrary(Song songToBeInserted){

        String query = "INSERT INTO Song (songName, songURL, songArtist, songAlbum) VALUES (?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, songToBeInserted.getSongName());
            pstmt.setString(2, songToBeInserted.getSongURL());
            pstmt.setString(3, songToBeInserted.getSongArtist());
            pstmt.setString(4, songToBeInserted.getSongAlbum());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insertGearIntoDB(Gear gearToBeInserted){
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
        String sql = "SELECT gearMake, gearModel FROM Gear";
        ObservableList<String> gearList = FXCollections.observableArrayList();
        ObservableList<Gear> gearList2 = FXCollections.observableArrayList();

        try(Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

            while (rs.next()){
                Gear tempGear = new Gear();
                tempGear.setGearMake(rs.getString("gearMake"));
                tempGear.setGearModel(rs.getString("gearModel"));
                gearList.add(tempGear.getGearMake() + " " + tempGear.getGearModel()); //populate arrayList with make/model pairs. ex: 'Roland Cube30'
                gearList2.add(tempGear);
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        //return gearList2;
        return gearList;
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
    public TableColumn populateLibraryComponents() throws Exception{


        //query 3 times, one for song details, for genre, for tuning
        String sql = "SELECT * FROM Song WHERE songName = 'Shoot to Thrill'";
        final ObservableList<Song> songList = FXCollections.observableArrayList();
        
        try(Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                System.out.println("Column ["+i+"] ");
                return col;
                //tableview.getColumns().addAll(col);

            }
            } catch (SQLException e){
                System.out.println(e.getMessage());
        }
        return null;
    }
}
