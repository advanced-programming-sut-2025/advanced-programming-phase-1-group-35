package Controller.InGameMenu;

import Model.Fertilizer;
import Model.Tile;
import Model.enums.Crops.Crop;
import Model.enums.Crops.Seed;

import java.util.ArrayList;
import java.util.List;

public class FarmingController {
    private List<Tile> map = new ArrayList<Tile>();
    FarmingController (List<Tile> map){
        this.map = map;
    }
    private boolean isFloorplowed(Tile tile){return false;}
    private void plowFloor(Tile tile){}
    private void plantSeed(Seed seed, Tile tile){}
    private void showPlant(Tile tile){}
    private void fertilize(Fertilizer fertilize, Tile tile){}
    private void watering(Tile tile){}
    private int waterAmount(){return 0;}
    private Crop harvest(Tile tile){return null;}
    public static void crowAttack(int cropAmount){}
    }

