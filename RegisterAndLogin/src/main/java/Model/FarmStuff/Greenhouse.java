package Model.FarmStuff;

import Model.Buildings.Building;
import Model.Tile;

import java.util.ArrayList;

public class Greenhouse extends Building {
    private final int rows = 5;
    private final int cols = 6;
    private boolean isFixed = false; // when fixed , floorTiles should be set walkable;
    public static final int price = 1000;
    public static final int woodAmount = 500;

    public Greenhouse(Farm farm , Tile[][] tiles) {
        floorTiles = new Tile[cols][rows];
        super.farm = farm;
        doorTile = tiles[farm.getBounds().x + 27][farm.getBounds().y + 13];
        placeBuilding('@' , 23 , 6 , 8 , 7 , tiles);
    }


    public boolean isFixed() {
        return isFixed;
    }

    public void setFixed(boolean fixed) {
        isFixed = fixed;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }
    //TODO:add the watering and other farming related stuff after the farm part has been implemented
}

