package Model.Buildings;

import Model.Coordination;
import Model.FarmStuff.Farm;
import Model.Tile;

import java.awt.*;

public class Building {
    protected Farm farm ;
    String name;
    protected Rectangle bounds = new Rectangle();
    int buildingID;
    protected int buildingIdCounter;

    public void placeBuilding(char symbol , int x , int y , int width , int height , Tile[][] tiles){
        bounds.setBounds(farm.getBounds().x + x , farm.getBounds().y + y , width , height);
        for (int i = bounds.x ; i < bounds.x + bounds.width ; i++) {
            for (int j = bounds.y ; j < bounds.y + bounds.height ; j++) {
                tiles[i][j].setSymbol(symbol);
                tiles[j][i].setWalkable(false);
            }
        }
    }

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
