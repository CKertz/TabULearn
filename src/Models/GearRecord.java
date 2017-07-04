package Models;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Cooper on 7/3/2017.
 */
public class GearRecord {

    public String getSetting() {
        return Setting.get();
    }

    public SimpleStringProperty settingProperty() {
        return Setting;
    }

    public void setSetting(String setting) {
        this.Setting.set(setting);
    }

    public String getGear() {
        return Gear.get();
    }

    public SimpleStringProperty gearProperty() {
        return Gear;
    }

    public void setGear(String gear) {
        this.Gear.set(gear);
    }

    private SimpleStringProperty Setting = new SimpleStringProperty();
    private SimpleStringProperty Gear = new SimpleStringProperty();

    private SimpleStringProperty settingName = new SimpleStringProperty();
    private SimpleStringProperty settingLocation = new SimpleStringProperty();
    private SimpleStringProperty gearMake = new SimpleStringProperty();
    private SimpleStringProperty gearModel = new SimpleStringProperty();

    public GearRecord(String settingName, String settingLocation, String gearMake, String gearModel, String gearMakeModel, String settingNameLocation){
        this.settingName = new SimpleStringProperty(settingName);
        this.settingLocation = new SimpleStringProperty(settingLocation);
        this.gearMake = new SimpleStringProperty(gearMake);
        this.gearModel = new SimpleStringProperty(gearModel);
        this.Gear = new SimpleStringProperty(gearMakeModel);
        this.Setting = new SimpleStringProperty(settingNameLocation);

    }

    public String getSettingName() {
        return settingName.get();
    }

    public SimpleStringProperty settingNameProperty() {
        return settingName;
    }

    public void setSettingName(String settingName) {
        this.settingName.set(settingName);
    }

    public String getSettingLocation() {
        return settingLocation.get();
    }

    public SimpleStringProperty settingLocationProperty() {
        return settingLocation;
    }

    public void setSettingLocation(String settingLocation) {
        this.settingLocation.set(settingLocation);
    }

    public String getGearMake() {
        return gearMake.get();
    }

    public SimpleStringProperty gearMakeProperty() {
        return gearMake;
    }

    public void setGearMake(String gearMake) {
        this.gearMake.set(gearMake);
    }

    public String getGearModel() {
        return gearModel.get();
    }

    public SimpleStringProperty gearModelProperty() {
        return gearModel;
    }

    public void setGearModel(String gearModel) {
        this.gearModel.set(gearModel);
    }


}
