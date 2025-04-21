package Model.FarmStuff;

import Model.Tile;

import java.util.ArrayList;

public class Greenhouse extends Farm {
    private int rows = 6;
    private int cols = 7;
    private boolean isFixed = false;
    public static final int price = 1000;
    public static final int woodAmount = 500;
    Tile[][] tiles = new Tile[rows][cols];

    public boolean isFixed() {
        return isFixed;
    }

    public void setFixed(boolean fixed) {
        isFixed = fixed;
    }
    //TODO:add the watering and other farming related stuff after the farm part has been implemented
}
