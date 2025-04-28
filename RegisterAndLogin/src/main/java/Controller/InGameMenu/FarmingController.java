package Controller.InGameMenu;

import Model.App;
import Model.CropClasses.Crop;
import Model.CropClasses.Seed;
import Model.CropClasses.Tree;
import Model.Fertilizer;
import Model.Result;
import Model.Tile;
import Model.enums.Crops.CropEnum;
import Model.enums.Crops.MixedSeeds;
import Model.enums.Crops.TreeEnum;
import Model.enums.Seasons;
import java.util.Random;

public class FarmingController {
    private Tile[][] map = new Tile[300][250];

    public FarmingController(Tile[][] map) {
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
        try {
            if (map[x+1][y+1].getPlanted() != null && map[x+1][y].getPlanted() != null && map[x][y+1].getPlanted() != null) {
                if (sameCrop(tile, map[x+1][y+1]) && sameCrop(tile, map[x+1][y]) && sameCrop(tile, map[x][y+1])) {
                    result[1] = map[x][y+1];
                    result[2] = map[x+1][y];
                    result[3] = map[x+1][y+1];
                    return result;
                }
            }
            if (map[x-1][y+1].getPlanted() != null && map[x-1][y].getPlanted() != null && map[x][y+1].getPlanted() != null) {
                if (sameCrop(tile, map[x-1][y+1]) && sameCrop(tile, map[x-1][y]) && sameCrop(tile, map[x][y+1])) {
                    result[1] = map[x-1][y];
                    result[2] = map[x][y+1];
                    result[3] = map[x-1][y+1];
                    return result;
                }
            }
            if (map[x-1][y-1].getPlanted() != null && map[x-1][y].getPlanted() != null && map[x][y-1].getPlanted() != null) {
                if (sameCrop(tile, map[x-1][y-1]) && sameCrop(tile, map[x-1][y]) && sameCrop(tile, map[x][y-1])) {
                    result[1] = map[x-1][y];
                    result[2] = map[x][y-1];
                    result[3] = map[x-1][y-1];
                    return result;
                }
            }
            if (map[x+1][y-1].getPlanted() != null && map[x+1][y].getPlanted() != null && map[x][y-1].getPlanted() != null) {
                if (sameCrop(tile, map[x+1][y-1]) && sameCrop(tile, map[x+1][y]) && sameCrop(tile, map[x][y-1])) {
                    result[1] = map[x+1][y];
                    result[2] = map[x][y-1];
                    result[3] = map[x+1][y-1];
                    return result;
                }
            }
        } catch (Exception e) {
            // Just ignore out of bounds
        }
        return null;
    }

    private boolean sameCrop(Tile a, Tile b) {
        if (a.getPlanted() == null || b.getPlanted() == null) return false;
        return ((Crop)a.getPlanted()).getName().equals(((Crop)b.getPlanted()).getName());
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
            for(Tile tile1 : findTilesWithSameSeed(tile)){
                tile1.setPlowed(false);
                tile1.changeTileContents(temp);
                temp.setGiant(true);
                //TODO:add this crop and any other planted crop to farms planted arraylist
            }
            return new Result(true, "seed planted,giant crop incoming");
        }
    return new Result(true, "seed planted");
    }

    private void fertilize(Fertilizer fertilize, Tile tile) {
        tile.setFertilized(true);
    }

    private void watering(Tile tile) {
        tile.setWatered(true);
    }

    private int waterAmount() {
        return 0;
    }

    private void harvestCrop(Tile tile) {
        if (tile.getPlanted().getClass() == Crop.class) {
            Crop crop = (Crop) tile.getPlanted();
            App.getCurrentGame().getPlayingUser().getInventory().addItem(crop);
            if (crop.isGiant()) {
            for(int i = 0; i < 3 ; i++){
                App.getCurrentGame().getPlayingUser().getInventory().addItem(crop);
                App.getCurrentGame().getPlayingUser().getInventory().addItem(crop.HarvestAndDropSeed());
            if(crop.isOneTime()){
                crop.getCropTile().setPlanted(null);
            }
            }
        }
            crop.getCropTile().setPlanted(null);
        }
    }
    public static void crowAttack(int cropAmount) {
//        if(App.getCurrentGame().getMap().getFarms().get
        //TODO
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
            return null;
        }
    }
    public void addForAgingTree(){
    Random random1 = new Random();
    for(Tile[] tile1 : App.getCurrentGame().getMap().getTiles()){
        for (Tile tile : tile1) {
            if (tile.getPlanted() == null && tile.isPlowed()){
                if(random1.nextInt(100) < 1){
                    Tree tree = new Tree(TreeEnum.getRandomForagingTree());
                    tile.setPlanted(tree);
                }
            }
        }
    }
    }
    public void addForagingCrop(){

    }
}
