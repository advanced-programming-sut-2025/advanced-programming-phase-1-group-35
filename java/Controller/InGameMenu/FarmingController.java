package Controller.InGameMenu;

import Model.Fertilizer;
import Model.Floor;
import models.enums.Crops.Crop;
import models.enums.Crops.Seed;

import java.util.ArrayList;
import java.util.List;

public class FarmingController {
    private List<Floor> map = new ArrayList<Floor>();
    FarmingController (List<Floor> map){
        this.map = map;
    }
    private boolean isFloorplowed(Floor tile){return false;}
    private void plowFloor(Floor tile){}
    private void plantSeed(Seed seed, Floor tile){}
    private void showPlant(Floor floor){}
    private void fertilize(Fertilizer fertilize, Floor tile){}
    private void watering(Floor tile){}
    private int waterAmount(){return 0;}
    private Crop harvest(Floor tile){return null;}
    public static void crowAttack(int cropAmount){}
    }

