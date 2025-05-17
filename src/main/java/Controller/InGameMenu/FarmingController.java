package Controller.InGameMenu;

import Model.App;
import Model.CropClasses.Crop;
import Model.CropClasses.Sapling;
import Model.CropClasses.Seed;
import Model.CropClasses.Tree;
import Model.Fertilizer;
import Model.Result;
import Model.Tile;
import Model.enums.CraftingItems;
import Model.enums.Crops.*;
import Model.enums.Seasons;
import Model.enums.Shops.Products.GeneralStoreProducts;
import Model.enums.TileType;
import Model.enums.ToolTypes;

import java.util.ArrayList;
import java.util.List;
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
            if (map[x + 1][y + 1].getPlanted() != null && map[x + 1][y].getPlanted() != null && map[x][y + 1].getPlanted() != null) {
                if (sameCrop(tile, map[x + 1][y + 1]) && sameCrop(tile, map[x + 1][y]) && sameCrop(tile, map[x][y + 1])) {
                    result[1] = map[x][y + 1];
                    result[2] = map[x + 1][y];
                    result[3] = map[x + 1][y + 1];
                    return result;
                }
            }
            if (map[x - 1][y + 1].getPlanted() != null && map[x - 1][y].getPlanted() != null && map[x][y + 1].getPlanted() != null) {
                if (sameCrop(tile, map[x - 1][y + 1]) && sameCrop(tile, map[x - 1][y]) && sameCrop(tile, map[x][y + 1])) {
                    result[1] = map[x - 1][y];
                    result[2] = map[x][y + 1];
                    result[3] = map[x - 1][y + 1];
                    return result;
                }
            }
            if (map[x - 1][y - 1].getPlanted() != null && map[x - 1][y].getPlanted() != null && map[x][y - 1].getPlanted() != null) {
                if (sameCrop(tile, map[x - 1][y - 1]) && sameCrop(tile, map[x - 1][y]) && sameCrop(tile, map[x][y - 1])) {
                    result[1] = map[x - 1][y];
                    result[2] = map[x][y - 1];
                    result[3] = map[x - 1][y - 1];
                    return result;
                }
            }
            if (map[x + 1][y - 1].getPlanted() != null && map[x + 1][y].getPlanted() != null && map[x][y - 1].getPlanted() != null) {
                if (sameCrop(tile, map[x + 1][y - 1]) && sameCrop(tile, map[x + 1][y]) && sameCrop(tile, map[x][y - 1])) {
                    result[1] = map[x + 1][y];
                    result[2] = map[x][y - 1];
                    result[3] = map[x + 1][y - 1];
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
        return ((Crop) a.getPlanted()).getName().equals(((Crop) b.getPlanted()).getName());
    }


    public Result plantSeed(String seedName, String direction) {
        Seed seed = null;
        Tile tile;
        Tile[][] map = App.getCurrentGame().getMap().getTiles();
        if (!direction.matches("up|down|left|right|\\d+ \\d+")) {
            return new Result(false, "Invalid direction,you can use\"up\",\"down\",\"left\",\"right\"");
        }
        switch (direction) {
            case "up":
                tile = map[App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().getX()]
                        [App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().getY() + 1];
                break;
            case "down":
                tile = map[App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().x]
                        [App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().getY() - 1];
                break;
            case "left":
                tile = map[App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().x - 1]
                        [App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().getY()];
                break;
            case "right":
                tile = map[App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().x + 1]
                        [App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().getY()];
                break;
            case "here":
                tile = App.getCurrentGame().getPlayingUser().getCurrentTile();
                break;
            default:
                String[] parts = direction.split(" ");
                tile = map[Integer.parseInt(parts[0])][Integer.parseInt(parts[1])];
        }
        for (SeedEnum seedEnum : SeedEnum.values()) {
            if (seedEnum.name().equals(seedName)) {
                seed = new Seed(seedEnum, tile);
                break;
            }
        }
        if (seed == null && !seedName.toLowerCase().equals("mixed seed")) {
            return new Result(false, "please enter a valid seed name");
        }
        if (!App.getCurrentGame().getPlayingUser().backPack.items.containsKey(seed.getSeedEnum())) {
            return new Result(false, "you don't have the required seed in your inventory");
        }

        if (!isFloorplowed(tile)) {
            return new Result(false, "Floor is not plowed");
        }
        App.getCurrentGame().getPlayingUser().backPack.items.put(seed.getSeedEnum(), App.getCurrentGame().getPlayingUser().backPack.items.get(seed.getSeedEnum())-1);
        if(App.getCurrentGame().getPlayingUser().getBackPack().items.get(seed.getSeedEnum()) == 0){
            App.getCurrentGame().getPlayingUser().backPack.items.remove(seed.getSeedEnum());
        }
        tile.setPlowed(false);
        Crop crop = new Crop(seed.getCropEnum(),tile);
        tile.changeTileContents(crop);
        App.getCurrentGame().getMap().AddCrop(crop);
        tile.setSymbol('&');
        tile.setContentSymbol('&');
        if (findTilesWithSameSeed(tile) != null) {
            Crop temp = (Crop) tile.getPlanted();
            for (Tile tile1 : findTilesWithSameSeed(tile)) {
                Crop temp1 = (Crop) tile1.getPlanted();
                if (temp1.getCurrentState() > temp.getCurrentState()) {
                    temp = temp1;
                }
            }
            for (Tile tile1 : findTilesWithSameSeed(tile)) {
                tile1.setPlowed(false);
                tile1.changeTileContents(temp);
                temp.setGiant(true);
                tile1.setSymbol('&');
                tile1.setContentSymbol('&');
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
                        [App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().getY() + 1];
                break;
            case "down":
                tile = map[App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().x]
                        [App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().getY() - 1];
                break;
            case "left":
                tile = map[App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().x - 1]
                        [App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().getY()];
                break;
            case "right":
                tile = map[App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().x + 1]
                        [App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().getY()];
                break;
            case "here":
                tile = App.getCurrentGame().getPlayingUser().getCurrentTile();
                break;
            default:
                String[] parts = direction.split(" ");
                tile = map[Integer.parseInt(parts[0])][Integer.parseInt(parts[1])];
        }
        for (SaplingEnum saplingEnum : SaplingEnum.values()) {
            if (saplingEnum.name().substring(0, saplingEnum.name().indexOf("_")).equals(saplingName)) {
                sapling = new Sapling(saplingEnum.getTree());
            }
        }
        if (sapling == null) {
            return new Result(false, "please enter a valid sapling name");
        }
        if (!App.getCurrentGame().getPlayingUser().backPack.items.containsKey(sapling)) {
            return new Result(false, "you don't have the required sapling in your inventory");
        }
        Tree tree = new Tree(sapling.getTree());
        tile.changeTileContents(tree);
        tile.setPlanted(tree);
        App.getCurrentGame().getPlayingUser().backPack.items.remove(sapling);
        App.getCurrentGame().getMap().addTrees(tree);
        App.getCurrentGame().getPlayingUser().getFarm().addTrees(tree);
        return new Result(true, "sapling planted");
    }

    public void watering(Tile tile) {
        tile.setWatered(true);
        Crop crop = (Crop) tile.getPlanted();
        crop.setDaysSinceWatered(0);
//        App.getCurrentGame().getPlayingUser().backPack.items.get();
    }

    public int waterAmount() {
        return 0;
    }

    public Result harvestCrop(Tile tile) {
        if (!App.getCurrentGame().getPlayingUser().getCurrentTool().getToolType().equals(ToolTypes.SCYTHE)){
            return new Result(false, "you need a SCYTHE to harvest crop");
        }
        if(tile.getPlanted() == null){
            return new Result(false, "no plants here dude sorry :(");
        }

        if(!App.getCurrentGame().getPlayingUser().getBackPack().doesBackPackHasSpace()){
            return new Result(false, "your backpack is full");
        }

        if (tile.getPlanted() instanceof Crop crop) {
            if(crop.getCurrentState() < crop.getStages().size() || crop.getDaysSinceLastGrowth() < crop.getStages().get(crop.getStages().size()-1)){
                return new Result(false, crop.getName() + " is not fully developed yet!");
            }
            ArrayList <Crop> crops = new ArrayList<>();
            crops.add(crop);
            if (crop.isGiant()) {
                Tile[][] temp = App.getCurrentGame().getMap().getTiles();
                ArrayList<Tile> tiles = new ArrayList<>();
                int x = tile.getCoordination().x;
                int y = tile.getCoordination().y;
                if((temp[x][y+1].getPlanted() != null && temp[x][y+1].getPlanted().equals(tile.getPlanted()))&&
                    temp[x+1][y+1].getPlanted() != null && temp[x+1][y+1].getPlanted().equals(tile.getPlanted())&&
                    temp[x+1][y].getPlanted() != null && temp[x+1][y].getPlanted().equals(tile.getPlanted())
                ){
                    crops.add((Crop) temp[x][y+1].getPlanted());
                    crops.add((Crop) temp[x+1][y+1].getPlanted());
                    crops.add((Crop) temp[x+1][y].getPlanted());
                }
                else if((temp[x][y+1].getPlanted() != null && temp[x][y+1].getPlanted().equals(tile.getPlanted()))&&
                        temp[x-1][y+1].getPlanted() != null && temp[x-1][y+1].getPlanted().equals(tile.getPlanted())&&
                        temp[x-1][y].getPlanted() != null && temp[x-1][y].getPlanted().equals(tile.getPlanted())
                ){
                    crops.add((Crop) temp[x][y+1].getPlanted());
                    crops.add((Crop) temp[x-1][y+1].getPlanted());
                    crops.add((Crop) temp[x-1][y].getPlanted());
                }
                else if((temp[x][y-1].getPlanted() != null && temp[x][y-1].getPlanted().equals(tile.getPlanted()))&&
                        temp[x+1][y-1].getPlanted() != null && temp[x+1][y-1].getPlanted().equals(tile.getPlanted())&&
                        temp[x+1][y].getPlanted() != null && temp[x+1][y].getPlanted().equals(tile.getPlanted())
                ){
                    crops.add((Crop) temp[x][y-1].getPlanted());
                    crops.add((Crop) temp[x+1][y-1].getPlanted());
                    crops.add((Crop) temp[x+1][y].getPlanted());
                }
                else if((temp[x][y-1].getPlanted() != null && temp[x][y-1].getPlanted().equals(tile.getPlanted()))&&
                        temp[x-1][y-1].getPlanted() != null && temp[x-1][y-1].getPlanted().equals(tile.getPlanted())&&
                        temp[x-1][y].getPlanted() != null && temp[x-1][y].getPlanted().equals(tile.getPlanted())
                ){
                    crops.add((Crop) temp[x][y-1].getPlanted());
                    crops.add((Crop) temp[x-1][y-1].getPlanted());
                    crops.add((Crop) temp[x-1][y].getPlanted());
                }
            }
                for (Crop crop1 : crops) {
                    App.getCurrentGame().getPlayingUser().backPack.items.put(crop1,
                            App.getCurrentGame().getPlayingUser().backPack.items.getOrDefault(crop1, 0) + 1);
                    Seed seed = crop1.HarvestAndDropSeed();
                    if (seed != null) {
                    App.getCurrentGame().getPlayingUser().backPack.items.put(seed, seed.getSeedAmount());
                    }
                    if (crop1.isOneTime()) {
                        crop1.getCropTile().setPlanted(null);
                        App.getCurrentGame().getMap().getCrops().remove(crop1);
                        App.getCurrentGame().getPlayingUser().getFarm().getCrops().remove(crop1);
                        App.getCurrentGame().getPlayingUser().getFarmingSkill().gainXp();
                        return new Result(true, "crop harvested");
                    } else {
                        crop1.setCurrentState(crop1.getCurrentState() - 1);
                        crop1.setDaysSinceLastGrowth(0);
                        return new Result(true, "crop harvested and is now regrowing");
                    }
                }

        } else if (tile.getPlanted().getClass() == Tree.class) {
            Tree tree = (Tree) tile.getPlanted();
            if (!(tree.getCurrentState() ==tree.getStages().size()) || tree.getDaysSinceLastGrowth()<7) {
                return new Result(false, "fruits aren't developed yet");
            }
            App.getCurrentGame().getPlayingUser().backPack.items.put(tree.getFruit(), 1);
            tree.setDaysSinceLastGrowth(0);
            App.getCurrentGame().getPlayingUser().getFarmingSkill().gainXp();
            return new Result(true, "fruit picked! 8)");
        }
        return new Result(false, "no crop nor tree found there");
    }
    public Tile[] findCloseTiles(Tile tile) {
        Tile[][] tiles = App.getCurrentGame().getMap().getTiles();
        int x = tile.getCoordination().x;
        int y = tile.getCoordination().y;
        Tile[] closeTiles = new Tile[8];
        closeTiles[0] = tiles[x+1][y];
        closeTiles[1] = tiles[x+1][y+1];
        closeTiles[2] = tiles[x][y+1];
        closeTiles[3] = tiles[x-1][y+1];
        closeTiles[4] = tiles[x-1][y];
        closeTiles[5] = tiles[x-1][y-1];
        closeTiles[6] = tiles[x][y-1];
        closeTiles[7] = tiles[x+1][y-1];
        return closeTiles;
    }
    public void crowAttack() {
        if (App.getCurrentGame().getPlayingUser().getFarm().getCrops().size() > 16) {
            Random rand = new Random();
            int random = rand.nextInt(App.getCurrentGame().getPlayingUser().getFarm().getCrops().size());
            if (rand.nextInt(100) < 25) {
            Crop crop = App.getCurrentGame().getPlayingUser().getFarm().getCrops().get(random);
            boolean scareCrow = false;
            for(Tile tile : findCloseTiles(crop.getcropTile())){
                if(tile.getContents().contains(CraftingItems.Scarecrow)){
                    scareCrow = true;
                    break;
                }
            }
            if(!scareCrow){
                System.out.println("Crow attacking " +crop.getName() +
                        " at x = " + crop.getCropTile().coordination.x +
                        " and y = " + crop.getCropTile().coordination.y);
                App.getCurrentGame().getMap().getCrops().remove(crop);
                App.getCurrentGame().getPlayingUser().getFarm().getCrops().remove(crop);
                crop.getCropTile().setPlanted(null);
//                crop.getCropTile().setSymbol('X');
                crop.getCropTile().setContentSymbol('X');
                }
            }
        }
    }

    private static final Random random = new Random();

    public static CropEnum MixedSeedCrop(Seasons season) {
        if (season == null) {
            return CropEnum.CARROT;
        }
        try {
            MixedSeeds mixedSeeds = MixedSeeds.valueOf(season.name().toUpperCase()); // <-- toUpperCase here
            CropEnum[] possibleCrops = mixedSeeds.getPossibleCrops();
            if (possibleCrops.length == 0) {
                return CropEnum.CARROT;
            }
            CropEnum randomCropEnum = possibleCrops[random.nextInt(possibleCrops.length)];
            return randomCropEnum.getCrop();
        } catch (IllegalArgumentException e) {
            return CropEnum.CARROT;
        }
    }
    public void addForAgingTree(){
    Random random1 = new Random();
    for(Tile[] tile1 : App.getCurrentGame().getMap().getTiles()){
        for (Tile tile : tile1) {
            if (tile.getPlanted() == null && tile.isPlowed() && tile.getTileType().equals(TileType.Soil)){
                if(random1.nextInt(100) < 1){
                    Tree tree = new Tree(TreeEnum.getRandomForagingTree());
                    tile.setPlanted(tree);
                    App.getCurrentGame().getMap().addTrees(tree);
                    App.getCurrentGame().getPlayingUser().getFarm().addTrees(tree);
                    tile.setContentSymbol(tree.getSymbol());
                    tile.setSymbol(tree.getSymbol());
                    tile.addContents(tree);
                    tree.setTile(tile);
                    System.out.println("tree spawned at " + tile.getCoordination().x + " " + tile.getCoordination().y);
                }
            }
        }
    }
    }

    public void addForagingCrop() {
        Random random1 = new Random();
        for (Tile[] tile1 : App.getCurrentGame().getMap().getTiles()) {
            for (Tile tile : tile1) {
                if (tile.getPlanted() == null && tile.isPlowed() && tile.getTileType().equals(TileType.Soil)) {
                    if (random1.nextInt(100) < 1) {
                        Crop crop;
                        do {
                            crop = new Crop(CropEnum.getRandomForagingCrop(),tile);
                        } while (!crop.getSeasons().contains(App.getCurrentGame().getGameCalender().getSeason()));
                        tile.setPlanted(crop);
                        tile.setContentSymbol(crop.getSymbol());
                        App.getCurrentGame().getMap().AddCrop(crop);
                        App.getCurrentGame().getPlayingUser().getFarm().AddCrop(crop);
                        tile.addContents(crop);
                    }
                }
            }
        }
    }

    //TODO: because the chance is so small we should add a way to make sure something spawns
    public void addForagingSeeds() {
        FarmingController farmingController = new FarmingController(App.getCurrentGame().getMap().getTiles());
        Random random1 = new Random();
        for (Tile[] tile1 : App.getCurrentGame().getMap().getTiles()) {
            for (Tile tile : tile1) {
                if (tile.getPlanted() == null && tile.getTileType().equals(TileType.Soil) && tile.isPlowed()) {
                    if (random1.nextInt(1000) < 1) {
                        Seed seed;
                        do {
                        if (random1.nextInt(2) == 1) {
                            seed = new Seed(ForagingSeeds.getRandomForagingSeed(), tile);
                        } else {
                            seed = new Seed(farmingController.MixedSeedCrop(App.getCurrentGame().getGameCalender().getSeason()).getSource(), tile, true);
                        }
                        } while (!ForagingSeeds.findForagingSeeds(seed.getCropEnum().getSource()).
                                getSeasons().contains(App.getCurrentGame().getGameCalender().getSeason()));
                        if (tile.isPlowed()) {
                            plantSeed(seed.getName(), (tile.getCoordination().toString()));
                        } else {
                            tile.addContents(seed.getSeedEnum());
                            tile.setContentSymbol(seed.getSymbol());
                        }
                    }
                }
            }
        }
    }

    public void generateStartingPlants() {
//       SeedEnum.MIXED.setCropEnum(FarmingController.MixedSeedCrop(App.getCurrentGame().getSeason()));
       ForagingSeeds.MIXED.setCropEnum(FarmingController.MixedSeedCrop(App.getCurrentGame().getGameCalender().getSeason()));
       System.out.println("Generating starting plants");
        Random random1 = new Random();
        for(Tile[] tile1 : App.getCurrentGame().getMap().getTiles()){
            for (Tile tile : tile1) {
                if (tile.getPlanted() == null && tile.getTileType() == TileType.Soil){
                    if(random1.nextInt(100) < 1){
                        Tree tree = new Tree(TreeEnum.getRandomForagingTree());
                        tile.setPlanted(tree);
                        App.getCurrentGame().getMap().addTrees(tree);
                        App.getCurrentGame().getPlayingUser().getFarm().addTrees(tree);
                        tile.addContents(tree);
                        tile.setContentSymbol(tree.getSymbol());
                        tree.setTile(tile);
//                        System.out.println("tree planted, location : " + tile.coordination);
                    }
                }
            }
        }
        for (Tile[] tile1 : App.getCurrentGame().getMap().getTiles()) {
            for (Tile tile : tile1) {
                if (tile.getPlanted() == null && tile.getTileType() == TileType.Soil) {
                    if (random1.nextInt(100) < 1) {
                        Crop crop;
                        do {
                            crop = new Crop(CropEnum.getRandomForagingCrop(),tile);
                        } while (!crop.getSeasons().contains(App.getCurrentGame().getGameCalender().getSeason()));
                        tile.setPlanted(crop);
                        tile.setContentSymbol(crop.getSymbol());
                        App.getCurrentGame().getMap().AddCrop(crop);
                        App.getCurrentGame().getPlayingUser().getFarm().AddCrop(crop);
                        tile.addContents(crop);
                        tile.setContentSymbol(crop.getSymbol());
//                        System.out.println("crop planted, location : " + tile.coordination);
                    }
                }
            }
        }
            int counter = 0;
        FarmingController farmingController = new FarmingController(App.getCurrentGame().getMap().getTiles());
        for (Tile[] tile1 : App.getCurrentGame().getMap().getTiles()) {
            for (Tile tile : tile1) {
                if (tile.getPlanted() == null && tile.getTileType() == TileType.Soil) {
                    if (random1.nextInt(400) < 1) {
                        Seed seed;
                        do {
                        if (random1.nextInt(2) == 1) {
                            seed = new Seed(ForagingSeeds.getRandomForagingSeed(), tile);
                        } else {
                            seed = new Seed(farmingController.MixedSeedCrop(App.getCurrentGame().getGameCalender().getSeason()).getSource(), tile, true);
                        }
                        } while (!ForagingSeeds.findForagingSeeds(seed.getCropEnum().getSource()).
                                getSeasons().contains(App.getCurrentGame().getGameCalender().getSeason()));
                        if (tile.isPlowed()) {
                            plantSeed(seed.getName(), (tile.getCoordination().toString()));
                        } else {
                            tile.addContents(seed.getSeedEnum());
                            tile.setContentSymbol(seed.getSymbol());
                        }
                    }
                }
            }
        }
        System.out.println("Generating finished");
    }

    public Result ShowCrop(int x, int y) {
        Tile[][] tiles = App.getCurrentGame().getMap().getTiles();
        Tile tile = tiles[x][y];
        if (tiles[x][y].getPlanted() == null) {
            return new Result(false, "nothing is planted here");
        }
        String type = tiles[x][y].getPlanted().getClass().getName();
        StringBuilder sb = new StringBuilder();
        switch (type) {
            case "Model.CropClasses.Crop":
                Crop crop = (Crop) tiles[x][y].getPlanted();
                sb.append("======================================\n");
                sb.append("Crop name: " + crop.getName());
                sb.append("\n**************************************\n");
                sb.append("days until full growth: " + (crop.getTotalHarvestTime() - crop.getDaysSincePlanted()));
                sb.append("\n**************************************\n");
                sb.append("current state: " + crop.getCurrentState());
                sb.append("\n**************************************\n");
                sb.append("has crop been watered ? " + tiles[x][y].isWatered());
                sb.append("\n**************************************\n");
                sb.append("has crop been fertilized ? " + tiles[x][y].isFertilized());
                sb.append("\n======================================\n");
                return new Result(true, sb.toString());
            case "Model.CropClasses.Tree":
                Tree tree = (Tree) tiles[x][y].getPlanted();
                sb.append("======================================\n");
                sb.append("Tree name: " + tree.getName());
                sb.append("\n**************************************\n");
                sb.append("fruit name: " + tree.getFruit().getName());
                sb.append("\n**************************************\n");
                sb.append("current state: " + tree.getCurrentState()); //TODO
                sb.append("\n======================================\n");
                return new Result(true, sb.toString());
        }
        return new Result(false, "nothing is planted here");
    }

    public Result Fertilize(String fertilizerName, String direction) {
        Fertilizer fertilizer = null; //TODO: add different type of fertilizers
        Tile tile;
        Tile[][] tiles = App.getCurrentGame().getMap().getTiles();
        switch (direction) {
            case "up":
                tile = tiles[App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.x]
                        [App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.y - 1];
                break;
            case "down":
                tile = tiles[App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.x]
                        [App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.y + 1];
                break;
            case "left":
                tile = tiles[App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.x - 1]
                        [App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.y];
                break;
            case "right":
                tile = tiles[App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.x + 1]
                        [App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.y];
            default:
                tile = App.getCurrentGame().getPlayingUser().getCurrentTile();
        }
        if (App.getCurrentGame().getPlayingUser().backPack.items.containsKey(fertilizer)) {
            return new Result(false, "you don't have the said fertilizer");
        }
        if (tile.getPlanted() == null) {
            return new Result(false, "nothing is planted here");
        }
        tile.setFertilized(true);
        return new Result(true, "the fertilizer has been used");
    }

    public String showWater() {
        //TODO:
        //return App.getCurrentGame().getPlayingUser().getInventory().items
        return "yay water!";
    }


}
