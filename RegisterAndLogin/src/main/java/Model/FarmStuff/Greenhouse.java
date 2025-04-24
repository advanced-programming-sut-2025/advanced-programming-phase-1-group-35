package Model.FarmStuff;

import Model.Buildings.Building;
import Model.Tile;

import java.util.ArrayList;

public class Greenhouse extends Building {
    private int rows = 6;
    private int cols = 7;
    private boolean isFixed = false;
    public static final int price = 1000;
    public static final int woodAmount = 500;
    Tile[][] tiles = new Tile[rows][cols];

    public Greenhouse(Farm farm , Tile[][] tiles) {
        super.farm = farm;
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

