package Controller.InGameMenu;

import Model.App;
import Model.CropClasses.Crop;
import Model.CropClasses.GiantCrop;
import Model.CropClasses.Seed;
import Model.Fertilizer;
import Model.Result;
import Model.Tile;
import Model.enums.Crops.CropEnum;
import Model.enums.Crops.MixedSeeds;
import Model.enums.Crops.SeedEnum;
import Model.enums.Seasons;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FarmingController {
    private List<Tile> map = new ArrayList<Tile>();

    FarmingController(List<Tile> map) {
        this.map = map;
    }

    private boolean isFloorplowed(Tile tile) {
        return false;
    }

    private void plowFloor(Tile tile) {
        tile.setPlowed(true);
    }

    private Tile[] findTilesWithSameSeed(Tile tile) {
        Tile[] result = new Tile[4];
        result[0] = tile;
        Tile[][] map = App.getCurrentGame().getMap().getTiles();
        int x = tile.getCoordination().getX();
        int y = tile.getCoordination().getY();
        try{
            if((map[x+1][y+1].getPlanted() == tile.getPlanted())) {
                if (map[x + 1][y].getPlanted() == tile.getPlanted() && map[x][y + 1].getPlanted() == tile.getPlanted()) {
                    result[1] = map[x][y + 1];
                    result[2] = map[x + 1][y];
                    result[3] = map[x + 1][y + 1];
                    return result;
                }
            }
            if(map[x-1][y+1].getPlanted() == tile.getPlanted()){
                if(map[x-1][y].getPlanted() == tile.getPlanted() && map[x][y+1].getPlanted() == tile.getPlanted()){
                    result[1] = map[x-1][y];
                    result[2] = map[x][y+1];
                    result[3] = map[x-1][y+1];
                    return result;
                }
            }
            if(map[x-1][y-1].getPlanted() == tile.getPlanted()){
                if(map[x-1][y].getPlanted() == tile.getPlanted() && map[x][y-1].getPlanted() == tile.getPlanted()){
                    result[1] = map[x-1][y];
                    result[2] = map[x][y-1];
                    result[3] = map[x-1][y-1];
                    return result;
                }
            }
            if(map[x+1][y-1].getPlanted() == tile.getPlanted()){
                if(map[x+1][y].getPlanted() == tile.getPlanted() && map[x][y-1].getPlanted() == tile.getPlanted()){
                    result[1] = map[x+1][y];
                    result[2] = map[x][y-1];
                    result[3] = map[x-1][y+1];
                    return result;
                }
            }
        }catch(Exception e){
        }
        return null;
    }

    private Result plantSeed(Seed seed, Tile tile) {
    if (!isFloorplowed(tile)) {
    return new Result(false, "Floor is not plowed");
    }
        tile.setPlowed(false);
        tile.changeTileContents(new Crop(seed.getCropEnum()));
        if(findTilesWithSameSeed(tile) !=null){
            Crop temp = (Crop) tile.getPlanted();
            for(Tile tile1 : findTilesWithSameSeed(tile)) {
                Crop temp1 = (Crop) tile1.getPlanted();
                if(temp1.getCurrentState() > temp.getCurrentState()){
                   temp = temp1;
                }
            }
            GiantCrop giantCrop = new GiantCrop(temp,findTilesWithSameSeed(tile));
            for(Tile tile1 : findTilesWithSameSeed(tile)){
                tile1.setPlowed(false);
                tile1.changeTileContents(giantCrop);
            }
            return new Result(true, "seed planted,giant crop incoming");
        }
    return new Result(true, "seed planted");
    }

    private void showPlant(Tile tile) {
    }

    private void fertilize(Fertilizer fertilize, Tile tile) {
    }

    private void watering(Tile tile) {
    }

    private int waterAmount() {
        return 0;
    }

    private CropEnum harvest(Tile tile) {
        return null;
    }

    public static void crowAttack(int cropAmount) {
    }

    private static final Random random = new Random();

    public CropEnum MixedSeedCrop(Seasons season) {
        if (season == null) {
            return null;
        }
        try {
            MixedSeeds mixedSeeds = MixedSeeds.valueOf(season.name().toUpperCase()); // <-- toUpperCase here
            CropEnum[] possibleCrops = mixedSeeds.getPossibleCrops();
            if (possibleCrops.length == 0) {
                return null;
            }
            CropEnum randomCropEnum = possibleCrops[random.nextInt(possibleCrops.length)];
            return randomCropEnum.getCrop();
        } catch (IllegalArgumentException e) {
            return null; // if season does not match
        }
    }
}
