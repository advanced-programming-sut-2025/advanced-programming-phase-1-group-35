package Controller.InGameMenu;

import Model.App;
import Model.CropClasses.Crop;
import Model.CropClasses.Sapling;
import Model.CropClasses.Seed;
import Model.CropClasses.Tree;
import Model.Fertilizer;
import Model.Result;
import Model.Tile;
import Model.Tools.Tool;
import Model.enums.Crops.*;
import Model.enums.Seasons;
import java.util.Random;

public class FarmingController {
    private Tile[][] map;

    public FarmingController(Tile[][] map) {
        this.map = map;
    }

    private boolean isFloorplowed(Tile tile) {
        return tile.isPlowed();
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


    public Result plantSeed(String seedName, String direction) {
        Seed seed = null;
        Tile tile;
        Tile[][] map = App.getCurrentGame().getMap().getTiles();
        if(!direction.matches("up|down|left|right|\\d+ \\d+")) {
            return new Result(false, "Invalid direction,you can use\"up\",\"down\",\"left\",\"right\"");
        }
        switch (direction) {
            case "up":
                tile = map[App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().getX()]
                          [App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().getY()+1];
        break;
            case "down":
                tile = map[App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().x]
                        [App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().getY()-1];
                break;
            case "left":
                tile = map[App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().x-1]
                        [App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().getY()];
            break;
            case "right":
                tile = map[App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().x+1]
                        [App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().getY()];
                break;
            case "here":
                tile = App.getCurrentGame().getPlayingUser().getCurrentTile();
                break;
            default:
                String[] parts = direction.split(" ");
                tile = map[Integer.parseInt(parts[0])][Integer.parseInt(parts[1])];
        }
        for(SeedEnum seedEnum : SeedEnum.values()) {
            if(seedEnum.name().equals(seedName)){
                seed = new Seed(seedEnum,tile);
                break;
            }
        }
        if(seed == null) {
            return new Result(false, "please enter a valid seed name");
        }
        if(!App.getCurrentGame().getPlayingUser().getInventory().itemInterfaces.contains(seed)) {
            return new Result(false, "you don't have the required seed in your inventory");
        }

    if (!isFloorplowed(tile)) {
    return new Result(false, "Floor is not plowed");
    }
    //TODO:we just need to reduce the amount by one not get rid of it completely in the inventory
    App.getCurrentGame().getPlayingUser().getInventory().itemInterfaces.remove(seed);
        tile.setPlowed(false);
        Crop crop = new Crop(seed.getCropEnum());
        tile.changeTileContents(crop);
        App.getCurrentGame().getMap().AddCrop(crop);
        App.getCurrentGame().getPlayingUser().getFarm().AddCrop(crop);
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
            }
            return new Result(true, "seed planted,giant crop incoming");
        }
    return new Result(true, "seed planted");
    }

    public Result plantSapling(String saplingName, String direction) {
        Sapling sapling = null;
        Tile tile = null;
        switch (direction) {
            case "up":
                tile = map[App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().getX()]
                        [App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().getY()+1];
                break;
            case "down":
                tile = map[App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().x]
                        [App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().getY()-1];
                break;
            case "left":
                tile = map[App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().x-1]
                        [App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().getY()];
                break;
            case "right":
                tile = map[App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().x+1]
                        [App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().getY()];
                break;
            case "here":
                tile = App.getCurrentGame().getPlayingUser().getCurrentTile();
                break;
            default:
                String[] parts = direction.split(" ");
                tile = map[Integer.parseInt(parts[0])][Integer.parseInt(parts[1])];
        }
        for(SaplingEnum saplingEnum : SaplingEnum.values()) {
            if(saplingEnum.name().substring(0,saplingEnum.name().indexOf("_")).equals(saplingName)){
                sapling = new Sapling(saplingEnum.getTree());
            }
        }
        if(sapling == null) {
            return new Result(false, "please enter a valid sapling name");
        }
        if(!App.getCurrentGame().getPlayingUser().getInventory().itemInterfaces.contains(sapling)) {
            return new Result(false, "you don't have the required sapling in your inventory");
        }
        Tree tree = new Tree(sapling.getTree());
        tile.changeTileContents(tree);
        tile.setPlanted(tree);
        App.getCurrentGame().getPlayingUser().getInventory().itemInterfaces.remove(sapling);
        App.getCurrentGame().getMap().addTrees(tree);
        App.getCurrentGame().getPlayingUser().getFarm().addTrees(tree);
        return new Result(true, "sapling planted");
    }

    public void fertilize(Fertilizer fertilize, Tile tile) {
        tile.setFertilized(true);
    }

    public void watering(Tile tile) {
        tile.setWatered(true);
        Crop crop = (Crop)tile.getPlanted();
        crop.setDaysSinceWatered(0);
    }

    public int waterAmount() {
        return 0;
    }

    public Result harvestCrop(Tile tile) {
        if(!App.getCurrentGame().getPlayingUser().getCurrentTool().getToolName().equals("HOE")){
            return new Result(false, "you need a hoe to harvest crop");
        }
        if (tile.getPlanted().getClass() == Crop.class) {
            Crop crop = (Crop) tile.getPlanted();
            App.getCurrentGame().getPlayingUser().getInventory().addItem(crop);
            if (crop.isGiant()) {
            for(int i = 0; i < 3 ; i++){
                App.getCurrentGame().getPlayingUser().getInventory().addItem(crop);
                App.getCurrentGame().getPlayingUser().getInventory().addItem(crop.HarvestAndDropSeed());
            if(crop.isOneTime()){
                crop.getCropTile().setPlanted(null);
                App.getCurrentGame().getMap().getCrops().remove(crop);
                App.getCurrentGame().getPlayingUser().getFarm().getCrops().remove(crop);
                return new Result(true, "crop harvested");
            }
            else{
                crop.setCurrentState(crop.getCurrentState()-1);
                crop.setDaysSinceLastGrowth(0);
                return new Result(true, "crop harvested and is now regrowing");
            }
            }
        }
        }
        else if(tile.getPlanted().getClass() == Tree.class){
            Tree tree = (Tree) tile.getPlanted();
            App.getCurrentGame().getPlayingUser().getInventory().addItem(tree.getFruit());
            return new Result(true, "fruit picked! 8)");
        }
        return new Result(false, "no crop nor tree found there");
    }
    public static void crowAttack() {
        if(App.getCurrentGame().getPlayingUser().getFarm().getCrops().size() > 16){
         App.getCurrentGame().getMap().getCrops().remove(App.getCurrentGame().getMap().getCrops().get(0)); //TODO:make it random
            App.getCurrentGame().getPlayingUser().getFarm().getCrops().remove(App.getCurrentGame().getPlayingUser().getFarm().getCrops().get(0));
        }
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
                    App.getCurrentGame().getMap().addTrees(tree);
                    App.getCurrentGame().getPlayingUser().getFarm().addTrees(tree);
                    tile.addContents(tree);
                    tree.setTile(tile);
                }
            }
        }
    }
    }
    public void addForagingCrop(){
        Random random1 = new Random();
        for(Tile[] tile1 : App.getCurrentGame().getMap().getTiles()){
            for (Tile tile : tile1) {
                if (tile.getPlanted() == null && tile.isPlowed()){
                    if(random1.nextInt(100) < 1){
                        Crop crop;
                        do {
                            crop = new Crop(CropEnum.getRandomForagingCrop());
                        }while (!crop.getSeason().contains(App.getCurrentGame().getSeason()));
                        tile.setPlanted(crop);
                        App.getCurrentGame().getMap().AddCrop(crop);
                        App.getCurrentGame().getPlayingUser().getFarm().AddCrop(crop);
                        tile.addContents(crop);
                    }
                }
            }
        }
    }
    //TODO: because the chance is so small we should add a way to make sure something spawns
    public void addForagingSeeds(){
        Random random1 = new Random();
        for(Tile[] tile1 : App.getCurrentGame().getMap().getTiles()){
            for (Tile tile : tile1) {
                if (tile.getPlanted() == null && tile.isPlowed()){
                    if(random1.nextInt(100) < 1){
                        Seed seed;
                        do{
                        seed = new Seed(ForagingSeeds.getRandomForagingSeed(),tile);
                        }while(!ForagingSeeds.findForagingSeeds(seed.getCropEnum().getSource()).
                                getSeasons().contains(App.getCurrentGame().getSeason()));
//                        plantSeed(seed.getSeedName(), (tile.getCoordination().toString()));
                        tile.addContents(seed);
                    }
                }
            }
        }
    }
    public Result ShowCrop(int x, int y) {
        Tile[][] tiles = App.getCurrentGame().getMap().getTiles();
        if(tiles[x][y].getPlanted() == null){
            return new Result(false, "nothing is planted here");
        }
        String type = tiles[x][y].getPlanted().getClass().getName();
        StringBuilder sb = new StringBuilder();
        switch (type){
            case "Crop":
                Crop crop = (Crop) tiles[x][y].getPlanted();
                sb.append("======================================\n");
                sb.append("Crop name: " + crop.getName());
                sb.append("**************************************\n");
                sb.append("days until full growth: " + (crop.getTotalHarvestTime()-crop.getDaysSincePlanted()));
                sb.append("**************************************\n");
                sb.append("current state: " + crop.getCurrentState());
                sb.append("**************************************\n");
                sb.append("has crop been watered ? " + tiles[x][y].isWatered());
                sb.append("**************************************\n");
                sb.append("has crop been fertilized ? " + tiles[x][y].isFertilized());
                sb.append("======================================\n");
            return new Result(true, sb.toString());
            case "Tree":
                Tree tree = (Tree) tiles[x][y].getPlanted();
                sb.append("======================================\n");
                sb.append("Tree name: " + tree.getName());
                sb.append("**************************************\n");
                sb.append("fruit name: " + tree.getFruit().getName());
                sb.append("**************************************\n");
                sb.append("current state: " + tree.getStages().get(0)); //TODO
                sb.append("======================================\n");
                return new Result(true, sb.toString());
        }
        return new Result(false, "nothing is planted here");
    }

    public Result Fertilize(String fertilizerName, String direction){
        Fertilizer fertilizer = null; //TODO: add different type of fertilizers
        Tile tile;
        Tile[][] tiles = App.getCurrentGame().getMap().getTiles();
        switch (direction){
            case "up":
                tile = tiles[App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.x]
                [App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.y-1];
                break;
                case "down":
            tile = tiles[App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.x]
                            [App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.y+1];
                    break;
                    case "left":
            tile = tiles[App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.x-1]
                                [App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.y];
                        break;
            case "right":
                            tile = tiles[App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.x+1]
                                    [App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.y];
            default:
                tile = App.getCurrentGame().getPlayingUser().getCurrentTile();
        }
        if(App.getCurrentGame().getPlayingUser().getInventory().itemInterfaces.contains(fertilizer)){
            return new Result(false, "you don't have the said fertilizer");
        }
        if(tile.getPlanted() == null){
            return new Result(false, "nothing is planted here");
        }
        tile.setFertilized(true);
        return new Result(true, "the fertilizer has been used");
    }
        public String showWater(){
        //TODO:
        //return App.getCurrentGame().getPlayingUser().getInventory().items
        return "yay water!";
    }



}
