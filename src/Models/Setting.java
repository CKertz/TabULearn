package Models;

/**
 * Created by Cooper on 5/18/2017.
 */
public class Setting {
    public String getSettingName() {
        return settingName;
    }

    public void setSettingName(String settingName) {
        this.settingName = settingName;
    }

    public String getSettingLocation() {
        return settingLocation;
    }

    public void setSettingLocation(String settingLocation) {
        this.settingLocation = settingLocation;
    }

    private String settingName;
    private String settingLocation;
}
