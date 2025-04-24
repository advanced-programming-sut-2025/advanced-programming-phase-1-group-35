package Model.Buildings;

import Model.Coordination;

import java.awt.*;

public class Building {
    String name;
    protected Rectangle bounds = new Rectangle();
    int buildingID;
    protected int buildingIdCounter;

    public int getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(int buildingID) {
        this.buildingID = buildingID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
