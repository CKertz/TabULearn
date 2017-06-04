package Models;

/**
 * Created by Cooper on 5/18/2017.
 */
public class Gear {

    private int addedCount;
    private boolean inListView;//Used to prevent duplicates in the import scene in the "Added Gear" listview
    private int gearID;
    private String gearMake;
    private String gearModel;
    private int songID;
    private int settingID;

    public boolean isInListView() {
        return inListView;
    }

    public void setInListView(boolean inListView) {
        this.inListView = inListView;
    }

    public String getGearMake() {
        return gearMake;
    }

    public void setGearMake(String gearMake) {
        this.gearMake = gearMake;
    }

    public String getGearModel() {
        return gearModel;
    }

    public void setGearModel(String gearModel) {
        this.gearModel = gearModel;
    }
    public int getGearID() {
        return gearID;
    }

    public void setGearID(int gearID) {
        this.gearID = gearID;
    }

    public int getSongID() {
        return songID;
    }

    public void setSongID(int songID) {
        this.songID = songID;
    }

    public int getSettingID() {
        return settingID;
    }

    public void setSettingID(int settingID) {
        this.settingID = settingID;
    }
}
